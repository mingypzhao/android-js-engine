package com.jsengine;

import com.facebook.jni.annotations.DoNotStrip;

@DoNotStrip
public abstract class JSCallback {

    @DoNotStrip
    public abstract void invoke(Object object);
}
