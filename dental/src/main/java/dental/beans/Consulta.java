package dental.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import dental.generales.IConstantes;

public class Consulta implements Serializable {

	private static final long serialVersionUID = 6844578006904551820L;

	private Integer id;
	private String documento;
	private String motivoConsulta;
	private String historiaEnfermedadActual;
	private Date fechaConsulta;
	private String observaciones;
	private String observacionesOdontograma;
	private String observacionAutomatica;
	private String diagnostico;
	private Boolean consultaEscaneada;

	//Temporales
	private EstructuraTabla estructuraTabla;
	private Boolean cargueRapido;
	private Boolean modoEdicion;
	private Boolean modoConsulta;
	private Boolean modoEdicionOdontograma;

	/* Listados */
	private List<ConsultaHabitosOrales> habitosOrales;
	private List<ConsultaExamenClinico> examenesClinicos;
	private List<ConsultaAnamnesis> anamnesis;
	private List<ConsultaHigieneOral> higienesOrales;
	private List<Diente> dientesOdontograma;
	private List<DocumentoConsulta> documentos;

	/*
	 * Constantes
	 */
	public final static String MAX = "SELECT MAX(id) consecutivo  FROM consulta";

	public Consulta() {
		this.estructuraTabla = new EstructuraTabla();
		this.fechaConsulta = null;//new Date();
		this.observaciones = ".";
		this.motivoConsulta=".";
		this.historiaEnfermedadActual=".";
		this.observacionAutomatica="";
		this.cargueRapido = false;
		this.modoEdicion = false;
		this.modoConsulta=false;
		this.consultaEscaneada= false;
		this.modoEdicionOdontograma = false;
	}


	public void getCamposBD() {

		this.estructuraTabla.setTabla("consulta");
		this.estructuraTabla.getLlavePrimaria().put("id", this.id);

		this.estructuraTabla.getPersistencia().put("id", this.id);
		this.estructuraTabla.getPersistencia().put("documento", this.documento);
		this.estructuraTabla.getPersistencia().put("consulta_escaneada", this.consultaEscaneada);
		this.estructuraTabla.getPersistencia().put("motivo_consulta", this.motivoConsulta);
		this.estructuraTabla.getPersistencia().put("historia_enfermedad_actual", this.historiaEnfermedadActual);
		this.estructuraTabla.getPersistencia().put("fecha_consulta", this.fechaConsulta);
		this.estructuraTabla.getPersistencia().put("observaciones", this.observaciones);
		this.estructuraTabla.getPersistencia().put("observaciones_odontograma", this.observacionesOdontograma);
		this.estructuraTabla.getPersistencia().put("observacion_auto_odontograma", this.observacionAutomatica);
		
		this.estructuraTabla.getPersistencia().put("diagnostico", this.diagnostico);
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
	public String getMotivoConsulta() {
		return motivoConsulta;
	}

	public void setMotivoConsulta(String motivoConsulta) {
		this.motivoConsulta = motivoConsulta;
	}

	@Size(max = 500, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getHistoriaEnfermedadActual() {
		return historiaEnfermedadActual;
	}

	public void setHistoriaEnfermedadActual(String historiaEnfermedadActual) {
		this.historiaEnfermedadActual = historiaEnfermedadActual;
	}

	public Date getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	

	@Size(max = 500, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Size(max = 500, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getObservacionesOdontograma() {
		return observacionesOdontograma;
	}

	public void setObservacionesOdontograma(String observacionesOdontograma) {
		this.observacionesOdontograma = observacionesOdontograma;
	}

	public EstructuraTabla getEstructuraTabla() {
		return estructuraTabla;
	}

	public void setEstructuraTabla(EstructuraTabla estructuraTabla) {
		this.estructuraTabla = estructuraTabla;
	}
	
	

	public Boolean getConsultaEscaneada() {
		return consultaEscaneada;
	}

	public void setConsultaEscaneada(Boolean consultaEscaneada) {
		this.consultaEscaneada = consultaEscaneada;
	}

	// LISTADOS
	public List<ConsultaHabitosOrales> getHabitosOrales() {
		return habitosOrales;
	}

	public void setHabitosOrales(List<ConsultaHabitosOrales> habitosOrales) {
		this.habitosOrales = habitosOrales;
	}

	public List<Diente> getDientesOdontograma() {
		return dientesOdontograma;
	}

	public void setDientesOdontograma(List<Diente> dientesOdontograma) {
		this.dientesOdontograma = dientesOdontograma;
	}

	public List<ConsultaExamenClinico> getExamenesClinicos() {
		return examenesClinicos;
	}

	public void setExamenesClinicos(List<ConsultaExamenClinico> examenesClinicos) {
		this.examenesClinicos = examenesClinicos;
	}

	public List<ConsultaAnamnesis> getAnamnesis() {
		return anamnesis;
	}

	public void setAnamnesis(List<ConsultaAnamnesis> anamnesis) {
		this.anamnesis = anamnesis;
	}

	public List<ConsultaHigieneOral> getHigienesOrales() {
		return higienesOrales;
	}

	public void setHigienesOrales(List<ConsultaHigieneOral> higienesOrales) {
		this.higienesOrales = higienesOrales;
	}
	

	public List<DocumentoConsulta> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoConsulta> documentos) {
		this.documentos = documentos;
	}
	
	
	public String getObservacionAutomatica() {
		return observacionAutomatica;
	}


	public void setObservacionAutomatica(String observacionAutomatica) {
		this.observacionAutomatica = observacionAutomatica;
	}


	// TEMPORALES
	public Boolean getCargueRapido() {
		return cargueRapido;
	}

	public void setCargueRapido(Boolean cargueRapido) {
		this.cargueRapido = cargueRapido;
	}

	public Boolean getModoEdicion() {
		return modoEdicion;
	}

	public void setModoEdicion(Boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}

	public Boolean getModoEdicionOdontograma() {
		return modoEdicionOdontograma;
	}

	public void setModoEdicionOdontograma(Boolean modoEdicionOdontograma) {
		this.modoEdicionOdontograma = modoEdicionOdontograma;
	}

	public Boolean getModoConsulta() {
		return modoConsulta;
	}

	public void setModoConsulta(Boolean modoConsulta) {
		this.modoConsulta = modoConsulta;
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
		Consulta other = (Consulta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
