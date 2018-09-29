import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by admin on 25.09.2018.
 */
public class Class1 {
    private ArrayList<Double> x_In = new ArrayList<>();
    private ArrayList<Double> y_In = new ArrayList<>();
    private double XX;
    private double EPS;
    private int N;


    private int left, right;//левая и правая границы участка вычисления(индексы)
    private double lastLagr;
    private double lastEps;

    private void InputFromFile() throws IOException {
        String path = "laba1\\src\\InputData.txt";
        char c = '\n';
        Scanner scan = new Scanner(new File(path)).useDelimiter(";");
        int i = 0;
        while (scan.hasNext()) {
            String s = scan.next();
            //System.out.println(s);
            if (i == 0) {
                Scanner sc = new Scanner(s).useDelimiter(",");
                //System.out.println(s);
                while (sc.hasNext()) {
                    x_In.add(Double.parseDouble(sc.next()));
                }
                i++;
            } else if (i == 1) {
                Scanner sc = new Scanner(s).useDelimiter(",");
                while (sc.hasNext()) {
                    y_In.add(Double.parseDouble(sc.next()));
                }
                i++;
            } else if (i == 2) {
                Scanner sc = new Scanner(s).useDelimiter(",");
                N = (int) (Double.parseDouble(sc.next()));
                i++;
            }
            else if (i == 3) {
                Scanner sc = new Scanner(s).useDelimiter(",");
                XX =(Double.parseDouble(sc.next()));
                i++;
            }
            else if (i == 4) {
                Scanner sc = new Scanner(s).useDelimiter(",");
                EPS = (Double.parseDouble(sc.next()));
                i++;
            }
        }
    }

    private void ErrorFromInput() {

        double keepValue;
        keepValue = x_In.get(0);
        for (int i = 1; i < x_In.size(); i++) {
            if (keepValue > x_In.get(i)) {
               Exit(Error.IER3);
            }
            keepValue = x_In.get(i);
        }
        if (XX < x_In.get(0) || XX > x_In.get(x_In.size() - 1)) {
           Exit(Error.IER4);
        }
    }
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
            sum += y_In.get(i) * l(i);
        System.out.println("L_" + (right - left) + " = " + sum);
        return sum;

    }

    /**
     * @param number - номер вычисляемого многочлена для полинома Лагранжа number >= 1 && number < N
     * @return значение многочлена степени N
     */
    private double l(int number) {
        StringBuilder Top, Bot;
        Top = new StringBuilder();
        Bot = new StringBuilder();
        double top = 1, bot = 1, x_k = x_In.get(number);
        for (int i = left; i <= right; i++) {
            if (i != number) {
                double x_i = x_In.get(i);
                top *= (XX - x_i);
                bot *= (x_k - x_i);
                Top.append("(").append(XX).append(" - ").append(x_i).append(")");
                Bot.append("(").append(x_k).append(" - ").append(x_i).append(")");
            }

        }
        System.out.println(Top + " = " + top);
        System.out.println(Bot + " = " + bot);
        System.out.println();
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
                if (XX - x_In.get(left - 1) < x_In.get(right + 1) - XX)
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
                brWriter.print(lastLagr + "\n");
            }
            brWriter.print(e);
            brWriter.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        System.exit(e.getCode());
    }

    Class1() {
        try {
            InputFromFile();
            ErrorFromInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        left = right = 0;
        for (int i = 0; i < N && right == 0; i++){
            if (x_In.get(i) >= XX)
                right = i;
        }
        left = right - 1;

        for(Double x : x_In)
            System.out.print(x + " ");
        System.out.println();
        for(Double x : y_In)
            System.out.print(x + " ");
        System.out.println();
        System.out.println(XX);
        System.out.println(EPS);
        System.out.println();
    }
    public void Calculate() throws FileNotFoundException {
        double newLagrange = Lagrange();

        do{
            lastLagr = newLagrange;
            addNearestPoint();
            newLagrange = Lagrange();
        }while(CheckAccuracy(Math.abs(newLagrange - lastLagr)));
        System.out.println(newLagrange);
        Exit(Error.IER0);
    }


    public static void main(String[] args) throws IOException {
        Class1 a = new Class1();
        a.Calculate();
    }
}