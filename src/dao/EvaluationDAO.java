package dao;

import projet.java.models.classes.Evaluation;

import java.sql.*;
import java.util.HashSet;

import static projet.java.utils.Connexion.getInstance;

public class EvaluationDAO {
    private Connection connection = null;

    public EvaluationDAO(Connection connection) {
        this.setConnection(connection);
    }

    public static boolean create(Evaluation obj) {
        String ma_requete = "INSERT INTO EVALUATION (forme,typeEval,intitule,coef,module,numEvaluation,dateEvaluation) values(?,?,?,?,?,?,?)";

        PreparedStatement mon_prepared_statement = null;
        try {
            mon_prepared_statement = getInstance().prepareStatement(ma_requete);
            mon_prepared_statement.setString(1, obj.getForme());
            mon_prepared_statement.setString(2, obj.getType());
            mon_prepared_statement.setString(3, obj.getIntitule());
            mon_prepared_statement.setDouble(4, obj.getCoef());
            mon_prepared_statement.setString(5, obj.getModule());
            mon_prepared_statement.setInt(6, obj.getIdEvaluation());
            mon_prepared_statement.setDate(7, (Date) obj.getDateEvaluation());
            mon_prepared_statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean verifEvaluation(int id) {
        try {
            String query = "SELECT * " + "FROM EVALUATION " + "WHERE numEvaluation = ?";
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

    public static HashSet<Evaluation> readList() {

        HashSet<Evaluation> evaluations = new HashSet<>();

        String query = "SELECT * FROM EVALUATION";
        Statement statement;
        ResultSet rs;

        try {
            statement = getInstance().createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                Evaluation evaluation = new Evaluation(rs.getString("forme"), rs.getString("typeEval"),
                        rs.getString("intitule"), rs.getDouble("coef"), rs.getInt("numEvaluation"),
                        rs.getString("module"), rs.getDate("dateEvaluation"));

                evaluations.add(evaluation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evaluations;
    }

    public static boolean update(Evaluation obj, int id) {
        try {
            String query = "UPDATE EVALUATION "
                    + "SET Forme = ?, TypeEval = ?, Intitule = ?, Coef = ?, Module = ?, dateEvaluation = ?, NumEvaluation =?" +
                    " WHERE id = " + id;
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setString(1, obj.getForme());
            prepare.setString(2, obj.getType());
            prepare.setString(3, obj.getIntitule());
            prepare.setDouble(4, obj.getCoef());
            prepare.setString(5, obj.getModule());
            prepare.setString(6, String.valueOf(obj.getDateEvaluation()));
            prepare.setInt(7, obj.getIdEvaluation());
            prepare.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public static boolean delete(Evaluation obj) {
        String ma_requete = "DELETE FROM EVALUATION WHERE numEvaluation ='" + obj.getIdEvaluation() + "'";

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

    public static int renvoieIDEvaluation(Evaluation obj) {
        int idEvaluation = 0;
        try {
            String query = "SELECT * " + "FROM Evaluation WHERE numEvaluation=" + obj.getIdEvaluation();
            PreparedStatement prepare = getInstance().prepareStatement(query);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                idEvaluation = result.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return idEvaluation;
    }

    public static double renvoieCoefEvaluation(int idEvaluatation) {
        double coefEval = 0;
        String query = "SELECT * FROM EVALUATION WHERE id = ?";
        try {
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, idEvaluatation);
            ResultSet resultSet = prepare.executeQuery();
            if (resultSet.next()) {
                coefEval = resultSet.getDouble("coef");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coefEval;
    }

    public Evaluation read(int id) {
        try {
            String query = "SELECT * " + "FROM EVALUATION " + "WHERE numEvaluation = ?";
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, id);

            ResultSet rs = prepare.executeQuery();
            if (rs.next()) {
                return new Evaluation(rs.getString("forme"), rs.getString("type"),
                        rs.getString("intitule"), rs.getDouble("coef"), rs.getInt("numEvaluation"),
                        rs.getString("module"), rs.getDate("dateEval"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


}
