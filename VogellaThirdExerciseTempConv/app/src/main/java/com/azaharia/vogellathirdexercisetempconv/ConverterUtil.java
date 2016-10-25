package com.azaharia.vogellathirdexercisetempconv;

/**
 * Created by AZaharia on 7/14/2016.
 */
public class ConverterUtil {
    public static float convertFahrenheitToCelsius(float fahrenheit){
        return ((fahrenheit - 32) * 5 / 9);
    }

    public static float convertCelsiusToFahrenheit(float celsius){
        return ((celsius * 9) / 5) + 32;
    }
    public static float multiply(float f){
        return f*2;
    }
}
