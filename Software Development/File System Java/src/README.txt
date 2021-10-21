open command line.
cd to INSIDE src folder. 

cd Desktop/VSFS/src
javac *.java
java VSFS list FSRecord.notes
java VSFS copyin FSRecord.notes EF.txt IF
java VSFS mkdir FSRecord.notes dir1/dir2/
java VSFS rm FSRecord.notes IF
java VSFS rmdir FSRecord.notes dir1/dir2/

javac -cp junit-4.12.jar:. *.java
java -cp junit-4.12.jar:hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore Test1