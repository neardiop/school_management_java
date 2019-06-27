package projet.java.controlers;

import dao.FormateurDAO;
import dao.ModuleDAO;
import dao.PersonneDAO;
import dao.PersonneDAOFactory;
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
import projet.java.models.classes.Formateur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FormateurController implements Initializable {

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
    public TableColumn<Formateur, Boolean> statut;
    PersonneDAOFactory personneDAOFactory = new PersonneDAOFactory();
    PersonneDAO formateurDao = personneDAOFactory.getPersonneDAO("FORMATEURDAO");
    @FXML
    private TableView<Formateur> tableau;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField txtNumFormateur;
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
    private ComboBox<Boolean> txtStatut;
    @FXML
    private RadioButton choixInscrire;
    @FXML
    private RadioButton choixMiseAJour;
    @FXML
    private RadioButton choixDetailFormateur;
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
    private Pane detailFormateur;
    @FXML
    private ComboBox<String> ajoutModules;
    @FXML
    private ComboBox<String> mesModules;
    @FXML
    private ComboBox<String> retirerModule;
    @FXML
    private Button importer;
    @FXML
    private Button btnBrowser;
    @FXML
    private TextField txtBrowser;
    private ObservableList<Formateur> formateurs = FXCollections.observableArrayList(
            FormateurDAO.readList()
    );


    public FormateurController() {

    }

    public void fileUploed() {
        EtudiantController.upload(btnBrowser, txtBrowser, importer);
    }

    public void importerXL() {
        if (FormateurDAO.importerXL(txtBrowser.getText())) {
            txtBrowser.setText(null);
            actualise();
            resultat.setText("Importation reussie !!!");
        } else {
            resultat.setText("Erreur de l'importation");
        }
    }

    public void exporterXL() {
        if (FormateurDAO.exportXL()) {
            resultat.setText("Exportation reussie !!!");
        } else
            resultat.setText("Erreur d'exportation");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        importer.setDisable(true);

        txtStatut.getItems().add(true);
        txtStatut.getItems().add(false);

        id.setCellValueFactory(new PropertyValueFactory<>("idFormateur"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        password.setCellValueFactory(new PropertyValueFactory<>("pwd"));
        statut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        tableau.setItems(formateurs);

        if (AcceuilResponsableController.getFiliereSelected() == null) {
            txtStatut.setDisable(false);
        } else {
            txtStatut.setDisable(true);
        }

        System.out.println(AcceuilResponsableController.getFiliereSelected());
    }

    public void actualise() {
        ObservableList<Formateur> formateurActualise = FXCollections.observableArrayList(
                FormateurDAO.readList()
        );
        tableau.setItems(formateurActualise);
    }

    public void inscrireFormateur() {
        Formateur formateur = new Formateur(txtNom.getText(), txtPrenom.getText(), txtAdresse.getText(), txtEmail.getText(),
                txtTelephone.getText(), txtSexe.getText().charAt(0), txtLogin.getText(), txtPwd.getText(), Integer.parseInt(txtNumFormateur.getText()),
                txtStatut.getSelectionModel().getSelectedItem());
        if (FormateurDAO.verifFormateur(Integer.parseInt(txtNumFormateur.getText()))) {
            resultat.setText("Error: Formateur non inscrit , ID déja existant");
        } else {
            if (AcceuilResponsableController.getFiliereSelected() != null) {
                if (FormateurDAO.createR(formateur)) {
                    txtNumFormateur.setText("");
                    txtNom.setText("");
                    txtPrenom.setText("");
                    txtEmail.setText("");
                    txtAdresse.setText("");
                    txtTelephone.setText("");
                    txtSexe.setText("");
                    txtLogin.setText("");
                    txtPwd.setText("");
                    txtStatut.setValue(null);
                    resultat.setText("Formateur inscrit");
                    actualise();
                } else {
                    resultat.setText("Error: Formateur  non inscrit");
                }
            } else {
                if (formateurDao.create(formateur)) {
                    txtNumFormateur.setText("");
                    txtNom.setText("");
                    txtPrenom.setText("");
                    txtEmail.setText("");
                    txtAdresse.setText("");
                    txtTelephone.setText("");
                    txtSexe.setText("");
                    txtLogin.setText("");
                    txtPwd.setText("");
                    txtStatut.setValue(null);
                    resultat.setText("Formateur inscrit!!!");
                    actualise();
                } else {
                    resultat.setText("Error: Formateur  non inscrit");
                }
            }
        }
    }

    public void afficherFormateurAUpdate() {
        Formateur formateur = tableau.getSelectionModel().getSelectedItem();
        txtNumFormateur.setText(String.valueOf(formateur.getIdFormateur()));
        txtNom.setText(formateur.getNom());
        txtPrenom.setText(formateur.getPrenom());
        txtSexe.setText(String.valueOf(formateur.getSexe()));
        txtTelephone.setText(formateur.getTelephone());
        txtAdresse.setText(formateur.getAdresse());
        txtEmail.setText(formateur.getEmail());
        txtLogin.setText(formateur.getLogin());
        txtPwd.setText(formateur.getPwd());
        txtStatut.setValue(formateur.isStatut());
    }

    public void updateFormateur() {
        Formateur formateurAUpdate = new Formateur(txtNom.getText(), txtPrenom.getText(), txtAdresse.getText(), txtEmail.getText(),
                txtTelephone.getText(), txtSexe.getText().charAt(0), txtLogin.getText(), txtPwd.getText(), Integer.parseInt(txtNumFormateur.getText()),
                txtStatut.getSelectionModel().getSelectedItem());
        Formateur formateur = tableau.getSelectionModel().getSelectedItem();
        if (formateurDao.update(formateurAUpdate, FormateurDAO.renvoieIDFormateur(formateur))) {
            resultat.setText("Formateur mis à jour");
            txtNumFormateur.setText("");
            txtNom.setText("");
            txtPrenom.setText("");
            txtEmail.setText("");
            txtAdresse.setText("");
            txtTelephone.setText("");
            txtSexe.setText("");
            txtLogin.setText("");
            txtPwd.setText("");
            txtStatut.setValue(null);
            actualise();
        } else
            resultat.setText("Erreur de mise à jour");
    }

    public void decisionButtonValider() {
        if (group.getSelectedToggle() == choixInscrire) {
            inscrireFormateur();
        } else if (group.getSelectedToggle() == choixMiseAJour) {
            updateFormateur();
        } else {
            resultat.setText("Veuiller cocher\n un bouton SVP");
        }
    }

    public void detailFormateur() {
        if (tableau.getSelectionModel().getSelectedItem() != null) {
            if (group.getSelectedToggle() == choixDetailFormateur) {
                if (FormateurDAO.validiteFormateur(tableau.getSelectionModel().getSelectedItem())) {
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
                } else {
                    resultat.setText("Formateur non valide !!! Contacter l'administrateur");
                }
            }
        } else
            resultat.setText("Veuiller sélectionné\nun formateur SVP");
    }

    public void attributInscrire() {
        if (group.getSelectedToggle() == choixInscrire || group.getSelectedToggle() == choixMiseAJour) {
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

    public void lesModules() {
        ajoutModules.getItems().removeAll(ModuleDAO.retournerModule());
        ajoutModules.getItems().addAll(ModuleDAO.retournerModule());
    }

    private void modules(ComboBox<String> retirerModule) {
        if (tableau.getSelectionModel().getSelectedItem() != null) {
            retirerModule.getItems().removeAll(ModuleDAO.retournerModule());
            Formateur formateur = tableau.getSelectionModel().getSelectedItem();
            retirerModule.getItems().addAll(ModuleDAO.retournerModuleFormateur(formateur));
        } else
            resultat.setText("Veuiller sélectionné\nun module SVP");
    }

    public void mesModules() {
        modules(mesModules);
    }

    public void mesModulesARetirer() {
        modules(retirerModule);
    }

    public void ajoutModuleAFormateur() {
        if (tableau.getSelectionModel().getSelectedItem() != null) {
            if (ModuleDAO.verifIdModuleInFiliere(ModuleDAO.renvoieIDModule(ajoutModules.getValue()))) {
                Formateur formateur = tableau.getSelectionModel().getSelectedItem();
                if (ModuleDAO.verifModuleFormateur(FormateurDAO.renvoieIDFormateur(formateur))) {
                    resultat.setText("Ce module est déja \nenseigné par ce formateur");
                } else {
                    if (ModuleDAO.ajoutModuleAFormateur(FormateurDAO.renvoieIDFormateur(formateur), ModuleDAO.renvoiIdModuleFiliere(ModuleDAO.renvoieIDModule(ajoutModules.getValue())))) {
                        resultat.setText("Module ajouté au formateur");
                    } else {
                        resultat.setText("Erreur: Module non ajouté");
                    }
                }
            } else {
                resultat.setText("Ce module n'est pas\nencore associé à une filiére");
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
    }

    public void confirmationSuppression() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Attention vous risquez de supprimer une ligne", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            supprimerFormateur();
        }
    }

    public void retourner() {
        Stage stage = (Stage) btnRetourner.getScene().getWindow();
        Scene scene = null;
        try {
            if (LoginControlller.getConnect() == "ADMIN") {
                scene = new Scene(FXMLLoader.load(getClass().getResource("/projet/java/view/acceuil.fxml")));
            } else if (LoginControlller.getConnect() == "RESPONSABLE") {
                scene = new Scene(FXMLLoader.load(getClass().getResource("/projet/java/view/AcceuilResponsable.fxml")));
            }
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


