package dao;

import projet.java.models.classes.Filiere;
import projet.java.models.classes.Module;
import projet.java.models.classes.ResponsaleFiliere;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

import static projet.java.utils.Connexion.getInstance;

public class FiliereDAO implements ProduitDAO<Filiere> {

    private Connection connection = null;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public FiliereDAO (Connection connection) {
        this.setConnection(connection);
    }

    @Override
    public boolean create(Filiere obj) {
        String ma_requete = "INSERT INTO Filiere (intitule,numFiliere) values(?,?)";

        PreparedStatement mon_prepared_statement = null;
        try {
            mon_prepared_statement = getInstance().prepareStatement(ma_requete);
            mon_prepared_statement.setString(1,obj.getIntituleFiliere());
            mon_prepared_statement.setInt(2,obj.getIdFiliere());
            mon_prepared_statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Filiere read(int id) {
        try {
            String query = "SELECT * " + "FROM FILIERE " + "WHERE idFiliere = ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, id);
            ResultSet rs = prepare.executeQuery();
            if (rs.next()) {
                return new Filiere(rs.getInt("numFiliere"),
                        rs.getString("intitule"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(Filiere obj,int id) {
        try {
            String query = "UPDATE FILIERE "
                    + "SET intitule = ? , numFiliere = ? WHERE idFiliere = ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setString(1, obj.getIntituleFiliere());
            prepare.setInt(2, obj.getIdFiliere());
            prepare.setInt(3,id);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Filiere obj) {
        String ma_requete = "DELETE FROM FILIERE WHERE numFiliere ='"+ obj.getIdFiliere() +"'";

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

    public static HashSet<Filiere> readList() {

        HashSet<Filiere> filieres = new HashSet<>();

        String query = "SELECT * FROM FILIERE";
        Statement statement;
        ResultSet rs;

        try {
            statement=getInstance().createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()){
                Filiere filiere = new Filiere(rs.getInt("numFiliere"),
                        rs.getString("intitule"));

                filieres.add(filiere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filieres;
    }

    public static HashSet<Filiere> readListResponsable(String login) {

        HashSet<Filiere> filieres = new HashSet<>();

        String query = "SELECT * FROM FILIERE";
        Statement statement;
        ResultSet rs;

        try {
            statement=getInstance().createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()){
                Filiere filiere = new Filiere(rs.getInt("numFiliere"),
                        rs.getString("intitule"));

                filieres.add(filiere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filieres;
    }

    public static boolean verifFiliere(int id) {
        try {
            String query = "SELECT * " + "FROM FILIERE " + "WHERE numFiliere = ?";
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

    public static ArrayList<String> retournerFiliere(){
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String query = "SELECT * " + "FROM FILIERE ";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                String filiere = result.getString("intitule");
                arrayList.add(filiere);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return arrayList;
    }

    public static int renvoieIDFiliere(String nomFiliere){
        int idFiliere = 0;
        try {
            String query = "SELECT * " + "FROM FILIERE WHERE intitule= ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setString(1,nomFiliere);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                idFiliere = result.getInt("idFiliere");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return idFiliere;
    }

    public static ArrayList<String> retournerFiliereResponsable(ResponsaleFiliere rf ){
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String query = "SELECT * FROM FILIERE INNER JOIN LINKRESPONSABLEFILIERE ON FILIERE.idFiliere=LINKRESPONSABLEFILIERE.idFiliere WHERE idResponsable =?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1,ResponsableFiliereDAO.renvoieIdResponsable(rf));
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                String filiere = result.getString("intitule");
                arrayList.add(filiere);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return arrayList;
    }

    public static int retournerFiliere(int idModule){
        int idFiliere=0;
        try{
            String query = "SELECT * FROM MODULEFILIERE  WHERE idModule =?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1,idModule);
            ResultSet result = prepare.executeQuery();
            if (result.next()){
                idFiliere = result.getInt("idFiliere");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idFiliere;
    }


    public static boolean ajoutFiliereAResponsable(int idFiliere,int idResponsable){

        String ma_requete = "INSERT INTO LINKRESPONSABLEFILIERE (idFiliere,idResponsable) values(?,?)";
        PreparedStatement mon_prepared_statement = null;
        try {
            mon_prepared_statement = getInstance().prepareStatement(ma_requete);
            mon_prepared_statement.setInt(1,idFiliere);
            mon_prepared_statement.setInt(2,idResponsable);
            mon_prepared_statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean verifResponsableFiliere(int id) {
        try {
            String query = "SELECT * " + "FROM LINKRESPONSABLEFILIERE " + "WHERE idResponsable = ?";
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

    public static boolean deleteModuleInFiliere(String nomModule) {
        String ma_requete = "DELETE FROM MODULEFILIERE INNER JOIN MODULE ON MODULE.id=MODULEFILIERE.idModule WHERE intitule='"+ nomModule +"'";


        try {

            PreparedStatement mon_prepared_statement = null;

            mon_prepared_statement = getInstance().prepareStatement(ma_requete);

            mon_prepared_statement.execute();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
