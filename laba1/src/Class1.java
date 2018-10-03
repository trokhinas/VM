import java.io.*;
import java.util.ArrayList;

public class Class1 {
    private ArrayList<Double> X;
    private ArrayList<Double> Y;
    private double XX;
    private double EPS;
    private int N;

    private String input = "laba1\\src\\InputData\\InputData.txt";
    private String output = "OutData.txt";


    private int left, right;//левая и правая границы участка вычисления(индексы)
    private int power = 1;//Степень многолчена Лагранжа = количество_точек - 1


    /**
     * @return значение полинома Лагранжа в заданной степени для точки XX
     */
    private double Lagrange() {
        double sum = 0;
        for (int i = left; i <= right; i++)
            sum += Y.get(i) * l_(i);//значение полинома Лагранжа = СУММА{i = left..right} f_i*l_i
        System.out.println("L_" + (right - left) + " = " + sum);
        System.out.println("---------------------------------------------------------------------");
        return sum;
    }
    /**
     * @param j - номер вычисляемого многочлена для полинома Лагранжа j >= 1 && j < N
     * @return значение многочлена степени N
     */
    private double l_(int j) {
        StringBuilder Top, Bot;
        Top = new StringBuilder();
        Bot = new StringBuilder();
        double top = 1, bot = 1, x_j = X.get(j);
        for (int i = left; i <= right; i++) {
            if (i != j) {
                double x_i = X.get(i);
                top *= (XX - x_i);//(x - x_i)
                bot *= (x_j - x_i);//(x_j - x_i)
                Top.append("(").append(XX).append(" - ").append(x_i).append(")");
                Bot.append("(").append(x_j).append(" - ").append(x_i).append(")");
            }

        }
        System.out.println(Top + " = " + top);
        System.out.println(Bot + " = " + bot);
        System.out.println();
        return top / bot;
    }

    private void addNearestPoint() throws NotEnoughPointsError {
        if (power < N - 1) {//power - степень полинома Лагранжа, которая < N
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
            power++;
        }
        else throw new NotEnoughPointsError();
    }
    private void addLeft(){
        left --;
    }
    private void addRight(){
        right++;
    }

    public Class1() {
        Data dataFromInput = new Data(input, output);
        X = dataFromInput.getXVector();Y = dataFromInput.getYVector();
        XX = dataFromInput.getXX();N = dataFromInput.getN();EPS = dataFromInput.getEPS();

        left = right = 0;
        for (int i = 0; i < N && right == 0; i++){
            if (X.get(i) >= XX)
                right = i;
        }
        left = right - 1;
    }



    private void Calculate() throws NotEnoughPointsError, WeakAccuracyError {
        double newLagrange = Lagrange(), accuracy;//сразу же вычисляем значение L_1
        Checker checker = new Checker(EPS);
        do {
            double lastLagrange = newLagrange;
            addNearestPoint();//добавляем точку(если это возможно)
            newLagrange = Lagrange();//вычисляем значение полином Лагранжа высшей степени
            accuracy = Math.abs(Math.abs(newLagrange) - Math.abs(lastLagrange));//вычисление погрешности
            //также не учитываем значение погрешности при m < 2
            if (!checker.checkWeakAccuracy(accuracy)) throw new WeakAccuracyError();
        } while (!checker.checkAccuracy(accuracy));

        String msg = "Код ошибки: " + String.valueOf(Error.IER0.getCode()) + '\n'
                + Error.IER0.toString() + '\n'
                + "Y = " + newLagrange + '\n'
                + "Был построен многочлен степени " + power + '\n'
                + "EPS = " + accuracy;
        try {
            PrintStream ps = new PrintStream(output);
            ps.println(msg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(msg);
    }

    public void startAlgorithm() {
        try {
            Calculate();
        } catch (LagrangeError error) {
            LagrangePrinter lp = new LagrangePrinter(output);
            try { lp.print(error); }
            catch (FileNotFoundException e) { e.printStackTrace(); }
            System.exit(error.getCode());
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