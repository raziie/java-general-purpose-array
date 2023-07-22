package ir.ac.kntu;

import java.util.ArrayList;

import static java.util.Arrays.deepToString;

public class Others {
    public static boolean checkIfItContainsOtherOperators(String input) {
        if (input.contains("@")) {
            return true;
        } else if (input.contains("&")) {
            return true;
        } else {
            return false;
        }
    }

    public static void applyOtherOperators(String[] halves) {
        if (halves[1].contains("@")) {
            String[] elements = halves[1].split("@");
            applyAtSign(elements, halves[0]);
        } else if (halves[1].contains("&")) {
            halves[1] = halves[1].substring(1);
            applyAnd(halves);
        }
    }

    public static void applyAtSign(String[] elements, String name) {
        int firstArraydimension = Array.findArray(elements[0]).getDimension();
        int secondArrayDimension = Array.findArray(elements[1]).getDimension();
        if (!Operators.checkIfTheElemntsAreFloatOrInteger(elements)) {
            System.out.println("wrong type");
        } else if (!Operators.checkForSameTypes(elements)) {
            System.out.println("inequality of types");
        } else if (!(firstArraydimension == 2) || !(secondArrayDimension == 2)) {
            System.out.println("wrong dimension");
        } else if (!checkIfDimensionsAreCompitable(elements)) {
            System.out.println("incompatible dimension");
        } else {
            if (name.matches("[a-zA-Z]+[a-zA-Z[\\_[\\d]]]*")) {
                Array.create(multipleMatrixes(name, elements));
            } else {
                System.out.println("invalid name");
            }
        }
    }

    public static boolean checkIfDimensionsAreCompitable(String[] elements) {
        ArrayList<Integer> dimensionsOne = Array.findArray(elements[0]).getDimensions();
        ArrayList<Integer> dimensionsTwo = Array.findArray(elements[1]).getDimensions();
        return dimensionsOne.get(1) == dimensionsTwo.get(0);
    }


    public static String[] multipleMatrixes(String name, String[] elements) {
        String[] result = new String[2];
        result[0] = name;
        ArrayList<Object> firstArray = Array.findArray(elements[0]).getArray();
        ArrayList<Object> secondArray = Array.findArray(elements[1]).getArray();
        int row = Array.findArray(elements[0]).getDimensions().get(0);
        int column = Array.findArray(elements[1]).getDimensions().get(1);
        if (Array.findArray(elements[0]).getType() == ArrayType.FLOAT) {
            Float[][] product = multipleMatrixesF(changeStyleF(firstArray), changeStyleF(secondArray), row, column);
            result[1] = deepToString(product);
        } else {
            int[][] product = multipleMatrixesI(changeStyleI(firstArray), changeStyleI(secondArray), row, column);
            result[1] = deepToString(product);
        }
        result[1] = result[1].replaceAll(" ", "");
        return result;
    }

    public static ArrayList<ArrayList<Float>> changeStyleF(ArrayList<Object> array) {
        ArrayList<ArrayList<Float>> result = new ArrayList<>();
        for (Object obj : array) {
            ArrayList<Float> subArray = new ArrayList<>();
            for (String obj2 : (ArrayList<String>) obj) {
                subArray.add(Float.valueOf(obj2));
            }
            result.add(subArray);
        }
        return result;
    }

    public static ArrayList<ArrayList<Integer>> changeStyleI(ArrayList<Object> array) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (Object obj : array) {
            ArrayList<Integer> subArray = new ArrayList<>();
            for (String obj2 : (ArrayList<String>) obj) {
                subArray.add(Integer.valueOf(obj2));
            }
            result.add(subArray);
        }
        return result;
    }

    public static int[][] multipleMatrixesI(ArrayList<ArrayList<Integer>> firstArray,
                                            ArrayList<ArrayList<Integer>> secondArray, int row, int column) {
        int[][] result = new int[row][column];
        int rows = firstArray.get(0).size();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                int element = 0;
                for (int k = 0; k < rows; k++) {
                    element += (firstArray.get(i).get(k)) * (secondArray.get(k).get(j));
                }
                result[i][j] = element;
            }
        }
        return result;
    }

    public static Float[][] multipleMatrixesF(ArrayList<ArrayList<Float>> firstArray,
                                              ArrayList<ArrayList<Float>> secondArray, int row, int column) {
        Float[][] result = new Float[row][column];
        int rows = firstArray.get(0).size();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                Float element = Float.valueOf(0);
                for (int k = 0; k < rows; k++) {
                    element += (firstArray.get(i).get(k)) * (secondArray.get(k).get(j));
                }
                result[i][j] = element;
            }
        }
        return result;
    }

    public static void applyAnd(String[] halves) {
        ArrayType type = Array.findArray(halves[1]).getType();
        if (!(type == ArrayType.FLOAT || type == ArrayType.INTEGER)) {
            System.out.println("wrong type");
        } else if (Array.findArray(halves[1]).getDimension() != 2) {
            System.out.println("wrong dimension");
        } else {
            if (halves[0].matches("[a-zA-Z]+[a-zA-Z[\\_[\\d]]]*")) {
                Array.create(transpose(halves[0], halves[1]));
            } else {
                System.out.println("invalid name");
            }
        }
    }

    public static String[] transpose(String name, String array) {
        String[] result = new String[2];
        result[0] = name;
        ArrayList<Object> actualArray = Array.findArray(array).getArray();
        ArrayList<ArrayList<String>> theArray = (ArrayList<ArrayList<String>>) (ArrayList<?>) actualArray;
        ArrayList<Integer> dimensions = Array.findArray(array).getDimensions();
        String[][] transposed = new String[dimensions.get(1)][dimensions.get(0)];
        for (int i = 0; i < dimensions.get(0); i++) {
            for (int j = 0; j < dimensions.get(1); j++) {
                transposed[j][i] = (theArray.get(i).get(j));
            }
        }
        result[1] = deepToString(transposed);
        result[1] = result[1].replaceAll(" ", "");
        return result;
    }
}
