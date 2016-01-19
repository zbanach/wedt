package pl.edu.pw.elka.wedt;


import org.junit.Test;
import org.maltparser.MaltParserService;
import org.maltparser.core.exception.MaltChainedException;
import org.maltparser.core.syntaxgraph.DependencyStructure;
import org.maltparser.core.syntaxgraph.edge.Edge;

public class MaltParserTest {

    @Test
    public void testParsing() {
        try {
            MaltParserService service =  new MaltParserService();
            // Inititalize the parser model 'model0' and sets the working directory to '.' and sets the logging file to 'parser.log'
            service.initializeParserModel("-c " +
                    "skladnica_liblinear_stackeager_final -m parse -w " +
                    "." +
                    " -lfi" +
                    " parser.log");

            // Creates an array of tokens, which contains the Swedish sentence 'Grundavdraget upphör alltså vid en taxerad inkomst på 52500 kr.'
            // in the CoNLL data format.
            String[] tokens = new String[4];
            tokens[0] = "1\tAla\tAla\tsubst\tsubst\tsg|nom|f\t_\t_\t_\t_";
            tokens[1] = "2\tma\tmieć\tverb\tfin\tsg|ter|imperf\t_\t_\t_\t_";
            tokens[2] = "3\tkota\tkot\tsubst\tsubst\tsg|acc|m2\t_\t_\t_\t_";
            tokens[3] = "4\t.\t.\tinterp\tinterp\t_\t_\t_\t_\t_";
            DependencyStructure graph = service.parse(tokens);

            // Outputs the dependency graph created by MaltParser.
            System.out.println(graph);
            // Terminates the parser model
            service.terminateParserModel();
        } catch (MaltChainedException e) {
            System.err.println("MaltParser exception: " + e.getMessage());
        }
    }
}
