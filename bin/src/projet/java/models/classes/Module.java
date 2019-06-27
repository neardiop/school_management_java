package projet.java.models.classes;

public class Module {
	private int idModule;
	private String intitule;
	private int coef;

	public Module(int idModule, String intitule, int coef) {
		this.idModule = idModule;
		this.intitule = intitule;
		this.coef = coef;
	}

	public int getIdModule() {
		return idModule;
	}

	public void setIdModule(int idModule) {
		this.idModule = idModule;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public int getCoef() {
		return coef;
	}

	public void setCoef(int coef) {
		this.coef = coef;
	}
}
