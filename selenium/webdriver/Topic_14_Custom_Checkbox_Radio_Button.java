package webdriver;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;
import java.time.Duration;

@Listeners(TestListener.class)
public class Topic_14_Custom_Checkbox_Radio_Button {

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

    //@Test
    public void TC_01_Ubuntu() {
        driver.get("https://login.ubuntu.com/");
        By newUserRadio = By.id("id_new_user");
        By acceptTosCheckbox = By.id("id_accept_tos");

        //Click bằng WebElement
        // driver.findElement(By.xpath("//label[@for='id_new_user']")).click();
        // Assert.assertTrue(driver.findElement(newUserRadio).isSelected());
        // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(newUserRadio));
        // driver.findElement(By.xpath("//label[@for='id_accept_tos']")).click();
        // Assert.assertTrue(driver.findElement(acceptTosCheckbox).isSelected());

        //Click bằng JavaExecutor
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(newUserRadio));
        Assert.assertTrue(driver.findElement(newUserRadio).isSelected());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(newUserRadio));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(acceptTosCheckbox));
        Assert.assertTrue(driver.findElement(acceptTosCheckbox).isSelected());
    }

    @Test
    public void TC_02_Custom_Radio_Checkbox() {
        By capitalRadioButton = By.xpath("//div[@aria-label='Cần Thơ']");
        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
        Assert.assertEquals(driver.findElement(capitalRadioButton).getAttribute("aria-checked"), "false");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(capitalRadioButton));
        Assert.assertEquals(driver.findElement(capitalRadioButton).getAttribute("aria-checked"), "true");
    }

    private void isLoadingDone() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".kd-loader-wrap")));
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
