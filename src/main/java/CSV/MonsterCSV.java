package CSV;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class MonsterCSV {
    @CsvBindByName(column = "Name")
    private String name;
    @CsvBindByName(column = "Level")
    private String level;
    @CsvBindByName(column = "Attribute")
    private String attribute;
    @CsvBindByName(column = "Monster Type")
    private String monsterType;
    @CsvBindByName(column = "Card Type")
    private String cardType;
    @CsvBindByName(column = "Atk")
    private String attack;
    @CsvBindByName(column = "Def")
    private String defence;
    @CsvBindByName(column = "Description")
    private String description;
    @CsvBindByName(column = "Price")
    private String price;

    public String getName() {
        return name;
    }

    public int getLevel() {
        return Integer.parseInt(level);
    }

    public String getAttribute() {
        return attribute;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public String getCardType() {
        return cardType;
    }

    public int getAttack() {
        return Integer.parseInt(attack);
    }

    public int getDefence() {
        return Integer.parseInt(defence);
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return Integer.parseInt(price);
    }

    public MonsterCSV findSpellTrap(String name) throws FileNotFoundException {
        List<MonsterCSV> monsters = new CsvToBeanBuilder<MonsterCSV>
                (new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\CSV\\" + "Monster.csv"))
                .withType(MonsterCSV.class)
                .build()
                .parse();
        for (MonsterCSV monsterCSV : monsters)
            if (name.equals(monsterCSV.getName()))
                return monsterCSV;
        return null;
    }

}
