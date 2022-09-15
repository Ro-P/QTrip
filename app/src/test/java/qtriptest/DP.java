package qtriptest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;


public class DP {
    @DataProvider (name = "data-provider")
    public Object[][] dpMethod(Method m) throws IOException{
        String filePath = "./src/test/resources/DatasetsforQTrip.xlsx";

        DataFormatter formatter = new DataFormatter();
        File fileName = new File(filePath);
        FileInputStream file = new FileInputStream(fileName);
        
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet(m.getName());

        int rowcount = sheet.getPhysicalNumberOfRows();
        
        System.out.println("RowCount:---"+rowcount);
        int colcount = sheet.getRow(1).getLastCellNum();
        System.out.println("ColCount:---"+colcount);

       
       Object[][] datapro  = new Object[rowcount-1][colcount-1];
       
              for(int outer =1; outer<rowcount;outer++){
                  XSSFRow row = sheet.getRow(outer);
      
                  for(int inner =1; inner<colcount;inner++){
                      XSSFCell cell = row.getCell(inner);
                       datapro[outer-1][inner-1]= formatter.formatCellValue(cell);
                  }
                 
              }
        return datapro;
    }
}
