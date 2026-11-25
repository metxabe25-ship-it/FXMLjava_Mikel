package eus.liburutegia;

import eus.liburutegia.view.LiburuFormView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Orain arte bezala: bista Java hutsean eraikitzen da.
        // LiburuFormView view = new LiburuFormView();

        // TODO (4. urratsa): ordezkatu goiko lerroa FXMLLoader erabiliz.
        FXMLLoader loader = new FXMLLoader(App.class.getResource("liburu-form.fxml"));
        Parent root = loader.load();

        stage.setTitle("Liburutegia");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
