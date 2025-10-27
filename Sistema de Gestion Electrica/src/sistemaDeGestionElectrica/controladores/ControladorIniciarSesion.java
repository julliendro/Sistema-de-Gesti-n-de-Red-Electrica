/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica.controladores;

import sistema.de.gestion.electrica.SimuladorDeBD;
import sistema.de.gestion.electrica.Usuario;
import sistema.de.gestion.electrica.Cliente;
import sistema.de.gestion.electrica.Operario;

/**
 * Clase ControladorIniciarSesion.
 * Gestiona el caso de uso de inicio de sesión para Clientes y Operarios.
 */
public class ControladorIniciarSesion {
    
    private final SimuladorDeBD simuladorDeBD; // Referencia a la capa de persistencia (Base de Datos).

    /*
     * Constructor que inyecta la dependencia del Simulador de Base de Datos.
     */
    public ControladorIniciarSesion(SimuladorDeBD simuladorDeBD) {
        this.simuladorDeBD = simuladorDeBD;
    }

    /*
     * Procesa el intento de inicio de sesión con el identificador (NIS o ID Universal) y la contraseña.
     * @return El objeto Usuario autenticado (Cliente u Operario) o null si falla.
     */
    public Usuario procesarInicioSesion(String identificador, String contrasena) {
        
        // 1. Validación de campos de entrada obligatorios.
        if (identificador == null || identificador.trim().isEmpty() || contrasena == null || contrasena.isEmpty()) {
            System.out.println("ERROR CONTROL: Los campos no pueden estar vacíos.");
            return null;
        }

        // 2. Intenta autenticar al usuario delegando la lógica a la capa de datos.
        Usuario usuarioAutenticado = this.simuladorDeBD.autenticarUsuario(identificador, contrasena);

        if (usuarioAutenticado != null) {
            
            // 3. Registra el inicio de sesión en el objeto Usuario (operación de dominio).
            usuarioAutenticado.iniciarSesion(usuarioAutenticado.getCorreoElectronico(), contrasena); 

            System.out.println("--- Sesión Iniciada Exitosamente ---");
            
            // 4. Muestra un mensaje de redirección basado en el tipo de usuario.
            if (usuarioAutenticado instanceof Cliente) {
                System.out.println("Redirigiendo al Panel de Cliente (NIS: " + ((Cliente)usuarioAutenticado).getNIS() + ").");
            } else if (usuarioAutenticado instanceof Operario) {
                System.out.println("Redirigiendo al Panel de Operario (" + ((Operario)usuarioAutenticado).getDepartamento() + ").");
            }

            return usuarioAutenticado; // Retorna el objeto autenticado.

        } else {
            System.out.println("ERROR CONTROL: Credenciales inválidas.");
            return null; // Retorna null si la autenticación falló.
        }
    }
}
