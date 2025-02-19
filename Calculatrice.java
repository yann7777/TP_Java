// Sixième exercice


import java.util.Scanner;

public class Calculatrice {
    
    public double addition(double a, double b) {
        return a + b;
    }

    // Méthode pour diviser deux nombres avec gestion d'exception
    public double division(double a, double b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("Erreur : Attention on ne peut pas diviser un nombre par zéro !");
        }
        return a / b;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculatrice calculatrice = new Calculatrice();

        System.out.print("Entrez le premier nombre : ");
        double num1 = scanner.nextDouble();
        
        System.out.print("Entrez le deuxième nombre : ");
        double num2 = scanner.nextDouble();

        double resultatAddition = calculatrice.addition(num1, num2);
        System.out.println(num1 + " + " + num2 + " = " + resultatAddition);

        try {
            double resultatDivision = calculatrice.division(num1, num2);
            System.out.println(num1 + " / " + num2 + " = " + resultatDivision);
        } catch (ArithmeticException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        scanner.close();
    }
}
