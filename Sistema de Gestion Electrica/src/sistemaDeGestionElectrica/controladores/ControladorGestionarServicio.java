/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica.controladores;

import sistema.de.gestion.electrica.SimuladorDeBD;
import sistema.de.gestion.electrica.SolicitudDeServicio;
import sistema.de.gestion.electrica.Medidor;
import sistema.de.gestion.electrica.Cliente;
import sistema.de.gestion.electrica.EstadoDeMedidor;
import sistema.de.gestion.electrica.EstadoDeSolicitudDeServicio;
import sistema.de.gestion.electrica.Operario;
import sistema.de.gestion.electrica.Servicio;
import java.util.Date;
import java.util.List;


/**
 * Clase ControladorGestionarServicio.
 * Contiene la lógica de negocio para gestionar y procesar las Solicitudes de Servicio.
 * Es usado principalmente por el Operario.
 */
public class ControladorGestionarServicio {
    
    private final SimuladorDeBD simuladorDeBD; // Referencia a la capa de datos (dependencia).

    /*
     * Constructor que recibe la dependencia del Simulador de Base de Datos.
     */
    public ControladorGestionarServicio(SimuladorDeBD simuladorDeBD) {
        this.simuladorDeBD = simuladorDeBD;
    }
    
    /*
     * Obtiene y retorna la lista completa de todas las solicitudes registradas.
     */
    public List<SolicitudDeServicio> obtenerTodasLasSolicitudes() {
        System.out.println("Mostrando todas las Solicitudes registradas.");
        return this.simuladorDeBD.getTodasLasSolicitudes(); // Delega la obtención a la capa de datos.
    }
    
    /*
     * Busca solicitudes pendientes asociadas a un cliente, identificado por su NIS.
     * @param nisString Número de Identificación de Suministro (NIS) como cadena.
     * @return Lista de solicitudes pendientes del cliente o null si hay error.
     */
    public List<SolicitudDeServicio> filtrarSolicitudesPorNIS(String nisString) {
        try {
            int nis = Integer.parseInt(nisString); // Convierte el NIS a entero.
            Cliente cliente = this.simuladorDeBD.getClientePorNIS(nis); // Busca el cliente.

            if (cliente == null) {
                System.out.println("ERROR: No se encontró ningún Cliente con el NIS: " + nisString);
                return null;
            }

            // Delega la búsqueda de solicitudes pendientes al Simulador
            List<SolicitudDeServicio> solicitudesPendientes = this.simuladorDeBD.getSolicitudesPendientesPorCliente(cliente);
            
            System.out.println("Mostrando " + solicitudesPendientes.size() + " solicitudes pendientes para el NIS: " + nisString);
            return solicitudesPendientes;

        } catch (NumberFormatException e) {
            System.out.println("ERROR: El valor ingresado para el NIS no es un número válido.");
            return null;
        }
    }
    
    /*
     * Obtiene el medidor actualmente asociado al cliente de la solicitud.
     */
    public Medidor obtenerMedidorAsociado(SolicitudDeServicio solicitud) {
        if (solicitud == null || solicitud.getCliente() == null) {
            return null;
        }
        // Busca el Medidor en la BD que esté enlazado al cliente de la solicitud.
        Medidor medidor = this.simuladorDeBD.getMedidorAsociadoACliente(solicitud.getCliente());
        
        if (medidor != null) {
             System.out.println("Cargada información de Medidor #" + medidor.getIdMedidor() + ".");
        }
        return medidor;
    }

    /*
     * Ejecuta el proceso de cambio de estado de un servicio (ej. Activación o Suspensión).
     * @param nuevoEstadoMedidor Nombre del estado al que debe cambiar el medidor ("Activo", "Suspendido", etc.).
     * @return true si el proceso fue completado con éxito, false si falló alguna validación o paso.
     */
    public boolean procesarCambioDeServicio(
        SolicitudDeServicio solicitud,
        Medidor medidor,
        String nuevoEstadoMedidor,
        Operario operario
    ) {
        // Validación de datos básicos de entrada
        if (solicitud == null || medidor == null || nuevoEstadoMedidor == null || operario == null) {
            System.out.println("ERROR: Datos de entrada incompletos para procesar el cambio de servicio.");
            return false;
        }
        
        // 1. Obtener el objeto del nuevo Estado del Medidor
        EstadoDeMedidor estadoActivo = this.simuladorDeBD.getEstadoMedidorPorNombre(nuevoEstadoMedidor);
        
        if (estadoActivo == null) {
            System.out.println("ERROR: El nuevo estado de medidor '" + nuevoEstadoMedidor + "' no existe en la BD.");
            return false;
        }

        // 2. Aplicar el cambio de estado del Medidor (Operación de dominio)
        operario.cambiarEstadoMedidor(medidor, estadoActivo);

        // 3. Si se está activando, actualizar la fecha de inicio del Servicio
        if (nuevoEstadoMedidor.equalsIgnoreCase("Activo")) {

            Servicio servicioAsociado = this.simuladorDeBD.getServicioPorMedidor(medidor);

            if (servicioAsociado != null) {
                servicioAsociado.setFechaActivacion(new Date()); // Registra la fecha actual de activación.
                System.out.println("Fecha de Activación de Servicio actualizada.");
            } else {
                System.out.println("ADVERTENCIA: No se encontró el Servicio asociado.");
            }
        }
        
        // Actualiza la fecha del Medidor para registrar el momento del cambio
        medidor.setFechaActual(new Date()); 

        // 4. Finalizar la Solicitud (cambiar su estado)
        
        // Obtener el objeto EstadoDeSolicitudDeServicio "Finalizada"
        EstadoDeSolicitudDeServicio estadoFinalizada = this.simuladorDeBD.getEstadoSolicitudPorNombre("Finalizada");

        if (estadoFinalizada == null) {
            System.out.println("ERROR: No se pudo obtener el estado 'Finalizada' para la Solicitud.");
            return false;
        }

        // Actualizar el estado de la Solicitud y persistir el cambio
        solicitud.actualizarEstado(estadoFinalizada);
        this.simuladorDeBD.actualizarSolicitud(solicitud); 
        
        return true;
    }
    
}
