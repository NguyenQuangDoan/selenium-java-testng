#!/bin/bash

echo "=== Compiling Java files ==="
javac -cp "libraries/*" -d out selenium/listeners/*.java 2>/dev/null
javac -cp "libraries/*:out" -d out selenium/webdriver/*.java

if [ $? -eq 0 ]; then
    echo "=== Compilation successful ==="
    echo "=== Running TestNG tests ==="
    java -cp "out:libraries/*" org.testng.TestNG -usedefaultlisteners false testng.xml
else
    echo "=== Compilation failed ==="
    exit 1
fi
