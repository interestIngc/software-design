package clock;

import java.time.Instant;

public class FakeClock implements Clock {
    Instant time;

    public FakeClock(Instant time) {
        this.time = time;
    }

    @Override
    public Instant now() {
        return time;
    }

    public void setTimeMs(Instant time) {
        this.time = time;
    }
}
