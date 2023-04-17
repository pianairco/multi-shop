package ir.piana.business.multishop.common.util;

public class CategoryUtils {
    public static long getBitsAsTrue(int bitCount) {
        return (long) Math.pow(2, bitCount) - 1;
    }

    public static long getNextCategory(long category, int[] pattern) {
        int beforeBitsCount = 0;
        for (int i = pattern.length - 1; i > 0; i--) {
            beforeBitsCount += pattern[i];
            long levelBits = getBitsAsTrue(pattern[i]) << beforeBitsCount;
            long l = category & levelBits;
            if (l > 0) {
                if (l != levelBits) {
                    long t = ((l >> (beforeBitsCount)) + 1) << (beforeBitsCount);
                    long next =  (((category >> (beforeBitsCount + pattern[i])) << (beforeBitsCount + pattern[i])) | t) |
                            (category & getBitsAsTrue(beforeBitsCount));
                    return next;
                }
            }
        }
        return Long.MAX_VALUE;
    }

    public static void main(String[] args) {
        System.out.println(getNextCategory(0x40bff3c000000000l,
                new int[] {2, 8, 8, 8, 8, 8, 8, 6}));
    }
}
