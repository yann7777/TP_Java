// Septième exercice

import java.util.*;
import java.util.concurrent.*;
import java.text.SimpleDateFormat;

class Transaction {
    private double montant;
    private Date date;

    // Constructeur
    public Transaction(double montant, String dateStr) throws Exception {
        this.montant = montant;
        this.date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
    }

    public double getMontant() {
        return montant;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Transaction{" + "montant=" + montant + ", date=" + sdf.format(date) + '}';
    }
}

// Comparateur pour trier par date décroissante
class ComparateurDate implements Comparator<Transaction> {
    @Override
    public int compare(Transaction t1, Transaction t2) {
        return t2.getDate().compareTo(t1.getDate()); // Tri décroissant
    }
}

public class GestionTransactions {
    public static void main(String[] args) throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        
        // Ajout de transactions avec montant et date
        transactions.add(new Transaction(1500, "2024-02-15"));
        transactions.add(new Transaction(2000, "2024-01-10"));
        transactions.add(new Transaction(500, "2024-03-20"));
        transactions.add(new Transaction(3000, "2024-02-05"));
        transactions.add(new Transaction(1200, "2024-03-10"));
        transactions.add(new Transaction(2500, "2024-01-25"));
        transactions.add(new Transaction(800, "2024-02-28"));
        transactions.add(new Transaction(4000, "2024-03-15"));
        transactions.add(new Transaction(3500, "2024-01-18"));

        // ExecutorService pour exécuter le tri en parallèle avec 4 threads
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        
        Future<List<Transaction>> future = executorService.submit(() -> {
            transactions.sort(new ComparateurDate()); // Tri par date décroissante
            return transactions;
        });

        try {
            List<Transaction> transactionsTriees = future.get(); // Récupération des résultats
            
            // Affichage des transactions triées
            System.out.println("Transactions triées par date décroissante :");
            transactionsTriees.forEach(System.out::println);

            // On sélectionne les 5 plus grosses promotions (transactions avec les montants les plus élevés)
            transactionsTriees.sort(Comparator.comparing(Transaction::getMontant).reversed());
            System.out.println("\nTop 5 des plus grosses promotions :");
            transactionsTriees.stream().limit(5).forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown(); // Fermeture du thread pool
        }
    }
}
