import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;



public class Data {

    private ArrayList<Double> x_In = new ArrayList<>();
    private ArrayList<Double> y_In = new ArrayList<>();
    private double XX;
    private double EPS;
    private int N;

    private void InputFromFile()
    {
        String path = "laba1\\src\\InputData.txt";
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
    private void ErrorFromInput() throws IER3, IER4 {

        double keepValue;
        keepValue = x_In.get(0);
        for (int i = 1; i < x_In.size(); i++) {
            if (keepValue > x_In.get(i)) { throw new IER3(Error.IER3.toString()); }
            keepValue = x_In.get(i);
        }
        if (XX < x_In.get(0) || XX > x_In.get(N - 1)) { throw new IER4(Error.IER4.toString()); }
    }

    Data()  {
        InputFromFile();
        try {
            ErrorFromInput();
        } catch (LagrangeError e) {
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
        Data a = new Data();

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
