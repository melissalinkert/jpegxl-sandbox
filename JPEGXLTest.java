import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import dev.brunsli.wrapper.Codec;

public class JPEGXLTest {
    public static void main(String[] args) throws Exception {
        System.loadLibrary("brunsli_jni");

        boolean encode = args[0].equals("encode");
        Path input = Paths.get(args[1]);
        Path output = Paths.get(args[2]);

        byte[] in = Files.readAllBytes(input);
        byte[] out = null;

        if (encode) {
            out = Codec.encode(in);
        }
        else {
            out = Codec.decode(in);
        }

        Files.write(output, out);
    }
}
