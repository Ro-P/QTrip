package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.AdventurePage;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class testCase_02 {
    RemoteWebDriver driver;

    @BeforeTest
    public void getDriver()throws MalformedURLException{
        this.driver= DriverSingleton.getSingletonDriver();
    }

    @Test(description = "Verify search and filter flow",priority = 2,dataProvider = "data-provider",dataProviderClass = DP.class,groups={"Search and Filter flow"})
    public void TestCase02(String CityName,String category,String duration,String ExpectedFilteredResults,String ExpectedUnFilteredResults)throws InterruptedException{
    // 1. Navigate to the Home page of QTrip
    HomePage home = new HomePage(driver);
    home.navigateToHomePage();

   // 2. Search for a city that's not present
   home.searchCity("Hubli");
   Thread.sleep(1000);

   // 3. Verify if the no matches found message is displayed
   home.verifyNoCityFound("No City found");

   // 4. Search for a city that's present
   home.searchCity(CityName);
   Thread.sleep(2000);

   // 5. verify that the city is displayed on auto complete
   home.assertAutoCompleteText(CityName);

   // 6. Click on the city
   home.selectCity(CityName);
   Thread.sleep(5000);

   // 7. Select Filters : hours
   AdventurePage adventure = new AdventurePage(driver);
   adventure.setFilterValue(duration);
   //Thread.sleep(5000);

   // 8. Verify that appropriate data is displayed
  // Assert.assertEquals(adventure.getResultCount(),3);

   // 9. Select category
   adventure.setCategoryValue(category);

   // 10. Verify that appropriate data is displayed
   Assert.assertEquals(adventure.getResultCount(),Integer.parseInt(ExpectedFilteredResults));
   
   // 11. Clear Filters and category
   adventure.clearFilterValue();
   adventure.clearCategoryFilter();

   // 12. Verify that all the records are displayed
   Assert.assertEquals(adventure.getResultCount(),Integer.parseInt(ExpectedUnFilteredResults));
    }
}
