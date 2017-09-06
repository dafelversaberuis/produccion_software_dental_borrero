package dental.modulos.personal;



import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dental.Conexion;
import dental.beans.Usuario;
import dental.generales.ConsultarFuncionesAPI;
import dental.generales.IConstantes;
import dental.generales.IEmail;
import dental.modulos.IConsultasDAO;

@ManagedBean
@ViewScoped
public class AdministrarPersonal extends ConsultarFuncionesAPI implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2221958861238708985L;

	private Usuario					personal;
	private Usuario					personalTransaccion;
	private List<Usuario>		    administradores;
	

	// privados

	/**
	 * Obtiene una clave aleatoria numérica de n dígitos
	 * 
	 * @return clave
	 */
	public String getClaveAleatoria() {
		String clave = "";
		int n = 0;
		try {
			for (int i = 1; i <= IConstantes.NUMERO_DIGITOS_CLAVE_ALEATORIA.intValue(); i++) {
				n = (int) (10.0 * Math.random()) + 0;
				clave = clave + String.valueOf(n);

			}
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
		return clave;

	}

	// privados

	/**
	 * Valida un administrador
	 * 
	 * @param aTransaccion
	 * @return ok
	 */
	private boolean isValidoAdministrador(String aTransaccion) {
		boolean ok = true;

		if (aTransaccion.equals("C")) {
			if (this.administradores != null && this.administradores.size() > 0 && this.administradores.stream().anyMatch(i -> i.getCorreoElectronico().trim().toLowerCase().equals(this.personal.getCorreoElectronico().trim().toLowerCase()))) {
				ok = false;
				this.mostrarMensajeGlobal("correoExistenteAdministrador", "advertencia");
			}
			if (this.isVacio(this.personal.getNombres())) {
				ok = false;
				this.mostrarMensajeGlobal("nombresVacios", "advertencia");
			}

			if (this.isVacio(this.personal.getApellidos())) {
				ok = false;
				this.mostrarMensajeGlobal("apellidosVacios", "advertencia");
			}

			if (this.isVacio(this.personal.getCorreoElectronico())) {
				ok = false;
				this.mostrarMensajeGlobal("correoVacio", "advertencia");
			}
			

		} else {

			if (this.administradores != null && this.administradores.size() > 0 && this.administradores.stream().anyMatch
					(i -> i.getIdDocumento() != this.personalTransaccion.getIdDocumento() && i.getCorreoElectronico().trim().toLowerCase().equals(this.personalTransaccion.getCorreoElectronico().trim().toLowerCase()))) {
				ok = false;
				this.mostrarMensajeGlobal("correoExistenteAdministrador", "advertencia");
			}
			if (this.isVacio(this.personalTransaccion.getNombres())) {
				ok = false;
				this.mostrarMensajeGlobal("nombresVacios", "advertencia");
			}

			if (this.isVacio(this.personalTransaccion.getApellidos())) {
				ok = false;
				this.mostrarMensajeGlobal("apellidosVacios", "advertencia");
			}

			if (this.isVacio(this.getPersonalTransaccion().getCorreoElectronico())) {
				ok = false;
				this.mostrarMensajeGlobal("correoVacio", "advertencia");
			}
		

		}

		return ok;
	}

	// publicos

	/**
	 * Crea un nuevo administrador del software
	 */
	public void crearAdministrador() {
		Conexion conexion = new Conexion();

		try {
			if (isValidoAdministrador("C")) {
				conexion.setAutoCommitBD(false);

				this.personal.setEstado(IConstantes.ACTIVO);
				this.personal.setNombres(this.getSinEspacios(this.personal.getNombres()));
				this.personal.setApellidos(this.getSinEspacios(this.personal.getApellidos()));
				this.personal.setCorreoElectronico(this.personal.getCorreoElectronico().trim());
				this.personal.setClave(this.getClaveAleatoria());

				this.personal.getCamposBD();

				conexion.insertarBD(this.personal.getEstructuraTabla().getTabla(), this.personal.getEstructuraTabla().getPersistencia());
				conexion.commitBD();

				this.mostrarMensajeGlobal("creacionExitosa", "exito");
				//Se envía clave a todos menos a los pacientes
				if(!this.personal.getTipoAdministracion().equals("C")){
					this.mostrarMensajeGlobalPersonalizado(this.getMensaje("claveAleatoria", this.getPersonal().getClave()), "exito");
					IEmail.enviarCorreo(this.getMensaje("mensajeClaveAleatoria", this.personal.getNombres() + " " + this.personal.getApellidos(), this.personal.getClave()), this.getMensaje("asuntoClaveAleatoria"), this.personal.getCorreoElectronico());
					this.mostrarMensajeGlobalPersonalizado(this.getMensaje("claveCorreoExitoso", this.getPersonal().getCorreoElectronico()), "exito");
				}
				// reseteo de variables
				this.personal = null;
				this.getPersonal();
				this.administradores = null;
				this.getAdministradores();
			}

		} catch (Exception e) {
			conexion.rollbackBD();
			this.mostrarMensajeGlobal("transaccionFallida", "error");
		} finally {
			conexion.cerrarConexion();
		}

	}

	/**
	 * Genera una nueva clave aleatoria para el administrador
	 */
	public void generarClaveAdministrador() {

		Conexion conexion = new Conexion();

		try {
			boolean ok = true;
			if (this.personalTransaccion != null && this.personalTransaccion.gettTipoClave() != null && this.personalTransaccion.gettTipoClave().equals("A")) {

				this.personalTransaccion.setClave(this.getClaveAleatoria());

			} else {

				if (this.isVacio(this.personalTransaccion.getClave())) {
					ok = false;
				}

			}
			if (ok) {
				conexion.setAutoCommitBD(false);

				this.personalTransaccion.getCamposBD();

				conexion.actualizarBD(this.personalTransaccion.getEstructuraTabla().getTabla(), this.personalTransaccion.getEstructuraTabla().getPersistencia(), this.personalTransaccion.getEstructuraTabla().getLlavePrimaria(), null);
				conexion.commitBD();

				this.mostrarMensajeGlobalPersonalizado(this.getMensaje("claveAleatoria", this.personalTransaccion.getClave()), "exito");

				IEmail.enviarCorreo(this.getMensaje("mensajeClaveAleatoria", this.personalTransaccion.getNombres() + " " + this.personalTransaccion.getApellidos(), this.personalTransaccion.getClave()), this.getMensaje("asuntoClaveAleatoria"), this.personalTransaccion.getCorreoElectronico());
				this.mostrarMensajeGlobalPersonalizado(this.getMensaje("claveCorreoExitoso", this.personalTransaccion.getCorreoElectronico()), "exito");

				this.cerrarModal("panelClaveAdministrador");

				// reseteo de variables
				this.personalTransaccion = null;
				this.getPersonalTransaccion();
				this.administradores = null;
				this.getAdministradores();

			} else {

				this.mostrarMensajeGlobal("claveEnBlanco", "error");
			}

		} catch (Exception e) {
			conexion.rollbackBD();
			this.mostrarMensajeGlobal("transaccionFallida", "error");
		} finally {
			conexion.cerrarConexion();
		}

	}

	/**
	 * Edita un registro de administrador de software
	 */
	public void editarAdministrador() {
		Conexion conexion = new Conexion();

		try {
			if (isValidoAdministrador("E")) {
				conexion.setAutoCommitBD(false);

				this.personalTransaccion.setNombres(this.getSinEspacios(this.personalTransaccion.getNombres()));
				this.personalTransaccion.setApellidos(this.getSinEspacios(this.personalTransaccion.getApellidos()));

				this.personalTransaccion.getCamposBD();
				conexion.actualizarBD(this.personalTransaccion.getEstructuraTabla().getTabla(), this.personalTransaccion.getEstructuraTabla().getPersistencia(), this.personalTransaccion.getEstructuraTabla().getLlavePrimaria(), null);
				conexion.commitBD();
				this.mostrarMensajeGlobal("edicionExitosa", "exito");
				this.mostrarMensajeGlobal("algunosCambios", "advertencia");
				this.cerrarModal("panelEdicionAdministrador");

				// reseteo de variables
				this.personalTransaccion = null;
				this.getPersonalTransaccion();
				this.administradores = null;
				this.getAdministradores();
			}

		} catch (Exception e) {
			conexion.rollbackBD();
			this.mostrarMensajeGlobal("transaccionFallida", "error");
		} finally {
			conexion.cerrarConexion();
		}

	}

	/**
	 * Elimina un registro de adminiistador
	 */
	public void eliminarAdministrador() {

		Conexion conexion = new Conexion();
		try {

			conexion.setAutoCommitBD(false);
			this.personalTransaccion.getCamposBD();
			conexion.eliminarBD(this.personalTransaccion.getEstructuraTabla().getTabla(), this.personalTransaccion.getEstructuraTabla().getLlavePrimaria());
			conexion.commitBD();
			this.mostrarMensajeGlobal("eliminacionExitosa", "exito");

		} catch (Exception e) {
			conexion.rollbackBD();
			this.mostrarMensajeGlobal("transaccionFallida", "error");
			this.mostrarMensajeGlobal("eliminacionFallida", "error");

		} finally {
			conexion.cerrarConexion();
		}

		// reseteo de variables
		this.personalTransaccion = null;
		this.administradores = null;
		this.getAdministradores();

	}

	/**
	 * Este método borra el formulario de creación de un administrador
	 */
	public void cancelarAdministrador() {

		try {
			this.personal = new Usuario();

			this.administradores = null;
			this.getAdministradores();
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

	}

	/**
	 * Este método borra el formulario de edición de un administrador en
	 * transacción
	 */
	public void cancelarAdministradorTransaccion(String aVista) {
		try {

			this.personalTransaccion = new Usuario();
			this.administradores = null;
			this.getAdministradores();

			if (aVista != null && aVista.equals("MODAL_EDITAR_ADMINISTRADOR")) {
				this.cerrarModal("panelEdicionAdministrador");

			} else if (aVista != null && aVista.equals("MODAL_CLAVE_ADMINISTRADOR")) {
				this.cerrarModal("panelClaveAdministrador");

			} else if (aVista != null && aVista.equals("MODAL_ELIMINAR_ADMINISTRADOR")) {
				this.cerrarModal("panelEliminacionAdministrador");

			}

		} catch (Exception e) {

			IConstantes.log.error(e, e);

		}

	}

	/**
	 * Asigna un administrador para realizar una acción
	 * 
	 * @param aAgrupador
	 * @param aVista
	 */
	public void asignarAdministrador(Usuario aPersonal, String aVista) {

		try {

			this.personalTransaccion = aPersonal;

			if (aVista != null && aVista.equals("MODAL_EDITAR_ADMINISTRADOR")) {
				this.abrirModal("panelEdicionAdministrador");

			} else if (aVista != null && aVista.equals("MODAL_CLAVE_ADMINISTRADOR")) {
				this.abrirModal("panelClaveAdministrador");

			} else {

				this.abrirModal("panelEliminacionAdministrador");

			}

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

	}

	/**
	 * Obtiene un listado de administradores
	 * 
	 * @return administradores
	 */
	public List<Usuario> getAdministradores() {
		try {
			if (this.administradores == null) {

				this.administradores = IConsultasDAO.getAdministradores(null);

			}
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
		return administradores;
	}

	// get/sets

	public Usuario getPersonal() {
		try {
			if (this.personal == null) {
				this.personal = new Usuario();
				this.personal.setTipoAdministracion("O");

			}
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
		return personal;
	}

	public void setPersonal(Usuario personal) {
		this.personal = personal;
	}

	public Usuario getPersonalTransaccion() {
		return personalTransaccion;
	}

	public void setPersonalTransaccion(Usuario personalTransaccion) {
		this.personalTransaccion = personalTransaccion;
	}

	public void setAdministradores(List<Usuario> administradores) {
		this.administradores = administradores;
	}

}
