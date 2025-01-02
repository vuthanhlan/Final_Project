package feature;

import actions.AddToCartAction;
import actions.CheckOutTestAction;
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
import ui.CheckOutPageUI;

import java.time.Duration;

public class CheckOutTest {
    WebDriver driver;
    WebDriverWait wait;
    LoginAction login;
    AddToCartAction addToCart;
    CheckOutTestAction checkOut;
    AddToCartPageUI addToCartPageUI;
    CheckOutPageUI checkOutPageUI;

    @BeforeMethod
    public void setUp() throws Exception {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
        login = new LoginAction(driver);
        addToCart = new AddToCartAction(driver);
        checkOut = new CheckOutTestAction(driver);
        addToCartPageUI = new AddToCartPageUI(driver);
        checkOutPageUI = new CheckOutPageUI(driver);
    }
    @Test
    public void testCheckOutSuccess() throws Exception {
        //B1: Login
        login.LoginSuccess();
        //B2:Thêm 2 sản phẩm vào giỏ hàng
        addToCart.addProduct();
        //B3:Click icon giỏ hàng
        addToCart.clickButtonCart();
        //B4: Click button CheckOut
        checkOut.clickButtonCheckout();
        //B5:Checkout: Your Information
        checkOut.fillCheckOutDetails("John", "Doe", "12345");
        //B6: Click button finish
        checkOut.clickButtonFinish();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete"));
    }

    @Test
    public void testCheckOutNullFirstName(){
        //B1: Login
        login.LoginSuccess();
        //B2:Thêm 2 sản phẩm vào giỏ hàng
        addToCart.addProduct();
        //B3:Click icon giỏ hàng
        addToCart.clickButtonCart();
        //B4: Click button CheckOut
        checkOut.clickButtonCheckout();
        //B5:Checkout: Your Information
        checkOut.fillCheckOutDetails(null, "Doe", "12345");
        WebElement error= checkOutPageUI.errorMess();
        Assert.assertEquals(error.getText(), "Error: First Name is required");
    }

    @Test
    public void testCheckOutNullLAstName(){
        //B1: Login
        login.LoginSuccess();
        //B2:Thêm 2 sản phẩm vào giỏ hàng
        addToCart.addProduct();
        //B3:Click icon giỏ hàng
        addToCart.clickButtonCart();
        //B4: Click button CheckOut
        checkOut.clickButtonCheckout();
        //B5:Checkout: Your Information
        checkOut.fillCheckOutDetails("John", null, "12345");
        WebElement error= checkOutPageUI.errorMess();
        Assert.assertEquals(error.getText(), "Error: Last Name is required");
    }

    @Test
    public void testCheckOutNullZipCode(){
        //B1: Login
        login.LoginSuccess();
        //B2:Thêm 2 sản phẩm vào giỏ hàng
        addToCart.addProduct();
        //B3:Click icon giỏ hàng
        addToCart.clickButtonCart();
        //B4: Click button CheckOut
        checkOut.clickButtonCheckout();
        //B5:Checkout: Your Information
        checkOut.fillCheckOutDetails("John", "Doe", null);
        WebElement error= checkOutPageUI.errorMess();
        Assert.assertEquals(error.getText(), "Error: Postal Code is required");
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
