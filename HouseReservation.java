import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HouseReservation extends Reservation {

    private int numFloors;

    static String newReservationNum;


    // update house reservation data using passed in parameters
    public HouseReservation(String lodAddr, String mailAddr, String resStartDate, String resEndDate, int numberNights, int numberBeds, int numberBedrooms, int numberBath, int lodSize, int numberFloors, String resStatus, String accNum) throws JSONException, IOException {
    	/*
	   * Validate parameters 
       * Validate that resStartDate and resEndDate are not the same, throw SameReservationDate if they are the same
       * Assign parameter's values to attributes
	   */
        super();
        // check if the resStartData and resEndDate are the same
        if (Objects.equals(resStartDate, resEndDate)) {
            throw new SameReservationDate(resStartDate);
        }

        // check if the lodAddr and mailAddr are different
        if ((mailAddr != null) && (!mailAddr.equals(lodAddr))) {
            throw new MismatchedAddresses(lodAddr);
        }

        Random rnd = new Random();
        int number = rnd.nextInt(99999999);
        newReservationNum = "O" + String.valueOf(number);

        reservationNum = newReservationNum;
        lodgingAddress = lodAddr;
        mailingAddress = mailAddr;
        reservationStartDate = resStartDate;
        reservationEndDate = resEndDate;
        numNights = numberNights;
        numBeds = numberBeds;
        numBedrooms = numberBedrooms;
        numBathrooms = numberBath;
        numFloors = numberFloors;
        lodgingSize = lodSize;
        reservationStatus = resStatus;
        accountNumber = accNum;

        String accountPath = "C:\\tmp-umgc\\accounts\\" + accountNumber;
        JSONObject toPutInFile = new JSONObject();
        toPutInFile.put("reservationNum", newReservationNum);
        toPutInFile.put("lodgingAddress", lodAddr);
        toPutInFile.put("mailingAddress", mailAddr);
        toPutInFile.put("reservationStartDate", resStartDate);
        toPutInFile.put("reservationEndDate", resEndDate);
        toPutInFile.put("numNights", numberNights);
        toPutInFile.put("numFloors", numberFloors);
        toPutInFile.put("numBeds", numberBeds);
        toPutInFile.put("numBedrooms", numberBedrooms);
        toPutInFile.put("numBathrooms", numberBath);
        toPutInFile.put("lodgingSize", lodSize);
        toPutInFile.put("reservationStatus", resStatus);
        toPutInFile.put("accountNumber", accNum);


        File accountJson = new File(accountPath + "\\" + newReservationNum + ".json");
        accountJson.createNewFile();
        FileWriter writer = new FileWriter(accountJson.getAbsolutePath());
        writer.write(toPutInFile.toString());
        writer.close();
        System.out.println("New House Reservation created: " + newReservationNum);
    }
  
    //create and return a copy of the object
    public HouseReservation clone() {
        /*
	   * new HouseReservation(this.resNum, ...)
	   */        
        return null;
    }

    public int getNumFloors() {
        return numFloors;
    }

    public void setNumFloors(int floors) {
        this.numFloors = floors;
    }

    // formats and returns data in json format
    public String toString() {
        /*
         * return "<HotelReservation paramaters and attributes>"
         */
        return "<House>" + "\n" +
                "<accountNumber>" + accountNumber + "</accountNumber>" + "\n" +
                "<reservationNum>" + reservationNum + "</reservationNum>" + "\n" +
                "<lodgingAddress>" + lodgingAddress + "</lodgingAddress>" + "\n" +
                "<mailingAddress>" + mailingAddress + "</mailingAddress" + "\n" +
                "<reservationStartDate>" + reservationStartDate + "</reservationStartDate>" + "\n" +
                "<reservationEndDate>" + reservationEndDate + "</reservationEndDate>" + "\n" +
                "<numNights>" + numNights + "</numNights>" + "\n" +
                "<numBeds>" + numBeds + "</numBeds>" + "\n" +
                "<numBedrooms>" + numBedrooms + "</numBedrooms>" + "\n" +
                "<numBathrooms>" + numBathrooms + "</numBathrooms>" + "\n" +
                "<numFloors>" + numFloors + "</numFloors>" + "\n" +
                "<lodgingSize>" + lodgingSize + "</lodgingSize>" + "\n" +
                "<reservationStatus>" + reservationStatus + "</reservationStatus>" + "\n" +
                "</House>";
    }
}