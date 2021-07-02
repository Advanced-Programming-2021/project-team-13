package view.allmenu;

import controll.ImportAndExportController;
import view.Regex;

import java.util.regex.Matcher;

public class ImportAndExportView {
    private final ImportAndExportController importAndExportController = new ImportAndExportController(this);

    public void run(String command) {
        if (command.matches("import card (?<cardName>.+)"))
            importCard(Regex.getInputMatcher(command, "import card (?<cardName>.+)"));
        else if (command.matches("export card (?<cardName>.+)"))
            exportCard(Regex.getInputMatcher(command, "export card (?<cardName>.+)"));
        else
            System.out.println("invalid command");
    }

    private void exportCard(Matcher inputMatcher) {
        inputMatcher.find();
        String cardName = inputMatcher.group("cardName");
        importAndExportController.importCard(cardName);
    }

    private void importCard(Matcher inputMatcher) {
        inputMatcher.find();
        String cardName = inputMatcher.group("cardName");
        importAndExportController.exportCard(cardName);
    }

    public void printCantFindCard(String cardName) {
        System.out.println("card with name " + cardName + " does not exist");
    }

    public void printSuccessful(String message) {
        System.out.println("card " + message + " successfully");
    }

    public void printCantFindFile() {
        System.out.println("can't find file");
    }
}
