package projet.java.models.classes;

import java.util.HashSet;

public class Filiere {
	private int idFiliere;
	private String intituleFiliere;

	public Filiere(int idFiliere, String intituleFiliere) {
		this.idFiliere = idFiliere;
		this.intituleFiliere = intituleFiliere;
	}

	public int getIdFiliere() {
		return idFiliere;
	}

	public void setIdFiliere(int idFiliere) {
		this.idFiliere = idFiliere;
	}

	public String getIntituleFiliere() {
		return intituleFiliere;
	}

	public void setIntituleFiliere(String intituleFiliere) {
		this.intituleFiliere = intituleFiliere;
	}

	@Override
	public String toString() {
		return "Filiere{" +
				"idFiliere=" + idFiliere +
				", intituleFiliere='" + intituleFiliere + '\'' +
				'}';
	}
}
