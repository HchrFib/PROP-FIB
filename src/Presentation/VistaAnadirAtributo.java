package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** @file VistaAnadirAtributo.java
 @brief Implementación de la vista que nos permite concretar qué tipo de atributos tendrá el tipo de ítem nuevo
  que estamos definiendo.
 */

/** @class VistaAnadirAtributo

Esta vista se complementa a la vista "vistaDefinirTipo" para
 permitir al usuario definir nuevos tipos de ítems para poder trabajar con datasets diferentes a peliculas o series.
 */
public class VistaAnadirAtributo {

    private CtrlPresentacio ctrl_presentacio;
    // FRAME
    private JFrame frameVista = new JFrame("Obtener Recomendacion");

    // PANEL
    private JPanel mainPanel = new JPanel();
    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel centerPanel = new JPanel();

    // LABEL
    private JLabel title = new JLabel("Seleccione, primero, el tipo de atributo que desea añadir");
    private JLabel title2 = new JLabel("y, posteriormente, inserte el nombre de dicho atributo");

    // BUTTON
    private JButton submit = new JButton("Añadir");
    private JButton cancel = new JButton("Cancelar");

    // TEXT FIELD + COMBO BOX
    private JTextField nombreAtributo = new JTextField("nombreAtributo");
    private String[] types = {"bool", "text", "num", "categorico"};
    private JComboBox attributeType = new JComboBox(types);

    /** @brief Constructora por defecto. */
    public VistaAnadirAtributo() {}

    /** @brief Creadora con paso de parámetros.

    Crea la instancia de la clase VistaAnadirAtributo.
    \pre <em>Cierto</em>
    \post Crea la instancia, la asocia con el controlador Presentacion
    y inicializa los componentes de la vista asi como sus "action listeners".
     */

    public VistaAnadirAtributo(CtrlPresentacio ctrlPresentacio) {
        this.ctrl_presentacio = ctrlPresentacio;
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
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameVista.setPreferredSize(new Dimension(800, 300));
        frameVista.add(mainPanel);
        frameVista.setLocationRelativeTo(null);
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

        // TEXT FIELD OPTIONS
        nombreAtributo.setPreferredSize(new Dimension(100,20));

        // PANEL OPTIONS

        // center panel
        //centerPanel.setLayout(new FlowLayout());
        centerPanel.setPreferredSize(new Dimension(500, 100));
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        centerPanel.add(attributeType, c);

        c.gridx = 1;
        c.gridy = 0;
        centerPanel.add(Box.createRigidArea(new Dimension(25, 0)), c);

        c.gridx = 2;
        c.gridy = 0;
        centerPanel.add(nombreAtributo, c);

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
                    ctrl_presentacio.atributoNuevoCancelado();
                }
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String tipo = (String) attributeType.getSelectedItem();
                String nombre = nombreAtributo.getText();

                ctrl_presentacio.atributoAnadido(tipo, nombre);
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
}

