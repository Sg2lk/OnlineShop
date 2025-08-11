package SistemaTienda;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.sql.*;

/**
 * Clase encargada de establecer la conexión con la base de datos para manipular la información recibida o almacenada
 * Esta clase consistiría en la parte principal de "Controlador" dentro de nuestro patrón de diseño MCV (modelo-vista-controlador)
 */
public class LeerEscribirBD {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();

    /**
     * Método que comprueba si las credenciales de nombre y contraseña de un usuario existen en la base de datos
     * @param nombre nombre del usuario
     * @param pass contraseña del usuario
     * @return devuelve una clase "Cliente" para verificar si los datos escritos coinciden con los leídos en la base de datos
     */
    public Cliente log(String nombre, String pass){

        Cliente l = new Cliente();
        String sql = "SELECT * FROM login WHERE usuario = ? AND password = ?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, pass);

            rs = ps.executeQuery();
            if (rs.next()) {
                l.setNombre(rs.getString("nombre"));
                l.setPass(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }

    /**
     * Se encarga de almacenar la información de un usuario que quiere acceder a la tienda
     * @param c clase Cliente con la información correspondiente a cada usuario (nombre, contraseña, email, teléfono...)
     */
    public void RegistrarUsuario(Cliente c) {
        String sql = "INSERT INTO login (usuario, password, nombre, apellidos, email, telefono, dia, mes, anio, domicilio, tarjeta, caducidad, cvv) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getUsuario());
            ps.setString(2, c.getPass());
            ps.setString(3, c.getNombre());
            ps.setString(4, c.getApellidos());
            ps.setString(5, c.getEmail());
            ps.setInt(6, c.getTelefono());
            ps.setString(7, " ");
            ps.setString(8, " ");
            ps.setString(9, " ");
            ps.setString(10, " ");
            ps.setString(11, " ");
            ps.setString(12, " ");
            ps.setString(13, " ");
            ps.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "error");

        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("error sql");
            }
        }
    }

    /**
     * Método similar al anterior encargado de leer si existe un nombre y contraseña asociado a un administrador.
     * Y retorna una clase "Admin" para su posterior comprobación
     * @param nombre nombre del administrador
     * @param pass contraseña del administrador
     * @return clase "Admin" devuelta para su comprobación con los datos escritos
     */
    public Admin leerAdmin(String nombre, String pass){

        Admin l = new Admin();
        String sql = "SELECT * FROM admins WHERE usuario = ? AND password = ?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, pass);

            rs = ps.executeQuery();
            if (rs.next()) {
                l.setNombre(rs.getString("usuario"));
                l.setPass(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }

    /**
     * Método que se encarga de leer todas las variables de tipo "Blob" de la base de datos, es decir, las imágenes, para almacenarlas y mostrarlas posteriormente en pantalla
     * @return retorna una lista dinámica de tipo "Blob" para ser convertidas a imágenes
     */
    public List<Blob> leerImagen() {
        String sql = "SELECT imagen FROM ropa";
        List<Blob> blobs = new ArrayList<>();

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Blob blob = rs.getBlob(1);
                blobs.add(blob);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return blobs;
    }

    /**
     * Método encargado de eliminar una prenda específica de la base de datos
     * @param cad nombre del producto a eliminar
     */
    public boolean eliminarRopa(String cad){

        String sql = "DELETE FROM ropa WHERE nombre = ?";
        int res;

        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1,cad);
            res = ps.executeUpdate();
            if(res>0){
                JOptionPane.showMessageDialog(null,"Prenda eliminada");
            }
            else{
                JOptionPane.showMessageDialog(null,"Error al eliminar prenda");
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    /**
     * Método que lee de la base de datos la información correspondiente a cada producto
     * @return devuelve una lista dinámica de tipo String de dos dimensiones para su posterior manipulación
     */
    public List<List<String>> leerRopa() {
        String sql = "SELECT genero, tipo, precio, nombre, descripcion, stock FROM ropa"; // Reemplaza tu_tabla por el nombre de tu tabla

        List<List<String>> resultados = new ArrayList<>();

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                List<String> fila = new ArrayList<>();
                fila.add(rs.getString(1)); // Columna 2
                fila.add(rs.getString(2)); // Columna 3
                fila.add(rs.getString(3));
                fila.add(rs.getString(4));
                fila.add(rs.getString(5));
                fila.add(rs.getString(6));
                resultados.add(fila);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultados;
    }

    /**
     * Método que almacena en la base de datos toda la información correspondiente a una prenda
     * @param r clase "Ropa" recibida para obtener los datos de cada producto y ser escritos
     */
    public boolean escribirImagen(Ropa r){
        String sql = "INSERT INTO ropa (imagen,genero,tipo,precio,nombre,descripcion,stock) VALUES (?,?,?,?,?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setBlob(1,r.getF_imagen());
            ps.setString(2,r.getGenero());
            ps.setString(3,r.getTipo_prenda());
            ps.setString(4,r.getPrecio());
            ps.setString(5,r.getNombre_prenda());
            ps.setString(6,r.getDescripcion());
            ps.setString(7,r.getStock());
            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            return false;

        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error sql");
            }
        }

        return true;
    }

    /**
     * Método similar a "leerImagen" que lee las variables de tipo "Blob" procedentes del carrito del usuario
     * @return devuelve una lista dinámica de tipo "Blob" para mostrar posteriormente las imágenes de la ropa guardada por el usuario
     */
    public List<Blob> leerImagenCarrito() {

        String sql = "SELECT imagen FROM carrito";
        List<Blob> blobs = new ArrayList<>();

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Blob blob = rs.getBlob(1);
                blobs.add(blob);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return blobs;
    }

    /**
     * Método similar a "leerRopa" que lee la información de cada prenda guardada en el carrito de un usuario
     * @return retorna una lista dinámica de tipos String de dos dimensiones para su posterior manipulación
     */
    public List<List<String>> leerRopaCarrito() {
        String sql = "SELECT genero, tipo, precio, nombre, descripcion FROM carrito"; // Reemplaza tu_tabla por el nombre de tu tabla

        List<List<String>> resultados = new ArrayList<>();

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                List<String> fila = new ArrayList<>();
                fila.add(rs.getString(1));
                fila.add(rs.getString(2));
                fila.add(rs.getString(3));
                fila.add(rs.getString(4));
                fila.add(rs.getString(5));
                resultados.add(fila);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultados;
    }

    /**
     * Método similar a "RegistrarUsuario" que almacena toda la información de la prenda que ha guardado un usuario en el carrito
     * @param r clase "Ropa" con la información del producto seleccionado
     * @return devuelve un booleano para el manejo de cierre de ventanas
     */
    public boolean escribirCarrito(Ropa r){
        String sql = "INSERT INTO carrito (imagen, genero,tipo,precio,nombre,descripcion) VALUES (?,?,?,?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setBlob(1,r.getImage());
            ps.setString(2,r.getGenero());
            ps.setString(3,r.getTipo_prenda());
            ps.setString(4,r.getPrecio());
            ps.setString(5,r.getNombre_prenda());
            ps.setString(6,r.getDescripcion());
            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error al guardar");

        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("error sql");
            }
        }

        return true;
    }

    /**
     * Método que actualiza la información de un usuario añadiendo sus datos relacionados con el pago
     * @param cad teléfono del usuario para poder identificarlo
     * @param dia dia de nacimiento
     * @param mes mes de nacimiento
     * @param anio año de nacimiento
     * @param domicilio domicilio del usuario
     * @param tarjeta número de tarjeta del usuario
     * @param caducidad caducidad de la tarjeta
     * @param cvv número de seguridad de la tarjeta
     */
    public boolean actualizarCliente(int cad, String dia, String mes, String anio, String domicilio, String tarjeta, String caducidad, String cvv){

        String sql = "UPDATE login SET dia = ?, mes = ?, anio = ?, domicilio = ?, tarjeta = ?, caducidad = ?, cvv = ?  WHERE telefono = ?";
        int res;

        List<List<String>> vec = this.leerRopaCarrito();
        try{

            vec.get(0);

            try{
                con = cn.getConnection();
                ps = con.prepareStatement(sql);

                ps.setString(1,dia);
                ps.setString(2,mes);
                ps.setString(3,anio);
                ps.setString(4,domicilio);
                ps.setString(5,tarjeta);
                ps.setString(6,caducidad);
                ps.setString(7,cvv);
                ps.setInt(8,cad);

                res = ps.executeUpdate();

                if(res>0){
                    JOptionPane.showMessageDialog(null,"Datos del cliente actualizados");
                }
                else{
                    JOptionPane.showMessageDialog(null,"Error al guardar datos del cliente");
                    return false;
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }catch (IndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null,"Carrito vacío");
            return false;
        }
        return true;
    }

    /**
     * Método que produce un bucle para actualizar el stock de cada producto de manera correcta siendo complementado por los dos
     * métodos siguientes
     */
    public void actualizarStock(){

        LeerEscribirBD x = new LeerEscribirBD();

        List<List<String>> cesta = x.leerRopaCarrito();

        for(int i=0;i<cesta.size();i++){
            String cad = cesta.get(i).get(3);//leo el nombre de la prenda
            String stock = x.leerStock(cad);
            int aux = Integer.parseInt(stock);
            aux--;
            String nuevoStock = String.valueOf(aux);//actualizo el stock

            x.updateStockOnly(nuevoStock,cad);
        }
    }

    /**
     * Método encargado de actualizar el stock de una prenda después de ser comprada
     * @param sn nuevo stock de la prenda a ser actualizado
     * @param n nombre del producto para identificarlo y actualizar su stock
     */
    public void updateStockOnly(String sn, String n){

        String sql = "UPDATE ropa SET stock = ? WHERE nombre = ?";

        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1,sn);
            ps.setString(2,n);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método que lee el stock de un producto que hay actualmente en la base de datos
     * @param nombre nombre para identificar el producto a actualizar
     * @return devuelve el stock que hay de un producto para su posterior actualización
     */
    public String leerStock(String nombre){

        String sql = "SELECT * FROM ropa WHERE nombre = ?";

        String cad = null;

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,nombre);

            rs = ps.executeQuery();
            if (rs.next()) {
                cad = rs.getString("stock");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cad;
    }

    /**
     * Método que se encarga de eliminar toda la ropa guardada en el carrito de un usuario después de realizar su compra
     */
    public void cleanCarrito() {
        String sql = "DELETE FROM carrito";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                System.out.println("Se eliminaron todos los registros de la tabla");
            } else {
                System.out.println("Carrito vacío");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar registros: " + e.getMessage());
        }
    }

    /**
     * Método que se emplea para cargar las imágenes de fondo de las distintas ventanas de la tienda
     * @return lista dinámica de tipo BLob que almacena las imágenes de los fondos para su posterior visualización
     */

    public List<Blob> leerFondos() {

        String sql = "SELECT fondo FROM fondos";
        List<Blob> blobs = new ArrayList<>();

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Blob blob = rs.getBlob(1);
                blobs.add(blob);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return blobs;
    }
}
