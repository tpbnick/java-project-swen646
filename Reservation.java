import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

abstract class Reservation {

    protected float basePrice = 120.00F;

    protected String reservationNum; // unique reservation number
  
    protected String lodgingAddress;
  
    protected String mailingAddress;
  
    protected String reservationStartDate;
  
    protected String reservationEndDate;
  
    protected int numNights;
  
    protected int numBeds;
  
    protected int numBedrooms;
  
    protected int numBathrooms;
  
    protected int lodgingSize;
  
    protected String reservationStatus;
  
    protected String accountNumber; // unique confirmation number

    protected Reservation() {

    }

    // formats and returns data in json format
    public String toString() {
        /*
	   * return "<reservation paramaters and attributes>"
	   */
        return null;
    }

    // calculate and return the reservation's price
    public float calculatePrice(String accountNumber, String reservationNumber) {
        /*
	   * iterate through reservations
	   * call calculatePrice() for each object and sum up values
	   * return the sum of values
	   */
        String filePath = "C:\\tmp-umgc\\accounts\\" + accountNumber + "\\" + reservationNumber;
        String filePathJson = filePath + ".json";

        try {
            if (Files.exists(Path.of(filePathJson))) {
                String jsonReader = Files.readString(Path.of(String.valueOf(new File(filePathJson))));
                JSONObject reservation = new JSONObject(jsonReader);
                if (reservationNumber.charAt(0) == 'H') {
                    // Hotel check
                    reservationNumber = "H" + reservationNumber;
                    basePrice += 50.00;
                    boolean kitchenetteCheck = (boolean) reservation.get("hasKitchenette");
                    if (kitchenetteCheck) {
                        basePrice += 10.00; // 10 charge for kitchenette
                    }
                    int size = (int) reservation.get("lodgingSize");
                    if (size > 900) {
                        basePrice += 15.00;
                    }
                    System.out.println("The base price was modified because it is a hotel reservation");
                } else if (reservationNumber.charAt(0) == 'O') {
                    // Hoyse Check
                    reservationNumber = "O" + reservationNumber;
                    int size = (int) reservation.get("lodgingSize");
                    if (size > 900) {
                        basePrice += 15.00;
                    }
                    System.out.println("The base price was modified because it is a house reservation");
                } else if (reservationNumber.charAt(0) == 'C') {
                    // Cabin Check
                    reservationNumber = "C" + reservationNumber;
                    boolean kitchenCheck = (boolean) reservation.get("hasFullKitchen");
                    if (kitchenCheck) {
                        basePrice += 20.00;
                    }
                    int size = (int) reservation.get("lodgingSize");
                    if (size > 900) {
                        basePrice += 15.00;
                    }
                    int bathroomCheck = (int) reservation.get("numBathrooms");
                    // add 5 charge for each bathroom after 1
                    for (int i = 1; i < bathroomCheck; i++) {
                        basePrice += 5.00;
                    }
                    System.out.println("The base price was modified because it is a cabin reservation");
                }
                int nightsCheck = (int) reservation.get("numNights");
                basePrice = basePrice * nightsCheck;
            }
        } catch (Exception e) {
            System.out.println("The provided account number and reservation number do not exist " + e.toString());
        }
        System.out.println("The total price of reservation " + reservationNumber + " is: $" + basePrice);
        return basePrice;
    }

    // create reservation data using passed in parameters
    protected Reservation(String resNum, String lodAddr, String mailAddr, String resStartDate, String resEndDate, int numberNights, int numberBeds, int numberBedrooms, int numberBath, int lodSize, String resStatus, String accNum) {
    	/*
	   * Validate parameters 
       * Validate that resStartDate and resEndDate are not the same, throw SameReservationDate if they are the same
       * Assign parameter's values to attributes
	   */
        reservationNum = resNum;
        lodgingAddress = lodAddr;
        mailingAddress = mailAddr;
        reservationStartDate = String.valueOf(resStartDate);
        reservationEndDate = String.valueOf(resEndDate);
        numNights = numberNights;
        numBeds = numberBeds;
        numBedrooms = numberBedrooms;
        numBathrooms = numberBath;
        lodgingSize = lodSize;
        reservationStatus = resStatus;
        accountNumber = accNum;

        if (Objects.equals(resStartDate, resEndDate)) {
            throw new SameReservationDate(resStartDate);
        }

        // check if the lodAddr and mailAddr are different
        if ((mailAddr != null) && (!mailAddr.equals(lodAddr))) {
            throw new MismatchedAddresses(lodAddr);
        }

    }

    public Reservation(String resStartDate, String resEndDate){
        /*
        * Validate parameters 
        * Validate that resStartDate and resEndDate are not the same, throw SameReservationDate if they are the same
        * Assign parameters's values to attributes
        * Sets resStatus to draft by default because not all attributes are included
        */
        reservationStartDate = String.valueOf(resStartDate);
        reservationEndDate = String.valueOf(resEndDate);

        if (resStartDate == resEndDate) {
            throw new SameReservationDate(resStartDate);
        }
    };

    //create and return a copy of the object
    public abstract Reservation clone();

    // Sets a draft reservation
    public void setDraftReservation(String accountNumber, String reservationNum) throws JSONException, IOException {
        /*
         * assign the reservation object's reservationStatus to draft
         */
        String filePath = "C:\\tmp-umgc\\accounts\\" + accountNumber + "\\" + reservationNum + ".json";
        File jsonFile = new File (filePath);
        String reservation = Files.readString(Path.of(jsonFile.getAbsolutePath()));
        JSONObject currentReservation = new JSONObject(reservation);
        currentReservation.put("reservationStatus", "draft");
        FileWriter writer = new FileWriter(filePath);
        writer.write(currentReservation.toString());
        writer.close();
    }

    // Cancels reservation for provided reservationNum
    public void cancelReservation(String accountNumber, String reservationNum) throws IOException, JSONException {
        /*
         * change the  reservationStatus to cancelled for the object specified by reservationNum
         */
        String filePath = "C:\\tmp-umgc\\accounts\\" + accountNumber + "\\" + reservationNum + ".json";
        File jsonFile = new File (filePath);
        String reservation = Files.readString(Path.of(jsonFile.getAbsolutePath()));
        JSONObject currentReservation = new JSONObject(reservation);
        currentReservation.put("reservationStatus", "cancelled");
        FileWriter writer = new FileWriter(filePath);
        writer.write(currentReservation.toString());
        writer.close();
    }

    // Updates reservation for provided reservation with parameter's value
    public void updateCabinReservation(String accountNumber, String reservationNum, String lodAddr, String mailAddr, String resStartDate, String resEndDate, int numberNights, int numberBeds, int numberBedrooms, int numberBath, int lodSize, boolean loft, boolean fullKitchen, String resStatus) throws IOException, JSONException {
        /*
         * changes the parameters of the reservation object
         */
        String filePath = "C:\\tmp-umgc\\accounts\\" + accountNumber + "\\" + reservationNum + ".json";
        File jsonFile = new File (filePath);
        String reservation = Files.readString(Path.of(jsonFile.getAbsolutePath()));
        JSONObject currentReservation = new JSONObject();
        currentReservation.put("reservationStatus", "updated");

        if (lodAddr != null) {
            currentReservation.put("lodgingAddress", lodAddr);
        }

        currentReservation.put("mailingAddress", mailAddr);
        currentReservation.put("reservationStartDate", resStartDate);
        currentReservation.put("reservationEndDate", resEndDate);
        currentReservation.put("numNights", numberNights);
        currentReservation.put("hasFullKitchen", fullKitchen);
        currentReservation.put("hasLoft", loft);
        currentReservation.put("numBeds", numberBeds);
        currentReservation.put("numBedrooms", numberBedrooms);
        currentReservation.put("numBathrooms", numberBath);
        currentReservation.put("lodgingSize", lodSize);
        currentReservation.put("reservationStatus", "updated"); // sets status to updated

        FileWriter writer = new FileWriter(filePath);
        writer.write(currentReservation.toString());
        writer.close();
        System.out.println("Cabin reservation updated!");
    }

    // Updates reservation for provided reservation with parameter's value
    public void updateHotelReservation(String accountNumber, String reservationNum, String lodAddr, String mailAddr, String resStartDate, String resEndDate, int numberNights, int numberBeds, int numberBedrooms, int numberBath, int lodSize, boolean kitchen, String resStatus) throws IOException, JSONException {
        /*
         * changes the parameters of the reservation object
         */
        String filePath = "C:\\tmp-umgc\\accounts\\" + accountNumber + "\\" + reservationNum + ".json";
        File jsonFile = new File (filePath);
        String reservation = Files.readString(Path.of(jsonFile.getAbsolutePath()));
        JSONObject currentReservation = new JSONObject();
        currentReservation.put("reservationStatus", "updated");

        currentReservation.put("lodgingAddress", lodAddr);
        currentReservation.put("mailingAddress", mailAddr);
        currentReservation.put("reservationStartDate", resStartDate);
        currentReservation.put("reservationEndDate", resEndDate);
        currentReservation.put("numNights", numberNights);
        currentReservation.put("numBeds", numberBeds);
        currentReservation.put("numBedrooms", numberBedrooms);
        currentReservation.put("numBathrooms", numberBath);
        currentReservation.put("lodgingSize", lodSize);
        currentReservation.put("reservationStatus", "updated"); // sets status to updated
        currentReservation.put("hasKitchenette", kitchen);

        FileWriter writer = new FileWriter(filePath);
        writer.write(currentReservation.toString());
        writer.close();
        System.out.println("Hotel reservation updated!");

    }

    // Updates reservation for provided reservation with parameter's value
    public void updateHouseReservation(String accountNumber, String reservationNum, String lodAddr, String mailAddr, String resStartDate, String resEndDate, int numberNights, int numberBeds, int numberBedrooms, int numberBath, int lodSize, String resStatus, int numberFloors) throws IOException, JSONException {
        /*
         * changes the parameters of the reservation object
         */
        String filePath = "C:\\tmp-umgc\\accounts\\" + accountNumber + "\\" + reservationNum + ".json";
        File jsonFile = new File (filePath);
        String reservation = Files.readString(Path.of(jsonFile.getAbsolutePath()));
        JSONObject currentReservation = new JSONObject();
        currentReservation.put("reservationStatus", "updated");

        if (lodAddr != null) {
            currentReservation.put("lodgingAddress", lodAddr);
        }

        currentReservation.put("mailingAddress", mailAddr);
        currentReservation.put("reservationStartDate", resStartDate);
        currentReservation.put("reservationEndDate", resEndDate);
        currentReservation.put("numNights", numberNights);
        currentReservation.put("numBeds", numberBeds);
        currentReservation.put("numBedrooms", numberBedrooms);
        currentReservation.put("numBathrooms", numberBath);
        currentReservation.put("lodgingSize", lodSize);
        currentReservation.put("reservationStatus", "updated"); // sets status to updated
        currentReservation.put("numFloors", numberFloors);

        FileWriter writer = new FileWriter(filePath);
        writer.write(currentReservation.toString());
        writer.close();
        System.out.println("House reservation updated!");

    }

    public float getBasePrice() {
        return basePrice;
    }
      
    public void setBasePrice(float price) {
    }
      
    public String getReservationNum() {
        return reservationNum;
    }
      
    public String getLodgingAddress() {
        return lodgingAddress;
    }
      
    public void setLodgingAddr(String lodgingAddr) {
        this.lodgingAddress = lodgingAddr;
    }
      
    public String getMailingAddress() {
        return mailingAddress;
    }
      
    public void setMailingAddress(String mailingAddr) {
        this.mailingAddress = mailingAddr;
    }
      
    public String getReservationStartDate() {
        return reservationStartDate;
    }
      
    public void setReservationStartDate(Date startDate) {
        this.reservationStartDate = String.valueOf(startDate);
    }
      
    public String getReservationEndDate() {
        return reservationEndDate;
    }
      
    public void setReservationEndDate(Date endDate) {
        this.reservationEndDate = String.valueOf(endDate);
    }
      
    public int getNumNights() {
        return numNights;
    }
      
    public void setNumNights(int nights) {
        this.numNights = nights;
    }
      
    public int getNumBeds() {
        return numBeds;
    }
      
    public void setNumBeds(int beds) {
        this.numBeds = beds;
    }
    
    public int getNumBedrooms() {
        return numBedrooms;
    }
      
    public void setNumBedrooms(int bedrooms) {
        this.numBedrooms = bedrooms;
    }
      
    public int getNumBathrooms() {
        return numBathrooms;
    }
      
    public void setNumBathrooms(int bathrooms) {
        this.numBathrooms = bathrooms;
    }
      
    public int getLodgingSize() {
        return lodgingSize;
    }
      
    public void setLodgingSize(int lodgeSize) {
        this.lodgingSize = lodgeSize;
    }
      
    public String getReservationStatus() {
        return reservationStatus;
    }
      
    public void setReservationStatus(String status) {
        this.reservationStatus = status;
    }
      
    public String getAccountNumber() {
        return accountNumber;
    }
      
    public void setAccountNumber(String accountNum) {
        this.accountNumber = accountNum;
    } 
}