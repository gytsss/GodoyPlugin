package com.godoy.mylibrary;

public class GodoyLogger {

    static final String LOGTAG = "GodLOG";

    static GodoyLogger _instance = null;
    public static GodoyLogger getInstance()
    {
        if(_instance == null)
            _instance = new GodoyLogger();
        return _instance;
    }

    public String getLogtag()
    {
        return LOGTAG;
    }
}
