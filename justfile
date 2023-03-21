alias cl := clean
alias co := compile
alias r := run-default
alias t := thesis

version := "0.9.0"

clean:
    git clean -d -x -f

compile:
    mvn package

run-default: compile
    java -jar target/jabyinja-{{version}}.jar target/classes/com/zoltanbalazs/Main.class

run FILE: compile
    java -jar target/jabyinja-{{version}}.jar {{FILE}}

thesis:
    cd thesis && pdflatex --interaction nonstopmode -halt-on-error -file-line-error thesis.tex && bibtex thesis && pdflatex --interaction nonstopmode -halt-on-error -file-line-error thesis.tex && pdflatex thesis.tex