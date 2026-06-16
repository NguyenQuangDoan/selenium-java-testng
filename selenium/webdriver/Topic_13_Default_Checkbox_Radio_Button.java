package webdriver;

import listeners.TestListener;
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
import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class)
public class Topic_13_Default_Checkbox_Radio_Button {

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
    public void TC_01_Telerik_Checkbox() throws InterruptedException {
        By dualZoneCheckbox = By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::span/input");
        By petrolRadio = By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::span/input");

        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
        isLoadingDone();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id("demo-runner")));
        clickAndVerifySelected(dualZoneCheckbox);
        clickAndVerifyNotSelected(dualZoneCheckbox);

        driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
        isLoadingDone();
        clickAndVerifySelected(petrolRadio);
    }

    @Test
    public void TC_02_Material_Radio_Button() {
        By summerRadio = By.xpath("//input[@value='Summer']");
        By checkedCheckbox = By.xpath("//label[contains(text(),'Checked')]/preceding-sibling::div//input");
        By indeterminateCheckbox = By.xpath("//label[contains(text(),'Indeterminate')]/preceding-sibling::div//input");

        driver.get("https://material.angular.io/components/radio/examples");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id("radio-harness")));
        clickElement(summerRadio);
        checkAndClickIfNotSelected(summerRadio);
        verifyElementSelected(summerRadio);
        
        driver.get("https://material.angular.io/components/checkbox/examples");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id("checkbox-configurable")));
        clickAndVerifySelected(checkedCheckbox);
        clickAndVerifySelected(indeterminateCheckbox);
        clickAndVerifyNotSelected(checkedCheckbox);
        clickAndVerifyNotSelected(indeterminateCheckbox);
    }

    @Test
    public void TC_03_Select_All() {
        driver.get("https://automationfc.github.io/multiple-fields/");
        List<WebElement> everhadCheckboxes = driver.findElements(By.xpath("//label[contains(text(),'Have you ever had')]/following-sibling::div//input"));
        
        for (WebElement checkbox : everhadCheckboxes) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }

        for (WebElement checkbox : everhadCheckboxes) {
            Assert.assertTrue(checkbox.isSelected());
        }

        for (WebElement checkbox : everhadCheckboxes) {
            if (checkbox.isSelected()) {
                checkbox.click();
            }
        }

        for (WebElement checkbox : everhadCheckboxes) {
            Assert.assertFalse(checkbox.isSelected());
        }

        for (WebElement checkbox : everhadCheckboxes) {
            if (!checkbox.isSelected() && checkbox.getAttribute("value").equals("Heart Attack")) {
                checkbox.click();
                break;
            }
        }
    }

    private void clickElement(By locator) {
        driver.findElement(locator).click();
    }

    private void checkAndClickIfNotSelected(By locator) {
        if (!driver.findElement(locator).isSelected()) {
            driver.findElement(locator).click();
        }
    }

    private void verifyElementSelected(By locator) {
        Assert.assertTrue(driver.findElement(locator).isSelected());
    }

    private void verifyElementNotSelected(By locator) {
        Assert.assertFalse(driver.findElement(locator).isSelected());
    }

    private void clickAndVerifySelected(By locator) {
        clickElement(locator);
        verifyElementSelected(locator);
    }

    private void clickAndVerifyNotSelected(By locator) {
        clickElement(locator);
        verifyElementNotSelected(locator);
    }

    private void isLoadingDone() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".kd-loader-wrap")));
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
