package webdriver;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class)
public class Topic_01_Check_Environment {

    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    boolean headless = false;

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Mac")) {
            System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        }

        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        if (!headless) {
            driver.manage().window().maximize();
        }
    }

    @Test
    public void TC_01_ValidateUrl() {
    }

    @Test
    public void TC_02_ValidatePageTitle() {
    }

    @Test
    public void TC_03_NavigateFunction() {
    }

    @Test
    public void TC_04_GetPageSourceCode() {
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
