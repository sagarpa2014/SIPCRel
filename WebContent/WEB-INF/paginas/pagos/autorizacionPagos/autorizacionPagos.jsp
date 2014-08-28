<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<script type="text/javascript" src="<s:url value="/js/autorizacionPagos.js" />"></script>
<s:if test="hasActionErrors()">
   <s:include value="/WEB-INF/paginas/template/abrirMensajeDialogo.jsp" />
</s:if>
<div id="dialogo_1"></div>
<s:if test="msjOk!=null && msjOk !=''">
	<div  class="msjSatisfactorio"><s:property value="%{msjOk}"/></div>	
</s:if>
<div class="borderBottom"><h1>CONSULTA DE PAGOS SOLICITADOS</h1></div><br>
<s:form action="realizaBusquedaAutorizacionPago" ><!--  onsubmit="return chkCamposBusquedaPagos();"-->
	<fieldset id="" class="clear">
		<legend>Criterios de B&uacute;squeda</legend>
		<div>
			<label class="left1">Especialista:</label>
			<s:select id="idEspecialista" name="idEspecialista" list="lstEspecialistaPagos" listKey="idEspecialista" listValue="%{nombre}" headerKey="-1" headerValue="-- Seleccione una opci�n --" tabindex="0"  onchange="recuperaProgByEspecialista()" />
		</div>
		<div id="recuperaProgByEspecialista"><s:include value="lstProgramas.jsp"/></div>
		<div>
			<label class="left1">Fecha Inicio:</label>	
			<s:if test="%{fechaInicio==null}" >
				<s:textfield name="fechaInicio" maxlength="10" size="10" id="fechaInicio" readonly="true" cssClass="dateBox" />
			</s:if>
			<s:else>
				<s:textfield key="fechaInicio" value="%{}"    maxlength="10" size="10" id="fechaInicio" readonly="true" cssClass="dateBox" />
			</s:else>
			<img src="../images/calendar.gif" id="trg1" style="cursor: pointer;" alt="Seleccione la fecha" border="0" class="dtalign" title="Seleccione la fecha" />
		</div>
		<div>
			<label class="left1">Fecha Fin:</label>	
			<s:if test="%{fechaFin==null}" >
				<s:textfield name="fechaFin" maxlength="10" size="10" id="fechaFin" readonly="true" cssClass="dateBox" />
			</s:if>
			<s:else>
				<s:textfield key="fechaFin" value="%{}"   maxlength="10" size="10" id="fechaFin" readonly="true" cssClass="dateBox" />
			</s:else>
			<img src="../images/calendar.gif" id="trg2" style="cursor: pointer;" alt="Seleccione la fecha" border="0" class="dtalign" title="Seleccione la fecha" />
		</div>
		<div>
			<label class="left1">Estatus:</label>
			<s:select id="estatusCve" name="estatusCve" list="lstEstatusPago" listKey="estatusId" listValue="%{descripcionStatus}" headerKey="-1" headerValue="-- Seleccione una opci�n --" tabindex="0" onchange="" value="%{}"/>
		</div>				
		<div>
			<p><span class="requerido">*&nbsp;Debe seleccionar al menos una opci&oacute;n</span></p>
		</div>
		<div class="aline"></div>
		<div class="accion">	    	
		    <s:submit  value="Consultar Solicitud" cssClass="boton2" />
		</div>
	</fieldset>
</s:form>
<s:form action="registrarAutorizacion" method="post" enctype="multipart/form-data" onsubmit="return chkFileAtentaNota();">
	<s:hidden id="numCampos" name="numCampos" value="%{numCampos}"/>	
	<s:if test="%{bandera==true}">
		<s:if test="lstPagosV.size() > 0">
			<div class="clear"></div>
			<br/>
			<fieldset>
				<legend>Resultado de B&uacute;squeda</legend>
				<div id="tablaResultados">
					
				</div>
				
				<table>					
						<tr>
							<th>No. Registro</th>
							<th>Especialista</th>
							<th>Programa</th>
							<th>No. Carta</th>
							<th>Comprador</th>
							<th>Estatus Actual</th>
							<th>Etapa</th>
							<th>Volumen (ton.)</th>
							<th>Importe Neto ($)</th>
							<s:if test="%{estatusCve == 7}">
								<th>Estatus Nuevo</th>
							</s:if>
							<s:else>
								<th>Descarga</th>
							</s:else>
							<th>Carga Atenta Nota Firmada</th>
						</tr>
						<s:iterator value="lstPagosV" var="resultado"  status="itStatus">
							<s:hidden id="v%{#itStatus.count}" name="idPagos" value="%{idPago}"/>
							<tr>
								<td align="center"><font class="arial12bold"><s:property value="%{#itStatus.count}"/></font></td>
								<td align="center"><font class="arial12bold"><s:text name="especialista"><s:param value="%{especialista}"/></s:text></font></td>
								<td align="center"><font class="arial12bold"><s:text name="nombrePgrCorto"><s:param value="%{nombrePgrCorto}"/></s:text></font></td>
								<td align="center"><font class="arial12bold"><s:text name="noCarta"><s:param value="%{noCarta}"/></s:text></font></td>
								<td align="center"><font class="arial12bold"><s:text name="nombreComprador"><s:param value="%{nombreComprador}"/></s:text></font></td>
								<td align="center"><font class="arial12bold"><s:text name="estatusPago"><s:param value="%{estatusPago}"/></s:text></font></td>
								<td align="center"><font class="arial12bold"><s:text name="etapa"><s:param value="%{etapa}"/></s:text></font></td>
								<td align="center"><font class="arial12bold"><s:text name="volumen"><s:param value="%{volumen}"/></s:text></font></td>
								<td align="center"><font class="arial12bold"><s:text name="importe"><s:param value="%{importe}"/></s:text></font></td>
								<s:if test="%{estatusPago == 'REGISTRADO'}">
									<td><s:select id="estatusId%{#itStatus.count}" name="estatusId" onchange="selectEstatus(this.value, %{#itStatus.count});" headerKey="-1" headerValue="-- Pendiente --"	list="#{'1':'AUTORIZADO', '8':'RECHAZADO'}"  value="" /></td>
									<td id="contenedorFile<s:property value="%{#itStatus.count}"/>"></td>
								</s:if>	
								<s:else>
									<td align="center"><a href="<s:url value="/pagos/consigueAtentaNota?idPago=%{idPago}"/>" title="Descargar" class="pdf"></a></td>
									<td id="contenedorFile<s:property value="%{#itStatus.count}"/>">
										<s:file name="doc" id="f%{count}" onchange="cambiaValor(%{idPago},  %{#itStatus.count});"/>
										<s:hidden id="p%{#itStatus.count}" name="idPagosCopia" value="%{}"/>
									</td>
								</s:else>								
							</tr>
						</s:iterator>
				</table>
				
				
				
			</fieldset>
			<div class="accion">
				<s:submit  value="Guardar" cssClass="boton2" />
				<a href="<s:url value="/pagos/busquedaAutorizacionPagos"/>" class="boton" title="" >Limpiar</a>
			</div>
		</s:if>	
		<s:else><div class="advertencia">No se encontraron registros con los criterios capturados</div></s:else>
	</s:if>

	
</s:form>
<script type="text/javascript">
	<!--
		Calendar.setup({
			inputField     :    "fechaInicio",     
			ifFormat       :    "%d/%m/%Y",     
			button         :    "trg1",  
			align          :    "Br",           
			singleClick    :    true
		});
		Calendar.setup({
			inputField     :    "fechaFin",     
			ifFormat       :    "%d/%m/%Y",     
			button         :    "trg2",  
			align          :    "Br",           
			singleClick    :    true
			});

		
	//-->
</script>
