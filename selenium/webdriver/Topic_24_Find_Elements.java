package webdriver;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

import java.time.Duration;

@Listeners(TestListener.class)
public class Topic_24_Find_Elements {

    WebDriver driver;
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        if (!headless) {
            driver.manage().window().maximize();
        }
    }

    @Test
    public void TC_01_Find_Element() {
        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

        // Nếu tìm thấy duy nhất 1 element
        driver.findElement(By.cssSelector("input#FirstName"));

        // Nếu tìm thấy nhiều element
        driver.findElement(By.cssSelector("input[type='text']"));

        // Nếu không tìm thấy element
        driver.findElement(By.cssSelector("input[type='selenium']"));
    }

    @Test
    public void TC_02_Find_Elements() {
        List<WebElement> elements = null;
        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

        // Nếu tìm thấy duy nhất 1 element
        elements = driver.findElements(By.cssSelector("input#FirstName"));
        System.out.println("Số element tìm được = " + elements.size());

        // Nếu tìm thấy nhiều element
        elements = driver.findElements(By.cssSelector("input[type='text']"));
        System.out.println("Số element tìm được = " + elements.size());

        // Nếu không tìm thấy element
        elements = driver.findElements(By.cssSelector("input[type='selenium']"));
        System.out.println("Số element tìm được = " + elements.size());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
