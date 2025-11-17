/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package sistema.de.gestion.electrica;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    
    // --- Configuración de la Base de Datos ---
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // Defino el Driver Moderno de MySQL que voy a usar.
    private static final String URL = "jdbc:mysql://localhost:3306/sgre"; // Defino la URL de conexión a mi base de datos 'sgre'.
    private static final String USER = "root"; // Defino el usuario de la base de datos.
    private static final String PASS = "julianbenja04Fl"; // Defino la contraseña de la base de datos.

    /**
     * Este método lo uso para establecer y retornar una nueva conexión a la base de datos.
     * @return Me devuelve un Objeto Connection a la base de datos 'sgre'.
     * @throws SQLException Lanzo esta excepción si hay un error al intentar conectar.
     */
    public static Connection getConnection() throws SQLException {
        // Intento asegurar que el driver JDBC esté registrado.
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            // Si el driver no se encuentra (me falta el JAR), muestro un error.
            System.err.println("Error: Driver JDBC de MySQL no encontrado.");
            // Envuelvo la excepción en una SQLException para propagarla.
            throw new SQLException(e);
        }

        // Configuro las propiedades de la conexión.
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        props.setProperty("autoReconnect", "true");
        props.setProperty("serverTimezone", "America/Argentina/Salta"); // Esto me ayuda a gestionar correctamente las fechas y horas.
        
        // Retorno la conexión establecida con la URL y mis propiedades.
        return DriverManager.getConnection(URL, props);
    }
    
    /**
     * Este método lo uso para intentar establecer una conexión y verificar que mi configuración sea correcta.
     * La clase main (SistemaDeGestionElectrica) lo utiliza al inicio.
     * @return Retorna true si la conexión es exitosa, sino false.
     * @throws SQLException Lanzo la excepción si la conexión falla, para que el llamador la maneje.
     */
    public static boolean testConnection() throws SQLException {
        // Utilizo try-with-resources para asegurar que la conexión se cierre automáticamente.
        try (Connection conn = getConnection()) {
            // Si la conexión no es nula, significa que fue exitosa.
            return conn != null;
        } 
    }
    
    /**
     * Este método me ayuda a cerrar la conexión de forma segura.
     * @param conn La conexión que necesito cerrar.
     */
    public static void close(Connection conn) {
        // Verifico que la conexión no sea nula antes de intentar cerrarla.
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // Si hay un error al intentar cerrar, lo muestro.
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}