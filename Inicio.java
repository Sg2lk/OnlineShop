package SistemaTienda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * Inicio se encarga de mostrar la primera ventana que decidirá si estamos entrando como un usuario o un administrador.
 * Y corresponde con la parte de "Vista" de nuestro MVC
 */

public class Inicio{
    private JFrame ventana;
    private JRadioButton botonUsuario;

    public Inicio(){
        ventanaInicio();
    }

    /**
     * Se encarga de establecer todos los botones y textos necesarios
     */
    public void ventanaInicio(){

        ventana = new JFrame();
        JPanel panel = new JPanel();
        Color f = new Color(236, 236, 218);

        JLabel opcion = new JLabel("Elija una opción de entrada");
        opcion.setBounds(15,10,250,30);
        opcion.setHorizontalAlignment(SwingConstants.CENTER);
        opcion.setFont(new Font("Mongolian Baiti", Font.PLAIN, 19));

        botonUsuario = new JRadioButton("Usuario");
        botonUsuario.setBounds(10,60,90,30);
        botonUsuario.setFont(new Font("Georgia Pro", Font.PLAIN, 15));
        botonUsuario.setBackground(f);

        JRadioButton botonAdmin = new JRadioButton("Admin", true);
        botonAdmin.setBounds(10,100,90,30);
        botonAdmin.setFont(new Font("Georgia Pro", Font.PLAIN, 15));
        botonAdmin.setBackground(f);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(botonUsuario);
        grupo.add(botonAdmin);

        JButton cambio = new JButton("Entrar");
        cambio.setBounds(95,150,100,30);

        panel.setLayout(null);
        panel.setBackground(f);

        panel.add(opcion);
        panel.add(botonUsuario);
        panel.add(botonAdmin);
        panel.add(cambio);

        ventana.setSize(300, 250);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);

        cambio.addActionListener(entrar);

        ventana.add(panel);

    }

    ActionListener entrar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(botonUsuario.isSelected()){
                ventana.dispose();
                new TiendaInicio();
            }else{
                ventana.dispose();
                new TiendaInicioAdmin();
            }
        }
    };
}
