#!/bin/bash

# Usage: ./run-single.sh Topic_06_Browser_Exercise

if [ -z "$1" ]; then
    echo "Usage: ./run-single.sh <ClassName>"
    echo "Example: ./run-single.sh Topic_06_Browser_Exercise"
    exit 1
fi

CLASS_NAME=$1

echo "=== Compiling listeners and $CLASS_NAME.java ==="
javac -cp "libraries/*" -d out selenium/listeners/*.java 2>/dev/null
javac -cp "libraries/*:out" -d out selenium/webdriver/${CLASS_NAME}.java

if [ $? -eq 0 ]; then
    echo "=== Compilation successful ==="
    echo "=== Running test class: webdriver.$CLASS_NAME ==="
    java -cp "out:libraries/*" org.testng.TestNG -usedefaultlisteners false -testclass webdriver.${CLASS_NAME}
else
    echo "=== Compilation failed ==="
    exit 1
fi
