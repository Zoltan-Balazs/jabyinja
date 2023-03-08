alias cl := clean
alias co := compile
alias r := run-default

clean:
    rm src/*.class

compile:
    javac src/*.java

run-default:
    cd src && java -Dfile.encoding=UTF-8 Main

run FILE:
    cd src && java -Dfile.encoding=UTF-8 Main {{FILE}}

package:
    cd src && jar -cmf ../manifest.mf jabyinja.jar