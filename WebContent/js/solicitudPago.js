$(document).ready(function(){
	$.ajaxSetup({
		'beforeSend' : function(xhr) {
			try{
				xhr.overrideMimeType('text/html; charset=iso-8859-1');
			}catch(e){}
		}});
});
/********INICIALIZACION DE SOLICITUD DE PAGO *****/
function chkCamposInicializacionSP(){	
	var registrar = $('#registrar').val();
	if(registrar == 0){
		var idPrograma = $('#idPrograma').val();
		if(idPrograma == -1){
			$('#dialogo_1').html('Seleccione el programa');
			abrirDialogo();
			return false;
		}else{
			if ($('#errorProgramaYaExisteConfDoc').length){
				var errorProgramaYaExisteConfDoc = $('#errorProgramaYaExisteConfDoc').val();
				if(errorProgramaYaExisteConfDoc!=0){
					$('#dialogo_1').html('La inicializaci�n del programa ya se encuentra registrado, por favor verifique');
			   		abrirDialogo();
			 		return false;	
				}
			}
		}
	}
	
	var selectedItems = new Array();
	$(".ck:checkbox:checked").each(function() {selectedItems.push($(this).val());});
	if (selectedItems.length == 0){ 
	    $('#dialogo_1').html('Debe marcar al menos un elemento en el apartado de "Documentaci�n".');
		abrirDialogo();
	    return false;
	}
	
	if($(".check_todos1").is(":checked")) {
		selectedItems = new Array();
		$(".ck1:checkbox:checked").each(function() {selectedItems.push($(this).val());});
		var numDocFianza = $('#numDocFianza').val();
		if(selectedItems.length != numDocFianza){
			$('#dialogo_1').html('Debe marcar todos los documentos de la fianza');
			abrirDialogo();
		    return false;
		}
	}
	if(registrar == 3){
		$("#idPrograma").removeAttr('disabled');
	}
	
}

function verificarProgramaConfigurado(){
	var idPrograma = $('#idPrograma').val();
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "verificarProgramaConfigurado",
		   data: "idPrograma="+idPrograma,
		   success: function(data){
				$('#verificarProgramaConfigurado').html(data).ready(function () {
					$("#verificarProgramaConfigurado").fadeIn('slow');
				});
		   }
		});//fin ajax
}

/********ASIGNACION DE CARTA DE ADHESION *****/
$(document).ready(function() {
	$("#numCamposACA").keyup(function(event){
		var numCamposACA = $('#numCamposACA').val();
		var idPrograma = $('#idPrograma').val();
		if(numCamposACA == null || numCamposACA == ""){
			return false;
		}else{
			var patron =/^\d{1,2}$/;
			if(!numCamposACA.match(patron)){
				$('#dialogo_1').html('El valor del N�mero de Carta Adhesi�n es incorrecto, por favor verifique');
				abrirDialogo();
				return false;
			} 
			
		}
		
		$.ajax({
			   async: false,
			   type: "POST",
			   url: "agregarCamposAsignacionCA",
			   data: "numCamposACA="+numCamposACA+
		   		"&idPrograma="+idPrograma, 
			   success: function(data){
					$('#agregarCamposAsignacionCA').html(data).ready(function () {
						$("#agregarCamposAsignacionCA").fadeIn('slow');
						
					});
			   }
			});//fin ajax
		
		
		
	});//termina keyup numCampos	
});	 

function chkCamposAsignarCartaAdhesion(){
	var registrar = $('#registrar').val();
	if(registrar == 0){
		var dia =0;
		var mes =0;
		var anio =0;
		/****PROGAMA****/
		var idPrograma = $('#idPrograma').val();
		if(idPrograma == -1){
			$('#dialogo_1').html('Seleccione el programa');
			abrirDialogo();
			return false;
		}
		/****DOCUMENTO CARTA ADHESION****/
		var docCA = $('#docCA').val();
		if(docCA == null || docCA==""){
			$('#dialogo_1').html('Seleccione el archivo de la carta de adhesi�n');
			abrirDialogo();
			return false;
		}
		/*****FECHA DOCUMENTO CARTA DE ADHESION****/
		var fechaDocCA = $('#fechaDocCA').val();
		var fechaDocCATemp = 0;
		if(fechaDocCA == null || fechaDocCA==""){
			$('#dialogo_1').html('Seleccione la fecha del documento de la carta de adhesi�n');
			abrirDialogo();
			return false;
		}else{
			dia = fechaDocCA.substring(0,2);
			mes = fechaDocCA.substring(3,5);
			anio = fechaDocCA.substring(6,10);
			fechaDocCATemp = anio+""+""+mes+""+dia;
		}
		/*****FECHA ACUSE CARTA DE ADHESION****/
		var fechaAcuseCA = $('#fechaAcuseCA').val();
		var fechaAcuseCATemp = 0;
		if(fechaAcuseCA == null || fechaAcuseCA==""){
			$('#dialogo_1').html('Seleccione la fecha del acuse de la carta de adhesi�n');
			abrirDialogo();
			return false;
		}else{
			dia = fechaAcuseCA.substring(0,2);
			mes = fechaAcuseCA.substring(3,5);
			anio = fechaAcuseCA.substring(6,10);
			fechaAcuseCATemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaAcuseCATemp) < fechaDocCATemp){
				$('#dialogo_1').html('La fecha del acuse de la solicitud no debe ser menor a la fecha del documento');
				abrirDialogo();
				return false;
			}
		}
		/*****NO OFICIO CARTA ADHESION****/
		var noOficioCA = $('#noOficioCA').val();
		if(noOficioCA == null || noOficioCA==""){
			$('#dialogo_1').html('Capture el n�mero de oficio de la carta de adhesi�n');
			abrirDialogo();
			return false;
		}
		/*****NO CAMPOS ASIGANCION CAMPOS****/
		var numCamposACA = $('#numCamposACA').val();
		console.log("numCamposACA"+numCamposACA);
		if(numCamposACA == null || numCamposACA == ""){
			return false;
		}else{
			var patron =/^\d{1,2}$/;
			if(!numCamposACA.match(patron)){
				$('#dialogo_1').html('El valor del N�mero de Carta Adhesi�n es incorrecto, por favor verifique');
				abrirDialogo();
				return false;
			} 
			
		}	
		var i=0;
		var j=0;	
		var idEspecialista = 0;
		var folioCartAdhesion = "";
		var tmpIdEspecialista = 0;
		var tmpfolioCartAdhesion = 0;
		var fechaFirmaCA = "";
		var fechaFirmaCATmp = "";
		for (i=1;i<parseInt(numCamposACA)+1;i++){
			folioCartAdhesion = $('#ca'+i).val();
			idEspecialista = $('#esp'+i).val();
			if(folioCartAdhesion == "" || idEspecialista == -1 ){
	   			$('#dialogo_1').html('Capture los valores del registro '+i+', de la asignaci�n de cartas a especialistas');
		   		abrirDialogo();
		 		return false;
	   		 }else{
	   			 /*Valida que las fechas de la firma de carta de adhesion no sea mayor a la fecha del oficio*/
	   			fechaFirmaCA = $('#fechaFirmaCA'+i).val();
	   			console.log("fechaFirmaCA "+fechaFirmaCA);
	   			 dia = fechaFirmaCA.substring(0,2);
	   			 mes = fechaFirmaCA.substring(3,5);
	   			 anio = fechaFirmaCA.substring(6,10); 
	   			fechaFirmaCATmp = anio+""+""+mes+""+dia;
	   			console.log("fechaFirmaCATmp "+fechaFirmaCATmp);
	   			console.log("fechaDocCATemp "+fechaDocCATemp);
	   			if(parseInt(fechaDocCATemp) < fechaFirmaCATmp){
	   				$('#dialogo_1').html('La fecha del oficio es menor a la fecha de firma del registro '+i+' por favor verifique');
	   				console.log("La fecha si es menor "+fechaDocCATemp +"-"+fechaFirmaCATmp);
	   				abrirDialogo();
	   				return false;
	   			} else{
	   				console.log("La fecha no es menor ");
	   			} 
	   			  
	   			 
	   			 /*Valida que la informaci�n no se encuentre repetido*/
	   			for (j=1;j<parseInt(numCamposACA)+1;j++){
	   				if(i!=j){
	   					tmpfolioCartAdhesion = $('#ca'+j).val();
	   					tmpIdEspecialista = $('#esp'+j).val();   					
	   					if(folioCartAdhesion == tmpfolioCartAdhesion && idEspecialista == tmpIdEspecialista ){
	   						$('#dialogo_1').html('El registro '+i+", de la asignaci�n de carta adhesi�n se encuentra repetido, por favor verifique");
	   				   		abrirDialogo();
	   				 		return false;
	   					}
	   				}	   				
	   			} 
	   		 }
			
		}//end for
	}else if(registrar == 3){
		var numCamposACA = $('#numCamposACA').val();
		var folioCartAdhesion ="";
		var idEspecialista ="";
		$("#idPrograma").removeAttr('disabled');
		for (i=1;i<parseInt(numCamposACA)+1;i++){
			folioCartAdhesion = $('#ca'+i).val();
			idEspecialista = $('#esp'+i).val();
			if(folioCartAdhesion == "" || idEspecialista == -1 ){
	   			$('#dialogo_1').html('Capture los valores del registro '+i+', de la asignaci�n de cartas a especialistas');
		   		abrirDialogo();
		 		return false;
	   		 }
		}
	}
	
	
	
}

function chkCamposAsigCAaEspecialista(){
	var noOficioCA = $('#noOficioCA').val();
	var folioCartaAdhesion = $('#folioCartaAdhesion').val();
	var idEspecialista = $('#idEspecialista').val();	
	if(idEspecialista == -1 && (noOficioCA == null || noOficioCA=="") 
			&&(folioCartaAdhesion == null || folioCartaAdhesion=="" )){
			$('#dialogo_1').html('Debe seleccionar al menos un dato, para realizar la consulta');
			abrirDialogo();
			return false;
		}
}
function recuperaFechaCartaAdhesion(folioCartaAdhesion, count){
	if(folioCartaAdhesion == -1){
		return false;
	}
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "recuperaFechaCartaAdhesion",
		   data: "folioCartaAdhesion="+folioCartaAdhesion+
		   		 "&count="+count, 
		   success: function(data){
				$('#ffca'+count).html(data).ready(function () {		
				});
		   }
		});//fin ajax
	
}


/*****************RELACION DE DOCUMENTOS DE SOLICITUD DE PAGO  ***************/
function selectDocumentacionSP(){
	var folioCartaAdhesion = $('#folioCartaAdhesion').val();
	//var registrar = $('#registrar').val();
	var tipoDocumentacion = $('input:radio[name=tipoDocumentacion]:checked').val();
	var urlenvio = "";
	if(tipoDocumentacion==1){
		urlenvio = "capDocumentacion";
	}else if(tipoDocumentacion == 2){
		urlenvio = "capFianza";
	}
	$("#contenidoDocumentacion").fadeOut('slow', function() {
		  	$.ajax({
			   async: false,
			   type: "POST",
			   url: urlenvio,
			   data: "folioCartaAdhesion="+folioCartaAdhesion,
			   		//+"&registrar="+registrar,
			   success: function(data){
					$('#contenidoDocumentacion').html(data).ready(function () {
						$("#contenidoDocumentacion").fadeIn('slow');
					});
			   }
			});
	 });
}

function chkCamposDocumentacion(){	
	var patron = /^(xlsx|xls|doc|docx|jpg|png|pdf|txt)$/;
	var estatusCA = $('#estatusCA').val();
	//var alcanceDocumentacion = $('input:checkbox[name=alcanceDocumentacion]:checked').val();
	var alcanceDocumentacion = "";
	if(($('#alcanceDocumentacion')).is(":checked")) {
		alcanceDocumentacion = "true";
	}else{
		alcanceDocumentacion = "false";
	}
	
	var habilitarOficioObs =  "false", doctosSinObservacion="false";
	var capObsExpediente = new Array();
	$(".ck:checkbox:checked").each(function() {capObsExpediente.push($(this).val());});
	
	var habilitaAccionSP = $('input:radio[name=habilitaAccionSP]:checked').val();	
	
	if(habilitaAccionSP==1){
		doctosSinObservacion = "true";
		habilitarOficioObs = "false";
		if(capObsExpediente.length > 0){ 
		    $('#dialogo_1').html('No debe marcar ning�n documento observado, si el expediente no tiene tiene observaciones.');
			abrirDialogo();
		    return false;
		}
	}else if(habilitaAccionSP==2){
		habilitarOficioObs = "true";
		doctosSinObservacion = "false";
		if (capObsExpediente.length == 0){ 
		    $('#dialogo_1').html('Debe marcar al menos un documento con observaci�n para cargar el oficio de observaciones.');
			abrirDialogo();
		    return false;
		}
	}
	
	var idCriterioPago = $('#idCriterioPago').val();	
	var idExpedientesTotal = $('#idExpedientesTotal').val();
	var idExpTotalArray = idExpedientesTotal.split(',');
	var i = 0;	
	
	if(estatusCA == 3){
		//Valida que el documento 1 sea capturado
		var doc1 = $('#doc1').val();
		if(doc1 == null || doc1 == ""){
			$('#dialogo_1').html('Seleccione el archivo del escrito de relaci�n de documentos');
			abrirDialogo();
			return false;
		}else{
			ext = doc1.toLowerCase().substring(doc1.lastIndexOf(".")+1);
			if(!ext.match(patron)){
				$('#dialogo_1').html('Extensi�n no permitida en Escrito de Entrega de Documentos , solo puede cargar archivos con extensiones xlsx, xls, doc,docx, jpg, png, pdf � txt');
		   		abrirDialogo();
				return false;
			}
		}//End doc1
		/*************FECHA DOCUMENTO DE OFICIO DE ENTREGA DE DOCUMENTOS ************/
		var fechaDocEntDoctos = $('#fechaDocEntDoctos').val();
		var fechaDocEntDoctosTemp = 0;
		if(fechaDocEntDoctos == null || fechaDocEntDoctos==""){
			$('#dialogo_1').html('Seleccione la fecha de documento del archivo de entrega de documentos');
			abrirDialogo();
			return false;
		}else{
			dia = fechaDocEntDoctos.substring(0,2);
			mes = fechaDocEntDoctos.substring(3,5);
			anio = fechaDocEntDoctos.substring(6,10);
			fechaDocEntDoctosTemp = anio+""+""+mes+""+dia;
		}
		/*************FECHA ACUSE DE OFICIO DE ENTREGA DE DOCUMENTOS ************/
		var fechaAcuseEntDoctos = $('#fechaAcuseEntDoctos').val();
		var fechaAcuseEntDoctosTemp = 0;
		if(fechaAcuseEntDoctos == null || fechaAcuseEntDoctos == ""){
			$('#dialogo_1').html('Seleccione la fecha del acuse del archivo de entrega de documentos');
			abrirDialogo();
			return false;
		}else{
			dia = fechaAcuseEntDoctos.substring(0,2);
			mes = fechaAcuseEntDoctos.substring(3,5);
			anio = fechaAcuseEntDoctos.substring(6,10);
			fechaAcuseEntDoctosTemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaAcuseEntDoctosTemp) < fechaDocEntDoctosTemp){
				$('#dialogo_1').html('La fecha del acuse del archivo de entrega de documentos no debe ser menor a la fecha del documento');
				abrirDialogo();
				return false;
			}
		}
	}	
	
	if(alcanceDocumentacion=="true"){
		/*********OFICIO DE OBSERVACIONES*****/
		var docObs = $('#docObs').val();
		if(docObs == null || docObs ==""){
			$('#dialogo_1').html('Seleccione el archivo del oficio de observaciones');
			abrirDialogo();
			return false;
		}
		var fechaDocOBS = $('#fechaDocOBS').val();
		var fechaDocOBSTemp = 0;
		if(fechaDocOBS == null || fechaDocOBS==""){
			$('#dialogo_1').html('Seleccione la fecha de documento de observaci�n');
			abrirDialogo();
			return false;
		}else{
			dia = fechaDocOBS.substring(0,2);
			mes = fechaDocOBS.substring(3,5);
			anio = fechaDocOBS.substring(6,10);
			fechaDocOBSTemp = anio+""+""+mes+""+dia;	
		}
		var fechaAcuseOBS = $('#fechaAcuseOBS').val();
		var fechaAcuseOBSTemp = "";
		if(fechaAcuseOBS == null || fechaAcuseOBS == ""){
			$('#dialogo_1').html('Seleccione la fecha de acuse del oficio de observaci�n');
			abrirDialogo();
			return false;
		}else{
			dia = fechaAcuseOBS.substring(0,2);
			mes = fechaAcuseOBS.substring(3,5);
			anio = fechaAcuseOBS.substring(6,10);
			fechaAcuseOBSTemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaAcuseOBSTemp) < fechaDocOBSTemp){
				$('#dialogo_1').html('La fecha del acuse de observaci�n no debe ser menor a la fecha del documento');
				abrirDialogo();
				return false;
			}
		}
		var noOficioOBS = $('#noOficioOBS').val();
		if(noOficioOBS == null || noOficioOBS == ""){
			$('#dialogo_1').html('Seleccione el n�mero de oficio de observaci�n');
			abrirDialogo();
			return false;
		}
		
	}
	
	
	if((estatusCA == 3 && capObsExpediente.length == 0 && habilitarOficioObs=="false" && doctosSinObservacion=="false" )
		||(estatusCA == 3 && capObsExpediente.length >0 && habilitarOficioObs=="false" && doctosSinObservacion=="false")
		||(estatusCA == 6 && capObsExpediente.length == 0 && habilitarOficioObs=="false" && doctosSinObservacion=="false")
		||(estatusCA == 6 && capObsExpediente.length >0 && habilitarOficioObs=="false" && doctosSinObservacion=="false")
		||(estatusCA == 8 && capObsExpediente.length == 0 && habilitarOficioObs=="false" && doctosSinObservacion=="false")
		||(estatusCA == 8 && capObsExpediente.length >0 && habilitarOficioObs=="false" && doctosSinObservacion=="false")){ //sin ninguna observacion sin subir oficio de observaciones
		//ca.setEstatus(6); //DOCUMENTACI�N PARCIAL
		
		var idExpediente="";
		for(i=0; i< idExpTotalArray.length; i++){
			   if(idExpTotalArray[i]!=1){
				   idExpediente = idExpTotalArray[i];
				   if($('#doc'+idExpediente).length){
					   var doc = $('#doc'+idExpediente).val();
					   var docRequerido = $('#docRequerido'+idExpediente).val();
					   expediente = recuperaNombreDocumento(idExpediente);
					   if(doc == null || doc == ""){							
							if($('#capObsExpediente'+idExpediente).is(":checked")){
								console.log("No es necesario cargar el documento");
							}else if(docRequerido=="true"){
								$('#dialogo_1').html('Seleccione el archivo de '+expediente);
								abrirDialogo();
								return false;
							}
						}else{//end doc ==null
							ext = doc.toLowerCase().substring(doc.lastIndexOf(".")+1);
							if(!ext.match(patron)){
								$('#dialogo_1').html('Extensi�n no permitida para el archivo de '+expediente+', solo puede cargar archivos con extensiones xlsx, xls, doc, docx, jpg, png, pdf � txt');
						   		abrirDialogo();
								return false;
							}
							if(idExpediente == 3){
								if(idCriterioPago == 1 || idCriterioPago == 3){
									var v=$('#v3').val();
								    var patronVolumen =/^\d{1,10}((\.\d{1,3})|(\.))?$/;
							    	if(v=="" || v==null){
							    		$('#dialogo_1').html('Capture el valor del volumen solicitado a apoyar');
							       		abrirDialogo();
							    		return false;
							    	}
							    	if(!v.match(patronVolumen)){
							    		$('#dialogo_1').html('El valor del registro es inv�lido, se aceptan hasta 10 digitos a la izquierda y 3 m�ximo a la derecha');
							       		abrirDialogo();
							       		return false;
							    	}
								}
							}
							if(idExpediente == 5){
								//Si no hubo alguna observacion, valida que la fecha de expedici�n sea capturada
								if($('#capObsExpediente5').is(":checked")){
									console.log("checado ");
								}else{
									var anexo32DyaCapturado = $('#anexo32DyaCapturado').val();
									console.log("anexo32DyaCapturado "+ anexo32DyaCapturado);
									if(anexo32DyaCapturado == "false"){
										console.log("anexo32DyaCapturado no se ha capturado");
										var fechaExpedicion = $('#fechaExpedicion').val();
										if(fechaExpedicion == null || fechaExpedicion == ""){
											$('#dialogo_1').html('Seleccione la fecha de expedici�n del Anexo 32-D');
											abrirDialogo();
											return false;
										}
									}
									
								}
								
							}// end expediente = 5								
							if(idExpediente == 10 || idExpediente ==34){
								var tipoConstancia = $('input:radio[name=tipoConstancia]:checked').val();
								if(tipoConstancia != 1 && tipoConstancia != 2 && tipoConstancia != 3){
									$('#dialogo_1').html('Debe seleccionar el tipo de constancia, si carga cualquier documento relacionado');
									abrirDialogo();
									return false;
								}
							}
						}//end else documento diferente de null
				   }//end  if($('#doc'+idExpediente).length){
				   
			   }
		}//End for //DOCUMENTACION PARCIAL
		
	}else if((estatusCA == 3 && capObsExpediente.length == 0 && doctosSinObservacion =="true" && habilitarOficioObs=="false")//VALIDA 
			|| (estatusCA == 3 && capObsExpediente.length >0 && habilitarOficioObs =="true" && doctosSinObservacion=="false")
			|| (estatusCA == 6 && capObsExpediente.length == 0 && doctosSinObservacion =="true" && habilitarOficioObs=="false")
			|| (estatusCA == 6 && capObsExpediente.length >0 && habilitarOficioObs =="true" && doctosSinObservacion=="false")
			|| (estatusCA == 8 && capObsExpediente.length == 0 && doctosSinObservacion =="true" && habilitarOficioObs=="false")
			|| (estatusCA == 8 && capObsExpediente.length >0 && habilitarOficioObs =="true" && doctosSinObservacion=="false")){//sin ninguna observacion con marca de doctos sin observacion
		//ca.setEstatus(5); //5; "DOCUMENTACI�N COMPLETA SIN OBSERVACIONES" || ca.setEstatus(4); //4; "DOCUMENTACI�N COMPLETA CON OBSERVACIONES"
		//Valida los documentos requeridos
		var idExpediente="";
		var idExpedientesRequeridos = $('#idExpedientesRequeridoshi').val();
		var idERArray = "";
		if(idExpedientesRequeridos!=""){
			idERArray = idExpedientesRequeridos.split(',');
		}
			
		for(i=0; i< idERArray.length; i++){
			   if(idERArray[i]!=1){
				   idExpediente = idERArray[i];
				   if($('#doc'+idExpediente).length){
					   console.log("expediente " + idERArray[i]);
					   var doc = $('#doc'+idExpediente).val();
					   var docRequerido = $('#docRequerido'+idExpediente).val();
					   expediente = recuperaNombreDocumento(idExpediente);
					   console.log("1");
					   if(doc == null || doc == ""){							
							if($('#capObsExpediente'+idExpediente).is(":checked")){
								console.log("No es necesario cargar el documento");
							}else if(docRequerido=="true"){
								console.log("2");
								$('#dialogo_1').html('Seleccione el archivo de '+expediente);
								abrirDialogo();
								return false;
							}
						}else{//end doc ==null
							ext = doc.toLowerCase().substring(doc.lastIndexOf(".")+1);
							if(!ext.match(patron)){
								$('#dialogo_1').html('Extensi�n no permitida para el archivo de '+expediente+', solo puede cargar archivos con extensiones xlsx, xls, doc, docx, jpg, png, pdf � txt');
						   		abrirDialogo();
								return false;
							}
							if(idExpediente == 3 && docRequerido=="true"){
								if(idCriterioPago == 1 || idCriterioPago == 3){
									var v=$('#v3').val();
								    var patronVolumen =/^\d{1,10}((\.\d{1,3})|(\.))?$/;
							    	if(v=="" || v==null){
							    		$('#dialogo_1').html('Capture el valor del volumen solicitado a apoyar');
							       		abrirDialogo();
							    		return false;
							    	}
							    	if(!v.match(patronVolumen)){
							    		$('#dialogo_1').html('El valor del registro es inv�lido, se aceptan hasta 10 digitos a la izquierda y 3 m�ximo a la derecha');
							       		abrirDialogo();
							       		return false;
							    	}
								}
								
							}
							if(idExpediente == 5){
								//Si no hubo alguna observacion, valida que la fecha de expedici�n sea capturada
								if($('#capObsExpediente5').is(":checked")){
									console.log("checado ");
								}else{
									var anexo32DyaCapturado = $('#anexo32DyaCapturado').val();
									console.log("anexo32DyaCapturado "+ anexo32DyaCapturado);
									if(anexo32DyaCapturado == "false"){
										console.log("anexo32DyaCapturado no se ha capturado");
										var fechaExpedicion = $('#fechaExpedicion').val();
										if(fechaExpedicion == null || fechaExpedicion == ""){
											$('#dialogo_1').html('Seleccione la fecha de expedici�n del Anexo 32-D');
											abrirDialogo();
											return false;
										}
									}
									
								}
								
							}// end expediente = 5	
							
						}//end else documento diferente de null
				   }//end  if($('#doc'+idExpediente).length){
			   }
		 }//end for de idExpedientes requeridos
		var certDepositoOAlmacenamiento = $('#certDepositoOAlmacenamiento').val();
		if(certDepositoOAlmacenamiento!=0){
			var tipoConstancia = $('input:radio[name=tipoConstancia]:checked').val();
			if(tipoConstancia != 1 && tipoConstancia != 2 && tipoConstancia != 3){
				$('#dialogo_1').html('Debe seleccionar el tipo de constancia');
				abrirDialogo();
				return false;
			}
			if(tipoConstancia==1 || tipoConstancia ==3){
				//Validar que el documento certificado de deposito haya sido cargado
				if($('#doc10').length){
					var doc = $('#doc10').val();
					 expediente = recuperaNombreDocumento(10);
					 if(doc == null || doc == ""){							
							$('#dialogo_1').html('Seleccione el archivo de '+expediente);
							abrirDialogo();
							return false;					
					}else{
						if(!ext.match(patron)){
							$('#dialogo_1').html('Extensi�n no permitida para el archivo de '+expediente+', solo puede cargar archivos con extensiones xlsx, xls, doc, docx, jpg, png, pdf � txt');
					   		abrirDialogo();
							return false;
						}
					}
				}
				 			
			}else if(tipoConstancia==2 || tipoConstancia==3){
				//Validar que el documento constancia de almacenamiento haya sido cargado
				if($('#doc34').length){
					var doc = $('#doc34').val();
					 expediente = recuperaNombreDocumento(34);
					 if(doc == null || doc == ""){							
							$('#dialogo_1').html('Seleccione el archivo de '+expediente);
							abrirDialogo();
							return false;					
					}else{
						if(!ext.match(patron)){
							$('#dialogo_1').html('Extensi�n no permitida para el archivo de '+expediente+', solo puede cargar archivos con extensiones xlsx, xls, doc, docx, jpg, png, pdf � txt');
					   		abrirDialogo();
							return false;
						}
					}
				}			
				
			}	
		}//End si se configuro relacion de certificado o constancia de almacenamiento
		
	}	//end else if 
	
	if(habilitarOficioObs == "true"){
		 //Validar que minimo sea cargado el no de oficio de la solicitud de pago
		 var noOficioOBS = $('#noOficioOBS').val();
			if(noOficioOBS == null || noOficioOBS == ""){
				$('#dialogo_1').html('Seleccione el n�mero de oficio de observaci�n');
				abrirDialogo();
				return false;
			}
	 }
	
	if(estatusCA==4 && alcanceDocumentacion=="false"){
		var doctosObservados = $('#doctosObservados').val();
		if (capObsExpediente.length == doctosObservados){ 
			$('#dialogo_1').html('Debe desmarcar por lo menos un registro, para cargar el oficio de respuesta');
			abrirDialogo();
			return false;
		}
		
		//Si los datos del oficio de observaciones no se han capturado, validar que se capturen archivo y fechas
		if( $('#docObs').length){
			console.log("docObs");
			var docObs = $('#docObs').val();
			if(docObs == null || docObs ==""){
				$('#dialogo_1').html('Seleccione el archivo del oficio de observaciones');
				abrirDialogo();
				return false;
			}
		}
		console.log("docObs1");
		var fechaDocOBSTemp = 0;
		if( $('#fechaDocOBS').length){
			var fechaDocOBS = $('#fechaDocOBS').val();
			
			if(fechaDocOBS == null || fechaDocOBS==""){
				$('#dialogo_1').html('Seleccione la fecha de documento de observaci�n');
				abrirDialogo();
				return false;
			}else{
				dia = fechaDocOBS.substring(0,2);
				mes = fechaDocOBS.substring(3,5);
				anio = fechaDocOBS.substring(6,10);
				fechaDocOBSTemp = anio+""+""+mes+""+dia;	
			}
			
		}
		
		if( $('#fechaAcuseOBS').length){
			var fechaAcuseOBS = $('#fechaAcuseOBS').val();
			var fechaAcuseOBSTemp = "";
			if(fechaAcuseOBS == null || fechaAcuseOBS == ""){
				$('#dialogo_1').html('Seleccione la fecha de acuse del oficio de observaci�n');
				abrirDialogo();
				return false;
			}else{
				dia = fechaAcuseOBS.substring(0,2);
				mes = fechaAcuseOBS.substring(3,5);
				anio = fechaAcuseOBS.substring(6,10);
				fechaAcuseOBSTemp = anio+""+""+mes+""+dia;
				if(parseInt(fechaAcuseOBSTemp) < fechaDocOBSTemp){
					$('#dialogo_1').html('La fecha del acuse de observaci�n no debe ser menor a la fecha del documento');
					abrirDialogo();
					return false;
				}
			}
		}	
		
		var idExpedientesObservados = $('#idExpedientesObservados').val(); 
		var idExpObArray = idExpedientesObservados.split(',');
		for(i=0; i< idExpObArray.length; i++){
			console.log("idExpObArray"+idExpObArray[i]);
			if($('#capObsExpediente'+idExpObArray[i]).is(":checked")){
			}else{
				var doc = $('#doc'+idExpObArray[i]).val();
				if(doc == null || doc == ""){
					expediente = recuperaNombreDocumento(idExpObArray[i]);							
					$('#dialogo_1').html('Seleccione el archivo de '+expediente);
					abrirDialogo();
					return false;
				}else{//end doc ==null
					expediente = recuperaNombreDocumento(idExpObArray[i]);
					ext = doc.toLowerCase().substring(doc.lastIndexOf(".")+1);
					if(!ext.match(patron)){
						$('#dialogo_1').html('Extensi�n no permitida para el archivo de '+expediente+', solo puede cargar archivos con extensiones xlsx, xls, doc, docx, jpg, png, pdf � txt');
				   		abrirDialogo();
						return false;
					}
					//Si el expediente es Solicitud pago validar que tenga el volumen
					if(idExpObArray[i] == 3){
						if(idCriterioPago == 1 || idCriterioPago == 3){
							var v=$('#v3').val();
						    var patronVolumen =/^\d{1,10}((\.\d{1,3})|(\.))?$/;
					    	if(v=="" || v==null){
					    		$('#dialogo_1').html('Capture el valor del volumen solicitado a apoyar');
					       		abrirDialogo();
					    		return false;
					    	}
					    	if(!v.match(patronVolumen)){
					    		$('#dialogo_1').html('El valor del registro es inv�lido, se aceptan hasta 10 digitos a la izquierda y 3 m�ximo a la derecha');
					       		abrirDialogo();
					       		return false;
					    	}
						}
					}//expediente 3
				}
		
			}//end else	 
	
		}//End for
		/******OFICIO DE RESPUESTA********/
		if ($('#docResp').length){
			var docResp = $('#docResp').val();
			if(docResp == null || docResp == ""){
				$('#dialogo_1').html('Seleccione el archivo del oficio de respuesta de observaci�n');
				abrirDialogo();
				return false;
			}else{
				ext = docResp.toLowerCase().substring(docResp.lastIndexOf(".")+1);
				if(!ext.match(patron)){
					$('#dialogo_1').html('Extensi�n no permitida en oficio de respuesta, solo puede cargar archivos con extensiones xlsx, xls, doc,docx, jpg, png, pdf � txt');
			   		abrirDialogo();
					return false;
				}
			}//End docRespObs1
			/*************FECHA DOCUMENTO DE OFICIO DE RESPUESTA ************/
			var fechaDocResp = $('#fechaDocResp').val();
			var fechaDocRespTemp1 = 0;
			if(fechaDocResp == null || fechaDocResp ==""){
				$('#dialogo_1').html('Seleccione la fecha de documento del archivo de respuesta de observaciones');
				abrirDialogo();
				return false;
			}else{
				dia = fechaDocResp.substring(0,2);
				mes = fechaDocResp.substring(3,5);
				anio = fechaDocResp.substring(6,10);
				fechaDocRespTemp1 = anio+""+""+mes+""+dia;
			}
			/*************FECHA ACUSE DE OFICIO DE RESPUESTA DE OBSERVACIONES ************/
			var fechaAcuseResp = $('#fechaAcuseResp').val();
			var fechaAcuseRespTemp1 = 0;
			if(fechaAcuseResp == null || fechaAcuseResp == ""){
				$('#dialogo_1').html('Seleccione la fecha del acuse del archivo de respuesta de observaciones');
				abrirDialogo();
				return false;
			}else{
				dia = fechaAcuseResp.substring(0,2);
				mes = fechaAcuseResp.substring(3,5);
				anio = fechaAcuseResp.substring(6,10);
				fechaAcuseRespTemp1 = anio+""+""+mes+""+dia;
				if(parseInt(fechaAcuseRespTemp1) < fechaDocRespTemp1){
					$('#dialogo_1').html('La fecha del acuse del archivo de respuesta de observaciones no debe ser menor a la fecha del documento');
					abrirDialogo();
					return false;
				}
			}
		}// end ($('#docResp').length)
		$("#fechaDocOBS").removeAttr('disabled');
		$("#fechaAcuseOBS").removeAttr('disabled');
	}//end estatusCA==4
	
	$("#capObsExpediente9").removeAttr('disabled');
		
}//end chkCamposDocumentacion


function chkCamposFianza(){
	var doc = "";
	var idExpediente = 0;
	var valida = 0;
	var idCriterioPago = $('#idCriterioPago').val();
	//Valida que todos los documentos en la fianza sean cargados (32,4,5,15,33,13), importa el orden
	if($('#doc32').length){
		idExpediente = 32;
		doc = $('#doc32').val();
		valida = validaDocumentoFianza(doc, idExpediente);
		if(valida == 1){
			return false;
		}
	}
	
	if(idCriterioPago == 1 || idCriterioPago == 3){
		var v=$('#v3').val();
	    var patronVolumen =/^\d{1,10}((\.\d{1,3})|(\.))?$/;
    	if(v=="" || v==null){
    		$('#dialogo_1').html('Capture el valor del volumen solicitado a apoyar');
       		abrirDialogo();
    		return false;
    	}
    	if(!v.match(patronVolumen)){
    		$('#dialogo_1').html('El valor del registro es inv�lido, se aceptan hasta 10 digitos a la izquierda y 3 m�ximo a la derecha');
       		abrirDialogo();
       		return false;
    	}
	}

	if($('#doc4').length){
		idExpediente = 4;
		doc = $('#doc4').val();
		valida = validaDocumentoFianza(doc, idExpediente);
		if(valida == 1){
			return false;
		}
	}
	if($('#doc5').length){
		idExpediente = 5;
		doc = $('#doc5').val();
		valida = validaDocumentoFianza(doc, idExpediente);
		if(valida == 1){
			return false;
		}
	}
	var fechaExpedicion = $('#fechaExpedicion').val();
	if(fechaExpedicion == null || fechaExpedicion == ""){
		$('#dialogo_1').html('Seleccione la fecha de expedici�n del Anexo 32-D');
		abrirDialogo();
		return false;
	}
	
	if($('#doc15').length){
		idExpediente = 15;
		doc = $('#doc15').val();
		valida = validaDocumentoFianza(doc, idExpediente);
		if(valida == 1){
			return false;
		}
	}
	
	if($('#doc33').length){
		idExpediente = 33;
		doc = $('#doc33').val();
		valida = validaDocumentoFianza(doc, idExpediente);
		if(valida == 1){
			return false;
		}
	}
	if($('#doc13').length){
		idExpediente = 13;
		doc = $('#doc13').val();
		valida = validaDocumentoFianza(doc, idExpediente);
		if(valida == 1){
			return false;
		}
	}

	
}

function validaDocumentoFianza(doc, idExpediente){
	var patron = /^(xlsx|xls|doc|docx|jpg|png|pdf|txt)$/;
	var expediente = recuperaNombreDocumento(idExpediente);
	if(doc == null || doc == ""){
		$('#dialogo_1').html('Seleccione el archivo de '+expediente);
		abrirDialogo();
		return 1;
	}else{
		ext = doc.toLowerCase().substring(doc.lastIndexOf(".")+1);
		if(!ext.match(patron)){
			$('#dialogo_1').html('Extensi�n no permitida para el archivo de '+expediente+', solo puede cargar archivos con extensiones xlsx, xls, doc,docx, jpg, png, pdf � txt');
	   		abrirDialogo();
			return 1;
		}
	}
	
	return 0;
}

function recuperaNombreDocumento(idExpediente){
	if(idExpediente == 2 ){
		return "Carta Adhesi�n";
	}else if(idExpediente == 3){
		return "Solicitud de Pago del Apoyo";
	}else if(idExpediente == 4){
		return "Estado de Cuenta";
	}else if(idExpediente == 5){
		return "Anexo 32-D";
	}else if(idExpediente == 6){
		return "Tipo de Cambio Utilizado";
	}else if(idExpediente == 7){
		return "Dictamen Contable del Auditor";
	}else if(idExpediente == 8){
		return "Relaci�n de Compras al Productor";
	}else if(idExpediente == 9){
		return "Reporte de Cruce";
	}else if(idExpediente == 10){
		return "Relaci�n de Certificados de Dep�sito";
	}else if(idExpediente == 11){
		return "Relaci�n de Movilizaci�n";
	}else if(idExpediente == 12){
		return "Relaci�n de Ventas";
	}else if(idExpediente == 13){
		return "Contrato de Mandato";
	}else if(idExpediente == 14){
		return "Escrito Libre";
	}else if(idExpediente == 15){
		return "Fianza";
	}else if(idExpediente == 32){
		return "Solicitud de Pago del Apoyo";
	}else if(idExpediente == 33){
		return "Dictamen Contable del Auditor";
	}else if(idExpediente == 34){
		return "Constancia de Almacenamiento";
	}
}

function habilitarOficioObservacion(idExpediente){
	
	var selectedItems = new Array();
	var registrar = $('#registrar').val();
	var capObsExpediente = $('#capObsExpediente'+idExpediente).val();
	console.log('capObsExpediente '+ capObsExpediente);
	if(idExpediente==8){
		if(($('#capObsExpediente'+idExpediente)).is(":checked")) {
			$("#capObsExpediente9").attr("checked", "checked");
			$("#capObsExpediente9").attr('disabled','disabled'); 
		}else{	
			$("#capObsExpediente9").removeAttr("checked");
			//$("#capObsExpediente9").removeAttr('disabled'); 
		}
	
	}
	
	
	if(idExpediente == 5){
		if(($('#capObsExpediente'+idExpediente)).is(":checked")) {
			$(".datosAnexo5").fadeOut('slow');	
		}else{
			$(".datosAnexo5").fadeIn('slow');	
		}
		
	}
	$(".ck:checkbox:checked").each(function() {selectedItems.push($(this).val());});
	if (selectedItems.length == 0){ 
		$("#habilitarOficioObservacion").fadeOut('slow');
	    return false;
	}
	
	if(registrar != 3 && registrar !=4){
		$.ajax({
			   async: false,
			   type: "POST",
			   url: "habilitarOficioObservacion", 
			   success: function(data){
					$('#habilitarOficioObservacion').html(data).ready(function () {
						$("#habilitarOficioObservacion").fadeIn('slow');		
					});
			   }
			});//fin ajax
	}
}

function chkCamposRegistraCertificadoDeposito(){
	//Valida que la informacion no se encuentre repetida
	
	var numCampos = $('#numCampos').val();
	if(numCampos == null || numCampos == ""){
		$('#dialogo_1').html('Capture el dato en N�mero Campos a Capturar Certificados');
   		abrirDialogo();
 		return false;
		
	}else{
		var patron =/^\d{1,2}$/;
		if(!numCampos.match(patron)){
    		$('#dialogo_1').html('El valor del campo es inv�lido, se aceptan hasta 2 d�gitos');
       		abrirDialogo();
       		$('#numCampos').val(null);
       		return false;
    	}else{
    		var tempAlm = "", alm ="";
    		var tempFolio = "", folio = "";
    		var fechaExpedicion = "",  fechaExpedicionTemp = "", fechaFinVigencia = "", fechaFinVigenciaTemp = "";
    		var dia =0, mes = 0, anio = 0;
    		var volumen = "";
    		var i = 0, j = 0;
    		for (i=1;i<parseInt(numCampos)+1;i++){
    			alm = $('#alm'+i).val();
    			claveBodega = $('#claveBodega'+i).val();
    			folio = $('#folio'+i).val();
    			fechaExpedicion =  $('#fechaExpedicion'+i).val();
    			fechaFinVigencia =  $('#fechaFinVigencia'+i).val();
    			volumen = $('#v'+i).val();
    			if(alm==-1 || (claveBodega == "" || claveBodega == null) || (folio == "" || folio == null)  
    					|| (fechaExpedicion == "" || fechaExpedicion == null) || (fechaFinVigencia == "" || fechaFinVigencia == null) 
    					|| (volumen == "" || volumen == null)){
    	   			$('#dialogo_1').html('Capture los valores del registro '+i+' en la captura de certificados');
    		   		abrirDialogo();
    		 		return false;
    	   		}else{
    	   			//Valida que el volumen sea un valor valido
    	   			patron =/^\d{1,10}((\.\d{1,3})|(\.))?$/;
    	  			 if(!volumen.match(patron)){
    		   			$('#dialogo_1').html('El valor de volumen es inv�lido, se aceptan hasta 10 digitos a la izquierda y 3 digitos m�ximo a la derecha');
    			   		abrirDialogo();
    			 		return false;
    	  			 }
    	   			//Verifica que los valores capturados no se encuentren repetidos por almacenadora, bodega y folio
    	   			for (j=1;j<parseInt(numCampos)+1;j++){
    	   	   			if(i!=j){
    	   	   				tempAlm = $('#alm'+j).val();
    	   	   				tempFolio = $('#folio'+j).val();
    	   	   				if(alm == tempAlm && folio == tempFolio){
    	   	   					$('#dialogo_1').html('El registro '+i+' se encuentra repetido, favor de verificar');
    	   	   			   		abrirDialogo();
    	   	   			 		return false;
    	   	   				}
    	   	   			}	   				
    	   	   		}// end for j
    	   			//Verifica que las fechas sean congruentes
    	   			dia = fechaExpedicion.substring(0,2);
    	   			mes = fechaExpedicion.substring(3,5);
    	   			anio = fechaExpedicion.substring(6,10);
    	   			fechaExpedicionTemp = anio+""+""+mes+""+dia;
    	   			dia = fechaFinVigencia.substring(0,2);
    	   			mes = fechaFinVigencia.substring(3,5);
    	   			anio = fechaFinVigencia.substring(6,10);
    	   			fechaFinVigenciaTemp = anio+""+""+mes+""+dia;   
    	   			
    	   			console.log("fechaExpedicionTemp "+fechaExpedicionTemp);
    	   			console.log("fechaFinVigenciaTemp "+fechaFinVigenciaTemp);
    	   			if(parseInt(fechaFinVigenciaTemp) < fechaExpedicionTemp){
    	   				$('#dialogo_1').html('La fecha fin de vigencia no debe ser menor a la fecha de expedici�n en el registro '+i+' por favor verifique');
    	   				abrirDialogo();
    	   				return false;
    	   			}
    	   		}
    		} //end for
    		
    	} //end else numcampos valido
		
	}//end else diferente de vacio o nulo
	
}// end chkCamposregistraCertificadoDeposito

function validaClaveBodega(claveBodega, count){
	//Valida que la bodega exista en la base de datos	
	if((claveBodega == "" || claveBodega == null)){
		return false;
	}
	
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "validaClaveBodega", 
		   data: "claveBodega="+claveBodega+
	   			"&count="+count,
		   success: function(data){
			   var $response=$(data);
			   var idEstado = $response.filter('#idEstado'+count).text();
			   $('#estadoBodega').val(idEstado);
	  
			   if(errorClaveBodega == 1){
				   $('#errorClaveBodega').val(1);
			   }else{
				   $('#errorClaveBodega').val(0);
			   }
			   $("#validaClaveBodega"+count).html(data).ready(function () {	
				});
			   $("#errorClaveBodega"+count).fadeOut("slow");
		   }
		});//fin ajax
}


function validaAlmaBodegaFolio(index){
	
	var claveBodega = $('#claveBodega'+index).val();	
	
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "validaAlmaBodegaFolio", 
		   data: "claveBodega="+claveBodega+
	   			"&count="+count,
		   success: function(data){
			   var $response=$(data);
			   var errorClaveBodega = $response.filter('#errorClaveBodega'+count).text();
			   console.log("errorClaveBodega "+errorClaveBodega);
			   if(errorClaveBodega == 1){
				   $('#errorClaveBodega').val(1);
			   }else{
				   $('#errorClaveBodega').val(0);
			   }
			   $("#validaClaveBodega"+count).html(data).ready(function () {	
				});
		   }
		});//fin ajax
}

function chkCamposAnexo32(){
	var patron = /^(xlsx|xls|doc|docx|jpg|png|pdf|txt)$/;
	var doc = $('#doc').val();
	if(doc == null || doc == ""){
		$('#dialogo_1').html('Seleccione el archivo a sustituir');
		abrirDialogo();
		return false;
	}else{
		ext = doc.toLowerCase().substring(doc.lastIndexOf(".")+1);
		if(!ext.match(patron)){
			$('#dialogo_1').html('Extensi�n no permitida, solo puede cargar archivos con extensiones xlsx, xls, doc,docx, jpg, png, pdf � txt');
	   		abrirDialogo();
			return false;
		}
	}
	var fechaExpedicion = $('#fechaExpedicion').val();
	if(fechaExpedicion == null || fechaExpedicion == ""){
		$('#dialogo_1').html('Seleccione la fecha de expedici�n del anexo');
		abrirDialogo();
		return false;
	}
		
}


function chkCamposSolicitudPago(){
	var patron = /^(xlsx|xls|doc|docx|jpg|png|pdf|txt)$/;
	var doc = $('#doc').val();
	if(doc == null || doc == ""){
		$('#dialogo_1').html('Seleccione el archivo a sustituir');
		abrirDialogo();
		return false;
	}else{
		ext = doc.toLowerCase().substring(doc.lastIndexOf(".")+1);
		if(!ext.match(patron)){
			$('#dialogo_1').html('Extensi�n no permitida, solo puede cargar archivos con extensiones xlsx, xls, doc,docx, jpg, png, pdf � txt');
	   		abrirDialogo();
			return false;
		}
	}
	var volumen = $('#volumen').val();
	var patronVolumen =/^\d{1,10}((\.\d{1,3})|(\.))?$/;
	if(volumen=="" || volumen==null){
		$('#dialogo_1').html('Capture el valor del volumen solicitado a apoyar');
   		abrirDialogo();
		return false;
	}
	if(!volumen.match(patronVolumen)){
		$('#dialogo_1').html('El valor del volumen es inv�lido, se aceptan hasta 10 digitos a la izquierda y 3 m�ximo a la derecha');
   		abrirDialogo();
   		return false;
	}
	
	
}

function chkCamposPagoCartaAdhesion(){
	var band = false;
	var numCampos = $('#numCampos').val();	
	var patron;
	var criterioPago = $('#criterioPago').val();;
	var volumenApoyar = 0.0, importeApoyar = 0.0, etapa = 0;
	var volumenAut = 0.0, importeAut = 0.0;
	var i = 0;
	for (i=1;i<parseInt(numCampos)+1;i++){
		if (criterioPago==1){
			volumenApoyar = $('#volumenApoyar'+i).val();
			if (volumenApoyar != null && volumenApoyar != ""){
				band = true;
	   			//Valida que el volumen sea un valor valido
	   			patron =/^\d{1,10}((\.\d{1,3})|(\.))?$/;
	  			 if(!volumenApoyar.match(patron)){
		   			$('#dialogo_1').html('REGISTRO: '+i+' - El valor de volumen a apoyar es inv�lido, se aceptan hasta 10 digitos a la izquierda y 3 digitos m�ximo a la derecha');
			   		abrirDialogo();
			 		return false;
	  			 }
	  			 volumenAut = $('#volumenAut'+i).val();
	  			 if(!volumenApoyar > volumenAut){
		   			$('#dialogo_1').html('REGISTRO: '+i+' - El valor de volumen a apoyar no debe ser mayor al volumen autorizado, favor de verificar');
			   		abrirDialogo();
			 		return false;
		  		}		  			 
			}
		} else if (criterioPago==2){
			importeApoyar = $('#importeApoyar'+i).val();
			etapa = $('#etapa'+i).val();
			if ((importeApoyar != null && importeApoyar != "") || (etapa != null && etapa!=-1)){
				band = true;
	   			//Valida que el importe sea un valor valido
	   			patron =/^\d{1,12}((\.\d{1,2})|(\.))?$/;
	  			if(!importeApoyar.match(patron)){
		   			$('#dialogo_1').html('REGISTRO: '+i+' - El valor de importe a apoyar es inv�lido, se aceptan hasta 12 digitos a la izquierda y 2 digitos m�ximo a la derecha');
			   		abrirDialogo();
			 		return false;
	  			}
	  			importeAut = $('#importeAut'+i).val();
	  			if(!importeApoyar > importeAut){
		   			$('#dialogo_1').html('REGISTRO: '+i+' - El valor de importe a apoyar no debe ser mayor al importe autorizado, favor de verificar');
			   		abrirDialogo();
			 		return false;
	  			}
	  			//Valida que la etapa sea un valor valido
				if(etapa == -1){
					$('#dialogo_1').html('Seleccione la etapa para el registro a capturar');
					abrirDialogo();
					return false;
				}
			}
		} else if (criterioPago==3){
			volumenApoyar = $('#volumenApoyar'+i).val();
			etapa = $('#etapa'+i).val();
			if ((volumenApoyar != null && volumenApoyar != "") || (etapa != null && etapa!=-1)){
				band = true;
	   			//Valida que el volumen sea un valor valido
	   			patron =/^\d{1,10}((\.\d{1,3})|(\.))?$/;
	  			 if(!volumenApoyar.match(patron)){
		   			$('#dialogo_1').html('REGISTRO: '+i+' - El valor de volumen a apoyar es inv�lido, se aceptan hasta 10 digitos a la izquierda y 3 digitos m�ximo a la derecha');
			   		abrirDialogo();
			 		return false;
	  			 }
	  			 volumenAut = $('#volumenAut'+i).val();
	  			 if(!volumenApoyar > volumenAut){
		   			$('#dialogo_1').html('REGISTRO: '+i+' - El valor de volumen a apoyar no debe ser mayor al volumen autorizado, favor de verificar');
			   		abrirDialogo();
			 		return false;
		  		}
	  			//Valida que la etapa sea un valor valido
				if(etapa == -1){
					$('#dialogo_1').html('Seleccione la etapa para el registro a capturar');
					abrirDialogo();
					return false;
				}
			}				
		}
	} //end for	
	if (!band) {
		$('#dialogo_1').html('Debe capturar al menos un pago para la carta de adhesi�n, favor verifique');
		abrirDialogo();
		return false;			
	}
}// end chkCamposPagoCartaAdhesion



function chkCampoConsultaCartaAdhesionSP(){

	var folioCartaAdhesion = $('#folioCartaAdhesion').val();
	var idEspecialista = $('#idEspecialista').val();
	var idEstatusCA = $('#idEstatusCA').val();	
	var busParticipante = $('#busParticipante').val();	
	if((folioCartaAdhesion == null || folioCartaAdhesion =="") && 
			   (idEspecialista == -1) && (idEstatusCA == -1) && (busParticipante == null || busParticipante =="")){
				$('#dialogo_1').html('Debe capturar al menos un dato, para consultar');
				abrirDialogo();
			 	return false;
	}
	
}

function vistaPreviaAtentaNota(){
	var idPrograma = $('#idPrograma').val();
	var folioCartaAdhesion = $('#folioCartaAdhesion').val();
	var nombreComprador = $('#nombreComprador').val();
	var criterioPago = $('#criterioPago').val();
	
	$("#pagos").fadeOut('5000', function(){
		$.ajax({
			   async: false,
			   type: "POST",
			   url: "vistaPreviaAtentaNota",
			   data: "idPrograma="+idPrograma+
		   		"&folioCartaAdhesion="+folioCartaAdhesion+
		   		"&nombreComprador="+nombreComprador+
		   		"&criterioPago="+criterioPago,
			   success: function(data){
					$('#vistaPrevia').html(data).ready(function () {
						$("#vistaPrevia").fadeIn('slow');
					});
			   }
			});
		});
}

function cerrarVistaPreviaAtentaNota(){
	
	$("#vistaPrevia").fadeOut('3000', function(){
		$("#pagos").fadeIn('3000');	
	});
}



/********      Certificados de Deposito   *****/

function seleccionaTipoCargaCer(){
	var tipoCargaCer = $('input:radio[name=tipoCargaCer]:checked').val();
	var urlenvio = "cargaIndividual";
	var folioCartaAdhesion = $('#folioCartaAdhesion').val();
	if(tipoCargaCer==1){
		urlenvio = "cargaMasiva";
	}
	$("#tipoCargaCer").fadeOut('slow', function() {
		$.ajax({
			   async: false,
			   type: "POST",
			   url: urlenvio,
			   data:"folioCartaAdhesion="+folioCartaAdhesion,
			   success: function(data){
					$('#tipoCargaCer').html(data).ready(function () {
						$("#tipoCargaCer").fadeIn('slow');
						
					});
			   }
			});//fin ajax
	 });
}



function chkArchivoCertificadoDeposito(){
	//Valida que la informacion no se encuentre repetida
	var doc = $('#doc').val();
	if(doc == null || doc == ""){
		$('#dialogo_1').html('Debe seleccionar el Archivo a cargar');
   		abrirDialogo();
 		return false;
	}
}

/********      Constancias de Almacenamiento   *****/

function chkCamposRegistraConstanciasAlmacenamiento(){
	//Valida que la informacion no se encuentre repetida
	
	var numCampos = $('#numCampos').val();
	if(numCampos == null || numCampos == ""){
		$('#dialogo_1').html('Capture el dato en N�mero Campos a Capturar Constancias');
   		abrirDialogo();
 		return false;
		
	}else{
		var patron =/^\d{1,2}$/;
		if(!numCampos.match(patron)){
    		$('#dialogo_1').html('El valor del campo es inv�lido, se aceptan hasta 2 d�gitos');
       		abrirDialogo();
       		$('#numCampos').val(null);
       		return false;
    	}else{
    		var tempAlm = "", alm ="";
    		var tempFolio = "", folio = "";
    		var fechaExpedicion = "";
    		var volumen = "";
    		var i = 0, j = 0;
    		for (i=1;i<parseInt(numCampos)+1;i++){
    			alm = $('#alm'+i).val();
    			claveBodega = $('#claveBodega'+i).val();
    			folio = $('#folio'+i).val();
    			fechaExpedicion =  $('#fechaExpedicion'+i).val();
    			volumen = $('#v'+i).val();
    			if(alm==-1 || (claveBodega == "" || claveBodega == null) || (folio == "" || folio == null)  
    					|| (fechaExpedicion == "" || fechaExpedicion == null)
    					|| (volumen == "" || volumen == null)){
    	   			$('#dialogo_1').html('Capture los valores del registro '+i+' en la captura de constancias');
    		   		abrirDialogo();
    		 		return false;
    	   		}else{
    	   			//Valida que el volumen sea un valor valido
    	   			patron =/^\d{1,10}((\.\d{1,3})|(\.))?$/;
    	  			 if(!volumen.match(patron)){
    		   			$('#dialogo_1').html('El valor de volumen es inv�lido, se aceptan hasta 10 digitos a la izquierda y 3 digitos m�ximo a la derecha');
    			   		abrirDialogo();
    			 		return false;
    	  			 }
    	   			//Verifica que los valores capturados no se encuentren repetidos por almacenadora, bodega y folio
    	   			for (j=1;j<parseInt(numCampos)+1;j++){
    	   	   			if(i!=j){
    	   	   				tempAlm = $('#alm'+j).val();
    	   	   				tempFolio = $('#folio'+j).val();
    	   	   				if(alm == tempAlm && folio == tempFolio){
    	   	   					$('#dialogo_1').html('El registro '+i+' se encuentra repetido, favor de verificar');
    	   	   			   		abrirDialogo();
    	   	   			 		return false;
    	   	   				}
    	   	   			}	   				
    	   	   		}// end for j
				}
    		} //end for
    		
    	} //end else numcampos valido
		
	}//end else diferente de vacio o nulo
	
}// end chkCamposRegistraConstanciasAlmacenamiento


function seleccionaTipoCargaCons(){
	var tipoCargaCons = $('input:radio[name=tipoCargaCons]:checked').val();
	var urlenvio = "cargaIndividualCons";
	var folioCartaAdhesion = $('#folioCartaAdhesion').val();
	if(tipoCargaCons==1){
		urlenvio = "cargaMasivaCons";
	}
	$("#tipoCargaCons").fadeOut('slow', function() {
		$.ajax({
			   async: false,
			   type: "POST",
			   url: urlenvio,
			   data:"folioCartaAdhesion="+folioCartaAdhesion,
			   success: function(data){
					$('#tipoCargaCons').html(data).ready(function () {
						$("#tipoCargaCons").fadeIn('slow');
						
					});
			   }
			});//fin ajax
	 });
}

function chkArchivoConstanciasAlmacenamiento(){
	//Valida que la informacion no se encuentre repetida
	var doc = $('#doc').val();
	if(doc == null || doc == ""){
		$('#dialogo_1').html('Debe seleccionar el Archivo a cargar');
   		abrirDialogo();
 		return false;
	}
}

// Dictamen Auditor
function chkCamposregistraAuditorSolPago(){
	var i=0;
	var auditorV ="";
	var volumenAuditor= "";

	var numCamposAV = $('#numCamposAV').val();
	if(numCamposAV == null || numCamposAV == ""){
		$('#dialogo_1').html('Capture el N�mero de Campos Auditor con Volumen Dictaminado');
   		abrirDialogo();
		return false;
	}else{
		var patron =/^\d{1,3}$/;
		if(!numCamposAV.match(patron)){
			$('#dialogo_1').html('El valor del campo es inv�lido, se aceptan hasta 3 d�gitos');
	   		abrirDialogo();
	   		$('#numCamposAV').val(null);
	   		return false;
		}	
	}
	
	for (i=1;i<parseInt(numCamposAV)+1;i++){
		capNumeroAuditor = $('#capNumeroAuditor'+i).val();
		auditorV = $('#auditorV'+i).val();
		volumenAuditor = $('#v'+i).val();
		if(capNumeroAuditor == ""|| auditorV == -1 || volumenAuditor == "" ){
   			$('#dialogo_1').html('Capture los valores del registro '+i);
	   		abrirDialogo();
	 		return false;
   		 }
	}//end
	
}// end chkCamposregistraAuditorSolPago

function validarNumeroAuditor(numeroAuditor, count){
	if(numeroAuditor == null ||  numeroAuditor == ""){
        $("#validarNumeroAuditor").fadeOut('slow');
        return false;
	}
	
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "validarNumeroAuditor",
		   data: "numeroAuditor="+numeroAuditor+
		   "&count="+count,
		   success: function(data){
				$('#validarNumeroAuditor'+count).html(data).ready(function () {
					
				});
		   }
		});//fin ajax
}

function verificaObservacion(idExpediente){	
	var capObsExpediente = $('#capObsExpediente'+idExpediente).val();
	console.log('capObsExpediente '+ capObsExpediente);
	if(idExpediente==8){
		if(($('#capObsExpediente'+idExpediente)).is(":checked")) {
			$("#capObsExpediente9").attr("checked", "checked");
			$("#capObsExpediente9").attr('disabled','disabled'); 
		}else{	
			$("#capObsExpediente9").removeAttr("checked");
			//$("#capObsExpediente9").removeAttr('disabled'); 
		}
	
	}
}

function recuperaDocRequeridos(){
	//var doctosSinObservacion = $('input:checkbox[name=doctosSinObservacion]:checked').val();
	var habilitaAccionSP = $('input:radio[name=habilitaAccionSP]:checked').val();
	var folioCartaAdhesion = $('#folioCartaAdhesion').val();
	var estatusCA = $('#estatusCA').val();
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "recuperaDocRequeridos",
		   data:"folioCartaAdhesion="+folioCartaAdhesion+
		   		//"&doctosSinObservacion="+doctosSinObservacion+
		   		"&habilitaAccionSP="+habilitaAccionSP+
		   		"&estatusCA="+estatusCA,
		   success: function(data){
			   var $response=$(data);
			   var idExpedientesRequeridos = $response.filter('#idExpedientesRequeridos').text();
			   var myArray = idExpedientesRequeridos.split(',');
			   var i=0, color="";
			   color="#B82323";
			   
			   for(i=0; i< myArray.length; i++){
				   if(myArray[i]!=1){
					   $('#sp'+myArray[i]).css({"color":color});
					   $('#docRequerido'+myArray[i]).val(true); 
					   if(myArray[i] == 3){
						   $('#spv').css({"color":color});
					   }
					   if(myArray[i] == 5){
						   $('#spFE').css({"color":color});
					   }
				   }
			   }	
			   $('#spTC').css({"color":color});//Set tipo constancia como requerido
				$('#recuperaDocRequeridosYhabilitaOficioObs').html(data).ready(function () {
					$("#recuperaDocRequeridosYhabilitaOficioObs").fadeIn('slow');
					
				});
		   }
		});
}

function validarTipoConstancia(tipoConstancia){
	
	if(tipoConstancia==1){//Certificado de deposito
		$('#sp10').css({"color":"#B82323"});
		$('#sp34').css({"color":"#ffffff"});
		$('#docRequerido10').val(true); 
		$('#docRequerido34').val(false); 
	}else if(tipoConstancia==2){//Constancia de almacenamiento
		$('#sp34').css({"color":"#B82323"});
		$('#sp10').css({"color":"#ffffff"});
		$('#docRequerido34').val(true);
		$('#docRequerido10').val(false);
	}else{//ambos
		$('#sp10').css({"color":"#B82323"});
		$('#sp34').css({"color":"#B82323"});
		$('#docRequerido10').val(true);
		$('#docRequerido34').val(true);
	}
	
	
}
