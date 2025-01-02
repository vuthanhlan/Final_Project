package feature;

import org.openqa.selenium.WebDriver;
import ui.AddToCartPageUI;
import ui.CheckOutPageUI;
import ui.LoginPageUI;
import untils.ExcelUntils;

import java.util.List;
import java.util.Map;

public class Actions {
    WebDriver driver;
    LoginPageUI loginPageUI;
    AddToCartPageUI addToCartPageUI;
    CheckOutPageUI checkOutPageUI;
    String excelFilePath="dataProducts.xlsx";

    public Actions(WebDriver driver) {
        this.driver = driver;
        loginPageUI = new LoginPageUI(driver);
        addToCartPageUI = new AddToCartPageUI(driver);
        checkOutPageUI = new CheckOutPageUI(driver);
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

    public void clickButtonAddToCart(String product){
        addToCartPageUI.addToCartButton(product).click();
    }
    public void clickButtonCart(){
        addToCartPageUI.cartButton().click();
    }
    public void clickButtonRemove(String product){
        addToCartPageUI.buttonRemove(product).click();
    }

    public void addProduct(){
        List<Map<String, String>> excelData= ExcelUntils.readExcelData(excelFilePath,"Sheet1");
        //Thêm 2 sản phẩm vào giỏ hàng
        for (Map<String, String> rowData : excelData) {
            clickButtonAddToCart(rowData.get("Products"));
        }
    }

    public void removeProduct(){
        List<Map<String, String>> excelData= ExcelUntils.readExcelData(excelFilePath,"Sheet1");
        for (Map<String, String> rowData : excelData) {
            clickButtonRemove(rowData.get("Products"));
            break;
        }
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
