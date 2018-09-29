

public class Checker {

    private double EPS;
    private double lastAccuracy;


    public Checker(double lagrangeEPS) {
        if(lagrangeEPS < 0) throw new IllegalArgumentException();
        EPS = lagrangeEPS;
        lastAccuracy = -1;
    }

    public boolean checkWeakAccuracy(double newAccuracy) {
        boolean result = lastAccuracy == -1 || newAccuracy < lastAccuracy;
        lastAccuracy = newAccuracy;
        return result;//true - is ok otherwise is bad
    }
    public boolean checkAccuracy(double newAccuracy) {
        return newAccuracy < EPS;
    }



}
