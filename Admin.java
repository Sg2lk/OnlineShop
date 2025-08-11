package SistemaTienda;

import javax.swing.*;
import java.security.PublicKey;

/**
 * Esta clase se encarga de establecer los atributos que tiene un administrador y que sirve de intermediario entre la información recibida en pantalla y la base de datos.
 * Y corresponde con la parte de "Controlador" de nuestro MVC
 */

public class Admin extends Usuario{
    private String nombre;
    private String pass;

    public Admin(){}

    public void setNombre(String n){
        nombre=n;
    }

    public void setPass(String p){
        pass=p;
    }

    String getNombre(){
        return nombre;
    }

    String getPass(){
        return pass;
    }

    /**
     * Método que sirve para comprobar si los datos escritos en pantalla son correctos y coinciden con los almacenados en la base datos
     * @param nU caja de texto donde el usuario ingresa su nombre de administrador
     * @param pU caja de texto donde el usuario ingresa su contraseña de administrador
     * @return retorna true si la información coincide con los de la base de datos o false si no existen dichos datos
     */
    @Override
    public boolean validar(JTextField nU, JTextField pU) {
        String nombreUsuario = nU.getText();
        String passUsuario = pU.getText();

        if (nU.getText().isEmpty() || pU.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Introduzca su usuario de administrador y contraseña");
            nU.requestFocus();
            return false;

        } else {

            Admin ad = new Admin();
            LeerEscribirBD le = new LeerEscribirBD();

            ad = le.leerAdmin(nombreUsuario, passUsuario);

            if (ad.getNombre() != null && ad.getPass() != null) {

                return true;

            } else {

                JOptionPane.showMessageDialog(null, "Administrador no encontrado");
                nU.requestFocus();
                return false;
            }
        }
    }
}
