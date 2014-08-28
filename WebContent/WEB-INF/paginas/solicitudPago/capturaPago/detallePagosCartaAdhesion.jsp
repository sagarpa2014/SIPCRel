<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<script type="text/javascript" src="<s:url value="/js/solicitudPago.js" />"></script>
<div id="pagos">
	<s:form action="capturaPagoCartaAdhesion" method="post" enctype="multipart/form-data" accept-charset="utf-8" onsubmit="return chkCamposPagoCartaAdhesion();">
		<s:hidden id="numCampos" name="numCampos" value="%{numCampos}"/>
		<s:hidden id="criterioPago" name="criterioPago" value="%{criterioPago}"/>
		<s:hidden id="folioCartaAdhesion" name="folioCartaAdhesion" value="%{folioCartaAdhesion}"/>
	
		<s:if test="%{idPago!=null}">
			<div class="clear"></div>
			<br>
			<div class="pdf">
				<a href="<s:url value="/solicitudPago/consigueAtentaNota?idPago=%{idPago}"/>" title="Descargar Atenta Nota" ></a>
			</div>
			<br>
			<br>
		</s:if>
		
		<div class="borderBottom"><h1>CAPTURA DE PAGOS DE LA CARTA DE ADHESI�N</h1></div><br>
	
		<div id="dialogo_1"></div>
		<fieldset>
			<div>
				<label class="left1">Carta de Adhesi�n: </label>
				<font class="arial14boldVerde"><s:property value="folioCartaAdhesion"/></font>
			</div>
			<div class="clear"></div>
			<div>
				<label class="left1">Estatus: </label>
				<font class="arial14bold"><s:property value="estatusCA"/></font>
			</div>
			<div class="clear"></div>
			<div>
				<label class="left1">Participante: </label>
				<font class="arial14bold"><s:property value="nombreComprador"/></font>
			</div>
			<div class="clear"></div>
			<div>
				<label class="left1">Programa de Apoyos: </label>
				<font class="arial14bold"><s:property value="nombrePrograma"/></font>
			</div>
			<div class="clear"></div>
			<div>
				<label class="left1">Criterio de Pago: </label>
				<font class="arial14bold"><s:property value="descCriterioPago"/></font>		
			</div>
			<div class="clear"></div>	
			<div>
				<label class="left1">Incluye Fianza: </label>
				<font class="arial14bold"><s:property value="fianza"/></font>
			</div>
			<div class="clear"></div>	
			<div>
				<label class="left1">Cuenta Bancaria (CLABE):</label>
				<font class="arial14bold"><s:property value="clabe"/></font>
			</div>	
			<div class="clear"></div>
			<div>
				<label class="left1">Banco:</label>
				<font class="arial14bold"><s:property value="nombreBanco"/></font>
			</div>
			<div class="clear"></div>
			<div>
				<label class="left1">N�mero de Cuenta:</label>
				<font class="arial14bold"><s:property value="ctaBancaria"/></font>
			</div>
			<div class="clear"></div>
			<div>
				<label class="left1">Plaza:</label>
				<font class="arial14bold"><s:property value="plaza.numeroPlaza"/> - <s:property value="plaza.nombrePlaza"/></font>
	<!-- 			<s:textfield  disabled="true" maxlength="100" size="30"  value="%{plaza.numeroPlaza}"/>  -->
			</div>
			<div class="clear"></div>
			<div>
				<label class="left1">Sucursal:</label>
				<font class="arial14bold"><s:property value="sucursal"/></font>
			</div>
			<div class="clear"></div>
		</fieldset>
	    <fieldset>
			<legend>Captura de Pagos</legend>
			<div>
	            <label class="left1"><span class="requerido">*</span>Fecha Atenta Nota:</label>
	            <s:if test="%{fechaAtentaNota==null}">
	                           <s:textfield name="fechaAtentaNota" maxlength="10" size="10" id="fechaAtentaNota" readonly="true" cssClass="dateBox" />
	            </s:if>
	            <s:else>
	                           <s:textfield key="fechaAtentaNota" value="%{fechaAtentaNota}" name="fechaAtentaNota" maxlength="10" size="10" id="fechaAtentaNota" readonly="true" cssClass="dateBox" />
	            </s:else>
	            <img src="../images/calendar.gif" id="trg1" style="cursor: pointer;" alt="Seleccione la fecha" border="0" class="dtalign" title="Seleccione la fecha" />
			</div>
			<div class="clear"></div>
			<div>
			<s:if test="%{fianza!=null}">
	            <label class="left1"><span class="norequerido">*</span>Fianza</label>
	            	<s:checkbox id="tieneFianza"  name="tieneFianza" fieldValue="true" value="%{fianza}" />
			</s:if>
			</div>
			<div class="clear"></div>                           
			<table class="clean">
				<tr>
					<th class="clean">Estado</th>
					<th class="clean">Cultivo</th>
					<th class="clean">Variedad</th>
					<s:if test="criterioPago==1 || criterioPago==3">
						<th class="clean">Volumen Autorizado</th>
						<th class="clean">Volumen Apoyado</th>
						<th class="clean">Cuota Apoyo</th>
						<th class="clean">Volumen a Apoyar</th>
						<s:if test="criterioPago==3">
							<th class="clean">Etapa</th>
						</s:if>
					</s:if>
					<s:else>
						<th class="clean">Importe Autorizado</th>
						<th class="clean">Importe Apoyado</th>
						<th class="clean">Importe a Apoyar</th>
						<th class="clean">Etapa</th>
					</s:else>
				</tr>	
				<s:iterator value="lstDetallePagosCAEspecialistaV" var="resultado" status="itStatus">
					<tr>
						<td>
							<s:textfield disabled="true" maxlength="50" size="20" value="%{#resultado.estado}"/>
						</td>
						<td>
							<s:textfield disabled="true" maxlength="50" size="20" value="%{#resultado.cultivo}"/>
						</td>
						<td>
							<s:textfield disabled="true" maxlength="50" size="30" value="%{#resultado.variedad}"/>
						</td>
						<s:if test="idCriterioPago==1 || idCriterioPago==3">
							<td align="CENTER">
								<s:textfield disabled="true" id="volumenAut%{#itStatus.count}" name="volumenesAut" maxlength="20" size="15" cssClass="cantidad" value="%{getText('volumen',{#resultado.volumen})}"/>
							</td>
							<td align="CENTER">
								<s:textfield disabled="true" id="volumenApo%{#itStatus.count}" name="volumenesApo" maxlength="20" size="15" cssClass="cantidad" value="%{getText('volumen',{#resultado.volumenApoyado})}"/>
							</td>
							<td align="CENTER">
								$<s:textfield disabled="true" id="cuotaApoyo%{#itStatus.count}" name="cuotasApoyo" maxlength="15" size="10" cssClass="cantidad" value="%{getText('importe',{#resultado.cuota})}"/>
							</td>
							<td><s:textfield id="volumenApoyar%{#itStatus.count}" name="capVolumen[%{idAsiganacionCA}]" maxlength="20" size="15"  cssClass="cantidad" value="%{}" /></td>
							<s:if test="idCriterioPago==3">
								<td class="cEtapa">
									<s:select id="etapa%{#itStatus.count}" name="capEtapa[%{idAsiganacionCA}]"  headerKey="-1" onchange=""
										headerValue="Seleccione una opci�n"								
										list="#{'I':'I', 'II':'II', 'III':'III','IV':'IV'}" />
								</td>						
							</s:if>
						</s:if>
						<s:else>
							<td align="CENTER">
								$<s:textfield disabled="true" id="importeAut%{#itStatus.count}" name="ImportesAut" maxlength="20" size="15" cssClass="cantidad" value="%{getText('importe',{#resultado.importe})}"/>
							</td>
							<td align="CENTER">
								$<s:textfield disabled="true" id="importeApo%{#itStatus.count}" name="ImportesApo" maxlength="20" size="15" cssClass="cantidad" value="%{getText('importe',{#resultado.importeApoyado})}"/>
							</td>
							<td>$<s:textfield id="importeApoyar%{#itStatus.count}" name="capImporte[%{idAsiganacionCA}]" maxlength="20" size="15"  cssClass="cantidad" value="%{}" /></td>
							<td class="cEtapa">
								<s:select id="etapa%{#itStatus.count}" name="capEtapa[%{idAsiganacionCA}]"  headerKey="-1" onchange=""
									headerValue="Seleccione una opci�n"								
									list="#{'I':'I', 'II':'II', 'III':'III','IV':'IV'}" />
							</td>
						</s:else>						
					</tr>	
				</s:iterator>
			</table>
		</fieldset>
		<div class="accion">
			<s:submit  value="Vista Previa" cssClass="boton2" action="vistaPreviaAtentaNota" onclick="return chkCamposPagoCartaAdhesion();"/>
			<s:submit  value="Guardar" cssClass="boton2" />
			<a href='<s:url value="/solicitudPago/verCartaAdhesionAsignadasPagos?idPrograma=%{idPrograma}"/>' class="boton" title="">Regresar</a>
		</div>
	</s:form>
	<script type="text/javascript">
	    <!--
		 Calendar.setup({
		                inputField     :    "fechaAtentaNota",     
		                ifFormat       :    "%d/%m/%Y",     
		                button         :    "trg1",  
		                align          :    "Br",           
		                singleClick    :    true
		 });                           
		 //-->
	</script>
</div>	
<div id="vistaPrevia" class="vistaPrevia"></div>	