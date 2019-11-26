-- This sql script sets up the database structure of our project.
-- Be careful, running this script will delete the current data base contents!

DROP DATABASE IF EXISTS FinalProject;

CREATE DATABASE FinalProject;

USE FinalProject; 

CREATE TABLE User(
  userID int not null primary key auto_increment,
  email varchar(20) not null,
  password varchar(20) not null,
  userType boolean not null
);

CREATE TABLE Student(
  studentID int(11) not null primary key,
  mac1 varchar(12) not null,
  mac2 varchar(12),
  schoolID int(11) not null,
  name varchar(40) not null,
  foreign key(studentID) references User(userID)
);

CREATE TABLE Instructor(
 instructorID int(11) not null primary key,
 name varchar(40) not null,
 foreign key(instructorID) references User(userID)
);

CREATE TABLE Course(
  courseID int(11) not null primary key auto_increment,
  courseName varchar(100) not null,
  instructorID int(11) not null,
  numGraceDays int(2),
  currentLectureNumber int(2) not null,
  foreign key(instructorID) references Instructor(instructorID)
);

CREATE TABLE Enrollment(
  enrollmentID int(11) not null primary key auto_increment,
  studentID int(11) not null,
  courseID int(11) not null,
  foreign key(studentID) references Student(studentID),
  foreign key(courseID) references Course(courseID)
);


CREATE TABLE Lecture(
  lectureID int(11) not null primary key auto_increment,
  courseID int(11) not null,
  instructorID int(11) not null,
  lectureStartTime datetime not null,
  lectureNumber int(2) not null,
  keyword varchar(10) not null,
  latitude double not null,
  longitude double not null,
  accuracy float not null
);

CREATE TABLE Attendance(
  attendanceID int(11) not null primary key auto_increment,
  studentID int(11) not null,
  courseID int(11) not null,
  prevLectureRating int(1),
  prescence boolean not null,
  lectureNumber int(2) not null,
  foreign key(studentID) references Student(studentID),
  foreign key(courseID) references Course(courseID)
);

CREATE TABLE Question(
  questionID int(11) not null primary key auto_increment,
  content long varchar not null,
  upvoteCount int(3) not null,
  timeCreated datetime not null,
  courseID int(11) not null,
  foreign key(courseID) references Course(courseID)
);

CREATE TABLE GraceDays(
  graceID int(11) not null primary key auto_increment,
  userID int(11) not null,
  numGraceDays int(2) not null,
  courseID int(11) not null,
  foreign key(courseID) references Course(courseID)
);
