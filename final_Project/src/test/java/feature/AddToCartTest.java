package feature;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.AddToCartPageUI;
import ui.LoginPageUI;
import untils.ExcelUntils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AddToCartTest {

    WebDriver driver;
    WebDriverWait wait;
    AddToCartPageUI addToCartPageUI;
    LoginPageUI loginPageUI;
    String excelFilePath="dataProducts.xlsx";

    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        addToCartPageUI = new AddToCartPageUI(driver);
        loginPageUI = new LoginPageUI(driver);

        driver.get("https://www.saucedemo.com/");

    }
    public void inputData(String user, String pass){
        loginPageUI.getTextUser().sendKeys(user);
        loginPageUI.getTextPassword().sendKeys(pass);
    }
    public void clickLoginButton(){
        loginPageUI.btnLogin().click();
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

    @Test
    public void AddToCartTest() {
        inputData("standard_user","secret_sauce");
        clickLoginButton();
        List<String> expectedProductNames = new ArrayList<>();
        List<String> expectedProductPrices = new ArrayList<>();
        List<Map<String, String>> excelData= ExcelUntils.readExcelData(excelFilePath,"Sheet1");

        //Thêm 2 sản phẩm vào giỏ hàng
        for (Map<String, String> rowData : excelData) {
            String productName = addToCartPageUI.inventoryItemName(rowData.get("Products")).getText();
            String productPrice = addToCartPageUI.inventoryItemPrice(rowData.get("Products")).getText();
            expectedProductNames.add(productName);
            expectedProductPrices.add(productPrice);
            clickButtonAddToCart(rowData.get("Products"));
        }
        //Verify số lượng trên icon của giỏ hàng
        int number= excelData.size();
        String quantity=String.valueOf(number);
        Assert.assertEquals(addToCartPageUI.cartButton().getText(), quantity,"Cart badge is incorrect");

        clickButtonCart();

        //Verify thông tin tên, giá của sản phẩm
        for(int i=0; i<excelData.size(); i++){
            Map<String, String> rowData = excelData.get(i);
            WebElement cartProductName= addToCartPageUI.cartItemName(rowData.get("Products"));
            WebElement cartProductPrice= addToCartPageUI.cartItemPrice(rowData.get("Products"));
            Assert.assertEquals(cartProductName.getText(), expectedProductNames.get(i),"Product name in cart is incorrect for product");
            Assert.assertEquals(cartProductPrice.getText(),expectedProductPrices.get(i),"Product price in cart is incorrect for product");
        }
    }

    @Test
    public void RemoveFromCartTest() {

        //Thêm 2 sản phẩm vào giỏ hàng
        AddToCartTest();
        List<Map<String, String>> excelData= ExcelUntils.readExcelData(excelFilePath,"Sheet1");

        //Xóa 1 sản phẩm
        for (Map<String, String> rowData : excelData) {
            clickButtonRemove(rowData.get("Products"));
            break;
        }

        //Verify số lượng sản phẩm còn lại trong giỏ hàng
        List<WebElement> cartItems= addToCartPageUI.cartItems();
        Assert.assertEquals(cartItems.size(),1,"Cart size is incorrect after removing a product.");

        //Verify số lượng trên icon của giỏ hàng sau khi remove
        String updatedCartBagde= addToCartPageUI.cartButton().getText();
        Assert.assertEquals(updatedCartBagde,"1","Cart badge is incorrect after removing a product");

        


    }
    @AfterMethod
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
}
