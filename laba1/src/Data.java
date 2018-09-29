import java.io.File;
import java.io.FileNotFoundException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;



public class Data {

    private ArrayList<Double> x_In = new ArrayList<>();
    private ArrayList<Double> y_In = new ArrayList<>();
    private double XX;
    private double EPS;
    private int N;

    private void InputFromFile(String path)
    {
        Scanner scan;
        try {
            scan = new Scanner(new File(path)).useDelimiter("\n");
            int i = 0;
            while (scan.hasNext()) {
                String s = scan.next();
                if (i == 0) {
                    Scanner sc = new Scanner(s).useDelimiter(" ");
                    while (sc.hasNext()) {
                        x_In.add(Double.parseDouble(sc.next()));
                    }
                    i++;
                } else if (i == 1) {
                    Scanner sc = new Scanner(s).useDelimiter(" ");
                    while (sc.hasNext()) {
                        y_In.add(Double.parseDouble(sc.next()));
                    }
                    i++;
                } else if (i == 2) {
                    Scanner sc = new Scanner(s).useDelimiter(" ");
                    N = (int) (Double.parseDouble(sc.next()));
                    i++;
                }
                else if (i == 3) {
                    Scanner sc = new Scanner(s).useDelimiter(" ");
                    XX =(Double.parseDouble(sc.next()));
                    i++;
                }
                else if (i == 4) {
                    Scanner sc = new Scanner(s).useDelimiter(" ");
                    EPS = (Double.parseDouble(sc.next()));
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

    }
    private void ErrorFromInput() throws XIsNotIncludedError, WrongVectorX {
        if(!Validator.isOrdered(x_In)) throw new WrongVectorX();
        if (!Validator.isIncluded(x_In, XX))  throw new XIsNotIncludedError();
    }

    public Data(String inputFile, String outputFile)  {
        InputFromFile(inputFile);
        try {
            ErrorFromInput();
        }
        catch (LagrangeError e) {
            try {
                PrintStream ps = new PrintStream(outputFile);
                e.printStackTrace(ps);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            System.exit(e.getCode());
        }
    }

    public ArrayList<Double> getXVector() {
        return x_In;
    }
    public ArrayList<Double> getYVector() {
        return y_In;
    }
    public double getXX() {
        return XX;
    }
    public int getN() {
        return N;
    }
    public double getEPS() {
        return EPS;
    }

    public static void main(String[] args) {
        String inp = "laba1\\src\\InputData.txt";
        String out = "OutData.txt";
        Data a = new Data(inp, out);

        for(double x : a.x_In)
            System.out.print(x + " ");
        System.out.println();
        for(double x : a.y_In)
            System.out.print(x + " ");
        System.out.println();
        System.out.println(a.N);
        System.out.println(a.XX);
        System.out.println(a.EPS);
        //System.out.print(a.x_In.size());
    }
}
