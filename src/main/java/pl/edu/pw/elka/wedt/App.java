package pl.edu.pw.elka.wedt;


import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;
import pl.edu.pw.elka.wedt.model.Word;

import java.util.List;

public class App {

    public static void main(String[] args) {

//        CsvReader csvReader = new CsvReader(Paths.get(".").toAbsolutePath().normalize().toString()+"\\src\\main\\java\\pl\\edu\\pw\\elka\\wedt\\resources\\slownikWydzwieku01.csv");
//        csvReader.makeSentimentDictionary().forEach((k,v) -> System.out.println("key: "+k+" value:"+v));


//        System.out.println("-------------");
//        for(Map.Entry<String, Map<String, Object>> entry : csvReader.makeSentimentDictionary().entrySet() ) {
//            String key = entry.getKey();
//            Map value = entry.getValue();
//            System.out.println("key: "+key+" value:"+value);
//            // do what you have to do here
//            // In your case, an other loop.
//        }


//        String word = "domu";
//        Dictionary polish = Dictionary.getForLanguage(„pl”);
//        DictionaryLookup dl = new DictionaryLookup(polish);
//        List<WordData> wordList = dl.lookup(word);
///*Pozycja 0 wybrana „na sztywno”, może być więcej rezultatów */
//        String stemmed = wordList.isEmpty() ? null : wordList.get(0).toString();
//
//        System.out.println(stemmed);


        PolishStemmer polishStem = new PolishStemmer();
        String toStemmed =  "pojechał";
        List<WordData> wordList = polishStem.lookup(toStemmed);
        WordData stemmed = wordList.isEmpty() ? null : wordList.get(0);
        System.out.println(wordList.isEmpty()+" "+stemmed);
        Word word = new Word(stemmed);
        System.out.println(word.getPOS());

//        PolishStemmer stemmer = new PolishStemmer();
//
//        String in = "Nie zabrakło oczywiście wpadek. Największym zaskoczeniem okazał się dla nas strój Katarzyny Zielińskiej, której ewidentnie o coś chodziło, ale wciąż nie wiemy o co.";
//        for (String t : in.toLowerCase(new Locale("pl")).split("[\\s\\.\\,]+")) {
//            System.out.println("> '" + t + "'");
//            for (WordData wd : stemmer.lookup(t)) {
//                System.out.print(
//                        "  - " +
//                                (wd.getStem() == null ? "<null>" : wd.getStem()) + ", " +
//                                wd.getTag());
//            }
//            System.out.println();
//        }


    }
}
