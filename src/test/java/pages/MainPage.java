package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы
    private final By orderButtonTop = By.className("Button_Button__ra12g");
    private final By orderButtonBottom = By.xpath("(//button[contains(@class, 'Button_Button__ra12g') and contains(text(), 'Заказать')])[2]");
    private final By accordionButton = By.id("accordion__heading-0");
    private final By accordionText = By.id("accordion__panel-0");

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

    public void clickAccordionButton() {
        clickWithScroll(accordionButton);
    }

    public String getAccordionText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(accordionText)).getText();
    }

    public boolean isAccordionTextDisplayed() {
        try {
            return driver.findElement(accordionText).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void clickWithScroll(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
