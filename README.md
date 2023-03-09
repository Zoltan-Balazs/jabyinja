# Ja(va) By(tecode) In(terpreter) (in) Ja(va)

My BSc thesis work for [Eötvös Loránd University's Computer Science curriculum](https://www.inf.elte.hu/en)

## Prerequisities

- The code uses *switch* expressions, so at the very least, you should have **JDK 12** installed to be able to build it
- To use the justfile (not necessary), you need to have **Just** installed

## Building the code

- As per the justfile, with a few simple commands you can already start interpreting Java code

### Without creating a jar file

```sh
$ cd src
$ javac *.java
$ java -Dfile.encoding=UTF-8 Main <optional class file>
```

### With creating a jar file

```sh
$ cd src
$ javac *.java
$ mkdir -p com/zoltanbalazs/
$ mv *.class com/zoltanbalazs/
$ jar cmvf ../META-INF/MANIFEST.MF jabyinja.jar com/zoltanbalazs/
$ java -jar jabyinja.jar <optional class file>
```

## Usage

Without specifying any files, the program will try to interpret `Main.class`, located in the same directory as from where you ran the program from
If you do intend to specifcy a given file, do it with a single argumentum, where you name the .class file (i.e. if your file is named Example.class, you write `Example.class` **NOT** `Example`)