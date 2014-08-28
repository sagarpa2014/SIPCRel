package mx.gob.comer.sipc.vistas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "asignacion_cartas_adhesion_especialista_v")
public class AsignacionCartasAdhesionEspecialistaV {
	
	private long idAsiganacionCA;
	private Integer idEspecialista;
	private Integer idComprador;
	private String folioCartaAdhesion;
	private Integer idPrograma;
	private Integer idEstado;
	private String estado;
	private Integer idCultivo;
	private String cultivo;
	private Integer idVariedad;
	private String variedad;;
	private Double cuota;
	private Double volumen;
	private Double importe;
	private Integer estatus;
	private Boolean fianza;
	private String clabe;
	private Double volumenApoyado;
	private Double importeApoyado;
	private String etapa;
	private String programa;
	private Integer idCriterioPago;
	private String criterioPago;
	private String comprador;
	private String descEstatus;
	private String descFianza;
	
	@Id
	@Column(name = "id_asignacion_ca")
	public long getIdAsiganacionCA() {
		return idAsiganacionCA;
	}
	public void setIdAsiganacionCA(long idAsiganacionCA) {
		this.idAsiganacionCA = idAsiganacionCA;
	}

	@Column(name = "id_especialista")
	public Integer getIdEspecialista() {
		return idEspecialista;
	}
	public void setIdEspecialista(Integer idEspecialista) {
		this.idEspecialista = idEspecialista;
	}

	@Column(name = "id_comprador")
	public Integer getIdComprador() {
		return idComprador;
	}
	public void setIdComprador(Integer idComprador) {
		this.idComprador = idComprador;
	}  

	@Column(name = "folio_carta_adhesion")        
	public String getFolioCartaAdhesion() {
		return folioCartaAdhesion;
	}
	public void setFolioCartaAdhesion(String folioCartaAdhesion) {
		this.folioCartaAdhesion = folioCartaAdhesion;
	}
	
	@Column(name = "id_programa")
	public Integer getIdPrograma() {
		return idPrograma;
	}
	
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	@Column(name = "id_estado")
	public Integer getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
	@Column(name = "estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
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
	@Column(name = "id_variedad")
	public Integer getIdVariedad() {
		return idVariedad;
	}
	public void setIdVariedad(Integer idVariedad) {
		this.idVariedad = idVariedad;
	}
	
	@Column(name = "variedad")
	public String getVariedad() {
		return variedad;
	}
	public void setVariedad(String variedad) {
		this.variedad = variedad;
	}
	@Column(name = "cuota_por_volumen")
	public Double getCuota() {
		return cuota;
	}
	public void setCuota(Double cuota) {
		this.cuota = cuota;
	}
	@Column(name = "volumen")	
	public Double getVolumen() {
		return volumen;
	}
	public void setVolumen(Double volumen) {
		this.volumen = volumen;
	}
	
	@Column(name = "importe")
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	
	@Column(name = "clabe")
	public String getClabe() {
		return clabe;
	}
	public void setClabe(String clabe) {
		this.clabe = clabe;
	}
	
	@Column(name = "estatus")
	public Integer getEstatus() {
		return estatus;
	}
	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}
	
	@Column(name = "fianza")
	public Boolean getFianza() {
		return fianza;
	}
	public void setFianza(Boolean fianza) {
		this.fianza = fianza;
	}
	
	@Column(name = "volumen_apoyado")
	public Double getVolumenApoyado() {
		return volumenApoyado;
	}
	public void setVolumenApoyado(Double volumenApoyado) {
		this.volumenApoyado = volumenApoyado;
	}
	
	@Column(name = "importe_apoyado")
	public Double getImporteApoyado() {
		return importeApoyado;
	}
	public void setImporteApoyado(Double importeApoyado) {
		this.importeApoyado = importeApoyado;
	}
	
	@Column(name = "etapa")
	public String getEtapa() {
		return etapa;
	}
	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}
	
	@Column(name = "desc_programa")
	public String getPrograma() {
		return programa;
	}
	public void setPrograma(String programa) {
		this.programa = programa;
	}
	
	@Column(name = "criterio_pago")
	public Integer getIdCriterioPago() {
		return idCriterioPago;
	}
	public void setIdCriterioPago(Integer idCriterioPago) {
		this.idCriterioPago = idCriterioPago;
	}
	
	@Column(name = "des_criterio_pago")
	public String getCriterioPago() {
		return criterioPago;
	}
	public void setCriterioPago(String criterioPago) {
		this.criterioPago = criterioPago;
	}
	
	@Column(name = "nombre_comprador")
	public String getComprador() {
		return comprador;
	}
	public void setComprador(String comprador) {
		this.comprador = comprador;
	}
	
	@Column(name = "estatus_ca")
	public String getDescEstatus() {
		return descEstatus;
	}
	public void setDescEstatus(String descEstatus) {
		this.descEstatus = descEstatus;
	}
	
	@Column(name = "desc_fianza")
	public String getDescFianza() {
		return descFianza;
	}
	public void setDescFianza(String descFianza) {
		this.descFianza = descFianza;
	}	
}