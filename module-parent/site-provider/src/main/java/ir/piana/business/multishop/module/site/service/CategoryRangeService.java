package ir.piana.business.multishop.module.site.service;

import ir.piana.business.multishop.module.site.data.entity.BayaCategoryEntity;
import ir.piana.business.multishop.module.site.data.entity.PianaCategoryEntity;
import ir.piana.business.multishop.module.site.data.repository.PianaCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CategoryRangeService {
    @Autowired
    private PianaCategoryRepository categoryRepository;

    private static final long[] GROUPS = new long[] {
            0x4000000000000000l,
            0x3fc0000000000000l,
            0x003fc00000000000l,
            0x00003fc000000000l,
            0x0000003fc0000000l,
            0x000000003f000000l,
            0x0000000000fc0000l,
            0x000000000003f000l,
            0x0000000000000f00l,
            0x00000000000000f0l,
            0x000000000000000fl,
    };

    public static final long ROOT_CATEGORY = GROUPS[0];

    private static final long[] GROUPS_LENGTH = new long[] {
            2,
            8,
            8,
            8,
            8,
            6,
            6,
            6,
            4,
            4,
            4
    };

    private static final long[] GROUPS_SHIFT = new long[] {
            62,
            54,
            46,
            38,
            30,
            24,
            18,
            12,
            8,
            4,
            0
    };

    @PostConstruct
    public void init() {
//        long t1 = 0x4205500000000000l;
//        long t2 = detectBoundary(t1);
//        String st1 = Long.toHexString(t1);
//        String st2 = Long.toHexString(t2);
//        String st11 = Long.toBinaryString(t1);
//        String st21 = Long.toBinaryString(t2);
//        System.out.println(st1);
//        System.out.println(st2);
    }

    public long detectBoundary (long category) {
        int shiftCounter = 0;
        for(int i = GROUPS.length - 1; i >= 0; i--) {
            if((GROUPS[i] & category) == 0) {
                shiftCounter += GROUPS_LENGTH[i];
            } else {
                long temp = category >> shiftCounter;
                temp += 1;
                return temp << shiftCounter;
            }
        }
        return Long.MAX_VALUE;
    }

    public long createId(long parentId, int level, long siblingCount) {
        return parentId | (GROUPS[level] & (siblingCount << GROUPS_SHIFT[level]));
    }

}
