package projet.java.controlers;

import dao.FormateurDAO;
import dao.ResponsableFiliereDAO;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import projet.java.utils.Connexion;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.sql.SQLException;

public class LoginControlller {

    @FXML
    private Button btnAnnuler;
    @FXML
    private TextField login;
    @FXML
    private PasswordField mdp;
    @FXML
    private Label erreur;
    @FXML
    private ToggleGroup group;
    @FXML
    private Pane adminPane;
    @FXML
    private Pane responsablePane;
    @FXML
    private Pane formateurPane;
    @FXML
    private Pane acceuilPane;
    @FXML
    private TextField mdpOublie;


    public TextField getLogin() {
        return login;
    }

    public void setLogin(TextField login) {
        this.login = login;
    }

    private static String user;

    public static String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void getUserAdmin(){
        setUser("ADMIN");
    }

    public void getUserFormateur(){
        setUser("FORMATEUR");
    }

    public void getUserResponsable(){
        setUser("RESPONSABLE");
    }

    public void close() {
        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
        stage.close();
    }

    public static String connect=null;

    public static String getConnect() {
        return connect;
    }

    public static void setConnect(String connect) {
        LoginControlller.connect = connect;
    }

    public void isValid() {
        try {
            if (user == null){
                erreur.setText("veuillez sélectionner un profit svp");
            }else{
                if (Connexion.checkUser(user,login.getText(),mdp.getText())){
                    if (user == "ADMIN"){
                        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/projet/java/view/acceuil.fxml")));
                        stage.setScene(scene);
                        setConnect("ADMIN");
                    }else if (user == "FORMATEUR"){
                        FormateurDAO.formateurConnect(login.getText());
                        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/projet/java/view/acceuilFormateur.fxml")));
                        stage.setScene(scene);
                    }else if(user == "RESPONSABLE"){
                        ResponsableFiliereDAO.responsableConnect(login.getText());
                        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/projet/java/view/AcceuilResponsable.fxml")));
                        stage.setScene(scene);
                        setConnect("RESPONSABLE");
                    }
                }else{
                    erreur.setText("Nom d'utilisateur ou mot de passe incorrect");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paneAdmin(){
        adminPane.setVisible(true);
        responsablePane.setVisible(false);
        formateurPane.setVisible(false);
        acceuilPane.setVisible(false);
    }

    public void paneResponsable(){
        responsablePane.setVisible(true);
        adminPane.setVisible(false);
        formateurPane.setVisible(false);
        acceuilPane.setVisible(false);
    }

    public void paneFormateur(){
        formateurPane.setVisible(true);
        adminPane.setVisible(false);
        responsablePane.setVisible(false);
        acceuilPane.setVisible(false);
    }

    public void mdpOublie(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veuillez contacter votre administrateur SVP", ButtonType.CANCEL);
        alert.showAndWait();
    }
}
