package dental.beans;

import java.io.Serializable;

import javax.validation.constraints.Size;

import dental.generales.IConstantes;

public class ClaseAnamnesis implements Serializable {
	private static final long	serialVersionUID	= -268263239093840479L;

	private Integer						id;
	private String						nombre;
	private String						indicativoVigencia;
	private String						alerta;

	private EstructuraTabla		estructuraTabla;
	private boolean						seleccionado;

	public ClaseAnamnesis() {
		this.estructuraTabla = new EstructuraTabla();
		this.seleccionado = true;
	}

	public ClaseAnamnesis(Integer id, String nombre, String alerta) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.alerta = alerta;
	}

	public void getCamposBD() {

		this.estructuraTabla.setTabla("clases_anamnesis");
		this.estructuraTabla.getLlavePrimaria().put("id", this.id);
		this.estructuraTabla.getPersistencia().put("nombre", this.nombre);
		this.estructuraTabla.getPersistencia().put("alerta", this.alerta);
		this.estructuraTabla.getPersistencia().put("indicativo_vigencia", this.indicativoVigencia);

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

	public String getIndicativoVigencia() {
		return indicativoVigencia;
	}

	public void setIndicativoVigencia(String indicativoVigencia) {
		this.indicativoVigencia = indicativoVigencia;
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

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
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
		ClaseAnamnesis other = (ClaseAnamnesis) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
