$(document).ready(function(){
	$.ajaxSetup({
		'beforeSend' : function(xhr) {
			try{
				xhr.overrideMimeType('text/html; charset=iso-8859-1');
			}catch(e){}
		}});
});

function recuperaMunicipioByEstado(){
	var idEstado = $('#idEstado').val();
	if(idEstado == -1){
		$("#recuperaMunicipioByEstado").fadeOut('slow');
		return false;
	}
	
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "recuperaMunicipioByEstado",
		   data: "idEstado="+idEstado, 
		   success: function(data){
				$('#recuperaMunicipioByEstado').html(data).ready(function () {
					$("#recuperaMunicipioByEstado").fadeIn('slow');
					
				});
		   }
		});//fin ajax
}
function recuperaLocalidadesByMunicipio(){
	var idEstado = $('#idEstado').val();
	var claveMunicipio = $('#claveMunicipio').val();
	if(idEstado == -1){
		$("#recuperaLocalidadesByMunicipio").fadeOut('slow');
		return false;
	}
	
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "recuperaLocalidadesByMunicipio",
		   data: "idEstado="+idEstado + 
		   "&claveMunicipio="+claveMunicipio, 
		   success: function(data){
				$('#recuperaLocalidadesByMunicipio').html(data).ready(function () {
					$("#recuperaLocalidadesByMunicipio").fadeIn('slow');
					
				});
		   }
		});//fin ajax
	
}

function datosFisComp(){
    // 1:Fisica, 0:Moral
    var personaFiscal = $('input:radio[name=personaFiscal]:checked').val();
    $.ajax({
	   async: false,
	   type: "POST",
	   url: "datosFiscales",
	   data:"&personaFiscal="+personaFiscal,
	   success: function(data){
           $('#datosFiscales').html(data).ready(function () {
                       $("#datosFiscales").fadeIn('slow');
                       
           });
	   }
	});//fin ajax     
}


function datosRepLegal(editar){ 
	var numCampos = $('#numCampos').val();
	var editar = $('#editar').val();
	var idComprador = $('#idComprador').val();
	
	if(numCampos==-1){
		$("#datosRepLegal").fadeOut('slow');
		return false;	
	}
   $.ajax({
	   async: false,
	   type: "POST",
	   url: "datosRepLegal",
	   data: "numCampos="+numCampos+
	   		 "&editar="+editar+
	   		 "&idComprador="+idComprador,
	   success: function(data){
			$('#datosRepLegal').html(data).ready(function () {
				$("#datosRepLegal").fadeIn('slow');
			});
	   }
	}); //termina ajax
}

function capturarRFC(){
	// 1:Fisica, 0:Moral
	var personaFiscal = $('input:radio[name=personaFiscal]:checked').val();
	if(personaFiscal == 1){
		$("#rfc").css("display", "inline");
		$("#curp").css("display", "inline");
	}else{
		$("#rfc").css("display", "inline");
		$("#curp").css("display", "none");
	}
		
}

function validarRfc(){
	rfc = $('#rfcc').val();
	var idComprador = $('#idComprador').val();
	if(rfc == null ||  rfc == ""){
        $("#validarRfc").fadeOut('slow');
        return false;    
	}
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "validarRfc",
		   data: "rfc="+rfc+
		   "&idComprador="+idComprador,
		   success: function(data){
				$('#validarRfc').html(data).ready(function () {
					$("#validarRfc").fadeIn('slow');
					
				});
		   }
		});//fin ajax
}

function chkCamposComprador(){
	//Validando los campos del formulario
	var personaFiscal = $('input:radio[name=personaFiscal]:checked').val();
	
	var rfc = $('#rfcc').val();
	var errorRfc = $('#errorRfc').val();
	if(rfc == "" || rfc == null){
		$('#dialogo_1').html('Escriba el RFC del Comprador');
	   	abrirDialogo();
	   	return false;
	}else if ($('#errorRfc').length){
		if(errorRfc!=0){
			$('#dialogo_1').html('El RFC del Comprador ya se encuentra registrado, por favor verifique');
	   		abrirDialogo();
	 		return false;
		}
	}
	
	patron = /^([A-Z,a-z]{3,4})([0-9]{6})([A-Z,a-z,0-9]{3})$/;
	if (!rfc.match(patron)){
	$('#dialogo_1').html('Escriba correctamente el RFC del Comprador');
   	abrirDialogo();
   	return false;
	}
	
	var apellidoPaterno = $('#apellidoPaterno').val();
	var apellidoMaterno = $('#apellidoMaterno').val();
	var curp = $('#curpc').val();
	if (personaFiscal==1){
		if(curp == "" || curp == null){
			$('#dialogo_1').html('Escriba el CURP del Comprador');
			abrirDialogo();
			return false;
		}else if(apellidoPaterno == "" || apellidoPaterno == null){
			$('#dialogo_1').html('Escriba el Apellido Paterno del Comprador');
			abrirDialogo();
			return false;
		}else if(apellidoMaterno == "" || apellidoMaterno == null){
			$('#dialogo_1').html('Escriba el Apellido Materno del Comprador');
			abrirDialogo();
			return false;
		}
	}
	
	var nombre = $('#nombre').val();
	if (personaFiscal == 1 || personaFiscal == 0){
		if((nombre == null || nombre == "")){
			$('#dialogo_1').html('Escriba el nombre del Comprador');
			abrirDialogo();
		 	return false;
		}
	}
	var entidadNacimiento = $('#entidadNacimiento').val();
	if (personaFiscal==1){
		if (entidadNacimiento == 0 || entidadNacimiento == null){
			$('#dialogo_1').html('Seleccione la Entidad de nacimiento del Comprador');
			abrirDialogo();
			return false;
		}
	}

	var idPerfil = $('#idPerfil').val();
	if(idPerfil != 1){
		var numCampos = $('#numCampos').val();
		if(numCampos==-1){
			$('#dialogo_1').html('Seleccione el n�mero de Representantes que se van a capturar');
			abrirDialogo();
		 	return false;
		}else{
			var capRepresentante = null;
			var capRepresentanteTmp = null;
			var i=0;
			var j=0;
			var count = 0;
			for (i=1;i<parseInt(numCampos)+1;i++){
				capRepresentante = $('#capRepresentante'+i).val();
				if (capRepresentante == -1){
		   			$('#dialogo_1').html('Capture los datos del representante legal del registro '+i);
			   		abrirDialogo();
			 		return false;
		   		}
				for (j=1;j<parseInt(numCampos)+1;j++){
	   				if(i!=j){
	   					capRepresentanteTmp = $('#capRepresentante'+j).val();
						if(capRepresentante == capRepresentanteTmp){
							$('#dialogo_1').html('El representante del registro '+i+' se encuentra repetido, favor de verificar');
		   			   		abrirDialogo();
		   			 		return false;
						}
	   				}
	   			}
				
				if($("#capEstatus"+i).is(":checked")) {
					count = count + 1;
				}
				
			}//end for
			
			console.log("count:"+count);
			if(count <1){
				$('#dialogo_1').html('Debe seleccionar un Representante, responsable del Comprador');
		   		abrirDialogo();
		 		return false;
			}
			
		}
		var idEstado = $('#idEstado').val();
		if((idEstado == null || idEstado == "" || idEstado ==-1)){
			$('#dialogo_1').html('Seleccione el estado del Comprador');
			abrirDialogo();
		 	return false;
		}
		
		var claveMunicipio = $('#claveMunicipio').val();
		if((claveMunicipio == null || claveMunicipio == ""|| claveMunicipio ==-1)){
			$('#dialogo_1').html('Seleccione el municipio del Comprador');
			abrirDialogo();
		 	return false;
		}
				
		var calle = $('#calle').val();
		if((calle == null || calle == "")){
			$('#dialogo_1').html('Escriba la calle del Comprador');
			abrirDialogo();
		 	return false;
		}
		
		var numExterior = $('#numExterior').val();
		if((numExterior == null || numExterior == "")){
			$('#dialogo_1').html('Escriba el numero exterior del Comprador');
			abrirDialogo();
		 	return false;
		}
		
		var codigoPostal = $('#codigoPostal').val();
		patron = /^([0-9]{1,9})$/;
		if(codigoPostal == null || codigoPostal == ""){
			$('#dialogo_1').html('Escriba el codigo postal del Comprador');
			abrirDialogo();
			return false;
		}else{
			if(!codigoPostal.match(patron)){
				$('#dialogo_1').html('El Codigo Postal acepta unicamente valores num�ricos');
				abrirDialogo();
				return false;
			}
		}
	}
}