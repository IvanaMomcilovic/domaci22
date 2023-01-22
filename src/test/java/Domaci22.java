import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import com.github.javafaker.Faker;

public class Domaci22 {
    /*Testirati na stranici https://vue-demo.daniel-avellaneda.com login stranicu.
Test 1: Verifikovati da se u url-u stranice javlja ruta "/login".
        Verifikovati da atribut type u polju za unos email ima vrednost "email"
        i za password da ima atribut type "password.
Test 2: Koristeci Faker uneti nasumicno generisan email i password
        i verifikovati da se pojavljuje poruka "User does not exist".
Test 3: Verifikovati da kad se unese admin@admin.com (sto je dobar email) i pogresan password (generisan faker-om),
 da se pojavljuje poruka "Wrong password"

Koristiti TestNG i dodajte before i after class metode.
Domaci se kači na github.
*/
    private WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "C:\\IVANA\\KURS\\drajverZaSelenium\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @BeforeMethod
    public void beforeTest() {
        driver.get("https://vue-demo.daniel-avellaneda.com");
        driver.manage().window().maximize();
    }

    @Test
    public void testUrl() throws InterruptedException {
        WebElement logIn = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/header/div/div[3]/a[3]"));
        logIn.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String expectedUrl = "https://vue-demo.daniel-avellaneda.com/login";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);

        WebElement emailPolje = driver.findElement(By.name("email"));
        String emailTip = emailPolje.getAttribute("type");
        Assert.assertEquals("email", emailTip);

        WebElement passwordPolje = driver.findElement(By.name("password"));
        String passwordTip = passwordPolje.getAttribute("type");
        Assert.assertEquals("password", passwordTip);
    }

    @Test
    public void testMessage() throws InterruptedException {
        Faker faker = new Faker();
        WebElement imejl = driver.findElement(By.name("email"));
        imejl.sendKeys(faker.internet().emailAddress());


        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(faker.internet().password());


        String expectedMessage = "User does not exist";
        WebElement messagePolje = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li"));
        String actualMessage = messagePolje.getText();
        Assert.assertEquals(actualMessage, expectedMessage);
    }

        @Test
        public void testMessageWrongPass () {
            Faker faker = new Faker();
            WebElement imejl = driver.findElement(By.name("email"));
            imejl.sendKeys("admin@admin.com");


           WebElement password = driver.findElement(By.id("password"));
            password.sendKeys(faker.internet().password());


          String  expectedMessage = "Wrong password";
           WebElement messagePolje = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li"));
            String actualMessage = messagePolje.getText();
            Assert.assertEquals(actualMessage, expectedMessage);
        }


    @AfterMethod
    public void afterMethod() {

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}


