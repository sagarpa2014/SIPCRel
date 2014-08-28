package mx.gob.comer.sipc.vistas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RelacionMaritimaPorBarco {

	private Long id;
	private Long idPerRel;
	private Integer idRelacion;
	private Integer idComprador;
	private String  folioCartaAdhesion;
	private String nombreBarco;
	private Integer cultivoRegistro;
	private String cultivo;
	private Integer idPgrCiclo;
	private String cicloLargo;
	private Integer ejercicio;
		
	@Id
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "id_per_rel")
	public Long getIdPerRel() {
		return idPerRel;
	}
	public void setIdPerRel(Long idPerRel) {
		this.idPerRel = idPerRel;
	}

	@Column(name = "id_tipo_relacion")
	public Integer getIdRelacion() {
		return idRelacion;
	}
	public void setIdRelacion(Integer idRelacion) {
		this.idRelacion = idRelacion;
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
	
	@Column(name = "nombre_barco")
	public String getNombreBarco() {
		return nombreBarco;
	}
	public void setNombreBarco(String nombreBarco) {
		this.nombreBarco = nombreBarco;
	}
	
	@Column(name = "cultivo_registro")
	public Integer getCultivoRegistro() {
		return cultivoRegistro;
	}
	
	public void setCultivoRegistro(Integer cultivoRegistro) {
		this.cultivoRegistro = cultivoRegistro;
	}
	@Column(name = "cultivo")
	public String getCultivo() {
		return cultivo;
	}
	public void setCultivo(String cultivo) {
		this.cultivo = cultivo;
	}

	@Column(name = "id_pgr_ciclo")
	public Integer getIdPgrCiclo() {
		return idPgrCiclo;
	}
	public void setIdPgrCiclo(Integer idPgrCiclo) {
		this.idPgrCiclo = idPgrCiclo;
	}
	
	@Column(name = "ciclo_largo")
	public String getCicloLargo() {
		return cicloLargo;
	}
	public void setCicloLargo(String cicloLargo) {
		this.cicloLargo = cicloLargo;
	}
	
	@Column(name = "ejercicio")
	public Integer getEjercicio() {
		return ejercicio;
	}
	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
	}	

}
