package mx.gob.comer.sipc.domain.transaccionales;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "campos_relacion")
public class CamposRelacion {

	private long idCampoRelacion;
	private Integer idGrupoRelacion;
	private Integer idCampo;
	private Integer posicion;
	private Boolean campoOpcional;
	
	@Id	
	@Column(name =  "id_campo_relacion")
	@GeneratedValue(generator = "campos_relacion_id_campo_relacion_seq")
	@SequenceGenerator(name = "campos_relacion_id_campo_relacion_seq", sequenceName = "campos_relacion_id_campo_relacion_seq")
	public long getIdCampoRelacion() {
		return idCampoRelacion;
	}
	
	public void setIdCampoRelacion(long idCampoRelacion) {
		this.idCampoRelacion = idCampoRelacion;
	}
	
	
	@Column(name = "id_grupo_relacion", updatable=false, insertable=false)
	public Integer getIdGrupoRelacion() {
		return idGrupoRelacion;
	}

	public void setIdGrupoRelacion(Integer idGrupoRelacion) {
		this.idGrupoRelacion = idGrupoRelacion;
	}

	@Column(name =  "id_campo")
	public Integer getIdCampo() {
		return idCampo;
	}
	public void setIdCampo(Integer idCampo) {
		this.idCampo = idCampo;
	}
	
	@Column(name =  "posicion")
	public Integer getPosicion() {
		return posicion;
	}
	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	@Column(name =  "opcional")
	public Boolean getCampoOpcional() {
		return campoOpcional;
	}

	public void setCampoOpcional(Boolean campoOpcional) {
		this.campoOpcional = campoOpcional;
	}	
}
