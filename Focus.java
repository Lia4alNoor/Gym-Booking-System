/* The class is used to send Focus Details
 */
package javafxapplication1;

import java.io.Serializable;
import java.util.ArrayList;


public class Focus implements Serializable {
    
    String FocusID;
    String FocusName;
    ArrayList Focus_Trainer ;
    public Focus() {
    }

    public ArrayList getFocus_Trainer() {
        return Focus_Trainer;
    }

    public void setFocus_Trainer(ArrayList Focus_Trainer) {
        this.Focus_Trainer = Focus_Trainer;
    }

    public void setFocusID(String FocusID) {
        this.FocusID = FocusID;
    }

    public Focus(String FocusName) {
        this.FocusName = FocusName;
    }

    public String getFocusName() {
        return FocusName;
    }

    public void setFocusName(String FocusName) {
        this.FocusName = FocusName;
    }

    public String getFocusID() {
        return FocusID;
    }

   
}
