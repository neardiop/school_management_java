package dao;

public class PersonneDAOFactoryDemo {
	PersonneDAOFactory personneDAOFactory = new PersonneDAOFactory();
	
	PersonneDAO responsable = personneDAOFactory.getPersonneDAO("RESPONSABLEFILIEREDAO");

	PersonneDAO etudiantDao = personneDAOFactory.getPersonneDAO("ETUDIANTDAO");
}
