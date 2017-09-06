package dental.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import dental.generales.IConstantes;

public class Pagos implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 6367623007963827662L;
  private Integer           id;
  private String            documento;
  private Date              fecha;
  private BigDecimal        abono;
  private String            numeroRecibo;
  private BigDecimal        saldo;

  private EstructuraTabla   estructuraTabla;

  public Pagos(){}

  public void getCamposBD(){

    this.estructuraTabla.setTabla("pagos");
    this.estructuraTabla.getLlavePrimaria().put("id", this.id);

    this.estructuraTabla.getPersistencia().put("documento", this.documento);
    this.estructuraTabla.getPersistencia().put("fecha", this.fecha);
    this.estructuraTabla.getPersistencia().put("abono", this.abono);
    this.estructuraTabla.getPersistencia().put("numero_recibo", this.numeroRecibo);
    this.estructuraTabla.getPersistencia().put("saldo", this.saldo);
  }

  public Integer getId(){
    return id;
  }

  public void setId(Integer id){
    this.id = id;
  }

  @Size(max = 12, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
  public String getDocumento(){
    return documento;
  }

  public void setDocumento(String documento){
    this.documento = documento;
  }

  public Date getFecha(){
    return fecha;
  }

  public void setFecha(Date fecha){
    this.fecha = fecha;
  }

  @Digits(integer = 13, fraction = 2, message = IConstantes.VALIDACION_MAXIMO_DECIMAL)
  @DecimalMin("0.00")
  public BigDecimal getAbono(){
    return abono;
  }

  public void setAbono(BigDecimal abono){
    this.abono = abono;
  }

  public String getNumeroRecibo(){
    return numeroRecibo;
  }

  public void setNumeroRecibo(String numeroRecibo){
    this.numeroRecibo = numeroRecibo;
  }

  @Digits(integer = 13, fraction = 2, message = IConstantes.VALIDACION_MAXIMO_DECIMAL)
  @DecimalMin("0.00")
  public BigDecimal getSaldo(){
    return saldo;
  }

  public void setSaldo(BigDecimal saldo){
    this.saldo = saldo;
  }

  public EstructuraTabla getEstructuraTabla(){
    return estructuraTabla;
  }

  public void setEstructuraTabla(EstructuraTabla estructuraTabla){
    this.estructuraTabla = estructuraTabla;
  }

}
