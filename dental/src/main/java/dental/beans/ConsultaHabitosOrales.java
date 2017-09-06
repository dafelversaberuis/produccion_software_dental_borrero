package dental.beans;

import java.io.Serializable;

public class ConsultaHabitosOrales implements Serializable{

  private static final long serialVersionUID = 5659933938700480306L;

  private boolean            valor;
  private Consulta			consulta;
  private ClaseHabitoOral	habitoOral;
  private EstructuraTabla   estructuraTabla;

  public ConsultaHabitosOrales(){
	  this.estructuraTabla = new EstructuraTabla();
	  this.valor=true;
  }
  

  public ConsultaHabitosOrales(ClaseHabitoOral habitoOral) {
	super();
	this.habitoOral = habitoOral;
	this.estructuraTabla = new EstructuraTabla();
	this.valor=true;
}
  


public void getCamposBD(){

    this.estructuraTabla.setTabla("consulta_habitos_orales");
    this.estructuraTabla.getLlavePrimaria().put("id_consulta", this.consulta!=null?this.consulta.getId():null);
    this.estructuraTabla.getLlavePrimaria().put("id_habito_oral", this.habitoOral!=null?this.habitoOral.getId():null);
    
    this.estructuraTabla.getPersistencia().put("id_consulta", this.consulta!=null?this.consulta.getId():null);
    this.estructuraTabla.getPersistencia().put("id_habito_oral", this.habitoOral!=null?this.habitoOral.getId():null);
    this.estructuraTabla.getPersistencia().put("valor", this.valor);
  }

 
  
  public ClaseHabitoOral getHabitoOral() {
	return habitoOral;
}


public void setHabitoOral(ClaseHabitoOral habitoOral) {
	this.habitoOral = habitoOral;
}


public boolean getValor(){
    return valor;
  }

  public void setValor(boolean valor){
    this.valor = valor;
  }

  public Consulta getConsulta() {
		return consulta;
	}

  public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
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
	result = prime * result + ((habitoOral == null) ? 0 : habitoOral.hashCode());
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
	ConsultaHabitosOrales other = (ConsultaHabitosOrales) obj;
	if (consulta == null) {
		if (other.consulta != null)
			return false;
	} else if (!consulta.equals(other.consulta))
		return false;
	if (habitoOral == null) {
		if (other.habitoOral != null)
			return false;
	} else if (!habitoOral.equals(other.habitoOral))
		return false;
	return true;
}

  

}
