import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by admin on 25.09.2018.
 */
public class LagrangePolynomialCalculator {

    private ArrayList<Double> vectorX = new ArrayList<>();
    private ArrayList<Double> vectorY = new ArrayList<>();
    private double XX;
    private double EPS;
    private int N;

    /**
     * Левая и правая границы участка вычисления(индексы)
     */
    private int left, right;
    private double lastLagr;
    private double lastEps;

    public LagrangePolynomialCalculator() {
        this(Constants.INPUT_FILE_PATH);
    }

    public LagrangePolynomialCalculator(String path) {
        try {
            inputFromFile(path);
            validateInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        left = right = 0;
        for (int i = 0; i < N && right == 0; i++) {
            if (vectorX.get(i) >= XX)
                right = i;
        }
        left = right - 1;

        printResults();
    }

    public void calculate() {
        double newLagrange = calculateLagrange();
        do {
            lastLagr = newLagrange;
            addNearestPoint();
            newLagrange = calculateLagrange();
        } while (checkAccuracy(Math.abs(newLagrange - lastLagr)));
        System.out.println(newLagrange);
        exit(Error.IER0);
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
            vectorX.add(Double.parseDouble(scanner.next()));
        }
    }

    private void readVectorY(Scanner scanner) {
        while (scanner.hasNext()) {
            vectorY.add(Double.parseDouble(scanner.next()));
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

    private void validateInput() {
        double keepValue;
        keepValue = vectorX.get(0);
        for (int i = 1; i < vectorX.size(); i++) {
            if (keepValue > vectorX.get(i)) {
                exit(Error.IER3);
            }
            keepValue = vectorX.get(i);
        }
        if (XX < vectorX.get(0) || XX > vectorX.get(vectorX.size() - 1)) {
            exit(Error.IER4);
        }
    }

    /**
     * @return значение полинома Лагранжа в заданной степени для точки XX
     */
    private double calculateLagrange() {
        double sum = 0;
        for (int i = left; i <= right; i++)
            sum += vectorY.get(i) * l(i);
        System.out.println("L_" + (right - left) + " = " + sum);
        return sum;
    }

    /**
     * @param number - номер вычисляемого многочлена для полинома Лагранжа number >= 1 && number < N
     * @return значение многочлена степени N
     */
    private double l(int number) {
        StringBuilder topLine = new StringBuilder();
        StringBuilder bottomLine = new StringBuilder();
        double top = 1, bot = 1, x_k = vectorX.get(number);
        for (int i = left; i <= right; i++) {
            if (i != number) {
                double x_i = vectorX.get(i);
                top *= (XX - x_i);
                bot *= (x_k - x_i);
                topLine.append("(").append(XX).append(" - ").append(x_i).append(")");
                bottomLine.append("(").append(x_k).append(" - ").append(x_i).append(")");
            }

        }
        System.out.println(topLine + " = " + top);
        System.out.println(bottomLine + " = " + bot);
        System.out.println();
        return top / bot;
    }

    /*
     * эта функция нуждается в доработке(некрасивая)
     * */
    private void addNearestPoint() {
        if (right - left != N - 1) {
            if (shouldAddLeft()) {
                addLeft();
            } else if (shouldAddRight()) {
                addRight();
            }
        } else
            exit(Error.IER1);
    }

    private boolean shouldAddLeft() {
        return right == N - 1 || XX - vectorX.get(left - 1) < vectorX.get(right + 1) - XX;
    }

    private boolean shouldAddRight() {
        return left == 0 || XX - vectorX.get(left - 1) >= vectorX.get(right + 1) - XX;
    }

    private void addLeft() {
        left--;
    }

    private void addRight() {
        right++;
    }

    private boolean checkAccuracy(double eps) {
        if (eps < lastEps) {
            lastEps = eps;
            return true;
        } else if (eps > lastEps) {
            exit(Error.IER2);
        }
        return false;
    }

    private void printResults() {
        printDoubleVector(vectorX, Constants.SPACE);
        printEmptyLine();
        printDoubleVector(vectorX, Constants.SPACE);
        printEmptyLine();
        System.out.println(XX);
        System.out.println(EPS);
        printEmptyLine();
    }

    private void printEmptyLine() {
        System.out.println();
    }

    private void printDoubleVector(ArrayList<Double> vector, String delimiter) {
        System.out.println(
                vector.stream()
                        .map(Object::toString)
                        .reduce((left, right) -> left + delimiter + right)
                        .orElse(Constants.EMPTY_STRING)
        );
    }

    private void exit(Error e) {
        System.out.print(e);
        try {
            PrintWriter brWriter = new PrintWriter(Constants.OUTPUT_FILE_PATH);
            if (e == Error.IER0) {
                brWriter.print(lastLagr + Constants.NEXT_LINE);
            }
            brWriter.print(e);
            brWriter.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        System.exit(e.getCode());
    }
}