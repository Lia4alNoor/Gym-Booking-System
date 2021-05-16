/*
The Application for connecting to the server using TCP Socket connection , used by the Staff at A- GYM to Book Personal Trainers , View Bookings and Update Bookings from the Sql Database using a 
Client- Server, Server-Database server model. 
*/

package javafxapplication1;


import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafxapplication1.Booking;
import javafxapplication1.Client;
import javafxapplication1.Focus;



public class A4 extends Application {
        Button btSignIn = new Button("Sign In");
        int port = 9999;
        String staffn = "";
          VBox vhb = new VBox();

        Stage window ;
    Scene scene1_SignIn, scene2_ChFx,scene3_Updateb,scene3_BookT,scene3_List,scene3_ListPT,scene3_ListDate,sceneListBy;
    
          Label LabEntSTID = new Label("Enter Staff ID");
          TextField TXEntStaffId = new TextField("ST01");
          Label LabEntStPass = new Label("Enter Staff Password ");
          PasswordField TXEntStPass = new PasswordField();
          Label lbgiveAceess = new Label();
          Button bscene1_Scene2 = new Button("Proceed");
          
          String StaffID = "";
          public void start(Stage primaryStage)  {     
            
                LabelFontCol(LabEntSTID,18); LabelFontCol(LabEntStPass, 18);
                btSignIn.setPrefSize(110,70);
                btSignIn.setAlignment(Pos.CENTER);
                btSignIn.setTranslateX(100);
                window = primaryStage;
                bscene1_Scene2.setVisible(false);
                
        btSignIn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
            public void handle(ActionEvent event) {
                               
                             try { 
                 Staff st1 = new Staff(TXEntStaffId.getText().trim(),TXEntStPass.getText());
                   
                 System.out.println("Staff ID = "+TXEntStaffId.getText().trim());
                   Socket  s1  = new Socket("localhost", port);
                   ObjectOutputStream clientOutputStream = new ObjectOutputStream(s1.getOutputStream());
                   SenderObj sendobj = new SenderObj(st1,"Login");
                   clientOutputStream.writeObject(sendobj);
                   ObjectInputStream clientInputStream = new   ObjectInputStream(s1.getInputStream());
                   st1 = (Staff)clientInputStream.readObject();
            if(st1.Slogin()){
                    staffn = st1.getName();
                    lbgiveAceess.setText("Acess Granted , Welcome "+staffn);
                    StaffID = st1.getID();
                    bscene1_Scene2.setVisible(true);
                    TXEntStaffId.setEditable(false);
                    btSignIn.setVisible(false);
            }else{
                     lbgiveAceess.setText("Acess Denied");
                    Alert a1 = new Alert(Alert.AlertType.ERROR, "ID / Password not found ", ButtonType.OK);
                   a1.showAndWait();
               }
            clientOutputStream.close();
            clientInputStream.close();
            s1.close();
                                  
                } catch (ConnectException ex) {
                     Alert a1 = new Alert(Alert.AlertType.ERROR, "ERROR : SERVER NOT FOUND ", ButtonType.OK);
                 a1.showAndWait();
                     
                }   catch(Exception E) {
                    System.out.println("Exception e ="+E);
                }
                            
            }
        }); 
  
 //Scene Change Buttons 
bscene1_Scene2.setOnAction(e-> window.setScene(scene2_ChFx));
Button back1 = new Button("Back ");
back1.setOnAction(e-> window.setScene(scene2_ChFx));
Button back2 = new Button("Back ");
back2.setOnAction(e-> window.setScene(scene2_ChFx));
Button back3 = new Button("Back ");
back3.setOnAction(e-> window.setScene(scene2_ChFx));
Button back4 = new Button("Back ");
back4.setOnAction(e-> window.setScene(scene2_ChFx));
Button back5 = new Button("Back ");
back5.setOnAction(e-> window.setScene(scene2_ChFx));
Button back6 = new Button("Back ");
back6.setOnAction(e-> window.setScene(scene2_ChFx));


//LAYOUT 1
VBox layout1 = new VBox(10);
 Text text = new Text("A Gym - ");
 Text tdum = new Text();
    TextFontCol(text, Color.ROYALBLUE, 50);
Button ResetLogin = new Button("Reset ");
layout1.getChildren().addAll(text,tdum,LabEntSTID,TXEntStaffId,LabEntStPass,TXEntStPass,btSignIn,bscene1_Scene2,lbgiveAceess,ResetLogin);
ResetLogin.setOnAction(e->{
                lbgiveAceess.setText("");
                bscene1_Scene2.setVisible(false);
                TXEntStaffId.setEditable(true);
                btSignIn.setVisible(true);
});
scene1_SignIn= new Scene(layout1, 500,500);
layout1.setPadding(new Insets(10, 50, 50, 50));
  

//LAYOUT 2- Select function to execute.

    Label lbWel = new Label("Welcome beloved of  AGYM ");
    LabelFontCol(lbWel, 20 );
    Button rbUpdate = new Button("Update Booking");
    Button rbBookTrainer = new Button("Book Trainer");
    Button rbListBk = new Button("List Bookings");
    Button SignOut = new Button("Sign Out");
    VBox layout2 = new VBox(20);
layout2.getChildren().addAll(lbWel,rbBookTrainer,rbListBk,rbUpdate,SignOut);
scene2_ChFx= new Scene(layout2, 500,500);
layout2.setPadding(new Insets(80, 80, 80, 50));

//LAYOUT 2.5 //LISTBY
    Label lblISTBY = new Label("List By ");
    Button btnBYPt = new Button("Personal Trainer");
    Button btn_ByClient = new Button("Client");
    Button btnByDate = new Button("Date");
   
    Label lbnull = new Label(" ");
    VBox layout2_ListBy = new VBox(20);
layout2_ListBy.getChildren().addAll(lblISTBY,btn_ByClient,btnBYPt,btnByDate,lbnull);
sceneListBy= new Scene(layout2_ListBy, 500,500);
layout2_ListBy.setPadding(new Insets(80, 80, 80, 50));



rbUpdate.setOnAction(e -> window.setScene(scene3_Updateb));
rbBookTrainer.setOnAction(e -> window.setScene(scene3_BookT));
rbListBk.setOnAction(e -> window.setScene(sceneListBy));
btn_ByClient.setOnAction(e-> window.setScene(scene3_List));
btnBYPt.setOnAction(e-> window.setScene(scene3_ListPT));
btnByDate.setOnAction(e-> window.setScene(scene3_ListDate));
SignOut.setOnAction(e->window.setScene(scene1_SignIn));
//LAYOUT 4 - BOOKTRAN 
    VBox lay4 = new VBox(10);
    ArrayList ArrUpBook = new ArrayList();
    Label FXLbBOOK = new Label("Book Personal Trainer");
    Label LabEntCLID = new Label("Enter Client ID");
    TextField TXEntCLId = new TextField("CL08");
    Label LabEntFocus = new Label("Select Focus ");
    ObservableList<String> FocusList =     FXCollections.observableArrayList(  "Flexibility","Posture Improvement","Muscle Gain","Yoga Basics","Weigth Loss");
    final ComboBox focusbox= new ComboBox(FocusList);
    focusbox.setValue("Flexibility");
    Label lbdate = new Label("Enter Date  ");
    Label lbError = new Label(); lbError.setStyle("-fx-color: red");
    DatePicker datePicker = new DatePicker();
    datePicker.setValue(LocalDate.now());
        
    datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(    LocalDate.now())        ) { setDisable(true);          } 
            }
        });
        datePicker.setEditable(true);
    
    
   
    Button btnSrchPT= new Button("Search Trainer");
    Button b2 = new Button("Book Slot");
    Button Reset = new Button("Reset");
    Label LabEntTime = new Label("Enter Time "); 
    Label lbBkID = new Label("Booking ID : ");
    TextField txBKid = new TextField();
    txBKid.setEditable(false);
   HBox hbBKID = new HBox(2,lbBkID,txBKid);
   Label lbTrainerAvailable = new Label("");
   Label PTidWanted = new Label("Enter PT ID :");
   TextField TXPTWanted = new TextField("AN");
   TXPTWanted.setPrefWidth(150);
   HBox hb2 = new HBox(PTidWanted,TXPTWanted);
   hb2.setSpacing(5);
   Label lbsetTime = new Label("Book Timings from :");
   Label whynot = new Label("-");
   Label lbGymTimings = new Label("The Gym operates from 5:00 am to 12:00 pm");
              LabelFontCol(lbGymTimings, 15);
   Label null_ = new Label();

   Label lbBookingStatus = new Label("");


        ObservableList<String> am_pm =   FXCollections.observableArrayList("05:00 am","06:00 am","07:00 am",
                "08:00 am","09:00 am","10:00 am","11:00 am","12:00 am","01:00 pm","02:00 pm","03:00 pm",
                "04:00 pm","05:00 pm","06:00 pm","07:00 pm","08:00 pm","09:00 pm","10:00 pm","11:00 pm","12:00 pm" );
                ComboBox ampm1 = new ComboBox(am_pm);
        ComboBox ampm2 = new ComboBox(am_pm);  
                ampm1.setValue("05:00 am");                ampm2.setValue("06:00 am");

        HBox hb0 = new HBox(ampm1,whynot,ampm2);
        hb0.setSpacing(10);
        
        Label lbBooked = new Label();
        HBox hbBooking = new HBox(lbBooked);
        hbBooking.setSpacing(10);
        Button SrchPTTime = new Button("Search Personal Trainer Availibility");
        HBox h1box = new HBox(datePicker);
        Label lbPTAvailable_time = new Label(" ");
        Button btnSearchClient = new Button("Search Client");
      
//Button - SearcH Personal Trainers       
btnSrchPT.setOnAction(action -> {
        try {
             Focus f1 = new Focus(focusbox.getValue().toString());
             System.out.println(f1.getFocusName());
             Socket  s1  = new Socket("localhost", port);
             SenderObj sendobj =  new SenderObj(f1,"gt_Focuses");
             System.out.println("Focus = "+ f1.getFocusName());
             ArrayList Tlist = null;
                 ObjectOutputStream clientOutputStream = new ObjectOutputStream(s1.getOutputStream());
                 clientOutputStream.writeObject(sendobj);
                 ObjectInputStream clientInputStream = new ObjectInputStream(s1.getInputStream());
                  Tlist = (ArrayList)clientInputStream.readObject();
                 clientOutputStream.close();
                 clientInputStream.close();
                 s1.close();
          String list = " ";
          
          for(int i = 0 ;i<Tlist.size();i=i+1){
              System.out.println(Tlist.get(i));
              list =list +  Tlist.get(i).toString();
              list= list +("  ");
              if(i%2 != 0 )
                       list=list+(" ,");
          }
          lbPTAvailable_time.setText("The Available Trainers are : "+list); // Give Trainer Name and PTID
       
      }  catch (ClassNotFoundException ex) {
            System.out.println("Ex : ---- "+ex);
         } catch (IOException ex) {
      }catch (Exception ex){System.out.println("EX :"+ex);
      }
});

//BOOK TRAINER
SrchPTTime.setOnAction(e->{
    lbBookingStatus.setText("");lbBooked.setText("");
    int flag = 0, srt_date =0,sop_date =0;
    String Errors = " ",e1= " ",e2= " ",e3= " "; 
            lbBookingStatus.setText("");
            NoErrorBox(TXEntCLId);NoErrorBox(TXPTWanted);
            hb0.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
            LocalDate value = datePicker.getValue();
            srt_date = Time(ampm1.getValue().toString());sop_date= Time(ampm2.getValue().toString());//Integer.parseInt(b);
            
            //ALL CONDITIONS.
            if(TXEntCLId.getText().trim().isEmpty()){
             flag--;
             e1 = (" Enter Client ID ");
             ErrorBox(TXEntCLId);
            }
            if(srt_date>=sop_date){
                e2 =". Enter Valid Time ";
                Alert a1 = new Alert(Alert.AlertType.ERROR, ". Please Enter Valid Time ", ButtonType.OK);
                a1.showAndWait();
                hb0.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, new Insets(-10, -10, -2, -5))));
                flag--;
            } 
            if(TXPTWanted.getText().trim().isEmpty())
            {    
                e3 = ( " Enter Valid Personal Trainer ID ");
                ErrorBox(TXPTWanted);
                flag= flag-3;
            }
            
            String str =value.format(DateTimeFormatter.ISO_DATE); //TRY CHANGE 
            System.out.println("Date :"+str);
            String g = e1+e2+e3 ;
            lbError.setText(g);
            System.out.println("Errors = "+g);
    if(flag >= 0){
            lbError.setText(" ");
                        Booking b1 = new Booking(str,"",srt_date,sop_date,"", TXEntCLId.getText().trim(),focusbox.getValue().toString(),"",TXEntStaffId.getText().trim(),staffn,"",TXPTWanted.getText().trim());
                        //String date, String BkID, int Srt_Timeint Sop_Time, String ClientName, String ClientID, String FocusName, String FocusID, String StaffID, String StaffName, String PTName, String PTID
            SenderObj sendobj = null;
        try {
             Socket  s1  = new Socket("localhost", port);
             sendobj = new SenderObj(b1, "checkAvail");
             ObjectOutputStream clientOutputStream = new ObjectOutputStream(s1.getOutputStream());
             clientOutputStream.writeObject(sendobj);
             ObjectInputStream clientInputStream = new ObjectInputStream(s1.getInputStream());
             b1 = (Booking)clientInputStream.readObject();
                 clientOutputStream.close();
                 clientInputStream.close();
                 s1.close();
           
            System.out.println(" b1 ID = "+b1.getBkID());
        } catch (IOException ex) {
                        System.out.println("b1 ID = "+b1.getBkID());

        } catch (ClassNotFoundException ex) {
                    System.out.println("ex "+ex);
        } 
        
         if(b1.getAcptBook()){
         
             System.out.println("Booking is Available !");
             txBKid.setText(b1.getBkID());
             lbBookingStatus.setText("Booked !");
            txBKid.setText(b1.getBkID());
            
             lbBooked.setText("Booking Made for "+ b1.getClientName()+" on "+b1.getDate()+" from "+ b1.getSrt_Time() +" for "+(b1.getSop_Time()- b1.getSrt_Time()) + "hrs on "+ b1.getFocusName());
        
         }else{
             lbBookingStatus.setText(b1.getERRORmessage());
             lbBookingStatus.setText("Booking Not Possible for the Following :"+b1.getERRORmessage());
         }
            
    }  
    
    }); 

Reset.setOnAction(e->{
        TXEntCLId.setText("");TXPTWanted.setText("");
    });   

lay4.getChildren().addAll(FXLbBOOK,hbBKID,LabEntFocus,focusbox,btnSrchPT,lbPTAvailable_time,lbTrainerAvailable,LabEntCLID,TXEntCLId,lbdate,
        h1box,LabEntTime,lbGymTimings,hb0,hb2,null_,lbError,SrchPTTime,lbBookingStatus,hbBooking,Reset,back5);//TimeaVAILABLE
                h1box.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, new Insets(5, 5, 5, 5))));
                hb0.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, new Insets(5, 5, 5, 5))));
                hbBooking.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, new Insets(5, 5, 5, 5))));


scene3_BookT= new Scene(lay4, 500,800);
lay4.setPadding(new Insets(10, 50, 50, 50));
lay4.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));

        
//LAYOUT 3 - UPDATE 
VBox lay3 = new VBox(20);
Label FXLbUPDATE = new Label("Update Bookings");
Button CheckBooking = new Button("Find Booking");
Button btnDelete = new Button("Delete Bookings ");
Label lb_Del_UP_Bking = new Label();
Button btn_GetUpdateDetails = new Button("Get Booking Details ");
Label BkID = new Label("Enter Booking ID ");
TextField TX_UpBkID = new TextField("");
Button btn_UpdateBooking = new Button("Update Booking ");
Label Up_lastLine = new Label("");
btn_GetUpdateDetails.setVisible(false);
CheckBooking.setOnAction(e->{
    ArrUpBook.clear();
    btn_GetUpdateDetails.setVisible(false);
    btn_UpdateBooking.setVisible(false);
    lb_Del_UP_Bking.setText(" ");
    if(TX_UpBkID.getText().isEmpty()){
        lb_Del_UP_Bking.setText(" Enter Booking ID .");
        System.out.println("Enter Booking ID !");
    }else{ 
         try {  
                Booking bc = new Booking();
                          
                        bc = new Booking(TX_UpBkID.getText().toString());
                bc.setStaffID_str(StaffID);
                Socket  s1  = new Socket("localhost", port);
                ObjectOutputStream clientOutputStream = new ObjectOutputStream(s1.getOutputStream());
                SenderObj sendobj = new SenderObj(bc,"Find Booking");
                clientOutputStream.writeObject(sendobj);
                ObjectInputStream clientInputStream = new ObjectInputStream(s1.getInputStream());
                bc = (Booking)clientInputStream.readObject();
            if(bc.getBooking_Found()){
                System.out.println("BK ID "+bc.getBkID()+" "+bc.getFocusName()+" Client ID"+bc.getClientID()+" "+bc.getDate()+" ");

                System.out.println("Booking Found ");
                        ArrUpBook.add(bc.getBkID());//0
                        ArrUpBook.add(bc.getFocusName());//1
                        ArrUpBook.add(bc.getClientID());//2
                        ArrUpBook.add(bc.getDate());//3
                        ArrUpBook.add(bc.getSrt_Time());//4
                        ArrUpBook.add( bc.getSop_Time());//5
                        ArrUpBook.add(bc.getPTID());//6
                         System.out.println("Sop Time ="+bc.getSop_Time());
                   lb_Del_UP_Bking.setText("  Booking Found ."); 
                   btn_GetUpdateDetails.setVisible(true);
                                   System.out.println("Booking Found 1");
                                   System.out.println("ArrUp="+ArrUpBook.toString());
                                  
                   vhb.setVisible(true);
                   TX_UpBkID.setEditable(false);
                   TX_UpBkID.setAlignment(Pos.CENTER);
                   btn_UpdateBooking.setVisible(true);
               }else{
                                lb_Del_UP_Bking.setText("  Booking not Found.");
                }
                
                
                
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Ex:"+ex);
            }
    }
});
//Update Booking Pane
HBox UPh1 = new HBox();
//TextField TX_UpBkID = new TextField();
Label lb_UP_PTgot = new Label();

Button btnUP_GetPT = new Button("Search PT ");

HBox Uph2 = new HBox();
Label lb_Focusbox = new Label("Select Focus");
final ComboBox uP_focusbox= new ComboBox(FocusList);
uP_focusbox.setValue("Flexibility");
Uph2.getChildren().addAll(lb_Focusbox,uP_focusbox);

HBox Uph3 = new HBox();
Label Up_lbdate = new Label("Enter Date  ");
    DatePicker Up_datePicker = new DatePicker();
    //Up_datePicker.setValue(LocalDate.now());
        
    Up_datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore( LocalDate.now())  ) { setDisable(true);          } 
            }
        });
        Up_datePicker.setEditable(true);


Label Up_lbError = new Label(" "); lbError.setStyle("-fx-color: red");

Uph3.getChildren().addAll(Up_lbdate,Up_datePicker);

Label Up_GetTime = new Label("Change Time ");
HBox Uph4 = new HBox();
ComboBox Up_ampm1 = new ComboBox(am_pm);
ComboBox Up_ampm2 = new ComboBox(am_pm);  
 Up_ampm1.setValue("05:00 am");                Up_ampm2.setValue("06:00 am");

 
HBox Uph5 = new HBox();
Label lb_Up_ClientID = new Label("Client ID :");
TextField tx_Up_CliendID = new TextField();
Label whynot2 = new Label(" - ");
Uph5.getChildren().addAll(Up_GetTime,Up_ampm1,whynot2,Up_ampm2);
Uph4.getChildren().addAll(lb_Up_ClientID,tx_Up_CliendID);

HBox Uph6 = new HBox();
Label lb_Up_PT = new Label("Enter PT ID : ");
TextField tx_Up_PT = new TextField("");
Uph6.getChildren().addAll(lb_Up_PT,tx_Up_PT);

btnDelete.setOnAction(e->{
    System.out.println("Delete "+TX_UpBkID.getText().toString());
try {
                Booking b1 = new Booking(TX_UpBkID.getText().toString());
                Socket  s1  = new Socket("localhost", port);
                ObjectOutputStream clientOutputStream = new ObjectOutputStream(s1.getOutputStream());
                SenderObj sendobj = new SenderObj(b1,"DeleteBK");
                clientOutputStream.writeObject(sendobj);
                System.out.println(b1.getBkID());
                ObjectInputStream clientInputStream = new ObjectInputStream(s1.getInputStream());
                b1 = (Booking)clientInputStream.readObject();
                if(b1.getBooking_Deleted()){
                    lb_Del_UP_Bking.setText("  Booking Deleted .");
                }
                else{
                    lb_Del_UP_Bking.setText("  Booking Not Found .");
                    
                }   } catch (IOException ex) {
            } catch (ClassNotFoundException ex) {
                System.out.println("ex"+ex);
            }catch (Exception ex){
                System.out.println("EX = "+ex);
            }

});
btnUP_GetPT.setOnAction(e->{
                 try {
                     Focus f1 = new Focus(uP_focusbox.getValue().toString());
                     Socket  s1  = new Socket("localhost", port);
                     SenderObj sendobj =  new SenderObj(f1,"gt_Focuses");
                     ArrayList Tlist = null;
                     ObjectOutputStream clientOutputStream = new ObjectOutputStream(s1.getOutputStream());
                     clientOutputStream.writeObject(sendobj);
                     ObjectInputStream clientInputStream = new ObjectInputStream(s1.getInputStream());
                     Tlist = (ArrayList)clientInputStream.readObject();
                     clientOutputStream.close();
                     clientInputStream.close();
                     s1.close();
                     lb_UP_PTgot.setText(Tlist.toString());
                 } catch (IOException ex) {
                     Logger.getLogger(A4.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (ClassNotFoundException ex) {
                     Logger.getLogger(A4.class.getName()).log(Level.SEVERE, null, ex);
                 }
});
btn_GetUpdateDetails.setOnAction(E->{
    TX_UpBkID.setText(ArrUpBook.get(0).toString());
    TX_UpBkID.setEditable(false);
    uP_focusbox.setValue(ArrUpBook.get(1).toString());
    tx_Up_CliendID.setText(ArrUpBook.get(2).toString());
    String date = ArrUpBook.get(3).toString();
    LocalDate localDate = LocalDate.parse(date);
    Up_datePicker.setValue(localDate);
    Up_datePicker.setEditable(true);
    int temp = (int) ArrUpBook.get(4),
            temp1 =(int) ArrUpBook.get(5);
    tx_Up_PT.setText(ArrUpBook.get(6).toString());

    Up_ampm1.setValue(TimeSelect(temp));
    Up_ampm2.setValue(TimeSelect(temp1));
    Client c1 = new Client(tx_Up_CliendID.getText().trim());
    Focus f1 = new Focus(uP_focusbox.getValue().toString());
    PTrainer pt1 = new PTrainer(tx_Up_PT.getText().trim());

});
UPh1.setSpacing(15);Uph2.setSpacing(15);Uph3.setSpacing(15);Uph4.setSpacing(15);Uph5.setSpacing(15);
UPh1.getChildren().addAll(btnUP_GetPT);
HBox hbcheckandlb = new HBox();
hbcheckandlb.getChildren().addAll(CheckBooking,lb_Del_UP_Bking);
vhb.getChildren().addAll(Uph2,lb_UP_PTgot,UPh1,Uph3,Uph4,Uph5,Uph6);
vhb.setSpacing(25);
vhb.setVisible(false);
Button Up_Reset = new Button("Reset");

//Update Booking Button FX
btn_UpdateBooking.setOnAction(e->{
int flag =0 ;
if(tx_Up_CliendID.getText().trim().isEmpty() || tx_Up_PT.getText().trim().isEmpty()){
    flag--;
    Up_lbError.setText("Enter Vali Client ID and PT ID ");
}
if(flag >= 0){
    try {
//        Client c1 = new Client();
//        Focus f1 = new Focus(uP_focusbox.getValue().toString());
//        PTrainer pt1 = new PTrainer(tx_Up_PT.getText().trim());
        LocalDate value = Up_datePicker.getValue();
        String str =value.format(DateTimeFormatter.ISO_DATE);
        int srt_date = Time(Up_ampm1.getValue().toString()),sop_date= Time(Up_ampm2.getValue().toString());//Integer.parseInt(b);
        Booking b =new Booking(str, TX_UpBkID.getText().trim(), srt_date, sop_date,null , tx_Up_CliendID.getText().trim(), uP_focusbox.getValue().toString(), null,StaffID, staffn,null, tx_Up_PT.getText().trim());
        
        Socket  s1  = new Socket("localhost", port);
         ObjectOutputStream clientOutputStream = new ObjectOutputStream(s1.getOutputStream());
                 SenderObj sendobj = new SenderObj(b,"Update");
                 clientOutputStream.writeObject(sendobj);
                 ObjectInputStream clientInputStream = new   ObjectInputStream(s1.getInputStream());
                 b= (Booking)clientInputStream.readObject();
                 clientOutputStream.close();
                 clientInputStream.close();
        
              if(b.getBooking_Updates()){
            lb_Del_UP_Bking.setText(" Booking Updated ! ");
            vhb.setVisible(false);
        }else{
            lb_Del_UP_Bking.setText(" Booking Update for the Following not Possible .");
              }
        
    } catch (IOException ex) {
        Logger.getLogger(A4.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(A4.class.getName()).log(Level.SEVERE, null, ex);
    }
}
});
Up_Reset.setOnAction(e->{
    TX_UpBkID.setEditable(true);
    vhb.setVisible(false);
});


lay3.getChildren().addAll(FXLbUPDATE,BkID,TX_UpBkID,hbcheckandlb,btnDelete,btn_GetUpdateDetails,vhb,Up_lbError,btn_UpdateBooking,Up_Reset,back1);
scene3_Updateb= new Scene(lay3, 500,800);
lay3.setPadding(new Insets(10, 50, 50, 50));
   


   
//LAYOUT 5 - LIST Bookings - ClientID
VBox lay5 = new VBox(20);
Label FXLbLIST = new Label("List Bookings");
Label LabENTCLID_list = new Label("Enter Client ID");
Button btnListBy_Client = new Button("Go");
Label lb_Lst_Cl_GO = new Label();
HBox hb_Cl_LstGO = new HBox(btnListBy_Client,lb_Lst_Cl_GO);
TextField TXEntCLId_list = new TextField("list clientID");
ListView<String> list_CL_BookID = new ListView<String>();
ListView<String> list_CL_Focus = new ListView<String>();
ListView<String> list_CL_TrainerName = new ListView<String>();
ListView<String> list_CL_Date = new ListView<String>();
ListView<String> list_CL_StTime = new ListView<String>();
ListView<String> list_CL_SoTime = new ListView<String>();



HBox hbClient_List = new HBox(list_CL_BookID,list_CL_Focus,list_CL_TrainerName,list_CL_Date,list_CL_StTime,list_CL_SoTime);
hbClient_List.setVisible(false);
btnListBy_Client.setOnAction(e->{
    lb_Lst_Cl_GO.setText(" ");
    int flag = 0;
    
    if(TXEntCLId_list.getText().trim().isEmpty()){
        flag= flag - 10;
    }
    
    if(flag>0)
    { lb_Lst_Cl_GO.setVisible(true);
      lb_Lst_Cl_GO.setText(" Enter Client ID");
    }else{
        hb_Cl_LstGO.setVisible(true);
        hbClient_List.setVisible(true);
        ObservableList<String> items =FXCollections.observableArrayList ("Booking ID");
        list_CL_BookID.setItems(items);
        ObservableList<String> items_TrainerName_Cl_Lst =FXCollections.observableArrayList ("Trainer Name");
        list_CL_TrainerName.setItems(items_TrainerName_Cl_Lst);
        ObservableList<String> items_Focus_Cl_Lst =FXCollections.observableArrayList (    "Focus");
        list_CL_Focus.setItems(items_Focus_Cl_Lst);
        ObservableList<String> items_Date_Cl_Lst =FXCollections.observableArrayList (    "Date");
        list_CL_Date.setItems(items_Date_Cl_Lst);
        ObservableList<String> items_SoTime_Cl_Lst =FXCollections.observableArrayList (    "End Time");
        list_CL_SoTime.setItems(items_SoTime_Cl_Lst);    
        ObservableList<String> items_StTime_Cl_Lst =FXCollections.observableArrayList (    "Start Time");
        list_CL_StTime.setItems(items_StTime_Cl_Lst);    
        
       
     Client c2 = new Client(TXEntCLId_list.getText().trim());
   try {
          Socket  s1  = new Socket("localhost", port);
          SenderObj  sendobj = new SenderObj(c2 , "Lst_Cl");
          ObjectOutputStream clientOutputStream = new ObjectOutputStream(s1.getOutputStream());
          clientOutputStream.writeObject(sendobj);
          ObjectInputStream clientInputStream = new ObjectInputStream(s1.getInputStream());
             ArrayList ar = (ArrayList)clientInputStream.readObject();
             clientOutputStream.close();
             clientInputStream.close();
             s1.close();
             
             if((boolean)ar.get(0)){ //if client Exists 
                   Make_List(ar, items, items_Focus_Cl_Lst, items_TrainerName_Cl_Lst, items_Date_Cl_Lst, items_StTime_Cl_Lst, items_SoTime_Cl_Lst);  
             }else{
                 lb_Lst_Cl_GO.setText("No Booking Found ");
             }
             
             } catch (IOException ex) {
                    ex.printStackTrace();
             } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
             } catch(NullPointerException NUX){
                 lb_Lst_Cl_GO.setText(" No Bookings Found .");
             } 
    }
});
        
        
lay5.getChildren().addAll(FXLbLIST,LabENTCLID_list,TXEntCLId_list,hb_Cl_LstGO,hbClient_List,back3);
scene3_List= new Scene(lay5, 1000,900);
lay5.setPadding(new Insets(10, 50, 50, 50));


//List Bookings BY PT 
VBox lay52 = new VBox(20);
Label FXLbLISTPT = new Label("List Bookings by Personal Trainer");
TextField TXlistPT = new TextField("");
Button btnListBy_PT = new Button("Go");
Label lb_ListbyPt_GO = new Label();
HBox hb_ListbyPT_go = new HBox(btnListBy_PT,lb_ListbyPt_GO);
ListView<String> list_PT_BookID = new ListView<String>();
ListView<String> list_PT_Focus = new ListView<String>();
ListView<String> list_PT_ClientName = new ListView<String>();
ListView<String> list_PT_Date = new ListView<String>();
ListView list_PT_StTime = new ListView();
ListView list_PT_SoTime = new ListView();

HBox hbPT_List = new HBox(list_PT_BookID,list_PT_Focus,list_PT_ClientName,list_PT_Date,list_PT_StTime,list_PT_SoTime);

btnListBy_PT.setOnAction(e->{
    lb_ListbyPt_GO.setText(" ");
        int flag = 0;
    
    if(TXlistPT.getText().isEmpty()){
        flag--;
    }
    
    if(flag<0)
       lb_ListbyPt_GO.setText("  Enter Personal Trainer ID");
    
    else{
    
        ObservableList<String> items_bkID_PT =FXCollections.observableArrayList ("Booking ID");
        list_PT_BookID.setItems(items_bkID_PT);
        ObservableList<String> items_ClientName_PT_Lst =FXCollections.observableArrayList (  "Client Name");
        list_PT_ClientName.setItems(items_ClientName_PT_Lst);
        ObservableList<String> items_Focus_PT_Lst =FXCollections.observableArrayList (    "Focus");
        list_PT_Focus.setItems(items_Focus_PT_Lst);
        ObservableList<String> items_Date_PT_Lst =FXCollections.observableArrayList (    "Date");
        list_PT_Date.setItems(items_Date_PT_Lst);
        ObservableList items_StTime_PT_Lst =FXCollections.observableArrayList (    "Start Time");
        list_PT_StTime.setItems(items_StTime_PT_Lst);
        ObservableList items_SoTime_PT_Lst =FXCollections.observableArrayList (    "End Time");
        list_PT_SoTime.setItems(items_SoTime_PT_Lst);    

     
     PTrainer pt1 = new PTrainer(TXlistPT.getText().trim());
   try {
       
         Socket  s1  = new Socket("localhost", port);
         SenderObj  sendobj = new SenderObj(pt1 , "Lst_PT");
         ObjectOutputStream clientOutputStream = new ObjectOutputStream(s1.getOutputStream());
         clientOutputStream.writeObject(sendobj);
         ObjectInputStream clientInputStream = new ObjectInputStream(s1.getInputStream());
             ArrayList ar = (ArrayList)clientInputStream.readObject();
                 clientOutputStream.close();
                 clientInputStream.close();
                 s1.close();
                     Make_List (  ar,   items_bkID_PT,   items_Focus_PT_Lst,   items_ClientName_PT_Lst, items_Date_PT_Lst,       items_StTime_PT_Lst,   items_SoTime_PT_Lst);
                     
                  
                 
             } catch (NullPointerException ex) {

                 lb_ListbyPt_GO.setText("No Personal Trainer Record Found.");
             } catch (Exception ex) {
                   
             }  
    }
});
lay52.getChildren().addAll(FXLbLISTPT,TXlistPT,hb_ListbyPT_go,hbPT_List,back6);
scene3_ListPT= new Scene(lay52, 1000,600);
lay52.setPadding(new Insets(10, 50, 50, 50));

//List Bookings BY Date 
VBox lay53 = new VBox(20);
Label FXLbLISTDate = new Label("List Bookings by Date");
Button btnListBy_Date = new Button("Go");
Label lbList_DATE_GO = new Label();
DatePicker datePicker_lIST = new DatePicker();
datePicker_lIST.setValue(LocalDate.now());
HBox hb_ByDate_Go = new HBox(btnListBy_Date,lbList_DATE_GO);
ListView<String> list_Date_BookID = new ListView<String>();
ListView<String> list_Date_Focus = new ListView<String>();
ListView<String> list_Date_ClientName = new ListView<String>();
ListView<String> list_Date_PTName = new ListView<String>();
ListView<String> list_Date_StTime = new ListView<String>();
ListView<String> list_Date_SoTime = new ListView<String>();
HBox hbDate_List = new HBox(list_Date_BookID,list_Date_Focus,list_Date_ClientName,list_Date_PTName,list_Date_StTime,list_Date_SoTime);

btnListBy_Date.setOnAction(e->{
lbList_DATE_GO.setText(" ");
ObservableList<String> items_bkID_Date =FXCollections.observableArrayList ("Booking ID");
list_Date_BookID.setItems(items_bkID_Date);
ObservableList<String> items_Focus_Date_Lst =FXCollections.observableArrayList (    "Focus");
list_Date_Focus.setItems(items_Focus_Date_Lst);
ObservableList<String> items_Date_clName_Lst =FXCollections.observableArrayList (    "Client Name");
list_Date_ClientName.setItems(items_Date_clName_Lst);
ObservableList<String> items_PTName_Date_Lst =FXCollections.observableArrayList (  "Personal Trainer Name");
list_Date_PTName.setItems(items_PTName_Date_Lst);

ObservableList<String> items_SoTime_Date_Lst =FXCollections.observableArrayList (    "End Time");
list_Date_SoTime.setItems(items_SoTime_Date_Lst);    
ObservableList<String> items_StTime_Date_Lst =FXCollections.observableArrayList (    "Start Time");
list_Date_StTime.setItems(items_StTime_Date_Lst);

      ArrayList Date_List= null;
   try {
       
         Socket  s1  = new Socket("localhost", port);
         SenderObj  sendobj = new SenderObj(datePicker_lIST.getValue().toString() , "Lst_Date");
         ObjectOutputStream clientOutputStream = new ObjectOutputStream(s1.getOutputStream());
         clientOutputStream.writeObject(sendobj);
         ObjectInputStream clientInputStream = new ObjectInputStream(s1.getInputStream());
             ArrayList ar = (ArrayList)clientInputStream.readObject();
                 clientOutputStream.close();
                 clientInputStream.close();
                 s1.close();
                    Make_List (  ar,   items_bkID_Date,   items_Focus_Date_Lst,   items_Date_clName_Lst, items_PTName_Date_Lst,       items_StTime_Date_Lst,   items_SoTime_Date_Lst);
                    if(!(boolean)ar.get(0)){
                        lbList_DATE_GO.setText("No Bookings Found.");
                    }
                    
                   } catch (NullPointerException ex) {
                     lbList_DATE_GO.setText("Booking Details not Available");
             } catch (Exception ex) {
                    ex.printStackTrace();
             }  
    
});
lay53.getChildren().addAll(FXLbLISTDate,datePicker_lIST,hb_ByDate_Go,hbDate_List,back4);
scene3_ListDate= new Scene(lay53, 1000,500);
lay53.setPadding(new Insets(10, 50, 50, 50));
window.setScene(scene1_SignIn);
window.setTitle("A GYM");
window.show();
    }


 
public static void main(String[] args) {
    System.out.println("Client Running  ");
    launch(args); 
}

   public void ErrorBox(TextField tf){
         tf.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
}  
   public void NoErrorBox(TextField tf){
         tf.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
}  
   private static int Time(String toString) {
         String c = String.valueOf(toString.charAt(0));
         String c2 = String.valueOf(toString.charAt(1));
         String c3= c.concat(c2);
       char slastChar = toString.charAt(toString.length() - 2);
                int time = Integer.parseInt(c3);
                if(slastChar == 'p')
                    time = time+12;
                return time;
    }
   public  ArrayList ConFor_PTList(Focus f1) throws IOException, ClassNotFoundException{
             Socket  s1  = new Socket("localhost", port);
             SenderObj sendobj =  new SenderObj(f1,"gt_Focuses");
             ArrayList Tlist = null;
                 ObjectOutputStream clientOutputStream = new ObjectOutputStream(s1.getOutputStream());
                 clientOutputStream.writeObject(sendobj);
                 ObjectInputStream clientInputStream = new ObjectInputStream(s1.getInputStream());
                  Tlist = (ArrayList)clientInputStream.readObject();
                 clientOutputStream.close();
                 clientInputStream.close();
                 s1.close();
        return Tlist;
     }
   public void con_ForDeleteBooking(TextField TX_UpBkID,Label lb_Del_UP_Bking){
            try {
                Booking b1 = new Booking(TX_UpBkID.getText().toString());
                Socket  s1  = new Socket("localhost", port);
                ObjectOutputStream clientOutputStream = new ObjectOutputStream(s1.getOutputStream());
                SenderObj sendobj = new SenderObj(b1,"DeleteBK");
                clientOutputStream.writeObject(sendobj);
                System.out.println(b1.getBkID());
                ObjectInputStream clientInputStream = new ObjectInputStream(s1.getInputStream());
                b1 = (Booking)clientInputStream.readObject();
                if(b1.getBooking_Deleted()){
                    lb_Del_UP_Bking.setText("Booking Deleted .");
                }
                else{
                    lb_Del_UP_Bking.setText("Booking Not Found .");
                    
                }   } catch (IOException ex) {
            } catch (ClassNotFoundException ex) {
            }catch (Exception ex){
                System.out.println("EX = "+ex);
            }
}
   public static String TimeSelect(int temp){
     String u ="05:00 am,06:00 am,07:00 am,08:00 am,09:00 am,10:00 am,11:00 am,12:00 am,01:00 pm,02:00 pm,03:00 pm,04:00 pm,05:00 pm,06:00 pm,07:00 pm,08:00 pm,09:00 pm,10:00 pm,11:00 pm,12:00 pm";
     String [] TimeArr = u.split(",");
     String ans = "";
           int st = 5;
         for(int i = 0 ; i<TimeArr.length;i++){
              if(temp == st)
              {
                                      return(TimeArr[i]);
              }
          st++;
         }
    return " ";
    }    
  
public Staff StaffLoginCon(TextField TXEntStaffId,TextField TXEntStPass ) throws IOException, ClassNotFoundException{
        Staff st1 = new Staff(TXEntStaffId.getText().trim(),TXEntStPass.getText());
                 Socket  s1  = new Socket("localhost", port);
                 ObjectOutputStream clientOutputStream = new ObjectOutputStream(s1.getOutputStream());
                 SenderObj sendobj = new SenderObj(st1,"Login");
                 clientOutputStream.writeObject(sendobj);
                 ObjectInputStream clientInputStream = new   ObjectInputStream(s1.getInputStream());
                 clientOutputStream.close();
                 clientInputStream.close();
                 return st1 = (Staff)clientInputStream.readObject();
    }    
   //Graphics Methods
   public void TextFontCol(Text text,Color c,int size){
   text.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, size));
   text.setFill(c); 
   }
   public void LabelFontCol(Label l , int size){
       l.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, size));
   }
   
   public void Make_List (ArrayList PT_List,ObservableList<String> items_bkID_PT,ObservableList<String> items_Focus_PT_Lst,ObservableList<String> items_ClientName_PT_Lst,ObservableList<String> items_Date_PT_Lst,
                             ObservableList items_StTime_PT_Lst,ObservableList items_SoTime_PT_Lst){
                  ArrayList a1 = (ArrayList) PT_List.get(1);
                  ArrayList a2 = (ArrayList) PT_List.get(2);
                  ArrayList a3 = (ArrayList) PT_List.get(3);
                  ArrayList a4 = (ArrayList) PT_List.get(4);
                  ArrayList a5 = (ArrayList) PT_List.get(5);
                  ArrayList a6 = (ArrayList) PT_List.get(6);   
                     for(int i = 0 ; i < a1.size();i++){
                 items_bkID_PT.add(a1.get(i).toString());
                 items_Focus_PT_Lst.add(a2.get(i).toString());
                 items_ClientName_PT_Lst.add(a3.get(i).toString());
                 items_Date_PT_Lst.add(a4.get(i).toString());
                 
                 int int_str =  Integer.parseInt(a5.get(i).toString());
int int_sop =  Integer.parseInt(a6.get(i).toString());
                 String srt = TimeSelect(int_str );            
                 String sop = TimeSelect(int_sop );
                 items_StTime_PT_Lst.add(srt);
                 items_SoTime_PT_Lst.add(sop); 
                     }
              }
   
   
}    





