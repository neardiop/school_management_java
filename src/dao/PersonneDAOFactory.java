package dao;

import java.sql.Connection;

import projet.java.utils.Connexion;

public class PersonneDAOFactory {
	private Connection connection = Connexion.getInstance();
	
	public PersonneDAO getPersonneDAO(String typePersonne) {
		if (typePersonne == null) {
			return null;
		}
		if (typePersonne.equalsIgnoreCase("ETUDIANTDAO")) {
			return new EtudiantDAO(connection);
		} else if (typePersonne.equalsIgnoreCase("FORMATEURDAO")) {
			return new FormateurDAO(connection);
		} else if (typePersonne.equalsIgnoreCase("RESPONSABLEFILIEREDAO")) {
			return new ResponsableFiliereDAO(connection);
		} else if (typePersonne.equalsIgnoreCase("ADMINDAO")) {
			return new AdminDAO(connection);
		}

		return null;
	}
}
