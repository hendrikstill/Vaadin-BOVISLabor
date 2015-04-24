package com.bovis;

import javax.enterprise.inject.Default;

@Default
public class SimpleGreetingImpl implements Greeting {

    @Override
    public String getText() {
        return "Hello, World!";
    }
}