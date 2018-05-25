package database;

import java.io.*;
import java.util.HashMap;

public class Dictionary {

    private static final String wordListDirectory = "src" + File.separator + "res" + File.separator;


    public enum Category{
        country, capital, name, brand, tvShow, animal
    }


    private static HashMap<String, Category> wordList = new HashMap<String, Category>();

    //Starts the dictionary - should be called during server setup
    public static void loadAllWords() throws IOException {
        loadWords("Countries.txt", Category.country);
        loadWords("Capitals.txt", Category.capital);
        loadWords("Names.txt", Category.name);
        loadWords("Brands.txt", Category.brand);
        loadWords("TV Shows.txt", Category.tvShow);
        loadWords("Animals.txt", Category.animal);
    }

    private static void loadWords(String fileName, Category cat) throws IOException {
        File file = new File(wordListDirectory + fileName);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String readWord;

        while((readWord = br.readLine()) != null)
            wordList.put(readWord.toLowerCase(), cat);

        br.close();
    }

    public static boolean checkCategory(String word, Category cat, char first) {
        if(!wordList.containsKey(word))
            return false;
        if(wordList.get(word) != cat)
            return false;
        return true;
    }

}
