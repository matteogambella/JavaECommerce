@echo off

set L=C:\prg\libs\
set LIBS=%L%xstream-1.4.7.jar;%L%xmlpull-1.1.3.1.jar;%L%xpp3_min-1.1.4c.jar;%L%mysql-connector-java-5.1.34-bin.jar;.

C:\prg\jdk8\bin\javac -cp %LIBS% *.java

start cmd /k "color 2F && c:\prg\jdk8\bin\java LogNavigazioneGUI 4242 "log.xml" "eventogui.xsd""

C:\prg\jdk8\bin\java -cp %LIBS% Progetto

