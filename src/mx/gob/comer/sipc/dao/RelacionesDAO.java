package mx.gob.comer.sipc.dao;


import java.math.BigInteger;
import java.util.List;

import mx.gob.comer.sipc.domain.catalogos.Predios;
import mx.gob.comer.sipc.domain.catalogos.Productores;
import mx.gob.comer.sipc.domain.catalogos.Relaciones;
import mx.gob.comer.sipc.domain.transaccionales.CamposRelacionCertificados;
import mx.gob.comer.sipc.domain.transaccionales.CamposRelacionMaritima;
import mx.gob.comer.sipc.domain.transaccionales.CamposRelacionTerrestre;
import mx.gob.comer.sipc.domain.transaccionales.CamposRelacionVentas;
import mx.gob.comer.sipc.domain.transaccionales.ComprasContrato;
import mx.gob.comer.sipc.domain.transaccionales.ComprasDatosParticipante;
import mx.gob.comer.sipc.domain.transaccionales.ComprasDatosProductor;
import mx.gob.comer.sipc.domain.transaccionales.ComprasEntradaBodega;
import mx.gob.comer.sipc.domain.transaccionales.ComprasFacVenta;
import mx.gob.comer.sipc.domain.transaccionales.ComprasFacVentaGlobal;
import mx.gob.comer.sipc.domain.transaccionales.ComprasPagoProdAxc;
import mx.gob.comer.sipc.domain.transaccionales.ComprasPagoProdSinAxc;
import mx.gob.comer.sipc.domain.transaccionales.ComprasPredio;
import mx.gob.comer.sipc.domain.transaccionales.GruposRelacion;
import mx.gob.comer.sipc.domain.transaccionales.IniEsquemaRelacion;
import mx.gob.comer.sipc.domain.transaccionales.PersonalizacionRelaciones;
import mx.gob.comer.sipc.domain.transaccionales.ProgramaRelacionCiclos;
import mx.gob.comer.sipc.domain.transaccionales.ProgramaRelacionCultivos;
import mx.gob.comer.sipc.vistas.domain.ComprasBodegaTicketV;
import mx.gob.comer.sipc.vistas.domain.ComprasDatosParticipanteV;
import mx.gob.comer.sipc.vistas.domain.ComprasDatosProductorV;
import mx.gob.comer.sipc.vistas.domain.ComprasMaxCamposV;
import mx.gob.comer.sipc.vistas.domain.ComprasPredioV;
import mx.gob.comer.sipc.vistas.domain.GrupoPorRelacion;
import mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionCertificadosV;
import mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionDefaultV;
import mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionMaritimaV;
import mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionTerrestreV;
import mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionV;
import mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionVentasV;
import mx.gob.comer.sipc.vistas.domain.IniEsquemaRelacionV;
import mx.gob.comer.sipc.vistas.domain.PersonalizacionRelacionesV;
import mx.gob.comer.sipc.vistas.domain.ProgramaRelacionCiclosV;
import mx.gob.comer.sipc.vistas.domain.ProgramaRelacionCultivosV;
import mx.gob.comer.sipc.vistas.domain.RelacionMaritimaPorBarco;
import mx.gob.comer.sipc.vistas.domain.RelacionPorFolioCABodega;


import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.Transaction;



import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.googlecode.s2hibernate.struts2.plugin.annotations.TransactionTarget;

public class RelacionesDAO {
	

	@SessionTarget
	Session session;
	
	@TransactionTarget
	Transaction transaction;
		
	public RelacionesDAO() {
		
	}
	public RelacionesDAO(Session session) {
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	public List<Relaciones> consultaRelacion(int idRelacion)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<Relaciones> lst = null;
		
		if (idRelacion != 0 && idRelacion != -1){
			consulta.append("where idRelacion = ").append(idRelacion);
		}
		
		consulta.insert(0, "From Relaciones ");
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
	}
	
	
	public List<GruposCamposRelacionDefaultV> consultaGruposCamposRelacionDefaultV(int idRelacion, String tipoSeccion) throws JDBCException {
		return consultaGruposCamposRelacionDefaultV(idRelacion, tipoSeccion, 0);
	}
	
	public List<GruposCamposRelacionDefaultV> consultaGruposCamposRelacionDefaultV(long idGpoCampoRelacionD) throws JDBCException {
		return consultaGruposCamposRelacionDefaultV(0, null, idGpoCampoRelacionD);
	}
	
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionDefaultV> consultaGruposCamposRelacionDefaultV(int idRelacion, String tipoSeccion, long idGpoCampoRelacionD)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<GruposCamposRelacionDefaultV> lst = null;
		
		if (idRelacion != 0 && idRelacion != -1){
			consulta.append("where idRelacion = ").append(idRelacion);
		}

		if (tipoSeccion != null && !tipoSeccion.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and tipoSeccion='").append(tipoSeccion).append("'");
			}else{
				consulta.append("where tipoSeccion='").append(tipoSeccion).append("'");
			}
		}	
		if (idGpoCampoRelacionD != 0 && idGpoCampoRelacionD != -1){
			if(consulta.length()>0){
				consulta.append(" and idGpoCampoRelacionD =").append(idGpoCampoRelacionD);
			}else{
				consulta.append("where idGpoCampoRelacionD =").append(idGpoCampoRelacionD);
			}
		}
		consulta.insert(0, "From GruposCamposRelacionDefaultV ").append(" ORDER BY posicionGrupo, posicionCampo");
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
	}
	
	public List<GruposCamposRelacionV> consultaGruposCampostV(long idCampoRelacion, String tipoSeccion)throws  JDBCException{
		return consultaGruposCampostV(idCampoRelacion, -1, -1, -1, -1, tipoSeccion, "", "");
	}
	public List<GruposCamposRelacionV> consultaGruposCampostV(long idCampoRelacion, int idTipoRelacion, long idPerRel, int idGrupo, int idCampo, String tipoSeccion)throws  JDBCException{
		return consultaGruposCampostV(idCampoRelacion, idTipoRelacion, idPerRel, idGrupo, idCampo, tipoSeccion, "", "");
	}
	
	public List<GruposCamposRelacionV> consultaGruposCampostV(long idCampoRelacion, int idTipoRelacion, long idPerRel, int idGrupo, int idCampo, String tipoSeccion, String tipoDato)throws  JDBCException{
		return consultaGruposCampostV(idCampoRelacion, idTipoRelacion, idPerRel, idGrupo, idCampo, tipoSeccion, "", tipoDato);
	}
	
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionV> consultaGruposCampostV(long idCampoRelacion, int idTipoRelacion, long idPerRel, int idGrupo, int idCampo, String tipoSeccion, String tipoGrupo, String tipoDato)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<GruposCamposRelacionV> lst = null;
		
		if (idCampoRelacion != 0 && idCampoRelacion != -1){
			consulta.append("where idCampoRelacion = ").append(idCampoRelacion);
		}
		if (idTipoRelacion != 0 && idTipoRelacion != -1){
			if(consulta.length()>0){
				consulta.append(" and idTipoRelacion=").append(idTipoRelacion);
			}else{
				consulta.append("where idTipoRelacion=").append(idTipoRelacion);
			}
		}

		if (idPerRel != 0 && idPerRel != -1){
			if(consulta.length()>0){
				consulta.append(" and idPerRel=").append(idPerRel);
			}else{
				consulta.append("where idPerRel=").append(idPerRel);
			}
		}
		if (idGrupo != 0 && idGrupo != -1){
			if(consulta.length()>0){
				consulta.append(" and idGrupo=").append(idGrupo);
			}else{
				consulta.append("where idGrupo=").append(idGrupo);
			}
		}
		
		if (idCampo != 0 && idCampo != -1){
			if(consulta.length()>0){
				consulta.append(" and idCampo =").append(idCampo);
			}else{
				consulta.append("where idCampo =").append(idCampo);
			}
		}
		
		if (tipoSeccion !=  null && !tipoSeccion.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and tipoSeccion ='").append(tipoSeccion).append("'");
			}else{
				consulta.append("where tipoSeccion ='").append(tipoSeccion).append("'");
			}
		}
		
		if (tipoGrupo !=  null && !tipoGrupo.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and tipoGrupo ='").append(tipoGrupo).append("'");
			}else{
				consulta.append("where tipoGrupo ='").append(tipoGrupo).append("'");
			}
		}
		consulta.insert(0, "From GruposCamposRelacionV ").append(" ORDER BY posicionGrupo, posicionCampo");
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
	}
	
	public List<PersonalizacionRelacionesV> consultaPersonalizacionRelaciones(long idPerRel)throws  JDBCException{
		return consultaPersonalizacionRelaciones(idPerRel, 0,null, null, null, null,0);
	}
	
	@SuppressWarnings("unchecked")
	public List<PersonalizacionRelacionesV> consultaPersonalizacionRelaciones(long idPerRel, int idRelacion,  String cicloAgricola, String cultivos, String fechaInicio, String fechaFin, long idIniEsquemaRelacion)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<PersonalizacionRelacionesV> lst = null;
		if (idPerRel != 0 && idPerRel != -1){
			consulta.append("where idPerRel = ").append(idPerRel);
		}

		if (idRelacion != 0 && idRelacion != -1){
			if(consulta.length()>0){
				consulta.append(" and idTipoRelacion=").append(idRelacion);
			}else{
				consulta.append("where idTipoRelacion=").append(idRelacion);
			}
		}
		
		if (cicloAgricola != null && !cicloAgricola.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and cicloAgricola='").append(cicloAgricola).append("'");
			}else{
				consulta.append("where cicloAgricola='").append(cicloAgricola).append("'");
			}
		}
		if (cultivos != null &&  !cultivos.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and cultivos= '").append(cultivos).append("'");
			}else{
				consulta.append("where cultivos='").append(cultivos).append("'");
			}
		}
		
		if((fechaInicio != null && !fechaInicio.equals(""))&& (fechaFin !=null && !fechaFin.equals(""))){
			if(consulta.length()>0){
				consulta.append(" and  (TO_CHAR(fecha_creacion,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}else{
				consulta.append(" where (TO_CHAR(fecha_creacion,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}
		}else{
			if(fechaInicio != null && !fechaInicio.equals("")){
				if(consulta.length()>0){
					consulta.append(" and (TO_CHAR(fecha_creacion,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}else{
					consulta.append("where (TO_CHAR(fecha_creacion,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}
			}
		}
		
		if (idIniEsquemaRelacion != 0 &&  idIniEsquemaRelacion != -1){
			if(consulta.length()>0){
				consulta.append(" and idIniEsquemaRelacion=").append(idIniEsquemaRelacion);
			}else{
				consulta.append("where idIniEsquemaRelacion=").append(idIniEsquemaRelacion);
			}
		}
		
		consulta.insert(0, "From PersonalizacionRelacionesV ").append(" order by nombreEsquema, relacion");
		lst= session.createQuery(consulta.toString()).list();
				
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<GruposRelacion> consultaGruposRelacion(long idPerRel)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<GruposRelacion> lst = null;
		
		if (idPerRel != 0 && idPerRel != -1){
			consulta.append("where idPerRel = ").append(idPerRel);
		}

		consulta.insert(0, "From GruposRelacion ");
		lst= session.createQuery(consulta.toString()).list();

		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<GrupoPorRelacion> consultaGrupoPorRelacion(long idPerRel)throws  JDBCException{
		List<GrupoPorRelacion> lst = null;
		String consulta = "select g.id_grupo,  g.grupo, g.posicion_grupo,  g.visible, count(g.id_grupo) as  numero_campos " +
				"from grupos_campos_relacion_v g where g.tipo_seccion='DET' and id_per_rel = " + idPerRel +
				" group by g.id_grupo, g.grupo, g.posicion_grupo,  g.visible order by g.posicion_grupo";  
		lst= session.createSQLQuery(consulta.toString()).addEntity(GrupoPorRelacion.class).list();
		
		return lst;
	}
		
	
	
	
	@SuppressWarnings("unchecked")
	public List<ProgramaRelacionCultivosV> consultaCultivosPerRel(long idPerRel)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ProgramaRelacionCultivosV> lst=null;
		if (idPerRel != 0 && idPerRel != -1){
			consulta.append("where idPerRel = ").append(idPerRel);
		}
		consulta.insert(0, "From ProgramaRelacionCultivosV ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProgramaRelacionCiclosV> consultaCiclosEjerPerRel(long idPerRel)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ProgramaRelacionCiclosV> lst=null;
		if (idPerRel != 0 && idPerRel != -1){
			consulta.append("where idPerRel = ").append(idPerRel);
		}
		consulta.insert(0, "From ProgramaRelacionCiclosV ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	public int borrarPartProd(long idCompPer, long idCompProd)throws JDBCException {
		int elementosBorrados = 0;
		try{
			StringBuilder hql = new StringBuilder()			
			.append("DELETE FROM compras_predio WHERE id_comp_prod= ").append(idCompProd).append("; ");
			hql.append("DELETE FROM compras_contrato WHERE id_comp_prod= ").append(idCompProd).append("; ");
			hql.append("DELETE FROM compras_entrada_bodega WHERE id_comp_prod= ").append(idCompProd).append("; ");
			hql.append("DELETE FROM compras_fac_venta WHERE id_comp_prod= ").append(idCompProd).append("; ");
			hql.append("DELETE FROM compras_fac_venta_global WHERE id_comp_prod= ").append(idCompProd).append("; ");
			hql.append("DELETE FROM compras_pago_prod_sin_axc WHERE id_comp_prod= ").append(idCompProd).append("; ");
			hql.append("DELETE FROM compras_pago_prod_axc WHERE id_comp_prod= ").append(idCompProd).append(";");
			hql.append("DELETE FROM compras_pago_prod_sin_axc WHERE id_comp_prod= ").append(idCompProd).append("; ");
			hql.append("DELETE FROM compras_comp_productor WHERE id_comp_per= ").append(idCompPer).append(" and id_comp_prod =").append(idCompProd).append("; ");
			//hql.append("DELETE FROM compras_datos_participante WHERE id_comp_per= ").append(idCompPer);
			elementosBorrados = session.createSQLQuery(hql.toString()).executeUpdate();
			
		}catch (JDBCException e){
			transaction.rollback();
			throw e;
		}	
		return elementosBorrados;
	}
	
	
	
	public int borrarFolioMaritima(BigInteger folioSerial)throws JDBCException {
		int elementosBorrados = 0;
		try{
			StringBuilder hql = new StringBuilder()
			.append("DELETE FROM campos_relacion_maritima WHERE folio_registro= ").append(folioSerial);
			elementosBorrados = session.createSQLQuery(hql.toString()).executeUpdate();
			
		}catch (JDBCException e){
			transaction.rollback();
			throw e;
		}	
		return elementosBorrados;
	}
		
	
	@SuppressWarnings("unchecked")
	public List<ProgramaRelacionCiclosV> consultaIdPgrCiclo(Integer idCiclo, Integer ejercicio, long idPerRel)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ProgramaRelacionCiclosV> lst=null;
		if (idCiclo != 0 && idCiclo != -1){
			consulta.append("where idCiclo = ").append(idCiclo);
		}
		if (ejercicio != 0 && ejercicio != -1){
			if(consulta.length()>0){
				consulta.append(" and ejercicio =").append(ejercicio);
			}else{
				consulta.append("where ejercicio =").append(ejercicio);
			}
		}
		if (idPerRel != 0 && idPerRel != -1){
			if(consulta.length()>0){
				consulta.append(" and idPerRel =").append(idPerRel);
			}else{
				consulta.append("where idPerRel =").append(idPerRel);
			}
		}
		consulta.insert(0, "From ProgramaRelacionCiclosV ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}

	public int registraFolioCartaCompras(String folioCartaAdhesion,  long idComPer)throws JDBCException {
		int updatedEntities = 0;
		try{
			StringBuilder hql = new StringBuilder()
			.append("update  compras_datos_participante set folio_carta_adhesion = '").append(folioCartaAdhesion).append("', ").append(" id_estatus_compras_datos_par=2")
			.append(" WHERE id_comp_per=").append(idComPer);
			
			updatedEntities  = session.createSQLQuery(hql.toString()).executeUpdate();
		}catch (JDBCException e){
			transaction.rollback();
			throw e;
		}	
		return updatedEntities;
	}
	
	
	
	
	//
	
	//CERTIFICADOS DE DEPOSITO	
	public List<CamposRelacionCertificados> consultaCamposCertificados(Integer idComprador, Integer idCultivo, long idPgrCiclo, String claveBodega,int razonSocialAlmacen)throws  JDBCException{
		return consultaCamposCertificados(-1, idComprador, idCultivo, idPgrCiclo, null, claveBodega, razonSocialAlmacen);
	}
	
	@SuppressWarnings("unchecked")
	public List<CamposRelacionCertificados> consultaCamposCertificados(long idCampoRelacion, Integer idComprador, Integer idPgrCultivo, long idPgrCiclo, String folioCertificado, String claveBodega, int razonSocialAlmacen)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<CamposRelacionCertificados> lst=null;
		
		if (idCampoRelacion != 0 && idCampoRelacion != -1){
			consulta.append("where idCampoRelacion = ").append(idCampoRelacion);
		}
		if (idComprador != 0 && idComprador != -1){
			if(consulta.length()>0){
				consulta.append(" and idComprador =").append(idComprador);
			}else{
				consulta.append("where idComprador =").append(idComprador);
			}
		}
		if (idPgrCultivo != 0 && idPgrCultivo != -1){
			if(consulta.length()>0){
				consulta.append(" and idPgrCultivo =").append(idPgrCultivo);
			}else{
				consulta.append("where idPgrCultivo =").append(idPgrCultivo);
			}
		}
		if (idPgrCiclo != 0 && idPgrCiclo != -1){
			if(consulta.length()>0){
				consulta.append(" and idPgrCiclo =").append(idPgrCiclo);
			}else{
				consulta.append("where idPgrCiclo =").append(idPgrCiclo);
			}
		}
		if (folioCertificado != null && !folioCertificado.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folio ='").append(folioCertificado).append("'");
			}else{
				consulta.append("where folio =").append(folioCertificado).append("'");
			}
		}	
		
		if (claveBodega != null && !claveBodega.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and claveBodega ='").append(claveBodega).append("'");
			}else{
				consulta.append("where claveBodega ='").append(claveBodega).append("'");
			}
		}	
		
		if (razonSocialAlmacen != 0 && razonSocialAlmacen != -1){
			if(consulta.length()>0){
				consulta.append(" and razonSocialAlmacen =").append(razonSocialAlmacen);
			}else{
				consulta.append("where razonSocialAlmacen =").append(razonSocialAlmacen);
			}
		}
		
		consulta.insert(0, "From CamposRelacionCertificados ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	
	public List<GruposCamposRelacionCertificadosV> consultaGruposCamposCertificadosV(String folio, int razonSocialAlmacen)throws  JDBCException{
		return consultaGruposCamposCertificadosV(-1, -1, -1, "", "", -1,  null, folio, false, -1, razonSocialAlmacen);
	}
	
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionCertificadosV> consultaGruposCamposCertificadosV(int idTipoRelacion,  long idPerRel, Integer idComprador, String folioCartaAdhesion, String claveBodega, int cultivoRegistro, String tipoSeccion, String folio, boolean limit,long idPgrCiclo, int razonSocialAlmacen)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<GruposCamposRelacionCertificadosV> lst = null;
		if (idTipoRelacion != 0 && idTipoRelacion != -1){
			consulta.append("where idTipoRelacion=").append(idTipoRelacion);
		}
		
		if (idPerRel != 0 && idPerRel != -1){
			if(consulta.length()>0){
				consulta.append(" and idPerRel=").append(idPerRel);
			}else{
				consulta.append("where idPerRel=").append(idPerRel);
			}
		}
		if (idComprador != 0 && idComprador != -1){
			if(consulta.length()>0){
				consulta.append(" and idComprador=").append(idComprador);
			}else{
				consulta.append("where idComprador=").append(idComprador);
			}
		}
		if (folioCartaAdhesion !=  null && !folioCartaAdhesion.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}else{
				consulta.append("where folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}
		}
		if (claveBodega !=  null && !claveBodega.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and claveBodega ='").append(claveBodega).append("'");
			}else{
				consulta.append("where claveBodega ='").append(claveBodega).append("'");
			}
		}
		if (cultivoRegistro != 0 && cultivoRegistro != -1){
			if(consulta.length()>0){
				consulta.append(" and cultivoRegistro =").append(cultivoRegistro);
			}else{
				consulta.append("where cultivoRegistro =").append(cultivoRegistro);
			}
		}
		if (tipoSeccion !=  null && !tipoSeccion.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and tipoSeccion ='").append(tipoSeccion).append("'");
			}else{
				consulta.append("where tipoSeccion ='").append(tipoSeccion).append("'");
			}
		}		
		if (folio != null && !folio.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folio = '").append(folio).append("'");
			}else{
				consulta.append("where folio = '").append(folio).append("'");;
			}
		}	
		if (idPgrCiclo != 0 && idPgrCiclo != -1){
			if(consulta.length()>0){
				consulta.append(" and idPgrCiclo =").append(idPgrCiclo);
			}else{
				consulta.append("where idPgrCiclo =").append(idPgrCiclo);
			}
		}
		if (razonSocialAlmacen != 0 && razonSocialAlmacen != -1){
			if(consulta.length()>0){
				consulta.append(" and razonSocialAlmacen =").append(razonSocialAlmacen);
			}else{
				consulta.append("where razonSocialAlmacen =").append(razonSocialAlmacen);
			}
		}
		
		consulta.insert(0, "From GruposCamposRelacionCertificadosV ").append(" ORDER BY folioCartaAdhesion, claveBodega, folio, posicionGrupo, posicionCampo");    
		if(limit){
			lst= session.createQuery(consulta.toString()).setFirstResult(0).setMaxResults(1).list();   
		}else{
			lst= session.createQuery(consulta.toString()).list();
		} 
		
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionCertificadosV> consultaRelacionCDByBodegaAlmacen(long idPerRel, int idComprador, String folioCartaAdhesion)throws  JDBCException{
		List<GruposCamposRelacionCertificadosV> lst = null;
		StringBuilder condicion = new StringBuilder();
		if (idPerRel != 0 && idPerRel != -1){
			condicion.append(" where idPerRel = ").append(idPerRel);
		} 
		if (idComprador != 0 && idComprador != -1){
			if(condicion.length()>0){
				condicion.append(" and idComprador =").append(idComprador);
			}else{
				condicion.append(" where idComprador =").append(idComprador);
			}
		}
		
		if (folioCartaAdhesion !=  null && !folioCartaAdhesion.isEmpty()){
			if(condicion.length()>0){
				condicion.append(" and folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}else{
				condicion.append(" where folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}
		}
		 
		String consulta = "select new mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionCertificadosV (idPerRel, idTipoRelacion, idComprador, folioCartaAdhesion, claveBodega, razonSocialAlmacen, almacen, cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio )"+
						" from GruposCamposRelacionCertificadosV"+
						condicion.toString()+
						" group by idPerRel, idTipoRelacion, idComprador, folioCartaAdhesion, claveBodega, razonSocialAlmacen, almacen, cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio"+
						" order by ejercicio, cultivoRegistro, cicloLargo, claveBodega,  almacen ";
		lst = session.createQuery(consulta.toString()).list();
		
		return lst;
	}
	
		
	
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionCertificadosV> consultaRelacionCDByBodegaAlmacenRegistroCA(long idPerRel, int idComprador)throws  JDBCException{
		List<GruposCamposRelacionCertificadosV> lst = null;
		
		String consulta = "select new mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionCertificadosV (idPerRel, idTipoRelacion, idComprador, folioCartaAdhesion, claveBodega, razonSocialAlmacen, almacen, cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio )"+
				" from GruposCamposRelacionCertificadosV"+
				" where idPerRel = "+idPerRel+" and idComprador= "+idComprador+ " and folioCartaAdhesion is null"+
				" group by idPerRel, idTipoRelacion, idComprador, folioCartaAdhesion, claveBodega, razonSocialAlmacen, almacen, cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio"+
				" order by ejercicio, cultivoRegistro, cicloLargo, claveBodega,  almacen ";
		lst = session.createQuery(consulta.toString()).list();
		return lst;
	
	}
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionCertificadosV> consultaRelacionBodegaFolioCertificado(long idPerRel, int idComprador, String claveBodega, int cultivoRegistro, long idPgrCiclo,  Integer razonSocialAlmacen)throws  JDBCException{
		List<GruposCamposRelacionCertificadosV> lst = null;
		String consulta = "select new mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionCertificadosV  (idPerRel,  idComprador, folioCartaAdhesion, claveBodega, razonSocialAlmacen, almacen, cultivoRegistro, cultivo, idPgrCiclo, cicloLargo, ejercicio, folio)"+
						" from   GruposCamposRelacionCertificadosV "+
						" where idPerRel = "+idPerRel+" and idComprador= "+idComprador+" and claveBodega= '"+claveBodega+"'"+" and razonSocialAlmacen = "+razonSocialAlmacen+
						" and cultivo_registro ="+cultivoRegistro+" and id_pgr_ciclo="+idPgrCiclo+
						" group by idPerRel,  idComprador, folioCartaAdhesion, claveBodega, razonSocialAlmacen, almacen, cultivoRegistro, cultivo, idPgrCiclo, cicloLargo, ejercicio, folio"+
						" order by folio";
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
	}
	
	public int registraFolioCartaCertificados(String folioCartaAdhesion, Integer idComprador, long idPerRel, String claveBodega, int cultivoRelacion, long idPgrCiclo, int razonSocialAlmacen)throws JDBCException {
		int updatedEntities = 0;
		try{
			StringBuilder hql = new StringBuilder()
			.append("update  campos_relacion_certificados set folio_carta_adhesion  = '").append(folioCartaAdhesion).append("'")
			.append(" WHERE id_comprador= ").append(idComprador).append(" AND id_per_rel=").append(idPerRel)
			.append(" AND clave_bodega ='").append(claveBodega).append("'").append(" AND id_pgr_cultivo=").append(cultivoRelacion)
			.append(" and id_pgr_ciclo = ").append(idPgrCiclo).append(" and razon_social_almacen=").append(razonSocialAlmacen);
			
			updatedEntities  = session.createSQLQuery(hql.toString()).executeUpdate();
			
		}catch (JDBCException e){
			transaction.rollback();
			throw e;
		}	
		return updatedEntities;
	}
	
	public int borrarFolioCertificados(long idPerRel, Integer idComprador, String claveBodega, int cultivoRegistro, String folioCertificado, long idPgrCiclo, int razonSocialAlmacen)throws JDBCException {
		int elementosBorrados = 0;
		try{
			StringBuilder hql = new StringBuilder()
			.append("DELETE FROM campos_relacion_certificados WHERE id_per_rel= ").append(idPerRel)
			.append(" and id_comprador=").append(idComprador).append(" and id_pgr_cultivo = ").append(cultivoRegistro)
			.append(" and id_pgr_ciclo = ").append(idPgrCiclo).append(" and folio = '"+folioCertificado+"' and razon_social_almacen =").append(razonSocialAlmacen)
			.append(" and claveBodega=").append(claveBodega);
			elementosBorrados = session.createSQLQuery(hql.toString()).executeUpdate();
			
		}catch (JDBCException e){
			transaction.rollback();
			throw e;
		}	
		return elementosBorrados;
	}

	//VENTAS	
	@SuppressWarnings("unchecked")
	public List<CamposRelacionVentas> consultaCamposVentas(long idCampoRelacion, Integer idComprador, Integer idPgrCultivo, long idPgrCiclo, String folioFacVenta)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<CamposRelacionVentas> lst=null;
		
		if (idCampoRelacion != 0 && idCampoRelacion != -1){
			consulta.append("where idCampoRelacion = ").append(idCampoRelacion);
		}
		if (idComprador != 0 && idComprador != -1){
			if(consulta.length()>0){
				consulta.append(" and idComprador =").append(idComprador);
			}else{
				consulta.append("where idComprador =").append(idComprador);
			}
		}
		
		if (idPgrCultivo != 0 && idPgrCultivo != -1){
			if(consulta.length()>0){
				consulta.append(" and idPgrCultivo =").append(idPgrCultivo);
			}else{
				consulta.append("where idPgrCultivo =").append(idPgrCultivo);
			}
		}
		
		if (idPgrCiclo != 0 && idPgrCiclo != -1){
			if(consulta.length()>0){
				consulta.append(" and idPgrCiclo =").append(idPgrCiclo);
			}else{
				consulta.append("where idPgrCiclo =").append(idPgrCiclo);
			}
		}
		
		if (folioFacVenta != null && !folioFacVenta.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folio ='").append(folioFacVenta).append("'");
			}else{
				consulta.append("where folio =").append(folioFacVenta).append("'");
			}
		}	
		consulta.insert(0, "From CamposRelacionVentas ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionVentasV> consultaGruposCamposVentasV(int idTipoRelacion,  long idPerRel, Integer idComprador, String folioCartaAdhesion, Integer idCultivo, String tipoSeccion, String folio, boolean limit, long idPgrCiclo)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<GruposCamposRelacionVentasV> lst = null;
		if (idTipoRelacion != 0 && idTipoRelacion != -1){
			consulta.append("where idTipoRelacion=").append(idTipoRelacion);
		}
		
		if (idPerRel != 0 && idPerRel != -1){
			if(consulta.length()>0){
				consulta.append(" and idPerRel=").append(idPerRel);
			}else{
				consulta.append("where idPerRel=").append(idPerRel);
			}
		}
		if (idComprador != 0 && idComprador != -1){
			if(consulta.length()>0){
				consulta.append(" and idComprador=").append(idComprador);
			}else{
				consulta.append("where idComprador=").append(idComprador);
			}
		}
		if (folioCartaAdhesion !=  null && !folioCartaAdhesion.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}else{
				consulta.append("where folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}
		}
		if (idCultivo != 0 && idCultivo != -1){
			if(consulta.length()>0){
				consulta.append(" and cultivoRegistro =").append(idCultivo);
			}else{
				consulta.append("where cultivoRegistro =").append(idCultivo);
			}
		}
		if (tipoSeccion !=  null && !tipoSeccion.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and tipoSeccion ='").append(tipoSeccion).append("'");
			}else{
				consulta.append("where tipoSeccion ='").append(tipoSeccion).append("'");
			}    
		}		
		if (folio != null && !folio.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folio = '").append(folio).append("'");
			}else{
				consulta.append("where folio = '").append(folio).append("'");;
			}
		}	
		
		if (idPgrCiclo != 0 && idPgrCiclo != -1){
			if(consulta.length()>0){
				consulta.append(" and idPgrCiclo =").append(idPgrCiclo);
			}else{
				consulta.append("where idPgrCiclo =").append(idPgrCiclo);
			}
		}
		consulta.insert(0, "From GruposCamposRelacionVentasV ").append(" ORDER BY folioCartaAdhesion, folio, posicionGrupo, posicionCampo");    
		if(limit){
			lst= session.createQuery(consulta.toString()).setFirstResult(0).setMaxResults(1).list();   
		}else{
			lst= session.createQuery(consulta.toString()).list();
		} 
		return lst;
	}
	
	

	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionVentasV> consultaRelacionVentaByCicloCultivo(long idPerRel, int idComprador, String folioCartaAdhesion)throws JDBCException{
		StringBuilder condicion = new StringBuilder();
		List<GruposCamposRelacionVentasV> lst = null;
		String consulta = "";
		if (idPerRel != 0 && idPerRel != -1){
			condicion.append("where idPerRel = ").append(idPerRel);
		} 
		if (idComprador != 0 && idComprador != -1){
			if(condicion.length()>0){
				condicion.append(" and idComprador =").append(idComprador);
			}else{
				condicion.append("where idComprador =").append(idComprador);
			}
		}
		
		if (folioCartaAdhesion !=  null && !folioCartaAdhesion.isEmpty()){
			if(condicion.length()>0){
				condicion.append(" and folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}else{
				condicion.append("where folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}
		}
		consulta = "select new mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionVentasV(idPerRel, idComprador, folioCartaAdhesion, cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio )"+
				" from GruposCamposRelacionVentasV "+
				 condicion.toString()+
				" group by idPerRel, idComprador, folioCartaAdhesion, cultivoRegistro, cultivo, idPgrCiclo, cicloLargo, ejercicio "+
				" order by ejercicio, cultivoRegistro, cicloLargo";
		lst = session.createQuery(consulta.toString()).list();
		
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionVentasV> consultaVentaByFolioCicloCultivo(long idPerRel, int idComprador,  int cultivoRegistro, long idPgrCiclo)throws  JDBCException{
		List<GruposCamposRelacionVentasV> lst = null;
		String consulta = "select new mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionVentasV(idPerRel, idComprador, folioCartaAdhesion, cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio, folio)"+
				" from GruposCamposRelacionVentasV "+
				" where idPerRel = "+idPerRel+" and idComprador= "+idComprador+
				" and cultivo_registro = "+cultivoRegistro+" and id_pgr_ciclo = "+idPgrCiclo+
				" group by idPerRel, idComprador, folioCartaAdhesion, cultivoRegistro, cultivo, idPgrCiclo, cicloLargo, ejercicio, folio "+
				" order by ejercicio, cultivoRegistro, cicloLargo, folio ";
		lst = session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionVentasV> consultaRelacionVentaByCicloCultivoRegistroCA(long idPerRel, int idComprador)throws  JDBCException{
		List<GruposCamposRelacionVentasV> lst = null;
		
		String consulta = "select new mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionVentasV(idPerRel, idComprador, folioCartaAdhesion, cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio )"+
				" from GruposCamposRelacionVentasV "+
				" where idPerRel = "+idPerRel+" and idComprador= "+idComprador+ " and folioCartaAdhesion is null"+
				" group by idPerRel, idComprador, folioCartaAdhesion, cultivoRegistro, cultivo, idPgrCiclo, cicloLargo, ejercicio "+
				" order by ejercicio, cultivoRegistro, cicloLargo";
		lst = session.createQuery(consulta.toString()).list();
		
		
		return lst;
	
	}
	
	public int borrarFolioVentas(long idPerRel, Integer idComprador,
			Integer cultivoRelacion, String folioFacVenta, long idPgrCiclo) {
		int elementosBorrados = 0;
		try{
			StringBuilder hql = new StringBuilder()
			.append("DELETE FROM campos_relacion_ventas WHERE id_per_rel= ").append(idPerRel)
					.append(" and id_comprador =").append(idComprador).append(" and id_pgr_cultivo = ").append(cultivoRelacion)
					.append(" and id_pgr_ciclo = ").append(idPgrCiclo).append(" and folio = '"+folioFacVenta+"'");
			elementosBorrados = session.createSQLQuery(hql.toString()).executeUpdate();
			
		}catch (JDBCException e){
			transaction.rollback();
			throw e;
		}	
		return elementosBorrados;
	}
	
	
	public int registraFolioCartaVentas(String folioCartaAdhesion, Integer idComprador, long idPerRel, int cultivoRelacion, long idPgrCiclo)throws JDBCException {
		int updatedEntities = 0;
		try{
			StringBuilder hql = new StringBuilder()
			.append("update  campos_relacion_ventas set folio_carta_adhesion  = '").append(folioCartaAdhesion).append("'")
			.append(" WHERE id_comprador= ").append(idComprador).append(" AND id_per_rel=").append(idPerRel)
			.append(" AND id_pgr_cultivo=").append(cultivoRelacion)
			.append(" and id_pgr_ciclo = ").append(idPgrCiclo);
			updatedEntities  = session.createSQLQuery(hql.toString()).executeUpdate();
		}catch (JDBCException e){
			transaction.rollback();
			throw e;
		}	
		return updatedEntities;
	}
	//TERRESTRE	
	@SuppressWarnings("unchecked")
	public List<CamposRelacionTerrestre> consultaCamposTerrestre(long idCampoRelacion, Integer idComprador, Integer idPgrCultivo, long idPgrCiclo, String folioTalonTerrestre, String claveBodegaOpp)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<CamposRelacionTerrestre> lst=null;
		
		if (idCampoRelacion != 0 && idCampoRelacion != -1){
			consulta.append("where idCampoRelacion = ").append(idCampoRelacion);
		}
		if (idComprador != 0 && idComprador != -1){
			if(consulta.length()>0){
				consulta.append(" and idComprador =").append(idComprador);
			}else{
				consulta.append("where idComprador =").append(idComprador);
			}
		}
		if (idPgrCultivo != 0 && idPgrCultivo != -1){
			if(consulta.length()>0){
				consulta.append(" and idPgrCultivo =").append(idPgrCultivo);
			}else{
				consulta.append("where idPgrCultivo =").append(idPgrCultivo);
			}
		}
		if (idPgrCiclo != 0 && idPgrCiclo != -1){
			if(consulta.length()>0){
				consulta.append(" and idPgrCiclo =").append(idPgrCiclo);
			}else{
				consulta.append("where idPgrCiclo =").append(idPgrCiclo);
			}
		}
		
		if (folioTalonTerrestre != null && !folioTalonTerrestre.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folio ='").append(folioTalonTerrestre).append("'");
			}else{
				consulta.append("where folio ='").append(folioTalonTerrestre).append("'");
			}
		}	
		
		if (claveBodegaOpp != null && !claveBodegaOpp.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and claveBodegaOpp ='").append(claveBodegaOpp).append("'");
			}else{
				consulta.append("where claveBodegaOpp ='").append(claveBodegaOpp).append("'");
			}
		}	
		consulta.insert(0, "From CamposRelacionTerrestre ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionTerrestreV> consultaGruposCamposTerrestreV(int idTipoRelacion,  long idPerRel, Integer idComprador, String folioCartaAdhesion, String claveBodegaOpp, Integer idCultivo, String tipoSeccion, String folio, boolean limit, long idPgrCiclo, int estadoBodega)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<GruposCamposRelacionTerrestreV> lst = null;
		if (idTipoRelacion != 0 && idTipoRelacion != -1){
			consulta.append("where idTipoRelacion=").append(idTipoRelacion);
		}
		
		if (idPerRel != 0 && idPerRel != -1){
			if(consulta.length()>0){
				consulta.append(" and idPerRel=").append(idPerRel);
			}else{
				consulta.append("where idPerRel=").append(idPerRel);
			}
		}
		if (idComprador != 0 && idComprador != -1){
			if(consulta.length()>0){
				consulta.append(" and idComprador=").append(idComprador);
			}else{
				consulta.append("where idComprador=").append(idComprador);
			}
		}
		if (folioCartaAdhesion !=  null && !folioCartaAdhesion.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}else{
				consulta.append("where folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}
		}
		if (claveBodegaOpp !=  null && !claveBodegaOpp.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and claveBodegaOpp ='").append(claveBodegaOpp).append("'");
			}else{
				consulta.append("where claveBodegaOpp ='").append(claveBodegaOpp).append("'");
			}
		}
		if (idCultivo != 0 && idCultivo != -1){
			if(consulta.length()>0){
				consulta.append(" and cultivoRegistro =").append(idCultivo);
			}else{
				consulta.append("where cultivoRegistro =").append(idCultivo);
			}
		}
		if (tipoSeccion !=  null && !tipoSeccion.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and tipoSeccion ='").append(tipoSeccion).append("'");
			}else{
				consulta.append("where tipoSeccion ='").append(tipoSeccion).append("'");
			}
		}		
		if(folio != null && !folio.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folio = '").append(folio).append("'");
			}else{
				consulta.append("where folio = '").append(folio).append("'");;
			}
		}	
		
		if (idPgrCiclo != 0 && idPgrCiclo != -1){
			if(consulta.length()>0){
				consulta.append(" and idPgrCiclo =").append(idPgrCiclo);
			}else{
				consulta.append("where idPgrCiclo =").append(idPgrCiclo);
			}
		}
		
		if (estadoBodega != 0 && estadoBodega != -1){
			if(consulta.length()>0){
				consulta.append(" and estadoBodega =").append(estadoBodega);
			}else{
				consulta.append("where estadoBodega =").append(estadoBodega);
			}
		}
		consulta.insert(0, "From GruposCamposRelacionTerrestreV ").append(" ORDER BY folioCartaAdhesion, claveBodegaOpp, folio, posicionGrupo, posicionCampo");    
		if(limit){
			lst= session.createQuery(consulta.toString()).setFirstResult(0).setMaxResults(1).list();   
		}else{
			lst= session.createQuery(consulta.toString()).list();
		} 
		return lst;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionTerrestreV> consultaRelacionTerrestreByBodegaOPP(long idPerRel, int idComprador, String folioCartaAdhesion)throws  JDBCException{
		List<GruposCamposRelacionTerrestreV> lst = null;
		StringBuilder condicion = new StringBuilder();
		if (idPerRel != 0 && idPerRel != -1){
			condicion.append(" where idPerRel = ").append(idPerRel);
		} 
		if (idComprador != 0 && idComprador != -1){
			if(condicion.length()>0){
				condicion.append(" and idComprador =").append(idComprador);
			}else{
				condicion.append(" where idComprador =").append(idComprador);
			}
		}
		
		if (folioCartaAdhesion !=  null && !folioCartaAdhesion.isEmpty()){
			if(condicion.length()>0){
				condicion.append(" and folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}else{
				condicion.append(" where folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}
		}		 
		String consulta = "select new mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionTerrestreV (idPerRel, idTipoRelacion, idComprador, folioCartaAdhesion, claveBodegaOpp, estadoBodega, estado, cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio )"+
						" from GruposCamposRelacionTerrestreV"+
						condicion.toString()+
						" group by idPerRel, idTipoRelacion, idComprador, folioCartaAdhesion, claveBodegaOpp, estadoBodega, estado, cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio"+
						" order by ejercicio, cultivoRegistro, cicloLargo, claveBodegaOpp, estado";
		lst = session.createQuery(consulta.toString()).list();
		
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionTerrestreV> consultaRelacionTerrestreByBodegaOPPRegistroCA(long idPerRel, int idComprador)throws  JDBCException{
		List<GruposCamposRelacionTerrestreV> lst = null;
		
		String consulta = "select new mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionTerrestreV (idPerRel, idTipoRelacion, idComprador, folioCartaAdhesion, claveBodegaOpp, estadoBodega, estado, cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio )"+
				" from GruposCamposRelacionTerrestreV"+
				" where idPerRel = "+idPerRel+" and idComprador= "+idComprador+ " and folioCartaAdhesion is null"+
				" group by idPerRel, idTipoRelacion, idComprador, folioCartaAdhesion, claveBodegaOpp, estadoBodega, estado, cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio"+
				" order by ejercicio, cultivoRegistro, cicloLargo, claveBodegaOpp ";
		lst = session.createQuery(consulta.toString()).list();
		return lst;
	}

	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionTerrestreV> consultaTerrestreByFolioBodegaOPlanP(long idPerRel, int idComprador,  int cultivoRegistro, long idPgrCiclo, String claveBodegaOpp)throws  JDBCException{
		List<GruposCamposRelacionTerrestreV> lst = null;
		String consulta = "select new mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionTerrestreV(idPerRel, idComprador, folioCartaAdhesion, claveBodegaOpp, cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio, folio)"+
				" from GruposCamposRelacionTerrestreV "+
				" where idPerRel = "+idPerRel+" and idComprador= "+idComprador+
				" and cultivo_registro = "+cultivoRegistro+" and id_pgr_ciclo = "+idPgrCiclo+" and claveBodegaOpp='"+claveBodegaOpp+"'"+
				" group by idPerRel, idComprador, folioCartaAdhesion, claveBodegaOpp, cultivoRegistro, cultivo, idPgrCiclo, cicloLargo, ejercicio, folio "+
				" order by  folio ";
		lst = session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	public int registraFolioCartaTerrestre (String folioCartaAdhesion, Integer idComprador, long idPerRel, Integer idCultivo,long idPgrCiclo, String claveBodegaOpp)throws JDBCException {
		int updatedEntities = 0;
		try{
			StringBuilder hql = new StringBuilder()
			.append("update campos_relacion_terrestre set folio_carta_adhesion  = '").append(folioCartaAdhesion).append("'")
			.append(" WHERE id_comprador = ").append(idComprador).append(" AND id_per_rel=").append(idPerRel)
			.append(" AND id_pgr_cultivo = ").append(idCultivo).append("AND id_pgr_ciclo= ").append(idPgrCiclo)
			.append(" AND clave_bodega_o_pp = '").append(claveBodegaOpp).append("'");
			updatedEntities  = session.createSQLQuery(hql.toString()).executeUpdate();
			
		}catch (JDBCException e){
			transaction.rollback();
			throw e;
		}	
		return updatedEntities;
	}
	
	public int borrarFolioTerrestre(long idPerRel, Integer idComprador, String claveBodegaOpp, int cultivoRegistro, String folioTalon, long idPgrCiclo)throws JDBCException {
		int elementosBorrados = 0;
		try{
			StringBuilder hql = new StringBuilder()
			.append("DELETE FROM campos_relacion_terrestre WHERE id_per_rel= ").append(idPerRel)
			.append(" and id_comprador=").append(idComprador).append(" and id_pgr_cultivo = ").append(cultivoRegistro)
			.append(" and id_pgr_ciclo = ").append(idPgrCiclo).append(" and folio = '"+folioTalon+"' ")
			.append(" and clave_bodega_o_pp='").append(claveBodegaOpp).append("'");
			elementosBorrados = session.createSQLQuery(hql.toString()).executeUpdate();
			
		}catch (JDBCException e){
			transaction.rollback();
			throw e;
		}	
		return elementosBorrados;
	}
	
		
	//RELACION MARITIMA
	@SuppressWarnings("unchecked")
	public List<CamposRelacionMaritima> consultaCamposMaritima(
			long idCampoRelacion, Integer idComprador, Integer idPgrCultivo,
			long idPgrCiclo, String folioTalonMaritimo, String claveBodegaOpp, String nombreBarco, String lugarDestino)
			throws JDBCException {
		StringBuilder consulta= new StringBuilder();
		List<CamposRelacionMaritima> lst=null;
		
		if (idCampoRelacion != 0 && idCampoRelacion != -1){
			consulta.append("where idCampoRelacion = ").append(idCampoRelacion);
		}
		if (idComprador != 0 && idComprador != -1){
			if(consulta.length()>0){
				consulta.append(" and idComprador =").append(idComprador);
			}else{
				consulta.append("where idComprador =").append(idComprador);
			}
		}
		if (idPgrCultivo != 0 && idPgrCultivo != -1){
			if(consulta.length()>0){
				consulta.append(" and idPgrCultivo =").append(idPgrCultivo);
			}else{
				consulta.append("where idPgrCultivo =").append(idPgrCultivo);
			}
		}
		if (idPgrCiclo != 0 && idPgrCiclo != -1){
			if(consulta.length()>0){
				consulta.append(" and idPgrCiclo =").append(idPgrCiclo);
			}else{
				consulta.append("where idPgrCiclo =").append(idPgrCiclo);
			}
		}
		if (folioTalonMaritimo != null && !folioTalonMaritimo.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folio ='").append(folioTalonMaritimo).append("'");
			}else{
				consulta.append("where folio ='").append(folioTalonMaritimo).append("'");
			}
		}	
		
		if(claveBodegaOpp != null && !claveBodegaOpp.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and claveBodegaOpp ='").append(claveBodegaOpp).append("'");
			}else{
				consulta.append("where claveBodegaOpp ='").append(claveBodegaOpp).append("'");
			}
		}		
		if(nombreBarco != null && !nombreBarco.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and nombreBarco ='").append(nombreBarco).append("'");
			}else{
				consulta.append("where nombreBarco ='").append(nombreBarco).append("'");
			}
		}		
		if(lugarDestino != null && !lugarDestino.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and lugarDestino ='").append(lugarDestino).append("'");
			}else{
				consulta.append("where lugarDestino ='").append(lugarDestino).append("'");
			}
		}
		consulta.insert(0, "From CamposRelacionMaritima ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
		
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionMaritimaV> consultaGruposCamposMaritimaV(
			int idTipoRelacion, long idPerRel, Integer idComprador,
			String folioCartaAdhesion, String claveBodegaOpp,
			Integer cultivoRegistro, String tipoSeccion,
			String folioTalonMaritimo, boolean limit, long idPgrCiclo,
			String nombreBarco, String lugarDestino) throws JDBCException {
		StringBuilder consulta= new StringBuilder();
		List<GruposCamposRelacionMaritimaV> lst = null;
		if (idTipoRelacion != 0 && idTipoRelacion != -1){
			consulta.append("where idTipoRelacion=").append(idTipoRelacion);
		}
		
		if (idPerRel != 0 && idPerRel != -1){
			if(consulta.length()>0){
				consulta.append(" and idPerRel=").append(idPerRel);
			}else{
				consulta.append("where idPerRel=").append(idPerRel);
			}
		}
		if (idComprador != 0 && idComprador != -1){
			if(consulta.length()>0){
				consulta.append(" and idComprador=").append(idComprador);
			}else{
				consulta.append("where idComprador=").append(idComprador);
			}
		}
		if (folioCartaAdhesion !=  null && !folioCartaAdhesion.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}else{
				consulta.append("where folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}
		}
		if (claveBodegaOpp !=  null && !claveBodegaOpp.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and claveBodegaOpp ='").append(claveBodegaOpp).append("'");
			}else{
				consulta.append("where claveBodegaOpp ='").append(claveBodegaOpp).append("'");
			}
		}
		if (cultivoRegistro != 0 && cultivoRegistro != -1){
			if(consulta.length()>0){
				consulta.append(" and cultivoRegistro =").append(cultivoRegistro);
			}else{
				consulta.append("where cultivoRegistro =").append(cultivoRegistro);
			}
		}
		if (tipoSeccion !=  null && !tipoSeccion.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and tipoSeccion ='").append(tipoSeccion).append("'");
			}else{
				consulta.append("where tipoSeccion ='").append(tipoSeccion).append("'");
			}
		}		
		if (folioTalonMaritimo != null && !folioTalonMaritimo.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folio = '").append(folioTalonMaritimo).append("'");
			}else{
				consulta.append("where folio = '").append(folioTalonMaritimo).append("'");;
			}
		}	
		
		if (idPgrCiclo != 0 && idPgrCiclo != -1){
			if(consulta.length()>0){
				consulta.append(" and idPgrCiclo =").append(idPgrCiclo);
			}else{
				consulta.append("where idPgrCiclo =").append(idPgrCiclo);
			}
		}
		if(nombreBarco != null && !nombreBarco.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and nombreBarco ='").append(nombreBarco).append("'");
			}else{
				consulta.append("where nombreBarco ='").append(nombreBarco).append("'");
			}
		}		
		if(lugarDestino != null && !lugarDestino.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and lugarDestino ='").append(lugarDestino).append("'");
			}else{
				consulta.append("where lugarDestino ='").append(lugarDestino).append("'");
			}
		}
		consulta.insert(0, "From GruposCamposRelacionMaritimaV ").append(" ORDER BY folioCartaAdhesion, claveBodegaOpp, folio, posicionGrupo, posicionCampo");    
		if(limit){
			lst= session.createQuery(consulta.toString()).setFirstResult(0).setMaxResults(1).list();   
		}else{
			lst= session.createQuery(consulta.toString()).list();
		} 		
		return lst;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionMaritimaV> consultaRelacionMaritimaByBodegaOPP(long idPerRel, int idComprador, String folioCartaAdhesion)throws  JDBCException{
		List<GruposCamposRelacionMaritimaV> lst = null;
		StringBuilder condicion = new StringBuilder();
		if (idPerRel != 0 && idPerRel != -1){
			condicion.append(" where idPerRel = ").append(idPerRel);
		} 
		if (idComprador != 0 && idComprador != -1){
			if(condicion.length()>0){
				condicion.append(" and idComprador =").append(idComprador);
			}else{
				condicion.append(" where idComprador =").append(idComprador);
			}
		}
		
		if (folioCartaAdhesion !=  null && !folioCartaAdhesion.isEmpty()){
			if(condicion.length()>0){
				condicion.append(" and folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}else{
				condicion.append(" where folioCartaAdhesion ='").append(folioCartaAdhesion).append("'");
			}
		}
		 
		String consulta = "select new mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionMaritimaV (idPerRel, idTipoRelacion, idComprador, folioCartaAdhesion, claveBodegaOpp,  cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio, nombreBarco, lugarDestino )"+
						" from GruposCamposRelacionMaritimaV"+
						  condicion.toString()+
						" group by idPerRel, idTipoRelacion, idComprador, folioCartaAdhesion, claveBodegaOpp, cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio, nombreBarco, lugarDestino"+
						" order by ejercicio, cultivoRegistro, cicloLargo, claveBodegaOpp, nombreBarco, lugarDestino";
		lst = session.createQuery(consulta.toString()).list();
		
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionMaritimaV> consultaRelacionMaritimaByBodegaOPPRegistroCA(long idPerRel, int idComprador)throws  JDBCException{
		List<GruposCamposRelacionMaritimaV> lst = null;		
		String consulta = "select new mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionMaritimaV (idPerRel, idTipoRelacion, idComprador, folioCartaAdhesion, claveBodegaOpp, cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio, nombreBarco, lugarDestino)"+
				" from GruposCamposRelacionMaritimaV"+
				" where idPerRel = "+idPerRel+" and idComprador= "+idComprador+ " and folioCartaAdhesion is null"+
				" group by idPerRel, idTipoRelacion, idComprador, folioCartaAdhesion, claveBodegaOpp,  cultivoRegistro, cultivo,  idPgrCiclo, cicloLargo, ejercicio, nombreBarco, lugarDestino"+
				" order by ejercicio, cultivoRegistro, cicloLargo, claveBodegaOpp ";
		lst = session.createQuery(consulta.toString()).list();
		return lst;
	}

	@SuppressWarnings("unchecked")
	public List<GruposCamposRelacionMaritimaV> consultaMaritimaByFolioBodegaOPlanP(long idPerRel, int idComprador,  int cultivoRegistro, long idPgrCiclo, String claveBodegaOpp, String nombreBarco, String lugarDestino)throws  JDBCException{
		List<GruposCamposRelacionMaritimaV> lst = null;
		String consulta = "select new mx.gob.comer.sipc.vistas.domain.GruposCamposRelacionMaritimaV(idPerRel, idComprador, folioCartaAdhesion, claveBodegaOpp, cultivoRegistro, cultivo, idPgrCiclo, cicloLargo, ejercicio, folio, nombreBarco, lugarDestino)"+
				" from GruposCamposRelacionMaritimaV "+
				" where idPerRel = "+idPerRel+" and idComprador= "+idComprador+
				" and cultivoRegistro = "+cultivoRegistro+" and idPgrCiclo = "+idPgrCiclo+
				" and nombreBarco='"+nombreBarco+"' and lugarDestino='"+lugarDestino+"' and claveBodegaOpp='"+claveBodegaOpp+"'"+
				" group by idPerRel, idComprador, folioCartaAdhesion, claveBodegaOpp, cultivoRegistro, cultivo, idPgrCiclo, cicloLargo, ejercicio, folio, nombreBarco, lugarDestino "+
				" order by  folio ";
		lst = session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	public int registraFolioCartaMaritima(String folioCartaAdhesion,
			Integer idComprador, long idPerRel, Integer idCultivo,
			long idPgrCiclo, String claveBodegaOpp, String nombreBarco,
			String lugarDestino) throws JDBCException {
		int updatedEntities = 0;
		try{
			StringBuilder hql = new StringBuilder()
			.append("update  campos_relacion_maritima set folio_carta_adhesion  = '").append(folioCartaAdhesion).append("'")
			.append(" WHERE id_comprador= ").append(idComprador).append(" AND id_per_rel=").append(idPerRel)
			.append(" AND id_pgr_cultivo = ").append(idCultivo).append("AND id_pgr_ciclo= ").append(idPgrCiclo)
			.append(" AND clave_bodega_o_pp = '").append(claveBodegaOpp).append("'")
			.append(" AND nombre_barco='").append(nombreBarco).append("'")
			.append(" AND lugar_destino='").append(lugarDestino).append("'");
			updatedEntities  = session.createSQLQuery(hql.toString()).executeUpdate();
			
		}catch (JDBCException e){
			transaction.rollback();
			throw e;
		}	
		return updatedEntities;
	}
	
	public int borrarFolioMaritima(long idPerRel, Integer idComprador,
			String claveBodegaOpp, int cultivoRegistro, String folioTalon,
			long idPgrCiclo, String nombreBarco, String lugarDestino)
			throws JDBCException {
	int elementosBorrados = 0;
		try{
			StringBuilder hql = new StringBuilder()
			.append("DELETE FROM campos_relacion_maritima WHERE id_per_rel= ").append(idPerRel)
			.append(" and id_comprador=").append(idComprador).append(" and id_pgr_cultivo = ").append(cultivoRegistro)
			.append(" and id_pgr_ciclo = ").append(idPgrCiclo).append(" and folio = '"+folioTalon+"' ")
			.append(" and clave_bodega_o_pp='").append(claveBodegaOpp).append("'")
			.append(" AND nombre_barco='").append(nombreBarco).append("'")
			.append(" AND lugar_destino='").append(lugarDestino).append("'");
			elementosBorrados = session.createSQLQuery(hql.toString()).executeUpdate();			
		}catch (JDBCException e){
			transaction.rollback();
			throw e;
		}	
		return elementosBorrados;
	}
	
	//RELACION DE COMPRAS
	public List<ComprasDatosParticipante> consultaComprasDatosParticipantes(Integer idComprador, String claveBodega, long idPerRel)throws  JDBCException{
		return consultaComprasDatosParticipantes(idComprador,claveBodega, idPerRel, 0, 0, 0,0);
	}
	
	public List<ComprasDatosParticipante> consultaComprasDatosParticipantes(long idCompPer)throws  JDBCException{
		return consultaComprasDatosParticipantes(-1,null, -1, idCompPer,0,0,0);
	}
	
	@SuppressWarnings("unchecked")
	public List<ComprasDatosParticipante> consultaComprasDatosParticipantes(Integer idComprador, String claveBodega, long idPerRel, long idCompPer, int idCultivo, long idPgrCiclo, int estadoAcopio)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasDatosParticipante> lst = null;
		if (idComprador != 0 && idComprador != -1){
			consulta.append(" where idComprador =").append(idComprador);
		}
		if (claveBodega !=  null && !claveBodega.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and claveBodega = '").append(claveBodega).append("'");
			}else{
				consulta.append(" where claveBodega = '").append(claveBodega).append("'");
			}
		}
		if (idPerRel != 0 && idPerRel != -1){
			if(consulta.length()>0){
				consulta.append(" and idPerRel=").append(idPerRel);
			}else{
				consulta.append(" where idPerRel=").append(idPerRel);
			}
		}
		
		if (idCompPer != 0 && idCompPer != -1){
			if(consulta.length()>0){
				consulta.append(" and idCompPer=").append(idCompPer);
			}else{
				consulta.append(" where idCompPer=").append(idCompPer);
			}
		}
		
		if (idCultivo != 0 && idCultivo != -1){
			if(consulta.length()>0){
				consulta.append(" and idCultivo=").append(idCultivo);
			}else{
				consulta.append(" where idCultivo=").append(idCultivo);
			}
		}
		
		if (idPgrCiclo != 0 && idPgrCiclo != -1){
			if(consulta.length()>0){
				consulta.append(" and idPgrCiclo=").append(idPgrCiclo);
			}else{
				consulta.append(" where idPgrCiclo=").append(idPgrCiclo);
			}
		}
		
		if(estadoAcopio != 0 && estadoAcopio != -1){
			if(consulta.length()>0){
				consulta.append(" and estadoAcopio=").append(estadoAcopio);
			}else{
				consulta.append(" where estadoAcopio=").append(estadoAcopio);
			}
		}
		
		consulta.insert(0, "From ComprasDatosParticipante ").append(" order by idComprador, claveBodega");
		System.out.println(consulta);
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<ComprasDatosProductor> consultaComprasDatosProductor(long idCompProd, long idCompPer, long folioProductor)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasDatosProductor> lst=null;
		if (idCompProd != 0 && idCompProd != -1){
			consulta.append(" where idCompProd=").append(idCompProd);
		}
		
		if ( idCompPer != 0 && idCompPer!=-1){
			if(consulta.length()>0){
				consulta.append(" and idCompPer=").append(idCompPer);
			}else{
				consulta.append(" where idCompPer=").append(idCompPer);
			}
		}
		if ( folioProductor != 0 && folioProductor != -1){
			if(consulta.length()>0){
				consulta.append(" and folioProductor=").append(folioProductor);
			}else{
				consulta.append(" where folioProductor=").append(folioProductor);
			}
		}		
		consulta.insert(0, "From ComprasDatosProductor ").append(" order by folioProductor");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ComprasDatosProductorV> consultaComprasDatosProductorV(long idCompPer, long folioProductor)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasDatosProductorV> lst=null;
		if (idCompPer != 0 && idCompPer != -1){
			consulta.append(" where idCompPer=").append(idCompPer);
		}
		if ( folioProductor != 0){
			if(consulta.length()>0){
				consulta.append(" and folioProductor=").append(folioProductor);
			}else{
				consulta.append(" where folioProductor=").append(folioProductor);
			}
		}		
		
		consulta.insert(0, "From ComprasDatosProductorV ").append(" order by folioProductor");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	
	public List<ComprasPredioV> consultaComprasPredioV(long idCompProd)throws  JDBCException{
		return consultaComprasPredioV(idCompProd, null, -1, -1, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<ComprasPredioV> consultaComprasPredioV(long idCompProd, String folioPredio, int folioPredioSec, long idPerRel, String predioAlterno)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasPredioV> lst=null;
		if (idCompProd != 0 && idCompProd != -1){
			consulta.append("where idCompProd = ").append(idCompProd);
		}
		
		if (folioPredio != null && ! folioPredio.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folioPredio='").append(folioPredio).append("'");
			}else{
				consulta.append(" where folioPredio='").append(folioPredio).append("'");
			}
		}
		
		if (folioPredioSec != -1 && folioPredioSec !=0){
			if(consulta.length()>0){
				consulta.append(" and folioPredioSec=").append(folioPredioSec);
			}else{
				consulta.append(" where folioPredioSec=").append(folioPredio);
			}
		}
		
		if (idPerRel != 0 && idPerRel != -1){
			if(consulta.length()>0){
				consulta.append(" and idPerRel=").append(idPerRel);
			}else{
				consulta.append(" where idPerRel=").append(idPerRel);
			}
		}
		
		if (predioAlterno != null && ! predioAlterno.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and predioAlterno='").append(predioAlterno).append("'");
			}else{
				consulta.append(" where predioAlterno='").append(predioAlterno).append("'");
			}
		}
		
		consulta.insert(0, "From ComprasPredioV ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	
	public List<ComprasPredio> consultaComprasPredio(long idCompProd)throws  JDBCException{
		return consultaComprasPredio(0, idCompProd, null, 0, null);
	}
	@SuppressWarnings("unchecked")
	public List<ComprasPredio> consultaComprasPredio(long idPredio, long idCompProd, String predio, int predioSecuencial, String predioAlterno)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasPredio> lst=null;
		if (idPredio != 0 && idPredio != -1){
			consulta.append("where idPredio = ").append(idPredio);
		}
		if (idCompProd != 0 && idCompProd != -1){
			if(consulta.length()>0){
				consulta.append(" and idCompProd=").append(idCompProd);
			}else{
				consulta.append(" where idCompProd=").append(idCompProd);
			}
		}
		
		
		if (predio != null && ! predio.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folioPredio='").append(predio).append("'");
			}else{
				consulta.append(" where folioPredio='").append(predio).append("'");
			}
		}
		
		if (predioSecuencial != -1 && predioSecuencial !=0){
			if(consulta.length()>0){
				consulta.append(" and folioPredioSec=").append(predioSecuencial);
			}else{
				consulta.append(" where folioPredioSec=").append(predioSecuencial);
			}
		}
		
		if (predioAlterno != null && ! predioAlterno.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and predioAlterno='").append(predioAlterno).append("'");
			}else{
				consulta.append(" where predioAlterno='").append(predioAlterno).append("'");
			}
		}
	
		consulta.insert(0, "From ComprasPredio ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<ComprasEntradaBodega> consultaComprasEntradaBodega(long idCompProd)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasEntradaBodega> lst=null;
		if (idCompProd != 0 && idCompProd != -1){
			consulta.append("where idCompProd = ").append(idCompProd);
		}
		consulta.insert(0, "From ComprasEntradaBodega ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<ComprasFacVentaGlobal> consultaComprasFacVentaGlobal(long idCompProd)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasFacVentaGlobal> lst=null;
		if (idCompProd != 0 && idCompProd != -1){
			consulta.append("where idCompProd = ").append(idCompProd);
		}
		consulta.insert(0, "From ComprasFacVentaGlobal ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	
	public List<ComprasFacVenta> consultaComprasFacVenta(long idCompProd)throws  JDBCException{
		return consultaComprasFacVenta(idCompProd, null);
	}
	@SuppressWarnings("unchecked")
	public List<ComprasFacVenta> consultaComprasFacVenta(long idCompProd, String folioFac)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasFacVenta> lst=null;
		if (idCompProd != 0 && idCompProd != -1){
			consulta.append("where idCompProd = ").append(idCompProd);
		}
		if (folioFac != null && !folioFac.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folioFac='").append(folioFac).append("'");
			}else{
				consulta.append(" where folioFac=").append(folioFac).append("'");
			}
		}
		
		consulta.insert(0, "From ComprasFacVenta ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<ComprasContrato> consultaComprasContratos(long idCompProd)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasContrato> lst=null;
		if (idCompProd != 0 && idCompProd != -1){
			consulta.append("where idCompProd = ").append(idCompProd);
		}
		consulta.insert(0, "From ComprasContrato ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<ComprasPagoProdAxc> consultaComprasPagoProdAxc(long idCompProd)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasPagoProdAxc> lst=null;
		if (idCompProd != 0 && idCompProd != -1){
			consulta.append("where idCompProd = ").append(idCompProd);
		}
		consulta.insert(0, "From ComprasPagoProdAxc ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<ComprasPagoProdSinAxc> consultaComprasPagoProdSinAxc(long idCompProd)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasPagoProdSinAxc> lst=null;
		if (idCompProd != 0 && idCompProd != -1){
			consulta.append("where idCompProd = ").append(idCompProd);
		}
		consulta.insert(0, "From ComprasPagoProdSinAxc ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}

	/**Inicializacion de Programa **/ 	
	@SuppressWarnings("unchecked")
	public List<ProgramaRelacionCiclos > consultaProgramaRelacionCiclos(long idIniEsquemaRelacion)throws  JDBCException{
		List<ProgramaRelacionCiclos> lst = null;
		StringBuilder consulta= new StringBuilder();
		
		if (idIniEsquemaRelacion != 0 && idIniEsquemaRelacion != -1){
			consulta.append("where idIniEsquemaRelacion = ").append(idIniEsquemaRelacion);
		}
		
		consulta.insert(0, "From ProgramaRelacionCiclos ");
		lst= session.createQuery(consulta.toString()).list();		
		
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProgramaRelacionCultivos > consultaProgramaRelacionCultivos(long idIniEsquemaRelacion)throws  JDBCException{
		List<ProgramaRelacionCultivos> lst = null;
		StringBuilder consulta= new StringBuilder();
		
		if (idIniEsquemaRelacion != 0 && idIniEsquemaRelacion != -1){
			consulta.append("where idIniEsquemaRelacion = ").append(idIniEsquemaRelacion);
		}
		
		consulta.insert(0, "From ProgramaRelacionCultivos ");
		lst= session.createQuery(consulta.toString()).list();		
		
		return lst;
	}
	
	public List<IniEsquemaRelacionV> consultaIniEsquemaRelacionV(long idIniEsquemaRelacion)throws  JDBCException{
		return consultaIniEsquemaRelacionV(idIniEsquemaRelacion, null,null, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<IniEsquemaRelacionV> consultaIniEsquemaRelacionV(long idIniEsquemaRelacion, String nombreEsquema, String fechaInicio, String fechaFin)throws  JDBCException{
		List<IniEsquemaRelacionV> lst = null;
		StringBuilder consulta= new StringBuilder();
		
		if (idIniEsquemaRelacion != 0 && idIniEsquemaRelacion != -1){
			consulta.append("where idIniEsquemaRelacion = ").append(idIniEsquemaRelacion);
		}
		
		
		if (nombreEsquema != null && !nombreEsquema.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and nombreEsquema =").append(nombreEsquema);
			}else{
				consulta.append("where nombreEsquema =").append(nombreEsquema);
			}
		}
		if((fechaInicio != null && !fechaInicio.equals(""))&& (fechaFin !=null && !fechaFin.equals(""))){
			if(consulta.length()>0){
				consulta.append(" and  (TO_CHAR(fecha_creacion,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}else{
				consulta.append(" where (TO_CHAR(fecha_creacion,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}
		}else{
			if(fechaInicio != null && !fechaInicio.equals("")){
				if(consulta.length()>0){
					consulta.append(" and (TO_CHAR(fecha_creacion,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}else{
					consulta.append("where (TO_CHAR(fecha_creacion,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}
			}
		}
		consulta.insert(0, "From IniEsquemaRelacionV ");
		lst= session.createQuery(consulta.toString()).list();		
		
		return lst;
	}
	
	public List<IniEsquemaRelacion> consultaIniEsquemaRelacion(long idIniEsquemaRelacion)throws  JDBCException{
		return consultaIniEsquemaRelacion(idIniEsquemaRelacion, null,null, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<IniEsquemaRelacion> consultaIniEsquemaRelacion(long idIniEsquemaRelacion, String nombreEsquema, String fechaInicio, String fechaFin)throws  JDBCException{
		List<IniEsquemaRelacion> lst = null;
		StringBuilder consulta= new StringBuilder();
		
		if (idIniEsquemaRelacion != 0 && idIniEsquemaRelacion != -1){
			consulta.append("where idIniEsquemaRelacion = ").append(idIniEsquemaRelacion);
		}
		
		
		if (nombreEsquema != null && !nombreEsquema.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and nombreEsquema =").append(nombreEsquema);
			}else{
				consulta.append("where nombreEsquema =").append(nombreEsquema);
			}
		}
		if((fechaInicio != null && !fechaInicio.equals(""))&& (fechaFin !=null && !fechaFin.equals(""))){
			if(consulta.length()>0){
				consulta.append(" and  (TO_CHAR(fecha_creacion,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}else{
				consulta.append(" where (TO_CHAR(fecha_creacion,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}
		}else{
			if(fechaInicio != null && !fechaInicio.equals("")){
				if(consulta.length()>0){
					consulta.append(" and (TO_CHAR(fecha_creacion,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}else{
					consulta.append("where (TO_CHAR(fecha_creacion,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}
			}
		}
		consulta.insert(0, "From IniEsquemaRelacion ");
		lst= session.createQuery(consulta.toString()).list();		
		
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<PersonalizacionRelaciones> consultaPersonalizacionRelacion(long idPerRel, int idRelacion, long idIniEsquemaRelacion)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<PersonalizacionRelaciones> lst = null;
		if (idPerRel != 0 && idPerRel != -1){
			consulta.append("where idPerRel = ").append(idPerRel);
		}

		if (idRelacion != 0 && idRelacion != -1){
			if(consulta.length()>0){
				consulta.append(" and idRelacion=").append(idRelacion);
			}else{
				consulta.append("where idRelacion=").append(idRelacion);
			}
		}
		if (idIniEsquemaRelacion != 0 && idIniEsquemaRelacion != -1){
			if(consulta.length()>0){
				consulta.append(" and idIniEsquemaRelacion=").append(idIniEsquemaRelacion);
			}else{
				consulta.append("where idIniEsquemaRelacion=").append(idIniEsquemaRelacion);
			}
		}
		
		
		consulta.insert(0, "From PersonalizacionRelaciones ");
		lst= session.createQuery(consulta.toString()).list();
				
		return lst;
	}
	
	public int borrarCiclosEsquemaRel(long idIniEsquemaRelacion)throws JDBCException {
		int elementosBorrados = 0;
		try{
			StringBuilder hql = new StringBuilder()
			.append("DELETE FROM programa_relacion_ciclos WHERE id_ini_esquema_relacion= ").append(idIniEsquemaRelacion);
			elementosBorrados = session.createSQLQuery(hql.toString()).executeUpdate();
			
		}catch (JDBCException e){
			transaction.rollback();
			throw e;
		}	
		return elementosBorrados;
	}
	
	public int borrarCultivosEsquemaRel(long idIniEsquemaRelacion)throws JDBCException {
		int elementosBorrados = 0;
		try{
			StringBuilder hql = new StringBuilder()
			.append("DELETE FROM programa_relacion_cultivos WHERE id_ini_esquema_relacion= ").append(idIniEsquemaRelacion);
			elementosBorrados = session.createSQLQuery(hql.toString()).executeUpdate();
			
		}catch (JDBCException e){
			transaction.rollback();
			throw e;
		}	
		return elementosBorrados;
	}
	
	@SuppressWarnings("unchecked")
	public List<ComprasMaxCamposV> consultaComprasMaxCamposV(long idCompPer, long idCompProd)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasMaxCamposV> lst = null;
		if (idCompPer != 0 && idCompPer != -1){
			consulta.append("where idCompPer = ").append(idCompPer);
		}
		
		if (idCompProd != 0 && idCompProd != -1){
			if(consulta.length()>0){
				consulta.append(" and idCompProd= ").append(idCompProd);
			}else{
				consulta.append("where idCompProd = ").append(idCompProd);
			}
		}		
		
		consulta.insert(0, "From ComprasMaxCamposV ");
		lst= session.createQuery(consulta.toString()).list();
				
		return lst;
	}
	
	public int borrarRegistros (long idCompProd, int apartado)throws JDBCException {
		int elementosBorrados = 0;
		try{
			StringBuilder consulta = new StringBuilder();
			if(apartado == 6){
				consulta.append("DELETE FROM compras_predio WHERE id_comp_prod= ").append(idCompProd).append("; ");//predios del productor
			}
			
			if(apartado == 7){
				consulta.append("DELETE FROM compras_entrada_bodega WHERE id_comp_prod=").append(idCompProd).append("; "); //Entradas a la Bodega
			}
			
			if(apartado == 9){
				consulta.append("DELETE FROM compras_fac_venta WHERE id_comp_prod=").append(idCompProd).append("; ");//Factura de Venta del Grano
			}
			
			if(apartado == 12){
				consulta.append("DELETE FROM compras_pago_prod_sin_axc WHERE id_comp_prod=").append(idCompProd).append("; ");//Pago al Productor sin AXC
			}			
			elementosBorrados = session.createSQLQuery(consulta.toString()).executeUpdate();
		}catch (JDBCException e){
			transaction.rollback();
			throw e;
		}	
		return elementosBorrados;
	}
	
	@SuppressWarnings("unchecked")
	public List<ComprasDatosParticipante> consultaCultivoCiclo(Integer idComprador, Integer idCultivo, long idPgrCiclo)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasDatosParticipante> lst = null;
		if (idComprador != 0 && idComprador != -1){
			consulta.append(" where idComprador =").append(idComprador);
		}
		if (idCultivo != 0 && idCultivo != -1){
			if(consulta.length()>0){
				consulta.append(" and idCultivo = '").append(idCultivo).append("'");
			}else{
				consulta.append(" where idCultivo = '").append(idCultivo).append("'");
			}
		}
		if (idPgrCiclo != 0 && idPgrCiclo != -1){
			if(consulta.length()>0){
				consulta.append(" and idPgrCiclo=").append(idPgrCiclo);
			}else{
				consulta.append(" where idPgrCiclo=").append(idPgrCiclo);
			}
		}
		consulta.insert(0, "From ComprasDatosParticipante ");
		System.out.println(consulta);
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<ComprasBodegaTicketV> consultaBodegaTicket(long idCompProd, String boletaTicketBascula, String claveBodega)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasBodegaTicketV> lst = null;
		if (idCompProd != 0 && idCompProd != -1){
			consulta.append(" where idCompProd =").append(idCompProd);
		}
		if (boletaTicketBascula != null && boletaTicketBascula != ""){
			if(consulta.length()>0){
				consulta.append(" and boletaTicketBascula = '").append(boletaTicketBascula).append("'");
			}else{
				consulta.append("where boletaTicketBascula = '").append(boletaTicketBascula).append("'");
			}
		}
		if (claveBodega != null && claveBodega != ""){
			if(consulta.length()>0){
				consulta.append(" and claveBodega = '").append(claveBodega).append("'");
			}else{
				consulta.append("where claveBodega = '").append(claveBodega).append("'");
			}
		}
		consulta.insert(0, "From ComprasBodegaTicketV ");
		System.out.println(consulta);
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
	}
	
	/****nuevo***/
	public List<ComprasDatosParticipanteV> consultaComprasDatosParticipanteV(long idCompPer)throws  JDBCException{
		return consultaComprasDatosParticipanteV( 0, null, null, 0, 0, idCompPer,0,0);
	}
	public List<ComprasDatosParticipanteV> consultaComprasDatosParticipanteV(String folioCartaAdhesion)throws  JDBCException{
		return consultaComprasDatosParticipanteV( 0, folioCartaAdhesion, null, 0, 0, 0,0,0);
	}
	public List<ComprasDatosParticipanteV> consultaComprasDatosParticipanteV(int idRelacion, String folioCartaAdhesion, String claveBodega)throws  JDBCException{
		return consultaComprasDatosParticipanteV( idRelacion,  folioCartaAdhesion,  claveBodega, 0, 0, 0, 0,0);
	}
	@SuppressWarnings("unchecked")
	public List<ComprasDatosParticipanteV> consultaComprasDatosParticipanteV(int idRelacion, String folioCartaAdhesion, String claveBodega, long idPerRel, Integer idComprador, long idCompPer, int idEstado, int idEstatus)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ComprasDatosParticipanteV> lst = null;
		if (idRelacion != 0 && idRelacion != -1){
			consulta.append("where idRelacion = ").append(idRelacion);
		}
		if (folioCartaAdhesion != null && !folioCartaAdhesion.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and folioCartaAdhesion = '").append(folioCartaAdhesion).append("'");
			}else{
				consulta.append("where folioCartaAdhesion = '").append(folioCartaAdhesion).append("'");
			}
		}		
		if (claveBodega != null && !claveBodega.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and claveBodega = '").append(claveBodega).append("'");
			}else{
				consulta.append("where claveBodega = '").append(claveBodega).append("'");
			}
		}
		if (idPerRel != 0 && idPerRel != -1){
			if(consulta.length()>0){
				consulta.append(" and idPerRel = ").append(idPerRel);
			}else{
				consulta.append("where idPerRel = ").append(idPerRel);
			}
		}
		
		if (idComprador != 0 && idComprador != -1){
			if(consulta.length()>0){
				consulta.append(" and idComprador = ").append(idComprador);
			}else{
				consulta.append("where idComprador = ").append(idComprador);
			}
		}
		
		if (idCompPer != 0 && idCompPer != -1){
			if(consulta.length()>0){
				consulta.append(" and idCompPer = ").append(idCompPer);
			}else{
				consulta.append("where idCompPer = ").append(idCompPer);
			}
		}
		if (idEstatus != 0 && idEstatus != -1){
			if(consulta.length()>0){
				consulta.append(" and idEstatusComprasDatosPar = ").append(idEstatus);
			}else{
				consulta.append("where idEstatusComprasDatosPar = ").append(idEstatus);
			}
		}
		consulta.insert(0, "From ComprasDatosParticipanteV ").append(" order by claveBodega, cultivo, cicloLargo, ejercicio, estado");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<RelacionPorFolioCABodega> consultaRelacionComprasXFolioCAYBodega(String folioCartaAdhesion)throws  JDBCException{
		List<RelacionPorFolioCABodega> lst = null;
		
		String consulta = "select folio_carta_adhesion, clave_bodega, estado_acopio, id_tipo_relacion "+
						" from  compras_datos_participante_v "+
						" where folio_carta_adhesion = '"+folioCartaAdhesion+"'"+
						" group by  folio_carta_adhesion, clave_bodega, estado_acopio, id_tipo_relacion";
		lst= session.createSQLQuery(consulta.toString()).addEntity(RelacionPorFolioCABodega.class).list();
		
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<Productores> consultaProductores(Long productor)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<Productores> lst = null;
		if (productor != 0 && productor != -1){
			consulta.append("where productor = ").append(productor);
		}
		consulta.insert(0, "From Productores ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	
	public List<Predios> consultaPredios(String predio, long secuencial)throws  JDBCException{
		return consultaPredios(predio, secuencial, null);
	}
	
	public List<Predios> consultaPredios(String predio, long secuencial, String predioAlterno)throws  JDBCException{
		return consultaPredios(predio, secuencial, predioAlterno, "", 0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Predios> consultaPredios(String predio, long secuencial, String predioAlterno, String folioProductor, int idEstado)throws  JDBCException{
		
				
		StringBuilder consulta= new StringBuilder();
		List<Predios> lst = null;
		if (predio != null && !predio.isEmpty() ){
			consulta.append("where predio = '").append(predio).append("'");
		}
		
		if(secuencial != 0 && secuencial != -1){
			if(consulta.length()>0){
				consulta.append(" and predioSecuencial = ").append(secuencial);
			}else{
				consulta.append("where predioSecuencial = ").append(secuencial);
			}
		}
		
		
		if (predioAlterno != null && !predioAlterno.isEmpty() ){
			if(consulta.length()>0){
				consulta.append(" and predioAlterno = '").append(predioAlterno).append("'");
			}else{
				consulta.append("where predioAlterno = '").append(predioAlterno).append("'");
			}
		}
		
		if (folioProductor != null && !folioProductor.isEmpty() ){
			if(consulta.length()>0){
				consulta.append(" and folioProductor = ").append(folioProductor);
			}else{
				consulta.append("where folioProductor = ").append(folioProductor);
			}
		}
		
		
		if(idEstado != 0 && idEstado != -1){
			if(consulta.length()>0){
				consulta.append(" and idEstado = ").append(idEstado);
			}else{
				consulta.append("where idEstado = ").append(idEstado);
			}
		}
		consulta.insert(0, "From Predios ").append(" order by predio, predioSecuencial, predioAlterno ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	
	public Double getSuperficiePredioPrograma(String folioPredio, Integer secuencial, long idPerRel)throws JDBCException{
		StringBuilder consulta= new StringBuilder();
		double superficie = 0;
		consulta.insert(0, "select  COALESCE(sum(superficie),0) from compras_predio where folio_predio = ('"+folioPredio+"')"+" and "+ "folio_predio_sec="+secuencial+" and "+"id_per_rel="+idPerRel);
		superficie = Double.parseDouble(session.createSQLQuery(consulta.toString()).list().get(0).toString());
		return superficie;
	}
	
	public Double getSuperficiePredioProgramaAlterno(String folioPredio,  long idPerRel)throws JDBCException{
		StringBuilder consulta= new StringBuilder();
		double superficie = 0;
		consulta.insert(0, "select  COALESCE(sum(superficie),0) from compras_predio where folio_predio = ('"+folioPredio+"') and "+"id_per_rel="+idPerRel);
		superficie = Double.parseDouble(session.createSQLQuery(consulta.toString()).list().get(0).toString());
		return superficie;
	}
	
	public int validaBoletaByBodega(String boletaTicketBascula, String claveBodega)throws JDBCException{
		
		int valida = 0;		
		StringBuilder query = new StringBuilder();
		query.append("select count(1)")
		.append("from compras_entrada_bodega ceb, ")
		.append("compras_comp_productor ccp,")
		.append("compras_datos_participante cdp")
		.append("where ceb.boleta_ticket_bascula = ").append(boletaTicketBascula).append("' ")
		.append("and ceb.id_comp_prod = ccp.id_comp_prod ")
		.append("and ccp.id_comp_per = cdp.id_comp_per ")
		.append("and cdp.clave_bodega = '").append(claveBodega).append("' ");
		valida = (Integer) session.createSQLQuery(query.toString()).addEntity(Integer.class).list().get(0);
		return valida;
	}
	
	public int validaPartBodegaProd(String claveBodega, long folioProductor, int idComprador)throws JDBCException{
		
		int valida = 0;		
		StringBuilder query = new StringBuilder();
		query.append("select count(1)")
		.append("from compras_comp_productor ccp, ")
		.append("compras_datos_participante cdp ")
		.append("where ccp.id_comp_per = cdp.id_comp_per ")
		.append("and cdp.clave_bodega = '").append(claveBodega).append("' ")
		.append(" and ccp.folio_productor = ").append(folioProductor)
		.append(" and cdp.id_comprador= ").append(idComprador);
		valida = Integer.parseInt(session.createSQLQuery(query.toString()).list().get(0).toString());
		return valida;
	}
	
	
	public int validaFacturaByProductor(String folioFactura, long folioProductor, long idCompProd){
		int valida = 0;
		StringBuilder consulta= new StringBuilder();
		consulta.append(" where cfv.id_comp_prod = ccp.id_comp_prod ");
		
		if (folioFactura != null && !folioFactura.isEmpty() ){
			if(consulta.length()>0){
				consulta.append(" and cfv.folio_fac='").append(folioFactura).append("'");
			}else{
				consulta.append("where cfv.folio_fac='").append(folioFactura).append("'");
			}			
		}
		
		if (folioProductor !=0 &&  folioProductor != -1){
			if(consulta.length()>0){
				consulta.append(" and ccp.folio_productor = ").append(folioProductor);
			}else{
				consulta.append("where ccp.folio_productor = ").append(folioProductor);
			}
		}
		
		if (idCompProd !=0 &&  idCompProd != -1){
			if(consulta.length()>0){
				consulta.append(" and ccp.id_comp_prod  = ").append(idCompProd);
			}else{
				consulta.append("where ccp.id_comp_prod  = ").append(idCompProd);
			}
		}
			
		consulta.insert(0, "select count(1) from compras_comp_productor ccp, compras_fac_venta cfv,compras_datos_participante cdp ");
	
		valida = Integer.parseInt(session.createSQLQuery(consulta.toString()).list().get(0).toString());
		return valida;
	}

}