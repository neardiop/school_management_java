package dao;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import projet.java.models.classes.Etudiant;
import projet.java.models.classes.Formateur;

import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.HashSet;

import static projet.java.utils.Connexion.getInstance;

public class FormateurDAO implements PersonneDAO<Formateur> {
    private Connection connection = null;

    public FormateurDAO(Connection connection) {
        this.setConnection(connection);
    }

    public static boolean createR(Formateur obj) {
        String ma_requete = "INSERT INTO FORMATEUR (nom,prenom,adresse,email,telephone,sexe,login,password,numFormateur,statut,connect) values(?,?,?,?,?,?,?,?,?,?,?)";

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
            mon_prepared_statement.setInt(9, obj.getIdFormateur());
            mon_prepared_statement.setBoolean(10, false);
            mon_prepared_statement.setBoolean(11, false);


            mon_prepared_statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean verifFormateur(int id) {
        try {
            String query = "SELECT * " + "FROM FORMATEUR " + "WHERE numFormateur = ?";
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

    public static HashSet<Formateur> readList() {

        HashSet<Formateur> formateurs = new HashSet<>();

        String query = "SELECT * FROM FORMATEUR";
        Statement statement;
        ResultSet rs;

        try {
            statement = getInstance().createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                Formateur formateur = new Formateur(rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"), rs.getString("email"),
                        rs.getString("telephone"), rs.getString("sexe").charAt(0),
                        rs.getString("login"), rs.getString("password"),
                        rs.getInt("numFormateur"), rs.getBoolean("statut"));

                formateurs.add(formateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formateurs;
    }

    public static int renvoieIDFormateur(Formateur obj) {
        int idFormateur = 0;
        try {
            String query = "SELECT * " + "FROM FORMATEUR WHERE numFormateur=" + obj.getIdFormateur();
            PreparedStatement prepare = getInstance().prepareStatement(query);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                idFormateur = result.getInt("idFormateur");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return idFormateur;
    }

    public static int nbFormateurAValider() {
        int nbFormateur = 0;
        try {
            String query = "SELECT count(*) AS nbFor FROM FORMATEUR WHERE statut=" + false;
            PreparedStatement prepare = getInstance().prepareStatement(query);
            ResultSet resultSet = prepare.executeQuery();
            if (resultSet.next()) {
                nbFormateur = resultSet.getInt("nbFor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nbFormateur;
    }

    public static boolean validiteFormateur(Formateur obj) {
        boolean stat = false;
        try {
            String query = "SELECT * FROM FORMATEUR WHERE numFormateur=" + obj.getIdFormateur() + " ";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            ResultSet resultSet = prepare.executeQuery();
            if (resultSet.next()) {
                stat = resultSet.getBoolean("statut");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stat;
    }

    public static boolean formateurConnect(String login) {
        try {
            String query = "UPDATE FORMATEUR "
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

    public static boolean formateurDisConnect(String login) {
        try {
            String query = "UPDATE FORMATEUR "
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

    public static Formateur renvoiFormateurConnect() {

        String query = "SELECT * FROM FORMATEUR WHERE connect=true ";
        Statement statement;
        ResultSet rs;

        try {
            statement = getInstance().createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                Formateur formateur = new Formateur(rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"), rs.getString("email"),
                        rs.getString("telephone"), rs.getString("sexe").charAt(0),
                        rs.getString("login"), rs.getString("password"),
                        rs.getInt("numFormateur"), rs.getBoolean("statut"));
                return formateur;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int retournerIdModuleFiliere(Formateur obj) {
        int id = 0;
        try {
            String query = "SELECT * " + "FROM FORMATEURMODULE " + "WHERE idFormateur = ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, obj.getIdFormateur());

            ResultSet rs = prepare.executeQuery();
            if (rs.next()) {
                id = rs.getInt("idModuleFiliere");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return id;
    }

    public static HashSet<Etudiant> formateurEtudiant(int idModuleFiliere) {
        HashSet<Etudiant> etudiants = new HashSet<>();
        try {
            String query = "SELECT * FROM ETUDIANT INNER JOIN FILIERE ON ETUDIANT.nomFiliere=FILIERE.intitule " +
                    "INNER JOIN  MODULEFILIERE ON FILIERE.idFiliere=MODULEFILIERE.idFiliere WHERE MODULEFILIERE.idModule= ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, idModuleFiliere);
            ResultSet rs = prepare.executeQuery();
            while (rs.next()) {
                Etudiant etudiant = new Etudiant(rs.getString("nom"),
                        rs.getString("prenom"), rs.getDate("dateNaissance"),
                        rs.getString("sexe").charAt(0), rs.getString("email"),
                        rs.getString("telephone"), rs.getString("adresse"),
                        rs.getInt("numEtudiant"), rs.getString("nomFiliere"));
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }

    public static boolean importerXL(String file) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(file)));
            String line;
            while ((line = br.readLine()) != null) {
                String[] value = line.split(",");
                String query = "INSERT INTO FORMATEUR (nom,prenom,adresse,email,telephone,sexe,login,password,numFormateur,statut,connect)" +
                        "values('" + value[0] + "','" + value[1] + "','" + value[2] + "','" + value[3] + "','" + value[4] + "','" + value[5] + "','" + value[6] + "','" + value[7] + "','" + value[8] + "','" + value[9] + "','" + false + "')";
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
            PrintWriter pw = new PrintWriter(new File("exportFormateurs.csv"));
            StringBuilder sb = new StringBuilder();
            String query = "SELECT * FROM FORMATEUR";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            ResultSet rs = prepare.executeQuery();
            while (rs.next()) {
                sb.append(rs.getInt("numFormateur"));
                sb.append(',');
                sb.append(rs.getString("nom"));
                sb.append(',');
                sb.append(rs.getString("prenom"));
                sb.append(',');
                sb.append(rs.getString("adresse"));
                sb.append(',');
                sb.append(rs.getString("email"));
                sb.append(',');
                sb.append(rs.getString("telephone"));
                sb.append(',');
                sb.append(rs.getString("sexe"));
                sb.append(',');
                sb.append(rs.getString("login"));
                sb.append(',');
                sb.append(rs.getString("password"));
                sb.append(',');
                sb.append(rs.getBoolean("statut"));
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

    public static void formateurPdf() {
        Document doc = new Document();
        String query = "SELECT * FROM FORMATEUR";
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);
        PdfPCell cell;

        try {
            PdfWriter.getInstance(doc, new FileOutputStream("Formateur.pdf"));
            doc.open();
            doc.add(new Paragraph("Liste des Formateurs\n\n"));
            cell = new PdfPCell(new Phrase("ID"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Nom"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Prénom"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Sexe"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("E-mail"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Téléphone"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Statut"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Login"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Password"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);


            PreparedStatement prepare = getInstance().prepareStatement(query);
            ResultSet rs = prepare.executeQuery();

            while (rs.next()) {
                cell = new PdfPCell(new Phrase(rs.getString("numFormateur").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("nom").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("prenom").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("sexe").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("email").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("telephone").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("statut").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("login").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("password").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
            doc.add(table);
            doc.close();
            Desktop.getDesktop().open(new File("Formateur.pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean create(Formateur obj) {
        String ma_requete = "INSERT INTO FORMATEUR (nom,prenom,adresse,email,telephone,sexe,login,password,numFormateur,statut,connect) values(?,?,?,?,?,?,?,?,?,?,?)";

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
            mon_prepared_statement.setInt(9, obj.getIdFormateur());
            mon_prepared_statement.setBoolean(10, obj.isStatut());
            mon_prepared_statement.setBoolean(11, false);


            mon_prepared_statement.execute();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Erreur");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public Formateur read(int id) {
        try {
            String query = "SELECT * " + "FROM FORMATEUR " + "WHERE numFormateur = ?";
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, id);

            ResultSet rs = prepare.executeQuery();
            if (rs.next()) {
                return new Formateur(rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"),
                        rs.getString("telephone"), rs.getString("email"), rs.getString("sexe").charAt(0), rs.getString("login"),
                        rs.getString("password"), rs.getInt("numFormateur"), rs.getBoolean("statut"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(Formateur obj, int id) {
        try {
            String query = "UPDATE FORMATEUR "
                    + "SET Nom = ?, Prenom = ?, Adresse = ?, Email = ?, Telephone = ?, Sexe = ?, Login =?, Password =?,Statut=?,numFormateur=?" +
                    " WHERE idFormateur = " + id;
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, obj.getNom());
            prepare.setString(2, obj.getPrenom());
            prepare.setString(3, obj.getAdresse());
            prepare.setString(4, obj.getEmail());
            prepare.setString(5, obj.getTelephone());
            prepare.setString(6, String.valueOf(obj.getSexe()));
            prepare.setString(7, obj.getLogin());
            prepare.setString(8, obj.getPwd());
            prepare.setBoolean(9, obj.isStatut());
            prepare.setInt(10, obj.getIdFormateur());
            prepare.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Formateur obj) {
        String ma_requete = "DELETE FROM FORMATEUR WHERE numFormateur ='" + obj.getIdFormateur() + "'";

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

}
