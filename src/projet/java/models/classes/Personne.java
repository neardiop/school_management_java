package projet.java.models.classes;


import java.util.Date;

public abstract class Personne {
	private String nom;
	private String prenom;
	private Date dateNaissance;
	private String adresse;
	private String email;
	private String telephone;
	private char sexe;

	public Personne(String nom, String prenom, Date dateNaissance, String adresse, String email,
			String telephone, char sexe) {
		this.nom = nom;
		this.prenom = prenom;		
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.email = email;
		this.telephone = telephone;
		this.sexe = sexe;
	}

	public Personne(String nom ,String prenom){
		this.nom=nom;
		this.prenom=prenom;
	}

	public Personne(String nom, String prenom, String adresse, String email, String telephone, char sexe) {
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.email = email;
		this.telephone = telephone;
		this.sexe = sexe;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public char getSexe() {
		return sexe;
	}

	public void setSexe(char sexe) {
		this.sexe = sexe;
	}

	public java.sql.Date getDateNaissance() {
		java.sql.Date date= (java.sql.Date) dateNaissance;
		return date;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String tel) {
		this.telephone = tel;
	}
	
	@Override
	public String toString() {
		return "Personne [nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance + ", adresse="
				+ adresse + ", email=" + email + ", telephone=" + telephone + ", sexe=" + sexe + "]";
	}

}
