package ejercicios;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Ejercicio_03_05_01  {

    public static void main(String[] args) {
    	
    	HikariDataSource constructor_fuente_de_datos = new HikariDataSource();
    	
    	constructor_fuente_de_datos.setJdbcUrl("jdbc:h2:~/mydatabase;INIT=RUNSCRIPT FROM 'classpath:schema" + ".sql'");
    	constructor_fuente_de_datos.setUsername("FuegoAbrasador");
    	constructor_fuente_de_datos.setPassword("Tr53n0Q533str3m3c3");
    	
    	DataSource fuente_de_datos = constructor_fuente_de_datos;
        //desconozco si una Variable "HikariDataSource" sea igual a un "DataSource". El hecho de poder pasar uno como parametro al otro sin mayor complicación implica que sí, pero el código del los apuntes me ha echo dudar.
    	
    	String informacion_prepared_statement = "insert into" + " users (first_name, last_name, registration_date) " + "values (?,?,?)";

        try (Connection conexion = fuente_de_datos.getConnection(); PreparedStatement acceso = conexion.prepareStatement(informacion_prepared_statement,  Statement.RETURN_GENERATED_KEYS)) {
        	acceso.setString(1, "Mikel");
        	acceso.setString(2, "Etxebarria");
        	acceso.setObject(3, LocalDateTime.now());
        	acceso.addBatch();
        	acceso.setString(1, "Jaione");
        	acceso.setString(2, "Gomez");
        	acceso.setObject(3, LocalDateTime.now());

        	acceso.addBatch();

            int[] numero_de_inserciones_usuario = acceso.executeBatch();
            
            System.out.println("Se han insertado " + Arrays.toString(numero_de_inserciones_usuario) + " filas en la base de datos.");
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

}