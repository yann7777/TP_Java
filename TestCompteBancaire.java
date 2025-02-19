// Le quatrième exercice


import java.util.Scanner;

// Classe d'exception personnalisée pour gérer les erreurs de solde insuffisant
class SoldeException extends Exception {
    public SoldeException(String message) {
        super(message);
    }
}

// Classe représentant un compte bancaire
class CompteBancaire {
    private double solde;

    // Constructeur pour initialiser le solde du compte
    public CompteBancaire(double soldeInitial) {
        this.solde = soldeInitial;
    }

    // Méthode pour afficher le solde actuel du compte
    public void afficherSolde() {
        System.out.println("Solde actuel : " + solde + " €");
    }

    // Méthode pour effectuer un retrait
    public void retirer(double montant) throws SoldeException {
        // Vérification si le montant est positif
        if (montant <= 0) {
            throw new SoldeException("Le montant doit être positif.");
        }

        // Vérification si le solde est suffisant
        if (montant > solde) {
            throw new SoldeException("Solde insuffisant pour effectuer le retrait.");
        }

        // Effectuer le retrait
        solde -= montant;
        System.out.println("Retrait de " + montant + " € effectué avec succès.");
    }
}

// Classe de test pour simuler les opérations sur un compte bancaire
public class TestCompteBancaire {
    public static void main(String[] args) {
        // Création d'un compte avec un solde initial de 500 €
        CompteBancaire compte = new CompteBancaire(1000);

        // Affichage du solde actuel
        compte.afficherSolde();

        // Demande du montant à retirer
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le montant à retirer : ");
        double montant = scanner.nextDouble();

        try {
            // Tentative de retrait
            compte.retirer(montant);
        } catch (SoldeException e) {
            // Gestion de l'exception si le solde est insuffisant
            System.out.println("Erreur : " + e.getMessage());
        }

        // Affichage du solde après l'opération
        compte.afficherSolde();

        // Fermeture du scanner
        scanner.close();
    }
}
