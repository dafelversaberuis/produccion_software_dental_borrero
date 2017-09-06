package dental.modulos.atencionOdontologica;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dental.Conexion;
import dental.beans.FirmaDispositivoMovil;
import dental.generales.ConsultarFuncionesAPI;
import dental.generales.IConstantes;
import dental.modulos.IConsultasDAO;

@ManagedBean
@ViewScoped
public class FirmarDispositivo extends ConsultarFuncionesAPI implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8282922302845589971L;

	FirmaDispositivoMovil			firmaDispositivoMovil;

	/**
	 * Limpia la firma en el dispositivo
	 */
	public void limpiarFirma() {

		this.firmaDispositivoMovil.setFirma(null);
		this.mostrarMensajeGlobal("FIRMA LIMPIADA", "advertencia");
	}

	/**
	 * Guarda el registro de la firma
	 */
	public void guardar() {

		Conexion conexion = new Conexion();
		try {
			conexion.setAutoCommitBD(false);
			if (this.firmaDispositivoMovil.getDocumentoOdontologo() != null && !this.firmaDispositivoMovil.getDocumentoOdontologo().trim().equals("")) {
				FirmaDispositivoMovil temp = IConsultasDAO.getFirma(this.firmaDispositivoMovil.getDocumentoOdontologo());

				if (temp != null && temp.getDocumentoOdontologo() != null) {

					this.firmaDispositivoMovil.getCamposBD();
					conexion.actualizarBD(this.firmaDispositivoMovil.getEstructuraTabla().getTabla(), this.firmaDispositivoMovil.getEstructuraTabla().getPersistencia(), this.firmaDispositivoMovil.getEstructuraTabla().getLlavePrimaria(), null);

				} else {

					this.firmaDispositivoMovil.getCamposBD();
					conexion.insertarBD(this.firmaDispositivoMovil.getEstructuraTabla().getTabla(), this.firmaDispositivoMovil.getEstructuraTabla().getPersistencia());

				}
				conexion.commitBD();

				this.mostrarMensajeGlobal("FIRMA EXITOSA. POR FAVOR INDIQUELE AL ODONTOLOGO QUE LA LEA EN EL COMPUTADOR", "exito");
				this.firmaDispositivoMovil.setFirma(null);

			} else {

				this.mostrarMensajeGlobal("DEBE SELECCIONAR UN ODONTOLOGO", "advertencia");

			}

		} catch (Exception e) {
			conexion.rollbackBD();
			this.mostrarMensajeGlobal("transaccionFallida", "error");
			IConstantes.log.error(e, e);
		} finally {
			conexion.cerrarConexion();
		}

	}

	/**
	 * Obtiene instancia de objeto firma
	 * 
	 * @return firmaDispositivoMovil
	 */
	public FirmaDispositivoMovil getFirmaDispositivoMovil() {
		try {
			if (this.firmaDispositivoMovil == null) {
				this.firmaDispositivoMovil = new FirmaDispositivoMovil();
			}

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
		return firmaDispositivoMovil;
	}

	/**
	 * Establece firma de dispositivo firma
	 * 
	 * @param firmaDispositivoMovil
	 */
	public void setFirmaDispositivoMovil(FirmaDispositivoMovil firmaDispositivoMovil) {
		this.firmaDispositivoMovil = firmaDispositivoMovil;
	}

}
