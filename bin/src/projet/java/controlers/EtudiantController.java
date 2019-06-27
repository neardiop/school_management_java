package projet.java.controlers;

import dao.EtudiantDAO;
import dao.FiliereDAO;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import projet.java.models.classes.Etudiant;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class EtudiantController implements Initializable {

    @FXML
    public TableColumn<Etudiant, Integer> id;
    @FXML
    public TableColumn<Etudiant, String> nom;
    @FXML
    public TableColumn<Etudiant, String> prenom;
    @FXML
    public TableColumn<Etudiant, java.util.Date> dateDeNaissance;
    @FXML
    public TableColumn<Etudiant, String> sexe;
    @FXML
    public TableColumn<Etudiant, String> telephone;
    @FXML
    public TableColumn<Etudiant, String> email;
    @FXML
    public TableColumn<Etudiant, String> adresse;
    @FXML
    public TableColumn<Etudiant, String> filiereEtudiant;
    PersonneDAOFactory personneDAOFactory = new PersonneDAOFactory();
    PersonneDAO etudiantDao = personneDAOFactory.getPersonneDAO("ETUDIANTDAO");
    @FXML
    private TableView<Etudiant> tableau;
    @FXML
    private TextField txtNumEtudiant;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private DatePicker dateNaissance;
    @FXML
    private TextField txtSexe;
    @FXML
    private TextField txtTelephone;
    @FXML
    private TextField txtAdresse;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox filiere;
    @FXML
    private TextArea resultat;
    @FXML
    private TextField txtRecherche;
    @FXML
    private Button btnRetourner;
    @FXML
    private ComboBox<String> filiereChoice;
    @FXML
    private RadioButton choixInscrire;
    @FXML
    private RadioButton choixMiseAJour;
    @FXML
    private ToggleGroup group;
    @FXML
    private Button importer;
    @FXML
    private Button btnBrowser;
    @FXML
    private TextField txtBrowser;
    @FXML
    private Button btnActualise;


    private ObservableList<Etudiant> etudiants = FXCollections.observableArrayList(
            EtudiantDAO.readList()
    );
    private ObservableList<Etudiant> etudiantsE = FXCollections.observableArrayList(
            EtudiantDAO.readListE(AcceuilResponsableController.getFiliereSelected())
    );

    public EtudiantController() {

    }

    static void upload(Button btnBrowser, TextField txtBrowser, Button importer) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Stage stage = (Stage) btnBrowser.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        txtBrowser.setText(file.getPath());
        importer.setDisable(false);
    }

    public void fileUploed() {
        upload(btnBrowser, txtBrowser, importer);
    }

    public void importerXL() {
        if (EtudiantDAO.importerXL(txtBrowser.getText())) {
            txtBrowser.setText(null);
            actualise();
            resultat.setText("Importation reussie !!!");
        } else {
            resultat.setText("Erreur de l'importation");
        }
    }

    public void exporterXL() {
        if (EtudiantDAO.exportXL()) {
            resultat.setText("Exportation reussie !!!");
        } else
            resultat.setText("Erreur d'exportation");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        filiereChoice.getItems().addAll(FiliereDAO.retournerFiliere());

        importer.setDisable(true);

        id.setCellValueFactory(new PropertyValueFactory<>("numEtudiant"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateDeNaissance.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        filiereEtudiant.setCellValueFactory(new PropertyValueFactory<>("filiere"));
        if (AcceuilResponsableController.getFiliereSelected() == null) {
            tableau.setItems(etudiants);
        } else {
            tableau.setItems(etudiantsE);
        }

        nom.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    public void actualise() {
        ObservableList<Etudiant> etudiantsActualise = FXCollections.observableArrayList(
                EtudiantDAO.readList()
        );
        tableau.setItems(etudiantsActualise);
    }

    public void inscrireEtudiant() {
        Etudiant etudiant = new Etudiant(txtNom.getText(), txtPrenom.getText(), Date.valueOf(dateNaissance.getValue()), txtSexe.getText().charAt(0), txtEmail.getText(),
                txtTelephone.getText(), txtAdresse.getText(), Integer.parseInt(txtNumEtudiant.getText()), filiereChoice.getValue());
        if (EtudiantDAO.verifEtudiant(Integer.parseInt(txtNumEtudiant.getText()))) {
            resultat.setText("Error: Etudiant non inscrit , ID déja existant");
        } else {
            if (etudiantDao.create(etudiant)) {
                txtNumEtudiant.setText("");
                txtNom.setText("");
                txtPrenom.setText("");
                txtEmail.setText("");
                txtAdresse.setText("");
                txtTelephone.setText("");
                txtSexe.setText("");
                dateNaissance.setValue(null);
                filiereChoice.setValue(null);
                resultat.setText("Etudiant inscrit");
                actualise();
            } else {
                resultat.setText("Error: Etudiant non inscrit");
            }
        }
    }

    public void afficherEtudiantAupdate() {
        EtudiantModuleController.displayEtudiant(tableau, txtNumEtudiant, txtNom, txtPrenom, txtSexe, txtTelephone, txtAdresse, txtEmail, filiereChoice, dateNaissance);

    }


    public void updateEtudiant() {
        Etudiant etudiant = new Etudiant(txtNom.getText(), txtPrenom.getText(), Date.valueOf(dateNaissance.getValue()), txtSexe.getText().charAt(0), txtEmail.getText(),
                txtTelephone.getText(), txtAdresse.getText(), Integer.parseInt(txtNumEtudiant.getText()), filiereChoice.getValue());
        Etudiant etudiantSelectionne = tableau.getSelectionModel().getSelectedItem();
        if (etudiantDao.update(etudiant, EtudiantDAO.renvoieIdEtudiant(etudiantSelectionne))) {
            resultat.setText("Etudiant mis à jour");
            txtNumEtudiant.setText("");
            txtNom.setText("");
            txtPrenom.setText("");
            txtEmail.setText("");
            txtAdresse.setText("");
            txtTelephone.setText("");
            txtSexe.setText("");
            filiere.setValue(null);
            dateNaissance.setValue(null);
            actualise();
        } else
            resultat.setText("Erreur de mise à jour");
    }

    public void initialiser() {
        tableau.getSelectionModel().setSelectionMode(null);
    }

    public void rechercherEtudiant() {
        EtudiantModuleController.searchEtudiant(etudiantDao, txtRecherche, etudiants, tableau);
    }

    public void supprimerEtudiant() {
        Etudiant etudiantSelectionne = tableau.getSelectionModel().getSelectedItem();
        if (etudiantDao.delete(etudiantSelectionne)) {
            resultat.setText("Etudiant Supprimé !!!");
            actualise();
        } else
            resultat.setText("Error: Etudiant non supprimé");
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

    public void decisionButtonValider() {
        if (group.getSelectedToggle() == choixInscrire) {
            inscrireEtudiant();
        } else if (group.getSelectedToggle() == choixMiseAJour) {
            updateEtudiant();
        } else {
            resultat.setText("Veuiller cocher\n un bouton SVP");
        }
    }

    public void onEditChanged(TableColumn.CellEditEvent<Etudiant, String> etudiantStringCellEditEvent) {
        Etudiant etudiant = tableau.getSelectionModel().getSelectedItem();
        etudiant.setNom(etudiantStringCellEditEvent.getNewValue());
    }

}


