package Util;

import java.io.IOException;

public interface iFileWriter {
    public void writeFullContent() throws IOException;
    public byte[] writePartialContent(byte[] fileContents) throws Exception;
}
