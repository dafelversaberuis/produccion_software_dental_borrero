package dental.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Size;

import org.primefaces.model.UploadedFile;

import dental.generales.IConstantes;

public class Tratamiento implements Serializable {

	private static final long serialVersionUID = 578836371848978995L;/**
	 * 
	 */
	
	private Integer		id;
	private String		nombre;
	private Boolean		estado;
	private String		descripcion;
	private BigDecimal  valor;
	private byte[]		imagen;
	
	//Temporales
	private EstructuraTabla	estructuraTabla;
	private boolean         seleccionado;
	private String			nombreImagen;
	private UploadedFile	imagenFile;
	private Boolean			modoEdicion; 
	private Boolean 		usado;
	

	/*
	 * Constantes
	 */
	public final static String MAX = "SELECT MAX(id) consecutivo  FROM tratamiento";
	

	public Tratamiento() {
		this.estructuraTabla = new EstructuraTabla();
		this.estado=true;
		this.modoEdicion=false;
		this.usado=false;
	}
	

	public Tratamiento(Integer id) {
		super();
		this.id = id;
		this.estructuraTabla = new EstructuraTabla();	
		this.modoEdicion=false;
		this.usado=false;
	}

	public Tratamiento(Integer id,String nombre) {
		super();
		this.id = id;
		this.nombre=nombre;
		this.estructuraTabla = new EstructuraTabla();	
		this.modoEdicion=false;
		this.usado=false;
	}


	public void getCamposBD() {
		this.estructuraTabla.setTabla("tratamiento");
		this.estructuraTabla.getLlavePrimaria().put("id", this.id);
		
		this.estructuraTabla.getPersistencia().put("nombre", this.nombre);
		this.estructuraTabla.getPersistencia().put("descripcion", this.nombre);
		this.estructuraTabla.getPersistencia().put("estado", this.estado);
		this.estructuraTabla.getPersistencia().put("valor", this.valor);
		this.estructuraTabla.getPersistencia().put("estado", this.estado);
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
	
	public Boolean getEstado() {
		return estado;
	}


	public void setEstado(Boolean estado) {
		this.estado = estado;
	}


	public BigDecimal getValor() {
		return valor;
	}


	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


	public byte[] getImagen() {
		return imagen;
	}


	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	
	//TEMPORALES


	public String getNombreImagen() {
		return nombreImagen;
	}


	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}


	public UploadedFile getImagenFile() {
		return imagenFile;
	}


	public void setImagenFile(UploadedFile imagenFile) {
		this.imagenFile = imagenFile;
	}


	public EstructuraTabla getEstructuraTabla() {
		return estructuraTabla;
	}

	public void setEstructuraTabla(EstructuraTabla estructuraTabla) {
		this.estructuraTabla = estructuraTabla;
	}
	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	


	public Boolean getModoEdicion() {
		return modoEdicion;
	}


	public void setModoEdicion(Boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}


	public Boolean getUsado() {
		return usado;
	}


	public void setUsado(Boolean usado) {
		this.usado = usado;
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
		Tratamiento other = (Tratamiento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	

	
	
	
	

}
