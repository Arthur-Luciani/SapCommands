package ErrorHandler;

import SapGenericEnuns.ErrorCodes;

public class SapErrorHandler {

    private static final int noDataLoad = -2147483638;
    private static final int noFindByID = -2147220373;
    private static final int noFindByID2 = -2147159288;
    private static final int noConn = -2147220378;

    public ErrorCodes getCode(int errorCode){
        if (errorCode == noDataLoad){
            return ErrorCodes.NO_DATA_LOAD;
        } else if (errorCode == noFindByID  || errorCode == noFindByID2) {
            return ErrorCodes.NO_FIND_BY_ID;
        } else if (errorCode == noConn) {
            return ErrorCodes.NO_CONN;
        }
        return ErrorCodes.NO_MAPPED;
    }
}
