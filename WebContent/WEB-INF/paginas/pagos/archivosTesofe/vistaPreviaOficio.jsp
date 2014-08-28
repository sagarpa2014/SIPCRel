<%@taglib uri="/struts-tags" prefix="s"%>
<div class="clear"></div>
<BR>
<div class="withborder">
	<s:if test="%{errorOficioDuplicado!=true}">
		<span class="izquierda">
			<img width="312" height="102"  src="<s:url value="/images/logoSagarpa.png" />" />
		</span> 
		<span class="derecha">
			<font class="arial12bold">
				ASERCA<br>
				Coordinaci�n General de Comercializaci�n<br><br>
				 <s:property value="leyendaOficio"/>
			</font>
		</span>
		<div class="clear"></div>
		<br>
		<p class="arial12normal">
			No de Oficio <font class="arial12bold"><s:property value="claveOficio"/><s:property value="noOficio"/><s:property value="anioOficio"/></font><br>
			M�xico, D.F. <s:property value="fechaActual"/><br>
			 
		</p>
		<p class="arial12normal">
			<s:property value="destinatario.nombre"/><br>
			<s:property value="destinatario.puesto"/><br>
			P&nbsp;R&nbsp;E&nbsp;S&nbsp;E&nbsp;N&nbsp;T&nbsp;E
		</p>
		<p class="arial12normal">Con referencia a la operaci�n del esquema <s:property value="nombrePrograma"/></p>
		<p class="arial12normal">
			Al respecto, le informo que han sido autorizados los apoyos correspondientes 
			para que en lo conducente dentro del proceso de dispersi�n de los recursos para el 
			pago de los apoyos del citado esquema, del ciclo&nbsp;<font class="arial12bold"><s:property value="ciclo"/></font>,&nbsp;
			solicito a esa Direcci�n General a su 
			cargo se realicen las acciones necesarias para el registro y autorizaci�n de la 
			Cuenta de Liquidaci�n Certificada (CLC) y notificaci�n a esta Coordinaci�n General, 
			para realizar el proceso de vinculaci�n de archivos de pago TESOFE por medio del sistema SIAFF; 
			dicha autorizaci�n de emisi�n de apoyos se refiere a la dispersi�n de recursos correspondientes 
			a&nbsp;<font class="arial12bold"><s:property value="noDepositos"/></font>&nbsp;dep�sitos en cuentas bancarias a trav�s de la Tesorer�a de la Federaci�n, 
			lo que significa una erogaci�n de&nbsp;<font class="arial12bold"><s:property value="importeLetra"/></font>.
		</p>
		<br>
		<center>
			<font class="arial12normal">A&nbsp;T&nbsp;E&nbsp;N&nbsp;T&nbsp;A&nbsp;M&nbsp;E&nbsp;N&nbsp;T&nbsp;E </font><br>
			<font class="arial12normal">
				<s:property value="emisor.nombre"/><br><br><br>
				<s:property value="emisor.puesto"/>
			</font>
		</center>
		<br>
		<div id="cpp">
			<div id="frasecpp"><font class="arial12normal">Vo.Bo.</font></div>
			<div id="personal">
				<s:iterator value="lstPersonalVoBo" var="resultado"  status="itStatus">
					<font class="arial12normal"><s:property value="%{nombre}"/>&nbsp;<s:property value="%{paterno}"/>&nbsp;<s:property value="%{materno}"/></font><br>
				</s:iterator>				
			</div>
		</div>
		<div id="cpp">
			<div id="frasecpp"><font class="arial12normal">C.c.p</font></div>
			<div id="personal">
				<s:iterator value="lstPersonal" var="resultado"  status="itStatus">
					<font class="arial12normal"><s:property value="%{nombre}"/>&nbsp;<s:property value="%{paterno}"/>
					&nbsp;<s:property value="%{materno}"/>.-<s:property value="%{puesto}"/>.-Presente</font><br>
				</s:iterator>				
			</div>
		</div>
		<div class="clear"></div>
		<br>
		<div class="borderBottom" style="text-align:center"><font class="arial12bold">DETALLE DE PAGOS</font></div>
		<br>
		<div class="clear"></div>
		<table style="width:100%">
			<tr>
				<th>Comprador</th>
				<th>No. Carta</th>
				<s:if test="criterioPago==2 || criterioPago==3">
					<th>Etapa</th>
				</s:if>
				<s:if test="criterioPago==1 || criterioPago==3">
					<th>Volumen</th>
				</s:if>
				<th>Importe</th>
			</tr>
			<s:iterator value="lstPagosV" var="resultado">
				<tr>
					<td><s:property value="nombreComprador" /></td>
					<td><s:property value="noCarta" /></td>
					<s:if test="criterioPago==2 || criterioPago==3">
						<td align="center"><s:property value="%{etapa}"/></td>
					</s:if>
					<s:if test="criterioPago==1 || criterioPago==3">
						<td align="right"><s:text name="volumen"><s:param value="%{volumen}"/></s:text></td>
					</s:if>
					<td align="right"><s:text name="importe"><s:param value="%{importe}"/></s:text></td>
				</tr>	
			</s:iterator>	
			<tr>
				<td>&nbsp;</td>
				<td align="right"><font class="arial12bold">TOTALES:</font></td>
				<s:if test="criterioPago==2 || criterioPago==3">
					<td><s:property value="%{etapa}"/></td>
				</s:if>
				<s:if test="criterioPago==1 || criterioPago==3">
					<td align="right"><s:text name="volumen"><s:param value="%{totalVolumen}"/></s:text></td>
				</s:if>
				<td align="right">$&nbsp;<s:text name="importe"><s:param value="%{totalImporte}"/></s:text></td>
			</tr>
		</table>
	</s:if>
	<s:else>
		<span class="error">
			El n�mero de oficio ya se encuentra registrado, por favor verifique
		</span>	
	</s:else>
	<br>
	<div class="accion">
		<a href="#" class="boton" title="" onclick="cerrarVistaPreviaOficio()">Cerrar</a>
	</div>
</div>
