package mx.gob.comer.sipc.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import mx.gob.comer.sipc.domain.Aviso;
import mx.gob.comer.sipc.domain.Bitacora;
import mx.gob.comer.sipc.domain.Parametros;
import mx.gob.comer.sipc.log.AppLogger;
import mx.gob.comer.sipc.vistas.domain.NotificacionesV;

import org.hibernate.*;

import com.googlecode.s2hibernate.struts2.plugin.annotations.*;


public class UtileriasDAO {

  @SessionTarget
  Session session;

  @TransactionTarget
  Transaction transaction;

  	public UtileriasDAO() {
	  
  	}
  	public UtileriasDAO(Session session) {
  		this.session = session;
  	}

	public boolean sessionOk() throws Exception
  	{
  		if (session == null)
  	    {
  			try
  			{
  				session = com.googlecode.s2hibernate.struts2.plugin.util.HibernateSessionFactory.getNewSession();
	  	        if (!session.isOpen())
	  	            throw new NullPointerException("Fix the code: session's not here");
	
	  	        transaction = session.beginTransaction();
  			}
  			catch (HibernateException e) 
  			{
				return false;
			}
  	    }
		return true;
  	}
  
	@SuppressWarnings("unchecked")
	public String getParametros(String variable)
	{
		String respuesta = "";
		try
		{
			if(sessionOk())
			{
				String valor = null;
				List<Parametros> lst = (List<Parametros>) session.createQuery("from Parametros where variable = '"+ variable+"'").list();
				if(lst!=null && lst.size()>0){
					valor = lst.get(0).getValor();
				}		
				respuesta = valor;
			}
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return respuesta;
	} // end getParametros()

	public Date getFechaDiaHabilSumaDias(String fecha, int dias) throws Exception{ 
		String query;
		Date resp;
		try {
			query = "SELECT obten_fecha_mas_dias('"+fecha+"',"+dias+")";
			resp = (Date) session.createSQLQuery(query).list().get(0);
			return resp;
		}catch(JDBCException e){
			e.printStackTrace();
			AppLogger.error("Codigo error:" +  e.getErrorCode() );
			AppLogger.error("Mensaje:" +  e.getMessage()  );
			AppLogger.error("SQL:" +  e.getSQL() );
			AppLogger.error("Causa:"+e.getCause().getMessage());
			throw e;
		}catch (HibernateException e) {
			AppLogger.error( e.getCause() );
			throw e;
		}	    
	} // end getFechaDiaHabilSumaDias()

	public Date getFechaDiaHabilSumaDias(Date fecha, int dias) throws Exception{ 
		String query;
		Date resp;
		try {
			query = "SELECT obten_fecha_mas_dias('"+fecha+"',"+dias+")";
			resp = (Date) session.createSQLQuery(query).list().get(0);
			return resp;
		}catch(JDBCException e){
			e.printStackTrace();
			AppLogger.error("Codigo error:" +  e.getErrorCode() );
			AppLogger.error("Mensaje:" +  e.getMessage()  );
			AppLogger.error("SQL:" +  e.getSQL() );
			AppLogger.error("Causa:"+e.getCause().getMessage());
			throw e;
		}catch (HibernateException e) {
			AppLogger.error( e.getCause() );
			throw e;
		}	    
	} // end getFechaDiaHabilSumaDias()
	
	public Date getFechaDiaHabilSumaDias(Calendar fecha, int dias) throws Exception{ 
		String query;
		StringBuilder fechaTemp = new StringBuilder();
		fechaTemp.append(fecha.get(Calendar.YEAR)).append("-");
		fechaTemp.append(fecha.get(Calendar.MONTH)+1).append("-");
		fechaTemp.append(fecha.get(Calendar.DAY_OF_MONTH));
		Date resp;
		try {
			query = "SELECT obten_fecha_mas_dias('"+fechaTemp.toString()+"',"+dias+")";
			resp = (Date) session.createSQLQuery(query).list().get(0);
			return resp;
		}catch(JDBCException e){
			e.printStackTrace();
			AppLogger.error("Codigo error:" +  e.getErrorCode() );
			AppLogger.error("Mensaje:" +  e.getMessage()  );
			AppLogger.error("SQL:" +  e.getSQL() );
			AppLogger.error("Causa:"+e.getCause().getMessage());
			throw e;
		}catch (HibernateException e) {
			AppLogger.error( e.getCause() );
			throw e;
		}	    
	} // end getFechaDiaHabilSumaDias()

	public Date getFechaDiaHabilRestaDias(String fecha, int dias) throws Exception{ 
		String query;
		Date resp;
		try {
			query = "SELECT obten_fecha_menos_dias('"+fecha+"',"+dias+")";
			resp = (Date) session.createSQLQuery(query).list().get(0);
			return resp;
		}catch(JDBCException e){
			e.printStackTrace();
			AppLogger.error("Codigo error:" +  e.getErrorCode() );
			AppLogger.error("Mensaje:" +  e.getMessage()  );
			AppLogger.error("SQL:" +  e.getSQL() );
			AppLogger.error("Causa:"+e.getCause().getMessage());
			throw e;
		}catch (HibernateException e) {
			AppLogger.error( e.getCause() );
			throw e;
		}	    
	} // end getFechaDiaHabilSumaDias()

	public Date getFechaDiaHabilRestaDias(Date fecha, int dias) throws Exception{ 
		String query;
		Date resp;
		try {
			query = "SELECT obten_fecha_menos_dias('"+fecha+"',"+dias+")";
			resp = (Date) session.createSQLQuery(query).list().get(0);
			return resp;
		}catch(JDBCException e){
			e.printStackTrace();
			AppLogger.error("Codigo error:" +  e.getErrorCode() );
			AppLogger.error("Mensaje:" +  e.getMessage()  );
			AppLogger.error("SQL:" +  e.getSQL() );
			AppLogger.error("Causa:"+e.getCause().getMessage());
			throw e;
		}catch (HibernateException e) {
			AppLogger.error( e.getCause() );
			throw e;
		}	    
	} // end getFechaDiaHabilSumaDias()
	
	@SuppressWarnings("unchecked")
	public boolean getDiaHabil(String fecha) throws Exception {
		StringBuilder hql = new StringBuilder();
		hql.append("select valida_dia_habil(date '").append(fecha).append("') as res");
		List<Boolean> res = 
			(List<Boolean>) session.createSQLQuery(hql.toString()).addScalar("res",Hibernate.BOOLEAN).list();
		return res.get(0).booleanValue();
	}
	
	@SuppressWarnings("unchecked")
	public boolean getDiaHabil(Date fechaDate) throws Exception {
		StringBuilder hql = new StringBuilder();
		String fecha = new SimpleDateFormat("yyyy-MM-dd").format(fechaDate);
		hql.append("select valida_dia_habil(date '").append(fecha).append("') as res");
		List<Boolean> res = 
			(List<Boolean>) session.createSQLQuery(hql.toString()).addScalar("res",Hibernate.BOOLEAN).list();
		return res.get(0).booleanValue();
	}
	
		

	public void guardaBitacora(Bitacora bitacora) throws Exception{
	   try {
		   	session.save(bitacora);
	   }catch( Exception e ){
	         transaction.rollback();
	         AppLogger.warn( "error al guardar la bitacora (" + e.getMessage() + ")" );
	         throw e; 
	  }
   }

	public int getNumeroDiaAnio(String fecha) {
		String query;
		Integer resp;
		try {
			query = "SELECT obten_numero_dia_anio('"+fecha+"')";
			resp = (Integer) session.createSQLQuery(query).list().get(0);
			return resp.intValue();
		}catch(JDBCException e){
			e.printStackTrace();
			AppLogger.error("Codigo error:" +  e.getErrorCode() );
			AppLogger.error("Mensaje:" +  e.getMessage()  );
			AppLogger.error("SQL:" +  e.getSQL() );
			AppLogger.error("Causa:"+e.getCause().getMessage());
			throw e;
		}catch (HibernateException e) {
			AppLogger.error( e.getCause() );
			throw e;
		}	    
	} // end getNumeroDiaAnio()

	public int getNumeroQuincenas(String fecha) throws Exception{ 
		String query;
		Integer resp;
		try {
			query = "SELECT obten_numero_quincena('"+fecha+"')";
			resp = (Integer) session.createSQLQuery(query).list().get(0);
			return resp.intValue();
		}catch(JDBCException e){
			e.printStackTrace();
			AppLogger.error("Codigo error:" +  e.getErrorCode() );
			AppLogger.error("Mensaje:" +  e.getMessage()  );
			AppLogger.error("SQL:" +  e.getSQL() );
			AppLogger.error("Causa:"+e.getCause().getMessage());
			throw e;
		}catch (HibernateException e) {
			AppLogger.error( e.getCause() );
			throw e;
		}	    
	} // end getNumeroQuincenas()

	public Integer getClaveRastreoTESOFE(String clave) throws Exception{ 
		String query;
		Integer resp;
		try {
			query = "SELECT obten_clave_rastreo_tesofe('"+clave+"')";
			resp = (Integer) session.createSQLQuery(query).list().get(0);
			return resp;
		}catch(JDBCException e){
			e.printStackTrace();
			AppLogger.error("Codigo error:" +  e.getErrorCode() );
			AppLogger.error("Mensaje:" +  e.getMessage()  );
			AppLogger.error("SQL:" +  e.getSQL() );
			AppLogger.error("Causa:"+e.getCause().getMessage());
			throw e;
		}catch (HibernateException e) {
			AppLogger.error( e.getCause() );
			throw e;
		}	    
	} // end getClaveRastreoTESOFE()

	public String eliminaCaracteresEspeciales(String cadena) throws Exception{ 
		String query;
		String resp;
		try {
			query = "SELECT replace(replace(replace(replace(replace(replace(upper('"+cadena+"'),'Ñ','N'),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U')";
			resp = (String) session.createSQLQuery(query).list().get(0);
			return resp;
		}catch(JDBCException e){
			e.printStackTrace();
			AppLogger.error("Codigo error:" +  e.getErrorCode() );
			AppLogger.error("Mensaje:" +  e.getMessage()  );
			AppLogger.error("SQL:" +  e.getSQL() );
			AppLogger.error("Causa:"+e.getCause().getMessage());
			throw e;
		}catch (HibernateException e) {
			AppLogger.error( e.getCause());
			throw e;
		}	    
	} // end eliminaCaracteresEspeciales()


	@SuppressWarnings("unchecked")
	public Date getUltimoDiaHabilMes(Date fecha) throws Exception
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select obten_fecha_habil_mes('").append(fecha).append("') as res");

		System.out.println("ultimo dia habil " + hql);
		List<Date> res = (List<Date>) session.createSQLQuery(hql.toString()).addScalar("res", Hibernate.DATE).list();
		return res.get(0);
	}

	public void guardaBitacoraCA(Bitacora bitacora)
	{
		try
		{
			session = com.googlecode.s2hibernate.struts2.plugin.util.HibernateSessionFactory.getNewSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(bitacora);
			transaction.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (session != null && session.isOpen())
			{
				session.close();
			}
		}

	}

	
	@SuppressWarnings("unchecked")
	public String isolatedGetParametros(String variable){
		
		String valor = null;
		Session s = null;
		try{
			s = com.googlecode.s2hibernate.struts2.plugin.util.HibernateSessionFactory.getNewSession();
			List<Parametros> lst = (List<Parametros>) s.createQuery("from Parametros where variable = '"+ variable+"'").list();
			if(lst!=null && lst.size()>0){
				valor = ((Parametros) lst.get(0)).getValor();
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(s!=null && s.isOpen()){
				s.close();
			}
		}
		return valor;
	}
	@SuppressWarnings("unchecked")
	public List<Aviso> consultaAvisos(int idUsuario, int idAviso, boolean habilitar){
		StringBuilder consulta= new StringBuilder();
		List<Aviso> lst=null;
		if (idUsuario != 0 && idUsuario != -1){
			consulta.append("where para = ").append(idUsuario);
		}
		if(idAviso !=0 && idAviso!=-1){
			if(consulta.length()>0){
				consulta.append(" and idAviso = ").append(idAviso);
			}else{
				consulta.append("where idAviso= ").append(idAviso);
			}
		}
		if(habilitar){
			if(consulta.length()>0){
				consulta.append(" and habilitar = ").append(true);
			}else{
				consulta.append("where habilitar= ").append(true);
			}
		}
		consulta.insert(0, "From Aviso ").append(" ORDER BY idAviso desc");
		lst= session.createQuery(consulta.toString()).list();
		
		return lst;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<NotificacionesV> consultaDestinatariosNotifacacionV(int idEvento, boolean aviso, boolean correo){
		StringBuilder consulta= new StringBuilder();
		List<NotificacionesV> lst=null;
		if(idEvento!=0 && idEvento !=-1){
			consulta.append("where eventoId = ").append(idEvento);
		}
		if(aviso){
			if(consulta.length()>0){
				consulta.append(" and aviso = ").append(true);
			}else{
				consulta.append("where aviso= ").append(true);
			}
		}
		if(correo){
			if(consulta.length()>0){
				consulta.append(" and correo = ").append(true);
			}else{
				consulta.append("where correo= ").append(true);
			}
		}
		consulta.insert(0, "From NotificacionesV ");
		lst= session.createQuery(consulta.toString()).list();
		return lst;
	}
	
	public Date getFechaDiaNaturalSumaDias(String fecha, int dias) throws Exception{ 
		String query;
		Date resp;
		try {
			query = "SELECT obten_fecha_mas_dias_naturales('"+fecha+"',"+dias+")";
			resp = (Date) session.createSQLQuery(query).list().get(0);
			return resp;
		}catch(JDBCException e){
			e.printStackTrace();
			AppLogger.error("Codigo error:" +  e.getErrorCode() );
			AppLogger.error("Mensaje:" +  e.getMessage()  );
			AppLogger.error("SQL:" +  e.getSQL() );
			AppLogger.error("Causa:"+e.getCause().getMessage());
			throw e;
		}catch (HibernateException e) {
			AppLogger.error( e.getCause() );
			throw e;
		}	    
	} // end getFechaDiaNaturalSumaDias()

	public Date getFechaDiaNaturalSumaDias(Date fecha, int dias) throws Exception{ 
		String query;
		Date resp;
		try {
			query = "SELECT obten_fecha_mas_dias_naturales('"+fecha+"',"+dias+")";
			resp = (Date) session.createSQLQuery(query).list().get(0);
			return resp;
		}catch(JDBCException e){
			e.printStackTrace();
			AppLogger.error("Codigo error:" +  e.getErrorCode() );
			AppLogger.error("Mensaje:" +  e.getMessage()  );
			AppLogger.error("SQL:" +  e.getSQL() );
			AppLogger.error("Causa:"+e.getCause().getMessage());
			throw e;
		}catch (HibernateException e) {
			AppLogger.error( e.getCause() );
			throw e;
		}	    
	} // end getFechaDiaNaturalSumaDias()
	
	public Date getFechaDiaNaturalSumaDias(Calendar fecha, int dias) throws Exception{ 
		String query;
		StringBuilder fechaTemp = new StringBuilder();
		fechaTemp.append(fecha.get(Calendar.YEAR)).append("-");
		fechaTemp.append(fecha.get(Calendar.MONTH)+1).append("-");
		fechaTemp.append(fecha.get(Calendar.DAY_OF_MONTH));
		Date resp;
		try {
			query = "SELECT obten_fecha_mas_dias_naturales('"+fechaTemp.toString()+"',"+dias+")";
			resp = (Date) session.createSQLQuery(query).list().get(0);
			return resp;
		}catch(JDBCException e){
			e.printStackTrace();
			AppLogger.error("Codigo error:" +  e.getErrorCode() );
			AppLogger.error("Mensaje:" +  e.getMessage()  );
			AppLogger.error("SQL:" +  e.getSQL() );
			AppLogger.error("Causa:"+e.getCause().getMessage());
			throw e;
		}catch (HibernateException e) {
			AppLogger.error( e.getCause() );
			throw e;
		}	    
	} // end getFechaDiaNaturalSumaDias()
	
	// Borra objeto en BD
    public void borrarObjeto(Object o) throws JDBCException {
        session.delete(o);
        session.flush();
    } // end borrarObjeto()
    
    // Guarda objeto en BD
	public void guardaObjeto(Object o) throws  Exception, JDBCException{
		 try {
			session.save(o);
			session.flush();
			session.clear();				
		 } catch( Exception e ){
	        transaction.rollback();
	        throw e; 
	     } 
	} // end guardaObjeto 
}//end Class
