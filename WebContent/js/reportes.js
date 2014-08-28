$(document).ready(function(){
	$.ajaxSetup({
		'beforeSend' : function(xhr) {
			try{
				xhr.overrideMimeType('text/html; charset=iso-8859-1');
			}catch(e){}
		}});
});

function chkCampoConsultaPagosTesofe(){
	var oficioCGC = $('#oficioCGC').val();
	var idPrograma = $('#idPrograma').val();
	var fechaInicio = $('#fechaInicio').val();
	var fechaFin = $('#fechaFin').val();
	var patronOficio =/^F00.\d{4}\/\d{1,8}\/\d{4}$/;
	if((idPrograma == -1) && (oficioCGC==null||oficioCGC=="")&& (fechaInicio==null || fechaInicio=="") && (fechaFin==null || fechaFin=="")){
		$('#dialogo_1').html('Debe capturar al menos un dato, para realizar la consulta');
   		abrirDialogo();
   		return false;
	}
	if(oficioCGC!=null && oficioCGC!=""){
		if(!oficioCGC.match(patronOficio)){
			$('#dialogo_1').html('El oficio es incorrecto, se debe capturar conforme a la siguiente nomenclatura ejemplo: F00.4000/99999/2013');
			abrirDialogo();
			return false;
		} 
	}
	if(fechaFin!=null && fechaFin!=""){
		if(fechaInicio==null || fechaInicio==""){
			$('#dialogo_1').html('Debe capturar la fecha inicio');
			abrirDialogo();
		 	return false;
		}
	}
}


function generarAcuseRechazos(){
	var numeroRechazos = $('#numeroRechazos').val();
	var i = 0;
	var oficios ="";
	var selectedPagos = new Array();
	$("input[@name='idPagos[]']:checked").each(function() {selectedPagos.push($(this).val());});
	if (selectedPagos.length == 0){ 
	    $('#dialogo_1').html('Por favor seleccione los pagos para generar los acuses correspondientes.');
		abrirDialogo();
	    return false;
	}
	var selectedOficios = new Array();
	for (i=1;i<parseInt(numeroRechazos)+1;i++){
		pagos = $('#p'+i).val();
		oficios = $('#o'+i).val();
		if($('#p'+i).is(':checked')){
			/*Verificar que el oficio haya sido capturado*/
			if(oficios == "" || oficios == null){
				 $('#dialogo_1').html('Por favor capture el n�mero de oficio');
					abrirDialogo();
				    return false;
			}else{
				patronOficio =/^F00.\d{4}\/\d{1,8}\/\d{4}$/;
				if(!oficios.match(patronOficio)){
					$('#dialogo_1').html('El valor del oficio en el registro '+i+'es incorrecto, se debe capturar conforme a la siguiente nomenclatura ejemplo: F00.4000/99999/2013');
					abrirDialogo();
				    return false;
				}else{
					selectedOficios.push(oficios);
				}
			}
		}
		
	}//end for
	$("#espera").fadeIn('slow', function() {
		  $("#img-espera").fadeIn('slow', function(){
			  	$("#pContenido").slideUp('slow', function() {
				$.ajax({
					   async: false,
					   type: "POST",
					   url: "generarAcuseRechazos",
					   data: "selectedPagos="+selectedPagos+
					   		 "&selectedOficios="+selectedOficios,
					   success: function(data){
							$('#pContenido').html(data).ready(function () {
								hideLoading();
							});
					   }
					}); //termina ajax
				$("#pContenido").fadeIn("slow"); 
			  	});
		  });
	  });
	}
	
function generarAcusesAplicados(){
	
	var selectedPagos = new Array();
	$("input[@name='idPagos[]']:checked").each(function() {selectedPagos.push($(this).val());});
	if (selectedPagos.length == 0){ 
	    $('#dialogo_1').html('Por favor seleccione los pagos para generar los acuses correspondientes.');
		abrirDialogo();
	    return false;
	}
	$("#espera").fadeIn('slow', function() {
		  $("#img-espera").fadeIn('slow', function(){
			  	$("#pContenido").slideUp('slow', function() {
				$.ajax({
					   async: false,
					   type: "POST",
					   url: "generarAcusesAplicados",
					   data: "selectedPagos="+selectedPagos,
					   success: function(data){
							$('#pContenido').html(data).ready(function () {
								hideLoading(); 
							});
					   }
					}); //termina ajax	
					$("#pContenido").fadeIn("slow");
			  	 });
		  });
	  });
}

function selectTipoReporte() {
	var tipoReporte = $('input:radio[name=tipoReporte]:checked').val();
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "seleccionCriterios",
		   data: "tipoReporte="+tipoReporte,
		   success: function(data){
				$('#seleccionCriterios').html(data).ready(function () {
				});
		   }
		}); //termina ajax	
}
function generarReporteDinamico() {
	var tipoReporte = $('input:radio[name=tipoReporte]:checked').val();
	//0 pagados 1 tramitado-pagado-rechazado-pendiente
	var agrupado="";
	var tramitado=0;
	var pagado=0;
	var rechazado=0;
	var pendiente=0;
	var patron =/^\d{1}$/;
	var numCriterios = $('#numCriterios').val();
	
	/*Verificando las fechas del reporte*/
	var fechaInicio = $('#fechaInicio').val();
	var fechaFin = $('#fechaFin').val();
	
	var idPrograma = $('#idPrograma').val();
	
	
	var selectCriterios = new Array();
	$("input:checkbox:checked.ck").each(function() {selectCriterios.push($(this).val());});
	if (selectCriterios.length == 0){ 
	    $('#dialogo_1').html('Por favor seleccione los criterios del reporte.');
		abrirDialogo();
	    return false;
	}

	
	var selectAgrupados = new Array();
	var compararSelectAgrupados = new Array();
	var i = 0;
	for (i=1;i<=parseInt(numCriterios);i++){
		if($('#id'+i).is(':checked')){
			agrupado = $('#a'+i).val();
			/*Verificar que el oficio haya sido capturado*/
			if(agrupado == "" || agrupado == null){
				 $('#dialogo_1').html('Por favor capture el valor de la agrupaci�n para el registro n�mero '+i);
					abrirDialogo();
				    return false;
			}else{
				if(!agrupado.match(patron)){
					$('#dialogo_1').html('El valor de la agrupaci�n en el registro '+i+' es incorrecto, se aceptan solo un digito n�merico');
					abrirDialogo();
				    return false;
				}else{
					selectAgrupados.push(agrupado);
					compararSelectAgrupados.push(agrupado);
				}
			}
		}
		
	}
	
	
	/*Ordenando los elementos del arreglo que capturo el usuario*/
	var temp=0;
	var j = 0;
	for(i=0; i<compararSelectAgrupados.length; i++){
		for(j=i; j<compararSelectAgrupados.length-1; j++){
			if(compararSelectAgrupados[i]>compararSelectAgrupados[j+1]){
				temp = compararSelectAgrupados[i];
				compararSelectAgrupados[i] = compararSelectAgrupados[j+1];
				compararSelectAgrupados[j+1] = temp;
			}
		}
	}
	
	
	/*Verificando que los elementos capturados empiecen con 0 y sean secuenciales*/
	for(i=0; i<compararSelectAgrupados.length-1; i++){
		if(i==0){
			if(compararSelectAgrupados[i]!=0){
				$('#dialogo_1').html('Para agrupar, debe comenzar desde cero.');
				abrirDialogo();
			    return false;
				
			}
		}
		temp = (parseInt(compararSelectAgrupados[i])+1);
		if(compararSelectAgrupados[i+1]!=temp){
			$('#dialogo_1').html('Los valores en la agrupaci�n deben ser secuenciales, por favor verifique');
			abrirDialogo();
		    return false;
		}
	}
	
	if(tipoReporte == 0){//pagado
	}else if(tipoReporte==1){//tramitado-pagado-pendiente
		if( $('#tramitado').is(':checked')){
			tramitado = $('#tramitado').val();
		}
		if( $('#pagado').is(':checked')){
			pagado = $('#pagado').val();
		}
		if( $('#rechazado').is(':checked')){
			rechazado = $('#rechazado').val();
		}
		if( $('#pendiente').is(':checked')){
			pendiente = $('#pendiente').val();
		}
		
		var selecColumnas = new Array();
		$("input:checkbox:checked.columnas").each(function() {selecColumnas.push($(this).val());});
		if(selecColumnas.length == 0){
			 $('#dialogo_1').html('Por favor seleccione las columnas del reporte.');
				abrirDialogo();
			    return false;
		}
		
	}
	/*Ajax*/
	$("#espera").fadeIn('slow', function() {
		  $("#img-espera").fadeIn('slow', function(){
			  	$("#pContenido").slideUp('slow', function() {
					$.ajax({
						   async: false,
						   type: "POST",
						   url: "generarReporteDinamico",
						   data: "tipoReporte="+tipoReporte+
						   		"&selectCriterios="+selectCriterios+
						   		"&selectAgrupados="+selectAgrupados+
						   		"&tramitado="+tramitado+
						   		"&pagado="+pagado+
						   		"&rechazado="+rechazado+
						   		"&pendiente="+pendiente+
						   		"&fechaInicio="+fechaInicio+
						   		"&fechaFin="+fechaFin+
						   		"&idPrograma="+idPrograma,
						   success: function(data){
								$('#resultadoReporteDinamico').html(data).ready(function () {
									hideLoading();
								});
						   }
						}); 
					 $("#pContenido").fadeIn("slow");
			  	 });
		  });
	  });
	  
					
}

function irPaginando(pagina) {
	$("#espera").fadeIn('slow', function() {
		  $("#img-espera").fadeIn('slow', function(){
			  	$("#pContenido").slideUp('slow', function() {
					$.ajax({
						   async: false,
						   type: "POST",
						   url: "irPaginando",
						   data: "pagina="+pagina,
						   success: function(data){
								$('#paginando').html(data).ready(function () {
									hideLoading();
								});
						   }
						}); //end Ajax
					 $("#pContenido").fadeIn("slow");
			  	 });
		  });
	  });

}
function hideLoading() {
	  $("#img-espera").fadeOut("slow", function() {
		  $("#espera").fadeOut("slow");
	  });
}

	

function programaSeleccionado(idCampo){
	if(idCampo==1){
		if(($('#id'+idCampo)).is(":checked")) {
			$("#idPrograma").fadeIn('slow');
			//$("#idPrograma").attr('disabled','enabled');			
		}else{	
			$("#idPrograma").fadeOut('slow');
			//$("#idPrograma").attr('disabled','disabled');			
		}
	}
}