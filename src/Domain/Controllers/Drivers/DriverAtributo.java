package Domain.Controllers.Drivers;
import Domain.Classes.Atributo;
import Domain.Classes.atributoCategorico;
import Domain.Classes.tipoAtributo;

public class DriverAtributo {
    public static void main(String[] args){
        try {
            testingConstructora();
            testingAtributoCategoricoBitset();
        } catch(Exception e) {
            System.out.println("Algo ha ido mal");
        }
    }
    private static void testingConstructora() {
        Atributo u1 = new Atributo(tipoAtributo.freetext, "Hola");

        System.out.println("Todo OK!");
    }
    private static void testingAtributoCategoricoBitset(){
        String categories = "en;spa" ;
        String generes = "por;accio" ;
        String atributsMix = "por;spa" ;
        atributoCategorico aC = new atributoCategorico(tipoAtributo.categorico, "idiomes");
        atributoCategorico aC2 = new atributoCategorico(tipoAtributo.categorico, "generes");
        atributoCategorico aC3 = new atributoCategorico(tipoAtributo.categorico, "mix");
        aC.setValor(categories);
        aC2.setValor(generes);
        aC3.setValor(atributsMix);
        System.out.println(aC.getCategoria().toString());
        System.out.println(aC.getCategoria().cardinality());
        for (Byte i : aC.getCategoria().toByteArray()) {
            System.out.println(i + " ");
        }
        System.out.println(aC2.getCategoria().toString());
        System.out.println(aC3.getCategoria().toString());
        System.out.println("TODO OK!");
    }
}