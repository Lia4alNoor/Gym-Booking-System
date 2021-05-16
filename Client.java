/*
 A client Object Class with capabilities to expand the application use according to requirement . 
 */
package javafxapplication1;

import java.io.Serializable;
import java.util.ArrayList;

public class Client implements Serializable{
  
    
    String ID;
    String Name;  
    String desc;
    int wt,ht;
    String dob,email,phone;
    Boolean ClientFound;

 
 public Client(String ID, String Name) {
        this.ID = ID;
        this.Name = Name;
    }
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    

    public Client(String ID) {
    this.ID = ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }
    
    


    

   


    
   public ArrayList BKID = new ArrayList();
   public ArrayList Focus = new ArrayList();
   public ArrayList TrainerName = new ArrayList();
   public ArrayList Date = new ArrayList();
   public ArrayList srt_Time = new ArrayList();
   public ArrayList sop_Time = new ArrayList();


    public ArrayList getBKID() {
        return BKID;
    }

    public ArrayList getDate() {
        return Date;
    }

    public ArrayList getTrainerName() {
        return TrainerName;
    }

    public ArrayList getSop_Time() {
        return sop_Time;
    }

    
   

    public ArrayList getFocus() {
        return Focus;
    }
    
   
     boolean ClientExist = true;

    public void setClientExist(boolean ClientExist) {
        this.ClientExist = ClientExist;
    }

    public boolean isClientExist() {
        return ClientExist;
    }
     
   
}
