package dental.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.primefaces.model.UploadedFile;

import dental.generales.IConstantes;

public class Usuario implements Serializable {

	private static final long					serialVersionUID	= -4187017874243593946L;

	private String										idDocumento;
	private String										nombres;
	private String										apellidos;
	private String										telefono;
	private String										genero;
	private String										direccion;
	private String										estadoCivil;
	private String										ocupacion;
	private String										correoElectronico;
	private Date											fechaNacimiento;
	private String										acudiente;
	private Integer										numeroHistoriaClinica;
	private BigDecimal								valorInicioTratamiento;
	private BigDecimal								valorTotalTratamiento;
	private BigDecimal								valorAbonado;
	private String										estado;
	private byte[]										archivo;
	private String										clave;
	private String										tipoAdministracion;

	// Temporales
	private String										tTipoClave;
	private Integer										edad;
	private String										tFotoDecodificada;
	private String										tNombreFoto;
	private UploadedFile							tFile;
	private Boolean										modoEdicion;

	// Listados
	private List<Consulta>						consultas;
	private List<TratamientoUsuario>	tratamientosUsuario;
	private List<DocumentoUsuario>		documentos;

	private EstructuraTabla						estructuraTabla;

	public Usuario() {
		this.fechaNacimiento = new Date();
		this.estructuraTabla = new EstructuraTabla();
		this.estado = IConstantes.ACTIVO;
		this.estadoCivil = "S";
		this.genero = "M";
		this.tNombreFoto = "../imagenes/usuarios/usuario.jpg";
		this.modoEdicion = false;
	}

	public Usuario(String idDocumento, String nombres) {
		super();
		this.idDocumento = idDocumento;
		this.fechaNacimiento = new Date();
		this.nombres = nombres;
		this.estructuraTabla = new EstructuraTabla();
		this.estado = IConstantes.ACTIVO;
		this.estadoCivil = "S";
		this.genero = "M";
		this.tNombreFoto = "../imagenes/usuarios/usuario.jpg";
		this.modoEdicion = false;
	}

	public void getCamposBD() {

		this.estructuraTabla.setTabla("usuario");
		this.estructuraTabla.getLlavePrimaria().put("documento", this.idDocumento);
		this.estructuraTabla.getPersistencia().put("archivo", this.archivo);
		this.estructuraTabla.getPersistencia().put("documento", this.idDocumento);
		this.estructuraTabla.getPersistencia().put("nombres", this.nombres);
		this.estructuraTabla.getPersistencia().put("apellidos", this.apellidos);
		this.estructuraTabla.getPersistencia().put("telefono", this.telefono);
		this.estructuraTabla.getPersistencia().put("genero", this.genero);
		this.estructuraTabla.getPersistencia().put("direccion", this.direccion);
		this.estructuraTabla.getPersistencia().put("estado_civil", this.estadoCivil);
		this.estructuraTabla.getPersistencia().put("ocupacion", this.ocupacion);
		this.estructuraTabla.getPersistencia().put("correo_electronico", this.correoElectronico);
		this.estructuraTabla.getPersistencia().put("fecha_nacimiento", this.fechaNacimiento);
		this.estructuraTabla.getPersistencia().put("acudiente", this.acudiente);
		this.estructuraTabla.getPersistencia().put("tipo_administracion", this.tipoAdministracion);
		this.estructuraTabla.getPersistencia().put("clave", this.clave);
		this.estructuraTabla.getPersistencia().put("estado", this.estado);
	}

	@Size(max = 12, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	@Size(max = 100, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	@Size(max = 100, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@Size(max = 50, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Size(max = 10, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	@Size(max = 255, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Size(max = 12, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	@Size(max = 255, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	// @Pattern(regexp =
	// "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
	// message = IConstantes.VALIDACION_EMAIL_INCORRECTO)
	@SuppressWarnings("deprecation")
	@Size(max = 100, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	@Email(message = IConstantes.VALIDACION_EMAIL_INCORRECTO)
	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Size(max = 100, message = IConstantes.VALIDACION_MAXIMA_LONGITUD)
	public String getAcudiente() {
		return acudiente;
	}

	public void setAcudiente(String acudiente) {
		this.acudiente = acudiente;
	}

	public Integer getNumeroHistoriaClinica() {
		return numeroHistoriaClinica;
	}

	public void setNumeroHistoriaClinica(Integer numeroHistoriaClinica) {
		this.numeroHistoriaClinica = numeroHistoriaClinica;
	}

	@Digits(integer = 13, fraction = 2, message = IConstantes.VALIDACION_MAXIMO_DECIMAL)
	@DecimalMin("0.00")
	public BigDecimal getValorInicioTratamiento() {
		return valorInicioTratamiento;
	}

	public void setValorInicioTratamiento(BigDecimal valorInicioTratamiento) {
		this.valorInicioTratamiento = valorInicioTratamiento;
	}

	@Digits(integer = 13, fraction = 2, message = IConstantes.VALIDACION_MAXIMO_DECIMAL)
	@DecimalMin("0.00")
	public BigDecimal getValorTotalTratamiento() {
		return valorTotalTratamiento;
	}

	public void setValorTotalTratamiento(BigDecimal valorTotalTratamiento) {
		this.valorTotalTratamiento = valorTotalTratamiento;
	}

	@Digits(integer = 13, fraction = 2, message = IConstantes.VALIDACION_MAXIMO_DECIMAL)
	@DecimalMin("0.00")
	public BigDecimal getValorAbonado() {
		return valorAbonado;
	}

	public void setValorAbonado(BigDecimal valorAbonado) {
		this.valorAbonado = valorAbonado;
	}

	@Pattern(regexp = "[AI]", message = IConstantes.VALIDACION_ACTIVO_INACTIVO)
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getTipoAdministracion() {
		return tipoAdministracion;
	}

	public void setTipoAdministracion(String tipoAdministracion) {
		this.tipoAdministracion = tipoAdministracion;
	}

	// Temporales

	public String accionTexto() {

		return (this.modoEdicion != null && this.modoEdicion) ? "Modificar " : "Crear ";
	}

	public String gettFotoDecodificada() {
		return tFotoDecodificada;
	}

	public void settFotoDecodificada(String tFotoDecodificada) {
		this.tFotoDecodificada = tFotoDecodificada;
	}

	public String gettTipoClave() {
		return tTipoClave;
	}

	public void settTipoClave(String tTipoClave) {
		this.tTipoClave = tTipoClave;
	}

	public UploadedFile gettFile() {
		return tFile;
	}

	public void settFile(UploadedFile tFile) {
		this.tFile = tFile;
	}

	public String gettNombreFoto() {
		return tNombreFoto;
	}

	public void settNombreFoto(String tNombreFoto) {
		this.tNombreFoto = tNombreFoto;
	}

	public String getEdadTexto() {
		String edadTexto = "";
		int dias = 0;
		Date fechaActual = new Date();
		if (this.fechaNacimiento != null) {
			dias = (int) ((fechaActual.getTime() - this.fechaNacimiento.getTime()) / 86400000);
			if (dias <= 31 && dias > 0)
				edadTexto = dias + " dias";
			if (dias > 31 && dias <= 690)
				edadTexto = (int) Math.ceil(dias / 30) + " Meses";
			if (dias > 690)
				edadTexto = (int) Math.floor(dias / 365) + " Años";
		}

		return edadTexto;
	}

	public String getTipoUsuarioTexto() {
		if (this.tipoAdministracion != null) {
			if (this.tipoAdministracion.equals("A"))
				return "Administrador";

			if (this.tipoAdministracion.equals("O"))
				return "Odontólogo";

			if (this.tipoAdministracion.equals("X"))
				return "Auxiliar";

			if (this.tipoAdministracion.equals("S"))
				return "Secretaria";

			if (this.tipoAdministracion.equals("C"))
				return "Cliente";
		}

		return "Cliente";
	}

	public Integer getEdad() {
		Date fechaActual = new Date();
		int dias = (int) ((fechaActual.getTime() - this.fechaNacimiento.getTime()) / 86400000);
		edad = (int) Math.floor(dias / 365);// edad en años
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public EstructuraTabla getEstructuraTabla() {
		return estructuraTabla;
	}

	public void setEstructuraTabla(EstructuraTabla estructuraTabla) {
		this.estructuraTabla = estructuraTabla;
	}

	public boolean isModoEdicion() {
		return modoEdicion;
	}

	public void setModoEdicion(boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}

	// Listados
	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

	public List<TratamientoUsuario> getTratamientosUsuario() {
		return tratamientosUsuario;
	}

	public void setTratamientosUsuario(List<TratamientoUsuario> tratamientosUsuario) {
		this.tratamientosUsuario = tratamientosUsuario;
	}

	public List<DocumentoUsuario> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoUsuario> documentos) {
		this.documentos = documentos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDocumento == null) ? 0 : idDocumento.hashCode());
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
		Usuario other = (Usuario) obj;
		if (idDocumento == null) {
			if (other.idDocumento != null)
				return false;
		} else if (!idDocumento.equals(other.idDocumento))
			return false;
		return true;
	}

}
