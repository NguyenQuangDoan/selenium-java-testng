package webdriver;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

@Listeners(TestListener.class)
public class Topic_27_Explicit_Wait {

    WebDriver driver;
    WebDriverWait explicitWait;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    boolean headless = false;

    String uploadFilePath = projectPath + "/uploadFiles/";

    String islandFile = "island.JPG";
    String seaFile = "sea.JPG";
    String skyFile = "sky.JPG";

    String islandFilePath = uploadFilePath + islandFile;
    String seaFilePath = uploadFilePath + seaFile;

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
        
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // @Test
    public void TC_01_Explicit_Wait(){
        driver.get("https://automationfc.github.io/dynamic-loading/");
        driver.findElement(By.cssSelector("div#start button")).click();
        driver.findElement(By.cssSelector("div#loading")).isDisplayed();
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#finish h4")));
        Assert.assertEquals(driver.findElement(By.cssSelector("div#finish h4")).getText(), "Hello World!");
    }

    //@Test
    public void TC_02_Explicit_Wait(){
        driver.get("https://automationfc.github.io/dynamic-loading/");
        driver.findElement(By.cssSelector("div#start button")).click();
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loading")));
        Assert.assertEquals(driver.findElement(By.cssSelector("div#finish h4")).getText(), "Hello World!");
    }

    //@Test
    public void TC_03_Explicit_Wait(){
        driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".calendarContainer")));
        explicitWait.until(ExpectedConditions.textToBe(By.cssSelector(".label"), "No Selected Dates to display."));

        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table//a[text()='18']"))).click();
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='raDiv']")));
        
        explicitWait.until(ExpectedConditions.textToBe(By.cssSelector(".label"), "Saturday, July 18, 2026"));
    }

    @Test
    public void TC_04_Explicit_Wait(){
        driver.get("https://gofile.io");
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#index_main .animate-spin")));
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(normalize-space(), 'File Manager')]"))).click();
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#index_main .animate-spin")));
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#filemanager_loading .animate-spin")));
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#filemanager_mainbuttons_createFolder"))).click();
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#popup_folderName"))).sendKeys("Selenium");
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form//button[contains(normalize-space(), 'Create Folder')]"))).click();
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Success']")));
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Close Popup']"))).click();
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Selenium']"))).click();
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#index_main .animate-spin")));
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#filemanager_loading .animate-spin")));
        By uploadFileBy = By.cssSelector("input[type='file']");
        explicitWait.until(ExpectedConditions.presenceOfElementLocated(uploadFileBy)).sendKeys(islandFilePath + "\n" + seaFilePath);
        Assert.assertTrue(explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.progress-container"))));
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#index_main .animate-spin")));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class,'tem_open') and text()='"+ islandFile +"']")));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class,'tem_open') and text()='"+ seaFile +"']")));
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#filemanager_mainbuttons_checkboxAll input"))).click();
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#filemanager_mainbuttons_delete"))).click();
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#popup_confirmdelete"))).click();
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Success']")));
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
