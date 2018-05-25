package database;

import java.io.*;
import java.util.HashMap;

public class Dictionary {

    /* Singleton */
    private static Dictionary instance = new Dictionary();

    private Dictionary(){
        try {
            loadAllWords();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Dictionary getInstance() {
        if(instance == null) {
            instance = new Dictionary();
        }
        return instance;
    }


    /* TODO: change this to be used into jar*/
    private static final String wordListDirectory = "/Users/davidfalcao/Documents/GitHub/GameStop/src/database/res/";


    public enum Category{
        country, capital, name, brand, tvShow, animal
    }


    private HashMap<String, Category> wordList = new HashMap<String, Category>();

    /* TODO: change this to be used into jar*/
    public void loadAllWords() throws IOException {
        loadWords("Countries.txt", Category.country);
        loadWords("Capitals.txt", Category.capital);
        loadWords("Names.txt", Category.name);
        loadWords("Brands.txt", Category.brand);
        loadWords("TV Shows.txt", Category.tvShow);
        loadWords("Animals.txt", Category.animal);
    }

    private void loadWords(String fileName, Category cat) throws IOException {
        File file = new File(wordListDirectory + fileName);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String readWord;

        //InputStream in = getClass().getResourceAsStream("res/"+fileName);

        while((readWord = br.readLine()) != null)
            wordList.put(readWord.toLowerCase(), cat);

        br.close();
    }

    public boolean checkCategory(String word, Category cat, char first) {
        if(!wordList.containsKey(word))
            return false;
        if(wordList.get(word) != cat)
            return false;
        return true;
    }

}
