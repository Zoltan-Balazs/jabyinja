# Ja(va) By(tecode) In(terpreter) (in) Ja(va)

My BSc thesis work for [Eötvös Loránd University's Computer Science curriculum](https://www.inf.elte.hu/en)

## Prerequisities

- Code:
    - [JDK17](https://openjdk.org/projects/jdk/17/)
    - [Maven 3.9.0](https://maven.apache.org/download.cgi)
- Thesis:
    - Arch:
        - [biber](https://archlinux.org/packages/community/any/biber/)
        - [ghostscript](https://archlinux.org/packages/extra/x86_64/ghostscript/)
        - [texlive-bibtexextra](https://archlinux.org/packages/extra/any/texlive-bibtexextra/)
        - [texlive-full](https://aur.archlinux.org/packages/texlive-full)
    - Debian:
        - [biber](https://packages.debian.org/search?keywords=biber)
        - [ghostscript](https://packages.debian.org/search?keywords=ghostscript)
        - [texlive-bibtex-extra](https://packages.debian.org/search?keywords=texlive-bibtex-extra)
        - [texlive-font-utils](https://packages.debian.org/search?keywords=texlive-font-utils)
        - [texlive-lang-european](https://packages.debian.org/search?keywords=texlive-lang-european)
        - [texlive-latex-base](https://packages.debian.org/search?keywords=texlive-latex-base)
        - [texlive-science](https://packages.debian.org/search?keywords=texlive-science)
    - Other distros:
        *Find the closest matching packages, or install the whole of latex from the official source*
        - Remove existing installation:
            ```sh
            rm -rf /usr/local/texlive/20*
            rm -rf ~/.texlive20*
            ```
        - Create a folder for texlive:
            ```sh
            sudo mkdir -p /usr/local/texlive/$(date +%Y)
            sudo chown -R "$USER" /usr/local/texlive
            ```
        - Install from the official source:
            ```sh
            mkdir /tmp/texlive
            cd /tmp/texlive
            wget http://mirror.ctan.org/systems/texlive/tlnet/install-tl.zip
            unzip ./install-tl.zip -d install-tl
            cd install-tl/install-tl-$(date +%Y%m%d)
            perl ./install-tl
            ```

            *Once in perl console, enter `i`*
        - Set variables:
            ```sh
            export MANPATH="$MANPATH:/usr/local/texlive/$(date +%Y)/texmf-dist/doc/man"
            export INFOPATH="$INFOPATH:/usr/local/texlive/$(date +%Y)/texmf-dist/doc/info"
            export PATH=/usr/local/texlive/$(date +%Y)/bin/x86_64-linux:$PATH
            ```
- Other:
    - [Just](https://github.com/casey/just) *(optional - for use with justfile)*

## Building the code

Normally:
```sh
$ mvn package
$ java -jar target/jabyinja-*.jar <optional class file>
```

With Just:
```sh
$ just co
$ java -jar target/jabyinja-*.jar <optional class file>
```

## Generating a pdf from the thesis

*Ignore any warnings*

Normally:
```sh
$ cd thesis/
$ pdflatex thesis.tex
$ bibtex thesis
$ pdflatex thesis.tex
$ pdflatex thesis.tex
```

With Just:
```sh
$ just t
```

## Usage

Without specifying any files, the program will try to interpret `Main.class`, located in the same directory as from where you ran the program from
If you do intend to specifcy a given file, do it with a single argumentum, where you name the .class file (i.e. if your file is named Example.class, you write `Example.class` **NOT** `Example`)
