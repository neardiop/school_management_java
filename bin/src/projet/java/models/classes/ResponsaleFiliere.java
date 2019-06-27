package projet.java.models.classes;

import java.sql.Date;
import java.time.LocalDate;

public class ResponsaleFiliere extends Personne {
	private int idResponsable;	
	private String login;
	private String pwd;
	
	public ResponsaleFiliere(String nom, String prenom, String adresse, String email,
							 String telephone, char sexe, String login, String pwd,int idResponsable) {
		super(nom, prenom, adresse, email, telephone, sexe);
		this.login = login;
		this.pwd = pwd;
		this.idResponsable = idResponsable;
	}

	public int getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(int idResponsable) {
		this.idResponsable = idResponsable;
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
}
