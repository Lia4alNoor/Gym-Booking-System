/*
The Object is used send 2 objects - A string defining what function is to be executed and An object transferring data
 */
package javafxapplication1;

import java.io.Serializable;

public class SenderObj implements Serializable {
    Object obj;
    String FX;

    public SenderObj() {
    }

    public SenderObj(Object obj, String FX) {
        this.obj = obj;
        this.FX = FX;
    }

    public Object getObj() {
        return obj;
    }

    public String getFX() {
        return FX;
    }
    
}
