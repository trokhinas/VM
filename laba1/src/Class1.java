import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by admin on 25.09.2018.
 */
public class Class1 {
    private ArrayList<Double> x_In = new ArrayList<>();
    private ArrayList<Double> y_In = new ArrayList<>();
    private double XX_In;
    private double EPS;
    private int N;
    private double lastLagr;
    private double lastEps;


    private int left, right;//левая и правая границы участка вычисления(индексы)

    public void InputFromFile() throws IOException {
        String path = "laba1\\src\\InputData.txt";
        char c = '\n';
        Scanner scan = new Scanner(new File(path)).useDelimiter(";");
        int i = 0;
        while (scan.hasNext()) {
            String s = scan.next();
            System.out.println(s);
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
                XX_In=(Double.parseDouble(sc.next()));
                i++;
            }
            else if (i == 4) {
                Scanner sc = new Scanner(s).useDelimiter(",");
                EPS = (Double.parseDouble(sc.next()));
                i++;
            }
        }
    }

    public void ErrorFromInput() throws FileNotFoundException {

        double keepValue;
        keepValue = x_In.get(0);
        for (int i = 1; i < x_In.size(); i++) {
            if (keepValue > x_In.get(i)) {
               Exit(Error.IER3);
            }
            keepValue = x_In.get(i);
        }
        if (XX_In < x_In.get(0) || XX_In > x_In.get(x_In.size() - 1)) {
           Exit(Error.IER4);
        }
    }
    public boolean CheckAccuracy(double eps,double YY) throws FileNotFoundException {
        if (eps<EPS){
            Exit(Error.IER0);
        }else
        if (eps>lastEps){
            Exit(Error.IER1);
        }
        lastEps=eps;
        return false;
    }
    public void ModuleNotGood(double ln_1,double ln_2) throws FileNotFoundException {
        if (ln_1>ln_2){
            Exit(Error.IER2);
        }
    }



    /**
     * @return значение полинома Лагранжа в заданной степени для точки XX_In
     */
    private double Lagrange() {
        double sum = 0;
        for (int i = left; i <= right; i++)
            sum += y_In.get(i) * l(i);
        return sum;
    }

    /**
     * @param number - номер вычисляемого многочлена для полинома Лагранжа number >= 1 && number < N
     * @return значение многочлена степени N
     */
    private double l(int number) {
        double top = 1, bot = 1, x_k = x_In.get(number);
        for (int i = left; i <= right; i++) {
            if (i != number) {
                double x = x_In.get(i);
                top *= (XX_In - x);
                bot *= (x_k - x);
            }

        }
        return top / bot;
    }

    private void addNearestPoint() {
        if (right - left != N - 1) {
            if(right == N - 1)
                left--;
            else if(left == 0)
                right++;
            else {
                if(XX_In - x_In.get(left - 1) < x_In.get(right + 1) - XX_In)
                    left--;
                else
                    right ++;
            }
            return;
        }
        Exit(Error.IER1);

    }

    private void Exit(Error e) {
        System.out.print(e);
        try {
            PrintWriter brWriter = new PrintWriter("OutData.txt");
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

    }

    public static void main(String[] args) throws IOException {
        Class1 a = new Class1();
        a.InputFromFile();
        a.ErrorFromInput();
    }
}