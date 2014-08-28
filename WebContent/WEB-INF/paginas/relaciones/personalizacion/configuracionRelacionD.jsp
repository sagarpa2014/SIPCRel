<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" src="<s:url value="/js/relaciones.js" />"></script>
<s:if test="bandConfiguracionYaRegistrada==true">
	<div class="msjError">La configuraci&oacute;n para el esquema y tipo de relaci&oacute;n ya se encuentra registrado, por favor verifique</div>
</s:if>
<s:else>
	<s:if test='registrar==0'>
		<br>
		<div class="izquierda">
			<a href="#" class="boton" title="" id="valoresDefault" onclick="poneValoresDefault();">Poner Valores por Default</a>
		</div>
		<div  class="izquierda">&nbsp;</div>
		<div class="izquierda">
			<a href="#" class="boton" title="" id="valoresDefault" onclick="quitarValoresDefault();">Limpiar Valores por Default</a>
		</div>	
	</s:if>
	<div class="clear"></div>			
	<!-- Encabezado de la relacion -->
	<div class="borderBottom" style="text-align:center"><h1>Encabezado de la Relaci&oacute;n</h1></div><br>				
	<table>
		<tr>
			<th>Campos</th>
			<th>Posici&oacute;n</th>		
		</tr>
		<s:hidden id="sizeArrayEnc" name="" value="%{lstGpoCampoRelEncabezadoV.size()}"/>
		<s:iterator value="lstGpoCampoRelEncabezadoV" var="resultado"  status="itStatus">
			<tr>
				<td><s:property value="%{campo}"/></td>
				<td align="center"><s:textfield id="posicionEnc%{#itStatus.count}" name="posicionEnc[%{idCampoRelacion}]"   maxlength="500" size="1"  value="%{posicionCampo}" cssClass="classPEnc" onchange="validaGrupoEnc(this.value, %{#itStatus.count});"/></td>
				<s:hidden id="grEnc%{#itStatus.count-1}"  name="" value="%{idGrupo}"/>
				<s:hidden id="grEncValor%{#itStatus.count-1}"  name="" value="%{campo}"/>
			</tr>		
		</s:iterator>
	</table>
	<!-- Detalle de la relacion -->
	<div class="borderBottom" style="text-align:center"><h1>Detalle de la Relaci&oacute;n</h1></div><br>
	<table>
		<tr>
			<th>Grupo</th>
			<th>Posici&oacute;n</th>
			<th>Visible</th>
			<th>Campos</th>
			<th>Posici&oacute;n</th>		
			<th>Campo Opcional</th>		
		</tr>
		<s:hidden id="sizeArrayCampoDet" name="" value="%{lstGpoCampoRelDetalleV.size()}"/>
		<s:iterator value="lstGpoCampoRelDetalleV" var="resultado"  status="itStatus">
			<tr>
				<s:if test='#temporal!=idGrupo'>
					<td><s:property value="grupo"/></td>
					<td align="center">
						<s:textfield id="posicionGrupoDetalle%{#itStatus.count}" name="posicionGrupoDetalle[%{idGrupo}]"  maxlength="1" size="1"  value="%{posicionGrupo}" cssClass="pgd" onchange="setValHiddenGrupo(this.value, %{#itStatus.count}, %{idGrupo}, '%{grupo}');"/>
						<s:if test='registrar==0'>
							<s:hidden id="idGrupo%{#itStatus.count}"  name="idGrupos" value=""/>
							<s:hidden id="nombreGrupos%{#itStatus.count}"  name="nombreGrupos" value=""/>
							<s:hidden id="idGrupoTotal%{#itStatus.count}"  name="idGrupoTotalName" value="%{idGrupo}"/> <!-- Default -->
							<s:hidden id="" name="count" value="%{#itStatus.count}"/> <!-- Default -->
						</s:if>
						<s:else>
							<s:if test='posicionGrupo!=null'>
								<s:hidden id="idGrupo%{#itStatus.count}"  name="idGrupos" value="%{idGrupo}"/>
								<s:hidden id="nombreGrupos%{#itStatus.count}"  name="nombreGrupos" value="%{grupo}"/>
							</s:if>
							<s:else>
								<s:hidden id="idGrupo%{#itStatus.count}"  name="idGrupos" value=""/>
								<s:hidden id="nombreGrupos%{#itStatus.count}"  name="nombreGrupos" value=""/>
							</s:else>							
						</s:else>
						
					</td>
					<td>
						<s:if test="registrar==0 || visible != true ">			
							<input id="visible<s:property value="%{idGrupo}"/>" name="grupoVisible" value="<s:property value="%{idGrupo}"/>" type="checkbox"  class="ck"/>
						</s:if>
						<s:else>
							<input id="visible<s:property value="%{idGrupo}"/>" name="grupoVisible" value="<s:property value="%{idGrupo}"/>" type="checkbox" checked="checked"/>
						</s:else>
						
					</td>			
				</s:if>
				<s:else>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>				
				</s:else>	
				<td><s:property value="%{campo}"/></td>
				<td align="center">					
					<s:set name="clase">g<s:property value="%{idGrupo}"/></s:set>					
					<s:textfield id="posicionDet%{#itStatus.count}" name="posicionDet[%{idCampoRelacion}]"  cssClass="%{#clase}" maxlength="1" size="1"  value="%{posicionCampo}" onchange="setValHiddenCampo(this.value, %{#itStatus.count}, %{idGrupo}, '%{grupo}');"/>
					<s:if test='registrar==0'>
						<s:hidden id="idGrupoEnCampo%{#itStatus.count}"  name="" value=""/>
					</s:if>
					<s:else>
						<s:if test='posicionCampo!=null'>
							<s:hidden id="idGrupoEnCampo%{#itStatus.count}"  name="" value="%{idGrupo}"/>
						</s:if>
						<s:else>
							<s:hidden id="idGrupoEnCampo%{#itStatus.count}"  name="" value=""/>
						</s:else>
					</s:else>
				</td>
				<td style="text-align: center;">
					<s:if test="registrar==0 || opcional != true ">			
						<input id="opcional<s:property value="%{idCampoRelacion}"/>" name="camposOpcional" value="<s:property value="%{idCampoRelacion}"/>" type="checkbox"  class=""/>
					</s:if>
					<s:else>
						<input id="opcional<s:property value="%{idCampoRelacion}"/>" name="camposOpcional" value="<s:property value="%{idCampoRelacion}"/>" type="checkbox" checked="checked"/>
					</s:else>				
				</td>
			</tr>
			<s:set name="temporal">
				<s:property value="idGrupo" />
			</s:set>
		</s:iterator>
	</table>
</s:else>