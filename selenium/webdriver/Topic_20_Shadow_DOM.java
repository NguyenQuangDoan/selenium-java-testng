package webdriver;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

@Listeners(TestListener.class)
public class Topic_20_Shadow_DOM {

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
    public void TC_01_Home_Shopping() {
        driver.get("https://shop.polymer-project.org/");

        WebElement shopAppShadowHost = driver.findElement(By.cssSelector("shop-app"));
        SearchContext shopAppShadowRoot = shopAppShadowHost.getShadowRoot();
        
        WebElement shopHomeShadowHost = shopAppShadowRoot.findElement(By.cssSelector("iron-pages > shop-home"));
        SearchContext shopHomeShadowRoot = shopHomeShadowHost.getShadowRoot();

        shopHomeShadowRoot.findElement(By.cssSelector("div.item:nth-of-type(1) a")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://shop.polymer-project.org/list/mens_outerwear");
    }

    @Test
    public void TC_02_Nested() {
        driver.get("https://automationfc.github.io/shadow-dom");

        WebElement shadowHost = driver.findElement(By.cssSelector("#shadow_host"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        
        Assert.assertTrue(shadowRoot.findElement(By.cssSelector("span.info")).isDisplayed());
        Assert.assertFalse(shadowRoot.findElement(By.cssSelector("input[type='checkbox']")).isSelected());

        WebElement nestedShadowHost = shadowRoot.findElement(By.cssSelector("#nested_shadow_host"));
        SearchContext nestedShadowRoot = nestedShadowHost.getShadowRoot();
        
        Assert.assertTrue(nestedShadowRoot.findElement(By.cssSelector("#nested_shadow_content > div")).isDisplayed());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
