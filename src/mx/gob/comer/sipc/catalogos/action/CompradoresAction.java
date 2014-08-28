package mx.gob.comer.sipc.catalogos.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.JDBCException;

import mx.gob.comer.sipc.dao.CatalogosDAO;
import mx.gob.comer.sipc.dao.InscripcionDAO;
import mx.gob.comer.sipc.dao.UtileriasDAO;
import mx.gob.comer.sipc.domain.Comprador;
import mx.gob.comer.sipc.domain.Estado;
import mx.gob.comer.sipc.domain.Expediente;
import mx.gob.comer.sipc.domain.ExpedienteComprador;
import mx.gob.comer.sipc.domain.Localidades;
import mx.gob.comer.sipc.domain.Municipios;
import mx.gob.comer.sipc.domain.Programa;
import mx.gob.comer.sipc.domain.catalogos.RepresentanteLegal;
import mx.gob.comer.sipc.domain.catalogos.RepresentanteComprador;
import mx.gob.comer.sipc.log.AppLogger;
import mx.gob.comer.sipc.utilerias.Utilerias;
import mx.gob.comer.sipc.vistas.domain.CompradorExpedientesV;
import mx.gob.comer.sipc.vistas.domain.CompradoresV;
import mx.gob.comer.sipc.vistas.domain.RepresentanteCompradorV;
import mx.gob.comer.sipc.vistas.domain.RepresentanteLegalV;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CompradoresAction extends ActionSupport implements SessionAware{
	
	private CatalogosDAO cDAO = new CatalogosDAO();
	private InscripcionDAO iDAO = new InscripcionDAO ();
	private List<Comprador> lstCompradores;
	private List<Estado> lstEstados;
	private List<Municipios> lstMunicipios;
	private List<Localidades> lstLocalidades;	
	private List<Expediente> lstExpedientes;
	private List<RepresentanteLegalV> lstRepLegalV;
	private List<RepresentanteCompradorV> lstRecuperaRepLeg;
	private Map<String, Object> session;
	private List<Programa> lstProgramas;
	private Comprador comprador;
	private int idComprador;
	private int idExpediente;
	private UtileriasDAO utileriasDAO = new UtileriasDAO();
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private Date fechaNacimiento;
	private Integer entidadNacimiento;
	private int[] capRepresentante;
	private String[] capRfc;
	private String[] capCurp;
	private int[] capEstatus; 
	private String tipoPersona;
	private String rfc;
	private String curp;
	private String telefono;
	private String fax;
	private String correoElectronico;
	private Integer claveLocalidad;
	private String calle;
	private String colonia;
	private String numExterior;
	private String numInterior;
	private String codigoPostal;
	private Integer estatus;
	private Integer idEstado;
	private Integer claveMunicipio;
	private int personaFiscal;
	private String msjOk;
	private CompradoresV compradoresV;
	private RepresentanteLegal representantel;
	private int editar;
	private int numCampos;
	private File doc;
	private String docFileName;
	private String rutaComprador;
	private String nombreArchivo;
	private Integer idRepresentante;
	private String rutaExpediente;
	private long idExpedienteComprador;
	private List<CompradorExpedientesV> lstCompradorExpedientesV;
	private List<RepresentanteComprador> lstRepresentanteComprador;
	private List <RepresentanteCompradorV> lstRepresentanteCompradorV;
	private int numCamposAnterior;
	private String representante;
	private String estatusRepresentante;
	private int errorRfc;
	private String msjError;
	private Date fechaAlta;
	private Date fechaModificacion;
	private Integer usuarioModificacion;
	private Integer usuarioCreacion;
	private String descestatus;
	private String motivo;
	private int tipoSexo;
	private String sexo;
	private String folio;
	private int idPerfil;
	
	public String busquedaCompradores(){
		try{
			lstCompradores = cDAO.consultaComprador(0, null, "","");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String ejecutaBusquedaCompradores(){
		try{
			lstCompradores = cDAO.consultaComprador(0, null,nombre, rfc);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String datosFiscales(){
		lstEstados = cDAO.consultaEstado(0);
		return SUCCESS;
		
	}
	
	public String registrarComprador(){
		session = ActionContext.getContext().getSession();
		idPerfil = (Integer) session.get("idPerfil");
		try{			
			int elementosBorrados = 0;
					
			comprador = new Comprador();
			if(editar==3){
				comprador = cDAO.consultaComprador(idComprador, null, "","").get(0);
				elementosBorrados = cDAO.borrarRepresentanteComprador(idComprador);
				AppLogger.info("app", "Se borraron "+elementosBorrados+" de la tabla representante_comprador");
			}
			
			if(folio == null || folio.isEmpty()){
				comprador.setFolio(null);
			}else{
				comprador.setFolio(folio);
			}

			comprador.setRfc(rfc.toUpperCase());
			comprador.setNombre(nombre.toUpperCase());
			
			if (personaFiscal == 1){
				tipoPersona = "F";
				if (tipoSexo == 1){
					sexo = "M";
				}else{
					sexo = "F";
				}
				if(sexo == null || sexo.isEmpty()){
					comprador.setSexo(null);
				}else{
					comprador.setSexo(sexo);				
				}
			}else{
				tipoPersona = "M";
				comprador.setSexo(null);
			}
			
			comprador.setTipoPersona(tipoPersona);
			
			if(curp == null || curp.isEmpty()){
				comprador.setCurp(null);
			}else{
				comprador.setCurp(curp.toUpperCase());
			}
			
			if(apellidoPaterno == null || apellidoPaterno.isEmpty() || apellidoPaterno.equals("")){
					comprador.setApellidoPaterno(null);
				}else{
					comprador.setApellidoPaterno(apellidoPaterno.toUpperCase());
				}
				
			if(apellidoMaterno == null || apellidoMaterno.isEmpty() || apellidoMaterno.equals("")){
				comprador.setApellidoMaterno(null);
			}else{
				comprador.setApellidoMaterno(apellidoMaterno.toUpperCase());
			}
			
			if(fechaNacimiento == null){
				comprador.setFechaNacimiento(null);
			}else{
				comprador.setFechaNacimiento(fechaNacimiento);
			}
			
			if(entidadNacimiento == null || entidadNacimiento == -1){
				comprador.setEntidadNacimiento(null);
			}else{
				comprador.setEntidadNacimiento(entidadNacimiento);
			}

			if(idEstado == null  || idEstado == -1){
				comprador.setIdEstado(null);
			}else{
				comprador.setIdEstado(idEstado);
			}
			
			if(claveMunicipio == null || claveMunicipio == -1){
				comprador.setClaveMunicipio(null);
			}else{
				comprador.setClaveMunicipio(claveMunicipio);
			}
			
			if(claveLocalidad == null || claveLocalidad == -1){
				comprador.setClaveLocalidad(null);
			}else{
				comprador.setClaveLocalidad(claveLocalidad);
			}
			
			if(calle == null || calle.isEmpty()){
				comprador.setCalle(null);
			}else{
				comprador.setCalle(calle.toUpperCase());
			}
			
			if(colonia == null || colonia.isEmpty()){
				comprador.setColonia(null);
			}else{
				comprador.setColonia(colonia.toUpperCase());
			}
			
			if(numExterior == null || numExterior.isEmpty()){
				comprador.setNumExterior(null);
			}else{
				comprador.setNumExterior(numExterior.toUpperCase());
			}
			
			if(numInterior == null || numInterior.isEmpty()){
				comprador.setNumInterior(null);
			}else{
				comprador.setNumExterior(numInterior.toUpperCase());
			}
	
			if(codigoPostal == null || codigoPostal.isEmpty()){
				comprador.setCodigoPostal(null);
			}else{
				comprador.setCodigoPostal(codigoPostal.toUpperCase());
			}
			
			if(telefono == null || telefono.isEmpty()){
				comprador.setTelefono(null);
			}else{
				comprador.setTelefono(telefono);
			}
			
			if(fax == null || fax.isEmpty()){
				comprador.setFax(null);
			}else{
				comprador.setFax(fax.toUpperCase());
			}
			
			if(correoElectronico == null || correoElectronico.isEmpty()){
				comprador.setCorreoElectronico(null);
			}else{
				comprador.setCorreoElectronico(correoElectronico.toLowerCase());
			}
			
			if(editar == 3){
				comprador.setUsuarioModificacion((Integer) session.get("idUsuario"));
				comprador.setFechaModificacion(new Date());
			}else{
				comprador.setUsuarioCreacion((Integer) session.get("idUsuario"));
				comprador.setFechaAlta(new Date());
			}
			
			comprador.setEstatus(1);	
			comprador = (Comprador) cDAO.guardaObjeto(comprador);
			
			
			if(editar !=3){
				comprador.setRutaComprador(comprador.getIdComprador()+"/");
				rutaComprador  = utileriasDAO.getParametros("RUTA_COMPRADORES");
				Utilerias.crearDirectorio(rutaComprador+comprador.getIdComprador()+"/");
				cDAO.guardaObjeto(comprador);		
				
			}
			
			if(idPerfil != 1){	
				for (int i = 0; i < capRepresentante.length; i++) {				
					RepresentanteComprador rc = new RepresentanteComprador();
					rc.setIdRepresentante(capRepresentante[i]);
					rc.setIdComprador(comprador.getIdComprador());
					if (capEstatus[0] == i){
						rc.setEstatus(1);
					}else{
						rc.setEstatus(0);
					}
					cDAO.guardaObjeto(rc);				
				}
			}else if (idPerfil == 1 && numCampos != -1){
				for (int i = 0; i < capRepresentante.length; i++) {				
					RepresentanteComprador rc = new RepresentanteComprador();
					rc.setIdRepresentante(capRepresentante[i]);
					rc.setIdComprador(comprador.getIdComprador());
					if (capEstatus[0] == i){
						rc.setEstatus(1);
					}else{
						rc.setEstatus(0);
					}
					cDAO.guardaObjeto(rc);				
				}
			}
			
			lstCompradores = cDAO.consultaComprador(0);
			if (editar == 3){
				msjOk = "El Comprador se edit� correctamente";			
			}else{		
				msjOk = "El Comprador se agreg� correctamente";
			}
			nombre = "";
			rfc="";
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return SUCCESS;
	}
	
	public String recuperaDatosComprador(){
		try{	
			/*Recupera los datos del Comprador*/
			lstEstados = cDAO.consultaEstado(0);
			compradoresV = cDAO.consultaCompradoresV(idComprador).get(0);
			comprador = cDAO.consultaComprador(idComprador, null, "","").get(0);
			
			lstRepresentanteCompradorV = cDAO.consultaRepresentanteCompradorV(0, idComprador);

			if (compradoresV.getIdEstado() != null){
				lstMunicipios= cDAO.consultaMunicipiosbyEdo(compradoresV.getIdEstado(), 0, 0, null);
			}
			
			if (compradoresV.getIdEstado() !=null && compradoresV.getClaveMunicipio() != null){
				lstLocalidades= cDAO.consultaLocalidadByMunicipio(0, compradoresV.getIdEstado(), compradoresV.getClaveMunicipio(), 0, null);
			}
			
			if (compradoresV.getTipoPersona().equals("F")){
				personaFiscal = 1;
				rfc = compradoresV.getRfc();
				curp = compradoresV.getCurp();
				apellidoPaterno = comprador.getApellidoPaterno();
				apellidoMaterno = comprador.getApellidoMaterno();
				fechaNacimiento = compradoresV.getFechaNacimiento();
				
				if(compradoresV.getEntidadNacimiento() != null){
					entidadNacimiento = compradoresV.getEntidadNacimiento();
				}
				if (compradoresV.getSexo() != null){
					if (compradoresV.getSexo().equals("M")){
						tipoSexo = 1;
					}else{
						tipoSexo = 2;
					}
				}
			}else{
				personaFiscal = 0;
				rfc = compradoresV.getRfc();
			}
			
			folio = compradoresV.getFolio();
			nombre = comprador.getNombre();
			
			numCamposAnterior = lstRepresentanteCompradorV.size();
			if(lstRepresentanteCompradorV.size() != 0){
				numCampos = lstRepresentanteCompradorV.size();
				if(lstRepresentanteCompradorV.get(0).getNombreRepresentante() != null){
					representante = lstRepresentanteCompradorV.get(0).getNombreRepresentante();
				}
			}
			datosRepLegal(); 

			telefono = compradoresV.getTelefono();
			fax = compradoresV.getFax();
			correoElectronico = compradoresV.getCorreoElectronico();
			
			if(compradoresV.getIdEstado() != null){
				idEstado = compradoresV.getIdEstado();
			}
			
			if(compradoresV.getClaveMunicipio() != null){
				claveMunicipio = compradoresV.getClaveMunicipio();
			}
			
			if(compradoresV.getClaveLocalidad()!=null){
				claveLocalidad = compradoresV.getClaveLocalidad();
			}
			colonia = compradoresV.getColonia();
			calle = compradoresV.getCalle();
			numExterior = compradoresV.getNumExterior();
			numInterior = compradoresV.getNumInterior();
			codigoPostal = compradoresV.getCodigoPostal();
		}catch (JDBCException e) {	
			e.printStackTrace();
		}
		return SUCCESS;
	}	
	
	public String detalleComprador(){
		try{
			session = ActionContext.getContext().getSession();
			lstEstados = cDAO.consultaEstado(0);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	
	
	public String recuperaMunicipioByEstado(){
		try{
			lstMunicipios= cDAO.consultaMunicipiosbyEdo(idEstado, 0, 0, null);	
		}catch(Exception e){
			e.printStackTrace();
		}	
		return SUCCESS;
	}
	
	public String recuperaLocalidadesByMunicipio(){
		try{
			lstLocalidades= cDAO.consultaLocalidadByMunicipio(0, idEstado, claveMunicipio, 0, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String detallesComprador(){
		try{
			/*Recupera los datos del Comprador*/
			compradoresV = cDAO.consultaCompradoresV(idComprador).get(0);
			if (compradoresV.getTipoPersona().equals("F")){
				personaFiscal = 1;
				if (compradoresV.getSexo().equals("M")){
					tipoSexo = 1;
				}else{
					tipoSexo = 2;
				}
			}else{
				personaFiscal = 0;
			}
			
		}catch (JDBCException e) {	
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String agregarComprador(){
		try{
			session = ActionContext.getContext().getSession();
			lstEstados = cDAO.consultaEstado(0);
			lstExpedientes = cDAO.consultaExpediente(0, "CM");
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	

	
	public String recuperaDatosRepLegal(){
		representantel = cDAO.consultaRepresentanteLegal(idRepresentante,"","").get(0);
		return SUCCESS;
	}
	
	public String validarRfc(){
		try {
			List<Comprador> lstCompradoresRfc = cDAO.consultaCompradorbyRfc(rfc);
			System.out.println("diComprador "+idComprador);
			if(lstCompradoresRfc.size()>0){
				if (lstCompradoresRfc.get(0).getIdComprador() != idComprador){
					errorRfc = 1;
					msjError = "El RFC del Comprador ya se encuentra registrado, por favor verifique";
					}
			}
		}catch (JDBCException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	return SUCCESS;
	}
	
	public String datosRepLegal(){
		try { 
			lstRecuperaRepLeg = new ArrayList<RepresentanteCompradorV>();
			
			if(editar == 0){
				for(int i=1; i<=numCampos; i++){
					lstRecuperaRepLeg.add(new RepresentanteCompradorV());
				}
			}else if(editar == 3){
				lstRecuperaRepLeg = cDAO.consultaRepresentanteCompradorV(0, idComprador);
				numCamposAnterior = lstRecuperaRepLeg.size();
				if(numCampos > numCamposAnterior){
					int resta = 0;
					resta = numCampos - numCamposAnterior;
					for(int i =1; i<=resta; i++){
						lstRecuperaRepLeg.add(new RepresentanteCompradorV());
					}
				}else if(numCampos < numCamposAnterior){
					List<RepresentanteCompradorV> lstRepresentanteCompradorVTmp = new ArrayList<RepresentanteCompradorV>();
					for (int i=1; i<=numCampos; i++){
						lstRepresentanteCompradorVTmp.add(lstRecuperaRepLeg.get(i-1));
					}
					lstRecuperaRepLeg = new ArrayList<RepresentanteCompradorV>();
					lstRecuperaRepLeg = lstRepresentanteCompradorVTmp;
				}
			}
		} catch (JDBCException e) {	
			addActionError("Ocurri� un error inesperado al consultar los estados asociados al programa, favor de reportar al administrador");
			AppLogger.error("errores","Ocurri� un error al consultar los estados asociados al programa debido a:"+e.getCause());
			e.printStackTrace();
		}
		lstRepLegalV = cDAO.consultaRepresentanteLegalV(-1);
		return SUCCESS;
	}
	
	public String verExpediente(){
		try{
			comprador = cDAO.consultaComprador(idComprador).get(0);
			rutaComprador =  utileriasDAO.getParametros("RUTA_COMPRADORES");
			
			if(comprador.getRutaComprador()!=null){
				rutaComprador += comprador.getRutaComprador();
			}else{
				Utilerias.crearDirectorio(rutaComprador+comprador.getIdComprador());
				//guardar la ruta comprador
				comprador.setRutaComprador(comprador.getIdComprador()+"/");
				cDAO.guardaObjeto(comprador);
			}	
			//Consigue expediente del comprador
			if(comprador.getTipoPersona().equals("M")){
				lstExpedientes = cDAO.consultaExpediente(0, "CM");
			}else{
				lstExpedientes = cDAO.consultaExpediente(0, "CF");
			}
			
			lstCompradorExpedientesV = cDAO.consultaCompradoresExpedientesV(0,idComprador,0);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	public String cargarExpedienteComprador(){
		try {
			comprador = cDAO.consultaComprador(idComprador).get(0);
			rutaComprador =  utileriasDAO.getParametros("RUTA_COMPRADORES");
			rutaComprador += comprador.getRutaComprador();
			String ext = docFileName.toLowerCase().substring( docFileName.lastIndexOf(".") );
			nombreArchivo = idExpediente+new java.text.SimpleDateFormat("yyyyMMddHHmm").format(new Date() )+ext;
			if(idExpedienteComprador != 0){//Si ya existe el expediente, actualiza registro
				List<ExpedienteComprador> lstExpedientesComprador = cDAO.consultaExpedienteComprador(idExpedienteComprador, 0, 0);
				ExpedienteComprador e = lstExpedientesComprador.get(0);
				//borrando el archivo existente
				File f = new File(rutaComprador+e.getRutaExpediente());
				if(f.exists()){
					if(!f.delete()){
						AppLogger.error("errores","Error al eliminar: "+e.getRutaExpediente());
					}else{
						AppLogger.info("app","Se borro archivo: "+e.getRutaExpediente());
					}
				}
				e.setRutaExpediente(nombreArchivo);
				cDAO.guardaObjeto(e);
			}else{
				//guarda expediente comprador
				ExpedienteComprador ec = new ExpedienteComprador();
				ec.setRutaExpediente(nombreArchivo);
				ec.setIdComprador(comprador.getIdComprador());
				ec.setIdExpediente(idExpediente);
				cDAO.guardaObjeto(ec);
			}
			Utilerias.cargarArchivo(rutaComprador, nombreArchivo, doc);
		} catch (JDBCException e) {
				e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			verExpediente();	
		}
		return SUCCESS;
	}
	
	public String cambiarEstatus(){
		try{
			comprador = cDAO.consultaComprador(idComprador, null, "","").get(0);
			if (comprador.getEstatus() == 1){
				comprador.setEstatus(0);
				comprador.setMotivoInhabilitado(motivo);
			}else if (comprador.getEstatus() == 0){
				comprador.setEstatus(1);
			}
			cDAO.guardaObjeto(comprador);
			msjOk="Se actualiz� el estatus correctamente";
			busquedaCompradores();
			nombre = "";
			rfc="";
		}catch(JDBCException e) {	
			e.printStackTrace();
		}
		return SUCCESS;
	}
	

	public List<Estado> getLstEstados() {
		return lstEstados;
	}

	public void setLstEstados(List<Estado> lstEstados) {
		this.lstEstados = lstEstados;
	}

	public List<Municipios> getLstMunicipios() {
		return lstMunicipios;
	}

	public void setLstMunicipios(List<Municipios> lstMunicipios) {
		this.lstMunicipios = lstMunicipios;
	}
	
	public List<Localidades> getLstLocalidades() {
		return lstLocalidades;
	}

	public void setLstLocalidades(List<Localidades> lstLocalidades) {
		this.lstLocalidades = lstLocalidades;
	}

	public List<Comprador> getLstCompradores() {
		return lstCompradores;
	}
	public void setLstCompradores(List<Comprador> lstCompradores) {
		this.lstCompradores = lstCompradores;
	}

	public List<Expediente> getLstExpedientes() {
		return lstExpedientes;
	}

	public void setLstExpedientes(List<Expediente> lstExpedientes) {
		this.lstExpedientes = lstExpedientes;
	}
	
	public List<Programa> getLstProgramas() {
		return lstProgramas;
	}

	public void setLstProgramas(List<Programa> lstProgramas) {
		this.lstProgramas = lstProgramas;
	}
	
	public int getIdComprador() {
		return idComprador;
	}

	public void setIdComprador(int idComprador) {
		this.idComprador = idComprador;
	}

	public Comprador getComprador() {
		return comprador;
	}

	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}

	public void setSession(Map<String, Object> session) {
	    this.session = session;
	}
	
	public Map<String, Object> getSession() {
	    return session;
	}

	public String getMsjOk() {
		return msjOk;
	}

	public void setMsjOk(String msjOk) {
		this.msjOk = msjOk;
	}

	public int getPersonaFiscal() {
		return personaFiscal;
	}

	public void setPersonaFiscal(int personaFiscal) {
		this.personaFiscal = personaFiscal;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public Integer getClaveLocalidad() {
		return claveLocalidad;
	}

	public void setClaveLocalidad(Integer claveLocalidad) {
		this.claveLocalidad = claveLocalidad;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getNumExterior() {
		return numExterior;
	}

	public void setNumExterior(String numExterior) {
		this.numExterior = numExterior;
	}

	public String getNumInterior() {
		return numInterior;
	}

	public void setNumInterior(String numInterior) {
		this.numInterior = numInterior;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Integer getClaveMunicipio() {
		return claveMunicipio;
	}

	public void setClaveMunicipio(Integer claveMunicipio) {
		this.claveMunicipio = claveMunicipio;
	}

	public CompradoresV getCompradoresV() {
		return compradoresV;
	}

	public void setCompradoresV(CompradoresV compradoresV) {
		this.compradoresV = compradoresV;
	}

	public int getEditar() {
		return editar;
	}

	public void setEditar(int editar) {
		this.editar = editar;
	}

	public int getNumCampos() {
		return numCampos;
	}

	public void setNumCampos(int numCampos) {
		this.numCampos = numCampos;
	}

	public int[] getCapRepresentante() {
		return capRepresentante;
	}

	public void setCapRepresentante(int[] capRepresentante) {
		this.capRepresentante = capRepresentante;
	}

	public String[] getCapRfc() {
		return capRfc;
	}

	public void setCapRfc(String[] capRfc) {
		this.capRfc = capRfc;
	}

	public String[] getCapCurp() {
		return capCurp;
	}

	public void setCapCurp(String[] capCurp) {
		this.capCurp = capCurp;
	}

	public File getDoc() {
		return doc;
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}

	public String getRutaComprador() {
		return rutaComprador;
	}

	public void setRutaComprador(String rutaComprador) {
		this.rutaComprador = rutaComprador;
	}

	public String getDocFileName() {
		return docFileName;
	}

	public void setDocFileName(String docFileName) {
		this.docFileName = docFileName;
	}

	public String getRutaExpediente() {
		return rutaExpediente;
	}

	public void setRutaExpediente(String rutaExpediente) {
		this.rutaExpediente = rutaExpediente;
	}

	public Integer getIdRepresentante() {
		return idRepresentante;
	}

	public void setIdRepresentante(Integer idRepresentante) {
		this.idRepresentante = idRepresentante;
	}

	public List<CompradorExpedientesV> getLstCompradorExpedientesV() {
		return lstCompradorExpedientesV;
	}

	public void setLstCompradorExpedientesV(List<CompradorExpedientesV> lstCompradorExpedientesV) {
		this.lstCompradorExpedientesV = lstCompradorExpedientesV;
	}

	public int getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(int idExpediente) {
		this.idExpediente = idExpediente;
	}

	public long getIdExpedienteComprador() {
		return idExpedienteComprador;
	}

	public void setIdExpedienteComprador(long idExpedienteComprador) {
		this.idExpedienteComprador = idExpedienteComprador;
	}

	public CatalogosDAO getcDAO() {
		return cDAO;
	}

	public void setcDAO(CatalogosDAO cDAO) {
		this.cDAO = cDAO;
	}

	public InscripcionDAO getiDAO() {
		return iDAO;
	}

	public void setiDAO(InscripcionDAO iDAO) {
		this.iDAO = iDAO;
	}

	public UtileriasDAO getUtileriasDAO() {
		return utileriasDAO;
	}

	public void setUtileriasDAO(UtileriasDAO utileriasDAO) {
		this.utileriasDAO = utileriasDAO;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
	public List<RepresentanteCompradorV> getLstRecuperaRepLeg() {
		return lstRecuperaRepLeg;
	}

	public void setLstRecuperaRepLeg(List<RepresentanteCompradorV> lstRecuperaRepLeg) {
		this.lstRecuperaRepLeg = lstRecuperaRepLeg;
	}

	public List<RepresentanteComprador> getLstRepresentanteComprador() {
		return lstRepresentanteComprador;
	}

	public void setLstRepresentanteComprador(
			List<RepresentanteComprador> lstRepresentanteComprador) {
		this.lstRepresentanteComprador = lstRepresentanteComprador;
	}

	public List<RepresentanteCompradorV> getLstRepresentanteCompradorV() {
		return lstRepresentanteCompradorV;
	}

	public void setLstRepresentanteCompradorV(
			List<RepresentanteCompradorV> lstRepresentanteCompradorV) {
		this.lstRepresentanteCompradorV = lstRepresentanteCompradorV;
	}

	public int getNumCamposAnterior() {
		return numCamposAnterior;
	}

	public void setNumCamposAnterior(int numCamposAnterior) {
		this.numCamposAnterior = numCamposAnterior;
	}

	public String getRepresentante() {
		return representante;
	}
	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public int[] getCapEstatus() {
		return capEstatus;
	}
	public void setCapEstatus(int[] capEstatus) {
		this.capEstatus = capEstatus;
	}

	public String getEstatusRepresentante() {
		return estatusRepresentante;
	}
	public void setEstatusRepresentante(String estatusRepresentante) {
		this.estatusRepresentante = estatusRepresentante;
	}

	public RepresentanteLegal getRepresentantel() {
		return representantel;
	}
	public void setRepresentantel(RepresentanteLegal representantel) {
		this.representantel = representantel;
	}

	public int getErrorRfc() {
		return errorRfc;
	}
	public void setErrorRfc(int errorRfc) {
		this.errorRfc = errorRfc;
	}

	public String getMsjError() {
		return msjError;
	}
	public void setMsjError(String msjError) {
		this.msjError = msjError;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Integer getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Integer usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Integer getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(Integer usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getDescestatus() {
		return descestatus;
	}

	public void setDescestatus(String descestatus) {
		this.descestatus = descestatus;
	}
	
	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Integer getEntidadNacimiento() {
		return entidadNacimiento;
	}

	public void setEntidadNacimiento(Integer entidadNacimiento) {
		this.entidadNacimiento = entidadNacimiento;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getTipoSexo() {
		return tipoSexo;
	}
	
	public void setTipoSexo(int tipoSexo) {
		this.tipoSexo = tipoSexo;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	public String getFolio() {
		return folio;
	}

	public int getIdPerfil() {
		return idPerfil;
	}
	
	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}

	public List<RepresentanteLegalV> getLstRepLegalV() {
		return lstRepLegalV;
	}

	public void setLstRepLegalV(List<RepresentanteLegalV> lstRepLegalV) {
		this.lstRepLegalV = lstRepLegalV;
	}
	
}
