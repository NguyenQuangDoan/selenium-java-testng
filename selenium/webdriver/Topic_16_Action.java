package webdriver;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.openqa.selenium.Alert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class)
public class Topic_16_Action {

    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    boolean headless = false;
    Actions actions;
    WebDriverWait wait;

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
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void TC_01_Hover_To_Element() {
        driver.get("https://automationfc.github.io/jquery-tooltip/");
        actions.moveToElement(driver.findElement(By.id("age"))).perform();
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ui-tooltip-content']")).getText(), "We ask for your age only for statistical purposes.");
    }
    
    @Test
    public void TC_02_Hover_To_Element() throws InterruptedException {
        driver.get("http://www.myntra.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        // Hover vào Kids menu
        WebElement kidsMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-group='kids']")));
        actions.moveToElement(kidsMenu).perform();
        Thread.sleep(1000);
        
        // Đợi và hover vào Home & Bath
        WebElement homeBathLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='desktop-categoryContainer']//a[text()='Home & Bath']")));
        actions.moveToElement(homeBathLink).perform();
        Thread.sleep(500);
        
        // Click bằng JavaScript để chắc chắn
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", homeBathLink);
        Thread.sleep(2000);
        
        Assert.assertTrue(driver.getCurrentUrl().contains("kids-home-bath"));
    }

    @Test
    public void TC_03_Hover_To_Element() throws InterruptedException {
        driver.get("https://www.fahasa.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        // Hover vào Kids menu
        WebElement iconMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='icon_menu']")));
        actions.moveToElement(iconMenu).perform();
        Thread.sleep(1000);
        
        // Đợi và hover vào Home & Bath
        WebElement sachTrongNuoc = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Sách Trong Nước']")));
        actions.moveToElement(sachTrongNuoc).perform();
        Thread.sleep(500);
        
        // Click bằng JavaScript để chắc chắn
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='fhs_column_stretch']//a[text()='Kỹ Năng Sống']")).isDisplayed());
    }
    
    @Test
    public void TC_04_Click_And_Hold_Element() throws InterruptedException {
        driver.get("https://automationfc.github.io/jquery-selectable/");
        
        // Click and hold từ item 1 đến item 4
        WebElement item1 = driver.findElement(By.xpath("//li[text()='1']"));
        WebElement item4 = driver.findElement(By.xpath("//li[text()='4']"));
        
        actions.clickAndHold(item1)
               .moveToElement(item4)
               .release()
               .perform();
        
        Thread.sleep(500);

        // Verify các item được chọn (1, 2, 3, 4)
        List<WebElement> selectedItems = driver.findElements(By.xpath("//li[contains(@class,'ui-selected')]"));
        Assert.assertEquals(selectedItems.size(), 4);
        
        for (int i = 0; i < selectedItems.size(); i++) {
            Assert.assertEquals(selectedItems.get(i).getText(), String.valueOf(i + 1));   
        }
    }

    @Test
    public void TC_05_Click_And_Select_Random_Element() throws InterruptedException {
        driver.get("https://automationfc.github.io/jquery-selectable/");

        WebElement item1 = driver.findElement(By.xpath("//li[text()='1']"));
        WebElement item3 = driver.findElement(By.xpath("//li[text()='3']"));
        WebElement item6 = driver.findElement(By.xpath("//li[text()='6']"));
        WebElement item11 = driver.findElement(By.xpath("//li[text()='11']"));

        List<WebElement> itemsToSelect = Arrays.asList(item1, item3, item6, item11);
        
        actions.keyDown(Keys.COMMAND).perform();
        
        for (WebElement item : itemsToSelect) {
            actions.click(item).perform();
            Thread.sleep(200);
        }
        
        actions.keyUp(Keys.COMMAND).perform();
        
        for (WebElement item : itemsToSelect) {
            Assert.assertTrue(item.getAttribute("class").contains("ui-selected"));
        }
    }

    @Test
    public void TC_06_Double_Click() throws InterruptedException {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        
        WebElement doubleClickButton = driver.findElement(By.xpath("//button[text()='Double click me']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", doubleClickButton);
        Thread.sleep(500);
        
        actions.doubleClick(doubleClickButton).perform();
        Thread.sleep(2000);
        
        Assert.assertEquals(driver.findElement(By.id("demo")).getText(), "Hello Automation Guys!");
    }   

    @Test
    public void TC_07_Right_Click() throws InterruptedException {
        driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
        actions.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).perform();
        Thread.sleep(2000);
        Assert.assertTrue(driver.findElement(By.cssSelector("ul.context-menu-list")).isDisplayed());
        actions.moveToElement(driver.findElement(By.cssSelector(".context-menu-item.context-menu-icon-quit"))).perform();
        Thread.sleep(2000);
        Assert.assertTrue(driver.findElement(By.cssSelector(".context-menu-visible.context-menu-hover")).isDisplayed());
        actions.click(driver.findElement(By.cssSelector(".context-menu-item.context-menu-icon-quit"))).perform();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "clicked: quit");
        alert.accept();
        
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("ul.context-menu-list")));
    }

    @Test
    public void TC_08_Drag_Drop() throws InterruptedException {
        driver.get("https://automationfc.github.io/kendo-drag-drop/");
        
        WebElement source = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("droptarget"));
        
        actions.dragAndDrop(source, target).perform();
        Thread.sleep(2000);
        
        Assert.assertEquals(driver.findElement(By.id("droptarget")).getText(), "You did great!");
        
        String bgColor = driver.findElement(By.id("droptarget")).getCssValue("background-color");
        Assert.assertTrue(bgColor.equals("rgb(3, 169, 244)") || bgColor.equals("rgba(3, 169, 244, 1)"));
    }
    

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
