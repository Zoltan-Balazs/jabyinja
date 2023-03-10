alias cl := clean
alias co := compile
alias r := run-default

version := "0.9.0"

clean:
    rm -rf target/

compile:
    mvn package

run-default: compile
    java -jar target/jabyinja-{{version}}.jar target/classes/com/zoltanbalazs/Main.class

run FILE: compile
    java -jar target/jabyinja-{{version}}.jar {{FILE}}