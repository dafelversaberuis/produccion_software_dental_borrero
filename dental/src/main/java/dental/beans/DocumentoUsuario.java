package dental.beans;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.primefaces.model.UploadedFile;

import dental.generales.IConstantes;

public class DocumentoUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8675485074461182941L;
	private Integer						id;
	private String            documento;
	private String						nombre;
	private byte[]						archivo;
	private String						contentType;
	private String						extensionArchivo;

	private UploadedFile			tFile;

	private EstructuraTabla		estructuraTabla;

	public DocumentoUsuario() {
		this.estructuraTabla = new EstructuraTabla();

	}
	

	public void getCamposBD() {

		this.estructuraTabla.setTabla("documentos_usuario");
		this.estructuraTabla.getLlavePrimaria().put("id", this.id);
		
		this.estructuraTabla.getPersistencia().put("documento", this.documento);
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

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
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
