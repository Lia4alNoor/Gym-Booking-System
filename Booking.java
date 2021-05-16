package javafxapplication1;

import java.io.Serializable;
import java.util.concurrent.locks.*;

class Booking implements Serializable{
   String date ;
   String BkID;
   Boolean AcptBook = false;
   Boolean Booking_Deleted = false;
   Boolean Booking_Updated = false;
   Boolean Booking_Found = false;
   Boolean Slot_Available = true;
   String ERRORmessage = null;
   int Srt_Time,Sop_Time; 
   public Lock BookingLock;
   String ClientName,ClientID;
   String FocusName,FocusID;
   String StaffID,StaffName;
   String PTName,PTID;
   
   
    String StaffID_str = null;
    public Booking(String BkID) {
    this.BkID=BkID;
    }

   Booking() {
    }


    public String getPTName() {
        return PTName;
    }

    public void setPTName(String PTName) {
        this.PTName = PTName;
    }

    public void setPTID(String PTID) {
        this.PTID = PTID;
    }

    public String getPTID() {
        return PTID;
    }
    
    
        
     public Booking(String date, String BkID, int Srt_Time, int Sop_Time, String ClientName, String ClientID, String FocusName, String FocusID, String StaffID, String StaffName, String PTName, String PTID) {
        this.date = date;
        this.BkID = BkID;
        this.Srt_Time = Srt_Time;
        this.Sop_Time = Sop_Time;
        this.ClientName = ClientName;
        this.ClientID = ClientID;
        this.FocusName = FocusName;
        this.FocusID = FocusID;
        this.StaffID = StaffID;
        this.StaffName = StaffName;
        this.PTName = PTName;
        this.PTID = PTID;
    }

    public Booking(String date, String BkID, int Srt_Time, int Sop_Time, Lock BookingLock, String ClientName, String ClientID, String FocusName, String FocusID, String StaffID, String StaffName, String PTName, String PTID) {
        this.date = date;
        this.BkID = BkID;
        this.Srt_Time = Srt_Time;
        this.Sop_Time = Sop_Time;
        this.BookingLock = BookingLock;
        this.ClientName = ClientName;
        this.ClientID = ClientID;
        this.FocusName = FocusName;
        this.FocusID = FocusID;
        this.StaffID = StaffID;
        this.StaffName = StaffName;
        this.PTName = PTName;
        this.PTID = PTID;
    }

  
    public Booking(String date, String BkID, int Srt_Time, int Sop_Time, String ClientName, String ClientID, String FocusName, String FocusID, String StaffID, String StaffName) {
        this.date = date;
        this.BkID = BkID;
        this.Srt_Time = Srt_Time;
        this.Sop_Time = Sop_Time;
        this.ClientName = ClientName;
        this.ClientID = ClientID;
        this.FocusName = FocusName;
        this.FocusID = FocusID;
        this.StaffID = StaffID;
        this.StaffName = StaffName;
    }

    public void setStaffName(String StaffName) {
        this.StaffName = StaffName;
    }

    public void setStaffID(String StaffID) {
        this.StaffID = StaffID;
    }

    public void setFocusName(String FocusName) {
        this.FocusName = FocusName;
    }

    public void setFocusID(String FocusID) {
        this.FocusID = FocusID;
    }

    public void setClientName(String ClientName) {
        this.ClientName = ClientName;
    }

    public void setClientID(String ClientID) {
        this.ClientID = ClientID;
    }

    public String getStaffName() {
        return StaffName;
    }

    public String getStaffID() {
        return StaffID;
    }

    public String getFocusName() {
        return FocusName;
    }

    public String getFocusID() {
        return FocusID;
    }

    public String getClientName() {
        return ClientName;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setSrt_Time(int Srt_Time) {
        this.Srt_Time = Srt_Time;
    }

    public void setSop_Time(int Sop_Time) {
        this.Sop_Time = Sop_Time;
    }
    

    public void setDate(String date) {
        this.date = date;
    }

    public void setStaffID_str(String StaffID_str) {
        this.StaffID_str = StaffID_str;
    }

    public void setSlot_Available(Boolean Slot_Available) {
        this.Slot_Available = Slot_Available;
    }

    public Boolean getSlot_Available() {
        return Slot_Available;
    }

    public String getStaffID_str() {
        return StaffID_str;
    }

    public void setERRORmessage(String ERRORmessage) {
        this.ERRORmessage = ERRORmessage;
    }

    public String getERRORmessage() {
        return ERRORmessage;
    }

   
    public void setBooking_Found(Boolean Booking_Found) {
        this.Booking_Found = Booking_Found;
    }

    public Boolean getBooking_Found() {
        return Booking_Found;
    }

   
    public Boolean getBooking_Updates() {
        return Booking_Updated;
    }

    public void setBooking_Updates(Boolean Booking_Updates) {
        this.Booking_Updated = Booking_Updates;
    }
    

    public Boolean getAcptBook() {
        return AcptBook;
    }

    public void setAcptBook(Boolean AcptBook) {
        this.AcptBook = AcptBook;
    }

    public Boolean getBooking_Deleted() {
        return Booking_Deleted;
    }

    public void setBooking_Deleted(Boolean Booking_Deleted) {
        this.Booking_Deleted = Booking_Deleted;
    }

    public int getSrt_Time() {
        return Srt_Time;
    }

    public int getSop_Time() {
        return Sop_Time;
    }

    public void setBkID(String BkID) {
        this.BkID = BkID;
    }

    
    

    public String getBkID() {
        return BkID;
    }
  
    public String getDate() {
        return date;
    }
   
}

