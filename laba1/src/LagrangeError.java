public abstract class LagrangeError extends Exception{
    public LagrangeError(String message) {
        super(message);
    }
    abstract int getCode();
}
