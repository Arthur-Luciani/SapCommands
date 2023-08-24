package SapGeneric;

import Utils.NumberConverter;
import com.jacob.activeX.ActiveXComponent;
import org.springframework.beans.factory.annotation.Autowired;

public class Basic extends Standart{


    /**
     * @param elementId ID of element
     * @return String value from element
     */
    public String getText(String elementId){
        checkElement.isExisting(elementId);
        obj = new ActiveXComponent(session.invoke("FindById", elementId).toDispatch());
        return obj.getPropertyAsString("text");// Test here
    }

    /**
     * Set text (String) from GUI label
     * @param label GUI element
     * @param value Text value
     */
    public void setText(String label, String value){
        checkElement.isExisting(value);
        obj = new ActiveXComponent(session.invoke("FindById", label).toDispatch());
        obj.setProperty("text", value);
    }

    public float getFloatText(String elementId){
        return numberConverter.getFloat(getText(elementId));
    }

    public void setFloatText(String label, Float value){
        setText(label, numberConverter.getString(value));
    }

    public int getIntText(String elementId){
        return numberConverter.getInt(getText(elementId));
    }

    public void setIntText(String label, int value){
        setText(label, numberConverter.getString(value));
    }

}
