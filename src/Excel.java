import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hslf.model.Sheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.lowagie.text.Header;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


public class Excel {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws WriteException 
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
	
	    HSSFWorkbook wb = new HSSFWorkbook();
	    HSSFSheet sheet = wb.createSheet("new sheet");    
	    //sets the print area for the first sheet
	    wb.setPrintArea(0, "$A$1:$C$1000");
	    
	    for(int i=0;i<1000;i++){
            sheet.createRow(i).createCell(0).setCellValue(i);
        }
	    //Alternatively:
	   /* wb.setPrintArea(
	            0, //sheet index
	            0, //start column
	            1, //end column
	            0, //start row
	            0  //end row
	    );*/

	    FileOutputStream fileOut = new FileOutputStream("C:/workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();

	        
	}

}
