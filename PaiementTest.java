// Le deuxième exercice

import java.util.Scanner;

// Définition de l'interface Paiement
interface Paiement {
    void effectuerPaiement(double montant);
    void effectuerRetrait(double montant);
}

// Implémentation pour Wave
class PaiementWave implements Paiement {
    private double solde = 50000; // Solde initial fictif

    @Override
    public void effectuerPaiement(double montant) {
        if (montant > 0 && montant <= solde) {
            solde -= montant;
            System.out.println("Paiement de " + montant + " F effectué avec Wave. Solde restant : " + solde + " F.");
        } else {
            System.out.println("Paiement refusé : montant invalide ou solde insuffisant.");
        }
    }

    @Override
    public void effectuerRetrait(double montant) {
        if (montant > 0 && montant <= solde) {
            solde -= montant;
            System.out.println("Retrait de " + montant + " F effectué avec Wave. Solde restant : " + solde + " F.");
        } else {
            System.out.println("Retrait refusé : montant invalide ou solde insuffisant.");
        }
    }
}

// Implémentation pour Orange Money
class PaiementOrange implements Paiement {
    private double solde = 30000;

    @Override
    public void effectuerPaiement(double montant) {
        if (montant > 0 && montant <= solde) {
            solde -= montant;
            System.out.println("Paiement de " + montant + " F effectué avec Orange Money. Solde restant : " + solde + " F.");
        } else {
            System.out.println("Paiement refusé : montant invalide ou solde insuffisant.");
        }
    }

    @Override
    public void effectuerRetrait(double montant) {
        if (montant > 0 && montant <= solde) {
            solde -= montant;
            System.out.println("Retrait de " + montant + " F effectué avec Orange Money. Solde restant : " + solde + " F.");
        } else {
            System.out.println("Retrait refusé : montant invalide ou solde insuffisant.");
        }
    }
}

// Implémentation pour PayPal
class PaiementPayPal implements Paiement {
    private double solde = 100000;

    @Override
    public void effectuerPaiement(double montant) {
        if (montant > 0 && montant <= solde) {
            solde -= montant;
            System.out.println("Paiement de " + montant + " F effectué avec PayPal. Solde restant : " + solde + " F.");
        } else {
            System.out.println("Paiement refusé : montant invalide ou solde insuffisant.");
        }
    }

    @Override
    public void effectuerRetrait(double montant) {
        if (montant > 0 && montant <= solde) {
            solde -= montant;
            System.out.println("Retrait de " + montant + " F effectué avec PayPal. Solde restant : " + solde + " F.");
        } else {
            System.out.println("Retrait refusé : montant invalide ou solde insuffisant.");
        }
    }
}

// Classe principale pour tester les paiements et retraits
public class PaiementTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choix = 0;
        int action = 0;
        double montant = 0.0;

        System.out.println("=== Système de Transactions ===");
        System.out.println("1. Wave");
        System.out.println("2. Orange Money");
        System.out.println("3. PayPal");

        // Sélection du mode de paiement
        while (true) {
            System.out.print("Choisissez votre mode de transaction (1-3) : ");
            if (scanner.hasNextInt()) {
                choix = scanner.nextInt();
                if (choix >= 1 && choix <= 3) {
                    break;
                } else {
                    System.out.println("Veuillez entrer un nombre entre 1 et 3.");
                }
            } else {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                scanner.next();
            }
        }

        // Sélection de l'action (paiement ou retrait)
        System.out.println("1. Effectuer un paiement");
        System.out.println("2. Effectuer un retrait");

        while (true) {
            System.out.print("Que voulez-vous faire ? (1-2) : ");
            if (scanner.hasNextInt()) {
                action = scanner.nextInt();
                if (action == 1 || action == 2) {
                    break;
                } else {
                    System.out.println("Veuillez entrer 1 pour Paiement ou 2 pour Retrait.");
                }
            } else {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                scanner.next();
            }
        }

        // Saisie du montant
        while (true) {
            System.out.print("Entrez le montant : ");
            if (scanner.hasNextDouble()) {
                montant = scanner.nextDouble();
                if (montant > 0) {
                    break;
                } else {
                    System.out.println("Veuillez entrer un montant valide (supérieur à 0).");
                }
            } else {
                System.out.println("Entrée invalide, veuillez entrer un montant numérique.");
                scanner.next();
            }
        }

        // Détermination du mode de transaction
        Paiement paiement;
        switch (choix) {
            case 1:
                paiement = new PaiementWave();
                break;
            case 2:
                paiement = new PaiementOrange();
                break;
            case 3:
                paiement = new PaiementPayPal();
                break;
            default:
                System.out.println("Erreur inattendue.");
                return;
        }

        // Exécution de l'action choisie
        if (action == 1) {
            paiement.effectuerPaiement(montant);
        } else {
            paiement.effectuerRetrait(montant);
        }

        scanner.close();
    }
}
