package com.bichoncode.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 随机数生成工具
 */
public class RandomUtils {
    public static int getARandom(int range){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(range);
    }
}

