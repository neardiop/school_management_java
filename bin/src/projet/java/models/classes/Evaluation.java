package projet.java.models.classes;

import java.util.Date;

public class Evaluation {

    private String forme;

    private String type;

    private String intitule;

    private double coef;

    private int idEvaluation;

    private String Module;

    private Date dateEvaluation;

    public String getForme() {
        return forme;
    }

    public void setForme(String forme) {
        this.forme = forme;
    }

    public double getCoef() {
        return coef;
    }

    public void setCoef(int coef) {
        this.coef = coef;
    }

    public int getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(int idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    public void setCoef(double coef) {
        this.coef = coef;
    }

    public String getModule() {
        return Module;
    }

    public void setModule(String module) {
        Module = module;
    }

    public Date getDateEvaluation() {
        return dateEvaluation;
    }

    public void setDateEvaluation(Date dateEvaluation) {
        this.dateEvaluation = dateEvaluation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Evaluation(String forme, String type, String intitule, double coef, int idEvaluation, String module, Date dateEvaluation) {
        this.forme = forme;
        this.type = type;
        this.intitule = intitule;
        this.coef = coef;
        this.idEvaluation = idEvaluation;
        Module = module;
        this.dateEvaluation = dateEvaluation;
    }
}
