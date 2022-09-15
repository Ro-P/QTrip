package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import java.net.MalformedURLException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class testCase_03 {
    RemoteWebDriver driver;
    private String last_generated_username;
    List<WebElement> reservations; 

    @BeforeTest
    public void getDriver()throws MalformedURLException{
        this.driver= DriverSingleton.getSingletonDriver();
    }

    @Test(description = "Verify adventure booking and cancellation",priority = 3,dataProvider = "data-provider",dataProviderClass = DP.class,groups={"Booking and Cancellation Flow"})
    public void TestCase03(String userName,String password,String SearchCity,String AdventureName,String GuestName,String date,String count)throws InterruptedException{
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
        
    // 3. Search for an adventure
 
    home.searchCity(SearchCity);


    // 5. verify that the city is displayed on auto complete
    home.assertAutoCompleteText(SearchCity);
 

    // 6. Click on the city
    home.selectCity(SearchCity);


    AdventurePage adventure = new AdventurePage(driver);

    adventure.selectAdventure(AdventureName);
    
    // 4. Enter Name and Date and Reserve the adventure
    AdventureDetailsPage adventureDetails = new AdventureDetailsPage(driver);
    adventureDetails.bookAdventure(GuestName,date,Integer.parseInt(count));
    Thread.sleep(3000);
    // 5. Verify that the adventure booking was successful
    Assert.assertTrue(adventureDetails.isBookingSuccessful());
    // 6. Click on the history page
    HistoryPage history = new HistoryPage(driver);
    WebElement link = driver.findElement(By.xpath("//*[@id='reserved-banner']/a"));
    link.click();
    Thread.sleep(2000);

    // 7. Note down the transaction ID

     reservations = history.getReservations();
     String id = reservations.get(reservations.size()-1).getText();
     System.out.println(id+"-----");
    //  // 8. Cancel the Reservation
      history.cancelReservations(id);
      Thread.sleep(2000);
    // // // 9. Refresh the page
      history.refreshPage();
    // // // 10. Check if the transaction ID is removed
     Assert.assertTrue(history.isAdventureCancelled(id));
     home.navigateToHomePage();
     home.logoutUser();

    }
}
