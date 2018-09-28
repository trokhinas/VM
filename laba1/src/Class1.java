import java.io.*;
import java.util.ArrayList;


/**
 * Created by admin on 25.09.2018.
 */
public class Class1 {
    private ArrayList<Double> X = new ArrayList<>();
    private ArrayList<Double> Y = new ArrayList<>();
    private double XX;
    private double EPS;
    private int N;


    private int left, right;//левая и правая границы участка вычисления(индексы)
    private double lastLagrange;
    private double lastEps;




    private boolean CheckAccuracy(double eps) {
        if (eps<lastEps){
            lastEps=eps;
            return true;
        }else if (eps>lastEps){
            Exit(Error.IER2);
        }
        return false;
    }




    /**
     * @return значение полинома Лагранжа в заданной степени для точки XX
     */
    private double Lagrange() {
        double sum = 0;
        for (int i = left; i <= right; i++)
            sum += Y.get(i) * l(i);
        return sum;
    }

    /**
     * @param number - номер вычисляемого многочлена для полинома Лагранжа number >= 1 && number < N
     * @return значение многочлена степени N
     */
    private double l(int number) {
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
    private void addNearestPoint() {
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
        else Exit(Error.IER1);

    }
    private void addLeft(){
        left --;
    }
    private void addRight(){
        right++;
    }

    private void Exit(Error e) {
        System.out.print(e);
        try {
            PrintWriter brWriter = new PrintWriter("OutData.txt");
            if (e==Error.IER0){
                brWriter.print(lastLagrange + "\n");
            }
            brWriter.print(e);
            brWriter.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        System.exit(e.getCode());
    }

    Class1() {
        Data dataFromInput = new Data();
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
    public void Calculate() throws FileNotFoundException {
        double newLagrange = Lagrange();

        do{
            lastLagrange = newLagrange;
            addNearestPoint();
            newLagrange = Lagrange();
        }while(CheckAccuracy(Math.abs(newLagrange - lastLagrange)));
        System.out.println(lastLagrange);
        Exit(Error.IER0);
    }


    public static void main(String[] args) throws IOException {
        Class1 a = new Class1();
        a.Calculate();
    }
}