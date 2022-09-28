package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Browser {
	// Khai báo
	WebDriver driver;
	WebElement element;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}

		// Khởi tạo
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Browser() {
		// Các hàm tương tác với Browser thông qua biến driver

		// Đóng tab/ window đang active
		driver.close();

		// Đóng browser
		driver.quit();

		// Tìm ra 1 element (single)
		driver.findElement(By.cssSelector(""));

		// Tìm ra nhiều element (multiple)
		List<WebElement> links = driver.findElements(By.cssSelector(""));

		// Mở ra cái Url truyền vào
		driver.get("https://www.google.com");

		// Trả về 1 Url tại page đang đứng
		String testUrl = driver.getCurrentUrl();
		String testTitle = driver.getTitle();

		// Source code của page hiện tại
		String testSrcCode = driver.getPageSource();

		// Lấy ra ID của tab/ window đang đứng/ active (Windows/ Tab)
		driver.getWindowHandle();
		driver.getWindowHandles();

		// Trả về 1 Option
		driver.manage().getCookies(); // Cookies (fw)
		driver.manage().logs().getAvailableLogTypes(); // Log (fw)

		driver.manage().window().fullscreen();
		driver.manage().window().maximize();// Không full viền
		driver.manage().window();

		// Test GUI (Graphic User Interface)
		// Font/ Size/ Color/ Position/ Location,....
		// Ưu tiên làm chức năng functional, còn giao diện làm sau

		// Chờ element được tìm thấy trong khoảng thời gian x (Webdriver wait)
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		// Chờ page load thành công sau x giây
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

		// Chờ cho script được inject thành công vào browser/ element sau x giấy
		// (Javascript Executor)
		driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);

		// Chờ page load thành công sau x giây
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

		driver.navigate().back();
		driver.navigate().forward();
		driver.navigate().refresh();
		driver.navigate().to("");

		// Alert/ Frame (iFrame)/ window
		driver.switchTo().alert();
		driver.switchTo().frame(0);
		driver.switchTo().window("");
	}

	@Test
	public void TC_02_Element() {
		// Các hàm tương tác với Element thông qua element
	}

	@Test
	public void TC_03_Edge() {
		System.setProperty("webdriver.edge.driver", projectPath + "/browserDrivers/msedgedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
		driver.quit();
	}

	@AfterClass
	public void afterClass() {
		// Đóng tab/ window đang active
		driver.close();

		// Đóng browser
		driver.quit();
	}
}
