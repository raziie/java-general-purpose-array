package ir.ac.kntu;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Array> arrays = new ArrayList<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("\n" + "please enter your command:");
            String inputArray = input.nextLine();
            if (inputArray.equals("end")) {
                break;
            }
            inputArray = inputArray.replaceAll(" ", "");
            String[] halves = inputArray.split("=");
            if (halves[1].contains("[")) {
                if (halves[0].matches("[a-zA-Z]+[a-zA-Z[\\_[\\d]]]*")) {
                    Array.create(halves);
                } else {
                    System.out.println("invalid name");
                }
            } else if (halves[0].contains("[")) {
                Array.change(halves);
            } else if (Operators.checkIfItContainsBitwiseOperator(halves[1])) {
                Operators.applyBitwiseOperator(halves);
            } else if (Others.checkIfItContainsOtherOperators(halves[1])) {
                Others.applyOtherOperators(halves);
            } else {
                System.out.println("invalid command");
            }
        }
        input.close();
    }
}