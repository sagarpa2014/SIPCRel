package mx.gob.comer.sipc.vistas.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "solicitud_inscripcion_v")
public class SolicitudInscripcionV {
	private Long idSI;
	private String folioSI;
	private Integer idPrograma;
	private String programa;
	private Integer idArea;
	private Integer idComprador;
	private String comprador;
	private Integer idCicloPrograma;
	private String cicloCorto;
	private Integer ejercicio;
	private Date fechaDocSI;
	private Date fechaAcuseSI;
	private String numeroAuditor;
	private String auditor;
	private String tipoReporte;
	private String nomArchivoSI;
	private String nomArchivoOPA;
	private Date fechaDocOPA;
	private Date fechaAcuseOPA;
	private String noOficioPA;
	private Integer estatus;
	private String desEstatusSI;
	private Integer acreditacionJuridica;
	private String nomArchivoOA;
	private Date fechaDocOA;
	private Date fechaAcuseOA;
	private String noOficioA;
	private String nomArchivoOOA;
	private Date fechaDocOOA;
	private Date fechaAcuseOOA;
	private String noOficioOA;
	private String nomArchivoLeg;
	private Date fechaDocLeg;
	private Date fechaAcuseLeg;
	private String noOficioLeg;
	private Integer acreditacionDictaminacion;
	private String nomArchivoDic;
	private Date fechaDocDic;
	private Date fechaAcuseDic;
	private String noOficioDic;
	private Integer acreditacionSI;
	private String obsSolInscripcion;
	private String obsAcreditacionDic;
	private Boolean tTramitePresentacionSI;
	private Boolean tObservacionSI;
	private Double importeInscripcion;
	private Double volumenInscripcion;
	private Integer idCultivo;
	private String cultivo;
	private String folioCartaAdhesion;
	private String nomArchivoCa;
	private Date fechaFirmaCa;
	private Integer consecutivoCarta;
	private String acronimoCA;
	private Date fechaAcuseFCA;
	private Double volumenTotalCarta;
	private Double importeTotalCarta;
	private Date fechaFirmaCAComplemento;
	private String nomArchivoCACom;
	private Integer idCiclo;	
	
	@Id	
	@Column(name = "id_si")
	public Long getIdSI() {
		return idSI;
	}
	public void setIdSI(Long idSI) {
		this.idSI = idSI;
	}
	
	@Column(name = "folio_si")
	public String getFolioSI() {
		return folioSI;
	}
	public void setFolioSI(String folioSI) {
		this.folioSI = folioSI;
	}
	
	@Column(name = "id_programa")
	public Integer getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	
	@Column(name = "descripcion_corta")
	public String getPrograma() {
		return programa;
	}
	public void setPrograma(String programa) {
		this.programa = programa;
	}
	
	@Column(name = "id_area")
	public Integer getIdArea() {
		return idArea;
	}
	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}
	@Column(name = "id_comprador")
	public Integer getIdComprador() {
		return idComprador;
	}
	public void setIdComprador(Integer idComprador) {
		this.idComprador = idComprador;
	}
	
	@Column(name = "comprador")
	public String getComprador() {
		return comprador;
	}
	public void setComprador(String comprador) {
		this.comprador = comprador;
	}
	@Column(name = "id_ciclo_programa")
	public Integer getIdCicloPrograma() {
		return idCicloPrograma;
	}
	
	public void setIdCicloPrograma(Integer idCicloPrograma) {
		this.idCicloPrograma = idCicloPrograma;
	}
	
	@Column(name = "ciclo_corto")
	public String getCicloCorto() {
		return cicloCorto;
	}
	public void setCicloCorto(String cicloCorto) {
		this.cicloCorto = cicloCorto;
	}
	
	@Column(name = "id_ejercicio")
	public Integer getEjercicio() {
		return ejercicio;
	}
	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
	}
	
	@Column(name = "fecha_doc_si")
	public Date getFechaDocSI() {
		return fechaDocSI;
	}
	
	public void setFechaDocSI(Date fechaDocSI) {
		this.fechaDocSI = fechaDocSI;
	}
	
	@Column(name = "fecha_acuse_si")
	public Date getFechaAcuseSI() {
		return fechaAcuseSI;
	}
	public void setFechaAcuseSI(Date fechaAcuseSI) {
		this.fechaAcuseSI = fechaAcuseSI;
	}
	
	@Column(name = "numero_auditor")
	public String getNumeroAuditor() {
		return numeroAuditor;
	}
	public void setNumeroAuditor(String numeroAuditor) {
		this.numeroAuditor = numeroAuditor;
	}
	
	@Column(name = "auditor")
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	@Column(name = "tipo_reporte")
	public String getTipoReporte() {
		return tipoReporte;
	}
	public void setTipoReporte(String tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	
	@Column(name = "nom_archivo_si")
	public String getNomArchivoSI() {
		return nomArchivoSI;
	}
	public void setNomArchivoSI(String nomArchivoSI) {
		this.nomArchivoSI = nomArchivoSI;
	}
	
	@Column(name = "nom_archivo_opa")
	public String getNomArchivoOPA() {
		return nomArchivoOPA;
	}
	public void setNomArchivoOPA(String nomArchivoOPA) {
		this.nomArchivoOPA = nomArchivoOPA;
	}
	
	@Column(name = "fecha_doc_opa")
	public Date getFechaDocOPA() {
		return fechaDocOPA;
	}
	public void setFechaDocOPA(Date fechaDocOPA) {
		this.fechaDocOPA = fechaDocOPA;
	}
	
	@Column(name = "fecha_acuse_opa")
	public Date getFechaAcuseOPA() {
		return fechaAcuseOPA;
	}
	public void setFechaAcuseOPA(Date fechaAcuseOPA) {
		this.fechaAcuseOPA = fechaAcuseOPA;
	}
	
	@Column(name = "no_oficio_pa")
	public String getNoOficioPA() {
		return noOficioPA;
	}
	public void setNoOficioPA(String noOficioPA) {
		this.noOficioPA = noOficioPA;
	}
	
	@Column(name = "estatus")
	public Integer getEstatus() {
		return estatus;
	}
	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}
	
	@Column(name = "acreditacion_juridica")
	public Integer getAcreditacionJuridica() {
		return acreditacionJuridica;
	}
	public void setAcreditacionJuridica(Integer acreditacionJuridica) {
		this.acreditacionJuridica = acreditacionJuridica;
	}
	
	@Column(name = "des_estatus_si")
	public String getDesEstatusSI() {
		return desEstatusSI;
	}
	public void setDesEstatusSI(String desEstatusSI) {
		this.desEstatusSI = desEstatusSI;
	}
	@Column(name = "nom_archivo_oa")
	public String getNomArchivoOA() {
		return nomArchivoOA;
	}
	public void setNomArchivoOA(String nomArchivoOA) {
		this.nomArchivoOA = nomArchivoOA;
	}
	
	@Column(name = "fecha_doc_oa")
	public Date getFechaDocOA() {
		return fechaDocOA;
	}
	public void setFechaDocOA(Date fechaDocOA) {
		this.fechaDocOA = fechaDocOA;
	}
	
	@Column(name = "fecha_acuse_oa")
	public Date getFechaAcuseOA() {
		return fechaAcuseOA;
	}
	public void setFechaAcuseOA(Date fechaAcuseOA) {
		this.fechaAcuseOA = fechaAcuseOA;
	}
	
	@Column(name = "no_oficio_a")
	public String getNoOficioA() {
		return noOficioA;
	}
	public void setNoOficioA(String noOficioA) {
		this.noOficioA = noOficioA;
	}
	
	@Column(name = "nom_archivo_ooa")
	public String getNomArchivoOOA() {
		return nomArchivoOOA;
	}
	public void setNomArchivoOOA(String nomArchivoOOA) {
		this.nomArchivoOOA = nomArchivoOOA;
	}
	
	@Column(name = "fecha_doc_ooa")
	public Date getFechaDocOOA() {
		return fechaDocOOA;
	}
	public void setFechaDocOOA(Date fechaDocOOA) {
		this.fechaDocOOA = fechaDocOOA;
	}
	
	@Column(name = "fecha_acuse_ooa")
	public Date getFechaAcuseOOA() {
		return fechaAcuseOOA;
	}
	public void setFechaAcuseOOA(Date fechaAcuseOOA) {
		this.fechaAcuseOOA = fechaAcuseOOA;
	}
	
	@Column(name = "no_oficio_oa")
	public String getNoOficioOA() {
		return noOficioOA;
	}
	public void setNoOficioOA(String noOficioOA) {
		this.noOficioOA = noOficioOA;
	}
	@Column(name = "nom_archivo_leg")
	public String getNomArchivoLeg() {
		return nomArchivoLeg;
	}
	public void setNomArchivoLeg(String nomArchivoLeg) {
		this.nomArchivoLeg = nomArchivoLeg;
	}
	@Column(name = "fecha_doc_leg")
	public Date getFechaDocLeg() {
		return fechaDocLeg;
	}
	public void setFechaDocLeg(Date fechaDocLeg) {
		this.fechaDocLeg = fechaDocLeg;
	}
	
	@Column(name = "fecha_acuse_leg")
	public Date getFechaAcuseLeg() {
		return fechaAcuseLeg;
	}
	public void setFechaAcuseLeg(Date fechaAcuseLeg) {
		this.fechaAcuseLeg = fechaAcuseLeg;
	}
	
	@Column(name = "no_oficio_leg")
	public String getNoOficioLeg() {
		return noOficioLeg;
	}
	public void setNoOficioLeg(String noOficioLeg) {
		this.noOficioLeg = noOficioLeg;
	}
	
	@Column(name = "acreditacion_dictaminacion")
	public Integer getAcreditacionDictaminacion() {
		return acreditacionDictaminacion;
	}
	public void setAcreditacionDictaminacion(Integer acreditacionDictaminacion) {
		this.acreditacionDictaminacion = acreditacionDictaminacion;
	}
	
	@Column(name = "nom_archivo_dic")
	public String getNomArchivoDic() {
		return nomArchivoDic;
	}
	public void setNomArchivoDic(String nomArchivoDic) {
		this.nomArchivoDic = nomArchivoDic;
	}
	
	@Column(name = "fecha_doc_dic")
	public Date getFechaDocDic() {
		return fechaDocDic;
	}
	public void setFechaDocDic(Date fechaDocDic) {
		this.fechaDocDic = fechaDocDic;
	}
	
	@Column(name = "fecha_acuse_dic")
	public Date getFechaAcuseDic() {
		return fechaAcuseDic;
	}
	public void setFechaAcuseDic(Date fechaAcuseDic) {
		this.fechaAcuseDic = fechaAcuseDic;
	}
	
	@Column(name = "no_oficio_dic")
	public String getNoOficioDic() {
		return noOficioDic;
	}
	public void setNoOficioDic(String noOficioDic) {
		this.noOficioDic = noOficioDic;
	}
	
	@Column(name = "acreditacion_si")
	public Integer getAcreditacionSI() {
		return acreditacionSI;
	}
	public void setAcreditacionSI(Integer acreditacionSI) {
		this.acreditacionSI = acreditacionSI;
	}	
	
	@Column(name = "obs_sol_inscripcion")
	public String getObsSolInscripcion() {
		return obsSolInscripcion;
	}
	public void setObsSolInscripcion(String obsSolInscripcion) {
		this.obsSolInscripcion = obsSolInscripcion;
	}
	@Column(name = "obs_acreditacion_dic")
	public String getObsAcreditacionDic() {
		return obsAcreditacionDic;
	}
	public void setObsAcreditacionDic(String obsAcreditacionDic) {
		this.obsAcreditacionDic = obsAcreditacionDic;
	}
	
	@Column(name = "t_tramite_presentacion_si")
	public Boolean gettTramitePresentacionSI() {
		return tTramitePresentacionSI;
	}
	public void settTramitePresentacionSI(Boolean tTramitePresentacionSI) {
		this.tTramitePresentacionSI = tTramitePresentacionSI;
	}
	
	@Column(name = "t_observacion_si")
	public Boolean gettObservacionSI() {
		return tObservacionSI;
	}
	public void settObservacionSI(Boolean tObservacionSI) {
		this.tObservacionSI = tObservacionSI;
	}
	@Column(name = "importe_inscripcion")
	public Double getImporteInscripcion() {
		return importeInscripcion;
	}
	public void setImporteInscripcion(Double importeInscripcion) {
		this.importeInscripcion = importeInscripcion;
	}
	@Column(name = "volumen_inscripcion")
	public Double getVolumenInscripcion() {
		return volumenInscripcion;
	}
	public void setVolumenInscripcion(Double volumenInscripcion) {
		this.volumenInscripcion = volumenInscripcion;
	}
	
	@Column(name = "id_cultivo")
	public Integer getIdCultivo() {
		return idCultivo;
	}
	public void setIdCultivo(Integer idCultivo) {
		this.idCultivo = idCultivo;
	}

	@Column(name = "cultivo")
	public String getCultivo() {
		return cultivo;
	}
	public void setCultivo(String cultivo) {
		this.cultivo = cultivo;
	}
	
	@Column(name = "folio_carta_adhesion")
	public String getFolioCartaAdhesion() {
		return folioCartaAdhesion;
	}
	public void setFolioCartaAdhesion(String folioCartaAdhesion) {
		this.folioCartaAdhesion = folioCartaAdhesion;
	}
	
	@Column(name = "nom_archivo_ca")
	public String getNomArchivoCa() {
		return nomArchivoCa;
	}
	public void setNomArchivoCa(String nomArchivoCa) {
		this.nomArchivoCa = nomArchivoCa;
	}
		
	@Column(name = "fecha_firma_ca")
	public Date getFechaFirmaCa() {
		return fechaFirmaCa;
	}
	public void setFechaFirmaCa(Date fechaFirmaCa) {
		this.fechaFirmaCa = fechaFirmaCa;
	}
		
	@Column(name = "consecutivo_carta")
	public Integer getConsecutivoCarta() {
		return consecutivoCarta;
	}
	public void setConsecutivoCarta(Integer consecutivoCarta) {
		this.consecutivoCarta = consecutivoCarta;
	}
	
	@Column(name = "acronimo_ca")
	public String getAcronimoCA() {
		return acronimoCA;
	}
	public void setAcronimoCA(String acronimoCA) {
		this.acronimoCA = acronimoCA;
	}
	
	@Column(name = "fecha_acuse_fca")
	public Date getFechaAcuseFCA() {
		return fechaAcuseFCA;
	}
	public void setFechaAcuseFCA(Date fechaAcuseFCA) {
		this.fechaAcuseFCA = fechaAcuseFCA;
	}
	
	@Column(name = "volumen_total_carta")
	public Double getVolumenTotalCarta() {
		return volumenTotalCarta;
	}
	public void setVolumenTotalCarta(Double volumenTotalCarta) {
		this.volumenTotalCarta = volumenTotalCarta;
	}
	
	@Column(name = "importe_total_carta")
	public Double getImporteTotalCarta() {
		return importeTotalCarta;
	}
	public void setImporteTotalCarta(Double importeTotalCarta) {
		this.importeTotalCarta = importeTotalCarta;
	}	
	
	@Column(name = "fecha_firma_ca_complemento")
	public Date getFechaFirmaCAComplemento() {
		return fechaFirmaCAComplemento;
	}
	public void setFechaFirmaCAComplemento(Date fechaFirmaCAComplemento) {
		this.fechaFirmaCAComplemento = fechaFirmaCAComplemento;
	}
	
	@Column(name = "nom_archivo_ca_com")
	public String getNomArchivoCACom() {
		return nomArchivoCACom;
	}
	
	public void setNomArchivoCACom(String nomArchivoCACom) {
		this.nomArchivoCACom = nomArchivoCACom;
	}

	@Column(name = "id_ciclo")
	public Integer getIdCiclo() {
		return idCiclo;
	}
	public void setIdCiclo(Integer idCiclo) {
		this.idCiclo = idCiclo;
	}
}
