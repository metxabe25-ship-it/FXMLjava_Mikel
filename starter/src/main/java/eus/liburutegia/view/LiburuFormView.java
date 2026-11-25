package eus.liburutegia.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Liburu bat sartzeko formularioa, Java hutsean eraikita.
 * Ariketa: bista bera liburu-form.fxml fitxategian idaztea.
 */
public class LiburuFormView {

    private final GridPane root = new GridPane();
    private final TextField izenburuaField = new TextField();
    private final TextField egileaField = new TextField();
    private final TextField urteaField = new TextField();
    private final Button gordeButton = new Button("Gorde");
    private final Label mezuaLabel = new Label();

    public LiburuFormView() {
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

        root.add(new Label("Izenburua:"), 0, 0);
        root.add(izenburuaField, 1, 0);

        root.add(new Label("Egilea:"), 0, 1);
        root.add(egileaField, 1, 1);

        root.add(new Label("Urtea:"), 0, 2);
        root.add(urteaField, 1, 2);

        root.add(gordeButton, 1, 3);
        root.add(mezuaLabel, 0, 4, 2, 1);   // zutabea, errenkada, colspan, rowspan

        gordeButton.setOnAction(e -> mezuaLabel.setText(
                "Gordeta: " + izenburuaField.getText()
                + " (" + egileaField.getText() + ", " + urteaField.getText() + ")"));
    }

    public Parent getRoot() {
        return root;
    }
}
