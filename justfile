alias cl := clean
alias co := compile
alias r := run-default

clean:
    rm src/*.class

compile:
    javac src/*.java

run-default:
    cd src && java Main

run FILE:
    cd src && java Main {{FILE}}