package dao;

import java.sql.Connection;

import projet.java.utils.Connexion;

public class ProduitDAOFactory {
    private Connection connection = Connexion.getInstance();

    public ProduitDAO getProduitDAO(String typeProduit) {
        if (typeProduit == null) {
            return null;
        }
        if (typeProduit.equalsIgnoreCase("FILIEREDAO")) {
            return new FiliereDAO(connection);
        }else if (typeProduit.equalsIgnoreCase("MODULEDAO"))
            return new ModuleDAO(connection);

        return null;
    }
}
