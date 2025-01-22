package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;


/** @file VistaRecomendacion.java
 @brief Implementación de la vista que permite solicitar y obtener recomendaciones.
 */

/** @class VistaRecomendacion

Esta vista permite al usuario obtener 'n' recomendaciones para un usuario concreto, usando una estrategia concreta.
 */
public class VistaRecomendacion {
    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista = new JFrame("Obtener Recomendacion");
    private JPanel mainPanel = new JPanel();
    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JLabel title = new JLabel("Inserte el id del usuario sobre el que solicitaremos una recomendacion asi como el numero de recomendaciones.");
    private JLabel title2 = new JLabel("Seleccione, también, qué tipo de estrategia desea utilizar para solicitar dichas recomendaciones.");
    private JButton submit = new JButton("Solicitar");
    private JButton cancel = new JButton("Cancelar");
    private JTextField userIdField = new JTextField("userId");
    private JTextField numRecommendaciones = new JTextField("num-recomendaciones");
    private String[] strategies = {"Collaborative Filtering", "Content-Based Filtering", "Hybrid"};
    private JComboBox selectStrategy = new JComboBox(strategies);

    /** @brief Constructora por defecto. */
    public VistaRecomendacion() {}

    /** @brief Creadora con paso de parámetros.

    Crea la instancia de la clase VistaRecomendacion.
    \pre <em>Cierto</em>
    \post Crea la instancia, la asocia con el controlador Presentación
    y inicializa los componentes de la vista asi como sus "action listeners".
     */
    public VistaRecomendacion(CtrlPresentacio ctrlPresentacio) {
        ctrl_presentacio = ctrlPresentacio;
        setComponents();
        setActionsListeners();
    }

    /** @brief Configura los componentes de la vista

    \pre <em>Cierto</em>
    \post Inicializa el frame principal y configura los text fields principales, así como los buttons y el combobox.
    Se define la estructura de la vista así como su estética.
     */
    private void setComponents() {
        // FRAME OPTIONS
        frameVista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frameVista.setPreferredSize(new Dimension(800, 300));
        frameVista.setLocationRelativeTo(null);
        frameVista.add(mainPanel);
        frameVista.pack();

        // LABEL OPTIONS
        title.setBackground(Color.darkGray);
        title.setOpaque(true);
        title.setForeground(Color.lightGray);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Roboto",Font.PLAIN,14));

        title2.setBackground(Color.darkGray);
        title2.setOpaque(true);
        title2.setForeground(Color.lightGray);
        title2.setHorizontalAlignment(JLabel.CENTER);
        title2.setFont(new Font("Roboto",Font.PLAIN,14));

        // BUTTONS OPTIONS
        submit.setFocusable(false);
        submit.setFont(new Font("Roboto",Font.PLAIN,12));
        cancel.setFocusable(false);
        cancel.setFont(new Font("Roboto",Font.PLAIN,12));

        // TEXT FIELD OPTIONS
        userIdField.setPreferredSize(new Dimension(120,25));
        userIdField.setForeground(Color.gray);
        userIdField.setFont(new Font("Roboto",Font.PLAIN,12));

        numRecommendaciones.setPreferredSize(new Dimension(150,25));
        numRecommendaciones.setForeground(Color.gray);
        numRecommendaciones.setFont(new Font("Roboto",Font.PLAIN,12));

        // PANEL OPTIONS

        // center panel
        //centerPanel.setLayout(new FlowLayout());
        centerPanel.setPreferredSize(new Dimension(500, 100));
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        centerPanel.add(userIdField, c);

        c.gridx = 1;
        c.gridy = 0;
        centerPanel.add(Box.createRigidArea(new Dimension(25, 0)), c);

        c.gridx = 2;
        c.gridy = 0;
        centerPanel.add(numRecommendaciones, c);

        c.gridx = 3;
        c.gridy = 0;
        centerPanel.add(Box.createRigidArea(new Dimension(25, 0)), c);

        c.gridx = 4;
        c.gridy = 0;
        selectStrategy.setPreferredSize(new Dimension(120, 25));
        centerPanel.add(selectStrategy, c);

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

    /** @brief Función privada para comprobar si un string almacena una información numérica

    \pre <em>Cierto</em>
    \post Devuelve cierto ssi "str" es numérico.
     */
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /** @brief Configura los "action listeners" de los distintos componentes

    \pre <em>Cierto</em>
    \post Permite que el usuario pueda interactuar con el sistema a través de los distintos buttons
    así como de los text fields y el combobox.
     */
    private void setActionsListeners() {
        userIdField.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (Objects.equals(userIdField.getText(), "userId")) {
                    userIdField.setText("");
                    userIdField.setForeground(Color.black);
                }
                if (Objects.equals(numRecommendaciones.getText(), "")) {
                    numRecommendaciones.setText("num-recomendaciones");
                    numRecommendaciones.setForeground(Color.gray);
                }
            }
        });

        numRecommendaciones.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (Objects.equals(userIdField.getText(), "")) {
                    userIdField.setText("userId");
                    userIdField.setForeground(Color.gray);
                }
                if (Objects.equals(numRecommendaciones.getText(), "num-recomendaciones")) {
                    numRecommendaciones.setText("");
                    numRecommendaciones.setForeground(Color.black);
                }
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String userId = userIdField.getText();
                String numRec = numRecommendaciones.getText();

                if (!(isNumeric(userId) && isNumeric(numRec))) {
                    JOptionPane.showMessageDialog(null,"Tanto 'userId' como 'numRecomendaciones' tienen que ser de tipo numerico", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    int userValue = Integer.parseInt(userId);
                    int numRecValue = Integer.parseInt(numRec);
                    String estrategia = (String) selectStrategy.getSelectedItem();
                    System.out.println("Estrategia seleccionada: " +  estrategia);
                    boolean existeUsuario = ctrl_presentacio.existeUsuario(userValue);

                    if (existeUsuario) {
                        ctrl_presentacio.solicitarRecomendaciones(userValue,numRecValue,estrategia);
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"El usuario introducido no existe", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int answer = JOptionPane.showConfirmDialog(null,"¿Estas seguro de que quieres cancelar la operacion?", "Warning", JOptionPane.YES_NO_OPTION);

                if (answer == 0)  {  // yes
                    ctrl_presentacio.recomendacionCancelada();
                }
            }
        });

        frameVista.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int answer = JOptionPane.showConfirmDialog(null,"¿Estas seguro de que quieres cancelar la operacion?", "Warning", JOptionPane.YES_NO_OPTION);

                if (answer == 0)  {  // yes
                    ctrl_presentacio.recomendacionCancelada();
                }
            }
        });
    }

    /** @brief Resetea los textFields  */
    private void resetear() {
        userIdField.setText("userId");
        numRecommendaciones.setText("num-recomendaciones");
    }

    /** @brief Muestra la vista

    \pre <em>Cierto</em>
    \post Muestra la vista por pantalla.
     */
    public void hacerVisible() {
        resetear();
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
