package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

public class Setup {
    public static WebDriver driver;

    @BeforeTest
    public void  setUp()
    {
        System.setProperty("webdriver.chrome.driver","./src/test/resources/chromedriver.exe");
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--headed");
        driver=new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://automationpractice.com/");
    }

    public void getLink()
    {
        driver.get("http://automationpractice.com/");
    }

    @AfterTest
    public  void closeDriver()
    {
        driver.quit();
    }
}