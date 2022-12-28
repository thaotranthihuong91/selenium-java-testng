package webdriver;

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

public class Topic_11_Custom_Checkbox_Radio {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jse;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		jse = (JavascriptExecutor) driver;
	}

	@Test
	public void TC_01_Custom_Radio_Checkbox() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");

		// Case1: Thẻ input bị che nên không thao tác được nhưng dùng để verify được (Vì
		// hàm isSelected() chỉ work với thẻ input)

		// Case2: Thẻ khác thẻ input: để click (span/div/label...) -> đang hiển thị được
		// Thẻ input dùng để verify

		By radioButton = By.cssSelector("div[aria-label='Cần Thơ']");
		By checkbox = By.cssSelector("div[aria-label='Quảng Ngãi']");

		if (!"true".equals(driver.findElement(radioButton).getAttribute("aria-checked"))) {
			clickElementByJS(jse, radioButton);
			sleepInSecond(2);
		}

		if (!"true".equals(driver.findElement(checkbox).getAttribute("aria-checked"))) {
			clickElementByJS(jse, checkbox);
			sleepInSecond(2);
		}

		//Verify cách 1
		Assert.assertTrue(driver.findElement(By.cssSelector("div[aria-label='Cần Thơ'][aria-checked='true']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("div[aria-label='Quảng Ngãi'][aria-checked='true']")).isDisplayed());
		
		//Verify cách 2
		Assert.assertEquals(driver.findElement(radioButton).getAttribute("aria-checked"), "true");
		Assert.assertEquals(driver.findElement(checkbox).getAttribute("aria-checked"), "true");
	}

	public void clickElementByJS(JavascriptExecutor jse, By by) {
		WebElement ele = driver.findElement(by);
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
