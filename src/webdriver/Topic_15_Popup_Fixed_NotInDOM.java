package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Popup_Fixed_NotInDOM {
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

		FirefoxOptions op = new FirefoxOptions();
		op.setProfile(new FirefoxProfile());
		op.addPreference("dom.webnotifications.enabled", false);
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	//Fixed popup: hiển thị, đóng theo yêu cầu của người dùng
	@Test
	public void TC_01_FixedPopup_NotInDOM_TiKi() {
		driver.get("https://tiki.vn/");

		By loginPopup = By.cssSelector("div.ReactModal__Content");

		// Verify chưa click vào login button
		Assert.assertEquals(driver.findElements(loginPopup).size(), 0);

		// Click bật login popup
		driver.findElement(By.cssSelector("div[data-view-id*='header_account'")).click();
		sleepInSecond(3);

		// Verify hiển thị 1 trong 2 cách dưới
		Assert.assertEquals(driver.findElements(loginPopup).size(), 1);
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

		//driver.findElement(By.cssSelector("input[name='tel']")).sendKeys("0987654321");
		//sleepInSecond(3);

		//Click Đăng nhập với email
		driver.findElement(By.cssSelector("p.login-with-email")).click();
		sleepInSecond(2);
		
		//Click button Đăng nhập
		driver.findElement(By.xpath("//button[contains(text(),'Đăng nhập')]")).click();
		
		//Verify error message displayed
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='error-mess' and text()='Email không được để trống']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='error-mess' and text()='Mật khẩu không được để trống']")).isDisplayed());

		// Close popup
		driver.findElement(By.cssSelector("img.close-img")).click();
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
