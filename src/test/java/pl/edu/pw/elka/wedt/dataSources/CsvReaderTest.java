package pl.edu.pw.elka.wedt.dataSources;

import junit.framework.TestCase;

import java.nio.file.Paths;

/**
 * Created by Artur Góralewski.
 */
public class CsvReaderTest extends TestCase {

    CsvReader csvReader;

    public void setUp() throws Exception {
        super.setUp();
        this.csvReader = new CsvReader(Paths.get(".").toAbsolutePath().normalize().toString()+"\\src\\main\\java\\pl\\edu\\pw\\elka\\wedt\\resources\\slownikWydzwieku01.csv");
    }

    public void tearDown() throws Exception {

    }

    public void testMakeSentimentDictionary() throws Exception {
//        csvReader.makeSentimentDictionary().forEach((k,v) -> System.out.println("key: "+k+" value:"+v));


        //        for(Map.Entry<String, Map<String, Object>> entry : csvReader.makeSentimentDictionary().entrySet() ) {
        //            String key = entry.getKey();
        //            Map value = entry.getValue();
        //            System.out.println("key: "+key+" value:"+value);
        //            // do what you have to do here
        //            // In your case, an other loop.
        //        }

    }
}