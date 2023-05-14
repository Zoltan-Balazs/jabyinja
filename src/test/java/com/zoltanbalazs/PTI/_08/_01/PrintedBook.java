package com.zoltanbalazs.PTI._08._01;

public class PrintedBook extends Book {
    public enum Cover {
        Softcover,
        Hardcover;
    }

    private Cover cover;

    public PrintedBook() {
        this.cover = Cover.Hardcover;
        this.pageNumber += 6;
    }

    public PrintedBook(String author, String title, int pageNumber, Cover cover) {
        super(author, title, pageNumber + 6);
        this.cover = cover;
    }

    public int getPrice() {
        return this.cover == Cover.Softcover ? pageNumber * 2 : pageNumber * 3;
    }

    @Override
    public String createReference(String article, int startNumber, int endNumber) {
        StringBuilder bld = new StringBuilder();

        bld.append(super.toString()).append(" [").append(startNumber).append("-").append(endNumber)
                .append("] referenced in article: ").append(article);

        return bld.toString();
    }

    @Override
    public String toString() {
        StringBuilder bld = new StringBuilder();

        bld.append(super.toString()).append(" - ").append(this.cover);

        return bld.toString();
    }
}
