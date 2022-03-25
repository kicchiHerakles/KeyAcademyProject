package com.theherakles.keyacademy.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AutomationException extends Exception {
    public AutomationException(String message) {
        super(message);
    }
}
