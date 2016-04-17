public class Book {
    private String name;
    private String author;
    private String owner;
    Book(String name, String author, String owner) {
        this.name = name;
        this.author = author;
        this.owner = owner;
    }
    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    public String getOwner() {
        return owner;
    }
}
