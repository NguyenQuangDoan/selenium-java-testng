#!/bin/bash

cat > /tmp/TestFirefox.java << 'EOF'
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestFirefox {
    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/browserDrivers/geckodriver");
        
        try {
            System.out.println("Starting Firefox...");
            WebDriver driver = new FirefoxDriver();
            System.out.println("Firefox started successfully!");
            driver.get("https://www.google.com");
            System.out.println("Page title: " + driver.getTitle());
            driver.quit();
            System.out.println("Test passed!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
EOF

cd /tmp
javac -cp "/Users/doannguyen/Desktop/selenium-java-testng/libraries/*" TestFirefox.java
java -cp ".:/Users/doannguyen/Desktop/selenium-java-testng/libraries/*" TestFirefox
