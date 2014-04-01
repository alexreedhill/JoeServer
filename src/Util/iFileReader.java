package Util;

import java.io.IOException;

public interface iFileReader {
    public byte[] read() throws IOException;
    public byte[] read(String path) throws IOException;
}
