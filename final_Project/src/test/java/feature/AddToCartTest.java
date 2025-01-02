package feature;

import actions.AddToCartAction;
import actions.LoginAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.AddToCartPageUI;
import untils.ExcelUntils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddToCartTest {

    WebDriver driver;
    WebDriverWait wait;
    AddToCartPageUI addToCartPageUI;
    LoginAction loginAction;
    AddToCartAction addToCartAction;
    String excelFilePath="dataProducts.xlsx";
    List<String> expectedProductNames = new ArrayList<>();
    List<String> expectedProductPrices = new ArrayList<>();
    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        addToCartPageUI = new AddToCartPageUI(driver);
        loginAction = new LoginAction(driver);
        addToCartAction = new AddToCartAction(driver);
        addToCartPageUI = new AddToCartPageUI(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
    }
    public void addProduct(){
        List<Map<String, String>> excelData= ExcelUntils.readExcelData(excelFilePath,"Sheet1");
        //Thêm 2 sản phẩm vào giỏ hàng
        for (Map<String, String> rowData : excelData) {
            String productName = addToCartPageUI.inventoryItemName(rowData.get("Products")).getText();
            String productPrice = addToCartPageUI.inventoryItemPrice(rowData.get("Products")).getText();
            expectedProductNames.add(productName);
            expectedProductPrices.add(productPrice);
            addToCartAction.clickButtonAddToCart(rowData.get("Products"));
        }
    }

    @Test
    public void addToCartTest() {
        //Login
        loginAction.LoginSuccess();

        //Thêm 2 sản phẩm vào giỏ hàng
        addProduct();
        //Verify số lượng trên icon của giỏ hàng
        Assert.assertEquals(addToCartPageUI.cartButton().getText(), "2","Cart badge is incorrect");

        //Click icon giỏ hàng
        addToCartAction.clickButtonCart();

        //Verify số lượng sản phẩm trong giỏ hàng
        List<WebElement> cartItems= addToCartPageUI.cartItems();
        Assert.assertEquals(cartItems.size(),2,"Cart size is incorrect after adding a product.");
        //Verify thông tin tên, giá của sản phẩm trong giỏ hàng
        List<Map<String, String>> excelData= ExcelUntils.readExcelData(excelFilePath,"Sheet1");
        for(int i=0; i<excelData.size(); i++){
            Map<String, String> rowData = excelData.get(i);
            WebElement cartProductName= addToCartPageUI.cartItemName(rowData.get("Products"));
            WebElement cartProductPrice= addToCartPageUI.cartItemPrice(rowData.get("Products"));
            Assert.assertEquals(cartProductName.getText(),expectedProductNames.get(i),"Product name in cart is incorrect for product");
            Assert.assertEquals(cartProductPrice.getText(),expectedProductPrices.get(i),"Product price in cart is incorrect for product");
        }
    }

    @Test
    public void removeFromCartTest() {
        //B1: Login
        loginAction.LoginSuccess();
        //B2:Thêm 2 sản phẩm vào giỏ hàng
        addProduct();
        //B3: Click icon giỏ hàng
        addToCartAction.clickButtonCart();
       //B4: Xóa 1 sản phẩm
        addToCartAction.removeProduct();

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
