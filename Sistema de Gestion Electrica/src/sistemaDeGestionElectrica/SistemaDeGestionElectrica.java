/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistema.de.gestion.electrica;

import java.sql.SQLException;

// Esta es mi clase principal para iniciar la aplicación de gestión eléctrica (versión consola).
public class SistemaDeGestionElectrica {

    // Este es el punto de entrada principal de mi aplicación.
    public static void main(String[] args) {
        
        // Muestro un mensaje inicial en la consola para saber que el sistema está arrancando.
        System.out.println("Iniciando Sistema de Gestión Eléctrica...");
        
        // Uso un bloque try-catch para manejar posibles errores de conexión a la base de datos o de ejecución.
        try {
            // Intento realizar una prueba de conexión a la base de datos usando mi clase DBConnection.
            if (DBConnection.testConnection()) { 
                // Si la conexión es exitosa, lo indico en la consola.
                System.out.println("Conexión a la Base de Datos establecida correctamente.");
                
                // 1. Creo una instancia de mi GestorDeInterfaz, que se encarga de manejar el menú de consola.
                GestorDeInterfaz gestor = new GestorDeInterfaz();
                
                // 2. Inicio el ciclo principal de la aplicación, que mostrará el menú de opciones.
                gestor.iniciar();
                
            } else {
                // Si la prueba de conexión falla, muestro un error fatal.
                System.err.println("ERROR FATAL: No se pudo establecer la conexión a la base de datos.");
            }
        // Capturo específicamente cualquier error relacionado con la base de datos (SQL).
        } catch (SQLException e) { 
            // Muestro un mensaje de error si hubo un problema al intentar conectar.
            System.err.println("Error de SQL al intentar conectar: " + e.getMessage());
        // Capturo cualquier otra excepción no esperada durante el inicio.
        } catch (Exception e) {
            // Muestro un mensaje de error general y la traza completa de la excepción.
            System.err.println("Error no controlado al iniciar el sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
