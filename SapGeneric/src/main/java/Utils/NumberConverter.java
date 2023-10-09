package Utils;


import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class NumberConverter {

    public <T> String getString(T value){
        if (value instanceof Integer){
            return String.valueOf(value);
        } else if (value instanceof Float) {
            String strValue = String.format("%.2f", value);
            return strValue.replace(".",",");
        } else if (value instanceof  LocalDate) {
            return ((LocalDate) value).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } else if (value instanceof  Long) {
            return String.valueOf(value);
        } else if (value instanceof BigDecimal) {
            ((BigDecimal) value).setScale(2);
            return value.toString();
        }
        return null;
    }


    public int getInt(String value){
        return  (int) getFloat(value);
    }

    public float getFloat(String value){
        value = value.replace(" ", "");
        value = value.replace(".", "");
        value = value.replace(",", ".");
        String floatValue = String.format("%.2f", Float.valueOf(value));
        floatValue = floatValue.replace(",", ".");
        return Float.parseFloat(floatValue);
    }

    public long getLong(String value){
        value = value.replace(" ", "");
        value = value.replace(".", "");
        return Long.parseLong(value);
    }

    public String get3Float(float value){
        String str = String.format("%.3f", value);
        return str.replace(".",",");
    }

    public LocalDate getDate(String value){
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


}
