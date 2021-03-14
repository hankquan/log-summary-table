package com.github.howaric.log.summary.core;

public abstract class ReportLine implements ReportInfo {

    /**
     * get number of a step
     *
     * @return
     */
    protected abstract String getNo();

    /**
     * get action name of this step
     *
     * @return
     */
    protected abstract String getAction();

    /**
     * get result of this step
     *
     * @return
     */
    protected abstract String getResult();

    /**
     * get remarks of this step
     *
     * @return
     */
    protected abstract String getRemarks();

    /**
     * get cost time
     *
     * @return
     */
    protected abstract String getTime();

    /**
     * get create timestamp
     *
     * @return
     */
    protected abstract Long getCreateTime();

}
