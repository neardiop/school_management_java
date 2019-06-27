package projet.java.utils;

import java.sql.*;

public class Connexion {
        // URL de connexion
        // Ici le nom de la BDD est etablissement
        private String url = "jdbc:mysql://localhost/etablissement?serverTimezone=UTC";
        // Nom du user
        private String user = "root";
        // Mot de passe de l'utilisateur
        private String password = "";
        // Objet Connection
        private static Connection connect;

        // Constructeur privé
        private Connexion() {
            try {
                connect = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Méthode qui va nous retourner notre instance ou la créer si elle n'existe pas
        public static Connection getInstance() {
            if (connect == null) {
                new projet.java.utils.Connexion();
            }
            return connect;
        }

        public static boolean checkUser(String user, String login, String password) throws SQLException {
            String query="";
            switch (user) {
                case "ADMIN":
                    query = "SELECT * FROM administrateur WHERE Login = ? AND Password = ?";
                    break;
                case "RESPONSABLE":
                    query = "SELECT * FROM responsableFiliere WHERE Login = ? AND Password = ?";
                    break;
                case "FORMATEUR":
                    query = "SELECT * FROM formateur WHERE Login = ? AND Password = ?";
                    break;
                default:
                    return false;
            }
            PreparedStatement statement = getInstance().prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return true;
            } else {
                return false;
            }
        }


}
