// PTrainer object to send PT information
package javafxapplication1;

import java.io.Serializable;
import java.util.ArrayList;

public class PTrainer  implements Serializable{

    String ID;
    String Name;
    
    
    public PTrainer(String ID , String Name) {
   
     this.ID = ID;
     this.Name = Name;
    }
    public PTrainer(String ID ) {
   
     this.ID = ID;
    } 
 ArrayList BKID = new ArrayList();
   ArrayList Focus = new ArrayList();
   ArrayList ClientName = new ArrayList();
   ArrayList Date = new ArrayList();
   ArrayList srt_Time = new ArrayList();
   ArrayList sop_Time = new ArrayList();
   
    public String getID() {
        return ID;
    }
    

     boolean PTExist = true;

    public PTrainer() {
    }
     

    public void setPTExist(boolean PTExist) {
        this.PTExist = PTExist;
    }

    public boolean isPTExist() {
        return PTExist;
    }
}
