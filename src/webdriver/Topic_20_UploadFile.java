package webdriver;

import java.io.File;
import java.util.List;
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

public class Topic_20_UploadFile {
	WebDriver driver;
	JavascriptExecutor js;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	
	String pic1Name = "pic1.png";
	String pic2Name = "pic2.png";
	String pic3Name = "pic3.png";
	
	String pic1Path = projectPath + File.separator + "uploadFile" + File.separator + pic1Name;
	String pic2Path = projectPath + File.separator + "uploadFile" + File.separator + pic2Name;
	String pic3Path = projectPath + File.separator + "uploadFile" + File.separator + pic3Name;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}
		
		driver = new FirefoxDriver();
		js = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	
	public void TC_01_One_File_Per_Time() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		By uploadFile = By.xpath("//input[@type='file']");
		
		driver.findElement(uploadFile).sendKeys(pic1Path);
		sleepInSecond(1);		
		
		driver.findElement(uploadFile).sendKeys(pic2Path);
		sleepInSecond(1);
		
		driver.findElement(uploadFile).sendKeys(pic3Path);
		sleepInSecond(1);
		
		//Verify that files are loaded successfully
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + pic1Name + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + pic2Name + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + pic3Name + "']")).isDisplayed());
		
		//Click upload for each file
		
		List<WebElement> startBtns = driver.findElements(By.cssSelector("tbody.files button.start"));
		for (WebElement e : startBtns) {
			e.click();
			sleepInSecond(1);
		}
		
		//Verify that files are uploaded successfully
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+pic1Name+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+pic2Name+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+pic3Name+"']")).isDisplayed());
		
		//Verify các hình upload lên là hình thực
		Assert.assertTrue(isImageLoaded("//a[@title='"+pic1Name+"']/img"));
		Assert.assertTrue(isImageLoaded("//a[@title='"+pic2Name+"']/img"));
		Assert.assertTrue(isImageLoaded("//a[@title='"+pic3Name+"']/img"));
	}

	@Test	
	public void TC_02_Multiple_File_Per_Time() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		By uploadFile = By.xpath("//input[@type='file']");
		
		driver.findElement(uploadFile).sendKeys(pic1Path + "\n" + pic2Path + "\n" + pic3Path);
		sleepInSecond(1);		
		
		//Verify that files are loaded successfully
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + pic1Name + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + pic2Name + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + pic3Name + "']")).isDisplayed());
		
		//Click upload for each file		
		List<WebElement> startBtns = driver.findElements(By.cssSelector("tbody.files button.start"));
		for (WebElement e : startBtns) {
			e.click();
			sleepInSecond(3);
		}
		
		//Verify that files are uploaded successfully
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+pic1Name+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+pic2Name+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+pic3Name+"']")).isDisplayed());
		
		//Verify các hình upload lên là hình thực
		Assert.assertTrue(isImageLoaded("//a[@title='"+pic1Name+"']/img"));
		Assert.assertTrue(isImageLoaded("//a[@title='"+pic2Name+"']/img"));
		Assert.assertTrue(isImageLoaded("//a[@title='"+pic3Name+"']/img"));
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
	
	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) js.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
				getElement(locator));
		return status;
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}
}
