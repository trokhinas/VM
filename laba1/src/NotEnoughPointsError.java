



public class NotEnoughPointsError extends LagrangeError {
    private int code = 1;

    public int getCode() {
        return code;
    }

    public NotEnoughPointsError(String message) {
        super(message);
    }
    public NotEnoughPointsError() {
        super(Error.IER1.toString());
    }




}
