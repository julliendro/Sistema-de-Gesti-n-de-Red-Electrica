/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Clase SimuladorDeBD.
 * Simula la capa de persistencia (Base de Datos). 
 * Contiene colecciones (Listas) de todos los objetos de dominio y métodos para cargarlas.
 */
public class SimuladorDeBD {
    
    // Listas que representan las "tablas" de la Base de Datos
    private List <Cliente> clientes; // Almacena todos los usuarios tipo Cliente.
    private List <Operario> operarios; // Almacena todos los usuarios tipo Operario.
    private List <Medidor> medidores; // Almacena todos los medidores de energía.
    private List <SolicitudDeServicio> solicitudes; // Almacena las solicitudes de cambio de servicio.
    private List <Permisos> permisos; // Almacena los roles/permisos de los usuarios.
    private List <EstadoDeMedidor> estadoMedidor; // Almacena los estados posibles de un medidor (Activo, Suspendido...).
    private List <EstadoDeSolicitudDeServicio> estadoDeSolicitud; // Almacena los estados de una solicitud (Pendiente, Finalizada).
    private List <TipoDeSolicitud> tiposDeSolicitud; // Almacena los tipos de solicitud (Activacion, Suspension, Baja).
    private List <Factura> facturas; // Almacena las facturas generadas.
    private List <Servicio> infoServicio; // Almacena la información de servicio (relación Cliente-Medidor).
    private List <Pago> pagos; // Almacena los registros de pago de facturas.
    
    /**
     * Constructor del simulador. Inicializa las listas y carga los datos iniciales.
     */
    public SimuladorDeBD(){
        // Inicialización de todas las listas como ArrayList
        this.clientes = new ArrayList<>();
        this.operarios = new ArrayList<>();
        this.medidores = new ArrayList<>();
        this.solicitudes = new ArrayList<>();
        this.permisos = new ArrayList<>();
        this.estadoMedidor = new ArrayList<>();
        this.estadoDeSolicitud = new ArrayList<>();
        this.tiposDeSolicitud = new ArrayList<>();
        this.facturas = new ArrayList<>();
        this.infoServicio = new ArrayList<>();
        this.pagos = new ArrayList<>();
        
        cargarDatosIniciales(); // Llama al método para poblar las listas con datos de prueba.
    }
    
    /**
     * Carga todos los datos estáticos y de prueba en las listas.
     */
    private void cargarDatosIniciales(){
        
        // --- 1. Carga de Estados de Medidor ---
        EstadoDeMedidor eActivo = new EstadoDeMedidor(1, "Activo");
        EstadoDeMedidor eSuspendido = new EstadoDeMedidor(2, "Suspendido");
        EstadoDeMedidor eDeBaja = new EstadoDeMedidor(3, "De Baja");
        this.estadoMedidor.add(eActivo);
        this.estadoMedidor.add(eSuspendido);
        this.estadoMedidor.add(eDeBaja);
        
        // --- 2. Carga de Estados de Solicitud ---
        EstadoDeSolicitudDeServicio ePendiente = new EstadoDeSolicitudDeServicio(1, "Pendiente");
        EstadoDeSolicitudDeServicio eFinalizada = new EstadoDeSolicitudDeServicio(2, "Finalizada");
        this.estadoDeSolicitud.add(ePendiente);
        this.estadoDeSolicitud.add(eFinalizada);
        
        // --- 3. Carga de Tipos de Solicitud ---
        TipoDeSolicitud tActivacion = new TipoDeSolicitud(1, "Activacion");
        TipoDeSolicitud tSuspension = new TipoDeSolicitud(2, "Suspension");
        TipoDeSolicitud tBaja = new TipoDeSolicitud(3, "Baja");
        this.tiposDeSolicitud.add(tActivacion);
        this.tiposDeSolicitud.add(tSuspension);
        this.tiposDeSolicitud.add(tBaja);
        
        // --- 4. Carga de Permisos/Roles ---
        Permisos pCliente = new Permisos(1, "Cliente", "Permisos para pagar facturas y solicitar servicios");
        Permisos pOperario = new Permisos(2, "Operario", "Permisos para gestionar medidores y solicitudes");
        Permisos pAdministrador = new Permisos(3, "Administrador", "Permisos para configuracion y gestion total de sistemas");
        this.permisos.add(pCliente);
        this.permisos.add(pOperario);
        this.permisos.add(pAdministrador);
        
        // --- 5. Carga de Usuarios Clientes ---
        Cliente uCliente1 = new Cliente(1, "Laura", "García", "Av. San Martín 123", "3874112233", "laura.g@mail.com", "hash+Pass1", pCliente, 789012345);
        Cliente uCliente2 = new Cliente(2, "Juan", "Pérez", "Calle 10 Nro 45", "3875445566", "juan.p@mail.com", "hash+Pass2", pCliente, 901234567);
        this.clientes.add(uCliente1);
        this.clientes.add(uCliente2);
        
        // --- 6. Carga de Usuarios Operarios ---
        Operario uOperario1 = new Operario(3, "Carlos", "López", "Oficina Central Piso 3", "3875778899", "carlos.l@empresa.com", "hash+Op1", pOperario, "OP1001", "Instalaciones");
        Operario uOperario2 = new Operario(4, "Ana", "Rodríguez", "Sede Principal", "3873990011", "ana.r@empresa.com", "hash+Adm1", pAdministrador, "ADM001", "Sistemas");
        this.operarios.add(uOperario1);
        this.operarios.add(uOperario2);
        
        // --- 7. Carga de Medidores ---
        Medidor mMedidor1 = new Medidor(1,"iM10","ABB","Av.San Martín 123, Frente", 550, new Date(), new Date(), 2022, eActivo, uCliente1);
        Medidor mMedidor2 = new Medidor(2,"iM10","Siemens","Calle 10 Nro 45, Interior", 0, new Date(), new Date(), 2023, eSuspendido, uCliente2);
        Medidor mMedidor3 = new Medidor(3,"i310","ABB","Av.San Martín 123, Frente", 550, new Date(), new Date(), 2022, eActivo, uCliente1);
        this.medidores.add(mMedidor1);
        this.medidores.add(mMedidor2);
        this.medidores.add(mMedidor3);
        
        
        // --- 8. Carga de Información de Servicio ---
        Servicio sServicio1 = new Servicio(1, new Date(), new Date(), new Date(), uCliente1, mMedidor1);
        Servicio sServicio2 = new Servicio(2, new Date(), new Date(), new Date(), uCliente2, mMedidor2);
        Servicio sServicio3 = new Servicio(3, new Date(), new Date(), new Date(), uCliente1, mMedidor3);
        this.infoServicio.add(sServicio1);
        this.infoServicio.add(sServicio2);
        this.infoServicio.add(sServicio3);
        
        // --- 9. Carga de Facturas ---
        Factura fFactura1 = new Factura(1, 8500.5, new Date(), new Date(), 550, "Pagada", uCliente1, mMedidor1);
        Factura fFactura2 = new Factura(2, 12000, new Date(), new Date(), 1200, "Pagada", uCliente1, mMedidor3);
        Factura fFactura3 = new Factura(3, 0, new Date(), new Date(), 0, "Pendiente", uCliente2, mMedidor2);
        this.facturas.add(fFactura1);
        this.facturas.add(fFactura2);
        this.facturas.add(fFactura3);
        
        // --- 10. Carga de Pagos ---
        Pago pPago1 = new Pago(1, 8500.5, new Date(), "Tarjeta de Crédito", "Completado", fFactura1);
        Pago pPago2 = new Pago(2, 1200, new Date(), "Transferencia", "Completado", fFactura2);
        this.pagos.add(pPago1);
        this.pagos.add(pPago2);
        
        // --- 11. Carga de Solicitudes de Prueba ---
        SolicitudDeServicio sSolicitud1 = new SolicitudDeServicio (1, "", uCliente2, eSuspendido, tActivacion, ePendiente);
        this.solicitudes.add(sSolicitud1);
        
    }
    
    //Busca un Cliente en la lista por su Número de Identificación de Suministro (NIS).
    public Cliente getClientePorNIS(int NIS) {
        for (Cliente c : clientes) {
            if (c.getNIS() == NIS) return c; // Retorna el cliente si el NIS coincide.
        }
        return null; // Retorna null si no se encuentra.
    }
    
    //Retorna todos los estados posibles que un Medidor puede tener.
    public List<EstadoDeMedidor> getTodosLosEstadosDeMedidor() {
        return this.estadoMedidor; // Retorna la lista de estados cargados (Activo, Suspendido, De Baja, etc.)
    }
    
    //Busca un Medidor en la lista por su identificador único.
    public Medidor getMedidorPorID(int idMedidor) {
        for (Medidor m : medidores) {
            if (m.getIdMedidor() == idMedidor) return m;
        }
        return null;
    }
    
    //Busca un Operario en la lista por su identificador interno.
    public Operario getOperarioPorID(int idOperario) {
        for (Operario o : operarios) {
            if (o.getIdOperario() == idOperario) return o;
        }
        return null;
    }
    
    //Intenta autenticar a un usuario (Cliente por NIS u Operario por ID Universal) con su contraseña.
    public Usuario autenticarUsuario(String identificador, String contrasena){
        Usuario usuarioEncontrado = null;
        
        // 1. Intentar buscar como Cliente (si el identificador es numérico / NIS)
        try{
            int nis = Integer.parseInt(identificador);
            for (Cliente c : clientes){
                if (c.getNIS() == nis){
                    usuarioEncontrado = c;
                    break;
                }
            }
        } catch (NumberFormatException e) {
            // Ignora el error, el identificador no era un NIS, y pasa a buscar como Operario.
        }

        // 2. Si no se encontró como Cliente, buscar como Operario (por ID Universal)
        if (usuarioEncontrado == null) {
            for (Operario o : operarios) {
                if (o.getIdUniversal().equalsIgnoreCase(identificador)) {
                    usuarioEncontrado = o;
                    break;
                }
            }
        }

        // 3. Validar contraseña
        if (usuarioEncontrado != null) {
            if (usuarioEncontrado.getContrasena().equals(contrasena)) {
                return usuarioEncontrado; // Autenticación exitosa.
            }
        }
        return null; // Autenticación fallida.
    }
    
    //Añade un nuevo Medidor a la lista de persistencia simulada.
    public void agregarMedidor(Medidor medidor) {
        this.medidores.add(medidor);
        System.out.println("BD SIMULADA: Medidor " + medidor.getIdMedidor() + " persistido correctamente.");
    }
   
    //Obtiene el Medidor asociado a un Cliente, buscando por su ID de usuario.
    public Medidor getMedidorAsociadoACliente(Cliente cliente) {
        for (Medidor m : medidores) {
            // Verifica que el cliente del medidor no sea null y compara IDs.
            if (m.getCliente() != null && m.getCliente().getIdUsuario() == cliente.getIdUsuario()) {
                return m;
            }
        }
        return null; // El cliente no tiene un medidor asociado.
    }

    //Busca un TipoDeSolicitud (ej. "Activacion") por su nombre.
    public TipoDeSolicitud getTipoSolicitudPorNombre(String nombre) {
        for (TipoDeSolicitud tipo : tiposDeSolicitud) {
            if (tipo.getNombre().equalsIgnoreCase(nombre)) {
                return tipo;
            }
        }
        return null;
    }

    //Busca un EstadoDeSolicitudDeServicio (ej. "Pendiente") por su nombre.
    public EstadoDeSolicitudDeServicio getEstadoSolicitudPorNombre(String nombre) {
        for (EstadoDeSolicitudDeServicio estado : estadoDeSolicitud) {
            if (estado.getNombre().equalsIgnoreCase(nombre)) {
                return estado;
            }
        }
        return null;
    }

    
    //Agrega una nueva Solicitud de Servicio a la lista.
    public void agregarSolicitud(SolicitudDeServicio solicitud) {
        this.solicitudes.add(solicitud);
        System.out.println("BD SIMULADA: Solicitud " + solicitud.getIdSolicitud() + " persistida.");
    }

    //Genera un ID secuencial simple para una nueva Solicitud.
    public int generarIdSolicitud() {
        // Lógica simple: usa el ID del último elemento + 1.
        if (solicitudes.isEmpty()) {
            return 1;
        }
        return solicitudes.get(solicitudes.size() - 1).getIdSolicitud() + 1;
    }
    
    //Retorna todas las Solicitudes de Servicio almacenadas.
    public List<SolicitudDeServicio> getTodasLasSolicitudes() {
        return this.solicitudes; 
    }

    //Filtra y retorna las solicitudes con estado "Pendiente" asociadas a un Cliente específico.
    public List<SolicitudDeServicio> getSolicitudesPendientesPorCliente(Cliente cliente) {
        List<SolicitudDeServicio> pendientes = new ArrayList<>();
        String estadoBuscado = "Pendiente"; 

        for (SolicitudDeServicio s : solicitudes) {
            // Filtra por ID de cliente y estado "Pendiente".
            if (s.getCliente().getIdUsuario() == cliente.getIdUsuario() && 
                s.getEstadoDeSolicitudDeServicio().getNombre().equalsIgnoreCase(estadoBuscado)) {
                pendientes.add(s);
            }
        }
        return pendientes;
    }

    //Simula la persistencia del estado actualizado de una Solicitud existente.
    public void actualizarSolicitud(SolicitudDeServicio solicitud) {
        // En un simulador, la lista ya contiene el objeto, y este ya fue modificado.
        System.out.println("BD SIMULADA: Solicitud " + solicitud.getIdSolicitud() + " estado actualizado a " + solicitud.getEstadoDeSolicitudDeServicio().getNombre());
    }
    

    //Busca un EstadoDeMedidor (ej. "Activo") por su nombre.
    public EstadoDeMedidor getEstadoMedidorPorNombre(String nombre) {
        if (nombre == null) {
            return null;
        }
        
        for (EstadoDeMedidor estado : this.estadoMedidor) { 
            // Compara ignorando mayúsculas y minúsculas para una búsqueda flexible.
            if (estado.getNombre().equalsIgnoreCase(nombre)) {
                return estado;
            }
        }
        return null; // El estado no fue encontrado.
    }
    
    //Obtiene el objeto Servicio asociado a un Medidor.
    public Servicio getServicioPorMedidor(Medidor medidor) {
        if (medidor == null) return null;

        for (Servicio servicio : this.infoServicio) {
            // Busca en la lista de servicios usando el ID del medidor asociado.
            if (servicio.getMedidor() != null && servicio.getMedidor().getIdMedidor() == medidor.getIdMedidor()) {
                return servicio;
            }
        }
        return null;
    }
}
