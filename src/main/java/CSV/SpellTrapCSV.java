package CSV;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.TreeMap;

public class SpellTrapCSV {
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "type")
    private String type;
    @CsvBindByName(column = "Icon (Property)")
    private String icon;
    @CsvBindByName(column = "Description")
    private String description;
    @CsvBindByName(column = "status")
    private String status;
    @CsvBindByName(column = "price")
    private String price;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
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
        List<SpellTrapCSV> spellsAndTraps = new CsvToBeanBuilder<SpellTrapCSV>(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\CSV\\" + "SpellTrap.csv"))
                .withType(SpellTrapCSV.class)
                .build()
                .parse();
        for (SpellTrapCSV spellTrapCsv : spellsAndTraps)
            if (name.equals(spellTrapCsv.getName()))
                return spellTrapCsv;
        return null;
    }


    public static void getNameAndDescription(TreeMap<String, String> cards) throws Exception {
        List<SpellTrapCSV> spellsAndTraps = new CsvToBeanBuilder<SpellTrapCSV>(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\CSV\\" + "SpellTrap.csv"))
                .withType(SpellTrapCSV.class)
                .build()
                .parse();
        for (SpellTrapCSV spellTrapCsv : spellsAndTraps)
            cards.put(spellTrapCsv.getName(),spellTrapCsv.getDescription());
    }
}
