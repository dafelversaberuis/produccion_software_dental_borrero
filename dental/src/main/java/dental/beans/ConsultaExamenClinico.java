package dental.beans;

import java.io.Serializable;

import javax.validation.constraints.Size;

import dental.generales.IConstantes;

public class ConsultaExamenClinico implements Serializable{

  private static final long serialVersionUID = 5668191592152857491L;

  private Boolean            valor;
  private Consulta			consulta;
  private ClaseExamenClinico examenClinico;
  
  private EstructuraTabla   estructuraTabla;

  public ConsultaExamenClinico(){
	  this.estructuraTabla = new EstructuraTabla();
	  this.estructuraTabla = new EstructuraTabla();
	  this.valor=true;
  }

  
  public ConsultaExamenClinico(ClaseExamenClinico examenClinico) {
	super();
	this.examenClinico = examenClinico;
	this.estructuraTabla = new EstructuraTabla();
	this.valor=true;
}


public void getCamposBD(){

    this.estructuraTabla.setTabla("consulta_examen_clinico");
    this.estructuraTabla.getLlavePrimaria().put("id_consulta", this.consulta!=null?this.consulta.getId():null);
    this.estructuraTabla.getLlavePrimaria().put("id_examen_clinico", this.examenClinico!=null?this.examenClinico.getId():null);
    
    
    this.estructuraTabla.getPersistencia().put("id_consulta", this.consulta!=null?this.consulta.getId():null);
    this.estructuraTabla.getPersistencia().put("id_examen_clinico", this.examenClinico!=null?this.examenClinico.getId():null);
    this.estructuraTabla.getPersistencia().put("valor", this.valor);
  }

   public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}
  

	
  public Boolean getValor() {
		return valor;
	}

	public void setValor(Boolean valor) {
		this.valor = valor;
	}

	public ClaseExamenClinico getExamenClinico() {
		return examenClinico;
	}

	public void setExamenClinico(ClaseExamenClinico examenClinico) {
		this.examenClinico = examenClinico;
	}

  public EstructuraTabla getEstructuraTabla(){
    return estructuraTabla;
  }

  public void setEstructuraTabla(EstructuraTabla estructuraTabla){
    this.estructuraTabla = estructuraTabla;
  }

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((consulta == null) ? 0 : consulta.hashCode());
	result = prime * result + ((examenClinico == null) ? 0 : examenClinico.hashCode());
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
	ConsultaExamenClinico other = (ConsultaExamenClinico) obj;
	if (consulta == null) {
		if (other.consulta != null)
			return false;
	} else if (!consulta.equals(other.consulta))
		return false;
	if (examenClinico == null) {
		if (other.examenClinico != null)
			return false;
	} else if (!examenClinico.equals(other.examenClinico))
		return false;
	return true;
}

  
 

}
