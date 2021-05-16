DROP DATABASE gym;
CREATE DATABASE gym;USE gym;CREATE TABLE Client(
ClID VARCHAR(5),
ClName VARCHAR(25) NOT NULL,
Gender enum ('m','f') NOT NULL,
Heigth VARCHAR(5),
Weigth VARCHAR(5),
Phone VARCHAR(50),
CONSTRAINT pk1 PRIMARY KEY (ClID)
);CREATE TABLE Staff(
StaffID VARCHAR(5),
StName VARCHAR(25) NOT NULL,
PhoneNum VARCHAR(15) NOT NULL,
email VARCHAR(50) NOT NULL,
StPass VARCHAR(15) NOT NULL,
CONSTRAINT PK3 PRIMARY KEY (StaffID)
);
INSERT INTO Client
VALUES ('CL05', 'Client1','m','150','60','0559086987');
INSERT INTO Client
VALUES ('CL07', 'Client2','f','150','60','0509086987');
INSERT INTO Client
VALUES ('CL08', 'Client3','f','170','80','0559086989');
INSERT INTO Client
VALUES ('CL9', 'Client4','m','160','60','0559086967');
INSERT INTO Client
VALUES ('CL10', 'Client5','m','164','65','0529086967');
INSERT INTO Staff
VALUES ('ST01', 'Staffy','0557900987','staffy@gmail.com','1234');
INSERT INTO Staff
VALUES ('ST02', 'Stella','0507900987','stef@gmail.com','1234');
CREATE TABLE Focus (
FocusID VARCHAR(5) NOT NULL,
FocusName varchar(25),
CONSTRAINT PK_Focus PRIMARY KEY (FocusID)
);CREATE TABLE PT (
PTID VARCHAR(5) NOT NULL,
PTName VARCHAR(25) NOT NULL,
PhoneNum VARCHAR(15) NOT NULL,
email VARCHAR(50) NOT NULL,
CONSTRAINT PK_PT PRIMARY KEY (PTID)
);CREATE TABLE PTFocus (
FocusId VARCHAR(5) NOT NULL,
PTId VARCHAR(5) NOT NULL,
CONSTRAINT PK_PTMember PRIMARY KEY (FocusID, PTID),
CONSTRAINT FK_PTMember_PT FOREIGN KEY (PTID) REFERENCES PT(PTID),
CONSTRAINT FK_PTMember_Focus FOREIGN KEY (FocusID) REFERENCES Focus(FocusID)
);
CREATE TABLE Booking(
BookID VARCHAR(5),
StaffID VARCHAR (5) ,
ClID VARCHAR(5) ,
FocusID VARCHAR(5) ,
PTID VARCHAR(5) ,
BKdate DATE not null,
str_Time int,
sop_Time int,
CONSTRAINT BookID PRIMARY KEY (BookID),
CONSTRAINT C1 FOREIGN KEY (StaffID) REFERENCES Staff(StaffID),
CONSTRAINT C2 FOREIGN KEY (ClID) REFERENCES Client(ClID),
CONSTRAINT C3 FOREIGN KEY (FocusID) REFERENCES Focus(FocusID),
CONSTRAINT C5 FOREIGN KEY (PTID) REFERENCES PT(PTID)
);
INSERT INTO Focus VALUES
( 'FL', 'Flexibility' ),
( 'MG', 'Muscle Gain' ),
( 'WL', 'Weigth Loss' ),
( 'PI', 'Posture Improvement' ),
( 'YB', 'Yoga Basics');INSERT INTO PT VALUES
( 'KT', 'Kate' ,'0504555604','ktate@gmail.com'),
( 'MK', 'Mike' ,'0504555605','mkate@gmail.com' ),
( 'JF', 'Johnny' ,'0504555606','jfe@gmail.com'),
( 'AN', 'Ariana' ,'0504555607','annnae@gmail.com');INSERT INTO PTFocus VALUES
( 'FL', 'KT' ),
( 'WL', 'MK' ),
( 'PI', 'MK' ),
( 'PI', 'KT' ),
( 'YB', 'JF' ),
( 'FL', 'AN' ),
( 'MG', 'KT' );INSERT INTO Booking
VALUES ('0000', 'ST01','CL05','YB','JF',Date '2011-12-20','5','7'),('0001', 'ST02','CL07','YB','JF',Date '2011-12-20','8','9');
INSERT INTO Booking
VALUES ('0002', 'ST01','CL05','WL','MK',Date '2011-11-20','10','13');Select PTFocus.PTID,PT.PTName from PT,Focus,PTFocus where Focus.FocusID = PTFocus.FocusID and PT.PTID = PTFocus.PTID AND FocusName = "Muscle Gain" ;
INSERT INTO Booking
VALUES ('0004', 'ST01','CL05','WL','MK',Date '2020-02-04','10','13');
CREATE VIEW Lst_Cl  as SELECT ClID,BookID,FocusName,PTName,BKdate,str_Time,sop_Time FROM Focus f, PT pt , Booking b WHERE  b.PTID = pt.PTID AND f.FocusID = b.FocusID;
CREATE VIEW Lst_PT as Select PTID,BookID,FocusName ,ClName ,BKdate,str_Time,sop_Time From  Focus f ,Client c,Booking b where b.FocusID = f.FocusID and b.ClID = c.ClID ;
CREATE VIEW Lst_Date  as SELECT BkDate, BookID,ClName ,FocusName,PTName,str_Time,sop_Time FROM Focus f, PT pt ,Client cl, Booking b WHERE b.FocusID = f.FocusID AND  b.ClID = cl.ClID AND b.PTID = pt.PTID  ;
