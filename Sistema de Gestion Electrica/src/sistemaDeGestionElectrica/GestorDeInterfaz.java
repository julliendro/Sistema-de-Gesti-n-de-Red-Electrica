/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import sistema.de.gestion.electrica.controladores.*;

/**
 * Clase GestorDeInterfaz.
 * Maneja toda la entrada y salida (I/O) por consola.
 * Actúa como la capa de Presentación y llama a los Controladores para la lógica de negocio.
 */
public class GestorDeInterfaz {

    // Dependencias Inyectadas (finales para asegurar que no cambien)
    private final Scanner scanner;
    private final SimuladorDeBD simuladorDeBD;
    private final ControladorIniciarSesion controlLogin;
    private final ControladorAgregarMedidor controlAgregarMedidor;
    private final ControladorSolicitudCambio controlSolicitudCambio;
    private final ControladorGestionarServicio controlGestionarServicio;

    /*
     * Constructor para la Inyección de Dependencias.
     * Recibe todas las herramientas necesarias para operar.
     */
    public GestorDeInterfaz(Scanner scanner, ControladorIniciarSesion controlLogin, ControladorAgregarMedidor controlAgregarMedidor, ControladorSolicitudCambio controlSolicitudCambio, ControladorGestionarServicio controlGestionarServicio, SimuladorDeBD simuladorDeBD) {
        this.scanner = scanner;
        this.controlLogin = controlLogin;
        this.controlAgregarMedidor = controlAgregarMedidor;
        this.controlSolicitudCambio = controlSolicitudCambio;
        this.controlGestionarServicio = controlGestionarServicio;
        this.simuladorDeBD = simuladorDeBD;
    }

    // =============================================================
    // FLUJO PRINCIPAL Y MANEJO DE SESIÓN
    // =============================================================
    
    /*
     * CU001: Iniciar Sesión (Manejo de I/O)
     * Recoge credenciales y llama al controlador de login.
     */
    public Usuario ejecutarCU001_IniciarSesion() {
        System.out.println("\n--- CU001: INICIAR SESIÓN ---");
        System.out.print("Ingrese NIS/ID Universal (Ej. 789012345 o OP1001): ");
        String identificador = scanner.nextLine();
        System.out.print("Ingrese Contraseña (Ej. hash+Pass1 o hash+Op1): ");
        String contrasena = scanner.nextLine();
        
        // Llama a la lógica de negocio.
        Usuario usuario = controlLogin.procesarInicioSesion(identificador, contrasena);
        
        if (usuario != null) {
            System.out.println("Sesión iniciada: ¡Bienvenido, " + usuario.getNombre() + "! Tipo: " + usuario.getTipoUsuario() + ".");
        } else {
            System.out.println("Fallo de Autenticación: Identificador o Contraseña incorrectos.");
        }
        return usuario;
    }

    /*
     * Pregunta al usuario si quiere salir o reintentar el login.
     */
    public boolean manejarReintentoOSalida() {
        System.out.println("\nPresione Enter para intentar de nuevo o 's' para salir.");
        String input = scanner.nextLine();
        return input.equalsIgnoreCase("s");
    }
    
    /*
     * Muestra el menú principal, dinámico según el rol del usuario (Cliente u Operario).
     */
    public void mostrarMenuPrincipal(Usuario usuario) {
        System.out.println("\n==================== MENÚ PRINCIPAL ====================");
        System.out.println("USUARIO: " + usuario.getNombre() + " " + usuario.getApellido() + " (" + usuario.getTipoUsuario() + ")");
        System.out.println("------------------------------------------------------");
        
        if (usuario instanceof Cliente) {
            System.out.println("1. [CU003] Solicitar Cambio de Estado de Servicio");
        } else if (usuario instanceof Operario) {
            System.out.println("1. [CU002] Agregar Nuevo Medidor");
            System.out.println("2. [CU004] Activar/Gestionar Solicitudes de Servicio");
        }
        
        System.out.println("0. Cerrar Sesión / Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    /*
     * Método centralizado para manejar y reportar errores inesperados (catch-all).
     */
    private void manejarErrorNoPrevisto(Exception e) {
        System.err.println("\n------------------------------------------------------");
        System.err.println("❌ ERROR INESPERADO DEL SISTEMA: La operación falló.");
        System.err.println("Detalles: " + e.getMessage());
        System.err.println("------------------------------------------------------");
    }
    
    // =============================================================
    // MENÚS ESPECÍFICOS POR ROL (Lógica de flujo de menú)
    // =============================================================

    /*
     * Ejecuta la opción seleccionada por el Cliente.
     * Utiliza un try-catch genérico como red de seguridad.
     */
    public boolean ejecutarMenuCliente(Cliente cliente, String opcion) {
        try { // <- Red de seguridad contra NullPointer u otros RuntimeExceptions
            switch (opcion) {
                case "1" -> ejecutarCU003_SolicitarCambio(cliente);
                case "0" -> {
                    System.out.println("Cerrando sesión. Gracias.");
                    return true; // Indica al bucle principal que el sistema debe salir.
                }
                default -> System.out.println("Opción no válida.");
            }
        } catch (Exception e) {
            manejarErrorNoPrevisto(e);
        }
        return false;
    }
    
    /*
     * Ejecuta la opción seleccionada por el Operario.
     * Utiliza un try-catch genérico como red de seguridad.
     */
    public boolean ejecutarMenuOperario(Operario operario, String opcion) {
        try { // <- Red de seguridad contra NullPointer u otros RuntimeExceptions
            switch (opcion) {
                case "1" -> ejecutarCU002_AgregarMedidor();
                case "2" -> ejecutarCU004_ActivarServicio(operario);
                case "0" -> {
                    System.out.println("Cerrando sesión. Gracias.");
                    return true; // Indica al bucle principal que el sistema debe salir.
                }
                default -> {
                    System.out.println("Opción no válida.");
                }
            }
        } catch (Exception e) {
            manejarErrorNoPrevisto(e);
        }
        return false;
    }

    // =============================================================
    // IMPLEMENTACIÓN DE CASOS DE USO (I/O y Llamadas a Controladores)
    // =============================================================

    /**
     * CU002: Agregar Medidor.
     * Recoge todos los datos del medidor y el cliente asociado.
     * Maneja NumberFormatException para entradas no numéricas.
     */
    private void ejecutarCU002_AgregarMedidor() {
        System.out.println("\n--- CU002: AGREGAR MEDIDOR ---");
        
        try { // <- Manejo específico para errores de conversión de tipos (I/O)
            System.out.print("ID del Nuevo Medidor: ");
            int idMedidor = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Tipo de Medidor: ");
            String tipo = scanner.nextLine();
            
            System.out.print("Marca del Medidor: ");
            String marca = scanner.nextLine();
            
            System.out.print("Ubicación exacta (Ej. Calle/Nro, Depto): ");
            String ubicacion = scanner.nextLine();
            
            System.out.print("Año de Fabricación (Ej. 2024): ");
            int anioFabricacion = Integer.parseInt(scanner.nextLine());
            
            System.out.print("NIS del Cliente a Asociar (Ej. 789012345): ");
            int nisCliente = Integer.parseInt(scanner.nextLine());

            // Verifica si el cliente existe antes de continuar.
            Cliente clienteAsociar = simuladorDeBD.getClientePorNIS(nisCliente);
            if (clienteAsociar == null) {
                System.out.println("Error: No se encontró un Cliente con el NIS " + nisCliente + ".");
                return;
            }

            EstadoDeMedidor estadoInicial = simuladorDeBD.getEstadoMedidorPorNombre("Activo");
            Date fechaInstalacion = new Date();
            
            // Llama a la lógica del controlador para procesar la creación.
            boolean exito = controlAgregarMedidor.agregarMedidor(
                idMedidor, tipo, marca, ubicacion, fechaInstalacion, anioFabricacion, estadoInicial, clienteAsociar
            );

            if (exito) {
                System.out.println("CU002 Completado con éxito: Medidor #" + idMedidor + " agregado.");
                mostrarListaMedidores(simuladorDeBD.getMedidorPorID(idMedidor));
            } else {
                System.out.println("CU002 Fallido: Verifique los datos ingresados.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Debe ingresar un número para ID, Año de Fabricación o NIS.");
        }
    }

    /**
     * CU003: Solicitar cambio de Estado de Servicio.
     * Permite al cliente solicitar un cambio (Activación, Suspensión, Baja).
     */
    private void ejecutarCU003_SolicitarCambio(Cliente cliente) {
        System.out.println("\n--- CU003: SOLICITAR CAMBIO DE ESTADO ---");
        
        Medidor medidor = simuladorDeBD.getMedidorAsociadoACliente(cliente);
        if (medidor == null) {
            System.out.println("Error: No tiene un medidor asociado para solicitar un cambio.");
            return;
        }
        
        // Lógica de I/O para seleccionar el tipo de solicitud deseado.
        System.out.println("Estado actual de su servicio: " + medidor.getEstadoDeMedidor().getNombre());
        String estadoActualReportado = medidor.getEstadoDeMedidor().getNombre();

        System.out.println("Seleccione el tipo de Solicitud (Estado deseado):");
        System.out.println("    [1] Activacion, [2] Suspension, [3] Baja");
        System.out.print("    Opción (Ingrese el número 1, 2 o 3): ");
        String opcionTipo = scanner.nextLine();
        String tipoSolicitudDeseada;

        // Mapea la opción del usuario a un nombre de solicitud.
        switch (opcionTipo) {
            case "1" -> tipoSolicitudDeseada = "Activacion";
            case "2" -> tipoSolicitudDeseada = "Suspension";
            case "3" -> tipoSolicitudDeseada = "Baja";
            default -> {
                System.out.println("Opción de tipo de solicitud inválida. Operación cancelada.");
                return;
            }
        }

        System.out.print("Ingrese un comentario/motivo de la solicitud (al menos 10 caracteres): ");
        String descripcion = scanner.nextLine();
        
        // Llama a la lógica del controlador para procesar la solicitud.
        boolean exito = controlSolicitudCambio.procesarSolicitudCambio(
            cliente, estadoActualReportado, tipoSolicitudDeseada, descripcion
        );

        if (exito) {
            System.out.println("CU003 Completado: Solicitud de " + tipoSolicitudDeseada + " realizada con éxito.");
            mostrarListaSolicitudesPendientes(cliente);
        } else {
            System.out.println("CU003 Fallido: Ya existe una solicitud pendiente o la validación falló.");
        }
    }

    /**
     * CU004: Activar Servicio / Gestionar Solicitudes.
     * Permite al operario filtrar, seleccionar y cambiar el estado de un medidor.
     * Maneja NumberFormatException para entradas no numéricas.
     */
    private void ejecutarCU004_ActivarServicio(Operario operario) {
        System.out.println("\n--- CU004: GESTIONAR SOLICITUDES ---");
        
        try { // <- Manejo específico para errores de conversión de tipos (I/O)
            // Lógica para filtrar solicitudes pendientes.
            System.out.print("Ingrese NIS para filtrar (Dejar vacío para ver todas las Pendientes): ");
            String nisFiltro = scanner.nextLine();
            
            List<SolicitudDeServicio> solicitudesPendientes;
            if (nisFiltro.isEmpty()) {
                // Obtiene y filtra todas las solicitudes pendientes.
                solicitudesPendientes = simuladorDeBD.getTodasLasSolicitudes().stream()
                    .filter(s -> s.getEstadoDeSolicitudDeServicio().getNombre().equalsIgnoreCase("Pendiente"))
                    .collect(Collectors.toList());
            } else {
                // Llama al controlador para filtrar por NIS (si el filtro no está vacío).
                solicitudesPendientes = controlGestionarServicio.filtrarSolicitudesPorNIS(nisFiltro);
            }

            if (solicitudesPendientes.isEmpty()) {
                System.out.println("No se encontraron solicitudes pendientes bajo ese filtro.");
                return;
            }

            // Muestra la lista de solicitudes numerada.
            System.out.println("\nSolicitudes Pendientes encontradas:");
            int i = 1;  
            for (SolicitudDeServicio s : solicitudesPendientes) {
                System.out.printf("[%d] ID: %d, Cliente: %s (NIS %d), Tipo: %s\n", 
                    i++, s.getIdSolicitud(), s.getCliente().getApellido(), s.getCliente().getNIS(), s.getTipoDeSolicitud().getNombre());
            }
            
            // El operario elige una solicitud por número.
            System.out.print("Ingrese el número de la solicitud a gestionar: ");
            int indiceElegido = Integer.parseInt(scanner.nextLine());
            
            int indiceLista = indiceElegido - 1;  
            
            if (indiceLista < 0 || indiceLista >= solicitudesPendientes.size()) {
                System.out.println("Selección inválida.");
                return;
            }
            
            SolicitudDeServicio solicitudAProcesar = solicitudesPendientes.get(indiceLista);
            Medidor medidorAsociado = controlGestionarServicio.obtenerMedidorAsociado(solicitudAProcesar);
            
            // Verifica la existencia del medidor asociado.
            if (medidorAsociado == null) {
                System.out.println("Error: Medidor asociado a la solicitud no encontrado. No se puede proceder.");
                return;
            }

            // Muestra las opciones de estado de medidor disponibles para el cambio.
            List<EstadoDeMedidor> estadosDisponibles = simuladorDeBD.getTodosLosEstadosDeMedidor();  
            
            System.out.println("\nSeleccione el NUEVO Estado del Medidor #" + medidorAsociado.getIdMedidor() + ":");
            int j = 1;
            for (EstadoDeMedidor estado : estadosDisponibles) {
                System.out.printf("[%d] %s\n", j++, estado.getNombre());
            }
            System.out.print("Ingrese el número del nuevo estado: ");
            int opcionEstado = Integer.parseInt(scanner.nextLine());
            
            if (opcionEstado < 1 || opcionEstado > estadosDisponibles.size()) {
                System.out.println("Selección de estado inválida. Operación cancelada.");
                return;
            }
            
            String estadoDeseado = estadosDisponibles.get(opcionEstado - 1).getNombre();  

            // Pide confirmación final.
            System.out.print("¿CONFIRMA la aplicación del cambio y la finalización de la Solicitud? (s/n): ");
            String confirmacion = scanner.nextLine();
            if (!confirmacion.equalsIgnoreCase("s")) {
                System.out.println("Gestión cancelada por el operario.");
                return;
            }

            // Llama a la lógica del controlador para aplicar el cambio y finalizar la solicitud.
            boolean exito = controlGestionarServicio.procesarCambioDeServicio(
                solicitudAProcesar, medidorAsociado, estadoDeseado, operario
            );
            
            if (exito) {
                System.out.println("CU004 Completado: Medidor actualizado y Solicitud finalizada.");
                mostrarVerificacionPostProceso(medidorAsociado, solicitudAProcesar);
            } else {
                System.out.println("CU004 Fallido. El estado no pudo ser actualizado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Debe ingresar un número válido para las opciones o el filtro NIS.");
        }
    }
    
    // =============================================================
    // MÉTODOS AUXILIARES DE COMPROBACIÓN (MOSTRAR DATOS)
    // =============================================================
    
    /** Muestra el detalle del medidor agregado. */
    private void mostrarListaMedidores(Medidor medidorAgregado) {
        if (medidorAgregado == null) return;
        System.out.println("\n--- COMPROBACIÓN: LISTA DEL MEDIDOR AGREGADO ---");
        System.out.printf("| ID | TIPO | MARCA | CLIENTE (NIS) | ESTADO |\n");
        System.out.printf("| %-2d | %-4s | %-5s | %s (%d) | %s |\n", 
            medidorAgregado.getIdMedidor(), 
            medidorAgregado.getTipo(), 
            medidorAgregado.getMarca(),
            medidorAgregado.getCliente().getApellido(),
            medidorAgregado.getCliente().getNIS(),
            medidorAgregado.getEstadoDeMedidor().getNombre()
        );
    }
    
    /** Muestra las solicitudes pendientes de un cliente. */
    private void mostrarListaSolicitudesPendientes(Cliente cliente) {
        List<SolicitudDeServicio> pendientes = simuladorDeBD.getSolicitudesPendientesPorCliente(cliente);
        System.out.println("\n--- COMPROBACIÓN: SOLICITUDES PENDIENTES DE " + cliente.getApellido() + " ---");
        if (pendientes.isEmpty()) {
            System.out.println("No tiene solicitudes pendientes.");
            return;
        }
        System.out.printf("| ID | CLIENTE (NIS) | TIPO DE SOLICITUD | ESTADO |\n");
        for (SolicitudDeServicio s : pendientes) {
            System.out.printf("| %-2d | %s (%d) | %s | %s |\n", 
                s.getIdSolicitud(), 
                s.getCliente().getApellido(), 
                s.getCliente().getNIS(),
                s.getTipoDeSolicitud().getNombre(),
                s.getEstadoDeSolicitudDeServicio().getNombre()
            );
        }
    }
    
    /** Muestra el estado final de los objetos tras el CU004. */
    private void mostrarVerificacionPostProceso(Medidor medidor, SolicitudDeServicio solicitud) {
        System.out.println("\n--- VERIFICACIÓN POST-PROCESO ---");
        System.out.println("Medidor #" + medidor.getIdMedidor() + " -> Nuevo Estado: " + medidor.getEstadoDeMedidor().getNombre());
        System.out.println("Solicitud #" + solicitud.getIdSolicitud() + " -> Estado Final: " + solicitud.getEstadoDeSolicitudDeServicio().getNombre());
    }
}
