<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" src="<s:url value="/js/solicitudPago.js" />"></script>
<s:hidden id="folioCartaAdhesion" name="folioCartaAdhesion" value="%{folioCartaAdhesion}"/>
<s:hidden id="doctosObservados" name="doctosObservados" value="%{doctosObservados}"/>
<s:hidden id="idExpedientesTotal" name="idExpedientesTotal" value="%{idExpedientesTotal}"/>
<s:hidden id="idExpedientesObservados" name="idExpedientesObservados" value="%{idExpedientesObservados}"/>
<s:hidden id="certDepositoOAlmacenamiento" name="certDepositoOAlmacenamiento" value="%{certDepositoOAlmacenamiento}"/>
<div class="clear"></div>
<div class="borderBottom" style="text-align:center"><h1>Entrega de Documentos</h1></div><br>	
	<table  class="clean" width="100%">	
		<s:set name="bandTipoC">0</s:set>
		<s:set name="tipoConstancia"><s:property value="tipoConstancia"/></s:set>
		<s:iterator value="lstsExpedientesSPCartaAdhesionV" var="resultado"  status="itStatus">
			<s:if test="%{idExpediente==1}"><!-- ******************************************* ESCRITO DE ENTREGA DE DOCUMENTOS ******************************************* -->
					<tr>
						<td>
							<label class=""><span class="requerido">*</span><s:property value="%{expediente}"/></label>
						</td>
						<td>
							<s:if test="(estatusCA == 3)">
								<s:file name="doc%{idExpediente}" id="doc%{idExpediente}"/>
								<s:hidden id="docRequerido%{idExpediente}" name="docRequerido" value="%{}"/>								
				 			</s:if>
				 			<s:else>
								<a href="<s:url value="/devuelveArchivoByRuta?rutaCompleta=%{rutaDocumento}"/>" title="Descargar Archivo">Descargar Archivo</a>
							</s:else>							
						</td>
					</tr>
					<tr>
						<td>
							<label class=""><span class="requerido">*</span>Fecha Documento</label>
						</td>
						<td>							
							<s:if test="%{fechaDocumento==null}" >
								<s:textfield name="fechaDocEntDoctos" maxlength="10" size="10" id="fechaDocEntDoctos" readonly="true" cssClass="dateBox"/>
								<img src="../images/calendar.gif" id="trg1" style="cursor: pointer;" alt="Seleccione la fecha" border="0" class="dtalign" title="Seleccione la fecha" />
								<script type="text/javascript">
									<!--
										Calendar.setup({
											inputField     :    "fechaDocEntDoctos",     
											ifFormat       :    "%d/%m/%Y",     
											button         :    "trg1",  
											align          :    "Br",           
											singleClick    :    true
										});	
									//-->
								</script>		
							</s:if>
							<s:else>
								<font class="arial12bold"><s:text name="fecha"><s:param value="%{fechaDocumento}"/></s:text></font>
							</s:else>								
						</td>
					</tr>
					<tr>
						<td><label class=""><span class="requerido">*</span>Fecha Acuse</label></td>
						<td>								
							<s:if test="%{fechaAcuse==null}" >
								<s:textfield name="fechaAcuseEntDoctos" maxlength="10" size="10" id="fechaAcuseEntDoctos" readonly="true" cssClass="dateBox" />
								<img src="../images/calendar.gif" id="trg2" style="cursor: pointer;" alt="Seleccione la fecha" border="0" class="dtalign" title="Seleccione la fecha" />
								<script type="text/javascript">
									<!--
									Calendar.setup({
										inputField     :    "fechaAcuseEntDoctos",     
										ifFormat       :    "%d/%m/%Y",     
										button         :    "trg2",  
										align          :    "Br",           
										singleClick    :    true
									});									   
									//-->
								</script>
							</s:if>
							<s:else>
								<font class="arial12bold"><s:text name="fecha"><s:param value="%{fechaAcuse}"/></s:text></font>
							</s:else>						
						</td>
					</tr>
				</s:if><!-- ******************************************* TERMINA ESCRITO DE ENTREGA DE DOCUMENTOS ******************************************* -->
				<s:if test="%{idExpediente!=1 && idExpediente!=2}">
					<!-- ******************************************* DIVISORES DE BLOQUES ******************************************* -->
					<s:if test="%{idExpediente==3}">
						<tr>
							<td colspan="6">
								<div class="borderBottom" style="text-align:center"><h1>Solicitud de Pago del Apoyo</h1></div><br>							
							<td>
						</tr>
					</s:if>
					<s:if test="%{idExpediente==7}">
						<tr>
							<td colspan="6">
								<div class="borderBottom" style="text-align:center"><h1>Dictamen Contable de Auditor Externo</h1></div><br>							
							<td>
						</tr>
					</s:if>
					<s:if test="%{idExpediente==8}">
						<tr>
							<td colspan="6">
								<div class="borderBottom" style="text-align:center"><h1>Relaciones</h1></div><br>							
							<td>
						</tr>
					</s:if>
					<!-- ******************************************* TERMINA DIVISORES DE BLOQUES ******************************************* -->
					<s:if test="%{(idExpediente==10 || idExpediente==34) && #bandTipoC!=1}">
						<s:set name="bandTipoC">1</s:set>
						<s:if test="%{#tipoConstancia!=0}">
							<script>
								$(document).ready(function() {										
									$("#tipoConstanciaDiv input[type='radio']").attr('disabled','disabled');
								});	 	
							</script>
						</s:if>
						<tr>
							<td colspan="6">							
								<div class="inline" id="tipoConstanciaDiv">
									<label class="left1"><span class="norequerido" id="spTC">*</span>Tipo de Constancia:</label>
									<s:if test="certDepositoOAlmacenamiento==1">
										<s:radio label="" id="tipoConstanciac" name="tipoConstancia" list="#{1:'CERTIFICADOS DE DEP&Oacute;SITO'}" value="%{#tipoConstancia}" onclick="validarTipoConstancia(this.value);"/>									
									</s:if>
									<s:elseif test="certDepositoOAlmacenamiento==2">
										<s:radio label="" id="tipoConstanciac" name="tipoConstancia" list="#{2:'CONSTANCIA DE ALMACENAMIENTO'}" value="%{#tipoConstancia}" onclick="validarTipoConstancia(this.value);"/>
									</s:elseif>
									<s:else>
										<s:radio label="" id="tipoConstanciac" name="tipoConstancia" list="#{1:'CERTIFICADOS DE DEP&Oacute;SITO',2:'CONSTANCIA DE ALMACENAMIENTO', 3:'AMBOS'}" value="%{#tipoConstancia}" onclick="validarTipoConstancia(this.value);"/>
									</s:else>
									
								</div>							
							<td>
						</tr>
					</s:if>				
					<tr> <!-- ******************************************* ETIQUETAS DE LOS DOCUMENTOS ******************************************* -->		
						<td>
							<label class="">
								<s:if test="estatusCA == 3">
									<s:if test="%{idExpediente == 1}">
										<span class="requerido" id="sp<s:property value="%{idExpediente}"/>">*</span>
									</s:if>
									<s:else><span class="norequerido" id="sp<s:property value="%{idExpediente}"/>">*</span></s:else>
								 </s:if>
								 <s:else>
								 	<span class="norequerido" id="sp<s:property value="%{idExpediente}"/>">*</span>
								 </s:else>
								<s:if test="estatusCA == 3">													
									<s:if test="%{idExpediente == 5}">
										<s:if test="%{anexo32DyaCapturado == true}">
											<a href="<s:url value="/solicitudPago/capAnexo32D?folioCartaAdhesion=%{folioCartaAdhesion}&idExpSPCartaAdhesion=%{idExpSPCartaAdhesion}"/>" title="Anexo 32-D"><s:property value="%{expediente}"/></a>
										</s:if>
										<s:else>
											<s:property value="%{expediente}"/>
										</s:else>										
									</s:if>
									<s:elseif test="%{idExpediente == 4}">
										<s:if test="%{edoCuentaYaCapturado == true}">
											<a href="<s:url value="/solicitudPago/capEstadoCuenta?folioCartaAdhesion=%{folioCartaAdhesion}"/>" title="Estado de Cuenta"><s:property value="%{expediente}"/></a>
										</s:if>
										<s:else>
											<s:property value="%{expediente}"/>
										</s:else>										
									</s:elseif>
									<s:else>
										<s:property value="%{expediente}"/>
									</s:else>
								</s:if>
								<s:else>
									<s:if test="%{idExpediente == 3}">
										<a href="<s:url value="/solicitudPago/capSolicitudPago?folioCartaAdhesion=%{folioCartaAdhesion}&idExpSPCartaAdhesion=%{idExpSPCartaAdhesion}"/>" title="Solicitud Pago"><s:property value="%{expediente}"/></a>	
									</s:if>
									<s:elseif test="%{idExpediente == 4}">
										<a href="<s:url value="/solicitudPago/capEstadoCuenta?folioCartaAdhesion=%{folioCartaAdhesion}"/>" title="Estado de Cuenta"><s:property value="%{expediente}"/></a>
									</s:elseif>
									<s:elseif test="%{idExpediente == 5}">
										<a href="<s:url value="/solicitudPago/capAnexo32D?folioCartaAdhesion=%{folioCartaAdhesion}&idExpSPCartaAdhesion=%{idExpSPCartaAdhesion}"/>" title="Anexo 32-D"><s:property value="%{expediente}"/></a>
									</s:elseif>
									<s:elseif test="%{idExpediente == 7}">
										<a href="<s:url value="/solicitudPago/capAuditorSolPago?folioCartaAdhesion=%{folioCartaAdhesion}&tipoDocumentacion=1"/>" title="Dictamen Contable del Auditor"><s:property value="%{expediente}"/></a>
									</s:elseif>
									<s:elseif test="%{idExpediente == 10}">
										<a href="<s:url value="/solicitudPago/capCertificadoDeposito?folioCartaAdhesion=%{folioCartaAdhesion}"/>" title="Relaci�n de Certificados"><s:property value="%{expediente}"/></a>
									</s:elseif>
									<s:elseif test="%{idExpediente == 34}">
										<a href="<s:url value="/solicitudPago/capConstanciasAlmacenamiento?folioCartaAdhesion=%{folioCartaAdhesion}"/>" title="Constancia de Almacenamiento"><s:property value="%{expediente}"/></a>
									</s:elseif>
									<s:else>
										<s:property value="%{expediente}"/>
									</s:else>
								</s:else>								
							</label>
						</td><!-- *******************************************TERMINA ETIQUETAS DE LOS DOCUMENTOS ******************************************* -->						
						<!-- ******************************************* DOCUMENTOS ******************************************* -->
						<s:if  test="sustituirArchivo==true">							
							<td>
								<s:if test="%{rutaDocumento!=null && rutaDocumento !=''}">
									<a href="<s:url value="/devuelveArchivoByRuta?rutaCompleta=%{rutaDocumento}"/>" title="Descargar Archivo">Descargar Archivo</a>
								</s:if>
							</td>																					
							<td>									
								<label class=""><span class="norequerido">*</span>Cargar Documento</label>
							</td>
							<td><s:file name="doc%{idExpediente}" id="doc%{idExpediente}"/></td>
							<td>&nbsp;</td> 													
						</s:if>	
						<s:elseif  test="alcanceDocumentacion==true">
							<td>
								<s:if test="%{rutaDocumento!=null && rutaDocumento !=''}">
									<a href="<s:url value="/devuelveArchivoByRuta?rutaCompleta=%{rutaDocumento}"/>" title="Descargar Archivo">Descargar Archivo</a>
								</s:if>
							</td>
	 						<td>
 								&nbsp;
 							</td>
 							<td>
 								<s:if test="observacion==true">
 									<input id="capObsExpediente<s:property value="%{idExpediente}"/>" name="capObsExpediente" value="<s:property value="%{idExpediente}"/>" type="checkbox"  class="ck"  checked="checked"/>
 								</s:if>
 								<s:else>
 									<input id="capObsExpediente<s:property value="%{idExpediente}"/>" name="capObsExpediente" value="<s:property value="%{idExpediente}"/>" type="checkbox"  class="ck"  />
 								</s:else>
 							</td>
						</s:elseif>
						<s:elseif test="%{estatusCA==3}">
							<td>
								<s:if test="%{rutaDocumento!=null && rutaDocumento !=''}">
									<a href="<s:url value="/devuelveArchivoByRuta?rutaCompleta=%{rutaDocumento}"/>" title="Descargar Archivo">Descargar Archivo</a>		
									<s:if test="%{idExpediente == 4}">
										<s:hidden id="edoCuentaYaCapturado" name="edoCuentaYaCapturado" value="%{edoCuentaYaCapturado}"/>
									</s:if>						
								</s:if>
								<s:else>&nbsp;</s:else>
							</td>
							<td>
								<s:file name="doc%{idExpediente}" id="doc%{idExpediente}"/>
								<s:hidden id="docRequerido%{idExpediente}" name="docRequerido" value="%{}"/>
							</td>
							<td>
								<label class=""><span class="norequerido">*</span>Observaci&oacute;n</label>
							</td>
							<td>
								<input id="capObsExpediente<s:property value="%{idExpediente}"/>" name="capObsExpediente" value="<s:property value="%{idExpediente}"/>" type="checkbox"  class="ck" onclick="verificaObservacion(this.value);"/>								
							</td>									
						</s:elseif>
						<s:elseif  test="estatusCA==4">
							<td>
								<s:if test="observacion==true">
									<label class="">Documento Corregido</label>
								</s:if>
								<s:else><a href="<s:url value="/devuelveArchivoByRuta?rutaCompleta=%{rutaDocumento}"/>" title="Descargar Archivo">Descargar Archivo</a></s:else>
							</td>
							<td>
								<s:if test="observacion==true">
									<s:file name="doc%{idExpediente}" id="doc%{idExpediente}"/>
								</s:if>
								<s:else>&nbsp;</s:else>
							</td>
							<td>
								<s:if test="observacion==true">
									<label class=""><span class="norequerido">*</span>Observaci&oacute;n</label>
								</s:if>
								<s:else>&nbsp;</s:else>
							</td>
							<td>
								<s:if test="observacion==true">
									<input id="capObsExpediente<s:property value="%{idExpediente}"/>" name="capObsExpediente" value="<s:property value="%{idExpediente}"/>" type="checkbox"  class="ck"  checked="checked" onclick="verificaObservacion(this.value);" />
								</s:if>
								<s:else>&nbsp;</s:else>									
							</td>
						</s:elseif>
						<s:elseif  test="estatusCA==5">
							<td>
								<a href="<s:url value="/devuelveArchivoByRuta?rutaCompleta=%{rutaDocumento}"/>" title="Descargar Archivo">Descargar Archivo</a>
							</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>	
						</s:elseif>
						<s:elseif test="estatusCA==6 || estatusCA==8">
							<td>
								<s:if test="%{rutaDocumento!=null && rutaDocumento !=''}">
									<a href="<s:url value="/devuelveArchivoByRuta?rutaCompleta=%{rutaDocumento}"/>" title="Descargar Archivo">Descargar Archivo</a>
								</s:if>
								<s:else>&nbsp;</s:else>
							</td>
							<td>								
								<s:if test="%{rutaDocumento!=null && rutaDocumento !=''}">
									&nbsp;
								</s:if>
								<s:else>
									<s:file name="doc%{idExpediente}" id="doc%{idExpediente}"/>
									<s:hidden id="docRequerido%{idExpediente}" name="docRequerido" value="%{}"/>
								</s:else>
															
							</td>
							<td><label class=""><span class="norequerido">*</span>Observaci&oacute;n</label></td>
							<td>
								<s:if test="observacion==true">
									<input id="capObsExpediente<s:property value="%{idExpediente}"/>" name="capObsExpediente" value="<s:property value="%{idExpediente}"/>" type="checkbox"  class="ck"  checked="checked" onclick="verificaObservacion(this.value);"/>
								</s:if>
								<s:else>
									<input id="capObsExpediente<s:property value="%{idExpediente}"/>" name="capObsExpediente" value="<s:property value="%{idExpediente}"/>" type="checkbox"  class="ck" onclick="verificaObservacion(this.value);"/>
								</s:else>						
								
							</td>	
						</s:elseif>										
					</tr>
					<!-- DATOS ADICIONALES EN DOCUMENTOS Solicitud de Apoyo (3), Dictamen Contable (7) -->	
					<s:if test="%{idExpediente == 7}">
						<s:if test="lstAuditorSolPagoV.size() > 0">	
							<tr>	
								<td colspan="6">
									<s:include value="auditorVolumen.jsp"/>
								</td>
							</tr>
						</s:if>
						
					</s:if>
					<s:if test="%{idExpediente == 3}">
						<s:if test="%{idCriterioPago == 1 || idCriterioPago == 3}">
							<tr class="datosVolumen">
								<td>
									<label class=""><span class="norequerido" id="spv">*</span>Volumen Solicitado a Apoyar</label>
								</td>
								<td class="cVolumen">
									<s:if test="%{estatusCA==5 || alcanceDocumentacion== true || (estatusCA == 4 && observacion==false)}">
										<s:textfield id="v3" name="volumen" value="%{getText('volumenSinComas',{volumen})}" maxlength="14" size="20" disabled="true"/>
									</s:if>
									<s:else>
										<s:if test="volumen!=null">
											<s:textfield id="v3" name="volumen" value="%{getText('volumenSinComas',{volumen})}" maxlength="14" size="20"/>
										</s:if>
										<s:else>
											<s:textfield id="v3" name="volumen" value="%{}" maxlength="14" size="20"/>
										</s:else>
									</s:else>
									<script>
										$(document).ready(function() {		
											$(".cVolumen").change(function() {	
										        var v=$('#v3').val();
										        var patron =/^\d{1,10}((\.\d{1,3})|(\.))?$/;
										    	if(v=="" || v==null){
										    		return false;
										    	}
										    	if(!v.match(patron)){
										    		$('#dialogo_1').html('El valor del registro es inv�lido, se aceptan hasta 10 digitos a la izquierda y 3 m�ximo a la derecha');
										       		abrirDialogo();
										       		return false;
										    	}
										    }); 
										});
									</script>
								</td>
							</tr>						
						</s:if>						
					</s:if>
					<s:if test="%{idExpediente == 5}">
						<s:hidden id="anexo32DyaCapturado" name="anexo32DyaCapturado" value="%{anexo32DyaCapturado}"/>						
						<tr class="datosAnexo5">						
							<td>
								<label class=""><span class="norequerido" id="spFE">*</span>Fecha de Expedici&oacute;n Anexo 32-D</label>
							</td>
							<td>								
								<s:if test="%{fechaExpedicionAnexo==null}" >
									<s:textfield name="fechaExpedicion" maxlength="10" size="10" id="fechaExpedicion" readonly="true" cssClass="dateBox" />
									<img src="../images/calendar.gif" id="trgFechaExpedicion" style="cursor: pointer;" alt="Seleccione la fecha" border="0" class="dtalign" title="Seleccione la fecha" />
									<script type="text/javascript">
										<!--
											Calendar.setup({
												inputField     :    "fechaExpedicion",     
												ifFormat       :    "%d/%m/%Y",     
												button         :    "trgFechaExpedicion",  
												align          :    "Br",           
												singleClick    :    true
											});											   
											//-->
									</script>
								</s:if>
								<s:else>
									<font class="arial12bold"><s:text name="fecha"><s:param value="%{fechaExpedicionAnexo}"/></s:text></font>
								</s:else>																						
							</td>							
						</tr>						
					</s:if>
				</s:if>	<!-- end idExpediente!=1, idExpediente!=2 -->
				<div class="clear"></div>			
			</s:iterator>	
		</table>
		<div class="clear"></div>	
		<s:if test="estatusCA!=5 && estatusCA!=4">
			<div class="borderBottom"style="text-align:center"><h1>Acciones</h1></div><br>		
			<div class="inline">
				<label class="left1"><span class="norequerido">*</span>Seleccione una opci&oacute;n:</label>
				<s:radio label="" name="habilitaAccionSP" list="#{2:'TRAMITAR OFICIO DE OBSERVACIONES Y PAGO',1:'TRAMITAR PAGO SIN OBSERVACIONES:'}" value="%{}" onclick="recuperaDocRequeridos();"/>
			</div>	
		</s:if>		
		<div class="clear"></div>	
		<div id="recuperaDocRequeridosYhabilitaOficioObs"></div>
		<s:if test="estatusCA==4 && alcanceDocumentacion==false">
			<s:include value="datosOficioObservacion.jsp"/>
		</s:if>
		<s:if test="alcanceDocumentacion==true">
			<s:include value="datosOficioObservacion.jsp"/>
		</s:if>
		<div id="respuestaOficio">
			<s:if test="(estatusCA==4 && alcanceDocumentacion==false) || estatusCA==5 ">
				<s:include value="recuperaOficioObservacion.jsp"/>
				<s:if test="estatusCA==5"></s:if>
	 			<s:include value="recuperaOficioRespuestaObservacion.jsp"/>
	 		</s:if>	
 		</div>

		<s:if test="deshabilitaAccion != true">
			<s:if test="estatusCA!=5 || sustituirArchivo ==true ">
				<div class="accion">
					<s:submit  value="Guardar" cssClass="boton2"/>
					<a href="<s:url value="/solicitudPago/listarPrograma"/>" class="boton" title="">Cancelar</a>
				</div>
			</s:if>
		</s:if>
		
	</fieldset>
	<script>
		$(document).ready(function(){
			if($("#capObsExpediente9").length){
				$("#capObsExpediente9").attr('disabled','disabled');
			}
			 
		}); 
	</script>

