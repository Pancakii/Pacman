package misc;

public class Debug {
    /**
     * Prints the given message with ">> DEBUG >> " prefix.
     * @param message the said message
     */
    public static void out(final String message) {
        // comment this out if you do not want to see the debug messages
        System.out.println(">> DEBUG >> " + message);
    }
}
