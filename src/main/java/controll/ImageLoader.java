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

public class ImageLoader {
    private final HashMap<String, Image> cardsImage = new HashMap<String, Image>();
    private final File monsterFile = new File(System.getProperty("user.dir") + "/src/main/resources/shopImage/Monsters");
    private final File spellTrapFile = new File(System.getProperty("user.dir") + "/src/main/resources/shopImage/SpellTrap");
    private final FilenameFilter filenameFilter = (dir, name) -> name.endsWith(".png");
    private BufferedImage bufferedImage = null;

    public void load() {
        loadMonster();
        loadSpellAndTrap();
    }

    private void loadMonster() {
        if (monsterFile.isDirectory()) {
            for (File file : Objects.requireNonNull(monsterFile.listFiles(filenameFilter))) {
                try {
                    bufferedImage = ImageIO.read(file);
                    Image cardImage = SwingFXUtils.toFXImage(bufferedImage, null);
                    cardsImage.put(file.getName().replaceAll(".png", ""), cardImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadSpellAndTrap() {
        if (spellTrapFile.isDirectory()) {
            for (File file : Objects.requireNonNull(spellTrapFile.listFiles(filenameFilter))) {
                try {
                    bufferedImage = ImageIO.read(file);
                    Image cardImage = SwingFXUtils.toFXImage(bufferedImage, null);
                    cardsImage.put(file.getName().replaceAll(".png", ""), cardImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Image getCardImageByName(String cardName) {
        cardName = cardName.replaceAll("[\\s\\-_*';\\.,^!]", "");
        return cardsImage.get(cardName);
    }

    public HashMap<String, Image> getCardsImage() {
        return cardsImage;
    }
}
