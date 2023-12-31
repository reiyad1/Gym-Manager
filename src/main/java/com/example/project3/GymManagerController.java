package com.example.project3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This is the controller file for Gym Manager. We are connecting our GUI buttons to its corresponding functionality.
 * * @author Reiya Dave, Ifrah Sajjad
 */

public class GymManagerController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    //Text Areas
    @FXML
    private TextArea ta;

    //membership tab
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField membDob;
    @FXML
    private TextField membLoc;

    //Fitness class tab
    @FXML
    private TextField fcFirstName;
    @FXML
    private TextField fcLastName;
    @FXML
    private TextField fcMembDob;
    @FXML
    private TextField classname;
    @FXML
    private TextField classInstructor;
    @FXML
    private TextField classLocation;

    //membership tab buttons
    @FXML
    private RadioButton familyButton;
    @FXML
    private RadioButton standardButton;
    @FXML
    private RadioButton premiumButton;
    @FXML
    private HBox addRemoveButton;

    //fitnessclass buttons
    @FXML
    private RadioButton guestButton;
    @FXML
    private RadioButton noGuestButton;
    @FXML
    private HBox addRemoveButton2;


    //information hub menus
    @FXML
    private ChoiceBox <String> membDatabaseMenu;
    @FXML
    private ChoiceBox <String> classScheduleMenu;
    @FXML
    private ChoiceBox <String> membFeeMenu;
    /**Initial MemberDatabase object*/
    MemberDatabase mainData = new MemberDatabase();
    ClassSchedule classSchedule = new ClassSchedule();
    /**Creates calendar object*/
    Calendar calendar = Calendar.getInstance(); // creating calendar object

//    ObservableList<String> membDataBaseList = FXCollections.observableArrayList("Print by member", "Load member list from file");
//    ObservableList<String> classDataList = FXCollections.observableArrayList("Show all classes", "Load class schedule by text file");
//    ObservableList<String> membFeeList = FXCollections.observableArrayList("Current Bill");
//
//    @FXML
//    private void initialize(){
//        membDatabaseMenu.setItems(membDataBaseList);
//        classScheduleMenu.setItems(classDataList);
//        membFeeMenu.setItems(membFeeList);
//    }

    /***
     * Method that check the location of the member and sets the location, else it's invalid
     * @return boolean
     * @param tempLoc is the temporary location
     * @param memb is the member being inputted
     */
    public boolean locSetting(String tempLoc, Member memb){
        if (tempLoc.toLowerCase().equals("bridgewater")){
            memb.setLocation(Location.BRIDGEWATER);
        }
        else if (tempLoc.toLowerCase().equals("edison")){
            memb.setLocation(Location.EDISON);
        }
        else if (tempLoc.toLowerCase().equals("piscataway")){
            memb.setLocation(Location.PISCATAWAY);
        }
        else if (tempLoc.toLowerCase().equals("somerville")){
            memb.setLocation(Location.SOMERVILLE);
        }
        else if (tempLoc.toLowerCase().equals("franklin")){
            memb.setLocation(Location.FRANKLIN);
        }
        else{
            //System.out.println(tempLoc + ": invalid location!");
            ta.appendText("\n" + tempLoc + ": invalid location!");
            return false;
        }
        return true;
    }

    //membership tab
    /**
     * Method for button conditions. This includes buttons for selecting membership type.
     * @param event
     * */
    @FXML
    void getAddMember(MouseEvent event){
        if (familyButton.isSelected()){
            //call family add
            commandAF();
        }
        else if (standardButton.isSelected()){
            //call standard add
            commandA();
        }
        else if (premiumButton.isSelected()){
            //call premium add
            commandAP();
        }
        else{
            //error, membership type needs to be selected
            ta.appendText("\nError: Membership type needs to be selected.");
        }
    }

    /**
     * Method to remove member from database from remove button.
     * @param event
     * */
    @FXML
    void getRemoveMember(MouseEvent event){
        //call commandR function
        commandR();
    }

    //fitnessclass tab
    /**
     * Method to add a guest, no guest, or send an error if neither button is selected.
     * @param event
     * */
    @FXML
    void getFcAddMember(MouseEvent event){
        if (guestButton.isSelected()){
            //call guest add
            commandCG();;
        }
        else if (noGuestButton.isSelected()){
            //call no guest add
            commandC();
        }
        else{
            //error, guest or no guest needs to be selected
            ta.appendText("\nError: Guest type needs to be selected.");
        }
    }

    /**
     * Method to remove a guest, no guest, or send an error if neither is selected.
     * @param event
     * */
    @FXML
    void getFcRemoveMember(MouseEvent event){
        if (guestButton.isSelected()){
            //call guest remove
            commandDG();
        }
        else if (noGuestButton.isSelected()){
            //call no guest remove
            commandD();
        }
        else{
            //error, guest or no guest needs to be selected
            ta.appendText("\nError: Guest type needs to be selected.");
        }
    }



    /** Method to add member to gym member database.
     * Runs through necessary checks to see if member can be added, then adds member
     *
     * @return boolean
     */
    @FXML
    public boolean commandA() {
        Member memb = new Member();
        Date today = new Date();

        //insert try-catch here for reading all the inputs, catch if one of them cannot be read
        //change from token to reading text input of each thing

            //memb.setFname(firstName.getText());
            //memb.setLname(lastName.getText());
            Date tempDob = new Date();
            try {
                memb.setFname(firstName.getText());
                memb.setLname(lastName.getText());
                tempDob = new Date(membDob.getText());
                memb.setDob(tempDob);
                String tempLoc = membLoc.getText();
            }
            catch (NoSuchElementException e){
                ta.appendText("\nError: Please check that you have filled all boxes and correct date format (mm/dd/yyyy.)");
                return false;
            }

            //memb.setDob(tempDob);
            String tempLoc = membLoc.getText();


        Date bday18 = new Date("10/01/2004");
        /*StringTokenizer token = new StringTokenizer(input, " ");
        token.nextToken(); // skipping the initial letter

        memb.setFname(token.nextToken());
        memb.setLname(token.nextToken());
        Date tempDob = new Date(token.nextToken());
        memb.setDob(tempDob);

        //set location from token and check if in location enum
        String tempLoc = token.nextToken();*/
        if (locSetting(tempLoc, memb) == false){
            return false;
        }

        if (tempDob.isValid() == true) { // for dob
            if (tempDob.compareTo(today) == 1){
                //System.out.println("DOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": cannot be today or a future date!");
                ta.appendText("\nDOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": cannot be today or a future date!");
                return false;
            }
            if (tempDob.compareTo(bday18) == 1) {
                //System.out.println("DOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": must be 18 or older to join!");
                ta.appendText("\nDOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": must be 18 or older to join!");
                return false;
            }
        }
        else{
            //System.out.println("DOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": invalid calendar date!");
            ta.appendText("\nDOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": invalid calendar date!");
            return false;

        }

        Date expire = new Date();   //in parameter, date three months after current date
        expire.setDay(today.getDay());
        int expireMonth = 0;
        int expireYear = today.getYear();
        if (today.getMonth() > 9){
            expireMonth = 3 - (12-today.getMonth());
            expireYear++;
        }
        else{
            expireMonth = today.getMonth() + 3;
        }
        expire.setMonth(expireMonth);
        expire.setYear(expireYear);
        memb.setExpire(expire);
        if (mainData.add(memb) == false){
            //System.out.println(memb.getFname() + " " + memb.getLname() + " is already in the database.");
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " is already in the database.");
            return false;
        }

        //sets expiration date
        //System.out.println(memb.getFname() + " " + memb.getLname() + " added.");
        ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " added.");
        return true;
    }

    /***
     * Method that adds a member with the family membership to the member database and set to expire 3 months later
     * @return boolean
     *
     */
    @FXML
    public boolean commandAF() {
        Family fam = new Family();
        Date today = new Date();
        Date tempDob = new Date();
        String tempLoc = "";

        try{
            fam.setFname(firstName.getText());
            fam.setLname(lastName.getText());
            tempDob = new Date(membDob.getText());
            fam.setDob(tempDob);
            tempLoc = membLoc.getText();
        }
        catch (NoSuchElementException e){
            ta.appendText("\nError: Please check that you have filled all boxes and correct date format (mm/dd/yyyy.)");
            return false;
        }


        Date bday18 = new Date("10/01/2004");
        /*StringTokenizer token = new StringTokenizer(input, " ");
        token.nextToken(); // skipping the initial letter

        fam.setFname(token.nextToken());
        fam.setLname(token.nextToken());
        Date tempDob = new Date(token.nextToken());
        fam.setDob(tempDob);

        //set location from token and check if in location enum
        String tempLoc = token.nextToken();*/
        if (locSetting(tempLoc, fam) == false){
            return false;
        }

        if (tempDob.isValid() == true) { // for dob
            if (tempDob.compareTo(today) == 1){
                //System.out.println("DOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": cannot be today or a future date!");
                ta.appendText("\nDOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": cannot be today or a future date!");
                return false;
            }
            if (tempDob.compareTo(bday18) == 1) {
                //System.out.println("DOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": must be 18 or older to join!");
                ta.appendText("\nDOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": must be 18 or older to join!");
                return false;
            }
        }
        else{
            //System.out.println("DOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": invalid calendar date!");
            ta.appendText("\nDOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": invalid calendar date!");
            return false;

        }

        Date expire = new Date();   //in parameter, date three months after current date
        expire.setDay(today.getDay());
        int expireMonth = 0;
        int expireYear = today.getYear();
        if (today.getMonth() > 9){
            expireMonth = 3 - (12-today.getMonth());
            expireYear++;
        }
        else{
            expireMonth = today.getMonth() + 3;
        }
        expire.setMonth(expireMonth);
        expire.setYear(expireYear);
        fam.setExpire(expire);
        if (mainData.add(fam) == false){
            //System.out.println(fam.getFname() + " " + fam.getLname() + " is already in the database.");
            ta.appendText("\n" + fam.getFname() + " " + fam.getLname() + " is already in the database.");
            return false;
        }
        fam.setIsFamMembership(true);
        fam.setGuestPass(1);
        //System.out.println(fam.getFname() + " " + fam.getLname() + " added.");
        ta.appendText("\n" + fam.getFname() + " " + fam.getLname() + " added.");
        return true;
    }

    /***
     * Method that adds a member with the premium membership to the member database.
     * The expiration date will be set to expire one year later.
     * @return boolean
     */
    @FXML
    public boolean commandAP() {
        Premium prem = new Premium();
        Date today = new Date();
        Date tempDob = new Date();
        String tempLoc = "";

        try {
            prem.setFname(firstName.getText());
            prem.setLname(lastName.getText());
            tempDob = new Date(membDob.getText());
            prem.setDob(tempDob);
            tempLoc = membLoc.getText();
        }
        catch (NoSuchElementException e){
            ta.appendText("\nError: Please check that you have filled all boxes and correct date format (mm/dd/yyyy.)");
            return false;
        }

        Date bday18 = new Date("10/01/2004");
        /*StringTokenizer token = new StringTokenizer(input, " ");
        token.nextToken(); // skipping the initial letter

        prem.setFname(token.nextToken());
        prem.setLname(token.nextToken());
        Date tempDob = new Date(token.nextToken());
        prem.setDob(tempDob);

        //set location from token and check if in location enum
        String tempLoc = token.nextToken();*/
        if (locSetting(tempLoc, prem) == false){
            return false;
        }

        if (tempDob.isValid() == true) { // for dob
            if (tempDob.compareTo(today) == 1){
                //System.out.println("DOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": cannot be today or a future date!");
                ta.appendText("\nDOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": cannot be today or a future date!");
                return false;
            }
            if (tempDob.compareTo(bday18) == 1) {
                //System.out.println("DOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": must be 18 or older to join!");
                ta.appendText("\nDOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": must be 18 or older to join!");
                return false;
            }
        }
        else{
            //System.out.println("DOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": invalid calendar date!");
            ta.appendText("\nDOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": invalid calendar date!");
            return false;

        }

        Date expire = new Date();   //in parameter, date three months after current date
        expire.setDay(today.getDay());
        int expireMonth = 0;
        int expireYear = today.getYear() + 1;
        expire.setMonth(expireMonth);
        expire.setYear(expireYear);
        prem.setExpire(expire);
        //do something to indicate that its premium membership
        if (mainData.add(prem) == false){
            //System.out.println(prem.getFname() + " " + prem.getLname() + " is already in the database.");
            ta.appendText("\n" + prem.getFname() + " " + prem.getLname() + " is already in the database.");
            return false;
        }
        prem.setIsPremMembership(true);
        prem.setGuestPass(3);
        //System.out.println(prem.getFname() + " " + prem.getLname() + " added.");
        ta.appendText("\n" + prem.getFname() + " " + prem.getLname() + " added.");
        return true;
    }

    /** Method to remove member from gym member database.
     * Runs through necessary checks to see if member can be added, then adds member
     * @return boolean
     *
     */
    @FXML
    public boolean commandR() {
        Member memb = new Member();
        Date tempDob = new Date();

        try {
            memb.setFname(firstName.getText());
            memb.setLname(lastName.getText());
            tempDob = new Date(membDob.getText());
            memb.setDob(tempDob);
        }
        catch (NoSuchElementException e){
            ta.appendText("\nError: Please check that you have filled all boxes and correct date format (mm/dd/yyyy.)");
            return false;
        }

        /*StringTokenizer token = new StringTokenizer(input, " ");

        token.nextToken();
        memb.setFname(token.nextToken());
        memb.setLname(token.nextToken());
        Date tempDob = new Date(token.nextToken());
        memb.setDob(tempDob);*/
        if (mainData.remove(memb) == true) {
            //System.out.println(memb.getFname() + " " + memb.getLname() + " removed.");
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " removed.");
        } else {
            //System.out.println(memb.getFname() + " " + memb.getLname() + " is not in database.");
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " is not in database.");
        }
        return true;
    }
    /**Method to print the contents of the gym member database.*/
    @FXML
    public void commandP() {
        ta.appendText("\n" + mainData.print());
    }

    /**Method to print the contents of the gym member database, ordered by county and zipcode.
     */
    @FXML
    public void commandPC() {
        ta.appendText("\n" + mainData.printByCounty());
    }

    /**Method to print the contents of the gym member database, ordered by last name, then first name.
     */
    @FXML
    public void commandPN() {
        ta.appendText("\n" + mainData.printByName());
    }

    /**Method to print the contents of the gym member database, ordered by expiration date.*/
    @FXML
    public void commandPD() {
        ta.appendText("\n" + mainData.printByExpirationDate());
    }

    /**Method to print the list of members with the membership fees. */
    @FXML
    public void commandPF() {
        ta.appendText("\n" + mainData.printByMembershipFee());
    }

    /**Method to print the contents of fitness classes.*/
    @FXML
    public void commandS() {
        //check if class schedule is empty
        if (classSchedule.getNumClasses() == 0) {
            //System.out.println("Fitness Class schedule is empty.");
            ta.appendText("Fitness Class schedule is empty.");
            return;
        }
        System.out.println("-Fitness Classes-");
        for (int i = 0; i < classSchedule.getNumClasses(); i++) {
            //System.out.println(classSchedule.getFitnessClass()[i].toClassString());
            ta.appendText("\n" + classSchedule.getFitnessClass()[i].toClassString());
            ta.appendText("\n" + classSchedule.getFitnessClass()[i].printClass());

        }
        //System.out.println("-end of class list-");
        ta.appendText("\n-end of class list-");
    }

    /**
     * Method to check a member into fitness class, doing the necessary checks to ensure that member can be checked in.
     * Checks for time conflict, date validity, and whether a member is in database or not.
    */
    @FXML
    public void commandC() {
        Member memb = new Member();
        FitnessClass tempClass = new FitnessClass();
        Date today = new Date();
        Date tempDob = new Date();
        String className = "";
        String instructor = "";
        String classLoc = "";

        try {
            className = classname.getText();
            instructor = classInstructor.getText();
            classLoc = classLocation.getText();
            memb.setFname(fcFirstName.getText());
            memb.setLname(fcLastName.getText());
            tempDob = new Date(fcMembDob.getText());
            memb.setDob(tempDob);
        }
        catch (NoSuchElementException e){
            ta.appendText("\nError: Please check that you have filled all boxes and correct date format (mm/dd/yyyy.)");
            return;
        }


        className = className.substring(0, 1).toUpperCase() + className.substring(1);
        tempClass.setClassName(className);
        tempClass.setInstructor(instructor);
        if (classLoc.toLowerCase().equals("bridgewater")){
            tempClass.setClassLocation(Location.BRIDGEWATER);
        }
        else if (classLoc.toLowerCase().equals("edison")){
            tempClass.setClassLocation(Location.EDISON);
        }
        else if (classLoc.toLowerCase().equals("piscataway")){
            tempClass.setClassLocation(Location.PISCATAWAY);
        }
        else if (classLoc.toLowerCase().equals("somerville")){
            tempClass.setClassLocation(Location.SOMERVILLE);
        }
        else if (classLoc.toLowerCase().equals("franklin")){
            tempClass.setClassLocation(Location.FRANKLIN);
        }
        else{
            System.out.println(classLoc + ": invalid location!");
            return;
        }
        /*memb.setFname(fcFirstName.getText());
        memb.setLname(fcLastName.getText());
        Date tempDob = new Date(fcMembDob.getText());
        memb.setDob(tempDob);*/


        //for checking in the member after all of the checks
        int index = classSchedule.find(tempClass);
        if (index == -1){
            //System.out.println(className + " by " + instructor + " does not exist at " + classLoc);
            ta.appendText("\n" + className + " by " + instructor + " does not exist at " + classLoc);
            return;
        }
        else if (index == -2){
            System.out.println(className + " - class does not exist.");
            ta.appendText("\n" + className + " - class does not exist.");
            return;
        }
        else if (index == -3){
            //System.out.println(instructor + " - instructor does not exist.");
            ta.appendText("\n" + instructor + " - instructor does not exist.");
            return;
        }
        else if (index == -4){
            //System.out.println(classLoc + " - invalid location.");
            ta.appendText("\n" + classLoc + " - invalid location.");
            return;
        }


        //checks if the date of birth is invalid
        if(tempDob.isValid() != true){
            //System.out.println("DOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + " : invalid calendar date!");
            ta.appendText("\nDOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + " : invalid calendar date!");
            return;
        }

        //checks if member is in database, gets member if not
        //CHECK AND MAKE SURE THIS IS RIGHT
        if (mainData.pubFind(memb) != null){
            if (mainData.pubFind(memb) instanceof Family && mainData.pubFind(memb) instanceof Premium == false){
                memb = (Family) mainData.pubFind(memb);

            }
            else if (mainData.pubFind(memb) instanceof Premium){
                memb = (Premium) mainData.pubFind(memb);

            }
            else{
                memb = mainData.pubFind(memb);
            }
        }
        else{
            //System.out.println(memb.getFname() + " " + memb.getLname() + " " + memb.getDob().getMonth() + "/" + memb.getDob().getDay() + "/" + memb.getDob().getYear() + " is not in database.");
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " " + memb.getDob().getMonth() + "/" + memb.getDob().getDay() + "/" + memb.getDob().getYear() + " is not in database.");
            return;
        }

        //checks if expiration date has already passed
        if (memb.getExpire().compareTo(today) == -1){
            //System.out.println(memb.getFname() + " " + memb.getLname() + " " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() +" membership expired.");
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() +" membership expired.");
            return;
        }

        if (memb instanceof Family == false && memb instanceof Premium == false){
            if (!(memb.getLocation().getTownship().equals(tempClass.getClassLocation().getTownship()))){
                //System.out.println(memb.getFname() + " " + memb.getLname() + " checking in " + tempClass.getClassLocation().toString()
                        //+ " - standard membership restriction.");
                ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " checking in " + tempClass.getClassLocation().toString()
                        + " - standard membership restriction.");
                return;
            }
        }

        tempClass = classSchedule.getFitnessClass()[index];
        //make checks to see if member has already checked in or not
        if (tempClass.find(memb) == -1){
            int tempHr = tempClass.getClassTime().getHour();
            int tempMin = tempClass.getClassTime().getMin();
            for (int i = 0; i < classSchedule.getNumClasses(); i++){
                if (tempHr == classSchedule.getFitnessClass()[i].getClassTime().getHour() && tempMin == classSchedule.getFitnessClass()[i].getClassTime().getMin()){
                    if (classSchedule.getFitnessClass()[i].find(memb) != -1){
                        //System.out.println("Time conflict - " + classSchedule.getFitnessClass()[i].toClassString());
                        ta.appendText("\nTime conflict - " + classSchedule.getFitnessClass()[i].toClassString());
                        return;
                    }
                }
            }
            tempClass.addMember(memb);
            //System.out.println(memb.getFname() + " " + memb.getLname() + " checked in " + tempClass.toClassString());
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " checked in " + tempClass.toClassString());
            return;
        }
        else{
            //System.out.println(memb.getFname() + " " + memb.getLname() +  " has already checked in " + className + ".");
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() +  " has already checked in " + className + ".");
            return;
        }



    }

    /** Method that does family guest check-in for a fitness class; must keep track of the remaining number of guest passes.
     * */
    @FXML
    public void commandCG() {
        Member memb = new Member(); //since family and premium variables have instance variables, check the instance variables of those guest passes
        FitnessClass tempClass = new FitnessClass();
        Date today = new Date();
        Date tempDob = new Date();
        String className = "";
        String instructor = "";
        String classLoc = "";

        try {
            className = classname.getText();
            instructor = classInstructor.getText();
            classLoc = classLocation.getText();
            memb.setFname(fcFirstName.getText());
            memb.setLname(fcLastName.getText());
            tempDob = new Date(fcMembDob.getText());
            memb.setDob(tempDob);

        }
        catch (NoSuchElementException e){
            ta.appendText("\nError: Please check that you have filled all boxes and correct date format (mm/dd/yyyy.)");
            return;
        }



        className = className.substring(0, 1).toUpperCase() + className.substring(1);
        tempClass.setClassName(className);
        tempClass.setInstructor(instructor);
        if (classLoc.toLowerCase().equals("bridgewater")){
            tempClass.setClassLocation(Location.BRIDGEWATER);
        }
        else if (classLoc.toLowerCase().equals("edison")){
            tempClass.setClassLocation(Location.EDISON);
        }
        else if (classLoc.toLowerCase().equals("piscataway")){
            tempClass.setClassLocation(Location.PISCATAWAY);
        }
        else if (classLoc.toLowerCase().equals("somerville")){
            tempClass.setClassLocation(Location.SOMERVILLE);
        }
        else if (classLoc.toLowerCase().equals("franklin")){
            tempClass.setClassLocation(Location.FRANKLIN);
        }
        else{
            //System.out.println(classLoc + ": invalid location!");
            ta.appendText("\n" + classLoc + ": invalid location!");
            return;
        }
        /*memb.setFname(fcFirstName.getText());
        memb.setLname(fcLastName.getText());
        Date tempDob = new Date(fcMembDob.getText());*/



        //check if member is in mList
        if (mainData.pubFind(memb) != null){
            if (mainData.pubFind(memb) instanceof Family && mainData.pubFind(memb) instanceof Premium == false){
                memb = (Family) mainData.pubFind(memb);
            }
            else if (mainData.pubFind(memb) instanceof Premium){
                memb = (Premium) mainData.pubFind(memb);
            }
            else{
                //System.out.println("Standard membership - guest check-in is not allowed.");
                ta.appendText("\nStandard membership - guest check-in is not allowed.");
                return;

            }
        }
        else{
            //System.out.println(memb.getFname() + " " + memb.getLname() + " " + memb.getDob().getMonth() + "/" + memb.getDob().getDay() + "/" + memb.getDob().getYear() + " is not in database.");
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " " + memb.getDob().getMonth() + "/" + memb.getDob().getDay() + "/" + memb.getDob().getYear() + " is not in database.");
            return;
        }

        //check class location with members location
        if (!memb.getLocation().getTownship().equals(tempClass.getClassLocation().getTownship())){
            //System.out.println(memb.getFname() + " " + memb.getLname() + " Guest checking in " + tempClass.getClassLocation().toString()
                    //+ " - guest location restriction.");
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " Guest checking in " + tempClass.getClassLocation().toString()
                    + " - guest location restriction.");
            return;
        }

        //finds and sets tempClass to the class from classSchedule
        int index = classSchedule.find(tempClass);
        tempClass = classSchedule.getFitnessClass()[index];


        //checks in guest- first check number of guestpasses left
        if (memb.getGuestPass() > 0){
            tempClass.addGuest(memb);
            memb.setGuestPass(memb.getGuestPass()-1);
            //System.out.println(memb.getFname() + " " + memb.getLname() + " (guest) checked in " + tempClass.toClassString());
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " (guest) checked in " + tempClass.toClassString());
            tempClass.printClass();
            return;
        }
        else{
            //System.out.println(memb.getFname() + " " + memb.getLname() + " ran out of guest passes");
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " ran out of guest passes");
            return;
        }

    }

    /** Method to delete a member from a fitness class, doing the necessary checks to ensure that member can be removed from a fitness class.
     * Checks for date validity and class validity.
     * */
    @FXML
    public void commandD() {
        Member memb = new Member();
        FitnessClass tempClass = new FitnessClass();
        Date tempDob = new Date();
        String className = "";
        String instructor = "";
        String classLoc = "";

        try {
            className = classname.getText();
            instructor = classInstructor.getText();
            classLoc = classLocation.getText();
            memb.setFname(fcFirstName.getText());
            memb.setLname(fcLastName.getText());
            tempDob = new Date(fcMembDob.getText());
            memb.setDob(tempDob);
        }
        catch (NoSuchElementException e){
            ta.appendText("\nError: Please check that you have filled all boxes and correct date format (mm/dd/yyyy.)");
            return;
        }

        className = className.substring(0, 1).toUpperCase() + className.substring(1);
        tempClass.setClassName(className);
        tempClass.setInstructor(instructor);
        if (classLoc.toLowerCase().equals("bridgewater")){
            tempClass.setClassLocation(Location.BRIDGEWATER);
        }
        else if (classLoc.toLowerCase().equals("edison")){
            tempClass.setClassLocation(Location.EDISON);
        }
        else if (classLoc.toLowerCase().equals("piscataway")){
            tempClass.setClassLocation(Location.PISCATAWAY);
        }
        else if (classLoc.toLowerCase().equals("somerville")){
            tempClass.setClassLocation(Location.SOMERVILLE);
        }
        else if (classLoc.toLowerCase().equals("franklin")){
            tempClass.setClassLocation(Location.FRANKLIN);
        }
        else{
            //System.out.println(classLoc + ": invalid location!");
            ta.appendText("\n" + classLoc + ": invalid location!");
            return;
        }
        /*memb.setFname(fcFirstName.getText());
        memb.setLname(fcLastName.getText());
        Date tempDob = new Date(fcMembDob.getText());
        memb.setDob(tempDob);*/


        //checks validity of dob
        if (memb.getDob().isValid() == false){
            //error message that dob is invalid
            //System.out.println("DOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": invalid calendar date!");
            ta.appendText("\nDOB " + tempDob.getMonth() + "/" + tempDob.getDay() + "/" + tempDob.getYear() + ": invalid calendar date!");
            return;
        }
        //check if member is in mList
        if (mainData.pubFind(memb) != null){
            if (mainData.pubFind(memb) instanceof Family && mainData.pubFind(memb) instanceof Premium == false){
                memb = (Family) mainData.pubFind(memb);
            }
            else if (mainData.pubFind(memb) instanceof Premium){
                memb = (Premium) mainData.pubFind(memb);
            }
            else{
                memb = mainData.pubFind(memb);
            }
        }
        else{
            //System.out.println(memb.getFname() + " " + memb.getLname() + " " + memb.getDob().getMonth() + "/" + memb.getDob().getDay() + "/" + memb.getDob().getYear() + " is not in database.");
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " " + memb.getDob().getMonth() + "/" + memb.getDob().getDay() + "/" + memb.getDob().getYear() + " is not in database.");
            return;
        }

        //search for class in classschedule- if not there then error message
        int index = classSchedule.find(tempClass);
        if (index == -1){
            //System.out.println(className + " by " + instructor + " does not exist at " + classLoc);
            ta.appendText("\n" + className + " by " + instructor + " does not exist at " + classLoc);
            return;
        }
        else if (index == -2){
            //System.out.println(className + " - class does not exist.");
            ta.appendText("\n" + className + " - class does not exist.");
            return;
        }
        else if (index == -3){
            //System.out.println(instructor + " - instructor does not exist.");
            ta.appendText("\n" + instructor + " - instructor does not exist.");
            return;
        }
        else if (index == -4){
            //System.out.println(classLoc + " - invalid location.");
            ta.appendText("\n" + classLoc + " - invalid location.");
            return;
        }
        tempClass = classSchedule.getFitnessClass()[index];
        //if removeMember = false, then return error that memb is not in class
        //else, drop person from class

        if (tempClass.removeMember(memb) == false){
            //System.out.println(memb.getFname() + " " + memb.getLname() + " did not check in.");
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " did not check in.");
        }
        else{
            //System.out.println(memb.getFname() + " " + memb.getLname() + " is done with class.");
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " is done with class.");
        }


    }

    /**
     * Method that keeps track of the remaining number of guest passes after a guest is done with a fitness class and checks out
     * */
    @FXML
    public void commandDG() {
        Member memb = new Member(); //since family and premium variables have instance variables, check the instance variables of those guest passes
        FitnessClass tempClass = new FitnessClass();
        Date tempDob = new Date();
        String className = "";
        String instructor = "";
        String classLoc = "";

        try {
            className = classname.getText();
            instructor = classInstructor.getText();
            classLoc = classLocation.getText();
            memb.setFname(fcFirstName.getText());
            memb.setLname(fcLastName.getText());
            tempDob = new Date(fcMembDob.getText());
            memb.setDob(tempDob);
        }
        catch (NoSuchElementException e){
            ta.appendText("\nError: Please check that you have filled all boxes and correct date format (mm/dd/yyyy.)");
            return;
        }

        className = className.substring(0, 1).toUpperCase() + className.substring(1);
        tempClass.setClassName(className);
        tempClass.setInstructor(instructor);
        if (classLoc.toLowerCase().equals("bridgewater")){
            tempClass.setClassLocation(Location.BRIDGEWATER);
        }
        else if (classLoc.toLowerCase().equals("edison")){
            tempClass.setClassLocation(Location.EDISON);
        }
        else if (classLoc.toLowerCase().equals("piscataway")){
            tempClass.setClassLocation(Location.PISCATAWAY);
        }
        else if (classLoc.toLowerCase().equals("somerville")){
            tempClass.setClassLocation(Location.SOMERVILLE);
        }
        else if (classLoc.toLowerCase().equals("franklin")){
            tempClass.setClassLocation(Location.FRANKLIN);
        }
        else{
            //System.out.println(classLoc + ": invalid location!");
            ta.appendText("\n" + classLoc + ": invalid location!");
            return;
        }
        /*memb.setFname(fcFirstName.getText());
        memb.setLname(fcLastName.getText());
        Date tempDob = new Date(fcMembDob.getText());
        memb.setDob(tempDob);*/


        if (mainData.pubFind(memb) != null){
            if (mainData.pubFind(memb) instanceof Family && mainData.pubFind(memb) instanceof Premium == false){
                memb = (Family) mainData.pubFind(memb);
            }
            else if (mainData.pubFind(memb) instanceof Premium){
                memb = (Premium) mainData.pubFind(memb);
            }
            else{
                memb = mainData.pubFind(memb);
            }
        }
        else{
            //System.out.println(memb.getFname() + " " + memb.getLname() + " " + memb.getDob().getMonth() + "/" + memb.getDob().getDay() + "/" + memb.getDob().getYear() + " is not in database.");
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " " + memb.getDob().getMonth() + "/" + memb.getDob().getDay() + "/" + memb.getDob().getYear() + " is not in database.");
            return;
        }

        int index = classSchedule.find(tempClass);
        tempClass = classSchedule.getFitnessClass()[index];
        if (tempClass.removeGuest(memb) == false){
            //System.out.println("Guest not checked in.");
            ta.appendText("\nGuest not checked in.");
            return;
        }
        else{
            //System.out.println(memb.getFname() + " " + memb.getLname() + " Guest done with the class.");
            ta.appendText("\n" + memb.getFname() + " " + memb.getLname() + " Guest done with the class.");
            memb.setGuestPass(memb.getGuestPass()+1);
            return;
        }

    }

    /***
     * Method used to load the fitness class schedule from the file classSchedule.txt to the class schedule in the software system
     */
    @FXML
    public void commandLS() {
        String result = classSchedule.readFile();
        //append result to text area
        ta.appendText(result);
        commandS();

    }

    /**
     * Method that loads a list of members from the file memberList.txt to the member database
     *
     * */
    @FXML
    public void commandLM() {
        ta.appendText(mainData.loadMembers());

    }


}

