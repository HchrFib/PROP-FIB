package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/** @file VistaGestionUsuarios.java
 @brief Implementación de la vista que representa la funcionalidad de la gestión de usuarios.
 */

/** @class VistaGestionUsuarios

Esta vista permite al usuario interactuar con el sistema para añadir / eliminar usuarios a su antojo.
 */
public class VistaGestionUsuarios {
    private CtrlPresentacio ctrl_presentacio;
    private static final Color GP_COLOR = new Color(0, 0, 0, 150);

    // FRAME
    private JFrame frameVista = new JFrame("Gestionar Usuarios");

    // PANELS
    private JPanel mainPanel = new JPanel();
    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private final JPanel glassPanel = new JPanel() {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(GP_COLOR);
            g.fillRect(0, 0, getWidth(), getHeight());
        };
    };

    // LABELS
    private JLabel title = new JLabel("Seleccione qué desea hacer:");

    // BUTTONS
    private JButton cancel = new JButton("Cancelar y Volver");
    private JButton anadir = new JButton("Anadir Usuario");
    private JButton eliminar = new JButton("Eliminar Usuario");

    /** @brief Constructora por defecto. */
    public VistaGestionUsuarios(){}

    /** @brief Creadora con paso de parámetros.

    Crea la instancia de la clase VistaGestionUsuarios.
    \pre <em>Cierto</em>
    \post Crea la instancia, la asocia con el controlador Presentacion
    y inicializa los componentes de la vista asi como sus "action listeners".
     */
    public VistaGestionUsuarios(CtrlPresentacio ctrlPresentacio) {
        ctrl_presentacio = ctrlPresentacio;
        setComponents();
        setActionsListeners();
    }

    /** @brief Configura los componentes de la vista

    \pre <em>Cierto</em>
    \post Inicializa el frame principal y configura los buttons.
    Se define la estructura de la vista así como su estética.
     */
    private void setComponents() {
        // FRAME OPTIONS
        frameVista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frameVista.setPreferredSize(new Dimension(450, 200));
        frameVista.setLocationRelativeTo(null);
        frameVista.setGlassPane(glassPanel);
        frameVista.add(mainPanel);
        frameVista.pack();

        // LABEL OPTIONS
        title.setBackground(Color.darkGray);
        title.setOpaque(true);
        title.setForeground(Color.lightGray);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Roboto",Font.PLAIN,16));

        // BUTTONS OPTIONS
        cancel.setFocusable(false);
        cancel.setFont(new Font("Roboto",Font.PLAIN,12));
        anadir.setFocusable(false);
        anadir.setFont(new Font("Roboto",Font.PLAIN,12));
        eliminar.setFocusable(false);
        eliminar.setFont(new Font("Roboto",Font.PLAIN,12));

        // PANEL OPTIONS

        // center panel
        centerPanel.setLayout(new FlowLayout());
        centerPanel.setPreferredSize(new Dimension(500, 100));
        centerPanel.add(anadir);
        centerPanel.add(eliminar);

        // north panel
        northPanel.setPreferredSize(new Dimension(500,40));
        northPanel.setLayout(new GridLayout());
        northPanel.setBackground(Color.darkGray);
        northPanel.add(title);

        // buttons panel
        southPanel.setLayout(new FlowLayout());
        southPanel.setBackground(Color.darkGray);
        southPanel.add(cancel);

        // main panel
        mainPanel.setBackground(Color.lightGray);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(northPanel,BorderLayout.NORTH);
        //mainPanel.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.EAST);
        //mainPanel.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.WEST);
        mainPanel.add(centerPanel,BorderLayout.CENTER);
        mainPanel.add(southPanel,BorderLayout.SOUTH);

        // GLASS PANEL
        glassPanel.setOpaque(false);
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
                    ctrl_presentacio.gestionarUsuariosCancelado();
                }
            }
        });

        anadir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ctrl_presentacio.anadirUsuario();
            }
        });

        eliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ctrl_presentacio.eliminarUsuario();
            }
        });

        frameVista.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ctrl_presentacio.gestionarUsuariosCancelado();
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
