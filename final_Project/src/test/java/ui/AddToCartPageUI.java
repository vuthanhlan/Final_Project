package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AddToCartPageUI {
    WebDriver driver;
    public AddToCartPageUI(WebDriver driver) {
        this.driver = driver;
    }
    public WebElement addToCartButton(String product) {
        return driver.findElement(By.xpath("//div[contains(text(),'"+product+"')]/ancestor::div[@class='inventory_item_description']/descendant::button"));
    }
    public WebElement cartButton() {
        return driver.findElement(By.xpath("//a[contains(@class,'cart')]"));
    }
    public WebElement inventoryItemName(String product) {
        return driver.findElement(By.xpath("//div[contains(text(),'"+product+"')]"));
    }
    public WebElement inventoryItemPrice(String product) {
        return driver.findElement(By.xpath("//div[contains(text(),'"+product+"')]/ancestor::div[@class='inventory_item_description']/descendant::div[@class='inventory_item_price']"));
    }
    public WebElement cartItemName(String product) {
        return driver.findElement(By.xpath("//div[contains(text(),'"+product+"')]"));
    }
    public WebElement cartItemPrice(String product) {
        return driver.findElement(By.xpath("//div[contains(text(),'"+product+"')]/ancestor::div[@class='cart_item_label']/descendant::div[@class='inventory_item_price']"));
    }
    public WebElement buttonRemove(String product) {
        return driver.findElement(By.xpath("//div[contains(text(),'"+product+"')]/ancestor::div[@class='cart_item_label']/descendant::button"));

    }
    public List<WebElement> cartItems() {
        return driver.findElements(By.className("cart_item_label"));
    }
}
