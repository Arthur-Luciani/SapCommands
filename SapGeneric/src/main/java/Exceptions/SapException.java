package Exceptions;

import static Exceptions.ExceptionCauses.*;

public class SapException extends Exception{
    //Implements getStatusBar message

    private ExceptionCauses exceptionCauses;

    public SapException(String message) {
        super(message);
    }

    public SapException(String message, ExceptionCauses exceptionCauses) {
        super(message);
        this.exceptionCauses = exceptionCauses;
    }

    public SapException(String message, int number) {
        super(message);
        if (number == -2147483638){
            this.exceptionCauses = NO_DATA_LOAD;
        } else if (number == -2147220373  || number == -2147159288 || number == -1) {
            this.exceptionCauses = NO_FIND_BY_ID;
        } else if (number == -2147220378) {
            this.exceptionCauses = NO_CONN;
        } else {
            this.exceptionCauses = NO_MAPPED;
        }
    }

    public ExceptionCauses getExCode() {
        return exceptionCauses;
    }
}
