
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
    schoolName text,
    schoolEmail text,
    schoolPhone text,
    schoolAddres text,
    schoolHomeTown text,
    schoolCounty text,
    schoolMotto text
);

\COPY Account(uuid,schoolName,schoolEmail,schoolPhone,schoolAddres,schoolHomeTown,schoolCounty,schoolMotto) FROM '/tmp/Account.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Account OWNER TO prim;



-- -------------------
-- house
----------------------
CREATE TABLE  house (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    houseName text
);

\COPY house(uuid,houseName) FROM '/tmp/house.csv' WITH DELIMITER AS '|' CSV HEADER
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
    streamName text
);
\COPY stream(uuid,streamName) FROM '/tmp/stream.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE stream OWNER TO prim;


-- -------------------
-- Table systemConfig
-- -------------------

CREATE TABLE  systemConfig (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    term text,
    year text,
    smsSend text,
    endTerm text,
    eTMidTerm text,
    examAll text,
    openningDate text,
    closingDate text
);
\COPY systemConfig(uuid,term,year,smsSend,endTerm,eTMidTerm,examAll,openningDate,closingDate) FROM '/tmp/systemConfig.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE systemConfig OWNER TO prim;



-- -------------------
-- Table Comment
-- -------------------

CREATE TABLE  Comment (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    term text,
    year text,
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
\COPY Comment(uuid,term,year,headTeacherCom,gradeAplaincom,gradeAminuscom,gradeBpluscom,gradeBplaincom,gradeBminuscom,gradeCpluscom,gradeCplaincom,gradeCminuscom,gradeDpluscom,gradeDplaincom,gradeDminuscom,gradeEcom) FROM '/tmp/Comment.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Comment OWNER TO prim;



-- =========================
-- 1.  Student's Management
-- =========================




-- -------------------
-- Table student
-- -------------------


CREATE TABLE  student (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
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
    admissiondate timestamp with time zone DEFAULT now()
);
\COPY student(uuid,statusUuid,admmissinNo,streamUuid,firstname,middlename,lastname,gender,dateofbirth,birthcertificateNo,country,county,ward,regTerm,regYear,finalTerm,finalYear,admissiondate) FROM '/tmp/student.csv' WITH DELIMITER AS '|' CSV HEADER
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
-- 1.  Subject's/Staff Management
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
    statusUuid text,
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
\COPY staff(uuid,statusUuid,name,phone,email,gender,dob,country,county,ward,regDate) FROM '/tmp/staff.csv' WITH DELIMITER AS '|' CSV HEADER
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
    teacherUuid text REFERENCES staff(uuid),
    streamUuid text REFERENCES stream(uuid)
   
);
\COPY classTeacher(uuid,teacherUuid,streamUuid) FROM '/tmp/classTeacher.csv' WITH DELIMITER AS '|' CSV HEADER
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
    uuid text UNIQUE NOT NULL,
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
\COPY performance(uuid,studentUuid,subjectUuid,streamUuid,classUuid,term,year,openner,midterm,endterm) FROM '/tmp/performance.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE performance OWNER TO prim;



-- -------------------
-- Table gradingSystem
-- -------------------
 CREATE TABLE  gradingSystem (
     Id SERIAL PRIMARY KEY,
     Uuid text UNIQUE NOT NULL,
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
\COPY gradingSystem(Uuid,gradeAplain,gradeAminus,gradeBplus,gradeBplain,gradeBminus,gradeCplus,gradeCplain,gradeCminus,gradeDplus,gradeDplain,gradeDminus,gradeE) FROM '/tmp/gradingSystem.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE gradingSystem OWNER TO prim;


-- -------------------
-- Table BarWeight
-- -------------------
 CREATE TABLE  BarWeight (
    Id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
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
    uuid text UNIQUE NOT NULL,
    studentUuid text REFERENCES student(Uuid),
    term text,
    year text,
    meanScore float  
   
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
    term text,
    year text,
    amount integer
   
);
\COPY termFee(uuid,term,year,amount) FROM '/tmp/termFee.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE termFee OWNER TO prim;


-- -------------------
-- Table otherMoney
-- -------------------


CREATE TABLE  otherMoney (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    description text, 
    term text,
    year text,
    amount integer
   
);
\COPY otherMoney(uuid,description,term,year,amount) FROM '/tmp/otherMoney.csv' WITH DELIMITER AS '|' CSV HEADER
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
    amountPaid integer
   
);
\COPY studentFee(uuid,studentUuid,term,year,amountPaid) FROM '/tmp/studentFee.csv' WITH DELIMITER AS '|' CSV HEADER
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
    balance Integer
   
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
    amountWithdrawn Integer,
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
    amountDeposited Integer,
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
    ISBN text UNIQUE NOT NULL,
    author text,
    publisher text,
    title text,
    bookStatus text,
    borrowStatus text,
    bookcost Integer

);
\COPY book(uuid,ISBN,author,publisher,title,bookStatus,borrowStatus,bookcost) FROM '/tmp/book.csv' WITH DELIMITER AS '|' CSV HEADER
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
