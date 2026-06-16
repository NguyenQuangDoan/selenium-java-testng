package webdriver;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class)
public class Topic_12_Button {

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
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        if (!headless) {
            driver.manage().window().maximize();
        }
    }

    @Test
    public void TC_01_Fahasa() {
        String email = "test@example.com";
        String password = "Password123";
        driver.get("https://www.fahasa.com/customer/account/create");
        driver.findElement(By.xpath("//a[text()='Đăng nhập']")).click();
        Assert.assertFalse(driver.findElement(By.xpath("//button[@class='fhs-btn-login']")).isEnabled());
        String disabledBg = driver.findElement(By.xpath("//button[@class='fhs-btn-login']")).getCssValue("background-image");
        String disabledColor = disabledBg.substring(disabledBg.indexOf("rgb"), disabledBg.indexOf(")") + 1);
        Assert.assertEquals(Color.fromString(disabledColor).asHex(), "#e0e0e0");
        
        driver.findElement(By.xpath("//input[@id='login_username']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@id='login_password']")).sendKeys(password);
        Assert.assertTrue(driver.findElement(By.xpath("//button[@class='fhs-btn-login']")).isEnabled());
        String enabledBg = driver.findElement(By.xpath("//button[@class='fhs-btn-login']")).getCssValue("background-color");
        String enabledColor = enabledBg.substring(enabledBg.indexOf("rgb"), enabledBg.indexOf(")") + 1);
        Assert.assertEquals(Color.fromString(enabledColor).asHex(), "#c92127");
    }



    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
