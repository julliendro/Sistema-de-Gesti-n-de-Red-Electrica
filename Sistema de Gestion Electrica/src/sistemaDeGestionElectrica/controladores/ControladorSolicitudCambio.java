/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica.controladores;
import sistema.de.gestion.electrica.SimuladorDeBD;
import sistema.de.gestion.electrica.Cliente;
import sistema.de.gestion.electrica.Medidor;
import sistema.de.gestion.electrica.SolicitudDeServicio;
import sistema.de.gestion.electrica.EstadoDeSolicitudDeServicio;
import sistema.de.gestion.electrica.TipoDeSolicitud;


/**
 * Clase ControladorSolicitudCambio.
 * Implementa la lógica de negocio para registrar una Solicitud de Cambio de Servicio por un Cliente.
 */
public class ControladorSolicitudCambio {
    
    private final SimuladorDeBD simuladorDeBD; // Referencia a la capa de persistencia (Base de Datos).
    
    /*
     * Constructor que inyecta la dependencia del Simulador de Base de Datos.
     */
    public ControladorSolicitudCambio(SimuladorDeBD simuladorDeBD){
        this.simuladorDeBD = simuladorDeBD;
    }
    
    /*
     * Procesa y registra una nueva Solicitud de Cambio de Servicio.
     * @return true si la solicitud fue registrada, false si falló alguna validación.
     */
    public boolean procesarSolicitudCambio(Cliente cliente, String nombreEstadoActual, String nombreTipoSolicitud, String descripcion) {
        
        // 1. Obtener el medidor asociado al cliente para validaciones.
        Medidor medidorAsociado = this.simuladorDeBD.getMedidorAsociadoACliente(cliente);
        
        // 2. Validación: El cliente debe tener un medidor asociado.
        if (medidorAsociado == null) {
            System.out.println("Validación fallida: El Cliente no tiene un medidor asociado para solicitar un cambio.");
            return false;
        }

        // 3. Validación: El estado reportado debe coincidir con el estado actual del medidor.
        if (!medidorAsociado.getEstadoDeMedidor().getNombre().equalsIgnoreCase(nombreEstadoActual)) {
            System.out.println("Validación fallida: El estado actual reportado no coincide con el estado real del medidor.");
            return false;
        }

        // 4. Obtener el objeto TipoDeSolicitud (ej: Activacion) desde la BD.
        TipoDeSolicitud tipoDeSolicitud = this.simuladorDeBD.getTipoSolicitudPorNombre(nombreTipoSolicitud);

        // 5. Validación: El tipo de solicitud debe ser válido y existir.
        if (tipoDeSolicitud == null) {
            System.out.println("Validación fallida: El tipo de solicitud '" + nombreTipoSolicitud + "' no es válido o no existe.");
            return false;
        }
        
        // 6. Validación: La descripción debe cumplir con una longitud mínima.
        if (descripcion == null || descripcion.trim().length() < 10) {
            System.out.println("Validación fallida: Debe proporcionar un comentario descriptivo de al menos 10 caracteres.");
            return false;
        }
        
        // 7. Obtener el estado inicial de la solicitud ("Pendiente") desde la BD.
        EstadoDeSolicitudDeServicio estadoInicialSolicitud = this.simuladorDeBD.getEstadoSolicitudPorNombre("Pendiente");

        if (estadoInicialSolicitud == null) {
             System.out.println("ERROR INTERNO: No se pudo obtener el estado inicial 'Pendiente'.");
             return false;
        }

        // 8. Generar un ID único para la nueva solicitud.
        int idSolicitud = this.simuladorDeBD.generarIdSolicitud();

        // 9. Crear el objeto SolicitudDeServicio.
        SolicitudDeServicio nuevaSolicitud = new SolicitudDeServicio(
            idSolicitud,
            descripcion,
            cliente,
            medidorAsociado.getEstadoDeMedidor(), // Estado del medidor al momento de solicitar.
            tipoDeSolicitud, // Tipo de cambio solicitado.
            estadoInicialSolicitud // Estado por defecto: "Pendiente".
        );

        // 10. Persistir la nueva solicitud en la Base de Datos simulada.
        this.simuladorDeBD.agregarSolicitud(nuevaSolicitud); 

        // 11. Muestra un mensaje de confirmación al usuario.
        System.out.println("ÉXITO: Solicitud de cambio #" + idSolicitud + " registrada.");
        System.out.println("Tipo: " + tipoDeSolicitud.getNombre() + ". Estado De Solicitud: " + estadoInicialSolicitud.getNombre() + ".");

        return true;
    }
    
}
