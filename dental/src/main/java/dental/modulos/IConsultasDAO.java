package dental.modulos;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dental.Conexion;
import dental.beans.ClaseAnamnesis;
import dental.beans.ClaseExamenClinico;
import dental.beans.ClaseHabitoOral;
import dental.beans.ClaseHigieneOral;
import dental.beans.Consentimiento;
import dental.beans.Consultorio;
import dental.beans.EstadoDiente;
import dental.beans.FirmaDispositivoMovil;
import dental.beans.Tratamiento;
import dental.beans.Usuario;

public interface IConsultasDAO {
	
	
	
	/**
	 * Obtiene la firma almacenada
	 * 
	 * @param idDocumento
	 * @return firma
	 * @throws Exception
	 */
	public static FirmaDispositivoMovil getFirma(String idDocumento) throws Exception {
		List<Object> parametros = new ArrayList<Object>();
		FirmaDispositivoMovil firma = new FirmaDispositivoMovil();
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT *  FROM firma_dispositivo_movil d WHERE documento_odontologo = ?");

			if (idDocumento != null) {
				parametros.add(idDocumento);
				rs = conexion.consultarBD(sql.toString(), parametros);
				if (rs.next()) {

					firma.setDocumentoOdontologo((String) rs.getObject("documento_odontologo"));
					firma.setFirma((String) rs.getObject("firma"));

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
		return firma;
	}
	
	

	/**
	 * Obtiene un listado de administradores del software
	 * 
	 * @param aPersonal
	 * @return administradores
	 * @throws Exception
	 */
	public static List<Usuario> getAdministradores(Usuario aPersonal) throws Exception {
		List<Usuario> administradores = new ArrayList<Usuario>();
		List<Object> prametros = new ArrayList<Object>();
		Usuario usuario = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT *");
			sql.append("  FROM usuario u");
			sql.append("  WHERE 1=1 ");

			if (aPersonal != null && aPersonal.getCorreoElectronico() != null && !aPersonal.getCorreoElectronico().trim().equals("")) {
				sql.append("  AND UPPER(u.correo_electronico) = ?");
				prametros.add(aPersonal.getCorreoElectronico().trim().toUpperCase());
			}

			if (aPersonal != null && aPersonal.getClave() != null && !aPersonal.getClave().trim().equals("")) {
				sql.append("  AND u.clave = ?");
				prametros.add(aPersonal.getClave().trim());
			}

			if (aPersonal != null && aPersonal.getEstado() != null && !aPersonal.getEstado().trim().equals("")) {
				sql.append("  AND u.estado = ?");
				prametros.add(aPersonal.getEstado().trim());
			}

			if (aPersonal != null && aPersonal.getTipoAdministracion() != null && !aPersonal.getTipoAdministracion().trim().equals("")) {

				if (aPersonal.getTipoAdministracion().trim().equals("TODOS_CON_LOGUEO")) {
					sql.append("  AND u.tipo_administracion <> 'C'");

				} else {
					sql.append("  AND u.tipo_administracion = ?");
					prametros.add(aPersonal.getTipoAdministracion().trim());
				}

			}

			sql.append("  ORDER BY nombres, apellidos");

			rs = conexion.consultarBD(sql.toString(), prametros);

			while (rs.next()) {
				usuario = new Usuario();
				usuario.setModoEdicion(true);
				usuario.setIdDocumento((String) rs.getObject("documento"));
				usuario.setNombres((String) rs.getObject("nombres"));
				usuario.setApellidos((String) rs.getObject("apellidos"));
				usuario.setTelefono((String) rs.getObject("telefono"));
				usuario.setCorreoElectronico((String) rs.getObject("correo_electronico"));
				usuario.setFechaNacimiento((Date) rs.getObject("fecha_nacimiento"));
				usuario.setEstado((String) rs.getObject("estado"));
				usuario.setClave((String) rs.getObject("clave"));
				usuario.setTipoAdministracion((String) rs.getObject("tipo_administracion"));
				administradores.add(usuario);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return administradores;

	}

	/**
	 * Este método obtiene las clases de higiene oral
	 * 
	 * @param aClaseHigieneOral
	 * @return clasesHiguiene
	 */
	public static List<ClaseHigieneOral> getClasesHigieneOral(ClaseHigieneOral aClaseHigieneOral) throws Exception {

		List<ClaseHigieneOral> clasesHigiene = new ArrayList<ClaseHigieneOral>();
		List<Object> parametros = new ArrayList<Object>();
		ClaseHigieneOral claseHigieneOral = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT a.*");
			sql.append("  FROM clases_higiene_oral a ");
			sql.append("  WHERE 1=1");

			if (aClaseHigieneOral != null && aClaseHigieneOral.getId() != null) {
				sql.append("  AND a.id = ?");
				parametros.add(aClaseHigieneOral.getId());
			}

			if (aClaseHigieneOral != null && aClaseHigieneOral.getNombre() != null && !aClaseHigieneOral.getNombre().trim().equals("")) {

			}

			sql.append("  ORDER BY a.id ");

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {

				claseHigieneOral = new ClaseHigieneOral();

				claseHigieneOral.setId((Integer) rs.getObject("id"));
				claseHigieneOral.setNombre((String) rs.getObject("nombre"));
				claseHigieneOral.setIndicativoVigencia((String) rs.getObject("indicativo_vigencia"));

				clasesHigiene.add(claseHigieneOral);

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}

		return clasesHigiene;

	}

	public static List<ClaseHabitoOral> getClasesHabitoOral(ClaseHabitoOral aClaseHabitoOral) throws Exception {

		List<ClaseHabitoOral> clasesHigiene = new ArrayList<ClaseHabitoOral>();
		List<Object> parametros = new ArrayList<Object>();
		ClaseHabitoOral claseHabitoOral = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT a.*");
			sql.append("  FROM clases_habito_oral a ");
			sql.append("  WHERE 1=1");

			if (aClaseHabitoOral != null && aClaseHabitoOral.getId() != null) {
				sql.append("  AND a.id = ?");
				parametros.add(aClaseHabitoOral.getId());
			}

			if (aClaseHabitoOral != null && aClaseHabitoOral.getNombre() != null && !aClaseHabitoOral.getNombre().trim().equals("")) {

			}

			sql.append("  ORDER BY a.id ");

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {

				claseHabitoOral = new ClaseHabitoOral();

				claseHabitoOral.setId((Integer) rs.getObject("id"));
				claseHabitoOral.setNombre((String) rs.getObject("nombre"));
				claseHabitoOral.setIndicativoVigencia((String) rs.getObject("indicativo_vigencia"));

				clasesHigiene.add(claseHabitoOral);

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}
		return clasesHigiene;

	}

	public static List<ClaseAnamnesis> getClasesAnamnesis(ClaseAnamnesis aClaseAnamnesis) throws Exception {

		List<ClaseAnamnesis> clasesAnamnesisL = new ArrayList<ClaseAnamnesis>();
		List<Object> parametros = new ArrayList<Object>();
		ClaseAnamnesis claseAnamnesis = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT a.*");
			sql.append("  FROM clases_anamnesis a ");
			sql.append("  WHERE 1=1");
			if (aClaseAnamnesis != null && aClaseAnamnesis.getId() != null) {
				sql.append("  AND a.id = ?");
				parametros.add(aClaseAnamnesis.getId());
			}

			if (aClaseAnamnesis != null && aClaseAnamnesis.getNombre() != null && !aClaseAnamnesis.getNombre().trim().equals("")) {

			}

			sql.append("  ORDER BY a.id ");

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {

				claseAnamnesis = new ClaseAnamnesis();

				claseAnamnesis.setId((Integer) rs.getObject("id"));
				claseAnamnesis.setNombre((String) rs.getObject("nombre"));
				claseAnamnesis.setIndicativoVigencia((String) rs.getObject("indicativo_vigencia"));
				claseAnamnesis.setAlerta((String) rs.getObject("alerta"));
				clasesAnamnesisL.add(claseAnamnesis);

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}

		return clasesAnamnesisL;

	}

	public static List<ClaseExamenClinico> getClasesExamenClinico(ClaseExamenClinico aClaseExamenClinico) throws Exception {

		List<ClaseExamenClinico> clasesExamenClinicoL = new ArrayList<ClaseExamenClinico>();
		List<Object> parametros = new ArrayList<Object>();
		ClaseExamenClinico claseExamenClinico = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT a.*");
			sql.append("  FROM clases_examen_clinico a ");
			sql.append("  WHERE 1=1");

			if (aClaseExamenClinico != null && aClaseExamenClinico.getId() != null) {
				sql.append("  AND a.id = ?");
				parametros.add(aClaseExamenClinico.getId());
			}

			if (aClaseExamenClinico != null && aClaseExamenClinico.getNombre() != null && !aClaseExamenClinico.getNombre().trim().equals("")) {

			}

			sql.append("  ORDER BY a.id ");

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {

				claseExamenClinico = new ClaseExamenClinico();

				claseExamenClinico.setId((Integer) rs.getObject("id"));
				claseExamenClinico.setNombre((String) rs.getObject("nombre"));
				claseExamenClinico.setIndicativoVigencia((String) rs.getObject("indicativo_vigencia"));

				clasesExamenClinicoL.add(claseExamenClinico);

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}

		return clasesExamenClinicoL;

	}

	public static List<EstadoDiente> getEstadosDiente(EstadoDiente estadoDiente) throws Exception {
		List<EstadoDiente> estadosDiente = new ArrayList<EstadoDiente>();
		List<Object> parametros = new ArrayList<Object>();
		EstadoDiente estado = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT e.*");
			sql.append("  FROM estado_diente e ");
			sql.append("  WHERE 1=1");
			if (estadoDiente != null && estadoDiente.getId() != null) {
				sql.append("  AND e.id = ?");
				parametros.add(estadoDiente.getId());
			}
			sql.append("  ORDER BY e.id ");
			rs = conexion.consultarBD(sql.toString(), parametros);
			while (rs.next()) {
				estado = new EstadoDiente();
				estado.setId((Integer) rs.getObject("id"));
				estado.setNombre((String) rs.getObject("nombre"));
				estado.setDescripcion((String) rs.getObject("descripcion"));
				estado.setEstado((String) rs.getObject("estado"));
				estado.setZona1(rs.getBytes("zona1"));
				estado.setZona2(rs.getBytes("zona2"));
				estado.setZona3(rs.getBytes("zona3"));
				estado.setZona4(rs.getBytes("zona4"));
				estado.setZona5(rs.getBytes("zona5"));
				estado.setZonaTotal(rs.getBytes("zona_total"));
				estado.setDienteCompleto(rs.getString("diente_completo").equals("t"));
				estadosDiente.add(estado);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}

		return estadosDiente;

	}

	// Busca si el estado del diente está asociado a un elemento
	public static Boolean buscarEstadoDiente(EstadoDiente estadoDiente) throws Exception {
		Boolean estadoEncontrado = false;
		List<Object> parametros = new ArrayList<Object>();
		Conexion conexion = new Conexion();
		Long total = 0L;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			if (estadoDiente != null && estadoDiente.getId() != null) {
				sql.append("  SELECT count(*) total FROM diente" + " WHERE estado_general=?");
				parametros.add(estadoDiente.getId());
				sql.append("  OR  estado_oclusal=?");
				parametros.add(estadoDiente.getId());
				sql.append("  OR  estado_vestibular=?");
				parametros.add(estadoDiente.getId());
				sql.append("  OR  estado_palatino=?");
				parametros.add(estadoDiente.getId());
				sql.append("  OR  estado_distal=?");
				parametros.add(estadoDiente.getId());
				sql.append("  OR  estado_esmalte=?");
				parametros.add(estadoDiente.getId());
				rs = conexion.consultarBD(sql.toString(), parametros);
				while (rs.next()) {
					total = (Long) rs.getObject("total");
					estadoEncontrado = total > 0;
				}

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

		return estadoEncontrado;

	}

	public static List<Tratamiento> getTratamientos(Tratamiento t) throws Exception {
		List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
		List<Object> parametros = new ArrayList<Object>();
		Tratamiento tratamiento = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT t.*");
			sql.append("  FROM tratamiento t ");
			sql.append("  WHERE 1=1");
			if (t != null && t.getId() != null) {
				sql.append("  AND t.id = ?");
				parametros.add(t.getId());
			}

			sql.append("  ORDER BY t.nombre ");
			rs = conexion.consultarBD(sql.toString(), parametros);
			while (rs.next()) {
				tratamiento = new Tratamiento();
				tratamiento.setId((Integer) rs.getObject("id"));
				tratamiento.setNombre((String) rs.getObject("nombre"));
				tratamiento.setDescripcion((String) rs.getObject("descripcion"));
				tratamiento.setEstado(rs.getString("estado").equals("t"));
				tratamiento.setValor((BigDecimal) rs.getObject("valor"));
				tratamiento.setImagen(rs.getBytes("imagen"));
				tratamiento.setModoEdicion(true);
				tratamientos.add(tratamiento);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}
		return tratamientos;
	}

	public static List<Consultorio> getConsultorios() throws Exception {
		List<Consultorio> consultorios = new ArrayList<Consultorio>();
		List<Object> parametros = new ArrayList<Object>();
		Consultorio consultorio = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT c.*");
			sql.append("  FROM consultorio c ");

			sql.append("  ORDER BY c.nombre ");
			rs = conexion.consultarBD(sql.toString(), parametros);
			while (rs.next()) {
				consultorio = new Consultorio();
				consultorio.setId((Integer) rs.getObject("id"));
				consultorio.setNombre((String) rs.getObject("nombre"));
				consultorio.setDescripcion((String) rs.getObject("descripcion"));
				consultorio.setModoEdicion(true);
				consultorios.add(consultorio);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}
		return consultorios;
	}
	
	public static List<Consentimiento> getConsentimientos() throws Exception {
		List<Consentimiento> consentimientos = new ArrayList<Consentimiento>();
		List<Object> parametros = new ArrayList<Object>();
		Consentimiento consentimiento = null;
		Conexion conexion = new Conexion();
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT c.*");
			sql.append("  FROM consentimiento c ");

			sql.append("  ORDER BY c.nombre ");
			rs = conexion.consultarBD(sql.toString(), parametros);
			while (rs.next()) {
				consentimiento = new Consentimiento();
				consentimiento.setId((Integer) rs.getObject("id"));
				consentimiento.setNombre((String) rs.getObject("nombre"));
				consentimiento.setDescripcion((String) rs.getObject("descripcion"));
				consentimiento.setModoEdicion(true);
				consentimientos.add(consentimiento);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}
		return consentimientos;
	}
}
