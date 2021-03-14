package com.github.howaric;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTableTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTableTest.class);

    @Test
    public void simple() {
        LOGGER.info("#title this is my title {}", 1223);
        LOGGER.info("#header Version=1.1.2,Tag=My Tag");
        LOGGER.info("#section SectionA");
        LOGGER.info("#step StepA");
        LOGGER.info("Do logic here");
        LOGGER.info("just log what you like");
        LOGGER.info("#remark StepA did something");
        LOGGER.info("#step StepB");
        LOGGER.info("#remark StepB did another something");
        LOGGER.info("#section SectionB");
        LOGGER.info("#step StepC");
        LOGGER.error("#fail StepC failed");
        LOGGER.info("#summary");
    }

}
