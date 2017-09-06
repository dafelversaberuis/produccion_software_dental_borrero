package dental.beans;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.primefaces.model.UploadedFile;

import dental.generales.IConstantes;

public class DocumentoConsulta implements Serializable {
	private static final long serialVersionUID = 8951179535061266749L;
	private Integer id;
	private Consulta consulta;
	private String nombre;
	private byte[] archivo;
	private String contentType;
	private String extensionArchivo;

	private UploadedFile tFile;

	private EstructuraTabla estructuraTabla;

	public DocumentoConsulta() {
		this.estructuraTabla = new EstructuraTabla();

	}

	public void getCamposBD() {

		this.estructuraTabla.setTabla("documentos_consulta");
		this.estructuraTabla.getLlavePrimaria().put("id", this.id);

		this.estructuraTabla.getPersistencia().put("id_consulta", this.consulta!=null?this.consulta.getId():null);
		this.estructuraTabla.getPersistencia().put("nombre", this.nombre);
		this.estructuraTabla.getPersistencia().put("archivo", this.archivo);
		this.estructuraTabla.getPersistencia().put("content_type", this.contentType);
		this.estructuraTabla.getPersistencia().put("extension_archivo", this.extensionArchivo);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	@Size(max = 250, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public EstructuraTabla getEstructuraTabla() {
		return estructuraTabla;
	}

	public void setEstructuraTabla(EstructuraTabla estructuraTabla) {
		this.estructuraTabla = estructuraTabla;
	}

	public UploadedFile gettFile() {
		return tFile;
	}

	public void settFile(UploadedFile tFile) {
		this.tFile = tFile;
	}

	@Size(max = 100, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Size(max = 10, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getExtensionArchivo() {
		return extensionArchivo;
	}

	public void setExtensionArchivo(String extensionArchivo) {
		this.extensionArchivo = extensionArchivo;
	}

}
