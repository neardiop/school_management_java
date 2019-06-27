package dao;

import java.sql.*;
import java.util.HashSet;
import projet.java.models.classes.ResponsaleFiliere;

import static projet.java.utils.Connexion.getInstance;

public class ResponsableFiliereDAO implements PersonneDAO<ResponsaleFiliere> {
    private Connection connection = null;

    public ResponsableFiliereDAO(Connection connection) {
        this.setConnection(connection);
    }

    public static boolean verifResponsable(int id) {
        try {
            String query = "SELECT * " + "FROM RESPONSABLEFILIERE " + "WHERE numResponsable = ?";
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

    public static HashSet<ResponsaleFiliere> readList() {

        HashSet<ResponsaleFiliere> responsaleFilieres = new HashSet<>();

        String query = "SELECT * FROM RESPONSABLEFILIERE";
        Statement statement;
        ResultSet rs;

        try {
            statement = getInstance().createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                ResponsaleFiliere responsaleFiliere = new ResponsaleFiliere(rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"), rs.getString("email"),
                        rs.getString("telephone"), rs.getString("sexe").charAt(0),
                        rs.getString("login"), rs.getString("password"),
                        rs.getInt("numResponsable"));

                responsaleFilieres.add(responsaleFiliere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responsaleFilieres;
    }

    public static int renvoieIdResponsable(ResponsaleFiliere obj) {
        int idResponsable = 0;
        try {
            String query = "SELECT * " + "FROM RESPONSABLEFILIERE WHERE numResponsable=" + obj.getIdResponsable();
            PreparedStatement prepare = getInstance().prepareStatement(query);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                idResponsable = result.getInt("idResponsable");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return idResponsable;
    }

    @Override
    public boolean create(ResponsaleFiliere obj){
        String ma_requete = "INSERT INTO RESPONSABLEFILIERE (nom,prenom,adresse,email,telephone,sexe,login,password,numResponsable) values(?,?,?,?,?,?,?,?,?)";

        PreparedStatement mon_prepared_statement = null;
        try {
            mon_prepared_statement = getInstance().prepareStatement(ma_requete);
            mon_prepared_statement.setString(1, obj.getNom());
            mon_prepared_statement.setString(2, obj.getPrenom());
            mon_prepared_statement.setString(3, obj.getAdresse());
            mon_prepared_statement.setString(4, obj.getEmail());
            mon_prepared_statement.setString(5, obj.getTelephone());
            mon_prepared_statement.setString(6, String.valueOf(obj.getSexe()));
            mon_prepared_statement.setString(7, String.valueOf(obj.getLogin()));
            mon_prepared_statement.setString(8, String.valueOf(obj.getPwd()));
            mon_prepared_statement.setInt(9, obj.getIdResponsable());


            mon_prepared_statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public ResponsaleFiliere read(int id) {
        try {
            String query = "SELECT * " + "FROM RESPONSABLEFILIERE " + "WHERE numResponsable = ?";
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, id);

            ResultSet rs = prepare.executeQuery();
            if (rs.next()) {
                return new ResponsaleFiliere(rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"),
                        rs.getString("telephone"), rs.getString("email"), rs.getString("sexe").charAt(0), rs.getString("login"),
                        rs.getString("password"), rs.getInt("numResponsable"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(ResponsaleFiliere obj, int id) {
        try {
            String query = "UPDATE RESPONSABLEFILIERE "
                    + "SET Nom = ?, Prenom = ?, Adresse = ?, Email = ?, Telephone = ?, Sexe = ?, Login =?, Password =?,numResponsable=?" +
                    " WHERE idResponsable = " + id;
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, obj.getNom());
            prepare.setString(2, obj.getPrenom());
            prepare.setString(3, obj.getAdresse());
            prepare.setString(4, obj.getEmail());
            prepare.setString(5, obj.getTelephone());
            prepare.setString(6, String.valueOf(obj.getSexe()));
            prepare.setString(7, obj.getLogin());
            prepare.setString(8, obj.getPwd());
            prepare.setInt(9, obj.getIdResponsable());
            prepare.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(ResponsaleFiliere obj) {
        String ma_requete = "DELETE FROM RESPONSABLEFILIERE WHERE numResponsable ='" + obj.getIdResponsable() + "'";

        PreparedStatement mon_prepared_statement = null;
        try {

            mon_prepared_statement = getInstance().prepareStatement(ma_requete);

            mon_prepared_statement.execute();

            return true;

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

    public static boolean responsableConnect(String login) {
        try {
            String query = "UPDATE RESPONSABLEFILIERE "
                    + "SET connect = ? WHERE login = ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setBoolean(1, true);
            prepare.setString(2, login);
            prepare.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public static boolean responsableDisConnect(String login) {
        try {
            String query = "UPDATE RESPONSABLEFILIERE "
                    + "SET connect = ? WHERE login = ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setBoolean(1, false);
            prepare.setString(2, login);
            prepare.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public static ResponsaleFiliere renvoiResponsableConnect() {

        String query = "SELECT * FROM RESPONSABLEFILIERE WHERE connect=true ";
        Statement statement;
        ResultSet rs;

        try {
            statement=getInstance().createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()){
                ResponsaleFiliere responsaleFiliere = new ResponsaleFiliere(rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"), rs.getString("email"),
                        rs.getString("telephone"), rs.getString("sexe").charAt(0),
                        rs.getString("login"), rs.getString("password"),
                        rs.getInt("numResponsable"));

                return responsaleFiliere;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
