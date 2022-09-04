package javaLanguage.io.nio;

import java.nio.FloatBuffer;

/**
 * @Author: guang
 * @Date: 2022/9/4
 * @Desc: io_nio_01_FloatBufferExample
 */
public class io_nio_01_FloatBufferExample {
    public static void main(String[] args) {
        // 创建FloatBuffer，容量10,单位 float，可以防止10个float
        FloatBuffer buffer = FloatBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            float f = (float) Math.PI * i;
            buffer.put(f);
        }

        // 此时指针为尾部，将指针指向首
        buffer.flip();

        while (buffer.hasRemaining()) {
            float f = buffer.get();
            System.out.println(f);
        }
    }
}