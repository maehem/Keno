/*
 * This class load a JAR from a URL (or file-URL) and puts those resources
 * into the live classpath (systemClassLoader).
 *
 * This is a great tool for plugable features.
 *
 * Lifted from:
 * @url http://jimlife.wordpress.com/2007/12/19/java-adding-new-classpath-at-runtime/
 *
 */
package keno;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author mark
 */
public class ThemeLoader extends URLClassLoader {

    public ThemeLoader(URL url) throws MalformedURLException, IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super(new URL[]{url});
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class urlClass = URLClassLoader.class;
        Method method = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});
        method.setAccessible(true);
        method.invoke(urlClassLoader, new Object[]{url});
    }
}
