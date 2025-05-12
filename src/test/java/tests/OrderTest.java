package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.MainPage;
import pages.OrderPage;
import utils.TestData;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;

    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String comment;
    private final boolean fromTopButton;

    public OrderTest(String name, String surname, String address, String metro,
                     String phone, String date, String comment, boolean fromTopButton) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.comment = comment;
        this.fromTopButton = fromTopButton;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}, {1}, точка входа: {7}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {TestData.getOrderData()[0][0].toString(), TestData.getOrderData()[0][1].toString(),
                        TestData.getOrderData()[0][2].toString(), TestData.getOrderData()[0][3].toString(),
                        TestData.getOrderData()[0][4].toString(), TestData.getOrderData()[0][5].toString(),
                        TestData.getOrderData()[0][6].toString(), true},

                {TestData.getOrderData()[1][0].toString(), TestData.getOrderData()[1][1].toString(),
                        TestData.getOrderData()[1][2].toString(), TestData.getOrderData()[1][3].toString(),
                        TestData.getOrderData()[1][4].toString(), TestData.getOrderData()[1][5].toString(),
                        TestData.getOrderData()[1][6].toString(), false}
        });
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().browserVersion("136.0.7103.93").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);
    }

    @Test
    public void testOrderFlow() {
        try {
            if (fromTopButton) {
                mainPage.clickOrderButtonTop();
            } else {
                mainPage.clickOrderButtonBottom();
            }

            orderPage.fillOrderFormFirstPage(name, surname, address, metro, phone);
            orderPage.fillOrderFormSecondPage(date, comment);
            orderPage.confirmOrder();

            assertTrue("Окно успешного заказа не отображается", orderPage.isOrderSuccess());
        } catch (Exception e) {
            fail("Тест упал с исключением: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
