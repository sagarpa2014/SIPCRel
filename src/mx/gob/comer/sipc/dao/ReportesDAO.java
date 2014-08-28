package mx.gob.comer.sipc.dao;

import java.util.ArrayList;
import java.util.List;

import mx.gob.comer.sipc.vistas.domain.OficioCompradorProgramaV;
import mx.gob.comer.sipc.vistas.domain.ReporteConcentradoPagosV;
import mx.gob.comer.sipc.vistas.domain.ReporteDetConcentradoPagosEtapasV;
import mx.gob.comer.sipc.vistas.domain.ReporteDetConcentradoPagosV;
import mx.gob.comer.sipc.vistas.domain.ReporteGlobalV;
import mx.gob.comer.sipc.vistas.domain.ReporteProgramaCompradorV;
import mx.gob.comer.sipc.vistas.domain.RepresentanteCompradorV;
import mx.gob.comer.sipc.vistas.domain.RespuestaPagosV;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.googlecode.s2hibernate.struts2.plugin.annotations.TransactionTarget;

public class ReportesDAO {

	@SessionTarget
	Session session;
	
	@TransactionTarget
	Transaction transaction;
	
	
	@SuppressWarnings("unchecked")
	public List<RespuestaPagosV> consultaRespuestaPagosV(int idPrograma, String noOficio, String fechaInicio, String fechaFin )throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<RespuestaPagosV> lst = null;
		
		if (idPrograma != 0 && idPrograma != -1){
			consulta.append("where idPrograma = ").append(idPrograma);
		}
		
		if(noOficio != null && !noOficio.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and noOficio='").append(noOficio).append("'");
			}else{
				consulta.append("where noOficio='").append(noOficio).append("'");
			}
		}
		if((fechaInicio != null && !fechaInicio.equals(""))&& (fechaFin !=null && !fechaFin.equals(""))){
			if(consulta.length()>0){
				consulta.append(" and  (TO_CHAR(fechaOficio,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}else{
				consulta.append(" where (TO_CHAR(fechaOficio,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}
		}else{
			if(fechaInicio != null && !fechaInicio.equals("")){
				if(consulta.length()>0){
					consulta.append(" and (TO_CHAR(fechaOficio,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}else{
					consulta.append("where (TO_CHAR(fechaOficio,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}
			}
		}
		consulta.insert(0, "From RespuestaPagosV ").append(" order by idOficioPagos");
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
	}
	
	
	public List<OficioCompradorProgramaV> consultaOficioCompradorProgramaV(long idOficioPagos) throws  JDBCException{
		return consultaOficioCompradorProgramaV(idOficioPagos,null,0);
	}
	
	public List<OficioCompradorProgramaV> consultaOficioCompradorProgramaV(long idOficioPagos, int estatus) throws  JDBCException{
		return consultaOficioCompradorProgramaV(idOficioPagos,null, estatus);
	}
	@SuppressWarnings("unchecked")
	public List<OficioCompradorProgramaV> consultaOficioCompradorProgramaV(long idOficioPagos, String idPagos, int estatus)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<OficioCompradorProgramaV> lst = null;

		if (idOficioPagos != 0 && idOficioPagos != -1){
			consulta.append("where idOficioPagos=").append(idOficioPagos);
		}
		
		if(idPagos != null && idPagos!=""){
			if(consulta.length()>0){
				consulta.append(" and idPago in (").append(idPagos).append(")");
			}else{
				consulta.append("where idPago in (").append(idPagos).append(")");
			}
		}
		
		if(estatus !=0 && estatus !=-1){
			if(consulta.length()>0){
				consulta.append(" and estatus = ").append(estatus);
			}else{
				consulta.append("where estatus =").append(estatus);
			}
		}
		consulta.insert(0, "From OficioCompradorProgramaV ");
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<OficioCompradorProgramaV> consultaOficioCompradorProgramaVSession(long idOficioPagos)throws  Exception{
		StringBuilder consulta= new StringBuilder();
		List<OficioCompradorProgramaV> lst = null;
		try{
			session = com.googlecode.s2hibernate.struts2.plugin.util.HibernateSessionFactory.getNewSession();
			transaction = session.beginTransaction();
	
			if (idOficioPagos != 0 && idOficioPagos != -1){
				consulta.append("where idPago=").append(idOficioPagos);
			}
			
			consulta.insert(0, "From OficioCompradorProgramaV ");
			lst= session.createQuery(consulta.toString()).list();
			transaction.commit();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			if (session != null && session.isOpen()){
				session.close();
			}
		}
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<RepresentanteCompradorV> consultaRepresentanteCompradorVSession(int idRepresentate, int idComprador, int estatusRepresentante)throws Exception {
		StringBuilder consulta= new StringBuilder();
		List<RepresentanteCompradorV> lst = null;
		try{		
			session = com.googlecode.s2hibernate.struts2.plugin.util.HibernateSessionFactory.getNewSession();
			transaction = session.beginTransaction();

			if(idRepresentate!=0 && idRepresentate != -1){
				consulta.append("where idRepresentate = ").append(idRepresentate);
			}
			if(idComprador != 0 && idComprador != -1){
			       if(consulta.length()>0){
			    	   consulta.append(" and idComprador=").append(idComprador);
			       	}else{
			    		consulta.append("where idComprador=").append(idComprador);
			       	}
			}
			if(estatusRepresentante != 0 && estatusRepresentante != -1){
			       if(consulta.length()>0){
			    	   consulta.append(" and estatusRepresentante=").append(estatusRepresentante);
			       	}else{
			    		consulta.append("where estatusRepresentante=").append(estatusRepresentante);
			       	}
			}
			consulta.insert(0, "From RepresentanteCompradorV ");
	        lst= session.createQuery(consulta.toString()).list(); 
	        transaction.commit();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			if (session != null && session.isOpen()){
				session.close();
			}
		}
		
		return lst;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<RespuestaPagosV> consultaOficiosPagoV(long idOficioPagos, String noOficio, int folioCLC) throws JDBCException {
		StringBuilder consulta= new StringBuilder();
		List<RespuestaPagosV> lst=null;
		if (idOficioPagos != 0 && idOficioPagos != -1){
			consulta.append("where idOficioPagos = ").append(idOficioPagos);
		}
		
		if (noOficio != null && !noOficio.isEmpty()){
			if(consulta.length()>0){
				consulta.append(" and noOficio='").append(noOficio).append("'");
			}else{
				consulta.append("where noOficio='").append(noOficio).append("'");
			}
		}
		if (folioCLC!= 0 && folioCLC!=-1){
			if(consulta.length()>0){
				consulta.append(" and folioClc=").append(folioCLC);
			}else{
				consulta.append("where folioClc=").append(folioCLC);
			}
		}
		consulta.insert(0, "From RespuestaPagosV ").append(" ORDER BY noOficio");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}

	@SuppressWarnings("unchecked")
	public List<RespuestaPagosV> isolatedConsultaRespuestaPagosV(int idPrograma, String noOficio, String fechaInicio, String fechaFin )throws  JDBCException{
		Session s = null;
		StringBuilder consulta= new StringBuilder();
		List<RespuestaPagosV> lst = null;

		try{			
			s = com.googlecode.s2hibernate.struts2.plugin.util.HibernateSessionFactory.getNewSession();
			if (idPrograma != 0 && idPrograma != -1){
				consulta.append("where idPrograma = ").append(idPrograma);
			}
			
			if(noOficio != null && !noOficio.isEmpty()){
				if(consulta.length()>0){
					consulta.append(" and noOficio='").append(noOficio).append("'");
				}else{
					consulta.append("where noOficio='").append(noOficio).append("'");
				}
			}
			if((fechaInicio != null && !fechaInicio.equals(""))&& (fechaFin !=null && !fechaFin.equals(""))){
				if(consulta.length()>0){
					consulta.append(" and  (TO_CHAR(fechaOficio,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
							.append(" and '").append(fechaFin).append("'");
				}else{
					consulta.append(" where (TO_CHAR(fechaOficio,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
							.append(" and '").append(fechaFin).append("'");
				}
			}else{
				if(fechaInicio != null && !fechaInicio.equals("")){
					if(consulta.length()>0){
						consulta.append(" and (TO_CHAR(fechaOficio,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
					}else{
						consulta.append("where (TO_CHAR(fechaOficio,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
					}
				}
			}
			consulta.insert(0, "From RespuestaPagosV ").append(" order by idOficioPagos");
			lst= s.createQuery(consulta.toString()).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(s!=null && s.isOpen()){
				s.close();
			}
		}
		
		return lst;
	}
	
	@SuppressWarnings("rawtypes")
	public List consultaReporteDinamico(int tipoReporte, String [] idCriterios, String[] agrupados, int pagado, 
			int inicio, int numRegistros, int pagina, String fechaInicio, String fechaFin)throws  JDBCException{
		return consultaReporteDinamico(tipoReporte, idCriterios, agrupados, 0, pagado, 0,0, inicio, numRegistros, pagina, fechaInicio, fechaFin, 0);
	}

	@SuppressWarnings("rawtypes")
	public List consultaReporteDinamico(int tipoReporte, String [] idCriterios, String[] agrupados, int pagado, 
			int inicio, int numRegistros, int pagina, String fechaInicio, String fechaFin, int idPrograma)throws  JDBCException{
		return consultaReporteDinamico(tipoReporte, idCriterios, agrupados, 0, pagado, 0,0, inicio, numRegistros, pagina, fechaInicio, fechaFin, idPrograma);
	}

	@SuppressWarnings("rawtypes")
	public List consultaReporteDinamico(int tipoReporte, String [] idCriterios, String[] agrupados, int tramitado, int pagado,
			int rechazado, int pendiente, int inicio, int numRegistros, int pagina, String fechaInicio,String fechaFin, int idPrograma)throws  JDBCException{
		
		List lista = new ArrayList<Object>();
		StringBuilder consulta= new StringBuilder();
		String condicionPrograma="";
		String condicionComprador="";
		String condicionOficio="";
		String condicionFechaPago="";
		String condicionEstado="";
		StringBuilder sqlTramitados = new StringBuilder();
		StringBuilder sqlVolumenTramitados = new StringBuilder();
		StringBuilder sqlImporteTramitados = new StringBuilder();
		StringBuilder sqlAplicados = new StringBuilder();
		StringBuilder sqlVolumenAplicados = new StringBuilder();
		StringBuilder sqlImporteAplicados = new StringBuilder();
		StringBuilder sqlRechazados = new StringBuilder();
		StringBuilder sqlVolumenRechazados = new StringBuilder();
		StringBuilder sqlImporteRechazados = new StringBuilder();
		StringBuilder sqlPendientes = new StringBuilder();
		StringBuilder sqlVolumenPendiente = new StringBuilder();
		StringBuilder sqlImportePendiente = new StringBuilder();
		StringBuilder select= new StringBuilder();
		StringBuilder groupBy= new StringBuilder();
		StringBuilder orderBy= new StringBuilder();
		final String from =" from tramitados_pagados_pendiente_v t";
		final String where =" where estatus=5";
		
		String [] elementSelect = new String [idCriterios.length];
		String [] elementOrderBy = new String [idCriterios.length];
		String [] elementGroupBy = new String [idCriterios.length];
				
		/*Asignando los valores a los criterios seleccionados por el usuario*/
		int programa = 0;
		int agrupacionPrograma = 0;
		int participante = 0;
		int agrupacionParticipante = 0;
		int oficio = 0;
		int agrupacionOficio = 0;
		int fechaPago = 0;
		int agrupacionFechaPago = 0;
		int estado = 0;
		int agrupacionEstado = 0;
		int idCriterio = 0;
		for(int i=0; i<idCriterios.length; i++){
			idCriterio = Integer.parseInt(idCriterios[i]);
			if(tipoReporte == 0){
				if(idCriterio==1){
					programa = idCriterio;
					agrupacionPrograma = Integer.parseInt(agrupados[i]);
				}else if(idCriterio==2){
					participante = idCriterio;
					agrupacionParticipante = Integer.parseInt(agrupados[i]);
				}else if(idCriterio==3){
					oficio = idCriterio;
					agrupacionOficio = Integer.parseInt(agrupados[i]);
				}else if(idCriterio==4){
					fechaPago = idCriterio;
					agrupacionFechaPago = Integer.parseInt(agrupados[i]);
				}else if(idCriterio==5){
					estado = idCriterio;
					agrupacionEstado = Integer.parseInt(agrupados[i]);
				}
			} else {
				if(idCriterio==1){
					programa = idCriterio;
					agrupacionPrograma = Integer.parseInt(agrupados[i]);
				}else if(idCriterio==2){
					participante = idCriterio;
					agrupacionParticipante = Integer.parseInt(agrupados[i]);
				}else if(idCriterio==3){
					oficio = idCriterio;
					agrupacionOficio = Integer.parseInt(agrupados[i]);
				}else if(idCriterio==4){
					estado = idCriterio;
					agrupacionEstado = Integer.parseInt(agrupados[i]);
				}				
			}
		}

		String selectCount="(select coalesce(count(ps.id_pago),0) from pagos ps ";
		String selectVolumen="(select coalesce(sum(ps.volumen),0) from pagos ps ";
		String selectImporte="(select coalesce(sum(ps.importe),0) from pagos ps ";
		
		if(programa !=0){
			elementSelect[agrupacionPrograma]=" t.id_programa, t.programa,";
			elementGroupBy[agrupacionPrograma]=" t.id_programa, t.programa,";
			elementOrderBy[agrupacionPrograma]=" t.programa,";
			condicionPrograma=" and ps.id_programa=t.id_programa ";
			if(idPrograma>0){
				condicionPrograma+=" and t.id_programa = "+idPrograma+" ";
			}
		}		
		if(participante != 0){
			elementSelect[agrupacionParticipante]=" t.id_comprador, t.comprador, t.no_carta,";	
			elementGroupBy[agrupacionParticipante]=" t.id_comprador, t.comprador, t.no_carta,";
			elementOrderBy[agrupacionParticipante]=" t.comprador,";	
			condicionComprador = " and ps.id_comprador = t.id_comprador ";
		}
		if(oficio != 0){
			elementSelect[agrupacionOficio]=" t.id_oficio, t.no_oficio, t.fecha_oficio,";	
			elementGroupBy[agrupacionOficio]=" t.id_oficio, t.no_oficio, t.fecha_oficio,";
			elementOrderBy[agrupacionOficio]=" t.no_oficio,";
			condicionOficio = "and ps.id_oficio = t.id_oficio ";
		}
		if(fechaPago != 0){
			elementSelect[agrupacionFechaPago]=" t.fecha_pago,";
			elementGroupBy[agrupacionFechaPago]=" t.fecha_pago,";
			elementOrderBy[agrupacionFechaPago]=" t.fecha_pago,";
			condicionFechaPago = " and ps.fecha_pago=t.fecha_pago ";
		}
		if(estado != 0){
			selectCount="(select coalesce(count(distinct pd.id_pago),0) from pagos ps  , pagos_detalle pd ";
			selectVolumen="(select coalesce(sum(pd.volumen),0) from pagos ps  , pagos_detalle pd ";
			selectImporte="(select coalesce(sum(pd.importe),0) from pagos ps  , pagos_detalle pd ";
			
			elementSelect[agrupacionEstado]=" t.id_estado, t.estado,";
			elementGroupBy[agrupacionEstado]=" t.id_estado, t.estado,";
			elementOrderBy[agrupacionEstado]=" t.estado,";
			condicionEstado = " and ps.id_pago = pd.id_pago and pd.id_estado=t.id_estado ";
			if(participante != 0){
				condicionEstado = condicionEstado + " and ps.no_carta = t.no_carta ";
			}			
		}
		
		for(int i=0; i<elementSelect.length;i++){
			select.append(elementSelect[i]);
			groupBy.append(elementGroupBy[i]);
			orderBy.append(elementOrderBy[i]);
		}
		groupBy.insert(0, " GROUP BY ");
		groupBy.deleteCharAt(groupBy.length()-1);
		orderBy.insert(0, " ORDER BY ");
		orderBy.deleteCharAt(orderBy.length()-1);
		
		if(tramitado!=0){
			sqlTramitados.append(selectCount+" where ps.id_oficio is not null ");
			sqlVolumenTramitados.append(selectVolumen+" where ps.id_oficio is not null ");
			sqlImporteTramitados.append(selectImporte+" where ps.id_oficio is not null ");
			if(programa != 0){
				sqlTramitados.append(condicionPrograma);
				sqlVolumenTramitados.append(condicionPrograma);
				sqlImporteTramitados.append(condicionPrograma);
			}
			if(participante != 0){
				sqlTramitados.append(condicionComprador);
				sqlVolumenTramitados.append(condicionComprador);
				sqlImporteTramitados.append(condicionComprador);
			}
			if(oficio != 0){
				sqlTramitados.append(condicionOficio);
				sqlVolumenTramitados.append(condicionOficio);
				sqlImporteTramitados.append(condicionOficio);
			}
			if(estado != 0){
				sqlTramitados.append(condicionEstado);
				sqlVolumenTramitados.append(condicionEstado);
				sqlImporteTramitados.append(condicionEstado);
			}
						
			sqlTramitados.append(") as tramitados,\n");
			sqlVolumenTramitados.append(") as volumen_tramitado,\n ");
			sqlImporteTramitados.append(") as importe_tramitado,");
			select.append(sqlTramitados.toString()).append(sqlVolumenTramitados).append(sqlImporteTramitados);
		}
		
		
		if(pagado!=0){
			sqlAplicados.append(selectCount+" where ps.estatus =5 ");
			sqlVolumenAplicados.append(selectVolumen+" where ps.estatus =5 ");
			sqlImporteAplicados.append(selectImporte+" where ps.estatus =5 ");
			if(programa !=0){
				sqlAplicados.append(condicionPrograma);
				sqlVolumenAplicados.append(condicionPrograma);
				sqlImporteAplicados.append(condicionPrograma);
			}
			if(participante != 0){
				sqlAplicados.append(condicionComprador);
				sqlVolumenAplicados.append(condicionComprador);
				sqlImporteAplicados.append(condicionComprador);
			}
			if(oficio != 0){
				sqlAplicados.append(condicionOficio);
				sqlVolumenAplicados.append(condicionOficio);
				sqlImporteAplicados.append(condicionOficio);
			}	
			if(fechaPago != 0){
				sqlAplicados.append(condicionFechaPago);
				sqlVolumenAplicados.append(condicionFechaPago);
				sqlImporteAplicados.append(condicionFechaPago);
			}
			if(estado != 0){
				sqlAplicados.append(condicionEstado);
				sqlVolumenAplicados.append(condicionEstado);
				sqlImporteAplicados.append(condicionEstado);
			}
			
			sqlAplicados.append(")as aplicados,\n");
			sqlVolumenAplicados.append(") as volumen_aplicado,\n");
			sqlImporteAplicados.append(") as importe_aplicado,");
			select.append(sqlAplicados.toString()).append(sqlVolumenAplicados).append(sqlImporteAplicados);
		}
		
		if(rechazado!=0){
			sqlRechazados.append(selectCount+" where ps.estatus =6 ");
			sqlVolumenRechazados.append(selectVolumen+" where ps.estatus =6 ");
			sqlImporteRechazados.append(selectImporte+" where ps.estatus =6 ");
			if(programa !=0){
				sqlRechazados.append(condicionPrograma);
				sqlVolumenRechazados.append(condicionPrograma);
				sqlImporteRechazados.append(condicionPrograma);
			}
			if(participante != 0){
				sqlRechazados.append(condicionComprador);
				sqlVolumenRechazados.append(condicionComprador);
				sqlImporteRechazados.append(condicionComprador);
			}
			if(oficio != 0){
				sqlRechazados.append(condicionOficio);
				sqlVolumenRechazados.append(condicionOficio);
				sqlImporteRechazados.append(condicionOficio);
			}	
			if(estado != 0){
				sqlRechazados.append(condicionEstado);
				sqlVolumenRechazados.append(condicionEstado);
				sqlImporteRechazados.append(condicionEstado);
			}	
			
			sqlRechazados.append(")as rechazados,\n");	
			sqlVolumenRechazados.append(") as volumen_rechazado,\n");
			sqlImporteRechazados.append(") as importe_rechazado,");
			
			select.append(sqlRechazados.toString()).append(sqlVolumenRechazados).append(sqlImporteRechazados);
		}
	
		if(pendiente!=0){
			sqlPendientes.append(selectCount+" where ps.estatus in (2,4) ");
			sqlVolumenPendiente.append(selectVolumen+" where ps.estatus in (2,4) ");
			sqlImportePendiente.append(selectImporte+" where ps.estatus in (2,4) ");
			if(programa !=0){
				sqlPendientes.append(condicionPrograma);
				sqlVolumenPendiente.append(condicionPrograma);
				sqlImportePendiente.append(condicionPrograma);
			}
			if(participante != 0){
				sqlPendientes.append(condicionComprador);
				sqlVolumenPendiente.append(condicionComprador);
				sqlImportePendiente.append(condicionComprador);
			}
			if(oficio != 0){
				sqlPendientes.append(condicionOficio);
				sqlVolumenPendiente.append(condicionOficio);
				sqlImportePendiente.append(condicionOficio);
			}	
			if(estado != 0){
				sqlPendientes.append(condicionEstado);
				sqlVolumenPendiente.append(condicionEstado);
				sqlImportePendiente.append(condicionEstado);
			}	
			
			sqlPendientes.append(")as pendientes,\n");
			sqlVolumenPendiente.append(") as volumen_pendiente,\n");
			sqlImportePendiente.append(") as importe_pendiente,");
			select.append(sqlPendientes.toString()).append(sqlVolumenPendiente).append(sqlImportePendiente);
		}
		select.insert(0, "SELECT ");
		select.deleteCharAt(select.length()-1);
		
		if(tipoReporte == 1){
			if(programa !=0 && idPrograma>0){
				consulta.append(" where t.id_programa = "+idPrograma+" "+" ");
				if((fechaInicio != null && !fechaInicio.equals(""))&& (fechaFin !=null && !fechaFin.equals(""))){
					consulta.append("  and to_number(TO_char(t.fecha_oficio, 'YYYYMMDD'), '99999999') between to_number(TO_CHAR(to_date('").append(fechaInicio).append("', 'DD-MM-YYYY'), 'YYYYMMDD'), '99999999')")
					.append(" and to_number(TO_CHAR(to_date('").append(fechaFin).append("', 'DD-MM-YYYY'), 'YYYYMMDD'), '99999999')");
				}else if ((fechaInicio != null && !fechaInicio.equals(""))){
					consulta.append("and TO_DATE(t.fecha_oficio, 'DD-MM-YYYY')='").append(fechaInicio).append("'");
				}
			} else {
				if((fechaInicio != null && !fechaInicio.equals(""))&& (fechaFin !=null && !fechaFin.equals(""))){
					consulta.append("  where to_number(TO_char(t.fecha_oficio, 'YYYYMMDD'), '99999999') between to_number(TO_CHAR(to_date('").append(fechaInicio).append("', 'DD-MM-YYYY'), 'YYYYMMDD'), '99999999')")
					.append(" and to_number(TO_CHAR(to_date('").append(fechaFin).append("', 'DD-MM-YYYY'), 'YYYYMMDD'), '99999999')");
				}else if ((fechaInicio != null && !fechaInicio.equals(""))){
					consulta.append("where TO_DATE(t.fecha_oficio, 'DD-MM-YYYY')='").append(fechaInicio).append("'");
				}
			}
			if(pagina==0){				
				consulta.insert(0, select.toString()+"\n"+from+" ").append(groupBy.toString()+" ").append(orderBy.toString());
			}else{				
				consulta.insert(0,select.toString()+"\n"+from+" ").append(groupBy.toString()+" ").append(orderBy.toString()).append(" limit ").append(numRegistros).append(" offset ").append(inicio);
			}
		}else{
			if(programa !=0 && idPrograma>0){
				consulta.append(where+" and t.id_programa = "+idPrograma+" "+" ");
			} else {
				consulta.append(where+" ");
			}
			if((fechaInicio != null && !fechaInicio.equals(""))&& (fechaFin !=null && !fechaFin.equals(""))){
				consulta.append("  and to_number(TO_char(t.fecha_pago, 'YYYYMMDD'), '99999999') between to_number(TO_CHAR(to_date('").append(fechaInicio).append("', 'DD-MM-YYYY'), 'YYYYMMDD'), '99999999')")
				.append(" and to_number(TO_CHAR(to_date('").append(fechaFin).append("', 'DD-MM-YYYY'), 'YYYYMMDD'), '99999999')");
			}else if ((fechaInicio != null && !fechaInicio.equals(""))){
				consulta.append("and TO_DATE(t.fecha_pago, 'DD-MM-YYYY')='").append(fechaInicio).append("'");
			}
			if(pagina==0){
				consulta.insert(0,select.toString()+"\n"+from+" ").append(groupBy.toString()+" ").append(orderBy.toString());
			}else{
				consulta.insert(0,select.toString()+"\n"+from+" ").append(groupBy.toString()+" ").append(orderBy.toString()).append(" limit ").append(numRegistros).append(" offset ").append(inicio);;
			}
				
		}
		lista =  session.createSQLQuery(consulta.toString()).list();
		
		
		return lista;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ReporteGlobalV> consultaReporteGlobalPagos(String fechaInicio, String fechaFin)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ReporteGlobalV> lst = null;
		if((fechaInicio != null && !fechaInicio.equals(""))&& (fechaFin !=null && !fechaFin.equals(""))){
			if(consulta.length()>0){
				consulta.append(" and  (TO_CHAR(fecha,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}else{
				consulta.append(" where (TO_CHAR(fecha,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}
		}else{
			if(fechaInicio != null && !fechaInicio.equals("")){
				if(consulta.length()>0){
					consulta.append(" and (TO_CHAR(fecha,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}else{
					consulta.append("where (TO_CHAR(fecha,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}
			}
		}		
		consulta.insert(0, "From ReporteGlobalV ");
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
	}	

	@SuppressWarnings("unchecked")
	public List<ReporteProgramaCompradorV> consultaReporteProgramaCompradorPagos(String fechaInicio, String fechaFin )throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ReporteProgramaCompradorV> lst = null;
		if((fechaInicio != null && !fechaInicio.equals(""))&& (fechaFin !=null && !fechaFin.equals(""))){
			if(consulta.length()>0){
				consulta.append(" and  (TO_CHAR(fecha,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}else{
				consulta.append(" where (TO_CHAR(fecha,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}
		}else{
			if(fechaInicio != null && !fechaInicio.equals("")){
				if(consulta.length()>0){
					consulta.append(" and (TO_CHAR(fecha,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}else{
					consulta.append("where (TO_CHAR(fecha,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}
			}
		}		
		consulta.insert(0, "From ReporteProgramaCompradorV ");
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
	}	

	@SuppressWarnings("unchecked")
	public List<ReporteGlobalV> insolatedConsultaReporteGlobalPagos(String fechaInicio, String fechaFin)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ReporteGlobalV> lst = null;
		Session s = null;
		try{					
			s = com.googlecode.s2hibernate.struts2.plugin.util.HibernateSessionFactory.getNewSession();
			if((fechaInicio != null && !fechaInicio.equals(""))&& (fechaFin !=null && !fechaFin.equals(""))){
				if(consulta.length()>0){
					consulta.append(" and  (TO_CHAR(fecha,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
							.append(" and '").append(fechaFin).append("'");
				}else{
					consulta.append(" where (TO_CHAR(fecha,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
							.append(" and '").append(fechaFin).append("'");
				}
			}else{
				if(fechaInicio != null && !fechaInicio.equals("")){
					if(consulta.length()>0){
						consulta.append(" and (TO_CHAR(fecha,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
					}else{
						consulta.append("where (TO_CHAR(fecha,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
					}
				}
			}					
			consulta.insert(0, "From ReporteGlobalV ");
			lst= s.createQuery(consulta.toString()).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(s!=null && s.isOpen()){
				s.close();
			}
		}
		
		return lst;
	}	

	@SuppressWarnings("unchecked")
	public List<ReporteProgramaCompradorV> insolatedConsultaReporteProgramaCompradorPagos(String fechaInicio, String fechaFin)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ReporteProgramaCompradorV> lst = null;
		Session s = null;
		try{					
			s = com.googlecode.s2hibernate.struts2.plugin.util.HibernateSessionFactory.getNewSession();
			if((fechaInicio != null && !fechaInicio.equals(""))&& (fechaFin !=null && !fechaFin.equals(""))){
				if(consulta.length()>0){
					consulta.append(" and  (TO_CHAR(fecha,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
							.append(" and '").append(fechaFin).append("'");
				}else{
					consulta.append(" where (TO_CHAR(fecha,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
							.append(" and '").append(fechaFin).append("'");
				}
			}else{
				if(fechaInicio != null && !fechaInicio.equals("")){
					if(consulta.length()>0){
						consulta.append(" and (TO_CHAR(fecha,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
					}else{
						consulta.append("where (TO_CHAR(fecha,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
					}
				}
			}								
			consulta.insert(0, "From ReporteProgramaCompradorV ");
			lst= s.createQuery(consulta.toString()).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(s!=null && s.isOpen()){
				s.close();
			}
		}
		
		return lst;
	}	

	public Double consultaReporteGlobalTotales(int campo, String fechaInicio, String fechaFin)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		Double lst = null;
		if((fechaInicio != null && !fechaInicio.equals(""))&& (fechaFin !=null && !fechaFin.equals(""))){
			if(consulta.length()>0){
				consulta.append(" and  (TO_CHAR(fecha,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}else{
				consulta.append(" where (TO_CHAR(fecha,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}
		}else{
			if(fechaInicio != null && !fechaInicio.equals("")){
				if(consulta.length()>0){
					consulta.append(" and (TO_CHAR(fecha,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}else{
					consulta.append("where (TO_CHAR(fecha,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}
			}
		}										
		switch (campo) {
		case 1:
			consulta.insert(0, "select sum(tramitados) From reporte_global_v ");
			break;
		case 2:
			consulta.insert(0, "select sum(volumen_tramitado) From reporte_global_v ");
			break;
		case 3:
			consulta.insert(0, "select sum(importe_tramitado) From reporte_global_v ");
			break;
		case 4:
			consulta.insert(0, "select sum(pagados) From reporte_global_v ");
			break;
		case 5:
			consulta.insert(0, "select sum(volumen_pagado) From reporte_global_v ");
			break;
		case 6:
			consulta.insert(0, "select sum(importe_pagado) From reporte_global_v ");
			break;
		case 7:
			consulta.insert(0, "select sum(rechazados) From reporte_global_v ");
			break;
		case 8:
			consulta.insert(0, "select sum(volumen_rechazado) From reporte_global_v ");
			break;
		case 9:
			consulta.insert(0, "select sum(importe_rechazado) From reporte_global_v ");
			break;
		case 10:
			consulta.insert(0, "select sum(pendientes) From reporte_global_v ");
			break;
		case 11:
			consulta.insert(0, "select sum(volumen_pendiente) From reporte_global_v ");
			break;
		case 12:
			consulta.insert(0, "select sum(importe_pendiente) From reporte_global_v ");
			break;
		}
		lst= Double.parseDouble(session.createSQLQuery(consulta.toString()).list().get(0).toString());	
		return lst;
	}	

	public Double consultaReporteProgramaTotales(int campo, String fechaInicio, String fechaFin)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		Double lst = null;
		if((fechaInicio != null && !fechaInicio.equals(""))&& (fechaFin !=null && !fechaFin.equals(""))){
			if(consulta.length()>0){
				consulta.append(" and  (TO_CHAR(fecha,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}else{
				consulta.append(" where (TO_CHAR(fecha,'YYYY-MM-DD')) between '").append(fechaInicio).append("'")
						.append(" and '").append(fechaFin).append("'");
			}
		}else{
			if(fechaInicio != null && !fechaInicio.equals("")){
				if(consulta.length()>0){
					consulta.append(" and (TO_CHAR(fecha,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}else{
					consulta.append("where (TO_CHAR(fecha,'YYYY-MM-DD'))='").append(fechaInicio).append("'");
				}
			}
		}										
		switch (campo) {
		case 1:
			consulta.insert(0, "select sum(tramitados) From reporte_programa_comprador_v ");
			break;
		case 2:
			consulta.insert(0, "select sum(volumen_tramitado) From reporte_programa_comprador_v ");
			break;
		case 3:
			consulta.insert(0, "select sum(importe_tramitado) From reporte_programa_comprador_v ");
			break;
		case 4:
			consulta.insert(0, "select sum(pagados) From reporte_programa_comprador_v ");
			break;
		case 5:
			consulta.insert(0, "select sum(volumen_pagado) From reporte_programa_comprador_v ");
			break;
		case 6:
			consulta.insert(0, "select sum(importe_pagado) From reporte_programa_comprador_v ");
			break;
		case 7:
			consulta.insert(0, "select sum(rechazados) From reporte_programa_comprador_v ");
			break;
		case 8:
			consulta.insert(0, "select sum(volumen_rechazado) From reporte_programa_comprador_v ");
			break;
		case 9:
			consulta.insert(0, "select sum(importe_rechazado) From reporte_programa_comprador_v ");
			break;
		case 10:
			consulta.insert(0, "select sum(pendientes) From reporte_programa_comprador_v ");
			break;
		case 11:
			consulta.insert(0, "select sum(volumen_pendiente) From reporte_programa_comprador_v ");
			break;
		case 12:
			consulta.insert(0, "select sum(importe_pendiente) From reporte_programa_comprador_v ");
			break;
		}
		lst= Double.parseDouble(session.createSQLQuery(consulta.toString()).list().get(0).toString());	
		return lst;
	}	
	

	@SuppressWarnings("unchecked")
	public List<ReporteConcentradoPagosV> consultaReporteConcentradoPagos(int ejercicio)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ReporteConcentradoPagosV> lst = null;

		if (ejercicio != 0 && ejercicio != -1){
			consulta.append("where anio = ").append(ejercicio);
		}
		
		consulta.insert(0, "From ReporteConcentradoPagosV ");
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
	}	

	public Double consultaReporteConcetradoTotales(int campo, int ejercicio)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		Double lst = null;
		if (ejercicio != 0 && ejercicio != -1){
			consulta.append("where anio = ").append(ejercicio);
		}
		switch (campo) {
		case 1:
			consulta.insert(0, "select sum(volumen_1er_trimestre) From concentrado_pagos_trimestres_v ");
			break;
		case 2:
			consulta.insert(0, "select sum(importe_1er_trimestre) From concentrado_pagos_trimestres_v ");
			break;
		case 3:
			consulta.insert(0, "select sum(volumen_2do_trimestre) From concentrado_pagos_trimestres_v ");
			break;
		case 4:
			consulta.insert(0, "select sum(importe_2do_trimestre) From concentrado_pagos_trimestres_v ");
			break;
		case 5:
			consulta.insert(0, "select sum(volumen_3er_trimestre) From concentrado_pagos_trimestres_v ");
			break;
		case 6:
			consulta.insert(0, "select sum(importe_3er_trimestre) From concentrado_pagos_trimestres_v ");
			break;
		case 7:
			consulta.insert(0, "select sum(volumen_4to_trimestre) From concentrado_pagos_trimestres_v ");
			break;
		case 8:
			consulta.insert(0, "select sum(importe_4to_trimestre) From concentrado_pagos_trimestres_v ");
			break;
		}
		lst= Double.parseDouble(session.createSQLQuery(consulta.toString()).list().get(0).toString());	
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<ReporteConcentradoPagosV> insolatedConsultaReporteConcentradoPagos(int ejercicio)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ReporteConcentradoPagosV> lst = null;
		Session s = null;
		try{					
			s = com.googlecode.s2hibernate.struts2.plugin.util.HibernateSessionFactory.getNewSession();
			if (ejercicio != 0 && ejercicio != -1){
				consulta.append("where anio = ").append(ejercicio);
			}
			
			consulta.insert(0, "From ReporteConcentradoPagosV ");
			lst= s.createQuery(consulta.toString()).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(s!=null && s.isOpen()){
				s.close();
			}
		}

		return lst;
	}	
	
	@SuppressWarnings("unchecked")
	public List<ReporteDetConcentradoPagosV> consultaReporteDetConcentradoPagos(int idPrograma, int ejercicio)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ReporteDetConcentradoPagosV> lst = null;

		if (idPrograma != 0 && idPrograma != -1){
			consulta.append("where idPrograma = ").append(idPrograma);
		}
		
		if (ejercicio != 0 && ejercicio != -1){
			if(consulta.length()>0){
				consulta.append(" and ejercicio=").append(ejercicio);
			}else{
				consulta.append("where ejercicio=").append(ejercicio);
			}
		}

		consulta.insert(0, "From ReporteDetConcentradoPagosV ");
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
	}	

	public Double consultaReporteDetConcetradoTotales(int campo, int idPrograma, int ejercicio)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		Double lst = null;
		if (idPrograma != 0 && idPrograma != -1){
			consulta.append("where id_programa = ").append(idPrograma);
		}
		
		if (ejercicio != 0 && ejercicio != -1){
			if(consulta.length()>0){
				consulta.append(" and anio=").append(ejercicio);
			}else{
				consulta.append("where anio=").append(ejercicio);
			}
		}
		switch (campo) {
		case 1:
			consulta.insert(0, "select sum(volumen_1er_trimestre) From concentrado_det_pagos_trimestres_v ");
			break;
		case 2:
			consulta.insert(0, "select sum(importe_1er_trimestre) From concentrado_det_pagos_trimestres_v ");
			break;
		case 3:
			consulta.insert(0, "select sum(volumen_2do_trimestre) From concentrado_det_pagos_trimestres_v ");
			break;
		case 4:
			consulta.insert(0, "select sum(importe_2do_trimestre) From concentrado_det_pagos_trimestres_v ");
			break;
		case 5:
			consulta.insert(0, "select sum(volumen_3er_trimestre) From concentrado_det_pagos_trimestres_v ");
			break;
		case 6:
			consulta.insert(0, "select sum(importe_3er_trimestre) From concentrado_det_pagos_trimestres_v ");
			break;
		case 7:
			consulta.insert(0, "select sum(volumen_4to_trimestre) From concentrado_det_pagos_trimestres_v ");
			break;
		case 8:
			consulta.insert(0, "select sum(importe_4to_trimestre) From concentrado_det_pagos_trimestres_v ");
			break;
		}
		lst= Double.parseDouble(session.createSQLQuery(consulta.toString()).list().get(0).toString());	
		return lst;
	}

	@SuppressWarnings("unchecked")
	public List<ReporteDetConcentradoPagosV> insolatedConsultaReporteDetConcentradoPagos(int idPrograma, int ejercicio)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ReporteDetConcentradoPagosV> lst = null;
		Session s = null;
		try{					
			s = com.googlecode.s2hibernate.struts2.plugin.util.HibernateSessionFactory.getNewSession();
			if (idPrograma != 0 && idPrograma != -1){
				consulta.append("where idPrograma = ").append(idPrograma);
			}
			
			if (ejercicio != 0 && ejercicio != -1){
				if(consulta.length()>0){
					consulta.append(" and ejercicio=").append(ejercicio);
				}else{
					consulta.append("where ejercicio=").append(ejercicio);
				}
			}
	
			consulta.insert(0, "From ReporteDetConcentradoPagosV ");
			lst= s.createQuery(consulta.toString()).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(s!=null && s.isOpen()){
				s.close();
			}
		}		
		return lst;
	}
	@SuppressWarnings("unchecked")
	public List<ReporteDetConcentradoPagosEtapasV> consultaReporteDetConcentradoPagosEtapas(int idPrograma, int ejercicio)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ReporteDetConcentradoPagosEtapasV> lst = null;

		if (idPrograma != 0 && idPrograma != -1){
			consulta.append("where idPrograma = ").append(idPrograma);
		}
		
		if (ejercicio != 0 && ejercicio != -1){
			if(consulta.length()>0){
				consulta.append(" and ejercicio=").append(ejercicio);
			}else{
				consulta.append("where ejercicio=").append(ejercicio);
			}
		}

		consulta.insert(0, "From ReporteDetConcentradoPagosEtapasV ");
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
	}	

	public Double consultaReporteDetConcetradoEtapasTotales(int campo, int idPrograma, int ejercicio)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		Double lst = null;
		if (idPrograma != 0 && idPrograma != -1){
			consulta.append("where id_programa = ").append(idPrograma);
		}
		
		if (ejercicio != 0 && ejercicio != -1){
			if(consulta.length()>0){
				consulta.append(" and anio=").append(ejercicio);
			}else{
				consulta.append("where anio=").append(ejercicio);
			}
		}
		switch (campo) {
		case 1:
			consulta.insert(0, "select sum(volumen_1er_trimestre_etapa1) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 11:
			consulta.insert(0, "select sum(volumen_1er_trimestre_etapa2) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 12:
			consulta.insert(0, "select sum(volumen_1er_trimestre_etapa3) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 13:
			consulta.insert(0, "select sum(volumen_1er_trimestre_etapa4) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 14:
			consulta.insert(0, "select sum(volumen_1er_trimestre_etapa5) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 2:
			consulta.insert(0, "select sum(importe_1er_trimestre_etapa1) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 21:
			consulta.insert(0, "select sum(importe_1er_trimestre_etapa2) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 22:
			consulta.insert(0, "select sum(importe_1er_trimestre_etapa3) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 23:
			consulta.insert(0, "select sum(importe_1er_trimestre_etapa4) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 24:
			consulta.insert(0, "select sum(importe_1er_trimestre_etapa5) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 3:
			consulta.insert(0, "select sum(volumen_2do_trimestre_etapa1) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 31:
			consulta.insert(0, "select sum(volumen_2do_trimestre_etapa2) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 32:
			consulta.insert(0, "select sum(volumen_2do_trimestre_etapa3) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 33:
			consulta.insert(0, "select sum(volumen_2do_trimestre_etapa4) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 34:
			consulta.insert(0, "select sum(volumen_2do_trimestre_etapa5) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 4:
			consulta.insert(0, "select sum(importe_2do_trimestre_etapa1) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 41:
			consulta.insert(0, "select sum(importe_2do_trimestre_etapa2) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 42:
			consulta.insert(0, "select sum(importe_2do_trimestre_etapa3) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 43:
			consulta.insert(0, "select sum(importe_2do_trimestre_etapa4) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 44:
			consulta.insert(0, "select sum(importe_2do_trimestre_etapa5) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 5:
			consulta.insert(0, "select sum(volumen_3er_trimestre_etapa1) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 51:
			consulta.insert(0, "select sum(volumen_3er_trimestre_etapa2) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 52:
			consulta.insert(0, "select sum(volumen_3er_trimestre_etapa3) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 53:
			consulta.insert(0, "select sum(volumen_3er_trimestre_etapa4) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 54:
			consulta.insert(0, "select sum(volumen_3er_trimestre_etapa5) From concentrado_det_pagos_trimestres_etapas_v ");
			break;			
		case 6:
			consulta.insert(0, "select sum(importe_3er_trimestre_etapa1) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 61:
			consulta.insert(0, "select sum(importe_3er_trimestre_etapa2) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 62:
			consulta.insert(0, "select sum(importe_3er_trimestre_etapa3) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 63:
			consulta.insert(0, "select sum(importe_3er_trimestre_etapa4) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 64:
			consulta.insert(0, "select sum(importe_3er_trimestre_etapa5) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 7:
			consulta.insert(0, "select sum(volumen_4to_trimestre_etapa1) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 71:
			consulta.insert(0, "select sum(volumen_4to_trimestre_etapa2) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 72:
			consulta.insert(0, "select sum(volumen_4to_trimestre_etapa3) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 73:
			consulta.insert(0, "select sum(volumen_4to_trimestre_etapa4) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 74:
			consulta.insert(0, "select sum(volumen_4to_trimestre_etapa5) From concentrado_det_pagos_trimestres_etapas_v ");
			break;			
		case 8:
			consulta.insert(0, "select sum(importe_4to_trimestre_etapa1) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 81:
			consulta.insert(0, "select sum(importe_4to_trimestre_etapa2) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 82:
			consulta.insert(0, "select sum(importe_4to_trimestre_etapa3) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 83:
			consulta.insert(0, "select sum(importe_4to_trimestre_etapa4) From concentrado_det_pagos_trimestres_etapas_v ");
			break;
		case 84:
			consulta.insert(0, "select sum(importe_4to_trimestre_etapa5) From concentrado_det_pagos_trimestres_etapas_v ");
			break;			
		}
		lst= Double.parseDouble(session.createSQLQuery(consulta.toString()).list().get(0).toString());	
		return lst;
	}

	@SuppressWarnings("unchecked")
	public List<ReporteDetConcentradoPagosEtapasV> insolatedConsultaReporteDetConcentradoPagosEtapas(int idPrograma, int ejercicio)throws  JDBCException{
		StringBuilder consulta= new StringBuilder();
		List<ReporteDetConcentradoPagosEtapasV> lst = null;
		Session s = null;
		try{					
			s = com.googlecode.s2hibernate.struts2.plugin.util.HibernateSessionFactory.getNewSession();
			if (idPrograma != 0 && idPrograma != -1){
				consulta.append("where idPrograma = ").append(idPrograma);
			}
			
			if (ejercicio != 0 && ejercicio != -1){
				if(consulta.length()>0){
					consulta.append(" and ejercicio=").append(ejercicio);
				}else{
					consulta.append("where ejercicio=").append(ejercicio);
				}
			}
	
			consulta.insert(0, "From ReporteDetConcentradoPagosEtapasV ");
			lst= s.createQuery(consulta.toString()).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(s!=null && s.isOpen()){
				s.close();
			}
		}		
		return lst;
	}	


}
