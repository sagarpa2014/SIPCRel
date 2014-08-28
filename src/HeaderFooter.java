
 
import java.io.FileOutputStream;
 



import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class HeaderFooter {
 
 
    public static void main(String[]args) throws Exception {
    	Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();
        Sheet sheet = wb.createSheet("first-header - format sheet");
        sheet.getPrintSetup().setLandscape(true); 
        sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.LEGAL_PAPERSIZE); 
        sheet.createRow(0).createCell(0).setCellValue(123);
 
        //sheet.setFitToPage(true);
        
        //set page numbers in the footer
        Footer footer = sheet.getFooter();
        //&P == current page number
        //&N == page numbers
        footer.setRight("Page &P of &N");
 
         
        Header firstHeader=((XSSFSheet)sheet).getFirstHeader();
        //&F == workbook file name
        firstHeader.setLeft("&F ......... first header exito");
        
         
        for(int i=0;i<1000;i++){
            sheet.createRow(i).createCell(0).setCellValue(i);
        }
         
         
        XSSFSheet sheet2 = (XSSFSheet)wb.createSheet("odd header-even footer");

        Header oddHeader=sheet2.getOddHeader();
        //&B == bold
        //&E == double underline
        //&D == date
        oddHeader.setCenter("&B &E oddHeader     &D ");
        
         
        Footer evenFooter=sheet2.getEvenFooter();
        evenFooter.setRight("even footer &P");
        sheet2.createRow(10).createCell(0).setCellValue("Second sheet with an oddHeader and an evenFooter");
 
        for(int i=0;i<200;i=i+10){
            sheet2.createRow(i).createCell(0).setCellValue(123);
        }
         
        XSSFSheet sheet3 = (XSSFSheet)wb.createSheet("odd header- odd footer");
        sheet3.createRow(10).createCell(0).setCellValue("Third sheet with oddHeader and oddFooter");
        Header oddH=sheet3.getOddHeader();
        //&C == centered
        oddH.setCenter("centered oddHeader");
        oddH.setLeft("left ");
        oddH.setRight("right ");
         
        Footer oddF=sheet3.getOddFooter();
        oddF.setLeft("Page &P");
        oddF.setRight("Pages &N ");
         
        FileOutputStream fileOut = new FileOutputStream("C:/Users/luz.corona/Documents/headerFooter.xlsx");
        wb.write(fileOut);
        fileOut.close();
 
    }
}
