--Primero se deben borrar todas las tablas

--Para giis.demo.tkrun:
Drop table if exists Entrada;
drop table if exists Venta;
drop table if exists Partido;
drop table if exists Butaca;

drop table if exists BaseEmpleado;
drop table if exists EmpleadoDeportivo;
drop table if exists EmpleadoNoDeportivo;
drop table if exists CategoriaEquipo;
drop table if exists Equipo;

drop table if exists HorarioPeriodico;
drop table if exists HorarioEspecifico;
drop table if exists Entrenamiento;
drop table if exists FranjaHoraria;
drop table if exists Entrevista;

drop table if exists LineaVenta;
drop table if exists Producto;

drop table if exists Reserva;
drop table if exists Instalacion;
drop table if exists Usuario;
drop table if exists Abonado;
drop table if exists Abono;
drop table if exists PrecioSeccion;

drop table if exists TareaMantenimientoJardinero;
drop table if exists TareaMantenimiento;

CREATE TABLE IF NOT EXISTS "butaca" (
	"id_butaca"	INTEGER,
	"tribuna"	TEXT NOT NULL CHECK("tribuna" IN ('A', 'B', 'C', 'D')),
	"seccion"	TEXT NOT NULL CHECK("seccion" IN ('A', 'B', 'C', 'D', 'E', 'F')),
	"fila"	INTEGER NOT NULL CHECK("fila" BETWEEN 1 AND 10),
	"asiento"	INTEGER NOT NULL CHECK("asiento" BETWEEN 1 AND 15),
	PRIMARY KEY("id_butaca"),
	UNIQUE("tribuna","seccion","fila","asiento")
);
CREATE TABLE IF NOT EXISTS "entrada" (
	"id_entrada"	INTEGER,
	"id_partido"	INTEGER NOT NULL,
	"id_venta"	INTEGER NOT NULL,
	"id_butaca"	INTEGER NOT NULL,
	"precio"	REAL NOT NULL DEFAULT 30.0,
	PRIMARY KEY("id_entrada"),
	UNIQUE("id_partido","id_butaca"),
	FOREIGN KEY("id_butaca") REFERENCES "butaca"("id_butaca"),
	FOREIGN KEY("id_partido") REFERENCES "partido"("id_partido"),
	FOREIGN KEY("id_venta") REFERENCES "venta"("id_venta")
);

CREATE TABLE IF NOT EXISTS "venta" (
	"id_venta"	INTEGER,
	"fecha"	TEXT NOT NULL,
	"concepto"	TEXT,
	"tipo"	TEXT NOT NULL CHECK("tipo" IN ('ENTRADAS', 'MERCHANDISING', 'ABONOS', 'CAMPANAS_ACCIONISTAS')),
	"total"	REAL NOT NULL,
	PRIMARY KEY("id_venta")
);

CREATE TABLE IF NOT EXISTS "BaseEmpleado" (
    "dni" TEXT,
    "nombre" TEXT NOT NULL,
    "apellido" TEXT NOT NULL,
    "salario" NUMERIC NOT NULL CHECK(salario > 0),
    "fecha_nacimiento" DATE NOT NULL,
    "numero_de_telefono" TEXT NOT NULL UNIQUE,
    PRIMARY KEY("dni")
);

CREATE TABLE IF NOT EXISTS "EmpleadoDeportivo" (
	"dni" TEXT,
	"posicion" TEXT NOT NULL,
	"id_equipo"	INTEGER,
	PRIMARY KEY("dni"),
	FOREIGN KEY("dni") REFERENCES "BaseEmpleado"("dni") ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY("id_equipo") REFERENCES "Equipo"("id_equipo") ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS "EmpleadoNoDeportivo" (
    "dni" TEXT,
    "posicion" TEXT NOT NULL,
    PRIMARY KEY("dni"),
    FOREIGN KEY("dni") REFERENCES "BaseEmpleado"("dni") ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS "CategoriaEquipo" (
    "nombre" TEXT NOT NULL,
    "id_categoria" INTEGER PRIMARY KEY AUTOINCREMENT,
    "edad_minima" INTEGER NOT NULL,
    "edad_maxima" INTEGER NOT NULL,
    "tipo_equipo" TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS "Equipo" (
    "id_equipo" INTEGER PRIMARY KEY AUTOINCREMENT,
    "nombre" TEXT NOT NULL,
    "es_propio" INTEGER NOT NULL CHECK("es_propio" IN (0, 1)),
    "tipo_equipo" TEXT NOT NULL,
    "id_categoria" INTEGER NOT NULL,
    FOREIGN KEY("id_categoria") REFERENCES "CategoriaEquipo"("id_categoria") ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS "HorarioPeriodico" (
	"id_horario"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"dni_empleado"	TEXT NOT NULL,
	"dia_semana"	INTEGER NOT NULL CHECK("dia_semana" BETWEEN 1 AND 7),
	"hora_inicio"	TEXT NOT NULL,
	"hora_fin"		TEXT NOT NULL,
	FOREIGN KEY("dni_empleado") REFERENCES "EmpleadoNoDeportivo"("dni") ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS "HorarioEspecifico" (
	"id_horario"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"dni_empleado"	TEXT NOT NULL,
	"fecha"			DATE NOT NULL,
	"hora_inicio"	TEXT NOT NULL,
	"hora_fin"		TEXT NOT NULL,
	FOREIGN KEY("dni_empleado") REFERENCES "EmpleadoNoDeportivo"("dni") ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS "FranjaHoraria" (
	"id_franja" INTEGER PRIMARY KEY AUTOINCREMENT,
	"dni_jugador" TEXT NOT NULL,
	"fecha" DATE NOT NULL,
	"hora_inicio" TIME NOT NULL,
	"hora_fin" TIME NOT NULL,
	FOREIGN KEY ("dni_jugador") REFERENCES "EmpleadoDeportivo"("dni")
);

CREATE TABLE IF NOT EXISTS "Entrevista" (
	"id_entrevista" INTEGER PRIMARY KEY AUTOINCREMENT,
	"id_franja" NOT NULL,
	"nombre_medio" NOT NULL,
	FOREIGN KEY ("id_franja") REFERENCES "FranjaHoraria"("id_franja")
);

CREATE TABLE IF NOT EXISTS "Producto" (
	"id_producto" INTEGER PRIMARY KEY AUTOINCREMENT,
	"nombre" TEXT NOT NULL,
	"tipo" TEXT NOT NULL,
	"precio" REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS "LineaVenta" (
	"id_venta" INTEGER NOT NULL,
	"id_producto" INTEGER NOT NULL,
	"unidades" INTEGER NOT NULL,
	"precio_unitario" REAL NOT NULL,
	PRIMARY KEY("id_venta", "id_producto"),
	FOREIGN KEY("id_venta") REFERENCES "venta"("id_venta") ON DELETE CASCADE,
	FOREIGN KEY("id_producto") REFERENCES "Producto"("id_producto") ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "Partido" (
	"id_partido" INTEGER PRIMARY KEY AUTOINCREMENT,
	"fecha"	DATE NOT NULL,
	"hora_inicio" TIME NOT NULL,
	"hora_fin" TIME NOT NULL,
	"id_local"	INTEGER NOT NULL,
	"id_visitante"	INTEGER NOT NULL,
	"id_instalacion" INTEGER NOT NULL,
	
	"dia_del_club" INTEGER NOT NULL DEFAULT 0 CHECK("dia_del_club" IN (0,1)),
	"precio_abonado" REAL CHECK("precio_abonado" IS NULL OR "precio_abonado" >= 0),
	
	FOREIGN KEY("id_local") REFERENCES "Equipo"("id_equipo"),
	FOREIGN KEY("id_visitante") REFERENCES "Equipo"("id_equipo"),
	FOREIGN KEY("id_instalacion") REFERENCES "Instalacion"("id_instalacion"),
	CHECK("id_local" <> "id_visitante")
);

CREATE TABLE IF NOT EXISTS "PrecioSeccion" (
	
	"id_partido" INTEGER NOT NULL,
	"seccion" TEXT NOT NULL,
	"precio" REAL NOT NULL CHECK("precio" >= 0),
	PRIMARY KEY("id_partido", "seccion"),
	FOREIGN KEY("id_partido") REFERENCES "Partido"("id_partido")
	
);

CREATE TABLE IF NOT EXISTS "Entrenamiento" (
	"id_entrenamiento" INTEGER PRIMARY KEY AUTOINCREMENT,
	"fecha" DATE NOT NULL,
	"hora_inicio" TIME NOT NULL,
	"hora_fin" TIME NOT NULL,
	"id_equipo" INTEGER NOT NULL,
	"id_instalacion" INTEGER NOT NULL,
	FOREIGN KEY ("id_equipo") REFERENCES "Equipo"("id_equipo"),
	FOREIGN KEY("id_instalacion") REFERENCES "Instalacion"("id_instalacion")
);

CREATE TABLE IF NOT EXISTS "Reserva" (
	"id_reserva" INTEGER PRIMARY KEY AUTOINCREMENT,
	"fecha" TEXT NOT NULL,
	"hora_inicio" TEXT NOT NULL,
	"hora_fin" TEXT NOT NULL,
	"importe" REAL NOT NULL CHECK("importe" > 0),
	"fecha_registro" TEXT NOT NULL,
	"id_usuario" INTEGER NOT NULL,
	"id_instalacion" INTEGER NOT NULL,
	FOREIGN KEY("id_usuario") REFERENCES "Usuario"("id_usuario"),
	FOREIGN KEY("id_instalacion") REFERENCES "Instalacion"("id_instalacion"),
	CHECK("hora_inicio" >= '08:00'),
	CHECK("hora_fin" <= '22:00'),
	CHECK("hora_inicio" < "hora_fin")
);

CREATE TABLE IF NOT EXISTS "Usuario" (
	"id_usuario" INTEGER PRIMARY KEY AUTOINCREMENT,
	"nombre" TEXT NOT NULL,
	"tarjeta_bancaria" TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS "Instalacion" (
	"id_instalacion" INTEGER PRIMARY KEY,
	"nombre" TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS "Abonado" (
	"id_abonado" INTEGER PRIMARY KEY AUTOINCREMENT,
	"dni" TEXT NOT NULL UNIQUE,
	"nombre" TEXT NOT NULL,
	"apellidos" TEXT NOT NULL,
	"fecha_nacimiento" TEXT NOT NULL,
	"email" TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS "Abono" (
	"id_abono" INTEGER PRIMARY KEY AUTOINCREMENT,
	"id_venta" INTEGER NOT NULL,
	"id_abonado" INTEGER NOT NULL,
	"id_equipo" INTEGER NOT NULL,
	"id_butaca" INTEGER NOT NULL,
	"fecha_inicio" TEXT NOT NULL,
	"fecha_fin" TEXT NOT NULL,
	"fecha_registro" TEXT NOT NULL,
	"categoria" TEXT NOT NULL CHECK("categoria" IN ('INFANTIL', 'SUB_26', 'ADULTO', 'JUBILADO')),
	"precio" REAL NOT NULL,
	UNIQUE("id_abonado", "fecha_inicio"),
	UNIQUE("id_butaca", "fecha_inicio"),
	FOREIGN KEY("id_venta") REFERENCES "Venta"("id_venta"),
	FOREIGN KEY("id_abonado") REFERENCES "Abonado"("id_abonado"),
	FOREIGN KEY("id_equipo") REFERENCES "Equipo"("id_equipo"),
	FOREIGN KEY("id_butaca") REFERENCES "Butaca"("id_butaca")
);

CREATE TABLE IF NOT EXISTS "TareaMantenimiento" (
	"id_tarea" INTEGER PRIMARY KEY AUTOINCREMENT,
	"id_instalacion" INTEGER NOT NULL,
	"fecha" DATE NOT NULL,
	"hora_inicio" TEXT NOT NULL,
	"hora_fin" TEXT NOT NULL,
	"descripcion" TEXT DEFAULT 'Mantenimiento de jardinería',
	FOREIGN KEY("id_instalacion") REFERENCES "Instalacion"("id_instalacion") ON DELETE CASCADE,
	CHECK("hora_inicio" < "hora_fin")
);

CREATE TABLE IF NOT EXISTS "TareaMantenimientoJardinero" (
	"id_tarea" INTEGER NOT NULL,
	"dni_jardinero" TEXT NOT NULL,
	PRIMARY KEY("id_tarea", "dni_jardinero"),
	FOREIGN KEY("id_tarea") REFERENCES "TareaMantenimiento"("id_tarea") ON DELETE CASCADE,
	FOREIGN KEY("dni_jardinero") REFERENCES "EmpleadoNoDeportivo"("dni") ON DELETE CASCADE ON UPDATE CASCADE
);