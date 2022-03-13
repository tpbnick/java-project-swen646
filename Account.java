import java.util.ArrayList;
import java.io.*;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

public class Account {

    private final String filePath = "C:\\\\tmp-umgc\\\\accounts\\\\";

    private int reservationNum;

    private String mailingAddr;
  
    private String emailAddr;
  
    private long phoneNum;
  
    private ArrayList reservations;
  
    private String accountNum;
  
    private ArrayList draftReservations;

    private static String newAccountNum;

    // Creates new account with provided mailingAddr, phoneNum, and emailAddr
    public Account(String mailingAddr, long phoneNum, String emailAddr) throws IOException, JSONException {
        /*
	   * set the mailingAddr, phoneNum, and emailAddr parameters in the new account object
	   */
        Random rnd = new Random();
        int number = rnd.nextInt(99999999);
        newAccountNum = String.valueOf(number);
        String newAccountPath = filePath + "A" + newAccountNum;
        new File(newAccountPath).mkdir();

        JSONObject toPutInFile = new JSONObject();
        toPutInFile.put("mailingAddress", mailingAddr);
        toPutInFile.put("phoneNumber", phoneNum);
        toPutInFile.put("emailAddress", emailAddr);

        File accountJson = new File(newAccountPath + "\\" + "accountInfo.json");
        accountJson.createNewFile();
        FileWriter writer = new FileWriter(accountJson.getAbsolutePath());
        writer.write(toPutInFile.toString());
        writer.close();
        System.out.println("New account created with account number: A" + newAccountNum);
    }

    // formats and returns data in json format
    public String toString() {
	  /*
	   * return  "<reservationNum>" + ... "</phoneNum>"
	   */
        return "<Reservation>" +
                super.toString() + ""; // reservation details need to be added
    }

    public static String getNewAccountNum() { return newAccountNum;}

    public int getReservationNumber() {
        return reservationNum;
    }
      
    public String getMailingAddr() {
        return mailingAddr;
    }
      
    public void setMailingAddr(String mailAddr) {
        this.mailingAddr = mailAddr;
    }
      
    public String getEmailAddr() {
        return emailAddr;
    }
      
    public void setEmailAddr(String email) {
        this.emailAddr = email;
    }
      
    public long getPhoneNumber() {
        return phoneNum;
    }
      
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNum = phoneNumber;
    }
      
    public ArrayList getReservations() {
        return reservations;
    }
      
    public ArrayList getDraftReservations() {
        return draftReservations;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setReservationNum(int reservationNum) {
        this.reservationNum = reservationNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setReservations(ArrayList reservations) {
        this.reservations = reservations;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public void setDraftReservations(ArrayList draftReservations) {
        this.draftReservations = draftReservations;
    }
}