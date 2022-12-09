package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Browser_Element_B3 {
	// Khai báo
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

		// Khởi tạo
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void TC_01_CheckElementIsDisplayed() {
		// Nếu element hiển thị thì isDisplayed (Visible) trả về giá trị true
		// NNếu element KHÔNG hiển thị thì isDisplayed trả về giá trị false
		driver.get("https://automationfc.github.io/basic-form/index.html");
		WebElement emailTxtBox = driver.findElement(By.xpath("//input[@id='mail']"));
		WebElement eduTxtArea = driver.findElement(By.xpath("//textarea[@id='edu']"));
		WebElement ageUnder18RadioBtn = driver.findElement(By.xpath("//input[@id='under_18']"));
		WebElement image5 = driver.findElement(By.xpath("//h5[text()='Name: User5']"));

		if (emailTxtBox.isDisplayed()) {
			System.out.println("Email input is displayed!");
			emailTxtBox.sendKeys("Automation Testing");
		} else {
			System.out.println("Email input is not displayed!");
		}

		if (eduTxtArea.isDisplayed()) {
			System.out.println("Education text area is displayed!");
			eduTxtArea.sendKeys("Automation Testing");
		} else {
			System.out.println("Education text area is not displayed!");
		}

		if (ageUnder18RadioBtn.isDisplayed()) {
			System.out.println("Age under 18 radio button is displayed!");
			ageUnder18RadioBtn.click();
		} else {
			System.out.println("Age under 18 radio button is not displayed!");
		}

		if (image5.isDisplayed()) {
			System.out.println("Image5 is displayed!");
		} else {
			System.out.println("Image5 is not displayed!");
		}

	}

	@Test
	public void TC_02_CheckElementIsEnabled() {
		// Enabled: Có thể tương tác được
		// Disabled: Không tương tác được = Read only
		// Phạm vi áp dụng: Tất cả các loại element
		driver.get("https://automationfc.github.io/basic-form/index.html");
		WebElement emailTxtBox = driver.findElement(By.xpath("//input[@id='mail']"));
		WebElement eduTxtArea = driver.findElement(By.xpath("//textarea[@id='edu']"));
		WebElement ageUnder18RadioBtn = driver.findElement(By.xpath("//input[@id='under_18']"));
		WebElement job1Dropdown = driver.findElement(By.cssSelector("select#job1"));
		WebElement job2Dropdown = driver.findElement(By.cssSelector("select#job2"));
		WebElement job3Dropdown = driver.findElement(By.cssSelector("select#job3"));
		WebElement developmentCheckbox = driver.findElement(By.cssSelector("input#development"));
		WebElement slider1 = driver.findElement(By.cssSelector("input#slider-1"));
		WebElement slider2 = driver.findElement(By.cssSelector("input#slider-2"));
		WebElement disablePass = driver.findElement(By.cssSelector("input#disable_password"));
		WebElement disableAge = driver.findElement(By.cssSelector("input#radio-disabled"));
		WebElement bioTextArea = driver.findElement(By.cssSelector("textarea#bio"));
		WebElement disabledCheckbox = driver.findElement(By.cssSelector("input#check-disbaled"));

		if (emailTxtBox.isEnabled()) {
			System.out.println("Email is enabled!");
		} else {
			System.out.println("Email is disabled!");
		}

		if (eduTxtArea.isEnabled()) {
			System.out.println("Education textarea is enabled!");
		} else {
			System.out.println("Education textarea is disabled!");
		}

		if (ageUnder18RadioBtn.isEnabled()) {
			System.out.println("Age under 18 radio button is enabled!");
		} else {
			System.out.println("Age under 18 radio button is disabled!");
		}

		if (job1Dropdown.isEnabled()) {
			System.out.println("Job1 dropdown is enabled!");
		} else {
			System.out.println("Job1 dropdown radio button is disabled!");
		}

		if (job2Dropdown.isEnabled()) {
			System.out.println("Job2 dropdown is enabled!");
		} else {
			System.out.println("Job2 dropdown radio button is disabled!");
		}

		if (developmentCheckbox.isEnabled()) {
			System.out.println("Development checkbox is enabled!");
		} else {
			System.out.println("Development checkbox button is disabled!");
		}

		if (slider1.isEnabled()) {
			System.out.println("Slider 01 is enabled!");
		} else {
			System.out.println("Slider 01 is disabled!");
		}

		if (disablePass.isEnabled()) {
			System.out.println("DisabledPassword textbox is enabled!");
		} else {
			System.out.println("DisabledPassword is disabled!");
		}

		if (disableAge.isEnabled()) {
			System.out.println("DisabledAge Radio button is enabled!");
		} else {
			System.out.println("DisabledAge Radio button is disabled!");
		}

		if (bioTextArea.isEnabled()) {
			System.out.println("Biography textarea is enabled!");
		} else {
			System.out.println("Biography textarea is disabled!");
		}

		if (job3Dropdown.isEnabled()) {
			System.out.println("Job3 dropdown is enabled!");
		} else {
			System.out.println("Job3 dropdown is disabled!");
		}

		if (disabledCheckbox.isEnabled()) {
			System.out.println("Disabled checkbox is enabled!");
		} else {
			System.out.println("Disabled checkbox is disabled!");
		}

		if (slider2.isEnabled()) {
			System.out.println("Slider 02 is enabled!");
		} else {
			System.out.println("Slider 02 is disabled!");
		}

	}

	@Test
	public void TC_03_CheckElementIsSelected() {
		// Đã được chọn hay chưa: Selected -> true
		// Chưa được chọn: Deselected -> false
		// Phạm vi áp dụng: Radio button/ Checkbox/ Dropdown (default)
		WebElement ageUnder18RadioBtn = driver.findElement(By.xpath("//input[@id='under_18']"));
		WebElement javaCheckbox = driver.findElement(By.cssSelector("input#java"));

		if (!ageUnder18RadioBtn.isSelected()) {
			ageUnder18RadioBtn.click();
			if (ageUnder18RadioBtn.isSelected()) {
				System.out.println("Age under 18 is selected!");
			} else {
				System.out.println("Age under 18 is de-selected!");
			}
		}

		if (!javaCheckbox.isSelected()) {
			javaCheckbox.click();
			if (javaCheckbox.isSelected()) {
				System.out.println("Java checkbox is selected!");
				javaCheckbox.click();
				if (javaCheckbox.isSelected()) {
					System.out.println("Java checkbox is selected!");
				} else {
					System.out.println("Java checkbox is not selected!");
				}
			} else {
				System.out.println("Java checkbox is de-selected!");
			}
		}

	}

	@Test
	public void TC_04_MailChimp() {
		driver.get("https://login.mailchimp.com/signup/");
		WebElement emailInput = driver.findElement(By.cssSelector("input#email"));
		WebElement passInput = driver.findElement(By.cssSelector("input#new_password"));

		emailInput.sendKeys("thaotth@gmail.com");
		sleepInSecond(3);

		// Check lowercase
		passInput.sendKeys("aaa");
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());

		// Check uppercase
		passInput.clear();
		passInput.sendKeys("AAA");
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());

		// Check number
		passInput.clear();
		passInput.sendKeys("123456");
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());

		// Check special character
		passInput.clear();
		passInput.sendKeys("@#$%");
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char completed']")).isDisplayed());

		// Check minimum 8 characters
		passInput.clear();
		passInput.sendKeys("aB1@");
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char completed']")).isDisplayed());

		// Full
		passInput.clear();
		passInput.sendKeys("ABCabc@@@123");
		sleepInSecond(2);
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='lowercase-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='uppercase-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='number-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='8-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='special-char completed']")).isDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
