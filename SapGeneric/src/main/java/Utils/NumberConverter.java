package Utils;


import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class NumberConverter {

    public <T> String getString(T value){
        if (value instanceof Integer){
            return String.valueOf(value);
        } else if (value instanceof Float) {
            String strValue = String.format("%.2f", value);
            return strValue.replace(".",",");
        } else if (value instanceof  LocalDate) {
            return ((LocalDate) value).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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

    public LocalDate getDate(String value){
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


}
