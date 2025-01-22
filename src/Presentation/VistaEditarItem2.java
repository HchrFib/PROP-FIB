package Presentation;

import Domain.Classes.Atributo;
import Domain.Classes.Item;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class VistaEditarItem2 extends JFrame {
    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista = new JFrame("Editar Itemn");
    ArrayList<Item> listaItems = new ArrayList<>();

    public VistaEditarItem2(CtrlPresentacio ctrl_presentacio) {
        this.ctrl_presentacio = ctrl_presentacio;
    }

    private Vector strArray2Vector(String[] str) {
        Vector vector = new Vector();
        for (int i = 0; i < str.length; i++) {
            Vector v = new Vector();
            v.addElement(str[i]);
            vector.addElement(v);
        }
        return vector;
    }

    public void setItems(ArrayList<Item> items){
        listaItems = items;

        Item i1 = listaItems.get(1);
        ArrayList<Atributo> atributos = i1.getAtributos();
        String[] data = new String[atributos.size()];
        for (int i = 0; i < atributos.size(); i++) {
            data[i] = atributos.get(i).getName();
        }

        JList list = new JList(data);
        JButton submit = new JButton("Ok");
        JScrollPane scrollList = new JScrollPane(list);
        scrollList.setMinimumSize(new Dimension(100, 80));
        Box listBox = new Box(BoxLayout.Y_AXIS);
        listBox.add(scrollList);
        listBox.add(new JLabel("Atributos"));


        DefaultTableModel dm = new DefaultTableModel();
        Vector dummyHeader = new Vector();
        dummyHeader.addElement("");
        dm.setDataVector(strArray2Vector(data), dummyHeader);
        JTable table = new JTable(dm);
        table.setShowGrid(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setColumnHeader(null);
        scrollTable.setMinimumSize(new Dimension(100, 80));
        Box tableBox = new Box(BoxLayout.Y_AXIS);
        tableBox.add(scrollTable);
        tableBox.add(new JLabel("Valores"));

        Container c = getContentPane();
        c.setLayout(new BoxLayout(c, BoxLayout.X_AXIS));
        c.add(listBox);
        c.add(new JSeparator(SwingConstants.VERTICAL));
        //c.add(new JLabel("test"));
        //c.add(new JSeparator(SwingConstants.HORIZONTAL));
        c.add(tableBox);
        c.add(submit);
        setSize(220, 130);


        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                for (int row = 0; row < table.getRowCount(); row++) {
                    for (int col = 0; col < table.getColumnCount(); col++) {
                        System.out.println(table.getColumnName(col));
                        System.out.println(": ");
                        System.out.println(table.getValueAt(row, col));
                    }
                }
            }
        });
        setVisible(true);

    }

    public void hacerVisible() {
        //frameVista.setVisible(true);
    }
    public void esconder() {
        frameVista.setVisible(false);
    }
}
