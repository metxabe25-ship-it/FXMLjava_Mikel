package eus.liburutegia.controller;

import javafx.fxml.FXML;
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
    private Label mezualbl;

    @FXML
    private void initialize() {mezualbl.setText("Bete datuak eta sakatu Gorde.");}

    @FXML
    private void gorde(){
        String urtea = urteatxt.getText();
        if(!zenbakiaDa(urtea)){
            mezualbl.setText("Urtea ezin da testua izan.");

        }else {
            mezualbl.setText("Gordeta: " + izenbutxt.getText()
                    + " (" + egileatxt.getText() + ", " + urtea + ")");
        }
    }

    private boolean zenbakiaDa(String urtea){
        try {
            Integer.parseInt(urtea);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
