package webdriver;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;


@Listeners(TestListener.class)
public class Topic_17_iframe {

    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    boolean headless = false;
    JavascriptExecutor jsExecutor;

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
        jsExecutor = (JavascriptExecutor) driver;
    }

    @Test
    public void TC_01_WordPress() {
        driver.get("https://toidicodedao.com/");
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@title,'Facebook Social Plugin')]")));
        Assert.assertEquals(driver.findElement(By.xpath("//a[@title='Tôi đi code dạo']/parent::div/following-sibling::div[contains(text(),'followers')]")).getText(), "390,908 followers");

        //Quay lại trang chứa iframe
        driver.switchTo().defaultContent();
    }

    @Test
    public void TC_02_Formsite() {
        driver.get("https://www.formsite.com/templates/education/campus-safety-survey/");
        acceptCookie();
        driver.findElement(By.xpath("//img[@title='Campus-Safety-Survey-Forms-and-Examples']")).click();
        Assert.assertEquals(driver.findElement(By.id("tooltip")).getText(), "Interactive form loaded. Try filling out below.");

        // Switch vào iframe chứa form
        driver.switchTo().frame(driver.findElement(By.xpath("//div[@id='formTemplateContainer']/iframe")));
        
        new Select(driver.findElement(By.id("RESULT_RadioButton-2"))).selectByVisibleText("Freshman");
        new Select(driver.findElement(By.id("RESULT_RadioButton-3"))).selectByVisibleText("East Dorm");
        driver.findElement(By.xpath("//label[text()='Male']")).click();
        
        // Quay lại trang chính
        driver.switchTo().defaultContent();

        driver.findElement(By.xpath("//a[@title='Get this form']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector(".auth-title")).getText(), "Get This Form");
    }

    private void acceptCookie() {
        try {
            driver.findElement(By.xpath("//div[@role='dialog']//button[text()='Accept All']")).click();
        } catch (Exception e) {
            // Không có cookie popup
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
