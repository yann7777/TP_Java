// Cinquième exercice


import java.io.*;

public class FiltrerLignes {
    public static void main(String[] args) {
        // Nom des fichiers d'entrée et de sortie
        String fichierEntree = "input.txt";
        String fichierSortie = "output.txt";

        try (BufferedReader lecteur = new BufferedReader(new FileReader(fichierEntree));
             BufferedWriter ecrivain = new BufferedWriter(new FileWriter(fichierSortie))) {

            String ligne;
            while ((ligne = lecteur.readLine()) != null) {
                // Vérifier si la ligne commence par "N"
                if (ligne.startsWith("N")) {
                    ecrivain.write(ligne);
                    ecrivain.newLine();
                }
            }

            System.out.println("Traitement terminé ! Vérifiez le fichier " + fichierSortie);
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
