package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nb_etals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nb_etals);
	}
	
	private static class Marche {
		private Etal[] etals;
		private int nb_etalsMAX;
		
		private Marche(int nb_etals) {
			etals = new Etal[nb_etals];
			nb_etalsMAX = nb_etals;
			
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if(0 <=indiceEtal && indiceEtal < nb_etalsMAX && etals[indiceEtal] == null) {
				Etal etal = new Etal();
				etal.occuperEtal(vendeur,produit,nbProduit);
				etals[indiceEtal] = etal;
			}
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < nb_etalsMAX; i++) {
				if (etals[i] == null) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			Etal etal = new Etal();
			int nb_max_etal_produit = 0;
			for (int i = 0; i < nb_etalsMAX; i++) {
				etal = etals[i];
				if(etal.contientProduit(produit)) {
					nb_max_etal_produit++;
				}
			}
			Etal[] etals_produits = new Etal[nb_max_etal_produit];
			int indice = 0;
			for (int i = 0; i < nb_etalsMAX; i++) {
				etal = etals[i];
				if(etal.contientProduit(produit)) {
					etals_produits[indice] = etal;
					indice++;
				}
			}
			return etals_produits;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			Etal etal = new Etal();
			for (int i = 0; i < nb_etalsMAX ; i++) {
				etal = etals[i];
				if(etal.getVendeur() == gaulois) {
					return etal;
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nb_etal_vide = 0;
			Etal etal = new Etal();
			for (int i = 0; i < nb_etalsMAX; i++) {
				etal = etals[i];
				if(etal != null) {
					chaine.append(etal.afficherEtal());
				}
				else {
					nb_etal_vide++;
				}
			}
			
			if(nb_etal_vide > 0) {
				chaine.append("Il reste " + nb_etal_vide + " étals non utilisés dans le marché.\n");
			}
			return chaine.toString();
		}
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int ind = marche.trouverEtalLibre();
		marche.utiliserEtal(ind,vendeur, produit, nbProduit);
		chaine.append(vendeur + " cherche un endroit pour vendre " + nbProduit + " " +  produit + ".Le vendeur " + vendeur + " vend des " + produit + " à l'étal n°" + ind +".");
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit){
		StringBuilder chaine = new StringBuilder();
		marche.trouverEtals(produit);
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(this.rechercherEtal(vendeur).libererEtal());
		return chaine.toString();
	}
	
	public String afficherMarche() {
		return marche.afficherMarche();
	}
}