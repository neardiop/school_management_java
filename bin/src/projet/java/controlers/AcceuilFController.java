package projet.java.controlers;

import dao.FormateurDAO;
import dao.ModuleDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projet.java.models.classes.Formateur;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static dao.FormateurDAO.renvoiFormateurConnect;

public class AcceuilFController implements Initializable {

    @FXML
    private static String module;
    @FXML
    private ComboBox<String> mesModules;
    @FXML
    private ComboBox<String> action;

    @FXML
    private AnchorPane ap;
    @FXML
    private Label erreur;
    @FXML
    private Label titre;

    public static String getModule() {
        return module;
    }

    public static void setModule(String module) {
        AcceuilFController.module = module;
    }

    public void deconnexion(ActionEvent actionEvent) {
        Formateur formateur = renvoiFormateurConnect();
        FormateurDAO.formateurDisConnect(formateur.getLogin());
        passerScene("/projet/java/view/login.fxml");
        setModule(null);
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
        mesModules.getItems().setAll(ModuleDAO.retournerModuleFormateur(renvoiFormateurConnect()));
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Mes Etudiants");
        arrayList.add("Evaluations");
        action.getItems().setAll(arrayList);
        if (FormateurDAO.renvoiFormateurConnect().getSexe() == 'M') {
            titre.setText("Bienvenue Mr " + FormateurDAO.renvoiFormateurConnect().getNom());
        } else if (FormateurDAO.renvoiFormateurConnect().getSexe() == 'F') {
            titre.setText("Bienvenue Mme " + FormateurDAO.renvoiFormateurConnect().getNom());
        }
    }


    public void descisionBtnValider() {
        if (action.getSelectionModel().getSelectedItem() != null && mesModules.getSelectionModel().getSelectedItem() != null) {
            if (action.getSelectionModel().getSelectedItem() == "Mes Etudiants") {
                setModule(mesModules.getSelectionModel().getSelectedItem());
                passerScene("/projet/java/view/EtudiantModule.fxml");
            } else if (action.getSelectionModel().getSelectedItem() == "Evaluations") {
                setModule(mesModules.getSelectionModel().getSelectedItem());
                passerScene("/projet/java/view/evaluation.fxml");
            }
        } else {
            erreur.setText("Sélectionné un module et une action SVP");
        }
    }
}
