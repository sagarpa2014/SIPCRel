<%@taglib uri="/struts-tags" prefix="s"%>
<s:if test="lstProgramaRelacionCultivos.size() > 0">
	<div class="clear"></div>
	<center>
		<table class="clean">
			<tr>
				<th class="clean"></th>
				<th class="clean">Cultivo</th>
			</tr>
			<s:iterator value="lstProgramaRelacionCultivos" var="resultado"  status="itStatus">
				<tr>
					<td><s:property value="%{#itStatus.count}"/></td>
					<td>
						<s:select id="cultivo%{#itStatus.count}" name="selecCultivo" list="lstCultivo" listKey="idCultivo" listValue="%{cultivo}" headerKey="-1" headerValue="-- Seleccione una opci�n --"  value="%{idCultivo}" cssClass="cultivoClass" />
					</td>					
				</tr>	
			</s:iterator>
		</table>
	</center>
</s:if>
