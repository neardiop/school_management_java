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
import projet.java.models.classes.Note;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

public class NoteController implements Initializable {

    @FXML
    private TableView<Etudiant> tableau;

    @FXML
    public TableColumn<Etudiant, Integer> id;

    @FXML
    public TableColumn<Etudiant, String> nom;

    @FXML
    public TableColumn<Etudiant, String> prenom;

    @FXML
    public TableColumn<Etudiant, String> sexe;

    @FXML
    public TextField idNote;

    @FXML
    public TextField maNote;

    @FXML
    public TextArea commentaire;

    @FXML
    public Button btnValider;

    @FXML
    public RadioButton choixInserer;

    @FXML
    public RadioButton choixMiseAJour;

    @FXML
    public ToggleGroup group;

    @FXML
    public TableView<Note> tableNote;
    @FXML
    public  TableColumn<Note,Integer> idNot;
    @FXML
    public TableColumn<Note,Double> not;
    @FXML
    public TableColumn<Note,String> comm;
    @FXML
    public TableColumn<Note,Boolean> val;
    @FXML
    public Button btnRetourner;
    @FXML
    public TextArea resultat;
    @FXML
    public Label myn;


    public void exporterXL(){
        if (NoteDAO.exportXL(AcceuilFController.getModule(),EvaluationController.getEvaluationSelectionne().getIdEvaluation())){
            resultat.setText("Exportation reussie !!!");
        }else
            resultat.setText("Erreur d'exportation");
    }

    public void disableButton(boolean b){
        choixInserer.setDisable(b);
        choixMiseAJour.setDisable(b);
        idNote.setDisable(b);
        maNote.setDisable(b);
        commentaire.setDisable(b);
        btnValider.setDisable(b);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableButton(true);
        tableNote.setDisable(true);
        tableau.setEditable(true);
        id.setCellValueFactory(new PropertyValueFactory<>("numEtudiant"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        tableau.setItems(etudiantsE);

        idNot.setCellValueFactory(new PropertyValueFactory<>("idNote"));
        not.setCellValueFactory(new PropertyValueFactory<>("note"));
        comm.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
        val.setCellValueFactory(new PropertyValueFactory<>("valider"));
        tableNote.setItems(notes);
    }

    public HashSet<Etudiant> mesEtudiant(){
        return FormateurDAO.formateurEtudiant(ModuleDAO.renvoieIDModule(AcceuilFController.getModule()));
    }

    private ObservableList<Etudiant> etudiantsE = FXCollections.observableArrayList(
            mesEtudiant()
    );

    public HashSet<Note> mesNotes(){
        return NoteDAO.readList(EvaluationDAO.renvoieIDEvaluation(EvaluationController.getEvaluationSelectionne()));
    }
    private ObservableList<Note> notes = FXCollections.observableArrayList(
            mesNotes()
    );

    public void actualise(){
        mesNotes();
        ObservableList<Note> notes = FXCollections.observableArrayList(
                mesNotes()
        );
        tableNote.setItems(notes);
    }

    public void etudiantSelectionne(){
        if (tableau.getSelectionModel().getSelectedItem() != null){
            int idEtudiant = EtudiantDAO.renvoieIdEtudiant(tableau.getSelectionModel().getSelectedItem());
            double moyG = NoteDAO.renvoiTousLesNoteFoisCoef(EvaluationController.getEvaluationSelectionne().getModule(),idEtudiant);
            myn.setText(String.valueOf(moyG));
            disableButton(false);
            tableNote.setDisable(false);
            tableNote.getItems().removeAll(notes);
            Note note = NoteDAO.read(EtudiantDAO.renvoieIdEtudiant(tableau.getSelectionModel().getSelectedItem()),
                    EvaluationDAO.renvoieIDEvaluation(EvaluationController.getEvaluationSelectionne()));
            if (note != null){
                tableNote.getItems().add(note);
                idNote.setText(String.valueOf(note.getIdNote()));
                maNote.setText(String.valueOf(note.getNote()));
                commentaire.setText(note.getCommentaire());
                choixInserer.setDisable(true);
                group.selectToggle(choixMiseAJour);
            }else{
                resultat.setText("Cet étudiant n'as pas encore de note sur cette évaluation");
            }
        }
    }

    public void insererNote(double noteCoef){
        boolean validationNote = false;
        if (Integer.valueOf(maNote.getText()) < 10){
            validationNote=false;
        }else if (Integer.valueOf(maNote.getText()) >= 10){
            validationNote=true;
        }
        Note note = new Note(Integer.parseInt(idNote.getText()),EvaluationDAO.renvoieIDEvaluation(EvaluationController.getEvaluationSelectionne()), EtudiantDAO.renvoieIdEtudiant(tableau.getSelectionModel().getSelectedItem()),
                Double.parseDouble(maNote.getText()),commentaire.getText(),validationNote);
        if (NoteDAO.create(note,noteCoef)){
            resultat.setText("Note enregistré \navec succés");
            reset();
        }else
            resultat.setText("Erreur : \nnote non enregistré");
    }

    public void updateNote(double noteCoef){
        if (tableau.getSelectionModel().getSelectedItem() != null){
            Note note = tableNote.getItems().get(0);
            boolean validationNote = false;
            if (Double.parseDouble(maNote.getText()) < 10){
                validationNote=false;
            }else if (Double.parseDouble(maNote.getText()) >= 10){
                validationNote=true;
            }
            Note noteAUpdate = new Note(Integer.parseInt(idNote.getText()),EvaluationDAO.renvoieIDEvaluation(EvaluationController.getEvaluationSelectionne()), EtudiantDAO.renvoieIdEtudiant(tableau.getSelectionModel().getSelectedItem()),
                    Double.parseDouble(maNote.getText()),commentaire.getText(),validationNote);
            if (NoteDAO.update(noteAUpdate,NoteDAO.renvoieIDNote(note),noteCoef)){
                resultat.setText("Note modifié avec succés");
                reset();
                Note noteUpdate = NoteDAO.read(EtudiantDAO.renvoieIdEtudiant(tableau.getSelectionModel().getSelectedItem()),
                        EvaluationDAO.renvoieIDEvaluation(EvaluationController.getEvaluationSelectionne()));
                tableNote.getItems().removeAll(notes);
                tableNote.getItems().add(noteUpdate);
                choixInserer.setDisable(false);
                group.selectToggle(null);
                int idEtudiant = tableau.getSelectionModel().getSelectedItem().getNumEtudiant();
                double moyG = NoteDAO.renvoiTousLesNoteFoisCoef(EvaluationController.getEvaluationSelectionne().getModule(),idEtudiant);
                myn.setText(String.valueOf(moyG));

            }else{
                resultat.setText("Erreur de mise à jour");
            }

        }
    }

    public void reset(){
        idNote.setText(null);
        maNote.setText(null);
        commentaire.setText(null);
    }

    public void decisionBtnValider(){
        if (group.getSelectedToggle() == choixInserer){
            insererNote(noteFoisCoef());
        }else if (group.getSelectedToggle() == choixMiseAJour){
            updateNote(noteFoisCoef());
        }
    }

    public void retourner(){
        Stage stage = (Stage) btnRetourner.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("/projet/java/view/evaluation.fxml")));
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double noteFoisCoef(){
        double coef = EvaluationDAO.renvoieCoefEvaluation(EvaluationDAO.renvoieIDEvaluation(EvaluationController.getEvaluationSelectionne()));
        double noteCoef = Double.parseDouble(maNote.getText());
        return (coef*noteCoef);
    }
}
