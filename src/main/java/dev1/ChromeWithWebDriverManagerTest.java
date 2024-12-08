package dev1;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeWithWebDriverManagerTest {
	WebDriver driver;
    ExtentReports extent;
    ExtentTest test;
    @BeforeTest
    public void setup() {
    	 // Generate a unique report name using a timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/ExtentReport_" + timestamp + ".html";

        // Initialize ExtentReports
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);
        WebDriverManager.edgedriver().setup();
		//System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        driver = new EdgeDriver();
    }
    @Test
    public void checkTheUrl() {
        test = extent.createTest("checkTheUrl").info("Starting the test");

        try {
            // Navigate to URL
            driver.get("https://ecommerce-playground.lambdatest.io/");
            test.info("Navigated to URL").addScreenCaptureFromPath(captureScreenshot("navigateToURL"));

            // Verify URL
            String url = driver.getCurrentUrl();
            test.info("Captured current URL").addScreenCaptureFromPath(captureScreenshot("currentURL"));

            assertTrue(url.contains("lambdatest"), "URL does not contain 'lambdatest'");
            test.pass("URL contains 'lambdatest'").addScreenCaptureFromPath(captureScreenshot("urlCheckPassed"));
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage()).addScreenCaptureFromPath(captureScreenshot("testFailure"));
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
        extent.flush(); // Generate the report
    }

    public String captureScreenshot(String stepName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + stepName + "_" + timestamp + ".png";

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(screenshotPath));
        } catch (IOException e) {
            test.warning("Failed to capture screenshot: " + e.getMessage());
        }
        return screenshotPath;
    }
}