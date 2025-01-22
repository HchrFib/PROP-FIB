package Presentation;

import Domain.Classes.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

/** @file VistaMostrarValoracion.java
 @brief Implementación de la vista que nos muestra las recomendaciones obtenidas así como su calidad.
 */

/** @class VistaMostrarValoracion

Esta vista muestra las recomendaciones obtenidas para un usuario concreto así como la calidad de las mismas.
También, nos permite guardarlas en un archivo, si lo deseamos.
 */
public class VistaMostrarValoracion {
    private CtrlPresentacio ctrl_presentacio;
    private LinkedList<Pair<Integer, Double>> recomendacionesObtenidas;
    // FRAME
    private JFrame frameVista = new JFrame("Mostrar Valoracion de Calidad Obtenida");

    // PANELS
    private JPanel mainPanel = new JPanel();
    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel showRecPanel = new JPanel();

    // LABELS
    private JLabel userIdSeleccionado = new JLabel("null");
    private JLabel title = new JLabel("Estas son las recomendaciones que hemos obtenido para el usuario: " + userIdSeleccionado.getText());
    private JLabel subtitle = new JLabel("Y esta es la calidad de las mismas");
    private JLabel userId = new JLabel("userId: ");
    private JLabel calidadLabel = new JLabel("nDCG: ");
    private JLabel calidadObtenida = new JLabel("null");

    // BUTTONS
    private JButton ok = new JButton("OK");

    // LIST AND SCROLL
    private JScrollPane jsp = new JScrollPane(showRecPanel);

    /** @brief Constructora por defecto. */
    public VistaMostrarValoracion() {
    }

    /** @brief Creadora con paso de parámetros.

    Crea la instancia de la clase VistaMostrarRecomendacion.
    \pre <em>Cierto</em>
    \post Crea la instancia, la asocia con el controlador Presentacion
    y inicializa los componentes de la vista asi como sus "action listeners".
     */
    public VistaMostrarValoracion(CtrlPresentacio ctrlPresentacio) {
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
        frameVista.setPreferredSize(new Dimension(900, 500));
        frameVista.setResizable(false);
        frameVista.setLocationRelativeTo(null);
        frameVista.add(mainPanel);
        frameVista.pack();

        // LABEL OPTIONS
        title.setBackground(Color.darkGray);
        title.setOpaque(true);
        title.setForeground(Color.lightGray);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Roboto", Font.PLAIN, 16));

        subtitle.setBackground(Color.darkGray);
        subtitle.setOpaque(true);
        subtitle.setForeground(Color.lightGray);
        subtitle.setHorizontalAlignment(JLabel.CENTER);
        subtitle.setFont(new Font("Roboto", Font.PLAIN, 16));

        calidadLabel.setFont(new Font("Roboto", Font.PLAIN, 13));
        calidadObtenida.setFont(new Font("Roboto", Font.PLAIN, 13));

        userId.setFont(new Font("Roboto", Font.PLAIN, 13));
        userIdSeleccionado.setFont(new Font("Roboto", Font.PLAIN, 13));

        // BUTTONS OPTIONS
        ok.setFocusable(false);

        // SCROLL BAR OPTIONS
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setPreferredSize(new Dimension(500, 230));

        // PANEL OPTIONS

        // center panel
        centerPanel.setLayout(new GridBagLayout());
        //centerPanel.setPreferredSize(new Dimension(500, 100));
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        centerPanel.add(userId, c);

        c.gridx = 1;
        c.gridy = 0;
        centerPanel.add(Box.createRigidArea(new Dimension(25, 0)), c);

        c.gridx = 2;
        c.gridy = 0;
        centerPanel.add(userIdSeleccionado, c);

        c.gridx = 0;
        c.gridy = 1;
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)), c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        centerPanel.add(jsp, c);

        c.gridx = 0;
        c.gridy = 3;
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)), c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        centerPanel.add(calidadLabel, c);

        c.gridx = 1;
        c.gridy = 4;
        centerPanel.add(Box.createRigidArea(new Dimension(25, 0)), c);

        c.gridx = 2;
        c.gridy = 4;
        centerPanel.add(calidadObtenida, c);

        // north panel
        northPanel.setPreferredSize(new Dimension(500, 75));
        northPanel.setBackground(Color.darkGray);
        northPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        northPanel.add(title, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        northPanel.add(subtitle, gbc);

        // buttons panel
        southPanel.setLayout(new FlowLayout());
        southPanel.setBackground(Color.darkGray);
        southPanel.add(ok);

        // main panel
        mainPanel.setBackground(Color.lightGray);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(northPanel, BorderLayout.NORTH);
        //mainPanel.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.EAST);
        //mainPanel.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
    }

    /** @brief Configura los "action listeners" de los distintos componentes

    \pre <em>Cierto</em>
    \post Permite que el usuario pueda interactuar con el sistema a través de los distintos buttons.
     */
    private void setActionsListeners() {
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int answer = JOptionPane.showOptionDialog(
                        null,
                        "¿Desea guardar estas recomendaciones en un archivo?",
                        "Guardar Recomendaciones",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        0);
                if (answer == 0) {
                    int userValue;
                    userValue = Integer.parseInt(userIdSeleccionado.getText());
                    ctrl_presentacio.guardarRecomendaciones(userValue, recomendacionesObtenidas);

                }
                ctrl_presentacio.valoracionMostrada();
            }
        });

        frameVista.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int answer = JOptionPane.showConfirmDialog(null, "Si cierra la ventana no podrá guardar sus recomendaciones \n" +
                        "¿Desea cerrarla igualmente?", "Warning", JOptionPane.YES_NO_OPTION);

                if (answer == 0) {  // yes
                    ctrl_presentacio.valoracionMostrada();
                }
            }
        });
    }

    /**
     * @brief Configura el Scroll Panel
     * <p>
     * \pre <em>Cierto</em>
     * \post Llena la tabla del scroll panel con las recomendaciones obtenidas para el usuario de ID = userId.
     * También muestra en la vista, la calidad de esas recomendaciones.
     */
    public void mostrarRecomendaciones(int userId, LinkedList<Pair<Integer, Double>> recomendaciones, double NDCG) {
        this.recomendacionesObtenidas = recomendaciones;
        resetear();
        userIdSeleccionado.setText(Integer.toString(userId));
        calidadObtenida.setText(Double.toString(NDCG));

        GridLayout gl = new GridLayout(recomendaciones.size() + 1, 2);
        showRecPanel.setLayout(gl);

        JTextField cabecera1 = new JTextField();
        cabecera1.setEditable(false);
        cabecera1.setText("item-id");
        cabecera1.setFont(new Font("Roboto", Font.PLAIN, 13));

        JTextField cabecera2 = new JTextField();
        cabecera2.setEditable(false);
        cabecera2.setText("rating");
        cabecera2.setFont(new Font("Roboto", Font.PLAIN, 13));

        showRecPanel.add(cabecera1);
        showRecPanel.add(cabecera2);

        for (var it : recomendaciones) {
            JTextField itemId = new JTextField();
            itemId.setEditable(false);
            JTextField rating = new JTextField();
            rating.setEditable(false);
            itemId.setText(Integer.toString(it.getFirst()));
            rating.setText(Double.toString(it.getSecond()));
            showRecPanel.add(itemId);
            showRecPanel.add(rating);
        }

        title.setText("Estas son las recomendaciones que hemos obtenido para el usuario: " + userIdSeleccionado.getText());

    }

    /**
     * @brief Resetea el Scroll Panel
     * <p>
     * \pre <em>Cierto</em>
     * \post Resetea el Scroll Panel.
     */
    private void resetear() {
        showRecPanel.removeAll();
    }

    /**
     * @brief Muestra la vista
     * <p>
     * \pre <em>Cierto</em>
     * \post Muestra la vista por pantalla.
     */
    public void hacerVisible() {
        frameVista.setVisible(true);
    }

    /**
     * @brief Esconde la vista
     * <p>
     * \pre <em>Cierto</em>
     * \post Hace que la vista desaparezca de la pantalla.
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
