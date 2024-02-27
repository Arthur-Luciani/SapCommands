package Connection;

import ErrorHandler.SapErrorHandler;
import SapGenericEnuns.ErrorCodes;
import Utils.NumberConverter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComException;
import com.jacob.com.Variant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * This class provides all ActiveXComponents as you need to send requests do Sap API with established connection with Sap
 * in a free sap session
 * You can use this class to suppress any necessity that this library can't handle.
 * You should initialize it using {@link SapConnector#getMessenger(int)} method.
 * With this, you can construct your own methods using the Jacob library.
 */

public class SapMessenger {

    @Autowired
    NumberConverter numberConverter;
    public ActiveXComponent obj;
    public ActiveXComponent parentSession;
    public ActiveXComponent session;
    public Variant[] arg = new Variant[2];
    private static final Logger logger = LogManager.getLogger(SapMessenger.class);

    private static final String STATUS_BAR_ID = "wnd[0]/sbar";

    public SapMessenger(ActiveXComponent initSes) {
        this.parentSession = initSes;
        this.session = new ActiveXComponent(parentSession.invoke("Children",0).toDispatch());
    }

    /**
     * Set the correct window of session based on provided Id
     * @param Id Id with window information
     * @return Updated session
     */
    private ActiveXComponent updateSession(String Id) {
        try {
            new ActiveXComponent(parentSession.invoke("FindById", "wnd["+Id.charAt(4)+"]").toDispatch());
        } catch (Exception ignored) {}
        return session;
    }

    /**
     * Check if an Id is available to use
     * @param Id Id to analyze
     * @return Result of check as ErrorCode
     */
    public ErrorCodes isExisting(String Id) {
        long startTime = System.nanoTime();
        session = updateSession(Id);
        ErrorCodes statusCode = ErrorCodes.DEFAULT;
        List<ErrorCodes> errors = List.of(ErrorCodes.DEFAULT, ErrorCodes.NO_DATA_LOAD);

        while (errors.contains(statusCode)) {
            try {
                new ActiveXComponent(session.invoke("FindById", Id).toDispatch());
                statusCode = ErrorCodes.OK;
            } catch (ComException e) {
                statusCode = new SapErrorHandler().getCode(e.getHResult());
            }
        }
        return checkErrorCode(statusCode, Id, startTime);
    }

    /**
     * @param errorCode ErrorCode to analyze
     * @param Id Id from isExisting method
     * @param startTime Start time from isExisting method
     * @return Correct ErrorCode
     */
    private ErrorCodes checkErrorCode(ErrorCodes errorCode, String Id, long startTime) {
        switch (errorCode) {
            case NO_FIND_BY_ID -> {
                logger.error("No find by Id");
                return ErrorCodes.NO_FIND_BY_ID;
            }
            case NO_CONN -> {
                logger.error("No connection");
                return ErrorCodes.NO_CONN;
            }
            case NO_DATA_LOAD -> {
                logger.error("No data load");
                logger.debug("IsExisting method fails while waiting SAP data");
                return ErrorCodes.NO_DATA_LOAD;
            }
            case OK -> {
                if (verifyStatusBar().equals(ErrorCodes.OK)) {
                    logger.info(((System.nanoTime() - startTime) / 1000000) + " isExisting execution time");
                    return ErrorCodes.OK;
                }
                return isExisting(Id);
            }
            default -> {
                logger.error("No mapped error");
                return ErrorCodes.NO_MAPPED;
            }
        }
    }

    /**
     * @return StatusBar obj as ActiveXComponent
     */
    private ActiveXComponent getStatusBar() {
        ActiveXComponent tempSession = new ActiveXComponent(parentSession.invoke("FindById", "wnd[0]").toDispatch());
        ActiveXComponent statusObj = new ActiveXComponent(tempSession.invoke("FindById", STATUS_BAR_ID).toDispatch());
        obj = new ActiveXComponent(tempSession.invoke("FindById", "wnd[0]").toDispatch());
        return statusObj;
    }

    /**
     * Check if it has any Warning or Alert that can interrupt the automation and clean it
     * @return  OK if it has no interruptions, DEFAULT if it has some interruptions
     */
    private ErrorCodes verifyStatusBar() {
         ActiveXComponent statusBar = getStatusBar();
        if(statusBar.getPropertyAsBoolean("MessageAsPopup")) {
            obj.invoke("sendVKey", 12);
            return ErrorCodes.DEFAULT;
        }
        String messageType = statusBar.getPropertyAsString("MessageType");
        if (messageType.equals("W")) {
            obj.invoke("sendVKey", 0);
            return ErrorCodes.DEFAULT;
        }
        return ErrorCodes.OK;
    }

    public void disconnect() {
        new SapConnector().disconnect(session);
    }

}
