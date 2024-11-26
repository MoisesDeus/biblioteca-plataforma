package br.com.mddeveloper.Model;

public class Catalog {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int year;
    private int pages;

    public Catalog(int id, String title, String author, String genre, int year, int pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.pages = pages;
    }

    public Catalog() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Catalogo: " +
               "id:" + id +
               ", Titulo: " + title + '\'' +
               ", Autor: " + author + '\'' +
               ", Gênero: " + genre + '\'' +
               ", Ano de Lançamento: " + year +
               ", Quantidade de páginas: " + pages;
    }
}
