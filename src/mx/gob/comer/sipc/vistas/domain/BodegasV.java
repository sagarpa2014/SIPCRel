package mx.gob.comer.sipc.vistas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bodegas_v")
public class BodegasV {
	
	private String claveBodega;
	private String nombre;
	private String rfcBodega;
	private String nombreEstado;
	private String direccion;
	private Integer codigoPostal;

	@Id	
	@Column(name = "clave_bodega")
	public String getClaveBodega() {
		return claveBodega;
	}
	public void setClaveBodega(String claveBodega) {
		this.claveBodega = claveBodega;
	}
	
	@Column(name =  "nombre")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name =  "rfc_bodega")
	public String getRfcBodega() {
		return rfcBodega;
	}
	public void setRfcBodega(String rfcBodega) {
		this.rfcBodega = rfcBodega;
	}
	
	@Column(name =  "nombre_estado")
	public String getNombreEstado() {
		return nombreEstado;
	}
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	
	@Column(name =  "direccion")
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	@Column(name =  "codigo_postal")
	public Integer getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
}
