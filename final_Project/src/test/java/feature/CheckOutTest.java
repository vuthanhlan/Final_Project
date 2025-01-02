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
import ui.CheckOutPageUI;

import java.time.Duration;

public class CheckOutTest {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    AddToCartPageUI addToCartPageUI;
    CheckOutPageUI checkOutPageUI;

    @BeforeMethod
    public void setUp() throws Exception {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
        actions = new Actions(driver);
        addToCartPageUI = new AddToCartPageUI(driver);
        checkOutPageUI = new CheckOutPageUI(driver);
    }
    @Test
    public void testCheckOutSuccess() throws Exception {
        //B1: Login
        actions.LoginSuccess();
        //B2:Thêm 2 sản phẩm vào giỏ hàng
        actions.addProduct();
        //B3:Click icon giỏ hàng
        actions.clickButtonCart();
        //B4: Click button CheckOut
        actions.clickButtonCheckout();
        //B5:Checkout: Your Information
        actions.fillCheckOutDetails("John", "Doe", "12345");
        //B6: Click button finish
        actions.clickButtonFinish();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete"));
    }

    @Test
    public void testCheckOutNullFirstName(){
        //B1: Login
        actions.LoginSuccess();
        //B2:Thêm 2 sản phẩm vào giỏ hàng
        actions.addProduct();
        //B3:Click icon giỏ hàng
        actions.clickButtonCart();
        //B4: Click button CheckOut
        actions.clickButtonCheckout();
        //B5:Checkout: Your Information
        actions.fillCheckOutDetails(null, "Doe", "12345");
        WebElement error= checkOutPageUI.errorMess();
        Assert.assertEquals(error.getText(), "Error: First Name is required");
    }

    @Test
    public void testCheckOutNullLastName(){
        //B1: Login
        actions.LoginSuccess();
        //B2:Thêm 2 sản phẩm vào giỏ hàng
        actions.addProduct();
        //B3:Click icon giỏ hàng
        actions.clickButtonCart();
        //B4: Click button CheckOut
        actions.clickButtonCheckout();
        //B5:Checkout: Your Information
        actions.fillCheckOutDetails("John", "Doe", null);
        WebElement error= checkOutPageUI.errorMess();
        Assert.assertEquals(error.getText(), "Error: Postal Code is required");
    }

    @Test
    public void testCheckOutNullZipCode(){
        //B1: Login
        actions.LoginSuccess();
        //B2:Thêm 2 sản phẩm vào giỏ hàng
        actions.addProduct();
        //B3:Click icon giỏ hàng
        actions.clickButtonCart();
        //B4: Click button CheckOut
        actions.clickButtonCheckout();
        //B5:Checkout: Your Information
        actions.fillCheckOutDetails("John", null, "12345");
        WebElement error= checkOutPageUI.errorMess();
        Assert.assertEquals(error.getText(), "Error: Last Name is required");
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
