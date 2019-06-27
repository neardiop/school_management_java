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
import projet.java.models.classes.Administrateur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ParametreController implements Initializable {

    @FXML
    public TableView<Administrateur> tableau;
    @FXML
    public TableColumn<Administrateur, Integer> id;
    @FXML
    public TableColumn<Administrateur, String> nom;
    @FXML
    public TableColumn<Administrateur, String> prenom;
    @FXML
    public TableColumn<Administrateur, String> sexe;
    @FXML
    public TableColumn<Administrateur, String> login;
    @FXML
    public TableColumn<Administrateur, String> pwd;
    PersonneDAOFactory personneDAOFactory = new PersonneDAOFactory();
    PersonneDAO administrateurdao = personneDAOFactory.getPersonneDAO("ADMINISTRATEURDAO");
    @FXML
    private TextField txtNumAdmin;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtSexe;
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtPwd;
    @FXML
    private TextArea resultat;
    @FXML
    private TextField txtRecherche;
    @FXML
    private Button btnRetourner;
    @FXML
    private RadioButton choixInscrire;
    @FXML
    private RadioButton choixMiseAJour;
    @FXML
    private ToggleGroup group;

    @FXML
    private RadioButton radioEtu;

    @FXML
    private RadioButton radioForm;

    @FXML
    private ToggleGroup group1;

    private ObservableList<Administrateur> administrateurs = FXCollections.observableArrayList(
            AdminDAO.readList()
    );

    public ParametreController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        id.setCellValueFactory(new PropertyValueFactory<>("numAdmin"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        pwd.setCellValueFactory(new PropertyValueFactory<>("pwd"));
        tableau.setItems(administrateurs);
    }

    public void actualise() {
        ObservableList<Administrateur> adminActualise = FXCollections.observableArrayList(
                AdminDAO.readList()
        );
        tableau.setItems(adminActualise);
    }

    public void inscrireAdmin() {
        Administrateur administrateur = new Administrateur(txtNom.getText(), txtPrenom.getText(), txtSexe.getText().charAt(0), txtLogin.getText(),
                txtPwd.getText(), Integer.parseInt(txtNumAdmin.getText()));
        if (AdminDAO.verifAdmin(Integer.parseInt(txtNumAdmin.getText()))) {
            resultat.setText("Error: Administrateur non inscrit , ID déja existant");
        } else {
            if (administrateurdao.create(administrateur)) {
                txtNumAdmin.setText("");
                txtNom.setText("");
                txtPrenom.setText("");
                txtSexe.setText("");
                txtLogin.setText("");
                txtPwd.setText("");
                txtSexe.setText("");
                resultat.setText("Administrateur inscrit");
                actualise();
            } else {
                resultat.setText("Error: Administrateur non inscrit");
            }
        }
    }

    public void afficherAdminAupdate() {
        Administrateur administrateurSelectionne = tableau.getSelectionModel().getSelectedItem();
        txtNumAdmin.setText(String.valueOf(administrateurSelectionne.getNumAdmin()));
        txtNom.setText(administrateurSelectionne.getNom());
        txtPrenom.setText(administrateurSelectionne.getPrenom());
        txtSexe.setText(String.valueOf(administrateurSelectionne.getSexe()));
        txtLogin.setText(administrateurSelectionne.getLogin());
        txtPwd.setText(administrateurSelectionne.getPwd());
    }


    public void updateAdmin() {
        Administrateur admin = new Administrateur(txtNom.getText(), txtPrenom.getText(), txtSexe.getText().charAt(0), txtLogin.getText(),
                txtPwd.getText(), Integer.parseInt(txtNumAdmin.getText()));
        Administrateur adminSelect = tableau.getSelectionModel().getSelectedItem();
        if (administrateurdao.update(admin, AdminDAO.renvoieIdAdmin(adminSelect))) {
            resultat.setText("Administrateur mis à jour");
            txtNumAdmin.setText("");
            txtNom.setText("");
            txtPrenom.setText("");
            txtSexe.setText("");
            txtLogin.setText("");
            txtPwd.setText("");
            actualise();
        } else
            resultat.setText("Erreur de mise à jour");
    }

    public void exportPDF() {
        if (group1.getSelectedToggle() == radioForm)
            FormateurDAO.formateurPdf();
        else if (group1.getSelectedToggle() == radioEtu)
            EtudiantDAO.etudiantPdf();
    }

   /* public void rechercherEtudiant() {
        if (etudiantDao.read(Integer.parseInt(txtRecherche.getText())) != null) {
            etudiants.removeAll(etudiants);
            etudiants.add((Etudiant) etudiantDao.read(Integer.parseInt(txtRecherche.getText())));
            tableau.setItems(etudiants);
        } else {
            System.out.println("etudiant non trouvé");
        }
    }

    public void supprimerEtudiant() {
        Etudiant etudiantSelectionne = tableau.getSelectionModel().getSelectedItem();
        if (etudiantDao.delete(etudiantSelectionne)) {
            resultat.setText("Etudiant Supprimé !!!");
            actualise();
        } else
            resultat.setText("Error: Etudiant non supprimé");
    }*/

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

    public void decisionButtonValider() {
        if (group.getSelectedToggle() == choixInscrire) {
            inscrireAdmin();
        } else if (group.getSelectedToggle() == choixMiseAJour) {
            updateAdmin();
        } else {
            resultat.setText("Veuiller cocher\n un bouton SVP");
        }
    }

}


