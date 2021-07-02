package controll;

import com.gilecode.yagson.YaGson;
import model.cards.Card;
import view.ViewMaster;
import view.allmenu.ImportAndExportView;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImportAndExportController {
    private final ImportAndExportView importAndExportView;

    public ImportAndExportController(ImportAndExportView importAndExportView) {
        this.importAndExportView = importAndExportView;
    }

    public void importCard(String cardName) {
        YaGson mapper = new YaGson();
        try {
            String json = new String(Files.readAllBytes(Paths.get(cardName)));
            Card card = mapper.fromJson(json, Card.class);
            ViewMaster.getUser().getCards().add(card);
            importAndExportView.printSuccessful("imported");
        } catch (Exception e) {
            importAndExportView.printCantFindFile();
        }
    }

    public void exportCard(String cardName) {
        YaGson mapper = new YaGson();
        if (ViewMaster.getUser().getAllCards().containsKey(cardName)) {
            Card card = ViewMaster.getViewMaster().getDeckView().getDeckController().findCard(cardName);
            String json = mapper.toJson(card);
            try {
                FileWriter FW = new FileWriter(cardName);
                FW.write(json);
                FW.close();
                importAndExportView.printSuccessful("exported");
            } catch (Exception ignored) {
            }
        } else
            importAndExportView.printCantFindCard(cardName);
    }
}
