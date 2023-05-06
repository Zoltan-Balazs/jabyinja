package com.zoltanbalazs.PTI._08._01;

;

public class EBook extends Book {
    private int pdfSize;

    public EBook(String author, String title, int pageNumber, int fileSize) {
        super(author, title, pageNumber);
        this.pdfSize = fileSize;
    }

    public int getPrice() {
        return this.pageNumber + this.pdfSize;
    }

    @Override
    public String createReference(String article, int startNumber, int endNumber) {
        StringBuilder bld = new StringBuilder();

        bld.append(super.toString()).append(" (PDF size: ").append(this.pdfSize).append(") ]").append(startNumber)
                .append("-").append(endNumber).append("] referenced in article: ").append(article);

        return bld.toString();
    }

    public String createReference(String article, String date) {
        StringBuilder bld = new StringBuilder();

        bld.append(super.toString()).append(" (PDF size: ").append(this.pdfSize).append(") ]")
                .append("] referenced in article: ").append(article).append(", accessing PDF date: ").append(date);

        return bld.toString();
    }
}
