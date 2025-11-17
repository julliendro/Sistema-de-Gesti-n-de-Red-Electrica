/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;


// Importación de clases de la capa de controladores
import sistema.de.gestion.electrica.controladores.*;

// Importación de clases para manejo de errores de SQL, fechas y entrada/salida
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


/*
 * Clase GestorDeInterfaz.
 * Maneja toda la entrada y salida (I/O) por consola.
 * Actúa como la capa de Presentación y llama a los Controladores para la lógica de negocio.
 */
public class GestorDeInterfaz {

    // Inicializa el objeto Scanner para leer la entrada del usuario
    private final Scanner scanner;
    // Formato para parsear y mostrar fechas
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    // --- Capa de Persistencia y Controladores ---
    // Repositorio para acceder a la base de datos (DAO)
    private final RepositorioDAO repositorioDAO;
    // Controlador para la lógica de inicio de sesión
    private final ControladorIniciarSesion controladorLogin;
    // Controlador para la lógica de agregar un medidor
    private final ControladorAgregarMedidor controladorMedidor;
    // Controlador para la lógica de solicitar un cambio de servicio
    private final ControladorSolicitudCambio controladorSolicitud;
    // Controlador para la lógica de gestionar cambios de servicio por un operario
    private final ControladorGestionarServicio controladorServicio;
    
    // Almacena el usuario autenticado actualmente en el sistema
    private Usuario usuarioActual;

    // Constructor de la clase
    public GestorDeInterfaz() {
        // Inicializa el objeto Scanner
        this.scanner = new Scanner(System.in);
        // Inicialización del DAO y Controladores, inyectando el DAO en los Controladores
        this.repositorioDAO = new RepositorioDAO();
        this.controladorLogin = new ControladorIniciarSesion(repositorioDAO);
        this.controladorMedidor = new ControladorAgregarMedidor(repositorioDAO);
        this.controladorSolicitud = new ControladorSolicitudCambio(repositorioDAO);
        this.controladorServicio = new ControladorGestionarServicio(repositorioDAO);
    }

    // Método principal para iniciar la interfaz de consola
    public void iniciar() {
        // Bandera para controlar la salida del bucle principal
        boolean salir = false;
        // Bucle principal del sistema antes del login
        while (!salir) {
            // Muestra el menú de opciones iniciales
            System.out.println("\n=============================================");
            System.out.println("     SISTEMA DE GESTIÓN ELÉCTRICA      ");
            System.out.println("=============================================");
            System.out.println("1. Iniciar Sesión");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            // Lee la opción del usuario
            String opcion = scanner.nextLine();
            try {
                // Maneja la opción seleccionada
                switch (opcion) {
                    case "1":
                        // Llama al método para mostrar la pantalla de login
                        mostrarLogin();
                        // Si el login fue exitoso, muestra el menú principal por rol
                        if (usuarioActual != null) {
                            mostrarMenuPrincipal();
                            usuarioActual = null; // Cierra la sesión al volver al menú principal
                        }
                        break;
                    case "0":
                        // Establece la bandera para salir del bucle
                        System.out.println("Cerrando sistema...");
                        salir = true;
                        break;
                    default:
                        // Opción no reconocida
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (SQLException e) {
                // Captura y muestra errores relacionados con la base de datos
                System.err.println("Error de Base de Datos: " + e.getMessage());
            } catch (Exception e) {
                // Captura y muestra cualquier otro error inesperado
                System.err.println("Error Inesperado: " + e.getMessage());
            }
        }
    }

    // --------------------------------------------------
    // CU001: INICIAR SESIÓN
    // --------------------------------------------------
    // Solicita credenciales y procesa el inicio de sesión
    private void mostrarLogin() throws SQLException {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.print("Ingrese NIS/ID Universal: ");
        String identificador = scanner.nextLine(); // Lee el identificador
        System.out.print("Ingrese Contraseña: ");
        String contrasena = scanner.nextLine(); // Lee la contraseña

        // Delega la lógica de negocio de autenticación al Controlador
        this.usuarioActual = controladorLogin.procesarInicioSesion(identificador, contrasena);
    }
    
    // --------------------------------------------------
    // MENÚ PRINCIPAL
    // --------------------------------------------------
    // Muestra el menú de opciones basado en el rol del usuario actual
    private void mostrarMenuPrincipal() throws SQLException {
        boolean salir = false;
        // Bucle del menú principal después del login
        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");

            // Opciones específicas para Clientes
            if (usuarioActual instanceof Cliente) {
                System.out.println("1. " + CU003.getNombreCU());
                // Más opciones de Cliente (Pagar Factura, Ver Consumo...)
            // Opciones específicas para Operarios
            } else if (usuarioActual instanceof Operario) {
                System.out.println("1. " + CU002.getNombreCU());
                System.out.println("2. " + CU004.getNombreCU());
            }
            
            System.out.println("0. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();
            try {
                // Maneja la opción de cerrar sesión
                if (opcion.equals("0")) {
                    salir = true;
                    System.out.println("Sesión cerrada.");
                // Redirige al menú del Cliente
                } else if (usuarioActual instanceof Cliente) {
                    menuCliente(opcion);
                // Redirige al menú del Operario
                } else if (usuarioActual instanceof Operario) {
                    menuOperario(opcion);
                } else {
                    // Si el usuario no tiene un rol reconocido o la opción no es válida
                    System.out.println("Opción no válida.");
                }
            } catch (SQLException e) {
                // Manejo de errores de Base de Datos
                System.err.println("Error de Base de Datos: " + e.getMessage());
            } catch (NumberFormatException | ParseException e) {
                // Manejo de errores al convertir o parsear datos de entrada
                System.err.println("Error de entrada de datos: " + e.getMessage());
            }
        }
    }
    
    // --------------------------------------------------
    // MENÚS ESPECÍFICOS POR ROL
    // --------------------------------------------------
    // Maneja las opciones disponibles para un Cliente
    private void menuCliente(String opcion) throws SQLException, ParseException {
        switch (opcion) {
            case "1":
                solicitarCambioDeServicio(); // Llama al método para el CU003
                break;
            default:
                System.out.println("Opción de Cliente no válida.");
        }
    }

    // Maneja las opciones disponibles para un Operario
    private void menuOperario(String opcion) throws SQLException, ParseException {
        switch (opcion) {
            case "1":
                agregarNuevoMedidor(); // Llama al método para el CU002
                break;
            case "2":
                gestionarCambioDeServicio(); // Llama al método para el CU004
                break;
            default:
                System.out.println("Opción de Operario no válida.");
        }
    }
    
    // --------------------------------------------------
    // CU002: AGREGAR MEDIDOR
    // --------------------------------------------------
    // Solicita los datos para registrar un nuevo medidor
    private void agregarNuevoMedidor() throws SQLException, ParseException {
    System.out.println("\n--- " + CU002.getNombreCU() + " ---");
        
    // Solicita datos del medidor
    System.out.print("Tipo (Ej: iM10): ");
    String tipo = scanner.nextLine();
    System.out.print("Marca (Ej: ABB): ");
    String marca = scanner.nextLine();
    System.out.print("Ubicación: ");
    String ubicacion = scanner.nextLine();
    System.out.print("Año de Fabricación (YYYY): ");
    // Lee el año y lo convierte a entero
    int anioFabricacion = Integer.parseInt(scanner.nextLine());
    System.out.print("Fecha de Instalación (YYYY-MM-DD): ");
    // Lee la fecha y la parsea usando el formato definido
    Date fechaInstalacion = dateFormat.parse(scanner.nextLine());
    
    System.out.print("NIS de Cliente a asociar: ");
    String nisString = scanner.nextLine();
    
    Cliente cliente = null;
    try {
        // Intenta convertir el NIS a entero
        int nis = Integer.parseInt(nisString);
        // Busca el cliente por NIS usando el DAO
        cliente = repositorioDAO.getClientePorNIS(nis);
    } catch (NumberFormatException e) {
        // Error si el NIS no es numérico
        System.out.println("ERROR: El NIS debe ser un valor numérico.");
        return;
    }

    // Verifica si el cliente fue encontrado
    if (cliente == null) {
        System.out.println("ERROR: No se encontró el Cliente con el NIS " + nisString + ".");
        return;
    }

    // Obtiene el estado "Activo" por defecto para el nuevo medidor
    EstadoDeMedidor estadoActivo = repositorioDAO.getEstadoMedidorPorNombre("Activo");
    
    // Delega la creación del medidor al Controlador
    // Se pasa 0 como ID temporal, el DAO manejará el autoincremento.
    controladorMedidor.agregarMedidor(0, tipo, marca, ubicacion, fechaInstalacion, anioFabricacion, estadoActivo, cliente);
}

    // --------------------------------------------------
    // CU003: SOLICITAR CAMBIO DE ESTADO DE SERVICIO
    // --------------------------------------------------
    // Permite al Cliente solicitar un cambio de estado para su servicio
    private void solicitarCambioDeServicio() throws SQLException {
        System.out.println("\n--- " + CU003.getNombreCU() + " ---");
        // Castea el usuario actual a Cliente
        Cliente cliente = (Cliente) usuarioActual;
        // Busca el medidor asociado al cliente
        Medidor medidor = repositorioDAO.getMedidorAsociadoACliente(cliente.getIdUsuario());
        
        // Valida si el cliente tiene un medidor asociado
        if (medidor == null) {
            System.out.println("ERROR: No puedes solicitar un cambio. No tienes un medidor asociado.");
            return;
        }

        // Muestra el estado actual del servicio
        String estadoActual = medidor.getEstadoDeMedidor().getNombre();
        System.out.println("Estado actual de su servicio: " + estadoActual);
        
        // 1. Elegir Estado ACTUAL (para validación)
        System.out.print("Confirme el estado actual (" + estadoActual + "): ");
        String estadoActualIngresado = scanner.nextLine();
        
        // 2. Elegir Tipo de Solicitud (Nuevo estado deseado)
        System.out.print("Tipo de Solicitud (Ej: Activación, Suspensión, Baja): ");
        String tipoSolicitud = scanner.nextLine();
        
        // 3. Comentario
        System.out.print("Comentario (mínimo 10 caracteres): ");
        String descripcion = scanner.nextLine();
        
        // Delega la creación de la solicitud al Controlador
        controladorSolicitud.procesarSolicitudCambio(cliente, estadoActualIngresado, tipoSolicitud, descripcion);
    }

    // --------------------------------------------------
    // CU004: GESTIONAR CAMBIO DE SERVICIO (Activar Servicio)
    // --------------------------------------------------
    // Permite al Operario revisar y procesar solicitudes de cambio de servicio
    private void gestionarCambioDeServicio() throws SQLException {
    System.out.println("\n--- " + CU004.getNombreCU() + " ---");
    // Castea el usuario actual a Operario
    Operario operario = (Operario) usuarioActual;

    // Obtiene la lista de solicitudes de cambio de servicio pendientes
    List<SolicitudDeServicio> solicitudes = controladorServicio.obtenerTodasLasSolicitudes();
    
    // --- 1. Validación de ausencia de solicitudes ---
    if (solicitudes == null || solicitudes.isEmpty()) {
        System.out.println("ADVERTENCIA: Actualmente no hay Solicitudes Pendientes para gestionar.");
        return;
    }

    // Filtrar por NIS (Fase 2)
    System.out.print("Ingrese NIS para filtrar Solicitudes Pendientes (Deje vacío para ver todas): ");
    String nisFiltro = scanner.nextLine();

    // Aplica el filtro si se ingresa un NIS
    if (!nisFiltro.trim().isEmpty()) {
        List<SolicitudDeServicio> solicitudesFiltradas = null;
        try {
            // Filtra la lista de solicitudes por NIS usando el controlador
            solicitudesFiltradas = controladorServicio.filtrarSolicitudesPorNIS(nisFiltro);
        } catch (NumberFormatException e) {
            // Este error ya debería ser manejado por el controlador, pero se mantiene la estructura
        }
        
        // Si no hay resultados después del filtro
        if (solicitudesFiltradas == null || solicitudesFiltradas.isEmpty()) {
            System.out.println("No hay solicitudes pendientes para el NIS: " + nisFiltro);
            return;
        }
        solicitudes = solicitudesFiltradas; // Usa la lista filtrada
    }
    
    // Muestra las solicitudes pendientes (filtradas o todas)
    System.out.println("\n--- SOLICITUDES PENDIENTES ---");
    for (int i = 0; i < solicitudes.size(); i++) {
        SolicitudDeServicio s = solicitudes.get(i);
        // Muestra los detalles de cada solicitud
        System.out.println((i + 1) + ". ID: " + s.getIdSolicitud() + 
                            ", Cliente NIS: " + s.getCliente().getNIS() + 
                            ", Tipo: " + s.getTipoDeSolicitud().getNombre() + 
                            ", Descripción: " + s.getDescripcion());
    }

    // --- 2. Selección de Solicitud con opción de salida ---
    System.out.print("\nSeleccione el número de Solicitud a procesar (Presione 0 para salir): ");
    String entrada = scanner.nextLine();
    int indiceSeleccionado;

    // Intenta parsear la entrada a un número
    try {
        indiceSeleccionado = Integer.parseInt(entrada);
    } catch (NumberFormatException e) {
        System.out.println("Entrada no válida. Debe ingresar un número.");
        return;
    }
    
    // Validación de salida (opción 0)
    if (indiceSeleccionado == 0) {
        System.out.println("Operación cancelada.");
        return;
    }
    
    // Ajuste de índice a base 0
    indiceSeleccionado = indiceSeleccionado - 1;

    // Validación de rango del índice seleccionado
    if (indiceSeleccionado < 0 || indiceSeleccionado >= solicitudes.size()) {
        System.out.println("Selección no válida. El número de solicitud no existe.");
        return;
    }

    // Obtiene la solicitud y el medidor asociado
    SolicitudDeServicio solicitudSeleccionada = solicitudes.get(indiceSeleccionado);
    Medidor medidorAsociado = controladorServicio.obtenerMedidorAsociado(solicitudSeleccionada);
    
    // Valida si se encontró el medidor asociado
    if (medidorAsociado == null) {
        System.out.println("ERROR: La Solicitud seleccionada no tiene un Medidor asociado.");
        return;
    }
    
    // Muestra la información del Medidor
    System.out.println("\n--- DETALLES DEL MEDIDOR ASOCIADO ---");
    System.out.println("Medidor ID: " + medidorAsociado.getIdMedidor() + 
                        ", Marca: " + medidorAsociado.getMarca() + 
                        ", Estado Actual: " + medidorAsociado.getEstadoDeMedidor().getNombre());
    
    // Determina el nuevo estado del medidor basado en el tipo de solicitud
    String tipo = solicitudSeleccionada.getTipoDeSolicitud().getNombre();
    String nuevoEstado;
    
    // Mapeo de Tipo de Solicitud a Nuevo Estado de Medidor
    switch (tipo.toLowerCase()) {
        case "activación":
            nuevoEstado = "Activo";
            break;
        case "suspensión":
            nuevoEstado = "Suspendido";
            break;
        case "baja":
            nuevoEstado = "De Baja";
            break;
        default:
            System.out.println("Tipo de Solicitud (" + tipo + ") no reconocido para cambio de estado.");
            return;
    }

    // Pide confirmación antes de aplicar el cambio
    System.out.println("Cambiando Medidor de '" + medidorAsociado.getEstadoDeMedidor().getNombre() + "' a '" + nuevoEstado + "'.");
    System.out.print("Confirme la acción (S/N): ");
    String confirmacion = scanner.nextLine();
    
    // Si el operario confirma, procesa el cambio
    if (confirmacion.equalsIgnoreCase("S")) {
        // Delega la lógica final de cambio de estado y actualización de la solicitud al Controlador
        controladorServicio.procesarCambioDeServicio(solicitudSeleccionada, medidorAsociado, nuevoEstado, operario);
    } else {
        System.out.println("Proceso de cambio cancelado por el Operario.");
    }
    }
    
    // --- Clase interna para nombres de CU (para mantener la interfaz limpia) ---
    // Clases estáticas internas para obtener los nombres de los Casos de Uso (CU)
    private static class CU002 { private static String getNombreCU() { return "Agregar Nuevo Medidor"; } }
    private static class CU003 { private static String getNombreCU() { return "Solicitar cambio de Estado de Servicio"; } }
    private static class CU004 { private static String getNombreCU() { return "Gestionar Cambio de Servicio (Activar/Suspender/Baja)"; } }
}