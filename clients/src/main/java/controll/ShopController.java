package controll;


import view.ViewMaster;

public class ShopController {
    /*private final TreeMap<String, String> cards = new TreeMap<>();

    public ShopController() {
        try {
            MonsterCSV.getNameAndDescription(cards);
        } catch (FileNotFoundException ignored) {
        }
        try {
            SpellTrapCSV.getNameAndDescription(cards);
        } catch (Exception ignored) {
        }
    }
*/


    public String[] getDetail(String cardName) {
        try {
            ViewMaster.dataOutputStream.writeUTF("shop " + cardName);
            ViewMaster.dataOutputStream.flush();
            return ViewMaster.dataInputStream.readUTF().split("-");
        } catch (Exception ignored) {
        }
        return null;
    }

    public void buyCard(String selectedCardName, int cardPrice) {
        try {
            ViewMaster.dataOutputStream.writeUTF("buy " + selectedCardName + " " + ViewMaster.getCurrentUserToken()
                    + " " + cardPrice);
            ViewMaster.dataOutputStream.flush();
            String answer = ViewMaster.dataInputStream.readUTF();
        } catch (Exception e) {
        }
    }


/*
    public void buyCard(User user, Card card, int cardPrice) {
        user.addCard(card);
        user.addMoney(-cardPrice);
    }*/
}

