

public class Checker {

    private double EPS;
    private double lastAccuracy;
    private int m;


    public Checker(double lagrangeEPS) {
        if(lagrangeEPS < 0) throw new IllegalArgumentException();
        EPS = lagrangeEPS;
        lastAccuracy = -1;
        m = 0;
    }

    public boolean checkWeakAccuracy(double newAccuracy) {

        boolean result = true;
        System.out.println("E_" + m + " = " + newAccuracy);
        if(m < 2) {
            System.out.println("There is no compare because m = " + m);
        }
        else {
            System.out.println("There is compare because m = " + m);
            System.out.println("E_" + (m - 1) + " = " + lastAccuracy);
            result = newAccuracy < lastAccuracy;
        }
        lastAccuracy = newAccuracy;
        m++;
        return result;
    }
    public boolean checkAccuracy(double newAccuracy) {
        return newAccuracy < EPS;
    }



}
