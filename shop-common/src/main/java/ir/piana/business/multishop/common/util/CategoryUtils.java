package ir.piana.business.multishop.common.util;

public class CategoryUtils {
    public static long getBitsAsTrue(int bitCount) {
        return (long) Math.pow(2, bitCount) - 1;
    }

    public static long getNextCategory(long category, int[] pattern) {
        long lastActiveBits = 0;
        long preLastActiveBits = 0;
        int beforeBitsCount = 0;
        for (int i = 0; i < pattern.length; i++) {
            long levelBits = getBitsAsTrue(pattern[i]);
            long t = category & (levelBits << (64 - beforeBitsCount - pattern[i]));
            if (t != 0)
                lastActiveBits = t;
            else
                break;
            beforeBitsCount += pattern[i];
            if (i > 0)
                preLastActiveBits += pattern[i];
        }
        long next = (category >> (64 - beforeBitsCount + preLastActiveBits) << (64 - beforeBitsCount + preLastActiveBits)) |
                (((lastActiveBits >> (64 - beforeBitsCount)) + 1) << (64 - beforeBitsCount));
        return  next;
    }

    public static long getNextCategory2(long category, int[] pattern) {
        int beforeBitsCount = 0;
        for (int i = pattern.length - 1; i > 0; i--) {
            long levelBits = getBitsAsTrue(pattern[i]) << beforeBitsCount;
            beforeBitsCount += pattern[i];
            long l = category & levelBits;
            if (l > 0) {
                if (l != levelBits) {
                    long t = ((l >> (beforeBitsCount - pattern[i])) + 1) << (beforeBitsCount - pattern[i]);
                    long next =  (((category >> beforeBitsCount) << beforeBitsCount) | t) |
                            ((category << 64 - beforeBitsCount + pattern[i]) >> 64 - beforeBitsCount + pattern[i]);
                    return next;
                }
            }
        }
        return Long.MAX_VALUE;
    }

    public static void main(String[] args) {
        System.out.println(getNextCategory2(4041028100000000l,
                new int[] {2, 8, 8, 8, 8, 8, 8, 8, 6}));
    }
}
