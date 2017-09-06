package dental.beans;

import java.io.Serializable;

import javax.validation.constraints.Size;
import org.primefaces.model.UploadedFile;
import dental.generales.IConstantes;

public class Diente implements Serializable{

  private static final long serialVersionUID = -3656176349427793811L;
  private Integer           id;
  private Integer           cuadrante;
  private Integer           posicionOdontograma;
  private String            descripcionOclusal;
  private String            descripcionVestibular;
  private String            descripcionPalatino;
  private String            descripcionDistal;
  private String            descripcionEsmalte;
  private String            descripcionGeneral;
  private Integer      estadoGeneral;
  private Integer      estadoOclusal;
  private Integer      estadoVestibular;
  private Integer      estadoPalatino;
  private Integer      estadoDistal;
  private Integer      estadoEsmalte;
  private Consulta			consulta;
  private byte[]			imagenDiente; 
  
  
  //TEmporales
  private EstructuraTabla   estructuraTabla;
  private String			nombreFoto;
  private UploadedFile	    dienteFile;
  private Boolean		dienteCompleto;
  private String diagnosticosOdontograma;

  public final static String     MAX            = "SELECT MAX(id) consecutivo  FROM diente";

  public Diente(){
	  this.estructuraTabla = new EstructuraTabla();
	  this.diagnosticosOdontograma="";	  
  }
  

  public Diente(Integer cuadrante, Integer posicionOdontograma, Boolean dienteSano) {
	super();
	this.cuadrante = cuadrante;
	this.posicionOdontograma = posicionOdontograma;
	this.estructuraTabla = new EstructuraTabla();
	this.nombreFoto=dienteSano==true?"dientesano.png":"diente_ausente.png";
	this.dienteCompleto=true;
	this.diagnosticosOdontograma="";	  
}
  
  public Diente(Integer cuadrante, Integer posicionOdontograma, Boolean dienteSano,EstadoDiente eGeneral) {
		super();
		this.cuadrante = cuadrante;
		this.posicionOdontograma = posicionOdontograma;
		this.estructuraTabla = new EstructuraTabla();
		this.nombreFoto=dienteSano==true?"dientesano.png":"diente_ausente.png";
		this.dienteCompleto=true;
		this.estadoGeneral=eGeneral.getId();
		if(eGeneral!=null&&eGeneral.getZonaTotal()!=null)
			this.imagenDiente=eGeneral.getZonaTotal();
		
		
		this.diagnosticosOdontograma="";	  
	}
	  


public void getCamposBD(){

    this.estructuraTabla.setTabla("diente");
    this.estructuraTabla.getLlavePrimaria().put("id", this.id);

    this.estructuraTabla.getPersistencia().put("id", this.id);
    this.estructuraTabla.getPersistencia().put("cuadrante", this.cuadrante);  
    this.estructuraTabla.getPersistencia().put("posicion_odontograma", this.posicionOdontograma);
    this.estructuraTabla.getPersistencia().put("descripcion_vestibular", this.descripcionVestibular);
    this.estructuraTabla.getPersistencia().put("descripcion_palatino", this.descripcionPalatino);
    this.estructuraTabla.getPersistencia().put("descripcion_distal", this.descripcionDistal);
    this.estructuraTabla.getPersistencia().put("descripcion_esmalte", this.descripcionEsmalte);
    this.estructuraTabla.getPersistencia().put("descripcion_general", this.descripcionGeneral);
    this.estructuraTabla.getPersistencia().put("estado_general", this.estadoGeneral);
    this.estructuraTabla.getPersistencia().put("estado_oclusal", this.estadoOclusal);
    this.estructuraTabla.getPersistencia().put("estado_vestibular", this.estadoVestibular);
    this.estructuraTabla.getPersistencia().put("estado_palatino", this.estadoPalatino);
    this.estructuraTabla.getPersistencia().put("estado_distal", this.estadoDistal);
    this.estructuraTabla.getPersistencia().put("estado_esmalte", this.estadoEsmalte);
    this.estructuraTabla.getPersistencia().put("id_consulta", this.consulta!=null?this.consulta.getId():null);
    this.estructuraTabla.getPersistencia().put("imagen_diente", this.imagenDiente);
  }

 

  public Integer getId() {
	return id;
  }

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCuadrante() {
		return cuadrante;
	}
	
	public void setCuadrante(Integer cuadrante) {
		this.cuadrante = cuadrante;
	}
	
	public Integer getPosicionOdontograma() {
		return posicionOdontograma;
	}
	
	public void setPosicionOdontograma(Integer posicionOdontograma) {
		this.posicionOdontograma = posicionOdontograma;
	}
	
	@Size(max = 250, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getDescripcionOclusal() {
		return descripcionOclusal;
	}
	
	public void setDescripcionOclusal(String descripcionOclusal) {
		this.descripcionOclusal = descripcionOclusal;
	}
	
	@Size(max = 250, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getDescripcionVestibular() {
		return descripcionVestibular;
	}
	
	public void setDescripcionVestibular(String descripcionVestibular) {
		this.descripcionVestibular = descripcionVestibular;
	}
	
	@Size(max = 250, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getDescripcionPalatino() {
		return descripcionPalatino;
	}
	
	public void setDescripcionPalatino(String descripcionPalatino) {
		this.descripcionPalatino = descripcionPalatino;
	}
	
	@Size(max = 250, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getDescripcionDistal() {
		return descripcionDistal;
	}
	
	public void setDescripcionDistal(String descripcionDistal) {
		this.descripcionDistal = descripcionDistal;
	}
	
	@Size(max = 250, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getDescripcionEsmalte() {
		return descripcionEsmalte;
	}
	
	public void setDescripcionEsmalte(String descripcionEsmalte) {
		this.descripcionEsmalte = descripcionEsmalte;
	}
	
	@Size(max = 250, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getDescripcionGeneral() {
		return descripcionGeneral;
	}
	
	
	
	
	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public void setDescripcionGeneral(String descripcionGeneral) {
		this.descripcionGeneral = descripcionGeneral;
	}
	
	
	
	public Integer getEstadoGeneral() {
		return estadoGeneral;
	}


	public void setEstadoGeneral(Integer estadoGeneral) {
		this.estadoGeneral = estadoGeneral;
	}


	public Integer getEstadoOclusal() {
		return estadoOclusal;
	}


	public void setEstadoOclusal(Integer estadoOclusal) {
		this.estadoOclusal = estadoOclusal;
	}


	public Integer getEstadoVestibular() {
		return estadoVestibular;
	}


	public void setEstadoVestibular(Integer estadoVestibular) {
		this.estadoVestibular = estadoVestibular;
	}


	public Integer getEstadoPalatino() {
		return estadoPalatino;
	}


	public void setEstadoPalatino(Integer estadoPalatino) {
		this.estadoPalatino = estadoPalatino;
	}


	public Integer getEstadoDistal() {
		return estadoDistal;
	}


	public void setEstadoDistal(Integer estadoDistal) {
		this.estadoDistal = estadoDistal;
	}


	public Integer getEstadoEsmalte() {
		return estadoEsmalte;
	}


	public void setEstadoEsmalte(Integer estadoEsmalte) {
		this.estadoEsmalte = estadoEsmalte;
	}


	public byte[] getImagenDiente() {
		return imagenDiente;
	}
	
	public void setImagenDiente(byte[] imagenDiente) {
		this.imagenDiente = imagenDiente;
	}
	
	public EstructuraTabla getEstructuraTabla() {
		return estructuraTabla;
	}
	
	

	//TEMPORALES
	public void setEstructuraTabla(EstructuraTabla estructuraTabla) {
		this.estructuraTabla = estructuraTabla;
	}
	
	public String getNombreFoto() {
		return nombreFoto;
	}
	
	public String getDienteTexto() {
		String nombre=this.cuadrante!=null?this.cuadrante.toString():"";
		
		nombre+=this.posicionOdontograma!=null?this.posicionOdontograma.toString():"";
		
		return nombre;
	}
	
	public void setNombreFoto(String nombreFoto) {
		this.nombreFoto = nombreFoto;
	}
	
	public UploadedFile getDienteFile() {
		return dienteFile;
	}
	
	public void setDienteFile(UploadedFile dienteFile) {
		this.dienteFile = dienteFile;
	}
	

  
  public Boolean getDienteCompleto() {
		return dienteCompleto;
	}


	public void setDienteCompleto(Boolean dienteCompleto) {
		this.dienteCompleto = dienteCompleto;
	}


	public String getDiagnosticosOdontograma() {
		return diagnosticosOdontograma;
	}


	public void setDiagnosticosOdontograma(String diagnosticosOdontograma) {
		this.diagnosticosOdontograma = diagnosticosOdontograma;
	}


@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((cuadrante == null) ? 0 : cuadrante.hashCode());
	result = prime * result + ((posicionOdontograma == null) ? 0 : posicionOdontograma.hashCode());
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
	Diente other = (Diente) obj;
	if (cuadrante == null) {
		if (other.cuadrante != null)
			return false;
	} else if (!cuadrante.equals(other.cuadrante))
		return false;
	if (posicionOdontograma == null) {
		if (other.posicionOdontograma != null)
			return false;
	} else if (!posicionOdontograma.equals(other.posicionOdontograma))
		return false;
	return true;
}

  

}
