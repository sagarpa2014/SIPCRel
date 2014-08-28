package mx.gob.comer.sipc.oficios.pdf;

import java.awt.Color;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import mx.gob.comer.sipc.domain.Personal;
import mx.gob.comer.sipc.pagos.action.ArchivosTesofeAction;
import mx.gob.comer.sipc.utilerias.TextUtil;
import mx.gob.comer.sipc.vistas.domain.PagosV;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class OficioEnvioCGCDGAF extends PdfPageEventHelper {
	
	
	// Configuracion de fuentes
	//private final Font ARIALBOLD10 = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, Color.BLACK);
	private final Font TIMESROMAN10 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD, Color.BLACK);
	private final Font TIMESROMAN10NORMAL = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL, Color.BLACK);
	//private final Font ARIALNORMAL08 = FontFactory.getFont(FontFactory.HELVETICA,  8, Font.NORMAL, Color.BLACK);
	//private final Font ARIALNORMAL08LIGTH= FontFactory.getFont(FontFactory.HELVETICA,  8, Font.NORMAL	, Color.LIGHT_GRAY);
	private final Font TIMESROMAN08LIGTH= FontFactory.getFont(FontFactory.HELVETICA,  8, Font.NORMAL	, Color.LIGHT_GRAY);
	private final Font TIMESROMAN08	= FontFactory.getFont(FontFactory.TIMES_ROMAN, 8,Font.NORMAL, Color.BLACK);
	private final Font TIMESROMAN12	= FontFactory.getFont(FontFactory.TIMES_ROMAN, 11,Font.NORMAL, Color.BLACK);
	//private final Font ARIALBOLD12 = FontFactory.getFont(FontFactory.HELVETICA, 1, Font.NORMAL, Color.BLACK);
	

	private ArchivosTesofeAction ata;
	private PdfWriter writer;
	private Document document;
	private Paragraph parrafo;
	private PdfPTable piePagina;
	private StringBuilder texto;
	public OficioEnvioCGCDGAF(ArchivosTesofeAction ata) {
		this.ata = ata;
	}
	
	public void generarOficioEnvioCGCDGAF() throws Exception{	
		document = new Document(PageSize.LETTER, 50, 50, 50, 50);
		writer = PdfWriter.getInstance(document, new FileOutputStream(ata.getRutaSalida()));
		writer.setPageEvent(this);
		document.open();
		getEncabezado();
		addEmptyLine(1);
		getLugarYFecha();
		addEmptyLine(1);
		getDestinatario();
		addEmptyLine(1);
		Image img1 = Image.getInstance(ata.getRutaMarcaAgua()); 
		img1.setAlignment(Image.MIDDLE | Image.UNDERLYING); 
		document.add(img1);
		getCuerpo();
		addEmptyLine(1);
		getEmisor();
		document.newPage();
		getAnexoDetallePagos();
		document.close();
	}
	private void getAnexoDetallePagos() throws DocumentException {
		Paragraph pTitulo = new Paragraph();
		double totalVolumen = 0;
		double totalImporte = 0;
		
		Paragraph pOficio = new Paragraph();
		pOficio.setAlignment(Element.ALIGN_RIGHT);
		pOficio.add(new Chunk("ANEXO AL OFICIO "+ata.getClaveOficio()+ata.getNoOficio()+ata.getAnioOficio(), TIMESROMAN10));
		pOficio.setSpacingAfter(10f);
		document.add(pOficio);		
		
		pTitulo.setAlignment(Element.ALIGN_CENTER);
		pTitulo.add(new Chunk("DETALLE DE PAGOS", TIMESROMAN10));
		pTitulo.setSpacingAfter(10f);
		document.add(pTitulo);		
		
		PdfPCell cell = null;
		float[] w = {1};
		if(ata.getCriterioPago()== 3 ){
			float[] x = {10,25,  20, 15, 15, 15}; // %
			 w = x;
		}else{
			float [] y = {10,25, 25, 20, 20}; // %	
			w =y;
		}
		
		
		
		PdfPTable t = new PdfPTable(w);
		t.setWidthPercentage(100);
		parrafo =  new Paragraph("No.", TIMESROMAN08);
		cell = new PdfPCell(parrafo);
		cell =createCell(parrafo, 0, 1, 11);
		t.addCell(cell);
		parrafo =  new Paragraph("COMPRADOR", TIMESROMAN08);
		cell = new PdfPCell(parrafo);
		cell =createCell(parrafo, 0, 1, 11);
		t.addCell(cell);
		parrafo =  new Paragraph("CARTA", TIMESROMAN08);
		cell = new PdfPCell(parrafo);
		cell =createCell(parrafo, 0, 1, 11);
		t.addCell(cell);
		if(ata.getCriterioPago()== 2 || ata.getCriterioPago()==3){
			parrafo =  new Paragraph("ETAPA", TIMESROMAN08);
			cell = new PdfPCell(parrafo);
			cell =createCell(parrafo, 0, 1, 11);
			t.addCell(cell);
		}
		if(ata.getCriterioPago()== 1 || ata.getCriterioPago()==3){
			parrafo =  new Paragraph("VOLUMEN", TIMESROMAN08);
			cell = new PdfPCell(parrafo);
			cell =createCell(parrafo, 0, 1, 11);
			t.addCell(cell);	
		}
		
		parrafo =  new Paragraph("IMPORTE", TIMESROMAN08);
		cell = new PdfPCell(parrafo);
		cell =createCell(parrafo, 0, 1, 2);
		t.addCell(cell);
		int i=1;
		for (PagosV pago : ata.getLstPagosV()){
			
			if(ata.getCriterioPago()== 1 || ata.getCriterioPago()==3){
				totalVolumen += pago.getVolumen();	
			}			
			totalImporte += pago.getImporte();
			parrafo =  new Paragraph(i+"", TIMESROMAN08);
			cell = new PdfPCell(parrafo);
			cell =createCell(parrafo, 0, 2, 5);
			t.addCell(cell);
			parrafo =  new Paragraph(pago.getNombreComprador(), TIMESROMAN08);
			cell = new PdfPCell(parrafo);
			cell =createCell(parrafo, 0, 2, 5);
			t.addCell(cell);
			parrafo =  new Paragraph(pago.getNoCarta(), TIMESROMAN08);
			cell = new PdfPCell(parrafo);
			cell =createCell(parrafo, 0, 2, 5);
			t.addCell(cell);
			if(ata.getCriterioPago()== 2 || ata.getCriterioPago()==3){
				parrafo =  new Paragraph(pago.getEtapa(), TIMESROMAN08);
				cell = new PdfPCell(parrafo);
				cell =createCell(parrafo, 0, 1, 5);
				t.addCell(cell);	
			}
			if(ata.getCriterioPago()== 1 || ata.getCriterioPago()==3){
				parrafo =  new Paragraph(TextUtil.formateaNumeroComoVolumen(pago.getVolumen()), TIMESROMAN08);
				cell = new PdfPCell(parrafo);
				cell =createCell(parrafo, 0, 3, 5);
				t.addCell(cell);	
			}
			
			parrafo =  new Paragraph(TextUtil.formateaNumeroComoCantidad(pago.getImporte()), TIMESROMAN08);
			cell = new PdfPCell(parrafo);
			cell =createCell(parrafo, 0, 3, 13);
			t.addCell(cell);
			i++;
		}
		
		
		parrafo =  new Paragraph("", TIMESROMAN08);
		cell = new PdfPCell(parrafo);
		if(ata.getCriterioPago()==1){
			cell =createCell(parrafo, 2, 2, 1);
		}else if(ata.getCriterioPago()==2){
			cell =createCell(parrafo, 3, 2, 1);
		}else{
			cell =createCell(parrafo, 3, 2, 1);
		}
		
		t.addCell(cell);
		parrafo =  new Paragraph("TOTALES:", TIMESROMAN08);
		cell = new PdfPCell(parrafo);
		cell =createCell(parrafo, 0, 3, 5);
		t.addCell(cell);
		

		if(ata.getCriterioPago()==1 || ata.getCriterioPago()==3){
			parrafo =  new Paragraph(TextUtil.formateaNumeroComoVolumen(totalVolumen), TIMESROMAN08);
			cell = new PdfPCell(parrafo);
			cell =createCell(parrafo, 0, 3, 5);
			t.addCell(cell);	
		}
		
		
		parrafo =  new Paragraph("$ "+TextUtil.formateaNumeroComoCantidad(totalImporte), TIMESROMAN08);
		cell = new PdfPCell(parrafo);
		cell =createCell(parrafo, 0, 3, 13);
		t.addCell(cell);
		document.add(t);
		
		
	}

	public  void getEncabezado() throws MalformedURLException, IOException, DocumentException {
			PdfPCell cell = null;
			PdfPTable table1 = null;	
			Paragraph enunciado = null;
			/**TABLA DE LOGOS**/	
			Image sagarpa = Image.getInstance(ata.getRutaImagen());
			sagarpa.scalePercent(50, 50);
			table1= new PdfPTable(2);
			table1.setWidthPercentage(100);
			//enunciado=new Paragraph("ASERCA\nCoordinaci�n General de Comercializaci�n", TIMESROMAN12);
			enunciado = new Paragraph();
			enunciado.add(new Chunk("ASERCA\nCoordinaci�n General de Comercializaci�n", TIMESROMAN12));
			enunciado.add(new Chunk("\n\n"+ata.getLeyendaOficio().substring(0, 41)+"\n"+ata.getLeyendaOficio().substring(41, ata.getLeyendaOficio().length()), TIMESROMAN10NORMAL));
			cell =	createCell(null, 0, 2, 1, sagarpa);
			table1.addCell(cell);
			cell =	createCell(enunciado, 0, 3, 1, null);
			table1.addCell(cell);
	
			document.add(table1);
	}
	private void getLugarYFecha() throws DocumentException {
		parrafo = new Paragraph("N� de Oficio "+ata.getClaveOficio()+ata.getNoOficio()+ata.getAnioOficio()+"\nM�xico, D.F. "+ata.getFechaActual(), TIMESROMAN12);
		parrafo.setLeading(1,1);
		document.add(parrafo);	
	}
	private void getDestinatario() throws DocumentException {
		
		texto = new StringBuilder()
		.append(ata.getDestinatario().getNombre()).append("\n")
		.append(ata.getDestinatario().getPuesto()).append("\n")
		.append("P R E S E N T E");
		parrafo = new Paragraph(texto.toString(), TIMESROMAN12);
		parrafo.setLeading(1,1);
		document.add(parrafo);	
	
	}
	private void getCuerpo() throws DocumentException{
		parrafo= new Paragraph("Con referencia a la operaci�n del esquema "+ata.getNombrePrograma(), TIMESROMAN12);
		parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(parrafo);	
		addEmptyLine(1);
		StringBuilder texto = new StringBuilder();
		texto.append("Al respecto, le informo que han sido autorizados los apoyos correspondientes para que en ")
			 .append("lo conducente dentro del proceso de dispersi�n de los recursos para el pago de los apoyos ")
			 .append("del citado esquema, del ciclo ").append(ata.getCiclo()).append(", solicito a esa Direcci�n General a su cargo se realicen las acciones ") 
			 .append("necesarias para el registro y autorizaci�n de la Cuenta de Liquidaci�n Certificada (CLC) y ")
			 .append("notificaci�n a esta Coordinaci�n General, para realizar el proceso de vinculaci�n de archivos de pago TESOFE ")
			 .append("por medio del sistema SIAFF; dicha autorizaci�n de emisi�n de apoyos se refiere a la dispersi�n ")
			 .append("de recursos correspondientes a ").append(ata.getNoDepositos()).append(" ")
			 .append("dep�sitos en cuentas bancarias a trav�s de la Tesorer�a de la Federaci�n, ")
			 .append("lo que significa una erogaci�n de ").append(ata.getImporteLetra()).append(".");
		parrafo = new Paragraph(texto.toString(), TIMESROMAN12);
		parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(parrafo);	
		addEmptyLine(1);
		parrafo = new Paragraph("Sin m�s por el momento reciba un cordial saludo.", TIMESROMAN12);
		document.add(parrafo);	
	}
    private void getEmisor() throws DocumentException {
		parrafo = new Paragraph("A T E N T A M E N T E", TIMESROMAN12);
		parrafo.setAlignment(Element.ALIGN_CENTER);
		document.add(parrafo);	
		parrafo = new Paragraph(ata.getEmisor().getPuesto().toUpperCase(), TIMESROMAN12);
		parrafo.setAlignment(Element.ALIGN_CENTER);
		document.add(parrafo);
		addEmptyLine(3);
		parrafo = new Paragraph(ata.getEmisor().getNombre(), TIMESROMAN12);
		parrafo.setAlignment(Element.ALIGN_CENTER);
		document.add(parrafo);
	}

	private void addEmptyLine(int number) throws DocumentException {
		for (int i = 0; i < number; i++) {
			parrafo = new Paragraph("\n");
			document.add(parrafo);
		}
	}
	
	public void piePagina() throws DocumentException { 	
		piePagina = null;	
		PdfPCell celda = null;
		piePagina= new PdfPTable(2);
		piePagina.setTotalWidth(document.right() - document.left()); 
		piePagina.setWidths(new int[]{8,92});
		
		//Vo.Bo.
		parrafo = new Paragraph("Vo.Bo.", TIMESROMAN08);
		celda =	createCell(parrafo, 0, 2, 1);
		piePagina.addCell(celda);
		StringBuilder vobo = new StringBuilder();
		for(Personal p: ata.getLstPersonalVoBo()){
			vobo.append(p.getNombre()).append(" ").append(p.getPaterno()).append(" ")
			.append(p.getMaterno()!=null && !p.getMaterno().isEmpty()?p.getMaterno():"").append("\n");
			//.append(".- ").append(p.getPuesto()).append(".- ").append("Presente\n");
		}
		parrafo = new Paragraph(vobo.toString(), TIMESROMAN08);
		celda =	createCell(parrafo, 0, 2, 1);
		piePagina.addCell(celda);
		
		
		parrafo = new Paragraph("C.c.p", TIMESROMAN08);
		celda =	createCell(parrafo, 0, 2, 1);
		piePagina.addCell(celda);
		StringBuilder cpps = new StringBuilder();
		for(Personal p: ata.getLstPersonal()){
			cpps.append(p.getNombre()).append(" ").append(p.getPaterno()).append(" ")
			.append(p.getMaterno()!=null && !p.getMaterno().isEmpty()?p.getMaterno():"")
			.append(".- ").append(p.getPuesto()).append(".- ").append("Presente\n");
		}
		parrafo = new Paragraph(cpps.toString(), TIMESROMAN08);
		celda =	createCell(parrafo, 0, 2, 1);
		piePagina.addCell(celda);
		
		
		
		
		StringBuilder ubicacion = new StringBuilder();
		ubicacion.append("Av. Municipio Libre 377, Col. Santa Cruz Atoyac, Del. Benito Ju�rez  M�xico, DF 03310\n");
		ubicacion.append("t. +52 (55) 3871. 1000,  www.sagarpa.gob.mx");
		parrafo = new Paragraph(ubicacion.toString(), TIMESROMAN08LIGTH);
		parrafo.setLeading(1,1);
		celda =	createCell(parrafo, 2, 1, 1);
		piePagina.addCell(celda);
			
	} 
	
	public void onEndPage(PdfWriter writer, Document document) {
		PdfContentByte cb = writer.getDirectContent(); 
		try {
			System.out.println("pagina"+writer.getPageNumber());
			/*if(writer.getPageNumber()==1){
				System.out.println("p: "+writer.getPageNumber());
				piePagina();
			}*/
			piePagina();
			if(writer.getPageNumber()<=1){
				piePagina.writeSelectedRows(0, -1, document.left(), document.bottom()-5, cb);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	private PdfPCell createCell(Paragraph enunciado, int colspan, int alineamiento, int tipoBorde){
		return createCell(enunciado, colspan, alineamiento, tipoBorde, null);
	}
	private PdfPCell createCell(Paragraph enunciado, int colspan, int alineamiento, int tipoBorde, Image imagen)
	{
		PdfPCell cell = null;
		if(enunciado==null){
			cell =new PdfPCell(imagen);
		}else if(imagen==null){
			cell =new PdfPCell(enunciado);
		}else if(imagen!=null && enunciado!=null){
			cell =new PdfPCell(enunciado);
			cell.addElement(imagen);
		}
		switch(alineamiento) {
		   case 1: cell.setHorizontalAlignment(Element.ALIGN_CENTER); break;
		   case 2: cell.setHorizontalAlignment(Element.ALIGN_LEFT); break;
		   case 3: cell.setHorizontalAlignment(Element.ALIGN_RIGHT); break;
		   case 4: cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED); break;
		   case 5: cell.setHorizontalAlignment(Element.ALIGN_BOTTOM); break;
		}
		switch(tipoBorde) {
		   case 1: {
			   cell.setBorder(Rectangle.NO_BORDER); break;
		   }case 2: {
			   cell.setBorderWidthTop(1f);
			   cell.setBorderWidthLeft(1f);
			   cell.setBorderWidthRight(1f);
			   cell.setBorderWidthBottom(1f);
			   break;
		   }case 3: {
			   cell.setBorderWidthTop(1f);
			   cell.setBorderWidthLeft(1f);
			   cell.setBorderWidthRight(0f);
			   cell.setBorderWidthBottom(0f);
			   break;
		   }case 4: {
			   cell.setBorderWidthTop(1f);
			   cell.setBorderWidthLeft(0f);
			   cell.setBorderWidthRight(1f);
			   cell.setBorderWidthBottom(0f);
			   break;
		   } case 5: {
			   cell.setBorderWidthTop(0f);
			   cell.setBorderWidthLeft(1f);
			   cell.setBorderWidthRight(0f);
			   cell.setBorderWidthBottom(1f);   
			   break;
		   } case 6: {
			   cell.setBorderWidthTop(0f);
			   cell.setBorderWidthLeft(0f);
			   cell.setBorderWidthRight(1f);
			   cell.setBorderWidthBottom(1f);
			   break;
		   }case 7: {
			   cell.setBorderWidthTop(0f);
			   cell.setBorderWidthLeft(1f);
			   cell.setBorderWidthRight(0f);
			   cell.setBorderWidthBottom(0f);
			   break;
		   } case 8: {
			   cell.setBorderWidthTop(0f);
			   cell.setBorderWidthLeft(0f);
			   cell.setBorderWidthRight(1f);
			   cell.setBorderWidthBottom(0f);
			   break;
		   } case 9: {
			   cell.setBorderWidthTop(0f);
			   cell.setBorderWidthLeft(0f);
			   cell.setBorderWidthRight(0f);
			   cell.setBorderWidthBottom(1f);
			   break;
		   } case 10: {
			   cell.setBorderWidthTop(1f);
			   cell.setBorderWidthLeft(0f);
			   cell.setBorderWidthRight(0f);
			   cell.setBorderWidthBottom(0f);
			   break;
		   } case 11: {
			   cell.setBorderWidthTop(1f);
			   cell.setBorderWidthLeft(1f);
			   cell.setBorderWidthRight(0f);
			   cell.setBorderWidthBottom(1f);
			   break;
		   }case 12: {
			   cell.setBorderWidthTop(1f);
			   cell.setBorderWidthLeft(0f);
			   cell.setBorderWidthRight(1f);
			   cell.setBorderWidthBottom(1f);
			   break;
		   }case 13: {
			   cell.setBorderWidthTop(0f);
			   cell.setBorderWidthLeft(1f);
			   cell.setBorderWidthRight(1f);
			   cell.setBorderWidthBottom(1f);
			   break;
		   }
		   default: {
			   break;
		   }
		}
		if (colspan > 0){
			cell.setColspan(colspan);
		}
		cell.setPadding(4);
		return cell;
	}

}
