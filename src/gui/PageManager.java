package gui;

import java.util.ArrayList;

public class PageManager {
    private static PageManager ourInstance = new PageManager();
    private ArrayList<Page> pages = new ArrayList<Page>();
    private int index = -1;


    public static PageManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new PageManager();
        }
        return ourInstance;
    }

    private PageManager() {

    }

    public void push_page(Page page){
        pages.add(page);
        index++;

        if(index == 0){
            pages.get(index).start();
        }
        else {
            pages.get(index-1).hide_page();
            pages.get(index).start();

        }
    }

    public void pop_page(){

        pages.get(index).dispose();
        pages.remove(index);
        index--;

        if(index == -1){
            System.exit(0);
        }
        else pages.get(index).start();


    }



}
