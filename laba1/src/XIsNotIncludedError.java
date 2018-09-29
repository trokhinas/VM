



public class XIsNotIncludedError extends LagrangeError {
    private int code = 4;

    public int getCode() {
        return code;
    }

    public XIsNotIncludedError(String message) {
        super(message);
    }
    public XIsNotIncludedError() {
        super(Error.IER4.toString());
    }
}
