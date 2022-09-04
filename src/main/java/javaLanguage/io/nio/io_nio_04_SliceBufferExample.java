package javaLanguage.io.nio;

import java.nio.ByteBuffer;

/**
 * @Author: guang
 * @Date: 2022/9/4
 * @Desc: io_nio_04_SliceBufferExample
 */
public class io_nio_04_SliceBufferExample {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }

        buffer.position(3);

        //  指定还有多少数据需要取出(在从缓冲区写入通道时)，或者还有多少空间可以放入数据(在从通道读入缓冲区时)。
        buffer.limit(7);
        /**
         * 缓冲区切片，将一个大缓冲区的一部分切出来，作为一个单独的缓冲区，但是它们公用同一个内部数组。
         * 切片从原缓冲区的position位置开始，至limit为止。
         * 原缓冲区和切片各自拥有自己的属性
         */
        ByteBuffer slice = buffer.slice();

        for (int i=0; i<slice.capacity(); ++i) {
            byte b = slice.get( i );
            b *= 11;
            slice.put( i, b );
        }

        buffer.position( 0 );
        buffer.limit( buffer.capacity() );

        while (buffer.remaining()>0) {
            System.out.println( buffer.get() );
        }
    }
}