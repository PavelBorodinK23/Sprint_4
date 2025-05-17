package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.MainPage;
import pages.OrderPage;
import utils.TestData;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;
    private final String browser;
    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String comment;
    private final boolean fromTopButton;

    public OrderTest(String browser, String name, String surname, String address, String metro,
                     String phone, String date, String comment, boolean fromTopButton) {
        this.browser = browser;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.comment = comment;
        this.fromTopButton = fromTopButton;
    }

    @Parameterized.Parameters(name = "Браузер: {0}, Данные: {1} {2}, Точка входа: {8}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"chrome", TestData.getOrderData()[0][0], TestData.getOrderData()[0][1],
                        TestData.getOrderData()[0][2], TestData.getOrderData()[0][3],
                        TestData.getOrderData()[0][4], TestData.getOrderData()[0][5],
                        TestData.getOrderData()[0][6], true},

                {"chrome", TestData.getOrderData()[1][0], TestData.getOrderData()[1][1],
                        TestData.getOrderData()[1][2], TestData.getOrderData()[1][3],
                        TestData.getOrderData()[1][4], TestData.getOrderData()[1][5],
                        TestData.getOrderData()[1][6], false},

                {"firefox", TestData.getOrderData()[0][0], TestData.getOrderData()[0][1],
                        TestData.getOrderData()[0][2], TestData.getOrderData()[0][3],
                        TestData.getOrderData()[0][4], TestData.getOrderData()[0][5],
                        TestData.getOrderData()[0][6], true},

                {"firefox", TestData.getOrderData()[1][0], TestData.getOrderData()[1][1],
                        TestData.getOrderData()[1][2], TestData.getOrderData()[1][3],
                        TestData.getOrderData()[1][4], TestData.getOrderData()[1][5],
                        TestData.getOrderData()[1][6], false}
        });
    }

    @BeforeClass
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @Before
    public void setUp() {
        switch (browser.toLowerCase()) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "chrome":
            default:
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);
    }

    @Test
    public void testOrderFlow() {
        if (fromTopButton) {
            mainPage.clickOrderButtonTop();
        } else {
            mainPage.clickOrderButtonBottom();
        }

        orderPage.fillOrderFormFirstPage(name, surname, address, metro, phone);
        orderPage.fillOrderFormSecondPage(date, comment);
        orderPage.confirmOrder();

        assertTrue("Окно успешного заказа не отображается", orderPage.isOrderSuccess());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
