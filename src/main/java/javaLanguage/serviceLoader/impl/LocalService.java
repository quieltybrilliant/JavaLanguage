package javaLanguage.serviceLoader.impl;

import javaLanguage.serviceLoader.IService;

public  class LocalService  implements IService {
    @Override
    public String sayHello() {
        return "Hello LocalService";
    }
    @Override
    public String getScheme() {
        return "local";
    }
}