package projet.java.models.classes;

public class Note {

    private int idNote;
    private int idEvaluation;
    private int idEtudiant;
    private double note;
    private String commentaire;
    private boolean valider;
    private double noteFoisCoef;

    public double getNoteFoisCoef() {
        return noteFoisCoef;
    }

    public void setNoteFoisCoef(double noteFoisCoef) {
        this.noteFoisCoef = noteFoisCoef;
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public int getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(int idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public boolean isValider() {
        return valider;
    }

    public void setValider(boolean valider) {
        this.valider = valider;
    }

    public Note(int idNote, int idEvaluation, int idEtudiant, double note, String commentaire, boolean valider) {
        this.idNote = idNote;
        this.idEvaluation = idEvaluation;
        this.idEtudiant = idEtudiant;
        this.note = note;
        this.commentaire = commentaire;
        this.valider = valider;
    }
}
