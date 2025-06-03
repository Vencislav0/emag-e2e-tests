package constants;

public enum Timeouts {
    LONG_TIMEOUT(50),
    MEDIUM_TIMEOUT(20),
    SHORT_TIMEOUT(5),
    EXTRA_SHORT_TIMEOUT(3),
    API_TIMEOUT(60),
    DEFAULT_WAIT_TIMEOUT(10),
    DEFAULT_WAIT_INTERVAL(1);
    private final int timeout;

    Timeouts(int timeout){
        this.timeout = timeout;

    }

    public int getTimeout(){
        return this.timeout;
    }
}
