package dental.beans;

import java.io.Serializable;

public class ConsultaHigieneOral implements Serializable{
  private static final long serialVersionUID = -8677457955386661110L;
  private boolean            valor;
  private Consulta			consulta;
  private ClaseHigieneOral	higieneOral;
  private EstructuraTabla   estructuraTabla;

  
  
  public ConsultaHigieneOral(ClaseHigieneOral higieneOral) {
	super();
	this.higieneOral = higieneOral;
	this.estructuraTabla = new EstructuraTabla();
	this.valor=true;
	
	
}



public void getCamposBD(){

    this.estructuraTabla.setTabla("consulta_higiene_oral");
    this.estructuraTabla.getLlavePrimaria().put("id_consulta", this.consulta!=null?this.consulta.getId():null);
    this.estructuraTabla.getLlavePrimaria().put("id_higiene_oral", this.higieneOral!=null?this.higieneOral.getId():null);
    
    this.estructuraTabla.getPersistencia().put("id_consulta", this.consulta!=null?this.consulta.getId():null);
    this.estructuraTabla.getPersistencia().put("id_higiene_oral", this.higieneOral!=null?this.higieneOral.getId():null);
    this.estructuraTabla.getPersistencia().put("valor", this.valor);
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
  
  
  public ClaseHigieneOral getHigieneOral() {
	return higieneOral;
}



public void setHigieneOral(ClaseHigieneOral higieneOral) {
	this.higieneOral = higieneOral;
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
	result = prime * result + ((higieneOral == null) ? 0 : higieneOral.hashCode());
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
	ConsultaHigieneOral other = (ConsultaHigieneOral) obj;
	if (consulta == null) {
		if (other.consulta != null)
			return false;
	} else if (!consulta.equals(other.consulta))
		return false;
	if (higieneOral == null) {
		if (other.higieneOral != null)
			return false;
	} else if (!higieneOral.equals(other.higieneOral))
		return false;
	return true;
}

  

}
