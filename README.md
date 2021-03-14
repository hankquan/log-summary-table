# log-summary-table
Pirnt execution steps in a summary table

## usage
```java
    LOGGER.info("#title this is my {}", "title");
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
```
output
```
2021-03-14 18:46:53.326 [main] INFO  com.github.howaric.SimpleTableTest : =================================== [Summary: this is my title] ====================================
2021-03-14 18:46:53.336 [main] INFO  com.github.howaric.SimpleTableTest : - [SectionA]
2021-03-14 18:46:53.338 [main] INFO  com.github.howaric.SimpleTableTest : ===========================================  T1.s1 StepA ===========================================
2021-03-14 18:46:53.339 [main] INFO  com.github.howaric.SimpleTableTest : Do logic here
2021-03-14 18:46:53.339 [main] INFO  com.github.howaric.SimpleTableTest : just log what you like
2021-03-14 18:46:53.339 [main] INFO  com.github.howaric.SimpleTableTest : StepA did something
2021-03-14 18:46:53.339 [main] INFO  com.github.howaric.SimpleTableTest : ===========================================  T1.s2 StepB ===========================================
2021-03-14 18:46:53.339 [main] INFO  com.github.howaric.SimpleTableTest : StepB did another something
2021-03-14 18:46:53.339 [main] INFO  com.github.howaric.SimpleTableTest : - [SectionB]
2021-03-14 18:46:53.339 [main] INFO  com.github.howaric.SimpleTableTest : ===========================================  T1.s3 StepC ===========================================
2021-03-14 18:46:53.339 [main] ERROR com.github.howaric.SimpleTableTest : StepC failed
2021-03-14 18:46:53.339 [main] INFO  com.github.howaric.SimpleTableTest : -
*************************************
*  Summary:       this is my title  *
*  Version:                  1.1.2  *
*  Tag:                     My Tag  *
*  Start time: 2021-03-14 18:46:53  *
*  End time:   2021-03-14 18:46:53  *
*  Duration:                 00:00  *
*************************************
+-------+------------+--------+-----------------------------+---------------------+----------+
|  No.  |   ACTION   | RESULT |           REMARKS           |      TIMESTAMP      | DURATION |
+-------+------------+--------+-----------------------------+---------------------+----------+
|       | [SectionA] |        |                             |                     |          |
+-------+------------+--------+-----------------------------+---------------------+----------+
| T1.s1 | StepA      |   V    | StepA did something         | 2021-03-14 18:46:53 |  00:00   |
+-------+------------+--------+-----------------------------+---------------------+----------+
| T1.s2 | StepB      |   V    | StepB did another something | 2021-03-14 18:46:53 |  00:00   |
+-------+------------+--------+-----------------------------+---------------------+----------+
|       | [SectionB] |        |                             |                     |          |
+-------+------------+--------+-----------------------------+---------------------+----------+
| T1.s3 | StepC      |   X    | [ERROR] StepC failed        | 2021-03-14 18:46:53 |  00:00   |
+-------+------------+--------+-----------------------------+---------------------+----------+
```
