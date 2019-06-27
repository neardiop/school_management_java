package projet.java.controlers;

import dao.FiliereDAO;
import dao.ResponsableFiliereDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AcceuilResponsableController implements Initializable {


    public static String filiereSelected = null;

    public static String actionSelected;

    @FXML
    private AnchorPane ap;
    @FXML
    private ComboBox<String> mesFilieres;
    @FXML
    private Label bienvenu;
    @FXML
    private ComboBox<String> action;
    @FXML
    private Label erreur;

    public static String getFiliereSelected() {
        return filiereSelected;
    }

    public static void setFiliereSelected(String filiereSelected) {
        AcceuilResponsableController.filiereSelected = filiereSelected;
    }

    public static String getActionSelected() {
        return actionSelected;
    }

    public static void setActionSelected(String actionSelected) {
        AcceuilResponsableController.actionSelected = actionSelected;
    }

    public void deconnexion(ActionEvent actionEvent) {
        ResponsableFiliereDAO.responsableDisConnect(ResponsableFiliereDAO.renvoiResponsableConnect().getLogin());
        passerScene("/projet/java/view/login.fxml");
        setFiliereSelected(null);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mesFilieres.getItems().addAll(FiliereDAO.retournerFiliereResponsable(ResponsableFiliereDAO.renvoiResponsableConnect()));
        if (ResponsableFiliereDAO.renvoiResponsableConnect().getSexe() == 'M') {
            bienvenu.setText("Bienvenue Mr " + ResponsableFiliereDAO.renvoiResponsableConnect().getPrenom());
        } else if (ResponsableFiliereDAO.renvoiResponsableConnect().getSexe() == 'F') {
            bienvenu.setText("Bienvenue Mme " + ResponsableFiliereDAO.renvoiResponsableConnect().getPrenom());
        }
        action.getItems().addAll(
                "Etudiants",
                "Formateurs",
                "Modules"
        );
    }


    public void decisionBtnValider() {
        if (mesFilieres.getSelectionModel().getSelectedItem() != null || action.getSelectionModel().getSelectedItem() != null) {
            if (action.getSelectionModel().getSelectedItem() == "Etudiants") {
                setFiliereSelected(mesFilieres.getSelectionModel().getSelectedItem());
                passerScene("/projet/java/view/ListeEtudiants.fxml");
            } else if (action.getSelectionModel().getSelectedItem() == "Formateurs") {
                setFiliereSelected(mesFilieres.getSelectionModel().getSelectedItem());
                passerScene("/projet/java/view/ListeFormateurs.fxml");
            } else if (action.getSelectionModel().getSelectedItem() == "Modules") {
                setFiliereSelected(mesFilieres.getSelectionModel().getSelectedItem());
                passerScene("/projet/java/view/ListeModules.fxml");
            }
        } else {
            erreur.setVisible(true);
        }
    }
}
