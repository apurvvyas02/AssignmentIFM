package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestAssignment {

	static WebDriver driver;

	@Test(priority = 1)
	public void verifyNavToHomePage() throws InterruptedException {
		System.setProperty("webdriver.Chrome.Driver", "chromedriver.exe");
		driver = new ChromeDriver();

		driver.get("http://www.way2automation.com/angularjs-protractor/webtables/");
		String pageTitle = driver.getTitle();
		System.out.println(pageTitle);
		driver.manage().window().maximize();
		// Check page title
		assertEquals("Protractor practice website - WebTables", pageTitle);
	}

	@Test(priority = 2)
	public void checkTabDetails() {
		String tableBody = driver.findElement(By.xpath("//table/tbody")).getText();

		// Verify table body is not null
		assertNotNull("Not Null", tableBody);
		assertNotEquals("Not Blank", " ", tableBody);

		String colName = driver.findElement(By.xpath(
				"//table[@rows='rowCollection']/thead/tr[@class='smart-table-header-row']//th//span[text()='User Name']"))
				.getText();
		System.out.println(colName);

		// fetch actual username of row first and second
		String actualValueFirstRowUname = driver.findElement(By.xpath("//table/tbody/tr[1]/td[3]")).getText();
		String actualValueSecondRowUname = driver.findElement(By.xpath("//table/tbody/tr[2]/td[3]")).getText();

		// Check mat is displayed in first row and sale is displayed in second row
		assertEquals("matt", actualValueFirstRowUname);
		assertEquals("sale", actualValueSecondRowUname);

		// Check all details of first and second row
		String firstRowDetails = driver.findElement(By.xpath("//table/tbody/tr[1]")).getText();
		String secondRowDetails = driver.findElement(By.xpath("//table/tbody/tr[2]")).getText();

		System.out.println(firstRowDetails);
		System.out.println(secondRowDetails);

		// Verify the email and phone number and check box for first and second row

		for (int i = 1; i <= 2; i++) {

			String email = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[7]")).getText();
			String phoneNumber = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[8]")).getText();
			long number = Long.parseLong(phoneNumber);
			boolean isDisabled = driver
					.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[9]/input[@name='IsLocked']")).isEnabled();
			System.out.println(isDisabled);
			if (i == 1) {
				assertEquals("matt@comp.com", email);
				assertEquals(111222333, number);
				assertEquals(false, isDisabled);
				System.out.println("**** In IF");

			} else if (i == 2) {
				assertEquals("sales@comp.com", email);
				assertEquals(777111223, number);
				assertEquals(false, isDisabled);
				boolean isSelected = driver
						.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[9]/input[@name='IsLocked']"))
						.isSelected();
				assertEquals(false, isSelected);
				System.out.println("In else if");
			}
		}
	}

	// Sort the first name column and verify sorting
	@Test(priority = 3)
	public void sortFirstCol() throws InterruptedException {
		// Sort the first name column

		int rowCount = driver.findElements(By.xpath("//table/tbody/tr")).size();

		List<String> listBeforeSort = new ArrayList<String>();

		for (int j = 1; j <= rowCount; j++) {

			listBeforeSort.add(driver.findElement(By.xpath("//table/tbody/tr[" + j + "]/td[1]")).getText());

		}
		System.out.println("List before Sort " + listBeforeSort);

		driver.findElement(By.xpath("//table/thead//tr//span[text()='First Name']")).click();
		Thread.sleep(2000);
		List<String> listAfterSort = new ArrayList<String>();

		for (int i = 1; i <= rowCount; i++) {

			listAfterSort.add(driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[1]")).getText());

		}
		System.out.println("List After Click -and Sort " + listAfterSort);
		// Sort the first list - listBeforeSort
		listBeforeSort.sort(String.CASE_INSENSITIVE_ORDER);
		System.out.println("Case insensitive sort " + listBeforeSort);

		Collections.reverse(listBeforeSort);

		System.out.println("list before sort" + listBeforeSort);
		System.out.println("****** Assert and compare list");
		if (listBeforeSort.equals(listAfterSort)) {
			System.out.println("List is Sorted");
		} else {
			System.out.println("List is not sorted");
		}
	}

	// Check Email Domain
	@Test(priority = 4)
	public void checkMaxEmailDomain() {
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		int rowCount = driver.findElements(By.xpath("//table/tbody/tr")).size();
		for (int i = 1; i <= rowCount; i++) {

			String abc = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[7]")).getText();
			String arr[] = abc.split("@");
			System.out.println(arr[1]);
			if (hmap.containsKey(arr[1])) {
				hmap.put(arr[1], hmap.get(arr[1]) + 1);
			} else {
				hmap.put(arr[1], 1);
			}

		}
		int maxValue = Collections.max(hmap.values());
		Iterator<Entry<String, Integer>> emailDomain = hmap.entrySet().iterator();

		while (emailDomain.hasNext()) {
			if (maxValue == emailDomain.next().getValue()) {

				String domain = emailDomain.next().getKey();
				System.out.println("*Domain* \t" + domain + "\t  Value \t" + maxValue);
			}
		}
	}

	@Test(priority = 4)
	public void delSecondRow() throws InterruptedException {
		// Delete second Row
		String actualValAftDelSecRowUnm = driver.findElement(By.xpath("//table/tbody/tr[2]/td[3]")).getText();
		System.out.println(actualValAftDelSecRowUnm);
		driver.findElement(By.xpath("//table/tbody/tr[2]/td[11]/button/i")).click();
		driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();
		int rowCount = driver.findElements(By.xpath("//table/tbody/tr")).size();
		int rowCountAfterDel = driver.findElements(By.xpath("//table/tbody/tr")).size();
		Thread.sleep(2500);
		if (rowCount != rowCountAfterDel) {
			System.out.println("Count Validation - Second row deleted successfully");
			assertNotEquals("admin", actualValAftDelSecRowUnm);
			System.out.println("sale username doesn't exists");
		}

		// Refresh the page and verify the deleted record is visible
		driver.navigate().refresh();

		// assertEquals("admin", actualValueSecondRowUname);
		int rowCountAfterRefresh = driver.findElements(By.xpath("//table/tbody/tr")).size();
		if (rowCountAfterRefresh == rowCount) {
			System.out.println("detail Successfully displayed");
		}
	}

	// Edit details of a customer with username "testadmin"

	@Test(priority = 5)
	public void editTestAdmin() throws InterruptedException {
		WebElement testadminTr = driver
				.findElement(By.xpath("//table/tbody//td[contains(text(),'testadmin')]/ancestor::tr"));
		Thread.sleep(2500);
		System.out.println("Tr text:" + testadminTr.getText());
		testadminTr
				.findElement(
						By.xpath("//table/tbody//td[contains(text(),'testadmin')]/ancestor::tr//button[@type='edit']"))
				.click();

		WebElement fName = driver.findElement(By.xpath("//input[@name='FirstName']"));
		WebElement lName = driver.findElement(By.xpath("//input[@name='LastName']"));
		System.out.println("FName:" + fName.getText());

		fName.clear();
		fName.sendKeys("Apurv");
		lName.clear();
		lName.sendKeys("Vyas");
		Thread.sleep(25000);
		Select s = new Select(driver.findElement(By.name("RoleId")));
		s.selectByValue("2");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();
		Thread.sleep(2000);
		String firstName = driver.findElement(By.xpath("//table/tbody/tr[4]/td[1]")).getText();
		String lastName = driver.findElement(By.xpath("//table/tbody/tr[4]/td[2]")).getText();
		String role = driver.findElement(By.xpath("//table/tbody/tr[4]/td[6]")).getText();

		assertEquals("Apurv", firstName);
		assertEquals("Vyas", lastName);
		assertEquals("Admin", role);
	}

	// Edit and Delete Column name is empty
	@Test(priority = 6)
	public void verifyEditandDelCol() {

		String editCol = driver.findElement(By.xpath("//table/thead/tr[3]/th[10]")).getText();
		String delCol = driver.findElement(By.xpath("//table/thead/tr[3]/th[11]")).getText();

		assertEquals("", editCol);
		assertEquals("", delCol);
	}

	// Edit Links are not broken
	@Test(priority = 7)
	public void editBrokenLinks() throws InterruptedException {
		int rowCount = driver.findElements(By.xpath("//table/tbody/tr")).size();
		for (int i = 1; i <= rowCount; i++) {
			driver.findElement(By.xpath("//table/tbody/tr[" + i + "]//button[@type='edit']")).click();
			try {
				WebElement modalPop = driver
						.findElement(By.xpath("//div[contains(@class,'modal') and contains(@class,'ng-scope')]"));
				if (!modalPop.isDisplayed())
					System.out.println("Edit link brokem for Row:" + i);
				else {
					System.out.println("In Else");
					Thread.sleep(2000);
					modalPop.findElement(By.xpath("//button[contains(@class,'btn-danger')]")).click();
				}
			} catch (NoSuchElementException e) {
				System.out.println("Edit link brokem for Row:" + i);
			}
		}
	}

	// Store all rows in list
	@Test(priority = 8)
	public void storeList() {

		List<String> userList = new ArrayList<String>();
		int rowCount = driver.findElements(By.xpath("//table/tbody/tr")).size();
		for (int j = 1; j < rowCount; j++) {

			userList.add(driver.findElement(By.xpath("//table/tbody/tr[" + j + "]")).getText());
		}
		for (String string : userList) {
			System.out.println(string);
		}

	}

}