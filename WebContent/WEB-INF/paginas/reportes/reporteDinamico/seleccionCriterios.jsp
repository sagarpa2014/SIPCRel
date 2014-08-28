<%@taglib uri="/struts-tags" prefix="s"%>
<s:if test="%{tipoReporte==1}" >
	<br>
	<div class="izquierda">
		<input id="tramitado" name="tramitado" value="1" type="checkbox"  class="columnas" />
	</div>
	<div class="izquierda">
		<font class="fuente3">Tramitado</font>
	</div>
	<div class="izquierda">
		<input id="pagado" name="pagado" value="2" type="checkbox" class="columnas" />
	</div>
	<div class="izquierda">
		<font class="fuente3">Pagado</font>
	</div>
	<div class="izquierda">
		<input id="rechazado" name="rechazado" value="3" type="checkbox" class="columnas" />
	</div>
	<div class="izquierda">
		<font class="fuente3">Rechazado</font>
	</div>
	<div class="izquierda">
		<input id="pendiente" name="pendiente" value="4" type="checkbox" class="columnas"/>
	</div>
	<div class="izquierda">
		<font class="fuente3">Pendiente</font>
	</div>
</s:if>
<div class="clear"></div>
<br>
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
<div class="clear"></div>
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
<div class="clear"></div>	
<table>
	<tr>
		<th><input name="o1" value="-1" type="checkbox" onclick ="chkTodos();" class="check_todos"/></th>
		<th>Criterio</th>
		<th>Orden de Agrupaci�n</th>
	</tr>
	<s:hidden name="numCriterios" id="numCriterios" value="%{lstOpciones.size()}"/>
	<s:iterator value="lstOpciones" var="resultado"  status="itStatus">
		<tr>
			<td class="td1"><input id="id<s:property value="%{#itStatus.count}"/>" name="idCriterios" value="<s:property value="id"/>" type="checkbox"  class="ck" onclick="programaSeleccionado(this.value);"/></td>
			<td class="td1"><s:property value="%{criterio}"/></td>
			<td class="td1"><s:textfield id="a%{#itStatus.count}" name="agrupados"  maxlength="1" size="5"  value="%{}"/></td>
			<s:if test="%{id==1}" >
				<td class="td1"><s:select id="idPrograma" name="idPrograma" list="lstProgramas" listKey="idPrograma" listValue="%{descripcionCorta}" headerKey="-1" headerValue="-- Seleccione una opci�n --" cssClass="elementoOculto" /></td>
			</s:if>
		</tr>
	</s:iterator>
</table>
<div class="clear"></div>
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

