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

## Number precision

### Jabyinja

|                    	| BYTE 	| SHORT  	| INTEGER     	| LONG                 	| FLOAT        	| DOUBLE                 	|
|--------------------	|------	|--------	|-------------	|----------------------	|--------------	|------------------------	|
| MAX                	| 127  	| 32767  	| 2147483647  	| 9223372036854775807  	| 3.4028235E38 	| 1.7976931348623157E308 	|
| MIN                	| -128 	| -32768 	| -2147483648 	| -9223372036854775808 	| 1.4E-45      	| 4.9E-324               	|
| 0xCA               	| -54  	| -54    	| -54         	| -54                  	|              	|                        	|
| 0xFE               	| -2   	| -2     	| -2          	| -2                   	|              	|                        	|
| 0xBA               	| -70  	| -70    	| -70         	| -70                  	|              	|                        	|
| 0xBE               	| -66  	| -66    	| -66         	| -66                  	|              	|                        	|
| 0xCAFE             	| -2   	| -13570 	| -13570      	| -13570               	|              	|                        	|
| 0xBABE             	| -66  	| -17730 	| -17730      	| -17730               	|              	|                        	|
| 0xCAFEBABE         	| -66  	| -17730 	| -889275714  	| -889275714           	|              	|                        	|
| 0xCAFEBABECAFEBABE 	| -66  	| -17730 	| -889275714  	| -3819410105351357762 	|              	|                        	|
| PI (6)             	|      	|        	|             	|                      	| 3.141592     	| 3.141592              	|
| PI (7)             	|      	|        	|             	|                      	| 3.1415925    	| 3.1415926             	|
| PI (6) ^ 2         	|      	|        	|             	|                      	| 9.8696       	| 9.869600294464002     	|
| PI (6) ^ 3         	|      	|        	|             	|                      	| 31.006258    	| 31.006257328285756    	|
| PI (15)            	|      	|        	|             	|                      	| 3.1415927  	| 3.141592653589793      	|
| PI (16)            	|      	|        	|             	|                      	| 3.1415927    	| 3.141592653589793      	|
| PI (15) ^ 2        	|      	|        	|             	|                      	| 9.869605    	| 9.869604401089358      	|
| PI (15) ^ 3        	|      	|        	|             	|                      	| 31.006279    	| 31.006276680299816     	|

### /usr/bin/java

|                    	| BYTE 	| SHORT  	| INTEGER     	| LONG                 	| FLOAT        	| DOUBLE                 	|
|--------------------	|------	|--------	|-------------	|----------------------	|--------------	|------------------------	|
| MAX                	| 127  	| 32767  	| 2147483647  	| 9223372036854775807  	| 3.4028235E38 	| 1.7976931348623157E308 	|
| MIN                	| -128 	| -32768 	| -2147483648 	| -9223372036854775808 	| 1.4E-45      	| 4.9E-324               	|
| 0xCA               	| -54  	| -54    	| -54         	| -54                  	|              	|                        	|
| 0xFE               	| -2   	| -2     	| -2          	| -2                   	|              	|                        	|
| 0xBA               	| -70  	| -70    	| -70         	| -70                  	|              	|                        	|
| 0xBE               	| -66  	| -66    	| -66         	| -66                  	|              	|                        	|
| 0xCAFE             	| -2   	| -13570 	| -13570      	| -13570               	|              	|                        	|
| 0xBABE             	| -66  	| -17730 	| -17730      	| -17730               	|              	|                        	|
| 0xCAFEBABE         	| -66  	| -17730 	| -889275714  	| -889275714           	|              	|                        	|
| 0xCAFEBABECAFEBABE 	| -66  	| -17730 	| -889275714  	| -3819410105351357762 	|              	|                        	|
| PI (6)             	|      	|        	|             	|                      	| 3.141592     	| 3.141592              	|
| PI (7)             	|      	|        	|             	|                      	| 3.1415925    	| 3.1415926             	|
| PI (6) ^ 2         	|      	|        	|             	|                      	| 9.8696       	| 9.869600294464002     	|
| PI (6) ^ 3         	|      	|        	|             	|                      	| 31.006258    	| 31.006257328285756    	|
| PI (15)            	|      	|        	|             	|                      	| 3.1415927  	| 3.141592653589793      	|
| PI (16)            	|      	|        	|             	|                      	| 3.1415927    	| 3.141592653589793      	|
| PI (15) ^ 2        	|      	|        	|             	|                      	| 9.869605    	| 9.869604401089358      	|
| PI (15) ^ 3        	|      	|        	|             	|                      	| 31.006279    	| 31.006276680299816     	|

Thankfully, they are the exact same

## JVM Instructions checklist

| OPCODE         	| IMPLEMENTED?           	|
|----------------	|------------------------	|
| NOP            	| <ul><li>[x] </li></ul> 	|
| ACONST_NULL    	| <ul><li>[x] </li></ul> 	|
| ICONST_*       	| <ul><li>[x] </li></ul> 	|
| LCONST_*       	| <ul><li>[x] </li></ul> 	|
| FCONST_*       	| <ul><li>[x] </li></ul> 	|
| DCONST_*       	| <ul><li>[x] </li></ul> 	|
| BIPUSH         	| <ul><li>[x] </li></ul> 	|
| SIPUSH         	| <ul><li>[x] </li></ul> 	|
| LDC*           	| <ul><li>[x] </li></ul> 	|
| ILOAD*         	| <ul><li>[x] </li></ul> 	|
| LLOAD*         	| <ul><li>[x] </li></ul> 	|
| FLOAD*         	| <ul><li>[x] </li></ul> 	|
| DLOAD*         	| <ul><li>[x] </li></ul> 	|
| ALOAD*         	| <ul><li>[x] </li></ul> 	|
| IALOAD         	| <ul><li>[x] </li></ul> 	|
| LALOAD         	| <ul><li>[x] </li></ul> 	|
| FALOAD         	| <ul><li>[x] </li></ul> 	|
| DALOAD         	| <ul><li>[x] </li></ul> 	|
| AALOAD         	| <ul><li>[x] </li></ul> 	|
| BALOAD         	| <ul><li>[x] </li></ul> 	|
| CALOAD         	| <ul><li>[x] </li></ul> 	|
| SALOAD         	| <ul><li>[x] </li></ul> 	|
| ISTORE*        	| <ul><li>[x] </li></ul> 	|
| LSTORE*        	| <ul><li>[x] </li></ul> 	|
| FSTORE*        	| <ul><li>[x] </li></ul> 	|
| DSTORE*        	| <ul><li>[x] </li></ul> 	|
| ASTORE*        	| <ul><li>[x] </li></ul> 	|
| IASTORE        	| <ul><li>[x] </li></ul> 	|
| LASTORE        	| <ul><li>[x] </li></ul> 	|
| FASTORE        	| <ul><li>[x] </li></ul> 	|
| DASTORE        	| <ul><li>[x] </li></ul> 	|
| AASTORE        	| <ul><li>[x] </li></ul> 	|
| BASTORE        	| <ul><li>[x] </li></ul> 	|
| CASTORE        	| <ul><li>[x] </li></ul> 	|
| SASTORE        	| <ul><li>[x] </li></ul> 	|
| POP*           	| <ul><li>[x] </li></ul> 	|
| DUP*           	| <ul><li>[x] </li></ul> 	|
| DUP2*          	| <ul><li>[x] </li></ul> 	|
| SWAP           	| <ul><li>[x] </li></ul> 	|
| *ADD           	| <ul><li>[x] </li></ul> 	|
| *SUB           	| <ul><li>[x] </li></ul> 	|
| *MUL           	| <ul><li>[x] </li></ul> 	|
| *DIV           	| <ul><li>[x] </li></ul> 	|
| *REM           	| <ul><li>[x] </li></ul> 	|
| *NEG           	| <ul><li>[x] </li></ul> 	|
| *SHL           	| <ul><li>[x] </li></ul> 	|
| *SHR           	| <ul><li>[x] </li></ul> 	|
| *USHR          	| <ul><li>[x] </li></ul> 	|
| *AND           	| <ul><li>[x] </li></ul> 	|
| *OR            	| <ul><li>[x] </li></ul> 	|
| *XOR           	| <ul><li>[x] </li></ul> 	|
| IINC           	| <ul><li>[x] </li></ul> 	|
| I2*            	| <ul><li>[x] </li></ul> 	|
| L2*            	| <ul><li>[x] </li></ul> 	|
| F2*            	| <ul><li>[x] </li></ul> 	|
| D2*            	| <ul><li>[x] </li></ul> 	|
| LCMP           	| <ul><li>[x] </li></ul> 	|
| FCMP*          	| <ul><li>[x] </li></ul> 	|
| DCMP*          	| <ul><li>[x] </li></ul> 	|
| IF*            	| <ul><li>[ ] </li></ul> 	|
| GOTO           	| <ul><li>[ ] </li></ul> 	|
| ~~JSR~~          	| <ul><li>[x] </li></ul> 	|
| ~~RET~~           | <ul><li>[x] </li></ul> 	|
| TABLESWITCH    	| <ul><li>[ ] </li></ul> 	|
| LOOKUPSWITCH   	| <ul><li>[ ] </li></ul> 	|
| *RETURN        	| <ul><li>[ ] </li></ul> 	|
| *STATIC        	| <ul><li>[ ] </li></ul> 	|
| *FIELD         	| <ul><li>[ ] </li></ul> 	|
| INVOKE*        	| <ul><li>[ ] </li></ul> 	|
| NEW            	| <ul><li>[ ] </li></ul> 	|
| NEWARRAY       	| <ul><li>[x] </li></ul> 	|
| ANEWARRAY      	| <ul><li>[x] </li></ul> 	|
| ARRAYLENGTH    	| <ul><li>[ ] </li></ul> 	|
| ATHROW         	| <ul><li>[ ] </li></ul> 	|
| CHECKCAST      	| <ul><li>[ ] </li></ul> 	|
| MONITOR*       	| <ul><li>[ ] </li></ul> 	|
| WIDE           	| <ul><li>[ ] </li></ul> 	|
| MULTIANEWARRAY 	| <ul><li>[ ] </li></ul> 	|
| IF*NULL        	| <ul><li>[ ] </li></ul> 	|
| GOTO_W         	| <ul><li>[ ] </li></ul> 	|
| ~~JSR_W~~         | <ul><li>[x] </li></ul> 	|
