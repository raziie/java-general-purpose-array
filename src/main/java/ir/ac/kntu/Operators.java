package ir.ac.kntu;

import java.util.ArrayList;

import static java.util.Arrays.deepToString;

public class Operators {
    public static boolean checkIfItContainsBitwiseOperator(String input) {
        if (input.contains("+")) {
            return true;
        } else if (input.contains("-")) {
            return true;
        } else if (input.contains("*")) {
            return true;
        } else if (input.contains("/")) {
            return true;
        } else if (input.contains("#")) {
            return true;
        } else {
            return false;
        }
    }

    public static void applyBitwiseOperator(String[] halves) {
        if (halves[1].contains("+")) {
            String[] elements = halves[1].split("\\+");
            applyAddition(elements, halves[0]);
        } else if (halves[1].contains("-")) {
            String[] elements = halves[1].split("-");
            applySubtraction(elements, halves[0]);
        } else if (halves[1].contains("*")) {
            String[] elements = halves[1].split("\\*");
            applyMultiplication(elements, halves[0]);
        } else if (halves[1].contains("/")) {
            String[] elements = halves[1].split("\\/");
            applyDivision(elements, halves[0]);
        } else if (halves[1].contains("#")) {
            String[] elements = halves[1].split("#");
            applyHashtag(elements, halves[0]);
        }
    }

    public static boolean checkIfTheElemntsAreFloatOrInteger(String[] elements) {
        ArrayType typeOne = Array.findArray(elements[0]).getType();
        ArrayType typeTwo = Array.findArray(elements[1]).getType();
        if (typeOne == ArrayType.FLOAT || typeOne == ArrayType.INTEGER) {
            return typeTwo == ArrayType.FLOAT || typeTwo == ArrayType.INTEGER;
        }
        return false;
    }

    public static boolean checkForSameTypes(String[] elements) {
        ArrayType typeOne = Array.findArray(elements[0]).getType();
        ArrayType typeTwo = Array.findArray(elements[1]).getType();
        boolean bothAreFloat = (typeOne == ArrayType.FLOAT && typeTwo == ArrayType.FLOAT);
        boolean bothAreInteger = (typeOne == ArrayType.INTEGER && typeTwo == ArrayType.INTEGER);
        boolean bothAreString = (typeOne == ArrayType.STRING && typeTwo == ArrayType.STRING);
        boolean bothAreChar = (typeOne == ArrayType.CHAR && typeTwo == ArrayType.CHAR);
        return (bothAreFloat || bothAreInteger || bothAreString || bothAreChar);
    }

    public static void applyAddition(String[] elements, String name) {
        if (!checkIfTheElemntsAreFloatOrInteger(elements)) {
            System.out.println("wrong type");
        } else if (!checkForSameTypes(elements)) {
            System.out.println("inequality of types");
        } else if (Array.findArray(elements[0]).getDimension() != Array.findArray(elements[1]).getDimension()) {
            System.out.println("inequality of dimensions");
        } else if (!checkIfDimensionsAreSame(elements)) {
            System.out.println("inequality of sizes");
        } else {
            if (name.matches("[a-zA-Z]+[a-zA-Z[\\_[\\d]]]*")) {
                Array.create(add(name, elements));
            } else {
                System.out.println("invalid name");
            }
        }
    }

    public static boolean checkIfDimensionsAreSame(String[] elements) {
        String dimensionsOne = deepToString(Array.findArray(elements[0]).getDimensions().toArray());
        String dimensionsTwo = deepToString(Array.findArray(elements[1]).getDimensions().toArray());
        return dimensionsOne.equals(dimensionsTwo);
    }

    public static String[] add(String name, String[] elements) {
        String[] result = new String[2];
        result[0] = name;
        ArrayList<Object> firstArray = Array.findArray(elements[0]).getArray();
        ArrayList<Object> secondArray = Array.findArray(elements[1]).getArray();
        String stringArray = deepToString(firstArray.toArray());
        stringArray = stringArray.replaceAll(" ", "");
        int dimension = Array.calculateDimension(stringArray);
        ArrayList<Object> sum = Array.createArray(stringArray, dimension, Array.verifyType(stringArray));
        if (Array.findArray(elements[0]).getType() == ArrayType.INTEGER) {
            sumIntegerArrayLists(firstArray, secondArray, sum);
        } else {
            sumFloatArrayLists(firstArray, secondArray, sum);
        }
        result[1] = deepToString(sum.toArray());
        result[1] = result[1].replaceAll(" ", "");
        return result;
    }

    public static void sumFloatArrayLists(ArrayList<Object> firstArray, ArrayList<Object> secondArray,
                                          ArrayList<Object> sum) {
        if (Array.calculateDimension(deepToString(sum.toArray())) > 1) {
            for (int i = 0; i < sum.size(); i++) {
                ArrayList<Object> newFirstArray = (ArrayList<Object>) firstArray.get(i);
                ArrayList<Object> newSecondArray = (ArrayList<Object>) secondArray.get(i);
                ArrayList<Object> newSum = (ArrayList<Object>) sum.get(i);
                sumFloatArrayLists(newFirstArray, newSecondArray, newSum);
            }
        } else {
            for (int i = 0; i < sum.size(); i++) {
                float firstElement = Float.parseFloat(firstArray.get(i).toString());
                float secondElement = Float.parseFloat(secondArray.get(i).toString());
                sum.set(i, Float.toString(firstElement + secondElement));
            }
        }
    }

    public static void sumIntegerArrayLists(ArrayList<Object> firstArray, ArrayList<Object> secondArray,
                                            ArrayList<Object> sum) {
        if (Array.calculateDimension(deepToString(sum.toArray())) > 1) {
            for (int i = 0; i < sum.size(); i++) {
                ArrayList<Object> newFirstArray = (ArrayList<Object>) firstArray.get(i);
                ArrayList<Object> newSecondArray = (ArrayList<Object>) secondArray.get(i);
                ArrayList<Object> newSum = (ArrayList<Object>) sum.get(i);
                sumIntegerArrayLists(newFirstArray, newSecondArray, newSum);
            }
        } else {
            for (int i = 0; i < sum.size(); i++) {
                int firstElement = Integer.parseInt(firstArray.get(i).toString());
                int secondElement = Integer.parseInt(secondArray.get(i).toString());
                sum.set(i, Integer.toString(firstElement + secondElement));
            }
        }
    }

    public static void applySubtraction(String[] elements, String name) {
        if (!checkIfTheElemntsAreFloatOrInteger(elements)) {
            System.out.println("wrong type");
        } else if (!checkForSameTypes(elements)) {
            System.out.println("inequality of types");
        } else if (Array.findArray(elements[0]).getDimension() != Array.findArray(elements[1]).getDimension()) {
            System.out.println("inequality of dimensions");
        } else if (!checkIfDimensionsAreSame(elements)) {
            System.out.println("inequality of sizes");
        } else {
            if (name.matches("[a-zA-Z]+[a-zA-Z[\\_[\\d]]]*")) {
                Array.create(minus(name, elements));
            } else {
                System.out.println("invalid name");
            }
        }
    }

    public static String[] minus(String name, String[] elements) {
        String[] result = new String[2];
        result[0] = name;
        ArrayList<Object> firstArray = Array.findArray(elements[0]).getArray();
        ArrayList<Object> secondArray = Array.findArray(elements[1]).getArray();
        String stringArray = deepToString(firstArray.toArray());
        stringArray = stringArray.replaceAll(" ", "");
        int dimension = Array.calculateDimension(stringArray);
        ArrayList<Object> subtract = Array.createArray(stringArray, dimension, Array.verifyType(stringArray));
        if (Array.findArray(elements[0]).getType() == ArrayType.INTEGER) {
            subtractIntegerArrayLists(firstArray, secondArray, subtract);
        } else {
            subtractFloatArrayLists(firstArray, secondArray, subtract);
        }
        result[1] = deepToString(subtract.toArray());
        result[1] = result[1].replaceAll(" ", "");
        return result;
    }

    public static void subtractIntegerArrayLists(ArrayList<Object> firstArray, ArrayList<Object> secondArray,
                                                 ArrayList<Object> subtract) {
        if (Array.calculateDimension(deepToString(subtract.toArray())) > 1) {
            for (int i = 0; i < subtract.size(); i++) {
                ArrayList<Object> newFirstArray = (ArrayList<Object>) firstArray.get(i);
                ArrayList<Object> newSecondArray = (ArrayList<Object>) secondArray.get(i);
                ArrayList<Object> newSubtract = (ArrayList<Object>) subtract.get(i);
                subtractIntegerArrayLists(newFirstArray, newSecondArray, newSubtract);
            }
        } else {
            for (int i = 0; i < subtract.size(); i++) {
                int firstElement = Integer.parseInt(firstArray.get(i).toString());
                int secondElement = Integer.parseInt(secondArray.get(i).toString());
                subtract.set(i, Integer.toString(firstElement - secondElement));
            }
        }
    }

    public static void subtractFloatArrayLists(ArrayList<Object> firstArray, ArrayList<Object> secondArray,
                                               ArrayList<Object> subtract) {
        if (Array.calculateDimension(deepToString(subtract.toArray())) > 1) {
            for (int i = 0; i < subtract.size(); i++) {
                ArrayList<Object> newFirstArray = (ArrayList<Object>) firstArray.get(i);
                ArrayList<Object> newSecondArray = (ArrayList<Object>) secondArray.get(i);
                ArrayList<Object> newSubtract = (ArrayList<Object>) subtract.get(i);
                subtractFloatArrayLists(newFirstArray, newSecondArray, newSubtract);
            }
        } else {
            for (int i = 0; i < subtract.size(); i++) {
                float firstElement = Float.parseFloat(firstArray.get(i).toString());
                float secondElement = Float.parseFloat(secondArray.get(i).toString());
                subtract.set(i, Float.toString(firstElement - secondElement));
            }
        }
    }

    public static void applyMultiplication(String[] elements, String name) {
        if (!checkIfTheElemntsAreFloatOrInteger(elements)) {
            System.out.println("wrong type");
        } else if (!checkForSameTypes(elements)) {
            System.out.println("inequality of types");
        } else if (Array.findArray(elements[0]).getDimension() != Array.findArray(elements[1]).getDimension()) {
            System.out.println("inequality of dimensions");
        } else if (!checkIfDimensionsAreSame(elements)) {
            System.out.println("inequality of sizes");
        } else {
            if (name.matches("[a-zA-Z]+[a-zA-Z[\\_[\\d]]]*")) {
                Array.create(multiple(name, elements));
            } else {
                System.out.println("invalid name");
            }
        }
    }

    public static String[] multiple(String name, String[] elements) {
        String[] result = new String[2];
        result[0] = name;
        ArrayList<Object> firstArray = Array.findArray(elements[0]).getArray();
        ArrayList<Object> secondArray = Array.findArray(elements[1]).getArray();
        String stringArray = deepToString(firstArray.toArray());
        stringArray = stringArray.replaceAll(" ", "");
        int dimension = Array.calculateDimension(stringArray);
        ArrayList<Object> multiple = Array.createArray(stringArray, dimension, Array.verifyType(stringArray));
        if (Array.findArray(elements[0]).getType() == ArrayType.INTEGER) {
            multipleIntegerArrayLists(firstArray, secondArray, multiple);
        } else {
            multipleFloatArrayLists(firstArray, secondArray, multiple);
        }
        result[1] = deepToString(multiple.toArray());
        result[1] = result[1].replaceAll(" ", "");
        return result;
    }

    public static void multipleIntegerArrayLists(ArrayList<Object> firstArray, ArrayList<Object> secondArray,
                                                 ArrayList<Object> multiple) {
        if (Array.calculateDimension(deepToString(multiple.toArray())) > 1) {
            for (int i = 0; i < multiple.size(); i++) {
                ArrayList<Object> newFirstArray = (ArrayList<Object>) firstArray.get(i);
                ArrayList<Object> newSecondArray = (ArrayList<Object>) secondArray.get(i);
                ArrayList<Object> newMultiple = (ArrayList<Object>) multiple.get(i);
                multipleIntegerArrayLists(newFirstArray, newSecondArray, newMultiple);
            }
        } else {
            for (int i = 0; i < multiple.size(); i++) {
                int firstElement = Integer.parseInt(firstArray.get(i).toString());
                int secondElement = Integer.parseInt(secondArray.get(i).toString());
                multiple.set(i, Integer.toString(firstElement * secondElement));
            }
        }
    }

    public static void multipleFloatArrayLists(ArrayList<Object> firstArray, ArrayList<Object> secondArray,
                                               ArrayList<Object> multiple) {
        if (Array.calculateDimension(deepToString(multiple.toArray())) > 1) {
            for (int i = 0; i < multiple.size(); i++) {
                ArrayList<Object> newFirstArray = (ArrayList<Object>) firstArray.get(i);
                ArrayList<Object> newSecondArray = (ArrayList<Object>) secondArray.get(i);
                ArrayList<Object> newMultiple = (ArrayList<Object>) multiple.get(i);
                multipleFloatArrayLists(newFirstArray, newSecondArray, newMultiple);
            }
        } else {
            for (int i = 0; i < multiple.size(); i++) {
                float firstElement = Float.parseFloat(firstArray.get(i).toString());
                float secondElement = Float.parseFloat(secondArray.get(i).toString());
                multiple.set(i, Float.toString(firstElement * secondElement));
            }
        }
    }

    public static void applyDivision(String[] elements, String name) {
        if (!checkIfTheElemntsAreFloatOrInteger(elements)) {
            System.out.println("wrong type");
        } else if (!checkForSameTypes(elements)) {
            System.out.println("inequality of types");
        } else if (Array.findArray(elements[0]).getDimension() != Array.findArray(elements[1]).getDimension()) {
            System.out.println("inequality of dimensions");
        } else if (!checkIfDimensionsAreSame(elements)) {
            System.out.println("inequality of sizes");
        } else {
            if (name.matches("[a-zA-Z]+[a-zA-Z[\\_[\\d]]]*")) {
                Array.create(divide(name, elements));
            } else {
                System.out.println("invalid name");
            }
        }
    }

    public static String[] divide(String name, String[] elements) {
        String[] result = new String[2];
        result[0] = name;
        ArrayList<Object> firstArray = Array.findArray(elements[0]).getArray();
        ArrayList<Object> secondArray = Array.findArray(elements[1]).getArray();
        String stringArray = deepToString(firstArray.toArray());
        stringArray = stringArray.replaceAll(" ", "");
        int dimension = Array.calculateDimension(stringArray);
        ArrayList<Object> divide = Array.createArray(stringArray, dimension, Array.verifyType(stringArray));
        if (Array.findArray(elements[0]).getType() == ArrayType.INTEGER) {
            divideIntegerArrayLists(firstArray, secondArray, divide);
        } else {
            divideFloatArrayLists(firstArray, secondArray, divide);
        }
        result[1] = deepToString(divide.toArray());
        result[1] = result[1].replaceAll(" ", "");
        return result;
    }

    public static void divideIntegerArrayLists(ArrayList<Object> firstArray, ArrayList<Object> secondArray,
                                               ArrayList<Object> divide) {
        if (Array.calculateDimension(deepToString(divide.toArray())) > 1) {
            for (int i = 0; i < divide.size(); i++) {
                ArrayList<Object> newFirstArray = (ArrayList<Object>) firstArray.get(i);
                ArrayList<Object> newSecondArray = (ArrayList<Object>) secondArray.get(i);
                ArrayList<Object> newDivide = (ArrayList<Object>) divide.get(i);
                divideIntegerArrayLists(newFirstArray, newSecondArray, newDivide);
            }
        } else {
            for (int i = 0; i < divide.size(); i++) {
                int firstElement = Integer.parseInt(firstArray.get(i).toString());
                int secondElement = Integer.parseInt(secondArray.get(i).toString());
                divide.set(i, Integer.toString(firstElement / secondElement));
            }
        }
    }

    public static void divideFloatArrayLists(ArrayList<Object> firstArray, ArrayList<Object> secondArray,
                                             ArrayList<Object> divide) {
        if (Array.calculateDimension(deepToString(divide.toArray())) > 1) {
            for (int i = 0; i < divide.size(); i++) {
                ArrayList<Object> newFirstArray = (ArrayList<Object>) firstArray.get(i);
                ArrayList<Object> newSecondArray = (ArrayList<Object>) secondArray.get(i);
                ArrayList<Object> newDivide = (ArrayList<Object>) divide.get(i);
                divideFloatArrayLists(newFirstArray, newSecondArray, newDivide);
            }
        } else {
            for (int i = 0; i < divide.size(); i++) {
                float firstElement = Float.parseFloat(firstArray.get(i).toString());
                float secondElement = Float.parseFloat(secondArray.get(i).toString());
                divide.set(i, Float.toString(firstElement / secondElement));
            }
        }
    }

    public static void applyHashtag(String[] elements, String name) {
        if (!checkIfTheElemntsAreStringOrChar(elements)) {
            System.out.println("wrong type");
        } else if (!checkForSameTypes(elements)) {
            System.out.println("inequality of types");
        } else if (Array.findArray(elements[0]).getDimension() != Array.findArray(elements[1]).getDimension()) {
            System.out.println("inequality of dimensions");
        } else if (!checkIfDimensionsAreSame(elements)) {
            System.out.println("inequality of sizes");
        } else {
            if (name.matches("[a-zA-Z]+[a-zA-Z[\\_[\\d]]]*")) {
                Array.create(affix(name, elements));
            } else {
                System.out.println("invalid name");
            }
        }
    }

    public static boolean checkIfTheElemntsAreStringOrChar(String[] elements) {
        ArrayType typeOne = Array.findArray(elements[0]).getType();
        ArrayType typTwo = Array.findArray(elements[1]).getType();
        if (typeOne == ArrayType.STRING || typeOne == ArrayType.CHAR) {
            return typTwo == ArrayType.STRING || typTwo == ArrayType.CHAR;
        }
        return false;
    }

    public static String[] affix(String name, String[] elements) {
        String[] result = new String[2];
        result[0] = name;
        ArrayList<Object> firstArray = Array.findArray(elements[0]).getArray();
        ArrayList<Object> secondArray = Array.findArray(elements[1]).getArray();
        String stringArray = deepToString(firstArray.toArray());
        stringArray = stringArray.replaceAll(" ", "");
        int dimension = Array.calculateDimension(stringArray);
        ArrayList<Object> affixed = Array.createArray(stringArray, dimension, Array.verifyType(stringArray));
        if (Array.findArray(elements[0]).getType() == ArrayType.STRING) {
            affixStringArrayLists(firstArray, secondArray, affixed);
        } else {
            affixCharArrayLists(firstArray, secondArray, affixed);
        }
        result[1] = deepToString(affixed.toArray());
        result[1] = result[1].replaceAll(" ", "");
        return result;
    }

    public static void affixStringArrayLists(ArrayList<Object> firstArray, ArrayList<Object> secondArray,
                                             ArrayList<Object> affixed) {
        if (Array.calculateDimension(deepToString(affixed.toArray())) > 1) {
            for (int i = 0; i < affixed.size(); i++) {
                ArrayList<Object> newFirstArray = (ArrayList<Object>) firstArray.get(i);
                ArrayList<Object> newSecondArray = (ArrayList<Object>) secondArray.get(i);
                ArrayList<Object> newAffixed = (ArrayList<Object>) affixed.get(i);
                affixStringArrayLists(newFirstArray, newSecondArray, newAffixed);
            }
        } else {
            for (int i = 0; i < affixed.size(); i++) {
                String firstElement = firstArray.get(i).toString();
                String secondElement = secondArray.get(i).toString();
                firstElement = firstElement.substring(0, firstElement.length() - 1);
                secondElement = secondElement.substring(1);
                affixed.set(i, (firstElement + secondElement));
            }
        }
    }

    public static void affixCharArrayLists(ArrayList<Object> firstArray, ArrayList<Object> secondArray,
                                           ArrayList<Object> affixed) {
        if (Array.calculateDimension(deepToString(affixed.toArray())) > 1) {
            for (int i = 0; i < affixed.size(); i++) {
                ArrayList<Object> newFirstArray = (ArrayList<Object>) firstArray.get(i);
                ArrayList<Object> newSecondArray = (ArrayList<Object>) secondArray.get(i);
                ArrayList<Object> newAffixed = (ArrayList<Object>) affixed.get(i);
                affixCharArrayLists(newFirstArray, newSecondArray, newAffixed);
            }
        } else {
            for (int i = 0; i < affixed.size(); i++) {
                String firstElement = firstArray.get(i).toString();
                String secondElement = secondArray.get(i).toString();
                firstElement = firstElement.substring(1, firstElement.length() - 1);
                secondElement = secondElement.substring(1, secondElement.length() - 1);
                affixed.set(i, ("\"" + firstElement + secondElement) + "\"");
            }
        }
    }
}
