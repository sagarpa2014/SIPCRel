<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<s:hidden id="idIniEsquemaRelacion" name="idIniEsquemaRelacion" value="%{idIniEsquemaRelacion}"/>
<s:hidden id="idPerRel" name="idPerRel" value="%{idPerRel}"/>
<s:hidden id="claveBodega" name="claveBodega" value="%{claveBodega}"/>
<s:hidden id="estadoBodega" name="estadoBodega" value="%{estadoBodega}"/>
<s:hidden id="cultivoRelacion" name="cultivoRelacion" value="%{cultivoRelacion}"/>
<s:hidden id="idPgrCiclo" name="idPgrCiclo" value="%{idPgrCiclo}"/>
<s:if test="hasActionErrors()">
 	<s:include value="/WEB-INF/paginas/template/abrirMensajeDialogo.jsp"/>
</s:if>
<div id="dialogo_1"></div>
<fieldset>
	<s:include value="datosGeneralesRelCompras.jsp" />
</fieldset>

<s:if test="lstGpoCampoTerrestreRelDetalleV.size() > 0">
	<fieldset>
		<legend>Certificados</legend>		
		<display:table id="r"  name="lstGpoCampoTerrestreRelDetalleV"  list="lstGpoCampoTerrestreRelDetalleV"  pagesize="50" sort="list" requestURI="/relaciones/mostrarTerrestreByBodegaOPlantaProcesadora"  class="displaytag">
			<display:column  property="folio" title="Folio Tal�n"/>
			<display:column title="Consultar Registro" class="c"  headerClass="sortable">
				<a href='<s:url value="/relaciones/capturarRelaciones?registrar=1&idPerRel=%{idPerRel}&idIniEsquemaRelacion=%{idIniEsquemaRelacion}&cultivoRelacion=%{#attr.r.cultivoRegistro}&idPgrCiclo=%{idPgrCiclo}&folioTalonTerrestre=%{#attr.r.folio}&claveBodega=%{#attr.r.claveBodegaOpp}&estadoBodega=%{estadoBodega}"/>'  title="" onclick="">Consultar</a>
			</display:column>
			<s:if test="#attr.r.folioCartaAdhesion == null">
				<display:column title="Editar Registro" class="c" headerClass="sortable">
					<a href='<s:url value="/relaciones/capturarRelaciones?registrar=2&idPerRel=%{idPerRel}&idIniEsquemaRelacion=%{idIniEsquemaRelacion}&cultivoRelacion=%{#attr.r.cultivoRegistro}&idPgrCiclo=%{idPgrCiclo}&folioTalonTerrestre=%{#attr.r.folio}&claveBodega=%{#attr.r.claveBodegaOpp}&estadoBodega=%{estadoBodega}"/>'  title="" onclick="">Editar</a>
				</display:column>
				<display:column title="Borra Registro" class="c" headerClass="sortable">
					<a href='<s:url value="/relaciones/borrarRegistro?idPerRel=%{idPerRel}&idIniEsquemaRelacion=%{idIniEsquemaRelacion}&cultivoRelacion=%{#attr.r.cultivoRegistro}&idPgrCiclo=%{idPgrCiclo}&folioTalonTerrestre=%{#attr.r.folio}&claveBodega=%{#attr.r.claveBodegaOpp}&estadoBodega=%{estadoBodega}"/>'  title="" onclick="if (confirm('�Esta seguro en eliminar el registro?')){}else{return false;}">Borrar</a>
				</display:column>
			</s:if>
		</display:table>		
	</fieldset>
</s:if>
<s:else><div class="advertencia">No se encontraron registros</div></s:else>
<div class="derecha">
	<s:if test="lstGpoCampoTerrestreRelDetalleV.get(0).folioCartaAdhesion == null">
		<a href='<s:url value="/relaciones/capturarRelaciones?registrar=0&idPerRel=%{idPerRel}&idIniEsquemaRelacion=%{idIniEsquemaRelacion}&cultivoRelacion=%{cultivoRelacion}&idPgrCiclo=%{idPgrCiclo}&claveBodega=%{claveBodega}&estadoBodega=%{estadoBodega}"/>'  title="Registrar" >[Agregar Registro]</a>
	</s:if>
</div>
<div class="izquierda">
	<a href="<s:url value="listRelacionesCapturadas?idPerRel=%{idPerRel}&idIniEsquemaRelacion=%{idIniEsquemaRelacion}"/>" class="" title="" >&lt;&lt; Regresar</a>
</div>
<script>
</script>