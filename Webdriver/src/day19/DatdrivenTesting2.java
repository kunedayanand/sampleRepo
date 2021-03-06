package day19;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DatdrivenTesting2 {

		FirefoxDriver driver=null;
		@BeforeMethod
		public void setUp()
		{
			driver=new FirefoxDriver();
			driver.get("http://www.mortgagecalculator.org/");
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		}
		
		@Test
		public void datadrivenTest() throws IOException
		{
			FileInputStream f=new FileInputStream("E:\\April27Batch\\Webdriver\\src\\com\\qedge\\excelfiles\\MortgageCalculator.xlsx");
			XSSFWorkbook wb=new XSSFWorkbook(f);
			XSSFSheet ws=wb.getSheet("Sheet1");
			
			Row r=null;
			Cell c=null;
			Iterator<Row> row=ws.iterator();
			row.next();
			while(row.hasNext())
			{
				r=row.next();
				List<WebElement> input=driver.findElements(By.xpath("//input[@type='text']"));
			    for(int i=0;i<input.size();i++)
			    {
			    	input.get(i).clear();
			    }
			    driver.findElement(By.name("param[homevalue]")).sendKeys(Long.toString((long)r.getCell(0).getNumericCellValue()));
			    driver.findElement(By.name("param[principal]")).sendKeys(Long.toString((long)r.getCell(1).getNumericCellValue()));
			    driver.findElement(By.name("param[interest_rate]")).sendKeys(Long.toString((long)r.getCell(2).getNumericCellValue()));
			    driver.findElement(By.name("param[term]")).sendKeys(Long.toString((long)r.getCell(3).getNumericCellValue()));
			    driver.findElement(By.name("param[start_month]")).sendKeys(r.getCell(4).getStringCellValue());
			    driver.findElement(By.name("param[start_year]")).sendKeys(r.getCell(5).getStringCellValue());
			    driver.findElement(By.name("param[property_tax]")).sendKeys(Double.toString(r.getCell(6).getNumericCellValue()));
			    driver.findElement(By.name("param[pmi]")).sendKeys(Double.toString(r.getCell(7).getNumericCellValue()));
			    driver.findElement(By.name("cal")).click();
			    String emi=driver.findElement(By.xpath("//*[@id='calc']/form/section/section[2]/div/div/div[1]/div/div/div[3]/div[2]/div[2]/div[1]/div[1]/h3")).getText();
			    r.createCell(9).setCellValue(emi);
			    if(emi.contains(r.getCell(8).getStringCellValue()))
			    {
			    	r.createCell(10).setCellValue("Passed");
			    }
			    else
			    {
			    	r.createCell(10).setCellValue("Failed");
			    }
			
			}
			
			
			FileOutputStream f1=new FileOutputStream("E:\\April27Batch\\Webdriver\\src\\com\\qedge\\resultexcelfiles\\mortagageresults.xlsx");
			wb.write(f1);
			f1.close();
		}

	}


