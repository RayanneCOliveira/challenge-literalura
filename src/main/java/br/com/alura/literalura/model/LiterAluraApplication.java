package br.com.alura.literalura.model;

import br.com.alura.literalura.repository.AuthorRepository;
import br.com.alura.literalura.repository.BookRepository;
import br.com.alura.literalura.service.ApiService;
import br.com.alura.literalura.service.DataConversion;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.*;

public class LiterAluraApplication {

    private final Scanner scanner = new Scanner(System.in);
    private final ApiService api = new ApiService();
    private final DataConversion converter = new DataConversion();
    private List<Book> books = new ArrayList<>();
    private List<Author> author;
    private final BookRepository repository;
    private final AuthorRepository authorRepository;

    public LiterAluraApplication(BookRepository repository, AuthorRepository authorsRepository) {
        this.repository = repository;
        this.authorRepository = authorsRepository;
    }

    public void showMenu() {

        var choice = -1;

        while(choice != 0) {

            var menu = """
                    
                    Escolha uma opção:
                    
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    0 - Sair
                    
                    """;

            System.out.println(menu);

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    listRegisteredBooks();
                    break;
                case 3:
                    listRegisteredAuthors();
                    break;
                case 4:
                    listActorsAlive();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Volte sempre!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void searchBook() {

        var bookInfos = getBookInfos().result().stream()
                .flatMap(book -> book.authorInfos().stream()
                        .map(authorInfos ->
                                new Book(book.title(), Collections.singletonList((String) book.languages().get(0)),book.downloads(),
                                        new Author(authorInfos.name(),
                                                Optional.ofNullable( authorInfos.birthYear()).orElse(0),
                                                Optional.ofNullable(authorInfos.deathYear()).orElse(0),
                                                book.title()))))
                .toList();

        Book foundBook = bookInfos.get(0);
        Book book = new Book(foundBook);

        Author author = new Author(bookInfos.get(0).getAuthorName(),
                bookInfos.get(0).getAuthor().getBirthYear(),
                bookInfos.get(0).getAuthor().getDeathYear(),
                bookInfos.get(0).getTitle()
        );

        try {
            book.setAuthor(author);
            author.setBook(books);
            repository.save(book);
            System.out.println(book);
        } catch (InvalidDataAccessApiUsageException e){
            System.out.println("Dados de acesso inválidos" + e);
        }

    }

    private void listRegisteredBooks() {
        books = repository.findAll();
        books.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .sorted(Comparator.comparing(Book::getAuthorName)).forEach(System.out::println);
    }

    private void listRegisteredAuthors() {
        author = authorRepository.findAll();
        author.forEach(System.out::println);
    }

    private void listActorsAlive() {
        System.out.println("Insira o ano que deseja pesquisar:");
        var ano = scanner.nextInt();
        author = authorRepository.findAll();
        author.stream()
                .filter(d-> d.getBirthYear() <= ano && d.getDeathYear() >= ano)
                .forEach(System.out::println);
    }

    private void listBooksByLanguage() {

        System.out.println("""
                
                Insira o idioma para realizar a busca:
                
                es - Espanhol
                en - Inglês
                fr - Francês
                pt - Português
                
                """);

        var language = scanner.nextLine();
        List<Book> books = repository.findAll();

        List<Book> foundBook = books.stream()
                .filter(book -> Objects.equals(book.getLanguages(), language))
                .toList();

        if (foundBook.isEmpty()){

            System.out.println("Não existem livros nesse idioma no banco de dados.");

        } else { foundBook.forEach(System.out::println); }
    }

    private ResultInfos getBookInfos() {

        System.out.println("Insira o nome do livro que você deseja procurar:");

        var bookName = scanner.nextLine().replace(" ", "+");
        String ADDRESS = "https://gutendex.com/books/?search=";
        var json = api.getData(ADDRESS + bookName);
        return converter.getData(json, ResultInfos.class);
    }
}
