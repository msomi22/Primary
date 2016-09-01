
-- Schema Name: primarydb
-- Username: prim
-- Password: priM123PriM

-- These tables describe the database for a point of sale system

-- Make sure you have created a Postgres user with the above username, password
-- and appropriate permissions. For development environments, you can make the 
-- database user to be a superuser to allow for copying of external files. 

-- Then run the "dbSetup.sh" script in the bin folder of this project.

\c postgres

-- Then execute the following:
DROP DATABASE IF EXISTS primarydb; -- To drop a database you can't be logged into it. Drops if it exists.
CREATE DATABASE primarydb;

-- Connect with the database on the username
\c primarydb prim



-- =========================
-- 1.  School Management
-- =========================


-- -------------------
-- Account
----------------------
CREATE TABLE  Account (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    username text,
    schoolName text,
    schoolEmail text,
    schoolPhone text,
    schoolAddres text,
    schoolHomeTown text,
    schoolCounty text,
    schoolMotto text,
    dayBoarding text
);

\COPY Account(uuid,username,schoolName,schoolEmail,schoolPhone,schoolAddres,schoolHomeTown,schoolCounty,schoolMotto,dayBoarding) FROM '/tmp/Account.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Account OWNER TO prim;



-- -------------------
-- house
----------------------
CREATE TABLE  house (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountUuid text REFERENCES Account(Uuid),
    houseName text
);

\COPY house(uuid,accountUuid,houseName) FROM '/tmp/house.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE house OWNER TO prim;


-- -------------------
-- Table classroom
-- -------------------

CREATE TABLE  classroom (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    className text
);
\COPY classroom(uuid,className) FROM '/tmp/classroom.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE classroom OWNER TO prim;


-- -------------------
-- Table stream
-- -------------------

CREATE TABLE  stream (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountUuid text REFERENCES Account(Uuid),
    streamName text
);
\COPY stream(uuid,accountUuid,streamName) FROM '/tmp/stream.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE stream OWNER TO prim;


-- -------------------
-- Table systemConfig
-- -------------------

CREATE TABLE  systemConfig (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountUuid text REFERENCES Account(Uuid),
    term text,
    year text,
    smsSend text,
    examcode text,
    openningDate text,
    closingDate text
);
\COPY systemConfig(uuid,accountUuid,term,year,smsSend,examcode,openningDate,closingDate) FROM '/tmp/systemConfig.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE systemConfig OWNER TO prim;



-- -------------------
-- Table Comment
-- -------------------

CREATE TABLE  Comment (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountUuid text REFERENCES Account(Uuid),
    headTeacherCom text,
    gradeAplaincom text,
    gradeAminuscom text,
    gradeBpluscom text,
    gradeBplaincom text,
    gradeBminuscom text,
    gradeCpluscom text,
    gradeCplaincom text,
    gradeCminuscom text,
    gradeDpluscom text,
    gradeDplaincom text,
    gradeDminuscom text,
    gradeEcom text
    
   
);
\COPY Comment(uuid,accountUuid,headTeacherCom,gradeAplaincom,gradeAminuscom,gradeBpluscom,gradeBplaincom,gradeBminuscom,gradeCpluscom,gradeCplaincom,gradeCminuscom,gradeDpluscom,gradeDplaincom,gradeDminuscom,gradeEcom) FROM '/tmp/Comment.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Comment OWNER TO prim;



---------------------
-- Table SmsSend
-- -------------------


CREATE TABLE  SmsSend (
    Id SERIAL PRIMARY KEY,
    Uuid text UNIQUE NOT NULL,
    Status text,
    PhoneNo text,
    MessageId text,
    Cost text 

    

);
\COPY SmsSend(Uuid,Status,PhoneNo,MessageId,Cost) FROM '/tmp/SmsSend.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE SmsSend OWNER TO prim;



-- -------------------
-- Table SMSSPI
-- -------------------



CREATE TABLE  smsApi (
    Id SERIAL PRIMARY KEY,
    Uuid text UNIQUE NOT NULL,
    accountUuid text REFERENCES Account(Uuid),
    apiKey text,
    apiPassword text
    

);
\COPY smsApi(Uuid,accountUuid,apiKey,apiPassword) FROM '/tmp/smsApi.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE smsApi OWNER TO prim;











-- =========================
-- 1.  Student's Management
-- =========================




-- -------------------
-- Table student
-- -------------------


CREATE TABLE  student (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountUuid text REFERENCES Account(Uuid),
    statusUuid text,
    admmissinNo text,
    streamUuid text REFERENCES stream(uuid),
    firstname text,
    middlename text,
    lastname text,
    gender text,
    dateofbirth text,
    birthcertificateNo text,
    country text,
    county text,
    ward text,
    regTerm Integer,
    regYear Integer,
    finalTerm Integer,
    finalYear Integer,
    studentType text,
    studentLevel text,
    admissiondate timestamp with time zone DEFAULT now()
);
\COPY student(uuid,accountUuid,statusUuid,admmissinNo,streamUuid,firstname,middlename,lastname,gender,dateofbirth,birthcertificateNo,country,county,ward,regTerm,regYear,finalTerm,finalYear,studentType,studentLevel,admissiondate) FROM '/tmp/student.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE student OWNER TO prim;

-- -------------------
-- Table studentParent
-- -------------------

CREATE TABLE  studentParent (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    studentUuid text REFERENCES student(uuid),
    parentName text,
    parentPhone text,
    parentEmail text
   
);
\COPY studentParent(uuid,studentUuid,parentName,parentPhone,parentEmail) FROM '/tmp/studentParent.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE studentParent OWNER TO prim;



-- -------------------
-- studentHouse
----------------------
CREATE TABLE  studentHouse (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    studentUuid text REFERENCES student(uuid),
    houseUuid text REFERENCES House(uuid)
 
);

\COPY studentHouse(uuid,studentUuid,houseUuid) FROM '/tmp/studentHouse.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE studentHouse OWNER TO prim;


-- =========================
-- 1.  Subject's Management
-- =========================


-- -------------------
-- Table subject
-- -------------------


CREATE TABLE  subject (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    subjectCode text,
    subjectName text
   
);
\COPY subject(uuid,subjectCode,subjectName) FROM '/tmp/subject.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE subject OWNER TO prim;


--------------------
-- studentSubject
-- -------------------
CREATE TABLE studentSubject (
    Id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    studentUuid text REFERENCES Student(Uuid),
    subjectUuid text REFERENCES Subject(Uuid)
);

\COPY studentSubject(uuid,studentUuid,subjectUuid) FROM '/tmp/studentSubject.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE studentSubject OWNER TO prim;



-- -------------------
-- Table staff
-- -------------------


CREATE TABLE  staff (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountUuid text REFERENCES Account(Uuid),
    statusUuid text,
    employeeNo text, 
    name text,
    phone text,
    email text,
    gender text,
    dob text,
    country text,
    county text,
    ward text ,
    regDate timestamp with time zone DEFAULT now()
   
);
\COPY staff(uuid,accountUuid,statusUuid,employeeNo,name,phone,email,gender,dob,country,county,ward,regDate) FROM '/tmp/staff.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE staff OWNER TO prim;



-- -------------------
-- Table category
-- -------------------


CREATE TABLE  category (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    categoryName text
   
);
\COPY category(uuid,categoryName) FROM '/tmp/category.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE category OWNER TO prim;



-- -------------------
-- Table staffCategory
-- -------------------


CREATE TABLE  staffCategory (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    staffUuid text REFERENCES staff(uuid),
    categoryUuid text REFERENCES category(uuid)
    
);
\COPY staffCategory(uuid,staffUuid,categoryUuid) FROM '/tmp/staffCategory.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE staffCategory OWNER TO prim;



-- -------------------
-- Table users
-- -------------------


CREATE TABLE  users (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    staffUuid text REFERENCES staff(uuid),
    username text,
    password text

);
\COPY users(uuid,staffUuid,username,password) FROM '/tmp/user.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE users OWNER TO prim;


-- -------------------
-- Table teacherSubject
-- -------------------


CREATE TABLE  teacherSubject (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    teacherUuid text REFERENCES staff(uuid),
    subjectUuid text REFERENCES subject(uuid),
    streamUuid text REFERENCES stream(uuid)
   
);
\COPY teacherSubject(uuid,teacherUuid,subjectUuid,streamUuid) FROM '/tmp/teacherSubject.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE teacherSubject OWNER TO prim;




-- -------------------
-- Table classTeacher
-- -------------------


CREATE TABLE  classTeacher (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountUuid text REFERENCES Account(Uuid),
    teacherUuid text REFERENCES staff(uuid),
    streamUuid text REFERENCES stream(uuid)
   
);
\COPY classTeacher(uuid,accountUuid,teacherUuid,streamUuid) FROM '/tmp/classTeacher.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE classTeacher OWNER TO prim;





-- =========================
-- 3.  Exam Management
-- =========================


-- -------------------
-- Table exam
-- -------------------
 CREATE TABLE  exam (
    Id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    examName text, 
    outOf Integer
  
);
 \COPY exam(uuid,examName,outOf) FROM '/tmp/exam.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE exam OWNER TO prim;

-- -------------------
-- Table performance
-- -------------------


CREATE TABLE  performance ( 
    id SERIAL PRIMARY KEY,
    accountUuid text REFERENCES Account(Uuid),
    studentUuid text REFERENCES student(uuid),
    subjectUuid text REFERENCES subject(uuid),
    streamUuid text REFERENCES stream(uuid),
    classUuid text REFERENCES classroom(uuid),
    term text,
    year text,
    openner float,
    midterm float,
    endterm float 
   
);
\COPY performance(accountUuid,studentUuid,subjectUuid,streamUuid,classUuid,term,year,openner,midterm,endterm) FROM '/tmp/performance.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE performance OWNER TO prim;



-- -------------------
-- Table gradingSystem
-- -------------------
 CREATE TABLE  gradingSystem (
     Id SERIAL PRIMARY KEY,
     Uuid text UNIQUE NOT NULL,
     accountUuid text REFERENCES Account(Uuid),
     gradeAplain Integer,
     gradeAminus Integer,
     gradeBplus Integer,
     gradeBplain Integer,
     gradeBminus Integer,
     gradeCplus Integer,
     gradeCplain Integer,
     gradeCminus Integer,
     gradeDplus Integer,
     gradeDplain Integer,
     gradeDminus Integer,
     gradeE Integer
    
   
);

-- import data from the CSV file for the gradingSystem table
\COPY gradingSystem(Uuid,accountUuid,gradeAplain,gradeAminus,gradeBplus,gradeBplain,gradeBminus,gradeCplus,gradeCplain,gradeCminus,gradeDplus,gradeDplain,gradeDminus,gradeE) FROM '/tmp/gradingSystem.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE gradingSystem OWNER TO prim;


-- -------------------
-- Table BarWeight
-- -------------------
 CREATE TABLE  BarWeight (
    Id SERIAL PRIMARY KEY,
    studentUuid text REFERENCES student(Uuid),
    term text,
    year text,
    weight float
);

ALTER TABLE BarWeight OWNER TO prim;




-- -------------------
-- Table meanScore
-- -------------------
 CREATE TABLE  meanScore (
    Id SERIAL PRIMARY KEY,
    studentUuid text REFERENCES student(Uuid),
    term text,
    year text,
    meanScore float,
    streamPosition text,
    classPosition text
   
);
ALTER TABLE meanScore OWNER TO prim;



-- =========================
-- 4.  Finance Management
-- =========================

-- -------------------
-- Table termFee
-- -------------------


CREATE TABLE  termFee (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountUuid text REFERENCES Account(Uuid),
    term text,
    year text,
    studentLevel text,
    amount integer NOT NULL CHECK (amount>=0)
   
);
\COPY termFee(uuid,accountUuid,term,year,studentLevel,amount) FROM '/tmp/termFee.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE termFee OWNER TO prim;


-- -------------------
-- Table otherMoney
-- -------------------


CREATE TABLE  otherMoney (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountUuid text REFERENCES Account(Uuid),
    description text, 
    term text,
    year text,
    amount integer NOT NULL CHECK (amount>=0)
   
);
\COPY otherMoney(uuid,accountUuid,description,term,year,amount) FROM '/tmp/otherMoney.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE otherMoney OWNER TO prim;



-- -------------------
-- Table studentFee
-- -------------------


CREATE TABLE  studentFee (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    studentUuid text REFERENCES student(Uuid),
    term text,
    year text,
    studentType text,
    amountPaid integer NOT NULL CHECK (amountPaid>=0),
    transactionID text,
    datePaid timestamp with time zone DEFAULT now() 
   
);
\COPY studentFee(uuid,studentUuid,term,year,studentType,amountPaid,transactionID,datePaid) FROM '/tmp/studentFee.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE studentFee OWNER TO prim;




-- -------------------
-- Table studentOtherMoney
-- -------------------


CREATE TABLE  studentOtherMoney (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    studentUuid text REFERENCES student(Uuid),
    otherMoneyUuid text,
    term text,
    year text
   
);
\COPY studentOtherMoney(uuid,studentUuid,otherMoneyUuid,term,year) FROM '/tmp/studentOtherMoney.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE studentOtherMoney OWNER TO prim;




-- -------------------
-- Table  revertedMoney
-- -------------------

CREATE TABLE  revertedMoney (
    Id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    studentUuid text REFERENCES student(uuid),
    otherMoneyUuid text REFERENCES otherMoney(uuid),
    term text,
    year text
  
);
\COPY revertedMoney(uuid,studentUuid,otherMoneyUuid,term,year) FROM '/tmp/revertedMoney.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE revertedMoney OWNER TO prim;



-- -------------------
-- Table pocketMoney
-- -------------------


CREATE TABLE  pocketMoney (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    studentUuid text REFERENCES student(uuid),
    balance Integer NOT NULL CHECK (balance>=0)
   
);
\COPY pocketMoney(uuid,studentUuid,balance) FROM '/tmp/pocketMoney.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE pocketMoney OWNER TO prim;



-- -------------------
-- Table withdraw
-- -------------------


CREATE TABLE  withdraw (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    studentUuid text REFERENCES student(uuid),
    term text,
    year text,
    amountWithdrawn Integer NOT NULL CHECK (amountWithdrawn>=0),
    dateWithdrawn timestamp with time zone DEFAULT now() 
   
);
\COPY withdraw(uuid,studentUuid,term,year,amountWithdrawn,dateWithdrawn) FROM '/tmp/withdraw.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE withdraw OWNER TO prim;



-- -------------------
-- Table deposit
-- -------------------


CREATE TABLE  deposit (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    studentUuid text REFERENCES student(uuid),
    term text,
    year text,
    amountDeposited Integer NOT NULL CHECK (amountDeposited>=0),
    dateDeposited timestamp with time zone DEFAULT now()
   
);
\COPY deposit(uuid,studentUuid,term,year,amountDeposited,dateDeposited) FROM '/tmp/deposit.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE deposit OWNER TO prim;



-- ==================
-- ==================
-- .8 Library Management
-- ==================
-- ==================
-- -------------------
-- Table  book
-- -------------------

CREATE TABLE  book (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountUuid text REFERENCES Account(Uuid),
    ISBN text UNIQUE NOT NULL,
    author text,
    publisher text,
    title text,
    bookStatus text,
    borrowStatus text,
    bookcost Integer

);
\COPY book(uuid,accountUuid,ISBN,author,publisher,title,bookStatus,borrowStatus,bookcost) FROM '/tmp/book.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE book OWNER TO prim;


-- -------------------
-- Table  studentBook
-- -------------------

CREATE TABLE  studentBook (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    studentUuid text REFERENCES student(uuid),
    bookUuid text REFERENCES book(uuid),
    borrowStatus text,
    borrowDate timestamp with time zone DEFAULT now(),
    term text,
    year text,
    returnDate text
   

);
\COPY studentBook(uuid,studentUuid,bookUuid,borrowStatus,borrowDate,term,year,returnDate) FROM '/tmp/studentBook.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE studentBook OWNER TO prim;
