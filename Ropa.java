package SistemaTienda;

import java.util.List;
import javax.swing.*;
import java.io.FileInputStream;
import java.sql.Blob;

/**
 * Esta clase se emplea como intermediario entre las clases que reciben información sobre que quiere hacer el usuario o administrador y enviar esa información
 * a la base de datos ya sea para escribir, leer o actualizar datos de nuestra base de datos siempre que esa información esté relacionada con la ropa.
 * En esta clase también se gestiona si dicha información es correcta o incorrecta para ser gestionada por la base de datos.
 * Y corresponde a la parte de "Controlador" de nuestro MVC
 */

public class Ropa{

    private FileInputStream f_imagen;
    private Blob image;
    private String genero;
    private String tipo_prenda;
    private String precio;
    private String nombre_prenda;
    private String descripcion;
    private String stock;

    Ropa(){}

    public Blob getImage(){
        return image;
    }
    public void setImage(Blob b){
        image=b;
    }
    public FileInputStream getF_imagen() {
        return f_imagen;
    }
    public void setF_imagen(FileInputStream f_imagen) {
        this.f_imagen = f_imagen;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getTipo_prenda() {
        return tipo_prenda;
    }
    public void setTipo_prenda(String tipo_prenda) {
        this.tipo_prenda = tipo_prenda;
    }
    public String getPrecio() {
        return precio;
    }
    public void setPrecio(String precio) {
        this.precio = precio;
    }
    public String getNombre_prenda() {
        return nombre_prenda;
    }
    public void setNombre_prenda(String nombre_prenda) {
        this.nombre_prenda = nombre_prenda;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getStock() {
        return stock;
    }
    public void setStock(String stock) {
        this.stock = stock;
    }

    public List<Blob> infoImagenes(){
        LeerEscribirBD x = new LeerEscribirBD();
        return(x.leerImagen());
    }
    public List<List<String>> infoRopa() {
        LeerEscribirBD x = new LeerEscribirBD();

        return(x.leerRopa());
    }

    /**
     * Método que envía la información de una prenda para ser añadida a la base de datos comprobando que los datos son correctos
     * @param imagen imagen seleccionada desde nuestra carpeta
     * @param g género de la prenda
     * @param t tipo de prenda
     * @param p precio de la prenda
     * @param n nombre del producto
     * @param d descripción del producto
     * @param s stock del producto
     */

    public boolean guardarImagen(FileInputStream imagen, String g, String t, String p, String n, String d, String s) {

        Ropa r = new Ropa();
        boolean retorno;
        LeerEscribirBD le = new LeerEscribirBD();

        if (imagen==null || g==null || t ==null || p==null || n ==null || d ==null || s==null) {

            JOptionPane.showMessageDialog(null, "Campos vacíos");
            return false;

        } else {

            r.setF_imagen(imagen);
            r.setGenero(g);
            r.setTipo_prenda(t);
            r.setPrecio(p);
            r.setNombre_prenda(n);
            r.setDescripcion(d);
            r.setStock(s);

            retorno = le.escribirImagen(r);
            if(retorno){
                JOptionPane.showMessageDialog(null, "Imagen añadida");
            }
        }
        return retorno;
    }

    /**
     * Método que se encarga de enviar la información de una prenda específica para su posterior borrado de la base de datos
     * @param e nombre del producto a eliminar
     */
    public boolean borrarRopa(String e){
        boolean retornar;
        LeerEscribirBD x = new LeerEscribirBD();

        if(e!=null){
            retornar = x.eliminarRopa(e);
            return retornar;
        }

        return false;

    }

    /**
     * Método encargado de crear una clase "Ropa" que contenga los respectivos atributos de una prenda para su posterior guardado en el carrito
     * @param blob lista dinámica de las imágenes leídas de la base de datos
     * @param vec lista dinámica de dos dimensiones que contiene la información respectiva de cada prenda almacenada en la base de datos
     * @param indice posición que adquiere cada prenda que se muestra para poder acceder desde la lista anterior "vec"
     */
    public void guardarCarrito(List<Blob> blob, List<List<String>> vec, int indice){

        Ropa r = new Ropa();
        LeerEscribirBD le = new LeerEscribirBD();

        r.setImage(blob.get(indice));
        r.setGenero(vec.get(indice).get(0));
        r.setTipo_prenda(vec.get(indice).get(1));
        r.setPrecio(vec.get(indice).get(2));
        r.setNombre_prenda(vec.get(indice).get(3));
        r.setDescripcion(vec.get(indice).get(4));

        boolean p = le.escribirCarrito(r);
        if(p){
            JOptionPane.showMessageDialog(null, "Prenda añadida al carrito");
        } else{
            JOptionPane.showMessageDialog(null, "Error al añadir al carrito");
        }

    }
}
