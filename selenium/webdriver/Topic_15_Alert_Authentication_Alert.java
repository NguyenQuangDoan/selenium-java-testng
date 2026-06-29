package webdriver;

import listeners.TestListener;
import org.openqa.selenium.Alert;
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
public class Topic_15_Alert_Authentication_Alert {

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
    public void TC_01_Accept_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.alertIsPresent());
        String textOnAlert = alert.getText();
        Assert.assertEquals(textOnAlert, "I am a JS Alert");
        alert.accept();
        Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You clicked an alert successfully");
    }

    @Test
    public void TC_02_Confirm_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.alertIsPresent());
        String textOnAlert = alert.getText();
        Assert.assertEquals(textOnAlert, "I am a JS Confirm");
        alert.dismiss();
        Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You clicked: Cancel");
    }
    
    @Test
    public void TC_03_Prompt_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.alertIsPresent());
        String textOnAlert = alert.getText();
        Assert.assertEquals(textOnAlert, "I am a JS prompt");
        alert.sendKeys("Hello");
        alert.accept();
        Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You entered: Hello");
    }

    @Test
    public void TC_04_Authentication_Alert_Linnk_By_Pass() throws InterruptedException {
        //Cách 1 truyền trực tiếp username:password vào URL
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.example p")).getText(), "Congratulations! You must have the proper credentials.");
    }

    @Test
    public void TC_05_Authentication_Alert_Handle_By_Code() {
        //Cách 2: Truy cập URL có chứa authentication - Get attribute href và thêm username:password vào sau khi tách chuỗi
        String username = "admin";
        String password = "admin";
        driver.get("http://the-internet.herokuapp.com/");
        String url = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
        String[] urlParts = url.split("//");
        String authUrl = urlParts[0] + "//" + username + ":" + password + "@" + urlParts[1];
        driver.get(authUrl);
        Assert.assertEquals(driver.findElement(By.cssSelector("div.example p")).getText(), "Congratulations! You must have the proper credentials.");
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
