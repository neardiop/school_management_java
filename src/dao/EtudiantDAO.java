package dao;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import projet.java.models.classes.Etudiant;

import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;

import static projet.java.utils.Connexion.getInstance;

public class EtudiantDAO implements PersonneDAO<Etudiant> {
    private Connection connection = null;

    public EtudiantDAO(Connection connection) {
        this.setConnection(connection);
    }

    public static boolean verifEtudiant(int id) {
        try {
            String query = "SELECT * " + "FROM ETUDIANT " + "WHERE numEtudiant = ?";
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

    public static HashSet<Etudiant> readList() {

        HashSet<Etudiant> etudiants = new HashSet<>();

        String query = "SELECT * FROM ETUDIANT";
        Statement statement;
        ResultSet rs;

        try {
            statement = getInstance().createStatement();
            rs = statement.executeQuery(query);

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

    public static int renvoieIdEtudiant(Etudiant obj) {
        int idEtudiant = 0;
        try {
            String query = "SELECT * " + "FROM ETUDIANT WHERE numEtudiant=" + obj.getNumEtudiant();
            PreparedStatement prepare = getInstance().prepareStatement(query);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                idEtudiant = result.getInt("idEtudiant");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return idEtudiant;
    }

    public static HashSet<Etudiant> readListE(String nomFiliere) {

        HashSet<Etudiant> etudiants = new HashSet<>();

        String query = "SELECT * FROM ETUDIANT WHERE nomFiliere= ?";

        try {
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setString(1, nomFiliere);
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
            Scanner sc = new Scanner(br);
            boolean lineExist;
            String line;
            while (lineExist = sc.hasNextLine()) {
                line = sc.nextLine();
                String[] value = line.split(",");
                String query = "INSERT INTO Etudiant (nom,prenom,dateNaissance,sexe,email,telephone,adresse,numEtudiant,nomFiliere) " +
                        "values('" + value[0] + "','" + value[1] + "','" + value[2] + "','" + value[3] + "','" + value[4] + "','" + value[5] + "','" + value[6] + "','" + value[7] + "','" + value[8] + "')";
                PreparedStatement prepare = getInstance().prepareStatement(query);
                prepare.execute();
            }
            br.close();
            return true;
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
            PrintWriter pw = new PrintWriter(new File("exportEtudiants.csv"));
            StringBuilder sb = new StringBuilder();
            String query = "SELECT * FROM ETUDIANT";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            ResultSet rs = prepare.executeQuery();
            while (rs.next()) {
                sb.append(rs.getString("numEtudiant"));
                sb.append(',');
                sb.append(rs.getString("nom"));
                sb.append(',');
                sb.append(rs.getString("prenom"));
                sb.append(',');
                sb.append(rs.getDate("dateNaissance"));
                sb.append(',');
                sb.append(rs.getString("sexe"));
                sb.append(',');
                sb.append(rs.getString("email"));
                sb.append(',');
                sb.append(rs.getString("telephone"));
                sb.append(',');
                sb.append(rs.getString("adresse"));
                sb.append(',');
                sb.append(rs.getString("nomFiliere"));
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

    public static void etudiantPdf() {
        Document doc = new Document();
        String query = "SELECT * FROM ETUDIANT";
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        PdfPCell cell;

        try {
            PdfWriter.getInstance(doc, new FileOutputStream("Etudiant.pdf"));
            doc.open();
            doc.add(new Paragraph("Liste des étudiants\n\n"));
            cell = new PdfPCell(new Phrase("ID"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Nom"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Prénom"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Date de naissance"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Sexe"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Téléphone"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Adresse"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Filiére"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);


            PreparedStatement prepare = getInstance().prepareStatement(query);
            ResultSet rs = prepare.executeQuery();

            while (rs.next()) {
                cell = new PdfPCell(new Phrase(rs.getString("numEtudiant").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("nom").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("prenom").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(rs.getDate("dateNaissance").toString())));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("sexe").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("telephone").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("adresse").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("nomFiliere").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
            doc.add(table);
            doc.close();
            Desktop.getDesktop().open(new File("Etudiant.pdf"));
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
    public boolean create(Etudiant obj) {
        String ma_requete = "INSERT INTO Etudiant (nom,prenom,dateNaissance,sexe,email,telephone,adresse,numEtudiant,nomFiliere) values(?,?,?,?,?,?,?,?,?)";

        PreparedStatement mon_prepared_statement = null;
        try {
            mon_prepared_statement = getInstance().prepareStatement(ma_requete);
            mon_prepared_statement.setString(1, obj.getNom());
            mon_prepared_statement.setString(2, obj.getPrenom());
            mon_prepared_statement.setDate(3, obj.getDateNaissance());
            mon_prepared_statement.setString(4, String.valueOf(obj.getSexe()));
            mon_prepared_statement.setString(5, obj.getEmail());
            mon_prepared_statement.setString(6, obj.getTelephone());
            mon_prepared_statement.setString(7, obj.getAdresse());
            mon_prepared_statement.setInt(8, obj.getNumEtudiant());
            mon_prepared_statement.setString(9, obj.getFiliere());


            mon_prepared_statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public Etudiant read(int id) {
        try {
            String query = "SELECT * " + "FROM ETUDIANT " + "WHERE numEtudiant = ?";
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, id);
            ResultSet rs = prepare.executeQuery();
            if (rs.next()) {
                return new Etudiant(rs.getString("nom"),
                        rs.getString("prenom"), rs.getDate("dateNaissance"),
                        rs.getString("sexe").charAt(0), rs.getString("email"),
                        rs.getString("telephone"), rs.getString("adresse"),
                        rs.getInt("numEtudiant"), rs.getString("nomFiliere"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(Etudiant obj, int id) {
        try {
            String query = "UPDATE ETUDIANT "
                    + "SET Nom = ?, Prenom = ?, DateNaissance = ?, Adresse = ?, Email = ?, Telephone = ?, Sexe = ?, Filiere = ?,numEtudiant =?"
                    + "WHERE idEtudiant = ?";
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, obj.getNom());
            prepare.setString(2, obj.getPrenom());
            prepare.setDate(3, obj.getDateNaissance());
            prepare.setString(4, obj.getAdresse());
            prepare.setString(5, obj.getEmail());
            prepare.setString(6, obj.getTelephone());
            prepare.setString(7, String.valueOf(obj.getSexe()));
            prepare.setString(8, obj.getFiliere());
            prepare.setInt(9, obj.getNumEtudiant());
            prepare.setInt(10, id);
            prepare.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Etudiant obj) {
        String ma_requete = "DELETE FROM ETUDIANT WHERE numEtudiant ='" + obj.getNumEtudiant() + "'";

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
