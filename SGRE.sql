CREATE DATABASE `sgre` ;
USE sgre;

CREATE TABLE EstadoDeMedidor (
    idEstadoDeMedidor INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE EstadoDeSolicitudDeServicio (
    idEstadoDeSolicitudDeServicio INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE TipoDeSolicitud (
    idTipoDeSolicitud INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE Permisos (
    idPermisos INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(45) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);


CREATE TABLE Usuario (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(15) NOT NULL,
    apellido VARCHAR(15) NOT NULL,
    direccion VARCHAR(45) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    correoElectronico VARCHAR(25) UNIQUE NOT NULL,
    contraseña VARCHAR(20) NOT NULL,
    fechaRegistro DATE NOT NULL,
    Permisos_idPermisos INT,
    FOREIGN KEY (Permisos_idPermisos) REFERENCES Permisos(idPermisos)
);


CREATE TABLE Cliente (
    idCliente INT PRIMARY KEY, 
    NIS INT(20) UNIQUE NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES Usuario(idUsuario)
);


CREATE TABLE Operario (
    idOperario INT PRIMARY KEY,
    idUniversal VARCHAR(15) UNIQUE NOT NULL,
    departamento VARCHAR(45) NOT NULL,
    
    FOREIGN KEY (idOperario) REFERENCES Usuario(idUsuario)
);


CREATE TABLE Medidor (
    idMedidor INT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(45) NOT NULL,
    marca VARCHAR(45) NOT NULL,
    ubicacion VARCHAR(45) NOT NULL,
    consumoKwh INT DEFAULT 0,
    fecha DATETIME,
    fechaDeInstalacion DATE NOT NULL,
    añoDeFabricacion INT NOT NULL,
    
    Cliente_idCliente INT NOT NULL,
    EstadoDeMedidor_idEstadoDeMedidor INT NOT NULL,
    
    FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente(idCliente),
    FOREIGN KEY (EstadoDeMedidor_idEstadoDeMedidor) REFERENCES EstadoDeMedidor(idEstadoDeMedidor)
);

CREATE TABLE Servicio (
    idServicio INT PRIMARY KEY AUTO_INCREMENT,
    fechaActivacion DATE NOT NULL,
    fechaSuspencion DATE,
    fechaBaja DATE,
    
    Cliente_idCliente INT NOT NULL,
    Medidor_idMedidor INT NOT NULL,
    
    FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente(idCliente),
    FOREIGN KEY (Medidor_idMedidor) REFERENCES Medidor(idMedidor)
);


CREATE TABLE Factura (
    idFactura INT PRIMARY KEY AUTO_INCREMENT,
    monto FLOAT NOT NULL,
    fechaEmision DATE NOT NULL,
    fechaVencimiento DATE NOT NULL,
    consumoKwh INT NOT NULL,
    estado VARCHAR(15) NOT NULL, 
    
    Cliente_idCliente INT NOT NULL,
    Medidor_idMedidor INT NOT NULL,
    
    FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente(idCliente),
    FOREIGN KEY (Medidor_idMedidor) REFERENCES Medidor(idMedidor)
);

CREATE TABLE Pago (
    idPago INT PRIMARY KEY AUTO_INCREMENT,
    monto FLOAT NOT NULL,
    fechaDePago DATE NOT NULL,
    metodoDePago VARCHAR(45) NOT NULL,
    estado VARCHAR(15) NOT NULL,
    
    Factura_idFactura INT NOT NULL,
    FOREIGN KEY (Factura_idFactura) REFERENCES Factura(idFactura)
);

CREATE TABLE SolicitudDeServicio (
    idSolicitud INT PRIMARY KEY AUTO_INCREMENT,
    fechaSolicitud DATETIME NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    
    Cliente_idCliente INT NOT NULL,
    TipoDeSolicitud_idTipoDeSolicitud INT NOT NULL,
    EstadoDeMedidor_idEstadoDeMedidor INT NOT NULL, 
    EstadoDeSolicitudDeServicio_idEstadoDeSolicitudDeServicio INT NOT NULL,
    
    FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente(idCliente),
    FOREIGN KEY (TipoDeSolicitud_idTipoDeSolicitud) REFERENCES TipoDeSolicitud(idTipoDeSolicitud),
    FOREIGN KEY (EstadoDeMedidor_idEstadoDeMedidor) REFERENCES EstadoDeMedidor(idEstadoDeMedidor),
    FOREIGN KEY (EstadoDeSolicitudDeServicio_idEstadoDeSolicitudDeServicio) REFERENCES EstadoDeSolicitudDeServicio(idEstadoDeSolicitudDeServicio)
);
---------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO EstadoDeMedidor (nombre) VALUES
('Activo'),
('Suspendido'),
('De Baja');


INSERT INTO EstadoDeSolicitudDeServicio (nombre) VALUES
('Pendiente'),
('Finalizada');


INSERT INTO TipoDeSolicitud (nombre) VALUES
('Activación'),
('Suspensión'),
('Baja');


INSERT INTO Permisos (nombre, descripcion) VALUES
('Cliente', 'Permisos para pagar facturas y solicitar servicios.'),
('Operario', 'Permisos para gestionar medidores y solicitudes.'),
('Administrador', 'Permisos para configuración y gestión total del sistema.');


INSERT INTO Usuario (nombre, apellido, direccion, telefono, correoElectronico, contraseña, fechaRegistro, Permisos_idPermisos) VALUES
('Laura', 'García', 'Av. San Martín 123', '3874112233', 'laura.g@mail.com', 'hash+Pass1', '2024-01-15', 1), -- idUsuario 1 (Cliente)
('Juan', 'Pérez', 'Calle 10 Nro 45', '3875445566', 'juan.p@mail.com', 'hash+Pass2', '2024-02-20', 1),   -- idUsuario 2 (Cliente)
('Carlos', 'López', 'Oficina Central Piso 3', '3875778899', 'carlos.l@empresa.com', 'hash+Op1','2023-11-01', 2), -- idUsuario 3 (Operario)
('Ana', 'Rodríguez', 'Sede Principal', '3873990011', 'ana.r@empresa.com', 'hash+Adm1', '2023-10-01', 3);   -- idUsuario 4 (Administrador)


INSERT INTO Cliente (idCliente, NIS) VALUES
(1, '789012345'),
(2, '901234567');


INSERT INTO Operario (idOperario, idUniversal, departamento) VALUES
(3, 'OP1001', 'Instalaciones'),
(4, 'ADM001', 'Sistemas');


INSERT INTO Medidor (tipo, marca, ubicacion, consumoKwh, fecha, fechaDeInstalacion, añoDeFabricacion, Cliente_idCliente, EstadoDeMedidor_idEstadoDeMedidor) VALUES
('iM10', 'ABB', 'Av. San Martín 123, Frente', 550, NOW(), '2024-03-01', 2022, 1, 1), -- Cliente 1, Activo (ID 1)
('iM10', 'Siemens', 'Calle 10 Nro 45, Interior', 0, NOW(), '2024-04-10', 2023, 2, 2), -- Cliente 2, Suspendido (ID 2)
('i310', 'ABB', 'Av. San Martín 123, Patio', 1200, NOW(), '2024-03-05', 2022, 1, 1); -- Cliente 1, Activo (ID 1)


INSERT INTO Servicio (fechaActivacion, fechaSuspencion, fechaBaja, Cliente_idCliente, Medidor_idMedidor) VALUES
('2024-03-01', NULL, NULL, 1, 1), -- Medidor 1 (Activo)
('2024-04-10', '2024-09-01', NULL, 2, 2), -- Medidor 2 (Suspendido)
('2024-03-05', NULL, NULL, 1, 3); -- Medidor 3 (Activo)


INSERT INTO Factura (monto, fechaEmision, fechaVencimiento, consumoKwh, estado, Cliente_idCliente, Medidor_idMedidor) VALUES
(8500.50, '2024-09-01', '2024-09-15', 550, 'Pagada', 1, 1),
(12000.00, '2024-09-01', '2024-09-15', 1200, 'Pagada', 1, 3),
(0.00, '2024-09-01', '2024-09-15', 0, 'Pendiente', 2, 2); -- Factura de cargo fijo estando suspendido


INSERT INTO Pago (monto, fechaDePago, metodoDePago, estado, Factura_idFactura) VALUES
(8500.50, '2024-09-10', 'Tarjeta de Crédito', 'Completado', 1),
(12000.00, '2024-09-11', 'Transferencia', 'Completado', 2);


INSERT INTO SolicitudDeServicio (fechaSolicitud, descripcion, Cliente_idCliente, TipoDeSolicitud_idTipoDeSolicitud, EstadoDeMedidor_idEstadoDeMedidor, EstadoDeSolicitudDeServicio_idEstadoDeSolicitudDeServicio) VALUES
(NOW(), 'Solicito reactivar mi servicio lo antes posible.', 2, 1, 2, 1); -- Cliente 2, Solicitud de Activacion, Estado 2 (suspendido),  Estado de Solicitud 1 (Pendiente)
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Ficha de Medidor
SELECT
    M.idMedidor,
    M.tipo AS TipoMedidor,
    M.marca AS Marca,
    M.ubicacion AS UbicacionMedidor,
    EM.nombre AS EstadoActual,
    M.consumoKwh AS ConsumoReportado,
    M.fechaDeInstalacion,
    C.NIS,
    U.nombre AS NombreCliente,
    U.apellido AS ApellidoCliente,
    U.telefono AS TelefonoCliente,
    C.correoElectronico AS EmailCliente
FROM
    Medidor M
JOIN
    Cliente C ON M.Cliente_idCliente = C.idCliente
JOIN
    Usuario U ON C.idCliente = U.idUsuario
JOIN
    EstadoDeMedidor EM ON M.EstadoDeMedidor_idEstadoDeMedidor = EM.idEstadoDeMedidor;

-- Solicitudes de Servicio Pendientes
SELECT
    S.idSolicitud,
    S.fechaSolicitud,
    C.NIS AS NISCliente,
    U.nombre AS ClienteNombre,
    TS.nombre AS TipoSolicitud,
    EM.nombre AS EstadoMedidorDeseado,
    S.descripcion
FROM
    SolicitudDeServicio S
JOIN
    Cliente C ON S.Cliente_idCliente = C.idCliente
JOIN
    Usuario U ON C.idCliente = U.idUsuario
JOIN
    TipoDeSolicitud TS ON S.TipoDeSolicitud_idTipoDeSolicitud = TS.idTipoDeSolicitud
JOIN
    EstadoDeMedidor EM ON S.EstadoDeMedidor_idEstadoDeMedidor = EM.idEstadoDeMedidor
WHERE
    S.EstadoDeSolicitudDeServicio_idEstadoDeSolicitudDeServicio = 1;

-- Detalle de Deuda y Pagos por CLiente
SELECT
    C.NIS,
    F.idFactura,
    F.fechaEmision,
    F.fechaVencimiento,
    F.monto AS MontoFactura,
    F.estado AS EstadoFactura,
    M.idMedidor,
    P.fechaDePago,
    P.metodoDePago
FROM
    Cliente C
JOIN
    Factura F ON C.idCliente = F.Cliente_idCliente
JOIN
    Medidor M ON F.Medidor_idMedidor = M.idMedidor
LEFT JOIN
    Pago P ON F.idFactura = P.Factura_idFactura
WHERE
    C.idCliente = 2;
    
-- Resumen Operativo de Medidores por Estado
SELECT
    EM.nombre AS EstadoDelMedidor,
    COUNT(M.idMedidor) AS CantidadDeMedidores
FROM
    Medidor M
JOIN
    EstadoDeMedidor EM ON M.EstadoDeMedidor_idEstadoDeMedidor = EM.idEstadoDeMedidor
GROUP BY
    EM.nombre
ORDER BY
    CantidadDeMedidores DESC;
---------------------------------------------------------------------------------------------------------------------
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE Pago;
TRUNCATE TABLE Factura;
TRUNCATE TABLE SolicitudDeServicio;
TRUNCATE TABLE Servicio;
TRUNCATE TABLE Medidor;
TRUNCATE TABLE Cliente;
TRUNCATE TABLE Operario;
TRUNCATE TABLE Usuario;
TRUNCATE TABLE Permisos;
TRUNCATE TABLE EstadoDeMedidor;
TRUNCATE TABLE EstadoDeSolicitudDeServicio;
TRUNCATE TABLE TipoDeSolicitud;

SET FOREIGN_KEY_CHECKS = 1;

SELECT
    'EstadoDeMedidor' AS NombreDeTabla, COUNT(*) AS RecuentoFilas FROM EstadoDeMedidor
UNION ALL
SELECT
    'TipoDeSolicitud', COUNT(*) FROM TipoDeSolicitud
UNION ALL
SELECT
    'Permisos', COUNT(*) FROM Permisos
UNION ALL
SELECT
    'Usuario', COUNT(*) FROM Usuario
UNION ALL
SELECT
    'Cliente', COUNT(*) FROM Cliente
UNION ALL
SELECT
    'Operario', COUNT(*) FROM Operario
UNION ALL
SELECT
    'Medidor', COUNT(*) FROM Medidor
UNION ALL
SELECT
    'Servicio', COUNT(*) FROM Servicio
UNION ALL
SELECT
    'Factura', COUNT(*) FROM Factura
UNION ALL
SELECT
    'Pago', COUNT(*) FROM Pago
UNION ALL
SELECT
    'SolicitudDeServicio', COUNT(*) FROM SolicitudDeServicio
ORDER BY NombreDeTabla;