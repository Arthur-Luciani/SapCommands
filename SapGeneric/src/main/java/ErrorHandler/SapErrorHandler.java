package ErrorHandler;

public class SapErrorHandler {

    private static int noDataLoad = -2147483638;
    private static int noFindByID = -2147220373;
    private static int noConn = -2147220378;

    public ErrorCodes getCode(int errorCode){
        if (errorCode == noDataLoad){
            return ErrorCodes.NO_DATA_LOAD;
        } else if (errorCode == noFindByID) {
            return ErrorCodes.NO_FIND_BY_ID;
        } else if (errorCode == noConn) {
            return ErrorCodes.NO_CONN;
        }
        return ErrorCodes.NO_MAPPED;
    }
}
