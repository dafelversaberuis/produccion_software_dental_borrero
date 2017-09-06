package dental.beans;

import java.io.Serializable;

import javax.validation.constraints.Size;

import dental.generales.IConstantes;

public class ConsultaAnamnesis implements Serializable{

  private static final long serialVersionUID = -3656176349427793811L; 
  private Boolean           valor;
  private EstructuraTabla   estructuraTabla;
  private ClaseAnamnesis	anamnesis;
  private Consulta			consulta;
  
  public ConsultaAnamnesis(){
	  this.estructuraTabla = new EstructuraTabla();
	  this.valor=true;
  }
  

  public ConsultaAnamnesis(ClaseAnamnesis anamnesis) {
	super();
	this.anamnesis = anamnesis; this.estructuraTabla = new EstructuraTabla();
	  this.valor=false;
  }


  public void getCamposBD(){

    this.estructuraTabla.setTabla("consulta_anamnesis");
    this.estructuraTabla.getLlavePrimaria().put("id_consulta", this.consulta!=null?this.consulta.getId():null);
    this.estructuraTabla.getLlavePrimaria().put("id_anamnesis", this.anamnesis!=null?this.anamnesis.getId():null);

    this.estructuraTabla.getPersistencia().put("id_consulta", this.consulta!=null?this.consulta.getId():null);
    this.estructuraTabla.getPersistencia().put("id_anamnesis", this.anamnesis!=null?this.anamnesis.getId():null);
    this.estructuraTabla.getPersistencia().put("valor", this.valor);
  }

	public Boolean getValor() {
		return valor;
	}
	
	public void setValor(Boolean valor) {
		this.valor = valor;
	}
	
	
	public ClaseAnamnesis getAnamnesis() {
		return anamnesis;
	}
	
	public void setAnamnesis(ClaseAnamnesis anamnesis) {
		this.anamnesis = anamnesis;
	}
	
	public Consulta getConsulta() {
		return consulta;
	}
	
	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}
	
	 public EstructuraTabla getEstructuraTabla() {
		return estructuraTabla;
	}
	
	public void setEstructuraTabla(EstructuraTabla estructuraTabla) {
		this.estructuraTabla = estructuraTabla;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anamnesis == null) ? 0 : anamnesis.hashCode());
		result = prime * result + ((consulta == null) ? 0 : consulta.hashCode());
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
		ConsultaAnamnesis other = (ConsultaAnamnesis) obj;
		if (anamnesis == null) {
			if (other.anamnesis != null)
				return false;
		} else if (!anamnesis.equals(other.anamnesis))
			return false;
		if (consulta == null) {
			if (other.consulta != null)
				return false;
		} else if (!consulta.equals(other.consulta))
			return false;
		return true;
	}
	

}
