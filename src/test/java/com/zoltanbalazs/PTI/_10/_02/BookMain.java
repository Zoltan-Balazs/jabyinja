package com.zoltanbalazs.PTI._10._02;

import java.io.File;
import java.util.ArrayList;

import com.zoltanbalazs.PTI._10._02.PrintedBook.Cover;

public class BookMain {
        public static void main(String[] args) {
                Book b1 = new Book();
                Book b2 = new Book("Douglas Adams, Eoin Colfer, Thomas Tidholm", "The Hitchhiker's Guide to the Galaxy",
                                832);

                PrintedBook pb1 = new PrintedBook();
                PrintedBook pb2 = new PrintedBook("Douglas Adams, Eoin Colfer, Thomas Tidholm",
                                "The Hitchhiker's Guide to the Galaxy", 832, Cover.Softcover);

                EBook eb1 = new EBook("John Steinbeck", "Of Mice and Men", 107, 50);
                EBook eb2 = new EBook("Douglas Adams, Eoin Colfer, Thomas Tidholm",
                                "The Hitchhiker's Guide to the Galaxy", 832,
                                300);

                String basePath = new File(".").getAbsolutePath().replace(".", "")
                                + "src/test/java/com/zoltanbalazs/PTI/_10/_02/";
                Article a = new Article("Teszt Cím", "Teszt Body", "Teszt Konkluzió", basePath + "outFile.txt",
                                new ArrayList<>());

                a.addBookToReferences(b1);
                a.addBookToReferences(b2);
                a.addBookToReferences(pb1);
                a.addBookToReferences(pb2);
                a.addBookToReferences(eb1);
                a.addBookToReferences(eb2);

                a.print();

        }
}
