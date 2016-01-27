package org.ayo.random;

/**
 * @author zozohtnt
 * @author wendal(wendal1985@gmail.com)
 */
public class StringGenerator {

    /**
     * 
     * @param max larger than 0
     */
    public StringGenerator(int max) {
        maxLen = max;
        minLen = 1;
    }

    /**
     * 
     * @param min larger than 0
     * @param max larger than min
     */
    public StringGenerator(int min, int max) {
        maxLen = max;
        minLen = min;
    }

    /**
     * min length of the string
     */
    private int maxLen;

    /**
     * max length of the string
     */
    private int minLen;

    /**
     * 
     */
    public void setup(int min, int max) {
        minLen = min;
        maxLen = max;
    }

    /**
     */
    public String next() {
        if (maxLen <= 0 || minLen <= 0 || minLen > maxLen)
            return null;
        char[] buf = new char[R.random(minLen, maxLen)];
        for (int i = 0; i < buf.length; i++)
            buf[i] = CharGenerator.next();
        return new String(buf);
    }

}
