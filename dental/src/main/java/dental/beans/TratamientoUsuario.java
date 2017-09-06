package dental.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Size;

import dental.generales.IConstantes;

public class TratamientoUsuario implements Serializable {

	private static final long serialVersionUID = 5145834234392031684L;
	private Integer id;
	private String documento;
	private Date fechaInicio;
	private String observaciones;
	private String estado;
	private BigDecimal valorTratamiento;
	private BigDecimal descuento;
	private BigDecimal saldo;
	private byte[] archivo;
	private String firma;
	private String textoConsentimiento;
	private Boolean contabilizar;

	/* Listados */
	private List<DetalleTratamiento> detallesTratamiento;
	private List<Cita> evoluciones;
	
	//Temporales
	private EstructuraTabla estructuraTabla;
	private Boolean modoEdicion;	
	private Boolean modoEdicionLimitada;	
	private List<DetalleTratamiento> detallesTratamientoCrear;

	/*
	 * Constantes
	 */
	public final static String MAX = "SELECT MAX(id) consecutivo  FROM tratamiento_usuario";

	public TratamientoUsuario() {
		this.estructuraTabla = new EstructuraTabla();
		this.fechaInicio = new Date();
		this.observaciones = "";
		this.modoEdicion = false;
		this.modoEdicionLimitada = false;
		this.contabilizar=true;
		this.descuento=new BigDecimal("0");
		this.valorTratamiento=new BigDecimal("0");
		this.saldo=new BigDecimal("0");
		this.estado = "R";
		setDetallesTratamiento(new ArrayList<DetalleTratamiento>());
	}
	

	public TratamientoUsuario(Integer id) {
		super();
		this.id = id;
		this.estructuraTabla = new EstructuraTabla();
		this.fechaInicio = new Date();
		this.observaciones = "";
		this.contabilizar=true;
		this.modoEdicion = false;
		this.modoEdicionLimitada = false;
		this.descuento=new BigDecimal("0");
		this.valorTratamiento=new BigDecimal("0");
		this.saldo=new BigDecimal("0");
		this.estado = "R";
		setDetallesTratamiento(new ArrayList<DetalleTratamiento>());
	}


	public void getCamposBD() {
		this.estructuraTabla.setTabla("tratamiento_usuario");
		this.estructuraTabla.getLlavePrimaria().put("id", this.id);
		
		this.estructuraTabla.getPersistencia().put("id", this.id);
		this.estructuraTabla.getPersistencia().put("documento", this.documento);
		this.estructuraTabla.getPersistencia().put("fecha_inicio", this.fechaInicio);
		this.estructuraTabla.getPersistencia().put("observaciones", this.observaciones);
		this.estructuraTabla.getPersistencia().put("valor_tratamiento", this.valorTratamiento);
		this.estructuraTabla.getPersistencia().put("estado", this.estado);
		this.estructuraTabla.getPersistencia().put("descuento",this.descuento);
		this.estructuraTabla.getPersistencia().put("contabilizar",this.contabilizar);
		this.estructuraTabla.getPersistencia().put("saldo", this.saldo);
		this.estructuraTabla.getPersistencia().put("firma",this.firma);
		this.estructuraTabla.getPersistencia().put("texto_consentimiento", this.textoConsentimiento);
		this.estructuraTabla.getPersistencia().put("archivo", this.archivo);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Size(max = 12, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	@Size(max = 500, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public EstructuraTabla getEstructuraTabla() {
		return estructuraTabla;
	}

	public void setEstructuraTabla(EstructuraTabla estructuraTabla) {
		this.estructuraTabla = estructuraTabla;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getEstado() {
		return estado;
	}
	public String getEstadoTexto(){		
		if(this.estado!=null){
			if(this.estado.equals("R"))
				return "Registrado/Cotizacion";
			
			if(this.estado.equals("I"))
				return "Iniciado";
			
			if(this.estado.equals("T"))
				return "Terminado";
			
			if(this.estado.equals("S"))
				return "Suspendido";
			
			if(this.estado.equals("C"))
				return "Cancelado";						
		}
		
		return "";
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getValorTratamiento() {
		return valorTratamiento;
	}

	public void setValorTratamiento(BigDecimal valorTratamiento) {
		this.valorTratamiento = valorTratamiento;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}
	
	

	public String getFirma() {
		return firma;
	}


	public void setFirma(String firma) {
		this.firma = firma;
	}


	public String getTextoConsentimiento() {
		return textoConsentimiento;
	}


	public void setTextoConsentimiento(String textoConsentimiento) {
		this.textoConsentimiento = textoConsentimiento;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	
	
	
	
	public Boolean getContabilizar() {
		return contabilizar;
	}


	public void setContabilizar(Boolean contabilizar) {
		this.contabilizar = contabilizar;
	}


	// LISTADOS
	public List<DetalleTratamiento> getDetallesTratamiento() {
		return detallesTratamiento;
	}

	public void setDetallesTratamiento(List<DetalleTratamiento> detallesTratamiento) {
		this.detallesTratamiento = detallesTratamiento;
	}
	

	
	public List<Cita> getEvoluciones() {
		return evoluciones;
	}


	public void setEvoluciones(List<Cita> evoluciones) {
		this.evoluciones = evoluciones;
	}


	
	
	// TEMPORALES

	public Boolean getModoEdicion() {
		return modoEdicion;
	}

	

	public void setModoEdicion(Boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}

	public String accionTexto() {

		return (this.modoEdicion != null && this.modoEdicion) ? "Modificar " : "Crear ";
	}
	
	
	
	public Boolean getModoEdicionLimitada() {
		return modoEdicionLimitada;
	}


	public void setModoEdicionLimitada(Boolean modoEdicionLimitada) {
		this.modoEdicionLimitada = modoEdicionLimitada;
	}


	public List<DetalleTratamiento> getDetallesTratamientoCrear() {
		this.detallesTratamientoCrear=new ArrayList<DetalleTratamiento>();
		if(this.detallesTratamiento!=null) //Traigo aquellos que no estan para eliminar
			this.detallesTratamientoCrear=this.detallesTratamiento.stream().filter(
				d ->!d.getModoEliminar()).collect(Collectors.toList());
		return detallesTratamientoCrear;
		
	}

	public void setDetallesTratamientoCrear(List<DetalleTratamiento> detallesTratamientoCrear) {
		this.detallesTratamientoCrear = detallesTratamientoCrear;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TratamientoUsuario other = (TratamientoUsuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
