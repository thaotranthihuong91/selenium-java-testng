package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Textbox_TextArea {
	WebDriver driver;
	Random rand = new Random();
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String firstName, lastName, editFirstName, editLastName, passportNumber, commentInput;
	String employeeID = String.valueOf(rand.nextInt(99999));

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		firstName = "Automation";
		lastName = "FC";
		editFirstName = "Mohammed";
		editLastName = "Salah";
		passportNumber = "40517-402-96-3455";
		commentInput = "Thao\nTest";

	}

	@Test
	public void TC_01_Textbox_TextArea() {
		// Step1
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		// Step2: Login
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Admin");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		sleepInSecond(5);

		// Step3
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		// Step4
		driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
		sleepInSecond(3);

		// Step5,6: Nhập thông tin trong màn hình Add Employee và Save
		driver.findElement(By.name("firstName")).sendKeys(firstName);
		driver.findElement(By.name("lastName")).sendKeys(lastName);

		WebElement employeeIdTxtbox = driver
				.findElement(By.xpath("//label[text()='Employee Id']//parent::div//following-sibling::div/input"));
		employeeIdTxtbox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		sleepInSecond(1);
		employeeIdTxtbox.sendKeys(Keys.DELETE);
		sleepInSecond(1);
		employeeIdTxtbox.sendKeys(employeeID);
		driver.findElement(By.xpath("//p[text()='Create Login Details']//parent::div//span")).click();
		sleepInSecond(5);

		driver.findElement(By.xpath("//label[text()='Username']//parent::div//following-sibling::div//input"))
				.sendKeys("afc" + employeeID);
		driver.findElement(By.xpath("//label[text()='Password']//parent::div//following-sibling::div//input"))
				.sendKeys("Password123!!!");
		driver.findElement(By.xpath("//label[text()='Confirm Password']//parent::div//following-sibling::div//input"))
				.sendKeys("Password123!!!");
		driver.findElement(By.xpath("//button[text()=' Save ']")).click();
		sleepInSecond(8);

		// Step7
		Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"), lastName);
		Assert.assertEquals(
				driver.findElement(By.xpath("//label[text()='Employee Id']//parent::div//following-sibling::div/input"))
						.getAttribute("value"),
				employeeID);

		// Step8,9
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		sleepInSecond(5);

		// Step10
		driver.findElement(By.xpath("//h6[text()='Assigned Immigration Records']//following-sibling::button")).click();
		driver.findElement(By.xpath("//label[text()='Number']//parent::div//following-sibling::div/input"))
				.sendKeys(passportNumber);
		driver.findElement(By.xpath("//label[text()='Comments']//parent::div//following-sibling::div/textarea"))
				.sendKeys(commentInput);
		driver.findElement(By.xpath("//button[contains(string(),'Save')]")).click();
		sleepInSecond(6);

		// Step11
		driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
		sleepInSecond(3);

		// Step12
		Assert.assertEquals(
				driver.findElement(By.xpath("//label[text()='Number']//parent::div//following-sibling::div/input"))
						.getAttribute("value"),
				passportNumber);
		Assert.assertEquals(
				driver.findElement(By.xpath("//label[text()='Comments']//parent::div//following-sibling::div/textarea"))
						.getAttribute("value"),
				commentInput);

		// Step14
		driver.findElement(By.cssSelector("p.oxd-userdropdown-name")).click();
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
		sleepInSecond(3);

		// Step15
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("afc" + employeeID);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Password123!!!");
		driver.findElement(By.xpath("//button[contains(string(),'Login')]")).click();
		sleepInSecond(5);

		// Step16
		driver.findElement(By.xpath("//span[text()='My Info']")).click();
		sleepInSecond(5);

		// Step17
		Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"), lastName);
		Assert.assertEquals(
				driver.findElement(By.xpath("//label[text()='Employee Id']//parent::div//following-sibling::div/input"))
						.getAttribute("value"),
				employeeID);

		// Step18
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		sleepInSecond(5);
		driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
		sleepInSecond(3);

		Assert.assertEquals(
				driver.findElement(By.xpath("//label[text()='Number']//parent::div//following-sibling::div/input"))
						.getAttribute("value"),
				passportNumber);
		Assert.assertEquals(
				driver.findElement(By.xpath("//label[text()='Comments']//parent::div//following-sibling::div/textarea"))
						.getAttribute("value"),
				commentInput);

	}

	@AfterClass
	public void afterClass() {
		 driver.quit();
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
