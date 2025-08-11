package SistemaTienda;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Esta clase se encarga de crear la ventana para crear una cuenta de usuario con sus respectivos botones, cajas de texto...
 * y que corresponde la parte de "Vista" de nuestro patrón de diseño MVC
 */

public class TiendaCuenta{

    private JFrame ventanaCuenta;
    private JTextField nombreUsuarioCrear;
    private JTextField password2;
    private JTextField nombrePersona;
    private JTextField apellidosUsuario;
    private JTextField email;
    private JTextField telefono;

    public TiendaCuenta(){
        VentanaCreacionCuenta();
    }

    public void VentanaCreacionCuenta(){

        ventanaCuenta = new JFrame();
        JPanel panelSesion = new JPanel();
        nombreUsuarioCrear = new JTextField();
        apellidosUsuario = new JTextField();
        email = new JTextField();
        telefono = new JTextField();
        nombrePersona = new JTextField();
        password2 = new JTextField();
        JLabel fondoCuenta = new JLabel();

        JButton guardar = new JButton();

        panelSesion.setBackground(Color.WHITE);
        panelSesion.setLayout(null);

        nombreUsuarioCrear.setBounds(250, 50, 200, 28);
        password2.setBounds(250, 100, 200, 28);
        nombrePersona.setBounds(250, 150, 200, 28);
        apellidosUsuario.setBounds(250, 200, 200, 28);
        email.setBounds(250, 250, 200, 28);
        telefono.setBounds(250, 300, 200, 28);

        guardar.setText("Guardar");
        guardar.setBounds(310, 370, 90, 30);

        JLabel usuario = new JLabel();
        usuario.setText("Usuario");
        usuario.setBounds(114, 50, 150, 30);
        usuario.setHorizontalAlignment(SwingConstants.CENTER);
        usuario.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        JLabel password = new JLabel();
        password.setText("Contraseña");
        password.setBounds(100, 100, 150, 30);
        password.setHorizontalAlignment(SwingConstants.CENTER);
        password.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        JLabel nombre = new JLabel();
        nombre.setText("Nombre");
        nombre.setBounds(114, 150, 150, 30);
        nombre.setHorizontalAlignment(SwingConstants.CENTER);
        nombre.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        JLabel apellidos = new JLabel();
        apellidos.setText("Apellidos");
        apellidos.setBounds(110, 200, 150, 30);
        apellidos.setHorizontalAlignment(SwingConstants.CENTER);
        apellidos.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        JLabel email_usuario = new JLabel();
        email_usuario.setText("Email");
        email_usuario.setBounds(125, 250, 150, 30);
        email_usuario.setHorizontalAlignment(SwingConstants.CENTER);
        email_usuario.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        JLabel telefono_usuario = new JLabel();
        telefono_usuario.setText("Telefono");
        telefono_usuario.setBounds(115, 300, 150, 30);
        telefono_usuario.setHorizontalAlignment(SwingConstants.CENTER);
        telefono_usuario.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        ImageIcon logo = new ImageIcon("fondo1.jpg");
        fondoCuenta.setBounds(0, 0, 500, 500);
        fondoCuenta.setIcon(new ImageIcon(logo.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH)));

        panelSesion.add(nombreUsuarioCrear);
        panelSesion.add(apellidosUsuario);
        panelSesion.add(email);
        panelSesion.add(telefono);
        panelSesion.add(nombrePersona);
        panelSesion.add(password2);
        panelSesion.add(guardar);
        panelSesion.add(usuario);
        panelSesion.add(password);
        panelSesion.add(nombre);
        panelSesion.add(apellidos);
        panelSesion.add(email_usuario);
        panelSesion.add(telefono_usuario);
        panelSesion.add(fondoCuenta);

        LeerEscribirBD x = new LeerEscribirBD();
        List<Blob> blob = x.leerFondos();

        try{
            byte[] aux = blob.get(4).getBytes(1,(int) blob.get(4).length());

            BufferedImage img;

            try{
                img = ImageIO.read(new ByteArrayInputStream(aux));

                ImageIcon im = new ImageIcon(img);
                JLabel etiquetaImagen = new JLabel();
                etiquetaImagen.setBounds(0, 0, 500, 500);
                etiquetaImagen.setIcon(new ImageIcon(im.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH)));

                panelSesion.add(etiquetaImagen, JLayeredPane.DEFAULT_LAYER);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        ventanaCuenta.setSize(500, 500);
        ventanaCuenta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaCuenta.setLocationRelativeTo(null);
        ventanaCuenta.setResizable(false);
        ventanaCuenta.setVisible(true);
        ventanaCuenta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        guardar.addActionListener(guardarCuenta);

        ventanaCuenta.add(panelSesion);
    }

    ActionListener guardarCuenta = new ActionListener() {
        public void actionPerformed(ActionEvent g) {
            try {
                Cliente c = new Cliente();
                if(c.crearUsuario(nombreUsuarioCrear, password2, nombrePersona, apellidosUsuario, email, telefono)){
                    ventanaCuenta.dispose();
                    new TiendaInicio();
                }
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,"Debe introducir datos válidos");
            }
        }
    };
}