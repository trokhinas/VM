import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 25.09.2018.
 */
public class Class1 {
    ArrayList<Double> x_In = new ArrayList<>();
    ArrayList<Double> y_In = new ArrayList<>();
    ArrayList<Double> XX_In = new ArrayList<>();
    double EPS;
    int N;

    Class1()
    {
        for(Error i : Error.values())
            System.out.println(i);
    }

    public static void main(String[] args) {
        Class1 a = new Class1();
    }
}
