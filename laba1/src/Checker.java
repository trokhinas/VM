

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

        if(m < 2) {
            System.out.println("There is no compare because m = " + m);
            m++;
            lastAccuracy = newAccuracy;
            return true;
        }
        else {
            System.out.println("There is compare because m = " + m);
            boolean result = newAccuracy < lastAccuracy;
            lastAccuracy = newAccuracy;
            return  result;//true - is ok otherwise is bad
        }
    }
    public boolean checkAccuracy(double newAccuracy) {
        return newAccuracy < EPS;
    }



}
