package qtriptest;

import java.io.File;
import java.sql.Timestamp;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;

public class ReportSingleton {
     
    public static ExtentReports reports=null;
    public static ReportSingleton singleton = null;

    private ReportSingleton(){
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      String filaName = "ExtentRepot_"+timestamp+".html";
      reports = new ExtentReports(System.clearProperty("user.dir")+"/Reports/"+filaName);
    }

    public static ReportSingleton getReportSingleton(){
         if(singleton==null){
            singleton = new ReportSingleton();
         }
         return singleton;
    }

    public  ExtentReports getReport(){
        return reports;
    }
}