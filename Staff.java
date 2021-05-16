/*
 Staff details send using this Class Object
 */
package javafxapplication1;

import java.io.Serializable;


public class Staff implements Serializable{

   String pass;
   String passEnt ;
   String IDEnt;
   String Name;
   String ID;
final   String InCorrectPassword = "The password you have enetered is incorrect";
      public Staff(String IDEnt,String passEnt) {
        this.passEnt = passEnt;
        this.IDEnt = IDEnt;
    }

    public Staff(){
    }

    public String getName() {
        return Name;
    }

    public String getID() {
        return ID;
    }
    

    public String getPass() {
        return pass;
    }

    public String getPassEnt() {
        return passEnt;
    }

    public void setPassIDName(String pass,String ID,String Name) {
        this.Name=Name;
        this.pass = pass;
        this.ID = ID;
    }

    public void setPassEnt(String passEnt) {
        this.passEnt = passEnt;
    }

  
    public String getIDEnt() {
        return IDEnt;
    }

    public void setIDEnt(String IDEnt) {
        this.IDEnt = IDEnt;
    }
    
 
   public Boolean Slogin(){
             
 if(getPassEnt().equals(getPass()) && getIDEnt().equals(getID())){    
          System.out.println("let login both password and ID ARE RIGHT");
     return true;
   }
 if (getPassEnt().equals(getPass())){
            System.out.println("Password is wrong");
 } 
 if (getIDEnt().equals(getID())){
            System.out.println("ID is wrong");
 } 
          return false;
}
}