public abstract class LagrangeError extends Exception{
    public LagrangeError(String message) {
        super(message);
    }
    public LagrangeError() {
        super();
    }
    abstract int getCode();
}
