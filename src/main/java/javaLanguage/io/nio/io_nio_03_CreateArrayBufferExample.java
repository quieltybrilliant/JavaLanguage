package javaLanguage.io.nio;

import java.nio.ByteBuffer;

/**
 * @Author: guang
 * @Date: 2022/9/4
 * @Desc: io_iuc_CreateArrayBufferExample
 */
public class io_nio_03_CreateArrayBufferExample {
    public static void main(String[] args) {
        byte array[] = new byte[1024];

        // 将字节数组包装到缓冲区中
        ByteBuffer buffer = ByteBuffer.wrap( array );

        buffer.put( (byte)'a' );
        buffer.put( (byte)'b' );
        buffer.put( (byte)'c' );

        buffer.flip();

        System.out.println( (char)buffer.get() );
        System.out.println( (char)buffer.get() );
        System.out.println( (char)buffer.get() );
    }
}