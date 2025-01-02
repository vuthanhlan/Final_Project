package actions;

import org.openqa.selenium.WebDriver;
import ui.CheckOutPageUI;

public class CheckOutTestAction {
    WebDriver driver;
    CheckOutPageUI checkOutPageUI;
    public CheckOutTestAction(WebDriver driver) {
        this.driver = driver;
        checkOutPageUI = new CheckOutPageUI(driver);
    }
    public void clickButtonCheckout(){
        checkOutPageUI.buttonCheckOut().click();
    }

    public void fillCheckOutDetails(String firstName, String lastName, String ZipCode){
        if(firstName !=null){
            checkOutPageUI.textFirstName().sendKeys(firstName);
        }
        if(lastName !=null){
            checkOutPageUI.textLastName().sendKeys(lastName);
        }
        if(ZipCode !=null){
            checkOutPageUI.textZipCode().sendKeys(ZipCode);
        }

        checkOutPageUI.buttonContinue().click();
    }
    public void clickButtonFinish(){
        checkOutPageUI.buttonFinish().click();
    }
}
