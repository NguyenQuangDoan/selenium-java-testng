package webdriver;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
public class Topic_18_Popup_Shadow_DOM {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor jsExecutor;
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        jsExecutor = (JavascriptExecutor) driver;
    }

    //@Test
    public void TC_01_Fixed_Popup() throws InterruptedException {
        driver.get("https://ngoaingu24h.vn/");  
        driver.findElement(By.xpath("//button[text()='Đăng nhập']")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#custom-dialog .auth-form")));
        Assert.assertTrue(driver.findElement(By.cssSelector("#custom-dialog .auth-form")).isDisplayed());
        driver.findElement(By.cssSelector("input[placeholder='Tài khoản đăng nhập']")).sendKeys("automationfc");
        driver.findElement(By.cssSelector("input[placeholder='Mật khẩu']")).sendKeys("automationfc");
        
        // Tìm button trong form popup
        WebElement submitButton = driver.findElement(By.cssSelector(".auth-form button[type='submit']"));
        jsExecutor.executeScript("arguments[0].click();", submitButton);
        Thread.sleep(2000);

        // Đợi và verify error message (có thể có class hoặc div khác)
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(text(),'sai tài khoản') or contains(text(),'sai mật khẩu')]")));
        Assert.assertTrue(errorMessage.getText().contains("sai tài khoản") || errorMessage.getText().contains("sai mật khẩu"));

        WebElement closeButton = driver.findElement(By.xpath("//h2[text()='Đăng nhập']/button"));
        jsExecutor.executeScript("arguments[0].click();", closeButton);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#custom-dialog .auth-form")));
    }

    //@Test
    public void TC_02_Fixed_Popup() throws InterruptedException {
        driver.get("https://skills.kynaenglish.vn/dang-nhap");  
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#k-popup-account-login .k-popup-account-mb-content")));
        driver.findElement(By.cssSelector("input[id='user-login']")).sendKeys("automationfc@gmail.com");
        driver.findElement(By.cssSelector("input[id='user-password']")).sendKeys("automationfc");
        driver.findElement(By.cssSelector("#login-form button[type='submit']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("#login-form #password-form-login-message")).getText(), "Sai tên đăng nhập hoặc mật khẩu");
    }

    //@Test
    public void TC_03_Fixed_Popup_Not_In_DOM() throws InterruptedException {
        driver.get("https://tiki.vn/");  
        if (driver.findElements(By.cssSelector("#VIP_BUNDLE")).size() > 0) {
            driver.findElements(By.cssSelector("#VIP_BUNDLE .webpimg-container")).get(0).click();
        } else {
            System.out.println("Popup is not present");
        }

        driver.findElement(By.cssSelector("div[data-view-id='header_header_account_container']")).click();
        
        // Đợi popup xuất hiện
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ReactModal__Content")));
        Assert.assertTrue(driver.findElement(By.cssSelector(".ReactModal__Content")).isDisplayed());
        
        driver.findElement(By.cssSelector(".login-with-email")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[contains(@class,'ReactModal__Content ')]//button[text()='Đăng nhập']")).click();
        Assert.assertEquals(driver.findElements(By.xpath("//form//span[@class='error-mess']")).get(0).getText(), "Email không được để trống");
        Assert.assertEquals(driver.findElements(By.xpath("//form//span[@class='error-mess']")).get(1).getText(), "Mật khẩu không được để trống");
        
        driver.findElement(By.cssSelector(".ReactModal__Content button[class='btn-close']")).click();
        
        // Verify popup biến mất khỏi DOM (không còn tồn tại)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ReactModal__Content")));
        Assert.assertEquals(driver.findElements(By.cssSelector(".ReactModal__Content")).size(), 0);
    }

    //@Test
    public void TC_04_Random_Popup() throws InterruptedException {
        driver.get("https://vnk.edu.vn/");
        Thread.sleep(3000);
        
        try {
            if(driver.findElement(By.cssSelector(".pum-container")).isDisplayed()) {
                driver.findElement(By.cssSelector(".pum-container button.pum-close")).click();
                Thread.sleep(500);
                Assert.assertFalse(driver.findElement(By.cssSelector(".pum-container")).isDisplayed());
            }
        } catch (Exception e) {
            System.out.println("Popup is not displayed");
        }

        driver.findElement(By.xpath("//a[text()='Liên hệ']")).click();
    }

    // @Test
    // public void TC_05_Random_Popup_Not_In_DOM() throws InterruptedException {
    //     driver.get("https://www.javacodegeeks.com/");
    //     Thread.sleep(5000);
        
    //     try {
    //         if(driver.findElement(By.cssSelector("div[class=lepopup-popup-container] div[data-page=1]")).isDisplayed()) {
    //             driver.findElement(By.xpath("//a[text()='×']")).click();
    //             Thread.sleep(500);
    //             Assert.assertEquals(driver.findElements(By.cssSelector("div[class=lepopup-popup-container] div[data-page=1]")).size(), 0);
    //         }
    //     } catch (Exception e) {
    //         System.out.println("Popup is not displayed");
    //     }

    //     driver.findElement(By.cssSelector("#search-input")).sendKeys("Agile Testing Explained");
    //     driver.findElement(By.cssSelector("#search-submit")).click();
    //     Thread.sleep(3000);
    //     Assert.assertEquals(driver.findElement(By.xpath("//h2/a")).getText(), "Agile Testing Explained");
    // }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
