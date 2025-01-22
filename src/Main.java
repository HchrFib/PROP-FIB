import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        /*System.out.printf("Hello and welcome!");*/

        /*for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }*/
        BitSet categoria = new BitSet();
        List<String> posicionsBitset = new ArrayList<String>();
        String valor = "Drama; accion; lt; futbol";

        String[] cat = valor.split(";");
        for (String s : cat) {
            if (!posicionsBitset.contains(s)) posicionsBitset.add(s);
            int pos = posicionsBitset.indexOf(s);
            categoria.set(pos);
        }
        System.out.println(categoria);
        System.out.println(posicionsBitset);

    }
}