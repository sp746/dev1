package dev1;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeWithWebDriverManagerTest {
    WebDriver driver;
    @BeforeTest
    public void setup() {
        WebDriverManager.edgedriver().setup();
		//System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        driver = new EdgeDriver();
    }
    
    @Test
    void checkTheUrl() {

        driver.get("https://ecommerce-playground.lambdatest.io/");
        String url = driver.getCurrentUrl();
        assertTrue(url.contains("lambdatest"));
    }
    @AfterTest
    void tearDown() {
        driver.quit();
    }
}