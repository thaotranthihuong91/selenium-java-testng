package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_19_JavascriptExecutor {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Random rand = new Random();
	String emailAdd = "testing" + String.valueOf(rand.nextInt(999999)) + "@gmail.com";

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}

		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
	}

	//@Test
	public void TC_01_TechPanda() {		
		//navigateToUrlByJS("http://live.techpanda.org/");
		driver.get("http://live.techpanda.org/");
		sleepInSecond(5);
		
		//String homepgDomain = (String) executeForBrowser("return document.domain;");
		Assert.assertEquals(executeForBrowser("return document.domain;"), "live.techpanda.org");
		Assert.assertEquals(executeForBrowser("return document.URL;"), "http://live.techpanda.org/");

		clickToElementByJS("//a[text()='Mobile']");
		sleepInSecond(3);
		clickToElementByJS(
				"//a[@title='Samsung Galaxy']//parent::h2//following-sibling::div[@class='actions']//a[text()='Add to Compare']");

		Assert.assertTrue(getInnerText().contains("The product Samsung Galaxy has been added to comparison list."));
		Assert.assertTrue(areExpectedTextInInnerText("The product Samsung Galaxy has been added to comparison list."));

		hightlightElement("//a[text()='Customer Service']");
		clickToElementByJS("//a[text()='Customer Service']");
		sleepInSecond(3);

		hightlightElement("//input[@id=newsletter");
		Assert.assertEquals(executeForBrowser("return document.title;"), "Customer Service");
		scrollToElementOnTop("//input[@id=newsletter']");
		sleepInSecond(3);

		sendkeyToElementByJS("//input[@id=newsletter']", emailAdd);
		sleepInSecond(3);

		hightlightElement("//button[@title='Subscribe");
		clickToElementByJS("//button[@title='Subscribe']");
		sleepInSecond(3);

		Assert.assertTrue(getInnerText().contains("Thank you for you subscription."));
		Assert.assertTrue(areExpectedTextInInnerText("Thank you for you subscription."));

		navigateToUrlByJS("http://demo.guru99.com/v4/");
		sleepInSecond(5);
		Assert.assertEquals(executeForBrowser("return document.domain;"), "demo.guru99.com");
	}

	@Test
	public void TC_02_Rode() {
		driver.get("https://warranty.rode.com/");
		By registerBtn = By.xpath("//button[contains(text(),'Register')]");
		
		driver.findElement(registerBtn).click();
		sleepInSecond(3);
		Assert.assertEquals(getElementValidationMessage("//input[@id='firstname']"), "Please fill out this field.");
		getElement("//input[@id='firstname']").sendKeys("Automation");
		
		driver.findElement(registerBtn).click();
		sleepInSecond(3);
		Assert.assertEquals(getElementValidationMessage("//input[@id='surname']"), "Please fill out this field.");
		getElement("//input[@id='surname']").sendKeys("Test");
		
		driver.findElement(registerBtn).click();
		sleepInSecond(3);
		Assert.assertEquals(getElementValidationMessage("//input[@id='email']"), "Please fill out this field.");
		getElement("//input[@id='email']").sendKeys(emailAdd);
		
		driver.findElement(registerBtn).click();
		sleepInSecond(3);
		Assert.assertEquals(getElementValidationMessage("//input[@id='password']"), "Please fill out this field.");
		getElement("//input[@id='password']").sendKeys("123456");
		
		driver.findElement(registerBtn).click();
		sleepInSecond(3);
		Assert.assertEquals(getElementValidationMessage("//input[@id='password-confirm']"), "Please fill out this field.");
		getElement("//input[@id='password-confirm']").sendKeys("123456");
	}
	
	

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	// Sleep cứng (Static wait)
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor
				.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void hightlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element,
				"border: 4px solid red; border-style: dashed;");
		sleepInSecond(2);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}

	public void scrollToElementOnTop(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void scrollToElementOnDown(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
	}

	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
				getElement(locator));
		return status;
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}

}
