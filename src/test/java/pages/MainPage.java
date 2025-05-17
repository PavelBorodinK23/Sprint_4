package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы
    private final By orderButtonTop = By.className("Button_Button__ra12g");
    private final By orderButtonBottom = By.xpath("(//button[contains(@class, 'Button_Button__ra12g') and contains(text(), 'Заказать')])[2]");
    private final By accordionHeading = By.cssSelector("[data-accordion-component='AccordionItemHeading']");
    private final By accordionPanel = By.cssSelector("[data-accordion-component='AccordionItemPanel']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickOrderButtonTop() {
        clickWithScroll(orderButtonTop);
    }

    public void clickOrderButtonBottom() {
        clickWithScroll(orderButtonBottom);
    }

    /**
     * Кликает по кнопке аккордеона с указанным индексом
     * @param index индекс элемента аккордеона (начиная с 0)
     */
    public void clickAccordionButton(int index) {
        List<WebElement> items = driver.findElements(accordionHeading);
        if (index < items.size()) {
            clickWithScroll(items.get(index));
        } else {
            throw new IndexOutOfBoundsException("Аккордеон с индексом " + index + " не найден");
        }
    }

    /**
     * Возвращает текст ответа аккордеона с указанным индексом
     * @param index индекс элемента аккордеона (начиная с 0)
     * @return текст ответа
     */
    public String getAccordionText(int index) {
        List<WebElement> panels = driver.findElements(accordionPanel);
        if (index < panels.size()) {
            return wait.until(ExpectedConditions.visibilityOf(panels.get(index))).getText();
        }
        throw new IndexOutOfBoundsException("Панель аккордеона с индексом " + index + " не найдена");
    }

    /**
     * Проверяет, отображается ли текст ответа аккордеона
     * @param index индекс элемента аккордеона
     * @return true если текст отображается, false в противном случае
     */
    public boolean isAccordionTextDisplayed(int index) {
        List<WebElement> panels = driver.findElements(accordionPanel);
        if (index < panels.size()) {
            return panels.get(index).isDisplayed();
        }
        return false;
    }

    private void clickWithScroll(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    private void clickWithScroll(WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
