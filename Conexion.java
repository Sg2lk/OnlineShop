package SistemaTienda;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que se encarga únicamente de establecer la conexión entre java y la base de datos MySQL y que corresponde
 * a la parte de "Controlador" dentro de nuestro patrón diseño.
 */

public class Conexion{
    Connection con;

    /**
     * Método que sirve para acceder a la base de datos correspondiente
     * @return devuelve la conexión si se ha podido acceder y null si ha habido algún error en la conexión
     */

    public Connection getConnection() {
        try {
            String BD = "jdbc:mysql://localhost:3306/abc?serverTimezone=UTC";
            con = DriverManager.getConnection(BD, "root", "");
            return con;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"¡Conexión fallida!");
            System.out.println(e.getMessage());
        }
        return null;
    }
}
