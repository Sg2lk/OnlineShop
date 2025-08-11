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
 * Clase diseñada para mostrar la ventana final donde se recogen los datos del cliente para proceder al pago del producto y actualizar los datos de la base de datos
 * y que corresponde a la parte de "Vista" de nuestro MVC
 */

public class Pagar extends JFrame implements Interfaz{
    int dd, mm ,aa;
    JLayeredPane panel = new JLayeredPane();

    /**
     * Constructor implícito empleado para crear la ventana donde se recogen los datos necesarios del cliente para el pago del producto y enviar esa información
     * a la clase "LeerEscribirBD" donde se manipulará de la manera más conveniente
     */

    public Pagar(){

        int x = 10;
        int y = 10;
        int ancho = 150;
        int alto = 30;

        int textoX = 250;
        int textoY = 10;

        JTextField nombre = new JTextField();
        JLabel texto_nombre = new JLabel("Nombre completo");
        texto_nombre.setFont(new Font("Georgia Pro", Font.PLAIN, 18));
        JTextField email = new JTextField();
        JLabel texto_email = new JLabel("Email");
        texto_email.setFont(new Font("Georgia Pro", Font.PLAIN, 18));
        JTextField telefono = new JTextField();
        JLabel texto_telefono = new JLabel("Teléfono");
        texto_telefono.setFont(new Font("Georgia Pro", Font.PLAIN, 18));

        JTextField dia = new JTextField();
        JTextField mes = new JTextField();
        JTextField anio = new JTextField();
        JLabel texto_nac = new JLabel("Fecha de nacimiento");
        texto_nac.setFont(new Font("Georgia Pro", Font.PLAIN, 18));
        JLabel texto_dia = new JLabel("DD");
        texto_dia.setFont(new Font("Georgia Pro", Font.PLAIN, 16));
        JLabel texto_mes = new JLabel("MM");
        texto_mes.setFont(new Font("Georgia Pro", Font.PLAIN, 16));
        JLabel texto_anio = new JLabel("AA");

        texto_anio.setFont(new Font("Georgia Pro", Font.PLAIN, 16));
        JTextField domicilio = new JTextField();
        JLabel texto_domicilio = new JLabel("Domicilio");
        texto_domicilio.setFont(new Font("Georgia Pro", Font.PLAIN, 18));
        JTextField numeros_tarjeta = new JTextField();
        JLabel texto_tarjeta = new JLabel("Tarjeta");
        texto_tarjeta.setFont(new Font("Georgia Pro", Font.PLAIN, 18));
        JTextField fecha_cad = new JTextField();
        JLabel texto_cad = new JLabel("Fecha de caducidad");
        texto_cad.setFont(new Font("Georgia Pro", Font.PLAIN, 18));
        JTextField cvv = new JTextField();
        JLabel texto_cvv = new JLabel("CVV");
        texto_cvv.setFont(new Font("Georgia Pro", Font.PLAIN, 18));

        JButton finalizar = new JButton("Pagar");

        texto_nombre.setBounds(x,y,ancho,alto);
        texto_email.setBounds(x,y+=40,ancho,alto);
        texto_telefono.setBounds(x,y+=40,ancho,alto);
        texto_nac.setBounds(x,y+=40,ancho+100,alto);
        texto_dia.setBounds(textoX,y,ancho-100,alto);
        texto_mes.setBounds(textoX+50,y,ancho-100,alto);
        texto_anio.setBounds(textoX+100,y,ancho-100,alto);
        texto_domicilio.setBounds(x,y+=40,ancho,alto);
        texto_tarjeta.setBounds(x,y+=40,ancho,alto);
        texto_cad.setBounds(x,y+=40,ancho+100,alto);
        texto_cvv.setBounds(x, y + 40,ancho,alto);

        nombre.setBounds(textoX,textoY,ancho,alto);
        email.setBounds(textoX,textoY+=40,ancho,alto);
        telefono.setBounds(textoX,textoY+=40,ancho,alto);
        dia.setBounds(textoX+30,textoY+=40,ancho-130,alto);
        mes.setBounds(textoX+80,textoY,ancho-130,alto);
        anio.setBounds(textoX+130,textoY,ancho-130,alto);
        domicilio.setBounds(textoX,textoY+=40,ancho,alto);
        numeros_tarjeta.setBounds(textoX,textoY+=40,ancho,alto);
        fecha_cad.setBounds(textoX,textoY+=40,ancho,alto);
        cvv.setBounds(textoX,textoY+=40,ancho,alto);
        finalizar.setBounds(textoX,textoY+60,ancho,alto);

        finalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean comp;
                comp = camposVacios(nombre.getText(),email.getText(),telefono.getText(), dia.getText(),mes.getText(),anio.getText(),domicilio.getText(),numeros_tarjeta.getText(),fecha_cad.getText(),cvv.getText());
                        if(comp){
                            dispose();
                        }
            }
        });

        panel.add(finalizar, JLayeredPane.PALETTE_LAYER);
        panel.add(nombre,JLayeredPane.PALETTE_LAYER);
        panel.add(email,JLayeredPane.PALETTE_LAYER);
        panel.add(telefono, JLayeredPane.PALETTE_LAYER);
        panel.add(dia,JLayeredPane.PALETTE_LAYER);
        panel.add(mes,JLayeredPane.PALETTE_LAYER);
        panel.add(anio,JLayeredPane.PALETTE_LAYER);
        panel.add(domicilio,JLayeredPane.PALETTE_LAYER);
        panel.add(numeros_tarjeta,JLayeredPane.PALETTE_LAYER);
        panel.add(fecha_cad,JLayeredPane.PALETTE_LAYER);
        panel.add(cvv,JLayeredPane.PALETTE_LAYER);

        panel.add(texto_nombre,JLayeredPane.PALETTE_LAYER);
        panel.add(texto_email,JLayeredPane.PALETTE_LAYER);
        panel.add(texto_telefono,JLayeredPane.PALETTE_LAYER);
        panel.add(texto_nac,JLayeredPane.PALETTE_LAYER);
        panel.add(texto_dia,JLayeredPane.PALETTE_LAYER);
        panel.add(texto_mes,JLayeredPane.PALETTE_LAYER);
        panel.add(texto_anio,JLayeredPane.PALETTE_LAYER);
        panel.add(texto_domicilio,JLayeredPane.PALETTE_LAYER);
        panel.add(texto_tarjeta,JLayeredPane.PALETTE_LAYER);
        panel.add(texto_cad,JLayeredPane.PALETTE_LAYER);
        panel.add(texto_cvv,JLayeredPane.PALETTE_LAYER);

        LeerEscribirBD im = new LeerEscribirBD();
        List<Blob> blob = im.leerFondos();

        try{
            byte[] aux = blob.get(4).getBytes(1,(int) blob.get(4).length());

            BufferedImage img;

            try{
                img = ImageIO.read(new ByteArrayInputStream(aux));

                ImageIcon logo = new ImageIcon(img);
                JLabel fondoAnyadir = new JLabel();
                fondoAnyadir.setBounds(0, 0, 500, 450);
                fondoAnyadir.setIcon(new ImageIcon(logo.getImage().getScaledInstance(500, 450, Image.SCALE_SMOOTH)));

                panel.add(fondoAnyadir, JLayeredPane.DEFAULT_LAYER);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        setVisible(true);
        setSize(500,450);
        setLocationRelativeTo(null);
        setResizable(false);

        add(panel);
    }

    /**
     * Método que comprueba si los datos introducidos por el cliente son correctos y los envía a la clase "LeerEscribirBD" para su posterior manipulación
     * @param n cadena con el nombre del cliente
     * @param e cadena con el email del cliente
     * @param t cadena con el teléfono del cliente
     * @param nac_d cadena con el día de nacimiento del cliente
     * @param nac_m cadena con el mes de nacimiento del cliente
     * @param nac_a cadena con el año de nacimiento del cliente
     * @param d cadena con la domiciliación del cliente
     * @param tarjeta cadena con los números de la tarjeta del cliente
     * @param cad cadena con la fecha de caducidad de la tarjeta del cliente
     * @param cvv cadena con el código de seguridad de la tarjeta del cliente
     */

    public boolean camposVacios(String n, String e, String t, String nac_d,String nac_m, String nac_a, String d, String tarjeta, String cad, String cvv){

        boolean comp = false;

        LeerEscribirBD x = new LeerEscribirBD();

        if(!nac_d.isEmpty() || !nac_m.isEmpty() || !nac_a.isEmpty()){
            dd = Integer.parseInt(nac_d);
            mm = Integer.parseInt(nac_m);
            aa = Integer.parseInt(nac_a);
        }

        if(n.isEmpty() || e.isEmpty() || t.isEmpty() || nac_d.isEmpty() || nac_m.isEmpty() || nac_a.isEmpty() || d.isEmpty() || tarjeta.isEmpty() || cad.isEmpty() || cvv.isEmpty()) {

            if(dd > 31 || mm > 12 || aa > 2023){
                JOptionPane.showMessageDialog(null, "Fecha inválida");
            }else{
                JOptionPane.showMessageDialog(null, "Campos vacíos");
            }

        }else {
            comp = x.actualizarCliente(Integer.parseInt(t),nac_d,nac_m,nac_a,d,tarjeta,cad,cvv);
            x.actualizarStock();
            x.cleanCarrito();
        }
        return comp;
    }
}
