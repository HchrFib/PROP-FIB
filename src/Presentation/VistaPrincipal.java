package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


/** @file VistaPrincipal.java
 @brief Implementación de la vista que muestra la pantalla inicial.
 */

/** @class VistaPrincipal

 Esta vista representa la pantalla inicial y la más importante. Desde ella podemos
  ejecutar todas las demás funcionalidades. Está estructurada de forma que tiene una
  barra de menú y al centro de la pantalla tiene los botones relacionados con las funcionalidades.
 */
public class VistaPrincipal {
    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista = new JFrame("Vista Principal");
    private static final Color GP_COLOR = new Color(0, 0, 0, 150);

    // --------- BUTTONS --------
    private JButton insertRatingButton = new JButton("Insertar Nuevo Rating");
    private JButton getRecommButton = new JButton("Obtener Recomendaciones");
    private JButton definirTipoNuevo = new JButton("Definir Tipo Item Nuevo");
    private JButton gestionUsuarios = new JButton("Gestionar Usuarios");
    private JButton gestionItems = new JButton("Gestionar Items");
    private JButton valorarCalidad = new JButton("Valorar Recomendaciones");

    // --------- PANEL ------------
    private JPanel mainPanel = new JPanel();
    private JPanel buttonsPanel = new JPanel();
    private JPanel labelPanel = new JPanel();
    private JLabel buttonsLabel = new JLabel("¿Qué desea hacer?");
    private final JPanel glassPanel = new JPanel() {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(GP_COLOR);
            g.fillRect(0, 0, getWidth(), getHeight());
        };
    };

    // --------- MENU BAR -------------
    private JMenuBar menuBar = new JMenuBar();
    private JMenu datasetMenu = new JMenu("Archivo");
    private JMenu helpMenu = new JMenu("Help");
    private JMenuItem cargarDataset = new JMenuItem("Cargar Dataset");
    private JMenuItem exit = new JMenuItem("Salir");


    /** @brief Constructora por defecto.

     */
    public VistaPrincipal() {
    }

    /** @brief Creadora con paso de parámetros.

    Crea la instancia de la clase Vista Principal.
    \pre <em>Cierto</em>
    \post Crea la instancia, la asocia con el controlador Presentacion
     y inicializa los componentes de la vista asi como sus "action listeners".
     */
    public VistaPrincipal(CtrlPresentacio ctrlPresentacio) {
        ctrl_presentacio = ctrlPresentacio;
        setComponents();
        setActionsListeners();
    }

    /** @brief Configura los componentes de la vista

    \pre <em>Cierto</em>
    \post Inicializa el frame principal y configura la barra de menú así como los botones principales.
      Se define la estructura de la vista así como su estética.
     */
    private void setComponents() {
        // FRAME OPTIONS
        JFrame.setDefaultLookAndFeelDecorated(true);
        frameVista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frameVista.setMinimumSize(new Dimension(800,500));
        frameVista.setLayout(new BorderLayout());
        frameVista.setGlassPane(glassPanel);
        frameVista.setLocationRelativeTo(null);

        // BUTTONS
        insertRatingButton.setFocusable(false);
        insertRatingButton.setFont(new Font("Roboto",Font.PLAIN,14));

        getRecommButton.setFocusable(false);
        getRecommButton.setFont(new Font("Roboto",Font.PLAIN,14));

        valorarCalidad.setFocusable(false);
        valorarCalidad.setFont(new Font("Roboto",Font.PLAIN,14));

        definirTipoNuevo.setFocusable(false);
        definirTipoNuevo.setFont(new Font("Roboto",Font.PLAIN,14));

        gestionUsuarios.setFocusable(false);
        gestionUsuarios.setFont(new Font("Roboto",Font.PLAIN,14));

        gestionItems.setFocusable(false);
        gestionItems.setFont(new Font("Roboto",Font.PLAIN,14));

        // LABEL OPTIONS
        //buttonsLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buttonsLabel.setForeground(Color.WHITE);
        buttonsLabel.setSize(100,20);
        buttonsLabel.setHorizontalAlignment(JLabel.CENTER);
        buttonsLabel.setFont(new Font("Roboto",Font.PLAIN,16));

        labelPanel.setBackground(Color.darkGray);
        labelPanel.add(buttonsLabel);

        // MAIN PANEL OPTIONS
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(mainPanel);
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(labelPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel,BorderLayout.CENTER);
        mainPanel.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.EAST);
        mainPanel.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.WEST);
        JPanel south = new JPanel();
        south.setPreferredSize(new Dimension(0, 30));
        south.setBackground(Color.darkGray);
        mainPanel.add(south, BorderLayout.SOUTH);
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        // BUTTONS PANEL
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setBackground(Color.lightGray);
        buttonsPanel.add(insertRatingButton);
        buttonsPanel.add(getRecommButton);
        buttonsPanel.add(valorarCalidad);
        buttonsPanel.add(definirTipoNuevo);
        buttonsPanel.add(gestionUsuarios);
        buttonsPanel.add(gestionItems);

        // MENU BAR OPTIONS
        datasetMenu.setMnemonic(KeyEvent.VK_A); // Alt + F
        datasetMenu.setFont(new Font("Roboto",Font.PLAIN,14));
        helpMenu.setMnemonic(KeyEvent.VK_H); // Alt + H
        helpMenu.setFont(new Font("Roboto",Font.PLAIN,14));
        cargarDataset.setMnemonic(KeyEvent.VK_C);
        cargarDataset.setFont(new Font("Roboto",Font.PLAIN,14));
        exit.setMnemonic(KeyEvent.VK_S);
        exit.setFont(new Font("Roboto",Font.PLAIN,14));

        datasetMenu.add(cargarDataset);
        datasetMenu.add(exit);

        menuBar.add(datasetMenu);
        menuBar.add(helpMenu);

        frameVista.setJMenuBar(menuBar);

        // GLASS PANEL
        glassPanel.setOpaque(false);
    }

    /** @brief Configura los "action listeners de los distintos componentes"

    \pre <em>Cierto</em>
    \post Permite que el usuario pueda interactuar con el sistema a través de los distintos buttons
     así como de la barra de menú.
     */
    private void setActionsListeners() {
        insertRatingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("Insert pressed");
                if (ctrl_presentacio.dataSetCargado()) {
                    ctrl_presentacio.insertarRating();
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede insertar una nueva valoracion porque no se ha cargado ningun dataset.\n" +
                            "Cargue un dataset apretando [File], [Load DataSet]\n y seleccionando, posteriormente," +
                            " una carpeta concreta", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        gestionUsuarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("Insert pressed");
                if (ctrl_presentacio.dataSetCargado()) {
                    ctrl_presentacio.gestionarUsuarios();
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede gestionar los usuarios porque no se ha cargado ningun dataset.\n" +
                            "Cargue un dataset apretando [File], [Load DataSet]\n y seleccionando, posteriormente," +
                            " una carpeta concreta", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        gestionItems.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("Insert pressed");
                if (ctrl_presentacio.dataSetCargado()) {
                    ctrl_presentacio.gestionarItems();
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede gestionar los items porque no se ha cargado ningun dataset.\n" +
                            "Cargue un dataset apretando [File], [Load DataSet]\n y seleccionando, posteriormente," +
                            " una carpeta concreta", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        definirTipoNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("Tipo Nuevo pressed");
                ctrl_presentacio.definirTipoNuevo();
            }
        });

        cargarDataset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setCurrentDirectory(new File("./archivos/"));
                int response = fileChooser.showOpenDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    String directorio = fileChooser.getName(file);

                    if (!ctrl_presentacio.datasetCompleto(directorio)) {
                        System.out.println(directorio);
                        JOptionPane.showMessageDialog(null, "No se puede cargar la carpeta (con sus datasets) " +
                                "porque no tiene todos los datasets necesarios\n", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    else if (!ctrl_presentacio.carpetaContieneTipos(directorio)) {
                        JOptionPane.showMessageDialog(null, "No se puede cargar la carpeta (con sus datasets) porque no se ha definido el tipo de item que contiene\n" +
                                "Defina primero el tipo de item del nuevo dataset apretando [Definir Tipo Item Nuevo] para poder cargar sus datasets\n", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    else if (ctrl_presentacio.datasetModificado()) {
                        String[] responses = {"Guardar", "No Guardar"};
                        int answer = JOptionPane.showOptionDialog(null,
                                "El dataset con el que estaba trabajando, habia sido modificado. \n" +
                                "¿Quiere guardar esos cambios en una carpeta de copia?",
                                "Warning",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                responses,
                                0);

                        if (answer == 0) {
                            String nombre_directorio = ctrl_presentacio.guardarDataset();
                            JOptionPane.showMessageDialog(null, "Se ha guardado los datasets modificados " +
                                    "en una carpete de nombre: " + nombre_directorio, "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
                        }
                        ctrl_presentacio.cargarDataset(directorio);
                        System.out.println(directorio + " loaded");
                    }
                    else {
                        ctrl_presentacio.cargarDataset(directorio);
                    }

                }

            }
        });

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("Exit");
                System.exit(0);
            }
        });

        getRecommButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("get Recommendation pressed");
                if (ctrl_presentacio.dataSetCargado()) {
                    ctrl_presentacio.obtenerRecomendacion();
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede pedir ninguna recomendacion porque no se ha cargado ningun dataset.\n" +
                            "Cargue un dataset apretando [File], [Load DataSet]\n y seleccionando, posteriormente," +
                            " una carpeta concreta", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        valorarCalidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("Evaluate pressed");
                if (ctrl_presentacio.dataSetCargado()) {
                    ctrl_presentacio.valorarCalidad();
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede valorar la calidad de ninguna recomendacion porque no se ha cargado ningun dataset.\n" +
                            "Cargue un dataset apretando [File], [Load DataSet]\n y seleccionando, posteriormente," +
                            " una carpeta concreta", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        frameVista.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (ctrl_presentacio.datasetModificado()) {
                    String[] responses = {"Guardar", "No Guardar", "Cancelar"};
                    int answer = JOptionPane.showOptionDialog(null,
                            "El dataset con el que estaba trabajando, habia sido modificado. \n" +
                                    "¿Quiere guardar esos cambios en una carpeta de copia, antes de cerrar el programa?",
                            "Warning",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            responses,
                            0);

                    if (answer == 0) { // guardar y exit
                        String nombre_directorio = ctrl_presentacio.guardarDataset();
                        JOptionPane.showMessageDialog(null, "Se ha guardado los datasets modificados " +
                                "en una carpete de nombre: " + nombre_directorio, "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    }
                    else if (answer == 1) { // no guardar y exit
                        System.exit(0);
                    }
                }
                else {
                    System.exit(0);
                }
            }
        });
    }

    /** @brief Muestra la vista

    \pre <em>Cierto</em>
    \post Muestra la vista por pantalla.
     */
    public void hacerVisible() {
        frameVista.setVisible(true);
    }

    /** @brief Esconde la vista

    \pre <em>Cierto</em>
    \post Hace que la vista desaparezca de la pantalla.
     */
    public void esconder() {
        frameVista.setVisible(false);
    }

    /** @brief Habilita la vista

    \pre <em>Cierto</em>
    \post Permite la interacción del usuario con la vista.
     */
    public void enable() {
        frameVista.setEnabled(true);
        glassPanel.setVisible(false);
    }

    /** @brief Deshabilita la vista

    \pre <em>Cierto</em>
    \post Impide la interacción del usuario con la vista.
     */
    public void disable() {
        frameVista.setEnabled(false);
        glassPanel.setVisible(true);
    }
}