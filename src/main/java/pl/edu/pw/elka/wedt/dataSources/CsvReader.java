package pl.edu.pw.elka.wedt.dataSources;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artur Góralewski.
 */
public class CsvReader {

    //Input file which needs to be parsed
    String fileToParse;

    //Delimiter used in CSV file
    final String DELIMITER = "\t";

    public CsvReader(String fileToParse) {
        this.fileToParse = fileToParse;
    }

    public Map<String,Map<String,Object>> makeSentimentDictionary(){
        Map<String,Map<String,Object>> sentimentDictionary = new HashMap<>();
        BufferedReader fileReader = null;

        String[] attrNames = {"null"};

        try{
            String line;
            //Create the file reader
            fileReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileToParse), "UTF8"));//new FileReader(fileToParse));

            int lineNr =0;
            //Read the file line by line
            while ((line = fileReader.readLine()) != null){
                lineNr++;
                if(lineNr == 1){
                    attrNames = line.split(DELIMITER);
                }
                else{
                    //Get all tokens available in line
                    String[] tokens = line.split(DELIMITER);
                    String word = tokens[0];
                    Map<String, Object> attributes = new HashMap<>();
                    for(int i = 1; i<tokens.length; i++){
                        if(isInteger(tokens[i])){
                            attributes.put(attrNames[i], Integer.parseInt(tokens[i]));
                        }else{
                            if(isDouble(tokens[i])){
                                attributes.put(attrNames[i], Double.parseDouble(tokens[i]));
                            }
                            else
                                attributes.put(attrNames[i], tokens[i]);
                        }
                    }
                    sentimentDictionary.put(word, attributes);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sentimentDictionary;
    }

    private boolean isInteger(String value){
        try {
            Integer.parseInt(value);
        }catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isDouble(String value){
        try {
            Double.parseDouble(value);
        }catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
