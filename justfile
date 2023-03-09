alias cl := clean
alias co := compile
alias r := run-default
alias p := package

clean:
    rm -rf src/com/
    rm src/*.jar

compile:
    cd src/ && javac *.java && mkdir -p com/zoltanbalazs/ && mv *.class com/zoltanbalazs/ 

run-default: compile
    cd src/ && java -Dfile.encoding=UTF-8 com.zoltanbalazs.Main

run FILE: compile
    cd src/ && java -Dfile.encoding=UTF-8 com.zoltanbalazs.Main {{FILE}}

package: compile
    cd src/ && jar cmvf ../META-INF/MANIFEST.MF jabyinja.jar com/zoltanbalazs/ && mv jabyinja.jar ../
