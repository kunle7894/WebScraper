package excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelAcceser 
{
	private String fileLoc;
	private Workbook workBook;
	private Sheet sheet;
	private int rowLoc;
	private String error;
	
	public ExcelAcceser(String fir)
	{
		fileLoc = fir;
		rowLoc = 0;
		
		error = "";
		
		createWorkbook();
	}
	
	public void createWorkbook()
	{
		workBook = new HSSFWorkbook();
		sheet = workBook.createSheet();
	}
	
	public void addRowData(ArrayList<ArrayList<String>> data)
	{
		Column col;
		//Row row = sheet.createRow(rowLoc);
		//rowLoc++;
		for (int i=0; data.size()>0 && i<data.get(0).size(); i++)
		{
			rowLoc++;
			
			Row row = sheet.createRow(rowLoc);
			
			for (int j=0; j<data.size(); j++)
			{
				Cell cell = row.createCell(j+1);
				if (data.get(j).size()>i)
				{
					cell.setCellValue(data.get(j).get(i));
				}
			}
			sheet.autoSizeColumn(i);
		}
	}
	
	//Extracts File. Trys a different name if the current name is in use (SO you don't have to restart the possibly 30 minute process).
	public void extractFile() 
	{
		try (FileOutputStream fileOut = new FileOutputStream(fileLoc))
		{
			workBook.write(fileOut);
		}
		catch(IOException e)
		{
			try (FileOutputStream fileOut = new FileOutputStream(fileLoc.substring(0, fileLoc.length()-4)+"XJ71R.xls"))
			{
				workBook.write(fileOut);
			}
			catch(IOException f)
			{
				error = "File directory can't be reached";
			}
		}
	}
	
	public String getError()
	{
		return error;
	}
	
	public void setError(String val)
	{
		error = val;
	}
}
