package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
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
		//Cách 1: Khai báo biến và dùng lại
		
		//Cách 2: Khai báo biến cùng với kiểu dữ liệu trả về của hàm findElement
		
		//Xóa dữ liệu trong 1 field dạng editable (có thể nhập)
		//Textbox/ Text area/ Editable dropdown
		element.clear();
		//Nhập dữ liệu vào field dạng editable
		element.sendKeys("");
		element.sendKeys(Keys.ENTER);
		
		//Trả về giá trị nằm trong attribute của element
		element.getAttribute("");
		//Trả về thuộc tính css của element này
		//font/ size/ color
		element.getCssValue("");
		
		//Test GUI: point/ rectangle/ size (Visualize testing)
		element.getLocation();
		element.getRect();
		element.getSize();
		
		//Chụp hình và attach vào html report
		element.getScreenshotAs(OutputType.FILE);
		//Trả về thẻ html của element
		element.getTagName();
		
		
		element.getText();
		
		element.isDisplayed();//Hiển thị: true, không hiển thị: false
		
		//Trả về giá trị đúng hoặc sai của 1 element có thể thao tác được hay không
		//Enabled: true
		//Bị disable: false
		element.isEnabled();
		
		//trả về giá trị đúng hoặc sai của 1 element đã được chọn rồi hay chưa
		//checkbox/ radio
		element.isSelected();//Chọn rồi: true, chưa chọn: false
		
		//dropdown: có 1 thư viện riêng để xử lý (Select)
		
		//Chỉ làm việc được với form (register/login...form)
		element.submit();
		
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
