package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.rmi.Remote;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class testCase_01 {
    RemoteWebDriver driver;
    private String last_generated_username;

    @BeforeTest
    public void getDriver() throws MalformedURLException{
       this.driver = DriverSingleton.getSingletonDriver();
    }

    @Test(description = "Verify user registration",priority = 1,dataProvider = "data-provider" ,dataProviderClass = DP.class,groups={"Login Flow"})
    public void TestCase01(String userName,String password) throws InterruptedException{
       
        HomePage home = new HomePage(driver);
        home.navigateToHomePage();
        Thread.sleep(2000);

        home.clickOnRegister();
        Thread.sleep(3000);
        
        RegisterPage register = new RegisterPage(driver);
        register.registerNewUser(userName, password, password, true);
        last_generated_username = register.last_generated_user_name;
        Thread.sleep(3000);


        LoginPage login = new LoginPage(driver);
        login.performLogin(last_generated_username, password);

        Assert.assertTrue(home.isUserLoggedIn());
        
        home.logoutUser();
        Assert.assertFalse(home.isUserLoggedIn());

    }

}
