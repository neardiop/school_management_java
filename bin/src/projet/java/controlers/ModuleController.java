package projet.java.controlers;

import dao.FiliereDAO;
import dao.ModuleDAO;
import dao.NoteDAO;
import dao.ProduitDAOFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import projet.java.models.classes.Module;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModuleController implements Initializable {

    @FXML
    public TableColumn<Module, Integer> id;
    @FXML
    public TableColumn<Module, String> nom;
    @FXML
    public TableColumn<Module, Integer> coefficient;
    ProduitDAOFactory produitDAOFactory = new ProduitDAOFactory();
    ModuleDAO moduleDAO = (ModuleDAO) produitDAOFactory.getProduitDAO("MODULEDAO");
    FiliereDAO filiereDAO = (FiliereDAO) produitDAOFactory.getProduitDAO("FILIEREDAO");
    @FXML
    private TableView<Module> tableau;
    @FXML
    private TextField numModule;
    @FXML
    private TextField intitule;
    @FXML
    private TextField coeff;
    @FXML
    private TextArea resultat;
    @FXML
    private Button btnValider;
    @FXML
    private RadioButton choixInscrire;
    @FXML
    private RadioButton choixMiseAJour;
    @FXML
    private ToggleGroup group;
    @FXML
    private TextField txtRecherche;
    @FXML
    private Button btnRetourner;
    @FXML
    private Button importer;
    @FXML
    private Button btnBrowser;
    @FXML
    private TextField txtBrowser;
    @FXML
    private Button btnVoirNotes;

    private ObservableList<Module> modules = FXCollections.observableArrayList(
            ModuleDAO.readList()
    );
    private ObservableList<Module> modulesF = FXCollections.observableArrayList(
            ModuleDAO.readListM(FiliereDAO.renvoieIDFiliere(AcceuilResponsableController.getFiliereSelected()))
    );

    public ModuleController() {

    }

    public void fileUploed() {
        EtudiantController.upload(btnBrowser, txtBrowser, importer);
    }

    public void importerXL() {
        if (ModuleDAO.importerXL(txtBrowser.getText())) {
            txtBrowser.setText(null);
            actualise();
            resultat.setText("Importation reussie !!!");
        } else {
            resultat.setText("Erreur de l'importation");
        }
    }

    public void exporterXL() {
        if (ModuleDAO.exportXL()) {
            resultat.setText("Exportation reussie !!!");
        } else
            resultat.setText("Erreur d'exportation");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnVoirNotes.setDisable(true);
        importer.setDisable(true);
        id.setCellValueFactory(new PropertyValueFactory<>("idModule"));
        nom.setCellValueFactory(new PropertyValueFactory<>("intitule"));
        coefficient.setCellValueFactory(new PropertyValueFactory<>("coef"));
        if (AcceuilResponsableController.getFiliereSelected() == null) {
            tableau.setItems(modules);
        } else
            tableau.setItems(modulesF);
    }

    public void actualise() {
        ObservableList<Module> modulesActualise = FXCollections.observableArrayList(
                ModuleDAO.readList()
        );
        tableau.setItems(modulesActualise);
    }

    public void inscrireModule() {
        Module module = new Module(Integer.parseInt(numModule.getText()), intitule.getText(), Integer.parseInt(coeff.getText()));
        if (ModuleDAO.verifModule(Integer.parseInt(numModule.getText()))) {
            resultat.setText("Error: Module non inscrit ,\n ID déja existant");
        } else {
            if (moduleDAO.create(module)) {
                numModule.setText("");
                intitule.setText("");
                coeff.setText("");
                resultat.setText("Module inscrit");
                actualise();
            } else {
                resultat.setText("Error: Module non inscrit");
            }
        }
    }

    public void afficherModuleUpdate() {
        if (tableau.getSelectionModel().getSelectedItem() != null) {
            btnVoirNotes.setDisable(false);
            Module moduleSelectionne = tableau.getSelectionModel().getSelectedItem();
            numModule.setText(String.valueOf(moduleSelectionne.getIdModule()));
            intitule.setText(String.valueOf(moduleSelectionne.getIntitule()));
            coeff.setText(String.valueOf(moduleSelectionne.getCoef()));
        }
    }

    public void updateModule() {
        if (tableau.getSelectionModel().getSelectedItem() != null) {
            Module module = tableau.getSelectionModel().getSelectedItem();
            Module module1 = new Module(Integer.parseInt(numModule.getText()), intitule.getText(), Integer.parseInt(coeff.getText()));
            if (moduleDAO.update(module1, ModuleDAO.renvoieIDModule(module.getIntitule()))) {
                numModule.setText(null);
                intitule.setText(null);
                coeff.setText(null);
                resultat.setText("Mise à jour réussie !!!");
                actualise();
            } else {
                resultat.setText("Erreur de mise à jour");
            }
        }
    }

    public void decisionValider() {
        if (group.getSelectedToggle() == choixInscrire) {
            inscrireModule();
        } else if (group.getSelectedToggle() == choixMiseAJour) {
            updateModule();
        } else {
            resultat.setText("Veuiller séléctionner une action");
        }
    }

    public void rechercheModule() {
        if (txtRecherche.getText() != null) {
            if (moduleDAO.read(Integer.parseInt(txtRecherche.getText())) != null) {
                modules.removeAll(modules);
                modules.add(moduleDAO.read(Integer.parseInt(txtRecherche.getText())));
                tableau.setItems(modules);
            } else
                resultat.setText("Module non trouvé");
        }
    }

    public void supprimerModule() {
        Module moduleSelectionne = tableau.getSelectionModel().getSelectedItem();
        if (moduleDAO.delete(moduleSelectionne)) {
            resultat.setText("Module Supprimé !!!\n Actualisé SVP");
        } else
            resultat.setText("Error: Module non supprimé");
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

    public void displayNotes() {
        if (tableau.getSelectionModel().getSelectedItem() != null) {
            Module module = tableau.getSelectionModel().getSelectedItem();
            NoteDAO.notesPDF(module.getIntitule());
        }
    }
}


