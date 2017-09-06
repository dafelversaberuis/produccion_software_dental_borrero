package dental.modulos.atencionOdontologica;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DefaultScheduleEvent;
import dental.Conexion;
import dental.beans.Cita;
import dental.beans.ClaseAnamnesis;
import dental.beans.ClaseExamenClinico;
import dental.beans.ClaseHabitoOral;
import dental.beans.ClaseHigieneOral;
import dental.beans.Consulta;
import dental.beans.ConsultaAnamnesis;
import dental.beans.ConsultaExamenClinico;
import dental.beans.ConsultaHabitosOrales;
import dental.beans.ConsultaHigieneOral;
import dental.beans.DetalleTratamiento;
import dental.beans.Diente;
import dental.beans.DocumentoConsulta;
import dental.beans.DocumentoUsuario;
import dental.beans.EstadoDiente;
import dental.beans.Tratamiento;
import dental.beans.TratamientoUsuario;
import dental.beans.Usuario;

public interface IAtencionOdontologicaDAO {

	/**
	 * Obtiene el objeto de un anexo_historia a las condiciones enviadas como
	 * parámetro.
	 * 
	 * @param idAnexoHistoria:
	 *          objeto de la tabla para consultar por llave primaria
	 * @return objeto: objeto anexo_historia
	 * @throws Exception
	 */
	public static Usuario getUsuario(String idDocumento) throws Exception {
		List<Object> parametros = new ArrayList<Object>();
		Usuario usuario = new Usuario();
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT u.*  FROM usuario u " + "     WHERE  u.documento = ?");

			if (idDocumento != null) {
				parametros.add(idDocumento);
				rs = conexion.consultarBD(sql.toString(), parametros);
				if (rs.next()) {
					usuario.setModoEdicion(true);
					usuario.setIdDocumento((String) rs.getObject("documento"));
					usuario.setNombres((String) rs.getObject("nombres"));
					usuario.setApellidos((String) rs.getObject("apellidos"));
					usuario.setTelefono((String) rs.getObject("telefono"));
					usuario.setGenero((String) rs.getObject("genero"));
					usuario.setArchivo(rs.getBytes("archivo"));
					usuario.setDireccion((String) rs.getObject("direccion"));
					usuario.setEstadoCivil((String) rs.getObject("estado_civil"));
					usuario.setOcupacion((String) rs.getObject("ocupacion"));
					usuario.setCorreoElectronico((String) rs.getObject("correo_electronico"));
					usuario.setFechaNacimiento((Date) rs.getObject("fecha_nacimiento"));
					usuario.setAcudiente((String) rs.getObject("acudiente"));
					usuario.setEstado((String) rs.getObject("estado"));
					usuario.setClave((String) rs.getObject("clave"));
					usuario.setTipoAdministracion((String) rs.getObject("tipo_administracion"));
				}
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}
		return usuario;
	}

	/**
	 * Obtiene los usuarios dado parte del nombre o el documento.
	 */
	public static List<Usuario> getUsuariosSuggestion(String texto) throws Exception {

		List<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = new Usuario();
		List<Object> parametros = new ArrayList<Object>();
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			if (texto != null) {
				sql.append(" SELECT u.nombres,u.apellidos,u.documento,u.tipo_administracion  FROM usuario u" + " WHERE upper(u.nombres) LIKE ?  ");
				parametros.add(texto.toUpperCase() + "%");
				sql.append(" OR upper(u.apellidos) LIKE ?");
				parametros.add(texto.toUpperCase() + "%");
				sql.append(" OR u.documento LIKE ? ");
				parametros.add(texto.toUpperCase() + "%");
				rs = conexion.consultarBD(sql.toString(), parametros);
			}
			if (rs != null)
				while (rs.next()) {
					usuario = new Usuario();
					usuario.setNombres((String) rs.getObject("nombres"));
					usuario.setApellidos((String) rs.getObject("apellidos"));
					usuario.setIdDocumento((String) rs.getObject("documento"));
					usuario.setTipoAdministracion((String) rs.getObject("tipo_administracion"));
					usuarios.add(usuario);
				}

		} catch (Exception e) {
			System.out.println(e.toString());
			throw new Exception(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}
		return usuarios;
	}

	/**
	 * Obtiene los usuarios dado parte del nombre o el documento.
	 */
	public static List<Usuario> getUsuariosRol(String rol) throws Exception {

		List<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = new Usuario();
		List<Object> parametros = new ArrayList<Object>();
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			if (rol != null) {
				sql.append(" SELECT u.nombres,u.apellidos,u.documento  FROM usuario u" + " WHERE tipo_administracion LIKE ?  ");
				parametros.add(rol.toUpperCase());
				rs = conexion.consultarBD(sql.toString(), parametros);
			}
			if (rs != null)
				while (rs.next()) {
					usuario = new Usuario();
					usuario.setNombres((String) rs.getObject("nombres"));
					usuario.setApellidos((String) rs.getObject("apellidos"));
					usuario.setIdDocumento((String) rs.getObject("documento"));
					usuarios.add(usuario);
				}

		} catch (Exception e) {
			System.out.println(e.toString());
			throw new Exception(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}
		return usuarios;
	}

	public static List<DocumentoUsuario> getDocumentosUsuario(DocumentoUsuario documento, Usuario usuario) throws Exception {

		List<DocumentoUsuario> documentos = new ArrayList<DocumentoUsuario>();
		List<Object> parametros = new ArrayList<Object>();
		DocumentoUsuario doc = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT d.*" + "     FROM documentos_usuario d" + "     WHERE 1=1");

			if (documento != null && documento.getId() != null) {
				sql.append("  AND d.id = ?");
				parametros.add(documento.getId());
			}
			if (usuario != null && usuario.getIdDocumento() != null) {
				sql.append("  AND d.documento = ?");
				parametros.add(usuario.getIdDocumento());
			}

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				doc = new DocumentoUsuario();
				doc.setId((Integer) rs.getObject("id"));
				doc.setNombre((String) rs.getObject("nombre"));
				doc.setDocumento((String) rs.getObject("documento"));
				documentos.add(doc);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}

		return documentos;

	}

	/**
	 * Obtiene el adjunto de un documento si éste existe
	 * 
	 * @param aDocumento
	 * @return documento
	 * @throws Exception
	 */
	public static DocumentoUsuario getAdjuntoDocumentoUsuario(DocumentoUsuario aDocumento) throws Exception {

		List<Object> prametros = new ArrayList<Object>();
		DocumentoUsuario documento = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT d.archivo, d.content_type, d.extension_archivo" + "   FROM documentos_usuario d" + "	  WHERE 1=1 ");

			if (aDocumento != null && aDocumento.getId() != null) {
				sql.append("  AND d.id =  ? ");
				prametros.add(aDocumento.getId());
			}
			rs = conexion.consultarBD(sql.toString(), prametros);

			if (rs.next()) {

				documento = new DocumentoUsuario();
				documento.setArchivo(rs.getBytes("archivo"));
				documento.setContentType((String) rs.getObject("content_type"));
				documento.setExtensionArchivo((String) rs.getObject("extension_archivo"));
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return documento;

	}

	public static List<Consulta> getConsultasUsuario(Consulta consulta, Usuario usuario) throws Exception {

		List<Consulta> consultasUsuario = new ArrayList<Consulta>();
		List<Object> parametros = new ArrayList<Object>();
		Consulta consultaUsuario = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		Boolean modoConsulta = false;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT c.*" + "     FROM consulta c" + "     WHERE 1=1");

			if (consulta != null && consulta.getId() != null) {
				sql.append("  AND c.id = ?");
				parametros.add(consulta.getId());
			}
			if (usuario != null && usuario.getIdDocumento() != null) {
				sql.append("  AND c.documento = ?");
				parametros.add(usuario.getIdDocumento());
			}

			sql.append("  ORDER BY c.fecha_consulta DESC ");

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				consultaUsuario = new Consulta();
				if (!modoConsulta) { // Solo puede editar el primer elemento
					consultaUsuario.setModoConsulta(false);
					modoConsulta = true;
				} else
					consultaUsuario.setModoConsulta(true);

				consultaUsuario.setId((Integer) rs.getObject("id"));
				consultaUsuario.setDocumento((String) rs.getObject("documento"));
				consultaUsuario.setMotivoConsulta((String) rs.getObject("motivo_consulta"));
				consultaUsuario.setHistoriaEnfermedadActual((String) rs.getObject("historia_enfermedad_actual"));
				consultaUsuario.setObservaciones((String) rs.getObject("observaciones"));
				consultaUsuario.setObservacionesOdontograma((String) rs.getObject("observaciones_odontograma"));
				consultaUsuario.setObservacionAutomatica((String) rs.getObject("observacion_auto_odontograma"));
				consultaUsuario.setDiagnostico((String) rs.getObject("diagnostico"));
				consultaUsuario.setFechaConsulta((Date) rs.getObject("fecha_consulta"));
				consultaUsuario.setConsultaEscaneada(rs.getString("consulta_escaneada").equals("t"));
				consultasUsuario.add(consultaUsuario);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}

		return consultasUsuario;

	}

	public static List<ConsultaAnamnesis> getAnamnesisConsulta(Consulta consulta) throws Exception {
		List<ConsultaAnamnesis> lstAnamnesis = new ArrayList<ConsultaAnamnesis>();
		List<Object> parametros = new ArrayList<Object>();
		ConsultaAnamnesis anamnesis = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT a.*,ca.* " + "     FROM consulta_anamnesis a, clases_anamnesis ca" + "     WHERE a.id_anamnesis = ca.id");

			if (consulta != null && consulta.getId() != null) {
				sql.append("  AND a.id_consulta = ?");
				parametros.add(consulta.getId());
			}
			sql.append("  ORDER BY ca.id ");

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {

				anamnesis = new ConsultaAnamnesis(new ClaseAnamnesis((Integer) rs.getObject("id"), (String) rs.getObject("nombre"), (String) rs.getObject("alerta")));
				anamnesis.setConsulta(consulta);
				anamnesis.setValor(rs.getString("valor").equals("t"));
				lstAnamnesis.add(anamnesis);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}

		return lstAnamnesis;

	}

	public static List<ConsultaHabitosOrales> getHabitosConsulta(Consulta consulta) throws Exception {
		List<ConsultaHabitosOrales> lstHabitosOrales = new ArrayList<ConsultaHabitosOrales>();
		List<Object> parametros = new ArrayList<Object>();
		ConsultaHabitosOrales habito = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT * " + "     FROM clases_habito_oral h, consulta_habitos_orales ch" + " 	WHERE ch.id_habito_oral = h.id");

			if (consulta != null && consulta.getId() != null) {
				sql.append("  AND ch.id_consulta = ?");
				parametros.add(consulta.getId());
			}
			sql.append("  ORDER BY h.id ");

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {

				habito = new ConsultaHabitosOrales(new ClaseHabitoOral((Integer) rs.getObject("id"), (String) rs.getObject("nombre")));
				habito.setConsulta(consulta);
				habito.setValor(rs.getString("valor").equals("t"));
				lstHabitosOrales.add(habito);
			}
		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}
		return lstHabitosOrales;
	}

	public static List<ConsultaHigieneOral> getHigieneConsulta(Consulta consulta) throws Exception {
		List<ConsultaHigieneOral> lstHigienesOrales = new ArrayList<ConsultaHigieneOral>();
		List<Object> parametros = new ArrayList<Object>();
		ConsultaHigieneOral higiene = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT * " + "     FROM clases_higiene_oral h, consulta_higiene_oral ch" + " 	WHERE ch.id_higiene_oral = h.id");

			if (consulta != null && consulta.getId() != null) {
				sql.append("  AND ch.id_consulta = ?");
				parametros.add(consulta.getId());
			}
			sql.append("  ORDER BY h.id ");
			rs = conexion.consultarBD(sql.toString(), parametros);
			while (rs.next()) {
				higiene = new ConsultaHigieneOral(new ClaseHigieneOral((Integer) rs.getObject("id"), (String) rs.getObject("nombre")));
				higiene.setConsulta(consulta);
				higiene.setValor(rs.getString("valor").equals("t"));
				lstHigienesOrales.add(higiene);
			}
		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return lstHigienesOrales;
	}

	public static List<ConsultaExamenClinico> getExamenesConsulta(Consulta consulta) throws Exception {
		List<ConsultaExamenClinico> lstExamenesClinicos = new ArrayList<ConsultaExamenClinico>();
		List<Object> parametros = new ArrayList<Object>();
		ConsultaExamenClinico examen = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT * " + "     FROM clases_examen_clinico e, consulta_examen_clinico ce" + " 	WHERE ce.id_examen_clinico = e.id");

			if (consulta != null && consulta.getId() != null) {
				sql.append("  AND ce.id_consulta = ?");
				parametros.add(consulta.getId());
			}
			sql.append("  ORDER BY e.id ");
			rs = conexion.consultarBD(sql.toString(), parametros);
			while (rs.next()) {
				examen = new ConsultaExamenClinico(new ClaseExamenClinico((Integer) rs.getObject("id"), (String) rs.getObject("nombre")));
				examen.setConsulta(consulta);
				examen.setValor(rs.getString("valor").equals("t"));
				lstExamenesClinicos.add(examen);
			}
		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return lstExamenesClinicos;
	}

	public static List<DocumentoConsulta> getDocumentosConsulta(Consulta consulta) throws Exception {

		List<DocumentoConsulta> documentos = new ArrayList<DocumentoConsulta>();
		List<Object> parametros = new ArrayList<Object>();
		DocumentoConsulta doc = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT d.* FROM documentos_consulta d WHERE 1=1");

			if (consulta != null && consulta.getId() != null) {
				sql.append("  AND d.id_consulta = ?");
				parametros.add(consulta.getId());
				rs = conexion.consultarBD(sql.toString(), parametros);
				while (rs.next()) {
					doc = new DocumentoConsulta();
					doc.setId((Integer) rs.getObject("id"));
					doc.setNombre((String) rs.getObject("nombre"));
					doc.setConsulta(consulta);
					documentos.add(doc);
				}
			}
		} catch (Exception e) {
			throw new Exception(e);

		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}

		return documentos;

	}

	/**
	 * Obtiene el adjunto de un documento si éste existe
	 * 
	 * @param aDocumento
	 * @return documento
	 * @throws Exception
	 */
	public static DocumentoConsulta getAdjuntoDocumentoConsulta(DocumentoConsulta aDocumento) throws Exception {

		List<Object> prametros = new ArrayList<Object>();
		DocumentoConsulta documento = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT d.archivo, d.content_type, d.extension_archivo" + "   FROM documentos_consulta d" + "	  WHERE 1=1 ");

			if (aDocumento != null && aDocumento.getId() != null) {
				sql.append("  AND d.id =  ? ");
				prametros.add(aDocumento.getId());
			}
			rs = conexion.consultarBD(sql.toString(), prametros);

			if (rs.next()) {

				documento = new DocumentoConsulta();
				documento.setArchivo(rs.getBytes("archivo"));
				documento.setContentType((String) rs.getObject("content_type"));
				documento.setExtensionArchivo((String) rs.getObject("extension_archivo"));
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return documento;

	}

	public static List<Diente> getOdontogramaConsulta(Consulta consulta) throws Exception {
		List<Diente> dientes = new ArrayList<Diente>();
		List<Object> parametros = new ArrayList<Object>();
		Diente diente = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT * " + "     FROM diente d ");

			if (consulta != null && consulta.getId() != null) {
				sql.append("  WHERE d.id_consulta = ?");
				// System.out.println(sql);
				parametros.add(consulta.getId());
				rs = conexion.consultarBD(sql.toString(), parametros);

				while (rs.next()) {
					diente = new Diente((Integer) rs.getObject("cuadrante"), (Integer) rs.getObject("posicion_odontograma"), true);
					diente.setConsulta(consulta);
					diente.setId((Integer) rs.getObject("id"));
					diente.setDescripcionGeneral((String) rs.getObject("descripcion_general"));
					diente.setEstadoGeneral((Integer) rs.getObject("estado_general"));
					diente.setDescripcionOclusal((String) rs.getObject("descripcion_oclusal"));
					diente.setEstadoOclusal((Integer) rs.getObject("estado_oclusal"));
					diente.setDescripcionVestibular((String) rs.getObject("descripcion_vestibular"));
					diente.setEstadoVestibular((Integer) rs.getObject("estado_vestibular"));
					diente.setDescripcionPalatino((String) rs.getObject("descripcion_palatino"));
					diente.setEstadoPalatino((Integer) rs.getObject("estado_palatino"));
					diente.setDescripcionDistal((String) rs.getObject("descripcion_distal"));
					diente.setEstadoDistal((Integer) rs.getObject("estado_distal"));
					diente.setDescripcionEsmalte((String) rs.getObject("descripcion_esmalte"));
					diente.setEstadoEsmalte((Integer) rs.getObject("estado_esmalte"));
					diente.setImagenDiente(rs.getBytes("imagen_diente"));
					dientes.add(diente);
				}
			}
		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return dientes;
	}

	public static List<TratamientoUsuario> getTratamientosUsuario(TratamientoUsuario tratamiento, Usuario usuario) throws Exception {

		List<TratamientoUsuario> tratamientosUsuario = new ArrayList<TratamientoUsuario>();
		List<Object> parametros = new ArrayList<Object>();
		TratamientoUsuario tratamientoUsuario = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT t.*" + "     FROM tratamiento_usuario t" + "     WHERE 1=1");

			if (tratamiento != null && tratamiento.getId() != null) {
				sql.append("  AND t.id = ?");
				parametros.add(tratamiento.getId());
			}
			if (usuario != null && usuario.getIdDocumento() != null) {
				sql.append("  AND t.documento = ?");
				parametros.add(usuario.getIdDocumento());
			}

			sql.append("  ORDER BY t.fecha_inicio DESC ");

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				tratamientoUsuario = new TratamientoUsuario();
				tratamientoUsuario.setId((Integer) rs.getObject("id"));
				tratamientoUsuario.setDocumento((String) rs.getObject("documento"));
				tratamientoUsuario.setObservaciones((String) rs.getObject("observaciones"));
				tratamientoUsuario.setEstado((String) rs.getObject("estado"));
				tratamientoUsuario.setFirma((String) rs.getObject("firma"));
				tratamientoUsuario.setTextoConsentimiento((String) rs.getObject("texto_consentimiento"));
				tratamientoUsuario.setValorTratamiento((BigDecimal) rs.getObject("valor_tratamiento"));
				tratamientoUsuario.setSaldo((BigDecimal) rs.getObject("saldo"));
				tratamientoUsuario.setDescuento((BigDecimal) rs.getObject("descuento"));
				tratamientoUsuario.setFechaInicio((Date) rs.getObject("fecha_inicio"));
				tratamientoUsuario.setArchivo(rs.getBytes("archivo"));
				tratamientoUsuario.setModoEdicion(true);
				if (tratamientoUsuario.getFirma() != null && !tratamientoUsuario.getFirma().equals(""))
					tratamientoUsuario.setModoEdicionLimitada(true);
				tratamientosUsuario.add(tratamientoUsuario);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}

		return tratamientosUsuario;

	}

	public static List<DetalleTratamiento> getDetalleTratamiento(TratamientoUsuario tratamientoUsuario, DetalleTratamiento detalle) throws Exception {

		List<DetalleTratamiento> detallesTratamiento = new ArrayList<DetalleTratamiento>();
		List<Object> parametros = new ArrayList<Object>();
		DetalleTratamiento detalleTratamiento = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT dt.*, t.nombre" + "     FROM detalle_tratamiento dt,tratamiento t" + "     WHERE  dt.tratamiento = t.id");

			if (tratamientoUsuario != null && tratamientoUsuario.getId() != null) {
				sql.append("  AND dt.tratamiento_usuario = ?");
				parametros.add(tratamientoUsuario.getId());
			}
			if (detalle != null && detalle.getTratamiento() != null && detalle.getTratamientoUsuario() != null) {
				sql.append("  AND dt.tratamiento_usuario = ?");
				parametros.add(detalle.getTratamientoUsuario().getId());
				sql.append("  AND dt.tratamiento = ?");
				parametros.add(detalle.getTratamiento().getId());
			}

			sql.append("  ORDER BY dt.valor_total DESC ");

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				detalleTratamiento = new DetalleTratamiento();
				detalleTratamiento.setTratamiento(new Tratamiento((Integer) rs.getObject("tratamiento"), (String) rs.getObject("nombre")));
				detalleTratamiento.setTratamientoUsuario(new TratamientoUsuario((Integer) rs.getObject("tratamiento_usuario")));
				detalleTratamiento.setValorUnitario((BigDecimal) rs.getObject("valor_unitario"));
				detalleTratamiento.setValorTotal((BigDecimal) rs.getObject("valor_total"));
				detalleTratamiento.setCantidad((BigDecimal) rs.getObject("cantidad"));
				detalleTratamiento.setEstado((String) rs.getObject("estado"));
				detalleTratamiento.setModoEdicion(true);
				detallesTratamiento.add(detalleTratamiento);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}

		return detallesTratamiento;

	}

	// obtiene la agenda especificada
	public static List<Cita> getAgenda(Date fechaInicio, Date fechaFin, Cita pCita) throws Exception {

		List<Cita> lstCitas = new ArrayList<Cita>();
		List<Object> parametros = new ArrayList<Object>();
		Cita cita = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT c.*,u.nombres,u.apellidos FROM cita c, usuario u" + "     WHERE  c.paciente=u.documento");

			if (fechaInicio != null) {
				sql.append("  AND c.fecha_inicio >= ?");
				parametros.add(fechaInicio);
			}
			if (fechaFin != null) {
				sql.append("  AND c.fecha_fin <= ?");
				parametros.add(fechaFin);
			}
			if (pCita != null) {
				if (pCita.getPaciente() != null && pCita.getPaciente().getIdDocumento() != null) {
					sql.append("  AND c.paciente=? ");
					parametros.add(pCita.getPaciente().getIdDocumento());
				}
				if (pCita.getTratamiento() != null) {
					sql.append("  AND c.tratamiento=? ");
					parametros.add(pCita.getTratamiento());
				}

			}
			sql.append(" ORDER BY fecha_inicio DESC ");
			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				cita = new Cita();

				cita.setId((Integer) rs.getObject("id"));
				cita.setProfesional(new Usuario((String) rs.getObject("profesional"), ""));
				cita.setPaciente(new Usuario((String) rs.getObject("paciente"), (String) rs.getObject("nombres") + " " + (String) rs.getObject("apellidos")));
				cita.setEvent(new DefaultScheduleEvent(cita.getTituloCita(), (Date) rs.getObject("fecha_inicio"), (Date) rs.getObject("fecha_fin")));
				cita.setFechaInicio((Date) rs.getObject("fecha_inicio"));
				cita.setFechaFin((Date) rs.getObject("fecha_fin"));
				cita.setSaldo((BigDecimal) rs.getObject("saldo"));
				cita.setAbono((BigDecimal) rs.getObject("abono"));
				cita.setDetalleEvolucion((String) rs.getObject("detalle_evolucion"));
				cita.setDetalleEvolucionAutomatica((String) rs.getObject("detalle_evolucion_auto"));
				cita.setRecibo((String) rs.getObject("recibo"));
				cita.setConsulta((Integer) rs.getObject("id_consulta"));
				cita.setTratamiento((Integer) rs.getObject("tratamiento"));
				cita.setConsultorio((Integer) rs.getObject("consultorio"));
				cita.setEstado((String) rs.getObject("estado"));
				cita.setModoEdicion(true);
				lstCitas.add(cita);
			}
			// System.out.println("Total de citas:"+lstCitas.size());

		} catch (Exception e) {
			throw new Exception(e);

		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}

		return lstCitas;

	}

	// obtiene la agenda especificada
	public static List<Cita> getAgendaUsuario(Date fechaInicio, Date fechaFin, Cita pCita) throws Exception {

		List<Cita> lstCitas = new ArrayList<Cita>();
		List<Object> parametros = new ArrayList<Object>();
		Cita cita = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT c.*,u.nombres,u.apellidos FROM cita c, usuario u" + "     WHERE  c.paciente=u.documento");

			if (fechaInicio != null) {
				sql.append("  AND c.fecha_inicio >= ?");
				parametros.add(fechaInicio);
			}
			if (fechaFin != null) {
				sql.append("  AND c.fecha_fin <= ?");
				parametros.add(fechaFin);
			}

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				cita = new Cita();

				cita.setId((Integer) rs.getObject("id"));
				cita.setProfesional(new Usuario((String) rs.getObject("profesional"), ""));
				cita.setPaciente(new Usuario((String) rs.getObject("paciente"), (String) rs.getObject("nombres") + " " + (String) rs.getObject("apellidos")));
				cita.setEvent(new DefaultScheduleEvent(cita.getTituloCita(), (Date) rs.getObject("fecha_inicio"), (Date) rs.getObject("fecha_fin")));

				cita.setConsultorio((Integer) rs.getObject("consultorio"));
				cita.setEstado((String) rs.getObject("estado"));
				cita.setModoEdicion(true);
				lstCitas.add(cita);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}

		return lstCitas;

	}

}
