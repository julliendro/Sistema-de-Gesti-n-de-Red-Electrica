/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package sistema.de.gestion.electrica.controladores;

// Importaciones necesarias para manejar errores de SQL y clases de dominio
import java.sql.SQLException;
import sistema.de.gestion.electrica.RepositorioDAO;
import sistema.de.gestion.electrica.Usuario;
import sistema.de.gestion.electrica.Cliente;
import sistema.de.gestion.electrica.Operario;


/**
 * Clase ControladorIniciarSesion.
 * Gestiona el caso de uso de inicio de sesión para Clientes y Operarios.
 */
public class ControladorIniciarSesion {
    
    // Referencia al objeto de acceso a datos (DAO) para interactuar con la persistencia
    private final RepositorioDAO repositorioDAO; 

    // Constructor que recibe e inicializa la dependencia del RepositorioDAO
    public ControladorIniciarSesion(RepositorioDAO repositorioDAO) {
        this.repositorioDAO = repositorioDAO;
    }

    /*
     * Procesa el intento de inicio de sesión.
     * @return El objeto Usuario autenticado o null si falla.
     * @throws SQLException Propaga errores de la base de datos al GestorDeInterfaz.
     */
    public Usuario procesarInicioSesion(String identificador, String contrasena) throws SQLException {
        
        // 1. Validación de campos de entrada obligatorios.
        if (identificador == null || identificador.trim().isEmpty() || contrasena == null || contrasena.isEmpty()) {
            System.out.println("ERROR CONTROL: Los campos no pueden estar vacíos.");
            return null;
        }

        // 2. Intenta autenticar al usuario delegando la lógica a la capa de datos (DAO).
        Usuario usuarioAutenticado = this.repositorioDAO.autenticarUsuario(identificador, contrasena);

        // Verifica si se encontró un usuario con las credenciales dadas
        if (usuarioAutenticado != null) {
            
            // 3. Registra el inicio de sesión en el objeto Usuario (simulación de una operación de dominio).
            usuarioAutenticado.iniciarSesion(usuarioAutenticado.getCorreoElectronico(), contrasena); 

            System.out.println("--- Sesión Iniciada Exitosamente ---");
            
            // 4. Muestra un mensaje de redirección basado en el tipo de usuario (Cliente o Operario).
            if (usuarioAutenticado instanceof Cliente) {
                System.out.println("Redirigiendo al Panel de Cliente (NIS: " + ((Cliente)usuarioAutenticado).getNIS() + ").");
            } else if (usuarioAutenticado instanceof Operario) {
                System.out.println("Redirigiendo al Panel de Operario (" + ((Operario)usuarioAutenticado).getDepartamento() + ").");
            }

            // Devuelve el objeto Usuario autenticado
            return usuarioAutenticado;

        } else {
            // Si el DAO devuelve null, las credenciales son inválidas
            System.out.println("ERROR CONTROL: Credenciales inválidas.");
            return null;
        }
    }
}