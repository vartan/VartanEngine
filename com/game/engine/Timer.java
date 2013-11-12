
package com.game.engine;

/*
 * @author Vartan
 */
public class Timer {
    long startTime = 0;
    long length = 0;
    public Timer(long milliseconds) {
        length=milliseconds;
        reset();
    }
    public void reset() {
        startTime = System.currentTimeMillis();
    }
    public boolean isDone() {
        return System.currentTimeMillis() - startTime > length;
    }
}
