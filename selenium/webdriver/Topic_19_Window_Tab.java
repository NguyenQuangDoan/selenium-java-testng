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
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.Select;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class)
public class Topic_19_Window_Tab {

    WebDriver driver;
    WebDriverWait wait;
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
        wait = new WebDriverWait(driver, 30);
        if (!headless) {
            driver.manage().window().maximize();
        }
    }

    @Test
    public void TC_01_Github() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        String githubWindowId = driver.getWindowHandle();
        sleepInSeconds(2);

        driver.findElement(By.xpath("//a[@href='https://google.com.vn']")).click();
        sleepInSeconds(2);

        switchToWindowByTitle("Google");

        switchToWindowByTitle("Selenium WebDriver");

        driver.findElement(By.xpath("//a[@href='https://facebook.com']")).click();
        sleepInSeconds(2);

        switchToWindowByTitle("Facebook");
        sleepInSeconds(2);
        
        switchToWindowByTitle("Selenium WebDriver");
        driver.findElement(By.xpath("//a[@href='https://tiki.vn']")).click();
        sleepInSeconds(2);

        switchToWindowByTitle("Tiki - Mua hàng online");
        sleepInSeconds(2);
        
        closeAllWindowsExceptOriginal(githubWindowId);
    }

    @Test
    public void TC_02_Techpanda() {
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.xpath("//a[text()='Mobile']")).click();
        sleepInSeconds(2);
        driver.findElement(By.xpath("//a[text()='IPhone']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
        driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
        sleepInSeconds(2);

        driver.findElement(By.xpath("//button[@title='Compare']")).click();
        sleepInSeconds(2);

        switchToWindowByTitle("Products Comparison List");
        driver.findElement(By.xpath("//button[@title='Close Window']")).click();
        sleepInSeconds(2);
        switchToWindowByTitle("Mobile");
        driver.findElement(By.xpath("//a[text()='Clear All']")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        sleepInSeconds(2);
        Assert.assertTrue(driver.findElement(By.xpath("//ul[@class='messages']//span[text()='The comparison list was cleared.']")).isDisplayed());
    }

    @Test
    public void TC_03_Cambridge() {
        driver.get("https://dictionary.cambridge.org/vi/");
        String originalWindow = driver.getWindowHandle();
        driver.findElement(By.xpath("//span[@class='tb' and text()='Đăng nhập']")).click();
        sleepInSeconds(2);

        switchToWindowByTitle("Login");
        
        // Manual: Click vào checkbox "Verify you are human" trong 30 giây
        System.out.println("Please click Cloudflare checkbox manually...");
        sleepInSeconds(30);
        
        // Đợi button Login xuất hiện và clickable
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Log in']")));
        driver.findElement(By.xpath("//input[@value='Log in']")).click();
        sleepInSeconds(2);
        Assert.assertEquals(driver.findElement(By.xpath("//input[@data-gigya-placeholder='Email']/following-sibling::span")).getText(), "This field is required");
        Assert.assertEquals(driver.findElement(By.xpath("//input[@data-gigya-placeholder='Password']/following-sibling::span")).getText(), "This field is required");
        closeAllWindowsExceptOriginal(originalWindow);
        driver.findElement(By.xpath("//input[@aria-label='Tìm kiếm']")).sendKeys("automation");
        driver.findElement(By.xpath("//button[@aria-label='Search']")).click();
        sleepInSeconds(2);
        Assert.assertTrue(driver.findElements(By.xpath("//div[@class='di-title']//span[text()='automation']")).get(0).isDisplayed());
    }

    @Test
    public void TC_04_Harvard() {
        driver.get("https://courses.dce.harvard.edu/");
        String originalWindow = driver.getWindowHandle();
        driver.findElement(By.cssSelector("a[data-action='login']")).click();
        switchToWindowById(originalWindow);
        closeAllWindowsExceptOriginal(originalWindow);
        Assert.assertTrue(driver.findElement(By.cssSelector("div.activescreen")).isDisplayed());
        driver.findElement(By.cssSelector("button[class*='button--cancel']")).click();
        driver.findElement(By.xpath("//input[@id='crit-keyword']")).sendKeys("data science: an artificial ecosystem");
        Select select = new Select(driver.findElement(By.id("crit-summer_school")));
        select.selectByVisibleText("Adult, Extension, & Visiting College");
        Select select2 = new Select(driver.findElement(By.id("crit-session")));
        select2.selectByVisibleText("3-week session II");
        driver.findElement(By.cssSelector("button#search-button")).click();
    }


    private void sleepInSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void switchToWindowById(String windowId) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String id : allWindows) {
            if (id.equals(windowId)) {
                driver.switchTo().window(id);
                break;
            }
        }
    }

    private void switchToWindowByTitle(String expectedTitle) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String id : allWindows) {
            driver.switchTo().window(id);
            sleepInSeconds(1);
            if (driver.getTitle().contains(expectedTitle)) {
                break;
            }
        }
    }

    private void closeAllWindowsExceptOriginal(String originalWindow) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window);
                driver.close();
            }
        }
        driver.switchTo().window(originalWindow);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
