package dental.generales;

import org.apache.log4j.Logger;

public interface IConstantes {

	// manejo de logs
	public static final Logger log = Logger.getLogger("dental");

	// validaciones anotaciones beans

	public static String VALIDACION_MAXIMA_LONGITUD = "Máximo {max} caracteres";
	public static String VALIDACION_MINIMA_LONGITUD = "Mínimo {min} caracteres";
	public static String VALIDACION_MAXIMO_ENTERO = "Máximo {value}";
	public static String VALIDACION_MINIMO_ENTERO = "Mínimo {value}";
	public static String VALIDACION_VACIO = "No se permite sólo vacío";

	public static String VALIDACION_ACTIVO_INACTIVO = "Se debe especificar si está activo o inactivo";
	public static String VALIDACION_SI_NO = "Se debe especificar SI ó NO";
	public static String VALIDACION_MAXIMO_DECIMAL = "Máximo un número de {integer} digitos enteros y {fraction} decimales";
	public static String VALIDACION_EMAIL_INCORRECTO = "El formato del correo electrónico es incorrecto";
	public static String VALIDACION_MONEDA = "Sólo pesos colombianos ó dólares";

	public static String NOMBRE_SOFTWARE = "Dental software";

	public static Integer NUMERO_CARACTERES_TARJETA = 5;
	public static Integer NUMERO_DIGITOS_CLAVE_ALEATORIA = 6;
	public static String AFIRMACION = "S";
	public static String NEGACION = "N";
	public static String ACTIVO = "A";
	public static String INACTIVO = "I";
	public static String PESO_COLOMBIANO = "COP";
	public static String DOLAR = "US";
	public static String IDIOMA_ESPANOL = "ES";
	public static String TRANSACCION_APROBADA = "A";
	public static String TRANSACCION_PENDIENTE = "P";
	public static String TRANSACCION_EDITAR = "E";
	public static String TRANSACCION_CREAR = "C";

	public static Integer ID_DIENTE_SANO = 1;
	public static Integer ID_DIENTE_AUSENTE = 2;
	public static Integer ID_DIENTE_SIN_ERUPCIONAR = 3;
	
	public static Integer DECIMALES_REDONDEAR = 2;
	public static Integer VISTA_PERSONALIZACION = 2;
	public static Integer VISTA_lOGIN = 1;
	public static Integer MAXIMOS_REMITENTES_CORREO = 100;
	public static final String LOGO = "dental_software2.png";

	public static final String SMTP_HOST_NAME = "smtp.gmail.com";
	public static final int SMTP_HOST_PORT = 465;
	public static final String SMTP_AUTH_USER = "quimerapps@gmail.com";
	public static final String SMTP_AUTH_PWD = "mariacamila12";

	public static String ID_USUARIO_SESION = "dafelver";

	public static final String PAQUETE_MODULO_REPORTES = "/reportes/";
	public static final String PAQUETE_IMAGENES = "/imagenes/";

	// "/arbolitos/adopcionCliente/administrarCuenta.xhtml?faces-redirect=true";
	public static String PAGINA_NO_LOGUEO = "/index.xhtml?faces-redirect=true";
	public static String PAGINA_HOME = "/home.xhtml?faces-redirect=true";

}
