package projet.java.controlers;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import projet.java.models.classes.Evaluation;
import projet.java.models.classes.Formateur;
import projet.java.models.classes.Module;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import static dao.FormateurDAO.renvoiFormateurConnect;

public class EvaluationController implements Initializable {

    @FXML
    private TableView<Evaluation> tableau;

    @FXML
    public TableColumn<Evaluation, Integer> id;

    @FXML
    public TableColumn<Evaluation, String> forme;

    @FXML
    public TableColumn<Evaluation, String> type;

    @FXML
    public TableColumn<Evaluation, String> intitule;

    @FXML
    public TableColumn<Evaluation, Double> coef;

    @FXML
    public TableColumn<Evaluation, String> module;
    @FXML
    public TableColumn<Evaluation, Date> dateEval;


    @FXML
    private TextField txtNumEvaluation;
    @FXML
    private ComboBox<String> txtModule;
    @FXML
    private ComboBox<String> txtForme;
    @FXML
    private ComboBox<String> txtType;
    @FXML
    private TextField txtIntitule;
    @FXML
    private TextField txtCoef;
    @FXML
    private DatePicker txtDate;
    @FXML
    private Button btnValider;
    @FXML
    private TextArea resultat;
    @FXML
    private ToggleGroup group;
    @FXML
    private RadioButton choixInscrire;
    @FXML
    private RadioButton choixMiseAJour;
    @FXML
    private TextField txtRecherche;
    @FXML
    private Button btnRetourner;
    @FXML
    private AnchorPane ap;

    private static Evaluation evaluationSelectionne;

    public static Evaluation getEvaluationSelectionne() {
        return evaluationSelectionne;
    }

    public static void setEvaluationSelectionne(Evaluation evaluationSelectionne) {
        EvaluationController.evaluationSelectionne = evaluationSelectionne;
    }

    public EvaluationController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtModule.getItems().setAll(ModuleDAO.retournerModuleFormateur(renvoiFormateurConnect()));
        txtForme.getItems().addAll(
                "Control continu",
                "Examen Final"
        );
        txtType.getItems().addAll(
                "TP",
                "Oral",
                "Ecrit",
                "Travaux de Maison",
                "Projet"
        );

        id.setCellValueFactory(new PropertyValueFactory<>("idEvaluation"));
        forme.setCellValueFactory(new PropertyValueFactory<>("forme"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        intitule.setCellValueFactory(new PropertyValueFactory<>("intitule"));
        coef.setCellValueFactory(new PropertyValueFactory<>("coef"));
        module.setCellValueFactory(new PropertyValueFactory<>("module"));
        dateEval.setCellValueFactory(new PropertyValueFactory<>("dateEvaluation"));
        tableau.setItems(evaluations);
    }

    private ObservableList<Evaluation> evaluations = FXCollections.observableArrayList(
            EvaluationDAO.readList()
    );


    public void actualise() {
        ObservableList<Evaluation> evaluation = FXCollections.observableArrayList(
                EvaluationDAO.readList()
        );
        tableau.setItems(evaluation);
    }

    public void inscrireEvaluation() {
        Evaluation evaluation = new Evaluation(txtForme.getSelectionModel().getSelectedItem(),txtType.getSelectionModel().getSelectedItem(),
                txtIntitule.getText(),Double.parseDouble(txtCoef.getText()),Integer.parseInt(txtNumEvaluation.getText()),txtModule.getSelectionModel().getSelectedItem(),
                Date.valueOf(txtDate.getValue()));
        if (EvaluationDAO.verifEvaluation(Integer.parseInt(txtNumEvaluation.getText()))) {
            resultat.setText("Error: Evaluation non inscrite , ID déja existant");
        } else {
            if (EvaluationDAO.create(evaluation)) {
                txtNumEvaluation.setText("");
               txtModule.setValue(null);
               txtCoef.setText("");
               txtDate.setValue(null);
               txtForme.setValue(null);
               txtIntitule.setText("");
               txtType.setValue(null);
               resultat.setText("Evaluation inscrite");
               actualise();
            } else {
                resultat.setText("Error: Evaluation  non inscrite");
            }
        }
    }

    public void afficherEvaluationAUpdate() {
        Evaluation evaluation = tableau.getSelectionModel().getSelectedItem();
        txtNumEvaluation.setText(String.valueOf(evaluation.getIdEvaluation()));
        txtForme.setValue(evaluation.getForme());
        txtType.setValue(evaluation.getType());
        txtIntitule.setText(evaluation.getIntitule());
        txtCoef.setText(String.valueOf(evaluation.getCoef()));
        txtModule.setValue(evaluation.getModule());
        //txtDate.setValue(evaluation.getDateEvaluation());
    }

    public void updateEvaluation() {
        Evaluation evaluationAUpdate = new Evaluation(txtForme.getSelectionModel().getSelectedItem(),txtType.getSelectionModel().getSelectedItem(),
                txtIntitule.getText(),Double.parseDouble(txtCoef.getText()),Integer.parseInt(txtNumEvaluation.getText()),txtModule.getSelectionModel().getSelectedItem(),
                Date.valueOf(txtDate.getValue()));
        Evaluation evaluation = tableau.getSelectionModel().getSelectedItem();
        if (EvaluationDAO.update(evaluationAUpdate, EvaluationDAO.renvoieIDEvaluation(evaluation))) {
            resultat.setText("Evaluation mise à jour");
            txtNumEvaluation.setText("");
            txtDate.setValue(null);
            txtModule.setValue(null);
            txtCoef.setText("");
            txtIntitule.setText("");
            txtType.setValue(null);
            txtForme.setValue(null);
            actualise();
        } else
            resultat.setText("Erreur de mise à jour");
    }

    public void decisionButtonValider() {
        if (group.getSelectedToggle() == choixInscrire) {
            inscrireEvaluation();
        } else if (group.getSelectedToggle() == choixMiseAJour) {
            updateEvaluation();
        } else {
            resultat.setText("Veuiller cocher\n un bouton SVP");
        }
    }

    public void afficherNotes(){
        if (tableau.getSelectionModel().getSelectedItem() != null){
            Evaluation evaluation = tableau.getSelectionModel().getSelectedItem();
            setEvaluationSelectionne(evaluation);
            passerScene("/projet/java/view/voirNote.fxml");
        }
    }

    private  void passerScene(String urlScene) {
        try {
            Stage stage = (Stage) ap.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(urlScene)));
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public void detailFormateur(){
        if (tableau.getSelectionModel().getSelectedItem() != null){
            if (group.getSelectedToggle() == choixDetailFormateur){
                if (FormateurDAO.validiteFormateur(tableau.getSelectionModel().getSelectedItem())){
                    detailFormateur.setVisible(true);
                    txtNumFormateur.setVisible(false);
                    txtPwd.setVisible(false);
                    txtStatut.setVisible(false);
                    txtLogin.setVisible(false);
                    txtLogin.setVisible(false);
                    txtAdresse.setVisible(false);
                    txtEmail.setVisible(false);
                    txtNom.setVisible(false);
                    txtPrenom.setVisible(false);
                    txtSexe.setVisible(false);
                    txtTelephone.setVisible(false);
                    btnInscrire.setVisible(false);
                }else{
                    resultat.setText("Formateur non valide");
                }
            }
        }else
            resultat.setText("Veuiller sélectionné\nun formateur SVP");
    }

    public void attributInscrire(){
        if (group.getSelectedToggle() == choixInscrire || group.getSelectedToggle() == choixMiseAJour){
            detailFormateur.setVisible(false);
            txtNumFormateur.setVisible(true);
            txtPwd.setVisible(true);
            txtStatut.setVisible(true);
            txtLogin.setVisible(true);
            txtLogin.setVisible(true);
            txtAdresse.setVisible(true);
            txtEmail.setVisible(true);
            txtNom.setVisible(true);
            txtPrenom.setVisible(true);
            txtSexe.setVisible(true);
            txtTelephone.setVisible(true);
            btnInscrire.setVisible(true);
        }
    }

    public void lesModules(){
        ajoutModules.getItems().removeAll(ModuleDAO.retournerModule());
        ajoutModules.getItems().addAll(ModuleDAO.retournerModule());
    }

    private void modules(ComboBox<String> retirerModule) {
        if (tableau.getSelectionModel().getSelectedItem() != null){
            retirerModule.getItems().removeAll(ModuleDAO.retournerModule());
            Formateur formateur = tableau.getSelectionModel().getSelectedItem();
            retirerModule.getItems().addAll(ModuleDAO.retournerModuleFormateur(formateur));
        }else
            resultat.setText("Veuiller sélectionné\nun module SVP");
    }

    public void mesModules(){
        modules(mesModules);
    }

    public void mesModulesARetirer(){
        modules(retirerModule);
    }

    public void ajoutModuleAFormateur(){
        if (tableau.getSelectionModel().getSelectedItem() != null){
            Formateur formateur = tableau.getSelectionModel().getSelectedItem();
            if (ModuleDAO.verifModuleFormateur(FormateurDAO.renvoieIDFormateur(formateur))){
                resultat.setText("Ce module est déja \nenseigné par ce formateur");
            }else{
                if (ModuleDAO.ajoutModuleAFormateur(FormateurDAO.renvoieIDFormateur(formateur),ModuleDAO.renvoieIDModule(ajoutModules.getValue()))){
                    resultat.setText("Module ajouté au formateur");
                }else{
                    resultat.setText("Erreur: Module non ajouté");
                }
            }
        }
    }

    public void rechercherFormateur() {
        if (txtRecherche != null) {
            if (formateurDao.read(Integer.parseInt(txtRecherche.getText())) != null) {
                formateurs.removeAll(formateurs);
                formateurs.add((Formateur) formateurDao.read(Integer.parseInt(txtRecherche.getText())));
                tableau.setItems(formateurs);
            } else
                resultat.setText("Formateur non trouvé");
        } else
            resultat.setText("Veuiller saisir un ID \n a recherché");
    }

    public void supprimerFormateur() {
        if (tableau.getSelectionModel().getSelectedItem() != null) {
            Formateur formateur = tableau.getSelectionModel().getSelectedItem();
            if (formateurDao.delete(formateur)) {
                resultat.setText("Formateur Supprimé !!!");
                actualise();
            } else
                resultat.setText("Error: Formateur non supprimé");
        } else
            resultat.setText("Veuillez sélectionné\nun formateur SVP");
    }*/

    public void retourner() {
        Stage stage = (Stage) btnRetourner.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("/projet/java/view/acceuilFormateur.fxml")));
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


