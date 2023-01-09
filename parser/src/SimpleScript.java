import java.util.HashMap;

public class SimpleScript {
    private HashMap<String, Integer> varibles = new HashMap<String, Integer>();
    private static boolean verbose = false;

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-v")) {
            verbose
        }
    }
}
