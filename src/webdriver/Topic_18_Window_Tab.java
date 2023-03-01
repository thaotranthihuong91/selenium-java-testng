package webdriver;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_18_Window_Tab {
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
	//@Test
	public void TC_01_GitHub() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		// lấy id của driver hiện tại
		String githubID = driver.getWindowHandle();
		System.out.println("GitHub ID: " + githubID);

		// Click link google
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		sleepInSecond(2);

		switchToWindowByID(githubID);

		System.out.println("Page title google page: " + driver.getTitle());

		// Back to github page
		// Return current pageID
		String googleID = driver.getWindowHandle();
		// Cách 1: Switch to window by id
		switchToWindowByID(googleID);
		System.out.println("Page title google page: " + driver.getTitle());

		// Về lại trang github
		switchToWindowByTitle("Selenium WebDriver");
		System.out.println("Page title github page: " + driver.getTitle());

		// Click Facebook link
		String fbPageTitle = "Facebook - Đăng nhập hoặc đăng ký";
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		sleepInSecond(2);
		// Cách 2: Switch to window by title
		switchToWindowByTitle(fbPageTitle);
		System.out.println("Page title facebook page: " + driver.getTitle());

		// Về lại trang github
		switchToWindowByTitle("Selenium WebDriver");
		System.out.println("Page title github page: " + driver.getTitle());

		// Click tiki link
		driver.findElement(By.xpath("//a[text()='TIKI']")).click();
		sleepInSecond(2);
		String tikiPageTitle = "Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh";
		// Cách 2: Switch to window by title
		switchToWindowByTitle(tikiPageTitle);
		System.out.println("Page title tiki page: " + driver.getTitle());
		Assert.assertEquals(driver.getTitle(), tikiPageTitle);
		
		closeAllWindowExceptParendID(githubID);
	}

	//@Test
	public void TC_02_TechPanda() {
		driver.get("http://live.techpanda.org/");
		String techpandID = driver.getWindowHandle();
		String techpandTitle = driver.getTitle();
		By mobileTab = By.xpath("//a[text()='Mobile']");
		driver.findElement(mobileTab).click();
		
		//Add product to cart
		By addSonyPhoneToCompareBtn = By.xpath("//a[@title='Sony Xperia']//parent::h2//following-sibling::div[@class='actions']//a[text()='Add to Compare']");
		driver.findElement(addSonyPhoneToCompareBtn).click();
		By successMsgBox = By.xpath("//li[@class='success-msg']//span[text()='The product Sony Xperia has been added to comparison list.']");
		Assert.assertTrue(driver.findElement(successMsgBox).isDisplayed());
		
		By addSamPhoneToCompareBtn = By.xpath("//a[@title='Samsung Galaxy']//parent::h2//following-sibling::div[@class='actions']//a[text()='Add to Compare']");
		driver.findElement(addSamPhoneToCompareBtn).click();
		successMsgBox = By.xpath("//li[@class='success-msg']//span[text()='The product Samsung Galaxy has been added to comparison list.']");
		Assert.assertTrue(driver.findElement(successMsgBox).isDisplayed());

		
		//Click compare
		driver.findElement(By.xpath("//span[text()='Compare']")).click();
		
		//Switch to comparison page
		String comparisonPgTitle = "Products Comparison List - Magento Commerce";
		switchToWindowByTitle(comparisonPgTitle);
		sleepInSecond(2);
		Assert.assertEquals(driver.getTitle(), comparisonPgTitle);
		
		//Close tab
		closeAllWindowExceptParendID(techpandID);
		switchToWindowByTitle(techpandTitle);
		System.out.println("Page title: " + driver.getTitle());
		
		//Close tab, cách 2, click Close Window button. Nhớ sau khi click Close phải switch về trang parent
		
		//CLick Clear All
		By clearAllLink = By.xpath("//a[text()='Clear All']");
		driver.findElement(clearAllLink).click();
		WebDriverWait explicitWait = new WebDriverWait(driver, 10);
		Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
		sleepInSecond(2);
				
		successMsgBox = By.xpath("//li[@class='success-msg']//span[text()='The comparison list was cleared.']");
		Assert.assertTrue(driver.findElement(successMsgBox).isDisplayed());
		
	}

	@Test
	public void TC_03_CamDic() {
		driver.get("https://dictionary.cambridge.org/vi/");
		String camDicPageID = driver.getWindowHandle();
		String camDicPageTitle = driver.getTitle();
		
		By loginBtn = By.xpath("//span[text()='Đăng ký']//parent::span//preceding-sibling::span//span[text()='Đăng nhập']");
		driver.findElement(loginBtn).click();
		sleepInSecond(2);
		
		switchToWindowByID(camDicPageID);
		Assert.assertEquals(driver.getTitle(),"Login");
		System.out.println("Login page window title: " + driver.getTitle());
		Assert.assertTrue(driver.findElement(By.xpath("//h2[text()='Log in with your email account']")).isDisplayed());
		
		closeAllWindowExceptParendID(camDicPageID);
		switchToWindowByTitle(camDicPageTitle);		
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

	// Only apply for 2 window/tab. Switch sang tab khác tab có id truyền vào
	public void switchToWindowByID(String pageID) {
		// Lấy ra tất cả id của tab hoặc window đang có
		Set<String> lstID = driver.getWindowHandles();

		// Nếu id khác id truyền vào thì switch sang window có id đó
		for (String id : lstID) {
			if (!id.equals(pageID)) {
				driver.switchTo().window(id);
				sleepInSecond(5);
			}
		}
	}

	// With more than 2 windows/tabs
	//// Switch sang trang có title truyền vào
	public void switchToWindowByTitle(String pageTitle) {
		// Lấy tất cả id của tất cả window/tab
		Set<String> lstID = driver.getWindowHandles();

//		for(Iterator iterator = lstID.iterator(); iterator.hasNext()) {
//			String s = (String) iterator.next();
//		}

		for (String id : lstID) {
			driver.switchTo().window(id);
			String currentPageTitle = driver.getTitle();
			if (currentPageTitle.equals(pageTitle)) {
				break;
			}
		}
	}
	
	public void closeAllWindowExceptParendID(String pageID) {
		Set<String> lstID = driver.getWindowHandles();
		for(String id : lstID) {
			if(!id.equals(pageID)) {
				driver.switchTo().window(id);
				driver.close();
				
			}
		}
	}
}
