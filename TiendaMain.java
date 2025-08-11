package SistemaTienda;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Esta clase genera la ventana principal de la tienda donde se produce la visibilización de las prendas correspondientes, el carrito del usuario
 * y las opciones exclusivas de administrador. Y corresponde a la parte de "Vista" de nuestro MVC
 */

public class TiendaMain extends JFrame {
    private int font = 14;
    Color c = new Color(236,236,218);
    private int p = 350;
    private int q = 350;
    private int ancho = 150;
    private int alto = 150;
    private int coordY = 255;
    private JButton cesta;
    private final JLayeredPane mainTienda = new JLayeredPane();
    private FileInputStream fis;
    private int bytes;
    private JButton hombre;
    private JButton mujer;
    private JButton camisetas;
    private JButton pantalones;
    private JButton zapatos;

    public TiendaMain(boolean x) {
        tienda(x);
    }
    /**
     * Este método genera la ventana principal de la tienda con los botones, imágenes... y según reciba un true o false añadirá opciones exclusivas de administrador
     * o solo dejará las opciones básicas de cualquier usuario
     * @param x recibe true(administrador)/false(usuario)
     */
    public void tienda(boolean x) {

        botones();
        fondo();

        JLabel Logo = new JLabel();

        Logo.setText("StarShop");
        Logo.setBounds(630, 70, 300, 60);
        Logo.setHorizontalAlignment(SwingConstants.CENTER);
        Logo.setFont(new Font("Georgia Pro", Font.PLAIN, 52));

        mainTienda.add(Logo, JLayeredPane.PALETTE_LAYER);

        //-----------------------------------------------------------------------

            /*Ropa ropa = new Ropa(); // Utilizar este mostrar() para la ropa en oferta

            String[] aux = ropa.mostrar();

            int p = 350;
            int q =350;
            int ancho = 150;
            int alto = 150;

            for(String ruta: aux){
                ImageIcon icono = new ImageIcon(ruta);
                JLabel etiquetaImagen = new JLabel(icono);
                etiquetaImagen.setBounds(p,q,ancho,alto);
                etiquetaImagen.setIcon(new ImageIcon(icono.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));

                p+=200;

                mainTienda.add(etiquetaImagen, JLayeredPane.DEFAULT_LAYER);
            }*/

        //----------------------------------------------------------------------------

        if (x) {
            JButton anyadir = new JButton("añadir prenda");
            JButton eliminar = new JButton("eliminar prenda");

            anyadir.setBounds(10, 215, 130, 30);
            anyadir.setBackground(c);
            eliminar.setBounds(165, 215, 130, 30);
            eliminar.setBackground(c);

            anyadir.addActionListener(aniadir);
            eliminar.addActionListener(borrar);

            mainTienda.add(anyadir, JLayeredPane.PALETTE_LAYER);
            mainTienda.add(eliminar, JLayeredPane.PALETTE_LAYER);
        }

        setVisible(true);
        setSize(1500, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Starshop");

        add(mainTienda);
    }

    /**
     * Método encargado de establecer la imagen de fondo de la tienda
     */
    private void fondo() {

        LeerEscribirBD x = new LeerEscribirBD();
        List<Blob> blob = x.leerFondos();

        try{
            byte[] aux = blob.get(0).getBytes(1,(int) blob.get(0).length());

            BufferedImage img;

            try{
                img = ImageIO.read(new ByteArrayInputStream(aux));

                ImageIcon logo = new ImageIcon(img);
                JLabel etiquetaImagen = new JLabel();
                etiquetaImagen.setBounds(0, 0, 1500, 1000);
                etiquetaImagen.setIcon(new ImageIcon(logo.getImage().getScaledInstance(1500, 1000, Image.SCALE_SMOOTH)));

                mainTienda.add(etiquetaImagen, JLayeredPane.DEFAULT_LAYER);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método que se encarga de establecer los botones en sus correspondientes lugares
     */
    private void botones() {
        JButton categoria = new JButton("Categorias");
        JButton carrito = new JButton("carrito");

        carrito.setBounds(1390,215,90,30);
        carrito.setBackground(c);

        categoria.setBounds(0, 255, 310, 30);
        categoria.setBackground(Color.gray);

        carrito.addActionListener(e -> new Carrito());

        categoria.addActionListener(descubrirCategorias);

        mainTienda.add(carrito, JLayeredPane.PALETTE_LAYER);
        mainTienda.add(categoria, JLayeredPane.PALETTE_LAYER);

    }

    /**
     * Evento que crea una ventana para añadir una prenda con todos sus atributos
     */
    ActionListener aniadir = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            JFrame ventanaImagen = new JFrame();
            JPanel panel = new JPanel();

            panel.setLayout(null);

            int x = 180;
            int y = 30;
            int w = 140;
            int h = 25;
            int x2 = 10;

            JLabel g = new JLabel("Género");
            g.setBounds(x2,y*2,100,h);
            g.setFont(new Font("Georgia Pro", Font.PLAIN, font));

            JLabel t = new JLabel("Tipo de prenda");
            t.setBounds(x2,y*3,150,h);
            t.setFont(new Font("Georgia Pro", Font.PLAIN, font));

            JLabel p = new JLabel("Precio (Ej: 9.99)");
            p.setBounds(x2,y*4,150,h);
            p.setFont(new Font("Georgia Pro", Font.PLAIN, font));

            JLabel n = new JLabel("Nombre de la prenda");
            n.setBounds(x2,y*5,160,h);
            n.setFont(new Font("Georgia Pro", Font.PLAIN, font));

            JLabel d = new JLabel("Descripción");
            d.setBounds(x2,y*6,100,h);
            d.setFont(new Font("Georgia Pro", Font.PLAIN, font));

            JLabel s = new JLabel("Stock");
            s.setBounds(x2,y*7,100,h);
            s.setFont(new Font("Georgia Pro", Font.PLAIN, font));

            JLabel imagen = new JLabel("Inserta la imagen");
            imagen.setBounds(x2, y, w, h);
            imagen.setFont(new Font("Georgia Pro", Font.PLAIN, font));

            JTextField genero = new JTextField();
            genero.setBounds(x, y*2, w, h);

            JTextField tipo = new JTextField();
            tipo.setBounds(x, y*3, w, h);

            JTextField precio = new JTextField();
            precio.setBounds(x, y*4, w, h);

            JTextField nombre = new JTextField();
            nombre.setBounds(x, y*5, w, h);

            JTextField descripcion = new JTextField();
            descripcion.setBounds(x, y*6, w, h);

            JTextField stock = new JTextField();
            stock.setBounds(x,y*7,w,h);

            JButton guardar = new JButton("Guardar");
            guardar.setBounds(100,y*9,w,30);

            LeerEscribirBD im = new LeerEscribirBD();
            List<Blob> blob = im.leerFondos();

            try{
                byte[] aux = blob.get(2).getBytes(1,(int) blob.get(2).length());

                BufferedImage img;

                try{
                    img = ImageIO.read(new ByteArrayInputStream(aux));

                    ImageIcon logo = new ImageIcon(img);
                    JLabel carpeta = new JLabel();
                    carpeta.setBounds(240, y, 20, 20);
                    carpeta.setIcon(new ImageIcon(logo.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

                    panel.add(carpeta, JLayeredPane.PALETTE_LAYER);

                    carpeta.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JFileChooser se = new JFileChooser();
                            se.setFileSelectionMode(JFileChooser.FILES_ONLY);
                            int estado = se.showOpenDialog(null);

                            if(estado == JFileChooser.APPROVE_OPTION){

                                try {
                                    fis = new FileInputStream(se.getSelectedFile());
                                    bytes = (int) se.getSelectedFile().length();

                                } catch (FileNotFoundException ex) {
                                    System.out.println("Error al encontrar la imagen");
                                }
                            }

                        }
                    });

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            guardar.addActionListener(e1 -> {

                Ropa x1 = new Ropa();
                boolean comprobar;
                comprobar = x1.guardarImagen(fis,genero.getText(),tipo.getText(),precio.getText(),nombre.getText(),descripcion.getText(),stock.getText());

                if(comprobar){
                    ventanaImagen.dispose();
                }

            });

            panel.add(g);
            panel.add(t);
            panel.add(p);
            panel.add(n);
            panel.add(d);
            panel.add(s);
            panel.add(imagen);
            panel.add(genero);
            panel.add(tipo);
            panel.add(precio);
            panel.add(nombre);
            panel.add(descripcion);
            panel.add(stock);
            panel.add(guardar);

            try{
                byte[] aux = blob.get(4).getBytes(1,(int) blob.get(4).length());

                BufferedImage img;

                try{
                    img = ImageIO.read(new ByteArrayInputStream(aux));

                    ImageIcon logo = new ImageIcon(img);
                    JLabel fondoAnyadir = new JLabel();
                    fondoAnyadir.setBounds(0, 0, 350, 350);
                    fondoAnyadir.setIcon(new ImageIcon(logo.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH)));

                    panel.add(fondoAnyadir, JLayeredPane.DEFAULT_LAYER);

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            ventanaImagen.setVisible(true);
            ventanaImagen.setSize(350, 350);
            ventanaImagen.setLocationRelativeTo(null);
            ventanaImagen.setResizable(false);

            ventanaImagen.add(panel);

        }
    };
    /**
     * Evento que crea una ventana para eliminar un artículo por su nombre
     */
    ActionListener borrar = e -> {

        JFrame ventanaBorrar = new JFrame();
        JPanel miPanel = new JPanel();

        miPanel.setLayout(null);

        JTextField genero = new JTextField();
        genero.setBounds(10,40,265,25);

        JLabel nombre = new JLabel("Nombre del producto a eliminar:");
        nombre.setBounds(10,10,300,25);
        nombre.setFont(new Font("Georgia Pro", Font.PLAIN, font));

        JButton delete = new JButton("Eliminar");
        delete.setBounds(100,74,100,30);

        delete.addActionListener(e1 -> {
            Ropa x = new Ropa();
            boolean comp;
            comp = x.borrarRopa(genero.getText());
            if(comp){
                ventanaBorrar.dispose();
            }
        });

        miPanel.add(delete);
        miPanel.add(nombre);
        miPanel.add(genero);

        LeerEscribirBD im = new LeerEscribirBD();
        List<Blob> blob = im.leerFondos();

        try{
            byte[] aux = blob.get(4).getBytes(1,(int) blob.get(4).length());

            BufferedImage img;

            try{
                img = ImageIO.read(new ByteArrayInputStream(aux));

                ImageIcon logo = new ImageIcon(img);
                JLabel fondoAnyadir = new JLabel();
                fondoAnyadir.setBounds(0, 0, 350, 300);
                fondoAnyadir.setIcon(new ImageIcon(logo.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)));

                miPanel.add(fondoAnyadir, JLayeredPane.DEFAULT_LAYER);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        ventanaBorrar.setVisible(true);
        ventanaBorrar.setSize(300,150);
        ventanaBorrar.setLocationRelativeTo(null);
        ventanaBorrar.setResizable(false);

        ventanaBorrar.add(miPanel);
    };
    /**
     * Evento creado para colocar los botones de "hombre" y "mujer" y limpiar los demás botones e imágenes extra que se han ido añadiendo
     */
    ActionListener descubrirCategorias = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            eliminarBotonesAdicionales();
            clean();

            hombre = new JButton("hombre");
            mujer = new JButton("mujer");

            hombre.setBounds(0, coordY + 30, 310, 30);
            mujer.setBounds(0, coordY + 60, 310, 30);

            hombre.addActionListener(descubrirHombre);
            mujer.addActionListener(descubrirMujer);

            mainTienda.add(hombre, JLayeredPane.PALETTE_LAYER);
            mainTienda.add(mujer, JLayeredPane.PALETTE_LAYER);
            mainTienda.revalidate();
            mainTienda.repaint();
        }
    };
    /**
     * Evento creado para colocar los botones "hombre" con "camiseta", "pantalones" y "zapatos" y limpiar los demás botones e imágenes extra que se han ido añadiendo
     */
    ActionListener descubrirHombre = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            eliminarHombre();

            camisetas = new JButton("camisetas");
            pantalones = new JButton("pantalones");
            zapatos = new JButton("zapatos");

            camisetas.setBounds(0, coordY + 60, 310, 30);
            pantalones.setBounds(0, coordY + 90, 310, 30);
            zapatos.setBounds(0, coordY + 120, 310, 30);

            camisetas.addActionListener(camisetaHombre);
            pantalones.addActionListener(pantalonHombre);
            zapatos.addActionListener(zapatosHombre);

            mainTienda.add(camisetas, JLayeredPane.PALETTE_LAYER);
            mainTienda.add(pantalones, JLayeredPane.PALETTE_LAYER);
            mainTienda.add(zapatos, JLayeredPane.PALETTE_LAYER);
            mainTienda.revalidate();
            mainTienda.repaint();
        }
    };
    /**
     * Evento que muestra en pantalla la prenda seleccionada con sus respectivos atributos y elimina las imágenes o botones extra
     */
    ActionListener camisetaHombre = e -> {

        clean();

        Ropa ropa = new Ropa();
        List<List<String>> vec = ropa.infoRopa();
        List<Blob> blob = ropa.infoImagenes();

        int x = 350;

        for(int i=0;i<vec.size();i++){

            if(vec.get(i).get(0).equals("hombre") && vec.get(i).get(1).equals("camiseta")){

                try{
                    byte[] aux = blob.get(i).getBytes(1,(int) blob.get(i).length());

                    BufferedImage img;

                    try{
                        img = ImageIO.read(new ByteArrayInputStream(aux));

                        ImageIcon logo = new ImageIcon(img);
                        JLabel etiquetaImagen = new JLabel();
                        etiquetaImagen.setName("borrar");
                        etiquetaImagen.setBounds(x, q, ancho, alto);
                        x+=200;
                        etiquetaImagen.setIcon(new ImageIcon(logo.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH)));

                        int indice = i;
                        etiquetaImagen.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                eliminarOtrasEtiquetas(blob, vec, indice);
                            }
                        });

                        mainTienda.add(etiquetaImagen, JLayeredPane.PALETTE_LAYER);

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        }

        mainTienda.revalidate();
        mainTienda.repaint();
    };
    /**
     * Evento que muestra en pantalla la prenda seleccionada con sus respectivos atributos y elimina las imágenes o botones extra
     */
    ActionListener pantalonHombre = e -> {

        clean();

        Ropa ropa = new Ropa();
        List<List<String>> vec = ropa.infoRopa();
        List<Blob> blob = ropa.infoImagenes();

        int x = 350;

        for(int i=0;i<vec.size();i++){

            if(vec.get(i).get(0).equals("hombre") && vec.get(i).get(1).equals("pantalon")){

                try{
                    byte[] aux = blob.get(i).getBytes(1,(int) blob.get(i).length());

                    BufferedImage img;

                    try{
                        img = ImageIO.read(new ByteArrayInputStream(aux));

                        ImageIcon logo = new ImageIcon(img);
                        JLabel etiquetaImagen = new JLabel();
                        etiquetaImagen.setName("borrar");
                        etiquetaImagen.setBounds(x, q, ancho, alto);
                        x+=200;
                        etiquetaImagen.setIcon(new ImageIcon(logo.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH)));

                        int indice = i;
                        etiquetaImagen.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                eliminarOtrasEtiquetas(blob, vec, indice);
                            }
                        });

                        mainTienda.add(etiquetaImagen, JLayeredPane.PALETTE_LAYER);

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        }

        mainTienda.revalidate();
        mainTienda.repaint();

    };
    /**
     * Evento que muestra en pantalla la prenda seleccionada con sus respectivos atributos y elimina las imágenes o botones extra
     */
    ActionListener zapatosHombre = e -> {

        clean();

        Ropa ropa = new Ropa();
        List<List<String>> vec = ropa.infoRopa();
        List<Blob> blob = ropa.infoImagenes();

        int x = 350;

        for(int i=0;i<vec.size();i++){

            if(vec.get(i).get(0).equals("hombre") && vec.get(i).get(1).equals("zapatos")){

                try{
                    byte[] aux = blob.get(i).getBytes(1,(int) blob.get(i).length());

                    BufferedImage img;

                    try{
                        img = ImageIO.read(new ByteArrayInputStream(aux));

                        ImageIcon logo = new ImageIcon(img);
                        JLabel etiquetaImagen = new JLabel();
                        etiquetaImagen.setName("borrar");
                        etiquetaImagen.setBounds(x, q, ancho, alto);
                        x+=200;
                        etiquetaImagen.setIcon(new ImageIcon(logo.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH)));

                        int indice = i;
                        etiquetaImagen.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                eliminarOtrasEtiquetas(blob, vec, indice);
                            }
                        });

                        mainTienda.add(etiquetaImagen, JLayeredPane.PALETTE_LAYER);

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        }

        mainTienda.revalidate();
        mainTienda.repaint();

    };
    /**
     * Evento creado para colocar los botones "mujer" con "camiseta", "pantalones" y "zapatos" y limpiar los demás botones e imágenes extra que se han ido añadiendo
     */
    ActionListener descubrirMujer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            eliminarMujer();

            mujer.setBounds(0, coordY + 30, 310, 30);

            camisetas = new JButton("camisetas");
            pantalones = new JButton("pantalones");
            zapatos = new JButton("zapatos");

            camisetas.setBounds(0, coordY + 60, 310, 30);
            pantalones.setBounds(0, coordY + 90, 310, 30);
            zapatos.setBounds(0, coordY + 120, 310, 30);

            camisetas.addActionListener(camisetaMujer);
            pantalones.addActionListener(pantalonMujer);
            zapatos.addActionListener(zapatosMujer);

            mainTienda.add(camisetas, JLayeredPane.PALETTE_LAYER);
            mainTienda.add(pantalones, JLayeredPane.PALETTE_LAYER);
            mainTienda.add(zapatos, JLayeredPane.PALETTE_LAYER);
            mainTienda.revalidate();
            mainTienda.repaint();
        }
    };
    /**
     * Evento que muestra en pantalla la prenda seleccionada con sus respectivos atributos y elimina las imágenes o botones extra
     */
    ActionListener camisetaMujer = e -> {

        clean();

        Ropa ropa = new Ropa();
        List<List<String>> vec = ropa.infoRopa();
        List<Blob> blob = ropa.infoImagenes();

        int x = 350;

        for(int i=0;i<vec.size();i++){

            if(vec.get(i).get(0).equals("mujer") && vec.get(i).get(1).equals("camiseta")){

                try{
                    byte[] aux = blob.get(i).getBytes(1,(int) blob.get(i).length());

                    BufferedImage img;

                    try{
                        img = ImageIO.read(new ByteArrayInputStream(aux));

                        ImageIcon logo = new ImageIcon(img);
                        JLabel etiquetaImagen = new JLabel();
                        etiquetaImagen.setName("borrar");
                        etiquetaImagen.setBounds(x, q, ancho, alto);
                        x+=200;
                        etiquetaImagen.setIcon(new ImageIcon(logo.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH)));

                        int indice = i;
                        etiquetaImagen.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                eliminarOtrasEtiquetas(blob, vec, indice);
                            }
                        });

                        mainTienda.add(etiquetaImagen, JLayeredPane.PALETTE_LAYER);

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        }

        mainTienda.revalidate();
        mainTienda.repaint();
    };
    /**
     * Evento que muestra en pantalla la prenda seleccionada con sus respectivos atributos y elimina las imágenes o botones extra
     */
    ActionListener pantalonMujer = e -> {

        clean();

        Ropa ropa = new Ropa();
        List<List<String>> vec = ropa.infoRopa();
        List<Blob> blob = ropa.infoImagenes();

        int x = 350;

        for(int i=0;i<vec.size();i++){

            if(vec.get(i).get(0).equals("mujer") && vec.get(i).get(1).equals("pantalon")){

                try{
                    byte[] aux = blob.get(i).getBytes(1,(int) blob.get(i).length());

                    BufferedImage img;

                    try{
                        img = ImageIO.read(new ByteArrayInputStream(aux));

                        ImageIcon logo = new ImageIcon(img);
                        JLabel etiquetaImagen = new JLabel();
                        etiquetaImagen.setName("borrar");
                        etiquetaImagen.setBounds(x, q, ancho, alto);
                        x+=200;
                        etiquetaImagen.setIcon(new ImageIcon(logo.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH)));

                        int indice = i;
                        etiquetaImagen.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                eliminarOtrasEtiquetas(blob, vec, indice);
                            }
                        });

                        mainTienda.add(etiquetaImagen, JLayeredPane.PALETTE_LAYER);

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        }

        mainTienda.revalidate();
        mainTienda.repaint();
    };
    /**
     * Evento que muestra en pantalla la prenda seleccionada con sus respectivos atributos y elimina las imágenes o botones extra
     */
    ActionListener zapatosMujer = e -> {

        clean();

        Ropa ropa = new Ropa();
        List<List<String>> vec = ropa.infoRopa();
        List<Blob> blob = ropa.infoImagenes();

        int x = 350;

        for(int i=0;i<vec.size();i++){

            if(vec.get(i).get(0).equals("mujer") && vec.get(i).get(1).equals("zapatos")){

                try{
                    byte[] aux = blob.get(i).getBytes(1,(int) blob.get(i).length());

                    BufferedImage img;

                    try{
                        img = ImageIO.read(new ByteArrayInputStream(aux));

                        ImageIcon logo = new ImageIcon(img);
                        JLabel etiquetaImagen = new JLabel();
                        etiquetaImagen.setName("borrar");
                        etiquetaImagen.setBounds(x, q, ancho, alto);
                        x+=200;
                        etiquetaImagen.setIcon(new ImageIcon(logo.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH)));

                        int indice = i;
                        etiquetaImagen.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                eliminarOtrasEtiquetas(blob, vec, indice);
                            }
                        });

                        mainTienda.add(etiquetaImagen, JLayeredPane.PALETTE_LAYER);

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        }

        mainTienda.revalidate();
        mainTienda.repaint();
    };
    /**
     * Método que se encarga de eliminar todos los JLabel y JButton que se han ido añadiendo para dejar espacio a los nuevos JLabel, JButton, etc.
     */
    private void clean(){
        Component[] componentes = mainTienda.getComponents();
        for (Component componente : componentes) {
            if (componente instanceof JLabel etiqueta) {

                if (etiqueta.getName() != null && etiqueta.getName().equals("borrar")) {
                    mainTienda.remove(componente);
                }
            }
        }

        if(cesta!=null){
            if(cesta.getName().equals("borrar")){
                mainTienda.remove(cesta);
            }
        }
    }

    /**
     * Método que amplía cada imagen de una prenda para mostrar sus características específicas como su precio, descripción, etc.
     * @param blob lista dinámica de las imágenes leídas de la base de datos
     * @param vec lista dinámica de dos dimensiones que contiene la información respectiva de cada prenda almacenada en la base de datos
     * @param indice posición que adquiere cada prenda que se muestra para poder acceder desde la lista anterior "vec"
     */
    private void eliminarOtrasEtiquetas(List<Blob> blob, List<List<String>> vec, int indice) {

        clean();

        try{
            byte[] aux = blob.get(indice).getBytes(1,(int) blob.get(indice).length());

            BufferedImage img;

            try{
                img = ImageIO.read(new ByteArrayInputStream(aux));

                ImageIcon logo = new ImageIcon(img);
                JLabel etiquetaImagen = new JLabel();
                etiquetaImagen.setName("borrar");
                etiquetaImagen.setBounds(p, q-60, ancho+350, alto+350);
                etiquetaImagen.setIcon(new ImageIcon(logo.getImage().getScaledInstance(ancho+350, alto+350, Image.SCALE_SMOOTH)));

                JLabel precio = new JLabel(vec.get(indice).get(2));
                precio.setName("borrar");
                precio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 30));
                precio.setBounds(p+600,q,200,60);

                JLabel nombre = new JLabel(vec.get(indice).get(3));
                nombre.setName("borrar");
                nombre.setFont(new Font("Mongolian Baiti", Font.PLAIN, 40));
                nombre.setBounds(p+600,q-45,600,60);

                JLabel descripcion = new JLabel(vec.get(indice).get(4));
                descripcion.setName("borrar");
                descripcion.setFont(new Font("Georgia Pro", Font.PLAIN, 15));
                descripcion.setBounds(p+600,q+40,500,100);

                cesta = new JButton("Añadir a la cesta");
                cesta.setName("borrar");
                cesta.setBounds(p+600,q+400,ancho+300,alto-100);

                cesta.addActionListener(e -> {
                    Ropa x = new Ropa();
                    x.guardarCarrito(blob, vec, indice);
                });

                mainTienda.add(cesta, JLayeredPane.PALETTE_LAYER);
                mainTienda.add(precio, JLayeredPane.PALETTE_LAYER);
                mainTienda.add(nombre, JLayeredPane.PALETTE_LAYER);
                mainTienda.add(descripcion, JLayeredPane.PALETTE_LAYER);
                mainTienda.add(etiquetaImagen, JLayeredPane.PALETTE_LAYER);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        mainTienda.revalidate();
        mainTienda.repaint();
    }

    /**
     * Se encarga de que no se solapen los botones de hombre, mujer, camisetas, pantalones y zapatos cuando presionemos el botón "Categorías" y mostrar
     * los botones correspondientes
     */
    private void eliminarBotonesAdicionales() {

        if (hombre != null) {
            mainTienda.remove(hombre);
        }

        if (mujer != null) {
            mainTienda.remove(mujer);
        }

        if (camisetas != null || pantalones != null || zapatos != null) {
            mainTienda.remove(camisetas);
            mainTienda.remove(pantalones);
            mainTienda.remove(zapatos);
        }

    }

    /**
     * Se encarga de que no se solapen los botones de hombre, mujer, camisetas, pantalones y zapatos cuando presionemos el botón "Hombre" y mostrar
     * los botones correspondientes
     */
    private void eliminarHombre() {
        if (mujer != null) {
            mainTienda.remove(mujer);
        }

        if (camisetas != null || pantalones != null || zapatos != null) {
            mainTienda.remove(camisetas);
            mainTienda.remove(pantalones);
            mainTienda.remove(zapatos);
        }
    }

    /**
     * Se encarga de que no se solapen los botones de hombre, mujer, camisetas, pantalones y zapatos cuando presionemos el botón "Mujer" y mostrar
     * los botones correspondientes
     */
    private void eliminarMujer() {
        if (hombre != null) {
            mainTienda.remove(hombre);
        }

        if (camisetas != null || pantalones != null || zapatos != null) {
            mainTienda.remove(camisetas);
            mainTienda.remove(pantalones);
            mainTienda.remove(zapatos);
        }
    }
}
