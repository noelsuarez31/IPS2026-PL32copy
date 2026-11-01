--Datos para carga inicial de la base de datos

--Para giis.demo.tkrun:
delete from Entrada;
delete from Venta;
delete from Partido;
delete from Butaca;
delete from HorarioPeriodico;
delete from HorarioEspecifico;
delete from EmpleadoDeportivo;
delete from EmpleadoNoDeportivo;
delete from BaseEmpleado;
delete from Equipo;
delete from CategoriaEquipo;
delete from Entrenamiento;
delete from FranjaHoraria;
delete from Entrevista;
delete from Producto;
delete from LineaVenta;

delete from Reserva;
delete from Usuario;
delete from Instalacion;

delete from Abono;
delete from Abonado;
delete from TareaMantenimientoJardinero;
delete from TareaMantenimiento;
delete from Accion;
delete from Campana;
delete from Accionista;

INSERT INTO "Producto" ("nombre", "tipo", "precio") VALUES 
('Sudadera', 'ParteArriba', 65.00),
('Camiseta local', 'ParteArriba', 35.00),
('Pantalon', 'ParteAbajo', 35.00),
('Taza', 'Accesorios', 12.00),
('Gorra', 'Accesorios', 16.00),
('Mochila', 'Accesorios', 19.00),
('Bufanda', 'Accesorios', 15.00),
('Pantalon de chandal', 'ParteAbajo',38.00),
('Camiseta visitante', 'ParteArriba', 35.00);

with recursive tri(c) as (values ('A'),('B'),('C'),('D')), sec(c) as (values ('A'),('B'),('C'),('D'),('E'),('F')), num(x) as (select 1 union all select x + 1 from num where x < 15) insert into Butaca (tribuna, seccion, fila, asiento) select tri.c, sec.c, f.x, a.x from tri, sec, num f, num a where f.x <= 10 order by tri.c, sec.c, f.x, a.x;

INSERT INTO "entrada" VALUES (1,1,1,1,30.0);
INSERT INTO "entrada" VALUES (2,1,1,2,30.0);
INSERT INTO "entrada" VALUES (3,1,1,3,30.0);
INSERT INTO "entrada" VALUES (4,1,1,4,30.0);
INSERT INTO "entrada" VALUES (5,1,2,9,30.0);
INSERT INTO "entrada" VALUES (6,1,2,10,30.0);
INSERT INTO "entrada" VALUES (7,1,2,11,30.0);
INSERT INTO "entrada" VALUES (8,1,3,16,30.0);
INSERT INTO "entrada" VALUES (9,1,3,17,30.0);
INSERT INTO "entrada" VALUES (10,1,3,18,30.0);
INSERT INTO "entrada" VALUES (11,1,3,19,30.0);
INSERT INTO "entrada" VALUES (12,1,3,20,30.0);
INSERT INTO "entrada" VALUES (13,1,3,21,30.0);
INSERT INTO "entrada" VALUES (14,1,3,22,30.0);
INSERT INTO "entrada" VALUES (15,1,3,23,30.0);
INSERT INTO "entrada" VALUES (16,1,3,24,30.0);
INSERT INTO "entrada" VALUES (17,1,3,25,30.0);
INSERT INTO "entrada" VALUES (18,1,3,26,30.0);
INSERT INTO "entrada" VALUES (19,1,3,27,30.0);
INSERT INTO "entrada" VALUES (20,1,3,28,30.0);
INSERT INTO "entrada" VALUES (21,1,3,29,30.0);
INSERT INTO "entrada" VALUES (22,1,3,30,30.0);
INSERT INTO "entrada" VALUES (23,1,4,33,30.0);
INSERT INTO "entrada" VALUES (24,1,4,34,30.0);
INSERT INTO "entrada" VALUES (25,1,5,5,30.0);
INSERT INTO "entrada" VALUES (26,1,5,6,30.0);
INSERT INTO "entrada" VALUES (27,1,5,7,30.0);
INSERT INTO "entrada" VALUES (28,1,5,8,30.0);
INSERT INTO "entrada" VALUES (29,1,6,31,30.0);
INSERT INTO "entrada" VALUES (30,1,6,32,30.0);
INSERT INTO "entrada" VALUES (31,1,7,35,30.0);
INSERT INTO "entrada" VALUES (32,1,7,36,30.0);
INSERT INTO "entrada" VALUES (33,1,7,37,30.0);
INSERT INTO "entrada" VALUES (34,1,7,38,30.0);
INSERT INTO "entrada" VALUES (35,1,7,39,30.0);
INSERT INTO "entrada" VALUES (36,1,7,40,30.0);
INSERT INTO "entrada" VALUES (37,1,8,155,30.0);
INSERT INTO "entrada" VALUES (38,1,8,170,30.0);
INSERT INTO "entrada" VALUES (39,1,8,185,30.0);
INSERT INTO "entrada" VALUES (40,1,8,200,30.0);
INSERT INTO "entrada" VALUES (41,1,8,215,30.0);
INSERT INTO "entrada" VALUES (42,1,8,230,30.0);
INSERT INTO "entrada" VALUES (43,1,8,245,30.0);
INSERT INTO "entrada" VALUES (44,1,8,260,30.0);
INSERT INTO "entrada" VALUES (45,1,8,275,30.0);
INSERT INTO "entrada" VALUES (46,1,8,290,30.0);
INSERT INTO "entrada" VALUES (47,1,9,160,30.0);
INSERT INTO "entrada" VALUES (48,1,9,175,30.0);
INSERT INTO "entrada" VALUES (49,1,9,190,30.0);
INSERT INTO "entrada" VALUES (50,1,9,205,30.0);
INSERT INTO "entrada" VALUES (51,1,9,220,30.0);
INSERT INTO "entrada" VALUES (52,1,9,235,30.0);
INSERT INTO "entrada" VALUES (53,1,9,250,30.0);
INSERT INTO "entrada" VALUES (54,1,9,265,30.0);
INSERT INTO "entrada" VALUES (55,1,9,280,30.0);
INSERT INTO "entrada" VALUES (56,1,9,295,30.0);
INSERT INTO "entrada" VALUES (57,2,10,1,30.0);
INSERT INTO "entrada" VALUES (58,2,10,2,30.0);
INSERT INTO "entrada" VALUES (59,2,10,3,30.0);
INSERT INTO "entrada" VALUES (60,2,10,4,30.0);
INSERT INTO "entrada" VALUES (61,2,10,5,30.0);
INSERT INTO "entrada" VALUES (62,2,11,23,30.0);
INSERT INTO "entrada" VALUES (63,2,11,24,30.0);
INSERT INTO "entrada" VALUES (64,2,11,25,30.0);
INSERT INTO "entrada" VALUES (65,1,13,65,30.0);
INSERT INTO "entrada" VALUES (66,1,13,66,30.0);
INSERT INTO "entrada" VALUES (67,1,13,67,30.0);
INSERT INTO "entrada" VALUES (68,1,13,68,30.0);
INSERT INTO "entrada" VALUES (69,1,13,69,30.0);
INSERT INTO "entrada" VALUES (70,1,13,70,30.0);
INSERT INTO "entrada" VALUES (71,1,14,121,30.0);
INSERT INTO "entrada" VALUES (72,1,14,122,30.0);
INSERT INTO "entrada" VALUES (73,1,14,123,30.0);
INSERT INTO "entrada" VALUES (74,1,14,124,30.0);
INSERT INTO "entrada" VALUES (75,1,14,125,30.0);
INSERT INTO "entrada" VALUES (76,1,14,126,30.0);
INSERT INTO "entrada" VALUES (77,1,14,127,30.0);
INSERT INTO "entrada" VALUES (78,1,14,128,30.0);
INSERT INTO "entrada" VALUES (79,1,14,129,30.0);
INSERT INTO "entrada" VALUES (80,1,14,130,30.0);
INSERT INTO "entrada" VALUES (81,1,14,131,30.0);
INSERT INTO "entrada" VALUES (82,1,14,132,30.0);
INSERT INTO "entrada" VALUES (83,1,14,133,30.0);
INSERT INTO "entrada" VALUES (84,1,14,134,30.0);
INSERT INTO "entrada" VALUES (85,1,14,135,30.0);
INSERT INTO "entrada" VALUES (86,1,15,95,30.0);
INSERT INTO "entrada" VALUES (87,1,16,102,30.0);
INSERT INTO "entrada" VALUES (88,1,17,101,30.0);
INSERT INTO "entrada" VALUES (89,1,18,90,30.0);
INSERT INTO "entrada" VALUES (90,1,19,71,30.0);
INSERT INTO "entrada" VALUES (91,1,20,97,30.0);
INSERT INTO "entrada" VALUES (92,1,20,98,30.0);
INSERT INTO "entrada" VALUES (93,1,21,139,30.0);
INSERT INTO "entrada" VALUES (94,1,22,118,30.0);
INSERT INTO "entrada" VALUES (95,1,23,117,30.0);
INSERT INTO "entrada" VALUES (96,1,24,119,30.0);
INSERT INTO "entrada" VALUES (97,1,25,53,30.0);
INSERT INTO "entrada" VALUES (98,1,26,112,30.0);
INSERT INTO "entrada" VALUES (99,1,27,80,30.0);
INSERT INTO "entrada" VALUES (100,1,27,81,30.0);
INSERT INTO "entrada" VALUES (101,1,27,82,30.0);
INSERT INTO "entrada" VALUES (102,1,27,83,30.0);

INSERT INTO "venta" VALUES (1,'2026-10-05','Entradas partido 1','ENTRADAS',120.0);
INSERT INTO "venta" VALUES (2,'2026-10-05','Entradas partido 1','ENTRADAS',90.0);
INSERT INTO "venta" VALUES (3,'2026-10-06','Entradas partido 1','ENTRADAS',450.0);
INSERT INTO "venta" VALUES (4,'2026-10-06','Entradas partido 1','ENTRADAS',60.0);
INSERT INTO "venta" VALUES (5,'2026-10-07','Entradas partido 1','ENTRADAS',120.0);
INSERT INTO "venta" VALUES (6,'2026-10-07','Entradas partido 1','ENTRADAS',60.0);
INSERT INTO "venta" VALUES (7,'2026-10-08','Entradas partido 1','ENTRADAS',180.0);
INSERT INTO "venta" VALUES (8,'2026-10-08','Entradas partido 1','ENTRADAS',300.0);
INSERT INTO "venta" VALUES (9,'2026-10-08','Entradas partido 1','ENTRADAS',300.0);
INSERT INTO "venta" VALUES (10,'2026-10-20','Entradas partido 2','ENTRADAS',150.0);
INSERT INTO "venta" VALUES (11,'2026-10-21','Entradas partido 2','ENTRADAS',90.0);
INSERT INTO "venta" VALUES (12,'2026-10-22','Camiseta y bufanda','MERCHANDISING',55.0);
INSERT INTO "venta" VALUES (13,'2026-10-03','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',180.0);
INSERT INTO "venta" VALUES (14,'2026-10-03','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',450.0);
INSERT INTO "venta" VALUES (15,'2026-10-03','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',30.0);
INSERT INTO "venta" VALUES (16,'2026-10-03','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',30.0);
INSERT INTO "venta" VALUES (17,'2026-10-04','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',30.0);
INSERT INTO "venta" VALUES (18,'2026-10-04','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',30.0);
INSERT INTO "venta" VALUES (19,'2026-10-04','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',30.0);
INSERT INTO "venta" VALUES (20,'2026-10-04','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',60.0);
INSERT INTO "venta" VALUES (21,'2026-10-04','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',30.0);
INSERT INTO "venta" VALUES (22,'2026-10-04','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',30.0);
INSERT INTO "venta" VALUES (23,'2026-10-04','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',30.0);
INSERT INTO "venta" VALUES (24,'2026-10-04','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',30.0);
INSERT INTO "venta" VALUES (25,'2026-10-04','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',30.0);
INSERT INTO "venta" VALUES (26,'2026-10-04','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',30.0);
INSERT INTO "venta" VALUES (27,'2026-10-06','ENTRADAS PARA EL PARTIDO 1','ENTRADAS',120.0);
INSERT INTO "venta" VALUES (28,'2026-10-09 10:15','Abono de temporada','ABONOS',350.0);
INSERT INTO "venta" VALUES (29,'2026-10-12 16:40','Campaña de accionistas','CAMPANAS_ACCIONISTAS',1200.0);
INSERT INTO "venta" VALUES (30,'2026-10-23 17:45','Venta Merchandising','MERCHANDISING',97.0);
INSERT INTO "LineaVenta" ("id_venta", "id_producto", "unidades", "precio_unitario") VALUES
(12, (SELECT id_producto FROM Producto WHERE nombre='Camiseta local'), 1, 35.0),
(12, (SELECT id_producto FROM Producto WHERE nombre='Pantalon'), 1, 35.0),
(12, (SELECT id_producto FROM Producto WHERE nombre='Taza'), 1, 12.0),
(30, (SELECT id_producto FROM Producto WHERE nombre='Sudadera'), 1, 65.0),
(30, (SELECT id_producto FROM Producto WHERE nombre='Gorra'), 2, 16.0);

INSERT INTO "CategoriaEquipo" ("nombre", "edad_minima", "edad_maxima", "tipo_equipo") VALUES 
('Primer equipo', 19, 40, 'Profesional'),
('Filial', 19, 40, 'Profesional'),
('Regional', 19, 40, 'En formacion'),
('Juvenil', 16, 18, 'En formacion'),
('Cadete', 14, 15, 'En formacion'),
('Infantil', 12, 13, 'En formacion'),
('Alevin', 10, 11, 'En formacion'),
('Benjamin', 8, 9, 'En formacion'),
('Prebenjamin', 6, 7, 'En formacion');
INSERT INTO "Equipo" ("id_equipo", "nombre", "es_propio", "tipo_equipo", "id_categoria") VALUES 
(1, 'Real Oviedo A', 1, 'Primer equipo', 1),
(2, 'Real Oviedo B', 1, 'Filial', 2);
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('11111111A', 'Borja', 'Bastón', 3000.00, '1992-08-24', '611223344');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('22222222B', 'Jimmy', 'Suárez', 2500.00, '1996-12-31', '622334455');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('33333333C', 'Javier', 'Calleja', 6000.00, '1978-05-12', '633445566');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('44444444D', 'Nacho', 'González', 2200.00, '1985-03-15', '644556677');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('55555555E', 'Sergio', 'Tella', 1900.00, '1990-07-20', '655667788');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono")
VALUES ('66666666F', 'Daniel', 'Tellado', 1400.00, '1990-05-05', '632437172');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('77777777G', 'Lucas', 'Vázquez', 1500.00, '2008-03-10', '677889900');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('88888888H', 'Mateo', 'Alonso', 1200.00, '2009-05-14', '688990011'); 
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('99999999I', 'Alejandro', 'Pérez', 1300.00, '2007-01-20', '699001122'); 
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('12345678J', 'David', 'Villa', 2800.00, '2000-12-03', '612345678');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('87654321K', 'Santi', 'Cazorla', 3200.00, '1990-06-15', '687654321');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('11223344L', 'Diego', 'Mariño', 2100.00, '1990-05-09', '611223355'); 
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('55667788M', 'Manu', 'García', 2400.00, '1998-01-02', '655667799');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('99887766N', 'Pelayo', 'Fernández', 1100.00, '2008-09-18', '699887766'); 
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('33445566O', 'Pablo', 'Álvarez', 2000.00, '1995-11-25', '633445577'); 
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('77889900P', 'Gaspar', 'Campos', 2600.00, '2000-03-30', '677889911'); 
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('11111111A', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('22222222B', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('33333333C', 'ENTRENADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('66666666F', 'ENTRENADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('44444444D', 'TECNICO_ADICIONAL');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('55555555E', 'TECNICO_ADICIONAL');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('77777777G', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('88888888H', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('99999999I', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('12345678J', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('87654321K', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('11223344L', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('55667788M', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('99887766N', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('33445566O', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('77889900P', 'JUGADOR');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('10101010X', 'Luis', 'García', 5000.00, '1980-04-10', '600111222');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('20202020Y', 'Roberto', 'Martínez', 4000.00, '1983-09-15', '600222333');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('30303030Z', 'Alberto', 'López', 4800.00, '1979-01-22', '600333444');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('40404040W', 'Carlos', 'Sánchez', 3800.00, '1986-11-05', '600444556');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('10101010X', 'ENTRENADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('20202020Y', 'ENTRENADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('30303030Z', 'ENTRENADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('40404040W', 'ENTRENADOR');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('50505050A', 'Mario', 'Suárez', 2000.00, '1995-02-10', '610111111');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('50505050B', 'Jorge', 'Resurrección', 2200.00, '1992-01-08', '610222222');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('50505050C', 'Rubén', 'Pardo', 1800.00, '1994-10-22', '610333333');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('50505050D', 'Borja', 'Sainz', 1700.00, '2001-02-01', '610444444');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('50505050E', 'Abel', 'Bretones', 1900.00, '2000-05-21', '610555555');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('50505050F', 'Quentin', 'Braat', 2100.00, '1997-07-06', '610666666');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('50505050G', 'Dani', 'Calvo', 2300.00, '1994-04-01', '610777777');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('50505050A', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('50505050B', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('50505050C', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('50505050D', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('50505050E', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('50505050F', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('50505050G', 'JUGADOR');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('60606060A', 'Jon', 'Bautista', 2000.00, '1995-07-03', '620111111');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('60606060B', 'Víctor', 'Campuzano', 1900.00, '1997-05-31', '620222222');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('60606060C', 'Luka', 'Romero', 2500.00, '2004-11-18', '620333333');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('60606060D', 'Paulino', 'de la Fuente', 2100.00, '1997-06-27', '620444444');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('60606060E', 'Jimmy', 'G.', 1800.00, '1999-03-12', '620555555');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('60606060F', 'Lucas', 'Ahijado', 2200.00, '1995-01-30', '620666666');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('60606060G', 'Oier', 'Luengo', 2000.00, '1996-11-11', '620777777');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('60606060A', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('60606060B', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('60606060C', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('60606060D', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('60606060E', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('60606060F', 'JUGADOR');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES ('60606060G', 'JUGADOR');

INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('70707070A', 'Carlos', 'García', 45000.00, '1980-05-12', '700111222');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('70707070B', 'María', 'López', 22000.00, '1995-08-23', '700222333');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('70707070C', 'Roberto', 'Martínez', 28000.00, '1988-11-05', '700333444');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('70707070D', 'Laura', 'Sánchez', 20000.00, '1998-02-14', '700444555');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('70707070E', 'Javier', 'Fernández', 32000.00, '1982-07-30', '700555666');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('70707070F', 'Elena', 'Pérez', 19000.00, '1990-09-15', '700666777');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('70707070G', 'David', 'Gómez', 21000.00, '1985-04-20', '700777888');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('70707070H', 'Ana', 'Ruiz', 35000.00, '1987-12-01', '700888999');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('70707070I', 'Miguel', 'Torres', 22000.00, '1996-03-10', '700999000');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('70707070J', 'Sofía', 'Díaz', 20000.00, '1999-06-18', '700000111');
INSERT INTO "EmpleadoNoDeportivo" ("dni", "posicion") VALUES ('70707070A', 'GERENTE');
INSERT INTO "EmpleadoNoDeportivo" ("dni", "posicion") VALUES ('70707070B', 'VENDEDOR_DE_ENTRADAS');
INSERT INTO "EmpleadoNoDeportivo" ("dni", "posicion") VALUES ('70707070C', 'ENCARGADO_DE_TIENDA');
INSERT INTO "EmpleadoNoDeportivo" ("dni", "posicion") VALUES ('70707070D', 'EMPLEADO_DE_TIENDA');
INSERT INTO "EmpleadoNoDeportivo" ("dni", "posicion") VALUES ('70707070E', 'GESTOR_DE_INSTALACIONES');
INSERT INTO "EmpleadoNoDeportivo" ("dni", "posicion") VALUES ('70707070F', 'JARDINERIA');
INSERT INTO "EmpleadoNoDeportivo" ("dni", "posicion") VALUES ('70707070G', 'COCINA');
INSERT INTO "EmpleadoNoDeportivo" ("dni", "posicion") VALUES ('70707070H', 'DIRECTOR_DE_COMUNICACIONES');
INSERT INTO "EmpleadoNoDeportivo" ("dni", "posicion") VALUES ('70707070I', 'VENDEDOR_DE_ENTRADAS');
INSERT INTO "EmpleadoNoDeportivo" ("dni", "posicion") VALUES ('70707070J', 'EMPLEADO_DE_TIENDA');

-- Nuevos jardineros para pruebas de HU 4
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('70707070K', 'Marcos', 'Vega', 21500.00, '1992-04-10', '700000222');
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") 
VALUES ('70707070L', 'Lucía', 'Molina', 21000.00, '1995-11-20', '700000333');

INSERT INTO "EmpleadoNoDeportivo" ("dni", "posicion") VALUES ('70707070K', 'JARDINERIA');
INSERT INTO "EmpleadoNoDeportivo" ("dni", "posicion") VALUES ('70707070L', 'JARDINERIA');

-- Horarios periódicos para jardineros (pruebas rápidas de mantenimiento)
-- Elena Pérez (70707070F): Lunes a viernes, 08:00 a 16:00
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070F', 1, '08:00', '16:00');
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070F', 2, '08:00', '16:00');
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070F', 3, '08:00', '16:00');
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070F', 4, '08:00', '16:00');
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070F', 5, '08:00', '16:00');

-- Marcos Vega (70707070K): Lunes a viernes, 08:00 a 16:00
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070K', 1, '08:00', '16:00');
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070K', 2, '08:00', '16:00');
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070K', 3, '08:00', '16:00');
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070K', 4, '08:00', '16:00');
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070K', 5, '08:00', '16:00');

-- Lucía Molina (70707070L): Lunes a viernes, 08:00 a 16:00
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070L', 1, '08:00', '16:00');
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070L', 2, '08:00', '16:00');
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070L', 3, '08:00', '16:00');
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070L', 4, '08:00', '16:00');
INSERT INTO "HorarioPeriodico" ("dni_empleado", "dia_semana", "hora_inicio", "hora_fin") VALUES ('70707070L', 5, '08:00', '16:00');

insert into Equipo (nombre, es_propio, tipo_equipo, id_categoria) values ('Real Oviedo', 1, 'Profesional', (select id_categoria from CategoriaEquipo where nombre = 'Primer equipo'));
insert into Equipo (nombre, es_propio, tipo_equipo, id_categoria) values ('Real Oviedo B', 1, 'Profesional', (select id_categoria from CategoriaEquipo where nombre = 'Primer equipo'));
insert into Equipo (nombre, es_propio, tipo_equipo, id_categoria) values ('Sporting', 0, 'Profesional', (select id_categoria from CategoriaEquipo where nombre = 'Primer equipo'));
insert into Equipo (nombre, es_propio, tipo_equipo, id_categoria) values ('Racing', 0, 'Profesional', (select id_categoria from CategoriaEquipo where nombre = 'Primer equipo'));
insert into Equipo (nombre, es_propio, tipo_equipo, id_categoria) values ('Levante', 0, 'Profesional', (select id_categoria from CategoriaEquipo where nombre = 'Primer equipo'));
insert into Equipo (nombre, es_propio, tipo_equipo, id_categoria) values ('Alavés', 0, 'Profesional', (select id_categoria from CategoriaEquipo where nombre = 'Primer equipo'));

INSERT INTO "FranjaHoraria" ("dni_jugador", "fecha", "hora_inicio", "hora_fin") VALUES 
('11111111A', '2026-10-23', '10:00:00', '11:00:00'),
('11111111A', '2026-10-24', '12:00:00', '13:00:00');

INSERT INTO "Entrevista" ("id_franja","nombre_medio") VALUES 
(1, "Mundo Deportivo"),
(2, "Marca");
INSERT INTO "Entrenamiento" ("fecha", "hora_inicio", "hora_fin", "id_equipo", "id_instalacion") VALUES 
('2026-10-21', '16:00:00', '18:00:00', 1, 1),
('2026-10-27', '17:00:00', '19:00:00', 1, 1);

INSERT INTO "Partido" ("fecha", "hora_inicio", "hora_fin", "id_local", "id_visitante", "id_instalacion") VALUES 
('2026-10-21', '21:00:00', '23:00:00', 1, 2, 1),
('2026-10-27', '18:30:00', '20:30:00', 3, 4, 2),
('2026-10-29', '19:30:00', '22:30:00', 5, 6, 2),
('2026-11-03', '14:30:00', '16:30:00', 7, 9, 3);

insert into Instalacion (nombre) values ('Campo de fútbol 1');
insert into Instalacion (nombre) values ('Campo de fútbol 2');
insert into Instalacion (nombre) values ('Campo de fútbol 3');

insert into Usuario (nombre, tarjeta_bancaria) values ('Marta Fernández', '1111222233334444');
insert into Usuario (nombre, tarjeta_bancaria) values ('Pablo Ríos', '2222333344445555');
insert into Usuario (nombre, tarjeta_bancaria) values ('Lucía Torres', '3333444455556666');
insert into Usuario (nombre, tarjeta_bancaria) values ('Club Deportivo Naranco', '4444555566667777');

insert into Reserva (fecha, hora_inicio, hora_fin, importe, fecha_registro, id_usuario, id_instalacion) values ('2026-03-22', '14:00', '16:00', 100.0, '2026-10-21 12:41', (select id_usuario from Usuario where nombre = 'Marta Fernández'), (select id_instalacion from Instalacion where nombre = 'Campo de fútbol 1'));
insert into Reserva (fecha, hora_inicio, hora_fin, importe, fecha_registro, id_usuario, id_instalacion) values ('2026-03-22', '18:00', '19:30', 75.0, '2026-10-20 09:15', (select id_usuario from Usuario where nombre = 'Pablo Ríos'), (select id_instalacion from Instalacion where nombre = 'Campo de fútbol 1'));
insert into Reserva (fecha, hora_inicio, hora_fin, importe, fecha_registro, id_usuario, id_instalacion) values ('2026-03-31', '09:00', '11:00', 100.0, '2026-10-22 17:30', (select id_usuario from Usuario where nombre = 'Lucía Torres'), (select id_instalacion from Instalacion where nombre = 'Campo de fútbol 2'));
insert into Reserva (fecha, hora_inicio, hora_fin, importe, fecha_registro, id_usuario, id_instalacion) values ('2026-03-23', '08:00', '10:00', 100.0, '2026-10-24 13:20', (select id_usuario from Usuario where nombre = 'Pablo Ríos'), (select id_instalacion from Instalacion where nombre = 'Campo de fútbol 3'));
insert into Reserva (fecha, hora_inicio, hora_fin, importe, fecha_registro, id_usuario, id_instalacion) values ('2026-03-26', '20:00', '22:00', 100.0, '2026-10-31 10:00', (select id_usuario from Usuario where nombre = 'Club Deportivo Naranco'), (select id_instalacion from Instalacion where nombre = 'Campo de fútbol 3'));



INSERT INTO Abonado
    (dni, nombre, apellidos, fecha_nacimiento, email)
VALUES
    ('12345678A', 'Lucía', 'Fernández García', '2010-05-12',
     'lucia.fernandez@example.com'),

    ('23456789B', 'Pablo', 'Martínez López', '2005-03-20',
     'pablo.martinez@example.com'),

    ('34567890C', 'Marta', 'Suárez González', '1990-11-08',
     'marta.suarez@example.com'),

    ('45678901D', 'Antonio', 'Pérez Rodríguez', '1958-02-15',
     'antonio.perez@example.com');
     
     
INSERT INTO Venta (fecha, concepto, tipo, total)
VALUES
    ('2026-10-23', 'Abono de temporada',
     'ABONOS', 100.00),

    ('2026-10-23', 'Abono de temporada',
     'ABONOS', 200.00),

    ('2026-10-23', 'Abono de temporada',
     'ABONOS', 400.00),

    ('2026-10-23', 'Abono de temporada',
     'ABONOS', 250.00);
     
INSERT INTO Abono
    (id_venta, id_abonado, id_equipo, id_butaca,
     fecha_inicio, fecha_fin, fecha_registro, categoria, precio)
VALUES
    (
        (SELECT MAX(id_venta) - 3 FROM Venta),
        (SELECT id_abonado FROM Abonado WHERE dni = '12345678A'),
        (SELECT id_equipo FROM Equipo WHERE nombre = 'Real Oviedo' AND es_propio = 1 LIMIT 1),
        (SELECT id_butaca FROM Butaca
         WHERE tribuna = 'A' AND seccion = 'A' AND fila = 1 AND asiento = 1),
        '2026-07-01', '2027-06-30', '2026-10-25 12:27', 'INFANTIL', 100.00
    ),
    (
        (SELECT MAX(id_venta) - 2 FROM Venta),
        (SELECT id_abonado FROM Abonado WHERE dni = '23456789B'),
        (SELECT id_equipo FROM Equipo WHERE nombre = 'Real Oviedo' AND es_propio = 1 LIMIT 1),
        (SELECT id_butaca FROM Butaca
         WHERE tribuna = 'A' AND seccion = 'A' AND fila = 1 AND asiento = 2),
        '2026-07-01', '2027-06-30', '2026-11-27 09:15', 'SUB_26', 200.00
    ),
    (
        (SELECT MAX(id_venta) - 1 FROM Venta),
        (SELECT id_abonado FROM Abonado WHERE dni = '34567890C'),
        (SELECT id_equipo FROM Equipo WHERE nombre = 'Real Oviedo B' AND es_propio = 1 LIMIT 1),
        (SELECT id_butaca FROM Butaca
         WHERE tribuna = 'B' AND seccion = 'B' AND fila = 1 AND asiento = 1),
        '2026-07-01', '2027-06-30', '2026-10-27 11:15', 'ADULTO', 400.00
    ),
    (
        (SELECT MAX(id_venta) FROM Venta),
        (SELECT id_abonado FROM Abonado WHERE dni = '45678901D'),
        (SELECT id_equipo FROM Equipo WHERE nombre = 'Real Oviedo B' AND es_propio = 1 LIMIT 1),
        (SELECT id_butaca FROM Butaca
         WHERE tribuna = 'B' AND seccion = 'B' AND fila = 1 AND asiento = 2),
        '2026-07-01', '2027-06-30','2026-10-25 09:15', 'JUBILADO', 250.00
    );
-- creamos jugadores para probar las distintas categorias --
-- Juveniles --
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('81000001A', 'Hugo', 'Álvarez', 1000.00, '2008-01-15', '681000001'),
('81000002B', 'Marcos', 'Fernández', 1000.00, '2008-03-20', '681000002'),
('81000003C', 'Adrián', 'Gómez', 1000.00, '2008-05-10', '681000003'),
('81000004D', 'Iker', 'López', 1000.00, '2009-01-12', '681000004'),
('81000005E', 'Rodrigo', 'Martín', 1000.00, '2009-02-18', '681000005'),
('81000006F', 'Álvaro', 'García', 1000.00, '2009-04-25', '681000006'),
('81000007G', 'Nicolás', 'Díaz', 1000.00, '2009-06-30', '681000007'),
('81000008H', 'Pablo', 'Sánchez', 1000.00, '2010-01-10', '681000008'),
('81000009I', 'Samuel', 'Romero', 1000.00, '2010-02-14', '681000009'),
('81000010J', 'Diego', 'Navarro', 1000.00, '2010-03-22', '681000010');

INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES
('81000001A', 'JUGADOR'),
('81000002B', 'JUGADOR'),
('81000003C', 'JUGADOR'),
('81000004D', 'JUGADOR'),
('81000005E', 'JUGADOR'),
('81000006F', 'JUGADOR'),
('81000007G', 'JUGADOR'),
('81000008H', 'JUGADOR'),
('81000009I', 'JUGADOR'),
('81000010J', 'JUGADOR');

INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('81000011K', 'Darío', 'Benítez', 1000.00, '2008-02-14', '681000011'),
('81000012L', 'Joel', 'Cano', 1000.00, '2008-06-18', '681000012'),
('81000013M', 'Saúl', 'Vázquez', 1000.00, '2008-08-22', '681000013'),
('81000014N', 'Víctor', 'Ramos', 1000.00, '2009-01-25', '681000014'),
('81000015O', 'Guillermo', 'Blanco', 1000.00, '2009-05-14', '681000015'),
('81000016P', 'Pau', 'Gil', 1000.00, '2009-09-03', '681000016'),
('81000017Q', 'Aitor', 'Serrano', 1000.00, '2010-03-12', '681000017'),
('81000018R', 'Rubén', 'Castro', 1000.00, '2010-07-19', '681000018');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES
('81000011K', 'JUGADOR'),
('81000012L', 'JUGADOR'),
('81000013M', 'JUGADOR'),
('81000014N', 'JUGADOR'),
('81000015O', 'JUGADOR'),
('81000016P', 'JUGADOR'),
('81000017Q', 'JUGADOR'),
('81000018R', 'JUGADOR');

-- Cadetes --
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('82000001A', 'Mario', 'Serrano', 800.00, '2011-01-15', '682000001'),
('82000002B', 'Daniel', 'Molina', 800.00, '2011-02-20', '682000002'),
('82000003C', 'David', 'Morales', 800.00, '2011-03-12', '682000003'),
('82000004D', 'Javier', 'Ortega', 800.00, '2011-04-18', '682000004'),
('82000005E', 'Alejandro', 'Delgado', 800.00, '2011-05-22', '682000005'),
('82000006F', 'Manuel', 'Castro', 800.00, '2012-01-10', '682000006'),
('82000007G', 'Rubén', 'Ortiz', 800.00, '2012-02-14', '682000007'),
('82000008H', 'Carlos', 'Rubio', 800.00, '2012-03-19', '682000008'),
('82000009I', 'Jorge', 'Marín', 800.00, '2012-04-25', '682000009'),
('82000010J', 'Iván', 'Sanz', 800.00, '2012-05-30', '682000010');

INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES
('82000001A', 'JUGADOR'),
('82000002B', 'JUGADOR'),
('82000003C', 'JUGADOR'),
('82000004D', 'JUGADOR'),
('82000005E', 'JUGADOR'),
('82000006F', 'JUGADOR'),
('82000007G', 'JUGADOR'),
('82000008H', 'JUGADOR'),
('82000009I', 'JUGADOR'),
('82000010J', 'JUGADOR');

INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('82000011K', 'Óscar', 'Mendoza', 800.00, '2011-01-20', '682000011'),
('82000012L', 'Jaime', 'Guerrero', 800.00, '2011-03-15', '682000012'),
('82000013M', 'Gabriel', 'Reyes', 800.00, '2011-06-10', '682000013'),
('82000014N', 'Marcos', 'Cortes', 800.00, '2011-08-05', '682000014'),
('82000015O', 'Lucas', 'Paredes', 800.00, '2012-02-12', '682000015'),
('82000016P', 'Adrián', 'Suárez', 800.00, '2012-04-18', '682000016'),
('82000017Q', 'Eric', 'Vargas', 800.00, '2012-07-22', '682000017'),
('82000018R', 'Samuel', 'Lara', 800.00, '2012-09-11', '682000018');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES
('82000011K', 'JUGADOR'),
('82000012L', 'JUGADOR'),
('82000013M', 'JUGADOR'),
('82000014N', 'JUGADOR'),
('82000015O', 'JUGADOR'),
('82000016P', 'JUGADOR'),
('82000017Q', 'JUGADOR'),
('82000018R', 'JUGADOR');

-- Infantiles --
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('83000001A', 'Gonzalo', 'Iglesias', 600.00, '2013-01-15', '683000001'),
('83000002B', 'Héctor', 'Medina', 600.00, '2013-02-20', '683000002'),
('83000003C', 'Guillermo', 'Cortés', 600.00, '2013-03-11', '683000003'),
('83000004D', 'Jaime', 'Castillo', 600.00, '2013-04-16', '683000004'),
('83000005E', 'Raúl', 'Santos', 600.00, '2013-05-21', '683000005'),
('83000006F', 'Víctor', 'Lozano', 600.00, '2014-01-12', '683000006'),
('83000007G', 'Martín', 'Guerrero', 600.00, '2014-02-18', '683000007'),
('83000008H', 'César', 'Cano', 600.00, '2014-03-24', '683000008'),
('83000009I', 'Ángel', 'Prieto', 600.00, '2014-04-09', '683000009'),
('83000010J', 'Leo', 'Méndez', 600.00, '2014-05-14', '683000010');

INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES
('83000001A', 'JUGADOR'),
('83000002B', 'JUGADOR'),
('83000003C', 'JUGADOR'),
('83000004D', 'JUGADOR'),
('83000005E', 'JUGADOR'),
('83000006F', 'JUGADOR'),
('83000007G', 'JUGADOR'),
('83000008H', 'JUGADOR'),
('83000009I', 'JUGADOR'),
('83000010J', 'JUGADOR');

INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('83000011K', 'Ismael', 'Medina', 600.00, '2013-01-22', '683000011'),
('83000012L', 'Bruno', 'Navas', 600.00, '2013-03-30', '683000012'),
('83000013M', 'Fabián', 'Rojas', 600.00, '2013-05-18', '683000013'),
('83000014N', 'Oliver', 'Santana', 600.00, '2013-08-14', '683000014'),
('83000015O', 'Nicolás', 'Aguilar', 600.00, '2014-02-10', '683000015'),
('83000016P', 'Mateo', 'Bravo', 600.00, '2014-04-20', '683000016'),
('83000017Q', 'Daniel', 'Pacheco', 600.00, '2014-06-15', '683000017'),
('83000018R', 'Hugo', 'Morán', 600.00, '2014-09-01', '683000018');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES
('83000011K', 'JUGADOR'),
('83000012L', 'JUGADOR'),
('83000013M', 'JUGADOR'),
('83000014N', 'JUGADOR'),
('83000015O', 'JUGADOR'),
('83000016P', 'JUGADOR'),
('83000017Q', 'JUGADOR'),
('83000018R', 'JUGADOR');

-- Alevines --
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('84000001A', 'Enzo', 'Cruz', 500.00, '2015-01-10', '684000001'),
('84000002B', 'Bruno', 'Calvo', 500.00, '2015-02-15', '684000002'),
('84000003C', 'Oliver', 'Gallego', 500.00, '2015-03-20', '684000003'),
('84000004D', 'Mateo', 'Vidal', 500.00, '2015-04-25', '684000004'),
('84000005E', 'Lucas', 'León', 500.00, '2015-05-30', '684000005'),
('84000006F', 'Hugo', 'Herrera', 500.00, '2016-01-14', '684000006'),
('84000007G', 'Dylan', 'Márquez', 500.00, '2016-02-19', '684000007'),
('84000008H', 'Thiago', 'Peña', 500.00, '2016-03-23', '684000008'),
('84000009I', 'Gael', 'Flores', 500.00, '2016-04-28', '684000009'),
('84000010J', 'Ian', 'Cabrera', 500.00, '2016-05-15', '684000010');

INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES
('84000001A', 'JUGADOR'),
('84000002B', 'JUGADOR'),
('84000003C', 'JUGADOR'),
('84000004D', 'JUGADOR'),
('84000005E', 'JUGADOR'),
('84000006F', 'JUGADOR'),
('84000007G', 'JUGADOR'),
('84000008H', 'JUGADOR'),
('84000009I', 'JUGADOR'),
('84000010J', 'JUGADOR');

INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('84000011K', 'Alonso', 'Valero', 500.00, '2015-01-18', '684000011'),
('84000012L', 'Marcos', 'Cifuentes', 500.00, '2015-03-25', '684000012'),
('84000013M', 'Biel', 'Carreño', 500.00, '2015-06-12', '684000013'),
('84000014N', 'Rodrigo', 'Pallarés', 500.00, '2015-08-20', '684000014'),
('84000015O', 'Leo', 'Salas', 500.00, '2016-02-14', '684000015'),
('84000016P', 'Iker', 'Soler', 500.00, '2016-04-10', '684000016'),
('84000017Q', 'Alex', 'Moya', 500.00, '2016-07-05', '684000017'),
('84000018R', 'Luca', 'Bermejo', 500.00, '2016-09-15', '684000018');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES
('84000011K', 'JUGADOR'),
('84000012L', 'JUGADOR'),
('84000013M', 'JUGADOR'),
('84000014N', 'JUGADOR'),
('84000015O', 'JUGADOR'),
('84000016P', 'JUGADOR'),
('84000017Q', 'JUGADOR'),
('84000018R', 'JUGADOR');

-- Benjamines --
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('85000001A', 'Eric', 'Campos', 400.00, '2017-01-12', '685000001'),
('85000002B', 'Luca', 'Vega', 400.00, '2017-02-17', '685000002'),
('85000003C', 'Liam', 'Fuentes', 400.00, '2017-03-22', '685000003'),
('85000004D', 'Noah', 'Carrasco', 400.00, '2017-04-27', '685000004'),
('85000005E', 'Marc', 'Díez', 400.00, '2017-05-18', '685000005'),
('85000006F', 'Pau', 'Caballero', 400.00, '2018-01-11', '685000006'),
('85000007G', 'Biel', 'Reyes', 400.00, '2018-02-16', '685000007'),
('85000008H', 'Nil', 'Nieto', 400.00, '2018-03-21', '685000008'),
('85000009I', 'Pol', 'Aguilar', 400.00, '2018-04-26', '685000009'),
('85000010J', 'Arnau', 'Pascual', 400.00, '2018-05-31', '685000010');

INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES
('85000001A', 'JUGADOR'),
('85000002B', 'JUGADOR'),
('85000003C', 'JUGADOR'),
('85000004D', 'JUGADOR'),
('85000005E', 'JUGADOR'),
('85000006F', 'JUGADOR'),
('85000007G', 'JUGADOR'),
('85000008H', 'JUGADOR'),
('85000009I', 'JUGADOR'),
('85000010J', 'JUGADOR');

INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('85000011K', 'Jan', 'Calero', 400.00, '2017-01-20', '685000011'),
('85000012L', 'Teo', 'Bernal', 400.00, '2017-03-15', '685000012'),
('85000013M', 'Max', 'Gimeno', 400.00, '2017-05-22', '685000013'),
('85000014N', 'Martí', 'Roldán', 400.00, '2017-08-10', '685000014'),
('85000015O', 'Pol', 'Esteban', 400.00, '2018-02-18', '685000015'),
('85000016P', 'Roc', 'Chaves', 400.00, '2018-04-12', '685000016'),
('85000017Q', 'Nil', 'Montes', 400.00, '2018-06-25', '685000017'),
('85000018R', 'Unai', 'Casas', 400.00, '2018-09-08', '685000018');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES
('85000011K', 'JUGADOR'),
('85000012L', 'JUGADOR'),
('85000013M', 'JUGADOR'),
('85000014N', 'JUGADOR'),
('85000015O', 'JUGADOR'),
('85000016P', 'JUGADOR'),
('85000017Q', 'JUGADOR'),
('85000018R', 'JUGADOR');

-- Prebenjamines --
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('86000001A', 'Teo', 'Santana', 300.00, '2019-01-10', '686000001'),
('86000002B', 'Jan', 'Herrero', 300.00, '2019-02-15', '686000002'),
('86000003C', 'Alex', 'Lorenzo', 300.00, '2019-03-20', '686000003'),
('86000004D', 'Jon', 'Montero', 300.00, '2019-04-25', '686000004'),
('86000005E', 'Max', 'Hidalgo', 300.00, '2019-05-30', '686000005'),
('86000006F', 'Roc', 'Giménez', 300.00, '2020-01-12', '686000006'),
('86000007G', 'Martí', 'Ibáñez', 300.00, '2020-02-18', '686000007'),
('86000008H', 'Aleix', 'Ferrer', 300.00, '2020-03-24', '686000008'),
('86000009I', 'Brais', 'Durán', 300.00, '2020-04-19', '686000009'),
('86000010J', 'Unai', 'Santiago', 300.00, '2020-05-15', '686000010');

INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES
('86000001A', 'JUGADOR'),
('86000002B', 'JUGADOR'),
('86000003C', 'JUGADOR'),
('86000004D', 'JUGADOR'),
('86000005E', 'JUGADOR'),
('86000006F', 'JUGADOR'),
('86000007G', 'JUGADOR'),
('86000008H', 'JUGADOR'),
('86000009I', 'JUGADOR'),
('86000010J', 'JUGADOR');

INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('86000011K', 'Brais', 'Salguero', 300.00, '2019-01-15', '686000011'),
('86000012L', 'Aleix', 'Vergara', 300.00, '2019-03-18', '686000012'),
('86000013M', 'Enzo', 'Noguera', 300.00, '2019-05-20', '686000013'),
('86000014N', 'Gael', 'Pineda', 300.00, '2019-08-12', '686000014'),
('86000015O', 'Thiago', 'Godoy', 300.00, '2020-02-14', '686000015'),
('86000016P', 'Noah', 'Beltrán', 300.00, '2020-04-16', '686000016'),
('86000017Q', 'Liam', 'Alcaide', 300.00, '2020-06-22', '686000017'),
('86000018R', 'Eric', 'Castaño', 300.00, '2020-09-10', '686000018');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES
('86000011K', 'JUGADOR'),
('86000012L', 'JUGADOR'),
('86000013M', 'JUGADOR'),
('86000014N', 'JUGADOR'),
('86000015O', 'JUGADOR'),
('86000016P', 'JUGADOR'),
('86000017Q', 'JUGADOR'),
('86000018R', 'JUGADOR');

-- creamos mas tecnicos adicionales --
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('91000001A', 'Raúl', 'Hernández', 2100.00, '1984-03-12', '691000001'),
('91000002B', 'Marcos', 'Iglesias', 1950.00, '1988-07-25', '691000002'),
('91000003C', 'Adrián', 'Cano', 2200.00, '1982-11-14', '691000003'),
('91000004D', 'David', 'Giménez', 1900.00, '1991-05-09', '691000004'),
('91000005E', 'Pablo', 'Vidal', 2300.00, '1986-09-30', '691000005'),
('91000006F', 'Fernando', 'Romero', 2050.00, '1989-01-17', '691000006'),
('91000007G', 'Javier', 'Santos', 2150.00, '1985-06-22', '691000007'),
('91000008H', 'Álvaro', 'Morales', 2000.00, '1993-10-05', '691000008'),
('91000009I', 'Gonzalo', 'Molina', 2250.00, '1980-04-18', '691000009'),
('91000010J', 'Santiago', 'Delgado', 1950.00, '1995-12-08', '691000010');
INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES
('91000001A', 'TECNICO_ADICIONAL'),
('91000002B', 'TECNICO_ADICIONAL'),
('91000003C', 'TECNICO_ADICIONAL'),
('91000004D', 'TECNICO_ADICIONAL'),
('91000005E', 'TECNICO_ADICIONAL'),
('91000006F', 'TECNICO_ADICIONAL'),
('91000007G', 'TECNICO_ADICIONAL'),
('91000008H', 'TECNICO_ADICIONAL'),
('91000009I', 'TECNICO_ADICIONAL'),
('91000010J', 'TECNICO_ADICIONAL');

-- Actualizamos tipo equipo de los equipos ya creados y re asignamos a Borja Baston a uno de ellos --
UPDATE "Equipo" SET "tipo_equipo" = 'Profesional' WHERE "id_equipo" IN (1, 2);
UPDATE "EmpleadoDeportivo" SET "id_equipo" = 1 WHERE "dni" = '11111111A';

-- insertamos nuevos entrenadores para que alguno este asignado a equipos ya creados y --
-- otros para crear nuevos equipos --
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('92000001A', 'Álvaro', 'Cervera', 5000.00, '1975-09-20', '692000001'),
('92000002B', 'Roberto', 'Perera', 3500.00, '1980-04-12', '692000002'),
('92000003C', 'Jaime', 'Álvarez', 3000.00, '1986-02-28', '692000003'),
('92000004D', 'Andrés', 'Vallina', 2500.00, '1988-10-15', '692000004');

INSERT INTO "EmpleadoDeportivo" ("dni", "posicion", "id_equipo") VALUES
('92000001A', 'ENTRENADOR', 1),
('92000002B', 'ENTRENADOR', 1),
('92000003C', 'ENTRENADOR', 2),
('92000004D', 'ENTRENADOR', 2);

-- lo mismo con jugadores --
INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('93000001A', 'Carlos', 'Martínez', 2800.00, '1998-04-12', '693000001'),
('93000002B', 'David', 'Costas', 3000.00, '1995-03-26', '693000002'),
('93000003C', 'Dani', 'Calvo', 2900.00, '1994-04-01', '693000003'),
('93000004D', 'Rodri', 'Tarín', 2600.00, '1996-07-05', '693000004'),
('93000005E', 'Lucas', 'Ahijado', 2400.00, '1995-01-30', '693000005'),
('93000006F', 'Luismi', 'Sánchez', 2700.00, '1992-05-05', '693000006'),
('93000007G', 'Víctor', 'Camarasa', 3100.00, '1994-05-28', '693000007'),
('93000008H', 'Hugo', 'Rama', 2500.00, '1996-11-22', '693000008'),
('93000009I', 'Viti', 'Rozada', 2600.00, '1997-09-16', '693000009'),
('93000010J', 'Masca', 'Gómez', 2300.00, '2000-07-10', '693000010');

INSERT INTO "EmpleadoDeportivo" ("dni", "posicion", "id_equipo") VALUES
('93000001A', 'JUGADOR', 1),
('93000002B', 'JUGADOR', 1),
('93000003C', 'JUGADOR', 1),
('93000004D', 'JUGADOR', 1),
('93000005E', 'JUGADOR', 1),
('93000006F', 'JUGADOR', 1),
('93000007G', 'JUGADOR', 1),
('93000008H', 'JUGADOR', 1),
('93000009I', 'JUGADOR', 1),
('93000010J', 'JUGADOR', 1);

INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('94000001A', 'Marco', 'Suárez', 1500.00, '2004-06-15', '694000001'),
('94000002B', 'Charbel', 'Gómez', 1400.00, '2004-01-20', '694000002'),
('94000003C', 'Jaime', 'Vázquez', 1400.00, '2005-02-14', '694000003'),
('94000004D', 'Osky', 'Menéndez', 1300.00, '2004-08-30', '694000004'),
('94000005E', 'Yayo', 'González', 1600.00, '2004-07-18', '694000005'),
('94000006F', 'Víctor', 'Blanco', 1400.00, '2003-04-10', '694000006'),
('94000007G', 'Nacho', 'Estrada', 1350.00, '2004-11-25', '694000007'),
('94000008H', 'Mario', 'Fuente', 1500.00, '2004-03-08', '694000008'),
('94000009I', 'Enric', 'Baquero', 1450.00, '2002-09-12', '694000009'),
('94000010J', 'Pelayo', 'Rey', 1350.00, '2004-05-19', '694000010');

INSERT INTO "EmpleadoDeportivo" ("dni", "posicion", "id_equipo") VALUES
('94000001A', 'JUGADOR', 2),
('94000002B', 'JUGADOR', 2),
('94000003C', 'JUGADOR', 2),
('94000004D', 'JUGADOR', 2),
('94000005E', 'JUGADOR', 2),
('94000006F', 'JUGADOR', 2),
('94000007G', 'JUGADOR', 2),
('94000008H', 'JUGADOR', 2),
('94000009I', 'JUGADOR', 2),
('94000010J', 'JUGADOR', 2);

INSERT INTO "BaseEmpleado" ("dni", "nombre", "apellido", "salario", "fecha_nacimiento", "numero_de_telefono") VALUES
('95000001A', 'Adrián', 'López', 2200.00, '1998-02-10', '695000001'),
('95000002B', 'Cristian', 'Rivera', 2300.00, '1997-07-09', '695000002'),
('95000003C', 'Carlos', 'Dotor', 2100.00, '2001-03-15', '695000003'),
('95000004D', 'Sebas', 'Moyano', 2400.00, '1997-03-23', '695000004'),
('95000005E', 'Alemao', 'Silva', 2500.00, '1998-04-01', '695000005'),
('95000006F', 'Paulino', 'Guerra', 2200.00, '1997-06-27', '695000006'),
('95000007G', 'Álex', 'Millán', 2100.00, '1999-11-07', '695000007'),
('95000008H', 'Ilyas', 'Chaira', 2000.00, '2001-02-02', '695000008'),
('95000009I', 'Rahim', 'Alhassan', 2100.00, '2002-04-26', '695000009'),
('95000010J', 'Kwasi', 'Sibo', 2200.00, '1998-06-24', '695000010');

INSERT INTO "EmpleadoDeportivo" ("dni", "posicion") VALUES
('95000001A', 'JUGADOR'),
('95000002B', 'JUGADOR'),
('95000003C', 'JUGADOR'),
('95000004D', 'JUGADOR'),
('95000005E', 'JUGADOR'),
('95000006F', 'JUGADOR'),
('95000007G', 'JUGADOR'),
('95000008H', 'JUGADOR'),
('95000009I', 'JUGADOR'),
('95000010J', 'JUGADOR');

INSERT INTO "Accionista" ("dni", "nombre", "apellidos", "email") VALUES
('71987654A', 'Lucas', 'Vega Fernández', 'lucas.vega@correo.es'),
('71876543B', 'Lucía', 'Fernández González', 'lucia.fernandez@correo.es'),
('71765432C', 'Carlos', 'Rodríguez Menéndez', 'carlos.rodriguez@correo.es'),
('71654321D', 'Elena', 'García Suárez', 'elena.garcia@correo.es');

INSERT INTO "Campana" ("id_campana", "nombre", "fase", "precio_emision") VALUES
(1, 'Campaña 2024', 1, 10.00),
(2, 'Campaña 2024', 2, 12.00),
(3, 'Ampliación 2025', 1, 15.00);

-- Acciones de prueba (para Lucas Vega Fernández - 71987654A, exactamente como en el prototipo)
INSERT INTO "Accion" ("id_accion", "dni_propietario", "fecha_adquisicion", "precio_adquisicion", "en_venta", "precio_venta", "origen_tipo", "id_campana", "vendedor_previo") VALUES
(1, '71987654A', '2024-01-10', 10.00, 1, 18.00, 'CAMPANA', 1, NULL),
(2, '71987654A', '2024-01-11', 15.00, 1, NULL, 'COMPRA_ACCIONISTA', NULL, 'Lucía Fernández González'),
(3, '71987654A', '2025-02-03', 12.00, 0, NULL, 'COMPRA_ACCIONISTA', NULL, 'Carlos Rodríguez Menéndez'),
(4, '71987654A', '2025-03-15', 15.00, 0, NULL, 'CAMPANA', 3, NULL);

-- Acciones de prueba para otros accionistas (para un total de 20 en el club)
INSERT INTO "Accion" ("id_accion", "dni_propietario", "fecha_adquisicion", "precio_adquisicion", "en_venta", "precio_venta", "origen_tipo", "id_campana", "vendedor_previo") VALUES
(5, '71876543B', '2024-01-10', 10.00, 0, NULL, 'CAMPANA', 1, NULL),
(6, '71876543B', '2024-01-10', 10.00, 0, NULL, 'CAMPANA', 1, NULL),
(7, '71876543B', '2024-06-01', 12.00, 1, 16.00, 'CAMPANA', 2, NULL),
(8, '71876543B', '2024-06-01', 12.00, 0, NULL, 'CAMPANA', 2, NULL),
(9, '71876543B', '2025-03-15', 15.00, 0, NULL, 'CAMPANA', 3, NULL),
(10, '71765432C', '2024-01-10', 10.00, 1, 14.00, 'CAMPANA', 1, NULL),
(11, '71765432C', '2024-01-10', 10.00, 0, NULL, 'CAMPANA', 1, NULL),
(12, '71765432C', '2024-06-01', 12.00, 0, NULL, 'CAMPANA', 2, NULL),
(13, '71765432C', '2024-06-01', 12.00, 1, 15.00, 'CAMPANA', 2, NULL),
(14, '71765432C', '2025-03-15', 15.00, 0, NULL, 'CAMPANA', 3, NULL),
(15, '71654321D', '2024-01-10', 10.00, 0, NULL, 'CAMPANA', 1, NULL),
(16, '71654321D', '2024-01-10', 10.00, 0, NULL, 'CAMPANA', 1, NULL),
(17, '71654321D', '2024-06-01', 12.00, 0, NULL, 'CAMPANA', 2, NULL),
(18, '71654321D', '2024-06-01', 12.00, 0, NULL, 'CAMPANA', 2, NULL),
(19, '71654321D', '2025-03-15', 15.00, 0, NULL, 'CAMPANA', 3, NULL),
(20, '71654321D', '2025-03-15', 15.00, 0, NULL, 'CAMPANA', 3, NULL);
