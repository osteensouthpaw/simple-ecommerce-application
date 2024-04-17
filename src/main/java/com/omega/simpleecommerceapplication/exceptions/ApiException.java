package com.omega.simpleecommerceapplication.exceptions;

import java.time.LocalDateTime;

public record ApiException (
        String message,
        int statusCode,
        LocalDateTime dateTime,
        String path
){
}
