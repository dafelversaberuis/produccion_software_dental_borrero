package dental.beans;

import java.io.Serializable;

public class FirmaDispositivoMovil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2937465391212799591L;
	private String					documentoOdontologo;
	private String					firma;

	// Temporales
	private EstructuraTabla	estructuraTabla;

	public FirmaDispositivoMovil() {
		this.estructuraTabla = new EstructuraTabla();

	}

	public void getCamposBD() {
		this.estructuraTabla.setTabla("firma_dispositivo_movil");
		this.estructuraTabla.getLlavePrimaria().put("documento_odontologo", this.documentoOdontologo);
		this.estructuraTabla.getPersistencia().put("documento_odontologo",this.documentoOdontologo);
		this.estructuraTabla.getPersistencia().put("firma", this.firma);
	}

	public String getDocumentoOdontologo() {
		return documentoOdontologo;
	}

	public void setDocumentoOdontologo(String documentoOdontologo) {
		this.documentoOdontologo = documentoOdontologo;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public EstructuraTabla getEstructuraTabla() {
		return estructuraTabla;
	}

	public void setEstructuraTabla(EstructuraTabla estructuraTabla) {
		this.estructuraTabla = estructuraTabla;
	}

}
