/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica.controladores;
import java.sql.SQLException;
import sistema.de.gestion.electrica.RepositorioDAO;
import sistema.de.gestion.electrica.SolicitudDeServicio;
import sistema.de.gestion.electrica.Medidor;
import sistema.de.gestion.electrica.Cliente;
import sistema.de.gestion.electrica.EstadoDeMedidor;
import sistema.de.gestion.electrica.EstadoDeSolicitudDeServicio;
import sistema.de.gestion.electrica.Operario;
import sistema.de.gestion.electrica.Servicio;
import java.util.Date;
import java.util.List;


/*
 * Clase ControladorGestionarServicio.
 * Contiene la lógica de negocio para gestionar y procesar las Solicitudes de Servicio.
 * Es usado principalmente por el Operario.
 */
public class ControladorGestionarServicio {

    

    // Cambiamos el nombre de 'simuladorDeBD' a 'repositorioDAO' para reflejar el cambio.

    private final RepositorioDAO repositorioDAO; // Referencia al objeto de acceso a datos.



    public ControladorGestionarServicio(RepositorioDAO repositorioDAO) {

        this.repositorioDAO = repositorioDAO;

    }

    
    /*
     * Obtiene y retorna la lista completa de todas las solicitudes registradas.
     * @throws SQLException Propaga errores de la base de datos.
     */
    public List<SolicitudDeServicio> obtenerTodasLasSolicitudes() throws SQLException {

        System.out.println("Mostrando todas las Solicitudes registradas.");

        return this.repositorioDAO.getSolicitudesPendientes(); // Delega la obtención al repositorio.

    }

    
    /*
     * Busca solicitudes pendientes asociadas a un cliente, identificado por su NIS.
     * @throws SQLException Propaga errores de la base de datos.
     */
    public List<SolicitudDeServicio> filtrarSolicitudesPorNIS(String nisString) throws SQLException {

        try {

            int nis = Integer.parseInt(nisString); // Intenta convertir el NIS de String a int.

            Cliente cliente = this.repositorioDAO.getClientePorNIS(nis); // Busca el cliente por NIS.



            if (cliente == null) {

                System.out.println("ERROR: No se encontró ningún Cliente con el NIS: " + nisString);

                return null; // Retorna nulo si no se encuentra el cliente.

            }



            // Delega la búsqueda de solicitudes pendientes al Repositorio

            // ASUMIMOS que el DAO ahora recibe el ID del cliente, no el objeto completo.

            List<SolicitudDeServicio> solicitudesPendientes = this.repositorioDAO.getSolicitudesPendientesPorCliente(cliente.getIdUsuario());

            
            System.out.println("Mostrando " + solicitudesPendientes.size() + " solicitudes pendientes para el NIS: " + nisString);

            return solicitudesPendientes; // Retorna la lista de solicitudes pendientes.



        } catch (NumberFormatException e) {

            // Maneja el error de formato internamente, ya que no es un error de BD

            System.out.println("ERROR: El valor ingresado para el NIS no es un número válido.");

            return null; // Retorna nulo si el formato del NIS es incorrecto.

        }

    }

    
    /*
     * Obtiene el medidor actualmente asociado al cliente de la solicitud.
     * @throws SQLException Propaga errores de la base de datos.
     */
    public Medidor obtenerMedidorAsociado(SolicitudDeServicio solicitud) throws SQLException {

        if (solicitud == null || solicitud.getCliente() == null) {

            return null; // Validación de nulos.

        }

        // Busca el Medidor en la BD que esté enlazado al cliente de la solicitud.

        // ASUMIMOS que el DAO ahora recibe el ID del cliente, no el objeto completo.

        Medidor medidor = this.repositorioDAO.getMedidorAsociadoACliente(solicitud.getCliente().getIdUsuario());

        
        if (medidor != null) {

               System.out.println("Cargada información de Medidor #" + medidor.getIdMedidor() + ".");

        }

        return medidor; // Retorna el medidor asociado o nulo.

    }



    /*
     * Ejecuta el proceso de cambio de estado de un servicio.
     * @throws SQLException Propaga errores de la base de datos.
     */
    public boolean procesarCambioDeServicio(
        SolicitudDeServicio solicitud,
        Medidor medidor,
        String nuevoEstadoMedidor,
        Operario operario
    ) throws SQLException {
        // ... (Validación de datos básicos) ...
        if (solicitud == null || medidor == null || nuevoEstadoMedidor == null || operario == null) {
            System.out.println("ERROR: Datos de entrada incompletos para procesar el cambio de servicio.");
            return false; // Validación de datos de entrada.
        }
        
        // 1. Obtener el objeto del nuevo Estado del Medidor
        EstadoDeMedidor estadoActivo = this.repositorioDAO.getEstadoMedidorPorNombre(nuevoEstadoMedidor); // Busca el nuevo estado en la BD.
        
        if (estadoActivo == null) {
            System.out.println("ERROR: El nuevo estado de medidor '" + nuevoEstadoMedidor + "' no existe en la BD.");
            return false;
        }

        // 2. Aplicar el cambio de estado del Medidor (Operación de dominio)
        operario.cambiarEstadoMedidor(medidor, estadoActivo); // El operario ejecuta la acción sobre el medidor.
        
        // 3. Si se está activando, actualizar la fecha de inicio del Servicio
        if (nuevoEstadoMedidor.equalsIgnoreCase("Activación")) { // Verifica si el cambio implica una activación.
            Servicio servicioAsociado = this.repositorioDAO.getServicioPorMedidor(medidor.getIdMedidor());

            if (servicioAsociado != null) {
                servicioAsociado.setFechaActivacion(new Date()); // Establece la fecha de activación actual.
                // PERSISTENCIA DEL SERVICIO
                this.repositorioDAO.actualizarServicio(servicioAsociado); // Guarda el cambio en el servicio.
                System.out.println("Fecha de Activación de Servicio actualizada.");
            } else {
                System.out.println("ADVERTENCIA: No se encontró el Servicio asociado.");
            }
        }
        
        // Actualiza la fecha del Medidor para registrar el momento del cambio
        medidor.setFechaActual(new Date()); // Actualiza la fecha de última lectura/estado del medidor.
        
        // PERSISTENCIA DEL MEDIDOR
        this.repositorioDAO.actualizarMedidor(medidor); // Guarda los cambios realizados en el medidor.

        // 4. Finalizar la Solicitud (cambiar su estado)
        EstadoDeSolicitudDeServicio estadoFinalizada = this.repositorioDAO.getEstadoSolicitudPorNombre("Finalizada"); // Obtiene el objeto 'Finalizada'.

        if (estadoFinalizada == null) {
            System.out.println("ERROR: No se pudo obtener el estado 'Finalizada' para la Solicitud.");
            return false;
        }

        // Actualizar el estado de la Solicitud y persistir el cambio
        solicitud.actualizarEstado(estadoFinalizada); // Cambia el estado de la solicitud.
        this.repositorioDAO.actualizarSolicitud(solicitud); // Persiste el estado de la solicitud.
        
        System.out.println("ÉXITO: Solicitud #" + solicitud.getIdSolicitud() + " completada y Medidor actualizado a '" + nuevoEstadoMedidor + "'.");
        return true; // Retorna verdadero indicando el éxito del proceso.
    }

}
