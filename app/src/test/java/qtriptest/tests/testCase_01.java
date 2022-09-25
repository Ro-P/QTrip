package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Remote;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import com.relevantcodes.extentreports.ExtentReports;


public class testCase_01 {
    RemoteWebDriver driver;
    private String last_generated_username;
    static ExtentTest test;
    static ExtentReports report;
    ReportSingleton singleton;

    @BeforeTest
    public void getDriver() throws MalformedURLException{
       this.driver = DriverSingleton.getSingletonDriver();
       singleton= ReportSingleton.getReportSingleton();
       this.report = singleton.getReport();
       test = report.startTest("Verify user registration");
       report.loadConfig(new File("/home/crio-user/workspace/rohiniap999-ME_QTRIP_QA/app/extent_customization_configs.xml"));

    }

    @Test(description = "Verify user registration",priority = 1,dataProvider = "data-provider" ,dataProviderClass = DP.class,groups={"Login Flow"})
    public void TestCase01(String userName,String password) throws InterruptedException, IOException{
       
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
        boolean status = home.isUserLoggedIn();
       
        if(!status){
            test.log(LogStatus.PASS,"Verify user registration : PASSED");
           }else{
             test.log(LogStatus.FAIL,test.addScreenCapture(SeleniumWrapper.capture(driver))+"Verify user registration : FAILED");
           }
        test.log(LogStatus.INFO,test.addScreenCapture(SeleniumWrapper.capture(driver))+"Verify user registration : COMPLETED");
        Assert.assertFalse(status);   
        report.endTest(test);
    }
}
