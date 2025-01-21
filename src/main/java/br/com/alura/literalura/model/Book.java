package br.com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    private String title;
    private String authorName;
    private int downloads;
    private String languages;



    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Author author;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Book() {}

    public Book(String title, List<String> languages, int downloads, Author author) {
        this.title = title;
        this.author = author;
        this.authorName = author.getName();
        this.languages = languages.isEmpty() ? null : languages.get(0);
        this.downloads = downloads;
    }

    public Book(Book foundBook) {
        this.title = foundBook.title;
        this.authorName = foundBook.getAuthor().getName();
        this.languages = foundBook.languages;
        this.downloads = foundBook.downloads;
    }


    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String tile) {
        this.title = title;
    }

    @Override
    public String toString() {
        return """ 
                ---- Dados do Livro ----
                
                Título: %s
                Nome do(a) Autor(a): %s
                Idioma: %s
                Número de downloads: %d
                
                ------------------------
                """.formatted(title, authorName, languages, downloads);
    }
}
