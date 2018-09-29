



public class WrongVectorX extends LagrangeError {
    private int code = 3;

    public int getCode() {
        return code;
    }

    public WrongVectorX(String message) {
        super(message);
    }
    public WrongVectorX() {
        super(Error.IER3.toString());
    }
}
