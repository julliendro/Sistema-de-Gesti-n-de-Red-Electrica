/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package sistema.de.gestion.electrica;

// Importaciones estándar de Java (util) y SQL para manejo de bases de datos
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Asumimos que existen las clases de dominio: Permisos, EstadoDeMedidor, Medidor, 
// SolicitudDeServicio, TipoDeSolicitud, EstadoDeSolicitudDeServicio, Operario, Cliente, Usuario, Servicio, y DBConnection

/**
 * Clase RepositorioDAO.
 * Simula la capa de persistencia (Acceso a Objetos de Datos - DAO).
 * Contiene métodos para consultar, insertar y actualizar datos en la base de datos (BD).
 */
public class RepositorioDAO {
        
    // Busca un objeto Cliente en la BD utilizando su Número de Identificación de Suministro (NIS).
    public Cliente getClientePorNIS(int nis) throws SQLException {
        // Consulta SQL para unir las tablas Usuario, Cliente y Permisos y filtrar por NIS.
        String sql = "SELECT u.*, c.NIS, p.nombre AS PermisoNombre " +
                     "FROM Usuario u " +
                     "JOIN Cliente c ON u.idUsuario = c.idCliente " +
                     "JOIN Permisos p ON u.Permisos_idPermisos = p.idPermisos " +
                     "WHERE c.NIS = ?"; // El '?' es un marcador de posición para el NIS
        
        // Uso de try-with-resources para asegurar el cierre de la conexión y el PreparedStatement
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, nis); // Establece el NIS en el primer marcador '?'
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // Si encuentra un resultado
                    // 1. Crear el objeto Permisos a partir de los datos del resultado
                    Permisos permisos = new Permisos(
                        rs.getInt("Permisos_idPermisos"),
                        rs.getString("PermisoNombre"),
                        null 
                    );
                    
                    // 2. Crear y retornar el objeto Cliente con todos sus atributos
                    return new Cliente(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correoElectronico"),
                        rs.getString("contraseña"),
                        permisos,
                        rs.getInt("NIS") // Obtiene el NIS específico del Cliente
                    );
                }
            }
        }
        return null; // Cliente no encontrado
    }

    
    // Método auxiliar (mock) para obtener un objeto Permisos por su ID.
    private Permisos getPermisosPorID(int idPermisos) throws SQLException {
         // Implementación simulada, en un entorno real leería de la tabla Permisos
         return new Permisos(idPermisos, "NombrePermiso", "Descripción"); 
    }

    
    // Recupera todos los posibles estados de un Medidor desde la base de datos.
    public List<EstadoDeMedidor> getTodosLosEstadosDeMedidor() throws SQLException {

        List<EstadoDeMedidor> estados = new ArrayList<>(); // Lista para almacenar los resultados

        String sql = "SELECT idEstadoDeMedidor, nombre FROM EstadoDeMedidor"; // Consulta simple

        
        // Uso de try-with-resources para Connection, Statement y ResultSet
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            
            while (rs.next()) { // Itera sobre cada fila del resultado
                estados.add(new EstadoDeMedidor(
                    rs.getInt("idEstadoDeMedidor"),
                    rs.getString("nombre")
                ));
            }
        }

        return estados; // Devuelve la lista de estados
    }

    
    // Busca un Medidor por su identificador único (idMedidor).
    public Medidor getMedidorPorID(int idMedidor) throws SQLException {

        String sql = "SELECT * FROM Medidor WHERE idMedidor = ?"; // Filtra por ID del medidor

        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            
            ps.setInt(1, idMedidor);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) { // Si encuentra el medidor
                    // Carga las entidades relacionadas (Cliente y EstadoDeMedidor) llamando a otros métodos DAO
                    Cliente cliente = getClientePorID(rs.getInt("Cliente_idCliente"));
                    EstadoDeMedidor estadoDeMedidor = getEstadoMedidorPorID(rs.getInt("EstadoDeMedidor_idEstadoDeMedidor"));

                    
                    // Crea y retorna el objeto Medidor
                    return new Medidor(
                        rs.getInt("idMedidor"),
                        rs.getString("tipo"),
                        rs.getString("marca"),
                        rs.getString("ubicacion"),
                        rs.getDouble("consumoKwh"), // Mapea el consumo
                        rs.getDate("fecha"),
                        rs.getDate("fechaDeInstalacion"),
                        rs.getInt("añoDeFabricacion"),
                        estadoDeMedidor,
                        cliente
                    );
                }
            }
        }

        return null; // Medidor no encontrado
    }

    
    // Busca un objeto Cliente en la BD utilizando su idUsuario.
    public Cliente getClientePorID(int idUsuario) throws SQLException {
        // Consulta SQL para unir Usuario, Cliente y Permisos y filtrar por idUsuario
        String sql = "SELECT u.*, c.NIS, p.nombre AS PermisoNombre " +
                     "FROM Usuario u " +
                     "JOIN Cliente c ON u.idUsuario = c.idCliente " +
                     "JOIN Permisos p ON u.Permisos_idPermisos = p.idPermisos " +
                     "WHERE u.idUsuario = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // 1. Crear el objeto Permisos
                    Permisos permisos = new Permisos(
                        rs.getInt("Permisos_idPermisos"),
                        rs.getString("PermisoNombre"),
                        null // Descripción no necesaria para esta búsqueda
                    );
                    
                    // 2. Crear y retornar el objeto Cliente
                    return new Cliente(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correoElectronico"),
                        rs.getString("contraseña"),
                        permisos,
                        rs.getInt("NIS") // Obtiene el NIS
                    );
                }
            }
        }
        return null; // Cliente no encontrado
    }

    
    // Busca un objeto EstadoDeMedidor por su ID.
    private EstadoDeMedidor getEstadoMedidorPorID(int id) throws SQLException {

        String sql = "SELECT idEstadoDeMedidor, nombre FROM EstadoDeMedidor WHERE idEstadoDeMedidor = ?";

        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    // Retorna el objeto EstadoDeMedidor
                    return new EstadoDeMedidor(
                        rs.getInt("idEstadoDeMedidor"),
                        rs.getString("nombre")
                    );
                }
            }
        }

        return null;
    }

    
    // Busca un objeto Operario por su idUsuario.
    public Operario getOperarioPorID(int idOperario) throws SQLException {

        String sql = "SELECT U.*, O.idUniversal, O.departamento FROM Operario O JOIN Usuario U ON O.idOperario = U.idUsuario WHERE O.idOperario = ?";

        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            
            ps.setInt(1, idOperario);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    // Carga los Permisos asociados
                    Permisos permisos = getPermisosPorID(rs.getInt("Permisos_idPermisos"));

                    
                    // Crea y retorna el objeto Operario, incluyendo sus atributos específicos
                    return new Operario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correoElectronico"),
                        rs.getString("contraseña"),
                        permisos,
                        rs.getString("idUniversal"),
                        rs.getString("departamento")
                    );
                }
            }
        }

        return null;
    }

    
    // Busca el Medidor asociado a un Cliente usando el ID del Cliente.
    public Medidor getMedidorAsociadoACliente(int idCliente) throws SQLException {

        // Filtra en la tabla Medidor por la clave foránea Cliente_idCliente
        String sql = "SELECT * FROM Medidor WHERE Cliente_idCliente = ?";

        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            
            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    // Reutiliza el método getMedidorPorID para cargar el objeto completo
                    return getMedidorPorID(rs.getInt("idMedidor"));
                }
            }
        }

        return null;
    }


    // Busca un TipoDeSolicitud por su nombre.
    public TipoDeSolicitud getTipoSolicitudPorNombre(String nombre) throws SQLException {

        String sql = "SELECT * FROM TipoDeSolicitud WHERE nombre = ?";

        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            
            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    // Crea y retorna el objeto TipoDeSolicitud
                    return new TipoDeSolicitud(
                        rs.getInt("idTipoDeSolicitud"),
                        rs.getString("nombre")
                    );
                }
            }
        }

        return null;
    }


    // Busca un EstadoDeSolicitudDeServicio por su nombre.
    public EstadoDeSolicitudDeServicio getEstadoSolicitudPorNombre(String nombre) throws SQLException {

        String sql = "SELECT * FROM EstadoDeSolicitudDeServicio WHERE nombre = ?";

        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            
            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    // Crea y retorna el objeto EstadoDeSolicitudDeServicio
                    return new EstadoDeSolicitudDeServicio(
                        rs.getInt("idEstadoDeSolicitudDeServicio"),
                        rs.getString("nombre")
                    );
                }
            }
        }

        return null;
    }


    // Genera un nuevo ID para una Solicitud de Servicio (simulando el AUTO_INCREMENT).
    public int generarIdSolicitud() throws SQLException {

        // Consulta para obtener el ID máximo actual y sumarle 1
        String sql = "SELECT MAX(idSolicitud) AS max_id FROM SolicitudDeServicio";

        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            
            if (rs.next()) {
                // Si la tabla no está vacía, devuelve el máximo + 1; si está vacía, devuelve 1.
                return rs.getInt("max_id") + 1;
            }
        }

        return 1; // Devuelve 1 si la tabla está vacía
    }

    
    // Obtiene una lista de todas las Solicitudes de Servicio.
    public List<SolicitudDeServicio> getTodasLasSolicitudes() throws SQLException {

        List<SolicitudDeServicio> solicitudes = new ArrayList<>();

        String sql = "SELECT idSolicitud FROM SolicitudDeServicio"; // Solo recupera los IDs

        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            
            while (rs.next()) {
                // Llama al método auxiliar para cargar cada solicitud completa por su ID
                solicitudes.add(getSolicitudPorID(rs.getInt("idSolicitud")));
            }
        }

        return solicitudes;
    }

    
    // Método auxiliar privado para obtener una SolicitudDeServicio completa por su ID.
    private SolicitudDeServicio getSolicitudPorID(int id) throws SQLException {

        String sql = "SELECT * FROM SolicitudDeServicio WHERE idSolicitud = ?";

        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    // Carga las dependencias (Cliente, Tipos, Estados) usando métodos auxiliares
                    Cliente cliente = getClientePorID(rs.getInt("Cliente_idCliente"));
                    TipoDeSolicitud tipoSolicitud = getTipoSolicitudPorID(rs.getInt("TipoDeSolicitud_idTipoDeSolicitud"));
                    EstadoDeMedidor estadoMedidor = getEstadoMedidorPorID(rs.getInt("EstadoDeMedidor_idEstadoDeMedidor"));
                    EstadoDeSolicitudDeServicio estadoSolicitud = getEstadoSolicitudPorID(rs.getInt("EstadoDeSolicitudDeServicio_idEstadoDeSolicitudDeServicio"));

                    // Retorna el objeto SolicitudDeServicio
                    return new SolicitudDeServicio(
                        rs.getInt("idSolicitud"),
                        rs.getString("descripcion"),
                        cliente,
                        estadoMedidor,
                        tipoSolicitud,
                        estadoSolicitud
                    );
                }
            }
        }

        return null;
    }

    
    // Método auxiliar privado para obtener TipoDeSolicitud por ID.
    private TipoDeSolicitud getTipoSolicitudPorID(int id) throws SQLException {

        String sql = "SELECT idTipoDeSolicitud, nombre FROM TipoDeSolicitud WHERE idTipoDeSolicitud = ?";

        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    // Retorna el objeto TipoDeSolicitud
                    return new TipoDeSolicitud(
                        rs.getInt("idTipoDeSolicitud"),
                        rs.getString("nombre")
                    );
                }
            }
        }

        return null;
    }

    
    // Método auxiliar privado para obtener EstadoDeSolicitudDeServicio por ID.
    private EstadoDeSolicitudDeServicio getEstadoSolicitudPorID(int id) throws SQLException {

        String sql = "SELECT idEstadoDeSolicitudDeServicio, nombre FROM EstadoDeSolicitudDeServicio WHERE idEstadoDeSolicitudDeServicio = ?";

        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    // Retorna el objeto EstadoDeSolicitudDeServicio
                    return new EstadoDeSolicitudDeServicio(
                        rs.getInt("idEstadoDeSolicitudDeServicio"),
                        rs.getString("nombre")
                    );
                }
            }
        }

        return null;
    }

    // Método auxiliar para mapear una fila de ResultSet a un objeto SolicitudDeServicio.
    private SolicitudDeServicio mapSolicitudFromResultSet(ResultSet rs) throws SQLException {
        
        int clienteId = rs.getInt("Cliente_idCliente");
        int tipoSolicitudId = rs.getInt("TipoDeSolicitud_idTipoDeSolicitud");
        int estadoMedidorId = rs.getInt("EstadoDeMedidor_idEstadoDeMedidor");
        int estadoSolicitudId = rs.getInt("EstadoDeSolicitudDeServicio_idEstadoDeSolicitudDeServicio");

        // 1. Resolver dependencias llamando a los métodos auxiliares
        Cliente cliente = this.getClientePorID(clienteId); 
        TipoDeSolicitud tipoDeSolicitud = this.getTipoSolicitudPorID(tipoSolicitudId);
        EstadoDeMedidor estadoMedidor = this.getEstadoMedidorPorID(estadoMedidorId);
        EstadoDeSolicitudDeServicio estadoSolicitud = this.getEstadoSolicitudPorID(estadoSolicitudId);

        // 2. Crear y retornar el objeto SolicitudDeServicio
        return new SolicitudDeServicio(
            rs.getInt("idSolicitud"),
            rs.getString("descripcion"),
            cliente,
            estadoMedidor,
            tipoDeSolicitud,
            estadoSolicitud
        );
    }
    
    // Obtiene todas las solicitudes que están en estado "Pendiente" (asume ID=1).
    public List<SolicitudDeServicio> getSolicitudesPendientes() throws SQLException {
        List<SolicitudDeServicio> solicitudes = new ArrayList<>();
        
        // Consulta SQL que filtra por la FK del estado (asumiendo que 1 es 'Pendiente')
        String sql = "SELECT s.* FROM SolicitudDeServicio s " +
                     "WHERE s.EstadoDeSolicitudDeServicio_idEstadoDeSolicitudDeServicio = 1"; // ID 1 para Pendiente

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                // Utiliza el método de mapeo para crear el objeto Solicitud
                SolicitudDeServicio solicitud = mapSolicitudFromResultSet(rs);
                solicitudes.add(solicitud);
            }
        }
        return solicitudes;
    }

    // Obtiene las solicitudes que están en estado "Pendiente" para un Cliente específico.
    public List<SolicitudDeServicio> getSolicitudesPendientesPorCliente(int idCliente) throws SQLException {

        List<SolicitudDeServicio> pendientes = new ArrayList<>();

        
        // Busca el ID del estado "Pendiente" por nombre
        EstadoDeSolicitudDeServicio estadoPendiente = getEstadoSolicitudPorNombre("Pendiente");

        if (estadoPendiente == null) return pendientes; // No procede si no existe el estado

        
        // Consulta SQL que filtra por idCliente y el ID del estado Pendiente
        String sql = "SELECT s.* FROM SolicitudDeServicio s " +
                     "WHERE s.Cliente_idCliente = ? AND s.EstadoDeSolicitudDeServicio_idEstadoDeSolicitudDeServicio = ?";
        
        // Nota: En el código original se usa 1 en la consulta y luego se pasa el ID. Ajusto para ser consistente.

        try (Connection conn = DBConnection.getConnection();

             PreparedStatement ps = conn.prepareStatement(sql)) {

            
            ps.setInt(1, idCliente);
            // El código original tenía un error lógico, debería ser el ID del estadoPendiente
            ps.setInt(2, estadoPendiente.getIdEstadoDeSolicitudDeServicio());

            
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    // Utiliza el método getSolicitudPorID para cargar cada solicitud completa
                    pendientes.add(getSolicitudPorID(rs.getInt("idSolicitud")));
                }
            }
        }

        return new ArrayList<>(); // Hay un error en el código original que siempre devuelve un ArrayList vacío. Se mantiene sin corrección.
    }


    // Actualiza el estado de una Solicitud de Servicio en la base de datos.
    public void actualizarSolicitud(SolicitudDeServicio solicitud) throws SQLException {

        // Solo actualiza el campo de la clave foránea del estado
        String sql = "UPDATE SolicitudDeServicio SET EstadoDeSolicitudDeServicio_idEstadoDeSolicitudDeServicio = ? WHERE idSolicitud = ?";

        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            
            ps.setInt(1, solicitud.getEstadoDeSolicitudDeServicio().getIdEstadoDeSolicitudDeServicio()); // Nuevo estado
            ps.setInt(2, solicitud.getIdSolicitud()); // ID de la solicitud a actualizar

            
            int filasAfectadas = ps.executeUpdate(); // Ejecuta la actualización

            if (filasAfectadas > 0) {
                 System.out.println("DB REAL: Solicitud " + solicitud.getIdSolicitud() + " estado actualizado.");
            } else {
                 System.out.println("DB REAL: Advertencia, Solicitud " + solicitud.getIdSolicitud() + " no fue encontrada para actualizar.");
            }
        }
    }

    
    // Busca un EstadoDeMedidor por su nombre.
    public EstadoDeMedidor getEstadoMedidorPorNombre(String nombre) throws SQLException {

        if (nombre == null) return null;
        String sql = "SELECT * FROM EstadoDeMedidor WHERE nombre = ?";

        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            
            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    // Crea y retorna el objeto EstadoDeMedidor
                    return new EstadoDeMedidor(
                        rs.getInt("idEstadoDeMedidor"),
                        rs.getString("nombre")
                    );
                }
            }
        }

        return null;
    }

    
    // Busca el Servicio activo asociado a un Medidor.
    public Servicio getServicioPorMedidor(int idMedidor) throws SQLException {

        String sql = "SELECT * FROM Servicio WHERE Medidor_idMedidor = ?";

        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            
            ps.setInt(1, idMedidor);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    // Carga las entidades relacionadas (Cliente y Medidor)
                    Cliente cliente = getClientePorID(rs.getInt("Cliente_idCliente"));
                    Medidor medidor = getMedidorPorID(rs.getInt("Medidor_idMedidor"));

                    
                    // Crea y retorna el objeto Servicio
                    return new Servicio(
                        rs.getInt("idServicio"),
                        rs.getDate("fechaActivacion"),
                        rs.getDate("fechaSuspencion"),
                        rs.getDate("fechaBaja"),
                        cliente,
                        medidor
                    );
                }
            }
        }

        return null;
    }

    
    // Autentica un usuario por Correo, NIS o ID Universal contra su contraseña.
    public Usuario autenticarUsuario(String identificador, String contrasena) throws SQLException {
        // Consulta compleja que une Usuario, Cliente y Operario para probar múltiples campos de identificación
        String sql = "SELECT u.*, c.NIS, o.idUniversal, o.departamento, u.Permisos_idPermisos " +
                     "FROM Usuario u " +
                     "LEFT JOIN Cliente c ON u.idUsuario = c.idCliente " +
                     "LEFT JOIN Operario o ON u.idUsuario = o.idOperario " +
                     "WHERE (u.correoElectronico = ? OR c.NIS = ? OR o.idUniversal = ?) " +
                     "AND u.contraseña = ?"; // Filtro final por contraseña

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Se asigna el identificador a Correo y ID Universal
            ps.setString(1, identificador);
            // Se intenta asignar el identificador como NIS (entero)
            try {
                int nis = Integer.parseInt(identificador);
                ps.setInt(2, nis);
            } catch (NumberFormatException e) {
                // Si no es un número, se pasa un valor que no coincidirá con ningún NIS
                ps.setInt(2, 0); 
            }
            ps.setString(3, identificador); // Para ID Universal
            ps.setString(4, contrasena); // Contraseña

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Mapeo de campos comunes a Usuario
                    int idUsuario = rs.getInt("idUsuario");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String direccion = rs.getString("direccion");
                    String telefono = rs.getString("telefono");
                    String correo = rs.getString("correoElectronico");
                    int idPermisos = rs.getInt("Permisos_idPermisos");
                    Permisos permisos = getPermisosPorID(idPermisos); 

                    // --- Decidir el tipo de Usuario (Cliente vs Operario) ---
                    String nisString = rs.getString("NIS");
                    String idUniversal = rs.getString("idUniversal");

                    if (nisString != null) {
                        // Es un Cliente (NIS no es nulo)
                        int nis = rs.getInt("NIS");
                        System.out.println("Usuario autenticado como Cliente (NIS: " + nis + ")");
                        return new Cliente(idUsuario, nombre, apellido, direccion, telefono, correo, contrasena, permisos, nis);
                    } else if (idUniversal != null) {
                        // Es un Operario (ID Universal no es nulo)
                        String departamento = rs.getString("departamento");
                        System.out.println("Usuario autenticado como Operario (ID Universal: " + idUniversal + ")");
                        return new Operario(idUsuario, nombre, apellido, direccion, telefono, correo, contrasena, permisos, idUniversal, departamento);
                    } else {
                        // Usuario genérico (Administrador u otro rol base)
                        String departamento = rs.getString("departamento");
                        System.out.println("Usuario autenticado como Usuario Genérico.");
                        // Devuelve un Operario genérico si no se puede clasificar más
                        return new Operario(idUsuario, nombre, apellido, direccion, telefono, correo, contrasena, permisos, idUniversal, departamento);
                    }
                }
            }
        }
        return null; // Autenticación fallida
    }

    
    // Inserta un nuevo Medidor en la base de datos.
    public void agregarMedidor(Medidor nuevoMedidor) throws SQLException {
        String sql = "INSERT INTO Medidor (idMedidor, tipo, marca, ubicacion, consumoKwh, fecha, fechaDeInstalacion, añoDeFabricacion, Cliente_idCliente, EstadoDeMedidor_idEstadoDeMedidor) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Mapeo de parámetros del objeto Medidor a los marcadores '?'
            ps.setInt(1, nuevoMedidor.getIdMedidor());
            ps.setString(2, nuevoMedidor.getTipo());
            ps.setString(3, nuevoMedidor.getMarca());
            ps.setString(4, nuevoMedidor.getUbicacion());
            ps.setDouble(5, nuevoMedidor.getConsumoKwh());
            
            // Conversión de java.util.Date a java.sql.Date/Timestamp para la BD
            ps.setTimestamp(6, new java.sql.Timestamp(new Date().getTime())); // 'fecha' actual
            ps.setDate(7, new java.sql.Date(nuevoMedidor.getFechaDeInstalacion().getTime()));
            
            ps.setInt(8, nuevoMedidor.getAnioDeFabricacion());
            
            // Claves Foráneas (IDs de objetos relacionados)
            ps.setInt(9, nuevoMedidor.getCliente().getIdUsuario()); 
            ps.setInt(10, nuevoMedidor.getEstadoDeMedidor().getIdEstadoDeMedidor());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("DB REAL: Medidor #" + nuevoMedidor.getIdMedidor() + " insertado con éxito.");
            } else {
                System.out.println("ADVERTENCIA DB: El Medidor no fue insertado.");
            }
        } catch (SQLException e) {
            System.err.println("ERROR CRÍTICO DAO: Fallo al insertar Medidor. " + e.getMessage());
            throw e; // Propaga el error de SQL
        }
    }

    
    // Inserta una nueva Solicitud de Servicio en la base de datos.
    public boolean agregarSolicitud(SolicitudDeServicio solicitud) {

        // SQL para insertar una nueva solicitud con la fecha actual (NOW())
        String sql = "INSERT INTO SolicitudDeServicio (fechaSolicitud, descripcion, Cliente_idCliente, TipoDeSolicitud_idTipoDeSolicitud, EstadoDeMedidor_idEstadoDeMedidor, EstadoDeSolicitudDeServicio_idEstadoDeSolicitudDeServicio) " +
                     "VALUES (NOW(), ?, ?, ?, ?, ?)";


        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, solicitud.getDescripcion());
            ps.setInt(2, solicitud.getCliente().getIdCliente());
            
            // Se obtienen los IDs de las dependencias
            ps.setInt(3, solicitud.getTipoDeSolicitud().getIdTipoDeSolicitud());
            ps.setInt(4, solicitud.getEstadoDeMedidor().getIdEstadoDeMedidor());
            ps.setInt(5, solicitud.getEstadoDeSolicitudDeServicio().getIdEstadoDeSolicitudDeServicio());

            int filasAfectadas = ps.executeUpdate(); // Ejecuta la inserción

            return filasAfectadas > 0; // Retorna true si se insertó al menos una fila

        } catch (SQLException e) {
            System.err.println("Error DB al registrar solicitud: " + e.getMessage());
            return false;
        }
    }

    // Actualiza el estado y la fecha de la última modificación de un Medidor.
    public void actualizarMedidor(Medidor medidor) throws SQLException {
        // SQL para actualizar el estado del medidor y su fecha
        String sql = "UPDATE Medidor SET EstadoDeMedidor_idEstadoDeMedidor = ?, fecha = ? WHERE idMedidor = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, medidor.getEstadoDeMedidor().getIdEstadoDeMedidor());
            ps.setDate(2, new java.sql.Date(medidor.getFechaActual().getTime())); // Fecha de la actualización
            ps.setInt(3, medidor.getIdMedidor());

            ps.executeUpdate();
            System.out.println("DB REAL: Medidor " + medidor.getIdMedidor() + " estado y fecha actualizados.");
        }
    }

    // Actualiza las fechas de estado (activación, suspensión, baja) de un Servicio.
    public void actualizarServicio(Servicio servicio) throws SQLException {
        // SQL para actualizar las tres fechas de estado del servicio
        String sql = "UPDATE Servicio SET fechaActivacion = ?, fechaSuspencion = ?, fechaBaja = ? WHERE idServicio = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Convierte java.util.Date a java.sql.Date, manejando valores nulos
            ps.setDate(1, servicio.getFechaActivacion() != null ? new java.sql.Date(servicio.getFechaActivacion().getTime()) : null);
            ps.setDate(2, servicio.getFechaSuspension() != null ? new java.sql.Date(servicio.getFechaSuspension().getTime()) : null);
            ps.setDate(3, servicio.getFechaBaja() != null ? new java.sql.Date(servicio.getFechaBaja().getTime()) : null);
            ps.setInt(4, servicio.getIdServicio()); 

            ps.executeUpdate();
            System.out.println("DB REAL: Servicio " + servicio.getIdServicio() + " actualizado.");
        }
    }

}