package projet.java.models.classes;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;

public class Formateur extends Personne {

	private String login,pwd;
	private int idFormateur;
	private boolean statut;

	public Formateur(String nom, String prenom, String adresse, String email, String telephone, char sexe, String login, String pwd, int idFormateur,boolean statut) {
		super(nom, prenom, adresse, email, telephone, sexe);
		this.login = login;
		this.pwd = pwd;
		this.idFormateur = idFormateur;
		this.statut = statut;
	}

	//Composition
	private HashSet<Module> module = new HashSet<>();
	private HashSet<Evaluation> evaluation = new HashSet<>();
	


	public int getIdFormateur() {
		return idFormateur;
	}

	public void setIdFormateur(int idFormateur) {
		this.idFormateur = idFormateur;
	}

	public HashSet<Module> getModule() {
		return module;
	}

	public void setModule(HashSet<Module> module) {
		this.module = module;
	}

	public HashSet<Evaluation> getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(HashSet<Evaluation> evaluation) {
		this.evaluation = evaluation;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public boolean isStatut() {
		return statut;
	}

	public void setStatut(boolean statut) {
		this.statut = statut;
	}
}
