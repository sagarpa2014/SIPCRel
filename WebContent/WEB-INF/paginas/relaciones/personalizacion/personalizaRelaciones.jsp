<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" src="<s:url value="/js/relaciones.js" />"></script>
<s:form action="registraPersonalizacionRelacion" onsubmit="return chkCamposCapturaPersonalizacionRel();">
	<s:if test="hasActionErrors()">
	  	 <s:include value="/WEB-INF/paginas/template/abrirMensajeDialogo.jsp" />
	</s:if>
	<div class="borderBottom"><h1>RELACIONES</h1></div><br>
	<s:if test="msjOk!=null && msjOk !=''">
		<div  class="msjSatisfactorio"><s:property value="%{msjOk}"/></div>	
	</s:if>
	<s:hidden id="registrar" name="registrar" value="%{registrar}"/>
	<s:hidden id="idIniEsquemaRelacion" name="idIniEsquemaRelacion" value="%{idIniEsquemaRelacion}"/>
	<s:if test="registrar==3">
		<s:hidden id="idPerRel" name="idPerRel" value="%{idPerRel}"/>
	</s:if>
	<div id="dialogo_1"></div>
	<fieldset>
		<legend>Personalizaci&oacute;n de Relaciones</legend>
		<div class="clear"></div>
		<div>
			<label class="left1"><span class="requerido">*</span>Esquema:</label>
			<font class="arial12bold"><s:property value="%{nombreEsquema}"/></font>  
		</div>
		<div class="clear"></div>
		<div>
			<label class="left1"><span class="requerido">*</span>Tipo de Relaci&oacute;n:</label>
			<s:select id="idRelacion" name="idRelacion" list="lstRelaciones" listKey="idRelacion" listValue="%{relacion}" headerKey="-1" headerValue="-- Seleccione una opción --" onchange="recuperaConfRelacionD()" onclik="recuperaConfRelacionD()" />   
		</div>
		<div class="clear"></div>
		<div id="recuperaConfRelacionD">
			<s:if test="registrar==2 || registrar==3">
				<s:include value="configuracionRelacionD.jsp"/>
			</s:if>
		</div>		
	</fieldset>
	<div class="clear"></div>
	<s:if test="registrar==0 || registrar == 3">
		<div class="accion">
			<s:submit  value="Guardar" cssClass="boton2" />
			<a href="<s:url value="/relaciones/listPersonalizacionRelaciones"/>" class="boton" title="" >Cancelar</a>
		</div>
	</s:if>
	<div class="clear"></div>
	<div class="izquierda">
		<a href="<s:url value="/relaciones/listPersonalizacionRelaciones?idIniEsquemaRelacion=%{idIniEsquemaRelacion}"/>" class="" title="" >&lt;&lt; Regresar</a>
	</div>
	<s:if test="registrar==2">
		<script>
			$(document).ready(function() {	
				$("input").attr('disabled','disabled');
				$("select").attr('disabled','disabled');
			});	 	
		</script>
	</s:if>
	<s:elseif test="registrar==3">
		<script>
			$(document).ready(function() {	
				$("#idCultivo").attr('disabled','disabled');
				$("#cicloAgricola").attr('disabled','disabled');
				$("#idRelacion").attr('disabled','disabled');
			});	 	
		</script>
	</s:elseif>
	
</s:form>
