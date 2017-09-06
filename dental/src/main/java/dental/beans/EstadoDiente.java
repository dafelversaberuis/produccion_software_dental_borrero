package dental.beans;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.primefaces.model.UploadedFile;

import dental.generales.IConstantes;

public class EstadoDiente implements Serializable {

	private static final long serialVersionUID = -2931242457577356424L;
	
	
	private Integer		id;
	private String		nombre;
	private String		estado;
	private String		descripcion;
	private Boolean		dienteCompleto;
	private byte[]		zona1;
	private byte[]		zona2;
	private byte[]		zona3;
	private byte[]		zona4;
	private byte[]		zona5;
	private byte[]		zonaTotal;
	
	/*
	 * Constantes
	 */
	public final static String MAX = "SELECT MAX(id) consecutivo  FROM estado_diente";
	
	//Temporales
	private EstructuraTabla	estructuraTabla;
	private boolean         seleccionado;
	private String			zona1NombreFoto;
	private String			zona2NombreFoto;
	private String			zona3NombreFoto;
	private String			zona4NombreFoto;
	private String			zona5NombreFoto;
	private String			zonaTotalNombreFoto;
	private UploadedFile	zona1File;
	private UploadedFile	zona2File;	
	private UploadedFile	zona3File;
	private UploadedFile	zona4File;
	private UploadedFile	zona5File;
	private UploadedFile	zonaTotalFile;
	private String			diagnosticoOdontograma;
	private Boolean 		modoEdicion;
	

	public EstadoDiente() {
		this.estructuraTabla = new EstructuraTabla();
		this.dienteCompleto=true;
		this.diagnosticoOdontograma="";
		this.modoEdicion=false;
		
	}
	

	public EstadoDiente(Integer id) {
		super();
		this.id = id;
		this.estructuraTabla = new EstructuraTabla();
		this.diagnosticoOdontograma="";
		this.modoEdicion=false;
	}


	public void getCamposBD() {
		this.estructuraTabla.setTabla("estado_diente");
		this.estructuraTabla.getLlavePrimaria().put("id", this.id);
		
		this.estructuraTabla.getPersistencia().put("id", this.id);
		this.estructuraTabla.getPersistencia().put("nombre", this.nombre);
		this.estructuraTabla.getPersistencia().put("descripcion", this.nombre);
		this.estructuraTabla.getPersistencia().put("estado", this.estado);
		this.estructuraTabla.getPersistencia().put("diente_completo", this.dienteCompleto);
		this.estructuraTabla.getPersistencia().put("zona1", this.zona1);
		this.estructuraTabla.getPersistencia().put("zona2", this.zona2);
		this.estructuraTabla.getPersistencia().put("zona3", this.zona3);
		this.estructuraTabla.getPersistencia().put("zona4", this.zona4);
		this.estructuraTabla.getPersistencia().put("zona5", this.zona5);
		this.estructuraTabla.getPersistencia().put("zona_total", this.zonaTotal);
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

	public Boolean getDienteCompleto() {
		return dienteCompleto;
	}

	public void setDienteCompleto(Boolean dienteCompleto) {
		this.dienteCompleto = dienteCompleto;
	}

	public byte[] getZona1() {
		return zona1;
	}

	public void setZona1(byte[] zona1) {
		this.zona1 = zona1;
	}

	public byte[] getZona2() {
		return zona2;
	}

	public void setZona2(byte[] zona2) {
		this.zona2 = zona2;
	}

	public byte[] getZona3() {
		return zona3;
	}

	public void setZona3(byte[] zona3) {
		this.zona3 = zona3;
	}

	public byte[] getZona4() {
		return zona4;
	}

	public void setZona4(byte[] zona4) {
		this.zona4 = zona4;
	}

	public byte[] getZona5() {
		return zona5;
	}

	public void setZona5(byte[] zona5) {
		this.zona5 = zona5;
	}

	public byte[] getZonaTotal() {
		return zonaTotal;
	}

	public void setZonaTotal(byte[] zonaTotal) {
		this.zonaTotal = zonaTotal;
	}
	
	//TEMPORALES

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

	public String getZona1NombreFoto() {
		return zona1NombreFoto;
	}

	public void setZona1NombreFoto(String zona1NombreFoto) {
		this.zona1NombreFoto = zona1NombreFoto;
	}

	public String getZona2NombreFoto() {
		return zona2NombreFoto;
	}

	public void setZona2NombreFoto(String zona2NombreFoto) {
		this.zona2NombreFoto = zona2NombreFoto;
	}

	public String getZona3NombreFoto() {
		return zona3NombreFoto;
	}

	public void setZona3NombreFoto(String zona3NombreFoto) {
		this.zona3NombreFoto = zona3NombreFoto;
	}

	public String getZona4NombreFoto() {
		return zona4NombreFoto;
	}

	public void setZona4NombreFoto(String zona4NombreFoto) {
		this.zona4NombreFoto = zona4NombreFoto;
	}

	public String getZona5NombreFoto() {
		return zona5NombreFoto;
	}

	public void setZona5NombreFoto(String zona5NombreFoto) {
		this.zona5NombreFoto = zona5NombreFoto;
	}

	public String getZonaTotalNombreFoto() {
		return zonaTotalNombreFoto;
	}

	public void setZonaTotalNombreFoto(String zonaTotalNombreFoto) {
		this.zonaTotalNombreFoto = zonaTotalNombreFoto;
	}

	public UploadedFile getZona1File() {
		return zona1File;
	}

	public void setZona1File(UploadedFile zona1File) {
		this.zona1File = zona1File;
	}

	public UploadedFile getZona2File() {
		return zona2File;
	}

	public void setZona2File(UploadedFile zona2File) {
		this.zona2File = zona2File;
	}

	public UploadedFile getZona3File() {
		return zona3File;
	}

	public void setZona3File(UploadedFile zona3File) {
		this.zona3File = zona3File;
	}

	public UploadedFile getZona4File() {
		return zona4File;
	}

	public void setZona4File(UploadedFile zona4File) {
		this.zona4File = zona4File;
	}

	public UploadedFile getZona5File() {
		return zona5File;
	}

	public void setZona5File(UploadedFile zona5File) {
		this.zona5File = zona5File;
	}

	public UploadedFile getZonaTotalFile() {
		return zonaTotalFile;
	}

	public void setZonaTotalFile(UploadedFile zonaTotalFile) {
		this.zonaTotalFile = zonaTotalFile;
	}
	

	public String getDiagnosticoOdontograma() {
		return diagnosticoOdontograma;
	}


	public void setDiagnosticoOdontograma(String diagnosticoOdontograma) {
		this.diagnosticoOdontograma = diagnosticoOdontograma;
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
		EstadoDiente other = (EstadoDiente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	
	

}
