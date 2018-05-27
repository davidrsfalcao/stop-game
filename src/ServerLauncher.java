import gui.ServerPage;
import gui.PageManager;


public class ServerLauncher {


    public static void main(String[] args){


        PageManager pages = PageManager.getInstance();
        pages.push_page(new ServerPage());

    }

}
