/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica.controladores;
import java.sql.SQLException;
import sistema.de.gestion.electrica.RepositorioDAO;
import sistema.de.gestion.electrica.Medidor;
import sistema.de.gestion.electrica.EstadoDeMedidor;
import sistema.de.gestion.electrica.Cliente;
import java.util.Date;
import java.util.Calendar;

/**
 * Clase ControladorAgregarMedidor.
 * Contiene la lógica de negocio (reglas) para el caso de uso: Agregar Nuevo Medidor.
 * No realiza I/O, solo recibe datos y llama a la capa de persistencia (SimuladorDeBD).
 */
public class ControladorAgregarMedidor {
    
    // Cambiamos el nombre de 'simuladorDeBD' a 'repositorioDAO' para reflejar el cambio.
    private final RepositorioDAO repositorioDAO; // Referencia al objeto de acceso a datos (DAO).
    
    // Constructor que inyecta la dependencia del repositorio.
    public ControladorAgregarMedidor (RepositorioDAO repositorioDAO){
        this.repositorioDAO = repositorioDAO;
    }
    
    /*
     * Procesa la solicitud para crear y persistir un nuevo Medidor.
     * @return true si el medidor fue agregado con éxito, false en caso de fallo de validación.
     * @throws SQLException Propaga errores de la base de datos al GestorDeInterfaz.
     */
    public boolean agregarMedidor(int idMedidor, String tipo, String marca, String ubicacion, Date fechaDeInstalacion, int anioDeFabricacion, EstadoDeMedidor estadoDeMedidor, Cliente cliente) throws SQLException {
        

        // 2. Validación de campos obligatorios 
        if (tipo == null || tipo.trim().isEmpty() || marca == null || marca.trim().isEmpty() || ubicacion == null || ubicacion.trim().isEmpty()) {
            System.out.println("Validación fallida: Tipo, Marca o Ubicación no pueden estar vacíos.");
            return false; // Detiene la ejecución si faltan campos obligatorios.
        }
        
        // Lógica para obtener el año de la fecha de instalación
        Calendar calInstalacion = Calendar.getInstance(); // Obtiene una instancia de Calendar.
        calInstalacion.setTime(fechaDeInstalacion); // Establece la fecha de instalación.
        int anioInstalacion = calInstalacion.get(Calendar.YEAR); // Extrae el año.
        
        // 3. Validación de consistencia de fechas (regla de negocio)
        // La fecha de instalación no puede ser anterior al año en que se fabricó el medidor.
        if (anioInstalacion < anioDeFabricacion) {
            System.out.println("Validación fallida: La Fecha de Instalación (" + anioInstalacion + ") no puede ser anterior al Año de Fabricación (" + anioDeFabricacion + ").");
            return false; // Detiene la ejecución si la regla de negocio falla.
        }

        // 4. Creación del objeto Medidor
        // Se asume un valor inicial de 0.0 para la lectura y la fecha actual para la última lectura.
        Medidor nuevoMedidor = new Medidor(0, tipo, marca, ubicacion, 0.0, new Date(), fechaDeInstalacion, anioDeFabricacion, estadoDeMedidor, cliente);

        // 5. Persistencia del objeto 
        this.repositorioDAO.agregarMedidor(nuevoMedidor); // Llama al DAO para guardar el nuevo medidor en el repositorio.
        
        System.out.println("ÉXITO: El Medidor #" + idMedidor + " fue agregado al inventario.");

        return true; // Retorna verdadero indicando el éxito de la operación.
    }
}