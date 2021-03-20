package com.teran.mutants.infraestructure.shared.logger.log;

public interface Log {

    public void error (String message);
    public void error(Exception exception);
    public void info (String message);
    public void info(Exception exception);
    public void audit (String message);
    public void audit(Exception exception);

}
