package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.MainPage;

import java.time.Duration;

import static org.junit.Assert.*;

public class AccordionTest {
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        // Настройка драйвера Chrome
        WebDriverManager.chromedriver().browserVersion("136.0.7103.93").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Устанавливаем неявное ожидание
        driver.get("https://qa-scooter.praktikum-services.ru/"); // Переход на главную страницу
        mainPage = new MainPage(driver); // Инициализация страницы
    }

    @Test
    public void testAccordionOpensText() {
        try {
            mainPage.clickAccordionButton(); // Кликаем по кнопке аккордеона
            assertTrue("Текст не отображается после клика", mainPage.isAccordionTextDisplayed()); // Проверяем отображение текста
        } catch (Exception e) {
            fail("Тест упал с исключением: " + e.getMessage()); // Обработка исключений
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Закрываем драйвер после теста
        }
    }
}
