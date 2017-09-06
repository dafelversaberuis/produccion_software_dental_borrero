package dental.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.primefaces.model.ScheduleEvent;

public class Cita implements Serializable  {
	
	private static final long serialVersionUID = -6484824531408941932L;
	private Integer id;
	private Usuario paciente;
	private Usuario profesional;
	private Integer consultorio;
	private Integer consulta;
	private Integer tratamiento;
	private Date fechaInicio;
	private Date fechaFin;
	
	private String detalleEvolucion;
	private String detalleEvolucionAutomatica;
	private String estado;
	private BigDecimal abono;
	private BigDecimal saldo;
	private String recibo;
	

	//Temporales
	private EstructuraTabla estructuraTabla;
	private Boolean modoEdicion;	
	private ScheduleEvent event;
	private Date fechaActual;
	
	public final static String     MAX            = "SELECT MAX(id) consecutivo  FROM cita";
	
	public Cita(ScheduleEvent event) {
		super();
		this.event = event;
		this.estructuraTabla= new EstructuraTabla();
		this.profesional= new Usuario();
		this.modoEdicion=false;
		this.consultorio=0;
		this.estado="R";
		this.abono=new BigDecimal("0");
		this.fechaActual= new Date();
	}
	
	public Cita( Usuario paciente) {
		super();
		this.paciente = paciente;
		this.estructuraTabla= new EstructuraTabla();
		this.profesional= new Usuario();
		this.modoEdicion=false;
		this.consultorio=0;
		this.estado="R";
		this.abono=new BigDecimal("0");
		
	}
	
	public Cita() {		
		this.estructuraTabla= new EstructuraTabla();
		this.profesional= new Usuario();
		this.modoEdicion=false;
		this.consultorio=0;
		this.estado="R";
		this.abono=new BigDecimal("0");
	}


	public void getCamposBD() {
		this.estructuraTabla.setTabla("cita");
		this.estructuraTabla.getLlavePrimaria().put("id", this.id);
		
		this.estructuraTabla.getPersistencia().put("id", this.id);		
		this.estructuraTabla.getPersistencia().put("paciente", this.paciente!=null?this.paciente.getIdDocumento():null);
		this.estructuraTabla.getPersistencia().put("profesional", this.profesional!=null?this.profesional.getIdDocumento():null);
		this.estructuraTabla.getPersistencia().put("fecha_inicio", this.fechaInicio);
		this.estructuraTabla.getPersistencia().put("fecha_fin", this.fechaFin);
		this.estructuraTabla.getPersistencia().put("consultorio", this.consultorio);
		this.estructuraTabla.getPersistencia().put("detalle_evolucion", this.detalleEvolucion);
		this.estructuraTabla.getPersistencia().put("detalle_evolucion_auto", this.detalleEvolucionAutomatica);
		this.estructuraTabla.getPersistencia().put("estado", this.estado);
		this.estructuraTabla.getPersistencia().put("abono",this.abono);
		this.estructuraTabla.getPersistencia().put("saldo", this.saldo);
		this.estructuraTabla.getPersistencia().put("recibo",this.recibo);
		this.estructuraTabla.getPersistencia().put("id_consulta", this.consulta);
		this.estructuraTabla.getPersistencia().put("tratamiento", this.tratamiento);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Usuario getPaciente() {
		return paciente;
	}
	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}
	public Usuario getProfesional() {
		return profesional;
	}
	public void setProfesional(Usuario profesional) {
		this.profesional = profesional;
	}
	public Integer getConsultorio() {
		return consultorio;
	}
	public void setConsultorio(Integer consultorio) {
		this.consultorio = consultorio;
	}
	public Integer getConsulta() {
		return consulta;
	}
	public void setConsulta(Integer consulta) {
		this.consulta = consulta;
	}
	public Integer getTratamiento() {
		return tratamiento;
	}
	public void setTratamiento(Integer tratamiento) {
		this.tratamiento = tratamiento;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getDetalleEvolucion() {
		return detalleEvolucion;
	}
	public void setDetalleEvolucion(String detalleEvolucion) {
		this.detalleEvolucion = detalleEvolucion;
	}
	
	
	public String getDetalleEvolucionAutomatica() {
		return detalleEvolucionAutomatica;
	}

	public void setDetalleEvolucionAutomatica(String detalleEvolucionAutomatica) {
		this.detalleEvolucionAutomatica = detalleEvolucionAutomatica;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public BigDecimal getAbono() {
		return abono;
	}
	public void setAbono(BigDecimal abono) {
		this.abono = abono;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	public String getRecibo() {
		return recibo;
	}
	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}
	
	//TEMPORALES
	
	public EstructuraTabla getEstructuraTabla() {
		return estructuraTabla;
	}
	public void setEstructuraTabla(EstructuraTabla estructuraTabla) {
		this.estructuraTabla = estructuraTabla;
	}
	public Boolean getModoEdicion() {
		return modoEdicion;
	}
	public void setModoEdicion(Boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}
	
	public Boolean modoVisualizacion(){
		return (this.estado.equals("T")||this.estado.equals("P"));
			
	}
	
	
	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}
	
	public String getTituloCita(){
		String titulo="";
		if(this.paciente!=null)
			titulo= this.paciente.getNombres();
		return titulo;
	}
	
	
	
	public Date getFechaActual() {
		return new Date();
	}

	public String getEstadoTexto(){		
		if(this.estado!=null){
			if(this.estado.equals("R"))
				return "Registrado";
			
			if(this.estado.equals("A"))
				return "En Atencion";
			
			if(this.estado.equals("P"))
				return "Para Pago";
			
			if(this.estado.equals("T"))
				return "Terminada";
							
		}
		
		return "";
	}



	
}
