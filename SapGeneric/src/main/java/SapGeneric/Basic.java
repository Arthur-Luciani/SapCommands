package SapGeneric;

import SapGenericEnuns.ErrorCodes;
import Utils.NumberConverter;
import com.jacob.activeX.ActiveXComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class Basic{
    @Autowired
    SapMessenger sapMessenger;

    public void setStandart(SapMessenger sapMessenger) {
        this.sapMessenger = sapMessenger;
    }

    @Autowired
    NumberConverter numberConverter;


    /**
     * @param elementId GUI ID element
     * @return String value from GUI element
     */
    public String getText(String elementId){
        sapMessenger.isExisting(elementId);
        sapMessenger.obj = new ActiveXComponent(sapMessenger.session.invoke("FindById", elementId).toDispatch());
        return sapMessenger.obj.getPropertyAsString("text");
    }

    /**
     * @param label GUI ID element
     * @param value String value to put on GUI element
     */
    public void setText(String label, String value){
        sapMessenger.isExisting(label);
        sapMessenger.obj = new ActiveXComponent(sapMessenger.session.invoke("FindById", label).toDispatch());
        sapMessenger.obj.setProperty("text", value);
    }

    /**
     * @param elementId GUI ID element
     * @return Float value from GUI element
     */
    public float getFloatText(String elementId){
        return numberConverter.getFloat(getText(elementId));
    }


    /**
     * @param label GUI element
     * @param value Float value to put on GUI element
     */
    @Deprecated
    public void setFloatText(String label, Float value){
        setText(label, numberConverter.getString(value));
    }

    /**
     * @param elementId GUI ID element
     * @return Int value from GUI element
     */
    public int getIntText(String elementId){
        return numberConverter.getInt(getText(elementId));
    }


    /**
     * @param label GUI ID element
     * @param value Int value to put on GUI element
     */
    @Deprecated
    public void setIntText(String label, int value){
        setText(label, numberConverter.getString(value));
    }

    /**
     * @param elementId GUI ID element
     * @return Long value from GUI ID element
     */
    public long getLongText(String elementId){
        return numberConverter.getLong(getText(elementId));
    }

    /**
     * @param label GUI ID element
     * @param value Long value to put on GUI element
     */
    @Deprecated
    public void setLongText(String label, long value){
        setText(label, numberConverter.getString(value));
    }

    public BigDecimal getBigDecimalText(String elementId){
        return new BigDecimal(getText(elementId).replace(".", "").replace(",", "."));
    }

    /**
     * @param label GUI ID element
     * @param value Any number value
     */
    public <T> void setNumberText(String label, T value){
        setText(label, numberConverter.getString(value));
    }

    /**
     * @param elementId GUI ID element
     * @param option Select option
     */
    public void select(String elementId, Boolean option){
        sapMessenger.isExisting(elementId);
        sapMessenger.obj = new ActiveXComponent(this.sapMessenger.session.invoke("FindById", elementId).toDispatch());
        sapMessenger.obj.setProperty("selected", option);
    }

    /**
     * Checks if an element is existing in actual context
     * @param id GUi ID element
     */
    public boolean isExisting(String id){
        return sapMessenger.isExisting(id).equals(ErrorCodes.OK);
    }
}
