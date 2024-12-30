package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPageUI {
    WebDriver driver;
    public LoginPageUI(WebDriver driver) {
        this.driver = driver;
    }
    public WebElement getTextUser(){
        return driver.findElement(By.id("user-name"));
    }
    public WebElement getTextPassword(){
        return driver.findElement(By.id("password"));
    }
    public WebElement btnLogin(){
        return driver.findElement(By.id("login-button"));
    }
    public WebElement ErrorMess(){
        return driver.findElement(By.xpath("//h3[contains(text(),'Username')]"));
    }
}
