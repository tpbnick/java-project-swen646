import java.io.*;

import org.json.JSONArray;
import org.json.JSONException;

public class testCases {

    public static void main(String[] args) throws JSONException, IOException {
        // Create new directory to store account data
        File accountDirectory = new File("C:/tmp-umgc/accounts");
        if (!accountDirectory.exists()){
            accountDirectory.mkdirs();
            System.out.println("New accounts directory created at: " + accountDirectory);
        }
        // Begin test cases
        testcase1();
        testcase2();
        testcase3();
        testcase4();
        testcase5();
        testcase6();
    }

    // Create new account with a hotel reservation, then display reservation data
    public static void testcase1() throws JSONException, IOException {
        try {
            System.out.println("==========TEST CASE 1==========");
            // Create new manager instance
            Manager x = new Manager();
            // Creates new account
            new Account("1234 Dark Side Court, MD, 21060", 1231231234L, "spamspam@gmail.com");
            String newAccountNumber = "A" + Account.getNewAccountNum();
            // Creates new reservation
            new HotelReservation("1234 Test St.", null, "2022-03-25", "2022-03-28", 3,400, true, "completed", newAccountNumber);
            // Returns data created for new account
            x.loadAccountData();
            JSONArray accountData = x.getSpecificAccount(newAccountNumber);
        } catch (Exception e) {
            System.out.println("testcase1 failure: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    // Create new account with a house reservation, then display the total cost of the new reservation
    public static void testcase2() throws JSONException, IOException {
        try {
            System.out.println("==========TEST CASE 2==========");
            // Create new manager instance
            new Manager();
            // Creates new account
            new Account("4321 Example Blvd., Los Angeles, CA, 90001", 3213214321L, "example@gmail.com");
            String newAccountNumber = "A" + Account.getNewAccountNum();
            // Creates new reservation
            HouseReservation x = new HouseReservation("1234 Test St.", null, "2022-03-25", "2022-03-28", 3, 2, 1, 1, 1200, 2, "completed", newAccountNumber);
            // Returns total cost for reservation
            x.calculatePrice(newAccountNumber, x.newReservationNum); // Calculate the price
            x.setDraftReservation(newAccountNumber, x.newReservationNum); // Set the reservation to draft
        } catch (Exception e) {
            System.out.println("testcase2 failure: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    // Create new Cabin reservation, then update the reservation, then uses toString() method to display data
    public static void testcase3() throws JSONException, IOException {
        try {
            System.out.println("==========TEST CASE 3==========");
            // Create new manager instance
            new Manager();
            // Creates new account
            new Account("101010 Binary St., Los Angeles, CA, 90001", 10987654321L, "gmail@example.com");
            String newAccountNumber = "A" + Account.getNewAccountNum();
            // Creates new reservation
            CabinReservation x = new CabinReservation("1234 Test St.", null, "2022-03-25", "2022-03-28", 3, 2, 1, 1, 1200, true, true, "Updated", newAccountNumber);
            // Returns total cost for reservation
            x.calculatePrice(newAccountNumber, x.newReservationNum);
            x.updateCabinReservation(newAccountNumber, x.newReservationNum, "1234 Change St.", null, "2022-03-25", "2022-03-29", 4, 2, 1, 1, 1200, true, true, "Updated");
            System.out.println(x.toString());
        } catch (Exception e) {
            System.out.println("testcase3 failure: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    // Load all data from all accounts
    public static void testcase4() throws JSONException, IOException {
        try {
            System.out.println("==========TEST CASE 4==========");
            Manager all = new Manager();
            all.displayAllAccountData();
        } catch (Exception e) {
            System.out.println("testcase4 failure: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    // try to create reservation with mismatched addresses
    public static void testcase5() throws JSONException, IOException {
        try {
            System.out.println("==========TEST CASE 5==========");
            // Create new manager instance
            new Manager();
            // Creates new account
            new Account("6789 Demonstration Dr., Los Angeles, CA, 90001", 1234567890L, "test123@gmail.com");
            String newAccountNumber = "A" + Account.getNewAccountNum();
            // Creates new reservation with mismatched addresses
            new HouseReservation("1234 Test St.", "4321 Test St.", "2022-03-25", "2022-03-28", 3, 2, 1, 1, 1200, 2, "completed", newAccountNumber);
        } catch (Exception e) {
            System.out.println("testcase5 failure: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    // try to create reservation with same reservation start and end date
    public static void testcase6() throws JSONException, IOException {
        try {
            System.out.println("==========TEST CASE 6==========");
            // Create new manager instance
            new Manager();
            // Creates new account
            new Account("6789 Demonstration Dr., Los Angeles, CA, 90001", 1234567890L, "test123@gmail.com");
            String newAccountNumber = "A" + Account.getNewAccountNum();
            // Creates new reservation with same resStartDate and resEndDate
            new HouseReservation("1234 Test St.", null, "2022-03-25", "2022-03-25", 3, 2, 1, 1, 1200, 2, "completed", newAccountNumber);
        } catch (Exception e) {
            System.out.println("testcase6 failure: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
