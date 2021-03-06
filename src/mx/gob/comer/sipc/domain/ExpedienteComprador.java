package mx.gob.comer.sipc.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "expedientes_compradores")
public class ExpedienteComprador implements Serializable{
	/**
	 * 
	 */
	private Long idExpedienteComprador;
	private Integer idComprador;
	private String rutaExpediente;
	private Integer idExpediente;
	
	  
	@Id
	@GeneratedValue(generator = "expedientes_compradores_id_expediente_comprador_seq")
	@SequenceGenerator(name = "expedientes_compradores_id_expediente_comprador_seq", sequenceName = "expedientes_compradores_id_expediente_comprador_seq")
	@Column(name = "id_expediente_comprador")
	public Long getIdExpedienteComprador() {
		return idExpedienteComprador;
	}
	public void setIdExpedienteComprador(Long idExpedienteComprador) {
		this.idExpedienteComprador = idExpedienteComprador;
	}

	@Column(name = "id_comprador")
	public Integer getIdComprador() {
		return idComprador;
	}
	public void setIdComprador(Integer idComprador) {
		this.idComprador = idComprador;
	}
	
	@Column(name = "id_expediente")
	public Integer getIdExpediente() {
		return idExpediente;
	}
	
	public void setIdExpediente(Integer idExpediente) {
		this.idExpediente = idExpediente;
	}
	
	
	@Column(name = "ruta_expediente")
	public String getRutaExpediente() {
		return rutaExpediente;
	}
	public void setRutaExpediente(String rutaExpediente) {
		this.rutaExpediente = rutaExpediente;
	}

	
	
		
}
