package com.zoltanbalazs.PTI._10._02;

import java.util.ArrayList;

import com.zoltanbalazs.PTI._10._02.PrintedBook.Cover;

public class BookMain {
        public static void main(String[] args) {
                Book b1 = new Book();
                Book b2 = new Book("Douglas Adams, Eoin Colfer, Thomas Tidholm", "The Hitchhiker's Guide to the Galaxy",
                                832);

                /*
                 * System.out.println(b1.getShortName());
                 * System.out.println(b2.getShortName());
                 * System.out.println(b1);
                 * System.out.println(b2);
                 * 
                 * System.out.println(b1.createReference("Teszt", 15, 20));
                 */

                PrintedBook pb1 = new PrintedBook();
                PrintedBook pb2 = new PrintedBook("Douglas Adams, Eoin Colfer, Thomas Tidholm",
                                "The Hitchhiker's Guide to the Galaxy", 832, Cover.Softcover);

                /*
                 * System.out.println(pb1.getShortName() + " " + pb1.getPrice());
                 * System.out.println(pb2.getShortName() + " " + pb2.getPrice());
                 * System.out.println(pb1);
                 * System.out.println(pb2);
                 * 
                 * System.out.println(pb1.createReference("Teszt", 15, 20));
                 */

                EBook eb1 = new EBook("John Steinbeck", "Of Mice and Men", 107, 50);
                EBook eb2 = new EBook("Douglas Adams, Eoin Colfer, Thomas Tidholm",
                                "The Hitchhiker's Guide to the Galaxy", 832,
                                300);

                /*
                 * System.out.println(eb1.getShortName() + " " + eb1.getPrice());
                 * System.out.println(eb2.getShortName() + " " + eb2.getPrice());
                 * System.out.println(eb1);
                 * System.out.println(eb2);
                 * 
                 * System.out.println(eb1.createReference("Teszt", 15, 20));
                 * System.out.println(eb1.createReference("Teszt", "2021-04-15"));
                 */

                String basePath = "/home/zoli/Git/jabyinja/target/test-classes/com/zoltanbalazs/PTI/_10/_02/";
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
