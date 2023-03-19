package javaLanguage.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: guang
 * @Date: 2022/9/4
 * @Desc: io_nio_CopyFileExample
 */
public class io_nio_02_CopyFileExample {
    public static void main(String[] args) throws IOException {

        String inFile = "";
        String outFile = "";

        FileInputStream inputStream = new FileInputStream(inFile);
        FileOutputStream outputStream = new FileOutputStream(outFile);

        FileChannel inChannel = inputStream.getChannel();
        FileChannel outChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);


        while (true) {
            // 读入之前要清空
            buffer.clear();
            // position自动前进
            int r =  inChannel.read(buffer);

            if (r == -1) {
                break;
            }

            // position = 0; limit=读到的字节数
            buffer.flip();

            // 从 buffer 中读
            outChannel.write(buffer);
        }

    }
}