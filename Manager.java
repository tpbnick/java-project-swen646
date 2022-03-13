import java.nio.file.Path;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;

public class Manager {

    private final String filePath = "C:\\\\tmp-umgc\\\\accounts\\\\";

    private JSONObject loadedAccountData;

    private Reservation reservationData;

    private ArrayList accountNumbers = new ArrayList<String>();

    public void checkIfDirExists() {
        try {
            if (Files.exists(Path.of(filePath))) {
            } else {
                new File(filePath);
            }
        } catch (Exception e) {
            System.out.println("Setting up file path directory failed " + e.toString());
        }
    }

    public void loadAccountData() throws IOException, JSONException {
        // loads the account data as an array list
        checkIfDirExists();
        JSONObject accountDataArray = new JSONObject();
        File baseDir = new File(filePath);
        for (File subDir : baseDir.listFiles()) {
            accountNumbers.add(subDir.toString().substring(21, subDir.toString().length()));
            // System.out.println(accountNumbers);
            for (File accountData : subDir.listFiles()) {
                if (accountData.toString() != "accountInfo.json") {
                    String reservation = Files.readString(Path.of(accountData.getAbsolutePath()));
                    JSONObject accountJson = new JSONObject(reservation);
                    accountDataArray.append(subDir.toString(), reservation);
                }
            }
        }
        this.loadedAccountData = accountDataArray;
    }

    public void displayAllAccountData() throws IOException, JSONException {
        // loads the account data as an array list
        checkIfDirExists();
        JSONObject accountDataArray = new JSONObject();
        File baseDir = new File(filePath);
        for (File subDir : baseDir.listFiles()) {
            accountNumbers.add(subDir.toString().substring(21, subDir.toString().length()));
            // System.out.println(accountNumbers);
            for (File accountData : subDir.listFiles()) {
                if (accountData.toString() != "accountInfo.json") {
                    String reservation = Files.readString(Path.of(accountData.getAbsolutePath()));
                    JSONObject accountJson = new JSONObject(reservation);
                    accountDataArray.append(subDir.toString(), reservation);
                }
            }
        }
        this.loadedAccountData = accountDataArray;
        System.out.println("All accounts data: " + loadedAccountData.toString());
    }

    // Returns the specific account provided by accountNum
    public JSONArray getSpecificAccount(String accountNum) throws JSONException {
        // load the account data of the object specified by accountNum
        String key = filePath + accountNum;
        System.out.println(loadedAccountData.get(key.replace("\\\\", "\\")));
        return (JSONArray) loadedAccountData.get(key.replace("\\\\", "\\"));
    }

    // Returns a list of updated accounts
    public ArrayList updateAccounts() throws JSONException {
        ArrayList updatedAccounts = new ArrayList<String>();
        for (int i = 0; i < accountNumbers.size(); i++) {
            String key = filePath + accountNumbers.get(i);
            JSONArray accountsData = (JSONArray) loadedAccountData.get(key.replace("\\\\", "\\"));
            for (int j = 0; j < accountsData.length(); j++) {
                if (accountsData.get(j).toString().contains("updated")) {
                    updatedAccounts.add(accountNumbers.get(i));
                    System.out.println("The following accounts contain updated reservations: " + updatedAccounts);
                }
            }
        }
        return updatedAccounts;
    }

    public Manager() {

    }

    public String getFilePath() {
        return filePath;
    }

}