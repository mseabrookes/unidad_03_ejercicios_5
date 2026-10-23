package ejercicios;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Ejercicio_03_05_03 {

    public static void main(String[] args) throws SQLException {

    	HikariDataSource constructor_fuente_de_datos = new HikariDataSource();
    	
    	constructor_fuente_de_datos.setJdbcUrl("jdbc:h2:~/mydatabase;INIT=RUNSCRIPT FROM 'classpath:schema" + ".sql'");
    	constructor_fuente_de_datos.setUsername("FuegoAbrasador");
    	constructor_fuente_de_datos.setPassword("Tr53n0Q533str3m3c3");
    	
    	DataSource fuente_de_datos = constructor_fuente_de_datos;
    	int actualizacion;
    	int identificador_usuario;
    	String nombre_usuario;
    	String apellido_usuario;
    	LocalDateTime fecha_de_registro_usuario;
    	
    	String informacion_prepared_statement = "update " + "users set first_name = concat(first_name,?) where id >= ? ";
    	String informacion_prepared_statement_2 = "select * " + "from users";

        try (Connection conexion = fuente_de_datos.getConnection(); PreparedStatement acceso = conexion.prepareStatement(informacion_prepared_statement)) {
        	acceso.setString(1, "!");
        	acceso.setInt(2, 2);
        	
        	actualizacion = acceso.executeUpdate();

            System.out.println("I just updated " + actualizacion + " rows");


            try (PreparedStatement acceso_2 = conexion.prepareStatement(informacion_prepared_statement_2); ResultSet resultado_acceso_2 = acceso_2.executeQuery()) {
                while (resultado_acceso_2.next()) {
                	identificador_usuario = resultado_acceso_2.getInt("id");
                 	nombre_usuario = resultado_acceso_2.getString("first_name");
                 	apellido_usuario = resultado_acceso_2.getString("last_name");
                 	fecha_de_registro_usuario = resultado_acceso_2.getObject("registration_date", LocalDateTime.class);

                 	System.out.println("Usuario: " + identificador_usuario + " | " + nombre_usuario + " | " + apellido_usuario + " | " + fecha_de_registro_usuario);
                    }
            } catch (Exception e) {
            	e.printStackTrace();
              }
        } catch (Exception e) {
        	e.printStackTrace();
        	}
    }
}