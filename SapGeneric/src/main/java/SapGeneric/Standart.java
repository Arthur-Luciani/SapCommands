package SapGeneric;

import Conn.SapConn;
import ErrorHandler.ErrorCodes;
import ErrorHandler.SapErrorHandler;
import Utils.NumberConverter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComException;
import com.jacob.com.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * You can use this class to suppress any necessity that this library can't handle.
 * You should initialize it using {@link Conn.SapConn#getStandart(int)} method.
 * With this you can construct your own methods using Jacob library.
 */


public class Standart {

    @Autowired
    NumberConverter numberConverter;

    public ActiveXComponent obj;
    private ActiveXComponent statusObj;
    public ActiveXComponent parentSession;
    public ActiveXComponent session;
    public Variant[] arg = new Variant[2];

    private static final String STATUS_BAR_ID = "wnd[0]/sbar";




    public Standart(ActiveXComponent initSess) {
        this.session = new ActiveXComponent(initSess.invoke("Children",0).toDispatch());
        this.parentSession = initSess;
    }



    public ErrorCodes isExisting(String ID){
        long startTime = System.nanoTime();
        StringBuilder strBuilder = new StringBuilder();

        ErrorCodes statusCode = ErrorCodes.DEFAULT;
        try {
            session = new ActiveXComponent(parentSession.invoke("FindById", "wnd["+ID.charAt(4)+"]").toDispatch());
        } catch (ComException ignored){}

        while (statusCode.equals(ErrorCodes.DEFAULT) || statusCode.equals(ErrorCodes.NO_DATA_LOAD)){
            try {
                 new ActiveXComponent(session.invoke("FindById", ID).toDispatch());
                statusCode = ErrorCodes.OK;
            } catch (ComException e){
                statusCode = new SapErrorHandler().getCode(e.getHResult());
            }
        }
        switch (statusCode){
            case NO_DATA_LOAD -> {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            case NO_FIND_BY_ID -> {
                return ErrorCodes.NO_FIND_BY_ID;
            }
            case OK -> {
                if (handleStatus() == ErrorCodes.OK) {
                    log(startTime);
                    return ErrorCodes.OK;
                }
                isExisting(ID);
            }
            default -> throw new ComException() {};
        }
        long endTime = System.nanoTime();
        System.out.println(((endTime - startTime) / 1000000) + " milliseconds execution time");
        return null;
    }

    private void log(long startTime) {
        long time = (System.nanoTime() - startTime) / 1000000;

        if (time < 100) {
            System.out.println((time) + " isExisting execution time");
        } else {
            System.out.println((time) + " isExisting execution time | LoadData");
        }

    }

    public void setStatusBar() {
        ActiveXComponent tempSession = new ActiveXComponent(parentSession.invoke("FindById", "wnd[0]").toDispatch());
        statusObj = new ActiveXComponent(tempSession.invoke("FindById", STATUS_BAR_ID).toDispatch());
        obj = new ActiveXComponent(tempSession.invoke("FindById", "wnd[0]").toDispatch());
    }

    public ErrorCodes handleStatus() {
        setStatusBar();

        if(statusObj.getPropertyAsBoolean("MessageAsPopup")) {
            obj.invoke("sendVKey", 12);
            return ErrorCodes.NO_DATA_LOAD;
        }

        String messageType = statusObj.getPropertyAsString("MessageType");
        switch (messageType) {
            case "W" -> {
                obj.invoke("sendVKey", 0);
                return ErrorCodes.NO_DATA_LOAD;
            }
        }
        return ErrorCodes.OK;
    }

    public void disconnect() {
        new SapConn().disconnect(session);
    }

}
