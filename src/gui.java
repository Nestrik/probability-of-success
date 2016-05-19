import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.GroupLayout.Alignment.LEADING;

/**
 * Created by tatsienko on 03.03.2016.
 */
public class gui {
    Object[] headers = {"Name", "Description"};
    static gui mainPage = new gui();

    //Elements of main page
    JFrame mainFrame;

    SpinnerModel successQuantiseM = new SpinnerNumberModel(4, 1, 6, 1);
    SpinnerModel quantiseOfCubesM = new SpinnerNumberModel(2, 1, 12, 1);
    SpinnerModel requiredQuantiseOfSuccessM = new SpinnerNumberModel(1, 1, 12, 1);

    JSpinner successQuantiseT = new JSpinner(successQuantiseM);
    JSpinner quantiseOfCubesT = new JSpinner(quantiseOfCubesM);
    JSpinner requiredQuantiseOfSuccessT = new JSpinner(requiredQuantiseOfSuccessM);

    JTextField successProbabilityT;
    JTextField qAvailableCombinationsT;
    JTextField qSuccessCombinationsT;

    JTextField bernulliTimeRequiredT;
    JTextField bernulliSuccesResultT;

    JLabel successQuantiseL;
    JLabel quantiseOfCubesL;
    JLabel requiredQuantiseOfSuccessL;
    JLabel successProbabilityL;
    JLabel qAvailableCombinationsL;
    JLabel qSuccessCombinationsL;
    JLabel sError;
    JLabel sTime;

    JButton bCalculation;
    JButton bStopCalculation;

    public static void main(String[] args) {
        mainPage.createMainForm();
    }

    void createMainForm() {
        mainFrame = new JFrame();

        sError = new JLabel(" ");
        sTime = new JLabel(" ");

        successQuantiseL = new JLabel("Значения равно и выше которого куб считается Успехом");
        quantiseOfCubesL = new JLabel("Количество бросаемых костей");
        requiredQuantiseOfSuccessL = new JLabel("Количество необходимых Успехов");
        successProbabilityL = new JLabel("Вероятность Выигрышного броска");
        qAvailableCombinationsL = new JLabel("Количество возможных комбинаций");
        qSuccessCombinationsL = new JLabel("Количество возможных успешных комбинаций");

        successProbabilityT = new JTextField("%");
        qAvailableCombinationsT = new JTextField("number");
        qSuccessCombinationsT = new JTextField("number");
        bernulliTimeRequiredT = new JTextField("");
        bernulliSuccesResultT = new JTextField("");

        bCalculation = new JButton("Считать");
        bStopCalculation = new JButton("Остановить расчет");

        bCalculation.addActionListener(new StartCalculation());


        GroupLayout layout = new GroupLayout(mainFrame.getContentPane());
        mainFrame.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(successQuantiseL)
                        .addComponent(successQuantiseT))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(quantiseOfCubesL)
                        .addComponent(quantiseOfCubesT))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(requiredQuantiseOfSuccessL)
                        .addComponent(requiredQuantiseOfSuccessT))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(successProbabilityL)
                        .addComponent(successProbabilityT))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(qAvailableCombinationsL)
                        .addComponent(qAvailableCombinationsT))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(qSuccessCombinationsL)
                        .addComponent(qSuccessCombinationsT))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(sError)
                        .addComponent(sTime))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(bCalculation)
                        .addComponent(bStopCalculation))
                .addGroup(layout.createParallelGroup()
                        .addComponent(bernulliSuccesResultT)
                        .addComponent(bernulliTimeRequiredT))
        );

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(successQuantiseL)
                        .addComponent(quantiseOfCubesL)
                        .addComponent(requiredQuantiseOfSuccessL)
                        .addComponent(successProbabilityL)
                        .addComponent(qAvailableCombinationsL)
                        .addComponent(qSuccessCombinationsL)
                        .addComponent(sError)
                        .addComponent(bCalculation)
                        .addComponent(bernulliTimeRequiredT))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(successQuantiseT)
                        .addComponent(quantiseOfCubesT)
                        .addComponent(requiredQuantiseOfSuccessT)
                        .addComponent(successProbabilityT)
                        .addComponent(qAvailableCombinationsT)
                        .addComponent(qSuccessCombinationsT)
                        .addComponent(sTime)
                        .addComponent(bStopCalculation)
                        .addComponent(bernulliSuccesResultT))
        );

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    //Listener to StartCalculation button key
    private class StartCalculation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ProbabilityMathEco calcTry = new ProbabilityMathEco();
                calcTry.quantities = Integer.parseInt(quantiseOfCubesT.getValue().toString());
                calcTry.positiveHitMin = Byte.parseByte(successQuantiseT.getValue().toString());
                calcTry.byteToArrayOfPositiveHits();
                calcTry.requiredSumOfPositiveResult = Integer.parseInt(requiredQuantiseOfSuccessT.getValue().toString());

                calcTry.calculateWithBernulliFormula();
                bernulliSuccesResultT.setText(calcTry.getResultBer());
                bernulliTimeRequiredT.setText(calcTry.getTimeBer());

                /*calcTry.initialise();
                calcTry.mainController();
                successProbabilityT.setText(calcTry.getResult());
                qAvailableCombinationsT.setText(calcTry.getQuantitiesOfAvailableCombinations());
                qSuccessCombinationsT.setText(calcTry.getQuantitiesOfSuccesCombinations());*/

                sError.setText("Расчет выполнен!");
                //sTime.setText(calcTry.getTime());
                mainFrame.pack();
                mainFrame.repaint();
            } catch (Exception except) {
                sError.setText("Неизвестная ошибка!");
                mainFrame.pack();
                mainFrame.repaint();
            }
        }
    }

    private class StopCalculation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            /*Do nothing*/
        }
    }

    private class RefreshButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            /*Do nothing*/
        }
    }
}
