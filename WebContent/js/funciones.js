$(document).ready(function(){
	$.ajaxSetup({
		'beforeSend' : function(xhr) {
			try{
				xhr.overrideMimeType('text/html; charset=iso-8859-1');
			}catch(e){}
		}});
});


function chkCamposLogin(){ 
	var nombreUsuario = $('#nombreUsuario').val();
	var password = $('#password').val();
	
	if(nombreUsuario == null || nombreUsuario == ""){
 		$('#dialogo_1').html('Capture el usuario');
 		abrirDialogo();
		return false;	
 	}else{
 		
 	}
	if(password == null || password == ""){
 		$('#dialogo_1').html('Capture la contraseņa');
 		abrirDialogo();
		return false;	
 	}
	
}

function recuperaCompradoresByPrograma(){
	var idPrograma = $('#idPrograma').val();	
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "recuperaCompradoresByPrograma",
		   data: "idPrograma="+idPrograma, 
		   success: function(data){
				$('#compradores').html(data).ready(function () {
					$("#compradores").css("display", "inline");
					
				});
		   }
		});//fin ajax
		
}

function cierraAviso(idAviso){
	$("#espera").fadeIn('slow', function() {
		  $("#img-espera").fadeIn('slow', function(){
			  	$("#pContenido").slideUp('slow', function() {
						$.ajax({
							   async: false,
							   type: "POST",
							   url: "cierraAviso",
							   data: "idAviso="+idAviso, 
							   success: function(data){
									$('#avisos').html(data).ready(function () {
										//$("#withborder").fadeIn('slow');
										hideLoading();
									});
							   }
							});//fin ajax
			  		});
			  		$("#pContenido").fadeIn("slow");
			  	});
		  });
	  
}

function chkCamposBusquedaPagos(){
	var idPrograma = $('#idPrograma').val();
	var idComprador = $('#idComprador').val();
	var fechaInicio = $('#fechaInicio').val();
	var fechaFin = $('#fechaFin').val();
	var estatusId = $('#estatusId').val();
	var noCarta = $('#noCarta').val();
	if((fechaInicio == null || fechaInicio =="") && 
	   (fechaFin == null || fechaFin =="") && 
	   (idPrograma == -1) && (idComprador == -1)&&
	   (estatusId == -1)&& (noCarta == null)){
		$('#dialogo_1').html('Debe capturar al menos un dato');
		abrirDialogo();
	 	return false;
	}
	
}



function chkTodos(){ 
	if($(".check_todos").is(":checked")) {
		$(".ck:checkbox:not(:checked)").attr("checked", "checked");
	 }else{
		 $(".ck:checkbox:checked").removeAttr("checked");
	 }
}

function chkTodos1(){ 
	if($(".check_todos1").is(":checked")) {
		$(".ck1:checkbox:not(:checked)").attr("checked", "checked");
	 }else{
		 $(".ck1:checkbox:checked").removeAttr("checked");
	 }
}


function abrirDialogo() {
	$.fx.speeds._default = 300;
	$(function() {
	    $( "#dialogo_1" ).dialog({
	        autoOpen: true,
	        resizable:false,
	        show: "slide",
	        hide: "fade",
	        width:300,
	modal: true,
	buttons: {
		Ok: function() {
			$( this ).dialog( "close" );
		}   
	}
	    });

	    $( "#open_1" ).click(function() {
	        $( "#dialogo_1" ).dialog( "open" );
	        return false;
	    });

	$('.ui-widget-overlay').live('click', function() {
	     $('#dialogo_1').dialog( "close" );
	    });
	});	
}

function abrirDialogo2() {
	$.fx.speeds._default = 300;
	$(function() {
	    $( "#dialogo_2" ).dialog({
	        autoOpen: true,
	        resizable:false,
	        show: "slide",
	        hide: "fade",
	        width:300,
	modal: true,
	buttons: {
		Ok: function() {
			$( this ).dialog( "close" );
		}   
	}
	    });

	    $( "#open_2" ).click(function() {
	        $( "#dialogo_2" ).dialog( "open" );
	        return false;
	    });

	$('.ui-widget-overlay').live('click', function() {
	     $('#dialogo_2').dialog( "close" );
	    });
	});	
}


function showLoading() {
	  $("#espera").fadeIn('slow', function() {
		  $("#img-espera").fadeIn('slow');
	  });
}

function hideLoading() {
	  $("#img-espera").fadeOut("slow", function() {
		  $("#espera").fadeOut("slow");
	  });
}

function limpiarMsg(){
	$('#mensajesApp').html('');
}
function calculaTotalVolumen(v){
	var numCampos = $('#numCampos').val();
	var totalVolumen = null;
	var volumen = null;
	var i = 0;
	/*Calcula el valor total de volumen*/
	for (i=1;i<parseInt(numCampos)+1;i++){
		volumen = $('#v'+i).val();
		if(volumen!="" && volumen !=null){
				totalVolumen = (totalVolumen+parseFloat(volumen));	
		}
	}
	$("#totalesVolumen").val(totalVolumen.toFixed(3));	
}

function recuperaCartasByComprador(){
	console.log("Entra al metodo recuperaCartasByComprador...");
	var idPrograma = $('#idPrograma').val();	
	var idComprador = $('#idComprador').val();
	console.log("idPrograma: "+idPrograma);
	console.log("idComprador: "+idComprador);
	$.ajax({
		   async: false,
		   type: "POST",
		   url: "recuperaCartasByComprador",
		   data: "idPrograma="+idPrograma+"&idComprador="+idComprador, 
		   success: function(data){
				$('#cartas').html(data).ready(function () {
					$("#cartas").css("display", "inline");
					
				});
		   }
		});//fin ajax
}
    