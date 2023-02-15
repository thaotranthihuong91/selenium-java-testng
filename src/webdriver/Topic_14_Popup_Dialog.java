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

public class Topic_14_Popup_Dialog {
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
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	
	public void TC_01_FixedPopup_InDOM() {
		driver.get("https://ngoaingu24h.vn/");
		By loginPopup = By.cssSelector("div#modal-login-v1 div.modal-dialog");
		
		//Verify  pop up is undisplayed
		Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());
		
		//Click button Login
		driver.findElement(By.cssSelector("button.login_")).click();
		
		//Verify pop up is displayed
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());
		
		//Enter username and password
		driver.findElement(By.cssSelector("input#account-input")).sendKeys("thaotth");
		driver.findElement(By.cssSelector("input#password-input")).sendKeys("thaotth");
		driver.findElement(By.cssSelector("button.btn-login-v1")).click();
		sleepInSecond(5);
		
		//Verify message login fail
		Assert.assertEquals(driver.findElement(By.cssSelector("div.error-login-panel")).getText(),"Tài khoản không tồn tại!");
	}

	@Test
	public void TC_02_FixedPopup_InDOM() {
		driver.get("https://skills.kynaenglish.vn/");
		
		//Verify popup login undisplayed		
		By loginPopup = By.cssSelector("div#k-popup-account-login");
		Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());
		
		driver.findElement(By.cssSelector("a.login-btn")).click();
		sleepInSecond(2);
		
		//Verify popup login displayed
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());
		
		driver.findElement(By.cssSelector("input#user-login")).sendKeys("thaotth@gmail.com");
		driver.findElement(By.cssSelector("input#user-password")).sendKeys("123456789");
		driver.findElement(By.cssSelector("button#btn-submit-login")).click();
		sleepInSecond(3);
		
		//Verify message
		Assert.assertEquals(driver.findElement(By.cssSelector("div#password-form-login-message")).getText(), "Sai tên đăng nhập hoặc mật khẩu");
		
		driver.findElement(By.cssSelector("button.k-popup-account-close")).click();
		sleepInSecond(2);
		
		//Verify undisplayed
		Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());
	}

	
	public void TC_03_() {
		
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
