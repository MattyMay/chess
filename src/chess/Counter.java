package chess;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

public enum Counter implements Serializable{

    INSTANCE;

    private final AtomicLong value = new AtomicLong();

    public long generate() {
        return value.incrementAndGet();
    }

}