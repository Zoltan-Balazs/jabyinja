package com.zoltanbalazs.PTI._08._01;

public class Book {
    private String author;
    private String title;
    protected int pageNumber;

    public Book() {
        this.author = "John Steinbeck";
        this.title = "Of Mice and Men";
        this.pageNumber = 107;
    }

    public Book(String author, String title, int pageNumber) {
        if (title.length() < 4 || author.length() < 2) {
            throw new IllegalArgumentException("Incorrect input data!");
        }

        this.author = author;
        this.title = title;
        this.pageNumber = pageNumber;
    }

    public String getShortName() {
        return this.author.substring(0, 2) + ": " + this.title.substring(0, 4) + " (" + this.pageNumber + ")";
    }

    public String createReference(String article, int startNumber, int endNumber) {
        StringBuilder bld = new StringBuilder();

        bld.append(this.getShortName()).append(" [").append(startNumber).append("-").append(endNumber)
                .append("] referenced in article: ").append(article);

        return bld.toString();
    }

    @Override
    public String toString() {
        StringBuilder bld = new StringBuilder();

        bld.append(this.author).append(": ").append(this.title).append(" (").append(this.pageNumber).append(")");

        return bld.toString();
    }
}
