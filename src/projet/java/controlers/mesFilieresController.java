package projet.java.controlers;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projet.java.models.classes.Etudiant;
import projet.java.models.classes.Filiere;
import projet.java.models.classes.Module;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class mesFilieresController implements Initializable {

    @FXML
    private TableView<Filiere> tableau;

    @FXML
    public TableColumn<Filiere, Integer> id;

    @FXML
    public TableColumn<Filiere, String> nom;

    @FXML
    private TextField numFiliere;

    @FXML
    private TextField intitule;

    @FXML
    private TextArea resultat;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtRecherche;

    @FXML
    private Button btnRetourner;

    @FXML
    private ComboBox<String> mesModules;
    @FXML
    private ComboBox<String> ajoutModules;
    @FXML
    private Button btnAjouterModule;
    @FXML
    private ComboBox<String> retirerModule;
    @FXML
    private Button btnRetirer;


    ProduitDAOFactory produitDAOFactory = new ProduitDAOFactory();
    FiliereDAO filiereDAO = (FiliereDAO) produitDAOFactory.getProduitDAO("FILIEREDAO");

    public mesFilieresController()
    {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("idFiliere"));
        nom.setCellValueFactory(new PropertyValueFactory<>("intituleFiliere"));
        tableau.setItems(filieres);
    }

    private ObservableList<Filiere> filieres = FXCollections.observableArrayList(
            FiliereDAO.readList()
    );



    public void actualise(){
        ObservableList<Filiere> filieresActualise = FXCollections.observableArrayList(
                FiliereDAO.readList()
        );
        tableau.setItems(filieresActualise);
    }

    public void inscrireFiliere(){
        Filiere filiere = new Filiere(Integer.parseInt(numFiliere.getText()),intitule.getText());
        if (FiliereDAO.verifFiliere(Integer.parseInt(numFiliere.getText()))){
            resultat.setText("Error: Filiére non inscrit , ID déja existant");
        }else{
            if (filiereDAO.create(filiere)){
                numFiliere.setText("");
                intitule.setText("");
                resultat.setText("Filiére inscrite");
            }else {
                resultat.setText("Error: Filiéré non inscrite");
            }
        }
    }

    public void afficherFiliereUpdate(){
        if (tableau.getSelectionModel().getSelectedItem() != null){
            Filiere filiereSelectionne = tableau.getSelectionModel().getSelectedItem();
            numFiliere.setText(String.valueOf(filiereSelectionne.getIdFiliere()));
            intitule.setText(String.valueOf(filiereSelectionne.getIntituleFiliere()));
            btnUpdate.setDisable(false);
            mesModules.setDisable(false);
            ajoutModules.setDisable(false);
            btnAjouterModule.setDisable(false);
            retirerModule.setDisable(false);
            btnRetirer.setDisable(false);
        }
    }

    public void mesModules(){
        modules(mesModules);
    }

    public void lesModules(){
        if (tableau.getSelectionModel().getSelectedItem() != null){
            ajoutModules.getItems().removeAll(ModuleDAO.retournerModule());
            ajoutModules.getItems().addAll(ModuleDAO.retournerModule());
        }else
            resultat.setText("Veuiller sélectionné\nun module SVP");
    }

    public void mesModulesARetirer(){
        modules(retirerModule);
    }

    private void modules(ComboBox<String> retirerModule) {
        if (tableau.getSelectionModel().getSelectedItem() != null){
            retirerModule.getItems().removeAll(ModuleDAO.retournerModule());
            Filiere filiere = tableau.getSelectionModel().getSelectedItem();
            retirerModule.getItems().addAll(ModuleDAO.retournerModuleFiliere(filiere));
        }else
            resultat.setText("Veuiller sélectionné\nun module SVP");
    }

    public void updateFiliere(){
        if (tableau.getSelectionModel().getSelectedItem() != null){
            Filiere filiere = tableau.getSelectionModel().getSelectedItem();
            if (filiereDAO.update(filiere,FiliereDAO.renvoieIDFiliere(filiere.getIntituleFiliere()))){

            }
        }
    }

    public void initialiser(){
        btnUpdate.setDisable(true);
    }

    public void rechercheFiliere(){
        if (txtRecherche.getText() != null){
            if (filiereDAO.read(Integer.parseInt(txtRecherche.getText())) != null){
                filieres.removeAll(filieres);
                filieres.add(filiereDAO.read(Integer.parseInt(txtRecherche.getText())));
                tableau.setItems(filieres);
            }
            else
                resultat.setText("Etudiant non trouvé");
        }
    }

    public void supprimerEtudiant(){
        Filiere filiereSelectionne = tableau.getSelectionModel().getSelectedItem();
        if (filiereDAO.delete(filiereSelectionne)){
            resultat.setText("Filiére Supprimée !!!\n Actualisé SVP");
        }else
            resultat.setText("Error: Filiére non supprimée");
    }

    public void retourner(){
        Stage stage = (Stage) btnRetourner.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("/projet/java/view/acceuil.fxml")));
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ajoutModuleAFiliere(){
        if (tableau.getSelectionModel().getSelectedItem() != null){
            Filiere filiere = tableau.getSelectionModel().getSelectedItem();
            if (ModuleDAO.verifModuleFiliere(FiliereDAO.renvoieIDFiliere(filiere.getIntituleFiliere()))){
                resultat.setText("Ce module existe\n déja sur ce filiére");
            }else{
                if (ModuleDAO.ajoutModuleAFiliere(FiliereDAO.renvoieIDFiliere(filiere.getIntituleFiliere()),ModuleDAO.renvoieIDModule(ajoutModules.getValue()))){
                    resultat.setText("Module ajouté au filiére");
                }else{
                    resultat.setText("Erreur: Module non ajouté");
                }
            }
        }
    }

    public void deleteModuleInFiliere(){
        if (tableau.getSelectionModel().getSelectedItem() != null){
            if (ModuleDAO.deleteModuleInFiliere(retirerModule.getValue())){
                resultat.setText("Module retiré au filiére");
            }else
                resultat.setText("Erreur : Module non retiré");
        }else {
            resultat.setText("Veuiller sélectionné\nun module SVP");
        }
    }


}


