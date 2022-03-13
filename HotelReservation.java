import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HotelReservation extends Reservation {

    private boolean hasKitchenette;

    static String newReservationNum;

    // update hotel reservation data using passed in parameters
    public HotelReservation(String lodAddr, String mailAddr, String resStartDate, String resEndDate, int numberNights, int lodSize, boolean kitchen, String resStatus, String accNum) throws JSONException, IOException {
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
        newReservationNum = "H" + String.valueOf(number);

        reservationNum = newReservationNum;
        lodgingAddress = lodAddr;
        mailingAddress = mailAddr;
        reservationStartDate = resStartDate;
        reservationEndDate = resEndDate;
        numNights = numberNights;
        lodgingSize = lodSize;
        reservationStatus = resStatus;
        accountNumber = accNum;
        hasKitchenette = kitchen;

        String accountPath = "C:\\tmp-umgc\\accounts\\" + accountNumber;
        JSONObject toPutInFile = new JSONObject();
        toPutInFile.put("reservationNum", newReservationNum);
        toPutInFile.put("lodgingAddress", lodAddr);
        toPutInFile.put("mailingAddress", mailAddr);
        toPutInFile.put("reservationStartDate", resStartDate);
        toPutInFile.put("reservationEndDate", resEndDate);
        toPutInFile.put("numNights", numberNights);
        toPutInFile.put("hasKitchenette", kitchen);
        toPutInFile.put("numBeds", 2); // 2 beds only
        toPutInFile.put("numBedrooms", 1); // 1 bedroom only
        toPutInFile.put("numBathrooms", 1); // 1 bathroom only
        toPutInFile.put("lodgingSize", lodSize);
        toPutInFile.put("reservationStatus", resStatus);
        toPutInFile.put("accountNumber", accNum);

        File accountJson = new File(accountPath + "\\" + newReservationNum + ".json");
        accountJson.createNewFile();
        FileWriter writer = new FileWriter(accountJson.getAbsolutePath());
        writer.write(toPutInFile.toString());
        writer.close();
        System.out.println("New Hotel Reservation created: " + newReservationNum);
    }
      
    //create and return a copy of the object
    public HotelReservation clone() {
        /*
	   * new HotelReservation(this.resNum, ...);
	   */
    return null;
    }

    public boolean getHasKitchenette() {
        return hasKitchenette;
    }

    public void setHasKitchenette(boolean kitchenette) {
        this.hasKitchenette = kitchenette;
    }

    // formats and returns data in json format
    @Override
    public String toString() {
        /*
         * return "<HotelReservation paramaters and attributes>"
         */
        return "<Hotel>" + "\n" +
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
                "<hasKitchenette>" + hasKitchenette + "</hasKitchenette>" + "\n" +
                "<lodgingSize>" + lodgingSize + "</lodgingSize>" + "\n" +
                "<reservationStatus>" + reservationStatus + "</reservationStatus>" + "\n" +
                "</Hotel>";
    }
}