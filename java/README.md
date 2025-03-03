# CS417 Semester Project
(Initial code retrieved from Professor Kennedy's CS417 Examples, located here: https://github.com/cstkennedy/cs417-examples/tree/master/SemesterProject-CPU-Temps)
 
# Requirements

 * Java 12 (or better)

# Overview

This project processes CPU core temperatures from an input file using:
 * Piecewise Linear Approximation
 * Least Squares Approximation (Currently Unfinished)


# Input/Output Format

 Input will be provided in txt files via command line arguments.

 Example Input:

```
61.0 63.0 50.0 58.0
80.0 81.0 68.0 77.0
```

Output will be returned in .txt files.

Example Output:

```
0 <= x < 30; y = 61.0000 + 0.6333 x; interpolation
0 <= x <= 120; y = 67.4000 + 0.0567 x; least-squares
```

# Compilation

Within the CS417-Semester-Project/java directory, run:

```
./gradlew build
```

This will compile the code to be executed in the following step.

# Sample Execution & Output 

If run without command line arguments, using 

```
java -jar build/libs/CS417_project.jar 
```

The following output will be provided:

```
Please provide a filename.
Exception in thread "main" java.lang.NullPointerException: Cannot invoke "java.io.BufferedReader.lines()" because "inputTemps" is null
        at edu.odu.cs.cs417.TemperatureParser.parseRawTemps(TemperatureParser.java:93)
        at edu.odu.cs.cs417.TemperatureParser.parseRawTemps(TemperatureParser.java:77)
        at ParseTempsDriver.main(ParseTempsDriver.java:36)
```

If run using

```
java -jar build/libs/CS417_project.jar filename-01.txt
```

assuming the file provided data from 4 cores, output will be named in a similar fashion to:

```
filename-01-core-00.txt
filename-01-core-01.txt
filename-01-core-02.txt
filename-01-core-03.txt
```
