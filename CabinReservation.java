import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CabinReservation extends Reservation {

    private boolean hasFullKitchen;

    private boolean hasLoft;

    static String newReservationNum;


    // update cabin reservation data using passed in parameters
    public CabinReservation(String lodAddr, String mailAddr, String resStartDate, String resEndDate, int numberNights, int numberBeds, int numberBedrooms, int numberBath, int lodSize, boolean loft, boolean fullKitchen, String resStatus, String accNum) throws JSONException, IOException {
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
        newReservationNum = "C" + String.valueOf(number);

        reservationNum = newReservationNum;
        lodgingAddress = lodAddr;
        mailingAddress = mailAddr;
        reservationStartDate = resStartDate;
        reservationEndDate = resEndDate;
        numNights = numberNights;
        numBeds = numberBeds;
        numBedrooms = numberBedrooms;
        numBathrooms = numberBath;
        hasFullKitchen = fullKitchen;
        hasLoft = loft;
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
        toPutInFile.put("hasFullKitchen", fullKitchen);
        toPutInFile.put("hasLoft", loft);
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
        System.out.println("New Cabin Reservation created: " + newReservationNum);

    }
  
    //create and return a copy of the object
    public CabinReservation clone() {
       /*
	   * new CabinReservation(this.resNum, ...)
	   */
    return null;
    }

    // formats and returns data in json format
    @Override
    public String toString() {
        /*
         * return "<CabinReservation paramaters and attributes>"
         */
        return "<Cabin>" + "\n" +
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
                "<hasFullKitchen>" + hasFullKitchen + "</hasFullKitchen>" + "\n" +
                "<hasLoft>" + hasLoft + "</hasLoft>" + "\n" +
                "<lodgingSize>" + lodgingSize + "</lodgingSize>" + "\n" +
                "<reservationStatus>" + reservationStatus + "</reservationStatus>" + "\n" +
                "</Cabin>";
    }

    public boolean getHasFullKitchen() {
        return hasFullKitchen;
    }
  
    public void setHasFullKitchen(boolean kitchenette) {
        this.hasFullKitchen = kitchenette;
    }

    public boolean getHasLoft() {
        return hasLoft;
    }
  
    public void setHasLoft(boolean hasLoft) {
        this.hasLoft = hasLoft;
    }

}