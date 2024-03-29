import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class TargetString{
    public static ArrayList<Integer> avgerageFitnessLogs = new ArrayList<>();
    public static ArrayList<Integer> fitnessLog = new ArrayList<>();
    public static String target = "100011001001001111110010010011";
    public static void main(String[] args) {
        int numberOfStrings = 16;
        ArrayList<String> stringList = initializeStrings(numberOfStrings, 30);

        //System.out.println("Original Strings:");
        //printStrings(stringList);
        evaluateFitness(stringList);

        for (int gen= 1; gen<= 50; gen++) {// gen = num generations
            onePointCrossover(stringList, 6);
            fitnessLog.clear();
            mutation(stringList);
            evaluateFitness(stringList);
            System.out.println("Generation " +gen+ ": "  +fitnessLog);
        }

        //System.out.println("\nFinal Population:");
        //printStrings(stringList);
        System.out.println("average fittness: " + avgerageFitnessLogs);




    }

    private static ArrayList<String> initializeStrings(int numberOfStrings, int stringLength) {
        ArrayList<String> stringList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfStrings; i++) {
            stringList.add(generateString(stringLength, random));
        }

        return stringList;
    }

    private static String generateString(int length, Random random) {
        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomBit = random.nextInt(2); // Generates either 0 or 1
            binaryString.append(randomBit);
        }
        return binaryString.toString();
    }

    private static void printStrings(ArrayList<String> stringList) {
        for (String str : stringList) {
            System.out.println(str);
        }
    }



    private static void onePointCrossover(ArrayList<String> stringList, int topN) {

        ArrayList<String> sortedStrings = new ArrayList<>(stringList);
        Collections.sort(sortedStrings, (s1, s2) -> Double.compare(fitnessLog.get(stringList.indexOf(s2)), fitnessLog.get(stringList.indexOf(s1))));

        Random random = new Random();
        for (int i = 0; i < topN; i += 2) {
            if (i + 1 < topN) {
                int crossoverPoint = random.nextInt(sortedStrings.get(i).length());
                String firstPart = sortedStrings.get(i).substring(0, crossoverPoint) +
                        sortedStrings.get(i + 1).substring(crossoverPoint);
                String secondPart = sortedStrings.get(i + 1).substring(0, crossoverPoint) +
                        sortedStrings.get(i).substring(crossoverPoint);

                stringList.add(firstPart);
                stringList.add(secondPart);
            }
        }
    }





    private static void mutation(ArrayList<String> stringList) {
        int numberOfStrings = stringList.size();
        Random random = new Random();
        double mutationRate = 0.01; // Adjust mutation rate

        for (int i = 0; i < numberOfStrings; i++) {
            StringBuilder mutatedString = new StringBuilder(stringList.get(i));

            for (int j = 0; j < mutatedString.length(); j++) {
                if (random.nextDouble() < mutationRate) {
                    mutatedString.setCharAt(j, (char) ('1' - mutatedString.charAt(j) + '0'));
                }
            }

            // Update the string in the list after mutation
            stringList.set(i, mutatedString.toString());
        }
    }

    private static void evaluateFitness(ArrayList<String> stringList) {
        // Assuming fitness is calculated based on the number of '1's in the string
        int total = 0;
        int avg = 0;
        for (int i = 0; i < stringList.size(); i++) {
            int fitness = 0;
            for (int j = 0; j < stringList.get(i).length(); j++) {
                if (stringList.get(i).charAt(j) == target.charAt(j)) {
                    fitness++;
                }
            }
            fitnessLog.add(fitness);
            total += fitness;
        }
        avg = total/ stringList.size();
        avgerageFitnessLogs.add(avg);
    }
}
