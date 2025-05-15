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

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class AccordionTest {
    private WebDriver driver;
    private MainPage mainPage;
    private final String browser;
    private final int questionIndex;
    private final String expectedAnswer;

    public AccordionTest(String browser, int questionIndex, String expectedAnswer) {
        this.browser = browser;
        this.questionIndex = questionIndex;
        this.expectedAnswer = expectedAnswer;
    }

    @Parameterized.Parameters(name = "Браузер: {0}, Вопрос №{1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"chrome", 0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"chrome", 1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {"firefox", 0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"firefox", 1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."}
        });
    }

    @Before
    public void setUp() {
        // Настройка драйвера в зависимости от параметра
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new MainPage(driver);
    }

    @Test
    public void testAccordionItem() {
        mainPage.clickAccordionButton(questionIndex);
        String actualAnswer = mainPage.getAccordionText(questionIndex);
        assertEquals("Неверный текст ответа для вопроса " + questionIndex,
                expectedAnswer, actualAnswer);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
