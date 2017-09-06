package dental.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Size;
import org.primefaces.model.UploadedFile;
import dental.generales.IConstantes;

public class DetalleTratamiento implements Serializable{

  private static final long serialVersionUID = -3656176349427793811L;
  private TratamientoUsuario 	tratamientoUsuario;
  private Tratamiento			tratamiento;
  private String			    estado;
  private BigDecimal			    cantidad;
  private BigDecimal      		valorUnitario;
  private BigDecimal      		valorTotal;
  
  
  //TEmporales
  private EstructuraTabla   estructuraTabla;
  private Boolean			modoEdicion; 
  private Boolean			modoEliminar; 
  
  
  public DetalleTratamiento(){
	  this.estructuraTabla = new EstructuraTabla();
	  this.cantidad=new BigDecimal("1");
	  this.modoEdicion=false;
	  this.modoEliminar=false;
	  this.estado = "R";
	
	 
  }
  
  
  public DetalleTratamiento(Tratamiento tratamiento) {
	super();
	this.tratamiento = tratamiento;
	this.estructuraTabla = new EstructuraTabla();
	this.cantidad=new BigDecimal("1");
	this.modoEdicion=false;
	this.modoEliminar=false;
	setValorUnitario(tratamiento.getValor());	
	this.estado = "R";
}


public void getCamposBD(){

    this.estructuraTabla.setTabla("detalle_tratamiento");
    this.estructuraTabla.getLlavePrimaria().put("tratamiento_usuario", this.tratamientoUsuario!=null?this.tratamientoUsuario.getId():null);
    this.estructuraTabla.getLlavePrimaria().put("tratamiento", this.tratamiento!=null?this.tratamiento.getId():null);
    
    
    this.estructuraTabla.getPersistencia().put("tratamiento_usuario", this.tratamientoUsuario!=null?this.tratamientoUsuario.getId():null);
    this.estructuraTabla.getPersistencia().put("tratamiento", this.tratamiento!=null?this.tratamiento.getId():null);
    this.estructuraTabla.getPersistencia().put("valor_unitario",this.valorUnitario);
    this.estructuraTabla.getPersistencia().put("valor_total",this.valorTotal);
    this.estructuraTabla.getPersistencia().put("estado",this.estado);
    this.estructuraTabla.getPersistencia().put("cantidad",this.cantidad);
    
  }

	public TratamientoUsuario getTratamientoUsuario() {
		return tratamientoUsuario;
	}
	
	public void setTratamientoUsuario(TratamientoUsuario tratamientoUsuario) {
		this.tratamientoUsuario = tratamientoUsuario;
	}
	
	public Tratamiento getTratamiento() {
		return tratamiento;
	}
	
	public void setTratamiento(Tratamiento tratamiento) {
		this.tratamiento = tratamiento;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public BigDecimal getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	
	public void setValorUnitario(BigDecimal valor) {
		this.valorUnitario = valor;
	}
	
	
	
	public BigDecimal getValorTotal() {
		this.valorTotal= new BigDecimal("0");
		if(this.cantidad!=null && this.valorUnitario!=null)
			this.valorTotal=this.cantidad.multiply(this.valorUnitario);
		return valorTotal;
	}


	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
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

	
	public Boolean getModoEliminar() {
		return modoEliminar;
	}


	public void setModoEliminar(Boolean modoEliminar) {
		this.modoEliminar = modoEliminar;
	}

	public String getEstadoTexto(){		
		if(this.estado!=null){
			if(this.estado.equals("R"))
				return "Registrado";
			
			if(this.estado.equals("I"))
				return "Iniciado";
			
			if(this.estado.equals("T"))
				return "Terminado";
			
			if(this.estado.equals("S"))
				return "Suspendido";
							
		}
		
		return "";
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tratamiento == null) ? 0 : tratamiento.hashCode());
		result = prime * result + ((tratamientoUsuario == null) ? 0 : tratamientoUsuario.hashCode());
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
		DetalleTratamiento other = (DetalleTratamiento) obj;
		if (tratamiento == null) {
			if (other.tratamiento != null)
				return false;
		} else if (!tratamiento.equals(other.tratamiento))
			return false;
		if (tratamientoUsuario == null) {
			if (other.tratamientoUsuario != null)
				return false;
		} else if (!tratamientoUsuario.equals(other.tratamientoUsuario))
			return false;
		return true;
	}

   
  

	
	

}
