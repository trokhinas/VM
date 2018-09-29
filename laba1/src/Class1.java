import java.io.*;
import java.util.ArrayList;




/**
 * Created by admin on 25.09.2018.
 */
public class Class1 {
    private ArrayList<Double> X;
    private ArrayList<Double> Y;
    private double XX;
    private double EPS;
    private int N;

    private String input = "laba1\\src\\InputData.txt";
    private String output = "OutData.txt";


    private int left, right;//левая и правая границы участка вычисления(индексы)


    /**
     * @return значение полинома Лагранжа в заданной степени для точки XX
     */
    private double Lagrange() {
        double sum = 0;
        for (int i = left; i <= right; i++)
            sum += Y.get(i) * l_(i);
        return sum;
    }
    /**
     * @param number - номер вычисляемого многочлена для полинома Лагранжа number >= 1 && number < N
     * @return значение многочлена степени N
     */
    private double l_(int number) {
        double top = 1, bot = 1, x_k = X.get(number);
        for (int i = left; i <= right; i++) {
            if (i != number) {
                double x_i = X.get(i);
                top *= (XX - x_i);
                bot *= (x_k - x_i);
            }

        }
        return top / bot;
    }

    /*
    * эта функция нуждается в доработке(некрасивая)
    * */
    private void addNearestPoint() throws NotEnoughPointsError {
        if (right - left != N - 1) {
            if(right == N - 1)
                addLeft();
            else if(left == 0)
                addRight();
            else {
                if (XX - X.get(left - 1) < X.get(right + 1) - XX)
                    addLeft();
                else
                    addRight();
            }
        }
        else throw new NotEnoughPointsError();

    }
    private void addLeft(){
        left --;
    }
    private void addRight(){
        right++;
    }

    Class1() {
        Data dataFromInput = new Data(input, output);
        X = dataFromInput.getXVector();
        Y = dataFromInput.getYVector();
        XX = dataFromInput.getXX();
        N = dataFromInput.getN();
        EPS = dataFromInput.getEPS();

        left = right = 0;
        for (int i = 0; i < N && right == 0; i++){
            if (X.get(i) >= XX)
                right = i;
        }
        left = right - 1;

    }



    private void Calculate() throws NotEnoughPointsError, WeakAccuracyError {
        double newLagrange = Lagrange(), accuracy;
        Checker checker = new Checker(EPS);
        do {
            System.out.println(newLagrange);
            double lastLagrange = newLagrange;

            addNearestPoint();
            newLagrange = Lagrange();
            accuracy = Math.abs(newLagrange - lastLagrange);
            if (!checker.checkWeakAccuracy(accuracy)) throw new WeakAccuracyError();
        } while (!checker.checkAccuracy(accuracy));
        print(String.valueOf(Error.IER0) + " " + "\nY = " + newLagrange);
    }

    public void startAlgorithm() {
        try {
            Calculate();
        } catch (LagrangeError notEnoughPointsError) {
            try {
                notEnoughPointsError.printStackTrace(new PrintStream(output));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            notEnoughPointsError.printStackTrace();
        }

    }



    private void print(String msg) {
        System.out.print(msg);
        try {
            PrintStream ps = new PrintStream(output);
            ps.print(msg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        Class1 a = new Class1();
        a.startAlgorithm();
    }
}