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

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class)
public class Topic_08_Textbox_TextArea {

    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    boolean headless = false;

    // Helper method to handle insecure form warning alerts
    private void handleAlertIfPresent() {
        try {
            Thread.sleep(500);
            driver.switchTo().alert().accept();
            Thread.sleep(1000);
        } catch (Exception e) {
            // No alert present, continue
        }
    }
    
    // Helper method to click and auto-handle alert
    private void clickAndHandleAlert(By locator) throws InterruptedException {
        driver.findElement(locator).click();
        handleAlertIfPresent();
    }

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
        // Disable insecure form warning
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
    public void TC_01_TechPanda() throws InterruptedException {
        String txtFirstname = "John";
        String txtLastname = "Doe";
        String txtEmail = "john.doe" + new Random().nextInt(9999) + "@test.com";
        String txtPassword = "Password123";
        String txtConfirmPassword = "Password123";
        String expectedSuccessMessage = "Thank you for registering with Main Website Store.";
        String reviewText = "This is a test review.\n Another line.";
        String reviewSummary = "Test Summary";
        
        driver.get("https://live.techpanda.org/");
        driver.findElement(By.cssSelector(".footer a[title='My Account']")).click();
        driver.findElement(By.cssSelector("#login-form a[title='Create an Account']")).click();
        driver.findElement(By.cssSelector("#firstname")).sendKeys(txtFirstname);
        driver.findElement(By.cssSelector("#lastname")).sendKeys(txtLastname);
        driver.findElement(By.cssSelector("#email_address")).sendKeys(txtEmail);
        driver.findElement(By.cssSelector("#password")).sendKeys(txtPassword);
        driver.findElement(By.cssSelector("#confirmation")).sendKeys(txtConfirmPassword);
        clickAndHandleAlert(By.cssSelector("#form-validate button[title='Register']"));
        
        Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg")).getText(), expectedSuccessMessage);
        Assert.assertTrue(driver.findElement(By.cssSelector(".box-info")).getText().contains(txtFirstname + ' ' + txtLastname));
        Assert.assertTrue(driver.findElement(By.cssSelector(".box-info")).getText().contains(txtEmail));
        driver.findElement(By.xpath("//nav[@id='nav']//a[text()='Mobile']")).click();
        driver.findElement(By.cssSelector("li.item a[title='Samsung Galaxy']")).click();
        driver.findElement(By.xpath("//a[text()='Add Your Review']")).click();
        driver.findElement(By.cssSelector("input[value='5']")).click();
        driver.findElement(By.cssSelector("#review_field")).sendKeys(reviewText);
        driver.findElement(By.cssSelector("#summary_field")).sendKeys(reviewSummary);
        driver.findElement(By.cssSelector("#nickname_field")).sendKeys(txtFirstname + ' ' + txtLastname);
        clickAndHandleAlert(By.cssSelector("button[title='Submit Review']"));
        
        Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg")).getText(), "Your review has been accepted for moderation.");
    }

    @Test
    public void TC_02_OrangeHRM() throws InterruptedException {
        String txtFirstname = "John";
        String txtLastname = "Doe";
        String employeeId = String.valueOf(new Random().nextInt(99999));
        String userName = "doan" + new Random().nextInt(999);
        String password = "1234567a";
        String confirmPassword = "1234567a";
        String passportNumber = "P" + new Random().nextInt(999999);

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Admin");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//span[text()='PIM']/parent::a")).click();
        driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
        driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys(txtFirstname);
        driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys(txtLastname);
        driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).clear();
        driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).sendKeys(employeeId);
        
        // Get the actual Employee ID value after entering (system might auto-format it)
        String actualEmployeeId = driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value");
        
        // Wait for loader to disappear
        Thread.sleep(2000);
        
        // Click on the switch span instead of checkbox input
        driver.findElement(By.xpath("//p[text()='Create Login Details']/parent::div//span[@class='oxd-switch-input oxd-switch-input--active --label-right']")).click();
        driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).sendKeys(userName);
        driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).sendKeys(password);
        driver.findElement(By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input")).sendKeys(confirmPassword);
        driver.findElement(By.xpath("//button[text()=' Save ']")).click();
        
        // Wait for page to load after save
        Thread.sleep(3000);
        
        // Verify saved data
        Assert.assertEquals(driver.findElement(By.xpath("//input[@name='firstName']")).getAttribute("value"), txtFirstname);
        Assert.assertEquals(driver.findElement(By.xpath("//input[@name='lastName']")).getAttribute("value"), txtLastname);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value"), actualEmployeeId);

        driver.findElement(By.xpath("//a[text()='Immigration']")).click();
        Thread.sleep(2000);
        
        driver.findElement(By.xpath("//h6[text()='Assigned Immigration Records']/following-sibling::button")).click();
        Thread.sleep(2000);
        
        driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div//input")).sendKeys(passportNumber);
        driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div//textarea")).sendKeys("Passport number: " + passportNumber);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(3000);
        
        driver.findElement(By.xpath("//i[@class='oxd-icon bi-pencil-fill']/parent::button")).click();
        Thread.sleep(2000);
        
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div//input")).getAttribute("value"), passportNumber);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div//textarea")).getAttribute("value"), "Passport number: " + passportNumber);

        driver.findElement(By.xpath("//span[@class='oxd-userdropdown-tab']")).click();
        driver.findElement(By.xpath("//a[text()='Logout']")).click();
        Thread.sleep(2000);
        
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys(userName);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(3000);
        
        driver.findElement(By.xpath("//span[text()='My Info']/parent::a")).click();
        Thread.sleep(2000);
        
        Assert.assertEquals(driver.findElement(By.xpath("//input[@name='firstName']")).getAttribute("value"), txtFirstname);
        Assert.assertEquals(driver.findElement(By.xpath("//input[@name='lastName']")).getAttribute("value"), txtLastname);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value"), actualEmployeeId);
        
        driver.findElement(By.xpath("//a[text()='Immigration']")).click();
        Thread.sleep(2000);
        
        driver.findElement(By.xpath("//i[@class='oxd-icon bi-pencil-fill']/parent::button")).click();
        Thread.sleep(2000);
        
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div//input")).getAttribute("value"), passportNumber);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div//textarea")).getAttribute("value"), "Passport number: " + passportNumber);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
