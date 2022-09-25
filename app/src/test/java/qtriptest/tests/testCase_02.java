package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.HomePage;
import qtriptest.pages.AdventurePage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class testCase_02 {
    RemoteWebDriver driver;
    static ExtentTest test;
    static ExtentReports report;
    ReportSingleton singleton;

    @BeforeTest
    public void getDriver()throws MalformedURLException{
        this.driver= DriverSingleton.getSingletonDriver();
        singleton= ReportSingleton.getReportSingleton();
        this.report = singleton.getReport();
        test = report.startTest("Verify search and filter flow");
        report.loadConfig(new File("/home/crio-user/workspace/rohiniap999-ME_QTRIP_QA/app/extent_customization_configs.xml"));
    }

    @Test(description = "Verify search and filter flow",priority = 2,dataProvider = "data-provider",dataProviderClass = DP.class,groups={"Search and Filter flow"})
    public void TestCase02(String CityName,String category,String duration,String ExpectedFilteredResults,String ExpectedUnFilteredResults)throws InterruptedException, IOException{
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
  // Thread.sleep(5000);

   // 7. Select Filters : hours
   AdventurePage adventure = new AdventurePage(driver);
   adventure.setFilterValue(duration);
   

   // 8. Verify that appropriate data is displayed
  // Assert.assertEquals(adventure.getResultCount(),3);

   // 9. Select category
   adventure.setCategoryValue(category);
  // Thread.sleep(5000);

   // 10. Verify that appropriate data is displayed
   Assert.assertEquals(adventure.getResultCount(),Integer.parseInt(ExpectedFilteredResults));
   
   // 11. Clear Filters and category
   adventure.clearFilterValue();
   adventure.clearCategoryFilter();
   //Thread.sleep(5000);

   // 12. Verify that all the records are displayed
   boolean status = adventure.getResultCount()==Integer.parseInt(ExpectedUnFilteredResults);
   

   if(status){
    test.log(LogStatus.PASS,"Verify search and filter flow : PASSED");
   }else{
     test.log(LogStatus.FAIL,test.addScreenCapture(SeleniumWrapper.capture(driver))+"Verify search and filter flow : FAILED");
   }

   test.log(LogStatus.INFO,test.addScreenCapture(SeleniumWrapper.capture(driver))+"Verify search and filter flow : COMPLETED");
   Assert.assertTrue(status);
   report.endTest(test);
}
}
