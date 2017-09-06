package dental.modulos.personal;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dental.beans.Personal;
import dental.beans.Usuario;
import dental.generales.ConsultarFuncionesAPI;
import dental.generales.IConstantes;
import dental.modulos.IConsultasDAO;

@ManagedBean
@SessionScoped
public class AdministrarSesionCliente extends ConsultarFuncionesAPI implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8417107112347354338L;
	private Personal					personalSesion;
	private Personal					personal;

	public void init() {

	}

	// privados

	/**
	 * Obtiene validación de rol no admitido
	 * 
	 * @return pagina
	 */
	public String getNoRolAdmitido(String aInterfaz) {
		String pagina = null;
		int sw = 2;

		if (this.personalSesion != null && this.personalSesion.getId() != null) {

			sw = 0;

		}

		if (sw == 1) {
			pagina = IConstantes.PAGINA_HOME;

			this.mostrarMensajeGlobal("noPoseePrivilegiosSobreInterfaz", "advertencia");
			// Guarda el mensaje antes de redireccionar y lo muestra
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);

		} else if (sw == 2) {
			pagina = IConstantes.PAGINA_NO_LOGUEO;

			this.mostrarMensajeGlobal("noPoseePrivilegiosSobreInterfaz", "advertencia");
			// Guarda el mensaje antes de redireccionar y lo muestra
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);

		}

		return pagina;
	}

	/**
	 * Determina si un personal esta en sesión y lo deja o no acceder
	 * 
	 * @return pagina
	 */
	public String getNologueoPersonal() {
		String pagina = null;
		if (!(this.personalSesion != null && this.personalSesion.getId() != null)) {

			pagina = IConstantes.PAGINA_NO_LOGUEO;

			this.mostrarMensajeGlobal("noPoseePrivilegios", "advertencia");
			// Guarda el mensaje antes de redireccionar y lo muestra
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);

		}

		return pagina;
	}

	// publicos

	/**
	 * Cierra la sesión del administrador
	 */
	public String getCerrarSesion() {
		String pagina = IConstantes.PAGINA_NO_LOGUEO;
		this.personalSesion = null;

		// this.vistaLogueado = 0;

		this.mostrarMensajeGlobal("cierreSesionCorrecto", "exito");

		// Guarda el mensaje antes de redireccionar y lo muestra
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		return pagina;

	}

	public String getTipoUsuarioTexto(String aTipo) {
		if (aTipo != null) {
			if (aTipo.equals("A"))
				return "Administrador";

			if (aTipo.equals("O"))
				return "Odontólogo";

			if (aTipo.equals("X"))
				return "Auxiliar";

			if (aTipo.equals("S"))
				return "Secretaria";

			if (aTipo.equals("C"))
				return "Cliente";
		}

		return "Cliente";
	}

	/**
	 * Permite el acceso del personal
	 */
	public String accederPersonal() {
		String pagina = null;

		List<Usuario> administradores = null;

		try {

			if (this.personal != null && this.personal.getCorreoElectronico() != null && !this.personal.getCorreoElectronico().trim().equals("") && this.personal.getClave() != null && !this.personal.getClave().trim().equals("")) {

				Usuario admin = new Usuario();
				admin.setClave(this.personal.getClave());
				admin.setCorreoElectronico(this.personal.getCorreoElectronico().trim());
				admin.setEstado(IConstantes.ACTIVO);
				admin.setTipoAdministracion("TODOS_CON_LOGUEO");
				administradores = IConsultasDAO.getAdministradores(admin);

				if (administradores != null && administradores.size() > 0 && administradores.get(0) != null && administradores.get(0).getIdDocumento() != null && !administradores.get(0).getIdDocumento().trim().equals("")) {

					this.personalSesion = new Personal();
					this.personalSesion.setCorreoElectronico(administradores.get(0).getCorreoElectronico().trim());
					this.personalSesion.setNombres(administradores.get(0).getNombres().trim() + " " + administradores.get(0).getApellidos());
					this.personalSesion.setId(administradores.get(0).getIdDocumento().trim());
					this.personalSesion.setTipoAdministracion(administradores.get(0).getTipoAdministracion());
					this.mostrarMensajeGlobal("ingresoCorrecto", "exito");
					this.personal = null;

					pagina = IConstantes.PAGINA_HOME;

					// Guarda el mensaje antes de redireccionar y lo muestra
					FacesContext context = FacesContext.getCurrentInstance();
					context.getExternalContext().getFlash().setKeepMessages(true);

				} else {
					this.mostrarMensajeGlobal("noCoincideCredenciales", "advertencia");
				}

			}

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return pagina;
	}

	/**
	 * Obtiene los datos de sesion de un personal
	 * 
	 * @return personalSesion
	 */
	public Personal getPersonalSesion() {
		try {
			if (this.personalSesion == null) {
				this.personalSesion = new Personal();
			}
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
		return personalSesion;
	}

	/**
	 * Establece los datos de sesión de un personal
	 * 
	 * @param personalSesion
	 */
	public void setPersonalSesion(Personal personalSesion) {
		this.personalSesion = personalSesion;
	}

	/**
	 * Obtiene el personal que se logeua
	 * 
	 * @return personal
	 */
	public Personal getPersonal() {
		try {
			if (this.personal == null) {
				this.personal = new Personal();
			}
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
		return personal;
	}

	/**
	 * Establece el personal que se loguea
	 * 
	 * @param personal
	 */
	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

}
