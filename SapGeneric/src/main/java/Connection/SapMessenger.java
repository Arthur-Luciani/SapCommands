package Connection;

import Exceptions.ExceptionCauses;
import Exceptions.SapException;
import Utils.NumberConverter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComException;
import com.jacob.com.Variant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static Exceptions.ExceptionCauses.*;


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
    private final int sessionNumber;
    public ActiveXComponent obj;
    public ActiveXComponent parentSession;
    public ActiveXComponent session;
    public Variant[] arg = new Variant[2];
    private static final Logger logger = LogManager.getLogger(SapMessenger.class);

    private static final String STATUS_BAR_ID = "wnd[0]/sbar";

    public SapMessenger(ActiveXComponent initSes, int sessionNumber) {
        this.sessionNumber = sessionNumber;
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
            return new ActiveXComponent(parentSession.invoke("FindById", "wnd["+Id.charAt(4)+"]").toDispatch());
        } catch (Exception ignored) {}
        return session;
    }

    /**
     * Check if an Id is available to use
     * @param Id Id to analyze
     * @return Result of check as ErrorCode
     */
    public ExceptionCauses isExisting(String Id) {
        long startTime = System.nanoTime();
        session = updateSession(Id);
        ExceptionCauses statusCode = DEFAULT;
        List<ExceptionCauses> errors = List.of(DEFAULT, NO_DATA_LOAD);

        while (errors.contains(statusCode)) {
            try {
                new ActiveXComponent(session.invoke("FindById", Id).toDispatch());
                statusCode = OK;
            } catch (ComException e) {
                statusCode = new SapException(e.getMessage(), e.getHResult()).getExCode();

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
    private ExceptionCauses checkErrorCode(ExceptionCauses errorCode, String Id, long startTime) {
        switch (errorCode) {
            case NO_FIND_BY_ID -> {
                logger.error("No find by Id");
                if (!updatedConn()) {
                    return NO_FIND_BY_ID;
                }
                return isExisting(Id);
            }
            case NO_CONN -> {
                logger.error("No connection");
                return NO_CONN;
            }
            case NO_DATA_LOAD -> {
                logger.error("No data load");
                logger.debug("IsExisting method fails while waiting SAP data");
                return NO_DATA_LOAD;
            }
            case OK -> {
                if (verifyStatusBar().equals(OK)) {
                    logger.info(((System.nanoTime() - startTime) / 1000000) + " isExisting execution time");
                    return OK;
                }
                return isExisting(Id);
            }
            default -> {
                logger.error("No mapped error");
                return NO_MAPPED;
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
    private ExceptionCauses verifyStatusBar() {
         ActiveXComponent statusBar = getStatusBar();
        if(statusBar.getPropertyAsBoolean("MessageAsPopup")) {
            obj.invoke("sendVKey", 12);
            return DEFAULT;
        }
        String messageType = statusBar.getPropertyAsString("MessageType");
        if (messageType.equals("W")) {
            obj.invoke("sendVKey", 0);
            return DEFAULT;
        }
        return OK;
    }

    private boolean updatedConn() {
        try {
            session.getPropertyAsComponent("Info");
        } catch (Exception e) {
            try {
                this.parentSession = SapConnector.getSession(sessionNumber);
            } catch (SapException e1) {
                System.out.println(e1.getMessage());
            }
            this.session = new ActiveXComponent(parentSession.invoke("Children",0).toDispatch());
            return true;
        }
        return false;
    }

    public void disconnect() {
        new SapConnector().disconnect(session);
    }

}
