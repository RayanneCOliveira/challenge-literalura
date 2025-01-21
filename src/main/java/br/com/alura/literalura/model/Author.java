package br.com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String booksWritten;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Book> book;

    public Author() {}

    public Author(String name, int birthYear, int deathYear, String booksWritten) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.booksWritten = booksWritten;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        book.forEach(book1 -> book1.setAuthor(this));
        this.book = book;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public String getBooksWritten() {
        return booksWritten;
    }

    public void setBooksWritten(String booksWritten) {
        this.booksWritten = booksWritten;
    }

    @Override
    public String toString() {
        return """ 
                ---- Dados do(a) Autor(a) ----
                
                Autor(a): %s
                Ano de nascimento: %d
                Ano de falecimento: %d
                Livro(s): [%s]
                
                ------------------------------
                """.formatted(name, birthYear, deathYear, booksWritten);
    }
}