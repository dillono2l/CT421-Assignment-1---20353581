import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BinPacking{
    public static ArrayList<ArrayList<Integer>> bins = new ArrayList<>();
    public static int capacity;
    public static ArrayList<Integer> FitnessLogs = new ArrayList<>();
    public static void main(String[] args) throws FileNotFoundException {
        int numberOfStrings = 16;
        String filePath = "BPP5.txt";//change file name to change problem (BPP1 - BPP5)
        bins = initialize(filePath);
        System.out.println(bins);
        System.out.println(capacity);


        for(int i =0; i<100; i++)
        {
            ArrayList<Integer> selected = selection(2, bins.size());
            bins = onePointCrossover(selected, bins);
            if(i%5 == 0) bins = mutateBins(bins, 1);
            FitnessLogs.add(evaluateFitness(bins));
        }

        bins = mutateBins(bins, 4);


        System.out.println("fitness logs: " + FitnessLogs);
        System.out.println("Final fitness: " + evaluateFitness(bins));
        System.out.println(bins);



    }

    private static ArrayList<ArrayList<Integer>> initialize(String filePath) throws FileNotFoundException {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            String name = scanner.nextLine();
            System.out.println("Problem: " +name);

            int numItems = scanner.nextInt();

            capacity = scanner.nextInt();

        ArrayList<ArrayList<Integer>> bins = new ArrayList<>();
        while (scanner.hasNext()) {
            int weight = scanner.nextInt();
            int quantity = scanner.nextInt();
            for (int i = 0; i < quantity; i++) {
                ArrayList<Integer> bin = new ArrayList<>();
                bin.add(weight);
                bins.add(bin);
            }
        }

        return bins;
    }

    public static ArrayList<Integer> selection(int quantity, int range) {
        ArrayList<Integer> selectedDigits = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < quantity; i++) {
            int randomDigit = random.nextInt(range);
            selectedDigits.add(randomDigit);
        }

        return selectedDigits;
    }


    private static ArrayList<ArrayList<Integer>> onePointCrossover(ArrayList<Integer> selected, ArrayList<ArrayList<Integer>> binsArray) {
        ArrayList<ArrayList<Integer>> newBins = new ArrayList<>();
        ArrayList<ArrayList<Integer>> binsArr = new ArrayList<>(binsArray);
        for(int i =0; i < selected.size(); i++ ) {
            newBins.add(binsArr.get(selected.get(i)));//adding selected bins to temporary arraylist
            binsArray.remove(binsArr.get(selected.get(i)));
        }
        binsArr = binsArray;
        for(int j = 0; j<(newBins.size()-1); j += 2)
        {
            ArrayList<Integer> bin1 = newBins.get(j);
            ArrayList<Integer> bin2 = newBins.get(j + 1);
            int totalCap = getCapacity(bin1) + getCapacity(bin2);
            if(totalCap <= capacity)
            {
                bin1.addAll(bin2); //merge the two bins
                newBins.remove(j+1);
            }
        }
        binsArr.addAll(newBins); //add new bins to main
        return binsArr;
    }

    public static int getCapacity(ArrayList<Integer> bin)
    {
        int cap = 0;
        for(int i = 0; i< bin.size(); i++)
        {
            cap += bin.get(i);
        }
        return cap;
    }



    public static ArrayList<ArrayList<Integer>> mutateBins(ArrayList<ArrayList<Integer>> binsArr, int numberOfMutations) {
        Random random = new Random();

        for (int i = 0; i < numberOfMutations; i++) {
            // Select a random bin
            int binIndex = random.nextInt(binsArr.size());
            ArrayList<Integer> selectedBin = binsArr.get(binIndex);

            // If the bin is close to full, remove a random element and add it to its own bin
            if (getCapacity(selectedBin) > 850) {
                int elementIndexToRemove = random.nextInt(selectedBin.size());
                int removedElement = selectedBin.remove(elementIndexToRemove);

                // Create a new bin containing the removed element
                ArrayList<Integer> newBin = new ArrayList<>();
                newBin.add(removedElement);
                binsArr.add(newBin);
            }
        }
        return binsArr;
    }

    private static int evaluateFitness(ArrayList<ArrayList<Integer>> binsArr) {
        return binsArr.size();
    }
}
