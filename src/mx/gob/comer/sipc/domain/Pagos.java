package mx.gob.comer.sipc.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
*Pagos
*
*Clase que mapea a la tabla "pagos" de la base de datos  
*
*Versi�n 1.0
*
*Enero 2013
*
*/

@Entity
@Table(name="pagos")
public class Pagos {

	private Long idPago;
	private int idPrograma;
	private int idComprador;
	private String clabe;
	private Double importe;
	private Double volumen;
	private String claveRastreo;
	private Integer idOficio;
	private Integer estatus;
	private Date fechaCreacion;
	private Integer usuarioCreacion;
	private Date fechaModificacion;
	private Integer usuarioModificacion;
	private Date fechaPresentacion;
	private Date fechaPago;
	private Set<PagosDetalle> pagosDetalle;
	private String noCarta;
	private String desRechazo;
	private String etapa;
	private String atentaNota;
	private String archivoAtentaNota;
	private Date fechaAtentaNota;
	private Boolean fianza;
	
	public Pagos (){
		
	}
	
	public Pagos(Pagos v) {
		super();
		this.idPrograma = v.getIdPrograma();
		this.idComprador = v.getIdComprador();
		this.clabe = v.getClabe();
		this.importe = v.getImporte();
		this.volumen = v.getVolumen();
		this.pagosDetalle = v.getPagosDetalle();
	}




	@Id
	@GeneratedValue(generator = "pagos_id_pago_seq")
	@SequenceGenerator(name = "pagos_id_pago_seq", sequenceName = "pagos_id_pago_seq")
	@Column(name = "id_pago")
	public Long getIdPago() {
		return idPago;
	}

	public void setIdPago(Long idPago) {
		this.idPago = idPago;
	}	
	
	@Column(name = "id_programa")
	public int getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(int idPrograma) {
		this.idPrograma = idPrograma;
	}
	
	@Column(name = "id_comprador")
	public int getIdComprador() {
		return idComprador;
	}

	public void setIdComprador(int idComprador) {
		this.idComprador = idComprador;
	}

	@Column(name = "clabe")
	public String getClabe() {
		return clabe;
	}

	public void setClabe(String clabe) {
		this.clabe = clabe;
	}

	@Column(name = "importe")
	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	@Column(name = "volumen")
	public Double getVolumen() {
		return volumen;
	}

	public void setVolumen(Double volumen) {
		this.volumen = volumen;
	}

	@Column(name = "clave_rastreo")
	public String getClaveRastreo() {
		return claveRastreo;
	}

	public void setClaveRastreo(String claveRastreo) {
		this.claveRastreo = claveRastreo;
	}

	@Column(name = "id_oficio")
	public Integer getIdOficio() {
		return idOficio;
	}

	public void setIdOficio(Integer idOficio) {
		this.idOficio = idOficio;
	}
	
	@Column(name = "estatus")
	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	@Column(name = "fecha_creacion")
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	@Column(name = "fecha_modificacion")
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@Column(name = "usuario_creacion")
	public Integer getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(Integer usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	@Column(name = "usuario_modificacion")
	public Integer getUsuarioModificacion() {
		return usuarioModificacion;
	}
	
	public void setUsuarioModificacion(Integer usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	
	@Column(name = "fecha_presentacion")
	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	@Column(name = "fecha_pago")
	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	@OneToMany (cascade = {CascadeType.ALL},fetch = FetchType.LAZY)	
	@JoinColumn(name="id_pago", nullable=false) 	
	public Set<PagosDetalle> getPagosDetalle() {
		return pagosDetalle;
	}

	public void setPagosDetalle(Set<PagosDetalle> pagosDetalle) {
		this.pagosDetalle = pagosDetalle;
	}
	
	@Column(name = "no_carta")
	public String getNoCarta() {
		return noCarta;
	}

	public void setNoCarta(String noCarta) {
		this.noCarta = noCarta;
	}

	@Override
	public String toString() {
		return "Pagos [idPrograma=" + idPrograma + ", idComprador="
				+ idComprador + ", clabe=" + clabe
				+ ", importe=" + importe + ", volumen=" + volumen
				+  "]";
	}
	
	@Column(name = "desc_rechazo")
	public String getDescRechazo() {
		return desRechazo;
	}

	public void setDescRechazo(String desRechazo) {
		this.desRechazo = desRechazo;
	}

	@Column(name = "etapa")
	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	@Column(name = "archivo_atenta_nota")
	public String getArchivoAtentaNota() {
		return archivoAtentaNota;
	}

	public void setArchivoAtentaNota(String archivoAtentaNota) {
		this.archivoAtentaNota = archivoAtentaNota;
	}

	@Column(name = "atenta_nota")
	public String getAtentaNota() {
		return atentaNota;
	}

	public void setAtentaNota(String atentaNota) {
		this.atentaNota = atentaNota;
	}
	
	@Column(name = "fecha_atenta_nota")
	public Date getFechaAtentaNota() {
		return fechaAtentaNota;
	}

	public void setFechaAtentaNota(Date fechaAtentaNota) {
		this.fechaAtentaNota = fechaAtentaNota;
	}

	@Column(name = "fianza")
	public Boolean getFianza() {
		return fianza;
	}

	public void setFianza(Boolean fianza) {
		this.fianza = fianza;
	}

	
}
