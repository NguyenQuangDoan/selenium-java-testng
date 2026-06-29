package webdriver;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;
import java.time.Duration;

@Listeners(TestListener.class)
public class Topic_09_Default_Dropdown {

    WebDriver driver;
    Select select;
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
    public void TC_01() {
        driver.get("https://egov.danang.gov.vn/reg");
        select = new Select(driver.findElement(By.id("gioiTinh")));
        select.selectByVisibleText("Nam");
        Assert.assertEquals(select.getFirstSelectedOption().getText(), "Nam");
        Assert.assertEquals(select.getOptions().size(), 4);
        Assert.assertFalse(select.isMultiple());
    }

    @Test
    public void TC_02() throws InterruptedException {
        driver.get("https://rode.com/en-int/support/where-to-buy");
        select = new Select(driver.findElement(By.id("country")));
        select.selectByVisibleText("Vietnam");
        Thread.sleep(1000);
        Assert.assertEquals(select.getFirstSelectedOption().getText(), "Vietnam");
        driver.findElement(By.id("map_search_query")).sendKeys("Ho Chi Minh");
        driver.findElement(By.xpath("//button[text()='Search']")).click();
        Thread.sleep(3000);
        Assert.assertEquals(driver.findElements(By.xpath("//h3[text()='Dealers']/following-sibling::div/div")).size(), 16);

        List<WebElement> dealers = driver.findElements(By.xpath("//h3[text()='Dealers']/following-sibling::div/div//h4"));
        for (WebElement temp : dealers) {
            System.out.println(temp.getText());
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
