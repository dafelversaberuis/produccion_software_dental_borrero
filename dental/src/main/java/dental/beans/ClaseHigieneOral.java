package dental.beans;

import java.io.Serializable;

import javax.validation.constraints.Size;

import dental.generales.IConstantes;

public class ClaseHigieneOral implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4670612113799479529L;

	private Integer						id;
	private String						nombre;
	private String						indicativoVigencia;

	private EstructuraTabla		estructuraTabla;
	private boolean            seleccionado;

	public ClaseHigieneOral() {
		this.estructuraTabla = new EstructuraTabla();
		this.seleccionado=true;
	}

	
	public ClaseHigieneOral(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.estructuraTabla = new EstructuraTabla();
	}


	public void getCamposBD() {

		this.estructuraTabla.setTabla("clases_higiene_oral");
		this.estructuraTabla.getLlavePrimaria().put("id", this.id);
		this.estructuraTabla.getPersistencia().put("nombre", this.nombre);
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
		ClaseHigieneOral other = (ClaseHigieneOral) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}
