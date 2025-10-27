/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistema.de.gestion.electrica;

import java.util.Scanner;
import sistema.de.gestion.electrica.controladores.*; // Importa los controladores de la capa de negocio.

/**
 * Clase principal (Launcher).
 * Contiene el método main y el flujo de control de alto nivel.
 * Delega la ejecución y el manejo de excepciones de interfaz al GestorDeInterfaz.
 */
public class SistemaDeGestionElectrica {

    private static final Scanner scanner = new Scanner(System.in); // Objeto para manejar la entrada de la consola.
    private static final SimuladorDeBD simuladorDeBD = new SimuladorDeBD(); // Simulación de la capa de acceso a datos.
    
    // Declaración de las dependencias (Controladores de la lógica de negocio)
    private static ControladorIniciarSesion controlLogin;
    private static ControladorAgregarMedidor controlAgregarMedidor;
    private static ControladorSolicitudCambio controlSolicitudCambio;
    private static ControladorGestionarServicio controlGestionarServicio;
    
    // Delegación de la lógica de Interfaz (Capa de Presentación/I/O)
    private static GestorDeInterfaz gestorInterfaz;

    /**
     * Bloque estático de inicialización (Inyección de Dependencias).
     * Se ejecuta una vez al cargar la clase, inicializando todos los componentes.
     */
    static {
        // Inicialización de Controladores (se les inyecta el SimuladorDeBD)
        controlLogin = new ControladorIniciarSesion(simuladorDeBD);
        controlAgregarMedidor = new ControladorAgregarMedidor(simuladorDeBD);
        controlSolicitudCambio = new ControladorSolicitudCambio(simuladorDeBD);
        controlGestionarServicio = new ControladorGestionarServicio(simuladorDeBD);
        
        // Inicialización del gestor de interfaz (se le inyectan todos los Controladores y el Scanner)
        gestorInterfaz = new GestorDeInterfaz(
            scanner, controlLogin, controlAgregarMedidor, 
            controlSolicitudCambio, controlGestionarServicio, simuladorDeBD
        );
    }
    
    public static void main(String[] args) {
        
        System.out.println("=====================================================");
        System.out.println("        INICIO DEL SISTEMA DE GESTIÓN ELÉCTRICA      ");
        System.out.println("=====================================================");

        Usuario usuarioAutenticado = null; // Almacena el usuario logeado (null si no hay sesión).
        boolean salir = false; // Bandera para controlar el bucle principal.

        // Bucle principal de la aplicación. Se repite hasta que 'salir' sea true.
        while (!salir) {
            if (usuarioAutenticado == null) {
                // Si no hay usuario: intenta CU001 (Iniciar Sesión).
                usuarioAutenticado = gestorInterfaz.ejecutarCU001_IniciarSesion();
                if (usuarioAutenticado == null) {
                    // Maneja la opción de reintentar o cerrar el sistema.
                    salir = gestorInterfaz.manejarReintentoOSalida();
                }
            } else {
                // Si hay usuario: Muestra el menú y lee la opción.
                gestorInterfaz.mostrarMenuPrincipal(usuarioAutenticado);
                String opcion = scanner.nextLine();
                
                // Delega la ejecución del menú al GestorDeInterfaz (incluyendo el manejo de errores).
                if (usuarioAutenticado instanceof Cliente) {
                    salir = gestorInterfaz.ejecutarMenuCliente((Cliente) usuarioAutenticado, opcion);
                } else if (usuarioAutenticado instanceof Operario) {
                    salir = gestorInterfaz.ejecutarMenuOperario((Operario) usuarioAutenticado, opcion);
                }
            }
        }
        
        System.out.println("\n=====================================================");
        System.out.println("            FIN DE LA SIMULACIÓN - ADIÓS             ");
        System.out.println("=====================================================");
        scanner.close(); // Cierra el objeto Scanner al finalizar el programa.
    }
}
