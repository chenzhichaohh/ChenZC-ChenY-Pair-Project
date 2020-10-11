package com.bichoncode.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 随机数生成工具
 */
public class RandomUtils {
    public static int getARandom(int range){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        // 产生一个包括上限不包括下陷的值
        return random.nextInt(range);
    }
}

