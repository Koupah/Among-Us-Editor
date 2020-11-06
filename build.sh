#!/bin/sh

# build script for people who are too lazy to open eclipse
# made for linux, probably works on bsd/mac if you have all of the depends
# depends:
# javac
# jar
# javafx
# find

rm out -rf
# long and complicated command to source javafx swing and rpc libraries *and* build for java 7
javac -d ./out $(find -name "*java") -cp /usr/lib/jvm/java-11-openjdk/lib/javafx.swing.jar -cp Among-Us-Editor/java-discord-rpc-2.0.1-all.jar -source 7 -target 1.7
# used in about 2 seconds to get the images needed. please provide a better solution.
cp -r Among-Us-Editor/src/* out/
cp Among-Us-Editor/java-discord-rpc-2.0.1-all.jar out
cd out
# remove every java file in the out directory. remove this to distribute the source code with the jar (but make the filesize larger)
rm $(find -name "*java")
# get the contents of the java-discord-rpc library into the out folder
unzip -q java-discord-rpc-2.0.1-all.jar
rm -r java-discord-rpc-2.0.1-all.jar META-INF
# finally, create the jar file
jar -c --main-class=club.koupah.AUEditorMain -f among-us-editor.jar *
cd ..
