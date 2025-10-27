/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica.controladores;

import sistema.de.gestion.electrica.SimuladorDeBD;
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
    
    private final SimuladorDeBD simuladorDeBD; // Referencia a la capa de datos (dependencia).
    
    /*
     * Constructor que recibe la dependencia del Simulador de Base de Datos (Inyección de Dependencias).
     */
    public ControladorAgregarMedidor (SimuladorDeBD simuladorDeBD){
        this.simuladorDeBD = simuladorDeBD;
    }
    
    /*
     * Procesa la solicitud para crear y persistir un nuevo Medidor.
     * Incluye todas las validaciones de negocio.
     * * @return true si el medidor fue agregado con éxito, false en caso de fallo de validación.
     */
    public boolean agregarMedidor(int idMedidor, String tipo, String marca, String ubicacion, Date fechaDeInstalacion, int anioDeFabricacion, EstadoDeMedidor estadoDeMedidor, Cliente cliente){
        
        // 1. Validación de existencia (regla de unicidad de ID)
        if (this.simuladorDeBD.getMedidorPorID(idMedidor) != null) {
            System.out.println("Validación fallida: Ya existe un Medidor con el ID " + idMedidor + ".");
            return false;
        }

        // 2. Validación de campos obligatorios (control de nulos o vacíos)
        if (tipo == null || tipo.trim().isEmpty() || marca == null || marca.trim().isEmpty() || ubicacion == null || ubicacion.trim().isEmpty()) {
            System.out.println("Validación fallida: Tipo, Marca o Ubicación no pueden estar vacíos.");
            return false;
        }
        
        // Lógica para obtener el año de la fecha de instalación
        Calendar calInstalacion = Calendar.getInstance();
        calInstalacion.setTime(fechaDeInstalacion);
        int anioInstalacion = calInstalacion.get(Calendar.YEAR);
        
        // 3. Validación de consistencia de fechas (regla de negocio)
        if (anioInstalacion < anioDeFabricacion) {
            System.out.println("Validación fallida: La Fecha de Instalación (" + anioInstalacion + ") no puede ser anterior al Año de Fabricación (" + anioDeFabricacion + ").");
            return false;
        }

        // 4. Creación del objeto Medidor
        // Se inicializa la lectura actual en 0.0 y la fecha de última lectura como la fecha actual.
        Medidor nuevoMedidor = new Medidor(idMedidor, tipo, marca, ubicacion, 0.0, new Date(), fechaDeInstalacion, anioDeFabricacion, estadoDeMedidor, cliente);

        // 5. Persistencia del objeto (llama al SimuladorDeBD para guardarlo)
        this.simuladorDeBD.agregarMedidor(nuevoMedidor); 
        
        System.out.println("ÉXITO: El Medidor #" + idMedidor + " fue agregado al inventario.");

        return true;
    }
    
}
