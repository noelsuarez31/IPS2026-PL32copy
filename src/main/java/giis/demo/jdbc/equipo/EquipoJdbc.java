package giis.demo.jdbc.equipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import giis.demo.model.empleado.EmpleadoDeportivo;
import giis.demo.model.equipo.Equipo;

    public class EquipoJdbc {
    	
        public final static String QUERY_GET_CATEGORY_ID = "SELECT id_categoria FROM CategoriaEquipo WHERE nombre=?";
        
        public final static String QUERY_CREATE_TEAM = "INSERT INTO Equipo (nombre, es_propio, tipo_equipo, id_categoria) VALUES (?,?,?,?)";
        
        public final static String QUERY_SAVE_TEAM_MEMBERS = "UPDATE EmpleadoDeportivo SET id_equipo=? WHERE dni=?";
        
        public final static String QUERY_CHECK_MEMBER_TEAM = "SELECT id_equipo FROM EmpleadoDeportivo WHERE dni=?";
        
        public final static String QUERY_GET_LOCAL_TEAMS = "SELECT * FROM Equipo WHERE es_propio = 1 ORDER BY nombre";

        
        /**
         * Almacena un equipo en la BBDD. Saca el id_equipo de la BBDD (autoincremental).
         * Comprueba que los jugadores, los entrenadores y los miembros del cuerpo tecnico adicional
         * no tienen equipo asignado (ya que no se pueden repetir). Si no lo tienen,
         * les asigna el id_equipo correspondiente
         * @param con
         * @param equipo
         * @param jugadores
         * @param entrenadores
         * @param tecnicosAdicionales
         * @throws SQLException
         */
        public static void almacenarEquipo(Connection con, 
                Equipo equipo, List<EmpleadoDeportivo> jugadores, List<EmpleadoDeportivo> entrenadores, List<EmpleadoDeportivo> tecnicosAdicionales) throws SQLException {

            boolean originalAutoCommit = con.getAutoCommit();
            
            try {
                con.setAutoCommit(false);

                int idCategoria = 0;
                try(PreparedStatement pstGetCategoryId = con.prepareStatement(QUERY_GET_CATEGORY_ID)){
                    pstGetCategoryId.setString(1, equipo.getCategoriaEquipo().getNombre());
                    
                    try(ResultSet rsCategoria = pstGetCategoryId.executeQuery()) {
                        while(rsCategoria.next()) {
                            idCategoria = rsCategoria.getInt(1);
                        }
                    }
                }
                
                for(EmpleadoDeportivo ed : jugadores) {
                    validarMiembroSinEquipo(con, ed.getDni(), ed.getNombre(), "jugador");
                }
                
                for(EmpleadoDeportivo entr : entrenadores) {
                    validarMiembroSinEquipo(con, entr.getDni(), entr.getNombre(), "entrenador");
                }
                
                for(EmpleadoDeportivo ed : tecnicosAdicionales) {
                    validarMiembroSinEquipo(con, ed.getDni(), ed.getNombre(), "técnico adicional");
                }
                
                try(PreparedStatement pstCreateTeam = con.prepareStatement(QUERY_CREATE_TEAM, Statement.RETURN_GENERATED_KEYS)){
                    pstCreateTeam.setString(1, equipo.getNombre());
                    pstCreateTeam.setInt(2, equipo.getIsPropio()? 1 : 0);
                    pstCreateTeam.setString(3, equipo.getTipoEquipo());
                    pstCreateTeam.setInt(4, idCategoria);
                    
                    int updatedRows = pstCreateTeam.executeUpdate();
                    if(updatedRows != 1) {
                        throw new RuntimeException("numero de filas actualizadas incorrecto");
                    }
                    
                    try (ResultSet generatedKeys = pstCreateTeam.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int idGenerado = generatedKeys.getInt(1);
                            equipo.setIdEquipo(idGenerado); 
                        } else {
                            throw new SQLException("Creación de equipo fallida, no se obtuvo el ID.");
                        }
                    }
                }
                
                for(EmpleadoDeportivo ed : jugadores) {
                    try(PreparedStatement pstSaveJugadores = con.prepareStatement(QUERY_SAVE_TEAM_MEMBERS)){
                        pstSaveJugadores.setInt(1, equipo.getIdEquipo());
                        pstSaveJugadores.setString(2, ed.getDni());
                        pstSaveJugadores.executeUpdate();
                    }
                }
                
                for(EmpleadoDeportivo entr : entrenadores) {
                    try(PreparedStatement pstSaveEntrenadores = con.prepareStatement(QUERY_SAVE_TEAM_MEMBERS)){
                        pstSaveEntrenadores.setInt(1, equipo.getIdEquipo());
                        pstSaveEntrenadores.setString(2, entr.getDni());
                        pstSaveEntrenadores.executeUpdate();
                    }
                }
                
                for(EmpleadoDeportivo ed : tecnicosAdicionales) {
                    try(PreparedStatement pstSaveTecAdicionales = con.prepareStatement(QUERY_SAVE_TEAM_MEMBERS)){
                        pstSaveTecAdicionales.setInt(1, equipo.getIdEquipo());
                        pstSaveTecAdicionales.setString(2, ed.getDni());
                        pstSaveTecAdicionales.executeUpdate();
                    }
                }
                
                con.commit();
                
            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                throw e;
            } finally {
                try {
                    con.setAutoCommit(originalAutoCommit);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        private static void validarMiembroSinEquipo(Connection con, String dni, String nombre, String rol) throws SQLException {
            try(PreparedStatement pstCheck = con.prepareStatement(QUERY_CHECK_MEMBER_TEAM)){
                pstCheck.setString(1, dni);
                try(ResultSet rs = pstCheck.executeQuery()) {
                    if(rs.next()) {
                        rs.getInt("id_equipo");
                        if(!rs.wasNull()) {
                            throw new SQLException("El " + rol + " " + nombre + " (" + dni + ") ya pertenece a otro equipo.");
                        }
                    }
                }
            }
        }
        
        public static List<Equipo> getEquiposPropios(Connection con) throws SQLException {
        	
        	List<Equipo> equipos = new ArrayList<Equipo>();
        	
            try(PreparedStatement pst = con.prepareStatement(QUERY_GET_LOCAL_TEAMS);
            		ResultSet rs = pst.executeQuery()){
               
            	while(rs.next()) {
            		
            		Equipo eq = new Equipo(rs.getString("nombre"), 
            				rs.getString("tipo_equipo"), null);
            		
            		eq.setIdEquipo(rs.getInt("id_equipo"));
            		eq.setIsPropio(true);
            		equipos.add(eq);
            		
            	}
            }
            
            return equipos;
        }
        
}