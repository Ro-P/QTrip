package qtriptest.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventurePage {
  WebDriver driver;
  @FindBy(id = "duration-select")
  WebElement select_duration;
  @FindBy(id="category-select")
  WebElement select_category;
  @FindBy(xpath = "//div[@class='col-6 col-lg-3 mb-4']")
  List<WebElement> result_displayed;
  @FindBy(xpath="//div[@class='d-block d-md-flex justify-content-between flex-wrap pl-3 pr-3'][1]/child::h5")
  List<WebElement> adventure_list;
  @FindBy(xpath="//select[@id='category-select']//following-sibling::div")
  WebElement clear_category;
  @FindBy(xpath="//select[@id='duration-select']//following-sibling::div")
  WebElement clear_duration;

  public AdventurePage(WebDriver driver){
    this.driver = driver;
    PageFactory.initElements(new AjaxElementLocatorFactory(driver,20), this);
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  public void navigateToAdventurePage(String cityname){
     String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?city=" + cityname.toLowerCase();
     driver.get(url);
  }

  public void setFilterValue(String noOfHours){
    Select dropdown = new Select(select_duration);
    dropdown.selectByVisibleText(noOfHours);
  }

  public void clearFilterValue(){
       clear_duration.click();
       WebDriverWait wait = new WebDriverWait(driver,30);
       wait.until(ExpectedConditions.visibilityOfAllElements(result_displayed));
  }

  public void setCategoryValue(String category){
    Select dropdown = new Select(select_category);
    dropdown.selectByVisibleText(category);
  }

  public void clearCategoryFilter(){
    clear_category.click();
    WebDriverWait wait = new WebDriverWait(driver,30);
    wait.until(ExpectedConditions.visibilityOfAllElements(result_displayed));
  }

  public int getResultCount(){
    WebDriverWait wait = new WebDriverWait(driver,30);
    wait.until(ExpectedConditions.visibilityOfAllElements(result_displayed));
     return result_displayed.size();
  }

  public void selectAdventure(String adventureName){
    // WebDriverWait wait = new WebDriverWait(driver,30);
    // wait.until(ExpectedConditions.visibilityOfAllElements(adventure_list));
     for(int i=0;i<adventure_list.size();i++){
        if(adventure_list.get(i).getText().equals(adventureName)){
        adventure_list.get(i).click();
        return;
      }
     }
  }

}