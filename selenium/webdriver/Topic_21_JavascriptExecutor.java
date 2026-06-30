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
import org.openqa.selenium.Alert;

import java.time.Duration;
import java.util.Random;

@Listeners(TestListener.class)
public class Topic_21_JavascriptExecutor {

    WebDriver driver;
    WebDriverWait wait;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    boolean headless = false;
    JavascriptExecutor jsExecutor;
	String txtEmail = "john.doe" + new Random().nextInt(9999) + "@test.com";

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void TC_01_TechPanda() {
        navigateToUrlByJS("https://live.techpanda.org/");
		sleepInSecond(2);
		Assert.assertEquals(executeForBrowser("return document.domain;"), "live.techpanda.org");
		Assert.assertEquals(executeForBrowser("return document.URL;"), "https://live.techpanda.org/");
		hightlightElement("//a[text()='Mobile']");
		clickToElementByJS("//a[text()='Mobile']");
		sleepInSecond(2);
		hightlightElement("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']/button");
		clickToElementByJS("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']/button");
		sleepInSecond(2);
		Assert.assertEquals(executeForBrowser("return document.querySelector('li.success-msg').innerText;"), "Samsung Galaxy was added to your shopping cart.");
		sleepInSecond(2);
		hightlightElement("//a[text()='Customer Service']");
		clickToElementByJS("//a[text()='Customer Service']");
		sleepInSecond(2);
		Assert.assertEquals(executeForBrowser("return document.title;"), "Customer Service");
		scrollToBottomPage();
		hightlightElement("//input[@type='email']");
		sleepInSecond(2);
		inputToElementByJS("//input[@type='email']", "doan1@gmail.com");
		sleepInSecond(2);
		clickToElementByJS("//button[@title='Subscribe']");
		sleepInSecond(2);
		driver.switchTo().alert().accept();
		sleepInSecond(2);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.success-msg")));
		Assert.assertEquals(executeForBrowser("return document.querySelector('li.success-msg').innerText;"), "Thank you for your subscription.");
		navigateToUrlByJS("https://www.facebook.com/");
		sleepInSecond(2);
		Assert.assertEquals(executeForBrowser("return document.domain;"), "www.facebook.com");
    }

	@Test
	public void TC_02_HTML5_validation_Message() {
		navigateToUrlByJS("https://automationfc.github.io/html5/index.html");
		sleepInSecond(2);
		clickToElementByJS("//input[@value='SUBMIT']");

		String validationMessage = (String) executeForBrowser("return document.querySelector('#fname').validationMessage;");
		Assert.assertEquals(validationMessage, "Please fill out this field.");
		inputToElementByJS("//input[@id='fname']", "John");

		clickToElementByJS("//input[@value='SUBMIT']");

		validationMessage = (String) executeForBrowser("return document.querySelector('#pass').validationMessage;");
		Assert.assertEquals(validationMessage, "Please fill out this field.");
		inputToElementByJS("//input[@id='pass']", "123456");

		clickToElementByJS("//input[@value='SUBMIT']");

		validationMessage = (String) executeForBrowser("return document.querySelector('#em').validationMessage;");
		Assert.assertEquals(validationMessage, "Please fill out this field.");

		inputToElementByJS("//input[@id='em']", "123bhj");
		sleepInSecond(1);

		String emailValue = (String) executeForBrowser("return document.querySelector('#em').value;");

		clickToElementByJS("//input[@value='SUBMIT']");
		sleepInSecond(1);

		validationMessage = (String) executeForBrowser("return document.querySelector('#em').validationMessage;");
		Assert.assertEquals(validationMessage, "Please enter an email address.");
		inputToElementByJS("//input[@id='em']", "123bhj@gmail.com");

		clickToElementByJS("//input[@value='SUBMIT']");

		validationMessage = (String) executeForBrowser("return document.querySelector('select').validationMessage;");
		Assert.assertEquals(validationMessage, "Please select an item in the list.");
	}

	@Test
	public void TC_03_Create_An_Account() {
		navigateToUrlByJS("http://live.techpanda.org/");
		sleepInSecond(2);
		clickToElementByJS("//div[@id='header-account']//a[@title='My Account']");
		sleepInSecond(2);
		hightlightElement("//a[@title='Create an Account']");
		clickToElementByJS("//a[@title='Create an Account']");
		sleepInSecond(2);
		inputToElementByJS("//input[@id='firstname']", "John");
		inputToElementByJS("//input[@id='middlename']", "Jr.");
		inputToElementByJS("//input[@id='lastname']", "Doe");
		inputToElementByJS("//input[@id='email_address']", txtEmail);
		inputToElementByJS("//input[@id='password']", "Password123");
		inputToElementByJS("//input[@id='confirmation']", "Password123");
		hightlightElement("//button[@title='Register']");
		clickToElementByJS("//button[@title='Register']");
		driver.switchTo().alert().accept();
		sleepInSecond(4);
		Assert.assertEquals(executeForBrowser("return document.querySelector('li.success-msg').innerText;"), "Thank you for registering with Main Website Store.");
		clickToElementByJS("//div[@id='header-account']//a[@title='Log Out']");
		Assert.assertEquals(executeForBrowser("return document.title;"), "Home page");
		Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='You are now logged out']")).isDisplayed());
	}

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean isExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void sleepInSecond(int timeout) {
        try {
            Thread.sleep(timeout * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
		sleepInSecond(3);
	}

	public void hightlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
		sleepInSecond(2);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
		sleepInSecond(3);
	}

	public String getElementTextByJS(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].textContent;", getElement(locator));
	}

	public void scrollToElementOnTop(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void scrollToElementOnDown(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
	}
	
	public void setAttributeInDOM(String locator, String attributeName, String attributeValue) {
		jsExecutor.executeScript("arguments[0].setAttribute('" + attributeName + "', '" + attributeValue +"');", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}
	
	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void inputToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].value = '" + value + "'; arguments[0].checkValidity();", getElement(locator));
	}
	
	public String getAttributeInDOM(String locator, String attributeName) {
		return (String) jsExecutor.executeScript("return arguments[0].getAttribute('" + attributeName + "');", getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
	}

	public boolean isImageLoaded(String locator) {
		return (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", getElement(locator));
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}
}
