package pl.edu.pw.elka.wedt;


import pl.edu.pw.elka.wedt.main.dataSources.CsvReader;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {

        CsvReader csvReader = new CsvReader(Paths.get(".").toAbsolutePath().normalize().toString()+"\\src\\main\\java\\pl\\edu\\pw\\elka\\wedt\\main\\resources\\slownikWydzwieku01.csv");

        csvReader.makeSentimentDictionary().forEach((k,v) -> System.out.println("key: "+k+" value:"+v));

    }
}
