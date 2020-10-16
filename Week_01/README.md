<H3 align="center">Chap01 - JVM基础<H3>

- <h5>自己写一个简单的Hello.java，里面需要涉及基本类型，四则运行，if和for，然后自 己分析一下对应的字节码，有问题群里讨论。</h5>

  源代码：

  ```java
  public class Hello {
      private int count = 0;
      private double sum = 0.0D;
  
      public static void main(String[] args) {
          Hello hello = new Hello();
          hello.init();
          double result = hello.get();
          System.out.println(result);
      }
  
      public void init() {
          int times = 10;
          for (int i = 0; i < times; i++) {
              if (i > 5) {
                  break;
              }
              this.count++;
              sum += i;
          }
      }
  
      public double get() {
          return this.sum / this.count;
      }
  }
  ```

  

  字节码：

  ```java
  public class com.geekbang.chap01.Hello {
    public com.geekbang.chap01.Hello();
      Code:
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: aload_0
         5: iconst_0
         6: putfield      #2                  // Field count:I
         9: aload_0
        10: dconst_0
        11: putfield      #3                  // Field sum:D
        14: return
  
    public static void main(java.lang.String[]);
      Code:
         0: new           #4                  // class com/geekbang/chap01/Hello
         3: dup
         4: invokespecial #5                  // Method "<init>":()V
         7: astore_1
         8: aload_1
         9: invokevirtual #6                  // Method init:()V
        12: aload_1
        13: invokevirtual #7                  // Method get:()D
        16: dstore_2
        17: getstatic     #8                  // Field java/lang/System.out:Ljava/io/PrintStream;
        20: dload_2
        21: invokevirtual #9                  // Method java/io/PrintStream.println:(D)V
        24: return
  
    public void init();
      Code:
   // 将 int类型的字段 times=10 压入栈中
         0: bipush        10
   // 将 times=10 存储到局部变量区 slot=1 的位置
         2: istore_1
   // 将 int类型的字段 i=0 押入栈中
         3: iconst_0
   // 将 i=0 存储到局部变量区 slot=2 的位置
         4: istore_2
   // 从 局部变量区 加载 slot=2 位置的值 i=0，并压入栈中
         5: iload_2
   // 从 局部变量区 加载 slot=1 位置的值 10，并压入栈中
         6: iload_1
   // 比较 0 和 10，当前值大于等于后值时跳转到 第45行指令
         7: if_icmpge     45
   // 从 局部变量区 加载 0，并压入栈中
        10: iload_2
   // 将 int类型的常量 5， 压入栈中
        11: iconst_5
   // 比较 0和5，当前值小于等于后值时，执行 第18行指令；否则执行 goto 45 指令
        12: if_icmple     18
   // 跳转到 45 指令
        15: goto          45
   // 从 局部变量区，加载 slot=0 位置的 Hello 对象
        18: aload_0
        19: dup
   // 加载 Hello 对象的成员变量 int 类型的字段 count       
        20: getfield      #2                  // Field count:I
   // 将 int 类型的常量 1 压入栈中
        23: iconst_1
   // 将 count的值 和 常量1 相加
        24: iadd
   // 将 相加后的值，刷新到 成员变量 count
        25: putfield      #2                  // Field count:I
   // 从 局部变量区，加载 slot=0 位置的 Hello 对象
        28: aload_0
        29: dup
   // 加载 Hello 对象的成员变量 double 类型的字段 sum  
        30: getfield      #3                  // Field sum:D
   // 从 局部变量区 加载 slot=2 位置的值 i=0，并压入栈中
        33: iload_2
   // 将 i=0 从 int 转成 double
        34: i2d
   // 将 sum的值 和 转换后的i值 相加
        35: dadd
   // 将 相加后的值，刷新到 成员变量 sum
        36: putfield      #3                  // Field sum:D
   // i(slot=2) 自增1
        39: iinc          2, 1
   // 跳转到 5 指令 
        42: goto          5
        45: return
  
    public double get();
      Code:
         0: aload_0
         1: getfield      #3                  // Field sum:D
         4: aload_0
         5: getfield      #2                  // Field count:I
         8: i2d
         9: ddiv
        10: dreturn
  }
  ```



​		局部变量表:

       ```java
LocalVariableTable:
        Start  Length  Slot  Name   Signature
            5      40     2     i   I
            0      46     0  this   Lcom/geekbang/chap01/Hello;
            3      43     1 times   I
       ```





- <h5>自定义一个Classloader，加载一个Hello.xlass文件，执行hello方法，此文件内容是 一个Hello.class文件所有字节(x=255-x)处理后的文件。</h5>

  ```java
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
  ```

  

- <h5>画一张图，展示**Xmx**、**Xms**、**Xmn**、**Meta**、**DirectMemory**、**Xss**这些内存参数的 关系。</h5>

  - ![图片](/Users/tanghui/geekbang/kimmking/JAVA-000/Week_01/JVM内存模型以及参数.png)