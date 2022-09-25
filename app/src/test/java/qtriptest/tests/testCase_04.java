package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.HistoryPage;



public class testCase_04 {
    RemoteWebDriver driver;
    private String last_generated_username;
    List<WebElement> reservations; 
    private int number_of_reservation;
    static ExtentTest test;
    static ExtentReports report;
    ReportSingleton singleton;

    @BeforeTest
    public void getDriver()throws MalformedURLException{
        this.driver= DriverSingleton.getSingletonDriver();
        singleton= ReportSingleton.getReportSingleton();
        this.report = singleton.getReport();
        test = report.startTest("Relaibility flow");
        report.loadConfig(new File("/home/crio-user/workspace/rohiniap999-ME_QTRIP_QA/app/extent_customization_configs.xml"));
    }

    @Test(description ="Relaibility flow",priority = 4,dataProvider = "data-provider",dataProviderClass = DP.class,groups={"Reliability Flow"})
    public void TestCase04(String userName,String password,String dataset1,String dataset2,String dataset3) throws InterruptedException, IOException{
    // 1. Navigate to QTrip
    HomePage home = new HomePage(driver);
    home.navigateToHomePage();
    Thread.sleep(2000);

    home.clickOnRegister();
    Thread.sleep(3000);

    // 2.Create a new User
    RegisterPage register = new RegisterPage(driver);
    register.registerNewUser(userName, password, password, true);
    last_generated_username = register.last_generated_user_name;
    Thread.sleep(3000);


    LoginPage login = new LoginPage(driver);
    login.performLogin(last_generated_username, password);

    Assert.assertTrue(home.isUserLoggedIn());
    String[] data1arr = dataset1.split(";");
    // 3. Search for an adventure
    home.searchCity(data1arr[0]);
    Thread.sleep(2000);
    home.assertAutoCompleteText(data1arr[0]);
    home.selectCity(data1arr[0]);

    AdventurePage adventure = new AdventurePage(driver);
    adventure.selectAdventure(data1arr[1]);
    
     // 4. Enter Name and Date and Reserve the adventure
    AdventureDetailsPage adventureDetails = new AdventureDetailsPage(driver);
    adventureDetails.bookAdventure(data1arr[2],data1arr[3],Integer.parseInt(data1arr[4]));
    Thread.sleep(3000);

    // 5. Verify that the adventure booking was successful
    Assert.assertTrue(adventureDetails.isBookingSuccessful());
    // 6. Repeat steps 3-5 to make two more bookings
    //----2---
    String[] data2arr = dataset2.split(";");
    HomePage home2 = new HomePage(driver);
    home2.navigateToHomePage();
    home2.searchCity(data2arr[0]);
    Thread.sleep(2000);
    home2.assertAutoCompleteText(data2arr[0]);
    home2.selectCity(data2arr[0]);

    AdventurePage adventure2 = new AdventurePage(driver);
    adventure2.selectAdventure(data2arr[1]);
    
     // 4. Enter Name and Date and Reserve the adventure
     AdventureDetailsPage adventureDetails2 = new AdventureDetailsPage(driver);
    adventureDetails2.bookAdventure(data2arr[2],data2arr[3],Integer.parseInt(data2arr[4]));
    Thread.sleep(3000);

    // 5. Verify that the adventure booking was successful
    Assert.assertTrue(adventureDetails2.isBookingSuccessful());
    //-------3-----------
    String[] data3arr = dataset3.split(";");
    HomePage home3 = new HomePage(driver);
    home3.navigateToHomePage();
    home3.searchCity(data3arr[0]);
    Thread.sleep(2000);
    home3.assertAutoCompleteText(data3arr[0]);
    home3.selectCity(data3arr[0]);

    AdventurePage adventure3 = new AdventurePage(driver);
    adventure3.selectAdventure(data3arr[1]);
    
     // 4. Enter Name and Date and Reserve the adventure
     AdventureDetailsPage adventureDetails3 = new AdventureDetailsPage(driver);
    adventureDetails3.bookAdventure(data3arr[2],data3arr[3],Integer.parseInt(data3arr[4]));
    Thread.sleep(3000);

    // 5. Verify that the adventure booking was successful
    Assert.assertTrue(adventureDetails3.isBookingSuccessful());
    //number_of_reservation= adventureDetails.count;
    // 7. Click on the History Page
     HistoryPage history = new HistoryPage(driver);
     history.navigateToHistoryPage();
   
    reservations = history.getReservations();
    // 8. Check if all the bookings are displayed on the history page
    boolean status = 3==reservations.size();
    
    if(status){
        test.log(LogStatus.PASS, "Relaibility flow : PASSED");
    }else{
        test.log(LogStatus.FAIL,test.addScreenCapture(SeleniumWrapper.capture(driver))+"Relaibility flow : FAILED");
    }
    Assert.assertTrue(status);
    home.navigateToHomePage();
     home.logoutUser();
     test.log(LogStatus.INFO,test.addScreenCapture(SeleniumWrapper.capture(driver))+"Relaibility flow : COMPLETED");

     report.endTest(test);
    }

    @AfterSuite
    public void teardown(){
        
        report.flush();
    }
}
