//package Hello;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class HelloClassLoader extends ClassLoader {
    private String byteCodepath;

    public HelloClassLoader(String byteCodepath) {
        this.byteCodepath = byteCodepath;
    }

    public HelloClassLoader(ClassLoader parent, String byteCodepath) {
        super(parent);
        this.byteCodepath = byteCodepath;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        HelloClassLoader helloclassloader = new HelloClassLoader("./java/");
        try {
            Class<?> hello = helloclassloader.loadClass("Hello");
            Object o = hello.newInstance();

            hello.getMethod("hello").invoke(o);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected Class<?> findClass(String name)  {
        String fileName = byteCodepath + name + ".xlass";
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(fileName));
            baos = new ByteArrayOutputStream();
            int len;
            byte[] data = new byte[1024];
            while ((len = bis.read(data)) != -1) {
                baos.write(data,0,len);
            }
            byte[] bytes = baos.toByteArray();
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
            Class clazz = defineClass(null,bytes,0,bytes.length);
            return clazz;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (baos != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }
}