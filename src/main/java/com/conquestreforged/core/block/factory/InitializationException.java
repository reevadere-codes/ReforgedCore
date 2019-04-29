package com.conquestreforged.core.block.factory;

public class InitializationException extends RuntimeException {
    public InitializationException(String message) {
        super(message);
    }

    public InitializationException(Throwable origin) {
        super(origin);
    }
}
