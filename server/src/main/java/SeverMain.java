import controll.ImageLoader;
import controll.ServerController;


public class SeverMain {
    public static void main(String[] args) {
        ImageLoader.load();
        new ServerController().run();
    }
}
