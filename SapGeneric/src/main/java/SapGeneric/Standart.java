package SapGeneric;

import Utils.NumberConverter;
import com.jacob.activeX.*;
import com.jacob.com.Variant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
public abstract class Standart {

    @Autowired
    CheckElement checkElement;

    @Autowired
    NumberConverter numberConverter;

    public Standart(ActiveXComponent initSess) {
        this.session = new ActiveXComponent(initSess.invoke("Children",0).toDispatch());
        this.parentSession = initSess;
    }

    /**
     * Object used to control complex SAP_GUI elements
     */
    ActiveXComponent obj;


    /**
     * Object used to control current session and simple SAP_GUI elements as TextFields
     */
    ActiveXComponent session;


    /**
     * Parent session used to set others objects
     */
    ActiveXComponent parentSession;


    /**
     * Used as parameters in some methods
     */
    Variant[] arg = new Variant[2];


}
