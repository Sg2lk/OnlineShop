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
 * Esta clase crea la segunda ventana que nos permitirá acceder a la tienda con credenciales de administrador ya existentes en la base de datos.
 * Y corresponde a la parte de "Vista" de nuestro MVC
 */

public class TiendaInicioAdmin {
    private JFrame ventana;
    private JTextField usuario;
    private JTextField password;

    public TiendaInicioAdmin(){
        InitComponents();
    }

    /**
     * Método que crea toda la interfaz de esta ventana, establece los botones en su lugar, las imágenes, etc.
     */

    public void InitComponents(){
        ventana = new JFrame("Starshop");
        JPanel mainPanel = new JPanel();
        JLabel imagenFondo = new JLabel();
        Color f = new Color(236, 236, 218);

        JButton inicioS = new JButton();
        inicioS.setText("acceder");
        inicioS.setBounds(430, 255, 250, 30);
        inicioS.setHorizontalAlignment(SwingConstants.CENTER);
        inicioS.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        JLabel nombre = new JLabel();
        nombre.setText("Admin");
        nombre.setBounds(310, 155, 150, 30);
        nombre.setHorizontalAlignment(SwingConstants.CENTER);
        nombre.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        password = new JTextField("paradise");
        password.setBounds(430, 200, 250, 28);

        JLabel cont = new JLabel();
        cont.setText("Contraseña");
        cont.setBounds(295, 200, 150, 30);
        cont.setHorizontalAlignment(SwingConstants.CENTER);
        cont.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        usuario = new JTextField("alex");
        usuario.setBounds(430, 155, 250, 28);

        JLabel login = new JLabel();
        login.setText("¡Bienvenido!");
        login.setBounds(450, 10, 200, 200);
        login.setHorizontalAlignment(SwingConstants.CENTER);
        login.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        //Color f = new Color(235, 236, 218);

        mainPanel.setLayout(null);
        mainPanel.setBackground(f);

        ImageIcon logo = new ImageIcon("logo2.png");
        imagenFondo.setBounds(0, 50, 350, 300);
        imagenFondo.setIcon(new ImageIcon(logo.getImage().getScaledInstance(350, 300, Image.SCALE_SMOOTH)));

        mainPanel.add(inicioS);
        mainPanel.add(password);
        mainPanel.add(cont);
        mainPanel.add(nombre);
        mainPanel.add(usuario);
        mainPanel.add(login);
        mainPanel.add(imagenFondo);

        LeerEscribirBD x = new LeerEscribirBD();
        List<Blob> blob = x.leerFondos();

        try{
            byte[] aux = blob.get(5).getBytes(1,(int) blob.get(5).length());

            BufferedImage img;

            try{
                img = ImageIO.read(new ByteArrayInputStream(aux));

                ImageIcon im = new ImageIcon(img);
                JLabel miLogo = new JLabel();
                miLogo.setBounds(10, 60, 300, 300);
                miLogo.setIcon(new ImageIcon(im.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)));

                mainPanel.add(miLogo, JLayeredPane.DEFAULT_LAYER);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        ventana.setSize(800, 500);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);

        inicioS.addActionListener(acceder);

        ventana.add(mainPanel);

    }

    /**
     * Evento que llama a la clase "Admin" para validar el nombre introducido en las cajas de texto
     */
    ActionListener acceder = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            Admin a = new Admin();

            if(a.validar(usuario,password)){

                ventana.dispose();
                new TiendaMain(true);
            }
        }
    };
}
