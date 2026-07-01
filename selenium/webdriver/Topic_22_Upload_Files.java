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
public class Topic_22_Upload_Files {

    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    boolean headless = false;

    String uploadFilePath = projectPath + "/uploadFiles/";

    String islandFile = "island.JPG";
    String seaFile = "sea.JPG";
    String skyFile = "sky.JPG";

    String islandFilePath = uploadFilePath + islandFile;
    String seaFilePath = uploadFilePath + seaFile;
    String skyFilePath = uploadFilePath + skyFile;

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
    public void TC_01_Upload_File_By_Sendkey() throws InterruptedException {
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");
        By uploadFileBy = By.cssSelector("input[type='file']");
        
        driver.findElement(uploadFileBy).sendKeys(islandFilePath + "\n" + seaFilePath + "\n" + skyFilePath);

        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + islandFile + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + seaFile + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + skyFile + "']")).isDisplayed());

        List<WebElement> startUploadButtons = driver.findElements(By.cssSelector("table button.start"));
        for (WebElement startButton : startUploadButtons) {
            startButton.click();
            Thread.sleep(2000);
        }

        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[text()='" + islandFile + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[text()='" + seaFile + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[text()='" + skyFile + "']")).isDisplayed());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

