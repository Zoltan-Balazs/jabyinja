# Ja(va) By(tecode) In(terpreter) (in) Ja(va)

My BSc thesis work for [Eötvös Loránd University's Computer Science curriculum](https://www.inf.elte.hu/en)

## Prerequisities

- [JDK17](https://openjdk.org/projects/jdk/17/)
- [Maven 3.9.0](https://maven.apache.org/download.cgi)
- [Just](https://github.com/casey/just) *(optional - for use with justfile)*

## Building the code

- As per the justfile, with a few simple commands you can already start interpreting Java code

```sh
$ mvn package
$ java -jar target/jabyinja.jar <optional class file>
```

Or with just:
```sh
$ just co
$ java -jar target/jabyinja.jar <optional class file>
```

## Usage

Without specifying any files, the program will try to interpret `Main.class`, located in the same directory as from where you ran the program from
If you do intend to specifcy a given file, do it with a single argumentum, where you name the .class file (i.e. if your file is named Example.class, you write `Example.class` **NOT** `Example`)
