import Interfaces.Effects;
import model.Monster;
import view.ViewMaster;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        Effects effects = new Effects() {
//            @Override
//            public void useAbility() {
//
//            }
//        }
        new ViewMaster().run();
    }
}

class M {
    String name;
    int price;
    int level;
    public M(String name, int level, int p) {
        this.name = name;
        this.level = level;
        this.price = p;
    }
}



