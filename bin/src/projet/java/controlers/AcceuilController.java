package projet.java.controlers;

import dao.FormateurDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AcceuilController implements Initializable {


    @FXML
    private Label nbFormateur;

    @FXML
    private AnchorPane ap;

    public void afficherEtudiants(ActionEvent actionEvent) {
        passerScene("/projet/java/view/ListeEtudiants.fxml");
    }

    public void afficherFiliere(ActionEvent actionEvent) {
        passerScene("/projet/java/view/ListeFilieres.fxml");
    }

    public void afficherModule(ActionEvent actionEvent) {
        passerScene("/projet/java/view/ListeModules.fxml");
    }

    public void afficherFormateur(ActionEvent actionEvent) {
        passerScene("/projet/java/view/ListeFormateurs.fxml");
    }

    public void afficherResponsable(ActionEvent actionEvent) {
        passerScene("/projet/java/view/ListeResponsable.fxml");
    }

    public void afficherParametre(ActionEvent actionEvent) {
        passerScene("/projet/java/view/parametre.fxml");
    }

    public void deconnexion(ActionEvent actionEvent) {
        passerScene("/projet/java/view/login.fxml");
    }

    private void passerScene(String urlScene) {
        try {
            Stage stage = (Stage) ap.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(urlScene)));
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void newScene(FXMLLoader fxmlLoader) {
        Parent root;
        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (FormateurDAO.nbFormateurAValider() == 0) {
            nbFormateur.setVisible(false);
        } else {
            nbFormateur.setText(String.valueOf(" " + FormateurDAO.nbFormateurAValider()));
            nbFormateur.setTextFill(Color.rgb(100, 0, 0));
        }
    }
}
