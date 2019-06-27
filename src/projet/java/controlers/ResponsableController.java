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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import projet.java.models.classes.Filiere;
import projet.java.models.classes.Formateur;
import projet.java.models.classes.Module;
import projet.java.models.classes.ResponsaleFiliere;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

public class ResponsableController implements Initializable {

    @FXML
    private TableView<ResponsaleFiliere> tableau;

    @FXML
    public TableColumn<Formateur, Integer> id;

    @FXML
    public TableColumn<Formateur, String> nom;

    @FXML
    public TableColumn<Formateur, String> prenom;

    @FXML
    public TableColumn<Formateur, String> sexe;

    @FXML
    public TableColumn<Formateur, String> telephone;

    @FXML
    public TableColumn<Formateur, String> email;
    @FXML
    public TableColumn<Formateur, String> login;
    @FXML
    public TableColumn<Formateur, String> password;


    @FXML
    private Button btnUpdate;
    @FXML
    private TextField txtNumResponsable;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtSexe;
    @FXML
    private TextField txtTelephone;
    @FXML
    private TextField txtAdresse;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtPwd;
    @FXML
    private RadioButton choixInscrire;
    @FXML
    private RadioButton choixMiseAJour;
    @FXML
    private RadioButton choixDetail;
    @FXML
    private TextArea resultat;
    @FXML
    private ToggleGroup group;
    @FXML
    private TextField txtRecherche;
    @FXML
    private Button btnRetourner;
    @FXML
    private Button btnInscrire;
    @FXML
    private Pane detail;
    @FXML
    private ComboBox<String> ajoutFilieres;
    @FXML
    private ComboBox<String> mesFilieres;
    @FXML
    private ComboBox<String> retirerFiliere;



    PersonneDAOFactory personneDAOFactory = new PersonneDAOFactory();
    PersonneDAO responsablefilieredao = personneDAOFactory.getPersonneDAO("RESPONSABLEFILIEREDAO");

    public ResponsableController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        id.setCellValueFactory(new PropertyValueFactory<>("idResponsable"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        password.setCellValueFactory(new PropertyValueFactory<>("pwd"));
        tableau.setItems(responsaleFilieres);
    }

    private ObservableList<ResponsaleFiliere> responsaleFilieres = FXCollections.observableArrayList(
            ResponsableFiliereDAO.readList()
    );


    public void actualise() {
        ObservableList<ResponsaleFiliere> responsaleFilieresActualise = FXCollections.observableArrayList(
                ResponsableFiliereDAO.readList()
        );
        tableau.setItems(responsaleFilieresActualise);
    }

    public void inscrireResponsable() {
        ResponsaleFiliere responsaleFiliere = new ResponsaleFiliere(txtNom.getText(), txtPrenom.getText(), txtAdresse.getText(), txtEmail.getText(),
                txtTelephone.getText(), txtSexe.getText().charAt(0), txtLogin.getText(), txtPwd.getText(), Integer.parseInt(txtNumResponsable.getText()));
        if (ResponsableFiliereDAO.verifResponsable(Integer.parseInt(txtNumResponsable.getText()))) {
            resultat.setText("Error: Responsable non inscrit ,\n ID déja existant");
        } else {
            if (responsablefilieredao.create(responsaleFiliere)) {
                txtNumResponsable.setText("");
                txtNom.setText("");
                txtPrenom.setText("");
                txtEmail.setText("");
                txtAdresse.setText("");
                txtTelephone.setText("");
                txtSexe.setText("");
                txtLogin.setText("");
                txtPwd.setText("");
                resultat.setText("Responsable inscrit");
                actualise();
            } else {
                resultat.setText("Error: Responsable  non inscrit");
            }
        }
    }

    public void afficherResponsableAUpdate() {
        ResponsaleFiliere responsaleFiliere = tableau.getSelectionModel().getSelectedItem();
        txtNumResponsable.setText(String.valueOf(responsaleFiliere.getIdResponsable()));
        txtNom.setText(responsaleFiliere.getNom());
        txtPrenom.setText(responsaleFiliere.getPrenom());
        txtSexe.setText(String.valueOf(responsaleFiliere.getSexe()));
        txtTelephone.setText(responsaleFiliere.getTelephone());
        txtAdresse.setText(responsaleFiliere.getAdresse());
        txtEmail.setText(responsaleFiliere.getEmail());
        txtLogin.setText(responsaleFiliere.getLogin());
        txtPwd.setText(responsaleFiliere.getPwd());
    }

    public void updateResponsable() {
        ResponsaleFiliere responsableAUpdate = new ResponsaleFiliere(txtNom.getText(), txtPrenom.getText(), txtAdresse.getText(), txtEmail.getText(),
                txtTelephone.getText(), txtSexe.getText().charAt(0), txtLogin.getText(), txtPwd.getText(), Integer.parseInt(txtNumResponsable.getText()));
        ResponsaleFiliere responsaleFiliere = tableau.getSelectionModel().getSelectedItem();
        if (responsablefilieredao.update(responsableAUpdate, ResponsableFiliereDAO.renvoieIdResponsable(responsaleFiliere))) {
            resultat.setText("Responsable mis à jour");
            txtNumResponsable.setText("");
            txtNom.setText("");
            txtPrenom.setText("");
            txtEmail.setText("");
            txtAdresse.setText("");
            txtTelephone.setText("");
            txtSexe.setText("");
            txtLogin.setText("");
            txtPwd.setText("");
            actualise();
        } else
            resultat.setText("Erreur de mise à jour");
    }

    public void decisionButtonValider() {
        if (group.getSelectedToggle() == choixInscrire) {
            inscrireResponsable();
        } else if (group.getSelectedToggle() == choixMiseAJour) {
            updateResponsable();
        } else {
            resultat.setText("Veuiller cocher\n un bouton SVP");
        }
    }

    public void detailResponsable(){
        if (tableau.getSelectionModel().getSelectedItem() != null){
            if (group.getSelectedToggle() == choixDetail){
                detail.setVisible(true);
                txtNumResponsable.setVisible(false);
                txtPwd.setVisible(false);
                txtLogin.setVisible(false);
                txtLogin.setVisible(false);
                txtAdresse.setVisible(false);
                txtEmail.setVisible(false);
                txtNom.setVisible(false);
                txtPrenom.setVisible(false);
                txtSexe.setVisible(false);
                txtTelephone.setVisible(false);
                btnInscrire.setVisible(false);
            }
        }else
            resultat.setText("Veuiller sélectionné\nun Responsable SVP");
    }

    public void attributInscrire(){
        if (group.getSelectedToggle() == choixInscrire || group.getSelectedToggle() == choixMiseAJour){
            detail.setVisible(false);
            txtNumResponsable.setVisible(true);
            txtPwd.setVisible(true);
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

    public void lesFilieres(){
        ajoutFilieres.getItems().removeAll(FiliereDAO.retournerFiliere());
        ajoutFilieres.getItems().addAll(FiliereDAO.retournerFiliere());
    }

    private void filieres(ComboBox<String> retirerFiliere) {
        if (tableau.getSelectionModel().getSelectedItem() != null){
            retirerFiliere.getItems().removeAll(FiliereDAO.retournerFiliere());
            ResponsaleFiliere rf =tableau.getSelectionModel().getSelectedItem();
            retirerFiliere.getItems().addAll(FiliereDAO.retournerFiliereResponsable(rf));
        }else
            resultat.setText("Veuiller sélectionné\nune filiére SVP");
    }

    public void mesFiliere(){
        filieres(mesFilieres);
    }

    public void mesFilieresARetirer(){
        filieres(retirerFiliere);
    }

    public void ajoutModuleAFiliere(){
        if (tableau.getSelectionModel().getSelectedItem() != null){
            ResponsaleFiliere rf = tableau.getSelectionModel().getSelectedItem();
            if (FiliereDAO.verifResponsableFiliere(ResponsableFiliereDAO.renvoieIdResponsable(rf))){
                resultat.setText("Cette Filiére est\n déja géré par ce formateur");
            }else{
                if (FiliereDAO.ajoutFiliereAResponsable(FiliereDAO.renvoieIDFiliere(ajoutFilieres.getValue()),ResponsableFiliereDAO.renvoieIdResponsable(rf))){
                    resultat.setText("Module ajouté au filiére");
                }else{
                    resultat.setText("Erreur: Module non ajouté");
                }
            }
        }
    }
    public void rechercherResponsable() {
        if (txtRecherche != null) {
            if (responsablefilieredao.read(Integer.parseInt(txtRecherche.getText())) != null) {
                responsaleFilieres.removeAll(responsaleFilieres);
                responsaleFilieres.add((ResponsaleFiliere) responsablefilieredao.read(Integer.parseInt(txtRecherche.getText())));
                tableau.setItems(responsaleFilieres);
            } else
                resultat.setText("Responsable non trouvé");
        } else
            resultat.setText("Veuiller saisir un ID \n a recherché");
    }

    public void supprimerResponsable() {
        if (tableau.getSelectionModel().getSelectedItem() != null) {
            ResponsaleFiliere responsaleFiliere = tableau.getSelectionModel().getSelectedItem();
            if (responsablefilieredao.delete(responsaleFiliere)) {
                resultat.setText("Responsable Supprimé !!!");
                actualise();
            } else
                resultat.setText("Error: Responsable non supprimé");
        } else
            resultat.setText("Veuillez sélectionné\nun responsable SVP");
    }

    public void confirmationSuppression(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Attention vous risquez de supprimer une ligne",ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK){
            supprimerResponsable();
        }
    }

    public void retourner() {
        Stage stage = (Stage) btnRetourner.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("/projet/java/view/acceuil.fxml")));
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


