import model.players.User;
import org.junit.Assert;
import org.junit.Test;
import view.Menu;
import view.ViewMaster;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;

public class GameTest {

    @Test
    public void battleTest() {
        InputStream sysInBackup = System.in;
        User.getAllUsers().clear();
        ViewMaster.setCurrentMenu(Menu.LOGIN_MENU);
        ByteArrayInputStream in = new ByteArrayInputStream(("user create --u nima --nickname Nima --p 1234\n" +
                "user create --nickname Hooman --username homaan --password 1234\n" +
                "user login --password 1234 --username nima\n" +
                "menu enter shop\n" +
                "shop buy Magic Cylinder\n" +
                "shop buy Call of The Haunted\n" +
                "shop buy Time Seal\n" +
                "shop buy Magic Jammer\n" +
                "shop buy Mind Crush\n" +
                "shop buy Negate Attack\n" +
                "shop buy Torrential Tribute\n" +
                "shop buy Scanner\n" +
                "shop buy command knight\n" +
                "shop buy Horn IMp\n" +
                "shop buy yomi ship\n" +
                "shop buy suijin\n" +
                "shop buy fireyarou\n" +
                "shop buy Curtain of the Dark Ones\n" +
                "shop buy Feral Imp\n" +
                "shop buy Dark Magician\n" +
                "shop buy Wattkid\n" +
                "shop buy Baby Dragon\n" +
                "shop buy Hero of the East\n" +
                "shop buy Crawling dragon\n" +
                "shop buy Flame Manipulator\n" +
                "shop buy Blue_Eyes White Dragon\n" +
                "shop buy Slot Machine\n" +
                "shop buy Haniwa\n" +
                "shop buy Man_Eater Bug\n" +
                "shop buy Gate Guardian\n" +
                "shop buy bitron\n" +
                "shop buy Marshmallon\n" +
                "shop buy Beast King Barbaros\n" +
                "shop buy Leotron\n" +
                "shop buy The Calculator\n" +
                "shop buy Alexandrite Dragon\n" +
                "shop buy Mirage Dragon\n" +
                "shop buy Exploder Dragon\n" +
                "shop buy Warrior Dai Grepher\n" +
                "shop buy Dark Blade\n" +
                "shop buy Wattaildragon\n" +
                "shop buy Terratiger, the Empowered Warrior\n" +
                "shop buy The Tricky\n" +
                "shop buy Spiral Serpent\n" +
                "menu exit\n" +
                "menu enter deck\n" +
                "deck create testDeck\n" +
                "deck set-activate testDeck\n" +
                "deck add-card --card Magic Cylinder --deck testDeck\n" +
                "deck add-card --card Warrior Dai Grepher --deck testDeck\n" +
                "deck add-card --card Horn IMp --deck testDeck\n" +
                "deck add-card --card yomi ship --deck testDeck\n" +
                "deck add-card --card Time Seal --deck testDeck\n" +
                "deck add-card --card Magic Jammer --deck testDeck\n" +
                "deck add-card --card Call of The Haunted --deck testDeck\n" +
                "deck add-card --card Slot Machine --deck testDeck\n" +
                "deck add-card --card Haniwa --deck testDeck\n" +
                "deck add-card --card Exploder Dragon --deck testDeck\n" +
                "deck add-card --card Mind Crush --deck testDeck\n" +
                "deck add-card --card Negate Attack --deck testDeck\n" +
                "deck add-card --card Alexandrite Dragon --deck testDeck\n" +
                "deck add-card --card Mirage Dragon --deck testDeck\n" +
                "deck add-card --card Feral Imp --deck testDeck\n" +
                "deck add-card --card Torrential Tribute --deck testDeck\n" +
                "deck add-card --card command knight --deck testDeck\n" +
                "deck add-card --card Dark Magician --deck testDeck\n" +
                "deck add-card --card Wattkid --deck testDeck\n" +
                "deck add-card --card Baby Dragon --deck testDeck\n" +
                "deck add-card --card Marshmallon --deck testDeck\n" +
                "deck add-card --card Beast King Barbaros --deck testDeck\n" +
                "deck add-card --card Leotron --deck testDeck\n" +
                "deck add-card --card The Calculator --deck testDeck\n" +
                "deck add-card --card Baby Dragon --deck testDeck\n" +
                "deck add-card --card Hero of the East --deck testDeck\n" +
                "deck add-card --card Crawling dragon --deck testDeck\n" +
                "deck add-card --card Flame Manipulator --deck testDeck\n" +
                "deck add-card --card Blue_Eyes White Dragon --deck testDeck\n" +
                "deck add-card --card Terratiger, the Empowered Warrior --deck testDeck\n" +
                "deck add-card --card The Tricky --deck testDeck\n" +
                "deck add-card --card Spiral Serpent --deck testDeck\n" +
                "deck add-card --card Scanner --deck testDeck\n" +
                "deck add-card --card suijin --deck testDeck\n" +
                "deck add-card --card fireyarou --deck testDeck\n" +
                "deck add-card --card Curtain of the Dark Ones --deck testDeck\n" +
                "deck add-card --card Man_Eater Bug --deck testDeck\n" +
                "deck add-card --card Gate Guardian --deck testDeck\n" +
                "deck add-card --card bitron --deck testDeck\n" +
                "deck add-card --card Dark Blade --deck testDeck\n" +
                "deck add-card --card Wattaildragon --deck testDeck\n" +
                "menu exit\n" +
                "menu exit\n" +
                "user login --password 1234 --username homaan\n" +
                "menu enter shop\n" +
                "shop buy Magic Cylinder\n" +
                "shop buy Call of The Haunted\n" +
                "shop buy Time Seal\n" +
                "shop buy Magic Jammer\n" +
                "shop buy Mind Crush\n" +
                "shop buy Negate Attack\n" +
                "shop buy Torrential Tribute\n" +
                "shop buy command knight\n" +
                "shop buy Scanner\n" +
                "shop buy Horn IMp\n" +
                "shop buy yomi ship\n" +
                "shop buy suijin\n" +
                "shop buy fireyarou\n" +
                "shop buy Curtain of the Dark Ones\n" +
                "shop buy Feral Imp\n" +
                "shop buy Dark Magician\n" +
                "shop buy Wattkid\n" +
                "shop buy Baby Dragon\n" +
                "shop buy Hero of the East\n" +
                "shop buy Crawling dragon\n" +
                "shop buy Flame Manipulator\n" +
                "shop buy Blue_Eyes White Dragon\n" +
                "shop buy Slot Machine\n" +
                "shop buy Haniwa\n" +
                "shop buy Man_Eater Bug\n" +
                "shop buy Gate Guardian\n" +
                "shop buy bitron\n" +
                "shop buy Marshmallon\n" +
                "shop buy Beast King Barbaros\n" +
                "shop buy Leotron\n" +
                "shop buy The Calculator\n" +
                "shop buy Alexandrite Dragon\n" +
                "shop buy Mirage Dragon\n" +
                "shop buy Exploder Dragon\n" +
                "shop buy Warrior Dai Grepher\n" +
                "shop buy Dark Blade\n" +
                "shop buy Wattaildragon\n" +
                "shop buy Terratiger, the Empowered Warrior\n" +
                "shop buy The Tricky\n" +
                "shop buy Spiral Serpent\n" +
                "menu exit\n" +
                "menu enter deck\n" +
                "deck create testDeck\n" +
                "deck set-activate testDeck\n" +
                "deck add-card --card Magic Cylinder --deck testDeck\n" +
                "deck add-card --card Call of The Haunted --deck testDeck\n" +
                "deck add-card --card Time Seal --deck testDeck\n" +
                "deck add-card --card bitron --deck testDeck\n" +
                "deck add-card --card Marshmallon --deck testDeck\n" +
                "deck add-card --card Beast King Barbaros --deck testDeck\n" +
                "deck add-card --card Leotron --deck testDeck\n" +
                "deck add-card --card The Calculator --deck testDeck\n" +
                "deck add-card --card Alexandrite Dragon --deck testDeck\n" +
                "deck add-card --card Curtain of the Dark Ones --deck testDeck\n" +
                "deck add-card --card Feral Imp --deck testDeck\n" +
                "deck add-card --card Dark Magician --deck testDeck\n" +
                "deck add-card --card Wattkid --deck testDeck\n" +
                "deck add-card --card Baby Dragon --deck testDeck\n" +
                "deck add-card --card Baby Dragon --deck testDeck\n" +
                "deck add-card --card Hero of the East --deck testDeck\n" +
                "deck add-card --card Crawling dragon --deck testDeck\n" +
                "deck add-card --card Flame Manipulator --deck testDeck\n" +
                "deck add-card --card Blue_Eyes White Dragon --deck testDeck\n" +
                "deck add-card --card Slot Machine --deck testDeck\n" +
                "deck add-card --card Mind Crush --deck testDeck\n" +
                "deck add-card --card Negate Attack --deck testDeck\n" +
                "deck add-card --card Torrential Tribute --deck testDeck\n" +
                "deck add-card --card command knight --deck testDeck\n" +
                "deck add-card --card Horn IMp --deck testDeck\n" +
                "deck add-card --card Haniwa --deck testDeck\n" +
                "deck add-card --card Magic Jammer --deck testDeck\n" +
                "deck add-card --card Scanner --deck testDeck\n" +
                "deck add-card --card yomi ship --deck testDeck\n" +
                "deck add-card --card suijin --deck testDeck\n" +
                "deck add-card --card fireyarou --deck testDeck\n" +
                "deck add-card --card Man_Eater Bug --deck testDeck\n" +
                "deck add-card --card Warrior Dai Grepher --deck testDeck\n" +
                "deck add-card --card Dark Blade --deck testDeck\n" +
                "deck add-card --card Wattaildragon --deck testDeck\n" +
                "deck add-card --card Terratiger, the Empowered Warrior --deck testDeck\n" +
                "deck add-card --card Gate Guardian --deck testDeck\n" +
                "deck add-card --card Mirage Dragon --deck testDeck\n" +
                "deck add-card --card Exploder Dragon --deck testDeck\n" +
                "deck add-card --card The Tricky --deck testDeck\n" +
                "deck add-card --card Spiral Serpent --deck testDeck\n" +
                "menu exit\n" +
                "menu enter duel\n" +
                "duel --second-player nima --new --rounds 1\n" +
                "paper\n" +
                "stone\n" +
                "select --hand 1\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "set\n" +
                "select --hand 1\n" +
                "set\n" +
                "select --hand 1\n" +
                "select --hand \n" +
                "summon\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "set\n" +
                "select --hand 3\n" +
                "summon\n" +
                "select --hand 1\n" +
                "select --hand 5\n" +
                "select --hand 16\n" +
                "select --hand 1\n" +
                "summon\n" +
                "next phase\n" +
                "summon\n" +
                "select --monster 1\n" +
                "select --monster 5\n" +
                "attack 5\n" +
                "YES\n" +
                "select --monster 1\n" +
                "select --spell 2\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 2\n" +
                "summon\n" +
                "select --hand 1\n" +
                "set\n" +
                "next phase\n" +
                "select --monster 3\n" +
                "card selected\n" +
                "attack direct\n" +
                "select --monster 5\n" +
                "attack direct \n" +
                "select --monster 5\n" +
                "attack 5\n" +
                "YES\n" +
                "YES\n" +
                "select --monster 5\n" +
                "attack 1\n" +
                "attack 5\n" +
                "select --monster 3\n" +
                "attack 10\n" +
                "attack 1\n" +
                "attck 5\n" +
                "attack 5\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "summon\n" +
                "select --hand 2\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "summon\n" +
                "YES\n" +
                "select --hand 1\n" +
                "summon\n" +
                "select --hand 2\n" +
                "set\n" +
                "select --hand 2\n" +
                "set\n" +
                "select --hand 2\n" +
                "set\n" +
                "select --hand 2\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "summon\n" +
                "YES\n" +
                "select --hand 1\n" +
                "summon\n" +
                "select --hand 2\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "summon\n" +
                "next phase\n" +
                "select --monster 1\n" +
                "attack 2\n" +
                "attack 1\n" +
                "select --monster 3\n" +
                "attack 5\n" +
                "YES\n" +
                "1\n" +
                "map\n" +
                "select --monster 1\n" +
                "attack 5\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "select --hand 2\n" +
                "summon\n" +
                "next phase\n" +
                "next phase\n" +
                "select --monster --opponent 3\n"+
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "summon\n" +
                "1\n" +
                "2\n" +
                "summon\n" +
                "select --hand 1\n" +
                "summon\n" +
                "1\n" +
                "5\n" +
                "next phase\n" +
                "select --monster 5\n" +
                "attack 2\n" +
                "attack 5\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "select --hand 2\n" +
                "summon\n" +
                "YES\n" +
                "3\n" +
                "next phase\n" +
                "select --monster 2\n" +
                "attack 3\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "summon\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 2\n" +
                "summon\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "summon\n" +
                "select --monster 5\n" +
                "set --position attack\n" +
                "set --position defense\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --monster 2\n" +
                "select --monster 5\n" +
                "select --monster 4\n" +
                "attack 5\n" +
                "attack 3\n" +
                "select --monster 4\n" +
                "select --monster 1\n" +
                "attack 3\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "set\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 2\n" +
                "select --hand 1\n" +
                "summon\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 3\n" +
                "summon\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "attack 5\n" +
                "attack 2\n" +
                "select --monster 1\n" +
                "attack 5\n" +
                "select --monster 5\n" +
                "attack 2\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 2\n" +
                "set\n" +
                "select --hand 1\n" +
                "set\n" +
                "next phase\n" +
                "select --monster 1\n" +
                "attack 2\n" +
                "select --monster 5\n" +
                "attack 5\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "summon\n" +
                "YES\n" +
                "1 2 3\n" +
                "NO\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --monster 5\n" +
                "set --position attack\n" +
                "next phase\n" +
                "attack 5\n" +
                "select --monster 5\n" +
                "attack 5\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 6\n" +
                "summon\n" +
                "5\n" +
                "NO\n" +
                "YES\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 3\n" +
                "summon\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 6\n" +
                "summon\n" +
                "NO\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 3\n" +
                "summon\n" +
                "select --hand 1\n" +
                "summon\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 6\n" +
                "summon\n" +
                "select --hand 1\n" +
                "summon\n" +
                "NO\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "summon\n" +
                "next phase\n" +
                "select --monster 5\n" +
                "attack 5\n" +
                "select --monster 3\n" +
                "attack 3\n" +
                "select --monster 1\n" +
                "attack direct\n" +
                "menu exit\n" + "menu exit\n" + "menu exit\n" + "menu exit").getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        ViewMaster.setScanner(new Scanner(System.in));
        ViewMaster.getViewMaster().run();
        Assert.assertTrue(User.getUserByUsername("nima").getWinNum() == 1);
    }
    @Test
    public void TTest() {
        InputStream sysInBackup = System.in;
        User.getAllUsers().clear();
        ViewMaster.setCurrentMenu(Menu.LOGIN_MENU);
        ByteArrayInputStream in = new ByteArrayInputStream((
                "user create --u nima --nickname Nima --p 1234\n" +
                        "user create --nickname Hooman --username homaan --password 1234\n" +
                        "user login --password 1234 --username nima\n" +
                        "menu enter shop\n" +
                        "shop buy Magic Cylinder\n" +
                        "shop buy Call of The Haunted\n" +
                        "shop buy Time Seal\n" +
                        "shop buy Magic Jammer\n" +
                        "shop buy Mind Crush\n" +
                        "shop buy Negate Attack\n" +
                        "shop buy Torrential Tribute\n" +
                        "shop buy Scanner\n" +
                        "shop buy command knight\n" +
                        "shop buy Horn IMp\n" +
                        "shop buy yomi ship\n" +
                        "shop buy Yami\n" +
                        "shop buy Forest\n" +
                        "shop buy Closed Forest\n" +
                        "shop buy UMIIRUKA\n" +
                        "shop buy suijin\n" +
                        "shop buy fireyarou\n" +
                        "shop buy Curtain of the Dark Ones\n" +
                        "shop buy Feral Imp\n" +
                        "shop buy Dark Magician\n" +
                        "shop buy Wattkid\n" +
                        "shop buy Baby Dragon\n" +
                        "shop buy Hero of the East\n" +
                        "shop buy Crawling dragon\n" +
                        "shop buy Flame Manipulator\n" +
                        "shop buy Blue_Eyes White Dragon\n" +
                        "shop buy Slot Machine\n" +
                        "shop buy Haniwa\n" +
                        "shop buy Man_Eater Bug\n" +
                        "shop buy Battle Ox\n" +
                        "shop buy Silver Fang\n" +
                        "shop buy Curtain of Dark Ones\n" +
                        "shop buy Dark Magician\n" +
                        "shop buy Sword of Dark Destruction\n" +
                        "shop buy Flame Manipulator\n" +
                        "shop buy Gate Guardian\n" +
                        "shop buy bitron\n" +
                        "shop buy Herald of Creation\n" +
                        "shop buy Marshmallon\n" +
                        "shop buy Beast King Barbaros\n" +
                        "shop buy Leotron\n" +
                        "shop buy Dark Hole\n" +
                        "shop buy Black Pendant\n" +
                        "shop buy United We Stand\n" +
                        "shop buy The Calculator\n" +
                        "shop buy Alexandrite Dragon\n" +
                        "shop buy Raigeki\n" +
                        "shop buy Mystical space typhoon\n" +
                        "shop buy Pot of Greed\n" +
                        "shop buy Mirage Dragon\n" +
                        "shop buy Exploder Dragon\n" +
                        "shop buy Harpie's Feather Duster\n" +
                        "shop buy Warrior Dai Grepher\n" +
                        "shop buy Dark Blade\n" +
                        "shop buy Wattaildragon\n" +
                        "shop buy Twin Twisters\n" +
                        "shop buy Terratiger, the Empowered Warrior\n" +
                        "shop buy The Tricky\n" +
                        "shop buy Spiral Serpent\n" +
                        "menu exit\n" +
                        "menu enter deck\n" +
                        "deck create testDeck\n" +
                        "deck set-activate testDeck\n" +
                        "deck add-card --card Horn IMp --deck testDeck\n" +
                        "deck add-card --card Pot of Greed --deck testDeck\n" +
                        "deck add-card --card command knight --deck testDeck\n" +
                        "deck add-card --card Exploder Dragon --deck testDeck\n" +
                        "deck add-card --card Sword of Dark Destruction --deck testDeck\n" +
                        "deck add-card --card Dark Hole --deck testDeck\n" +
                        "deck add-card --card UMIIRUKA --deck testDeck\n" +
                        "deck add-card --card Closed Forest --deck testDeck\n" +
                        "deck add-card --card yomi ship --deck testDeck\n" +
                        "deck add-card --card The Calculator --deck testDeck\n" +
                        "deck add-card --card Forest --deck testDeck\n" +
                        "deck add-card --card Yami --deck testDeck\n" +
                        "deck add-card --card suijin --deck testDeck\n" +
                        "deck add-card --card Marshmallon --deck testDeck\n" +
                        "deck add-card --card Negate Attack --deck testDeck\n" +
                        "deck add-card --card Torrential Tribute --deck testDeck\n" +
                        "deck add-card --card fireyarou --deck testDeck\n" +
                        "deck add-card --card Curtain of the Dark Ones --deck testDeck\n" +
                        "deck add-card --card Feral Imp --deck testDeck\n" +
                        "deck add-card --card Dark Magician --deck testDeck\n" +
                        "deck add-card --card Wattkid --deck testDeck\n" +
                        "deck add-card --card Baby Dragon --deck testDeck\n" +
                        "deck add-card --card Baby Dragon --deck testDeck\n" +
                        "deck add-card --card Hero of the East --deck testDeck\n" +
                        "deck add-card --card Crawling dragon --deck testDeck\n" +
                        "deck add-card --card Flame Manipulator --deck testDeck\n" +
                        "deck add-card --card Blue_Eyes White Dragon --deck testDeck\n" +
                        "deck add-card --card Slot Machine --deck testDeck\n" +
                        "deck add-card --card Haniwa --deck testDeck\n" +
                        "deck add-card --card Man_Eater Bug --deck testDeck\n" +
                        "deck add-card --card Gate Guardian --deck testDeck\n" +
                        "deck add-card --card bitron --deck testDeck\n" +
                        "deck add-card --card Beast King Barbaros --deck testDeck\n" +
                        "deck add-card --card Leotron --deck testDeck\n" +
                        "deck add-card --card Alexandrite Dragon --deck testDeck\n" +
                        "deck add-card --card Warrior Dai Grepher --deck testDeck\n" +
                        "deck add-card --card Dark Blade --deck testDeck\n" +
                        "deck add-card --card Wattaildragon --deck testDeck\n" +
                        "deck add-card --card Mirage Dragon --deck testDeck\n" +
                        "deck add-card --card Terratiger, the Empowered Warrior --deck testDeck\n" +
                        "deck add-card --card The Tricky --deck testDeck\n" +
                        "deck add-card --card Spiral Serpent --deck testDeck\n" +
                        "menu exit\n" +
                        "menu exit\n" +
                        "user login --password 1234 --username homaan\n" +
                        "menu enter shop\n" +
                        "shop buy Yami\n" +
                        "shop buy Forest\n" +
                        "shop buy Closed Forest\n" +
                        "shop buy UMIIRUKA\n" +
                        "shop buy Torrential Tribute\n" +
                        "shop buy command knight\n" +
                        "shop buy Scanner\n" +
                        "shop buy Horn IMp\n" +
                        "shop buy yomi ship\n" +
                        "shop buy suijin\n" +
                        "shop buy fireyarou\n" +
                        "shop buy Curtain of the Dark Ones\n" +
                        "shop buy Feral Imp\n" +
                        "shop buy Dark Magician\n" +
                        "shop buy Wattkid\n" +
                        "shop buy Baby Dragon\n" +
                        "shop buy Hero of the East\n" +
                        "shop buy Crawling dragon\n" +
                        "shop buy Flame Manipulator\n" +
                        "shop buy Blue_Eyes White Dragon\n" +
                        "shop buy Slot Machine\n" +
                        "shop buy Haniwa\n" +
                        "shop buy Man_Eater Bug\n" +
                        "shop buy Harpie's Feather Duster\n" +
                        "shop buy Battle Ox\n" +
                        "shop buy Silver Fang\n" +
                        "shop buy Mystical space typhoon\n" +
                        "shop buy Sword of Dark Destruction\n" +
                        "shop buy Curtain of Dark Ones\n" +
                        "shop buy Dark Magician\n" +
                        "shop buy Flame Manipulator\n" +
                        "shop buy Gate Guardian\n" +
                        "shop buy bitron\n" +
                        "shop buy Marshmallon\n" +
                        "shop buy Herald of Creation\n" +
                        "shop buy Beast King Barbaros\n" +
                        "shop buy Leotron\n" +
                        "shop buy Twin Twisters\n" +
                        "shop buy Black Pendant\n" +
                        "shop buy The Calculator\n" +
                        "shop buy Alexandrite Dragon\n" +
                        "shop buy Mirage Dragon\n" +
                        "shop buy Dark Hole\n" +
                        "shop buy Exploder Dragon\n" +
                        "shop buy United We Stand\n" +
                        "shop buy Warrior Dai Grepher\n" +
                        "shop buy raigeki\n" +
                        "shop buy Dark Blade\n" +
                        "shop buy Pot of Greed\n" +
                        "shop buy Magnum Shield\n" +
                        "shop buy Wattaildragon\n" +
                        "shop buy Axe Raider\n" +
                        "shop buy Terratiger, the Empowered Warrior\n" +
                        "shop buy The Tricky\n" +
                        "shop buy Spiral Serpent\n" +
                        "menu exit\n" +
                        "menu enter deck\n" +
                        "deck create testDeck\n" +
                        "deck set-activate testDeck\n" +
                        "deck add-card --card Pot of Greed --deck testDeck\n" +
                        "deck add-card --card Twin Twisters --deck testDeck\n" +
                        "deck add-card --card Mystical space typhoon --deck testDeck\n" +
                        "deck add-card --card command knight --deck testDeck\n" +
                        "deck add-card --card Yami --deck testDeck\n" +
                        "deck add-card --card yomi ship --deck testDeck\n" +
                        "deck add-card --card Horn IMp --deck testDeck\n" +
                        "deck add-card --card Exploder Dragon --deck testDeck\n" +
                        "deck add-card --card Black Pendant --deck testDeck\n" +
                        "deck add-card --card United We Stand --deck testDeck\n" +
                        "deck add-card --card Magnum Shield--deck testDeck\n" +
                        "deck add-card --card Sword of Dark Destruction --deck testDeck\n" +
                        "deck add-card --card Closed Forest --deck testDeck\n" +
                        "deck add-card --card Dark Hole --deck testDeck\n" +
                        "deck add-card --card UMIIRUKA --deck testDeck\n" +
                        "deck add-card --card Forest --deck testDeck\n" +
                        "deck add-card --card Raigeki --deck testDeck\n" +
                        "deck add-card --card Harpie's Feather Duster --deck testDeck\n" +
                        "deck add-card --card Dark Hole --deck testDeck\n" +
                        "deck add-card --card Flame Manipulator --deck testDeck\n" +
                        "deck add-card --card suijin --deck testDeck\n" +
                        "deck add-card --card The Calculator --deck testDeck\n" +
                        "deck add-card --card Marshmallon --deck testDeck\n" +
                        "deck add-card --card Silver Fang --deck testDeck\n" +
                        "deck add-card --card bitron --deck testDeck\n" +
                        "deck add-card --card Torrential Tribute --deck testDeck\n" +
                        "deck add-card --card bitron --deck testDeck\n" +
                        "deck add-card --card fireyarou --deck testDeck\n" +
                        "deck add-card --card Feral Imp --deck testDeck\n" +
                        "deck add-card --card Dark Magician --deck testDeck\n" +
                        "deck add-card --card Wattkid --deck testDeck\n" +
                        "deck add-card --card Baby Dragon --deck testDeck\n" +
                        "deck add-card --card Baby Dragon --deck testDeck\n" +
                        "deck add-card --card Hero of the East --deck testDeck\n" +
                        "deck add-card --card Horn IMp --deck testDeck\n" +
                        "deck add-card --card Crawling dragon --deck testDeck\n" +
                        "deck add-card --card Blue_Eyes White Dragon --deck testDeck\n" +
                        "deck add-card --card Slot Machine --deck testDeck\n" +
                        "deck add-card --card Haniwa --deck testDeck\n" +
                        "deck add-card --card Gate Guardian --deck testDeck\n" +
                        "deck add-card --card bitron --deck testDeck\n" +
                        "deck add-card --card Beast King Barbaros --deck testDeck\n" +
                        "deck add-card --card Leotron --deck testDeck\n" +
                        "deck add-card --card Alexandrite Dragon --deck testDeck\n" +
                        "deck add-card --card Mirage Dragon --deck testDeck\n" +
                        "deck add-card --card Exploder Dragon --deck testDeck\n" +
                        "deck add-card --card Warrior Dai Grepher --deck testDeck\n" +
                        "deck add-card --card Dark Blade --deck testDeck\n" +
                        "deck add-card --card Wattaildragon --deck testDeck\n" +
                        "deck add-card --card Terratiger, the Empowered Warrior --deck testDeck\n" +
                        "deck add-card --card The Tricky --deck testDeck\n" +
                        "deck add-card --card Spiral Serpent --deck testDeck\n" +
                        "menu exit\n" +
                        "menu enter duel\n" +
                        "duel --second-player nima --new --rounds 1\n" +
                        "paper\n" +
                        "stone\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "select --hand 1\n" +
                        "active effect \n" +
                        "select --hand 4\n" +
                        "active effect\n" +
                        "select --hand 5\n" +
                        "summon\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "select --hand 3\n" +
                        "summon\n" +
                        "select --hand 5\n" +
                        "active effect\n" +
                        "select --hand 2\n" +
                        "set\n" +
                        "select --hand 3\n" +
                        "set\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "select --hand 2\n" +
                        "active effect\n" +
                        "5\n" +
                        "select --hand 1\n" +
                        "active effect\n" +
                        "3\n" +
                        "3\n" +
                        "abort\n" +
                        "surrender\n"+
                        "menu exit\n"+
                        "menu exit\n"+
                        "menu exit\n"
        ).getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        ViewMaster.setScanner(new Scanner(System.in));
        ViewMaster.getViewMaster().run();
        Assert.assertTrue(User.getUserByUsername("nima").getWinNum() == 1);
    }
    @Test
    public void lioghTest() {
        InputStream sysInBackup = System.in;
        User.getAllUsers().clear();
        ViewMaster.setCurrentMenu(Menu.LOGIN_MENU);
        ByteArrayInputStream in = new ByteArrayInputStream((
        "user create --u nima --nickname Nima --p 1234\n" +
                "user create --nickname Hooman --username homaan --password 1234\n" +
                "user login --password 1234 --username nima\n" +
                "menu enter shop\n" +
                "shop buy Magic Cylinder\n" +
                "shop buy Call of The Haunted\n" +
                "shop buy Time Seal\n" +
                "shop buy Magic Jammer\n" +
                "shop buy Mind Crush\n" +
                "shop buy Negate Attack\n" +
                "shop buy Torrential Tribute\n" +
                "shop buy Scanner\n" +
                "shop buy command knight\n" +
                "shop buy Horn IMp\n" +
                "shop buy yomi ship\n" +
                "shop buy Yami\n" +
                "shop buy Forest\n" +
                "shop buy Closed Forest\n" +
                "shop buy UMIIRUKA\n" +
                "shop buy suijin\n" +
                "shop buy fireyarou\n" +
                "shop buy Curtain of the Dark Ones\n" +
                "shop buy Feral Imp\n" +
                "shop buy Dark Magician\n" +
                "shop buy Wattkid\n" +
                "shop buy Baby Dragon\n" +
                "shop buy Hero of the East\n" +
                "shop buy Crawling dragon\n" +
                "shop buy Flame Manipulator\n" +
                "shop buy Blue_Eyes White Dragon\n" +
                "shop buy Slot Machine\n" +
                "shop buy Haniwa\n" +
                "shop buy Man_Eater Bug\n" +
                "shop buy Battle Ox\n" +
                "shop buy Silver Fang\n" +
                "shop buy Curtain of Dark Ones\n" +
                "shop buy Dark Magician\n" +
                "shop buy Sword of Dark Destruction\n" +
                "shop buy Flame Manipulator\n" +
                "shop buy Gate Guardian\n" +
                "shop buy bitron\n" +
                "shop buy Herald of Creation\n" +
                "shop buy Marshmallon\n" +
                "shop buy Beast King Barbaros\n" +
                "shop buy Leotron\n" +
                "shop buy Dark Hole\n" +
                "shop buy Black Pendant\n" +
                "shop buy United We Stand\n" +
                "shop buy The Calculator\n" +
                "shop buy Alexandrite Dragon\n" +
                "shop buy Raigeki\n" +
                "shop buy Mystical space typhoon\n" +
                "shop buy Pot of Greed\n" +
                "shop buy Mirage Dragon\n" +
                "shop buy Exploder Dragon\n" +
                "shop buy Harpie's Feather Duster\n" +
                "shop buy Warrior Dai Grepher\n" +
                "shop buy Dark Blade\n" +
                "shop buy Wattaildragon\n" +
                "shop buy Twin Twisters\n" +
                "shop buy Terratiger, the Empowered Warrior\n" +
                "shop buy The Tricky\n" +
                "shop buy Spiral Serpent\n" +
                "menu exit\n" +
                "menu enter deck\n" +
                "deck create testDeck\n" +
                "deck set-activate testDeck\n" +
                "deck add-card --card UMIIRUKA --deck testDeck\n" +
                "deck add-card --card Horn IMp --deck testDeck\n" +
                "deck add-card --card Pot of Greed --deck testDeck\n" +
                "deck add-card --card command knight --deck testDeck\n" +
                "deck add-card --card Exploder Dragon --deck testDeck\n" +
                "deck add-card --card Sword of Dark Destruction --deck testDeck\n" +
                "deck add-card --card Dark Hole --deck testDeck\n" +
                "deck add-card --card Closed Forest --deck testDeck\n" +
                "deck add-card --card yomi ship --deck testDeck\n" +
                "deck add-card --card The Calculator --deck testDeck\n" +
                "deck add-card --card Forest --deck testDeck\n" +
                "deck add-card --card Yami --deck testDeck\n" +
                "deck add-card --card suijin --deck testDeck\n" +
                "deck add-card --card Marshmallon --deck testDeck\n" +
                "deck add-card --card Negate Attack --deck testDeck\n" +
                "deck add-card --card Torrential Tribute --deck testDeck\n" +
                "deck add-card --card fireyarou --deck testDeck\n" +
                "deck add-card --card Curtain of the Dark Ones --deck testDeck\n" +
                "deck add-card --card Feral Imp --deck testDeck\n" +
                "deck add-card --card Dark Magician --deck testDeck\n" +
                "deck add-card --card Wattkid --deck testDeck\n" +
                "deck add-card --card Baby Dragon --deck testDeck\n" +
                "deck add-card --card Baby Dragon --deck testDeck\n" +
                "deck add-card --card Hero of the East --deck testDeck\n" +
                "deck add-card --card Crawling dragon --deck testDeck\n" +
                "deck add-card --card Flame Manipulator --deck testDeck\n" +
                "deck add-card --card Blue_Eyes White Dragon --deck testDeck\n" +
                "deck add-card --card Slot Machine --deck testDeck\n" +
                "deck add-card --card Haniwa --deck testDeck\n" +
                "deck add-card --card Man_Eater Bug --deck testDeck\n" +
                "deck add-card --card Gate Guardian --deck testDeck\n" +
                "deck add-card --card bitron --deck testDeck\n" +
                "deck add-card --card Beast King Barbaros --deck testDeck\n" +
                "deck add-card --card Leotron --deck testDeck\n" +
                "deck add-card --card Alexandrite Dragon --deck testDeck\n" +
                "deck add-card --card Warrior Dai Grepher --deck testDeck\n" +
                "deck add-card --card Dark Blade --deck testDeck\n" +
                "deck add-card --card Wattaildragon --deck testDeck\n" +
                "deck add-card --card Mirage Dragon --deck testDeck\n" +
                "deck add-card --card Terratiger, the Empowered Warrior --deck testDeck\n" +
                "deck add-card --card The Tricky --deck testDeck\n" +
                "deck add-card --card Spiral Serpent --deck testDeck\n" +
                "menu exit\n" +
                "menu exit\n" +
                "user login --password 1234 --username homaan\n" +
                "menu enter shop\n" +
                "shop buy Yami\n" +
                "shop buy Forest\n" +
                "shop buy Closed Forest\n" +
                "shop buy UMIIRUKA\n" +
                "shop buy Torrential Tribute\n" +
                "shop buy command knight\n" +
                "shop buy Scanner\n" +
                "shop buy Horn IMp\n" +
                "shop buy yomi ship\n" +
                "shop buy suijin\n" +
                "shop buy fireyarou\n" +
                "shop buy Curtain of the Dark Ones\n" +
                "shop buy Feral Imp\n" +
                "shop buy Dark Magician\n" +
                "shop buy Wattkid\n" +
                "shop buy Baby Dragon\n" +
                "shop buy Hero of the East\n" +
                "shop buy Crawling dragon\n" +
                "shop buy Flame Manipulator\n" +
                "shop buy Blue_Eyes White Dragon\n" +
                "shop buy Slot Machine\n" +
                "shop buy Haniwa\n" +
                "shop buy Man_Eater Bug\n" +
                "shop buy Harpie's Feather Duster\n" +
                "shop buy Battle Ox\n" +
                "shop buy Silver Fang\n" +
                "shop buy Mystical space typhoon\n" +
                "shop buy Sword of Dark Destruction\n" +
                "shop buy Curtain of Dark Ones\n" +
                "shop buy Dark Magician\n" +
                "shop buy Flame Manipulator\n" +
                "shop buy Gate Guardian\n" +
                "shop buy bitron\n" +
                "shop buy Marshmallon\n" +
                "shop buy Herald of Creation\n" +
                "shop buy Beast King Barbaros\n" +
                "shop buy Leotron\n" +
                "shop buy Twin Twisters\n" +
                "shop buy Black Pendant\n" +
                "shop buy The Calculator\n" +
                "shop buy Alexandrite Dragon\n" +
                "shop buy Mirage Dragon\n" +
                "shop buy Dark Hole\n" +
                "shop buy Exploder Dragon\n" +
                "shop buy United We Stand\n" +
                "shop buy Warrior Dai Grepher\n" +
                "shop buy raigeki\n" +
                "shop buy Dark Blade\n" +
                "shop buy Pot of Greed\n" +
                "shop buy Magnum Shield\n" +
                "shop buy Wattaildragon\n" +
                "shop buy Axe Raider\n" +
                "shop buy Terratiger, the Empowered Warrior\n" +
                "shop buy The Tricky\n" +
                "shop buy Spiral Serpent\n" +
                "menu exit\n" +
                "menu enter deck\n" +
                "deck create testDeck\n" +
                "deck set-activate testDeck\n" +
                "deck add-card --card Pot of Greed --deck testDeck\n" +
                "deck add-card --card Twin Twisters --deck testDeck\n" +
                "deck add-card --card The Tricky --deck testDeck\n" +
                "deck add-card --card Man_Eater Bug --deck testDeck\n" +
                "deck add-card --card Scanner --deck testDeck\n" +
                "deck add-card --card Terratiger, the Empowered Warrior --deck testDeck\n" +
                "deck add-card --card yomi ship --deck testDeck\n" +
                "deck add-card --card bitron --deck testDeck\n" +
                "deck add-card --card Harpie's Feather Duster --deck testDeck\n" +
                "deck add-card --card Mystical space typhoon --deck testDeck\n" +
                "deck add-card --card command knight --deck testDeck\n" +
                "deck add-card --card Yami --deck testDeck\n" +
                "deck add-card --card Horn IMp --deck testDeck\n" +
                "deck add-card --card Exploder Dragon --deck testDeck\n" +
                "deck add-card --card Black Pendant --deck testDeck\n" +
                "deck add-card --card United We Stand --deck testDeck\n" +
                "deck add-card --card Magnum Shield--deck testDeck\n" +
                "deck add-card --card Sword of Dark Destruction --deck testDeck\n" +
                "deck add-card --card Closed Forest --deck testDeck\n" +
                "deck add-card --card Dark Hole --deck testDeck\n" +
                "deck add-card --card UMIIRUKA --deck testDeck\n" +
                "deck add-card --card Forest --deck testDeck\n" +
                "deck add-card --card Raigeki --deck testDeck\n" +
                "deck add-card --card Dark Hole --deck testDeck\n" +
                "deck add-card --card Flame Manipulator --deck testDeck\n" +
                "deck add-card --card suijin --deck testDeck\n" +
                "deck add-card --card The Calculator --deck testDeck\n" +
                "deck add-card --card Marshmallon --deck testDeck\n" +
                "deck add-card --card Silver Fang --deck testDeck\n" +
                "deck add-card --card Torrential Tribute --deck testDeck\n" +
                "deck add-card --card bitron --deck testDeck\n" +
                "deck add-card --card fireyarou --deck testDeck\n" +
                "deck add-card --card Feral Imp --deck testDeck\n" +
                "deck add-card --card Dark Magician --deck testDeck\n" +
                "deck add-card --card Wattkid --deck testDeck\n" +
                "deck add-card --card Baby Dragon --deck testDeck\n" +
                "deck add-card --card Baby Dragon --deck testDeck\n" +
                "deck add-card --card Hero of the East --deck testDeck\n" +
                "deck add-card --card Horn IMp --deck testDeck\n" +
                "deck add-card --card Crawling dragon --deck testDeck\n" +
                "deck add-card --card Blue_Eyes White Dragon --deck testDeck\n" +
                "deck add-card --card Slot Machine --deck testDeck\n" +
                "deck add-card --card Haniwa --deck testDeck\n" +
                "deck add-card --card Gate Guardian --deck testDeck\n" +
                "deck add-card --card bitron --deck testDeck\n" +
                "deck add-card --card Beast King Barbaros --deck testDeck\n" +
                "deck add-card --card Leotron --deck testDeck\n" +
                "deck add-card --card Alexandrite Dragon --deck testDeck\n" +
                "deck add-card --card Mirage Dragon --deck testDeck\n" +
                "deck add-card --card Exploder Dragon --deck testDeck\n" +
                "deck add-card --card Warrior Dai Grepher --deck testDeck\n" +
                "deck add-card --card Dark Blade --deck testDeck\n" +
                "deck add-card --card Wattaildragon --deck testDeck\n" +
                "deck add-card --card Terratiger, the Empowered Warrior --deck testDeck\n" +
                "deck add-card --card Spiral Serpent --deck testDeck\n" +
                "menu exit\n" +
                "menu enter duel\n" +
                "duel --second-player nima --new --rounds 1\n" +
                "paper\n" +
                "stone\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "active effect\n" +
                "select --hand 2\n" +
                "special summon\n" +
                "6\n" +
                "a\n" +
                "Attack\n" +
                "select --hand 5\n" +
                "set\n" +
                "select --hand 1\n" +
                "set\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 2\n" +
                "summon\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "set\n" +
                "select --monster 3\n" +
                "flip-summon\n" +
                "YES\n" +
                "5\n" +
                "select --spell 5\n" +
                "active effect\n" +
                "surrender\n" +
                "menu exit\n" +
                "menu exit\n" +
                "menu exit"
        ).getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        ViewMaster.setScanner(new Scanner(System.in));
        ViewMaster.getViewMaster().run();
        Assert.assertTrue(User.getUserByUsername("nima").getWinNum() == 1);
    }
    @Test
    public void spellTest() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        User.getAllUsers().clear();
        ViewMaster.setCurrentMenu(Menu.LOGIN_MENU);
        ByteArrayInputStream in = new ByteArrayInputStream((
                "user create --u nima --nickname Nima --p 1234\n" +
                        "user create --nickname Hooman --username homaan --password 1234\n" +
                        "user login --password 1234 --username nima\n" +
                        "menu enter shop\n" +
                        "shop buy Magic Cylinder\n" +
                        "shop buy Call of The Haunted\n" +
                        "shop buy Time Seal\n" +
                        "shop buy Magic Jammer\n" +
                        "shop buy Mind Crush\n" +
                        "shop buy Negate Attack\n" +
                        "shop buy Torrential Tribute\n" +
                        "shop buy Scanner\n" +
                        "shop buy command knight\n" +
                        "shop buy Horn IMp\n" +
                        "shop buy yomi ship\n" +
                        "shop buy Yami\n" +
                        "shop buy Forest\n" +
                        "shop buy Closed Forest\n" +
                        "shop buy UMIIRUKA\n" +
                        "shop buy suijin\n" +
                        "shop buy fireyarou\n" +
                        "shop buy Curtain of the Dark Ones\n" +
                        "shop buy Feral Imp\n" +
                        "shop buy Dark Magician\n" +
                        "shop buy Wattkid\n" +
                        "shop buy Baby Dragon\n" +
                        "shop buy Hero of the East\n" +
                        "shop buy Crawling dragon\n" +
                        "shop buy Flame Manipulator\n" +
                        "shop buy Blue_Eyes White Dragon\n" +
                        "shop buy Slot Machine\n" +
                        "shop buy Haniwa\n" +
                        "shop buy Man_Eater Bug\n" +
                        "shop buy Battle Ox\n" +
                        "shop buy Silver Fang\n" +
                        "shop buy Curtain of Dark Ones\n" +
                        "shop buy Dark Magician\n" +
                        "shop buy Sword of Dark Destruction\n" +
                        "shop buy Flame Manipulator\n" +
                        "shop buy Gate Guardian\n" +
                        "shop buy bitron\n" +
                        "shop buy Herald of Creation\n" +
                        "shop buy Marshmallon\n" +
                        "shop buy Beast King Barbaros\n" +
                        "shop buy Leotron\n" +
                        "shop buy Dark Hole\n" +
                        "shop buy Black Pendant\n" +
                        "shop buy United We Stand\n" +
                        "shop buy The Calculator\n" +
                        "shop buy Alexandrite Dragon\n" +
                        "shop buy Raigeki\n" +
                        "shop buy Mystical space typhoon\n" +
                        "shop buy Pot of Greed\n" +
                        "shop buy Mirage Dragon\n" +
                        "shop buy Exploder Dragon\n" +
                        "shop buy Harpie's Feather Duster\n" +
                        "shop buy Warrior Dai Grepher\n" +
                        "shop buy Dark Blade\n" +
                        "shop buy Wattaildragon\n" +
                        "shop buy Twin Twisters\n" +
                        "shop buy Terratiger, the Empowered Warrior\n" +
                        "shop buy The Tricky\n" +
                        "shop buy Spiral Serpent\n" +
                        "menu exit\n" +
                        "menu enter deck\n" +
                        "deck create testDeck\n" +
                        "deck set-activate testDeck\n" +
                        "deck add-card --card Horn IMp --deck testDeck\n" +
                        "deck add-card --card Pot of Greed --deck testDeck\n" +
                        "deck add-card --card command knight --deck testDeck\n" +
                        "deck add-card --card Exploder Dragon --deck testDeck\n" +
                        "deck add-card --card Sword of Dark Destruction --deck testDeck\n" +
                        "deck add-card --card Dark Hole --deck testDeck\n" +
                        "deck add-card --card UMIIRUKA --deck testDeck\n" +
                        "deck add-card --card Closed Forest --deck testDeck\n" +
                        "deck add-card --card yomi ship --deck testDeck\n" +
                        "deck add-card --card The Calculator --deck testDeck\n" +
                        "deck add-card --card Forest --deck testDeck\n" +
                        "deck add-card --card Yami --deck testDeck\n" +
                        "deck add-card --card suijin --deck testDeck\n" +
                        "deck add-card --card Marshmallon --deck testDeck\n" +
                        "deck add-card --card Negate Attack --deck testDeck\n" +
                        "deck add-card --card Torrential Tribute --deck testDeck\n" +
                        "deck add-card --card fireyarou --deck testDeck\n" +
                        "deck add-card --card Curtain of the Dark Ones --deck testDeck\n" +
                        "deck add-card --card Feral Imp --deck testDeck\n" +
                        "deck add-card --card Dark Magician --deck testDeck\n" +
                        "deck add-card --card Wattkid --deck testDeck\n" +
                        "deck add-card --card Baby Dragon --deck testDeck\n" +
                        "deck add-card --card Baby Dragon --deck testDeck\n" +
                        "deck add-card --card Hero of the East --deck testDeck\n" +
                        "deck add-card --card Crawling dragon --deck testDeck\n" +
                        "deck add-card --card Flame Manipulator --deck testDeck\n" +
                        "deck add-card --card Blue_Eyes White Dragon --deck testDeck\n" +
                        "deck add-card --card Slot Machine --deck testDeck\n" +
                        "deck add-card --card Haniwa --deck testDeck\n" +
                        "deck add-card --card Man_Eater Bug --deck testDeck\n" +
                        "deck add-card --card Gate Guardian --deck testDeck\n" +
                        "deck add-card --card bitron --deck testDeck\n" +
                        "deck add-card --card Beast King Barbaros --deck testDeck\n" +
                        "deck add-card --card Leotron --deck testDeck\n" +
                        "deck add-card --card Alexandrite Dragon --deck testDeck\n" +
                        "deck add-card --card Warrior Dai Grepher --deck testDeck\n" +
                        "deck add-card --card Dark Blade --deck testDeck\n" +
                        "deck add-card --card Wattaildragon --deck testDeck\n" +
                        "deck add-card --card Mirage Dragon --deck testDeck\n" +
                        "deck add-card --card Terratiger, the Empowered Warrior --deck testDeck\n" +
                        "deck add-card --card The Tricky --deck testDeck\n" +
                        "deck add-card --card Spiral Serpent --deck testDeck\n" +
                        "menu exit\n" +
                        "menu exit\n" +
                        "user login --password 1234 --username homaan\n" +
                        "menu enter shop\n" +
                        "shop buy Yami\n" +
                        "shop buy Forest\n" +
                        "shop buy Closed Forest\n" +
                        "shop buy UMIIRUKA\n" +
                        "shop buy Torrential Tribute\n" +
                        "shop buy command knight\n" +
                        "shop buy Scanner\n" +
                        "shop buy Horn IMp\n" +
                        "shop buy yomi ship\n" +
                        "shop buy suijin\n" +
                        "shop buy fireyarou\n" +
                        "shop buy Curtain of the Dark Ones\n" +
                        "shop buy Feral Imp\n" +
                        "shop buy Dark Magician\n" +
                        "shop buy Wattkid\n" +
                        "shop buy Baby Dragon\n" +
                        "shop buy Hero of the East\n" +
                        "shop buy Crawling dragon\n" +
                        "shop buy Flame Manipulator\n" +
                        "shop buy Blue_Eyes White Dragon\n" +
                        "shop buy Slot Machine\n" +
                        "shop buy Haniwa\n" +
                        "shop buy Man_Eater Bug\n" +
                        "shop buy Harpie's Feather Duster\n" +
                        "shop buy Battle Ox\n" +
                        "shop buy Silver Fang\n" +
                        "shop buy Mystical space typhoon\n" +
                        "shop buy Sword of Dark Destruction\n" +
                        "shop buy Curtain of Dark Ones\n" +
                        "shop buy Dark Magician\n" +
                        "shop buy Flame Manipulator\n" +
                        "shop buy Gate Guardian\n" +
                        "shop buy bitron\n" +
                        "shop buy Marshmallon\n" +
                        "shop buy Herald of Creation\n" +
                        "shop buy Beast King Barbaros\n" +
                        "shop buy Leotron\n" +
                        "shop buy Twin Twisters\n" +
                        "shop buy Black Pendant\n" +
                        "shop buy The Calculator\n" +
                        "shop buy Alexandrite Dragon\n" +
                        "shop buy Mirage Dragon\n" +
                        "shop buy Dark Hole\n" +
                        "shop buy Exploder Dragon\n" +
                        "shop buy United We Stand\n" +
                        "shop buy Warrior Dai Grepher\n" +
                        "shop buy raigeki\n" +
                        "shop buy Dark Blade\n" +
                        "shop buy Pot of Greed\n" +
                        "shop buy Magnum Shield\n" +
                        "shop buy Wattaildragon\n" +
                        "shop buy Axe Raider\n" +
                        "shop buy Terratiger, the Empowered Warrior\n" +
                        "shop buy The Tricky\n" +
                        "shop buy Spiral Serpent\n" +
                        "menu exit\n" +
                        "menu enter deck\n" +
                        "deck create testDeck\n" +
                        "deck set-activate testDeck\n" +
                        "deck add-card --card Pot of Greed --deck testDeck\n" +
                        "deck add-card --card command knight --deck testDeck\n" +
                        "deck add-card --card Yami --deck testDeck\n" +
                        "deck add-card --card yomi ship --deck testDeck\n" +
                        "deck add-card --card Horn IMp --deck testDeck\n" +
                        "deck add-card --card Exploder Dragon --deck testDeck\n" +
                        "deck add-card --card Black Pendant --deck testDeck\n" +
                        "deck add-card --card United We Stand --deck testDeck\n" +
                        "deck add-card --card Magnum Shield--deck testDeck\n" +
                        "deck add-card --card Sword of Dark Destruction --deck testDeck\n" +
                        "deck add-card --card Closed Forest --deck testDeck\n" +
                        "deck add-card --card Dark Hole --deck testDeck\n" +
                        "deck add-card --card UMIIRUKA --deck testDeck\n" +
                        "deck add-card --card Forest --deck testDeck\n" +
                        "deck add-card --card Raigeki --deck testDeck\n" +
                        "deck add-card --card Harpie’s Feather Duster --deck testDeck\n" +
                        "deck add-card --Dark Hole --deck testDeck\n" +
                        "deck add-card --Twin Twister --deck testDeck\n" +
                        "deck add-card --Mystical space typhoon --deck testDeck\n" +
                        "deck add-card --card Flame Manipulator --deck testDeck\n" +
                        "deck add-card --card suijin --deck testDeck\n" +
                        "deck add-card --card The Calculator --deck testDeck\n" +
                        "deck add-card --card Marshmallon --deck testDeck\n" +
                        "deck add-card --card Silver Fang --deck testDeck\n" +
                        "deck add-card --card bitron --deck testDeck\n" +
                        "deck add-card --card Torrential Tribute --deck testDeck\n" +
                        "deck add-card --card bitron --deck testDeck\n" +
                        "deck add-card --card fireyarou --deck testDeck\n" +
                        "deck add-card --card Feral Imp --deck testDeck\n" +
                        "deck add-card --card Dark Magician --deck testDeck\n" +
                        "deck add-card --card Wattkid --deck testDeck\n" +
                        "deck add-card --card Baby Dragon --deck testDeck\n" +
                        "deck add-card --card Baby Dragon --deck testDeck\n" +
                        "deck add-card --card Hero of the East --deck testDeck\n" +
                        "deck add-card --card Horn IMp --deck testDeck\n" +
                        "deck add-card --card Crawling dragon --deck testDeck\n" +
                        "deck add-card --card Blue_Eyes White Dragon --deck testDeck\n" +
                        "deck add-card --card Slot Machine --deck testDeck\n" +
                        "deck add-card --card Haniwa --deck testDeck\n" +
                        "deck add-card --card Gate Guardian --deck testDeck\n" +
                        "deck add-card --card bitron --deck testDeck\n" +
                        "deck add-card --card Beast King Barbaros --deck testDeck\n" +
                        "deck add-card --card Leotron --deck testDeck\n" +
                        "deck add-card --card Alexandrite Dragon --deck testDeck\n" +
                        "deck add-card --card Mirage Dragon --deck testDeck\n" +
                        "deck add-card --card Exploder Dragon --deck testDeck\n" +
                        "deck add-card --card Warrior Dai Grepher --deck testDeck\n" +
                        "deck add-card --card Dark Blade --deck testDeck\n" +
                        "deck add-card --card Wattaildragon --deck testDeck\n" +
                        "deck add-card --card Terratiger, the Empowered Warrior --deck testDeck\n" +
                        "deck add-card --card The Tricky --deck testDeck\n" +
                        "deck add-card --card Spiral Serpent --deck testDeck\n" +
                        "menu exit\n" +
                        "menu enter duel\n" +
                        "duel --second-player nima --new --rounds 1\n" +
                        "paper\n" +
                        "stone\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "select --hand 2\n" +
                        "summon\n" +
                        "select --hand 1\n" +
                        "active effect\n" +
                        "select --hand 5\n" +
                        "active effect\n" +
                        "5 our board\n" +
                        "select --hand 5\n" +
                        "active effect\n" +
                        "5 our board\n" +
                        "select --hand 1\n" +
                        "active effect\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "select --hand 1\n" +
                        "summon\n" +
                        "select --hand 4\n" +
                        "active effect\n" +
                        "5 our board\n" +
                        "next phase\n" +
                        "select --monster 5\n" +
                        "attack 5\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "select --hand 1\n" +
                        "summon\n" +
                        "select --hand 3\n" +
                        "active effect\n" +
                        "5 our board\n" +
                        "abort\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "select --hand 1\n" +
                        "active effect\n" +
                        "select --hand 4\n" +
                        "active effect\n" +
                        "select --hand 5\n" +
                        "summon\n" +
                        "next phase\n" +
                        "select --monster 5\n" +
                        "attack 5\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "select --hand 1\n" +
                        "summon\n" +
                        "select --hand 3\n" +
                        "active effect\n" +
                        "next phase\n" +
                        "select --monster 5\n" +
                        "attack direct\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "select --hand 1\n" +
                        "summon\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "select --hand 3\n" +
                        "active effect\n" +
                        "select --hand 1\n" +
                        "summon\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "select --hand 4\n" +
                        "summon\n" +
                        "select --hand 4\n" +
                        "active effect\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "select --hand 4\n" +
                        "active effect\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "next phase\n" +
                        "surrender\n"+
                        "menu exit\n"+
                        "menu exit\n"+
                        "menu exit\n"

        )
                .getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        ViewMaster.setScanner(new Scanner(System.in));
        ViewMaster.getViewMaster().run();
        Assert.assertTrue(User.getUserByUsername("nima").getWinNum() == 1);
    }
//
//    @Test
//    public void test(){
//        URL url = getClass().getResource("/")
//        File file = new File(Paths.get());
//        FileReader reader = new FileReader(file);
//    }


    @Test
    public void AITest() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        User.getAllUsers().clear();
        ViewMaster.setCurrentMenu(Menu.LOGIN_MENU);
        ByteArrayInputStream in = new ByteArrayInputStream(("user create --u nima --nickname Nima --p 1234\n" +
                "user create --nickname Hooman --username homaan --password 1234\n" +
                "user login --password 1234 --username nima\n" +
                "menu enter shop\n" +
                "shop buy Magic Cylinder\n" +
                "shop buy Call of The Haunted\n" +
                "shop buy Time Seal\n" +
                "shop buy Magic Jammer\n" +
                "shop buy Mind Crush\n" +
                "shop buy Negate Attack\n" +
                "shop buy Torrential Tribute\n" +
                "shop buy Scanner\n" +
                "shop buy command knight\n" +
                "shop buy Horn IMp\n" +
                "shop buy yomi ship\n" +
                "shop buy suijin\n" +
                "shop buy fireyarou\n" +
                "shop buy Curtain of the Dark Ones\n" +
                "shop buy Feral Imp\n" +
                "shop buy Dark Magician\n" +
                "shop buy Wattkid\n" +
                "shop buy Baby Dragon\n" +
                "shop buy Hero of the East\n" +
                "shop buy Crawling dragon\n" +
                "shop buy Flame Manipulator\n" +
                "shop buy Blue_Eyes White Dragon\n" +
                "shop buy Curtain of the Dark Ones\n"+
                "shop buy UMIIRUKA\n"+
                "shop buy Yami\n"+
                "shop buy Closed Forest\n"+
                "shop buy Forest\n"+
                "shop buy Slot Machine\n" +
                "shop buy Haniwa\n" +
                "shop buy Man_Eater Bug\n" +
                "shop buy Gate Guardian\n" +
                "shop buy bitron\n" +
                "shop buy Marshmallon\n" +
                "shop buy Beast King Barbaros\n" +
                "shop buy Leotron\n" +
                "shop buy The Calculator\n" +
                "shop buy Alexandrite Dragon\n" +
                "shop buy Mirage Dragon\n" +
                "shop buy Exploder Dragon\n" +
                "shop buy Warrior Dai Grepher\n" +
                "shop buy Dark Blade\n" +
                "shop buy Wattaildragon\n" +
                "shop buy Terratiger, the Empowered Warrior\n" +
                "shop buy The Tricky\n" +
                "shop buy Spiral Serpent\n" +
                "menu exit\n" +
                "menu enter deck\n" +
                "deck create testDeck\n" +
                "deck set-activate testDeck\n" +
                "deck add-card --card Magic Cylinder --deck testDeck\n" +
                "deck add-card --card Warrior Dai Grepher --deck testDeck\n" +
                "deck add-card --card Horn IMp --deck testDeck\n" +
                "deck add-card --card yomi ship --deck testDeck\n" +
                "deck add-card --card Time Seal --deck testDeck\n" +
                "deck add-card --card Magic Jammer --deck testDeck\n" +
                "deck add-card --card Call of The Haunted --deck testDeck\n" +
                "deck add-card --card Slot Machine --deck testDeck\n" +
                "deck add-card --card Haniwa --deck testDeck\n" +
                "deck add-card --card Exploder Dragon --deck testDeck\n" +
                "deck add-card --card Mind Crush --deck testDeck\n" +
                "deck add-card --card Negate Attack --deck testDeck\n" +
                "deck add-card --card Alexandrite Dragon --deck testDeck\n" +
                "deck add-card --card Mirage Dragon --deck testDeck\n" +
                "deck add-card --card Feral Imp --deck testDeck\n" +
                "deck add-card --card Torrential Tribute --deck testDeck\n" +
                "deck add-card --card command knight --deck testDeck\n" +
                "deck add-card --card Dark Magician --deck testDeck\n" +
                "deck add-card --card UMIIRUKA --deck testDeck\n"+
                "deck add-card --card Closed Forest --deck testDeck\n"+
                "deck add-card --card Yami --deck testDeck\n"+
                "deck add-card --card Forest --deck testDeck\n"+
                "deck add-card --card Wattkid --deck testDeck\n" +
                "deck add-card --card Baby Dragon --deck testDeck\n" +
                "deck add-card --card Marshmallon --deck testDeck\n" +
                "deck add-card --card Beast King Barbaros --deck testDeck\n" +
                "deck add-card --card Leotron --deck testDeck\n" +
                "deck add-card --card The Calculator --deck testDeck\n" +
                "deck add-card --card Baby Dragon --deck testDeck\n" +
                "deck add-card --card Hero of the East --deck testDeck\n" +
                "deck add-card --card Crawling dragon --deck testDeck\n" +
                "deck add-card --card Flame Manipulator --deck testDeck\n" +
                "deck add-card --card Blue_Eyes White Dragon --deck testDeck\n" +
                "deck add-card --card Terratiger, the Empowered Warrior --deck testDeck\n" +
                "deck add-card --card The Tricky --deck testDeck\n" +
                "deck add-card --card Spiral Serpent --deck testDeck\n" +
                "deck add-card --card Scanner --deck testDeck\n" +
                "deck add-card --card suijin --deck testDeck\n" +
                "deck add-card --card fireyarou --deck testDeck\n" +
                "deck add-card --card Curtain of the Dark Ones --deck testDeck\n" +
                "deck add-card --card Man_Eater Bug --deck testDeck\n" +
                "deck add-card --card Gate Guardian --deck testDeck\n" +
                "deck add-card --card bitron --deck testDeck\n" +
                "deck add-card --card Dark Blade --deck testDeck\n" +
                "deck add-card --card Wattaildragon --deck testDeck\n" +
                "ai vs ai\nmenu exit\nmenu exit\nmenu exit").getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        ViewMaster.setScanner(new Scanner(System.in));
        ViewMaster.getViewMaster().run();
        Assert.assertTrue(true);
    }
}
