package dao;

import projet.java.models.classes.Administrateur;

import java.sql.*;
import java.util.HashSet;

import static projet.java.utils.Connexion.getInstance;

public class AdminDAO implements PersonneDAO<Administrateur> {
    private static Connection connection = null;

    public AdminDAO(Connection connection) {
        this.setConnection(connection);
    }

    public static HashSet<Administrateur> readList() {

        HashSet<Administrateur> administrateurs = new HashSet<>();

        String query = "SELECT * FROM administrateur";
        Statement statement;
        ResultSet rs;

        try {
            statement = getInstance().createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                Administrateur administrateur = new Administrateur(rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("sexe").charAt(0), rs.getString("login"), rs.getString("password"),
                        rs.getInt("numAdmin"));

                administrateurs.add(administrateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return administrateurs;
    }

    public static boolean verifAdmin(int id) {
        try {
            String query = "SELECT * " + "FROM ADMINISTRATEUR " + "WHERE numAdmin = ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, id);

            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public static int renvoieIdAdmin(Administrateur obj) {
        int idAdmin = 0;
        try {
            String query = "SELECT * " + "FROM ADMINISTRATEUR WHERE numAdmin=" + obj.getNumAdmin();
            PreparedStatement prepare = getInstance().prepareStatement(query);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                idAdmin = result.getInt("idAdmin");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return idAdmin;
    }

    @Override
    public boolean create(Administrateur obj) {
        String requete = "INSERT INTO ADMINISTRATEUR(nom,prenom,sexe,login,password,numAdmin) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement prepare = getInstance().prepareStatement(requete);
            prepare.setString(1, obj.getNom());
            prepare.setString(2, obj.getPrenom());
            prepare.setString(3, String.valueOf(obj.getSexe()));
            prepare.setString(4, obj.getLogin());
            prepare.setString(5, obj.getPwd());
            prepare.setInt(6, obj.getNumAdmin());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Administrateur read(int id) {
        return null;
    }

    @Override
    public boolean update(Administrateur obj, int id) {
        try {
            String query = "UPDATE ADMINISTRATEUR "
                    + "SET Nom = ?, Prenom = ?, Sexe = ?, Login = ?,Password =?,NumAdmin = ?"
                    + "WHERE idAdmin = ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setString(1, obj.getNom());
            prepare.setString(2, obj.getPrenom());
            prepare.setString(3, String.valueOf(obj.getSexe()));
            prepare.setString(4, obj.getLogin());
            prepare.setString(5, obj.getPwd());
            prepare.setInt(6, obj.getNumAdmin());
            prepare.setInt(7, id);
            prepare.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Administrateur obj) {

        String ma_requete = "DELETE FROM administrateur WHERE login_admin='" + obj.getLogin() + "'";

        PreparedStatement mon_prepared_statement = null;
        try {

            mon_prepared_statement = getInstance().prepareStatement(ma_requete);

            mon_prepared_statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
