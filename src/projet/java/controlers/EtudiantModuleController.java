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
import javafx.stage.Stage;
import projet.java.models.classes.Etudiant;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.HashSet;
import java.util.ResourceBundle;

public class EtudiantModuleController implements Initializable {

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
    private Button btnInscrire;
    private ObservableList<Etudiant> etudiants = FXCollections.observableArrayList(
            EtudiantDAO.readList()
    );
    private ObservableList<Etudiant> etudiantsE = FXCollections.observableArrayList(
            mesEtudiant()
    );

    public EtudiantModuleController() {

    }

    static void displayEtudiant(TableView<Etudiant> tableau, TextField txtNumEtudiant, TextField txtNom, TextField txtPrenom, TextField txtSexe, TextField txtTelephone, TextField txtAdresse, TextField txtEmail, ComboBox<String> filiereChoice, DatePicker dateNaissance) {
        Etudiant etudiantSelectionne = tableau.getSelectionModel().getSelectedItem();
        txtNumEtudiant.setText(String.valueOf(etudiantSelectionne.getNumEtudiant()));
        txtNom.setText(etudiantSelectionne.getNom());
        txtPrenom.setText(etudiantSelectionne.getPrenom());
        txtSexe.setText(String.valueOf(etudiantSelectionne.getSexe()));
        txtTelephone.setText(etudiantSelectionne.getTelephone());
        txtAdresse.setText(etudiantSelectionne.getAdresse());
        txtEmail.setText(etudiantSelectionne.getEmail());
        filiereChoice.setValue(etudiantSelectionne.getFiliere());
        Date deadlineDatePrompt = etudiantSelectionne.getDateNaissance();
        dateNaissance.setValue(deadlineDatePrompt.toLocalDate());
    }

    static void searchEtudiant(PersonneDAO etudiantDao, TextField txtRecherche, ObservableList<Etudiant> etudiants, TableView<Etudiant> tableau) {
        if (etudiantDao.read(Integer.parseInt(txtRecherche.getText())) != null) {
            etudiants.removeAll(etudiants);
            etudiants.add((Etudiant) etudiantDao.read(Integer.parseInt(txtRecherche.getText())));
            tableau.setItems(etudiants);
        } else {
            System.out.println("etudiant non trouvé");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableau.setEditable(true);

        filiereChoice.getItems().addAll(FiliereDAO.retournerFiliere());

        id.setCellValueFactory(new PropertyValueFactory<>("numEtudiant"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateDeNaissance.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        filiereEtudiant.setCellValueFactory(new PropertyValueFactory<>("filiere"));
        tableau.setItems(etudiantsE);

        btnInscrire.setDisable(true);
        choixInscrire.setDisable(true);
        choixMiseAJour.setDisable(false);

    }

    public HashSet<Etudiant> mesEtudiant() {
        return FormateurDAO.formateurEtudiant(ModuleDAO.renvoieIDModule(AcceuilFController.getModule()));
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
                txtAdresse.setText(" ");
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
        displayEtudiant(tableau, txtNumEtudiant, txtNom, txtPrenom, txtSexe, txtTelephone, txtAdresse, txtEmail, filiereChoice, dateNaissance);
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
            txtEmail.setText(" ");
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
        searchEtudiant(etudiantDao, txtRecherche, etudiants, tableau);
    }

    public void supprimerEtudiant() {
        Etudiant etudiantSelectionne = tableau.getSelectionModel().getSelectedItem();
        if (etudiantDao.delete(etudiantSelectionne)) {
            resultat.setText("Etudiant Supprimé !!!\n Actualisé SVP");
        } else
            resultat.setText("Error: Etudiant non supprimé");
    }

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

    public void decisionButtonValider() {
        if (group.getSelectedToggle() == choixInscrire) {
            inscrireEtudiant();
        } else if (group.getSelectedToggle() == choixMiseAJour) {
            updateEtudiant();
        } else {
            resultat.setText("Veuiller cocher \n un bouton SVP");
        }
    }
}


