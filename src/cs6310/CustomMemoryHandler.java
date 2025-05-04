package cs6310;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.*;

public class CustomMemoryHandler extends StreamHandler {
    private ByteArrayOutputStream baos;

    public CustomMemoryHandler() {
        baos = new ByteArrayOutputStream();
        setOutputStream(baos);
        setFormatter(new MyFormatter());  // or use the custom formatter MyFormatter if needed
        setLevel(Level.INFO);
    }

    public String getLog() {
        flush();
        return baos.toString();
    }

    public void close() {
        super.close();
        try {
            baos.close();
        } catch (IOException e) {
            // Handle or log if needed
        }
    }
}
