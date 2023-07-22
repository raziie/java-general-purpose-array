package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Array {
    private String name;

    private ArrayType type;

    private ArrayList<Object> array;

    private int dimension;

    private ArrayList<Integer> dimensions;

    public Array(String name, ArrayType type, ArrayList<Object> array, int dimension, ArrayList<Integer> dimensions) {
        this.name = name;
        this.type = type;
        this.array = array;
        this.dimension = dimension;
        this.dimensions = dimensions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ArrayType type) {
        this.type = type;
    }

    public void setArray(ArrayList<Object> array) {
        this.array = array;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void setDimensions(ArrayList<Integer> dimensions) {
        this.dimensions = dimensions;
    }

    public String getName() {
        return name;
    }

    public ArrayType getType() {
        return type;
    }

    public ArrayList<Object> getArray() {
        return array;
    }

    public int getDimension() {
        return dimension;
    }

    public ArrayList<Integer> getDimensions() {
        return dimensions;
    }

    public static boolean nameIsRepeated(String name) {
        for (Array array : Main.arrays) {
            if (array.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static int findIndexOfSimilarArray(String name) {
        for (int i = 0; i < Main.arrays.size(); i++) {
            if (Main.arrays.get(i).getName().equals(name)) {
                return i;
            }
        }
        return 0;
    }

    public static int calculateDimension(String array) {
        int dimension = 0;
        for (int i = 0; i < array.length(); i++) {
            if (array.charAt(i) == '[') {
                dimension++;
            } else {
                break;
            }
        }
        return dimension;
    }

    public static ArrayList<Object> createArray(String inputArray, int dimension, ArrayType type) {
        ArrayList<Object> array = new ArrayList<>();
        if (inputArray.charAt(1) == '[') {
            inputArray = inputArray.substring(1, inputArray.length() - 1);
            String regex = "", replacement = "";
            for (int i = 0; i < dimension - 1; i++) {
                regex += ("]");
                replacement += ("]");
            }
            regex += ",";
            replacement += "!";
            inputArray = inputArray.replaceAll(regex, replacement);
            String[] objects = inputArray.split("!");
            for (String object : objects) {
                array.add(createArray(object, dimension - 1, type));
            }
        } else {
            inputArray = inputArray.substring(1, inputArray.length() - 1);
            String[] inputArraySplit = inputArray.split(",");
            if (type == ArrayType.FLOAT) {
                addZeroIfNeeded(inputArraySplit);
            }
            return new ArrayList<>(Arrays.asList(inputArraySplit));
        }
        return array;
    }

    public static void addZeroIfNeeded(String[] inputArraySplit) {
        for (int i = 0; i < inputArraySplit.length; i++) {
            if (inputArraySplit[i].matches("\\d+\\.")) {
                inputArraySplit[i] += "0";
            } else if (inputArraySplit[i].matches("\\.\\d+")) {
                inputArraySplit[i] = "0" + inputArraySplit[i];
            }
        }
    }

    public static ArrayType verifyType(String input) {
        input = input.replaceAll("\\]|\\[", "");
        String[] objects = input.split(",");
        if (objects[0].matches("\\d*\\.\\d*") && objects[0].length() != 1) {
            return ArrayType.FLOAT;
        } else if (objects[0].charAt(0) == '"' && objects[0].charAt(objects[0].length() - 1) == '"') {
            return ArrayType.STRING;
        } else if (objects[0].charAt(0) == '\'' && objects[0].charAt(2) == '\'') {
            return ArrayType.CHAR;
        } else {
            return ArrayType.INTEGER;
        }
    }

    public static ArrayList<Integer> calculateDimensions(String inputArray, int dimension) {
        ArrayList<Integer> result = new ArrayList<>();
        while (inputArray.charAt(0) == '[') {
            if (inputArray.charAt(1) == '[') {
                inputArray = inputArray.substring(1, inputArray.length() - 1);
                String regex = "", replacement = "";
                for (int i = 0; i < dimension - 1; i++) {
                    regex += ("]");
                    replacement += ("]");
                }
                regex += ",";
                replacement += "!";
                inputArray = inputArray.replaceAll(regex, replacement);
                String[] objects = inputArray.split("!");
                inputArray = objects[0];
                result.add(objects.length);
                dimension--;
            } else {
                inputArray = inputArray.substring(1, inputArray.length() - 1);
                if (dimension != 1) {
                    String[] objects = inputArray.split("\\]\\!\\[");
                    inputArray = objects[0];
                }
                String[] inputArraySplit = inputArray.split(",");
                result.add(inputArraySplit.length);
            }
        }
        return result;
    }

    public static ArrayType checkType(String input) {
        if (input.matches("\\d*\\.\\d*") && input.length() != 1) {
            return ArrayType.FLOAT;
        } else if (input.charAt(0) == '"' && input.charAt(input.length() - 1) == '"') {
            return ArrayType.STRING;
        } else if (input.charAt(0) == '\'' && input.charAt(2) == '\'') {
            return ArrayType.CHAR;
        } else if (input.matches("-?\\d+")) {
            return ArrayType.INTEGER;
        }
        return null;
    }

    public static boolean checkForInvalidType(String input) {
        input = input.replaceAll("\\]|\\[", "");
        String[] objects = input.split(",");
        for (String object : objects) {
            if (object.matches("\\d*\\.\\d*") && object.length() != 1) {
                continue;
            } else if (object.charAt(0) == '"' && object.charAt(object.length() - 1) == '"') {
                continue;
            } else if (object.charAt(0) == '\'' && object.charAt(2) == '\'') {
                continue;
            } else if (object.matches("-?\\d+")) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }

    public static boolean checkForInequalType(String input) {
        input = input.replaceAll("\\]|\\[", "");
        String[] objects = input.split(",");
        if (objects[0].matches("\\d*\\.\\d*") && objects[0].length() != 1) {
            return !otherObjectsAreTypeOne(objects);
        } else if (objects[0].charAt(0) == '"' && objects[0].charAt(objects[0].length() - 1) == '"') {
            return !otherObjectsAreTypeTwo(objects);
        } else if (objects[0].charAt(0) == '\'' && objects[0].charAt(2) == '\'') {
            return !otherObjectsAreTypeThree(objects);
        } else {
            return !otherObjectsAreTypeFour(objects);
        }
    }

    public static boolean otherObjectsAreTypeOne(String[] objects) {
        for (String object : objects) {
            if ((object.matches("\\d*\\.\\d*") && object.length() != 1)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean otherObjectsAreTypeTwo(String[] objects) {
        for (String object : objects) {
            if (object.charAt(0) == '"' && object.charAt(object.length() - 1) == '"') {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean otherObjectsAreTypeThree(String[] objects) {
        for (String object : objects) {
            if (object.charAt(0) == '\'' && object.charAt(2) == '\'') {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean otherObjectsAreTypeFour(String[] objects) {
        for (String object : objects) {
            if (object.matches("-?\\d+")) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean checkForDifferentLength(String input) {
        while (input.charAt(1) == '[') {
            input = input.substring(1, input.length() - 1);
        }
        input = input.replaceAll("],", "]!");
        String[] objects = input.split("!");
        for (int i = 0; i < objects.length - 1; i++) {
            if (calculateCamas(objects[i]) != calculateCamas(objects[i + 1])) {
                return true;
            }
        }
        return false;
    }

    public static int calculateCamas(String input) {
        int camas = 0;
        Pattern cama = Pattern.compile(",");
        Matcher string = cama.matcher(input);
        while (string.find()) {
            camas++;
        }
        return camas;
    }

    public static Array findArray(String name) {
        for (Array array : Main.arrays) {
            if (name.equals(array.getName())) {
                return array;
            }
        }
        return null;
    }

    public static int findIndexOfArray(String name) {
        for (int i = 0; i < Main.arrays.size(); i++) {
            if (name.equals(Main.arrays.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    public static void updateArray(ArrayList<Object> array, String[] indexes, String substitute) {
        String[] restOfIndexes = Arrays.copyOfRange(indexes, 1, indexes.length);
        if (indexes.length > 1) {
            updateArray((ArrayList<Object>) array.get(Integer.parseInt(indexes[0])), restOfIndexes, substitute);
        } else {
            array.set(Integer.parseInt(indexes[0]), substitute);
        }
    }

    public static boolean checkForOutOfBounds(ArrayList<Integer> dimensions, String[] indexes) {
        for (int i = 0; i < indexes.length; i++) {
            if (Integer.parseInt(indexes[i]) >= dimensions.get(i)) {
                return true;
            }
        }
        return false;
    }

    public static void printArray(ArrayList<Object> array, int dimension) {
        int[] dimensions = new int[array.size()];
        Arrays.fill(dimensions, dimension);
        System.out.print("[");
        for (int i = 0; i < array.size(); i++) {
            if (dimensions[i] > 1) {
                dimensions[i]--;
                printArray((ArrayList<Object>) array.get(i), dimensions[i]);
            } else {
                System.out.print(array.get(i));
                if (i != array.size() - 1) {
                    System.out.print(" ");
                }
            }
        }
        System.out.print("]");
    }

    public static void create(String[] halves) {
        if (checkForInvalidType(halves[1])) {
            System.out.println("unknown type");
        } else if (checkForInequalType(halves[1])) {
            System.out.println("inequality of types");
        } else if (checkForDifferentLength(halves[1])) {
            System.out.println("different lengths or shapes");
        } else {
            int dimension = calculateDimension(halves[1]);
            ArrayList<Integer> dimensions = calculateDimensions(halves[1], dimension);
            ArrayList<Object> array = createArray(halves[1], dimension, verifyType(halves[1]));
            Array theArray = new Array(halves[0], verifyType(halves[1]), array, dimension, dimensions);
            if (nameIsRepeated(halves[0])) {
                Main.arrays.set(findIndexOfSimilarArray(halves[0]), theArray);
            } else {
                Main.arrays.add(theArray);
            }
            printArray(theArray.getArray(), theArray.getDimension());
        }
    }

    public static void change(String[] halves) {
        Pattern endOfName = Pattern.compile("\\[");
        Matcher string = endOfName.matcher(halves[0]);
        int bracketIndex = 0;
        if (string.find()) {
            bracketIndex = string.start();
        }
        String name = halves[0].substring(0, bracketIndex);
        String[] indexes = halves[0].substring(bracketIndex + 1, halves[0].length() - 1).split("\\]\\[");
        String substitute = halves[1];
        Array array = Array.findArray(name);
        int indexOfArray = findIndexOfArray(name);
        if ((array.getDimension() != indexes.length)) {
            System.out.println("out of bounds");
        } else if (array.getType() != checkType(substitute)) {
            System.out.println("inequality of types");
        } else if (checkForOutOfBounds(array.getDimensions(), indexes)) {
            System.out.println("out of bounds");
        } else {
            updateArray(array.getArray(), indexes, substitute);
            Main.arrays.set(indexOfArray, array);
            printArray(array.getArray(), array.getDimension());
        }
    }
}
