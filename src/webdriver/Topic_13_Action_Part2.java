package webdriver;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Action_Part2 {
	WebDriver driver;
	Actions action;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String dragAndDropHelperPath = projectPath + "\\dragAndDrop\\drag_and_drop_helper.js";

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		}

		driver = new FirefoxDriver();
		//driver = new ChromeDriver();
		action = new Actions(driver);
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void TC_01_ClickAndHold_Block() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		List<WebElement> listNumb = driver.findElements(By.cssSelector("ol#selectable>li"));
		action.clickAndHold(listNumb.get(0)).moveToElement(listNumb.get(3)).release().perform();
		sleepInSecond(2);

		List<WebElement> listSelectedNumb = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(listSelectedNumb.size(), 4);
	}

	public void TC_02_ClickAndSelect_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");

		// In both MAC and Windows
		Keys key = null;
		if (osName.contains("Windows")) {
			key = Keys.CONTROL;
		} else
			key = Keys.COMMAND;
		List<WebElement> listNumb = driver.findElements(By.cssSelector("ol#selectable>li"));

		// Hold Ctrl
		action.keyDown(key).perform();

		// Click choose random number
		action.click(listNumb.get(0)).click(listNumb.get(3)).click(listNumb.get(7)).perform();

		// Release Ctrl
		action.keyUp(key);

		sleepInSecond(2);
		List<WebElement> listSelectedNumb = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(listSelectedNumb.size(), 3);
	}

	public void TC_03_DoubleClick() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		// Scroll element into view
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath("//button[text()='Double click me']")));
		sleepInSecond(1);

		action.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
		Assert.assertEquals(driver.findElement(By.cssSelector("p#demo")).getText(), "Hello Automation Guys!");
	}

	public void TC_04_RightClick() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		action.contextClick(driver.findElement(By.cssSelector("span.context-menu-one"))).perform();
		sleepInSecond(3);

		Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-quit")).isDisplayed());
		action.moveToElement(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
		sleepInSecond(3);

		Assert.assertTrue(
				driver.findElement(By.cssSelector("li.context-menu-icon-quit.context-menu-visible.context-menu-hover"))
						.isDisplayed());
		action.click(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
		sleepInSecond(3);
		driver.switchTo().alert().accept();
		Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-quit")).isDisplayed());
	}

	
	public void TC_05_DragAndDropElement_HTML4() {
		driver.get("https://automationfc.github.io/kendo-drag-drop/");
		WebElement smallCircle = driver.findElement(By.cssSelector("div#draggable"));
		WebElement bigCircle = driver.findElement(By.cssSelector("div#droptarget"));

		action.dragAndDrop(smallCircle, bigCircle).perform();
		sleepInSecond(3);

		// Verify text
		Assert.assertEquals(bigCircle.getText(), "You did great!");

		// Verify color
		String bigCircleBackgroundColor = bigCircle.getCssValue("background-color");
		System.out.println(bigCircleBackgroundColor);

		Assert.assertEquals(Color.fromString(bigCircleBackgroundColor).asHex().toUpperCase(), "#03A9F4");
	}

	
	public void TC_06_DragAndDropElement_HTML5() throws InterruptedException, IOException {
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		// driver.get("http://the-internet.herokuapp.com/drag_and_drop");

		String sourceCss = "#column-a";
		String targetCss = "#column-b";

		String jsHelper = getContentFile(dragAndDropHelperPath);

		// Inject Jquery lib to site
		// String jqueryscript = readFile(jqueryPath);
		// javascriptExecutor.executeScript(jqueryscript);

		// A to B
		jsHelper = jsHelper + "$(\"" + sourceCss + "\").simulateDragDrop({ dropTarget: \"" + targetCss + "\"});";
		jsExecutor.executeScript(jsHelper);
		sleepInSecond(3);
		Assert.assertTrue(isElementDisplayed("//div[@id='column-a']/header[text()='B']"));

		// B to A
		jsExecutor.executeScript(jsHelper);
		sleepInSecond(3);
		Assert.assertTrue(isElementDisplayed("//div[@id='column-b']/header[text()='B']"));
	}
	
	@Test
	public void TC_07_DragAndDropElement_HTML5() throws AWTException {
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		dragAndDropHTML5ByXpath("//div[@id='column-a']", "//div[@id='column-b']");
		Assert.assertTrue(isElementDisplayed("//div[@id='column-a']/header[text()='B']"));
		Assert.assertTrue(isElementDisplayed("//div[@id='column-b']/header[text()='A']"));
		
		dragAndDropHTML5ByXpath("//div[@id='column-b']", "//div[@id='column-a']");
		Assert.assertTrue(isElementDisplayed("//div[@id='column-a']/header[text()='B']"));
		Assert.assertTrue(isElementDisplayed("//div[@id='column-b']/header[text()='A']"));
		
	}
		
	public String getContentFile(String filePath) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(filePath);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}

	public boolean isElementDisplayed(String xpath) {
		if (driver.findElement(By.xpath(xpath)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public void dragAndDropHTML5ByXpath(String sourceLocator, String targetLocator) throws AWTException {

		WebElement source = driver.findElement(By.xpath(sourceLocator));
		WebElement target = driver.findElement(By.xpath(targetLocator));

		// Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(500);

		// Get size of elements
		Dimension sourceSize = source.getSize();
		Dimension targetSize = target.getSize();

		// Get center distance
		int xCentreSource = sourceSize.width / 2;
		int yCentreSource = sourceSize.height / 2;
		int xCentreTarget = targetSize.width / 2;
		int yCentreTarget = targetSize.height / 2;

		Point sourceLocation = source.getLocation();
		Point targetLocation = target.getLocation();

		// Make Mouse coordinate center of element
		sourceLocation.x += 20 + xCentreSource;
		sourceLocation.y += 110 + yCentreSource;
		targetLocation.x += 20 + xCentreTarget;
		targetLocation.y += 110 + yCentreTarget;

		// Move mouse to drag from location
		robot.mouseMove(sourceLocation.x, sourceLocation.y);

		// Click and drag
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

		// Move to final position
		robot.mouseMove(targetLocation.x, targetLocation.y);

		// Drop
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
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
