<%@taglib uri="/struts-tags" prefix="s"%>
<s:if test="idEspecialista==-1 ">
	<div>
		<label class="left1">Programa:</label>
		<s:select id="idPrograma" name="idPrograma" list="lstProgByEspecialista" listKey="idPrograma" listValue="%{descripcionCorta}" headerKey="-1" headerValue="-- Seleccione una opci�n --" tabindex="0" onchange="" value="%{}"/>
	</div>
</s:if>
<s:else>
	<div>
		<label class="left1">Programa:</label>
		<s:select id="idPrograma" name="idPrograma" list="lstProgByEspecialista" listKey="idPrograma" listValue="%{descripcionCorta}" headerKey="-1" headerValue="-- Seleccione una opci�n --"  onchange="recuperaCompByPrograma()"/>
	</div>
</s:else>