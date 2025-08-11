package SistemaTienda;

import javax.swing.*;

/**
 * Clase que sirve para crear los atributos de un usuario para posteriormente enviar esa información a la base de datos.
 * Y corresponde con la parte de "Controlador" de nuestro MVC
 */

public class Cliente extends Usuario{
    private String nombre;
    private String pass;
    private String usuario;
    private String apellidos;
    private String email;
    private int telefono = 0;

    public Cliente(){}

    public void setUsuario(String u) {
        usuario = u;
    }

    public void setApellidos(String a) {
        apellidos = a;
    }

    public void setEmail(String e) {
        email = e;
    }

    public void setTelefono(int t) {
        telefono = t;
    }

    String getUsuario() {
        return usuario;
    }

    String getApellidos() {
        return apellidos;
    }

    String getEmail() {
        return email;
    }

    int getTelefono() {
        return telefono;
    }

    public void setNombre(String n) {
        nombre = n;
    }

    public void setPass(String p) {
        pass = p;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPass() {
        return pass;
    }

    /**
     * Método que consiste en comprobar si los datos introducidos por el usuario son correctos y los envía a la base de datos
     * para comprobar si son correctos o no
     * @param nU caja de texto con el nombre del usuario
     * @param pU caja de texto con la contraseña del usuario
     * @return devuelve true si esos datos existen en la base de datos o false si no existen
     */

    @Override
    public boolean validar(JTextField nU, JTextField pU) {

        String nombreUsuario = nU.getText();
        String passUsuario = pU.getText();

        if (nU.getText().isEmpty() || pU.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Introduzca su usuario y contraseña");
            nU.requestFocus();
            return false;

        } else {

            Cliente lg;
            LeerEscribirBD le = new LeerEscribirBD();

            lg = le.log(nombreUsuario, passUsuario);

            if (lg.getNombre() != null && lg.getPass() != null) {

                return true;

            } else {

                JOptionPane.showMessageDialog(null, "Usuario no encontrado");
                nU.requestFocus();
                return false;
            }
        }
    }

    /**
     * Método que se encarga de comprobar si los datos escritos en las cajas de texto son correctos y los envía a la base de datos
     * @param usuario caja de texto con el nombre de usuario del cliente
     * @param password caja de texto con la contraseña del cliente
     * @param nombre caja de texto con el nombre del cliente
     * @param apellidos caja de texto con los apellidos del cliente a del cliente
     * @param email caja de texto con el email del cliente
     * @param telefono caja de texto con el teléfono del cliente
     * @return devuelve true si el usuario si ha creado correctamente en la base de datos o falso si algo no ha salido como se esperaba
     */

    public boolean crearUsuario(JTextField usuario, JTextField password, JTextField nombre, JTextField apellidos, JTextField email, JTextField telefono) {

        Cliente lg = new Cliente();
        LeerEscribirBD le = new LeerEscribirBD();

        if (usuario.getText().isEmpty() || password.getText().isEmpty() || nombre.getText().isEmpty() || apellidos.getText().isEmpty() || email.getText().isEmpty() || telefono.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Campos vacios");
            return false;

        } else {

            lg.setUsuario(usuario.getText());
            lg.setPass(password.getText());
            lg.setNombre(nombre.getText());
            lg.setApellidos(apellidos.getText());
            lg.setEmail(email.getText());
            lg.setTelefono(Integer.parseInt(telefono.getText()));

            le.RegistrarUsuario(lg);
            JOptionPane.showMessageDialog(null, "Cliente Registrado");
            return true;
        }
    }
}
