package projet.java.models.classes;

public class Administrateur extends Personne {

    private char sexe;
    private String login, pwd;
    private int numAdmin;

    public Administrateur(String nom, String prenom, char sexe, String login, String pwd, int numAdmin) {
        super(nom, prenom);
        this.sexe = sexe;
        this.login = login;
        this.pwd = pwd;
        this.numAdmin = numAdmin;
    }

    public int getNumAdmin() {
        return numAdmin;
    }

    public void setNumAdmin(int numAdmin) {
        this.numAdmin = numAdmin;
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

    @Override
    public char getSexe() {
        return sexe;
    }

    @Override
    public void setSexe(char sexe) {
        this.sexe = sexe;
    }

    @Override
    public String toString() {
        return "Administrateur{" +
                "sexe='" + sexe + '\'' +
                ", login='" + login + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}

