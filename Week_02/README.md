<h3>第 3 课 作业</h3>

- 使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例。
  - 串行 GC 

    - ```java
      tanghui@tanghuis-MacBook-Pro work-demo % java -XX:+UseSerialGC -Xms2g -Xmx2g -XX:+PrintGCDetails GCLogAnalysis                       
      正在执行...
      [GC (Allocation Failure) [DefNew: 559232K->69888K(629120K), 0.0888818 secs] 559232K->163938K(2027264K), 0.0889102 secs] [Times: user=0.05 sys=0.03, real=0.09 secs] 
      [GC (Allocation Failure) [DefNew: 628868K->69887K(629120K), 0.1040953 secs] 722919K->290230K(2027264K), 0.1041185 secs] [Times: user=0.06 sys=0.04, real=0.11 secs] 
      [GC (Allocation Failure) [DefNew: 629119K->69888K(629120K), 0.0842754 secs] 849462K->418648K(2027264K), 0.0843023 secs] [Times: user=0.05 sys=0.03, real=0.08 secs] 
      [GC (Allocation Failure) [DefNew: 629120K->69887K(629120K), 0.0788783 secs] 977880K->541692K(2027264K), 0.0789055 secs] [Times: user=0.05 sys=0.03, real=0.08 secs] 
      [GC (Allocation Failure) [DefNew: 629119K->69887K(629120K), 0.0804173 secs] 1100924K->673737K(2027264K), 0.0804432 secs] [Times: user=0.05 sys=0.03, real=0.08 secs] 
      执行结束!共生成对象次数:11493
      ```

  - 并行 GC

    - ```java
      tanghui@tanghuis-MacBook-Pro work-demo % java -XX:+UseParallelGC -Xms2g -Xmx2g -XX:+PrintGCDetails GCLogAnalysis
      正在执行...
      [GC (Allocation Failure) [PSYoungGen: 524800K->87039K(611840K)] 524800K->141178K(2010112K), 0.0546816 secs] [Times: user=0.06 sys=0.36, real=0.05 secs] 
      [GC (Allocation Failure) [PSYoungGen: 611839K->87030K(611840K)] 665978K->256163K(2010112K), 0.0785121 secs] [Times: user=0.08 sys=0.52, real=0.07 secs] 
      [GC (Allocation Failure) [PSYoungGen: 611830K->87039K(611840K)] 780963K->368273K(2010112K), 0.0580490 secs] [Times: user=0.14 sys=0.32, real=0.06 secs] 
      [GC (Allocation Failure) [PSYoungGen: 611839K->87036K(611840K)] 893073K->484189K(2010112K), 0.0597369 secs] [Times: user=0.14 sys=0.33, real=0.06 secs] 
      [GC (Allocation Failure) [PSYoungGen: 611836K->87024K(611840K)] 1008989K->592684K(2010112K), 0.0594475 secs] [Times: user=0.14 sys=0.32, real=0.06 secs] 
      [GC (Allocation Failure) [PSYoungGen: 611824K->87033K(329216K)] 1117484K->699409K(1727488K), 0.0572351 secs] [Times: user=0.14 sys=0.30, real=0.05 secs] 
      [GC (Allocation Failure) [PSYoungGen: 328933K->140689K(382976K)] 941310K->758564K(1781248K), 0.0175465 secs] [Times: user=0.15 sys=0.01, real=0.02 secs] 
      [GC (Allocation Failure) [PSYoungGen: 382865K->180549K(471040K)] 1000740K->812115K(1869312K), 0.0244968 secs] [Times: user=0.17 sys=0.04, real=0.02 secs] 
      执行结束!共生成对象次数:13820
      ```

  - CMS GC

    - ```java
      tanghui@tanghuis-MacBook-Pro work-demo % java -XX:+UseConcMarkSweepGC -Xms2g -Xmx2g -XX:+PrintGCDetails GCLogAnalysis
      正在执行...
      [GC (Allocation Failure) [ParNew: 559232K->69887K(629120K), 0.0621523 secs] 559232K->159222K(2027264K), 0.0621983 secs] [Times: user=0.10 sys=0.38, real=0.06 secs] 
      [GC (Allocation Failure) [ParNew: 629119K->69886K(629120K), 0.0565144 secs] 718454K->271050K(2027264K), 0.0565419 secs] [Times: user=0.12 sys=0.34, real=0.06 secs] 
      [GC (Allocation Failure) [ParNew: 629118K->69888K(629120K), 0.0595857 secs] 830282K->388636K(2027264K), 0.0596135 secs] [Times: user=0.56 sys=0.03, real=0.06 secs] 
      [GC (Allocation Failure) [ParNew: 629120K->69886K(629120K), 0.0644978 secs] 947868K->508540K(2027264K), 0.0645250 secs] [Times: user=0.57 sys=0.04, real=0.06 secs] 
      [GC (Allocation Failure) [ParNew: 629118K->69888K(629120K), 0.0700048 secs] 1067772K->634805K(2027264K), 0.0700330 secs] [Times: user=0.65 sys=0.04, real=0.07 secs] 
      [GC (Allocation Failure) [ParNew: 629120K->69887K(629120K), 0.0729237 secs] 1194037K->767138K(2027264K), 0.0729496 secs] [Times: user=0.68 sys=0.04, real=0.07 secs] 
      执行结束!共生成对象次数:14238
      ```

  - G1 GC

    - ```java
      tanghui@tanghuis-MacBook-Pro work-demo % java -XX:+UseG1GC -Xms2g -Xmx2g -XX:+PrintGC GCLogAnalysis 
      正在执行...
      [GC pause (G1 Evacuation Pause) (young) 130M->47M(2048M), 0.0123123 secs]
      [GC pause (G1 Evacuation Pause) (young) 152M->81M(2048M), 0.0138063 secs]
      [GC pause (G1 Evacuation Pause) (young) 191M->118M(2048M), 0.0111209 secs]
      [GC pause (G1 Evacuation Pause) (young) 235M->153M(2048M), 0.0104943 secs]
      [GC pause (G1 Evacuation Pause) (young) 264M->193M(2048M), 0.0134963 secs]
      [GC pause (G1 Evacuation Pause) (young) 344M->236M(2048M), 0.0177097 secs]
      [GC pause (G1 Evacuation Pause) (young) 442M->295M(2048M), 0.0194277 secs]
      [GC pause (G1 Evacuation Pause) (young) 493M->348M(2048M), 0.0185854 secs]
      [GC pause (G1 Evacuation Pause) (young) 584M->400M(2048M), 0.0239248 secs]
      [GC pause (G1 Evacuation Pause) (young)-- 1833M->1211M(2048M), 0.0089972 secs]
      [GC pause (G1 Humongous Allocation) (young) (initial-mark) 1212M->1212M(2048M), 0.0022867 secs]
      [GC concurrent-root-region-scan-start]
      [GC concurrent-root-region-scan-end, 0.0001134 secs]
      [GC concurrent-mark-start]
      [GC concurrent-mark-end, 0.0080715 secs]
      [GC remark, 0.0014850 secs]
      [GC cleanup 1257M->1160M(2048M), 0.0013001 secs]
      [GC concurrent-cleanup-start]
      [GC concurrent-cleanup-end, 0.0000847 secs]
      执行结束!共生成对象次数:10621
        
        
      
      tanghui@tanghuis-MacBook-Pro work-demo % java -XX:+UseG1GC -Xms4g -Xmx4g -XX:+PrintGC GCLogAnalysis
      正在执行...
      [GC pause (G1 Evacuation Pause) (young) 204M->78M(4096M), 0.0242478 secs]
      [GC pause (G1 Evacuation Pause) (young) 256M->137M(4096M), 0.0213696 secs]
      [GC pause (G1 Evacuation Pause) (young) 315M->198M(4096M), 0.0232853 secs]
      [GC pause (G1 Evacuation Pause) (young) 376M->255M(4096M), 0.0217514 secs]
      [GC pause (G1 Evacuation Pause) (young) 433M->310M(4096M), 0.0218322 secs]
      [GC pause (G1 Evacuation Pause) (young) 488M->354M(4096M), 0.0212160 secs]
      [GC pause (G1 Evacuation Pause) (young) 532M->405M(4096M), 0.0212952 secs]
      [GC pause (G1 Evacuation Pause) (young) 611M->472M(4096M), 0.0283065 secs]
      [GC pause (G1 Evacuation Pause) (young) 704M->545M(4096M), 0.0318621 secs]
      执行结束!共生成对象次数:10825
      ```

      

- 使用压测工具(wrk或sb)，演练gateway-server-0.0.1-SNAPSHOT.jar 示例。
  - 并行 GC

    - ```java
      java -jar -XX:+UseParallelGC -Xms512m -Xmx512m  gateway-server-0.0.1-SNAPSHOT.jar
        -------------------------------------------------------------------------
      tanghui@tanghuis-MacBook-Pro work-demo % wrk -t8 -c40 -d60s http://localhost:8088/api/hello
      Running 1m test @ http://localhost:8088/api/hello
        8 threads and 40 connections
        Thread Stats   Avg      Stdev     Max   +/- Stdev
          Latency    47.66ms  164.31ms   1.75s    92.07%
          Req/Sec     8.19k     2.59k   19.68k    85.71%
        2704230 requests in 1.00m, 322.86MB read
      Requests/sec:  45004.70
      Transfer/sec:      5.37MB
        
        
        
      java -jar -XX:+UseParallelGC -Xms2g -Xmx2g  gateway-server-0.0.1-SNAPSHOT.jar
        -------------------------------------------------------------------------
      tanghui@tanghuis-MacBook-Pro work-demo % wrk -t8 -c40 -d60s http://localhost:8088/api/hello
      Running 1m test @ http://localhost:8088/api/hello
        8 threads and 40 connections
        Thread Stats   Avg      Stdev     Max   +/- Stdev
          Latency    48.90ms  181.01ms   1.42s    92.96%
          Req/Sec     8.36k     2.56k   18.97k    86.45%
        2655021 requests in 1.00m, 316.98MB read
        Socket errors: connect 0, read 0, write 0, timeout 6
      Requests/sec:  44180.10
      Transfer/sec:      5.27MB
        
      ```

  - CMS GC

    - ```java
      java -jar -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m  gateway-server-0.0.1-SNAPSHOT.jar
        -------------------------------------------------------------------------
      tanghui@tanghuis-MacBook-Pro work-demo % wrk -t8 -c40 -d60s http://localhost:8088/api/hello
      Running 1m test @ http://localhost:8088/api/hello
        8 threads and 40 connections
        Thread Stats   Avg      Stdev     Max   +/- Stdev
          Latency    46.05ms  178.09ms   1.43s    93.55%
          Req/Sec     7.87k     2.46k   14.91k    84.50%
        2711092 requests in 1.00m, 323.68MB read
      Requests/sec:  45112.92
      Transfer/sec:      5.39MB
        
        
        
      java -jar -XX:+UseConcMarkSweepGC -Xms2g -Xmx2g  gateway-server-0.0.1-SNAPSHOT.jar
        -------------------------------------------------------------------------
      tanghui@tanghuis-MacBook-Pro work-demo % wrk -t8 -c40 -d60s http://localhost:8088/api/hello
      Running 1m test @ http://localhost:8088/api/hello
        8 threads and 40 connections
        Thread Stats   Avg      Stdev     Max   +/- Stdev
          Latency    73.55ms  223.97ms   1.45s    90.72%
          Req/Sec     7.95k     2.50k   14.01k    83.75%
        2661353 requests in 1.00m, 317.74MB read
      Requests/sec:  44285.98
      Transfer/sec:      5.29MB
      ```

      

  - G1 GC

    - ```java
      java -jar -XX:+UseG1GC -Xms4g -Xmx4g  gateway-server-0.0.1-SNAPSHOT.jar
        -------------------------------------------------------------------------
      tanghui@tanghuis-MacBook-Pro work-demo % wrk -t8 -c40 -d60s http://localhost:8088/api/hello
      Running 1m test @ http://localhost:8088/api/hello
        8 threads and 40 connections
        Thread Stats   Avg      Stdev     Max   +/- Stdev
          Latency    74.22ms  237.60ms   1.74s    91.41%
          Req/Sec     7.80k     2.82k   17.62k    80.07%
        2609680 requests in 1.00m, 311.57MB read
      Requests/sec:  43437.88
      Transfer/sec:      5.19MB
      ```



GC总结：

- 串行 GC 
  - 年轻代使用`拷贝-复制`，老年代使用`标记-清除-整理`
  - 发生GC时，会暂停
  - 内存越小，GC次数越多
- 并行 GC
  - 年轻代使用`拷贝-复制`，老年代使用`标记-清除-整理`
  - GC处理时，暂停业务处理，所有线程处理GC垃圾回收。平常运行时，所以线程都去处理业务。因此，吞吐量比较高。
  - 内存越小，GC次数越多
- CMS GC
  - 年轻代使用`拷贝-复制`，老年代使用`标记-清除`
  - CMS默认GC线程数是1/4，并且老年代只清除，无整理。所以当GC发生时，吞吐量不如`并行 GC`
  - CMS GC 6个阶段
    - 初始化标记 - 暂停GC
    - 并行标记
    - 并行预清理
    - 最终标记 - 暂停GC
    - 并行清理
    - 并行重置
  - 因为无`整理`，并且`CMS GC` `6阶段` 暂停时间短，所以延迟比较低
  - 内存越小，GC次数越多
- G1 GC
  - 不分代,使用 region(2048) 存储数据，分为：
    - Eden区 （`标记-复制` 算法）
    - 存活区
    - 老年区 (`标记-复制-整理` 算法)
  - GC 3个阶段
    - (G1 Evacuation Pause) (young)
    - 类似 `CMS GC` 的 并发标记
      - initial-mark
      - concurrent-root-region-scan
      - concurrent-mark
      - remark
      - concurrent-cleanup
    - (G1 Evacuation Pause) (mix)
  - 使用 `-XX:MaxGCPauseMills` 参数可以控制 GC暂停时间

`ParNewGC + CMS`  适用于 `低延迟`

`ParallelGC + ParllelGC Old`  适用于 `高吞吐量`

`G1`  适用于 `内存大于4G，并且GC时间可控制`



















<h3>第 4 课 作业</h3>

- 写一段代码，使用HttpClient或OkHttp访问 http://localhost:8801，代码提交到 github。

```java
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class HttpClientDemo {
    public static void main(String[] args) {
        doGet();
    }
  
    public static void doGet() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://localhost:8808/test");

        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            System.out.println("响应状态码：" + response.getStatusLine());
            String result = IOUtils.toString(entity.getContent());
            System.out.println("返回内容：" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```



> 返回结果

```java
Connected to the target VM, address: '127.0.0.1:63210', transport: 'socket'
log4j:WARN No appenders could be found for logger (org.apache.http.client.protocol.RequestAddCookies).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
响应状态码：HTTP/1.1 200 OK
返回内容：hello,kimmking
Disconnected from the target VM, address: '127.0.0.1:63210', transport: 'socket'

Process finished with exit code 0
```

