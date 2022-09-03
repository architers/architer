package io.github.architers.log.common.service;


import io.github.architers.log.common.LogMeta;

public abstract class LogAbstract implements Log {

    @Override
    public void execute(LogFunction logFunction) {
        LogMeta logMeta = logFunction.getLogMeta();
        this.doLog(logMeta);
    }

    /**
     * 执行记录
     *
     * @param logMeta
     */
    protected abstract void doLog(LogMeta logMeta);
}
