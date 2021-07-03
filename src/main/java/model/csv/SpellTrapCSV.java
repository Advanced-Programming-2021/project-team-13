package model.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import enums.CardType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.TreeMap;

public class SpellTrapCSV {
    @CsvBindByName(column = "Name")
    private String name;
    @CsvBindByName(column = "Type")
    private String type;
    @CsvBindByName(column = "Icon (Property)")
    private String icon;
    @CsvBindByName(column = "Description")
    private String description;
    @CsvBindByName(column = "Status")
    private String status;
    @CsvBindByName(column = "Price")
    private String price;

    public String getName() {
        return name;
    }

    public CardType getType() {
        String s = "SPELL";
        if (s.equalsIgnoreCase(type)) {
            return CardType.SPELL;
        }
        return CardType.TRAP;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public int getPrice() {
        return Integer.parseInt(price);
    }

    public static SpellTrapCSV findSpellTrap(String name) throws FileNotFoundException {
        List<SpellTrapCSV> spellsAndTraps = new CsvToBeanBuilder<SpellTrapCSV>(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\model\\CSV\\" + "SpellTrap.csv"))
                .withType(SpellTrapCSV.class)
                .build()
                .parse();
        for (SpellTrapCSV spellTrapCsv : spellsAndTraps)
            if (name.equalsIgnoreCase(spellTrapCsv.getName().replaceAll("[ ,.\\-_;':()]", "")))
                return spellTrapCsv;
        return null;
    }


    public static void getNameAndDescription(TreeMap<String, String> cards) throws Exception {
        List<SpellTrapCSV> spellsAndTraps = new CsvToBeanBuilder<SpellTrapCSV>(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\model\\CSV\\" + "SpellTrap.csv"))
                .withType(SpellTrapCSV.class)
                .build()
                .parse();
        for (SpellTrapCSV spellTrapCsv : spellsAndTraps)
            cards.put(spellTrapCsv.getName().replaceAll("[ ,.\\-_;':()]", ""), spellTrapCsv.getDescription());
    }
}
