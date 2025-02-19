// Troisième exercice

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CharacterFrequencyCounter {
    public static Map<String, Integer> compterOccurrences(String phrase) {
        Map<String, Integer> occurrences = new HashMap<>();

        for (char c : phrase.toCharArray()) {
            String key = String.valueOf(c); // Convertir en String pour la clé
            occurrences.put(key, occurrences.getOrDefault(key, 0) + 1);
        }

        return occurrences;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Demande à l'utilisateur d'entrer une phrase
        System.out.print("Entrez une phrase : ");
        String phrase = scanner.nextLine();
        
        // Appel de la fonction et affichage du résultat
        Map<String, Integer> resultat = compterOccurrences(phrase);
        System.out.println("Occurrences des caractères : " + resultat);
        
        scanner.close();
    }
}
