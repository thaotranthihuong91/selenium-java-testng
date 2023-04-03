package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_21_Wait_Part1_Element_Status {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	WebDriverWait explicitWait;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}

		driver = new FirefoxDriver();
		// Cho việc findElement/findElements -> Chung chung
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Cho trạng thái của element -> Cụ thể
		explicitWait = new WebDriverWait(driver, 30);
	}

	// @Test
	public void TC_01_Visible_Displayed() {
		driver.get("https://www.facebook.com");
		// ĐK 1: Element có trên UI và có trên cây HTML

		// Chờ email textbox được hiển thị trước khi sendKey vào nó
		// Chờ trong khoảng thời gian 30s
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("input#email")));
	}

	 @Test
	public void TC_02_Invisible_Undisplayed_Case1() {
		// ĐK 2: Element không có (ko nhìn thấy) trên UI nhưng vẫn có trong cây HTML

		driver.get("https://www.facebook.com");
		driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();

		// Confirmation email txtbox is undisplayed
		explicitWait.until(ExpectedConditions
				.invisibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));

		driver.findElement(By.cssSelector("input[name='reg_email__']")).sendKeys("thao@gmail.com");

		// Confirmation Email txtbox is displayed
		explicitWait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
		driver.findElement(By.cssSelector("input[name='reg_email_confirmation__']")).sendKeys("thao@gmail.com");
	}

	// @Test
	public void TC_02_Invisible_Undisplayed_Case2() {
		// ĐK 3: Element không có (ko nhìn thấy) trên UI và không có trong cây HTML
		driver.get("https://www.facebook.com");
		// Confirmation Email txtbox is undisplayed
				explicitWait.until(ExpectedConditions
						.visibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
	}

	// @Test
	public void TC_03_Presence_Case1() {
		// ĐK 1: Element có trên UI và có trên cây HTML
		driver.get("https://www.facebook.com");
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input#emaill")));
	}
	
	// @Test
		public void TC_03_Presence_Case2() {
			// ĐK 2: Element không có (ko nhìn thấy) trên UI nhưng vẫn có trong cây HTML
			driver.get("https://www.facebook.com");
			driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();
			
			//Confirmation Email txtbox is presense			
			explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
		}

	@Test
	public void TC_04_Staleness() {
		//Có trong html và sau đó apply đk 3
		driver.get("https://www.facebook.com");
		driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();
		//B1: Element phải có trong HTML
		WebElement confirmEmailTxtbox = driver.findElement(By.cssSelector("input[name='reg_email_confirmation__']"));
		driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/preceding-sibling::img")).click();
		
		//Wait for confirm email staleness -> chạy nhanh
		explicitWait.until(ExpectedConditions.stalenessOf(confirmEmailTxtbox));
		
		//Chạy lâu
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));
		
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
