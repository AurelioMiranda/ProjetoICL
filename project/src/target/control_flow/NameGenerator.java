package target.control_flow;

public class NameGenerator {
    private static long count = 0L;

    public static String genLabel() {
        return "L" + count++;
    }
}
