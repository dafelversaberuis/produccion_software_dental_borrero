package dental.modulos.atencionOdontologica;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import org.jfree.util.Log;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import dental.Conexion;
import dental.beans.Cita;
import dental.beans.ClaseAnamnesis;
import dental.beans.ClaseExamenClinico;
import dental.beans.ClaseHabitoOral;
import dental.beans.ClaseHigieneOral;
import dental.beans.Consentimiento;
import dental.beans.Consulta;
import dental.beans.ConsultaAnamnesis;
import dental.beans.ConsultaExamenClinico;
import dental.beans.ConsultaHabitosOrales;
import dental.beans.ConsultaHigieneOral;
import dental.beans.Consultorio;
import dental.beans.DetalleTratamiento;
import dental.beans.Diente;
import dental.beans.DocumentoConsulta;
import dental.beans.DocumentoUsuario;
import dental.beans.EstadoDiente;
import dental.beans.FirmaDispositivoMovil;
import dental.beans.Tratamiento;
import dental.beans.TratamientoUsuario;
import dental.beans.Usuario;
import dental.generales.ConsultarFuncionesAPI;
import dental.generales.FirmaComoImagen;
import dental.generales.IConstantes;
import dental.modulos.IConsultasDAO;
import dental.modulos.personal.AdministrarSesionCliente;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean
@ViewScoped
public class AtencionOdontologica extends ConsultarFuncionesAPI implements Serializable {

	/**
	 * 
	 */
	private static final long					serialVersionUID					= 8809279673234444193L;

	// inyecta el bean de sesion parqa que traiga la persona logueada
	@ManagedProperty(value = "#{administrarSesionCliente}")
	private AdministrarSesionCliente	administrarSesionCliente;

	private boolean										indicativoDatosPorDefecto	= false;

	private Integer										vistaActual;
	private String										formaCambioFoto;

	private Usuario										usuario;
	private String										idPaciente;
	private Consulta									consulta;

	private ClaseHigieneOral					claseHigieneOral;
	private ClaseHigieneOral					claseHigieneOralTrnsccon;
	private List<ClaseHigieneOral>		clasesHigieneOral;

	private ClaseHabitoOral						claseHabitoOral;
	private ClaseHabitoOral						claseHabitoOralTrnsccon;
	private List<ClaseHabitoOral>			clasesHabitoOral;

	private ClaseAnamnesis						claseAnamnesis;
	private ClaseAnamnesis						claseAnamnesisTrnsccon;
	private List<ClaseAnamnesis>			clasesAnamnesis;

	private ClaseExamenClinico				claseExamenClinico;
	private ClaseExamenClinico				claseExamenClinicoTrnsccon;
	private List<ClaseExamenClinico>	clasesExamenClinico;

	private EstadoDiente							estadoDienteSeleccionado;
	private Diente										dienteSeleccionado;
	private List<EstadoDiente>				estadosDiente;
	private List<SelectItem>					itemsEstadosDiente;
	private List<SelectItem>					itemsEstadosDienteZona;

	private TratamientoUsuario				tratamientoUsuario;
	private List<Tratamiento>					tratamientosUsuario;
	private Tratamiento								tratamientoSeleccionado;
	private List<Tratamiento>					tratamientos;
	private List<Tratamiento>					tratamientosCrear;

	private List<Consentimiento>			consentimientos;
	private List<SelectItem>					itemsConsentimiento;
	private Consentimiento						consentimientoSeleccionado;
	private DocumentoUsuario					documentoUsuario;
	private DocumentoConsulta					documentoConsulta;

	// CITAS
	private ScheduleModel							calendario;
	private List<Cita>								citas;
	private List<Cita>								citasSinTratamiento;
	private Cita											citaSeleccionada;

	public String											textoConsulta;
	private List<SelectItem>					odontologos;
	private List<SelectItem>					consultorios;

	// EN caso que reciba parametros, busca la informacion del usuario
	public void buscarPaciente() {
		if (this.idPaciente != null) {
			this.usuario = new Usuario(this.idPaciente, "");
			consultarUsuario();
			this.idPaciente = null;
		}

	}

	/**
	 * Lee la firma almacenada
	 */
	public void leerFirma() {

		try {
			String documentoOdontologo = this.administrarSesionCliente.getPersonalSesion().getId();

			FirmaDispositivoMovil temp = IConsultasDAO.getFirma(documentoOdontologo);

			if (temp != null && temp.getDocumentoOdontologo() != null) {
				this.tratamientoUsuario.setFirma(temp.getFirma());
				this.mostrarMensajeGlobal("FIRMA LEIDA", "exito");

			} else {
				this.mostrarMensajeGlobal("NO EXISTE FIRMA REGISTRADA", "exito");
			}

		} catch (Exception e) {

			IConstantes.log.error(e, e);
		}

	}

	public void consultarUsuario() {
		try {

			Usuario usuarioTemporal = IAtencionOdontologicaDAO.getUsuario(this.usuario.getIdDocumento());
			if (usuarioTemporal != null && usuarioTemporal.getIdDocumento() != null) {
				this.usuario = usuarioTemporal;
				// Cargo las consultas del usuario
				this.usuario.setConsultas(IAtencionOdontologicaDAO.getConsultasUsuario(null, this.usuario));
				// Cargo los tratamientos
				this.usuario.setTratamientosUsuario(IAtencionOdontologicaDAO.getTratamientosUsuario(null, this.usuario));
				// Cargo los documentos adjuntos
				// this.usuario.setDocumentos(IAtencionOdontologicaDAO.getDocumentosUsuario(null,
				// this.usuario));
				this.mostrarMensajeGlobal("Paciente encontrado", "exito");
				if (this.usuario.getArchivo() != null && !this.usuario.getArchivo().equals("")) {
					// this.guardarImagenEnDisco(this.usuario.getIdDocumento(),
					// this.usuario.getArchivo(), "usuarios");

					this.usuario.settNombreFoto(convertirImagen(this.usuario.getArchivo()));
				} else
					this.usuario.settNombreFoto("../imagenes/usuarios/usuario.jpg");

				cargarConsulta(null);

			} else {
				// reseteo los datos si no encontró el usuario
				this.usuario = new Usuario(this.usuario.getIdDocumento(), "");
				this.usuario.setConsultas(null);
			}
			setVistaActual(0);

			// df
			// this.consulta = null;
			// getConsulta();
			// df

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Obtiene un método de autocompletar por nombres o documento
	 * 
	 * @param aTexto
	 * @return estudiantes
	 */
	public List<String> autocompletarUsuarios(String aTexto) {
		final String NOMBRE_METODO = " usarAutocompletarUsuarios() ";
		final List<String> strUsuarios = new ArrayList<String>();
		List<Usuario> usuariosBusqueda = new ArrayList<Usuario>();
		try {
			if (aTexto != null && !aTexto.equals("")) {
				usuariosBusqueda = IAtencionOdontologicaDAO.getUsuariosSuggestion(aTexto);
				for (Usuario u : usuariosBusqueda) {
					strUsuarios.add(u.getIdDocumento().concat("-").concat(u.getNombres() + " " + u.getApellidos() + " (" + u.getTipoUsuarioTexto() + ")"));
				}
			}
		} catch (Exception e) {
			IConstantes.log.error(NOMBRE_METODO + e, e);
		}
		return strUsuarios;
	}

	/**
	 * Asigna una foto para visualizarla
	 * 
	 * @param aActividadMantenimiento
	 */
	public void asignarFotoUsuario(boolean modal) {
		if (this.usuario.getArchivo() != null && !this.usuario.getArchivo().equals("")) {
			this.guardarImagenEnDisco(this.usuario.getIdDocumento(), this.usuario.getArchivo(), "usuarios");
			this.usuario.settNombreFoto("usuario" + this.usuario.getIdDocumento() + ".png");
		} else
			this.usuario.settNombreFoto("usuario.jpg");
		if (modal)
			this.abrirModal("panelVerFoto");
	}

	/**
	 * Recibir foto y asignara aobjeto
	 * 
	 * @param event
	 */
	public void recibirFoto(FileUploadEvent event) {

		try {
			this.usuario.settFile(event.getFile());
			this.usuario.setArchivo(event.getFile().getContents());
			// String id = (this.usuario.getIdDocumento() != null &&
			// this.usuario.getIdDocumento().equals(""))?
			// this.usuario.getIdDocumento() : "tmp";
			// Guardo temporalmente la imagen
			if (this.usuario.getArchivo() != null && !this.usuario.getArchivo().equals("")) {
				this.usuario.settNombreFoto(convertirImagen(this.usuario.getArchivo()));

				// this.usuario.settNombreFoto("usuario" + id + ".png");
				// this.guardarImagenEnDisco(id, this.usuario.getArchivo(), "usuarios");
			}
			this.mostrarMensajeGlobal("archivoRecibido", "advertencia");
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

	}

	public void webcamCapture(CaptureEvent captureEvent) {
		try {
			byte[] data = captureEvent.getData();
			// String path = this.getPath(IConstantes.PAQUETE_IMAGENES) + "/camara/";
			// InputStream in = new ByteArrayInputStream(data);
			// BufferedImage fotoBuffered = ImageIO.read(in);
			// String idImagem = salvarImagemFromBufferedImage(fotoBuffered);
			// Path fileLocation = Paths.get(path + idImagem + ".png");
			// byte[] data2 = Files.readAllBytes(fileLocation);

			this.usuario.setArchivo(data);
			this.usuario.settNombreFoto(convertirImagen(this.usuario.getArchivo()));
			this.usuario.settFile(null);
			this.mostrarMensajeGlobal("FOTO CAPTURADA", "advertencia");

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().validationFailed();
		}
	}

	private String salvarImagemFromBufferedImage(BufferedImage bf) {
		String id = gerarIdImagem();
		String path = this.getPath(IConstantes.PAQUETE_IMAGENES) + "/camara/";
		try {
			ImageIO.write(bf, "png", new File(path + id + ".png"));

			return id;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	private String gerarIdImagem() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyhhmmss");
		String id = simpleDateFormat.format(new Date());
		return id;
	}

	/**
	 * Limpia la foto cargada para cargar otra
	 */
	public void limpiarFotoCargada() {
		this.usuario.settFile(null);
		this.usuario.setArchivo(null);
		this.usuario.settNombreFoto(null);
	}

	/**
	 * Cancela la creación de una foto
	 */
	public void cancelarFoto() {
		try {
			this.cerrarModal("panelVerFoto");
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
	}

	/*
	 * Carga el modal para la subida de los documentos del usuario
	 */
	public void cargarDocumentoUsuario() {
		this.documentoUsuario = new DocumentoUsuario();
		// Cargo los documentos adjuntos
		try {
			if (this.usuario.getDocumentos() == null || this.usuario.getDocumentos().isEmpty())
				this.usuario.setDocumentos(IAtencionOdontologicaDAO.getDocumentosUsuario(null, this.usuario));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.abrirModal("panelVerDocumento");
	}

	/**
	 * Cancela la creación de una foto
	 */
	public void cancelarDocumentoUsuario() {
		try {
			this.cerrarModal("panelVerDocumento");
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
	}

	/**
	 * Guarda el registro de la atención odontológica
	 */
	public void guardarDocumentoUsuario() {
		Conexion conexion = new Conexion();
		try {
			conexion.setAutoCommitBD(false);

			// Se guarda el documento
			this.documentoUsuario.setDocumento(this.usuario.getIdDocumento());
			this.documentoUsuario.getCamposBD();
			conexion.insertarBD(this.documentoUsuario.getEstructuraTabla().getTabla(), this.documentoUsuario.getEstructuraTabla().getPersistencia());

			conexion.commitBD();
			this.documentoUsuario = new DocumentoUsuario();
			// Cargo los documentos adjuntos
			this.usuario.setDocumentos(IAtencionOdontologicaDAO.getDocumentosUsuario(null, this.usuario));

			this.mostrarMensajeGlobal("Se guardó el Documento", "exito");
		} catch (Exception e) {
			conexion.rollbackBD();
			this.mostrarMensajeGlobal("transaccionFallida", "error");
			IConstantes.log.error(e, e);
		} finally {
			conexion.cerrarConexion();
		}

	}

	/**
	 * Guarda el registro de la atención odontológica
	 */
	public void eliminarDocumentoUsuario(DocumentoUsuario d) {
		Conexion conexion = new Conexion();
		try {
			conexion.setAutoCommitBD(false);
			if (d != null) {
				d.getCamposBD();
				conexion.eliminarBD(d.getEstructuraTabla().getTabla(), d.getEstructuraTabla().getLlavePrimaria());

				conexion.commitBD();
			}
			// Cargo los documentos adjuntos
			this.usuario.setDocumentos(IAtencionOdontologicaDAO.getDocumentosUsuario(null, this.usuario));

			this.mostrarMensajeGlobal("Se eliminó el Documento", "exito");
		} catch (Exception e) {
			conexion.rollbackBD();
			this.mostrarMensajeGlobal("transaccionFallida", "error");
			IConstantes.log.error(e, e);
		} finally {
			conexion.cerrarConexion();
		}

	}

	/**
	 * Recibir el archivo y lo asigna al objeto
	 * 
	 * @param event
	 */
	public void recibirArchivoUsuario(FileUploadEvent event) {

		try {
			this.documentoUsuario.settFile(event.getFile());
			this.documentoUsuario.setArchivo(event.getFile().getContents());
			this.documentoUsuario.setContentType(event.getFile().getContentType());
			this.documentoUsuario.setExtensionArchivo(getTipoArchivo(event.getFile().getFileName()));
			this.mostrarMensajeGlobal("archivoRecibido", "advertencia");
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

	}

	/**
	 * Descarga el adjunto
	 */
	public void descargarDocumentoUsuario(DocumentoUsuario aDocumento) {
		try {
			DocumentoUsuario temp = IAtencionOdontologicaDAO.getAdjuntoDocumentoUsuario(aDocumento);
			if (temp != null) {
				descargarAdjunto(temp.getArchivo(), temp.getExtensionArchivo(), temp.getContentType());
			}

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

	}

	/**
	 * Limpia la foto cargada para cargar otra
	 */
	public void limpiarDocumento() {
		this.documentoUsuario.settFile(null);
		this.documentoUsuario.setArchivo(null);
	}

	/**
	 * Guarda el registro de la atención odontológica
	 */
	public void guardarAtencionOdontologica() {
		Conexion conexion = new Conexion();
		try {
			conexion.setAutoCommitBD(false);
			// Se guarda o actualiza el usuario
			this.usuario.getCamposBD();
			if (!this.usuario.isModoEdicion()) {
				conexion.insertarBD(this.usuario.getEstructuraTabla().getTabla(), this.usuario.getEstructuraTabla().getPersistencia());
				this.mostrarMensajeGlobal("Usuario creado", "exito");
				// Actualizo la consulta
				cargarConsulta(null);
			} else {
				conexion.actualizarBD(this.usuario.getEstructuraTabla().getTabla(), this.usuario.getEstructuraTabla().getPersistencia(), this.usuario.getEstructuraTabla().getLlavePrimaria(), null);
				this.mostrarMensajeGlobal("Usuario actualizado", "exito");
			}
			conexion.commitBD();
			this.usuario.setModoEdicion(true);

			this.abrirModal("panelUsuarioCompleto");
			this.setVistaActual(0);
		} catch (Exception e) {
			conexion.rollbackBD();
			this.mostrarMensajeGlobal("transaccionFallida", "error");
			IConstantes.log.error(e, e);
		} finally {
			conexion.cerrarConexion();
		}

	}

	public void cargarConsulta(Consulta c) {
		getClasesHigieneOral();
		getClasesAnamnesis();
		getClasesHabitoOral();
		getClasesExamenClinico();
		if (c != null) {
			// Busco en BD los elementos asociados
			try {
				this.consulta = c;
				this.consulta.setModoEdicion(true);
				this.consulta.setAnamnesis(IAtencionOdontologicaDAO.getAnamnesisConsulta(c));
				this.consulta.setHabitosOrales(IAtencionOdontologicaDAO.getHabitosConsulta(c));
				this.consulta.setHigienesOrales(IAtencionOdontologicaDAO.getHigieneConsulta(c));
				this.consulta.setExamenesClinicos(IAtencionOdontologicaDAO.getExamenesConsulta(c));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.vistaActual = 1;
		} else
			this.consulta = new Consulta();

		// Cargue de higiene oral
		if (this.consulta.getHigienesOrales() == null || this.consulta.getHigienesOrales().isEmpty()) {
			this.consulta.setHigienesOrales(new ArrayList<ConsultaHigieneOral>());
			for (ClaseHigieneOral ch : this.clasesHigieneOral) {
				this.consulta.getHigienesOrales().add(new ConsultaHigieneOral(ch));
			}
		}
		// Cargue de habitos orales
		if (this.consulta.getHabitosOrales() == null || this.consulta.getHabitosOrales().isEmpty()) {
			this.consulta.setHabitosOrales(new ArrayList<ConsultaHabitosOrales>());
			for (ClaseHabitoOral ch : this.clasesHabitoOral) {
				this.consulta.getHabitosOrales().add(new ConsultaHabitosOrales(ch));
			}
		}
		// Cargue de examenes clinicos
		if (this.consulta.getExamenesClinicos() == null || this.consulta.getExamenesClinicos().isEmpty()) {
			this.consulta.setExamenesClinicos(new ArrayList<ConsultaExamenClinico>());
			for (ClaseExamenClinico ce : this.clasesExamenClinico) {
				this.consulta.getExamenesClinicos().add(new ConsultaExamenClinico(ce));
			}
		}
		// Cargue de anamnesis
		if (this.consulta.getAnamnesis() == null || this.consulta.getAnamnesis().isEmpty()) {
			this.consulta.setAnamnesis(new ArrayList<ConsultaAnamnesis>());
			for (ClaseAnamnesis ca : this.clasesAnamnesis) {
				this.consulta.getAnamnesis().add(new ConsultaAnamnesis(ca));
			}
		}

		// Cargue Odontograma
		cargarOdontograma(this.consulta);

	}

	public String getCadenaAnamnesisAlerta() {
		String cadena = "";
		int cuenta = 0;
		if (this.consulta != null && this.consulta.getAnamnesis() != null && this.consulta.getAnamnesis().size() > 0) {
			for (ConsultaAnamnesis c : this.consulta.getAnamnesis()) {
				if (c.getAnamnesis() != null && c.getAnamnesis().getAlerta() != null && c.getAnamnesis().getAlerta().equals("S") && c.getValor()) {
					cuenta++;
					if (cuenta == 1) {
						cadena += "" + c.getAnamnesis().getNombre();
					} else {
						cadena += ", " + c.getAnamnesis().getNombre();
					}
				}
			}
		}

		return cadena;
	}

	public void guardarConsulta(Boolean guardadoParcial) {
		Conexion conexion = new Conexion();
		try {

			conexion.setAutoCommitBD(false);
			// Se crea la consulta
			this.consulta.setDocumento(this.usuario.getIdDocumento());

			if (!this.consulta.getModoEdicion()) {
				// df
				this.consulta.setFechaConsulta(new Date());
				//
				this.consulta.setId(conexion.getConsecutivo(Consulta.MAX).intValue());
				this.consulta.getCamposBD();
				conexion.insertarBD(this.consulta.getEstructuraTabla().getTabla(), this.consulta.getEstructuraTabla().getPersistencia());

				if (!guardadoParcial) {
					// Guardo los habitos orales
					for (ConsultaHabitosOrales ch : this.consulta.getHabitosOrales()) {
						ch.setConsulta(this.consulta);
						ch.getCamposBD();
						conexion.insertarBD(ch.getEstructuraTabla().getTabla(), ch.getEstructuraTabla().getPersistencia());
					}
					// Guardo la Higiene oral
					for (ConsultaHigieneOral ch : this.consulta.getHigienesOrales()) {
						ch.setConsulta(this.consulta);
						ch.getCamposBD();
						conexion.insertarBD(ch.getEstructuraTabla().getTabla(), ch.getEstructuraTabla().getPersistencia());
					}
					// Guardo los exámenes clínicos
					for (ConsultaExamenClinico ce : this.consulta.getExamenesClinicos()) {
						ce.setConsulta(this.consulta);
						ce.getCamposBD();
						conexion.insertarBD(ce.getEstructuraTabla().getTabla(), ce.getEstructuraTabla().getPersistencia());
					}
					// Guardo las anamnesis
					for (ConsultaAnamnesis ca : consulta.getAnamnesis()) {
						ca.setConsulta(this.consulta);
						ca.getCamposBD();
						conexion.insertarBD(ca.getEstructuraTabla().getTabla(), ca.getEstructuraTabla().getPersistencia());
					}
					this.abrirModal("panelConsultaCompleta");
					// df
					////////////// INFORMACION_DE_LAS_CONSULTAS_SE_ACTUALIZA/////////////////

					this.usuario.setConsultas(IAtencionOdontologicaDAO.getConsultasUsuario(null, this.usuario));
					this.usuario.setTratamientosUsuario(IAtencionOdontologicaDAO.getTratamientosUsuario(null, this.usuario));
					if (this.usuario.getArchivo() != null && !this.usuario.getArchivo().equals("")) {
						this.usuario.settNombreFoto(convertirImagen(this.usuario.getArchivo()));
					} else
						this.usuario.settNombreFoto("../imagenes/usuarios/usuario.jpg");
					// df
					//////////////////////////////////////////////////////

				}
			} else {
				this.consulta.getCamposBD();
				conexion.actualizarBD(this.consulta.getEstructuraTabla().getTabla(), this.consulta.getEstructuraTabla().getPersistencia(), this.consulta.getEstructuraTabla().getLlavePrimaria(), null);

				if (!guardadoParcial) {
					// Edito los habitos orales
					for (ConsultaHabitosOrales ch : consulta.getHabitosOrales()) {
						ch.getCamposBD();
						conexion.actualizarBD(ch.getEstructuraTabla().getTabla(), ch.getEstructuraTabla().getPersistencia(), ch.getEstructuraTabla().getLlavePrimaria(), null);
					}
					// Edito la Higiene oral
					for (ConsultaHigieneOral ch : this.consulta.getHigienesOrales()) {
						ch.setConsulta(this.consulta);
						ch.getCamposBD();
						conexion.actualizarBD(ch.getEstructuraTabla().getTabla(), ch.getEstructuraTabla().getPersistencia(), ch.getEstructuraTabla().getLlavePrimaria(), null);
					}
					// Edito los exámenes clínicos
					for (ConsultaExamenClinico ce : this.consulta.getExamenesClinicos()) {
						ce.setConsulta(this.consulta);
						ce.getCamposBD();
						conexion.actualizarBD(ce.getEstructuraTabla().getTabla(), ce.getEstructuraTabla().getPersistencia(), ce.getEstructuraTabla().getLlavePrimaria(), null);
					}
					// Edito las anamnesis
					for (ConsultaAnamnesis ca : consulta.getAnamnesis()) {
						ca.setConsulta(this.consulta);
						ca.getCamposBD();
						conexion.actualizarBD(ca.getEstructuraTabla().getTabla(), ca.getEstructuraTabla().getPersistencia(), ca.getEstructuraTabla().getLlavePrimaria(), null);
					}
					this.abrirModal("panelConsultaCompleta");
				}

			}
			conexion.commitBD();
			setVistaActual(1);
			this.consulta.setModoEdicion(true);

			if (!guardadoParcial)
				this.mostrarMensajeGlobal("Consulta guardada", "exito");

		} catch (Exception e) {
			conexion.rollbackBD();
			this.mostrarMensajeGlobal("transaccionFallida", "error");
			IConstantes.log.error(e, e);
		} finally {
			conexion.cerrarConexion();
		}

	}

	/**
	 * Carga el modal para la subida de los documentos del usuario
	 */
	public void cargarDocumentoConsulta(Consulta c) {
		this.documentoConsulta = new DocumentoConsulta();
		if (c != null) {
			this.consulta = c;
			// Cargo los documentos adjuntos
			try {
				if (this.consulta.getDocumentos() == null || this.consulta.getDocumentos().isEmpty())
					this.consulta.setDocumentos(IAtencionOdontologicaDAO.getDocumentosConsulta(this.consulta));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.abrirModal("panelVerDocumentoConsulta");
		}
	}

	/**
	 * Cancela la creación de una foto
	 */
	public void cancelarDocumentoConsulta() {
		try {
			this.cerrarModal("panelVerDocumentoConsulta");
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
	}

	/**
	 * Guarda el registro de la atención odontológica
	 */
	public void guardarDocumentoConsulta() {
		guardarConsulta(true);
		Conexion conexion = new Conexion();
		try {
			conexion.setAutoCommitBD(false);

			// Se guarda el documento
			this.documentoConsulta.setConsulta(this.consulta);
			;
			this.documentoConsulta.getCamposBD();
			conexion.insertarBD(this.documentoConsulta.getEstructuraTabla().getTabla(), this.documentoConsulta.getEstructuraTabla().getPersistencia());

			conexion.commitBD();
			this.documentoConsulta = new DocumentoConsulta();
			// Cargo los documentos adjuntos
			this.consulta.setDocumentos(IAtencionOdontologicaDAO.getDocumentosConsulta(this.consulta));

			this.mostrarMensajeGlobal("Se guardó el Documento", "exito");
		} catch (Exception e) {
			conexion.rollbackBD();
			this.mostrarMensajeGlobal("transaccionFallida", "error");
			IConstantes.log.error(e, e);
		} finally {
			conexion.cerrarConexion();
		}

	}

	/**
	 * Guarda el registro de la atención odontológica
	 */
	public void eliminarDocumentoConsulta(DocumentoConsulta d) {
		Conexion conexion = new Conexion();
		try {
			conexion.setAutoCommitBD(false);
			if (d != null) {
				d.getCamposBD();
				conexion.eliminarBD(d.getEstructuraTabla().getTabla(), d.getEstructuraTabla().getLlavePrimaria());

				conexion.commitBD();
			}
			// Cargo los documentos adjuntos
			this.consulta.setDocumentos(IAtencionOdontologicaDAO.getDocumentosConsulta(this.consulta));

			this.mostrarMensajeGlobal("Se eliminó el Documento", "exito");
		} catch (Exception e) {
			conexion.rollbackBD();
			this.mostrarMensajeGlobal("transaccionFallida", "error");
			IConstantes.log.error(e, e);
		} finally {
			conexion.cerrarConexion();
		}

	}

	/**
	 * Recibir el archivo y lo asigna al objeto
	 * 
	 * @param event
	 */
	public void recibirArchivoConsulta(FileUploadEvent event) {

		try {
			this.documentoConsulta.settFile(event.getFile());
			this.documentoConsulta.setArchivo(event.getFile().getContents());
			this.documentoConsulta.setContentType(event.getFile().getContentType());
			this.documentoConsulta.setExtensionArchivo(getTipoArchivo(event.getFile().getFileName()));
			this.mostrarMensajeGlobal("archivoRecibido", "advertencia");
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

	}

	/**
	 * Descarga el adjunto
	 */
	public void descargarDocumentoConsulta(DocumentoConsulta aDocumento) {
		try {
			DocumentoConsulta temp = IAtencionOdontologicaDAO.getAdjuntoDocumentoConsulta(aDocumento);
			if (temp != null) {
				descargarAdjunto(temp.getArchivo(), temp.getExtensionArchivo(), temp.getContentType());
			}

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

	}

	/**
	 * Limpia la foto cargada para cargar otra
	 */
	public void limpiarDocumentoConsulta() {
		this.documentoConsulta.settFile(null);
		this.documentoConsulta.setArchivo(null);
	}

	// METODOS ODONTOGRAMA
	public void copiarOdontograma(Consulta c) {
		getConsulta();
		if (c != null) {
			try {
				if (c.getId() != null) {
					c.setDientesOdontograma(IAtencionOdontologicaDAO.getOdontogramaConsulta(c));

					this.consulta.setDientesOdontograma(new ArrayList<Diente>());
					for (Diente d : c.getDientesOdontograma()) {
						d.setConsulta(this.consulta);
						d.setId(null);
						this.consulta.getDientesOdontograma().add(d);
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void cargarOdontograma(Consulta c) {
		Boolean copiarOdontograma = false;
		getEstadosDiente();
		if (c != null) {
			try {
				if (c.getId() != null) {
					this.consulta.setDientesOdontograma(IAtencionOdontologicaDAO.getOdontogramaConsulta(c));
					this.consulta.setModoEdicionOdontograma(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (consulta.getDientesOdontograma() == null || consulta.getDientesOdontograma().isEmpty()) {
				consulta.setModoEdicionOdontograma(false);
				// Si hay consultas previas, copio el odontograma de la última consulta
				// (siempre y cuando no sea consulta escaneada)
				if (this.usuario.getConsultas() != null && !this.usuario.getConsultas().isEmpty()) {
					for (Consulta consultaCopia : this.usuario.getConsultas())
						if (!consultaCopia.getConsultaEscaneada()) {
							copiarOdontograma(consultaCopia);
							// sigo buscando hasta que encuentre una consulta válida
							if (this.consulta.getDientesOdontograma() != null && !this.consulta.getDientesOdontograma().isEmpty()) {
								copiarOdontograma = true;
								break;
							}
						}
				}

				// si no se copio el odontograma, cargo uno por defecto
				if (!copiarOdontograma) {
					// Copio los estados por defecto SANO/AUSENTE /SIN ERUPCIONAR
					EstadoDiente dienteSano = new EstadoDiente(IConstantes.ID_DIENTE_SANO);
					Integer index = this.estadosDiente.indexOf(dienteSano);
					if (index >= 0)
						dienteSano = this.estadosDiente.get(index);
					else
						dienteSano = null;

					EstadoDiente dienteAusente = new EstadoDiente(IConstantes.ID_DIENTE_AUSENTE);
					index = this.estadosDiente.indexOf(dienteAusente);
					if (index >= 0)
						dienteAusente = this.estadosDiente.get(index);
					else
						dienteAusente = null;
					
					EstadoDiente dienteSinErupcionar = new EstadoDiente(IConstantes.ID_DIENTE_SIN_ERUPCIONAR);
					index = this.estadosDiente.indexOf(dienteSinErupcionar);
					if (index >= 0)
						dienteSinErupcionar = this.estadosDiente.get(index);
					else
						dienteSinErupcionar = null;
					

					this.consulta.setDientesOdontograma(new ArrayList<Diente>());
					Boolean dientesLeche = true;
					Boolean dientesHueso = true;
					// Dientes de leche inexistentes para niños mayores de 12 años
					if (this.usuario.getEdad() > 12) {
						dientesLeche = false;
						dientesHueso = true;
					}

					// Dientes de hueso inexistentes para niños menores de 6 años
					if (this.usuario.getEdad() < 6) {
						dientesLeche = true;
						dientesHueso = false;
					}
					// ADULTOS
					// Cuadrante 1 y 4
					for (Integer i = 1; i < 9; i++)
						if (dientesHueso) {
							this.consulta.getDientesOdontograma().add(new Diente(1, i, dientesHueso, dienteSano));
							this.consulta.getDientesOdontograma().add(new Diente(4, i, dientesHueso, dienteSano));
						} else {
							this.consulta.getDientesOdontograma().add(new Diente(1, i, dientesHueso, dienteSinErupcionar));
							this.consulta.getDientesOdontograma().add(new Diente(4, i, dientesHueso, dienteSinErupcionar));
						}

					// Cuadrante 2 y 3
					for (Integer i = 1; i < 9; i++)
						if (dientesHueso) {
							this.consulta.getDientesOdontograma().add(new Diente(2, i, dientesHueso, dienteSano));
							this.consulta.getDientesOdontograma().add(new Diente(3, i, dientesHueso, dienteSano));
						} else {
							this.consulta.getDientesOdontograma().add(new Diente(2, i, dientesHueso, dienteSinErupcionar));
							this.consulta.getDientesOdontograma().add(new Diente(3, i, dientesHueso, dienteSinErupcionar));
						}

					// NIÑOS Cuadrante 5,6,7 y 8
					for (Integer i = 1; i <= 5; i++)
						if (dientesLeche) {
							this.consulta.getDientesOdontograma().add(new Diente(5, i, dientesLeche, dienteSano));
							this.consulta.getDientesOdontograma().add(new Diente(6, i, dientesLeche, dienteSano));
							this.consulta.getDientesOdontograma().add(new Diente(7, i, dientesLeche, dienteSano));
							this.consulta.getDientesOdontograma().add(new Diente(8, i, dientesLeche, dienteSano));
						} else {
							this.consulta.getDientesOdontograma().add(new Diente(5, i, dientesLeche, dienteAusente));
							this.consulta.getDientesOdontograma().add(new Diente(6, i, dientesLeche, dienteAusente));
							this.consulta.getDientesOdontograma().add(new Diente(7, i, dientesLeche, dienteAusente));
							this.consulta.getDientesOdontograma().add(new Diente(8, i, dientesLeche, dienteAusente));
						}
				}

			}
			cargarObservacionesOdontograma();
		}
	}

	public void cargarObservacionesOdontograma() {
		String observaciones = "";
		String diagnostico;
		getEstadosDiente();
		for (EstadoDiente e : this.estadosDiente) {
			diagnostico = "";
			if (e.getDienteCompleto())
				for (Diente d : this.consulta.getDientesOdontograma()) {
					if (d.getEstadoGeneral() != null && e.getId().equals(d.getEstadoGeneral())) {
						diagnostico += !diagnostico.equals("") ? ", " : "";
						diagnostico += d.getDienteTexto();
					}
				}
			else {
				for (Diente d : this.consulta.getDientesOdontograma()) {
					String textoDiente = "";
					if (d.getEstadoOclusal() != null && e.getId().equals(d.getEstadoOclusal()))
						textoDiente += d.getDienteTexto() + " Oclusal";
					if (d.getEstadoDistal() != null && e.equals(d.getEstadoDistal())) {
						// Verifico si se ha guardado algo agrego el separador
						// de comas
						textoDiente += !textoDiente.equals("") ? " ," : d.getDienteTexto();
						textoDiente += "Distal ";
					}
					if (d.getEstadoEsmalte() != null && e.getId().equals(d.getEstadoEsmalte())) {
						textoDiente += !textoDiente.equals("") ? " ," : d.getDienteTexto();
						textoDiente += "Esmalte ";
					}
					if (d.getEstadoPalatino() != null && e.getId().equals(d.getEstadoPalatino())) {
						textoDiente += !textoDiente.equals("") ? " ," : d.getDienteTexto();
						textoDiente += " Palatino ";
					}
					if (d.getEstadoVestibular() != null && e.getId().equals(d.getEstadoVestibular())) {
						textoDiente += !textoDiente.equals("") ? " ," : d.getDienteTexto();
						textoDiente += " Vestibular, ";
					}

					if (!textoDiente.equals(""))
						diagnostico += "(" + textoDiente + ")";
				}
			}

			if (!diagnostico.equals(""))
				observaciones += "<b>" + e.getNombre() + "</b>: " + diagnostico + "<br/>";
		}
		this.consulta.setObservacionAutomatica(observaciones);
	}

	public void guardarOdontograma() {
		Conexion conexion = new Conexion();
		try {
			conexion.setAutoCommitBD(false);
			if (!this.consulta.getModoEdicionOdontograma()) {
				Integer id = conexion.getConsecutivo(Diente.MAX).intValue();
				for (Diente d : this.consulta.getDientesOdontograma()) {
					d.setConsulta(this.consulta);
					d.setId(id);
					id++;
					d.getCamposBD();
					conexion.insertarBD(d.getEstructuraTabla().getTabla(), d.getEstructuraTabla().getPersistencia());
				}
			} else {
				// Edito los dientes
				for (Diente d : this.consulta.getDientesOdontograma()) {
					d.getCamposBD();
					conexion.actualizarBD(d.getEstructuraTabla().getTabla(), d.getEstructuraTabla().getPersistencia(), d.getEstructuraTabla().getLlavePrimaria(), null);
				}
			}
			conexion.commitBD();
			this.consulta.setModoEdicionOdontograma(true);
			guardarConsulta(true);
			setVistaActual(2);
			this.abrirModal("panelOdontogramaCompleto");
			this.mostrarMensajeGlobal("Odontograma guardado", "exito");

		} catch (Exception e) {
			conexion.rollbackBD();
			this.mostrarMensajeGlobal("transaccionFallida", "error");
			IConstantes.log.error(e, e);
		} finally {
			conexion.cerrarConexion();
		}

	}

	public String getRandomSerialFoto() {
		int i = (int) (Math.random() * 10000000);
		return "" + i;
	}

	public String cargarImagenOdontograma2(Integer cuadrante, Integer posicion) {
		String path = this.getPath(IConstantes.PAQUETE_IMAGENES) + "/dientes/";
		String imagen = "../imagenes/dientes/dientesano.png";
		Integer anchoIcono = 55;
		if (this.consulta != null) {

			Diente diente = new Diente(cuadrante, posicion, true);
			if (this.consulta.getDientesOdontograma() != null) {
				int index = this.consulta.getDientesOdontograma().indexOf(diente);
				if (index >= 0) {// encontró el diente
					diente = this.consulta.getDientesOdontograma().get(index);
					if (diente.getImagenDiente() != null) {

						diente.setNombreFoto("diente" + diente.getDienteTexto() + (Math.random() * 100000) + ".png");
						// guardo la imagen en disco y se la asigno al diente
						try {
							ImageIO.write(ImageIO.read(new ByteArrayInputStream(diente.getImagenDiente())), "png", new File(path + diente.getNombreFoto()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					if (diente.getNombreFoto() != null)
						imagen = "../imagenes/dientes/" + diente.getNombreFoto();
				}

			}
			// getEstadoDienteSeleccionado();

		}
		imagen = "background-image: url('" + imagen + "');" + "background-origin: content-box;" + " background-size: cover;" + "float:right;" + " background-repeat:no-repeat;" + " width:" + anchoIcono + "px; height:" + anchoIcono + "px;" + " border:0; " + "background-color:transparent";

		return imagen;
	}

	public String cargarImagenOdontograma(Integer cuadrante, Integer posicion) {
		String path = this.getPath(IConstantes.PAQUETE_IMAGENES) + "/dientes/";
		String imagen = "../imagenes/dientes/dientesano.png";
		Integer anchoIcono = 55;
		String base64DataString = "";
		if (this.consulta != null) {

			Diente diente = new Diente(cuadrante, posicion, true);
			if (this.consulta.getDientesOdontograma() != null) {
				int index = this.consulta.getDientesOdontograma().indexOf(diente);
				if (index >= 0) {// encontró el diente
					diente = this.consulta.getDientesOdontograma().get(index);
					if (diente.getImagenDiente() != null) {

						diente.setNombreFoto("diente" + diente.getDienteTexto() + (Math.random() * 100000) + ".png");
						// guardo la imagen en disco y se la asigno al diente
						try {
							base64DataString = Base64.getEncoder().encodeToString(diente.getImagenDiente());
							ImageIO.write(ImageIO.read(new ByteArrayInputStream(diente.getImagenDiente())), "png", new File(path + diente.getNombreFoto()));
						} catch (IOException e) {
							e.printStackTrace();
						}

					}

					/*
					 * if (diente.getNombreFoto() != null) imagen = "../imagenes/dientes/"
					 * + diente.getNombreFoto();
					 */
				}

			}
			// getEstadoDienteSeleccionado();

		}
		imagen = "background-image: url('data:image/png;base64," + base64DataString + "');" + "background-origin: content-box;" + " background-size: cover;" + "float:right;" + " background-repeat:no-repeat;" + " width:" + anchoIcono + "px; height:" + anchoIcono + "px;" + " border:0; " + "background-color:transparent";

		return imagen;
	}

	/*
	 * public void buscarEstadoDiente(char opcion, Integer idEstado) { try { int
	 * index; // Cargo el estado actual index = this.estadosDiente.indexOf(new
	 * EstadoDiente(idEstado));
	 * 
	 * EstadoDiente estado; // Si encuentra el estado continúo if (index >= 0) {
	 * estado = this.estadosDiente.get(index); } else estado = new EstadoDiente();
	 * switch (opcion) { case 'n': break; case 'g': // general
	 * this.dienteSeleccionado.setEstadoGeneral(estado); break; case 'v':
	 * this.dienteSeleccionado.setEstadoVestibular(estado); break; case 'p':
	 * this.dienteSeleccionado.setEstadoPalatino(estado); break; case 'd':
	 * this.dienteSeleccionado.setEstadoDistal(estado); break; case 'o':
	 * this.dienteSeleccionado.setEstadoOclusal(estado); break; case 'e':
	 * this.dienteSeleccionado.setEstadoEsmalte(estado); break; case 'r'://
	 * Reseteo los estados this.dienteSeleccionado.setEstadoGeneral(new
	 * EstadoDiente()); this.dienteSeleccionado.setEstadoVestibular(new
	 * EstadoDiente()); this.dienteSeleccionado.setEstadoPalatino(new
	 * EstadoDiente()); this.dienteSeleccionado.setEstadoDistal(new
	 * EstadoDiente()); this.dienteSeleccionado.setEstadoOclusal(new
	 * EstadoDiente()); this.dienteSeleccionado.setEstadoEsmalte(new
	 * EstadoDiente()); break; }
	 * 
	 * } catch (Exception e) { IConstantes.log.error(e, e); } }
	 */
	public void cargarDiente(Integer cuadrante, Integer posicion) {
		this.dienteSeleccionado = null;
		int index;
		char resetEstados = 'n';
		// Busco el diente
		if (this.consulta.getDientesOdontograma() != null) {
			this.dienteSeleccionado = new Diente(cuadrante, posicion, true);
			index = this.consulta.getDientesOdontograma().indexOf(this.dienteSeleccionado);
			// Cargo la info guardada del diente
			if (index >= 0) {
				this.dienteSeleccionado = this.consulta.getDientesOdontograma().get(index);
				this.abrirModal("panelDetalleDiente");
				/*
				 * // Reseteo sus estados si está en modo de búsqueda if
				 * (this.consulta.getCargueRapido()) resetEstados = 'r';
				 * 
				 * //buscarEstadoDiente(resetEstados, 0);
				 * //getEstadoDienteSeleccionado(); //buscarEstadoDiente(resetEstados,
				 * this.getEstadoDienteSeleccionado().getId()); // Abro el modal si el
				 * estado del diente es por zonas if (!this.consulta.getCargueRapido()
				 * || !this.getEstadoDienteSeleccionado().getDienteCompleto())
				 * this.abrirModal("panelDetalleDiente"); else { buscarEstadoDiente('g',
				 * this.getEstadoDienteSeleccionado().getId()); // asigno el estado al
				 * diente armarDiente(false); this.dienteSeleccionado = null; }
				 */

			}

		}
	}

	/**
	 * Cancela la creación de una foto
	 */
	public void cerrarDiente() {

		try {
			this.dienteSeleccionado = null;

			this.cerrarModal("panelDetalleDiente");

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

	}

	public ByteArrayInputStream buscarImagenEstadoDiente(char opcion, Integer idEstado) {
		ByteArrayInputStream byteImagen = null;
		try {
			int index;
			// Cargo el estado actual
			index = this.estadosDiente.indexOf(new EstadoDiente(idEstado));

			EstadoDiente estado;
			// Si encuentra el estado continúo
			if (index >= 0) {
				estado = this.estadosDiente.get(index);
			} else
				estado = new EstadoDiente();
			switch (opcion) {
			case 'g': // general
				if (estado.getZonaTotal() != null)
					byteImagen = new ByteArrayInputStream(estado.getZonaTotal());
				break;
			case 'v':
				if (estado.getZona1() != null)
					byteImagen = new ByteArrayInputStream(estado.getZona1());
				break;
			case 'p':
				if (estado.getZona2() != null)
					byteImagen = new ByteArrayInputStream(estado.getZona2());
				break;
			case 'd':
				if (estado.getZona3() != null)
					byteImagen = new ByteArrayInputStream(estado.getZona3());
				break;
			case 'o':
				if (estado.getZona4() != null)
					byteImagen = new ByteArrayInputStream(estado.getZona4());
				break;
			case 'e':
				if (estado.getZona5() != null)
					byteImagen = new ByteArrayInputStream(estado.getZona5());
				break;

			}

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
		return byteImagen;
	}

	/*
	 * Método que superpone las imagenes de los estados y arma una imagen unica
	 * del estado del diente
	 */
	public void armarDiente(Boolean modal) {
		String path = this.getPath(IConstantes.PAQUETE_IMAGENES) + "/dientes/";
		ByteArrayInputStream byteImagen;
		boolean continuar = true;
		try {
			if (this.dienteSeleccionado != null) {

				if (modal && this.dienteSeleccionado.getDienteCompleto()) {
					if (this.dienteSeleccionado.getEstadoGeneral() == null || this.dienteSeleccionado.getEstadoGeneral().equals("")) {
						continuar = false;

						this.mostrarMensajeGlobal("Debe el elegir un estado para todo el diente", "advertencia");

					}

				} else {
					if (modal) {
						if ((this.dienteSeleccionado.getEstadoDistal() == null || this.dienteSeleccionado.getEstadoDistal().equals("")) && (this.dienteSeleccionado.getEstadoEsmalte() == null || this.dienteSeleccionado.getEstadoEsmalte().equals("")) && (this.dienteSeleccionado.getEstadoOclusal() == null || this.dienteSeleccionado.getEstadoOclusal().equals("")) && (this.dienteSeleccionado.getEstadoPalatino() == null || this.dienteSeleccionado.getEstadoPalatino().equals("")) && (this.dienteSeleccionado.getEstadoVestibular() == null || this.dienteSeleccionado.getEstadoVestibular().equals(""))) {
							continuar = false;
							this.mostrarMensajeGlobal("Debe al menos aplicar un estado a una zona", "advertencia");
						}
					}

				}
				if (continuar) {
					// Original
					BufferedImage resultado = ImageIO.read(new File(path + "dientesano.png"));
					Graphics g = resultado.getGraphics();

					// Si NO está marcado para cargar por zonas Cargo imagen del estado
					// del
					// diente completo
					// y borra lo que esté guardado en las zonas de lo contrario carga
					// todo
					if (this.dienteSeleccionado.getDienteCompleto()) {
						this.dienteSeleccionado.setEstadoDistal(null);
						this.dienteSeleccionado.setEstadoEsmalte(null);
						this.dienteSeleccionado.setEstadoOclusal(null);
						this.dienteSeleccionado.setEstadoPalatino(null);
						this.dienteSeleccionado.setEstadoVestibular(null);
						if (this.dienteSeleccionado.getEstadoGeneral() != null) {
							byteImagen = buscarImagenEstadoDiente('g', this.dienteSeleccionado.getEstadoGeneral());
							if (byteImagen != null)
								g.drawImage(ImageIO.read(byteImagen), 0, 0, null); // se rellena
																																		// con
																																		// imagen
																																		// uno
						}
					} else {

						if (this.dienteSeleccionado.getEstadoGeneral() != null) {
							byteImagen = buscarImagenEstadoDiente('g', this.dienteSeleccionado.getEstadoGeneral());
							if (byteImagen != null)
								g.drawImage(ImageIO.read(byteImagen), 0, 0, null); // se rellena
																																		// con
																																		// imagen
																																		// uno
						}

						// Oclusal
						if (this.dienteSeleccionado.getEstadoOclusal() != null) {
							byteImagen = buscarImagenEstadoDiente('o', this.dienteSeleccionado.getEstadoOclusal());
							if (byteImagen != null)
								g.drawImage(ImageIO.read(byteImagen), 0, 0, null); // se rellena
																																		// con
																																		// imagen
																																		// 2
						}

						// Vestibular
						if (this.dienteSeleccionado.getEstadoVestibular() != null) {
							byteImagen = buscarImagenEstadoDiente('v', this.dienteSeleccionado.getEstadoVestibular());
							if (byteImagen != null)
								g.drawImage(ImageIO.read(byteImagen), 0, 0, null); // se rellena
																																		// con
																																		// imagen
																																		// 2
						}

						// Palatino
						if (this.dienteSeleccionado.getEstadoPalatino() != null) {
							byteImagen = buscarImagenEstadoDiente('p', this.dienteSeleccionado.getEstadoPalatino());
							if (byteImagen != null)
								g.drawImage(ImageIO.read(byteImagen), 0, 0, null); // se rellena
																																		// con
																																		// imagen
																																		// 2
						}

						// Distal
						if (this.dienteSeleccionado.getEstadoDistal() != null) {
							byteImagen = buscarImagenEstadoDiente('d', this.dienteSeleccionado.getEstadoDistal());
							if (byteImagen != null)
								g.drawImage(ImageIO.read(byteImagen), 0, 0, null); // se rellena
																																		// con
																																		// imagen
																																		// 2
						}

						// Esmalte
						if (this.dienteSeleccionado.getEstadoEsmalte() != null) {
							byteImagen = buscarImagenEstadoDiente('e', this.dienteSeleccionado.getEstadoEsmalte());
							if (byteImagen != null)
								g.drawImage(ImageIO.read(byteImagen), 0, 0, null); // se rellena
																																		// con
																																		// imagen
																																		// 2
						}

					}

					this.dienteSeleccionado.setNombreFoto("diente" + this.dienteSeleccionado.getDienteTexto() + ".png");

					// this.dienteSeleccionado.setNombreFoto(
					// "diente" + this.dienteSeleccionado.getDienteTexto() +
					// (Math.random()
					// * 100000) + ".png");

					// guardo la imagen en disco y se la asigno al diente
					// ImageIO.write(resultado, "png", new File(path +
					// this.dienteSeleccionado.getNombreFoto()));

					// COnvierto el buffered en bytes
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(resultado, "png", baos);
					baos.flush();

					this.dienteSeleccionado.setImagenDiente(baos.toByteArray());
					baos.close();

					// Asigno nuevamente el diente al array
					int index = this.consulta.getDientesOdontograma().indexOf(this.dienteSeleccionado);
					if (index >= 0)
						this.consulta.getDientesOdontograma().set(index, this.dienteSeleccionado);

					if (modal){
						this.mostrarMensajeGlobal("Estado aplicado. No olvide guardar cambios", "advertencia");
						this.cerrarModal("panelDetalleDiente");
					}

					// this.estadoDienteSeleccionado=null;
					this.dienteSeleccionado = null;
					cargarObservacionesOdontograma();

				}

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	// MÉTODOS TRATAMIENTOS
	public void cancelarTratamientoUsuario() {
		this.tratamientoUsuario = null;
	}

	public void cargarTratamientoUsuario(TratamientoUsuario tu) {
		getTratamientos();
		// Reseteo los tratamientos
		this.tratamientos.forEach(t -> {
			t.setUsado(false);
		});

		if (tu == null)
			this.tratamientoUsuario = new TratamientoUsuario();
		else {
			this.tratamientoUsuario = tu;
			try {
				this.tratamientoUsuario.setDetallesTratamiento(IAtencionOdontologicaDAO.getDetalleTratamiento(tu, null));

				this.tratamientoUsuario.getDetallesTratamiento().forEach(dt -> {
					Integer index = this.tratamientos.indexOf(dt.getTratamiento());
					if (index >= 0)// si lo encuentra quito el procedimiento del
						// listado inicial
						this.tratamientos.get(index).setUsado(true);
					else
						this.tratamientos.get(index).setUsado(false);
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		this.vistaActual = 3;
	}

	public void imprimirConsentimiento() {
		try {

			Map<String, Object> parametros = new HashMap<String, Object>();
			String reporte = "imprimirConsentimiento.jasper";
			this.guardarFirmaComoImagen(this.tratamientoUsuario.getFirma(), "firma" + this.tratamientoUsuario.getId());
			parametros.put("rutaFirmas", this.getPath(IConstantes.PAQUETE_IMAGENES) + "/usuarios/firma" + this.tratamientoUsuario.getId() + ".png");
			parametros.put("pPaciente", this.usuario.getNombres().toUpperCase() + " " + this.usuario.getApellidos().toUpperCase());
			parametros.put("pTratamiento", this.tratamientoUsuario);
			parametros.put("pNombreCompletoConsultor", "Emilio Cárcamo");
			parametros.put("pRutaLogo", this.getPath(IConstantes.PAQUETE_IMAGENES + IConstantes.LOGO));

			generarListado(new JRBeanCollectionDataSource(this.tratamientoUsuario.getDetallesTratamiento()), reporte, "consentimientoPaciente" + new Date(), null, parametros);
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

	}

	public void imprimirCotizacion() {
		try {

			Map<String, Object> parametros = new HashMap<String, Object>();
			String reporte = "imprimirCotizacion.jasper";
			parametros.put("pPaciente", this.usuario.getNombres().toUpperCase() + " " + this.usuario.getApellidos().toUpperCase());
			parametros.put("pTratamiento", this.tratamientoUsuario);
			parametros.put("pNombreCompletoConsultor", "Emilio Cárcamo");
			parametros.put("pRutaLogo", this.getPath(IConstantes.PAQUETE_IMAGENES + IConstantes.LOGO));

			generarListado(new JRBeanCollectionDataSource(this.tratamientoUsuario.getDetallesTratamiento()), reporte, "cotizacion" + new Date(), null, parametros);
		} catch (Exception e) {
			IConstantes.log.error(e, e);

		}

	}

	/**
	 * Guarda en disco como imagen una firma signature
	 * 
	 * @param aSignature
	 * @param aNombreFirma
	 */
	public void guardarFirmaComoImagen(String aSignature, String aNombreFirma) {
		try {
			FirmaComoImagen firma = new FirmaComoImagen();
			byte[] archivo = firma.getImagenComoByte(aSignature);
			File outputfile = new File(this.getPath(IConstantes.PAQUETE_IMAGENES) + "/usuarios/" + aNombreFirma + ".png");

			BufferedImage img = ImageIO.read(new ByteArrayInputStream(archivo));
			if (img != null) {
				ImageIO.write(img, "png", outputfile);
			}

		} catch (Exception e) {
			IConstantes.log.error(e, e);

		}
	}

	public void agregarTratamiento(Tratamiento t) {
		if (t != null) {
			// Busco el tratamiento y le asigno todos los atributos
			if (!t.getUsado()) {
				this.tratamientoUsuario.getDetallesTratamiento().add(new DetalleTratamiento(t));
				this.mostrarMensajeGlobal("Procedimiento agregado", "exito");
			}
			t.setUsado(true);

		}
		calcularTotal();
	}

	// Elimina un detalle del listado de procedimientos y lo devuelve al listado
	// original
	public void eliminarDetalleTratamiento(DetalleTratamiento dt) {
		if (dt != null) {
			// Busco el tratamiento y le asigno todos los atributos
			dt.setModoEliminar(true);
			Integer index = this.tratamientos.indexOf(dt.getTratamiento());
			if (index >= 0) {// devuelvo el procedimiento al listado inicial
				this.tratamientos.get(index).setUsado(false);
				this.mostrarMensajeGlobal("Procedimiento eliminado", "exito");
			}
		}
		calcularTotal();
	}

	public void calcularTotal() {
		BigDecimal total = new BigDecimal("0.0");
		for (DetalleTratamiento dt : this.tratamientoUsuario.getDetallesTratamientoCrear()) {
			total = dt.getValorTotal().add(total);
		}
		setVistaActual(3);
		this.tratamientoUsuario.setValorTratamiento(total.subtract(this.tratamientoUsuario.getDescuento()));
		this.tratamientoUsuario.setSaldo(this.tratamientoUsuario.getValorTratamiento());
	}

	public void guardarTratamientoUsuario(Boolean modal) {
		Conexion conexion = new Conexion();
		Integer id;
		try {
			conexion.setAutoCommitBD(false);
			// La edición limitada solo permite cambiar algunos elementos del
			// tratamiento como el estado
			if (this.tratamientoUsuario.getFirma() != null)
				this.tratamientoUsuario.setModoEdicionLimitada(true);

			// this.tratamientoUsuario.setSaldo(this.tratamientoUsuario.getValorTratamiento());
			if (!this.tratamientoUsuario.getModoEdicion()) {
				id = conexion.getConsecutivo(TratamientoUsuario.MAX);
				this.tratamientoUsuario.setId(id);
				this.tratamientoUsuario.setDocumento(this.usuario.getIdDocumento());

				this.tratamientoUsuario.getCamposBD();
				conexion.insertarBD(this.tratamientoUsuario.getEstructuraTabla().getTabla(), this.tratamientoUsuario.getEstructuraTabla().getPersistencia());
				this.mostrarMensajeGlobal("Tratamiento guardado", "exito");
			} else {
				this.tratamientoUsuario.getCamposBD();
				conexion.actualizarBD(this.tratamientoUsuario.getEstructuraTabla().getTabla(), this.tratamientoUsuario.getEstructuraTabla().getPersistencia(), this.tratamientoUsuario.getEstructuraTabla().getLlavePrimaria(), null);
				this.mostrarMensajeGlobal("Tratamiento Actualizado", "exito");
			}

			// Creo ,edito o elimino el detalle del tratamiento
			if (!this.tratamientoUsuario.getModoEdicionLimitada())
				for (DetalleTratamiento d : this.tratamientoUsuario.getDetallesTratamiento()) {
					d.setTratamientoUsuario(this.tratamientoUsuario);
					d.getCamposBD();
					if (d.getModoEliminar())
						conexion.eliminarBD(d.getEstructuraTabla().getTabla(), d.getEstructuraTabla().getLlavePrimaria());
					else if (!d.getModoEdicion())
						conexion.insertarBD(d.getEstructuraTabla().getTabla(), d.getEstructuraTabla().getPersistencia());
					else
						conexion.actualizarBD(d.getEstructuraTabla().getTabla(), d.getEstructuraTabla().getPersistencia(), d.getEstructuraTabla().getLlavePrimaria(), null);

					d.setModoEdicion(true);
				}
			this.tratamientoUsuario.setModoEdicion(true);
			conexion.commitBD();
			if (modal) {
				this.abrirModal("panelTratamientoCompleto");
				// Cargo los tratamientos
				this.usuario.setTratamientosUsuario(IAtencionOdontologicaDAO.getTratamientosUsuario(null, this.usuario));
			}
			setVistaActual(3);

		} catch (Exception e) {
			conexion.rollbackBD();
			this.mostrarMensajeGlobal("transaccionFallida", "error");
			IConstantes.log.error(e, e);
		} finally {
			conexion.cerrarConexion();
		}

	}

	/*
	 * Carga el modal para la subida del consentimiento del tratamiento
	 */
	public void cargarConsentimiento(TratamientoUsuario tu) {

		cargarTratamientoUsuario(tu);

		this.abrirModal("panelConsentimiento");
	}

	public void cargarInformacionConsentimiento(Integer id) {
		getConsentimientos();
		Integer index = this.consentimientos.indexOf(new Consentimiento(id));
		if (index >= 0) {
			Consentimiento c = this.consentimientos.get(index);
			this.tratamientoUsuario.setTextoConsentimiento(c.getDescripcion());
		} else
			this.tratamientoUsuario.setTextoConsentimiento("");

	}

	/*
	 * Cierra el dialogo del consentimiento
	 */
	public void cerrarConsentimiento() {

		try {
			this.tratamientosUsuario = null;

			this.cerrarModal("panelConsentimiento");

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

	}

	/**
	 * Limpia la firma del técnico
	 */
	public void limpiarFirmaConsentimiento() {
		this.tratamientoUsuario.setFirma(null);
	}

	/*
	 * Carga una evolucion guardada para el tratamiento
	 */
	public void cargarEvolucion(Cita c) {

		this.citaSeleccionada = c;
		this.citaSeleccionada.setPaciente(this.usuario);
		this.citaSeleccionada.setTratamiento(this.tratamientoUsuario.getId());
		this.citaSeleccionada.setSaldo(this.tratamientoUsuario.getSaldo());

	}

	/*
	 * Carga una nueva evolucion para el tratamiento
	 */
	public void cargarNuevaEvolucion() {

		this.citaSeleccionada = new Cita(this.usuario);
		this.citaSeleccionada.setFechaInicio(new Date());
		this.citaSeleccionada.setFechaFin(new Date());
		this.citaSeleccionada.setTratamiento(this.tratamientoUsuario.getId());
		this.citaSeleccionada.setSaldo(this.tratamientoUsuario.getSaldo());

	}

	public void cargarObservacionesEvolucion(TratamientoUsuario tu) {
		String observaciones = "Estado del tratamiento: " + tu.getEstadoTexto() + "  , ";
		if (tu.getDetallesTratamiento() != null) {
			for (DetalleTratamiento d : tu.getDetallesTratamiento()) {
				observaciones += d.getTratamiento().getNombre() + ":" + d.getEstadoTexto() + "  , ";
			}
		}
		this.citaSeleccionada.setDetalleEvolucionAutomatica(observaciones);
	}

	// Actualiza el saldo del tratamiento
	public void actualizarSaldo() {
		if (this.citaSeleccionada.getAbono() != null && this.citaSeleccionada.getAbono().compareTo(BigDecimal.ZERO) > 0)
			this.citaSeleccionada.setSaldo(this.tratamientoUsuario.getSaldo().subtract(this.citaSeleccionada.getAbono()));
		else
			this.citaSeleccionada.setSaldo(this.tratamientoUsuario.getSaldo());
	}

	/*
	 * Carga el modal para agregar una evolución del tratamiento
	 */
	public void cargarEvolucionesTratamiento(TratamientoUsuario tu) {

		cargarTratamientoUsuario(tu);
		try {
			this.citas = IAtencionOdontologicaDAO.getAgenda(null, null, new Cita(this.usuario));
			// Cargo la evolución del tratamiento seleccionado
			this.tratamientoUsuario.setEvoluciones(new ArrayList<Cita>());
			this.citasSinTratamiento = new ArrayList<Cita>();

			if (this.citas != null)
				this.citas.forEach(c -> {
					if (c.getTratamiento() != null) {
						if (c.getTratamiento().equals(tu.getId()))
							this.tratamientoUsuario.getEvoluciones().add(c);
					} else
						this.citasSinTratamiento.add(c);
				});
			this.citaSeleccionada = null;
			this.abrirModal("panelEvolucion");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Cierra el dialogo de la evolución
	 */
	public void cerrarEvolucion() {

		try {
			this.tratamientoUsuario = null;

			this.cerrarModal("panelEvolucion");

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
	}

	/*
	 * Cierra el dialogo de la evolución
	 */
	public void cancelarEvolucion() {

		try {
			this.citaSeleccionada = null;

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
	}

	// Guarda la evolución del tratamiento seleccionado
	public void guardarEvolucionTratamiento() {
		// Actualizo el evento con el nombre del paciente
		Conexion conexion = new Conexion();

		if (this.citaSeleccionada != null) {
			try {
				conexion.setAutoCommitBD(false);
				if (this.citaSeleccionada.getAbono() != null && this.citaSeleccionada.getAbono().compareTo(BigDecimal.ZERO) != 0) {
					this.citaSeleccionada.setEstado("T");// Terminada
					this.tratamientoUsuario.setSaldo(this.citaSeleccionada.getSaldo());
					// Si el tratamiento estaba en Registrado, lo pasamos a iniciado
					if (this.tratamientoUsuario.getEstado().equals("R"))
						this.tratamientoUsuario.setEstado("I");
				} else
					this.citaSeleccionada.setEstado("P");// Para Pago
				// Si no existe la creo y la asigno al listado
				if (!this.citaSeleccionada.getModoEdicion()) {

					this.citaSeleccionada.setId(conexion.getConsecutivo(Cita.MAX).intValue());
					this.citaSeleccionada.getCamposBD();
					conexion.insertarBD(this.citaSeleccionada.getEstructuraTabla().getTabla(), this.citaSeleccionada.getEstructuraTabla().getPersistencia());
				} else {
					this.citaSeleccionada.getCamposBD();
					conexion.actualizarBD(this.citaSeleccionada.getEstructuraTabla().getTabla(), this.citaSeleccionada.getEstructuraTabla().getPersistencia(), this.citaSeleccionada.getEstructuraTabla().getLlavePrimaria(), null);

				}

				// Actualizo el tratamiento
				this.tratamientoUsuario.getCamposBD();
				conexion.actualizarBD(this.tratamientoUsuario.getEstructuraTabla().getTabla(), this.tratamientoUsuario.getEstructuraTabla().getPersistencia(), this.tratamientoUsuario.getEstructuraTabla().getLlavePrimaria(), null);
				for (DetalleTratamiento d : this.tratamientoUsuario.getDetallesTratamiento()) {
					d.setTratamientoUsuario(this.tratamientoUsuario);
					d.getCamposBD();
					conexion.actualizarBD(d.getEstructuraTabla().getTabla(), d.getEstructuraTabla().getPersistencia(), d.getEstructuraTabla().getLlavePrimaria(), null);
					d.setModoEdicion(true);
				}
				conexion.commitBD();
				this.mostrarMensajeGlobal("Evolución guardada", "exito");
				this.mostrarMensajeGlobal("Tratamiento Actualizado", "exito");
				// Vuelvo a cargar las evoluciones del tratamiento
				cargarEvolucionesTratamiento(this.tratamientoUsuario);
			} catch (Exception e) {
				conexion.rollbackBD();
				this.mostrarMensajeGlobal("transaccionFallida", "error");
				IConstantes.log.error(e, e);
			} finally {
				conexion.cerrarConexion();
			}
		}

	}

	/*
	 * METODOS DE LA AGENDA
	 */

	// Se selecciona un espacio vacio del calendario, luego se crea una nueva
	// instancia de la cita
	public void programarCita() {
		this.textoConsulta = "";
		Integer tiempoConsulta = 30;
		Date fechaInicio = new Date();
		Calendar fechaFin = Calendar.getInstance();
		fechaFin.setTime(fechaInicio); // Configuramos la fecha que se recibe
		fechaFin.add(Calendar.MINUTE, tiempoConsulta);
		this.citaSeleccionada = new Cita(new DefaultScheduleEvent("", fechaInicio, fechaFin.getTime()));
		this.citaSeleccionada.setPaciente(this.usuario);
		this.citaSeleccionada.setFechaInicio(fechaInicio);
		this.citaSeleccionada.setFechaFin(fechaFin.getTime());
		this.abrirModal("pnlNuevaCita");
	}

	/**
	 * consulta un usuario a partir de una cadena de texto
	 * 
	 * @param dato:
	 *          String que indica el diagnostico seleccionado
	 */
	public void agregarUsuarioCita(String dato) {
		String[] datosUsuario;
		final String NOMBRE_METODO = " agregarUsuarioCita(String dato)";
		datosUsuario = dato != null ? dato.split("-") : null;
		try {
			if (datosUsuario[0] != null) {
				this.citaSeleccionada.setPaciente(new Usuario(datosUsuario[0], datosUsuario[1]));
			}
		} catch (Exception e) {
			IConstantes.log.error(NOMBRE_METODO + e, e);
		}
	}

	public void cancelarFormularioCita() {
		this.citaSeleccionada = new Cita(new DefaultScheduleEvent());
		this.textoConsulta = "";
		this.cerrarModal("eventDialog");
	}

	public void agregarCita() {
		getCitas();
		// Actualizo el evento con el nombre del paciente
		// this.mostrarMensajeGlobal("Entró a guardar cita", "exito");
		Conexion conexion = new Conexion();
		if (this.citaSeleccionada.getPaciente() != null) {
			((DefaultScheduleEvent) this.citaSeleccionada.getEvent()).setTitle(this.citaSeleccionada.getPaciente().getNombres());
			this.citaSeleccionada.setFechaInicio(this.citaSeleccionada.getEvent().getStartDate());
			this.citaSeleccionada.setFechaFin(this.citaSeleccionada.getEvent().getEndDate());
			try {
				conexion.setAutoCommitBD(false);
				// Si no existe la creo y la asigno al listado
				if (!this.citaSeleccionada.getModoEdicion()) {

					this.citaSeleccionada.setId(conexion.getConsecutivo(Cita.MAX).intValue());
					this.citaSeleccionada.getCamposBD();
					conexion.insertarBD(this.citaSeleccionada.getEstructuraTabla().getTabla(), this.citaSeleccionada.getEstructuraTabla().getPersistencia());
					this.citaSeleccionada.setModoEdicion(true);
					this.citas.add(this.citaSeleccionada);
					if (this.calendario != null)
						this.calendario.addEvent(this.citaSeleccionada.getEvent());
				} else {
					this.citaSeleccionada.getCamposBD();
					conexion.actualizarBD(this.citaSeleccionada.getEstructuraTabla().getTabla(), this.citaSeleccionada.getEstructuraTabla().getPersistencia(), this.citaSeleccionada.getEstructuraTabla().getLlavePrimaria(), null);

					if (this.calendario != null)
						this.calendario.updateEvent(this.citaSeleccionada.getEvent());

				}
				conexion.commitBD();
				// this.cerrarModal("eventDialog");
				this.mostrarMensajeGlobal("Cita guardada", "exito");

			} catch (Exception e) {
				conexion.rollbackBD();
				this.mostrarMensajeGlobal("transaccionFallida", "error");
				IConstantes.log.error(e, e);
			} finally {
				conexion.cerrarConexion();
			}
		}

	}

	public void actualizarFecha() {
		Integer tiempoConsulta = 30;
		Calendar fechaFin = Calendar.getInstance();
		// fechaFin.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE,
		// Calendar.HOUR_OF_DAY, Calendar.MINUTE);
		fechaFin.setTime((Date) this.citaSeleccionada.getEvent().getStartDate()); // Configuramos
																																							// la
																																							// fecha
																																							// que
																																							// se
																																							// recibe
		fechaFin.add(Calendar.MINUTE, tiempoConsulta);
		((DefaultScheduleEvent) this.citaSeleccionada.getEvent()).setEndDate(fechaFin.getTime());
	}

	// Se selecciona un evento previamente creado
	public void onEventSelect(SelectEvent selectEvent) {
		buscarCita((ScheduleEvent) selectEvent.getObject());
		this.abrirModal("eventDialog");
	}

	// Se selecciona un espacio vacio del calendario, luego se crea una nueva
	// instancia de la cita
	public void onDateSelect(SelectEvent selectEvent) {
		this.textoConsulta = "";
		Integer tiempoConsulta = 30;
		Calendar fechaFin = Calendar.getInstance();
		fechaFin.setTime((Date) selectEvent.getObject()); // Configuramos la fecha
																											// que se recibe
		fechaFin.add(Calendar.MINUTE, tiempoConsulta);
		this.citaSeleccionada = new Cita(new DefaultScheduleEvent("", (Date) selectEvent.getObject(), fechaFin.getTime()));
		this.citaSeleccionada.setFechaInicio((Date) selectEvent.getObject());
		this.citaSeleccionada.setFechaFin(fechaFin.getTime());
	}

	// Busca un evento y lo asigna al objeto cita
	public void buscarCita(ScheduleEvent evento) {
		getCitas();
		for (Cita c : this.citas) {
			if (c.getEvent().equals(evento)) {
				c.setEvent(evento);
				this.citaSeleccionada = c;
				// Actualizo la info del evento
				this.citaSeleccionada.setEvent(evento);
				this.citaSeleccionada.setModoEdicion(true);
				// this.mostrarMensajeGlobal( "Evento encontrado","error");
				this.textoConsulta = c.getPaciente().getIdDocumento() + "-" + c.getPaciente().getNombres();
				break;
			}
		}
		// this.mostrarMensajeGlobal( "Evento movido : Day delta:" +
		// event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta(),
		// "error");

	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
		buscarCita(event.getScheduleEvent());
		agregarCita();
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		buscarCita(event.getScheduleEvent());
		agregarCita();
	}

	public String pasaraConsulta() {
		String pagina = null;
		pagina = "/atencionOdontologica/atencionOdontologica.xhtml?faces-redirect=true&includeViewParams=true" + "&idPaciente=" + this.citaSeleccionada.getPaciente().getIdDocumento();

		return pagina;

	}

	public void guardarPrimeraVez() {

		consultarUsuario();
		this.vistaActual = 1;
		this.consulta.setFechaConsulta(null);
	}

	//
	// listados y etructuras

	// gets/sets

	public Integer getVistaActual() {
		if (this.vistaActual == null) {
			this.vistaActual = 0;
		}
		return vistaActual;
	}

	public void setVistaActual(Integer vistaActual) {
		this.vistaActual = vistaActual;
	}

	public Usuario getUsuario() {
		if (this.usuario == null) {
			this.usuario = new Usuario();

			if (this.indicativoDatosPorDefecto) {
				this.usuario.setNombres("CARLOS FERNANDO");
				this.usuario.setApellidos("OSORIO PEREZ");
				this.usuario.setIdDocumento("95678412");
				Calendar cal = Calendar.getInstance();
				cal.set(1980, 12, 30);
				this.usuario.setFechaNacimiento(cal.getTime());
				this.usuario.setEdad(35);
				this.usuario.setTelefono("3114569875");
				this.usuario.setDireccion("Calle 17 # 28-39 San Alonso");
				this.usuario.setOcupacion("Abogado");
				this.usuario.setCorreoElectronico("carlosf@hotmail.com");
				Date fechaNacimiento = new Date();
				this.usuario.setFechaNacimiento(fechaNacimiento);
				this.usuario.setAcudiente("MARIA DE OSORIO");
			}
		}

		// agregado nuevo para que n quite la imagen
		if (this.usuario.getArchivo() != null && !this.usuario.getArchivo().equals("")) {
			// this.guardarImagenEnDisco(this.usuario.getIdDocumento(),
			// this.usuario.getArchivo(), "usuarios");

			this.usuario.settNombreFoto(convertirImagen(this.usuario.getArchivo()));
		} else
			this.usuario.settNombreFoto("../imagenes/usuarios/usuario.jpg");

		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(String idPaciente) {
		this.idPaciente = idPaciente;
	}

	public Consulta getConsulta() {
		if (this.consulta == null) {
			this.consulta = new Consulta();
			cargarConsulta(null);

		}
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	/**
	 * Este método lo usamos para obtener el objeto que crea las clases de higiene
	 * oral
	 * 
	 * @return claseHigieneOral
	 */
	public ClaseHigieneOral getClaseHigieneOral() {
		try {
			if (this.claseHigieneOral == null) {
				this.claseHigieneOral = new ClaseHigieneOral();
			}
		} catch (Exception e) {
			Log.error(e, e);
		}
		return claseHigieneOral;
	}

	/**
	 * Este método lo usamos para establecer el objeto que crea las clases de
	 * higiene oral
	 * 
	 * @param claseHigieneOral
	 */
	public void setClaseHigieneOral(ClaseHigieneOral claseHigieneOral) {
		this.claseHigieneOral = claseHigieneOral;
	}

	public ClaseHigieneOral getClaseHigieneOralTrnsccon() {
		try {

			if (this.claseHigieneOralTrnsccon == null) {
				this.claseHigieneOralTrnsccon = new ClaseHigieneOral();
			}

		} catch (Exception e) {
			Log.error(e, e);
		}

		return claseHigieneOralTrnsccon;
	}

	public void setClaseHigieneOralTrnsccon(ClaseHigieneOral claseHigieneOralTrnsccon) {
		this.claseHigieneOralTrnsccon = claseHigieneOralTrnsccon;
	}

	/**
	 * Obtiene un listado de higienes orales
	 * 
	 * @return clasesHigieneOral
	 */
	public List<ClaseHigieneOral> getClasesHigieneOral() {
		try {
			if (this.clasesHigieneOral == null) {
				this.clasesHigieneOral = IConsultasDAO.getClasesHigieneOral(null);
			}
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return clasesHigieneOral;
	}

	public void setClasesHigieneOral(List<ClaseHigieneOral> clasesHigieneOral) {
		this.clasesHigieneOral = clasesHigieneOral;
	}

	public ClaseHabitoOral getClaseHabitoOral() {
		if (this.claseHabitoOral == null) {
			this.claseHabitoOral = new ClaseHabitoOral();
		}
		return claseHabitoOral;
	}

	public void setClaseHabitoOral(ClaseHabitoOral claseHabitoOral) {
		this.claseHabitoOral = claseHabitoOral;
	}

	public ClaseHabitoOral getClaseHabitoOralTrnsccon() {
		if (this.claseHabitoOralTrnsccon == null) {
			this.claseHabitoOralTrnsccon = new ClaseHabitoOral();
		}
		return claseHabitoOralTrnsccon;
	}

	public void setClaseHabitoOralTrnsccon(ClaseHabitoOral claseHabitoOralTrnsccon) {
		this.claseHabitoOralTrnsccon = claseHabitoOralTrnsccon;
	}

	public List<ClaseHabitoOral> getClasesHabitoOral() {
		try {
			if (this.clasesHabitoOral == null) {
				this.clasesHabitoOral = IConsultasDAO.getClasesHabitoOral(null);
			}
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return clasesHabitoOral;
	}

	public void setClasesHabitoOral(List<ClaseHabitoOral> clasesHabitoOral) {
		this.clasesHabitoOral = clasesHabitoOral;
	}

	public ClaseAnamnesis getClaseAnamnesis() {
		if (this.claseAnamnesis == null) {
			this.claseAnamnesis = new ClaseAnamnesis();
		}
		return claseAnamnesis;
	}

	public void setClaseAnamnesis(ClaseAnamnesis claseAnamnesis) {
		this.claseAnamnesis = claseAnamnesis;
	}

	public ClaseAnamnesis getClaseAnamnesisTrnsccon() {
		if (this.claseAnamnesisTrnsccon == null) {
			this.claseAnamnesisTrnsccon = new ClaseAnamnesis();
		}
		return claseAnamnesisTrnsccon;
	}

	public void setClaseAnamnesisTrnsccon(ClaseAnamnesis claseAnamnesisTrnsccon) {
		this.claseAnamnesisTrnsccon = claseAnamnesisTrnsccon;
	}

	public List<ClaseAnamnesis> getClasesAnamnesis() {
		try {
			if (this.clasesAnamnesis == null) {
				this.clasesAnamnesis = IConsultasDAO.getClasesAnamnesis(null);
			}
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return clasesAnamnesis;
	}

	public void setClasesAnamnesis(List<ClaseAnamnesis> clasesAnamnesis) {
		this.clasesAnamnesis = clasesAnamnesis;
	}

	public ClaseExamenClinico getClaseExamenClinico() {
		if (this.claseExamenClinico == null) {
			this.claseExamenClinico = new ClaseExamenClinico();
		}
		return claseExamenClinico;
	}

	public void setClaseExamenClinico(ClaseExamenClinico claseExamenClinico) {
		this.claseExamenClinico = claseExamenClinico;
	}

	public ClaseExamenClinico getClaseExamenClinicoTrnsccon() {
		if (this.claseExamenClinicoTrnsccon == null) {
			this.claseExamenClinicoTrnsccon = new ClaseExamenClinico();
		}
		return claseExamenClinicoTrnsccon;
	}

	public void setClaseExamenClinicoTrnsccon(ClaseExamenClinico claseExamenClinicoTrnsccon) {
		this.claseExamenClinicoTrnsccon = claseExamenClinicoTrnsccon;
	}

	public List<ClaseExamenClinico> getClasesExamenClinico() {
		try {
			if (this.clasesExamenClinico == null) {
				this.clasesExamenClinico = IConsultasDAO.getClasesExamenClinico(null);
			}
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return clasesExamenClinico;
	}

	public void setClasesExamenClinico(List<ClaseExamenClinico> clasesExamenClinico) {
		this.clasesExamenClinico = clasesExamenClinico;
	}

	public List<EstadoDiente> getEstadosDiente() {
		String base64DataString = "";
		Integer anchoIcono = 55;
		try {
			if (this.estadosDiente == null) {
				this.estadosDiente = IConsultasDAO.getEstadosDiente(null);
				for (EstadoDiente ed : this.estadosDiente) {
					if (ed.getZonaTotal() != null) {

						base64DataString = Base64.getEncoder().encodeToString(ed.getZonaTotal());
						String imagen = "background-image: url('data:image/png;base64," + base64DataString + "');" + "background-origin: content-box;" + " background-size: cover;" + "float:right;" + " background-repeat:no-repeat;" + " width:" + anchoIcono + "px; height:" + anchoIcono + "px;" + " border:0; " + "background-color:transparent";

						ed.setZonaTotalNombreFoto(imagen);
					}
				}
			}
		} catch (Exception e) {

			IConstantes.log.error(e, e);
		}

		return estadosDiente;
	}

	public void setEstadosDiente(List<EstadoDiente> estadosDiente) {
		this.estadosDiente = estadosDiente;
	}

	public List<SelectItem> getItemsEstadosDiente() {

		List<SelectItem> itemsEstadosDiente = new ArrayList<SelectItem>();
		try {
			getEstadosDiente();
			itemsEstadosDiente.add(new SelectItem("", "Seleccione estado.."));

			if (this.estadosDiente != null) {
				this.estadosDiente.forEach(e -> itemsEstadosDiente.add(new SelectItem(e.getId(), e.getNombre())));
			}

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return itemsEstadosDiente;
	}

	public void setItemsEstadosDiente(List<SelectItem> itemsEstadosDiente) {
		this.itemsEstadosDiente = itemsEstadosDiente;
	}

	public List<SelectItem> getItemsEstadosDienteZona() {
		List<SelectItem> itemsEstadosDienteZona = new ArrayList<SelectItem>();
		try {
			getEstadosDiente();
			itemsEstadosDienteZona.add(new SelectItem("", "Seleccione estado.."));

			if (this.estadosDiente != null) {
				this.estadosDiente.forEach(e -> {
					if (!e.getDienteCompleto())
						itemsEstadosDienteZona.add(new SelectItem(e.getId(), e.getNombre()));
				});
			}
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
		return itemsEstadosDienteZona;
	}

	public void setItemsEstadosDienteZona(List<SelectItem> itemsEstadosDienteZona) {
		this.itemsEstadosDienteZona = itemsEstadosDienteZona;
	}

	public EstadoDiente getEstadoDienteSeleccionado() {
		if (this.estadoDienteSeleccionado == null)
			this.estadoDienteSeleccionado = new EstadoDiente();
		return estadoDienteSeleccionado;
	}

	public void setEstadoDienteSeleccionado(EstadoDiente estadoDienteSeleccionado) {
		this.estadoDienteSeleccionado = estadoDienteSeleccionado;
	}

	public Diente getDienteSeleccionado() {
		return dienteSeleccionado;
	}

	public void setDienteSeleccionado(Diente dienteSeleccionado) {
		this.dienteSeleccionado = dienteSeleccionado;
	}

	public TratamientoUsuario getTratamientoUsuario() {
		return tratamientoUsuario;
	}

	public void setTratamientoUsuario(TratamientoUsuario tratamientoUsuario) {
		this.tratamientoUsuario = tratamientoUsuario;
	}

	public List<Tratamiento> getTratamientos() {
		try {
			if (this.tratamientos == null) {
				this.tratamientos = IConsultasDAO.getTratamientos(null);
			}
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
		return tratamientos;
	}

	public void setTratamientos(List<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}

	public List<Tratamiento> getTratamientosCrear() {
		this.tratamientosCrear = new ArrayList<Tratamiento>();
		if (this.tratamientos != null) // Traigo aquellos que no estan para
			// eliminar
			this.tratamientosCrear = this.tratamientos.stream().filter(t -> !t.getUsado()).collect(Collectors.toList());
		return tratamientosCrear;
	}

	public List<Tratamiento> getTratamientosUsuario() {
		return tratamientosUsuario;
	}

	public void setTratamientosUsuario(List<Tratamiento> tratamientosUsuario) {
		this.tratamientosUsuario = tratamientosUsuario;
	}

	public Tratamiento getTratamientoSeleccionado() {
		if (this.tratamientoSeleccionado == null)
			this.tratamientoSeleccionado = new Tratamiento();
		return tratamientoSeleccionado;
	}

	public void setTratamientoSeleccionado(Tratamiento tratamientoSeleccionado) {
		this.tratamientoSeleccionado = tratamientoSeleccionado;
	}

	public DocumentoUsuario getDocumentoUsuario() {
		return documentoUsuario;
	}

	public void setDocumentoUsuario(DocumentoUsuario documentoUsuario) {
		this.documentoUsuario = documentoUsuario;
	}

	public DocumentoConsulta getDocumentoConsulta() {
		return documentoConsulta;
	}

	public void setDocumentoConsulta(DocumentoConsulta documentoConsulta) {
		this.documentoConsulta = documentoConsulta;
	}

	public ScheduleModel getCalendario() {
		if (this.calendario == null)
			getCitas();
		return calendario;
	}

	public void setCalendario(ScheduleModel calendario) {
		this.calendario = calendario;
	}

	public List<Cita> getCitas() {
		Integer tiempoConsulta = 10000; // 3 días
		Calendar fechaFin = Calendar.getInstance();
		fechaFin.setTime(new Date()); // Configuramos la fecha que se recibe
		fechaFin.add(Calendar.MINUTE, tiempoConsulta);
		if (this.citas == null) {
			try {
				this.citas = IAtencionOdontologicaDAO.getAgenda(new Date(), fechaFin.getTime(), null);
				this.calendario = new DefaultScheduleModel();
				this.citas.forEach(c -> this.calendario.addEvent(c.getEvent()));
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return citas;
	}

	public void setCitas(List<Cita> citas) {
		this.citas = citas;
	}

	public List<Cita> getCitasSinTratamiento() {

		return citasSinTratamiento;
	}

	public void setCitasSinTratamiento(List<Cita> citasSinTratamiento) {
		this.citasSinTratamiento = citasSinTratamiento;
	}

	public Cita getCitaSeleccionada() {
		// if(this.citaSeleccionada==null)
		// this.citaSeleccionada= new Cita();
		return citaSeleccionada;
	}

	public void setCitaSeleccionada(Cita citaSeleccionada) {
		this.citaSeleccionada = citaSeleccionada;
	}

	public String getTextoConsulta() {
		return textoConsulta;
	}

	public void setTextoConsulta(String textoConsulta) {
		this.textoConsulta = textoConsulta;
	}

	public List<SelectItem> getOdontologos() {
		this.odontologos = new ArrayList<SelectItem>();
		try {
			List<Usuario> lstOdontologos = IAtencionOdontologicaDAO.getUsuariosRol("O");
			// this.odontologos.add(new SelectItem("", "Seleccione Odontologo.."));
			if (lstOdontologos != null) {
				lstOdontologos.forEach(o -> this.odontologos.add(new SelectItem(o.getIdDocumento(), o.getNombres() + " " + o.getApellidos())));
			}

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return odontologos;
	}

	public void setOdontologos(List<SelectItem> odontologos) {
		this.odontologos = odontologos;
	}

	public List<SelectItem> getConsultorios() {
		if (consultorios == null) {
			consultorios = new ArrayList<SelectItem>();

			try {
				List<Consultorio> lstConsultorios = IConsultasDAO.getConsultorios();
				if (lstConsultorios != null) {
					lstConsultorios.forEach(c -> consultorios.add(new SelectItem(c.getId(), c.getNombre())));

				}

			} catch (Exception e) {
				IConstantes.log.error(e, e);
			}
		}
		return consultorios;
	}

	public void setConsultorios(List<SelectItem> consultorios) {
		this.consultorios = consultorios;
	}

	public AdministrarSesionCliente getAdministrarSesionCliente() {
		return administrarSesionCliente;
	}

	public void setAdministrarSesionCliente(AdministrarSesionCliente administrarSesionCliente) {
		this.administrarSesionCliente = administrarSesionCliente;
	}

	public List<Consentimiento> getConsentimientos() {
		if (this.consentimientos == null) {
			this.consentimientos = new ArrayList<Consentimiento>();

			try {
				this.consentimientos = IConsultasDAO.getConsentimientos();

			} catch (Exception e) {
				IConstantes.log.error(e, e);
			}
		}
		return consentimientos;
	}

	public void setConsentimientos(List<Consentimiento> consentimientos) {
		this.consentimientos = consentimientos;
	}

	public List<SelectItem> getItemsConsentimiento() {
		if (this.itemsConsentimiento == null) {
			getConsentimientos();
			this.itemsConsentimiento = new ArrayList<SelectItem>();
			this.itemsConsentimiento.add(new SelectItem("", "Cargar texto consentimiento..."));
			if (this.consentimientos != null) {
				this.consentimientos.forEach(c -> this.itemsConsentimiento.add(new SelectItem(c.getId(), c.getNombre())));

			}
		}
		return itemsConsentimiento;
	}

	public void setItemsConsentimiento(List<SelectItem> itemsConsentimiento) {

		this.itemsConsentimiento = itemsConsentimiento;
	}

	public Consentimiento getConsentimientoSeleccionado() {
		if (this.consentimientoSeleccionado == null)
			this.consentimientoSeleccionado = new Consentimiento();
		return consentimientoSeleccionado;
	}

	public void setConsentimientoSeleccionado(Consentimiento consentimientoSeleccionado) {
		this.consentimientoSeleccionado = consentimientoSeleccionado;
	}

	public String getFormaCambioFoto() {
		try {
			if (this.formaCambioFoto == null) {
				this.formaCambioFoto = "P";
				// Camara
			}

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
		return formaCambioFoto;
	}

	public void setFormaCambioFoto(String formaCambioFoto) {
		this.formaCambioFoto = formaCambioFoto;
	}

}
