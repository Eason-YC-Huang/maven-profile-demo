package ink.eason.demo.maven.profile;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Main {

    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        System.out.println(gson != null);
        InputStream is = Main.class.getClassLoader().getResourceAsStream("vaultlib/commons-collections4-4.4.jar");
        if (is != null) {
            System.out.println("file exists");
            Path libDir = Path.of("./vaultlib");
            if (Files.notExists(libDir)) {
                Files.createDirectory(libDir);
            }
            Path output = Path.of("./vaultlib/commons-collections4-4.4.jar/");
            try (FileChannel outCh = FileChannel.open(output, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                 ReadableByteChannel inCh = Channels.newChannel(is)) {
                long position = 0 , step = 16384;
                for (; ; ) {
                    long transferred = outCh.transferFrom(inCh, position, step);
                    if (transferred <= 0) {break;}
                    position += transferred;
                }
            }
            System.out.println("Done");
        }

    }

}
