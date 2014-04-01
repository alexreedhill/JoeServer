package Mocks;

import Util.iFileReader;

public class MockFileReader implements iFileReader {

    public byte[] read() {
        return "123".getBytes();
    }

    public byte[] read(String path) {
        return "123".getBytes();
    }

}
