import java.io.FileNotFoundException;
import java.io.PrintStream;

public class LagrangePrinter {

    private String path;
    private PrintStream printer;

    public LagrangePrinter(String outputPath) {
        path = outputPath;
    }

    public void print(LagrangeError e) throws FileNotFoundException {
        printer = new PrintStream(path);

        printer.println("Код ошибки: " + e.getCode());
        printer.println(e.getMessage());
        System.out.println("Код ошибки: " + e.getCode());
        System.out.println(e.getMessage());
    }
}
