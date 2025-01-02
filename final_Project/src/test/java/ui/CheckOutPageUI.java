package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckOutPageUI {
    WebDriver driver;
    public CheckOutPageUI(WebDriver driver) {
        this.driver = driver;
    }
    public WebElement buttonCheckOut() {
        return driver.findElement(By.id("checkout"));
    }
    public WebElement textFirstName() {
        return driver.findElement(By.id("first-name"));
    }
    public WebElement textLastName() {
        return driver.findElement(By.id("last-name"));
    }
    public WebElement textZipCode() {
        return driver.findElement(By.id("postal-code"));
    }
    public WebElement buttonContinue() {
        return driver.findElement(By.id("continue"));
    }
    public WebElement errorMess() {
        return driver.findElement(By.xpath("//div[contains(@class,'error')]"));
    }
    public WebElement buttonFinish() {
        return driver.findElement(By.id("finish"));
    }
}
