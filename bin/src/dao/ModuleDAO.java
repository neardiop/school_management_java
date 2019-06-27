package dao;

import projet.java.models.classes.Filiere;
import projet.java.models.classes.Formateur;
import projet.java.models.classes.Module;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

import static projet.java.utils.Connexion.getInstance;

public class ModuleDAO implements ProduitDAO<Module> {

    private Connection connection = null;

    public ModuleDAO(Connection connection) {
        this.setConnection(connection);
    }

    public static HashSet<Module> readList() {

        HashSet<Module> modules = new HashSet<>();

        String query = "SELECT * FROM MODULE ";
        Statement statement;
        ResultSet rs;

        try {
            statement = getInstance().createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                Module module = new Module(rs.getInt("numModule"),
                        rs.getString("intitule"), rs.getInt("coef"));

                modules.add(module);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    public static HashSet<Module> readListM(int idfiliere) {

        HashSet<Module> modules = new HashSet<>();

        String query = "SELECT * FROM MODULE INNER JOIN MODULEFILIERE ON MODULE.id=MODULEFILIERE.idModule WHERE idFiliere =?";

        try {
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, idfiliere);
            ResultSet rs = prepare.executeQuery();

            while (rs.next()) {
                Module module = new Module(rs.getInt("numModule"),
                        rs.getString("intitule"), rs.getInt("coef"));

                modules.add(module);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    public static boolean verifModule(int id) {
        try {
            String query = "SELECT * " + "FROM MODULE " + "WHERE numModule = ?";
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

    public static boolean verifModuleFiliere(int id) {
        try {
            String query = "SELECT * " + "FROM MODULEFILIERE " + "WHERE idFiliere = ?";
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

    public static boolean verifModuleFormateur(int id) {
        try {
            String query = "SELECT * " + "FROM FORMATEURMODULE " + "WHERE idFormateur = ?";
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

    public static ArrayList<String> retournerModule() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String query = "SELECT * " + "FROM MODULE ";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                String module = result.getString("intitule");
                arrayList.add(module);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return arrayList;
    }

    public static ArrayList<String> retournerModuleFiliere(Filiere filiere) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String query = "SELECT * FROM MODULE INNER JOIN MODULEFILIERE ON MODULE.id=MODULEFILIERE.idModule WHERE idFiliere =?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, FiliereDAO.renvoieIDFiliere(filiere.getIntituleFiliere()));
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                String module = result.getString("intitule");
                arrayList.add(module);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return arrayList;
    }

    public static ArrayList<String> retournerModuleFormateur(Formateur formateur) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String query = "SELECT * FROM MODULE INNER JOIN MODULEFILIERE ON MODULE.id=MODULEFILIERE.idModule INNER JOIN " +
                    "FORMATEURMODULE ON MODULEFILIERE.id=FORMATEURMODULE.idModuleFiliere WHERE idFormateur =?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, FormateurDAO.renvoieIDFormateur(formateur));
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                String module = result.getString("intitule");
                arrayList.add(module);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return arrayList;
    }

    public static boolean ajoutModuleAFiliere(int idFiliere, int idModule) {

        String ma_requete = "INSERT INTO MODULEFILIERE (idFiliere,idModule) values(?,?)";
        PreparedStatement mon_prepared_statement = null;
        try {
            mon_prepared_statement = getInstance().prepareStatement(ma_requete);
            mon_prepared_statement.setInt(1, idFiliere);
            mon_prepared_statement.setInt(2, idModule);
            mon_prepared_statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean ajoutModuleAFormateur(int idFormateur, int idModuleFiliere) {

        String ma_requete = "INSERT INTO FORMATEURMODULE (idFormateur,idModuleFiliere) values(?,?)";
        PreparedStatement mon_prepared_statement = null;
        try {
            mon_prepared_statement = getInstance().prepareStatement(ma_requete);
            mon_prepared_statement.setInt(1, idFormateur);
            mon_prepared_statement.setInt(2, idModuleFiliere);
            mon_prepared_statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int renvoieIDModule(String nomModule) {
        int idModule = 0;
        try {
            String query = "SELECT * " + "FROM MODULE WHERE intitule= ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setString(1, nomModule);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                idModule = result.getInt("id");
                return idModule;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return idModule;
    }

    public static boolean deleteModuleInFiliere(String nomModule) {
        String ma_requete = "DELETE FROM MODULEFILIERE INNER JOIN MODULE ON MODULE.id=MODULEFILIERE.idModule WHERE intitule='" + nomModule + "'";


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

    public static int renvoiIdModuleFiliere(int idModule) {
        int idModuleFiliere = 0;
        try {
            String query = "SELECT * " + "FROM MODULEFILIERE WHERE idModule= ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, idModule);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                idModuleFiliere = result.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return idModuleFiliere;
    }

    public static boolean verifIdModuleInFiliere(int idModule) {
        try {
            String query = "SELECT * " + "FROM MODULEFILIERE WHERE idModule= ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, idModule);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public static boolean importerXL(String file) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(file)));
            String line;
            while ((line = br.readLine()) != null) {
                String[] value = line.split(",");
                String query = "INSERT INTO MODULE (intitule,coef,numModule)" +
                        "values('" + value[0] + "','" + value[1] + "','" + value[2] + "')";
                PreparedStatement prepare = getInstance().prepareStatement(query);
                prepare.execute();
                return true;
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean exportXL() {
        try {
            PrintWriter pw = new PrintWriter(new File("exportModules.csv"));
            StringBuilder sb = new StringBuilder();
            String query = "SELECT * FROM MODULE ";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            ResultSet rs = prepare.executeQuery();
            while (rs.next()) {
                sb.append(rs.getInt("numModule"));
                sb.append(',');
                sb.append(rs.getString("intitule"));
                sb.append(',');
                sb.append(rs.getString("coef"));
                sb.append("\n");
            }
            pw.write(String.valueOf(sb));
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Module obj) {
        String ma_requete = "INSERT INTO MODULE (intitule,coef,numModule) values(?,?,?)";

        PreparedStatement mon_prepared_statement = null;
        try {
            mon_prepared_statement = getInstance().prepareStatement(ma_requete);
            mon_prepared_statement.setString(1, obj.getIntitule());
            mon_prepared_statement.setInt(2, obj.getCoef());
            mon_prepared_statement.setInt(3, obj.getIdModule());
            mon_prepared_statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Module read(int id) {
        try {
            String query = "SELECT * " + "FROM MODULE " + "WHERE numModule = ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, id);
            ResultSet rs = prepare.executeQuery();
            if (rs.next()) {
                return new Module(rs.getInt("numModule"),
                        rs.getString("intitule"), rs.getInt("coef"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(Module obj, int id) {
        try {
            String query = "UPDATE MODULE "
                    + "SET intitule = ? ,  coef=? , numModule = ? WHERE id = ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setString(1, obj.getIntitule());
            prepare.setDouble(2, obj.getCoef());
            prepare.setInt(3, obj.getIdModule());
            prepare.setInt(4, id);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Module obj) {
        String ma_requete = "DELETE FROM MODULE WHERE numModule ='" + obj.getIdModule() + "'";

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
}
