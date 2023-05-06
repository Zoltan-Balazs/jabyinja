package com.zoltanbalazs.PTI._10._02;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Article {
    private String title;
    private String body;
    private String conclusion;

    private PrintWriter out;
    private ArrayList<Book> refs;

    public Article(String title, String body, String conclusion, String outFile, ArrayList<Book> refs) {
        this.title = title;
        this.body = body;
        this.conclusion = conclusion;
        try {
            this.out = new PrintWriter(new File(outFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.refs = refs;
    }

    public void addBookToReferences(Book b) {
        refs.add(b);
    }

    public void print() {
        out.println(this.title);
        out.println(this.body);
        out.println(this.conclusion);

        for (int i = 0; i < refs.size(); ++i) {
            printBook(refs.get(i));
        }

        out.close();
    }

    private void printBook(Book b) {
        out.println(b.createReference(this.title, 1, b.getPageNumber()));
    }
}
