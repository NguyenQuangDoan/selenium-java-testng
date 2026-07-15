package webdriver;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.function.Function;

@Listeners(TestListener.class)
public class Topic_29_Fluent_Wait {

    WebDriver driver;
    FluentWait<WebDriver> fluentWait;
    FluentWait<WebElement> elementFluentWait;
    WebDriverWait explicitWait;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    boolean headless = false;

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Mac")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        }

        FirefoxOptions options = new FirefoxOptions();
        if (osName.contains("Mac")) {
            options.setBinary("/Applications/Firefox.app/Contents/MacOS/firefox");
        }
        options.addPreference("security.insecure_field_warning.contextual.enabled", false);
        options.addPreference("security.warn_submit_insecure", false);
        options.setAcceptInsecureCerts(true);
        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new FirefoxDriver(options);
        if (!headless) {
            driver.manage().window().maximize();
        }
        
    }

    //@Test
    public void TC_01(){
        
        fluentWait = new FluentWait<>(driver);
        fluentWait.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(100)).ignoring(NoSuchElementException.class);

        fluentWait.until(new Function<WebDriver, String>() {
            public String apply(WebDriver driver) {
                return driver.findElement(By.id("some-element")).getText();
            }
        });
    }

    //@Test
    public void TC_02(){
        driver.get("https://automationfc.github.io/fluent-wait/");
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("javascript_countdown_time")));
        
        elementFluentWait = new FluentWait<>(driver.findElement(By.id("javascript_countdown_time")));
        elementFluentWait.withTimeout(Duration.ofSeconds(15)).pollingEvery(Duration.ofMillis(100)).ignoring(NoSuchElementException.class);
        
        Assert.assertTrue(elementFluentWait.until(new Function<WebElement, Boolean>() {
            public Boolean apply(WebElement element) {
                return element.getText().endsWith("00");
            }
        }));
    }

    @Test
    public void TC_03(){
        driver.get("https://automationfc.github.io/dynamic-loading/");
        clickToElement("#start button");
        isElementDisplayed("#finish h4");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
    
    private String getDateTime() {
        return java.time.LocalDateTime.now().toString();
    }

    private WebElement getElement(String cssLocator) {
        fluentWait = new FluentWait<>(driver);
        fluentWait.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(100)).ignoring(NoSuchElementException.class);

        return fluentWait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.cssSelector(cssLocator));
            }
        });
    }

    private void clickToElement(String cssLocator) {
        fluentWait = new FluentWait<>(driver);
        fluentWait.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(100)).ignoring(NoSuchElementException.class);

       fluentWait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.cssSelector(cssLocator));
            }
        }).click();
    }

    private String getElementText(String cssLocator) {
        fluentWait = new FluentWait<>(driver);
        fluentWait.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(100)).ignoring(NoSuchElementException.class);

       return fluentWait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.cssSelector(cssLocator));
            }
        }).getText();
    }

    private Boolean isElementDisplayed(String cssLocator) {
        fluentWait = new FluentWait<>(driver);
        fluentWait.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(100)).ignoring(NoSuchElementException.class);

       return fluentWait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.cssSelector(cssLocator));
            }
        }).isDisplayed();
    }
}
