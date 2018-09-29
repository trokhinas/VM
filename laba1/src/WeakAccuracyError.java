



public class WeakAccuracyError extends LagrangeError {
    private int code = 2;

    public int getCode() {
        return code;
    }

    public WeakAccuracyError(String message) {
        super(message);
    }
    public WeakAccuracyError() {
        super(Error.IER2.toString());
    }
}
