import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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

    /**
     * Левая и правая границы участка вычисления(индексы)
     */
    private int left, right;
    private double lastLagr;
    private double lastEps;

    Class1() {
        try {
            inputFromFile(Constants.INPUT_FILE_PATH);
            ErrorFromInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        left = right = 0;
        for (int i = 0; i < N && right == 0; i++) {
            if (x_In.get(i) >= XX)
                right = i;
        }
        left = right - 1;

        for (Double x : x_In)
            System.out.print(x + " ");
        System.out.println();
        for (Double x : y_In)
            System.out.print(x + " ");
        System.out.println();
        System.out.println(XX);
        System.out.println(EPS);
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        Class1 a = new Class1();
        a.Calculate();
    }

    private void inputFromFile(String path) throws IOException {
        Scanner scan = new Scanner(new File(path)).useDelimiter(Constants.DELIMITER_SEMICOLON);
        int i = 0;
        while (scan.hasNext()) {
            String s = scan.next();
            Scanner sc = new Scanner(s).useDelimiter(Constants.DELIMITER_COMMA);
            switch (i) {
                case 0: {
                    readVectorX(sc);
                    break;
                }
                case 1: {
                    readVectorY(sc);
                    break;
                }
                case 2: {
                    readN(sc);
                    break;
                }
                case 3: {
                    readXX(sc);
                    break;
                }
                case 4: {
                    readEps(sc);
                    break;
                }
            }
            i++;
        }
    }

    private void readVectorX(Scanner scanner) {
        while (scanner.hasNext()) {
            x_In.add(Double.parseDouble(scanner.next()));
        }
    }

    private void readVectorY(Scanner scanner) {
        while (scanner.hasNext()) {
            y_In.add(Double.parseDouble(scanner.next()));
        }
    }

    private void readN(Scanner scanner) {
        N = (int) (Double.parseDouble(scanner.next()));
    }

    private void readXX(Scanner scanner) {
        XX = (Double.parseDouble(scanner.next()));
    }

    private void readEps(Scanner scanner) {
        EPS = (Double.parseDouble(scanner.next()));
    }

    private void ErrorFromInput() {
        double keepValue;
        keepValue = x_In.get(0);
        for (int i = 1; i < x_In.size(); i++) {
            if (keepValue > x_In.get(i)) {
                exit(Error.IER3);
            }
            keepValue = x_In.get(i);
        }
        if (XX < x_In.get(0) || XX > x_In.get(x_In.size() - 1)) {
            exit(Error.IER4);
        }
    }

    private boolean CheckAccuracy(double eps) {
        if (eps < lastEps) {
            lastEps = eps;
            return true;
        } else if (eps > lastEps) {
            exit(Error.IER2);
        }
        return false;
    }

    /**
     * @return значение полинома Лагранжа в заданной степени для точки XX
     */
    private double calculateLagrange() {
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
            if (right == N - 1)
                addLeft();
            else if (left == 0)
                addRight();
            else {
                if (XX - x_In.get(left - 1) < x_In.get(right + 1) - XX)
                    addLeft();
                else
                    addRight();
            }
        } else exit(Error.IER1);

    }

    private void addLeft() {
        left--;
    }

    private void addRight() {
        right++;
    }

    private void exit(Error e) {
        System.out.print(e);
        try {
            PrintWriter brWriter = new PrintWriter("OutData.txt");
            if (e == Error.IER0) {
                brWriter.print(lastLagr + "\n");
            }
            brWriter.print(e);
            brWriter.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        System.exit(e.getCode());
    }

    public void Calculate() throws FileNotFoundException {
        double newLagrange = calculateLagrange();

        do {
            lastLagr = newLagrange;
            addNearestPoint();
            newLagrange = calculateLagrange();
        } while (CheckAccuracy(Math.abs(newLagrange - lastLagr)));
        System.out.println(newLagrange);
        exit(Error.IER0);
    }
}