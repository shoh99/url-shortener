package com.example.url_shortener.services;

import org.springframework.stereotype.Component;

@Component
public class Base62Converter {

    private static final String BASE62_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String encode(long number) {
        if (number == 0) {
            return String.valueOf(BASE62_CHARS.charAt(0));
        }

        StringBuilder sb = new StringBuilder();

        while(number > 0 ) {
            int reminder = (int) (number % 62);
            sb.append(BASE62_CHARS.charAt(reminder));
            number /= 62;
        }

        return sb.reverse().toString();
    }
}
