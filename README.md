# Selenium Java TestNG Project

## Project Structure
```
selenium-java-testing-intellij/
├── .idea/
├── browserDrivers/
│   ├── chromedriver/
│   └── geckodriver/
├── libraries/
│   ├── selenium-server-standalone-3.141.59.jar
│   └── testng-7.4.0.jar
├── out/
├── selenium/
│   └── webdriver/
│       └── Topic_01_Check_Environment.java
└── selenium-java-testing-intellij.iml
```

## Setup Instructions

1. Clone this repository
2. Download browser drivers:
   - GeckoDriver: https://github.com/mozilla/geckodriver/releases
   - ChromeDriver: https://chromedriver.chromium.org/downloads
3. Place drivers in respective folders under `browserDrivers/`
4. Open project in IntelliJ IDEA
5. Run tests using TestNG

## Requirements
- Java 8+
- Selenium 3.141.59
- TestNG 7.4.0
- Firefox/Chrome browser
