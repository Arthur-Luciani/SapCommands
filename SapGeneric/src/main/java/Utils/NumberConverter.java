package Utils;

import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

@Service
public class NumberConverter {

    public <T> String getString(T value){
        if (value instanceof Integer){
            return String.valueOf(value);
        } else if (value instanceof Float) {
            String strValue = String.valueOf(value);
            return strValue.replace(".",",");
        }
        return null;
    }

    public int getInt(String value){
        return Integer.parseInt(value);
    }

    public float getFloat(String value){
        value = value.replace(".", "");
        value = value.replace(",", ".");
        String floatValue = String.format("%.2f", Float.valueOf(value));
        floatValue = floatValue.replace(",", ".");
        return Float.parseFloat(floatValue);
    }

}
