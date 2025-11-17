/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package sistema.de.gestion.electrica.controladores;
import sistema.de.gestion.electrica.RepositorioDAO;
import sistema.de.gestion.electrica.Cliente;
import sistema.de.gestion.electrica.Medidor;
import sistema.de.gestion.electrica.SolicitudDeServicio;
import sistema.de.gestion.electrica.EstadoDeSolicitudDeServicio;
import sistema.de.gestion.electrica.TipoDeSolicitud;
import java.sql.SQLException;



/**
 * Clase ControladorSolicitudCambio.
 * Implementa la lógica de negocio para registrar una Solicitud de Cambio de Servicio por un Cliente.
 */
public class ControladorSolicitudCambio {
    
    // Cambiamos el nombre de 'simuladorDeBD' a 'repositorioDAO' para reflejar el cambio.
    private final RepositorioDAO repositorioDAO; // Referencia al objeto de acceso a datos.
    
    public ControladorSolicitudCambio(RepositorioDAO repositorioDAO){
        this.repositorioDAO = repositorioDAO;
    }
    
    /*
     * Procesa y registra una nueva Solicitud de Cambio de Servicio.
     * @return true si la solicitud fue registrada, false si falló alguna validación.
     * @throws SQLException Propaga errores de la base de datos al GestorDeInterfaz.
     */
    public boolean procesarSolicitudCambio(Cliente cliente, String nombreEstadoActual, String nombreTipoSolicitud, String descripcion) throws SQLException {
        
        // 1. Obtener el medidor asociado al cliente para validaciones.
        // ASUMIMOS que el DAO ahora recibe el ID del cliente, no el objeto completo.
        Medidor medidorAsociado = this.repositorioDAO.getMedidorAsociadoACliente(cliente.getIdUsuario());
        
        // 2. Validación: El cliente debe tener un medidor asociado.
        if (medidorAsociado == null) {
            System.out.println("Validación fallida: El Cliente no tiene un medidor asociado para solicitar un cambio.");
            return false; // Detiene la solicitud si no hay medidor.
        }

        // 3. Validación: El estado reportado debe coincidir con el estado actual del medidor.
        if (!medidorAsociado.getEstadoDeMedidor().getNombre().equalsIgnoreCase(nombreEstadoActual)) {
            System.out.println("Validación fallida: El estado actual reportado no coincide con el estado real del medidor.");
            return false; // Detiene la solicitud si el estado es inconsistente.
        }

        // 4. Obtener el objeto TipoDeSolicitud (ej: Activacion) desde la BD.
        TipoDeSolicitud tipoDeSolicitud = this.repositorioDAO.getTipoSolicitudPorNombre(nombreTipoSolicitud);

        // 5. Validación: El tipo de solicitud debe ser válido y existir.
        if (tipoDeSolicitud == null) {
            System.out.println("Validación fallida: El tipo de solicitud '" + nombreTipoSolicitud + "' no es válido o no existe.");
            return false;
        }
        
        // 6. Validación: La descripción debe cumplir con una longitud mínima (10 caracteres).
        if (descripcion == null || descripcion.trim().length() < 10) {
            System.out.println("Validación fallida: Debe proporcionar un comentario descriptivo de al menos 10 caracteres.");
            return false;
        }
        
        // 7. Obtener el estado inicial de la solicitud ("Pendiente") desde la BD.
        EstadoDeSolicitudDeServicio estadoInicialSolicitud = this.repositorioDAO.getEstadoSolicitudPorNombre("Pendiente");

        if (estadoInicialSolicitud == null) {
             System.out.println("ERROR INTERNO: No se pudo obtener el estado inicial 'Pendiente'.");
             return false;
        }

        // 8. Generar un ID único para la nueva solicitud (el DAO debe manejar esto, generalmente con AUTO_INCREMENT)
        // Usaremos el método del DAO que calcula el siguiente ID.
        int idSolicitud = this.repositorioDAO.generarIdSolicitud(); // Pide al DAO que genere un ID.

        // 9. Crear el objeto SolicitudDeServicio.
        SolicitudDeServicio nuevaSolicitud = new SolicitudDeServicio(
            idSolicitud,
            descripcion,
            cliente,
            medidorAsociado.getEstadoDeMedidor(), // Estado del medidor al momento de solicitar.
            tipoDeSolicitud, // Tipo de cambio solicitado.
            estadoInicialSolicitud // Estado por defecto: "Pendiente".
        );

        // 10. Persistir la nueva solicitud en la Base de Datos.
        this.repositorioDAO.agregarSolicitud(nuevaSolicitud); // Llama al DAO para guardar el objeto.

        // 11. Muestra un mensaje de confirmación al usuario.
        System.out.println("ÉXITO: Solicitud de cambio #" + idSolicitud + " registrada.");
        System.out.println("Tipo: " + tipoDeSolicitud.getNombre() + ". Estado De Solicitud: " + estadoInicialSolicitud.getNombre() + ".");

        return true; // Retorna verdadero indicando el éxito de la operación.
    }
}