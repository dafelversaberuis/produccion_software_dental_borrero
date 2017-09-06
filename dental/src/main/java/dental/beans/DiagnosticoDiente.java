package dental.beans;

import java.io.Serializable;

import javax.validation.constraints.Size;

import dental.generales.IConstantes;

public class DiagnosticoDiente implements Serializable{

  private static final long serialVersionUID = 5668191592152857491L;
  private Integer           idConsulta;
  private Integer           idDiente;
  private String            valor;

  private EstructuraTabla   estructuraTabla;

  public DiagnosticoDiente(){}

  public void getCamposBD(){

    this.estructuraTabla.setTabla("diagnostico_diente");
    this.estructuraTabla.getLlavePrimaria().put("id_consulta", this.idConsulta);
    this.estructuraTabla.getLlavePrimaria().put("id_diente", this.idDiente);

    this.estructuraTabla.getPersistencia().put("id_consulta", this.idConsulta);
    this.estructuraTabla.getPersistencia().put("id_diente", this.idDiente);
    this.estructuraTabla.getPersistencia().put("valor", this.valor);
  }

  public Integer getIdConsulta(){
    return idConsulta;
  }

  public void setIdConsulta(Integer idConsulta){
    this.idConsulta = idConsulta;
  }

  public Integer getIdDiente(){
    return idDiente;
  }

  public void setIdDiente(Integer idDiente){
    this.idDiente = idDiente;
  }

  @Size(max = 1, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
  public String getValor(){
    return valor;
  }

  public void setValor(String valor){
    this.valor = valor;
  }

  public EstructuraTabla getEstructuraTabla(){
    return estructuraTabla;
  }

  public void setEstructuraTabla(EstructuraTabla estructuraTabla){
    this.estructuraTabla = estructuraTabla;
  }

  @Override
  public int hashCode(){
    final int prime = 31;
    int result = 1;
    result = prime * result + ((idConsulta == null) ? 0 : idConsulta.hashCode());
    result = prime * result + ((idDiente == null) ? 0 : idDiente.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj){
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DiagnosticoDiente other = (DiagnosticoDiente) obj;
    if (idConsulta == null) {
      if (other.idConsulta != null)
        return false;
    } else if (!idConsulta.equals(other.idConsulta))
      return false;
    if (idDiente == null) {
      if (other.idDiente != null)
        return false;
    } else if (!idDiente.equals(other.idDiente))
      return false;
    return true;
  }

}
