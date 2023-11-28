package SapGeneric;

import Conn.SapConn;
import ErrorHandler.ErrorCodes;
import ErrorHandler.SapErrorHandler;
import Utils.NumberConverter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComException;
import com.jacob.com.Variant;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * You can use this class to suppress any necessity that this library can't handle.
 * You should initialize it using {@link Conn.SapConn#getStandart(int)} method.
 * With this you can construct your own methods using Jacob library.
 */

@Component
public class Standart {

    @Autowired
    NumberConverter numberConverter;

    public Standart(ActiveXComponent initSess) {
        this.session = new ActiveXComponent(initSess.invoke("Children",0).toDispatch());
        this.parentSession = initSess;
    }

    /**
     * Object used to control complex SAP_GUI elements
     */
    public ActiveXComponent obj;


    /**
     * Object used to control current session and simple SAP_GUI elements as TextFields
     */
    public ActiveXComponent session;


    /**
     * Parent session used to set others objects
     */
    public ActiveXComponent parentSession;


    /**
     * Used as parameters in some methods
     */
    public Variant[] arg = new Variant[2];

    //Regex para verificar qual é a wnd [0] ou [1]...
    public ErrorCodes isExisting(String id){
        ErrorCodes statusCode = ErrorCodes.DEFAULT;

        try {
            session = new ActiveXComponent(parentSession.invoke("FindById", "wnd["+id.substring(4,5)+"]").toDispatch());
        } catch (ComException e){
            //return new SapErrorHandler().getCode(e.getHResult());
        }
        while (statusCode.equals(ErrorCodes.DEFAULT) || statusCode.equals(ErrorCodes.NO_DATA_LOAD)){
            try {
                new ActiveXComponent(session.invoke("FindById", id).toDispatch());
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
                return ErrorCodes.OK;
            }
            default -> throw new ComException() {};
        }
            return null;
    }

}
