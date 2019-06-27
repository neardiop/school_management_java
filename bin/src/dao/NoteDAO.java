package dao;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import projet.java.models.classes.Evaluation;
import projet.java.models.classes.Note;

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import static projet.java.utils.Connexion.getInstance;

public class NoteDAO {
    private Connection connection = null;

    public NoteDAO(Connection connection) {
        this.setConnection(connection);
    }

    public static boolean create(Note obj, double noteFoisCoef) {
        String ma_requete = "INSERT INTO NOTES (idEvaluation,idEtudiant,note,commentaire,valider,numNote,noteFoisCoef) values(?,?,?,?,?,?,?)";

        PreparedStatement mon_prepared_statement = null;
        try {
            mon_prepared_statement = getInstance().prepareStatement(ma_requete);
            mon_prepared_statement.setInt(1, obj.getIdEvaluation());
            mon_prepared_statement.setInt(2, obj.getIdEtudiant());
            mon_prepared_statement.setDouble(3, obj.getNote());
            mon_prepared_statement.setString(4, obj.getCommentaire());
            mon_prepared_statement.setBoolean(5, obj.isValider());
            mon_prepared_statement.setInt(6, obj.getIdNote());
            mon_prepared_statement.setDouble(7, noteFoisCoef);
            mon_prepared_statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static HashSet<Note> readList(int idEvaluation) {

        HashSet<Note> notes = new HashSet<>();

        String query = "SELECT * FROM NOTES WHERE idEvaluation=?";


        try {

            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, idEvaluation);
            ResultSet rs = prepare.executeQuery();
            while (rs.next()) {
                Note note = new Note(rs.getInt("numNote"), rs.getInt("idEvaluation"), rs.getInt("idEtudiant"),
                        rs.getDouble("note"), rs.getString("commentaire"), rs.getBoolean("valider"));

                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public static Note read(int id, int idEval) {
        try {
            String query = "SELECT * " + "FROM NOTES " + "WHERE idEtudiant = ? AND idEvaluation = ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, id);
            prepare.setInt(2, idEval);

            ResultSet rs = prepare.executeQuery();
            if (rs.next()) {
                return new Note(rs.getInt("numNote"), rs.getInt("idEvaluation"), rs.getInt("idEtudiant"),
                        rs.getDouble("note"), rs.getString("commentaire"), rs.getBoolean("valider"));

            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static boolean update(Note obj, int id, double noteFoisCoef) {
        try {
            String query = "UPDATE NOTES "
                    + "SET idEvaluation = ?, idEtudiant = ?, note = ?, commentaire = ?, valider = ? , noteFoisCoef = ?" +
                    " WHERE id = ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, obj.getIdEvaluation());
            prepare.setInt(2, obj.getIdEtudiant());
            prepare.setDouble(3, obj.getNote());
            prepare.setString(4, obj.getCommentaire());
            prepare.setBoolean(5, obj.isValider());
            prepare.setDouble(6, noteFoisCoef);
            prepare.setInt(7, id);
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

    public static double renvoiTousLesNoteFoisCoef(String module, int idEtudiant) {
        double noteFoisCoef = 0;
        String query = "SELECT * FROM NOTES INNER JOIN EVALUATION ON NOTES.idEvaluation=EVALUATION.id WHERE " +
                "(EVALUATION.module = ? AND NOTES.idEtudiant = ?)";
        try {
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setString(1, module);
            prepare.setInt(2, idEtudiant);
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                double notes = result.getDouble("noteFoisCoef");
                noteFoisCoef += notes;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noteFoisCoef;
    }

    public static boolean exportXL(String module, int idEvaluation) {
        try {
            PrintWriter pw = new PrintWriter(new File("exportNotes.csv"));
            StringBuilder sb = new StringBuilder();
            String query = "SELECT * FROM NOTES INNER JOIN EVALUATION ON NOTES.idEvaluation = EVALUATION.id " +
                    "NATURAL JOIN ETUDIANT WHERE ( EVALUATION.module = ? AND EVALUATION.numEvaluation =? )";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setString(1, module);
            prepare.setInt(2, idEvaluation);
            ResultSet rs = prepare.executeQuery();
            while (rs.next()) {
                sb.append(rs.getInt("numEtudiant"));
                sb.append(',');
                sb.append(rs.getString("nom"));
                sb.append(',');
                sb.append(rs.getString("prenom"));
                sb.append(',');
                sb.append(rs.getDouble("note"));
                sb.append(',');
                sb.append(rs.getDouble("coef"));
                sb.append(',');
                sb.append(rs.getDouble("noteFoisCoef"));
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

    public static int renvoieIDNote(Note obj) {
        int idNote = 0;
        try {
            String query = "SELECT * " + "FROM NOTES WHERE numNote= ?";
            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setInt(1, obj.getIdNote());
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                idNote = result.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return idNote;
    }

    public static void notesPDF(String module) {
        Document doc = new Document();
        String query = "SELECT * FROM NOTES INNER JOIN EVALUATION ON NOTES.idEvaluation = EVALUATION.id " +
                "NATURAL JOIN ETUDIANT WHERE EVALUATION.module = ?";
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        PdfPCell cell;

        try {
            PdfWriter.getInstance(doc, new FileOutputStream("Notes.pdf"));
            doc.open();
            doc.add(new Paragraph("Notes des étudiants\n\n"));
            cell = new PdfPCell(new Phrase("ID"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Nom"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Prénom"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Module"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Forme"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Type d'évaluation"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Intitulé"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Note"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);


            PreparedStatement prepare = getInstance().prepareStatement(query);
            prepare.setString(1, module);
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

                cell = new PdfPCell(new Phrase(rs.getString("module").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("forme").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("typeEval").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("intitule").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(rs.getString("note").toString()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
            doc.add(table);
            doc.close();
            Desktop.getDesktop().open(new File("Notes.pdf"));
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

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
