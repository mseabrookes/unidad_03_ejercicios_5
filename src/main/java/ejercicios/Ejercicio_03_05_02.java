package ejercicios;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

public class Ejercicio_03_05_02 {

    public static void main(String[] args) {
    	
    	HikariDataSource constructor_fuente_de_datos = new HikariDataSource();
    	
    	constructor_fuente_de_datos.setJdbcUrl("jdbc:h2:~/mydatabase;INIT=RUNSCRIPT FROM 'classpath:schema" + ".sql'");
    	constructor_fuente_de_datos.setUsername("FuegoAbrasador");
    	constructor_fuente_de_datos.setPassword("Tr53n0Q533str3m3c3");
    	
    	DataSource fuente_de_datos = constructor_fuente_de_datos;
    	int identificador_usuario;
    	String nombre_usuario;
    	String apellido_usuario;
    	LocalDateTime fecha_de_registro_usuario;
    	
    	String informacion_prepared_statement = "select * " + "from users";
    	//He cogido la costumbre de hacer PreparedStatement siempre. En este caso, creo que un statement normal también valdría; no lo he comprobado por ahorrarme el dolor de cabeza.

        try (Connection conexion = fuente_de_datos.getConnection(); PreparedStatement acceso = conexion.prepareStatement("select * " + "from users"); ResultSet resultado_acceso = acceso.executeQuery()) {
        	while (resultado_acceso.next()) {
            	identificador_usuario = resultado_acceso.getInt("id");
             	nombre_usuario = resultado_acceso.getString("first_name");
             	apellido_usuario = resultado_acceso.getString("last_name");
             	fecha_de_registro_usuario = resultado_acceso.getObject("registration_date", LocalDateTime.class);

             	System.out.println("Usuario: " + identificador_usuario + " | " + nombre_usuario + " | " + apellido_usuario + " | " + fecha_de_registro_usuario);
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
        } 
    }