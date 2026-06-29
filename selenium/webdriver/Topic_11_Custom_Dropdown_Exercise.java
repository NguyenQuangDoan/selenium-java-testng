package webdriver;

import listeners.TestListener;
import org.openqa.selenium.By;
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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;
import java.time.Duration;

@Listeners(TestListener.class)
public class Topic_11_Custom_Dropdown_Exercise {

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
    public void TC_01_JQuery() throws InterruptedException {
        driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
        selectItemInSelectableDropdown("//span[@id='speed-button']","//ul[@id='speed-menu']/li/div","Medium");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='speed-button']//span[@class='ui-selectmenu-text']")).getText(), "Medium");
        selectItemInSelectableDropdown("//span[@id='files-button']", "//ul[@id='files-menu']/li/div", "jQuery.js");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='files-button']//span[@class='ui-selectmenu-text']")).getText(), "jQuery.js");
        selectItemInSelectableDropdown("//span[@id='number-button']", "//ul[@id='number-menu']/li/div", "2");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text']")).getText(), "2");
        selectItemInSelectableDropdown("//span[@id='salutation-button']", "//ul[@id='salutation-menu']/li/div", "Mr.");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='salutation-button']//span[@class='ui-selectmenu-text']")).getText(), "Mr.");
    }

    @Test
    public void TC_02_ReactJS() throws InterruptedException {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
        selectItemInSelectableDropdown("//div[contains(@class,'dropdown')]", "//div[contains(@class,'menu')]//div[@role='option']", "Jenny Hess");
        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(@class,'dropdown')]/div[contains(@class, 'text')]")).getText(), "Jenny Hess");
    }
    
    @Test
    public void TC_03_VueJS() throws InterruptedException {
        driver.get("https://mikerodham.github.io/vue-dropdowns/");
        selectItemInSelectableDropdown("//div[@class='btn-group']", "//ul[@class='dropdown-menu']//a", "First Option");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='btn-group']/li[@class='dropdown-toggle']")).getText(), "First Option");
    }

    @Test
    public void TC_04_Editable_React() throws InterruptedException {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
        selectItemInEditableDropdown("//input[@class='search']", "//div[contains(@class,'menu')]//div[@role='option']", "Afghanistan");
        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(@class,'text')]")).getText(), "Afghanistan");
    }

    @Test
    public void TC_05_Huawei() throws InterruptedException {
        driver.get("https://id5.cloud.huawei.com/CAS/portal/userRegister/regbyemail.html");
        selectItemInHuaweiCountryDropdown("//div[contains(@class, 'hwid-ctryDropdown')]", "//div[contains(@class, 'hwid-dropList-show')]//input", "//div[contains(@class, 'hwid-dropList-show')]//ul//li", "Chủ doanh nghiệp");
        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(@class,'hwid-ctryDropdown')]//span[@class='hwid-select-text']")).getText(), "Vietnam");
        selectItemInHuaweiCountryDropdown("//div[contains(@class,'countryCodeDropDown')]", "//div[contains(@class,'countryCodeDropDown')]//input", "//div[contains(@class,'countryCodeDropDown')]//ul//li", "+84(Vietnam)");
        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(@class,'countryCodeDropDown')]//span[@class='hwid-select-text']")).getText(), "+84(Vietnam)");
    }

    @Test
    public void TC_06_FinPeace() throws InterruptedException {
        driver.get("https://sps.finpeace.vn/tools/sktccn");
        selectItemInEditableDropdown("//input[@id='job_id']", "//div[@class='ant-select-item-option-content']/parent::div", "Chủ doanh nghiệp");
        Assert.assertEquals(driver.findElement(By.xpath("//input[@id='job_id']/parent::span/following-sibling::span")).getText(), "Chủ doanh nghiệp");
        selectItemInEditableDropdown("//input[@id='gender']", "//div[@class='ant-select-item-option-content']/parent::div", "Nam");
        Assert.assertEquals(driver.findElement(By.xpath("//input[@id='gender']/parent::span/following-sibling::span")).getText(), "Nam");
        selectItemInEditableDropdown("//input[@id='married_status']", "//div[@class='ant-select-item-option-content']/parent::div", "Kết hôn, đã có con");
        Assert.assertEquals(driver.findElement(By.xpath("//input[@id='married_status']/parent::span/following-sibling::span")).getText(), "Kết hôn, đã có con");
    }

    @Test
    public void TC_07_JQuery_Honda() throws InterruptedException {
        driver.get("https://www.honda.com.vn/o-to/du-toan-chi-phi");
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement parentElement = driver.findElement(By.xpath("//button[@id='selectize-input']"));

        jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", parentElement);
        Thread.sleep(500);
        selectItemInSelectableDropdown("//button[@id='selectize-input']", "//div[contains(@class, 'dropdown-menu')]//a", "BR-V G (Đen ánh)");
        Assert.assertEquals(driver.findElement(By.xpath("//button[@id='selectize-input']")).getText(), "BR-V G (Đen ánh)");
        Select provinceSelect = new Select(driver.findElement(By.id("province")));
        provinceSelect.selectByVisibleText("TP. Hồ Chí Minh");
        Assert.assertEquals(provinceSelect.getFirstSelectedOption().getText(), "TP. Hồ Chí Minh");
        Thread.sleep(1000);
        Select feeSelect = new Select(driver.findElement(By.id("registration_fee")));
        feeSelect.selectByVisibleText("Khu vực I");
        Assert.assertEquals(feeSelect.getFirstSelectedOption().getText(), "Khu vực I");
    }
    
    @Test
    public void TC_08_Multiple_Select_Less_Than_3() throws InterruptedException {
        driver.get("http://multiple-select.wenzhixin.net.cn/templates/template.html?v=189&url=basic.html");
        
        selectItemInMultipleSelectDropdown("//label[contains(text(),'Basic Select')]/parent::div/following-sibling::div[1]//button", "//label[contains(text(),'Basic Select')]/parent::div/following-sibling::div[1]//ul/li", "January", "February", "March");
        Assert.assertEquals(driver.findElement(By.xpath("//label[contains(text(),'Basic Select')]/parent::div/following-sibling::div[1]//button/span")).getText(), "January, February, March");
    }

    @Test
    public void TC_09_Multiple_Select_More_Than_3() throws InterruptedException {
        driver.get("http://multiple-select.wenzhixin.net.cn/templates/template.html?v=189&url=basic.html");
        
        selectItemInMultipleSelectDropdown("//label[contains(text(),'Basic Select')]/parent::div/following-sibling::div[1]//button", "//label[contains(text(),'Basic Select')]/parent::div/following-sibling::div[1]//ul/li", "January", "February", "March", "April", "May");
        Assert.assertEquals(driver.findElement(By.xpath("//label[contains(text(),'Basic Select')]/parent::div/following-sibling::div[1]//button/span")).getText(), "5 of 12 selected");
    }

    @Test
    public void TC_10_Multiple_Select_Exact_12_Months() throws InterruptedException {
        driver.get("http://multiple-select.wenzhixin.net.cn/templates/template.html?v=189&url=basic.html");
        
        selectItemInMultipleSelectDropdown("//label[contains(text(),'Basic Select')]/parent::div/following-sibling::div[1]//button", "//label[contains(text(),'Basic Select')]/parent::div/following-sibling::div[1]//ul/li", "[Select all]");
        Assert.assertEquals(driver.findElement(By.xpath("//label[contains(text(),'Basic Select')]/parent::div/following-sibling::div[1]//button/span")).getText(), "All selected");
    }

    private void selectItemInSelectableDropdown(String parentXpath, String childXpath, String expectedTextItem) throws InterruptedException {
        driver.findElement(By.xpath(parentXpath)).click();
        List<WebElement> allItems = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));
        for (WebElement item : allItems) {
            if (item.getText().equals(expectedTextItem)) {
                item.click();
                break;
            }
        }
        Thread.sleep(1000);
    }


    private void selectItemInEditableDropdown(String editableXpath, String childXpath, String expectedTextItem) throws InterruptedException {
        driver.findElement(By.xpath(editableXpath)).sendKeys(expectedTextItem);
        Thread.sleep(1000);
        List<WebElement> allItems = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));
        for (WebElement item : allItems) {
            if (item.getText().equals(expectedTextItem)) {
                item.click();
                break;
            }
        }
    }

    private void selectItemInHuaweiCountryDropdown(String parentXpath, String editableXpath, String childXpath, String expectedItem) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement parentElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(parentXpath)));
        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
        actions.moveToElement(parentElement).click().perform();
        Thread.sleep(500);
        driver.findElement(By.xpath(editableXpath)).sendKeys(expectedItem);
        Thread.sleep(500);
        List<WebElement> allItems = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));
        for (WebElement item : allItems) {
            if (item.getText().equals(expectedItem)) {
                item.click();
                break;
            }
        }
    }

    private void selectItemInMultipleSelectDropdown(String parentXpath, String childXpath, String... expectedTextItems) throws InterruptedException {
        driver.findElement(By.xpath(parentXpath)).click();
        Thread.sleep(500);
        List<WebElement> allItems = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));
        for (String expectedTextItem : expectedTextItems) {
            for (WebElement item : allItems) {
                if (item.getText().equals(expectedTextItem)) {
                    item.click();
                    break;
                }
            }
        }
    }

    private boolean isLoadingWheelDisappear() {
        WebElement loadingWheel = driver.findElement(By.xpath("//div[@class='oxd-loading-spinner-container']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOf(loadingWheel));
        return true;
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
