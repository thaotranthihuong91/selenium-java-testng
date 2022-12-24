package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Button_Radio_Checkbox {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	
	public void TC_01_Button() {
		driver.get("https://www.fahasa.com/customer/account/create");
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
		By loginBtn = By.cssSelector("button.fhs-btn-login");
		// Verify login button disabled
		Assert.assertFalse(driver.findElement(loginBtn).isEnabled());
		
		String loginBtnBackground = driver.findElement(loginBtn).getCssValue("background-image");
		Assert.assertTrue(loginBtnBackground.contains("rgb(224, 224, 224)"));

		driver.findElement(By.cssSelector("input#login_username")).sendKeys("0393504540");
		driver.findElement(By.cssSelector("input#login_password")).sendKeys("0393504540");
		sleepInSecond(2);

		// Verify login button enabled
		Assert.assertTrue(driver.findElement(loginBtn).isEnabled());
		

		loginBtnBackground = driver.findElement(loginBtn).getCssValue("background-color");
		Color loginBtnBackgroundColor = Color.fromString(loginBtnBackground);
		Assert.assertEquals(loginBtnBackgroundColor.asHex().toUpperCase(), "#C92127");

	}

	
	public void TC_02_Checkbox_Radio() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		
		//Chọn 1 checkbox
		driver.findElement(By.xpath("//label[contains(text(),'Anemia')]/preceding-sibling::input")).click();
		
		//Chọn 1 radio
		driver.findElement(By.xpath("//label[contains(text(),\"I don't drink\")]/preceding-sibling::input")).click();
		
		//Verify các checkbox, radio đã được chọn
		Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(),'Anemia')]/preceding-sibling::input")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(),\"I don't drink\")]/preceding-sibling::input")).isSelected());
		
		//Checkbox có thể tự bỏ chọn được
		//Radio không thể tự bỏ chọn
		
		//Bỏ chọn checkbox, verify checkbox được bỏ chọn
		driver.findElement(By.xpath("//label[contains(text(),'Anemia')]/preceding-sibling::input")).click();
		Assert.assertFalse(driver.findElement(By.xpath("//label[contains(text(),'Anemia')]/preceding-sibling::input")).isSelected());
	}

	
	public void TC_03_Default_Checkbox_Multiple() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		//Chọn tất cả checkbox
		List<WebElement> allCheckboxes = driver.findElements(By.cssSelector("input.form-checkbox"));
		for(WebElement e : allCheckboxes) {
			e.click();
		}
		
		//Verify tất cả checkbox được chọn
		for(WebElement e : allCheckboxes) {
			Assert.assertTrue(e.isSelected());
		}
		
		//Nếu gặp 1 checkbox có tên là X thì mới chọn
		for(WebElement e : allCheckboxes) {
			if("Liver Disease".equals(e.getAttribute("value"))) {
				e.click();
			}			
		}
	}
	
	@Test
	public void TC_04_Default_Checkbox() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		//Chọn checkbox và verify
		
		checkToCheckboxOrRadio(By.xpath("//label[contains(text(),'Dual-zone air conditioning')]/preceding-sibling::input"));
		Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(),'Dual-zone air conditioning')]/preceding-sibling::input")).isSelected());
		
		//Bỏ chọn checkbox và verify
		uncheckToCheckbox(By.xpath("//label[contains(text(),'Dual-zone air conditioning')]/preceding-sibling::input"));
		Assert.assertFalse(driver.findElement(By.xpath("//label[contains(text(),'Dual-zone air conditioning')]/preceding-sibling::input")).isSelected());
	
		driver.get("http://demos.telerik.com/kendo-ui/styling/radios");
		checkToCheckboxOrRadio(By.xpath("//label[contains(text(),'2.0 Petrol, 147kW')]/preceding-sibling::input"));
		//driver.findElement(By.xpath("//label[contains(text(),'2.0 Petrol, 147kW')]/preceding-sibling::input")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(),'2.0 Petrol, 147kW')]/preceding-sibling::input")).isSelected());
	}
	
	public void checkToCheckboxOrRadio(By by) {
		if(!driver.findElement(by).isSelected()) {
			clickElementByJS(by);
		}
	}
	
	public void uncheckToCheckbox(By by) {
		if(driver.findElement(by).isSelected()) {
			clickElementByJS(by);
		}
	}
	
	public void clickElementByJS(By by) {
		WebElement ele = driver.findElement(by);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click()", ele);
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
}
