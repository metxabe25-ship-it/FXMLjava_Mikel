package eus.liburutegia.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LiburuFormController {
    @FXML
    private TextField izenbutxt;
    @FXML
    private TextField egileatxt;
    @FXML
    private TextField urteatxt;
    @FXML
    private Button gordebtn;
    @FXML
    private Label mezuaLabel;

    private void gorde(){
        String m = "Gordeta: " + izenbutxt.getText()
                + " (" + egileatxt.getText() + ", " + urteatxt.getText() + ")";

        mezuaLabel.setText(m);
    }
}
