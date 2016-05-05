import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tatsienko on 02.03.2016.
 */
public class ProbabilityMathEco {
    int[] variateListNew; //итератор комбинаций
    int quantities; //количество кубов
    long numberProbabilities; //количество возможных сочетаний
    int[] powTable; //таблица со степенями 6
    ArrayList<Integer> positiveHit = new ArrayList<>(); //список возможных успехов
    int requiredSumOfPositiveResult; //необходимое количество успехов, для зачета успешности броска
    long sumOfPositiveResult = 0; //количество возможных успешных бросков
    long result; //вероятность успешного броска
    byte positiveHitMin;
    int time;

    /*public static void main(String[] args) {
        //Initial parameters
        quantities = 6;
        requiredSumOfPositiveResult = 1;
        positiveHit = new ArrayList<>();
        positiveHit.add(6);
        positiveHit.add(5);
        positiveHit.add(4);

        //Initial code
        initialise();

        Date startTime = new Date();

        //start core code
        mainController();

        Date endTime = new Date();
        int elapsedTime = (int) (endTime.getTime() - startTime.getTime());

        result = (sumOfPositiveResult * 100 / numberProbabilities);

        //Output code
        System.out.println("Количество возможных комбинаций: " + numberProbabilities);
        System.out.println("Количество возможных успехов: " + sumOfPositiveResult);
        System.out.println("Вероятность успешного броска: " + result + "%");
        System.out.println("Затрачено времени на расчет: " + elapsedTime + " ms");

    }*/

    /*
    * Method iterate all possible combinations and transfer transmit them to positiveFounder
    * */
    public void mainController() {
        int[] counterNew = new int[variateListNew.length];
        int[] valueHolder = new int[variateListNew.length];

        //start values for local elements
        for (int n = 0; n < variateListNew.length; n++) {
            counterNew[n] = powTable[n];
            valueHolder[n] = 1; //initiate value creator
        }


        for (int line = 0; line < numberProbabilities; line++) {
            for (int i = 0; i < variateListNew.length; i++) {

                if (counterNew[i] >= 1) {
                    variateListNew[i] = valueHolder[i];
                    counterNew[i]--;
                }
                if (counterNew[i] < 1) {
                    counterNew[i] = powTable[i];
                    if (valueHolder[i] == 6) valueHolder[i] = 1;
                    else valueHolder[i]++;
                }

            }

            positiveFounder(variateListNew);
        }
        result = (sumOfPositiveResult * 100 / numberProbabilities);
        int endTime = (int) (new Date()).getTime();
        time = endTime - time;
    }

    /*
    * initialise a start values
    * */
    public void initialise() {
        time = (int) (new Date()).getTime();
        powTableCreator();
        numberProbabilities = (int) Math.pow(6, quantities);
        variateListNew = new int[quantities];
    }

    /*
    * Method create a table with result of often used Math.pow() function
    * */
    private void powTableCreator() {
        powTable = new int[quantities];
        for (int i = 0; i < quantities; i++) {
            powTable[i] = (int) Math.pow(6, i);
        }
    }

    /*
    * Method define a positive result of each line transmitted into, and incriminates result variable
    * */
    private void positiveFounder(int[] line) {
        int sucCounter = 0;
        for (int i = 0; i < line.length; i++) {
            if (positiveHit.contains(line[i])) sucCounter++;
        }
        if (sucCounter >= requiredSumOfPositiveResult) sumOfPositiveResult++;

    }

    public void byteToArrayOfPositiveHits() {
        int i = positiveHitMin;
        while (i <= 6) {
            positiveHit.add(i);
            i++;
        }
    }

    public String getResult() {
        return "" + result + "%";
    }

    public String getQuantitiesOfAvailableCombinations() {
        return "" + numberProbabilities;
    }

    public String getQuantitiesOfSuccesCombinations() {
        return "" + sumOfPositiveResult;
    }

    public String getTime() {
        return "" + time + " ms";
    }
}
