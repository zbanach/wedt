package pl.edu.pw.elka.wedt;


import org.junit.Test;
import org.maltparser.MaltParserService;
import org.maltparser.core.exception.MaltChainedException;
import org.maltparser.core.syntaxgraph.DependencyStructure;
import org.maltparser.core.syntaxgraph.edge.Edge;
import pl.edu.pw.elka.wedt.text.DocumentTokenizer;

public class MaltParserTest {

    @Test
    public void testParsing() {
        try {
            MaltParserService service =  new MaltParserService();
            service.initializeParserModel("-c " +
                    "skladnica_liblinear_stackeager_final -m parse -w " +
                    "." +
                    " -lfi" +
                    " parser.log");

//            String[] tokens = new String[4];
//            tokens[0] = "1\tAla\tAla\tsubst\tsubst\tsg|nom|f\t_\t_\t_\t_";
//            tokens[1] = "2\tma\tmieć\tverb\tfin\tsg|ter|imperf\t_\t_\t_\t_";
//            tokens[2] = "3\tkota\tkot\tsubst\tsubst\tsg|acc|m2\t_\t_\t_\t_";
//            tokens[3] = "4\t.\t.\tinterp\tinterp\t_\t_\t_\t_\t_";
            String[] tokens = new DocumentTokenizer().sentenceToCONLL("Telefon, który kupiłem miesiąc temu, nie " +
                    "działa rewelacyjnie.");
            DependencyStructure graph = service.parse(tokens);

            System.out.println(graph);
            service.terminateParserModel();
        } catch (MaltChainedException e) {
            System.err.println("MaltParser exception: " + e.getMessage());
        }
    }
}
