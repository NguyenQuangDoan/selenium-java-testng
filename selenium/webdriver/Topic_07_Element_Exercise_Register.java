package webdriver;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class)
public class Topic_07_Element_Exercise_Register {

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
    public void TC_01_Empty() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");
        driver.findElement(By.cssSelector("#frmLogin button.btn_pink_sm")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("#txtFirstname-error")).getText(), "Vui lòng nhập họ tên");
        Assert.assertEquals(driver.findElement(By.cssSelector("#txtEmail-error")).getText(), "Vui lòng nhập email");
        Assert.assertEquals(driver.findElement(By.cssSelector("#txtCEmail-error")).getText(), "Vui lòng nhập lại địa chỉ email");
        Assert.assertEquals(driver.findElement(By.cssSelector("#txtPassword-error")).getText(), "Vui lòng nhập mật khẩu");
        Assert.assertEquals(driver.findElement(By.cssSelector("#txtCPassword-error")).getText(), "Vui lòng nhập lại mật khẩu");
        Assert.assertEquals(driver.findElement(By.cssSelector("#txtPhone-error")).getText(), "Vui lòng nhập số điện thoại.");
    }

    @Test
    public void TC_02_Invalid_Email() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");
        driver.findElement(By.cssSelector("#txtFirstname")).sendKeys("John Doe");
        driver.findElement(By.cssSelector("#txtEmail")).sendKeys("123234@123@123");
        driver.findElement(By.cssSelector("#txtCEmail")).sendKeys("123234@123@123");
        driver.findElement(By.cssSelector("#txtPassword")).sendKeys("1234567");
        driver.findElement(By.cssSelector("#txtCPassword")).sendKeys("1234567");
        driver.findElement(By.cssSelector("#txtPhone")).sendKeys("0399125709");
        driver.findElement(By.cssSelector("#frmLogin button.btn_pink_sm")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("#txtEmail-error")).getText(), "Vui lòng nhập email hợp lệ");
        Assert.assertEquals(driver.findElement(By.cssSelector("#txtCEmail-error")).getText(), "Email nhập lại không đúng");
    }

    @Test
    public void TC_03_Incorrect_Confirm_Email() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");
        driver.findElement(By.cssSelector("#txtFirstname")).sendKeys("John Doe");
        driver.findElement(By.cssSelector("#txtEmail")).sendKeys("123234@123.com");
        driver.findElement(By.cssSelector("#txtCEmail")).sendKeys("123234@123@1234");
        driver.findElement(By.cssSelector("#txtPassword")).sendKeys("1234567");
        driver.findElement(By.cssSelector("#txtCPassword")).sendKeys("1234567");
        driver.findElement(By.cssSelector("#txtPhone")).sendKeys("0399125709");
        driver.findElement(By.cssSelector("#frmLogin button.btn_pink_sm")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("#txtCEmail-error")).getText(), "Email nhập lại không đúng");
    }

    @Test
    public void TC_04_Password_Less_Than_6_Chars() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");
        driver.findElement(By.cssSelector("#txtFirstname")).sendKeys("John Doe");
        driver.findElement(By.cssSelector("#txtEmail")).sendKeys("123234@123.com");
        driver.findElement(By.cssSelector("#txtCEmail")).sendKeys("123234@123.com");
        driver.findElement(By.cssSelector("#txtPassword")).sendKeys("123");
        driver.findElement(By.cssSelector("#txtCPassword")).sendKeys("123");
        driver.findElement(By.cssSelector("#txtPhone")).sendKeys("0399125709");
        driver.findElement(By.cssSelector("#frmLogin button.btn_pink_sm")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("#txtPassword-error")).getText(), "Mật khẩu phải có ít nhất 6 ký tự");
        Assert.assertEquals(driver.findElement(By.cssSelector("#txtCPassword-error")).getText(), "Mật khẩu phải có ít nhất 6 ký tự");
    }

    @Test
    public void TC_05_Incorrect_Confirm_Password() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");
        driver.findElement(By.cssSelector("#txtFirstname")).sendKeys("John Doe");
        driver.findElement(By.cssSelector("#txtEmail")).sendKeys("123234@123.com");
        driver.findElement(By.cssSelector("#txtCEmail")).sendKeys("123234@123.com");
        driver.findElement(By.cssSelector("#txtPassword")).sendKeys("1234567");
        driver.findElement(By.cssSelector("#txtCPassword")).sendKeys("12345678");
        driver.findElement(By.cssSelector("#txtPhone")).sendKeys("0399125709");
        driver.findElement(By.cssSelector("#frmLogin button.btn_pink_sm")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("#txtCPassword-error")).getText(), "Mật khẩu bạn nhập không khớp");
    }

    @Test
    public void TC_06_Invalid_Phone() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");
        driver.findElement(By.cssSelector("#txtFirstname")).sendKeys("John Doe");
        driver.findElement(By.cssSelector("#txtEmail")).sendKeys("123234@123.com");
        driver.findElement(By.cssSelector("#txtCEmail")).sendKeys("123234@123.com");
        driver.findElement(By.cssSelector("#txtPassword")).sendKeys("1234567");
        driver.findElement(By.cssSelector("#txtCPassword")).sendKeys("1234567");
        driver.findElement(By.cssSelector("#txtPhone")).sendKeys("09888");
        driver.findElement(By.cssSelector("#frmLogin button.btn_pink_sm")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("#txtPhone-error")).getText(), "Số điện thoại phải từ 10-11 số.");
        driver.findElement(By.cssSelector("#txtPhone")).clear();
        driver.findElement(By.cssSelector("#txtPhone")).sendKeys("1988888888");
        driver.findElement(By.cssSelector("#frmLogin button.btn_pink_sm")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("#txtPhone-error")).getText(), "Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019 - 088 - 03 - 05 - 07 - 08");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
