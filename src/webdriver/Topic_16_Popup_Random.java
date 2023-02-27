package webdriver;

import java.util.Random;
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

public class Topic_16_Popup_Random {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String emailAdd = "testdemo" + getRandomNumber() + "@gmail.com";
	String key = "Agile Testing Explained";

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

	// Random popup: hiển thị, đóng ngẫu nhiên hoặc không hiển thị

	public void TC_01_RandomPopup_InDOM() {
		driver.get("https://javacodegeeks.com/");
		sleepInSecond(20);
		By lePopup = By.xpath("//div[@class='lepopup-form-inner']//div[@data-type='rectangle']");
		if (driver.findElement(lePopup).isDisplayed()) {
			// Nhập email
			driver.findElement(By.cssSelector("div.lepopup-input>input")).sendKeys(emailAdd);
			sleepInSecond(5);
			driver.findElement(By.cssSelector("a.lepopup-button")).click();
			sleepInSecond(5);
			Assert.assertTrue(driver.findElement(By.cssSelector("div.lepopup-element-html-content>p")).getText()
					.contains("Your sign-up request was successful. We will contact you shortly."));
			sleepInSecond(10);
		}
		// Search
		driver.findElement(By.cssSelector("input#s")).sendKeys(key);
		driver.findElement(By.cssSelector("button.search-button")).click();
		sleepInSecond(3);

		Assert.assertEquals(driver.findElement(By.cssSelector("div.post-listing>article:first-child>h2>a")).getText(),
				key);

	}

	// @Test
	public void TC_02_RandomPopup_InDOM() {
		driver.get("https://vnk.edu.vn");
		sleepInSecond(30);
		By popUp = By.cssSelector("div.tve_flt");
		if (driver.findElement(popUp).isDisplayed()) {
			driver.findElement(By.cssSelector("svg.tcb-icon")).click();
			sleepInSecond(3);
		}
		By buttonDSKH = By.xpath("//button[text()='Danh sách khóa học']");
		driver.findElement(buttonDSKH).click();
		sleepInSecond(5);
		Assert.assertEquals(driver.getTitle(), "Lịch khai giảng các khóa học tại VNK EDU | VNK EDU");
	}

	@Test
	public void TC_03_RandomPopup_NotInDOM() {
		driver.get("https://dehieu.vn/");
		sleepInSecond(5);

		// findElement -> Fail khi không tìm thấy element -> NoSuchElementException
		// findElements -> Ko fail khi ko tìm thấy element -> Trả về 1 list rỗng

		By popUp = By.cssSelector("div.popup-content");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		if (driver.findElements(popUp).size() > 0 && driver.findElements(popUp).get(0).isDisplayed()) {
			driver.findElement(By.cssSelector("input#popup-name")).sendKeys("Thao");
			driver.findElement(By.id("popup-email")).sendKeys("1234");
			driver.findElement(By.id("close-popup")).click();
		}
		driver.findElement(By.xpath("//a[text()='Tất cả khóa học']")).click();
		sleepInSecond(3);
		String courseName = "Khóa Học Triển Khai Thi Công Và Giám Sát Hệ Thống Cơ Điện";
		driver.findElement(By.id("search-courses")).sendKeys(courseName);
		driver.findElement(By.id("search-course-button")).click();
		Assert.assertEquals(driver.findElements(By.cssSelector("div.course")).size(), 1);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.course-content>h4")).getText(), courseName);
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

	public int getRandomNumber() {
		return new Random().nextInt(99999);
	}
}
