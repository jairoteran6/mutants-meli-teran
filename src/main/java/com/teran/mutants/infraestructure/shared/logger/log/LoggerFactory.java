package com.teran.mutants.infraestructure.shared.logger.log;

public class LoggerFactory {

     static public Log create() {
        return LogService.getInstance();
    }

}
