package mx.gob.comer.sipc.vistas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "concentrado_det_pagos_trimestres_v")
public class ReporteDetConcentradoPagosV {
	
	private long id;
	private long idPrograma;
	private String programa;
	private long ejercicio;
	private long idComprador;
	private String comprador;
	private String ciclo;
	private String producto;
	private String estados;
	private Double volumen1erTrimestre;
	private Double importe1erTrimestre;
	private Double volumen2doTrimestre;
	private Double importe2doTrimestre;
	private Double volumen3erTrimestre;
	private Double importe3erTrimestre;
	private Double volumen4toTrimestre;
	private Double importe4toTrimestre;
	private String programaDesc;
	private String cartaAdhesion;
	private String entidadFederativa;
	private String municipio;

	@Id
	@Column(name =  "id")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column(name =  "id_programa")
	public long getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(long idPrograma) {
		this.idPrograma = idPrograma;
	}
	@Column(name =  "programa")
	public String getPrograma() {
		return programa;
	}	
	public void setPrograma(String programa) {
		this.programa = programa;
	}
	@Column(name =  "anio")
	public long getEjercicio() {
		return ejercicio;
	}
	public void setEjercicio(long ejercicio) {
		this.ejercicio = ejercicio;
	}
	@Column(name =  "id_comprador")
	public long getIdComprador() {
		return idComprador;
	}
	public void setIdComprador(long idComprador) {
		this.idComprador = idComprador;
	}
	@Column(name =  "comprador")
	public String getComprador() {
		return comprador;
	}
	public void setComprador(String comprador) {
		this.comprador = comprador;
	}
	@Column(name =  "ciclo")
	public String getCiclo() {
		return ciclo;
	}
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	@Column(name =  "producto")
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	@Column(name =  "estados")
	public String getEstados() {
		return estados;
	}
	public void setEstados(String estados) {
		this.estados = estados;
	}
	@Column(name =  "volumen_1er_trimestre")
	public Double getVolumen1erTrimestre() {
		return volumen1erTrimestre;
	}
	public void setVolumen1erTrimestre(Double volumen1erTrimestre) {
		this.volumen1erTrimestre = volumen1erTrimestre;
	}
	@Column(name =  "importe_1er_trimestre")
	public Double getImporte1erTrimestre() {
		return importe1erTrimestre;
	}
	public void setImporte1erTrimestre(Double importe1erTrimestre) {
		this.importe1erTrimestre = importe1erTrimestre;
	}
	@Column(name =  "volumen_2do_trimestre")
	public Double getVolumen2doTrimestre() {
		return volumen2doTrimestre;
	}
	public void setVolumen2doTrimestre(Double volumen2doTrimestre) {
		this.volumen2doTrimestre = volumen2doTrimestre;
	}
	@Column(name =  "importe_2do_trimestre")
	public Double getImporte2doTrimestre() {
		return importe2doTrimestre;
	}
	public void setImporte2doTrimestre(Double importe2doTrimestre) {
		this.importe2doTrimestre = importe2doTrimestre;
	}
	@Column(name =  "volumen_3er_trimestre")
	public Double getVolumen3erTrimestre() {
		return volumen3erTrimestre;
	}
	public void setVolumen3erTrimestre(Double volumen3erTrimestre) {
		this.volumen3erTrimestre = volumen3erTrimestre;
	}
	@Column(name =  "importe_3er_trimestre")
	public Double getImporte3erTrimestre() {
		return importe3erTrimestre;
	}
	public void setImporte3erTrimestre(Double importe3erTrimestre) {
		this.importe3erTrimestre = importe3erTrimestre;
	}
	@Column(name =  "volumen_4to_trimestre")
	public Double getVolumen4toTrimestre() {
		return volumen4toTrimestre;
	}
	public void setVolumen4toTrimestre(Double volumen4toTrimestre) {
		this.volumen4toTrimestre = volumen4toTrimestre;
	}
	@Column(name =  "importe_4to_trimestre")
	public Double getImporte4toTrimestre() {
		return importe4toTrimestre;
	}
	public void setImporte4toTrimestre(Double importe4toTrimestre) {
		this.importe4toTrimestre = importe4toTrimestre;
	}
// INICIO AHS
	@Column(name =  "programa_desc")
	public String getProgramaDesc() {
		return programaDesc;
	}
	public void setProgramaDesc(String programaDesc) {
		this.programaDesc = programaDesc;
	}
	@Column(name =  "carta_adhesion")
	public String getCartaAdhesion() {
		return cartaAdhesion;
	}
	public void setCartaAdhesion(String cartaAdhesion) {
		this.cartaAdhesion = cartaAdhesion;
	}
	
	@Column(name =  "entidad_federativa")
	public String getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	
	@Column(name =  "nombre_municipio")
	public String getMunicipio() {
		return municipio;
	}
	
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
// FIN AHS
}