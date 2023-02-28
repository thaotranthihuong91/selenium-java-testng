package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Iframe_frame {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor js;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}

		FirefoxOptions options = new FirefoxOptions();
		options.setProfile(new FirefoxProfile());
		options.addPreference("dom.webnotifications.enable", false);
		driver = new FirefoxDriver(options);
		js = (JavascriptExecutor) driver;

		// driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	// @Test
	public void TC_01_Iframe() {
		driver.get("https://skills.kynaenglish.vn/");
		By iFrame = By.cssSelector("div.face-content>iframe");
		Assert.assertTrue(driver.findElement(iFrame).isDisplayed());

		// Switch to facebook iframe
		// driver.switchTo().frame(0);
		driver.switchTo().frame(driver.findElement(iFrame));
		By countLike = By.xpath("//a[text()='Kyna.vn']//parent::div//following-sibling::div");
		Assert.assertEquals(driver.findElement(countLike).getText(), "165K likes");

		// Switch về trang parent
		driver.switchTo().defaultContent();

		// Switch sang chat iframe
		// driver.switchTo().frame("cs_chat_iframe");
		driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#cs_chat_iframe")));
		sleepInSecond(2);

		js.executeScript("arguments[0].click();", driver.findElement(By.cssSelector("div.button_bar")));
		// driver.findElement(By.cssSelector("div.button_bar")).click();
		sleepInSecond(3);

		driver.findElement(By.cssSelector("input.input_name")).sendKeys("thao");
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0987654321");
		new Select(driver.findElement(By.cssSelector("select#serviceSelect"))).selectByVisibleText("TƯ VẤN TUYỂN SINH");
		driver.findElement(By.name("message")).sendKeys("note");

		// Switch về trang parent
		driver.switchTo().defaultContent();

		driver.findElement(By.id("live-search-bar")).sendKeys("Excel");
		driver.findElement(By.className("search-button")).click();
		sleepInSecond(5);

		// Verify
		List<WebElement> lstResult = driver.findElements(By.cssSelector("div.content>h4"));
		for (WebElement e : lstResult) {
			Assert.assertTrue(e.getText().contains("Excel"));
		}
	}

	// @Test
	public void TC_02_Iframe() {
		//Trang1 chứa iframe2
		
		//Iframe2 chứa iframe3
		
		//Quay từ iframe3 về iframe2 chứ không hỗ trợ iframe3 về trang1
		driver.switchTo().parentFrame();
		
		//Iframe2 về trang1
		driver.switchTo().defaultContent();		
	}
	
	@Test
	public void TC_03_Frame() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		
		//Switch to frame
		driver.switchTo().frame("login_page");
		
		driver.findElement(By.name("fldLoginUserId")).sendKeys("thao");
		driver.findElement(By.cssSelector("a.login-btn")).click();
		sleepInSecond(5);
		//Switch to default
		driver.switchTo().defaultContent();
		
		//Verify hiển thị input password
		Assert.assertTrue(driver.findElement(By.cssSelector("input#keyboard")).isDisplayed());
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
