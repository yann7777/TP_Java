// Le premier exercice

import java.util.ArrayList;
import java.util.List;

public class FibonacciGenerator {
    public static void genererFibonacci(int n) {
        if (n <= 0) return;

        int a = 0, b = 1;
        System.out.print(a); // Premier élément

        for (int i = 1; i < n; i++) {
            System.out.print(", " + b);
            int temp = a + b;
            a = b;
            b = temp;
        }

        System.out.println(); // Nouvelle ligne après l'affichage
    }

    public static void main(String[] args) {
        int n = 10; // Nombre d'éléments à générer
        System.out.print("Les " + n + " premiers nombres de la suite de Fibonacci : ");
        genererFibonacci(n);
    }
}
