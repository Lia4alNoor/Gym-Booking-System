package javafxapplication1;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.sql.*;
import javafx.collections.FXCollections;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server1  implements Runnable {
   Socket csocket;
   private Lock lock= new ReentrantLock();
  Server1(Socket csocket) {
      this.csocket = csocket;
   }
public void run(){
         try {
             ObjectInputStream inS = new ObjectInputStream(csocket.getInputStream());
             ObjectOutputStream outS = new ObjectOutputStream(csocket.getOutputStream());
             ResultSet rs= null;  
             PreparedStatement ps = null;
             Staff stf= new Staff();
             SenderObj obj = new SenderObj();
            //  Login
             try {
                 Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gym","root","");  
                 Statement stmt= con.createStatement();
                      
                 
                 obj = (SenderObj) inS.readObject();//Retrives Function to be executed and an Object from Client
                 
                 switch (obj.getFX()) {
                     case "Login":
                         Login(obj, outS,con,stmt);
                         break;
                     case "gt_Focuses":
                         lock.lock();
                         Focus f1= (Focus) obj.getObj();
                         ArrayList PT_withFocus = FocusArr(rs, stmt, f1.getFocusName());
                         outS.writeObject(PT_withFocus);
                         System.out.println(PT_withFocus);
                         lock.unlock();
                         break;
                     case "checkAvail":
                         BookSlot_Update(obj, outS, con, stmt, "Book");
                         break;
                     case "Lst_Cl":
                          lock.lock();
                          ArrayList alistOFA =new ArrayList();
                          try{
                            Client c1= (Client) obj.getObj();
                            stmt=con.createStatement();//
                            rs=stmt.executeQuery("select * from Lst_Cl where ClID = \'"+ c1.getID()+ "\' ;");
                            System.out.println("Client id recieved for Listing = "+ c1.getID());
                            while(rs.next()) {
                                System.out.println("in looop ");
                                           c1.BKID.add(rs.getString(2));
                                           c1.Focus.add(rs.getString(3));
                                           c1.TrainerName.add(rs.getString(4));
                                           c1.Date.add(rs.getString(5));
                                           c1.srt_Time.add(rs.getString(6));
                                           c1.sop_Time.add(rs.getString(7));
                                           c1.ClientExist=true;
                                              }
                            System.out.println("serber = bkid lis "+c1.BKID);
                            alistOFA = new ArrayList ( FXCollections.observableArrayList(c1.ClientExist,c1. BKID,c1. Focus,c1.TrainerName,c1.Date,c1.srt_Time,c1.sop_Time));
                            System.out.println("bkid lis "+c1.BKID);
                          }
                          finally{
                            outS.writeObject(alistOFA);
                            lock.unlock();
                          }
                         break;  
                     case "Lst_Date":
                         Lst_Date(obj, outS,con,stmt);
                         break;
                     case "Lst_PT":
                        PTrainer pt1 = null;
                            try {
                                lock.lock();
                                 pt1 = (PTrainer) obj.getObj();
                                  stmt=con.createStatement();
                                  rs=stmt.executeQuery("select * from Lst_PT WHERE PTID =   \'"+ pt1.getID()+ "\' ;");
                                 System.out.println("PT id recieved for Listing = "+ pt1.getID());

                                 while(rs.next()) {
                                  pt1.BKID.add(rs.getString(2));
                                  pt1.Focus.add(rs.getString(3));
                                  pt1.ClientName.add(rs.getString(4));
                                  pt1.Date.add(rs.getString(5));
                                  pt1.srt_Time.add(rs.getString(6));
                                  pt1.sop_Time.add(rs.getString(7));
                                  pt1.PTExist =true;
                                 }
                                if(pt1.BKID.isEmpty())
                                    pt1.PTExist=false;
                                 pt1.PTExist=true;
                                ArrayList alistOFPT_Lst = new ArrayList ( FXCollections.observableArrayList(pt1.PTExist,pt1. BKID,pt1. Focus,pt1.ClientName,pt1.Date,pt1.srt_Time,pt1.sop_Time));
                                outS.writeObject(alistOFPT_Lst);
                                lock.unlock();
                            } catch (IOException ex) {
                                Logger.getLogger(Server1.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(Server1.class.getName()).log(Level.SEVERE, null, ex);
                                 pt1.PTExist=false;
                            }
                         break;
                     case "Update":
                         BookSlot_Update(obj, outS, con, stmt, "Update");
                         break;
                    case "DeleteBK":
                        lock.lock();
                       
                        Booking b1 = (Booking) obj.getObj();
                        String sql = "DELETE FROM Booking where BookID = ?";
                        System.out.println("delete booking "+b1.getBkID());
                         PreparedStatement ps2 = con.prepareStatement(sql);
                         ps2.setString(1,b1.getBkID());
                         int del = ps2.executeUpdate();
                         System.out.println("Number of deleted records: " + del);
                          ps2.close();
                        
                         if(del == 0) {
                             b1.setBooking_Deleted(false);
                         }else                        
                         b1.setBooking_Deleted(true);
                         outS.writeObject(b1);
                         lock.unlock();
                         break;     
                     case "Find Booking":
                        lock.lock();
                        Booking b2 = (Booking)obj.getObj();
//                        Client c3= new Client();
//                        Focus fUp = new Focus();
//                        PTrainer ptUp = new PTrainer();
                        stmt=con.createStatement();
                        try{
                        ResultSet rs1=stmt.executeQuery("select * from Booking where BookID = \'"+ b2.getBkID()+ "\' ;");    
                        while(rs1.next())
                        {
                        b2.setBooking_Found(true);
                        System.out.println("Booking Found for BKID "+ b2.getBkID());
                        b2.setBkID(rs1.getString(1));   
                        
                        b2.setClientID(rs1.getString(3)); //DELDIS
                        b2.setPTID(rs1.getString(5));
                            System.out.println("PT ID "+ b2.getPTID());
                        b2.setFocusID(rs1.getString(4));
                        b2.setDate(rs1.getString(6));
                        b2.setSrt_Time(rs1.getInt(7));
                        b2.setSop_Time(rs1.getInt(8));
                        }
                        System.out.println("B2.GETBookings = "+b2.getBooking_Found());
                        if(b2.getBooking_Found()){
                                rs1=stmt.executeQuery("select FocusName  from Focus  where FocusID = \'"+ b2.getFocusID()+ "\' ;");     
                                  while(rs1.next()){
                                   System.out.println(" Focus Name "+b2.getFocusID());
                                   b2.setFocusName(rs1.getString(1));
                                  }
                        System.out.println("Sending to Update in Client  : "+ b2.getFocusName());       
                        b2.setBooking_Found(true);
                        }                        
                        }catch(Exception e){
                         b2.setERRORmessage("Booking Not Found.");
                        } 
                          outS.writeObject(b2);
                          System.out.println("BK ID "+b2.getBkID()+" "+b2.getFocusName()+" Client ID"+b2.getClientID());
                          lock.unlock();
                          break;
                 }
                 
             } catch (IOException ex) {
                System.out.println("Exception : "+ex);
              }catch (SQLException ex) {
                System.out.println("Exception : "+ex);
             } catch (ClassNotFoundException ex) {
                System.out.println("Exception : "+ex);
             } catch(NullPointerException nux ){
                System.out.println("Exception : "+nux);
             }
         } catch (IOException ex) {
             System.out.println("Exception "+ ex);
         }
     }
   
   public static void main(String args[]) throws Exception { 
      ServerSocket ssock = new ServerSocket(9999);
      System.out.println("Server Ready! 1");
      System.out.println("Listening");
      while (true) {
         Socket sock = ssock.accept();
         System.out.println("Connected");
         new Thread(new Server1(sock)).start();
      }
   }
   
   
    public void Login(SenderObj obj,ObjectOutputStream outS,Connection con ,Statement stmt){
       try {
           lock.lock();
           try {
                 Staff stf =(Staff) obj.getObj();
                 stmt=con.createStatement();
                 
                 
                 
                 ResultSet rs=stmt.executeQuery("select * from Staff ");
                          while(rs.next()) {
                             if((rs.getString(1).equals(stf.getIDEnt())) && rs.getString(5).equals(stf.getPassEnt())){
                                stf.setPassIDName(rs.getString(5),rs.getString(1),rs.getString(2));  //Set Pass ,ID and Name
                             }
                            }
                System.out.println("ID = "+stf.getID());
                outS.writeObject(stf);
           } catch (SQLException ex) {
               Logger.getLogger(Server1.class.getName()).log(Level.SEVERE, null, ex);
           } finally {
               lock.unlock();
           }
       } catch (IOException ex) {
           Logger.getLogger(Server1.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
      public static ArrayList FocusArr (ResultSet rs ,Statement stmt,String focusname){ 
                   ArrayList PT_withFocus = new ArrayList();
        try {
            rs=stmt.executeQuery("Select PTFocus.PTID,PT.PTName from PT,Focus,PTFocus where Focus.FocusID = PTFocus.FocusID and PT.PTID = PTFocus.PTID AND FocusName = \'"+ focusname+ "\' ;");
            while(rs.next()){
                PT_withFocus.add(rs.getString(1));
                PT_withFocus.add(rs.getString(2));
                
            }
        } catch (SQLException ex) {
            
        }
        return PT_withFocus;
                  }
      
      
      public void BookSlot_Update(SenderObj obj,ObjectOutputStream outS,Connection con ,Statement stmt,String FX){
     lock.lock();

           String str_NewBKID ="";
            Booking b1= new  Booking();
          try {
                ResultSet rs2 = stmt.executeQuery("SELECT * FROM Booking ORDER BY BookID asc;");
                
                b1= (Booking) obj.getObj();
                
                if(FX == "Book"){
                 str_NewBKID = getNewBkid(stmt);
                int count =0;
                        rs2 = stmt.executeQuery("SELECT * FROM Booking ORDER BY BookID asc;");
                        while(rs2.next())
                        {  
                            count  = rs2.getInt(1)+1;
                        } 
                }
                stmt=con.createStatement();//
                System.out.println("check Avail - SERVER ");
                 System.out.println("cl id = "+b1.getClientID());
                                    System.out.println("focus = "+b1.getFocusName());
                                    System.out.println("pt id  = "+b1.getPTID());
                                    System.out.println("Date = "+b1.getDate());
                                    System.out.println("srt  = "+b1.Srt_Time);
                                    System.out.println("sop  = "+b1.Sop_Time);
                 ResultSet rs =stmt.executeQuery("SELECT * FROM Booking;");
                        b1.setSlot_Available(true) ;     



                 while(rs.next()){
                    System.out.println("pt = "+rs.getString(5));
                  if(rs.getString(5).equals(b1.getPTID())){    
                      System.out.println("Same PT");
                      if(!Accept_Reject(b1.getDate(),rs.getString(6),b1.getSrt_Time(),b1.getSop_Time(), rs.getInt(7),rs.getInt(8))){
                        System.out.println("No Booking Possible ! ");
                       b1.setSlot_Available(false);
                        break;
                    }
                  }      
                }
              System.out.println("Slot available Status = "+b1.Slot_Available);
            if(b1.getSlot_Available()){
                        rs2 = stmt.executeQuery("Select * FROM Focus Where FocusName =  \'"+ b1.getFocusName().toString()+ "\' ;");
                        while(rs2.next())
                        {  
                            b1.setFocusID( rs2.getString(1));
                        } 
                        rs2 = stmt.executeQuery("Select * FROM Client Where ClID =  \'"+ b1.getClientID()+ "\' ;");
                         while(rs2.next())
                        {  
                            b1.setClientName(rs2.getString(1));
                        } 
                       PreparedStatement ps = null;

                    if(FX=="Book"){
                                try{
                                               b1.setAcptBook(true);
                                               System.out.println("Slot Available");
                                               System.out.println(" Booking ID ="+str_NewBKID);
                                               String sql = "INSERT INTO Booking VALUES (?,?,?,?,   ?,  ?,?,?)";
                                                  ps = con.prepareStatement(sql);
                                                  ps.setString(1,str_NewBKID);
                                                  ps.setString(2,b1.getStaffID());   //STAFF ID 
                                                  ps.setString(3,b1.getClientID());
                                                  ps.setString(4,b1.getFocusID());
                                                  ps.setString(5,b1.getPTID());
                                                  ps.setString(6,b1.getDate());
                                                  ps.setInt(7, b1.getSrt_Time());
                                                  ps.setInt(8, b1.getSop_Time());

                                                  b1.setBkID(str_NewBKID);
                                                   int s =  ps.executeUpdate();
                                                   System.out.println("Booking Made !");
                                                    ps.close();
                                    }catch (SQLIntegrityConstraintViolationException ES ){ 
                                          b1.setAcptBook(false);
                                          System.out.println("ES - "+ES);
                                          b1.setERRORmessage("Booking cannot be made for the Following , Enter Valid Client ID and Personal Trainer ID.");
                                      }catch (Exception SQLEX){
                                                          b1.setAcptBook(false);
                                          b1.setERRORmessage("Enter Valid IDs.");
                                      }
                    }else if(FX == "Update")

                    {
                        if(b1.Slot_Available){
                                      String sql =   "UPDATE Booking SET StaffID = ?, ClID = ?,FocusID = ? ,PTID = ? , BKdate = ? ,str_Time = ? ,sop_Time = ? WHERE BookID = ? ";
                                      ps =con.prepareStatement( sql );  
                                      ps.setString(1,b1.getStaffID_str());
                                      System.out.println(b1.getStaffID_str()+b1.getClientID()+b1.getFocusID()+" "+b1.getDate()+" "+b1.getSrt_Time()+b1.getSop_Time()+"book id = "+b1.getBkID());
                                      ps.setString(2,b1.getClientID());
                                      ps.setString(3,b1.getFocusID());
                                      ps.setString(4,b1.getPTID());
                                      ps.setString(5,b1.getDate());
                                      ps.setInt(6,b1.getSrt_Time());
                                      ps.setInt(7,b1.getSop_Time());
                                      ps.setString(8, b1.getBkID());
                                      int i = ps.executeUpdate();
                                      System.out.println(i+" records inserted"); 
                                      ps.close();
                                      b1.setBooking_Updates(true);
                                     }
                    }
               }else{
                      b1.setAcptBook(false);
                      b1.setSlot_Available(false);
                      b1.setERRORmessage("Slot Unavailable ");
                     } 
                 outS.writeObject(b1);
                   } catch (SQLException ex) {
                       b1.setAcptBook(false);
                   } catch (IOException ex) {
                       Logger.getLogger(Server1.class.getName()).log(Level.SEVERE, null, ex);
                   }catch (Exception e){
                       
                   }
       finally {
            lock.unlock();
            }
      }
      
      
      
      
      
      
      
      
      
      
      
      
      
      //Methods
   public void Lst_Client(SenderObj obj , ObjectOutputStream outS,Connection con ,Statement stmt){
      ArrayList alistOFA = null;
       Client c1 = null;
       try {
           lock.lock();
           c1= (Client) obj.getObj();
            stmt=con.createStatement();//
           ResultSet rs=stmt.executeQuery("select * from Lst_Cl where ClID = \'"+ c1.getID()+ "\' ;");
           System.out.println("Client id recieved for Listing = "+ c1.getID());
           
           alistOFA =new ArrayList();
            
           while(rs.next()) {
                          c1.BKID.add(rs.getString(2));
                          c1.Focus.add(rs.getString(3));
                          c1.TrainerName.add(rs.getString(4));
                          c1.Date.add(rs.getString(5));
                          c1.srt_Time.add(rs.getString(6));
                          c1.sop_Time.add(rs.getString(7));
                          c1.ClientExist=true;
                             }
           alistOFA = new ArrayList ( FXCollections.observableArrayList(c1.ClientExist,c1. BKID,c1. Focus,c1.TrainerName,c1.Date,c1.srt_Time,c1.sop_Time));
            outS.writeObject(alistOFA);
       } catch (SQLException ex) {
           
       } catch (IOException ex) {
           Logger.getLogger(Server1.class.getName()).log(Level.SEVERE, null, ex);
       }catch(Exception e){
           
       }finally{
          
           lock.unlock();
       }
      }
   public void Lst_PT(SenderObj obj,ObjectOutputStream outS,Connection con ,Statement stmt){
       PTrainer pt1 = null;
       try {
           lock.lock();
            pt1 = (PTrainer) obj.getObj();
            stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Lst_PT WHERE PTID =   \'"+ pt1.getID()+ "\' ;");
            System.out.println("PT id recieved for Listing = "+ pt1.getID());
            while(rs.next()) {
             pt1.BKID.add(rs.getString(2));
             pt1.Focus.add(rs.getString(3));
             pt1.ClientName.add(rs.getString(4));
             pt1.Date.add(rs.getString(5));
             pt1.srt_Time.add(rs.getString(6));
             pt1.sop_Time.add(rs.getString(7));
             pt1.PTExist =true;
            }
           if(pt1.BKID.isEmpty())
               pt1.PTExist=false;
            pt1.PTExist=true;
           ArrayList alistOFPT_Lst = new ArrayList ( FXCollections.observableArrayList(pt1.PTExist,pt1. BKID,pt1. Focus,pt1.ClientName,pt1.Date,pt1.srt_Time,pt1.sop_Time));
           outS.writeObject(alistOFPT_Lst);
           lock.unlock();
       } catch (IOException ex) {
           Logger.getLogger(Server1.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(Server1.class.getName()).log(Level.SEVERE, null, ex);
           pt1.PTExist=false;
       }
   }
   public void Lst_Date(SenderObj obj,ObjectOutputStream outS,Connection con ,Statement stmt){
       try {
           lock.lock();
           String s = (String) obj.getObj();
           Boolean Date_Lstbool = false;
           ArrayList BKID = new ArrayList();
           ArrayList ClientName = new ArrayList();
           ArrayList Focus = new ArrayList();
           ArrayList TrainerName = new ArrayList();
           ArrayList srt_Time = new ArrayList();
           ArrayList sop_Time = new ArrayList();
           stmt=con.createStatement();
           System.out.println("Date Selected = "+s);
           ResultSet rs=stmt.executeQuery("select * from Lst_Date WHERE BKDate =  \'"+ s+ "\' ;");
           while(rs.next()) {
            BKID.add(rs.getString(2));
            ClientName.add(rs.getString(3));  // HELP!
            Focus.add(rs.getString(4));
            TrainerName.add(rs.getString(5));
            srt_Time.add(rs.getString(6));
            sop_Time.add(rs.getString(7));
            Date_Lstbool = true;
           }
           System.out.println(" Book ID S =  "+BKID);
           ArrayList alist2 = new ArrayList ( FXCollections.observableArrayList(Date_Lstbool,BKID,ClientName,Focus,TrainerName,srt_Time,sop_Time));
           outS.writeObject(alist2);
          
       } catch (IOException ex) {
           Logger.getLogger(Server1.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(Server1.class.getName()).log(Level.SEVERE, null, ex);
       }finally{
            lock.unlock();
       }
   }
   public static String getNewBkid(Statement stmt) throws SQLException{                 //Function to Generate a Booking ID And pad it to fit 4 digits 
    ResultSet rs2 = stmt.executeQuery("Select * FROM Booking ORDER BY BookID ASC ;");
    int count = 0;
            while(rs2.next())
            {  
                count  = rs2.getInt(1);
                System.out.println("Count = "+ count);
            }      
         
        String BkID = String.valueOf(count+1);
        
        
        for(int i = BkID.length() ; i < 4 ; i++ ){
           
           BkID = ("0"+BkID).toString();
        }
       return BkID;
    }
   
   //A method to check whether any slot clashes with the slot required. Used inside a while loop with if statements according to requiremnet for going through a larger list 
   public static boolean Accept_Reject(String date,String date2 ,int srt_Ent , int sop_Ent,int srt , int sop ){
        boolean b = true;
//    System.out.println("c1   I want date from  "+srt_Ent +" to "+ sop_Ent+" on  "+ date);
//    System.out.println(" date BOOKED from  "+srt +" to "+ sop+" on "+date2);
     
    if(date2.equals(date)){
        //Same Date
                if(sop_Ent == srt){
                    return true;
                }
                if(srt_Ent == sop){
                    return true;
                }
                if(srt_Ent <= srt && sop<= sop_Ent)
                    return false;
                for(int i =  srt; i<=sop; i++){ //Checks whether Booking in between 
                                 System.out.println("i = "+i);
                                 if (i == srt_Ent){    
                                     return false;
                                 }
                                 if (i == sop_Ent){
                                     return false;
                             }
                }
    }else{
     //Different Date 
        return  true;
    }
    
        return b;
    }
}

 
