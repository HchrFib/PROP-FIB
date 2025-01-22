package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Objects;
import java.util.Vector;

/** @file VistaDefinirTipo.java
 @brief Implementación de la vista que nos permite definir nuevos tipos de ítems.
 */

/** @class VistaDefinirTipo

CLASE IMPLEMENTADA POR JULEN COSTA.

Esta vista permite al usuario definir nuevos tipos de ítems para poder trabajar con datasets diferentes a peliculas o series.
 */
public class VistaDefinirTipo {
    private CtrlPresentacio ctrl_presentacio;

    // FRAME
    private JFrame frameVista = new JFrame("Definir Tipo Item Nuevo");

    // PANELS
    private JPanel mainPanel = new JPanel();
    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel listPanel = new JPanel();

    // LABELS
    private JLabel title = new JLabel("Seleccione, primero, la carpeta de datasets sobre la que quiere definir el nuevo tipo de item.");
    private JLabel title2 = new JLabel("Defina el nuevo tipo de item, añadiendo los atributos uno a uno:");
    private JLabel subtitle = new JLabel("Dataset Seleccionado: ");
    private JLabel datasetSeleccionado = new JLabel("null");

    // BUTTONS
    private JButton submit = new JButton("Guardar y Finalizar");
    private JButton cancel = new JButton("Cancelar Operacion");
    private JButton add = new JButton("Añadir Tipo Atributo");
    private JButton dataset = new JButton("Seleccionar Dataset");

    // LIST AND SCROLL
    private JList listaAtributos = new JList();
    private JScrollPane atributos = new JScrollPane(listaAtributos);
    private Vector<String> contenido = new Vector<String>();

    // TEXT FIELDS
    private JTextField userIdField = new JTextField("user-id");
    private JTextField itemIdField = new JTextField("item-id");
    private JTextField ratingField = new JTextField("rating");

    /** @brief Constructora por defecto. */
    public VistaDefinirTipo() {
    }

    /** @brief Creadora con paso de parámetros.

    Crea la instancia de la clase VistaDefinirTipo.
    \pre <em>Cierto</em>
    \post Crea la instancia, la asocia con el controlador Presentacion
    y inicializa los componentes de la vista asi como sus "action listeners".
     */
    public VistaDefinirTipo(CtrlPresentacio ctrlPresentacio) {
        ctrl_presentacio = ctrlPresentacio;
        setComponents();
        setActionsListeners();
    }

    /** @brief Configura los componentes de la vista

    \pre <em>Cierto</em>
    \post Inicializa el frame principal y configura el scroll panel, así como los buttons.
    Se define la estructura de la vista así como su estética.
     */
    private void setComponents() {
        // FRAME OPTIONS
        frameVista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frameVista.setPreferredSize(new Dimension(700, 350));
        frameVista.setLocationRelativeTo(null);
        frameVista.add(mainPanel);
        frameVista.pack();

        // LABEL OPTIONS
        title.setBackground(Color.darkGray);
        title.setOpaque(true);
        title.setForeground(Color.lightGray);
        title.setHorizontalAlignment(JLabel.CENTER);

        title2.setBackground(Color.darkGray);
        title2.setOpaque(true);
        title2.setForeground(Color.lightGray);
        title2.setHorizontalAlignment(JLabel.CENTER);


        // BUTTONS OPTIONS
        submit.setFocusable(false);
        cancel.setFocusable(false);
        add.setFocusable(false);
        dataset.setFocusable(false);

        // SCROLL PANEL OPTIONS
        listaAtributos.setListData(contenido);
        listaAtributos.setVisibleRowCount(8);

        // PANEL OPTIONS

        // center panel
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setPreferredSize(new Dimension(500, 100));
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        centerPanel.add(dataset, c);

        c.gridx = 1;
        c.gridy = 0;
        centerPanel.add(Box.createRigidArea(new Dimension(25, 0)), c);

        c.gridx = 2;
        c.gridy = 0;
        centerPanel.add(subtitle, c);

        c.gridx = 3;
        c.gridy = 0;
        centerPanel.add(Box.createRigidArea(new Dimension(25, 0)), c);

        c.gridx = 4;
        c.gridy = 0;
        centerPanel.add(datasetSeleccionado, c);

        c.gridx = 0;
        c.gridy = 1;
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)), c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 5;
        centerPanel.add(atributos, c);

        // north panel
        northPanel.setPreferredSize(new Dimension(500,100));
        northPanel.setBackground(Color.darkGray);
        northPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        northPanel.add(title, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        northPanel.add(title2, gbc);

        // buttons panel
        southPanel.setLayout(new FlowLayout());
        southPanel.setBackground(Color.darkGray);
        southPanel.add(add);
        southPanel.add(Box.createRigidArea(new Dimension(25, 0)));
        southPanel.add(submit);
        southPanel.add(cancel);


        // main panel
        mainPanel.setBackground(Color.lightGray);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(northPanel,BorderLayout.NORTH);
        //mainPanel.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.EAST);
        //mainPanel.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.WEST);
        mainPanel.add(centerPanel,BorderLayout.CENTER);
        mainPanel.add(southPanel,BorderLayout.SOUTH);
    }

    /** @brief Configura los "action listeners" de los distintos componentes

    \pre <em>Cierto</em>
    \post Permite que el usuario pueda interactuar con el sistema a través de los distintos componentes.
     */
    private void setActionsListeners() {
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int answer = JOptionPane.showConfirmDialog(null,"¿Estas seguro de que quieres cancelar la operacion?", "Warning", JOptionPane.YES_NO_OPTION);

                if (answer == 0)  {  // yes
                    ctrl_presentacio.tipoNuevoCancelado();
                }
            }
        });

        dataset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setCurrentDirectory(new File("./archivos/"));
                int response = fileChooser.showOpenDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    String directorio = fileChooser.getName(file);

                    datasetSeleccionado.setText(directorio);
                }
            }
        });

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (Objects.equals(datasetSeleccionado.getText(), "null")) {
                    JOptionPane.showMessageDialog(null, "No se puede añadir un nuevo tipo de atributo porque no se ha seleccionado ninguna carpeta (que contenga datasets)\n" +
                            "Seleccione primero dicha carpeta apretando [Seleccionar Dataset]\n", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else ctrl_presentacio.anadirAtributo();
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String directorio = "archivos/"+datasetSeleccionado.getText();
                ctrl_presentacio.guardarNuevoTipo(directorio, contenido);
            }
        });

        frameVista.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int answer = JOptionPane.showConfirmDialog(null,"¿Estas seguro de que quieres cancelar la operacion?", "Warning", JOptionPane.YES_NO_OPTION);

                if (answer == 0)  {  // yes
                    ctrl_presentacio.tipoNuevoCancelado();
                }
            }
        });
    }

    /** @brief Añade al scroll panel un nuevo tipo de atributo  */
    public void anadirAtributo(String tipo, String nombre) {
        String s = tipo + " , " + nombre;
        contenido.add(s);

        listaAtributos.setListData(contenido);

        //frameVista.repaint();
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
    public void enable() { frameVista.setEnabled(true); }

    /** @brief Deshabilita la vista

    \pre <em>Cierto</em>
    \post Impide la interacción del usuario con la vista.
     */
    public void disable() { frameVista.setEnabled(false); }
}
