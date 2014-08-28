package mx.gob.comer.sipc.pagos.action;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.gob.comer.sipc.dao.CatalogosDAO;
import mx.gob.comer.sipc.dao.PagosDAO;
import mx.gob.comer.sipc.dao.SolicitudPagoDAO;
import mx.gob.comer.sipc.dao.UtileriasDAO;
import mx.gob.comer.sipc.domain.EstatusPago;
import mx.gob.comer.sipc.domain.Pagos;
import mx.gob.comer.sipc.domain.Programa;
import mx.gob.comer.sipc.domain.catalogos.Especialista;
import mx.gob.comer.sipc.log.AppLogger;
import mx.gob.comer.sipc.utilerias.Utilerias;
import mx.gob.comer.sipc.vistas.domain.DocumentacionSPCartaAdhesionV;
import mx.gob.comer.sipc.vistas.domain.PagosV;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.JDBCException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AutorizacionPagosAction extends ActionSupport implements SessionAware, Serializable {
	
	private SolicitudPagoDAO spDAO = new SolicitudPagoDAO();
	private UtileriasDAO uDAO = new UtileriasDAO();
	
	private Map<String, Object> session;
	private PagosDAO pDAO = new PagosDAO();
	private UtileriasDAO utileriasDAO = new UtileriasDAO();
	private CatalogosDAO cDAO = new CatalogosDAO();	
	private List<Especialista> lstEspecialistaPagos;
	private List<Programa> lstProgByEspecialista;
	private List<PagosV> lstPagosV;
	private long idEspecialista;
	private int idPrograma;
	private int idComprador;
	private Date fechaInicio;
	private Date fechaFin;
	private boolean bandera;
	private PagosV pagosV;
	private long idPago;
	private Pagos pagos;
	private Integer[] estatusId;
	private Integer[] idPagos;
	private Integer[] idPagosCopia;
	private int estatus;
	private String msjOk;
	private String atentaNota;
	private String rutaArchivoAtentaNota;
	private String nombreArchivo;
	private String[] archivoAtentaNota;
	private String[] docFileName;
	
	private File[] doc;
	private int numCampos;
	private Map<Integer,File> servicios;
	private int count;

	private List<EstatusPago> lstEstatusPago;
	private int estatusCve;
	private String rutaSalida;
	
	private int indice;
	private List<DocumentacionSPCartaAdhesionV> lstDocumentacionSPCartaAdhesion;
	
	public String busquedaAutorizacionPagos(){
		try {			
			lstEspecialistaPagos = pDAO.consultaEspecialistaPagos(0);
			lstProgByEspecialista = pDAO.consultaProgByEspecialista(0);
			lstEstatusPago = cDAO.consultaEstatusPago();
			estatusCve = 7;
			lstPagosV = pDAO.consultaPagosV(-1, 0, 0, "", "", estatusCve, 0, 0,"");
			bandera = true;
			setNumCampos(lstPagosV.size());
		} catch (JDBCException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return SUCCESS;
	}
	
	public String recuperaProgByEspecialista() throws JDBCException, Exception{
		if(idEspecialista==-1){
			lstProgByEspecialista = pDAO.consultaProgByEspecialista(0);
		}else{
			lstProgByEspecialista = pDAO.consultaProgByEspecialista(idEspecialista);
		}
		return SUCCESS;
	}
	
	public String realizaBusquedaAutorizacionPago(){
		String fechaInicioS = "";
		String fechaFinS = "";
			try{
				session = ActionContext.getContext().getSession();
				/* Validacion de rango de fechas */
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
			
				/**recupera datos a trav�s de los criterios de busqueda seleccionado por el usuario**/
				lstPagosV = pDAO.consultaPagosV(-1, idPrograma, idComprador, fechaInicioS, fechaFinS, estatusCve, 0, idEspecialista, null);
				bandera = true;
				setNumCampos(lstPagosV.size());
			}catch (JDBCException e) {	
				e.printStackTrace();
			}finally{
				idPrograma = -1;
				try {
					lstEspecialistaPagos = pDAO.consultaEspecialistaPagos(0);
					lstProgByEspecialista = pDAO.consultaProgByEspecialista(0);
					lstEstatusPago = cDAO.consultaEstatusPago();
				} catch (JDBCException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
		
			return SUCCESS;
	}
	
	
	public String detallesAutorizacionPago(){
		try{
			/*Recupera los datos del pago*/
			pagosV = pDAO.consultaPagosV(idPago).get(0);
			
		}catch (JDBCException e) {	
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	
	public String registrarAutorizacion() throws Exception{	
		try{
			indice=0;
			//Validar tama�o de archivos
			if(idPagosCopia!=null){
				for(int i = 0, j = 0; i< idPagosCopia.length; i++){
					if(idPagosCopia[i]!=null){
						if(doc[j]!=null){
							if(Utilerias.verificarTamanioArchivo(doc[j])){
								addActionError("Lo sentimos los archivos cargados no deben exceder de 3 MegaBytes");
								return SUCCESS;
							}
							j++;
						}
					}
				}
			}
			
			if(estatusId!=null){
				for(int i=0; i<idPagos.length; i++){
					if(estatusId[i].intValue() == 1){					
						//Setear archivo, cargar en pago correspondiente
						nombreArchivo = setEstatusPago(idPagos[i]);
					}else if(estatusId[i].intValue()== 8){
						//Actualizar estatus en pago
						pagos = pDAO.consultaPagos(idPagos[i]).get(0);
						pagos.setEstatus(estatusId[i]);
						cDAO.guardaObjeto(pagos);					
					}
				}
			} else {
				for(int i=0; i<idPagos.length; i++){
					nombreArchivo = setEstatusPago(idPagos[i]);
				}
			}
			
			msjOk = "Se actualiz� satisfactoriamente el(los) registro(s)";
		} catch (JDBCException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			busquedaAutorizacionPagos();
		}
		return SUCCESS;
	}
	
	public String  setEstatusPago(Integer idPago) throws Exception{
		String nomArchivo = "";
		boolean band = false;
		int diasVigencia = 90;
		Date fechaVigenciaLimitePago;
		
		try{
			atentaNota = utileriasDAO.getParametros("RUTA_ATENTA_NOTA_PAGO");
			
			for(int i=0; i< idPagosCopia.length; i++){
				if(idPagosCopia[i]!=null){
					if(idPago.intValue() == idPagosCopia[i].intValue()){
						//Recupera pago 
						pagos = pDAO.consultaPagos(idPago).get(0);
						if(pagos.getEstatus()==7){
							// Valida vigencia del Anexo 32D y que no tenga observaciones  
							lstDocumentacionSPCartaAdhesion = spDAO.consultaExpedientesSPCartaAdhesionV(pagos.getNoCarta(), 5, null, "folioCartaAdhesion");
							fechaVigenciaLimitePago = uDAO.getFechaDiaNaturalSumaDias(lstDocumentacionSPCartaAdhesion.get(0).getFechaExpedicionAnexo(), diasVigencia);
							String fechaMin = new SimpleDateFormat("yyyyMMdd").format(lstDocumentacionSPCartaAdhesion.get(0).getFechaExpedicionAnexo()).toString();
							String fechaMax = new SimpleDateFormat("yyyyMMdd").format(fechaVigenciaLimitePago).toString();	
							String fechaAutPago = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
							if((lstDocumentacionSPCartaAdhesion.get(0).getObservacion()==null || !lstDocumentacionSPCartaAdhesion.get(0).getObservacion()) && Long.parseLong(fechaAutPago) >= Long.parseLong(fechaMin) && Long.parseLong(fechaAutPago) <= Long.parseLong(fechaMax)){
								String ext = docFileName[indice].toLowerCase().substring( docFileName[indice].lastIndexOf(".") );
								nombreArchivo = "AtentaNotaPago"+idPago+ext;
								File f = new File(atentaNota+pagos.getArchivoAtentaNota());
								if (f.exists()){
									if(!f.delete()){
										AppLogger.error("errores","Error al eliminar: "+pagos.getArchivoAtentaNota());
									}else{
										AppLogger.info("app","Se borro archivo: "+pagos.getArchivoAtentaNota());
									}
								}
								Utilerias.cargarArchivo(atentaNota, nombreArchivo, doc[indice]);
								pagos.setEstatus(1);
								pagos.setArchivoAtentaNota(nombreArchivo);
								cDAO.guardaObjeto(pagos);							
							} else {
								addActionError("La vigencia del documento Anexo 32D (Fecha Anexo: "+fechaVigenciaLimitePago.toString()+") no es v�lida para la autorizaci�n del pago: "+ pagos.getIdPago() + " Carta: "+ pagos.getNoCarta() +" (Fecha Aut.: "+new SimpleDateFormat("dd/MM/yyyy").format(new Date()).toString()+") o el anexo 32D esta en estatus de observaci�n, por favor verifique");
							}
						} else {
							String ext = docFileName[indice].toLowerCase().substring( docFileName[indice].lastIndexOf(".") );
							nombreArchivo = "AtentaNotaPago"+idPago+ext;
							File f = new File(atentaNota+pagos.getArchivoAtentaNota());
							if (f.exists()){
								if(!f.delete()){
									AppLogger.error("errores","Error al eliminar: "+pagos.getArchivoAtentaNota());
								}else{
									AppLogger.info("app","Se borro archivo: "+pagos.getArchivoAtentaNota());
								}
							}
							Utilerias.cargarArchivo(atentaNota, nombreArchivo, doc[indice]);
							pagos.setArchivoAtentaNota(nombreArchivo);
							cDAO.guardaObjeto(pagos);							
						}
						band = true;
						indice++;
					}
				}
			}
			if(!band){
				pagos = pDAO.consultaPagos(idPago).get(0);
				if(pagos.getEstatus()==7){
					pagos.setEstatus(1);
					cDAO.guardaObjeto(pagos);
				}
			}
		}catch (JDBCException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nomArchivo;
	}
	
	public String consigueAtentaNota() throws Exception{
		try{
			List<Pagos> pago = pDAO.consultaPagos(idPago);
			rutaSalida = cDAO.consultaParametros("RUTA_ATENTA_NOTA_PAGO");	
			nombreArchivo = pago.get(0).getArchivoAtentaNota(); 
			if (!rutaSalida.endsWith(File.separator)){
				rutaSalida += File.separator;
			}
			Utilerias.entregarArchivo(rutaSalida,nombreArchivo,"pdf");
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			realizaBusquedaAutorizacionPago();
		}		
		return SUCCESS;
	}
	
	public String recuperaFileByEstatus() throws Exception{
		
		return SUCCESS;
	}
	
	
	public Pagos getPagos() {
		return pagos;
	}

	public void setPagos(Pagos pagos) {
		this.pagos = pagos;
	}	
	

	public Integer[] getEstatusId() {
		return estatusId;
	}

	public void setEstatusId(Integer[] estatusId) {
		this.estatusId = estatusId;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	
	public void setSession(Map<String, Object> session) {
	    this.session = session;
	}
	
	public Map<String, Object> getSession() {
	    return session;
	}

	public List<Especialista> getLstEspecialistaPagos() {
		return lstEspecialistaPagos;
	}

	public void setLstEspecialistaPagos(List<Especialista> lstEspecialistaPagos) {
		this.lstEspecialistaPagos = lstEspecialistaPagos;
	}

	public List<Programa> getLstProgByEspecialista() {
		return lstProgByEspecialista;
	}

	public void setLstProgByEspecialista(List<Programa> lstProgByEspecialista) {
		this.lstProgByEspecialista = lstProgByEspecialista;
	}

	public List<PagosV> getLstPagosV() {
		return lstPagosV;
	}

	public void setLstPagosV(List<PagosV> lstPagosV) {
		this.lstPagosV = lstPagosV;
	}

	public long getIdEspecialista() {
		return idEspecialista;
	}

	public void setIdEspecialista(long idEspecialista) {
		this.idEspecialista = idEspecialista;
	}

	public int getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(int idPrograma) {
		this.idPrograma = idPrograma;
	}

	public int getIdComprador() {
		return idComprador;
	}

	public void setIdComprador(int idComprador) {
		this.idComprador = idComprador;
	}

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

	public boolean isBandera() {
		return bandera;
	}

	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}

	public PagosV getPagosV() {
		return pagosV;
	}

	public void setPagosV(PagosV pagosV) {
		this.pagosV = pagosV;
	}

	public long getIdPago() {
		return idPago;
	}

	public void setIdPago(long idPago) {
		this.idPago = idPago;
	}

	public Integer[] getIdPagos() {
		return idPagos;
	}

	public void setIdPagos(Integer[] idPagos) {
		this.idPagos = idPagos;
	}

	public String getMsjOk() {
		return msjOk;
	}

	public void setMsjOk(String msjOk) {
		this.msjOk = msjOk;
	}

	public String getAtentaNota() {
		return atentaNota;
	}

	public void setAtentaNota(String atentaNota) {
		this.atentaNota = atentaNota;
	}
	
		
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getRutaArchivoAtentaNota() {
		return rutaArchivoAtentaNota;
	}

	public void setRutaArchivoAtentaNota(String rutaArchivoAtentaNota) {
		this.rutaArchivoAtentaNota = rutaArchivoAtentaNota;
	}

	public String[] getArchivoAtentaNota() {
		return archivoAtentaNota;
	}

	public void setArchivoAtentaNota(String[] archivoAtentaNota) {
		this.archivoAtentaNota = archivoAtentaNota;
	}
	
	public File[] getDoc() {
		return doc;
	}

	public void setDoc(File[] doc) {
		this.doc = doc;
	}

	public int getNumCampos() {
		return numCampos;
	}

	public void setNumCampos(int numCampos) {
		this.numCampos = numCampos;
	}

	public Map<Integer, File> getServicios() {
		return servicios;
	}

	public void setServicios(Map<Integer, File> servicios) {
		this.servicios = servicios;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Integer[] getIdPagosCopia() {
		return idPagosCopia;
	}

	public void setIdPagosCopia(Integer[] idPagosCopia) {
		this.idPagosCopia = idPagosCopia;
	}

	public String[] getDocFileName() {
		return docFileName;
	}

	public void setDocFileName(String[] docFileName) {
		this.docFileName = docFileName;
	}

	public List<EstatusPago> getLstEstatusPago() {
		return lstEstatusPago;
	}

	public void setLstEstatusPago(List<EstatusPago> lstEstatusPago) {
		this.lstEstatusPago = lstEstatusPago;
	}

	public int getEstatusCve() {
		return estatusCve;
	}

	public void setEstatusCve(int estatusCve) {
		this.estatusCve = estatusCve;
	}

	public String getRutaSalida() {
		return rutaSalida;
	}

	public void setRutaSalida(String rutaSalida) {
		this.rutaSalida = rutaSalida;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public List<DocumentacionSPCartaAdhesionV> getLstDocumentacionSPCartaAdhesion() {
		return lstDocumentacionSPCartaAdhesion;
	}

	public void setLstDocumentacionSPCartaAdhesion(
			List<DocumentacionSPCartaAdhesionV> lstDocumentacionSPCartaAdhesion) {
		this.lstDocumentacionSPCartaAdhesion = lstDocumentacionSPCartaAdhesion;
	}	
}
