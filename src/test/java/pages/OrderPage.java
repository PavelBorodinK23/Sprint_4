package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы
    private final By nameField = By.xpath("//input[@placeholder='* Имя']");
    private final By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.xpath("//input[@placeholder='* Станция метро']");
    private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath("//button[text()='Далее']");
    private final By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodField = By.className("Dropdown-arrow");
    private final By rentalPeriodOption = By.xpath("//div[text()='сутки']");
    private final By blackPearlCheckbox = By.id("black");
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//button[contains(@class, 'Button_Middle__1CSJM') and text()='Заказать']");
    private final By confirmButton = By.xpath("//button[text()='Да']");
    private final By successModal = By.className("Order_ModalHeader__3FDaJ");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void fillOrderFormFirstPage(String name, String surname, String address, String metro, String phone) {
        waitAndSendKeys(nameField, name);
        waitAndSendKeys(surnameField, surname);
        waitAndSendKeys(addressField, address);

        // Особое заполнение для поля метро
        WebElement metroElement = wait.until(ExpectedConditions.elementToBeClickable(metroField));
        metroElement.sendKeys(metro);
        String metroOptionXpath = String.format("//div[text()='%s']", metro);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(metroOptionXpath))).click();

        waitAndSendKeys(phoneField, phone);
        clickWithScroll(nextButton);
    }

    public void fillOrderFormSecondPage(String date, String comment) {
        waitAndSendKeys(dateField, date);

        clickWithScroll(rentalPeriodField);
        clickWithScroll(rentalPeriodOption);

        clickWithScroll(blackPearlCheckbox);

        waitAndSendKeys(commentField, comment);

        clickWithScroll(orderButton);
    }

    public void confirmOrder() {
        clickWithScroll(confirmButton);
    }

    public boolean isOrderSuccess() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successModal)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void waitAndSendKeys(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    private void clickWithScroll(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
