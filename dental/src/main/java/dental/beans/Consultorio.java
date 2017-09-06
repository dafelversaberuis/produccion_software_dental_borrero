package dental.beans;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.primefaces.model.UploadedFile;

import dental.generales.IConstantes;

public class Consultorio implements Serializable {

	private static final long serialVersionUID = 578836371848978995L;
	/**
	 * 
	 */
	
	private Integer		id;
	private String		nombre;
	private String		estado;
	private String		descripcion;
		
	//Temporales
	private EstructuraTabla		estructuraTabla;
	private Boolean 		modoEdicion;
	

	public Consultorio() {
		this.estructuraTabla = new EstructuraTabla();
		this.modoEdicion=false;		
	}
	

	public void getCamposBD() {
		this.estructuraTabla.setTabla("consultorio");
		this.estructuraTabla.getLlavePrimaria().put("id", this.id);
		
		this.estructuraTabla.getPersistencia().put("id", this.id);
		this.estructuraTabla.getPersistencia().put("nombre", this.nombre);
		this.estructuraTabla.getPersistencia().put("descripcion", this.nombre);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Size(max = 250, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	public String accionTexto(){
		
		return (this.modoEdicion!=null && this.modoEdicion)?"Modificar ":"Crear ";
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
		Consultorio other = (Consultorio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	
	

}
