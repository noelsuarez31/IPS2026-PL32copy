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

delete from TareaMantenimientoJardinero;
delete from TareaMantenimiento;

INSERT INTO "Producto" ("nombre", "tipo", "precio") VALUES 
('Sudadera', 'ParteArriba', 65.00),
('Camiseta', 'ParteArriba', 35.00),
('Pantalon', 'ParteAbajo', 35.00),
('Taza', 'Accesorios', 12.00),
('Gorra', 'Accesorios', 16.00),
('Mochila', 'Accesorios', 19.00);

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
(12, (SELECT id_producto FROM Producto WHERE nombre='Camiseta'), 1, 35.0),
(12, (SELECT id_producto FROM Producto WHERE nombre='Pantalon'), 1, 35.0),
(12, (SELECT id_producto FROM Producto WHERE nombre='Taza'), 1, 12.0),
(30, (SELECT id_producto FROM Producto WHERE nombre='Sudadera'), 1, 65.0),
(30, (SELECT id_producto FROM Producto WHERE nombre='Gorra'), 2, 16.0);

INSERT INTO "CategoriaEquipo" ("nombre", "edad_minima", "edad_maxima", "tipo_equipo") VALUES 
('Primer equipo', 23, 40, 'Profesional'),
('Filial', 23, 40, 'Profesional'),
('Regional', 23, 40, 'En formacion'),
('Juvenil', 17, 19, 'En formacion');
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