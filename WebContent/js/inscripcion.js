$(document).ready(function(){
	$.ajaxSetup({
		'beforeSend' : function(xhr) {
			try{
				xhr.overrideMimeType('text/html; charset=iso-8859-1');
			}catch(e){}
		}});
});

function verificarDatosComprador(){
	var idComprador = $('#idComprador').val();
	var idPrograma = $('#idPrograma').val();
	if(idComprador == -1){
		$("#datosInscripcion").fadeOut('slow');
		return false;
	}
	if(idPrograma==-1){
		$('#dialogo_1').html('Debe seleccionar el programa para recuperar los ciclos del programa,');
		return false;
	}
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "verificarDatosComprador",
		   data: "idComprador="+idComprador+
		   		"&idPrograma="+idPrograma,
		   success: function(data){
				$('#datosInscripcion').html(data).ready(function () {
					$("#datosInscripcion").fadeIn('slow');
				});
		   }
		});//fin ajax
	
}
function chkCamposSolInscripcion(){	
	var registrar = $('#registrar').val();
	console.log("registrar 0"+registrar);
	var fechaPublicacionDOFInt = $('#fechaPublicacionDOFInt').val(); 
	var dia =0;
	var mes =0;
	var anio =0;
	
	if(registrar == 3){
		$("#idPrograma").removeAttr('disabled');
		$("#idComprador").removeAttr('disabled');
		$("#registrar").removeAttr('disabled');
		$("#idSI").removeAttr('disabled');
	}else{
		/****PROGAMA****/
		var idPrograma = $('#idPrograma').val();
		if(idPrograma == -1){
			$('#dialogo_1').html('Seleccione el programa');
			abrirDialogo();
			return false;
		}	
	}	
	
	/*****COMPRADOR****/
	var idComprador = $('#idComprador').val();
	if(idComprador == -1){
		$('#dialogo_1').html('Seleccione el comprador');
		abrirDialogo();
		return false;
	}
	
	/*****CICLO****/
	var idCicloPrograma = $('#idCicloPrograma').val();
	if(idCicloPrograma == -1){
		$('#dialogo_1').html('Seleccione el ciclo');
		abrirDialogo();
		return false;
	}
	/*****FOLIO INSCRIPCION****/
	var folioSI = $('#folioSI').val();
	if(folioSI == null || folioSI==""){
		$('#dialogo_1').html('Seleccione el n�mero de folio de la solicitud');
		abrirDialogo();
		return false;
	}else{
		/*****FOLIO INSCRIPCION EN CASO DE QUE LA CARTA DE ADHESION NO SEA GENERADO POR EL SISTEMA****/
		var cartaAdhesionSistema = $('#cartaAdhesionSistema').val();
		if(cartaAdhesionSistema==0){
			var acronimoCA = $('#acronimoCA').val();
			//Validar nomenclatura del folio de inscripcion	
			patron = /^[A-Z]{2,3}\-[A-Z]{2}\d{2}\-[A-Z]{1,5}-\d{3}$/;
			if(!folioSI.match(patron)){
				console.log("no match cadena "+folioSI);
				$('#dialogo_1').html('El folio es incorrecto, se debe capturar conforme a la siguiente nomenclatura ejemplo: '+acronimoCA+'-PV12-MAIZ-001');
		   		abrirDialogo();
		 		return false;
			}
			
		}		
		/** Valida si hubo un error en validacion de folio de la solicitud **/
		if ($('#errorProgramaYaExisteConfDoc').length){
			var errorProgramaYaExisteConfDoc = $('#errorProgramaYaExisteConfDoc').val();
			if(errorProgramaYaExisteConfDoc!=0){
				$('#dialogo_1').html('El folio de la solicitud ya se encuentra registrado, por favor verifique');
		   		abrirDialogo();
		 		return false;	
			}
		}
		
	}
	
	/*****CULTIVO****/
	var idCultivo = $('#idCultivo').val();
	if(idCultivo == -1){
		$('#dialogo_1').html('Seleccione el cultivo');
		abrirDialogo();
		return false;
	}
	
	/*****VOLUMEN O IMPORTE DEPENDE DEL CRITERIO DE PAGO****/
	var patron ="";
	var idCriterioPago = $('#idCriterioPago').val(); 
	if(idCriterioPago == 1 || idCriterioPago == 3){
		var volumenInscripcion = $('#volumenInscripcion').val();
		if(volumenInscripcion == null || volumenInscripcion==""){
			$('#dialogo_1').html('Capture el volumen de la solicitud');
			abrirDialogo();
			return false;
		}else{
			patron =/^\d{1,10}((\.\d{1,3})|(\.))?$/;
  			 if(!volumenInscripcion.match(patron)){
	   			$('#dialogo_1').html('El valor de volumen es inv�lido, se aceptan hasta 10 digitos a la izquierda y 3 digitos m�ximo a la derecha');
		   		abrirDialogo();
		 		return false;
  			 }
		}
	}else{
		var importeInscripcion = $('#importeInscripcion').val();
		if(importeInscripcion == null || importeInscripcion==""){
			$('#dialogo_1').html('Capture el importe de la solicitud');
			abrirDialogo();
			return false;
		}else{
			patron =/^\d{1,13}((\.\d{1,2})|(\.))?$/;
			if(!importeInscripcion.match(patron)){
				$('#dialogo_1').html('El valor del importe es inv�lido, se aceptan hasta 13 digitos a la izquierda con 2 m�ximo a la derecha');
		   		abrirDialogo();
		 		return false;
			}
		}
	}
	/****ARCHIVO DE LA SOLICITUD DE INSCRIPCI�N****/
	var docSI = $('#docSI').val();
	if(registrar == 0 || registrar == 1){
		if(docSI == null || docSI==""){
			$('#dialogo_1').html('Seleccione el archivo de la solicitud de inscripci�n');
			abrirDialogo();
			return false;
		}
	}
		
	/*****FECHA DOCUMENTO SI****/
	var fechaDocSI = $('#fechaDocSI').val();
	var fechaDocSITemp = 0;
	if(fechaDocSI == null || fechaDocSI==""){
		$('#dialogo_1').html('Seleccione la fecha del documento de la Solicitud de Inscripci�n');
		abrirDialogo();
		return false;
	}else{
		//Valida que la fecha de la solicitud no sea menor a la fecha de publicacion de DOF
		dia = fechaDocSI.substring(0,2);
		mes = fechaDocSI.substring(3,5);
		anio = fechaDocSI.substring(6,10);
		fechaDocSITemp = anio+""+""+mes+""+dia;
		if(parseInt(fechaDocSITemp) < fechaPublicacionDOFInt){
			console.log("La fecha es menor");
			$('#dialogo_1').html('La fecha de la solicitud no debe ser menor a la fecha de publicaci�n del DOF');
			abrirDialogo();
			return false;
		}
		
	}
	/*****FECHA ACUSE SI****/
	var fechaAcuseSI = $('#fechaAcuseSI').val();
	var fechaAcuseSITemp = 0;
	if(fechaAcuseSI == null || fechaAcuseSI==""){
		$('#dialogo_1').html('Seleccione la fecha del acuse de la Solicitud de Inscripci�n');
		abrirDialogo();
		return false;
	}else{
		dia = fechaAcuseSI.substring(0,2);
		mes = fechaAcuseSI.substring(3,5);
		anio = fechaAcuseSI.substring(6,10);
		fechaAcuseSITemp = anio+""+""+mes+""+dia;
		if(parseInt(fechaAcuseSITemp) < fechaDocSITemp){
			$('#dialogo_1').html('La fecha del acuse de la solicitud no debe ser menor a la fecha del documento');
			abrirDialogo();
			return false;
		}
	}
	
	/*****AUDITOR****/
	var numeroAuditor = $('#numeroAuditor').val();
	if(numeroAuditor == -1){
		$('#dialogo_1').html('Seleccione el n�mero de auditor');
		abrirDialogo();
		return false;
	}
	
	
	/*****ARCHIVO DE OFICIO DE PETICION DE ACREDITACION****/
	var docOPA = $('#docOPA').val();
	if(registrar == 0){
		if(docOPA == null || docOPA==""){
			$('#dialogo_1').html('Seleccione el archivo del oficio de petici�n de acreditaci�n');
			abrirDialogo();
			return false;
		}
	}
	/*****FECHA DE DOCUMENTO DE OFICIO DE PETICION DE ACREDITACION****/
	var fechaDocOPA = $('#fechaDocOPA').val();
	var fechaDocOPATemp = 0;
	if(fechaDocOPA == null || fechaDocOPA==""){
		$('#dialogo_1').html('Seleccione la fecha del documento de oficio de petici�n de acreditaci�n');
		abrirDialogo();
		return false;
	}else{
		//Valida que la fecha del oficio de peticion de acreditaci�n no sea menor a la fecha de publicacion de DOF
		dia = fechaDocOPA.substring(0,2);
		mes = fechaDocOPA.substring(3,5);
		anio = fechaDocOPA.substring(6,10);
		fechaDocOPATemp = anio+""+""+mes+""+dia;
		if(parseInt(fechaDocOPATemp) < fechaPublicacionDOFInt){
			$('#dialogo_1').html('La fecha del oficio de petici�n de acreditaci�n no debe ser menor a la fecha de publicaci�n del DOF');
			abrirDialogo();
			return false;
		}
	}
	/*****FECHA DE ACUSE DE OFICIO DE PETICION DE ACREDITACION****/
	var fechaAcuseOPA = $('#fechaAcuseOPA').val();
	var fechaAcuseOPATemp =0;
	if(fechaAcuseOPA == null || fechaAcuseOPA==""){
		$('#dialogo_1').html('Seleccione la fecha del acuse de oficio de petici�n de acreditaci�n');
		abrirDialogo();
		return false;
	}else{
		dia = fechaAcuseOPA.substring(0,2);
		mes = fechaAcuseOPA.substring(3,5);
		anio = fechaAcuseOPA.substring(6,10);
		fechaAcuseOPATemp = anio+""+""+mes+""+dia;
		if(parseInt(fechaAcuseOPATemp) < fechaDocOPATemp){
			$('#dialogo_1').html('La fecha del acuse del oficio de petici�n de acreditaci�n no debe ser menor a la fecha del documento');
			abrirDialogo();
			return false;
		}
	}
	
	/*****NO DE OFICIO DE PETICION DE ACREDITACION****/
	var noOficioPA = $('#noOficioPA').val();
	if(noOficioPA == null || noOficioPA==""){
		$('#dialogo_1').html('Capture el n�mero de oficio de petici�n de acreditaci�n');
		abrirDialogo();
		return false;
	}
			
	/*****ACREDITACION JURIDICA****/
	var acreditacionJuridica = $('input:radio[name=acreditacionJuridica]:checked').val();
	if(acreditacionJuridica == 0 || acreditacionJuridica == 1){
		/*****ARCHIVO DE OFICIO DE ACREDITACION****/
		var docOA = $('#docOA').val();
		var docOAFileName ="";
		if(registrar == 3){
			docOAFileName = $('#docOAFileName').val();
		}
		console.log("docOAFileName"+docOAFileName);
		console.log("registrar"+registrar);
		if(registrar == 0 || (registrar==3 && (docOAFileName=='' || docOAFileName==null))){
			if(docOA == null || docOA==""){
				$('#dialogo_1').html('Seleccione el archivo del oficio de acreditaci�n');
				abrirDialogo();
				return false;
			}
		}
		
		/*****FECHA DE DOCUMENTO DE OFICIO DE ACREDITACION****/
		var fechaDocOA = $('#fechaDocOA').val();
		var fechaDocOATemp = 0;
		if(fechaDocOA == null || fechaDocOA==""){
			$('#dialogo_1').html('Seleccione la fecha del documento de oficio de acreditaci�n');
			abrirDialogo();
			return false;
		}else{
			//Valida que la fecha del oficio de peticion de acreditaci�n no sea menor a la fecha de publicacion de DOF
			dia = fechaDocOA.substring(0,2);
			mes = fechaDocOA.substring(3,5);
			anio = fechaDocOA.substring(6,10);
			fechaDocOATemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaDocOATemp) < fechaPublicacionDOFInt){
				$('#dialogo_1').html('La fecha del oficio de acreditaci�n no debe ser menor a la fecha de publicaci�n del DOF');
				abrirDialogo();
				return false;
			}

		}
		/*****FECHA DE ACUSE DE OFICIO DE ACREDITACION****/
		var fechaAcuseOA = $('#fechaAcuseOA').val();
		var fechaAcuseOATemp = 0;
		if(fechaAcuseOA == null || fechaAcuseOA==""){
			$('#dialogo_1').html('Seleccione la fecha del acuse de oficio de acreditaci�n');
			abrirDialogo();
			return false;
		}else{
			dia = fechaAcuseOA.substring(0,2);
			mes = fechaAcuseOA.substring(3,5);
			anio = fechaAcuseOA.substring(6,10);
			fechaAcuseOATemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaAcuseOATemp) < fechaDocOATemp){
				$('#dialogo_1').html('La fecha del acuse del oficio de acreditaci�n no debe ser menor a la fecha del documento');
				abrirDialogo();
				return false;
			}
		}
		
		/*****NO DE OFICIO DE ACREDITACION****/
		var noOficioA = $('#noOficioA').val();
		if(noOficioA == null || noOficioA==""){
			$('#dialogo_1').html('Capture el n�mero de oficio de acreditaci�n');
			abrirDialogo();
			return false;
		}
		
	}else{
		/*****ARCHIVO DE OFICIO DE OBSERVACION DE ACREDITACION****/
		var docOOA = $('#docOOA').val();
		var docOOAFileName ="";
		if(registrar == 3){
			docOOAFileName = $('#docOOAFileName').val();
		}
		if(registrar == 0 || (registrar==3 && docOOAFileName!='' && docOOAFileName!=null)){
			if(docOOA == null || docOOA==""){
				$('#dialogo_1').html('Seleccione el archivo del oficio de observacion de acreditacion');
				abrirDialogo();
				return false;
			}
		}
		/*****FECHA DE DOCUMENTO DE OFICIO DE OBSERVACION DE ACREDITACION****/
		var fechaDocOOA = $('#fechaDocOOA').val();
		var fechaDocOOATemp = $('#fechaDocOOA').val();
		if(fechaDocOOA == null || fechaDocOOA==""){
			$('#dialogo_1').html('Seleccione la fecha del documento de oficio de observaci�n de acreditaci�n');
			abrirDialogo();
			return false;
		}else{
			//Valida que la fecha del oficio de peticion de acreditaci�n no sea menor a la fecha de publicacion de DOF
			dia = fechaDocOOA.substring(0,2);
			mes = fechaDocOOA.substring(3,5);
			anio = fechaDocOOA.substring(6,10);
			fechaDocOOATemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaDocOOATemp) < fechaPublicacionDOFInt){
				$('#dialogo_1').html('La fecha del oficio de observaci�n de acreditaci�n no debe ser menor a la fecha de publicaci�n del DOF');
				abrirDialogo();
				return false;
			}

		}
		
		/*****FECHA DE ACUSE DE OFICIO DE OBSERVACION DE ACREDITACION****/
		var fechaAcuseOOA = $('#fechaAcuseOOA').val();
		var fechaAcuseOOATemp = 0;
		if(fechaAcuseOOA == null || fechaAcuseOOA==""){
			$('#dialogo_1').html('Seleccione la fecha del acuse de oficio de observaci�n de acreditaci�n');
			abrirDialogo();
			return false;
		}else{
			dia = fechaAcuseOOA.substring(0,2);
			mes = fechaAcuseOOA.substring(3,5);
			anio = fechaAcuseOOA.substring(6,10);
			fechaAcuseOOATemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaAcuseOOATemp) < fechaDocOOATemp){
				$('#dialogo_1').html('La fecha del acuse de oficio de observaci�n no debe ser menor a la fecha del documento');
				abrirDialogo();
				return false;
			}
		}
		
		/*****NO DE OFICIO DE OBSERVACION DE ACREDITACION****/
		var noOficioOA = $('#noOficioOA').val();
		if(noOficioOA == null || noOficioOA==""){
			$('#dialogo_1').html('Capture el n�mero de oficio de observaci�n de acreditaci�n');
			abrirDialogo();
			return false;
		}
	}//end acreditacion con observaciones
	
	/*****ACREDITACION DE SOLICITUD DE INSCRIPCION****/
	var acreditacionSI = $('input:radio[name=acreditacionSI]:checked').val();	
	if(acreditacionSI == 2){
		/*****OBSERVACION DE ACREDITACION DE SOLICITUD DE INSCRIPCION****/
		var obsSolInscripcion = $('#obsSolInscripcion').val();
		if(obsSolInscripcion == null || obsSolInscripcion==""){
			$('#dialogo_1').html('Debe capturar el motivo de observaci�n de la solicitud de inscripci�n ');
			abrirDialogo();
			return false;
		}

	}//end acreditacion de dictaminacion

	
	/*****ACREDITACION DICTAMINACION****/
	var acreditacionDictamen = $('input:radio[name=acreditacionDictamen]:checked').val();	
	if(acreditacionDictamen == 2){
		/*****OBSERVACION DE ACREDITACION****/
		var obsDictaminacion = $('#obsDictaminacion').val();
		if(obsDictaminacion == null || obsDictaminacion==""){
			$('#dialogo_1').html('Debe capturar el motivo de observaci�n de la dictaminaci�n');
			abrirDialogo();
			return false;
		}

	}else{// acreditacion negativa o positiva
		var fechaDocDic = $('#fechaDocDic').val();
		var fechaDocDicTemp = 0;
		if(fechaDocDic != null && fechaDocDic==""){
			dia = fechaDocDic.substring(0,2);
			mes = fechaDocDic.substring(3,5);
			anio = fechaDocDic.substring(6,10);
			fechaDocDicTemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaDocDicTemp) < fechaPublicacionDOFInt){
				$('#dialogo_1').html('La fecha del oficio de dictaminaci�n no debe ser menor a la fecha de publicaci�n del DOF');
				abrirDialogo();
				return false;
			}
		}
		
		var fechaAcuseDic = $('#fechaDocDic').val();
		var fechaAcuseDicTemp = 0;
		if(fechaAcuseDic != null && fechaAcuseDic==""){
			dia = fechaAcuseDic.substring(0,2);
			mes = fechaAcuseDic.substring(3,5);
			anio = fechaAcuseDic.substring(6,10);
			fechaAcuseDicTemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaAcuseDicTemp) < fechaDocDicTemp){
				$('#dialogo_1').html('La fecha del acuse del oficio de dictaminaci�n no debe ser menor a la fecha del documento');
				abrirDialogo();
				return false;
			}
		}
		
	}//end acreditacion de dictaminacion
	
	/*****ALCANCE DE LA SOLICITUD DE INSCRIPCION****/
	if(registrar == 4){
		$("#idPrograma").removeAttr('disabled');
		$("#idComprador").removeAttr('disabled');
		var docASI = $('#docASI').val();
		if(docASI == null || docASI==""){
			$('#dialogo_1').html('Seleccione el archivo del oficio de alcance de la solicitud de inscripci�n');
			abrirDialogo();
			return false;
		}
		
		/*****FECHA DE DOCUMENTO DE OFICIO DE ALCANCE DE LA SOLICITUD****/
		var fechaDocASI = $('#fechaDocASI').val();
		var fechaDocASITemp = "";
		if(fechaDocASI == null || fechaDocASI==""){
			$('#dialogo_1').html('Seleccione la fecha del documento de alcance de la solicitud de inscripci�n');
			abrirDialogo();
			return false;
		}else{
			//Valida que la fecha del oficio de peticion de acreditaci�n no sea menor a la fecha de publicacion de DOF
			dia = fechaDocASI.substring(0,2);
			mes = fechaDocASI.substring(3,5);
			anio = fechaDocASI.substring(6,10);
			fechaDocASITemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaDocASITemp) < fechaPublicacionDOFInt){
				$('#dialogo_1').html('La fecha del oficio del alcance de la solicitud de inscripci�n no debe ser menor a la fecha de publicaci�n del DOF');
				abrirDialogo();
				return false;
			}

		}
		
		/*****FECHA DE ACUSE DE OFICIO DE OBSERVACION DE ACREDITACION****/
		var fechaAcuseASI = $('#fechaAcuseASI').val();
		var fechaAcuseOOATemp = "";
		if(fechaAcuseASI == null || fechaAcuseASI==""){
			$('#dialogo_1').html('Seleccione la fecha del acuse de oficio de observaci�n de acreditaci�n');
			abrirDialogo();
			return false;
		}else{
			dia = fechaAcuseASI.substring(0,2);
			mes = fechaAcuseASI.substring(3,5);
			anio = fechaAcuseASI.substring(6,10);
			fechaAcuseOOATemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaAcuseOOATemp) < fechaDocASITemp){
				$('#dialogo_1').html('La fecha del acuse de oficio de alcance no debe ser menor a la fecha del documento');
				abrirDialogo();
				return false;
			}
		}
		
		/*****NO DE OFICIO DE OBSERVACION DE ACREDITACION****/
		var noOficioASI = $('#noOficioASI').val();
		if(noOficioASI == null || noOficioASI==""){
			$('#dialogo_1').html('Capture el n�mero de oficio de alcance de la solicitud de inscripci�n');
			abrirDialogo();
			return false;
		}
	}// end registrar == 4
	
	

}//end chkCamposSolInscripcion


function chkCamposBusquedaSI(){
	var folioSI = $('#folioSI').val();
	var folioCartaAdhesion = $('#folioCartaAdhesion').val();
	var idPrograma = $('#idPrograma').val();
	
	if(idPrograma == -1 && (folioSI == null || folioSI=="") 
		&&(folioCartaAdhesion == null || folioCartaAdhesion=="" )){
		$('#dialogo_1').html('Debe seleccionar al menos un dato, para consultar la solicitud');
		abrirDialogo();
		return false;
	}
}

function chkFileExpediente(id){
	var val = document.getElementById("f"+id).value;
	if(val==null || val == ""){
		alert("AVISO: Seleccione el archivo por favor");
		return false;
	}
	return true;
}

function chkFileExpedienteObservacion(id){
	var val = document.getElementById("fo"+id).value;
	if(val==null || val == ""){
		alert("AVISO: Seleccione el archivo por favor");
		return false;
	}
	return true;
}

function agregaCultivoEstado(){
	var numCampos = $('#numCampos').val();
		
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "agregaCultivoEstado",
		   data: "numCampos="+numCampos,
		   success: function(data){
				$('#agregaCultivoEstado').html(data).ready(function () {
					$("#agregaCultivoEstado").fadeIn('slow');
					
				});
		   }
		});//fin ajax
}

/***************** INSCRIPCION ***************/
function acreditarSI(){
	var acreditacionSI = $('input:radio[name=acreditacionSI]:checked').val();	
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "acreditarSI",
		   data: "acreditacionSI="+acreditacionSI, 
		   success: function(data){
				$('#acreditarSI').html(data).ready(function () {
					$("#acreditarSI").fadeIn('slow');
					
				});
		   }
		});//fin ajax
	
	
}
function acreditar(){	
	var acreditacionJuridica = $('input:radio[name=acreditacionJuridica]:checked').val();
	/*var acreditacionTecnica = "";
	if($("#acreditacionTecnica").is(":checked")) {
		acreditacionTecnica = 1;
	}else{
		acreditacionTecnica = 0;
	}*/
	var idPrograma = $('#idPrograma').val();
	var registrar = $('#registrar').val();
	
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "acreditar",
		   data: "idPrograma="+idPrograma+
		   		"&acreditacionJuridica="+acreditacionJuridica+
		   		"&registrar="+registrar, 
		   success: function(data){
				$('#acreditacion').html(data).ready(function () {
					$("#acreditacion").fadeIn('slow');
					
				});
		   }
		});//fin ajax
	
}


function acreditarDictaminacion(){	
	var acreditacionDictamen = $('input:radio[name=acreditacionDictamen]:checked').val();	
	var registrar = $('#registrar').val();
		
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "acreditarDictaminacion",
		   data: "idPrograma="+idPrograma+
		   		"&acreditacionDictamen="+acreditacionDictamen+
		   		"&registrar="+registrar, 
		   success: function(data){
				$('#acreditarDictaminacion').html(data).ready(function () {
					$("#acreditarDictaminacion").fadeIn('slow');
					
				});
		   }
		});//fin ajax
	
}
function agregaCultivoEstadoIniProg(){
	var numCampos = $('#numCampos').val();
	
	
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "agregaCultivoEstadoIniProg",
		   data: "numCampos="+numCampos, 
		   success: function(data){
				$('#agregaCultivoEstadoIniProg').html(data).ready(function () {
					$("#agregaCultivoEstadoIniProg").fadeIn('slow');
					
				});
		   }
		});//fin ajax
}

function recuperaCultivoByVariedad(idCultivo, count){
		
	if(idCultivo == -1){
		return false;
	}

	$.ajax({
		   async: false,
		   type: "POST",
		   url: "recuperaCultivoByVariedad",
		   data: "idCultivo="+idCultivo+
		   		"&count="+count,	
		   success: function(data){
				$("#variedad"+count).html(data).ready(function () {
					//$("#prueba").fadeIn('slow');
					
				});
		   }
		});//fin ajax
}

function recuperaCultivoByVariedadAsigCA(idCultivo, count){
	
	if(idCultivo == -1){
		return false;
	}
	var idInicializacionEsquema = $('#idInicializacionEsquema').val();
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "recuperaCultivoByVariedadAsigCA",
		   data: "idCultivo="+idCultivo+
		   		"&count="+count+
		   		"&idInicializacionEsquema="+idInicializacionEsquema,	
		   success: function(data){
				$("#variedad"+count).html(data).ready(function () {
					
				});
		   }
		});//fin ajax
}


function recuperaCultivoByVariedadVXCV(idCultivo, count){
	if(idCultivo == -1){
		return false;
	}
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "recuperaCultivoByVariedadVXCV",
		   data: "idCultivo="+idCultivo+
		   		 "&count="+count,	
		   success: function(data){
				$("#contenedorVariedadVXCV"+count).html(data).ready(function () {
					//$("#prueba").fadeIn('slow');
					
				});
		   }
		});//fin ajax
}

function consigueNumCiclos(numCiclo){
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "consigueNumCiclos",
		   data: "numCiclos="+numCiclo, 
		   success: function(data){
				$('#consigueNumCiclos').html(data).ready(function () {
					$("#consigueNumCiclos").fadeIn('slow');
					
				});
		   }
		});//fin ajax
}

function chkCamposACA(){
	var dia=0, mes=0,  anio=0, patron="", ext;
	var patronDocumento = /^(xlsx|xls|doc|docx|jpg|png|pdf|txt)$/;
	var fechaDOCSIInt = $('#fechaDOCSIInt').val();
	var idCriterioPago = $('#idCriterioPago').val();
	var volumenXCultivoVariedad = $('#volumenXCultivoVariedad').val();
	var registrar = $('#registrar').val();
	var cult = -1, edo = -1, variedad = -1, volumen ="", importe ="";
	var tempCult ="", tempEdo ="", tempVariedad ="", i = 0, j = 0;
	var totalVolumen=0, totalImporte=0, volumenMaximoCultivoVariedad=0;
	if(registrar==0){
		var docCartaA = $('#docCartaA').val();
		if(docCartaA==null || docCartaA == ""){//Valida que el archivo de la carta de adhesi�n sea cargada
			$('#dialogo_1').html('Seleccione el archivo de la carta de adhesi�n.');
			abrirDialogo();
		    return false;
		}else{//end doc ==null
			ext = docCartaA.toLowerCase().substring(docCartaA.lastIndexOf(".")+1);
			if(!ext.match(patronDocumento)){
				$('#dialogo_1').html('Extensi�n no permitida para el archivo de la carta de adehsi�n, solo puede cargar archivos con extensiones xlsx, xls, doc, docx, jpg, png, pdf � txt');
		   		abrirDialogo();
				return false;
			}
		}
		var fechaFirmaCA = $('#fechaFirmaCA').val();
		if(fechaFirmaCA==null || fechaFirmaCA == ""){
			$('#dialogo_1').html('Seleccione la fecha de firma de la carta de Adhesi�n');
			abrirDialogo();
		    return false;
		}else{
			//Valida que la fecha de la carta de adhesion no debe ser menor a la fecha de documento de la solicitud de inscripci�n
			dia = fechaFirmaCA.substring(0,2);
			mes = fechaFirmaCA.substring(3,5);
			anio = fechaFirmaCA.substring(6,10);
			var fechaFirmaCATemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaFirmaCATemp) < fechaDOCSIInt){
				console.log("La fecha es menor");
				$('#dialogo_1').html('La fecha de la carta de adhesion no debe ser menor a la fecha de documento de la solicitud de inscripci�n');
				abrirDialogo();
				return false;
			}
		}		
		var docOL = $('#docOL').val();
		if(docOL==null || docOL == ""){
			$('#dialogo_1').html('Seleccione el archivo de oficio de elegibilidad.');
			abrirDialogo();
		    return false;
		}else{//end doc ==null
			ext = docOL.toLowerCase().substring(docOL.lastIndexOf(".")+1);
			if(!ext.match(patronDocumento)){
				$('#dialogo_1').html('Extensi�n no permitida para el oficio de elegibilidad, solo puede cargar archivos con extensiones xlsx, xls, doc, docx, jpg, png, pdf � txt');
		   		abrirDialogo();
				return false;
			}
		}
		var fechaDocLeg = $('#fechaDocLeg').val();
		if(fechaDocLeg==null || fechaDocLeg == ""){
			$('#dialogo_1').html('Seleccione la fecha de documento del oficio de legibilidad.');
			abrirDialogo();
		    return false;
		}else{
			//Valida que la fecha del oficio de elegibilidad no sea menor a la fecha de documento si
			dia = fechaDocLeg.substring(0,2);
			mes = fechaDocLeg.substring(3,5);
			anio = fechaDocLeg.substring(6,10);
			var fechaDocLegTemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaDocLegTemp) < fechaDOCSIInt){
				$('#dialogo_1').html('La fecha del documento de elegibilidad no debe ser menor a la fecha de documento de la solicitud de inscripci�n');
				abrirDialogo();
				return false;
			}
			
		}
		var fechaAcuseLeg = $('#fechaAcuseLeg').val();
		if(fechaAcuseLeg==null || fechaAcuseLeg == ""){
			$('#dialogo_1').html('Seleccione la fecha de acuse del oficio de legibilidad.');
			abrirDialogo();
		    return false;
		}else{
			dia = fechaAcuseLeg.substring(0,2);
			mes = fechaAcuseLeg.substring(3,5);
			anio = fechaAcuseLeg.substring(6,10);
			var fechaAcuseLegTemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaAcuseLegTemp) < fechaDocLegTemp){
				$('#dialogo_1').html('La fecha del acuse del oficio de elegibilidad no debe ser menor a la fecha del documento');
				abrirDialogo();
				return false;
			}
		}
		var noOficioLeg = $('#noOficioLeg').val();
		if(noOficioLeg==null || noOficioLeg == ""){
			$('#dialogo_1').html('Seleccione el n�mero de oficio de legibilidad.');
			abrirDialogo();
		    return false;
		}


		var numCampos = $('#numCampos').val();
		if(numCampos == null || numCampos == ""){
			$('#dialogo_1').html('Capture en n�mero de campos de cultivo, variedad, estado a apoyar.');
			abrirDialogo();       		
			return false;
		}
		patron =/^\d{1,3}$/;
		if(!numCampos.match(patron)){
			$('#dialogo_1').html('El valor del campo es inv�lido, se aceptan hasta 3 d�gitos');
	   		abrirDialogo();
	   		$('#numCampos').val(null);
	   		return false;
		}  	
		
		for (i=1;i<parseInt(numCampos)+1;i++){
			cult = $('#c'+i).val();
			edo = $('#e'+i).val();
			variedad = $('#va'+i).val();			
			if(idCriterioPago == 1 || idCriterioPago == 3){
				volumen = $('#v'+i).val();
				if(volumenXCultivoVariedad == 1){
					if(cult==-1 || edo==-1 ||  variedad==-1 || (volumen == "" || volumen == null)){
			   			$('#dialogo_1').html('Capture los valores del registro '+i+' en los  estados a apoyar');
				   		abrirDialogo();
				 		return false;
			   		 }
					$("#respVXCV"+i).find('input').removeAttr('disabled');
					volumenMaximoCultivoVariedad =  $('#respVXCV'+i).val();
				}else{
					if(cult==-1 || edo==-1 || (volumen == "" || volumen == null)){
			   			$('#dialogo_1').html('Capture los valores del registro '+i+' en los  estados a apoyar');
				   		abrirDialogo();
				 		return false;
			   		 }
				}
				var patron =/^\d{1,10}((\.\d{1,3})|(\.))?$/;
				if(!volumen.match(patron)){
					$('#dialogo_1').html('El valor del volumen m�ximo a apoyar es inv�lido, se aceptan hasta 10 d�gitos a la izquierda y 3 d�gitos m�ximo a la derecha');
					abrirDialogo();
					return false;
				}else{
					if(volumenXCultivoVariedad==1){
						console.log("Valida el volumen maximo por cultio variedad");
						//Valida el volumen maximo por cultivo variedad
						if(parseFloat(volumen) > parseFloat(volumenMaximoCultivoVariedad)){
							$('#dialogo_1').html('El valor del volumen no debe rebasar el m�ximo por cultivo variedad en el registro '+i);
							abrirDialogo();
							return false;
						}
						
					}
				}			
				totalVolumen = (totalVolumen+parseFloat(volumen));
				// end criterio pago por volumen o volumen/etapa
			}else if (idCriterioPago == 2){ 
				importe = $('#i'+i).val();
				if(cult==-1 || edo==-1  || (importe == "" || importe == null)){
		   			$('#dialogo_1').html('Capture los valores del registro '+i+' en los cultivo, variedad, estados a apoyar');
			   		abrirDialogo();
			 		return false;
				}
				var patron =/^\d{1,10}((\.\d{1,2})|(\.))?$/;
				if(!importe.match(patron)){
					$('#dialogo_1').html('El valor del importe m�ximo a apoyar es inv�lido, se aceptan hasta 10 d�gitos a la izquierda y 2 d�gitos m�ximo a la derecha');
					abrirDialogo();
					return false;
				}
				totalImporte = (totalImporte+parseFloat(importe));
			}
	   		for (j=1;j<parseInt(numCampos)+1;j++){
	   			if(i!=j){
	   				tempCult = $('#c'+j).val();
	   				tempEdo = $('#e'+j).val();
	   				tempVariedad = $('#va'+j).val();
	   				if(cult == tempCult && edo == tempEdo && variedad == tempVariedad){
	   					$('#dialogo_1').html('El registro '+i+' se encuentra repetido, favor de verificar');
	   			   		abrirDialogo();
	   			 		return false;
	   				}
	   			}	   				
	   		}// end for j
	   							   			
		}// end for	
		if(idCriterioPago == 1 || idCriterioPago==3){
			var volumenDisponibleValidar = $('#volumenDisponibleValidar').val();
			if(parseFloat(totalVolumen) > parseFloat(volumenDisponibleValidar)){
				$('#dialogo_1').html('El volumen total asignado '+totalVolumen+' no puede exceder del volumen disponible, favor de verificar');
			   		abrirDialogo();
			 		return false;
			}
			//Validar que el volumen del apoyo no exceda del volumen de la solicitud de inscripcion
			var volumenInscripcionValidar = $('#volumenInscripcionValidar').val();
			if(parseFloat(totalVolumen) > parseFloat(volumenInscripcionValidar)){
				console.log("totalVolumen "+totalVolumen);
				$('#dialogo_1').html('El volumen total asignado '+totalVolumen+' no puede exceder del volumen de la solicitud, favor de verificar');
			   		abrirDialogo();
			 		return false;
			}
		}else{
			var importeDisponibleValidar = $('#importeDisponibleValidar').val();
			if(parseFloat(totalImporte) > parseFloat(importeDisponibleValidar)){
				$('#dialogo_1').html('El importe total asignado '+totalImporte+' no puede exceder del importe disponible, favor de verificar');
			   		abrirDialogo();
			 		return false;
			}
			var importeInscripcionValidar = $('#importeInscripcionValidar').val();
			if(parseFloat(totalImporte) > parseFloat(importeInscripcionValidar)){
				$('#dialogo_1').html('El importe total asignado '+totalImporte+' no puede exceder del importe de la solicitud, favor de verificar');
			   		abrirDialogo();
			 		return false;
			}
		}
	}//end registrar == 0
	
	if($('#complementoPorampliacionChk').length > 0){
		//Valida los datos cuando existe un complemento
		var docAmpliacionCA = $('#docAmpliacionCA').val();
		if(docAmpliacionCA==null || docAmpliacionCA == ""){//Valida que el archivo del complemento de la carta de adhesi�n sea cargada
			$('#dialogo_1').html('Seleccione el archivo del complemento de la carta de adhesi�n.');
			abrirDialogo();
		    return false;
		}else{//end doc ==null
			ext = docAmpliacionCA.toLowerCase().substring(docAmpliacionCA.lastIndexOf(".")+1);
			if(!ext.match(patronDocumento)){
				$('#dialogo_1').html('Extensi�n no permitida para el archivo del complemento de la carta de adhesi�n, solo puede cargar archivos con extensiones xlsx, xls, doc, docx, jpg, png, pdf � txt');
		   		abrirDialogo();
				return false;
			}
		}	
		var fechaFirmaCAComplemento = $('#fechaFirmaCAComplemento').val();
		if(fechaFirmaCAComplemento==null || fechaFirmaCAComplemento == ""){
			$('#dialogo_1').html('Seleccione la fecha de firma de la carta de Adhesi�n');
			abrirDialogo();
		    return false;
		}else{
			//Valida que la fecha de la carta de adhesion no debe ser menor a la fecha de documento de la solicitud de inscripci�n
			dia = fechaFirmaCAComplemento.substring(0,2);
			mes = fechaFirmaCAComplemento.substring(3,5);
			anio = fechaFirmaCAComplemento.substring(6,10);
			var fechaFirmaCATemp = anio+""+""+mes+""+dia;
			if(parseInt(fechaFirmaCATemp) < fechaDOCSIInt){
				$('#dialogo_1').html('La fecha del complemento de la carta de adhesi�n no debe ser menor a la fecha de documento de la solicitud de inscripci�n');
				abrirDialogo();
				return false;
			}
		}
		var numCamposComp = $('#numCamposComp').val();
		if(numCamposComp == null || numCamposComp == ""){
			$('#dialogo_1').html('Capture en n�mero de campos de cultivo, variedad, estado a apoyar en el complemento de la carta');
			abrirDialogo();       		
			return false;
		}
		patron =/^\d{1,3}$/;
		if(!numCamposComp.match(patron)){
			$('#dialogo_1').html('El valor del campo es inv�lido, se aceptan hasta 3 d�gitos');
	   		abrirDialogo();
	   		$('#numCamposComp').val(null);
	   		return false;
		}  
		
		for (i=1;i<parseInt(numCamposComp)+1;i++){
			cult = $('#c'+i).val();
			edo = $('#e'+i).val();
			variedad = $('#va'+i).val();			
			if(idCriterioPago == 1 || idCriterioPago == 3){
				volumen = $('#v'+i).val();
				if(volumenXCultivoVariedad == 1){
					if(cult==-1 || edo==-1 ||  variedad==-1 || (volumen == "" || volumen == null)){
			   			$('#dialogo_1').html('Capture los valores del registro '+i+' en los  estados a apoyar');
				   		abrirDialogo();
				 		return false;
			   		 }
					$("#respVXCV"+i).find('input').removeAttr('disabled');
					volumenMaximoCultivoVariedad =  $('#respVXCV'+i).val();
				}else{
					if(cult==-1 || edo==-1 || (volumen == "" || volumen == null)){
			   			$('#dialogo_1').html('Capture los valores del registro '+i+' en los  estados a apoyar');
				   		abrirDialogo();
				 		return false;
			   		 }
				}
				var patron =/^\d{1,10}((\.\d{1,3})|(\.))?$/;
				if(!volumen.match(patron)){
					$('#dialogo_1').html('El valor del volumen m�ximo a apoyar es inv�lido, se aceptan hasta 10 d�gitos a la izquierda y 3 d�gitos m�ximo a la derecha');
					abrirDialogo();
					return false;
				}else{
					if(volumenXCultivoVariedad==1){
						console.log("Valida el volumen maximo por cultio variedad");
						//Valida el volumen maximo por cultivo variedad
						if(parseFloat(volumen) > parseFloat(volumenMaximoCultivoVariedad)){
							$('#dialogo_1').html('El valor del volumen no debe rebasar el m�ximo por cultivo variedad en el registro '+i);
							abrirDialogo();
							return false;
						}
						
					}
				}			
				totalVolumen = (totalVolumen+parseFloat(volumen));
				// end criterio pago por volumen o volumen/etapa
			}else if (idCriterioPago == 2){ 
				importe = $('#i'+i).val();
				if(cult==-1 || edo==-1  || (importe == "" || importe == null)){
		   			$('#dialogo_1').html('Capture los valores del registro '+i+' en los cultivo, variedad, estados a apoyar');
			   		abrirDialogo();
			 		return false;
				}
				var patron =/^\d{1,10}((\.\d{1,2})|(\.))?$/;
				if(!importe.match(patron)){
					$('#dialogo_1').html('El valor del importe m�ximo a apoyar es inv�lido, se aceptan hasta 10 d�gitos a la izquierda y 2 d�gitos m�ximo a la derecha');
					abrirDialogo();
					return false;
				}
				totalImporte = (totalImporte+parseFloat(importe));
			}
	   		for (j=1;j<parseInt(numCamposComp)+1;j++){
	   			if(i!=j){
	   				tempCult = $('#c'+j).val();
	   				tempEdo = $('#e'+j).val();
	   				tempVariedad = $('#va'+j).val();
	   				if(cult == tempCult && edo == tempEdo && variedad == tempVariedad){
	   					$('#dialogo_1').html('El registro '+i+' se encuentra repetido, favor de verificar');
	   			   		abrirDialogo();
	   			 		return false;
	   				}
	   			}	   				
	   		}// end for j
	   							   			
		}// end for	complementoPorampliacionChk
		if(idCriterioPago == 1 || idCriterioPago==3){
			var volumenDisponibleValidar = $('#volumenDisponibleValidar').val();
			if(parseFloat(totalVolumen) > parseFloat(volumenDisponibleValidar)){
				$('#dialogo_1').html('El volumen total asignado '+totalVolumen+' no puede exceder del volumen disponible, favor de verificar');
			   		abrirDialogo();
			 		return false;
			}
		}else{
			var importeDisponibleValidar = $('#importeDisponibleValidar').val();
			if(parseFloat(totalImporte) > parseFloat(importeDisponibleValidar)){
				$('#dialogo_1').html('El importe total asignado '+totalImporte+' no puede exceder del importe disponible, favor de verificar');
			   		abrirDialogo();
			 		return false;
			}
			
		}

	}// end complementoPorampliacionChk
	

}//****************** end chkCamposACA

/******** INICIALIZACION ESQUEMA *********/
function chkCamposInicializa(){
	
	//Validando los campos del formulario
	/***FECHA DE PUBLICACION DEL APOYO***/
	var fecha = $('#fechaPeticion').val();
	if(fecha=="" || fecha==null){
		$('#dialogo_1').html('Seleccione la fecha de publicaci�n de lineamientos');
		abrirDialogo();
	 	return false;
	}

	/***TITULO DE AVISO***/
	var descLineamiento = $('#descLineamiento').val();
	if(descLineamiento=="" || descLineamiento==null){
		$('#dialogo_1').html('Capture el t�tulo del lineamiento del esquema de apoyos');
		abrirDialogo();
	 	return false;
	}
	
	/***ARCHIVO DE PUBLICACION DOF***/
	var editar = $('#editar').val();
	if(editar!=3){
		var archivo = $('#doc').val();
		if(archivo==null || archivo == ""){
			$('#dialogo_1').html('Seleccione el archivo de publicaci�n DOF.');
			abrirDialogo();
		    return false;
		}
		
	}
	
	/***AREA RESPONSABLE***/
	var idArea = $('#idArea').val();
	if(idArea==-1){
		$('#dialogo_1').html('Seleccione el area responsable');
		abrirDialogo();
	 	return false;
	}
	/***TIPO COMPONENTE***/
	var idComponente = $('#idComponente').val();
	if(idComponente==-1){
		$('#dialogo_1').html('Seleccione el tipo de componente');
		abrirDialogo();
	 	return false;
	}
	
	/***DESCRIPCION CORTA***/
	var descCorta = $('#descCorta').val();
	if(descCorta=="" || descCorta==null){
		$('#dialogo_1').html('Capture la descripci�n corta del esquema de apoyos');
		abrirDialogo();
	 	return false;
	}
	var acronimoCA = $('#acronimoCA').val();
	if(acronimoCA=="" || acronimoCA==null){
		$('#dialogo_1').html('Capture la definici�n del acr�nimo');
		abrirDialogo();
	 	return false;
	}else{
		if ($('#errorValidaAcronimo').length){
			var errorValidaAcronimo = $('#errorValidaAcronimo').val();
			if(errorValidaAcronimo!=0){
				$('#dialogo_1').html('El folio de la solicitud ya se encuentra registrado, por favor verifique');
		   		abrirDialogo();
		 		return false;	
			}
		}
	}
	
	/***CICLO***/
	var numCiclos = $('#numCiclos').val();
	if(numCiclos ==-1){
		$('#dialogo_1').html('Seleccione el n�mero de ciclo a apoyar');
		abrirDialogo();
	 	return false;
	}else{
		var i=0;
		var ciclo ="";
		var anio ="";
		var j =0;
		var tempCiclo ="";
		var tempAnio ="";
		
		for (i=1;i<parseInt(numCiclos)+1;i++){
			ciclo = $('#ci'+i).val();
			anio = $('#a'+i).val();
			if(ciclo==-1 || anio==-1 ){
	   			$('#dialogo_1').html('Capture los valores del registro '+i+' de la captura de ciclos');
		   		abrirDialogo();
		 		return false;
	   		 }else{
	   			/*Valida que el ciclo seleccionado por el usuario no se encuentre repetido*/
		   			for (j=1;j<parseInt(numCiclos)+1;j++){
		   				if(i!=j){
		   					tempCiclo = $('#ci'+j).val();
		   					tempAnio = $('#a'+j).val();
		   					if(ciclo == tempCiclo && anio == tempAnio ){
		   						$('#dialogo_1').html('El ciclo del registro '+i+", se encuentra repetido, favor de verificar");
		   				   		abrirDialogo();
		   				 		return false;
		   					}
		   				}	   				
		   			} 
	   		 }
		}
	}
		
	
	/***CRITERIO DE PAGO***/
	var idCriterioPago = $('#idCriterioPago').val();
	if(idCriterioPago ==-1){
		$('#dialogo_1').html('Seleccione el criterio de pago');
		abrirDialogo();
	 	return false;
	}else{
		/***VOLUMEN (1), ETAPA(2) y ETAPA/VOLUMEN (3)***/
		if(idCriterioPago==1 || idCriterioPago == 3){
			//valida volumen			
			/***UNIDAD DE MEDIDA***/
			var idUnidadMedida = $('#idUnidadMedida').val();
			if(idUnidadMedida ==-1){
				$('#dialogo_1').html('Seleccione la unidad de medida');
				abrirDialogo();
			 	return false;
			}
			/***VOLUMEN***/	
			var volumen = $('#volumen').val();
			var patron =/^\d{1,10}((\.\d{1,3})|(\.))?$/;
			if(!volumen.match(patron)){
				$('#dialogo_1').html('El valor del volumen m�ximo a apoyar es inv�lido, se aceptan hasta 10 d�gitos a la izquierda y 3 d�gitos m�ximo a la derecha');
				abrirDialogo();
				return false;
			}
			var volxCulVar = $('input:radio[name=volxCulVar]:checked').val();
			if(volxCulVar == 0){ //si desea agregar volumen por cultivo-variedad 
				var numCamposVXCV = $('#numCamposVXCV').val();
				if(numCamposVXCV < 1 || numCamposVXCV ==null ||  numCamposVXCV ==""){
					$('#dialogo_1').html('Capture el n�mero de registros para el volumen por cultivo variedad');
			   		abrirDialogo();
					return false;
				}else{
					var cultivoVXCV ="";
					var variedadVXCV ="";
					var volumenVXCV ="";
					var cultivoVXCVTemp ="";
					var variedadVXCVTemp = "";
					var totalVolumen=0;
					//Validar que los campos sean seleccionados					
					for (i=1;i<parseInt(numCamposVXCV)+1;i++){
						cultivoVXCV = $('#cultivoVXCV'+i).val();
						variedadVXCV = $('#variedadVXCV'+i).val();
						volumenVXCV = $('#volumenVXCV'+i).val();					
						if(cultivoVXCV ==-1 || variedadVXCV ==-1 || (volumenVXCV == "" || volumenVXCV == null)){
				   			$('#dialogo_1').html('Capture los valores del registro '+i+' volumen  por cultivo variedad');
					   		abrirDialogo();
					 		return false;
				   		 }else{		
				   			console.log(i);
				   			
				   			 //Valida que el cultivo y variedad no se encuentren repetidos				   			
				   			for (j=1;j<parseInt(numCamposVXCV)+1;j++){
				   				if(i!=j){
				   					cultivoVXCVTemp = $('#cultivoVXCV'+j).val();
									variedadVXCVTemp = $('#variedadVXCV'+j).val();
									if(cultivoVXCV == cultivoVXCVTemp && variedadVXCV == variedadVXCVTemp){
										
										$('#dialogo_1').html('El registro '+i+' en volumen por cultivo variedad se encuentra repetido, favor de verificar');
					   			   		abrirDialogo1();
					   			 		return false;
									}
				   				}
				   			}
				   			//console.log(totalVolumen);
				   			totalVolumen = (totalVolumen+parseFloat(volumenVXCV));
				   		 }//end es diferente de null o campos vacios
						
					}
					if(parseFloat(volumen) != parseFloat(totalVolumen)){
						$('#dialogo_1').html('El volumen autorizado '+volumen+' difiere de la suma '+totalVolumen+' de volumen por cultivo variedad, favor de verificar');
	   			   		abrirDialogo();
	   			 		return false;
					}
					
				}// end numCamposVXCV es dirente de vacio					
			}// end volxCulVar si desea agregar volumen por cultivo-variedad
			
		}// end criterio == 1 o 3
	
		if(idCriterioPago==2 || idCriterioPago == 3){
			//Valida etapa
			noEtapa = $('#noEtapa').val();
			if(noEtapa==-1){
				$('#dialogo_1').html('Seleccione el n�mero de etapas');
				abrirDialogo();
				return false;
			}
			//Valida la captura de monto, cuota por etapa
			var cuotaMonto = "";
			var totalMonto =0;
			for (j=1;j<parseInt(noEtapa)+1;j++){
				cuotaMonto = $('#cuotaMonto'+j).val();
				if(cuotaMonto == null || cuotaMonto ==""){					
					$('#dialogo_1').html('Capture los valores del registro '+j+' en la asignaci�n de importe por etapa');
   			   		abrirDialogo();
   			 		return false;
				}else{
					totalMonto = (totalMonto+parseFloat(cuotaMonto));
					
				}
			}			
			
			if(idCriterioPago==2){
				importe = $('#importe').val();
				var patron =/^\d{1,10}((\.\d{1,2})|(\.))?$/;
				if(!importe.match(patron)){
					$('#dialogo_1').html('El valor del importe m�ximo a apoyar es inv�lido, se aceptan hasta 10 d�gitos a la izquierda y 2 d�gitos m�ximo a la derecha');
					abrirDialogo();
					return false;
				}else{
					if(parseFloat(importe) != parseFloat(totalMonto)){
						$('#dialogo_1').html('El valor del importe autorizado a apoyar '+importe+' difiere de la suma '+totalMonto+'de los montos por etapa');
						abrirDialogo();
						return false;
					}
				}
			}
			$(".textCentrado").removeAttr('disabled');
			console.log("desabilitando");
			
		}// end criterio etapa (2) || volumen/etapa (3) 
		
	}
	
	/***Valida los campos de los estados a apoyar***/
	var numCampos = $('#numCampos').val();	
	if(numCampos=="" || numCampos==null){
		$('#dialogo_1').html('Seleccione el n�mero de Estados a Apoyar');
		abrirDialogo();
	 	return false;
	}else{
		var cult = -1;
		var edo = -1;
		var variedad = -1;
		var cuota ="";
		var tempCult ="";
		var tempEdo ="";
		var tempVariedad ="";
		
		for (i=1;i<parseInt(numCampos)+1;i++){
			cult = $('#c'+i).val();
			edo = $('#e'+i).val();
			variedad = $('#va'+i).val();
			cuota = $('#v'+i).val();
			
			if(idCriterioPago == 1){
				if(cult==-1 || edo==-1 || (cuota == "" || cuota == null)){
		   			$('#dialogo_1').html('Capture los valores del registro '+i+' en los estados a apoyar');
			   		abrirDialogo();
			 		return false;
		   		 }
			}else{
				if(cult==-1 || edo==-1 ){
		   			$('#dialogo_1').html('Capture los valores del registro '+i+' en los estados a apoyar');
			   		abrirDialogo();
			 		return false;
				}
			}
			
	   		for (j=1;j<parseInt(numCampos)+1;j++){
	   			if(i!=j){
	   				tempCult = $('#c'+j).val();
	   				tempEdo = $('#e'+j).val();
	   				tempVariedad = $('#va'+j).val();
	   				if(cult == tempCult && edo == tempEdo && variedad == tempVariedad){
	   					$('#dialogo_1').html('El registro '+i+' se encuentra repetido, favor de verificar');
	   			   		abrirDialogo();
	   			 		return false;
	   				}
	   			}	   				
	   		}
	   			
	   		if(idCriterioPago == 1){
	   			patron =/^\d{1,13}((\.\d{1,2})|(\.))?$/;
	   			if(!cuota.match(patron)){
		   			$('#dialogo_1').html('El valor de la cuota "'+cuota+'" del registro '+i+' es inv�lido, se aceptan hasta 13 d�gitos a la izquierda y 2 d�gitos m�ximo a la derecha');
			   		abrirDialogo();
			   		return false;
	   			}	
	   		}
	   				   			
	   		 //}
			
		}// end for
	}// end validacion de los campos de cuotas	
	
	var periodoDOFSI = $('#periodoDOFSI').val();
	patron =/^\d{1,10}$/;
	if(!periodoDOFSI.match(patron)){
		$('#dialogo_1').html('El valor del periodo DOF a SI es inv�lido, se aceptan hasta 10 d�gitos');
		abrirDialogo();
		return false;
	}
	
	var periodoOSIROSI = $('#periodoOSIROSI').val();
	patron =/^\d{1,10}$/;
	if(!periodoOSIROSI.match(patron)){
		$('#dialogo_1').html('El valor del periodo OOSI a ROOSI es inv�lido, se aceptan hasta 10 d�gitos');
		abrirDialogo();
		return false;
	}
	var periodoCASP = $('#periodoCASP').val();
	if(!periodoCASP.match(patron)){
		$('#dialogo_1').html('El valor del periodo CA a SP es inv�lido, se aceptan hasta 10 d�gitos');
		abrirDialogo();
		return false;
	}

	var periodoSPOO = $('#periodoSPOO').val();
	if(!periodoSPOO.match(patron)){
		$('#dialogo_1').html('El valor del periodo SP a OO es inv�lido, se aceptan hasta 10 d�gitos');
		abrirDialogo();
		return false;
	}

	var periodoORPago = $('#periodoORPago').val();
	if(!periodoORPago.match(patron)){
		$('#dialogo_1').html('El valor del periodo OR a Pago es inv�lido, se aceptan hasta 10 d�gitos');
		abrirDialogo();
		return false;
	}
}

function validarAcronimo(){
	var acronimoCA = $('#acronimoCA').val();
	if(acronimoCA == null ||  acronimoCA == ""){
		$("#validarAcronimo").fadeOut('slow');
		return false;
	}
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "validarAcronimo",
		   data: "acronimoCA="+acronimoCA, 
		   success: function(data){
				$('#validarAcronimo').html(data).ready(function () {
					$("#validarAcronimo").fadeIn('slow');
				});
		   }
	});//fin ajax
}


function selectCriterioPago(){
	var idCriterioPago = $('#idCriterioPago').val();
	if(idCriterioPago == -1){
		$("#selectCriterioPago").fadeOut('slow');
		return false;
	}
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "selectCriterioPago",
		   data: "idCriterioPago="+idCriterioPago, 
		   success: function(data){
				$('#selectCriterioPago').html(data).ready(function () {
					$("#selectCriterioPago").fadeIn('slow');
				});
		   }
		});//fin ajax

}
function selectNumEtapas(){
	var idCriterioPago = $('#idCriterioPago').val();
	var editar = $('#editar').val();
	var idUnidadMedida = 0;
	var noEtapa = 0;
	var idPrograma = 0;
	if(idCriterioPago == 2 || idCriterioPago == 3){
		noEtapa = $('#noEtapa').val();
		if(idCriterioPago == 3){
			idUnidadMedida = $('#idUnidadMedida').val();
			if(idUnidadMedida == -1){
				$('#dialogo_1').html('Debe seleccionar la unidad de medida');
				abrirDialogo();
				return false;
			}
		}
	}
	
	if(editar == 3 || editar == 4){
		idPrograma = $('#idPrograma').val();
		
	}
	if(idCriterioPago == -1){
		$("#selectNumEtapas").fadeOut('slow');
		return false;
	}
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "selectNumEtapas",
		   data: "idCriterioPago="+idCriterioPago
		   		  +"&idUnidadMedida="+idUnidadMedida
		   		  +"&noEtapa="+noEtapa
		   		  +"&editar="+editar
		   		  +"&idPrograma="+idPrograma, 
		   success: function(data){
				$('#selectNumEtapas').html(data).ready(function () {
					$("#selectNumEtapas").fadeIn('slow');
				});
		   }
		});//fin ajax

}

function agregaNumCamposCultVar(){
	var volxCulVar = $('input:radio[name=volxCulVar]:checked').val();
	if(volxCulVar == 0){
		$("#numVolPorCultivoVariedad").css("display", "inline");
		$("#agregarVolumenPorCultivoVariedad").css("display", "inline");
	}else{
		$("#numVolPorCultivoVariedad").css("display", "none");
		$("#agregarVolumenPorCultivoVariedad").css("display", "none");
	}
		
}

function validarFolioInscripcion(){
	var folioSI = $('#folioSI').val();
	var idPrograma = $('#idPrograma').val();
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "validarFolioInscripcion",
		   data: "folioSI="+folioSI+
		   		"&idPrograma="+idPrograma,
		   success: function(data){
				$('#validarFolioInscripcion').html(data).ready(function () {
					$("#validarFolioInscripcion").fadeIn('slow');
					
				});
		   }
		});//fin ajax
}

function recuperaTotalVolumen(v, count){
	console.log("recuperaTotalVolumen");
	var numCampos = $('#numCampos').val();
	var totalVolumen = 0;
	var volumen = null;
	var i = 0;
	var vl = 0; 
	
	
	if(($('#complementoPorampliacionChk')).is(":checked")) {			
		vl = $('#volumenApoyarAux').val();	
		
	}
	/*Calcula el valor total de volumen*/
	for (i=1;i<parseInt(numCampos)+1;i++){
		volumen = $('#v'+i).val();
		if(volumen!="" && volumen !=null){
				totalVolumen = (totalVolumen+parseFloat(volumen));	
		}
	}	
	totalVolumen= totalVolumen+parseFloat(vl);
	$("#totalesVolumen").val(totalVolumen.toFixed(3));	
	var volumenXCultivoVariedad= $('#volumenXCultivoVariedad').val();
	console.log("volumenXCultivoVariedad "+volumenXCultivoVariedad);
	if(volumenXCultivoVariedad == 1){
		console.log("volumenXCultivoVariedad1 ");
		var idCultivo= $('#c'+count).val();
		var idVariedad= $('#va'+count).val();
		var idPrograma= $('#idPrograma').val();
		var idCriterioPago= $('#idCriterioPago').val();
		
		if((idCultivo != -1) && (idVariedad != -1)){
			console.log("consigue el volumen x cultivo variedad");			
			$.ajax({
				   async: false,
				   type: "POST",
				   url: "validarVolumenXCultivoVariedad",
				   data: "idCultivo="+idCultivo+
				   		"&idVariedad="+idVariedad+
				   		"&idPrograma="+idPrograma+
				   		"&idCriterioPago="+idCriterioPago+
				   		"&count="+count,
				   success: function(data){
					   $("#vxcv"+count).html(data).ready(function () {						
						   
						});
				   }
				});//fin ajax
		}
		
	}
	
}

function recuperaCartasEntregar(){
	var idPrograma = $('#idPrograma').val();
	if(idPrograma == -1){
		$("#recuperaCartasEntregar").fadeOut('slow');
		return false;
	}else{
		$.ajax({
			   async: false,
			   type: "POST",
			   url: "recuperaCartasEntregar",
			   data: "idPrograma="+idPrograma,
			   success: function(data){
					$('#recuperaCartasEntregar').html(data).ready(function () {
						$("#recuperaCartasEntregar").fadeIn('slow');
					});
			   }
			});
	}
}

function chkTodos(){ 
	if($(".check_todos").is(":checked")) {
		$(".ck:checkbox:not(:checked)").attr("checked", "checked");
	 }else{
		 $(".ck:checkbox:checked").removeAttr("checked");
	 }
}

function vistaPreviaOficio(){
	var idPrograma = $('#idPrograma').val();
	//var idEstado = $('#idEstado').val();
	var claveOficio = $('#claveOficio').val();
	var noOficio = $('#noOficio').val();
	var anioOficio = $('#anioOficio').val();
	var fechaOficioEntrega = $('#fechaOficioEntrega').val();
	
	
	if(claveOficio==null || claveOficio==""){
		   $('#dialogo_1').html('Capture el N�mero de clave de oficio.');
			abrirDialogo();
		    return false;
	}
	if(noOficio==null || noOficio==""){
		   $('#dialogo_1').html('Capture el n�mero de Oficio.');
			abrirDialogo();
		    return false;
	}
	if(anioOficio==null || anioOficio==""){
		   $('#dialogo_1').html('Capture el a�o del oficio.');
			abrirDialogo();
		    return false;
	}
	
	var selectedCartas = new Array();
	$("input[type='checkbox']:checked").each(function() {selectedCartas.push($(this).val());});
	if (selectedCartas.length == 0){ 
	    $('#dialogo_1').html('Por favor seleccione los cartas a entregar para tr�mite de pago.');
		abrirDialogo();
	    return false;
	}
	
	$("#pagos").fadeOut('5000', function(){
		$.ajax({
			   async: false,
			   type: "POST",
			   url: "vistaPreviaOficio",
			   data: "idPrograma="+idPrograma+
		   		"&claveOficio="+claveOficio+
		   		"&noOficio="+noOficio+
		   		"&anioOficio="+anioOficio+
		   		"&fechaOficioEntrega="+fechaOficioEntrega+
		   		"&selectedCartas="+selectedCartas,
			   success: function(data){
					$('#vistaPrevia').html(data).ready(function () {
						$("#vistaPrevia").fadeIn('slow');
					});
			   }
			});
		});
}

function cerrarVistaPreviaOficio(){
	
	$("#vistaPrevia").fadeOut('3000', function(){
		$("#pagos").fadeIn('3000');	
	});
}

function generarOficioEntregaCartas(){
	var idPrograma = $('#idPrograma').val();
	var claveOficio = $('#claveOficio').val();
	var noOficio = $('#noOficio').val();
	var anioOficio = $('#anioOficio').val();
	var fechaOficioEntrega = $('#fechaOficioEntrega').val();
	
	if(claveOficio==null || claveOficio==""){
		   $('#dialogo_1').html('Capture el N�mero de clave de oficio.');
			abrirDialogo();
		    return false;
	}
	if(noOficio==null || noOficio==""){
		   $('#dialogo_1').html('Capture el n�mero de Oficio.');
			abrirDialogo();
		    return false;
	}
	if(anioOficio==null || anioOficio==""){
		   $('#dialogo_1').html('Capture el a�o del oficio.');
			abrirDialogo();
		    return false;
	}
	
	
	var selectedCartas = new Array();
	$("input[type='checkbox']:checked").each(function() {selectedCartas.push($(this).val());});
	if (selectedCartas.length == 0){ 
		$('#dialogo_1').html('Por favor seleccione los cartas a entregar para tr�mite de pago.');
		abrirDialogo();
	    return false;
	}
	
	$("#pagos").fadeOut('5000', function(){
		$.ajax({
			   async: false,
			   type: "POST",
			   url: "generarOficioEntregaCartas",
			   data: "idPrograma="+idPrograma+
		   		"&claveOficio="+claveOficio+
		   		"&noOficio="+noOficio+
		   		"&anioOficio="+anioOficio+
		   		"&fechaOficioEntrega="+fechaOficioEntrega+
		   		"&selectedCartas="+selectedCartas,
			   success: function(data){
					$('#resultadoGeneracionOficio').html(data).ready(function () {
						$("#resultadoGeneracionOficio").fadeIn('slow');
					});
			   }
			});
		});
}

function cerrarErrorOficio(){
	$("#resultadoGeneracionOficio").fadeOut('3000', function(){
		$("#pagos").fadeIn('3000');	
	});
}


function selectAccionAlcanceEdicion (){
	var tipoAccion = $('input:radio[name=tipoAccion]:checked').val();
	var registrar = "";
	if(tipoAccion==1){
		registrar = 4;
		$("#registrar").val(4);	
	}else if(tipoAccion == 2){
		registrar = 3;
		$("#registrar").val(3);	
	}
	var idSI = $('#idSI').val();
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "selectAccionAlcanceEdicion",
		   data: "registrar="+registrar+
		   		 "&idSI="+idSI,
		   success: function(data){
				$('#datosInscripcion').html(data).ready(function () {
					$("#datosInscripcion").fadeIn('slow');
					
				});
		   }
		});//fin ajax
}

function recuperaCultivoByEstadoAsigCA(idEstado, count){
	if(idEstado == -1){
		return false;
	}
	var idInicializacionEsquema = $('#idInicializacionEsquema').val();
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "recuperaCultivoByEstadoAsigCA",
		   data: "idEstado="+idEstado+
		   		"&count="+count+
		   		"&idInicializacionEsquema="+idInicializacionEsquema,	
		   success: function(data){
				$("#cultivo"+count).html(data).ready(function () {
					
				});
		   }
		});//fin ajax
}


function complementoAmpliacion(){
	var complementoPorampliacionChk = "false";
	if(($('#complementoPorampliacionChk')).is(":checked")) {
		complementoPorampliacionChk = "true";				
	}
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "complementoPorAmpliacion",	
		   data: "complementoPorampliacionChk="+complementoPorampliacionChk,
		   success: function(data){
				$("#complementoPorAmpliacion").html(data).ready(function () {
					
				});
		   }
		});//fin ajax	
}

function agregaCultivoVariedadEstadoCAComp(){
	var numCamposComp = $('#numCamposComp').val();
	var idCriterioPago = $('#idCriterioPago').val();
	var idInicializacionEsquema = $('#idInicializacionEsquema').val();
	var complementoPorampliacionChk = "false";
	var volumenXCultivoVariedad = $('#volumenXCultivoVariedad').val();
	var folioCartaAdhesion = $('#folioCartaAdhesion').val();
	if(($('#complementoPorampliacionChk')).is(":checked")) {
		complementoPorampliacionChk = "true";
	}
		
	var patron =/^\d{1,3}$/;
	if(!numCamposComp.match(patron)){
		$('#dialogo_1').html('El valor del campo es inv�lido, se aceptan hasta 3 d�gitos');
   		abrirDialogo();
   		$('#numCamposComp').val(null);
   		//return false;
	}  
	
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "agregaCultivoVariedadEstadoCAComp",
		   data: "numCamposComp="+numCamposComp+
		   	"&idInicializacionEsquema="+idInicializacionEsquema+
	   		"&idCriterioPago="+idCriterioPago+
	   		"&complementoPorampliacionChk="+complementoPorampliacionChk+
	   		"&volumenXCultivoVariedad="+volumenXCultivoVariedad+
	   		"&folioCartaAdhesion="+folioCartaAdhesion,
		   success: function(data){
				$('#agregaCultivoVariedadEstadoCAComp').html(data).ready(function () {
					$("#agregaCultivoVariedadEstadoCAComp").fadeIn('slow');
					if(complementoPorampliacionChk=="true"){
						$("#activaAccion").fadeIn('slow'); //mostrar boton de guardar
					}else{
						$("#activaAccion").fadeOut('slow'); //ocultar boton de guardar
					}
				});
		   }
		});//fin ajax
}





