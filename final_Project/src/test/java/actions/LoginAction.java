package actions;

import org.openqa.selenium.WebDriver;
import ui.LoginPageUI;

public class LoginAction {
    WebDriver driver;
    LoginPageUI loginPageUI;
    public LoginAction(WebDriver driver) {
        this.driver = driver;
        loginPageUI = new LoginPageUI(driver);
    }
    public void inputData(String user, String pass){
        loginPageUI.getTextUser().sendKeys(user);
        loginPageUI.getTextPassword().sendKeys(pass);
    }
    public void clickLoginButton(){
        loginPageUI.btnLogin().click();
    }
    public void LoginSuccess(){
        inputData("standard_user","secret_sauce");
        clickLoginButton();
    }
}
