import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebFormTest {
    WebDriver driver;

    @BeforeAll
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void formTest() throws InterruptedException {
        driver.get("https://www.digitalunite.com/practice-webform-learners");
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();

        driver.findElement(By.id("edit-name")).sendKeys("Tama Shil");
        driver.findElement(By.id("edit-number")).sendKeys("01922206611");
        Utils.scroll(driver, 600);

        WebElement dateInput = driver.findElement(By.id("edit-date"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", dateInput);
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        String currentDate = dateFormat.format(date);

        driver.findElement(By.id("edit-date")).sendKeys(currentDate, Keys.ENTER);
        driver.findElement(By.id("edit-email")).sendKeys("tamashil124@gmail.com");
        driver.findElement(By.id("edit-tell-us-a-bit-about-yourself-")).sendKeys("Hi! I'm Tama Shil. I'm currently enhancing my knowledge in SDET and thoroughly enjoying the learning process.");

        Utils.scroll(driver, 600);
        File file =new File("./src/test/resources/SamplePDF.pdf");
        driver.findElement(By.id("edit-uploadocument-upload")).sendKeys(file.getAbsolutePath());
        Thread.sleep(3000);
        driver.findElement(By.id("edit-age")).click();
        driver.findElement(By.id("edit-submit")).click();

        String actualText = driver.findElement(By.tagName("h1")).getText();
        String expectedText = "Thank you for your submission!";
        Assertions.assertEquals(actualText, expectedText);

    }

    @AfterAll
    public void closeDriver() {
    //  driver.quit();

    }
}
