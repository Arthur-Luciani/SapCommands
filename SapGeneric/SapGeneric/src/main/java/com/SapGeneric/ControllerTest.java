package com.SapGeneric;

import com.SapGeneric.Util.NumberConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControllerTest {

    @Autowired
    NumberConverter numberConverter;

}
