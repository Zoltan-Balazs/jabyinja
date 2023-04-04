# Ja(va) By(tecode) In(terpreter) (in) Ja(va)

My BSc thesis work for [Eötvös Loránd University's Computer Science curriculum](https://www.inf.elte.hu/en)

## Prerequisities

- Code:
    - [JDK17](https://openjdk.org/projects/jdk/17/)
    - [Maven 3.9.0](https://maven.apache.org/download.cgi)
- Thesis:
    <details>
    <summary>Arch Linux</summary>

    - [biber](https://archlinux.org/packages/community/any/biber/)
    - [ghostscript](https://archlinux.org/packages/extra/x86_64/ghostscript/)
    - [pgf-umlcd](https://aur.archlinux.org/packages/pgf-umlcd)
    - [texlive-bibtexextra](https://archlinux.org/packages/extra/any/texlive-bibtexextra/)
    - [texlive-full](https://aur.archlinux.org/packages/texlive-full)
    </details>
    
    <details>
    <summary>Debian / Ubuntu</summary>

    - [biber](https://packages.debian.org/search?keywords=biber)
    - [ghostscript](https://packages.debian.org/search?keywords=ghostscript)
    - [texlive-bibtex-extra](https://packages.debian.org/search?keywords=texlive-bibtex-extra)
    - [texlive-font-utils](https://packages.debian.org/search?keywords=texlive-font-utils)
    - [texlive-lang-european](https://packages.debian.org/search?keywords=texlive-lang-european)
    - [texlive-latex-base](https://packages.debian.org/search?keywords=texlive-latex-base)
    - [texlive-pictures](https://packages.debian.org/search?keywords=texlive-pictures)
    - [texlive-science](https://packages.debian.org/search?keywords=texlive-science)
    </details>

    <details>
    <summary>MacOS</summary>

    - [biber](https://formulae.brew.sh/formula/biber)
    - [ghostscript](https://formulae.brew.sh/formula/ghostscript)
    - [texlive](https://formulae.brew.sh/formula/texlive)
    </details>

    <details>
    <summary>Other distros</summary>

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
    </details>

    <details>
    <summary>TeX Packages</summary>

    *Only install if on MacOS / there is an error in generating the pdf*
    - [adjustbox](https://ctan.org/pkg/adjustbox)
    - [algpseudocode](https://www.ctan.org/pkg/algorithmicx)
    - [amsfonts](https://ctan.org/pkg/amsfonts)
    - [amsmath](https://ctan.org/pkg/amsmath)
    - [amsthm](https://ctan.org/pkg/amsthm)
    - [array](https://ctan.org/pkg/array)
    - [listingsutf8](https://ctan.org/pkg/listingsutf8)
    - [longtable](https://ctan.org/pkg/longtable)
    - [makecell](https://ctan.org/pkg/makecell)
    - [minted](https://ctan.org/pkg/minted)
    - [multirow](https://ctan.org/pkg/multirow)
    - [paralist](https://ctan.org/pkg/paralist)
    - [pgf-umlcd](https://www.ctan.org/pkg/pgf-umlcd)
    - [rotating](https://ctan.org/pkg/rotating)
    - [subcaption](https://ctan.org/pkg/subcaption)
    - [todonotes](https://ctan.org/pkg/todonotes)

    ```sh
    $ tlmgr install adjustbox algpseudocode amsfonts \
            amsmath amsthm array listingsutf8 longtable \
            makecell minted multirow paralist pgf-umlcd \
            rotating subcaption todonotes
    ```
    </details>

        
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

## Generating a PDF from the thesis

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

Without specifying any files, the program will try to interpret `Main.class`, located in the same directory as from where you ran the program from.
If you do intend to specifcy a given file, do it with a single argument, where you name the .class file (i.e. if your file is named Example.class, you write `Example.class` **NOT** `Example`)
