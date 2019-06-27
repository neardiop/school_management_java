package projet.java.models.classes;

import java.time.LocalDate;
import java.util.Date;

public class Etudiant extends Personne {

	private int numEtudiant;
	private String filiere;

	public Etudiant(String nom, String prenom, Date dateNaissance, char sexe, String email, String telephone, String adresse, int numEtudiant,String filiere) {
		super(nom, prenom, dateNaissance, adresse, email, telephone, sexe);
		this.numEtudiant = numEtudiant;
		this.filiere = filiere;
	}

	public int getNumEtudiant() {
		return numEtudiant;
	}

	public void setNumEtudiant(int numEtudiant) {
		this.numEtudiant = numEtudiant;
	}

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}
}
