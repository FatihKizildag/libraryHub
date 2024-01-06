class Book {
    private int id;
    private String title;
    private String author;
    private int dueDays;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.dueDays = 0; // Not every book was initially borrowed
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleById(int bookId) {
        if (this.id == bookId) {
            return this.title;
        }
        return null;
    }

    public String getAuthor() {
        return author;
    }

    public int getDueDays() {
        return dueDays;
    }

    public void setDueDays(int dueDays) {
        this.dueDays = dueDays;
    }
}