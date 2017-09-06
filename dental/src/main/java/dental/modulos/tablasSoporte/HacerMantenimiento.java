package dental.modulos.tablasSoporte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jfree.util.Log;
import org.primefaces.event.FileUploadEvent;

import dental.Conexion;
import dental.beans.ClaseAnamnesis;
import dental.beans.ClaseExamenClinico;
import dental.beans.ClaseHabitoOral;
import dental.beans.ClaseHigieneOral;
import dental.beans.Consentimiento;
import dental.beans.EstadoDiente;
import dental.beans.Tratamiento;
import dental.generales.ConsultarFuncionesAPI;
import dental.generales.IConstantes;
import dental.modulos.IConsultasDAO;

@ManagedBean
@ViewScoped
public class HacerMantenimiento extends ConsultarFuncionesAPI implements Serializable{

  /**
   * 
   */
  private static final long        serialVersionUID = 2844636940533798901L;

  private ClaseHigieneOral         claseHigieneOral;
  private ClaseHigieneOral         claseHigieneOralTrnsccon;
  private List<ClaseHigieneOral>   clasesHigieneOral;

  private ClaseHabitoOral          claseHabitoOral;
  private ClaseHabitoOral          claseHabitoOralTrnsccon;
  private List<ClaseHabitoOral>    clasesHabitoOral;

  private ClaseAnamnesis           claseAnamnesis;
  private ClaseAnamnesis           claseAnamnesisTrnsccon;
  private List<ClaseAnamnesis>     clasesAnamnesis;

  private ClaseExamenClinico       claseExamenClinico;
  private ClaseExamenClinico       claseExamenClinicoTrnsccon;
  private List<ClaseExamenClinico> clasesExamenClinico;
  
  private EstadoDiente      	   estadoDiente;
  private EstadoDiente       	   estadoDienteTrnsccon;
  private List<EstadoDiente> 	   estadosDiente;
  
  private Tratamiento				tratamiento;
  private List<Tratamiento>			tratamientos;
  
  private Consentimiento				consentimiento;
  private List<Consentimiento>			consentimientos;

  // privados

  /**
   * Valida si cumple las condiciones de clase higiene oral para ser guardado o
   * editado
   * 
   * @param aTransaccion
   * @return ok
   */
  private boolean isOkHigieneOral(String aTransaccion){
    boolean ok = true;

    if (aTransaccion.equals(IConstantes.TRANSACCION_CREAR)) {
      if (this.isVacio(this.claseHigieneOral.getNombre())) {
        ok = false;
        this.mostrarMensajeGlobal("campoEstaVacio", "advertencia");
      } else {
        this.claseHigieneOral.setNombre(this.claseHigieneOral.getNombre().trim());
      }

    } else {
      if (this.isVacio(this.claseHigieneOralTrnsccon.getNombre())) {
        ok = false;
        this.mostrarMensajeGlobal("campoEstaVacio", "advertencia");
      } else {
        this.claseHigieneOralTrnsccon.setNombre(this.claseHigieneOralTrnsccon.getNombre().trim());
      }

    }

    return ok;

  }

  private boolean isOkHabitoOral(String aTransaccion){
    boolean ok = true;

    if (aTransaccion.equals(IConstantes.TRANSACCION_CREAR)) {
      if (this.isVacio(this.claseHabitoOral.getNombre())) {
        ok = false;
        this.mostrarMensajeGlobal("campoEstaVacio", "advertencia");
      } else {
        this.claseHabitoOral.setNombre(this.claseHabitoOral.getNombre().trim());
      }

    } else {
      if (this.isVacio(this.claseHabitoOralTrnsccon.getNombre())) {
        ok = false;
        this.mostrarMensajeGlobal("campoEstaVacio", "advertencia");
      } else {
        this.claseHabitoOralTrnsccon.setNombre(this.claseHabitoOralTrnsccon.getNombre().trim());
      }

    }

    return ok;

  }

  private boolean isOkAnamnesis(String aTransaccion){
    boolean ok = true;

    if (aTransaccion.equals(IConstantes.TRANSACCION_CREAR)) {
      if (this.isVacio(this.claseAnamnesis.getNombre())) {
        ok = false;
        this.mostrarMensajeGlobal("campoEstaVacio", "advertencia");
      } else {
        this.claseAnamnesis.setNombre(this.claseAnamnesis.getNombre().trim());
      }

    } else {
      if (this.isVacio(this.claseAnamnesisTrnsccon.getNombre())) {
        ok = false;
        this.mostrarMensajeGlobal("campoEstaVacio", "advertencia");
      } else {
        this.claseAnamnesisTrnsccon.setNombre(this.claseAnamnesisTrnsccon.getNombre().trim());
      }

    }

    return ok;

  }

  private boolean isOkExamenClinico(String aTransaccion){
    boolean ok = true;

    if (aTransaccion.equals(IConstantes.TRANSACCION_CREAR)) {
      if (this.isVacio(this.claseExamenClinico.getNombre())) {
        ok = false;
        this.mostrarMensajeGlobal("campoEstaVacio", "advertencia");
      } else {
        this.claseExamenClinico.setNombre(this.claseExamenClinico.getNombre().trim());
      }

    } else {
      if (this.isVacio(this.claseExamenClinicoTrnsccon.getNombre())) {
        ok = false;
        this.mostrarMensajeGlobal("campoEstaVacio", "advertencia");
      } else {
        this.claseExamenClinicoTrnsccon.setNombre(this.claseExamenClinicoTrnsccon.getNombre().trim());
      }

    }

    return ok;

  }

  // publicos

  /**
   * Este método asigna un objeto del listado de clases de higiene oral para
   * relizarle operaciones distintas
   * 
   * @param aClaseHigieneOral
   * @param aVista
   */
  public void asignarClaseHigieneOral(ClaseHigieneOral aClaseHigieneOral, String aVista){

    try {

      this.claseHigieneOralTrnsccon = aClaseHigieneOral;

      if (aVista != null && aVista.equals("MODAL_EDITAR")) {
        this.abrirModal("panelEdicion");

      } else {

        this.abrirModal("panelEliminacion");

      }

    } catch (Exception e) {
      IConstantes.log.error(e, e);
    }

  }

  /**
   * Cancela la edición o eliminación de una clase de higiene oral y lo vuelve a
   * su estado original
   * 
   * @param aVista
   */
  public void cancelarHigieneTransaccion(String aVista){
    try {

      this.claseHigieneOralTrnsccon = null;
      this.getClaseHigieneOralTrnsccon();
      this.clasesHigieneOral = null;
      this.getClasesHigieneOral();

      if (aVista != null && aVista.equals("MODAL_EDITAR")) {
        this.cerrarModal("panelEdicion");

      } else if (aVista != null && aVista.equals("MODAL_ELIMINAR")) {
        this.cerrarModal("panelEliminacion");

      }

    } catch (Exception e) {

      IConstantes.log.error(e, e);

    }

  }

  /**
   * Elimina un registro de clase de higiene oral
   */
  public void eliminarHigieneOral(){

    Conexion conexion = new Conexion();
    try {

      conexion.setAutoCommitBD(false);
      this.claseHigieneOralTrnsccon.getCamposBD();
      conexion.eliminarBD(this.claseHigieneOralTrnsccon.getEstructuraTabla().getTabla(),
          this.claseHigieneOralTrnsccon.getEstructuraTabla().getLlavePrimaria());
      conexion.commitBD();
      this.mostrarMensajeGlobal("eliminacionExitosa", "exito");

    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
      this.mostrarMensajeGlobal("No se puede eliminar la Higiene Oral, tiene otros elementos asociados", "error");
  	 

    } finally {
      conexion.cerrarConexion();
    }

    // reseteo de variables
    this.claseHigieneOralTrnsccon = null;
    this.getClaseHigieneOralTrnsccon();
    this.clasesHigieneOral = null;
    this.getClasesHigieneOral();

  }

  /**
   * Edita un registro de clase higiene oral
   */
  public void editarHigieneOral(){
    Conexion conexion = new Conexion();

    try {
      if (isOkHigieneOral(IConstantes.TRANSACCION_EDITAR)) {

        conexion.setAutoCommitBD(false);
        this.claseHigieneOralTrnsccon.getCamposBD();
        conexion.actualizarBD(this.claseHigieneOralTrnsccon.getEstructuraTabla().getTabla(),
            this.claseHigieneOralTrnsccon.getEstructuraTabla().getPersistencia(),
            this.claseHigieneOralTrnsccon.getEstructuraTabla().getLlavePrimaria(), null);
        conexion.commitBD();

        this.mostrarMensajeGlobal("edicionExitosa", "exito");
        this.cerrarModal("panelEdicion");

        // reseteo de variables
        this.clasesHigieneOral = null;
        this.getClasesHigieneOral();
      }

    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
    } finally {
      conexion.cerrarConexion();
    }

  }

  /**
   * Crea un nuevo registro de clase higiene oral
   */
  public void crearHigieneOral(){
    Conexion conexion = new Conexion();

    try {
      if (isOkHigieneOral(IConstantes.TRANSACCION_CREAR)) {
        conexion.setAutoCommitBD(false);

        this.claseHigieneOral.setIndicativoVigencia(IConstantes.ACTIVO);

        this.claseHigieneOral.getCamposBD();
        conexion.insertarBD(this.claseHigieneOral.getEstructuraTabla().getTabla(),
            this.claseHigieneOral.getEstructuraTabla().getPersistencia());
        conexion.commitBD();

        this.mostrarMensajeGlobal("creacionExitosa", "exito");

        // reseteo de variables
        this.claseHigieneOral = null;
        this.getClaseHigieneOral();
        this.clasesHigieneOral = null;
        this.getClasesHigieneOral();

      }

    } catch (Exception e) {
    	System.out.println(e.toString());
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
    } finally {
      conexion.cerrarConexion();
    }

  }

  /**
   * Este método borra el formulario de creación de un iva
   */
  public void cancelarHigieneOral(){

    try {
      this.claseHigieneOral = null;
      this.getClaseHigieneOral();
      this.clasesHigieneOral = null;
      this.getClasesHigieneOral();

    } catch (Exception e) {
      IConstantes.log.error(e, e);
    }

  }

  /**
   * metodos relacionados con habito oral
   */

  public void crearHabitoOral(){
    Conexion conexion = new Conexion();

    try {
      if (isOkHabitoOral(IConstantes.TRANSACCION_CREAR)) {
        conexion.setAutoCommitBD(false);

        this.claseHabitoOral.setIndicativoVigencia(IConstantes.ACTIVO);

        this.claseHabitoOral.getCamposBD();
        conexion.insertarBD(this.claseHabitoOral.getEstructuraTabla().getTabla(),
            this.claseHabitoOral.getEstructuraTabla().getPersistencia());
        conexion.commitBD();

        this.mostrarMensajeGlobal("creacionExitosa", "exito");

        // reseteo de variables
        this.claseHabitoOral = null;
        this.getClaseHabitoOral();
        this.clasesHabitoOral = null;
        this.getClasesHabitoOral();

      }

    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
    } finally {
      conexion.cerrarConexion();
    }

  }

  public void cancelarHabitoOral(){
    try {
      this.claseHabitoOral = null;
      this.getClaseHabitoOral();
      this.clasesHabitoOral = null;
      this.getClasesHabitoOral();

    } catch (Exception e) {
      IConstantes.log.error(e, e);
    }
  }

  public void asignarClaseHabitoOral(ClaseHabitoOral aClaseHabitoOral, String aVista){

    try {

      this.claseHabitoOralTrnsccon = aClaseHabitoOral;

      if (aVista != null && aVista.equals("MODAL_EDITAR")) {
        this.abrirModal("panelEdicion");

      } else {

        this.abrirModal("panelEliminacion");

      }

    } catch (Exception e) {
      IConstantes.log.error(e, e);
    }

  }

  public void editarHabitoOral(){
    Conexion conexion = new Conexion();

    try {
      if (isOkHabitoOral(IConstantes.TRANSACCION_EDITAR)) {

        conexion.setAutoCommitBD(false);
        this.claseHabitoOralTrnsccon.getCamposBD();
        conexion.actualizarBD(this.claseHabitoOralTrnsccon.getEstructuraTabla().getTabla(),
            this.claseHabitoOralTrnsccon.getEstructuraTabla().getPersistencia(),
            this.claseHabitoOralTrnsccon.getEstructuraTabla().getLlavePrimaria(), null);
        conexion.commitBD();

        this.mostrarMensajeGlobal("edicionExitosa", "exito");
        this.cerrarModal("panelEdicion");

        // reseteo de variables
        this.clasesHabitoOral = null;
        this.getClasesHabitoOral();
      }

    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
    } finally {
      conexion.cerrarConexion();
    }

  }

  public void cancelarHabitoTransaccion(String aVista){
    try {

      this.claseHabitoOralTrnsccon = null;
      this.getClaseHabitoOralTrnsccon();
      this.clasesHabitoOral = null;
      this.getClasesHabitoOral();

      if (aVista != null && aVista.equals("MODAL_EDITAR")) {
        this.cerrarModal("panelEdicion");

      } else if (aVista != null && aVista.equals("MODAL_ELIMINAR")) {
        this.cerrarModal("panelEliminacion");

      }

    } catch (Exception e) {

      IConstantes.log.error(e, e);

    }

  }

  public void eliminarHabitoOral(){
    Conexion conexion = new Conexion();
    try {

      conexion.setAutoCommitBD(false);
      this.claseHabitoOralTrnsccon.getCamposBD();
      conexion.eliminarBD(this.claseHabitoOralTrnsccon.getEstructuraTabla().getTabla(),
          this.claseHabitoOralTrnsccon.getEstructuraTabla().getLlavePrimaria());
      conexion.commitBD();
      this.mostrarMensajeGlobal("eliminacionExitosa", "exito");

    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
      this.mostrarMensajeGlobal("No se puede eliminar el Hábito, tiene otros elementos asociados", "error");
 	 

    } finally {
      conexion.cerrarConexion();
    }

    // reseteo de variables
    this.claseHabitoOralTrnsccon = null;
    this.getClaseHabitoOralTrnsccon();
    this.clasesHabitoOral = null;
    this.getClasesHabitoOral();
  }

  public void asignarClaseExamenClinico(ClaseExamenClinico aClaseExamenClinico, String aVista){

    try {

      this.claseExamenClinicoTrnsccon = aClaseExamenClinico;

      if (aVista != null && aVista.equals("MODAL_EDITAR")) {
        this.abrirModal("panelEdicion");

      } else {

        this.abrirModal("panelEliminacion");

      }

    } catch (Exception e) {
      IConstantes.log.error(e, e);
    }

  }

  /**
   * Crea un nuevo registro de clase examen clinico
   */
  public void crearExamenClinico(){
    Conexion conexion = new Conexion();

    try {
      if (isOkExamenClinico(IConstantes.TRANSACCION_CREAR)) {
        conexion.setAutoCommitBD(false);

        this.claseExamenClinico.setIndicativoVigencia(IConstantes.ACTIVO);

        this.claseExamenClinico.getCamposBD();
        conexion.insertarBD(this.claseExamenClinico.getEstructuraTabla().getTabla(),
            this.claseExamenClinico.getEstructuraTabla().getPersistencia());
        conexion.commitBD();

        this.mostrarMensajeGlobal("creacionExitosa", "exito");

        // reseteo de variables
        this.claseExamenClinico = null;
        this.getClaseExamenClinico();
        this.claseExamenClinico = null;
        this.getClasesExamenClinico();

      }

    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
    } finally {
      conexion.cerrarConexion();
    }
  }

  public void cancelarExamenClinicoTransaccion(String aVista){
    try {

      this.claseExamenClinicoTrnsccon = null;
      this.getClaseExamenClinicoTrnsccon();
      this.claseExamenClinico = null;
      this.getClaseExamenClinico();

      if (aVista != null && aVista.equals("MODAL_EDITAR")) {
        this.cerrarModal("panelEdicion");

      } else if (aVista != null && aVista.equals("MODAL_ELIMINAR")) {
        this.cerrarModal("panelEliminacion");

      }

    } catch (Exception e) {

      IConstantes.log.error(e, e);

    }

  }

  /**
   * Elimina un registro de clase examen clinico
   */
  public void eliminarExamenClinico(){

    Conexion conexion = new Conexion();
    try {

      conexion.setAutoCommitBD(false);
      this.claseExamenClinicoTrnsccon.getCamposBD();
      conexion.eliminarBD(this.claseExamenClinicoTrnsccon.getEstructuraTabla().getTabla(),
          this.claseExamenClinicoTrnsccon.getEstructuraTabla().getLlavePrimaria());
      conexion.commitBD();
      this.mostrarMensajeGlobal("eliminacionExitosa", "exito");

    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
      this.mostrarMensajeGlobal("No se puede eliminar el Examen clínico, tiene otros elementos asociados", "error");
  	 

    } finally {
      conexion.cerrarConexion();
    }

    // reseteo de variables
    this.claseExamenClinicoTrnsccon = null;
    this.getClaseExamenClinicoTrnsccon();
    this.claseExamenClinico = null;
    this.getClasesExamenClinico();

  }

  /**
   * Edita un registro de clase examen clinico
   */
  public void editarExamenClinico(){
    Conexion conexion = new Conexion();

    try {
      if (isOkExamenClinico(IConstantes.TRANSACCION_EDITAR)) {

        conexion.setAutoCommitBD(false);
        this.claseExamenClinicoTrnsccon.getCamposBD();
        conexion.actualizarBD(this.claseExamenClinicoTrnsccon.getEstructuraTabla().getTabla(),
            this.claseExamenClinicoTrnsccon.getEstructuraTabla().getPersistencia(),
            this.claseExamenClinicoTrnsccon.getEstructuraTabla().getLlavePrimaria(), null);
        conexion.commitBD();

        this.mostrarMensajeGlobal("edicionExitosa", "exito");
        this.cerrarModal("panelEdicion");

        // reseteo de variables
        this.setClasesExamenClinico(null);
        this.getClasesExamenClinico();
      }

    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
    } finally {
      conexion.cerrarConexion();
    }

  }

  public void cancelarExamenClinico(){

    try {
      this.claseExamenClinico = null;
      this.getClaseExamenClinico();
      this.clasesExamenClinico = null;
      this.getClasesExamenClinico();

    } catch (Exception e) {
      IConstantes.log.error(e, e);
    }

  }

  //
  public void asignarClaseAnamnesis(ClaseAnamnesis aClaseAnamnesis, String aVista){

    try {

      this.claseAnamnesisTrnsccon = aClaseAnamnesis;

      if (aVista != null && aVista.equals("MODAL_EDITAR")) {
        this.abrirModal("panelEdicion");

      } else {

        this.abrirModal("panelEliminacion");

      }

    } catch (Exception e) {
      IConstantes.log.error(e, e);
    }

  }

  /**
   * Crea un nuevo registro de clase anamnesis
   */
  public void crearAnamnesis(){
    Conexion conexion = new Conexion();

    try {
      if (isOkAnamnesis(IConstantes.TRANSACCION_CREAR)) {
        conexion.setAutoCommitBD(false);

        this.claseAnamnesis.setIndicativoVigencia(IConstantes.ACTIVO);

        this.claseAnamnesis.getCamposBD();
        conexion.insertarBD(this.claseAnamnesis.getEstructuraTabla().getTabla(),
            this.claseAnamnesis.getEstructuraTabla().getPersistencia());
        conexion.commitBD();

        this.mostrarMensajeGlobal("creacionExitosa", "exito");

        // reseteo de variables
        this.claseAnamnesis = null;
        this.getClasesAnamnesis();

      }

    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
    } finally {
      conexion.cerrarConexion();
    }
  }

  public void cancelarAnamnesisTransaccion(String aVista){
    try {

      this.claseAnamnesisTrnsccon = null;
      this.getClaseAnamnesisTrnsccon();
      this.claseAnamnesis = null;
      this.getClaseAnamnesis();

      if (aVista != null && aVista.equals("MODAL_EDITAR")) {
        this.cerrarModal("panelEdicion");

      } else if (aVista != null && aVista.equals("MODAL_ELIMINAR")) {
        this.cerrarModal("panelEliminacion");

      }

    } catch (Exception e) {

      IConstantes.log.error(e, e);

    }

  }

  /**
   * Elimina un registro de clase de higiene oral
   */
  public void eliminarAnamnesis(){

    Conexion conexion = new Conexion();
    try {

      conexion.setAutoCommitBD(false);
      this.claseAnamnesisTrnsccon.getCamposBD();
      conexion.eliminarBD(this.claseAnamnesisTrnsccon.getEstructuraTabla().getTabla(),
          this.claseAnamnesisTrnsccon.getEstructuraTabla().getLlavePrimaria());
      conexion.commitBD();
      this.mostrarMensajeGlobal("eliminacionExitosa", "exito");

    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
      this.mostrarMensajeGlobal("No se puede eliminar la anamnesis, tiene otros elementos asociados", "error");
  	 

    } finally {
      conexion.cerrarConexion();
    }

    // reseteo de variables
    this.claseAnamnesisTrnsccon = null;
    this.getClaseAnamnesisTrnsccon();
    this.clasesAnamnesis = null;
    this.getClasesAnamnesis();

  }

  /**
   * Edita un registro de clase anamnesis
   */
  public void editarAnamnesis(){
    Conexion conexion = new Conexion();

    try {
      if (isOkAnamnesis(IConstantes.TRANSACCION_EDITAR)) {

        conexion.setAutoCommitBD(false);
        this.claseAnamnesisTrnsccon.getCamposBD();
        conexion.actualizarBD(this.claseAnamnesisTrnsccon.getEstructuraTabla().getTabla(),
            this.claseAnamnesisTrnsccon.getEstructuraTabla().getPersistencia(), 
            this.claseAnamnesisTrnsccon.getEstructuraTabla().getLlavePrimaria(),
            null);
        conexion.commitBD();

        this.mostrarMensajeGlobal("edicionExitosa", "exito");
        this.cerrarModal("panelEdicion");

        // reseteo de variables
        this.claseAnamnesisTrnsccon=null;
        this.setClasesAnamnesis(null);
        this.getClasesAnamnesis();
      }

    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
    } finally {
      conexion.cerrarConexion();
    }

  }

  public void cancelarAnamnesis(){

    try {
      this.clasesAnamnesis = null;
      

    } catch (Exception e) {
      IConstantes.log.error(e, e);
    }

  }
  
  /**
   * Crea un nuevo registro de estado del diente
   */
  public void crearEstadoDiente(){
    Conexion conexion = new Conexion();

    try {
        conexion.setAutoCommitBD(false);        
        if(!this.estadoDiente.getModoEdicion()){  
        	
        	this.estadoDiente.setEstado(IConstantes.ACTIVO);
        	this.estadoDiente.setId(conexion.getConsecutivo(EstadoDiente.MAX));
           	this.estadoDiente.getCamposBD();
             conexion.insertarBD(this.estadoDiente.getEstructuraTabla().getTabla(),
            this.estadoDiente.getEstructuraTabla().getPersistencia());
        }else{ //edito el estado
        	this.estadoDiente.getCamposBD();
        	conexion.actualizarBD(this.estadoDiente.getEstructuraTabla().getTabla(),
                    this.estadoDiente.getEstructuraTabla().getPersistencia(), 
                    this.estadoDiente.getEstructuraTabla().getLlavePrimaria(),
                    null);
        }
        conexion.commitBD();

        this.mostrarMensajeGlobal("Estado Guardado satisfactoriamente", "exito");
        // reseteo de variables
        this.estadoDiente = new EstadoDiente();
        this.getEstadosDiente();


    } catch (Exception e) {
    	System.out.println(e.toString());
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
    } finally {
      conexion.cerrarConexion();
    }
  }
  public void asignarEstadoDiente(EstadoDiente e){
      this.estadoDiente=e;
      this.estadoDiente.setModoEdicion(true);
  }
  
  
  public void cancelarEstadoTransaccion(String aVista){
    try {

      this.claseAnamnesisTrnsccon = null;
      this.getClaseAnamnesisTrnsccon();
      this.claseAnamnesis = null;
      this.getClaseAnamnesis();

      if (aVista != null && aVista.equals("MODAL_EDITAR")) {
        this.cerrarModal("panelEdicion");

      } else if (aVista != null && aVista.equals("MODAL_ELIMINAR")) {
        this.cerrarModal("panelEliminacion");

      }

    } catch (Exception e) {

      IConstantes.log.error(e, e);

    }

  }

  /**
   * Elimina un registro del estado del diente
   */
  public void eliminarEstadoDiente( EstadoDiente ed){

    Conexion conexion = new Conexion();
    try {

      conexion.setAutoCommitBD(false);
      //revisa que el estado no esté en uso
      if(!IConsultasDAO.buscarEstadoDiente(ed)){
    	  ed.getCamposBD();
    	  conexion.eliminarBD(ed.getEstructuraTabla().getTabla(),
    			  ed.getEstructuraTabla().getLlavePrimaria());
	     conexion.commitBD();
	      this.mostrarMensajeGlobal("eliminacionExitosa", "exito");
	   }else{
		   this.mostrarMensajeGlobal("No se puede eliminar el estado, tiene otros elementos asociados", "error");
	   }

    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
      

    } finally {
      conexion.cerrarConexion();
    }

    // reseteo de variables
    this.estadoDiente = new EstadoDiente();
    this.getEstadosDiente();

  }
  
  public void cancelarEstadoDiente(){
	    try {

	      this.estadoDiente = new EstadoDiente();
	      this.estadoDienteTrnsccon=null;	     

	    } catch (Exception e) {

	      IConstantes.log.error(e, e);

	    }

	  }


  /**
	 * Limpia la foto cargada para cargar otra
	 */
  public void limpiarFotoCargada(Integer opcion) {
		switch (opcion){
			case 1:
				this.estadoDiente.setZona1(null);
				this.estadoDiente.setZona1File(null);
				break;
			case 2:
				this.estadoDiente.setZona2(null);
				this.estadoDiente.setZona2File(null);
				break;
			case 3:
				this.estadoDiente.setZona3(null);
				this.estadoDiente.setZona3File(null);
				break;
			case 4:
				this.estadoDiente.setZona4(null);
				this.estadoDiente.setZona4File(null);
				break;
			case 5:
				this.estadoDiente.setZona5(null);
				this.estadoDiente.setZona5File(null);
				break;
			case 0:
				this.estadoDiente.setZonaTotal(null);
				this.estadoDiente.setZonaTotalFile(null);
				break;
		}
	}

  public void recibirFoto(FileUploadEvent event) {

	try {
		Integer opcion =Integer.valueOf( (String)event.getComponent().getAttributes().get("opcion"));
		
		switch (opcion){
		case 1:
			this.estadoDiente.setZona1File(event.getFile());
			this.estadoDiente.setZona1(event.getFile().getContents());
			break;
		case 2:
			this.estadoDiente.setZona2File(event.getFile());
			this.estadoDiente.setZona2(event.getFile().getContents());
			break;
		case 3:
			this.estadoDiente.setZona3File(event.getFile());
			this.estadoDiente.setZona3(event.getFile().getContents());
			break;
		case 4:
			this.estadoDiente.setZona4File(event.getFile());
			this.estadoDiente.setZona4(event.getFile().getContents());
			break;
		case 5:
			this.estadoDiente.setZona5File(event.getFile());
			this.estadoDiente.setZona5(event.getFile().getContents());
			break;
		case 0:
			this.estadoDiente.setZonaTotalFile(event.getFile());
			this.estadoDiente.setZonaTotal(event.getFile().getContents());
			break;
	}
	
	this.mostrarMensajeGlobal("archivoRecibido", "advertencia");
	} catch (Exception e) {
		IConstantes.log.error(e, e);
	}

  }
  
  public void recibirFotoTratamiento(FileUploadEvent event) {
		try {
				this.tratamiento.setImagenFile(event.getFile());
				this.tratamiento.setImagen(event.getFile().getContents());
				this.mostrarMensajeGlobal("archivoRecibido", "advertencia");
	} catch (Exception e) {
		IConstantes.log.error(e, e);
	}

  }
  public void limpiarFotoTratamiento() {
	this.tratamiento.setImagenFile(null);
	this.tratamiento.setImagen(null);
  }
  
  
  /**
   * Crea un nuevo registro del tratamiento
   */
  public void guardarTratamiento(){
    Conexion conexion = new Conexion();

    try {
        conexion.setAutoCommitBD(false);
        if(!this.tratamiento.getModoEdicion()){  
        	
        	this.tratamiento.setEstado(true);
        	this.tratamiento.setId(conexion.getConsecutivo(Tratamiento.MAX));
           	this.tratamiento.getCamposBD();
             conexion.insertarBD(this.tratamiento.getEstructuraTabla().getTabla(),
            this.tratamiento.getEstructuraTabla().getPersistencia());
        }else{ //edito el estado
        	this.tratamiento.getCamposBD();
        	conexion.actualizarBD(this.tratamiento.getEstructuraTabla().getTabla(),
                    this.tratamiento.getEstructuraTabla().getPersistencia(), 
                    this.tratamiento.getEstructuraTabla().getLlavePrimaria(),
                    null);
        }      
        conexion.commitBD();
        this.mostrarMensajeGlobal("Tratamiento Guardado", "exito");
        // reseteo de variables
        this.tratamiento = new Tratamiento();
        this.getEstadosDiente();


    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
    } finally {
      conexion.cerrarConexion();
    }
  }
  
  public void asignarTratamiento(Tratamiento t){
      this.tratamiento=t;
      this.tratamiento.setModoEdicion(true);
  }
  
  /**
   * Elimina un registro del Tratamiento
   */
  public void eliminarTratamiento( Tratamiento t){

    Conexion conexion = new Conexion();
    try {

      conexion.setAutoCommitBD(false);
      //revisa que el estado no esté en uso
     	  t.getCamposBD();
    	  conexion.eliminarBD(t.getEstructuraTabla().getTabla(),
    			  t.getEstructuraTabla().getLlavePrimaria());
	     conexion.commitBD();
	      this.mostrarMensajeGlobal("eliminacionExitosa", "exito");

    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
      this.mostrarMensajeGlobal("No se puede eliminar el tratamiento, tiene otros elementos asociados", "error");
 	 
    } finally {
      conexion.cerrarConexion();
    }

    // reseteo de variables
    this.estadoDiente = new EstadoDiente();
    this.getEstadosDiente();

  }
  
  
  public void cancelarTratamiento(){
    try {

      this.tratamiento= new Tratamiento();     

    } catch (Exception e) {

      IConstantes.log.error(e, e);

    }
  }
  
  /**
   * Crea un nuevo registro del tratamiento
   */
  public void guardarConsentimiento(){
    Conexion conexion = new Conexion();

    try {
        conexion.setAutoCommitBD(false);
        if(!this.consentimiento.getModoEdicion()){  
        	
        	this.consentimiento.setEstado(true);
        	this.consentimiento.setId(conexion.getConsecutivo(Consentimiento.MAX));
           	this.consentimiento.getCamposBD();
             conexion.insertarBD(this.consentimiento.getEstructuraTabla().getTabla(),
            this.consentimiento.getEstructuraTabla().getPersistencia());
        }else{ //edito el estado
        	this.consentimiento.getCamposBD();
        	conexion.actualizarBD(this.consentimiento.getEstructuraTabla().getTabla(),
                    this.consentimiento.getEstructuraTabla().getPersistencia(), 
                    this.consentimiento.getEstructuraTabla().getLlavePrimaria(),
                    null);
        }      
        conexion.commitBD();
        this.mostrarMensajeGlobal("Consentimiento Guardado", "exito");
        // reseteo de variables
        this.consentimiento = new Consentimiento();
        this.getConsentimientos();


    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
    } finally {
      conexion.cerrarConexion();
    }
  }
  
  public void asignarConsentimiento(Consentimiento t){
      this.consentimiento=t;
      this.consentimiento.setModoEdicion(true);
  }
  
  /**
   * Elimina un registro del Consentimiento
   */
  public void eliminarConsentimiento( Consentimiento t){

    Conexion conexion = new Conexion();
    try {

      conexion.setAutoCommitBD(false);
      //revisa que el estado no esté en uso
     	  t.getCamposBD();
    	  conexion.eliminarBD(t.getEstructuraTabla().getTabla(),
    			  t.getEstructuraTabla().getLlavePrimaria());
	     conexion.commitBD();
	      this.mostrarMensajeGlobal("eliminacionExitosa", "exito");

    } catch (Exception e) {
      conexion.rollbackBD();
      this.mostrarMensajeGlobal("transaccionFallida", "error");
      this.mostrarMensajeGlobal("No se puede eliminar el consentimiento, tiene otros elementos asociados", "error");
 	 
    } finally {
      conexion.cerrarConexion();
    }

    // reseteo de variables
    this.estadoDiente = new EstadoDiente();
    this.getEstadosDiente();

  }
  
  
  public void cancelarConsentimiento(){
	    try {

	      this.consentimiento= new Consentimiento();     

	    } catch (Exception e) {

	      IConstantes.log.error(e, e);

	    }

	  }

	  
  //
  // listados y etructuras

  // gets/sets

  /**
   * Este método lo usamos para obtener el objeto que crea las clases de higiene
   * oral
   * 
   * @return claseHigieneOral
   */
  public ClaseHigieneOral getClaseHigieneOral(){
    try {
      if (this.claseHigieneOral == null) {
        this.claseHigieneOral = new ClaseHigieneOral();
      }
    } catch (Exception e) {
      Log.error(e, e);
    }
    return claseHigieneOral;
  }

  /**
   * Este método lo usamos para establecer el objeto que crea las clases de
   * higiene oral
   * 
   * @param claseHigieneOral
   */
  public void setClaseHigieneOral(ClaseHigieneOral claseHigieneOral){
    this.claseHigieneOral = claseHigieneOral;
  }

  public ClaseHigieneOral getClaseHigieneOralTrnsccon(){
    try {

      if (this.claseHigieneOralTrnsccon == null) {
        this.claseHigieneOralTrnsccon = new ClaseHigieneOral();
      }

    } catch (Exception e) {
      Log.error(e, e);
    }

    return claseHigieneOralTrnsccon;
  }

  public void setClaseHigieneOralTrnsccon(ClaseHigieneOral claseHigieneOralTrnsccon){
    this.claseHigieneOralTrnsccon = claseHigieneOralTrnsccon;
  }

  /**
   * Obtiene un listado de higienes orales
   * 
   * @return clasesHigieneOral
   */
  public List<ClaseHigieneOral> getClasesHigieneOral(){

    try {
      this.clasesHigieneOral = IConsultasDAO.getClasesHigieneOral(null);

    } catch (Exception e) {
      IConstantes.log.error(e, e);
    }

    return clasesHigieneOral;
  }

  public void setClasesHigieneOral(List<ClaseHigieneOral> clasesHigieneOral){
    this.clasesHigieneOral = clasesHigieneOral;
  }

  public ClaseHabitoOral getClaseHabitoOral(){
    if (this.claseHabitoOral == null) {
      this.claseHabitoOral = new ClaseHabitoOral();
    }
    return claseHabitoOral;
  }

  public void setClaseHabitoOral(ClaseHabitoOral claseHabitoOral){
    this.claseHabitoOral = claseHabitoOral;
  }

  public ClaseHabitoOral getClaseHabitoOralTrnsccon(){
    if (this.claseHabitoOralTrnsccon == null) {
      this.claseHabitoOralTrnsccon = new ClaseHabitoOral();
    }
    return claseHabitoOralTrnsccon;
  }

  public void setClaseHabitoOralTrnsccon(ClaseHabitoOral claseHabitoOralTrnsccon){
    this.claseHabitoOralTrnsccon = claseHabitoOralTrnsccon;
  }

  public List<ClaseHabitoOral> getClasesHabitoOral(){
    try {
      this.clasesHabitoOral = IConsultasDAO.getClasesHabitoOral(null);
    } catch (Exception e) {
      IConstantes.log.error(e, e);
    }

    return clasesHabitoOral;
  }

  public void setClasesHabitoOral(List<ClaseHabitoOral> clasesHabitoOral){
    this.clasesHabitoOral = clasesHabitoOral;
  }

  public ClaseAnamnesis getClaseAnamnesis(){
    if (this.claseAnamnesis == null) {
      this.claseAnamnesis = new ClaseAnamnesis();
    }
    return claseAnamnesis;
  }

  public void setClaseAnamnesis(ClaseAnamnesis claseAnamnesis){
    this.claseAnamnesis = claseAnamnesis;
  }

  public ClaseAnamnesis getClaseAnamnesisTrnsccon(){
    if (this.claseAnamnesisTrnsccon == null) {
      this.claseAnamnesisTrnsccon = new ClaseAnamnesis();
    }
    return claseAnamnesisTrnsccon;
  }

  public void setClaseAnamnesisTrnsccon(ClaseAnamnesis claseAnamnesisTrnsccon){
    this.claseAnamnesisTrnsccon = claseAnamnesisTrnsccon;
  }

  public List<ClaseAnamnesis> getClasesAnamnesis(){
    try {
      this.clasesAnamnesis = IConsultasDAO.getClasesAnamnesis(null);
    } catch (Exception e) {
      IConstantes.log.error(e, e);
    }

    return clasesAnamnesis;
  }

  public void setClasesAnamnesis(List<ClaseAnamnesis> clasesAnamnesis){
    this.clasesAnamnesis = clasesAnamnesis;
  }

  public ClaseExamenClinico getClaseExamenClinico(){
    if (this.claseExamenClinico == null) {
      this.claseExamenClinico = new ClaseExamenClinico();
    }
    return claseExamenClinico;
  }

  public void setClaseExamenClinico(ClaseExamenClinico claseExamenClinico){
    this.claseExamenClinico = claseExamenClinico;
  }

  public ClaseExamenClinico getClaseExamenClinicoTrnsccon(){
    if (this.claseExamenClinicoTrnsccon == null) {
      this.claseExamenClinicoTrnsccon = new ClaseExamenClinico();
    }
    return claseExamenClinicoTrnsccon;
  }

  public void setClaseExamenClinicoTrnsccon(ClaseExamenClinico claseExamenClinicoTrnsccon){
    this.claseExamenClinicoTrnsccon = claseExamenClinicoTrnsccon;
  }

  public List<ClaseExamenClinico> getClasesExamenClinico(){
    try {
      this.clasesExamenClinico = IConsultasDAO.getClasesExamenClinico(null);
    } catch (Exception e) {
      IConstantes.log.error(e, e);
    }

    return clasesExamenClinico;
  }

  public void setClasesExamenClinico(List<ClaseExamenClinico> clasesExamenClinico){
    this.clasesExamenClinico = clasesExamenClinico;
  }

  public EstadoDiente getEstadoDiente() {
	  if(this.estadoDiente==null)
		  this.estadoDiente= new EstadoDiente();
	return estadoDiente;
  }

  public void setEstadoDiente(EstadoDiente estadoDiente) {
	this.estadoDiente = estadoDiente;
  }

  public EstadoDiente getEstadoDienteTrnsccon() {
	  if(this.estadoDienteTrnsccon==null)
		  this.estadoDienteTrnsccon= new EstadoDiente();
	return estadoDienteTrnsccon;
  }

  public void setEstadoDienteTrnsccon(EstadoDiente estadoDienteTrnsccon) {
	this.estadoDienteTrnsccon = estadoDienteTrnsccon;
  }

  public List<EstadoDiente> getEstadosDiente() {
	  try {
	      this.estadosDiente = IConsultasDAO.getEstadosDiente(null);
	    } catch (Exception e) {
	      IConstantes.log.error(e, e);	     
	    }

	return estadosDiente;
  }

  public void setEstadosDiente(List<EstadoDiente> estadosDiente) {
	this.estadosDiente = estadosDiente;
  }

	public Tratamiento getTratamiento() {
		if(tratamiento==null)
			tratamiento=new Tratamiento();
		return tratamiento;
	}
	
	public void setTratamiento(Tratamiento tratamiento) {
		this.tratamiento = tratamiento;
	}
	
	public List<Tratamiento> getTratamientos() {
		try {
	      this.tratamientos = IConsultasDAO.getTratamientos(null);
	    } catch (Exception e) {
	      IConstantes.log.error(e, e);
	      System.out.println("Get Tratamientos"+e.toString());
	    }

		return tratamientos;
	}
	
	public void setTratamientos(List<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}

	public Consentimiento getConsentimiento() {
		if(consentimiento==null)
			consentimiento= new Consentimiento();
		return consentimiento;
	}

	public void setConsentimiento(Consentimiento consentimiento) {
		this.consentimiento = consentimiento;
	}

	public List<Consentimiento> getConsentimientos() {
		try {
				consentimientos = IConsultasDAO.getConsentimientos();				

			} catch (Exception e) {
				IConstantes.log.error(e, e);
			}
		return consentimientos;
	}

	public void setConsentimientos(List<Consentimiento> consentimientos) {
		this.consentimientos = consentimientos;
	}
	  
  

}
