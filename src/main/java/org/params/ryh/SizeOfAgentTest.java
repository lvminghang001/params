package org.params.ryh;


import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static org.params.ryh.SizeOfAgent.fullSizeOf;
import static org.params.ryh.SizeOfAgent.sizeOf;

/**
 * 以下结果在 64-bit JVM 下测试
 * 启动参数1（不压缩指针长度）：-javaagent:target/SizeOfAgent.jar -XX:-UseCompressedOops
 *
 * Created by zhuyb on 16/3/20.
 */
public class SizeOfAgentTest {

    public static void main(String[] args) {
        System.out.println("------------------空对象----------------------------");
        // 16 bytes + 0 + 0 = 16  空对象， 只有对象头
        JSONObject date = new com.alibaba.fastjson.JSONObject();
        date.put("check", false);
        date.put("message", "用户存在");
        Map<String,Object> date2=new HashMap<>();
        date2.put("check", false);
        date2.put("message", "用户存在");
        System.out.println(sizeOf(date));
        System.out.println(fullSizeOf(date));

        System.out.println(sizeOf(date2));
        System.out.println(fullSizeOf(date2));

    }

    public static class A {
        int a;
        Integer b;
    }

    public static class B {
        int a;
        Integer b;
    }

    public static class C{
        int c;
        B[] b = new B[2];

        // 初始化
        C() {
            for (int i = 0; i < b.length; i++) {
                b[i] = new B();
            }
        }
    }

    public static class D {
        byte d1;
    }

    public static class E extends D {
        byte e1;
    }

}
