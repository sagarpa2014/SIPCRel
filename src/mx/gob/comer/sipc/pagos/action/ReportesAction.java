package mx.gob.comer.sipc.pagos.action;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import mx.gob.comer.sipc.dao.CatalogosDAO;
import mx.gob.comer.sipc.dao.ReportesDAO;
import mx.gob.comer.sipc.dao.UtileriasDAO;
import mx.gob.comer.sipc.domain.Ejercicios;
import mx.gob.comer.sipc.domain.Programa;
import mx.gob.comer.sipc.log.AppLogger;
import mx.gob.comer.sipc.utilerias.TextUtil;
import mx.gob.comer.sipc.utilerias.Utilerias;
import mx.gob.comer.sipc.vistas.domain.ReporteConcentradoPagosV;
import mx.gob.comer.sipc.vistas.domain.ReporteDetConcentradoPagosEtapasV;
import mx.gob.comer.sipc.vistas.domain.ReporteDetConcentradoPagosV;
import mx.gob.comer.sipc.vistas.domain.RespuestaPagosV;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.JDBCException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import jxl.CellType;
import jxl.Workbook;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


@SuppressWarnings({ "serial", "deprecation" })
public class ReportesAction extends ActionSupport implements SessionAware,  ServletContextAware{
	private Map<String, Object> session;
	private CatalogosDAO cDAO = new CatalogosDAO();
	private ReportesDAO rDAO = new ReportesDAO();
	private UtileriasDAO utileriasDAO = new UtileriasDAO();
	private List<Programa> lstProgramas;
	private List<Ejercicios> lstEjercicios;
	private List<RespuestaPagosV> lstRespuestaPagoV;
	private List<ReporteConcentradoPagosV> lstReporteConcentradoPagosV;
	private List<ReporteDetConcentradoPagosV> lstReporteDetConcentradoPagosV;
	private List<ReporteDetConcentradoPagosEtapasV> lstReporteDetConcentradoPagosEtapasV;	
	/*Campos del formulario*/
	private Date fechaInicio;
	private Date fechaFin;
	private int idPrograma;
	private String oficioCGC;
	private boolean bandera;
	private boolean band;
	
	private ServletContext context;
	private String xls;
	/*Campos del formulario*/
	private String tipo;
	private long idOficio;
	private String nombreArchivo;
	private RespuestaPagosV oficioPagosV;
	private int ejercicio;
	private int trimestre;
	private String programa;
	private Integer numeroEtapa;
	
	private Double totalVolumen1erTrimestre;
	private Double totalImporte1erTrimestre;
	private Double totalVolumen2doTrimestre;
	private Double totalImporte2doTrimestre;
	private Double totalVolumen3erTrimestre;
	private Double totalImporte3erTrimestre;
	private Double totalVolumen4toTrimestre;
	private Double totalImporte4toTrimestre;

	private Double totalVolumen1erTrimestreEtapaI;
	private Double totalImporte1erTrimestreEtapaI;
	private Double totalVolumen2doTrimestreEtapaI;
	private Double totalImporte2doTrimestreEtapaI;
	private Double totalVolumen3erTrimestreEtapaI;
	private Double totalImporte3erTrimestreEtapaI;
	private Double totalVolumen4toTrimestreEtapaI;
	private Double totalImporte4toTrimestreEtapaI;
	private Double totalVolumen1erTrimestreEtapaII;
	private Double totalImporte1erTrimestreEtapaII;
	private Double totalVolumen2doTrimestreEtapaII;
	private Double totalImporte2doTrimestreEtapaII;
	private Double totalVolumen3erTrimestreEtapaII;
	private Double totalImporte3erTrimestreEtapaII;
	private Double totalVolumen4toTrimestreEtapaII;
	private Double totalImporte4toTrimestreEtapaII;
	private Double totalVolumen1erTrimestreEtapaIII;
	private Double totalImporte1erTrimestreEtapaIII;
	private Double totalVolumen2doTrimestreEtapaIII;
	private Double totalImporte2doTrimestreEtapaIII;
	private Double totalVolumen3erTrimestreEtapaIII;
	private Double totalImporte3erTrimestreEtapaIII;
	private Double totalVolumen4toTrimestreEtapaIII;
	private Double totalImporte4toTrimestreEtapaIII;
	private Double totalVolumen1erTrimestreEtapaIV;
	private Double totalImporte1erTrimestreEtapaIV;
	private Double totalVolumen2doTrimestreEtapaIV;
	private Double totalImporte2doTrimestreEtapaIV;
	private Double totalVolumen3erTrimestreEtapaIV;
	private Double totalImporte3erTrimestreEtapaIV;
	private Double totalVolumen4toTrimestreEtapaIV;
	private Double totalImporte4toTrimestreEtapaIV;
	private Double totalVolumen1erTrimestreEtapaV;
	private Double totalImporte1erTrimestreEtapaV;
	private Double totalVolumen2doTrimestreEtapaV;
	private Double totalImporte2doTrimestreEtapaV;
	private Double totalVolumen3erTrimestreEtapaV;
	private Double totalImporte3erTrimestreEtapaV;
	private Double totalVolumen4toTrimestreEtapaV;
	private Double totalImporte4toTrimestreEtapaV;

	
	public String capturaReportePagosTesofe(){
		try{	
			/*Recupera catalogos de programa*/
			lstProgramas = cDAO.consultaPrograma(0);
		}catch (JDBCException e) {
			e.printStackTrace();
			AppLogger.error("errores","Ocurri� un error al recuperar el cat�logo de programas, debido a: "+e.getCause());
	    }
		return SUCCESS;
	}

	public String realizarConsultaPagosTesofe(){
		try{	
			session = ActionContext.getContext().getSession();
			
			String fechaInicioS = "";
			String fechaFinS = "";
			/*Validaci�n de rango de fecha*/
			if(fechaInicio !=null && !fechaInicio.equals("")){
				if(fechaFin != null && !fechaFin.equals("")){
					fechaInicioS = new SimpleDateFormat("yyyyMMdd").format(fechaInicio).toString();		
					fechaFinS = new SimpleDateFormat("yyyyMMdd").format(fechaFin).toString();
					if(Long.parseLong(fechaFinS)< Long.parseLong(fechaInicioS)){
						addActionError("La fecha inicio no puede ser mayor a la fecha final");
						return SUCCESS;
					}			
				     	
				}
			}
			
			if(fechaInicio != null && !fechaInicio.equals("")){
				fechaInicioS = new SimpleDateFormat("yyyy-MM-dd").format(fechaInicio).toString();
			}
			if(fechaFin != null && !fechaFin.equals("")){
				fechaFinS = new SimpleDateFormat("yyyy-MM-dd").format(fechaFin).toString();
			}
			//Consulta el reporte de acuerdo a los criterios seleccionados por el usuario
			lstRespuestaPagoV = rDAO.consultaRespuestaPagosV(idPrograma, oficioCGC, fechaInicioS, fechaFinS);
			
			
			bandera = true;
			//subir a session los criterios que el usuario selecciono.
			session.put("fechaInicioS", fechaInicioS);
			session.put("fechaFinS", fechaFinS);
			session.put("idPrograma", idPrograma);
			session.put("oficioCGC", oficioCGC);
			
			
		}catch (JDBCException e) {
			e.printStackTrace();
			AppLogger.error("errores","Ocurri� un error al realizar la consulta de pagos Tesofe, debido a: "+e.getCause());
		}finally{
			lstProgramas = cDAO.consultaPrograma(0);
		}
		
		return SUCCESS;
	}
	
	
	public String exportaPagosTESOFE(){
		try{
			if (session==null){
				session = ActionContext.getContext().getSession();	
			}
			setTipo("pagos");
			
			String rutaPlantilla = context.getRealPath("/WEB-INF/archivos/plantillas");
			// Leer la Ruta de salida configurada en la tabla parametros
			String rutaSalida = utileriasDAO.isolatedGetParametros("RUTA_PLANTILLA_PAGOS");
			if(!new File(rutaSalida).exists() ){
				rutaSalida =context.getRealPath("/WEB-INF/archivos/archivosPagos");
			}
	
			//Consulta el reporte de acuerdo a los criterios seleccionados por el usuario
			lstRespuestaPagoV = rDAO.isolatedConsultaRespuestaPagosV((Integer)session.get("idPrograma"), (String)session.get("oficioCGC"), (String)session.get("fechaInicioS"), (String)session.get("fechaFinS"));
			
			if(lstRespuestaPagoV!=null && lstRespuestaPagoV.size()>0){
				// Generar XLS
				nombreArchivo = contruyeArchivo(rutaPlantilla,rutaSalida,lstRespuestaPagoV);
			}
		}catch (JDBCException e) {
			e.printStackTrace();
			AppLogger.error("errores","Ocurrio un error al realizar la consulta de pagos Tesofe, debido a: "+e.getCause());
		}finally{
			lstProgramas = cDAO.isolatedConsultaPrograma(0);
		}		
		return "SUCCESS";
	}

	
	private String contruyeArchivo(String rutaPlantilla, String rutaSalida, List<RespuestaPagosV> lst){
		String xlsOut = new SimpleDateFormat("yyyyMMddHHmmss").format( new Date() )+"-PAGOSTESOFE.xls";
		Integer sumaTotalPagos = 0, sumaTotalAplicados = 0, sumaTotalRechazados = 0;
		Double sumaTotalImporte = 0.0, sumaTotalVolumen = 0.0, sumaTotalImporteAplicados = 0.0, sumaTotalImporteRechazados = 0.0;
		if(!rutaPlantilla.endsWith(File.separator)){
			rutaPlantilla += File.separator;
		}
		rutaPlantilla += "PLANTILLA_PAGOS.xls";
		if(!rutaSalida.endsWith(File.separator)){
			rutaSalida += File.separator;
		}
				
		Workbook workbook = null;
		WritableWorkbook copy = null;
		try{
			// Abrir plantilla
			workbook = Workbook.getWorkbook(new File(rutaPlantilla));
			// Hacer copia
			
			copy = Workbook.createWorkbook(new File(rutaSalida+xlsOut), workbook);
			WritableSheet sheet = copy.getSheet(0); 
			// Escribir datos
			
			WritableCellFormat cf = new WritableCellFormat();
			cf.setBorder( Border.ALL , BorderLineStyle.THIN );
	        // Fecha del Reporte
	        sheet.addCell( new Label(0,2, "RELACION DE DEPOSITOS ENVIADOS A TESOFE AL: "+new SimpleDateFormat("dd-MM-yyyy").format( new Date() ), cf));

			int row = 5;
			for(int i= 0;i<lst.size();i++){				
				int col = 0;
				RespuestaPagosV p = lst.get(i);
		        // Fecha del Oficio
		        sheet.addCell( new Label(col++,row, new SimpleDateFormat("dd-MM-yyyy").format(p.getFechaOficio()), cf));
				// Oficio
				sheet.addCell( new Label(col++,row, p.getNoOficio(), cf));
				// Depositos
				sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoCantidadSincomas(p.getTotalPagos()), cf));
				sumaTotalPagos+=p.getTotalPagos();
				// Importe
				sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getTotalImporte()), cf));
				sumaTotalImporte+=p.getTotalImporte();
				// Volumen
		        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getTotalVolumen()), cf));
		        sumaTotalVolumen+=p.getTotalVolumen();
		        // Ciclo
		        sheet.addCell( new Label(col++,row, p.getCicloCorto(), cf));
		        // Esquema de Apoyos
		        sheet.addCell( new Label(col++,row, p.getDescripcionCorta(), cf));
		        // Folio de la CLC
		        sheet.addCell( new Label(col++,row, p.getFolioClc().toString(), cf));
		        // Archivo de Envio
		        sheet.addCell( new Label(col++,row, p.getArchivoEnvio() , cf));
				// Depositos Aplicados
		        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoCantidadSincomas(p.getAplicados()), cf));
		        sumaTotalAplicados+=p.getAplicados();
		        // Importe Aplicado
				sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getTotalImporteAplicados()), cf));
				sumaTotalImporteAplicados+=p.getTotalImporteAplicados();
				// Depositos Rechazados
		        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoCantidadSincomas(p.getRechazados()), cf));
		        sumaTotalRechazados+=p.getRechazados();
		        // Importe Rechazado
				sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getTotalImporteRechazados()), cf));
				sumaTotalImporteRechazados+=p.getTotalImporteRechazados();
		        row++;
			}
			sheet.addCell( new Label(1,row, "TOTALES:", cf));
			// Totales Depositos
			sheet.addCell( new Label(2,row, TextUtil.formateaNumeroComoCantidadSincomas(sumaTotalPagos), cf));
			// Totales Importe
			sheet.addCell( new Label(3,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte), cf));
			// Totales Volumen
	        sheet.addCell( new Label(4,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen), cf));

	        sheet.addCell( new Label(8,row, "TOTALES:", cf));
			// Totales Depositos Aplicados
	        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoCantidadSincomas(sumaTotalAplicados), cf));
	        // Totales Importe Aplicado
			sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporteAplicados), cf));
			// Totales Depositos Rechazados
	        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoCantidadSincomas(sumaTotalRechazados), cf));
	        // Totales Importe Rechazado
			sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporteRechazados), cf));
	        
			// Leyenda del Encabezado
			WritableCell cell = null;
			cell = sheet.getWritableCell(7,0);
			if (cell.getType() == CellType.LABEL){
			  ((Label) cell).setString("LISTADO DE PAGOS TESOFE "+ Calendar.getInstance().get(Calendar.YEAR));
			} 			
		}catch(Exception e){
			System.err.println( e.getMessage() );
		}finally{
			// Cerrar y guardar copia
			if(copy!=null){
				try{
					copy.write(); 
					copy.close();
				}catch(Exception e){
					System.out.println( e.getMessage() );
				}
			}
			// Cerrar plantilla
			if(workbook!=null){
				workbook.close();
			}
		}
		setXls(xlsOut);
		return xlsOut;
	}
	
	public String detalleOficioPagos(){
		setOficioPagosV(rDAO.consultaOficiosPagoV(idOficio,null,0).get(0));
		
		return SUCCESS;
	}
	

	public String consigueArchivoExcel() throws Exception{
		try{
			String rutaSalida = cDAO.consultaParametros("RUTA_PLANTILLA_PAGOS");	
			if (!rutaSalida.endsWith(File.separator)){
				rutaSalida += File.separator;
			}
			Utilerias.entregarArchivo(rutaSalida,nombreArchivo,"xls");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String reporteConcentradoPagos(){
		try{	
			lstEjercicios = cDAO.consultaEjercicio(0);

			if((Boolean)session.get("band")==null){
				ejercicio = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()).toString());
				trimestre = 0;
				band=true;
				session.put("band", band);
			}			
	
			Utilerias.getResponseISO();
			lstReporteConcentradoPagosV = rDAO.consultaReporteConcentradoPagos(ejercicio);
			totalVolumen1erTrimestre = rDAO.consultaReporteConcetradoTotales(1, ejercicio);
			totalImporte1erTrimestre = rDAO.consultaReporteConcetradoTotales(2, ejercicio);
			totalVolumen2doTrimestre = rDAO.consultaReporteConcetradoTotales(3, ejercicio);
			totalImporte2doTrimestre = rDAO.consultaReporteConcetradoTotales(4, ejercicio);
			totalVolumen3erTrimestre = rDAO.consultaReporteConcetradoTotales(5, ejercicio);
			totalImporte3erTrimestre = rDAO.consultaReporteConcetradoTotales(6, ejercicio);
			totalVolumen4toTrimestre = rDAO.consultaReporteConcetradoTotales(7, ejercicio);
			totalImporte4toTrimestre = rDAO.consultaReporteConcetradoTotales(8, ejercicio);
			
			session.put("ejercicio", ejercicio);
			session.put("trimestre", trimestre);
			session.put("numeroEtapa", numeroEtapa);
		}catch (JDBCException e) {
			e.printStackTrace();
			AppLogger.error("errores","Ocurrio un error al recuperar consulta del reporte concentrado de pagos, debido a: "+e.getCause());
	    }
		return SUCCESS;
	}

	public String exportaReporteConcentradoPagos(){
		try{
			if (session==null){
				session = ActionContext.getContext().getSession();	
			}
			setTipo("reportec");
			
			String rutaPlantilla = context.getRealPath("/WEB-INF/archivos/plantillas");
			// Leer la Ruta de salida configurada en la tabla parametros
			String rutaSalida = utileriasDAO.isolatedGetParametros("RUTA_PLANTILLA_REPORTES");
			if(!new File(rutaSalida).exists() ){
				rutaSalida =context.getRealPath("/WEB-INF/archivos/reportes");
			}	

			//Consulta el reporte de acuerdo a los criterios seleccionados por el usuario
			lstReporteConcentradoPagosV = rDAO.insolatedConsultaReporteConcentradoPagos((Integer)session.get("ejercicio"));
			
			if(lstReporteConcentradoPagosV!=null && lstReporteConcentradoPagosV.size()>0){
				// Generar XLS
				nombreArchivo = contruyeArchivoReporteConcentrado(rutaPlantilla,rutaSalida,lstReporteConcentradoPagosV, (Integer)session.get("trimestre"));
			}
		}catch (JDBCException e) {
			e.printStackTrace();
			AppLogger.error("errores","Ocurrio un error al realizar la consulta del reporte concentrado de pagos, debido a: "+e.getCause());
		}finally{
			lstReporteConcentradoPagosV = rDAO.insolatedConsultaReporteConcentradoPagos((Integer)session.get("ejercicio"));
		}		
		return "SUCCESS";
	}

	
	private String contruyeArchivoReporteConcentrado(String rutaPlantilla, String rutaSalida, List<ReporteConcentradoPagosV> lst, Integer trimestre){
		String xlsOut = new SimpleDateFormat("yyyyMMddHHmmss").format( new Date() )+"-REPORTECONCENTRADOPAGOS.xls";
		Double sumaTotalVolumen1erTrimestre = 0.0, sumaTotalImporte1erTrimestre = 0.0, sumaTotalVolumen2doTrimestre = 0.0, sumaTotalImporte2doTrimestre = 0.0, sumaTotalVolumen3erTrimestre = 0.0, sumaTotalImporte3erTrimestre = 0.0, sumaTotalVolumen4toTrimestre = 0.0, sumaTotalImporte4toTrimestre = 0.0;
		Double sumaSubtotalVolumenTrimestres = 0.0, sumaSubtotalImporteTrimestres = 0.0;
		if(!rutaPlantilla.endsWith(File.separator)){
			rutaPlantilla += File.separator;
		}
		if(trimestre==3){
			rutaPlantilla += "PLANTILLA_CONCENTRADO_4TO.xls";
		}
		else if (trimestre==2) {
			rutaPlantilla += "PLANTILLA_CONCENTRADO_3ER.xls";
		}
		else if (trimestre==1) {
			rutaPlantilla += "PLANTILLA_CONCENTRADO_2DO.xls";
		}
		else if (trimestre==0) {
			rutaPlantilla += "PLANTILLA_CONCENTRADO_1ER.xls";
		}
		if(!rutaSalida.endsWith(File.separator)){
			rutaSalida += File.separator;
		}
				
		Workbook workbook = null;
		WritableWorkbook copy = null;
		try{
			// Abrir plantilla
			workbook = Workbook.getWorkbook(new File(rutaPlantilla));
			// Hacer copia
			copy = Workbook.createWorkbook(new File(rutaSalida+xlsOut), workbook);
			WritableSheet sheet = copy.getSheet(0); 
			// Escribir datos
			WritableCellFormat cf = new WritableCellFormat();
			cf.setBorder( Border.ALL , BorderLineStyle.THIN );

			int row = 13;
			for(int i= 0;i<lst.size();i++){				
				int col = 0;
				ReporteConcentradoPagosV p = lst.get(i);
		        // Programa
				sheet.addCell( new Label(0,row, p.getPrograma(), cf));
				if(trimestre==3||trimestre==2||trimestre==1||trimestre==0){
					// Volumen 1er. Trimestre
			        sheet.addCell( new Label(1,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestre()), cf));
			        sumaTotalVolumen1erTrimestre+=p.getVolumen1erTrimestre();
					// Importe 1er. Trimestre
					sheet.addCell( new Label(2,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestre()), cf));
					sumaTotalImporte1erTrimestre+=p.getImporte1erTrimestre();
					col = 5;
				}
				if(trimestre==3||trimestre==2||trimestre==1){
					// Volumen 2do. Trimestre
			        sheet.addCell( new Label(3,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen2doTrimestre()), cf));
			        sumaTotalVolumen2doTrimestre+=p.getVolumen2doTrimestre();
					// Importe 2do Trimestre
					sheet.addCell( new Label(4,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte2doTrimestre()), cf));
					sumaTotalImporte2doTrimestre+=p.getImporte2doTrimestre();
					col = 7;
				}
				if(trimestre==3||trimestre==2){
					// Volumen 3er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen3erTrimestre()), cf));
			        sumaTotalVolumen3erTrimestre+=p.getVolumen3erTrimestre();
					// Importe 3er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte3erTrimestre()), cf));
					sumaTotalImporte3erTrimestre+=p.getImporte3erTrimestre();
					col = 9;
				}
				if(trimestre==3){
					// Volumen 4to. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen4toTrimestre()), cf));
			        sumaTotalVolumen4toTrimestre+=p.getVolumen4toTrimestre();
					// Importe 4to. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte4toTrimestre()), cf));
					sumaTotalImporte4toTrimestre+=p.getImporte4toTrimestre();
					col = 11;
				}

				// Volumen Subtotal
		        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestre()+p.getVolumen2doTrimestre()+p.getVolumen3erTrimestre()+p.getVolumen4toTrimestre()), cf));
		        sumaSubtotalVolumenTrimestres+=p.getVolumen1erTrimestre()+p.getVolumen2doTrimestre()+p.getVolumen3erTrimestre()+p.getVolumen4toTrimestre();
				// Importe Subtotal
				sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestre()+p.getImporte2doTrimestre()+p.getImporte3erTrimestre()+p.getImporte4toTrimestre()), cf));
				sumaSubtotalImporteTrimestres+=p.getImporte1erTrimestre()+p.getImporte2doTrimestre()+p.getImporte3erTrimestre()+p.getImporte4toTrimestre();
		        // Estados
				sheet.addCell( new Label(col++,row, p.getEstados(), cf));
		        // Ciclo
				sheet.addCell( new Label(col++,row, p.getCiclo(), cf));
		        // Producto
				sheet.addCell( new Label(col++,row, p.getProducto(), cf));

				row++;
			}
			if(trimestre==0){
				// Totales Volumen 1er. Trimestre
		        sheet.addCell( new Label(1,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestre), cf));
				// Totales Importe 1er. Trimestre
				sheet.addCell( new Label(2,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestre), cf));
				// Totales Volumen Subtotal
		        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestres), cf));
				// Totales Importe Subtotal
				sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestres), cf));
			}
			if(trimestre==1){
				// Totales Volumen 1er. Trimestre
		        sheet.addCell( new Label(1,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestre), cf));
				// Totales Importe 1er. Trimestre
				sheet.addCell( new Label(2,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestre), cf));
				// Totales Volumen 2do. Trimestre
		        sheet.addCell( new Label(3,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestre), cf));
				// Totales Importe 2do. Trimestre
				sheet.addCell( new Label(4,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestre), cf));
				// Totales Volumen Subtotal
		        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestres), cf));
				// Totales Importe Subtotal
				sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestres), cf));				
			}
			if(trimestre==2){
				// Totales Volumen 1er. Trimestre
		        sheet.addCell( new Label(1,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestre), cf));
				// Totales Importe 1er. Trimestre
				sheet.addCell( new Label(2,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestre), cf));
				// Totales Volumen 2do. Trimestre
		        sheet.addCell( new Label(3,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestre), cf));
				// Totales Importe 2do. Trimestre
				sheet.addCell( new Label(4,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestre), cf));
				// Totales Volumen 3er. Trimestre
		        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestre), cf));
				// Totales Importe 3er. Trimestre
				sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestre), cf));
				// Totales Volumen Subtotal
		        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestres), cf));
				// Totales Importe Subtotal
				sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestres), cf));
			}
			if(trimestre==3){
				// Totales Volumen 1er. Trimestre
		        sheet.addCell( new Label(1,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestre), cf));
				// Totales Importe 1er. Trimestre
				sheet.addCell( new Label(2,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestre), cf));
				// Totales Volumen 2do. Trimestre
		        sheet.addCell( new Label(3,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestre), cf));
				// Totales Importe 2do. Trimestre
				sheet.addCell( new Label(4,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestre), cf));
				// Totales Volumen 3er. Trimestre
		        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestre), cf));
				// Totales Importe 3er. Trimestre
				sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestre), cf));
				// Totales Volumen 4to. Trimestre
		        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestre), cf));
				// Totales Importe 4to. Trimestre
				sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestre), cf));
				// Totales Volumen Subtotal
		        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestres), cf));
				// Totales Importe Subtotal
				sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestres), cf));
			}

			// Leyenda del Encabezado
//			WritableCell cell = null;
//			cell = sheet.getWritableCell(7,0);
//			if (cell.getType() == CellType.LABEL){
//			  ((Label) cell).setString("REPORTE GLOBAL DE PAGOS "+ Calendar.getInstance().get(Calendar.YEAR));
//			} 			
		}catch(Exception e){
			System.err.println( e.getMessage() );
		}finally{
			// Cerrar y guardar copia
			if(copy!=null){
				try{
					copy.write(); 
					copy.close();
				}catch(Exception e){
					System.out.println( e.getMessage() );
				}
			}
			// Cerrar plantilla
			if(workbook!=null){
				workbook.close();
			}
		}
		setXls(xlsOut);
		return xlsOut;
	}
	
	public String consigueArchivoExcelRepC() throws Exception{
		try{
			String rutaSalida = cDAO.consultaParametros("RUTA_PLANTILLA_REPORTES");	
			if (!rutaSalida.endsWith(File.separator)){
				rutaSalida += File.separator;
			}
			Utilerias.entregarArchivo(rutaSalida,nombreArchivo,"xls");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String reporteDetalleConcentrado(){
		try{		
			Utilerias.getResponseISO();
			lstReporteDetConcentradoPagosV = rDAO.consultaReporteDetConcentradoPagos(idPrograma, ejercicio);
			if(lstReporteDetConcentradoPagosV!=null){
				programa = lstReporteDetConcentradoPagosV.get(0).getPrograma();
			}

			totalVolumen1erTrimestre = rDAO.consultaReporteDetConcetradoTotales(1, idPrograma, ejercicio);
			totalImporte1erTrimestre = rDAO.consultaReporteDetConcetradoTotales(2, idPrograma, ejercicio);
			totalVolumen2doTrimestre = rDAO.consultaReporteDetConcetradoTotales(3, idPrograma, ejercicio);
			totalImporte2doTrimestre = rDAO.consultaReporteDetConcetradoTotales(4, idPrograma, ejercicio);
			totalVolumen3erTrimestre = rDAO.consultaReporteDetConcetradoTotales(5, idPrograma, ejercicio);
			totalImporte3erTrimestre = rDAO.consultaReporteDetConcetradoTotales(6, idPrograma, ejercicio);
			totalVolumen4toTrimestre = rDAO.consultaReporteDetConcetradoTotales(7, idPrograma, ejercicio);
			totalImporte4toTrimestre = rDAO.consultaReporteDetConcetradoTotales(8, idPrograma, ejercicio);
			session.put("idPrograma", idPrograma);
			session.put("ejercicio", ejercicio);
			session.put("trimestre", trimestre);

		}catch (JDBCException e) {
			e.printStackTrace();
			AppLogger.error("errores","Ocurrio un error al recuperar la consulta del reporte detalle concentrado de pagos, debido a: "+e.getCause());
	    }
		return SUCCESS;
	}
	
	public String exportaReporteDetConcentradoPagos(){
		try{
			if (session==null){
				session = ActionContext.getContext().getSession();	
			}
			setTipo("reported");
			
			String rutaPlantilla = context.getRealPath("/WEB-INF/archivos/plantillas");
			// Leer la Ruta de salida configurada en la tabla parametros
			String rutaSalida = utileriasDAO.isolatedGetParametros("RUTA_PLANTILLA_REPORTES");
			if(!new File(rutaSalida).exists() ){
				rutaSalida =context.getRealPath("/WEB-INF/archivos/reportes");
			}	

			//Consulta el reporte de acuerdo a los criterios seleccionados por el usuario
			lstReporteDetConcentradoPagosV = rDAO.insolatedConsultaReporteDetConcentradoPagos((Integer)session.get("idPrograma"), (Integer)session.get("ejercicio"));
			
			if(lstReporteDetConcentradoPagosV!=null && lstReporteDetConcentradoPagosV.size()>0){
				// Generar XLS
				nombreArchivo = contruyeArchivoReporteDetConcentrado(rutaPlantilla,rutaSalida,lstReporteDetConcentradoPagosV, (Integer)session.get("trimestre"));
			}
		}catch (JDBCException e) {
			e.printStackTrace();
			AppLogger.error("errores","Ocurrio un error al realizar la consulta del reporte detalle concentrado de pagos, debido a: "+e.getCause());
		}finally{
			lstReporteDetConcentradoPagosV = rDAO.insolatedConsultaReporteDetConcentradoPagos((Integer)session.get("idPrograma"), (Integer)session.get("ejercicio"));
		}		
		return "SUCCESS";
	}

	
	private String contruyeArchivoReporteDetConcentrado(String rutaPlantilla, String rutaSalida, List<ReporteDetConcentradoPagosV> lst, Integer trimestre){
		String xlsOut = new SimpleDateFormat("yyyyMMddHHmmss").format( new Date() )+"-REPORTEDETCONCENTRADOPAGOS.xls";
		Double sumaTotalVolumen1erTrimestre = 0.0, sumaTotalImporte1erTrimestre = 0.0, sumaTotalVolumen2doTrimestre = 0.0, sumaTotalImporte2doTrimestre = 0.0, sumaTotalVolumen3erTrimestre = 0.0, sumaTotalImporte3erTrimestre = 0.0, sumaTotalVolumen4toTrimestre = 0.0, sumaTotalImporte4toTrimestre = 0.0;
		Double sumaSubtotalVolumenTrimestres = 0.0, sumaSubtotalImporteTrimestres = 0.0;
		if(!rutaPlantilla.endsWith(File.separator)){
			rutaPlantilla += File.separator;
		}
		if(trimestre==3){
			rutaPlantilla += "PLANTILLA_DETCONCENTRADO_4TO.xls";
		}
		else if (trimestre==2) {
			rutaPlantilla += "PLANTILLA_DETCONCENTRADO_3ER.xls";
		}
		else if (trimestre==1) {
			rutaPlantilla += "PLANTILLA_DETCONCENTRADO_2DO.xls";
		}
		else if (trimestre==0) {
			rutaPlantilla += "PLANTILLA_DETCONCENTRADO_1ER.xls";
		}
		if(!rutaSalida.endsWith(File.separator)){
			rutaSalida += File.separator;
		}
				
		Workbook workbook = null;
		WritableWorkbook copy = null;
		try{
			// Abrir plantilla
			workbook = Workbook.getWorkbook(new File(rutaPlantilla));
			// Hacer copia
			copy = Workbook.createWorkbook(new File(rutaSalida+xlsOut), workbook);
			WritableSheet sheet = copy.getSheet(0); 
			// Escribir datos
			WritableCellFormat cf = new WritableCellFormat();
			cf.setBorder( Border.ALL , BorderLineStyle.THIN );

			int row = 13;
			for(int i= 0;i<lst.size();i++){				
				int col = 0;
				ReporteDetConcentradoPagosV p = lst.get(i);
		        // Programa
				sheet.addCell( new Label(0,4, p.getProgramaDesc(), cf));
		        // Comprador
				sheet.addCell( new Label(0,row, p.getComprador(), cf));
		        // Carta Adhesion
				sheet.addCell( new Label(2,row, p.getCartaAdhesion(), cf));
		        // Municipio
				sheet.addCell( new Label(3,row, p.getMunicipio(), cf));
		        // Estado
				sheet.addCell( new Label(4,row, p.getEntidadFederativa(), cf));

				if(trimestre==3||trimestre==2||trimestre==1||trimestre==0){
					// Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestre()), cf));
			        sumaTotalVolumen1erTrimestre+=p.getVolumen1erTrimestre();
					// Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestre()), cf));
					sumaTotalImporte1erTrimestre+=p.getImporte1erTrimestre();
					col = 9;
				}
				if(trimestre==3||trimestre==2||trimestre==1){
					// Volumen 2do. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen2doTrimestre()), cf));
			        sumaTotalVolumen2doTrimestre+=p.getVolumen2doTrimestre();
					// Importe 2do Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte2doTrimestre()), cf));
					sumaTotalImporte2doTrimestre+=p.getImporte2doTrimestre();
					col = 11;
				}
				if(trimestre==3||trimestre==2){
					// Volumen 3er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen3erTrimestre()), cf));
			        sumaTotalVolumen3erTrimestre+=p.getVolumen3erTrimestre();
					// Importe 3er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte3erTrimestre()), cf));
					sumaTotalImporte3erTrimestre+=p.getImporte3erTrimestre();
					col = 13;
				}
				if(trimestre==3){
					// Volumen 4to. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen4toTrimestre()), cf));
			        sumaTotalVolumen4toTrimestre+=p.getVolumen4toTrimestre();
					// Importe 4to. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte4toTrimestre()), cf));
					sumaTotalImporte4toTrimestre+=p.getImporte4toTrimestre();
					col = 15;
				}

				if(trimestre==0){
					// Volumen Subtotal
			        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestre()), cf));
			        sumaSubtotalVolumenTrimestres+=p.getVolumen1erTrimestre();
					// Importe Subtotal
					sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestre()), cf));
					sumaSubtotalImporteTrimestres+=p.getImporte1erTrimestre();
				} else if(trimestre==1){
					// Volumen Subtotal
			        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestre()+p.getVolumen2doTrimestre()), cf));
			        sumaSubtotalVolumenTrimestres+=p.getVolumen1erTrimestre()+p.getVolumen2doTrimestre();
					// Importe Subtotal
					sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestre()+p.getImporte2doTrimestre()), cf));
					sumaSubtotalImporteTrimestres+=p.getImporte1erTrimestre()+p.getImporte2doTrimestre();					
				} else if(trimestre==2){
					// Volumen Subtotal
			        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestre()+p.getVolumen2doTrimestre()+p.getVolumen3erTrimestre()), cf));
			        sumaSubtotalVolumenTrimestres+=p.getVolumen1erTrimestre()+p.getVolumen2doTrimestre()+p.getVolumen3erTrimestre();
					// Importe Subtotal
					sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestre()+p.getImporte2doTrimestre()+p.getImporte3erTrimestre()), cf));
					sumaSubtotalImporteTrimestres+=p.getImporte1erTrimestre()+p.getImporte2doTrimestre()+p.getImporte3erTrimestre();
				} else if(trimestre==3){
					// Volumen Subtotal
			        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestre()+p.getVolumen2doTrimestre()+p.getVolumen3erTrimestre()+p.getVolumen4toTrimestre()), cf));
			        sumaSubtotalVolumenTrimestres+=p.getVolumen1erTrimestre()+p.getVolumen2doTrimestre()+p.getVolumen3erTrimestre()+p.getVolumen4toTrimestre();
					// Importe Subtotal
					sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestre()+p.getImporte2doTrimestre()+p.getImporte3erTrimestre()+p.getImporte4toTrimestre()), cf));
					sumaSubtotalImporteTrimestres+=p.getImporte1erTrimestre()+p.getImporte2doTrimestre()+p.getImporte3erTrimestre()+p.getImporte4toTrimestre();
				}
				
		        // Estados
				sheet.addCell( new Label(col++,row, p.getEstados(), cf));
		        // Ciclo
				sheet.addCell( new Label(col++,row, p.getCiclo(), cf));
		        // Producto
				sheet.addCell( new Label(col++,row, p.getProducto(), cf));

				row++;
			}
			if(trimestre==0){
				// Totales Volumen 1er. Trimestre
		        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestre), cf));
				// Totales Importe 1er. Trimestre
				sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestre), cf));
				// Totales Volumen Subtotal
		        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestres), cf));
				// Totales Importe Subtotal
				sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestres), cf));
			}
			if(trimestre==1){
				// Totales Volumen 1er. Trimestre
		        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestre), cf));
				// Totales Importe 1er. Trimestre
				sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestre), cf));
				// Totales Volumen 2do. Trimestre
		        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestre), cf));
				// Totales Importe 2do. Trimestre
				sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestre), cf));
				// Totales Volumen Subtotal
		        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestres), cf));
				// Totales Importe Subtotal
				sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestres), cf));				
			}
			if(trimestre==2){
				// Totales Volumen 1er. Trimestre
		        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestre), cf));
				// Totales Importe 1er. Trimestre
				sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestre), cf));
				// Totales Volumen 2do. Trimestre
		        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestre), cf));
				// Totales Importe 2do. Trimestre
				sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestre), cf));
				// Totales Volumen 3er. Trimestre
		        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestre), cf));
				// Totales Importe 3er. Trimestre
				sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestre), cf));
				// Totales Volumen Subtotal
		        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestres), cf));
				// Totales Importe Subtotal
				sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestres), cf));
			}
			if(trimestre==3){
				// Totales Volumen 1er. Trimestre
		        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestre), cf));
				// Totales Importe 1er. Trimestre
				sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestre), cf));
				// Totales Volumen 2do. Trimestre
		        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestre), cf));
				// Totales Importe 2do. Trimestre
				sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestre), cf));
				// Totales Volumen 3er. Trimestre
		        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestre), cf));
				// Totales Importe 3er. Trimestre
				sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestre), cf));
				// Totales Volumen 4to. Trimestre
		        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestre), cf));
				// Totales Importe 4to. Trimestre
				sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestre), cf));
				// Totales Volumen Subtotal
		        sheet.addCell( new Label(15,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestres), cf));
				// Totales Importe Subtotal
				sheet.addCell( new Label(16,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestres), cf));
			}

			// Leyenda del Encabezado
//			WritableCell cell = null;
//			cell = sheet.getWritableCell(7,0);
//			if (cell.getType() == CellType.LABEL){
//			  ((Label) cell).setString("REPORTE GLOBAL DE PAGOS "+ Calendar.getInstance().get(Calendar.YEAR));
//			} 			
		}catch(Exception e){
			System.err.println( e.getMessage() );
		}finally{
			// Cerrar y guardar copia
			if(copy!=null){
				try{
					copy.write(); 
					copy.close();
				}catch(Exception e){
					System.out.println( e.getMessage() );
				}
			}
			// Cerrar plantilla
			if(workbook!=null){
				workbook.close();
			}
		}
		setXls(xlsOut);
		return xlsOut;
	}
	
	public String consigueArchivoExcelRepDC() throws Exception{
		try{
			String rutaSalida = cDAO.consultaParametros("RUTA_PLANTILLA_REPORTES");	
			if (!rutaSalida.endsWith(File.separator)){
				rutaSalida += File.separator;
			}
			Utilerias.entregarArchivo(rutaSalida,nombreArchivo,"xls");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}	

	public String reporteDetalleConcentradoEtapas(){
		try{		
			Utilerias.getResponseISO();
			lstReporteDetConcentradoPagosEtapasV = rDAO.consultaReporteDetConcentradoPagosEtapas(idPrograma, ejercicio);
			if(lstReporteDetConcentradoPagosEtapasV!=null){
				programa = lstReporteDetConcentradoPagosEtapasV.get(0).getPrograma();
			}

			totalVolumen1erTrimestreEtapaI = rDAO.consultaReporteDetConcetradoEtapasTotales(1, idPrograma, ejercicio);
			totalVolumen1erTrimestreEtapaII = rDAO.consultaReporteDetConcetradoEtapasTotales(11, idPrograma, ejercicio);
			totalVolumen1erTrimestreEtapaIII = rDAO.consultaReporteDetConcetradoEtapasTotales(12, idPrograma, ejercicio);
			totalVolumen1erTrimestreEtapaIV = rDAO.consultaReporteDetConcetradoEtapasTotales(13, idPrograma, ejercicio);
			totalVolumen1erTrimestreEtapaV = rDAO.consultaReporteDetConcetradoEtapasTotales(14, idPrograma, ejercicio);
			
			totalImporte1erTrimestreEtapaI = rDAO.consultaReporteDetConcetradoEtapasTotales(2, idPrograma, ejercicio);
			totalImporte1erTrimestreEtapaII = rDAO.consultaReporteDetConcetradoEtapasTotales(21, idPrograma, ejercicio);
			totalImporte1erTrimestreEtapaIII = rDAO.consultaReporteDetConcetradoEtapasTotales(22, idPrograma, ejercicio);
			totalImporte1erTrimestreEtapaIV = rDAO.consultaReporteDetConcetradoEtapasTotales(23, idPrograma, ejercicio);
			totalImporte1erTrimestreEtapaV = rDAO.consultaReporteDetConcetradoEtapasTotales(24, idPrograma, ejercicio);
			
			totalVolumen2doTrimestreEtapaI = rDAO.consultaReporteDetConcetradoEtapasTotales(3, idPrograma, ejercicio);
			totalVolumen2doTrimestreEtapaII = rDAO.consultaReporteDetConcetradoEtapasTotales(31, idPrograma, ejercicio);
			totalVolumen2doTrimestreEtapaIII = rDAO.consultaReporteDetConcetradoEtapasTotales(32, idPrograma, ejercicio);
			totalVolumen2doTrimestreEtapaIV = rDAO.consultaReporteDetConcetradoEtapasTotales(33, idPrograma, ejercicio);
			totalVolumen2doTrimestreEtapaV = rDAO.consultaReporteDetConcetradoEtapasTotales(34, idPrograma, ejercicio);
			
			totalImporte2doTrimestreEtapaI = rDAO.consultaReporteDetConcetradoEtapasTotales(4, idPrograma, ejercicio);
			totalImporte2doTrimestreEtapaII = rDAO.consultaReporteDetConcetradoEtapasTotales(41, idPrograma, ejercicio);
			totalImporte2doTrimestreEtapaIII = rDAO.consultaReporteDetConcetradoEtapasTotales(42, idPrograma, ejercicio);
			totalImporte2doTrimestreEtapaIV = rDAO.consultaReporteDetConcetradoEtapasTotales(43, idPrograma, ejercicio);
			totalImporte2doTrimestreEtapaV = rDAO.consultaReporteDetConcetradoEtapasTotales(44, idPrograma, ejercicio);
			
			totalVolumen3erTrimestreEtapaI = rDAO.consultaReporteDetConcetradoEtapasTotales(5, idPrograma, ejercicio);
			totalVolumen3erTrimestreEtapaII = rDAO.consultaReporteDetConcetradoEtapasTotales(51, idPrograma, ejercicio);
			totalVolumen3erTrimestreEtapaIII = rDAO.consultaReporteDetConcetradoEtapasTotales(52, idPrograma, ejercicio);
			totalVolumen3erTrimestreEtapaIV = rDAO.consultaReporteDetConcetradoEtapasTotales(53, idPrograma, ejercicio);
			totalVolumen3erTrimestreEtapaV = rDAO.consultaReporteDetConcetradoEtapasTotales(54, idPrograma, ejercicio);
			
			totalImporte3erTrimestreEtapaI = rDAO.consultaReporteDetConcetradoEtapasTotales(6, idPrograma, ejercicio);
			totalImporte3erTrimestreEtapaII = rDAO.consultaReporteDetConcetradoEtapasTotales(61, idPrograma, ejercicio);
			totalImporte3erTrimestreEtapaIII = rDAO.consultaReporteDetConcetradoEtapasTotales(62, idPrograma, ejercicio);
			totalImporte3erTrimestreEtapaIV = rDAO.consultaReporteDetConcetradoEtapasTotales(63, idPrograma, ejercicio);
			totalImporte3erTrimestreEtapaV = rDAO.consultaReporteDetConcetradoEtapasTotales(64, idPrograma, ejercicio);
			
			totalVolumen4toTrimestreEtapaI = rDAO.consultaReporteDetConcetradoEtapasTotales(7, idPrograma, ejercicio);
			totalVolumen4toTrimestreEtapaII = rDAO.consultaReporteDetConcetradoEtapasTotales(71, idPrograma, ejercicio);
			totalVolumen4toTrimestreEtapaIII = rDAO.consultaReporteDetConcetradoEtapasTotales(72, idPrograma, ejercicio);
			totalVolumen4toTrimestreEtapaIV = rDAO.consultaReporteDetConcetradoEtapasTotales(73, idPrograma, ejercicio);
			totalVolumen4toTrimestreEtapaV = rDAO.consultaReporteDetConcetradoEtapasTotales(74, idPrograma, ejercicio);
			
			totalImporte4toTrimestreEtapaI = rDAO.consultaReporteDetConcetradoEtapasTotales(8, idPrograma, ejercicio);
			totalImporte4toTrimestreEtapaII = rDAO.consultaReporteDetConcetradoEtapasTotales(81, idPrograma, ejercicio);
			totalImporte4toTrimestreEtapaIII = rDAO.consultaReporteDetConcetradoEtapasTotales(82, idPrograma, ejercicio);
			totalImporte4toTrimestreEtapaIV = rDAO.consultaReporteDetConcetradoEtapasTotales(83, idPrograma, ejercicio);
			totalImporte4toTrimestreEtapaV = rDAO.consultaReporteDetConcetradoEtapasTotales(84, idPrograma, ejercicio);
			
			session.put("idPrograma", idPrograma);
			session.put("ejercicio", ejercicio);
			session.put("trimestre", trimestre);
			session.put("numeroEtapa", numeroEtapa);
		}catch (JDBCException e) {
			e.printStackTrace();
			AppLogger.error("errores","Ocurrio un error al recuperar la consulta del reporte detalle concentrado de pagos, debido a: "+e.getCause());
	    }
		return SUCCESS;
	}
	
	public String exportaReporteDetConcentradoPagosEtapas(){
		try{
			if (session==null){
				session = ActionContext.getContext().getSession();	
			}
			setTipo("reported");
			
			String rutaPlantilla = context.getRealPath("/WEB-INF/archivos/plantillas");
			// Leer la Ruta de salida configurada en la tabla parametros
			String rutaSalida = utileriasDAO.isolatedGetParametros("RUTA_PLANTILLA_REPORTES");
			if(!new File(rutaSalida).exists() ){
				rutaSalida =context.getRealPath("/WEB-INF/archivos/reportes");
			}	

			//Consulta el reporte de acuerdo a los criterios seleccionados por el usuario
			lstReporteDetConcentradoPagosEtapasV = rDAO.insolatedConsultaReporteDetConcentradoPagosEtapas((Integer)session.get("idPrograma"), (Integer)session.get("ejercicio"));
			
			if(lstReporteDetConcentradoPagosEtapasV!=null && lstReporteDetConcentradoPagosEtapasV.size()>0){
				// Generar XLS
				nombreArchivo = contruyeArchivoReporteDetConcentradoEtapas(rutaPlantilla,rutaSalida,lstReporteDetConcentradoPagosEtapasV, (Integer)session.get("trimestre"), (Integer)session.get("numeroEtapa"));
			}
		}catch (JDBCException e) {
			e.printStackTrace();
			AppLogger.error("errores","Ocurrio un error al realizar la consulta del reporte detalle concentrado de pagos, debido a: "+e.getCause());
		}finally{
			lstReporteDetConcentradoPagosEtapasV = rDAO.insolatedConsultaReporteDetConcentradoPagosEtapas((Integer)session.get("idPrograma"), (Integer)session.get("ejercicio"));
		}		
		return "SUCCESS";
	}

	
	private String contruyeArchivoReporteDetConcentradoEtapas(String rutaPlantilla, String rutaSalida, List<ReporteDetConcentradoPagosEtapasV> lst, Integer trimestre, Integer numeroEtapa){
		String xlsOut = new SimpleDateFormat("yyyyMMddHHmmss").format( new Date() )+"-REPORTEDETCONCENTRADOPAGOSETAPAS.xls";
		Double sumaTotalVolumen1erTrimestreEtapa1 = 0.0, sumaTotalVolumen1erTrimestreEtapa2 = 0.0, sumaTotalVolumen1erTrimestreEtapa3 = 0.0, sumaTotalVolumen1erTrimestreEtapa4 = 0.0, sumaTotalVolumen1erTrimestreEtapa5 = 0.0;
		Double sumaTotalImporte1erTrimestreEtapa1 = 0.0, sumaTotalImporte1erTrimestreEtapa2 = 0.0, sumaTotalImporte1erTrimestreEtapa3 = 0.0, sumaTotalImporte1erTrimestreEtapa4 = 0.0, sumaTotalImporte1erTrimestreEtapa5 = 0.0;
		Double sumaTotalVolumen2doTrimestreEtapa1 = 0.0, sumaTotalVolumen2doTrimestreEtapa2 = 0.0, sumaTotalVolumen2doTrimestreEtapa3 = 0.0, sumaTotalVolumen2doTrimestreEtapa4 = 0.0, sumaTotalVolumen2doTrimestreEtapa5 = 0.0;
		Double sumaTotalImporte2doTrimestreEtapa1 = 0.0, sumaTotalImporte2doTrimestreEtapa2 = 0.0, sumaTotalImporte2doTrimestreEtapa3 = 0.0, sumaTotalImporte2doTrimestreEtapa4 = 0.0, sumaTotalImporte2doTrimestreEtapa5 = 0.0;
		Double sumaTotalVolumen3erTrimestreEtapa1 = 0.0, sumaTotalVolumen3erTrimestreEtapa2 = 0.0, sumaTotalVolumen3erTrimestreEtapa3 = 0.0, sumaTotalVolumen3erTrimestreEtapa4 = 0.0, sumaTotalVolumen3erTrimestreEtapa5 = 0.0;
		Double sumaTotalImporte3erTrimestreEtapa1 = 0.0, sumaTotalImporte3erTrimestreEtapa2 = 0.0, sumaTotalImporte3erTrimestreEtapa3 = 0.0, sumaTotalImporte3erTrimestreEtapa4 = 0.0, sumaTotalImporte3erTrimestreEtapa5 = 0.0;
		Double sumaTotalVolumen4toTrimestreEtapa1 = 0.0, sumaTotalVolumen4toTrimestreEtapa2 = 0.0, sumaTotalVolumen4toTrimestreEtapa3 = 0.0, sumaTotalVolumen4toTrimestreEtapa4 = 0.0, sumaTotalVolumen4toTrimestreEtapa5 = 0.0;
		Double sumaTotalImporte4toTrimestreEtapa1 = 0.0, sumaTotalImporte4toTrimestreEtapa2 = 0.0, sumaTotalImporte4toTrimestreEtapa3 = 0.0, sumaTotalImporte4toTrimestreEtapa4 = 0.0, sumaTotalImporte4toTrimestreEtapa5 = 0.0;
		Double sumaSubtotalVolumenTrimestresEtapa1 = 0.0, sumaSubtotalVolumenTrimestresEtapa2 = 0.0, sumaSubtotalVolumenTrimestresEtapa3 = 0.0, sumaSubtotalVolumenTrimestresEtapa4 = 0.0, sumaSubtotalVolumenTrimestresEtapa5 = 0.0;
		Double sumaSubtotalImporteTrimestresEtapa1 = 0.0, sumaSubtotalImporteTrimestresEtapa2 = 0.0, sumaSubtotalImporteTrimestresEtapa3 = 0.0, sumaSubtotalImporteTrimestresEtapa4 = 0.0 ,sumaSubtotalImporteTrimestresEtapa5 = 0.0;
		if(!rutaPlantilla.endsWith(File.separator)){
			rutaPlantilla += File.separator;
		}
		if(trimestre==3){
			if(numeroEtapa==5){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_4TO_ETAPAV.xls";
			} else if(numeroEtapa==4){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_4TO_ETAPAIV.xls";
			} else if(numeroEtapa==3){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_4TO_ETAPAIII.xls";
			} else if(numeroEtapa==2){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_4TO_ETAPAII.xls";
			} else if(numeroEtapa==1){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_4TO_ETAPAI.xls";
			}
		}
		else if (trimestre==2) {
			if(numeroEtapa==5){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_3ER_ETAPAV.xls";
			} else if(numeroEtapa==4){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_3ER_ETAPAIV.xls";
			} else if(numeroEtapa==3){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_3ER_ETAPAIII.xls";
			} else if(numeroEtapa==2){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_3ER_ETAPAII.xls";
			} else if(numeroEtapa==1){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_3ER_ETAPAI.xls";
			}
		}
		else if (trimestre==1) {
			if(numeroEtapa==5){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_2DO_ETAPAV.xls";
			} else if(numeroEtapa==4){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_2DO_ETAPAIV.xls";
			} else if(numeroEtapa==3){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_2DO_ETAPAIII.xls";
			} else if(numeroEtapa==2){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_2DO_ETAPAII.xls";
			} else if(numeroEtapa==1){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_2DO_ETAPAI.xls";
			}
		}
		else if (trimestre==0) {
			if(numeroEtapa==5){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_1ER_ETAPAV.xls";
			} else if(numeroEtapa==4){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_1ER_ETAPAIV.xls";
			} else if(numeroEtapa==3){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_1ER_ETAPAIII.xls";
			} else if(numeroEtapa==2){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_1ER_ETAPAII.xls";
			} else if(numeroEtapa==1){
				rutaPlantilla += "PLANTILLA_DETCONCENTRADO_1ER_ETAPAI.xls";
			}
		}
		if(!rutaSalida.endsWith(File.separator)){
			rutaSalida += File.separator;
		}

		Workbook workbook = null;
		WritableWorkbook copy = null;
		try{
			// Abrir plantilla
			workbook = Workbook.getWorkbook(new File(rutaPlantilla));
			// Hacer copia
			copy = Workbook.createWorkbook(new File(rutaSalida+xlsOut), workbook);
			WritableSheet sheet = copy.getSheet(0); 
			// Escribir datos
			WritableCellFormat cf = new WritableCellFormat();
			cf.setBorder( Border.ALL , BorderLineStyle.THIN );

			int row = 13;
			for(int i= 0;i<lst.size();i++){				
				int col = 0, colEtapa = 0;				
				ReporteDetConcentradoPagosEtapasV p = lst.get(i);
		        // Programa
				sheet.addCell( new Label(0,4, p.getProgramaDesc(), cf));
		        // Comprador
				sheet.addCell( new Label(0,row, p.getComprador(), cf));
		        // Carta Adhesion
				sheet.addCell( new Label(2,row, p.getCartaAdhesion(), cf));
		        // Municipio
				sheet.addCell( new Label(3,row, p.getMunicipio(), cf));
		        // Estado
				sheet.addCell( new Label(4,row, p.getEntidadFederativa(), cf));

				if(trimestre==3||trimestre==2||trimestre==1||trimestre==0){
					if (numeroEtapa>=1){
						// Volumen 1er. Trimestre						
				        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa1()), cf));
				        sumaTotalVolumen1erTrimestreEtapa1+=p.getVolumen1erTrimestreEtapa1();
						// Importe 1er. Trimestre
						sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa1()), cf));
						sumaTotalImporte1erTrimestreEtapa1+=p.getImporte1erTrimestreEtapa1();
						colEtapa = 6;
						col = 9;
					} 	
					if (numeroEtapa>=2){
						// Volumen 1er. Trimestre
				        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa2()), cf));
				        sumaTotalVolumen1erTrimestreEtapa2+=p.getVolumen1erTrimestreEtapa2();
						// Importe 1er. Trimestre
						sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa2()), cf));
						sumaTotalImporte1erTrimestreEtapa2+=p.getImporte1erTrimestreEtapa2();
						colEtapa = 8;
						col = 13;
					}
					if (numeroEtapa>=3){
						// Volumen 1er. Trimestre
				        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa3()), cf));
				        sumaTotalVolumen1erTrimestreEtapa3+=p.getVolumen1erTrimestreEtapa3();
						// Importe 1er. Trimestre
						sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa3()), cf));
						sumaTotalImporte1erTrimestreEtapa3+=p.getImporte1erTrimestreEtapa3();
						colEtapa = 10;
						col = 17;
					}
					if (numeroEtapa>=4){
						// Volumen 1er. Trimestre
				        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa4()), cf));
				        sumaTotalVolumen1erTrimestreEtapa4+=p.getVolumen1erTrimestreEtapa4();
						// Importe 1er. Trimestre
						sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa4()), cf));
						sumaTotalImporte1erTrimestreEtapa4+=p.getImporte1erTrimestreEtapa4();
						colEtapa = 12;
						col = 21;
					}
					if (numeroEtapa>=5){
						// Volumen 1er. Trimestre
				        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa5()), cf));
				        sumaTotalVolumen1erTrimestreEtapa5+=p.getVolumen1erTrimestreEtapa5();
						// Importe 1er. Trimestre
						sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa5()), cf));
						sumaTotalImporte1erTrimestreEtapa5+=p.getImporte1erTrimestreEtapa5();
						colEtapa = 14;
						col = 25;
					}
				}
				if(trimestre==3||trimestre==2||trimestre==1){
					if (numeroEtapa>=1){
						// Volumen 2do. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen2doTrimestreEtapa1()), cf));
				        sumaTotalVolumen2doTrimestreEtapa1+=p.getVolumen2doTrimestreEtapa1();
						// Importe 2do Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte2doTrimestreEtapa1()), cf));
						sumaTotalImporte2doTrimestreEtapa1+=p.getImporte2doTrimestreEtapa1();
						col = 11;
					}
					if (numeroEtapa>=2){
						// Volumen 2do. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen2doTrimestreEtapa2()), cf));
				        sumaTotalVolumen2doTrimestreEtapa2+=p.getVolumen2doTrimestreEtapa2();
						// Importe 2do Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte2doTrimestreEtapa2()), cf));
						sumaTotalImporte2doTrimestreEtapa2+=p.getImporte2doTrimestreEtapa2();
						col = 17;
					}
					if (numeroEtapa>=3){
						// Volumen 2do. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen2doTrimestreEtapa3()), cf));
				        sumaTotalVolumen2doTrimestreEtapa3+=p.getVolumen2doTrimestreEtapa3();
						// Importe 2do Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte2doTrimestreEtapa3()), cf));
						sumaTotalImporte2doTrimestreEtapa3+=p.getImporte2doTrimestreEtapa3();
						col = 23;
					}
					if (numeroEtapa>=4){
						// Volumen 2do. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen2doTrimestreEtapa4()), cf));
				        sumaTotalVolumen2doTrimestreEtapa4+=p.getVolumen2doTrimestreEtapa4();
						// Importe 2do Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte2doTrimestreEtapa4()), cf));
						sumaTotalImporte2doTrimestreEtapa4+=p.getImporte2doTrimestreEtapa4();
						col = 29;
					}
					if (numeroEtapa>=5){
						// Volumen 2do. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen2doTrimestreEtapa5()), cf));
				        sumaTotalVolumen2doTrimestreEtapa5+=p.getVolumen2doTrimestreEtapa5();
						// Importe 2do Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte2doTrimestreEtapa5()), cf));
						sumaTotalImporte2doTrimestreEtapa5+=p.getImporte2doTrimestreEtapa5();
						col = 35;
					}
				}
				if(trimestre==3||trimestre==2){					
					if (numeroEtapa>=1){
						// Volumen 3er. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen3erTrimestreEtapa1()), cf));
				        sumaTotalVolumen3erTrimestreEtapa1+=p.getVolumen3erTrimestreEtapa1();
				        // Importe 3er. Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte3erTrimestreEtapa1()), cf));
						sumaTotalImporte3erTrimestreEtapa1+=p.getImporte3erTrimestreEtapa1();
						col = 13;
					}
					if (numeroEtapa>=2){
						// Volumen 3er. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen3erTrimestreEtapa2()), cf));
				        sumaTotalVolumen3erTrimestreEtapa2+=p.getVolumen3erTrimestreEtapa2();
				        // Importe 3er. Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte3erTrimestreEtapa2()), cf));
						sumaTotalImporte3erTrimestreEtapa2+=p.getImporte3erTrimestreEtapa2();
						col = 21;
					}
					if (numeroEtapa>=3){
						// Volumen 3er. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen3erTrimestreEtapa3()), cf));
				        sumaTotalVolumen3erTrimestreEtapa3+=p.getVolumen3erTrimestreEtapa3();
				        // Importe 3er. Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte3erTrimestreEtapa3()), cf));
						sumaTotalImporte3erTrimestreEtapa3+=p.getImporte3erTrimestreEtapa3();
						col = 29;
					}
					if (numeroEtapa>=4){
						// Volumen 3er. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen3erTrimestreEtapa4()), cf));
				        sumaTotalVolumen3erTrimestreEtapa4+=p.getVolumen3erTrimestreEtapa4();
				        // Importe 3er. Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte3erTrimestreEtapa4()), cf));
						sumaTotalImporte3erTrimestreEtapa4+=p.getImporte3erTrimestreEtapa4();
						col = 37;
					}
					if (numeroEtapa>=5){
						// Volumen 3er. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen3erTrimestreEtapa5()), cf));
				        sumaTotalVolumen3erTrimestreEtapa5+=p.getVolumen3erTrimestreEtapa5();
				        // Importe 3er. Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte3erTrimestreEtapa5()), cf));
						sumaTotalImporte3erTrimestreEtapa5+=p.getImporte3erTrimestreEtapa5();
						col = 45;
					}					
				}
				if(trimestre==3){
					if (numeroEtapa>=1){
						// Volumen 4to. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen4toTrimestreEtapa1()), cf));
				        sumaTotalVolumen4toTrimestreEtapa1+=p.getVolumen4toTrimestreEtapa1();
				        // Importe 4to. Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte4toTrimestreEtapa1()), cf));
						sumaTotalImporte4toTrimestreEtapa1+=p.getImporte4toTrimestreEtapa1();
						col = 15;
					}					
					if (numeroEtapa>=2){
						// Volumen 4to. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen4toTrimestreEtapa2()), cf));
				        sumaTotalVolumen4toTrimestreEtapa2+=p.getVolumen4toTrimestreEtapa2();
				        // Importe 4to. Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte4toTrimestreEtapa2()), cf));
						sumaTotalImporte4toTrimestreEtapa2+=p.getImporte4toTrimestreEtapa2();
						col = 25;
					}
					if (numeroEtapa>=3){
						// Volumen 4to. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen4toTrimestreEtapa3()), cf));
				        sumaTotalVolumen4toTrimestreEtapa3+=p.getVolumen4toTrimestreEtapa3();
				        // Importe 4to. Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte4toTrimestreEtapa3()), cf));
						sumaTotalImporte4toTrimestreEtapa3+=p.getImporte4toTrimestreEtapa3();
						col = 35;
					}
					if (numeroEtapa>=4){
						// Volumen 4to. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen4toTrimestreEtapa4()), cf));
				        sumaTotalVolumen4toTrimestreEtapa4+=p.getVolumen4toTrimestreEtapa4();
				        // Importe 4to. Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte4toTrimestreEtapa4()), cf));
						sumaTotalImporte4toTrimestreEtapa4+=p.getImporte4toTrimestreEtapa4();
						col = 45;
					}
					if (numeroEtapa>=5){
						// Volumen 4to. Trimestre
				        sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen4toTrimestreEtapa5()), cf));
				        sumaTotalVolumen4toTrimestreEtapa5+=p.getVolumen4toTrimestreEtapa5();
				        // Importe 4to. Trimestre
						sheet.addCell( new Label(++colEtapa,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte4toTrimestreEtapa5()), cf));
						sumaTotalImporte4toTrimestreEtapa5+=p.getImporte4toTrimestreEtapa5();
						col = 55;
					}
				}

				if(trimestre==0){
					if (numeroEtapa>=1){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa1()), cf));
				        sumaSubtotalVolumenTrimestresEtapa1+=p.getVolumen1erTrimestreEtapa1();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa1()), cf));
						sumaSubtotalImporteTrimestresEtapa1+=p.getImporte1erTrimestreEtapa1();
					}
					if (numeroEtapa>=2){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa2()), cf));
				        sumaSubtotalVolumenTrimestresEtapa2+=p.getVolumen1erTrimestreEtapa2();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa2()), cf));
						sumaSubtotalImporteTrimestresEtapa2+=p.getImporte1erTrimestreEtapa2();
					}
					if (numeroEtapa>=3){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa3()), cf));
				        sumaSubtotalVolumenTrimestresEtapa3+=p.getVolumen1erTrimestreEtapa3();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa3()), cf));
						sumaSubtotalImporteTrimestresEtapa3+=p.getImporte1erTrimestreEtapa3();
					}
					if (numeroEtapa>=4){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa4()), cf));
				        sumaSubtotalVolumenTrimestresEtapa4+=p.getVolumen1erTrimestreEtapa4();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa4()), cf));
						sumaSubtotalImporteTrimestresEtapa4+=p.getImporte1erTrimestreEtapa4();
					}
					if (numeroEtapa>=5){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa5()), cf));
				        sumaSubtotalVolumenTrimestresEtapa5+=p.getVolumen1erTrimestreEtapa5();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa5()), cf));
						sumaSubtotalImporteTrimestresEtapa5+=p.getImporte1erTrimestreEtapa5();
					}
				} else if(trimestre==1){
					if (numeroEtapa>=1){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa1()+p.getVolumen2doTrimestreEtapa1()), cf));
				        sumaSubtotalVolumenTrimestresEtapa1+=p.getVolumen1erTrimestreEtapa1()+p.getVolumen2doTrimestreEtapa1();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa1()+p.getImporte2doTrimestreEtapa1()), cf));
						sumaSubtotalImporteTrimestresEtapa1+=p.getImporte1erTrimestreEtapa1()+p.getImporte2doTrimestreEtapa1();
					}
					if (numeroEtapa>=2){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa2()+p.getVolumen2doTrimestreEtapa2()), cf));
				        sumaSubtotalVolumenTrimestresEtapa2+=p.getVolumen1erTrimestreEtapa2()+p.getVolumen2doTrimestreEtapa2();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa2()+p.getImporte2doTrimestreEtapa2()), cf));
						sumaSubtotalImporteTrimestresEtapa2+=p.getImporte1erTrimestreEtapa2()+p.getImporte2doTrimestreEtapa2();
					}
					if (numeroEtapa>=3){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa3()+p.getVolumen2doTrimestreEtapa3()), cf));
				        sumaSubtotalVolumenTrimestresEtapa3+=p.getVolumen1erTrimestreEtapa3()+p.getVolumen2doTrimestreEtapa3();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa3()+p.getImporte2doTrimestreEtapa3()), cf));
						sumaSubtotalImporteTrimestresEtapa3+=p.getImporte1erTrimestreEtapa3()+p.getImporte2doTrimestreEtapa3();
					}
					if (numeroEtapa>=4){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa4()+p.getVolumen2doTrimestreEtapa4()), cf));
				        sumaSubtotalVolumenTrimestresEtapa4+=p.getVolumen1erTrimestreEtapa4()+p.getVolumen2doTrimestreEtapa4();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa4()+p.getImporte2doTrimestreEtapa4()), cf));
						sumaSubtotalImporteTrimestresEtapa4+=p.getImporte1erTrimestreEtapa4()+p.getImporte2doTrimestreEtapa4();
					}
					if (numeroEtapa>=5){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa5()+p.getVolumen2doTrimestreEtapa5()), cf));
				        sumaSubtotalVolumenTrimestresEtapa5+=p.getVolumen1erTrimestreEtapa5()+p.getVolumen2doTrimestreEtapa5();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa5()+p.getImporte2doTrimestreEtapa5()), cf));
						sumaSubtotalImporteTrimestresEtapa5+=p.getImporte1erTrimestreEtapa5()+p.getImporte2doTrimestreEtapa5();
					}
				} else if(trimestre==2){
					if (numeroEtapa>=1){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa1()+p.getVolumen2doTrimestreEtapa1()+p.getVolumen3erTrimestreEtapa1()), cf));
				        sumaSubtotalVolumenTrimestresEtapa1+=p.getVolumen1erTrimestreEtapa1()+p.getVolumen2doTrimestreEtapa1()+p.getVolumen3erTrimestreEtapa1();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa1()+p.getImporte2doTrimestreEtapa1()+p.getImporte3erTrimestreEtapa1()), cf));
						sumaSubtotalImporteTrimestresEtapa1+=p.getImporte1erTrimestreEtapa1()+p.getImporte2doTrimestreEtapa1()+p.getImporte3erTrimestreEtapa1();
					}
					if (numeroEtapa>=2){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa2()+p.getVolumen2doTrimestreEtapa2()+p.getVolumen3erTrimestreEtapa2()), cf));
				        sumaSubtotalVolumenTrimestresEtapa2+=p.getVolumen1erTrimestreEtapa2()+p.getVolumen2doTrimestreEtapa2()+p.getVolumen3erTrimestreEtapa2();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa2()+p.getImporte2doTrimestreEtapa2()+p.getImporte3erTrimestreEtapa2()), cf));
						sumaSubtotalImporteTrimestresEtapa2+=p.getImporte1erTrimestreEtapa2()+p.getImporte2doTrimestreEtapa2()+p.getImporte3erTrimestreEtapa2();
					}
					if (numeroEtapa>=3){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa3()+p.getVolumen2doTrimestreEtapa3()+p.getVolumen3erTrimestreEtapa3()), cf));
				        sumaSubtotalVolumenTrimestresEtapa3+=p.getVolumen1erTrimestreEtapa3()+p.getVolumen2doTrimestreEtapa3()+p.getVolumen3erTrimestreEtapa3();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa3()+p.getImporte2doTrimestreEtapa3()+p.getImporte3erTrimestreEtapa3()), cf));
						sumaSubtotalImporteTrimestresEtapa3+=p.getImporte1erTrimestreEtapa3()+p.getImporte2doTrimestreEtapa3()+p.getImporte3erTrimestreEtapa3();
					}
					if (numeroEtapa>=4){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa4()+p.getVolumen2doTrimestreEtapa4()+p.getVolumen3erTrimestreEtapa4()), cf));
				        sumaSubtotalVolumenTrimestresEtapa4+=p.getVolumen1erTrimestreEtapa4()+p.getVolumen2doTrimestreEtapa4()+p.getVolumen3erTrimestreEtapa4();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa4()+p.getImporte2doTrimestreEtapa4()+p.getImporte3erTrimestreEtapa4()), cf));
						sumaSubtotalImporteTrimestresEtapa4+=p.getImporte1erTrimestreEtapa4()+p.getImporte2doTrimestreEtapa4()+p.getImporte3erTrimestreEtapa4();
					}
					if (numeroEtapa>=5){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa5()+p.getVolumen2doTrimestreEtapa5()+p.getVolumen3erTrimestreEtapa5()), cf));
				        sumaSubtotalVolumenTrimestresEtapa5+=p.getVolumen1erTrimestreEtapa5()+p.getVolumen2doTrimestreEtapa5()+p.getVolumen3erTrimestreEtapa5();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa5()+p.getImporte2doTrimestreEtapa5()+p.getImporte3erTrimestreEtapa5()), cf));
						sumaSubtotalImporteTrimestresEtapa5+=p.getImporte1erTrimestreEtapa5()+p.getImporte2doTrimestreEtapa5()+p.getImporte3erTrimestreEtapa5();
					}
				} else if(trimestre==3){
					if (numeroEtapa>=1){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa1()+p.getVolumen2doTrimestreEtapa1()+p.getVolumen3erTrimestreEtapa1()+p.getVolumen4toTrimestreEtapa1()), cf));
				        sumaSubtotalVolumenTrimestresEtapa1+=p.getVolumen1erTrimestreEtapa1()+p.getVolumen2doTrimestreEtapa1()+p.getVolumen3erTrimestreEtapa1()+p.getVolumen4toTrimestreEtapa1();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa1()+p.getImporte2doTrimestreEtapa1()+p.getImporte3erTrimestreEtapa1()+p.getImporte4toTrimestreEtapa1()), cf));
						sumaSubtotalImporteTrimestresEtapa1+=p.getImporte1erTrimestreEtapa1()+p.getImporte2doTrimestreEtapa1()+p.getImporte3erTrimestreEtapa1()+p.getImporte4toTrimestreEtapa1();
					}
					if (numeroEtapa>=2){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa2()+p.getVolumen2doTrimestreEtapa2()+p.getVolumen3erTrimestreEtapa2()+p.getVolumen4toTrimestreEtapa2()), cf));
				        sumaSubtotalVolumenTrimestresEtapa2+=p.getVolumen1erTrimestreEtapa2()+p.getVolumen2doTrimestreEtapa2()+p.getVolumen3erTrimestreEtapa2()+p.getVolumen4toTrimestreEtapa2();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa2()+p.getImporte2doTrimestreEtapa2()+p.getImporte3erTrimestreEtapa2()+p.getImporte4toTrimestreEtapa2()), cf));
						sumaSubtotalImporteTrimestresEtapa2+=p.getImporte1erTrimestreEtapa2()+p.getImporte2doTrimestreEtapa2()+p.getImporte3erTrimestreEtapa2()+p.getImporte4toTrimestreEtapa2();
					}
					if (numeroEtapa>=3){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa3()+p.getVolumen2doTrimestreEtapa3()+p.getVolumen3erTrimestreEtapa3()+p.getVolumen4toTrimestreEtapa3()), cf));
				        sumaSubtotalVolumenTrimestresEtapa3+=p.getVolumen1erTrimestreEtapa3()+p.getVolumen2doTrimestreEtapa3()+p.getVolumen3erTrimestreEtapa3()+p.getVolumen4toTrimestreEtapa3();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa3()+p.getImporte2doTrimestreEtapa3()+p.getImporte3erTrimestreEtapa3()+p.getImporte4toTrimestreEtapa3()), cf));
						sumaSubtotalImporteTrimestresEtapa3+=p.getImporte1erTrimestreEtapa3()+p.getImporte2doTrimestreEtapa3()+p.getImporte3erTrimestreEtapa3()+p.getImporte4toTrimestreEtapa3();
					}
					if (numeroEtapa>=4){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa4()+p.getVolumen2doTrimestreEtapa4()+p.getVolumen3erTrimestreEtapa4()+p.getVolumen4toTrimestreEtapa4()), cf));
				        sumaSubtotalVolumenTrimestresEtapa4+=p.getVolumen1erTrimestreEtapa4()+p.getVolumen2doTrimestreEtapa4()+p.getVolumen3erTrimestreEtapa4()+p.getVolumen4toTrimestreEtapa4();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa4()+p.getImporte2doTrimestreEtapa4()+p.getImporte3erTrimestreEtapa4()+p.getImporte4toTrimestreEtapa4()), cf));
						sumaSubtotalImporteTrimestresEtapa4+=p.getImporte1erTrimestreEtapa4()+p.getImporte2doTrimestreEtapa4()+p.getImporte3erTrimestreEtapa4()+p.getImporte4toTrimestreEtapa4();
					}
					if (numeroEtapa>=5){
						// Volumen Subtotal
				        sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoVolumenSinComas(p.getVolumen1erTrimestreEtapa5()+p.getVolumen2doTrimestreEtapa5()+p.getVolumen3erTrimestreEtapa5()+p.getVolumen4toTrimestreEtapa5()), cf));
				        sumaSubtotalVolumenTrimestresEtapa5+=p.getVolumen1erTrimestreEtapa5()+p.getVolumen2doTrimestreEtapa5()+p.getVolumen3erTrimestreEtapa5()+p.getVolumen4toTrimestreEtapa5();
						// Importe Subtotal
						sheet.addCell( new Label(col++,row, TextUtil.formateaNumeroComoImporteSinComas(p.getImporte1erTrimestreEtapa5()+p.getImporte2doTrimestreEtapa5()+p.getImporte3erTrimestreEtapa5()+p.getImporte4toTrimestreEtapa5()), cf));
						sumaSubtotalImporteTrimestresEtapa5+=p.getImporte1erTrimestreEtapa5()+p.getImporte2doTrimestreEtapa5()+p.getImporte3erTrimestreEtapa5()+p.getImporte4toTrimestreEtapa5();
					}
				}
				
		        // Estados
				sheet.addCell( new Label(col++,row, p.getEstados(), cf));
		        // Ciclo
				sheet.addCell( new Label(col++,row, p.getCiclo(), cf));
		        // Producto
				sheet.addCell( new Label(col++,row, p.getProducto(), cf));

				row++;
			}
			if(trimestre==0){
				if (numeroEtapa==1){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
				}
				if (numeroEtapa==2){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(15,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(16,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));
				}
				if (numeroEtapa==3){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(17,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(18,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(19,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(20,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa3), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa3), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(21,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa3), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(22,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa3), cf));
				}								
				if (numeroEtapa==4){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(21,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(22,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(23,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(24,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa3), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa3), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(25,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa3), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(26,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa3), cf));					
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa4), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa4), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(27,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa4), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(28,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa4), cf));
				}								
				if (numeroEtapa==5){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(25,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(26,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(27,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(28,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa3), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa3), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(29,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa3), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(30,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa3), cf));					
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa4), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa4), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(31,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa4), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(32,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa4), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa5), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa5), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(33,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa5), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(34,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa5), cf));
				}								
			}
			if(trimestre==1){
				if (numeroEtapa==1){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
				}
				if (numeroEtapa==2){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(17,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(18,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa2), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(19,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(20,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));
				}
				if (numeroEtapa==3){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(23,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(24,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa2), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(25,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(26,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa3), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa3), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(15,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa3), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(16,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa3), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(27,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa3), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(28,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa3), cf));
				}
				if (numeroEtapa==4){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(29,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(30,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(15,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa2), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(16,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(31,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(32,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa3), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa3), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(17,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa3), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(18,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa3), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(33,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa3), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(34,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa3), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa4), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa4), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(19,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa4), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(20,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa4), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(35,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa4), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(36,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa4), cf));
				}
				if (numeroEtapa==5){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(15,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(16,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(35,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(36,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(17,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa2), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(18,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(37,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(38,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa3), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa3), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(19,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa3), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(20,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa3), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(39,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa3), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(40,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa3), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa4), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa4), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(21,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa4), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(22,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa4), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(41,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa4), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(42,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa4), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa5), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa5), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(23,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa5), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(24,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa5), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(43,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa5), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(44,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa5), cf));
				}
			}
			if(trimestre==2){
				if (numeroEtapa==1){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa1), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
				}
				if (numeroEtapa==2){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa1), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(21,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(22,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa2), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa2), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(15,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa2), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(16,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(23,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(24,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));
				}
				if (numeroEtapa==3){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(17,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa1), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(18,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(29,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(30,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa2), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa2), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(19,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa2), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(20,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(31,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(32,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa3), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa3), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(15,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa3), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(16,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa3), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(21,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa3), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(22,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa3), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(33,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa3), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(34,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa3), cf));
				}
				if (numeroEtapa==4){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(21,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa1), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(22,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(37,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(38,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(15,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa2), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(16,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa2), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(23,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa2), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(24,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(39,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(40,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa3), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa3), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(17,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa3), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(18,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa3), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(25,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa3), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(26,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa3), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(41,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa3), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(42,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa3), cf));					
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa4), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa4), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(19,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa4), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(20,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa4), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(27,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa4), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(28,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa4), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(43,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa4), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(44,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa4), cf));
				}
				if (numeroEtapa==5){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(15,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(16,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(25,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa1), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(26,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(45,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(46,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(17,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa2), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(18,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa2), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(27,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa2), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(28,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(47,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(48,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa3), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa3), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(19,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa3), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(20,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa3), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(29,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa3), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(30,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa3), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(49,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa3), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(50,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa3), cf));					
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa4), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa4), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(21,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa4), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(22,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa4), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(31,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa4), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(32,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa4), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(51,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa4), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(52,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa4), cf));					
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa5), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa5), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(23,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa5), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(24,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa5), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(33,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa5), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(34,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa5), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(53,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa5), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(54,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa5), cf));
				}
			}
			if(trimestre==3){
				if (numeroEtapa==1){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa1), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa1), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa1), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(15,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(16,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));
				}
				if (numeroEtapa==2){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa1), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa1), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(17,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa1), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(18,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(25,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(26,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));					
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa2), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa2), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(15,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa2), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(16,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa2), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(19,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa2), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(20,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(27,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(28,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));
				}
				if (numeroEtapa==3){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(17,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa1), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(18,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa1), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(23,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa1), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(24,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(35,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(36,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));					
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa2), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa2), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(19,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa2), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(20,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa2), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(25,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa2), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(26,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(37,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(38,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));					
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa3), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa3), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(15,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa3), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(16,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa3), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(21,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa3), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(22,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa3), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(27,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa3), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(28,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa3), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(39,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa3), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(40,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa3), cf));
				}
				if (numeroEtapa==4){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(21,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa1), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(22,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa1), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(29,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa1), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(30,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(45,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(46,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));					
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(15,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa2), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(16,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa2), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(23,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa2), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(24,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa2), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(31,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa2), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(32,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(47,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(48,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));					
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa3), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa3), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(17,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa3), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(18,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa3), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(25,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa3), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(26,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa3), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(33,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa3), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(34,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa3), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(49,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa3), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(50,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa3), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa4), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa4), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(19,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa4), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(20,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa4), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(27,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa4), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(28,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa4), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(35,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa4), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(36,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa4), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(51,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa4), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(52,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa4), cf));
				}
				if (numeroEtapa==5){
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(5,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa1), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(6,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa1), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(15,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa1), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(16,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa1), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(25,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa1), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(26,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa1), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(35,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa1), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(36,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa1), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(55,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa1), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(56,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa1), cf));					
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(7,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa2), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(8,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa2), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(17,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa2), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(18,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa2), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(27,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa2), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(28,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa2), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(37,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa2), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(38,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa2), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(57,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa2), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(58,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa2), cf));					
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(9,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa3), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(10,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa3), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(19,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa3), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(20,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa3), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(29,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa3), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(30,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa3), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(39,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa3), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(40,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa3), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(59,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa3), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(60,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa3), cf));
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(11,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa4), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(12,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa4), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(21,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa4), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(22,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa4), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(31,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa4), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(32,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa4), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(41,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa4), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(42,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa4), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(61,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa4), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(62,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa4), cf));					
					// Totales Volumen 1er. Trimestre
			        sheet.addCell( new Label(13,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen1erTrimestreEtapa5), cf));
					// Totales Importe 1er. Trimestre
					sheet.addCell( new Label(14,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte1erTrimestreEtapa5), cf));
					// Totales Volumen 2do. Trimestre
			        sheet.addCell( new Label(23,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen2doTrimestreEtapa5), cf));
					// Totales Importe 2do. Trimestre
					sheet.addCell( new Label(24,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte2doTrimestreEtapa5), cf));
					// Totales Volumen 3er. Trimestre
			        sheet.addCell( new Label(33,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen3erTrimestreEtapa5), cf));
					// Totales Importe 3er. Trimestre
					sheet.addCell( new Label(34,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte3erTrimestreEtapa5), cf));
					// Totales Volumen 4to. Trimestre
			        sheet.addCell( new Label(43,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaTotalVolumen4toTrimestreEtapa5), cf));
					// Totales Importe 4to. Trimestre
					sheet.addCell( new Label(44,row, TextUtil.formateaNumeroComoImporteSinComas(sumaTotalImporte4toTrimestreEtapa5), cf));
					// Totales Volumen Subtotal
			        sheet.addCell( new Label(63,row, TextUtil.formateaNumeroComoVolumenSinComas(sumaSubtotalVolumenTrimestresEtapa5), cf));
					// Totales Importe Subtotal
					sheet.addCell( new Label(64,row, TextUtil.formateaNumeroComoImporteSinComas(sumaSubtotalImporteTrimestresEtapa5), cf));
				}
			}

			// Leyenda del Encabezado
//			WritableCell cell = null;
//			cell = sheet.getWritableCell(7,0);
//			if (cell.getType() == CellType.LABEL){
//			  ((Label) cell).setString("REPORTE GLOBAL DE PAGOS "+ Calendar.getInstance().get(Calendar.YEAR));
//			} 			
		}catch(Exception e){
			System.err.println( e.getMessage() );
		}finally{
			// Cerrar y guardar copia
			if(copy!=null){
				try{
					copy.write(); 
					copy.close();
				}catch(Exception e){
					System.out.println( e.getMessage() );
				}
			}
			// Cerrar plantilla
			if(workbook!=null){
				workbook.close();
			}
		}

		setXls(xlsOut);
		return xlsOut;
	}
	
	public String consigueArchivoExcelRepDEC() throws Exception{
		try{
			String rutaSalida = cDAO.consultaParametros("RUTA_PLANTILLA_REPORTES");	
			if (!rutaSalida.endsWith(File.separator)){
				rutaSalida += File.separator;
			}
			Utilerias.entregarArchivo(rutaSalida,nombreArchivo,"xls");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}	
	
	/*Lista*/
	public List<Programa> getLstProgramas() {
		return lstProgramas;
	}
	public void setLstProgramas(List<Programa> lstProgramas) {
		this.lstProgramas = lstProgramas;
	}
	
	public List<RespuestaPagosV> getLstRespuestaPagoV() {
		return lstRespuestaPagoV;
	}

	public void setLstRespuestaPagoV(List<RespuestaPagosV> lstRespuestaPagoV) {
		this.lstRespuestaPagoV = lstRespuestaPagoV;
	}

	/*Campos del formulario*/
	public Date getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}
	
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public int getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(int idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getOficioCGC() {
		return oficioCGC;
	}

	public void setOficioCGC(String oficioCGC) {
		this.oficioCGC = oficioCGC;
	}	
	
	public boolean isBandera() {
		return bandera;
	}

	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}

	/**
	 * Implementar la interfaz SessionAware, para session de usuario
	 */ 
	public void setSession(Map<String, Object> session) {
	    this.session = session;
	}
	
	public Map<String, Object> getSession() {
	    return session;
	}
	
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
	

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}
	
	public boolean isBand() {
		return band;
	}

	public void setBand(boolean band) {
		this.band = band;
	}	

	public int getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(int ejercicio) {
		this.ejercicio = ejercicio;
	}

	public int getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(int trimestre) {
		this.trimestre = trimestre;
	}

	public Double getTotalVolumen1erTrimestre() {
		return totalVolumen1erTrimestre;
	}

	public void setTotalVolumen1erTrimestre(Double totalVolumen1erTrimestre) {
		this.totalVolumen1erTrimestre = totalVolumen1erTrimestre;
	}

	public Double getTotalImporte1erTrimestre() {
		return totalImporte1erTrimestre;
	}

	public void setTotalImporte1erTrimestre(Double totalImporte1erTrimestre) {
		this.totalImporte1erTrimestre = totalImporte1erTrimestre;
	}

	public Double getTotalVolumen2doTrimestre() {
		return totalVolumen2doTrimestre;
	}

	public void setTotalVolumen2doTrimestre(Double totalVolumen2doTrimestre) {
		this.totalVolumen2doTrimestre = totalVolumen2doTrimestre;
	}

	public Double getTotalImporte2doTrimestre() {
		return totalImporte2doTrimestre;
	}

	public void setTotalImporte2doTrimestre(Double totalImporte2doTrimestre) {
		this.totalImporte2doTrimestre = totalImporte2doTrimestre;
	}

	public Double getTotalVolumen3erTrimestre() {
		return totalVolumen3erTrimestre;
	}

	public void setTotalVolumen3erTrimestre(Double totalVolumen3erTrimestre) {
		this.totalVolumen3erTrimestre = totalVolumen3erTrimestre;
	}

	public Double getTotalImporte3erTrimestre() {
		return totalImporte3erTrimestre;
	}

	public void setTotalImporte3erTrimestre(Double totalImporte3erTrimestre) {
		this.totalImporte3erTrimestre = totalImporte3erTrimestre;
	}

	public Double getTotalVolumen4toTrimestre() {
		return totalVolumen4toTrimestre;
	}

	public void setTotalVolumen4toTrimestre(Double totalVolumen4toTrimestre) {
		this.totalVolumen4toTrimestre = totalVolumen4toTrimestre;
	}

	public Double getTotalImporte4toTrimestre() {
		return totalImporte4toTrimestre;
	}

	public void setTotalImporte4toTrimestre(Double totalImporte4toTrimestre) {
		this.totalImporte4toTrimestre = totalImporte4toTrimestre;
	}

	public void setXls(String xls) {
		this.xls = xls;
	}

	public String getXls() {
		return xls;
	}
	
	public long getIdOficio() {
		return idOficio;
	}
	public void setIdOficio(long idOficio) {
		this.idOficio = idOficio;
	}

	public void setOficioPagosV(RespuestaPagosV oficioPagosV) {
		this.oficioPagosV = oficioPagosV;
	}

	public RespuestaPagosV getOficioPagosV() {
		return oficioPagosV;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public List<Ejercicios> getLstEjercicios() {
		return lstEjercicios;
	}

	public void setLstEjercicios(List<Ejercicios> lstEjercicios) {
		this.lstEjercicios = lstEjercicios;
	}

	public List<ReporteConcentradoPagosV> getLstReporteConcentradoPagosV() {
		return lstReporteConcentradoPagosV;
	}

	public void setLstReporteConcentradoPagosV(
			List<ReporteConcentradoPagosV> lstReporteConcentradoPagosV) {
		this.lstReporteConcentradoPagosV = lstReporteConcentradoPagosV;
	}

	public List<ReporteDetConcentradoPagosV> getLstReporteDetConcentradoPagosV() {
		return lstReporteDetConcentradoPagosV;
	}

	public void setLstReporteDetConcentradoPagosV(
			List<ReporteDetConcentradoPagosV> lstReporteDetConcentradoPagosV) {
		this.lstReporteDetConcentradoPagosV = lstReporteDetConcentradoPagosV;
	}
	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getPrograma() {
		return programa;
	}

	public List<ReporteDetConcentradoPagosEtapasV> getLstReporteDetConcentradoPagosEtapasV() {
		return lstReporteDetConcentradoPagosEtapasV;
	}

	public void setLstReporteDetConcentradoPagosEtapasV(
			List<ReporteDetConcentradoPagosEtapasV> lstReporteDetConcentradoPagosEtapasV) {
		this.lstReporteDetConcentradoPagosEtapasV = lstReporteDetConcentradoPagosEtapasV;
	}

	public Double getTotalVolumen1erTrimestreEtapaI() {
		return totalVolumen1erTrimestreEtapaI;
	}

	public void setTotalVolumen1erTrimestreEtapaI(
			Double totalVolumen1erTrimestreEtapaI) {
		this.totalVolumen1erTrimestreEtapaI = totalVolumen1erTrimestreEtapaI;
	}

	public Double getTotalImporte1erTrimestreEtapaI() {
		return totalImporte1erTrimestreEtapaI;
	}

	public void setTotalImporte1erTrimestreEtapaI(
			Double totalImporte1erTrimestreEtapaI) {
		this.totalImporte1erTrimestreEtapaI = totalImporte1erTrimestreEtapaI;
	}

	public Double getTotalVolumen2doTrimestreEtapaI() {
		return totalVolumen2doTrimestreEtapaI;
	}

	public void setTotalVolumen2doTrimestreEtapaI(
			Double totalVolumen2doTrimestreEtapaI) {
		this.totalVolumen2doTrimestreEtapaI = totalVolumen2doTrimestreEtapaI;
	}

	public Double getTotalImporte2doTrimestreEtapaI() {
		return totalImporte2doTrimestreEtapaI;
	}

	public void setTotalImporte2doTrimestreEtapaI(
			Double totalImporte2doTrimestreEtapaI) {
		this.totalImporte2doTrimestreEtapaI = totalImporte2doTrimestreEtapaI;
	}

	public Double getTotalVolumen3erTrimestreEtapaI() {
		return totalVolumen3erTrimestreEtapaI;
	}

	public void setTotalVolumen3erTrimestreEtapaI(
			Double totalVolumen3erTrimestreEtapaI) {
		this.totalVolumen3erTrimestreEtapaI = totalVolumen3erTrimestreEtapaI;
	}

	public Double getTotalImporte3erTrimestreEtapaI() {
		return totalImporte3erTrimestreEtapaI;
	}

	public void setTotalImporte3erTrimestreEtapaI(
			Double totalImporte3erTrimestreEtapaI) {
		this.totalImporte3erTrimestreEtapaI = totalImporte3erTrimestreEtapaI;
	}

	public Double getTotalVolumen4toTrimestreEtapaI() {
		return totalVolumen4toTrimestreEtapaI;
	}

	public void setTotalVolumen4toTrimestreEtapaI(
			Double totalVolumen4toTrimestreEtapaI) {
		this.totalVolumen4toTrimestreEtapaI = totalVolumen4toTrimestreEtapaI;
	}

	public Double getTotalImporte4toTrimestreEtapaI() {
		return totalImporte4toTrimestreEtapaI;
	}

	public void setTotalImporte4toTrimestreEtapaI(
			Double totalImporte4toTrimestreEtapaI) {
		this.totalImporte4toTrimestreEtapaI = totalImporte4toTrimestreEtapaI;
	}

	public Double getTotalVolumen1erTrimestreEtapaII() {
		return totalVolumen1erTrimestreEtapaII;
	}

	public void setTotalVolumen1erTrimestreEtapaII(
			Double totalVolumen1erTrimestreEtapaII) {
		this.totalVolumen1erTrimestreEtapaII = totalVolumen1erTrimestreEtapaII;
	}

	public Double getTotalImporte1erTrimestreEtapaII() {
		return totalImporte1erTrimestreEtapaII;
	}

	public void setTotalImporte1erTrimestreEtapaII(
			Double totalImporte1erTrimestreEtapaII) {
		this.totalImporte1erTrimestreEtapaII = totalImporte1erTrimestreEtapaII;
	}

	public Double getTotalVolumen2doTrimestreEtapaII() {
		return totalVolumen2doTrimestreEtapaII;
	}

	public void setTotalVolumen2doTrimestreEtapaII(
			Double totalVolumen2doTrimestreEtapaII) {
		this.totalVolumen2doTrimestreEtapaII = totalVolumen2doTrimestreEtapaII;
	}

	public Double getTotalImporte2doTrimestreEtapaII() {
		return totalImporte2doTrimestreEtapaII;
	}

	public void setTotalImporte2doTrimestreEtapaII(
			Double totalImporte2doTrimestreEtapaII) {
		this.totalImporte2doTrimestreEtapaII = totalImporte2doTrimestreEtapaII;
	}

	public Double getTotalVolumen3erTrimestreEtapaII() {
		return totalVolumen3erTrimestreEtapaII;
	}

	public void setTotalVolumen3erTrimestreEtapaII(
			Double totalVolumen3erTrimestreEtapaII) {
		this.totalVolumen3erTrimestreEtapaII = totalVolumen3erTrimestreEtapaII;
	}

	public Double getTotalImporte3erTrimestreEtapaII() {
		return totalImporte3erTrimestreEtapaII;
	}

	public void setTotalImporte3erTrimestreEtapaII(
			Double totalImporte3erTrimestreEtapaII) {
		this.totalImporte3erTrimestreEtapaII = totalImporte3erTrimestreEtapaII;
	}

	public Double getTotalVolumen4toTrimestreEtapaII() {
		return totalVolumen4toTrimestreEtapaII;
	}

	public void setTotalVolumen4toTrimestreEtapaII(
			Double totalVolumen4toTrimestreEtapaII) {
		this.totalVolumen4toTrimestreEtapaII = totalVolumen4toTrimestreEtapaII;
	}

	public Double getTotalImporte4toTrimestreEtapaII() {
		return totalImporte4toTrimestreEtapaII;
	}

	public void setTotalImporte4toTrimestreEtapaII(
			Double totalImporte4toTrimestreEtapaII) {
		this.totalImporte4toTrimestreEtapaII = totalImporte4toTrimestreEtapaII;
	}

	public Double getTotalVolumen1erTrimestreEtapaIII() {
		return totalVolumen1erTrimestreEtapaIII;
	}

	public void setTotalVolumen1erTrimestreEtapaIII(
			Double totalVolumen1erTrimestreEtapaIII) {
		this.totalVolumen1erTrimestreEtapaIII = totalVolumen1erTrimestreEtapaIII;
	}

	public Double getTotalImporte1erTrimestreEtapaIII() {
		return totalImporte1erTrimestreEtapaIII;
	}

	public void setTotalImporte1erTrimestreEtapaIII(
			Double totalImporte1erTrimestreEtapaIII) {
		this.totalImporte1erTrimestreEtapaIII = totalImporte1erTrimestreEtapaIII;
	}

	public Double getTotalVolumen2doTrimestreEtapaIII() {
		return totalVolumen2doTrimestreEtapaIII;
	}

	public void setTotalVolumen2doTrimestreEtapaIII(
			Double totalVolumen2doTrimestreEtapaIII) {
		this.totalVolumen2doTrimestreEtapaIII = totalVolumen2doTrimestreEtapaIII;
	}

	public Double getTotalImporte2doTrimestreEtapaIII() {
		return totalImporte2doTrimestreEtapaIII;
	}

	public void setTotalImporte2doTrimestreEtapaIII(
			Double totalImporte2doTrimestreEtapaIII) {
		this.totalImporte2doTrimestreEtapaIII = totalImporte2doTrimestreEtapaIII;
	}

	public Double getTotalVolumen3erTrimestreEtapaIII() {
		return totalVolumen3erTrimestreEtapaIII;
	}

	public void setTotalVolumen3erTrimestreEtapaIII(
			Double totalVolumen3erTrimestreEtapaIII) {
		this.totalVolumen3erTrimestreEtapaIII = totalVolumen3erTrimestreEtapaIII;
	}

	public Double getTotalImporte3erTrimestreEtapaIII() {
		return totalImporte3erTrimestreEtapaIII;
	}

	public void setTotalImporte3erTrimestreEtapaIII(
			Double totalImporte3erTrimestreEtapaIII) {
		this.totalImporte3erTrimestreEtapaIII = totalImporte3erTrimestreEtapaIII;
	}

	public Double getTotalVolumen4toTrimestreEtapaIII() {
		return totalVolumen4toTrimestreEtapaIII;
	}

	public void setTotalVolumen4toTrimestreEtapaIII(
			Double totalVolumen4toTrimestreEtapaIII) {
		this.totalVolumen4toTrimestreEtapaIII = totalVolumen4toTrimestreEtapaIII;
	}

	public Double getTotalImporte4toTrimestreEtapaIII() {
		return totalImporte4toTrimestreEtapaIII;
	}

	public void setTotalImporte4toTrimestreEtapaIII(
			Double totalImporte4toTrimestreEtapaIII) {
		this.totalImporte4toTrimestreEtapaIII = totalImporte4toTrimestreEtapaIII;
	}

	public Double getTotalVolumen1erTrimestreEtapaIV() {
		return totalVolumen1erTrimestreEtapaIV;
	}

	public void setTotalVolumen1erTrimestreEtapaIV(
			Double totalVolumen1erTrimestreEtapaIV) {
		this.totalVolumen1erTrimestreEtapaIV = totalVolumen1erTrimestreEtapaIV;
	}

	public Double getTotalImporte1erTrimestreEtapaIV() {
		return totalImporte1erTrimestreEtapaIV;
	}

	public void setTotalImporte1erTrimestreEtapaIV(
			Double totalImporte1erTrimestreEtapaIV) {
		this.totalImporte1erTrimestreEtapaIV = totalImporte1erTrimestreEtapaIV;
	}

	public Double getTotalVolumen2doTrimestreEtapaIV() {
		return totalVolumen2doTrimestreEtapaIV;
	}

	public void setTotalVolumen2doTrimestreEtapaIV(
			Double totalVolumen2doTrimestreEtapaIV) {
		this.totalVolumen2doTrimestreEtapaIV = totalVolumen2doTrimestreEtapaIV;
	}

	public Double getTotalImporte2doTrimestreEtapaIV() {
		return totalImporte2doTrimestreEtapaIV;
	}

	public void setTotalImporte2doTrimestreEtapaIV(
			Double totalImporte2doTrimestreEtapaIV) {
		this.totalImporte2doTrimestreEtapaIV = totalImporte2doTrimestreEtapaIV;
	}

	public Double getTotalVolumen3erTrimestreEtapaIV() {
		return totalVolumen3erTrimestreEtapaIV;
	}

	public void setTotalVolumen3erTrimestreEtapaIV(
			Double totalVolumen3erTrimestreEtapaIV) {
		this.totalVolumen3erTrimestreEtapaIV = totalVolumen3erTrimestreEtapaIV;
	}

	public Double getTotalImporte3erTrimestreEtapaIV() {
		return totalImporte3erTrimestreEtapaIV;
	}

	public void setTotalImporte3erTrimestreEtapaIV(
			Double totalImporte3erTrimestreEtapaIV) {
		this.totalImporte3erTrimestreEtapaIV = totalImporte3erTrimestreEtapaIV;
	}

	public Double getTotalVolumen4toTrimestreEtapaIV() {
		return totalVolumen4toTrimestreEtapaIV;
	}

	public void setTotalVolumen4toTrimestreEtapaIV(
			Double totalVolumen4toTrimestreEtapaIV) {
		this.totalVolumen4toTrimestreEtapaIV = totalVolumen4toTrimestreEtapaIV;
	}

	public Double getTotalImporte4toTrimestreEtapaIV() {
		return totalImporte4toTrimestreEtapaIV;
	}

	public void setTotalImporte4toTrimestreEtapaIV(
			Double totalImporte4toTrimestreEtapaIV) {
		this.totalImporte4toTrimestreEtapaIV = totalImporte4toTrimestreEtapaIV;
	}

	public Double getTotalVolumen1erTrimestreEtapaV() {
		return totalVolumen1erTrimestreEtapaV;
	}

	public void setTotalVolumen1erTrimestreEtapaV(
			Double totalVolumen1erTrimestreEtapaV) {
		this.totalVolumen1erTrimestreEtapaV = totalVolumen1erTrimestreEtapaV;
	}

	public Double getTotalImporte1erTrimestreEtapaV() {
		return totalImporte1erTrimestreEtapaV;
	}

	public void setTotalImporte1erTrimestreEtapaV(
			Double totalImporte1erTrimestreEtapaV) {
		this.totalImporte1erTrimestreEtapaV = totalImporte1erTrimestreEtapaV;
	}

	public Double getTotalVolumen2doTrimestreEtapaV() {
		return totalVolumen2doTrimestreEtapaV;
	}

	public void setTotalVolumen2doTrimestreEtapaV(
			Double totalVolumen2doTrimestreEtapaV) {
		this.totalVolumen2doTrimestreEtapaV = totalVolumen2doTrimestreEtapaV;
	}

	public Double getTotalImporte2doTrimestreEtapaV() {
		return totalImporte2doTrimestreEtapaV;
	}

	public void setTotalImporte2doTrimestreEtapaV(
			Double totalImporte2doTrimestreEtapaV) {
		this.totalImporte2doTrimestreEtapaV = totalImporte2doTrimestreEtapaV;
	}

	public Double getTotalVolumen3erTrimestreEtapaV() {
		return totalVolumen3erTrimestreEtapaV;
	}

	public void setTotalVolumen3erTrimestreEtapaV(
			Double totalVolumen3erTrimestreEtapaV) {
		this.totalVolumen3erTrimestreEtapaV = totalVolumen3erTrimestreEtapaV;
	}

	public Double getTotalImporte3erTrimestreEtapaV() {
		return totalImporte3erTrimestreEtapaV;
	}

	public void setTotalImporte3erTrimestreEtapaV(
			Double totalImporte3erTrimestreEtapaV) {
		this.totalImporte3erTrimestreEtapaV = totalImporte3erTrimestreEtapaV;
	}

	public Double getTotalVolumen4toTrimestreEtapaV() {
		return totalVolumen4toTrimestreEtapaV;
	}

	public void setTotalVolumen4toTrimestreEtapaV(
			Double totalVolumen4toTrimestreEtapaV) {
		this.totalVolumen4toTrimestreEtapaV = totalVolumen4toTrimestreEtapaV;
	}

	public Double getTotalImporte4toTrimestreEtapaV() {
		return totalImporte4toTrimestreEtapaV;
	}

	public void setTotalImporte4toTrimestreEtapaV(
			Double totalImporte4toTrimestreEtapaV) {
		this.totalImporte4toTrimestreEtapaV = totalImporte4toTrimestreEtapaV;
	}

	public Integer getNumeroEtapa() {
		return numeroEtapa;
	}

	public void setNumeroEtapa(Integer numeroEtapa) {
		this.numeroEtapa = numeroEtapa;
	}	
}
