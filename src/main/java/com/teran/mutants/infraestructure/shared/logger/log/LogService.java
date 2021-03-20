package com.teran.mutants.infraestructure.shared.logger.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LogService implements Log {

    private static LogService instance = null;
    private LogFactory logFactory=new LogFactory();
    private static final Logger LOG = LoggerFactory.getLogger("Log");
    private static final String INFO_TYPE = "Info";
    private static final String ERROR_TYPE = "Error";
    private static final String DEBUG_TYPE = "Debug";
    private static final String AUDIT_TYPE = "Audit";

    private LogService() { }

    public static LogService getInstance()
    {
        if (instance == null){
            instance = new LogService();
        }
        return instance;
    }

    public void error (String message){
        LOG.error(logFactory.createLog(ERROR_TYPE,message));
    }

    public void error(Exception exception){
        LOG.error(logFactory.createLog(ERROR_TYPE,exception));
    }

    public void info (String message){
        LOG.info(logFactory.createLog(INFO_TYPE,message));
    }

    public void info(Exception exception){
        LOG.info(logFactory.createLog(INFO_TYPE,exception));
    }

    public void audit (String message){
        LOG.info(logFactory.createLog(AUDIT_TYPE,message));
    }

    public void audit(Exception exception){
        LOG.info(logFactory.createLog(AUDIT_TYPE,exception));
    }

}




