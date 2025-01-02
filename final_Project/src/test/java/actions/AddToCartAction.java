package actions;

import org.openqa.selenium.WebDriver;
import ui.AddToCartPageUI;
import untils.ExcelUntils;

import java.util.List;
import java.util.Map;

public class AddToCartAction {
    WebDriver driver;
    AddToCartPageUI addToCartPageUI;
    String excelFilePath="dataProducts.xlsx";
    public AddToCartAction(WebDriver driver) {
        this.driver = driver;
        addToCartPageUI = new AddToCartPageUI(driver);
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

}
