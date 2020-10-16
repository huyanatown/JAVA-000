import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        HelloClassLoader classLoader = new HelloClassLoader();
        Class clazz = classLoader.findClass("Hello");
        Method hello = clazz.getDeclaredMethod("hello");
        hello.setAccessible(true);
        hello.invoke(clazz.newInstance());
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("src/main/resources/Hello.xlass");
        byte[] bytes = null;
        try {
            bytes = file2Bytes(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return defineClass(name, bytes, 0, bytes.length);
    }

    public static byte[] file2Bytes(File file) throws IOException {
        byte[] buffer = null;
        FileInputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        byte[] b = new byte[1];
        byte b255 = (byte) 255;
        int size = 0;
        try {
            while ((size = inputStream.read(b)) != -1) {
                b[0] = (byte)(b255 - b[0]);
                outputStream.write(b, 0, size);
            }
            buffer = outputStream.toByteArray();
        }finally {
            inputStream.close();
            inputStream.close();
        }
        return buffer;
    }

}
