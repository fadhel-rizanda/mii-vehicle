package interfaces;

public interface Lockable {
    void lock();
    void unlock();
    boolean isLocked();
}