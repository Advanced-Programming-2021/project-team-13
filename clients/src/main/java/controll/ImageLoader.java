package controll;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class ImageLoader extends Thread {
    private static final HashMap<String, Image> cardsImage = new HashMap<>();
    private static final File monsterFile = new File(System.getProperty("user.dir") + "/clients/src/main/resources/shopImage/Monsters");
    private static final File spellTrapFile = new File(System.getProperty("user.dir") + "/clients/src/main/resources/shopImage/SpellTrap");
    private static final FilenameFilter filenameFilter = (dir, name) -> name.endsWith(".jpg");

    @Override
    public void run() {
        load();
    }

    public static void load() {
        loadMonster();
        loadSpellAndTrap();
    }

    private static void loadMonster() {
        addCard(monsterFile);
    }

    private static void loadSpellAndTrap() {
        addCard(spellTrapFile);
    }

    private static void addCard(File spellTrapFile) {
        if (spellTrapFile.isDirectory()) {
            for (File file : Objects.requireNonNull(spellTrapFile.listFiles(filenameFilter))) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(file);
                    Image cardImage = SwingFXUtils.toFXImage(bufferedImage, null);
                    cardsImage.put(file.getName().replaceAll(".jpg", ""), cardImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Image getCardImageByName(String cardName) {
        cardName = cardName.replaceAll("[\\s\\-_*';\\.,^!]", "");
        return cardsImage.getOrDefault(cardName, cardsImage.get("createCard"));
    }

    public static HashMap<String, Image> getCardsImage() {
        return cardsImage;
    }
}
