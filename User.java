import java.rmi.server.RemoteRef;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class User{
    //First Name of the User.
    private String firstName;

    //last name of the user
    private String lastName;

    //ID number of the user
    private String uuid;

    //The MDS hash of the user's pin number
    private byte pinHash[];

    //the list of accounts for this user
    private ArrayList<Account> accounts;


    User(String firstName , String lastName, String pin,Bank theBank){

        //set user's name
        this.firstName = firstName;
        this.lastName = lastName;

        //store the pin's MDS hash, rather than the original value for security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            System.err.println("ERROR ! , caught NoSuchAlgorithmException");;
            e.printStackTrace();
            System.exit(1);
        }

         //get a new, unique universal ID for the user
         this.uuid = theBank.getNewUserUUID();

         //create empty list of accounts
         this.accounts = new ArrayList<>();

         //print log message
         System.out.printf("New user %s, %s with ID %s created.\n",lastName,firstName,this.uuid);
         

    }
    public void addAccount(Account newAccount ){
        this.accounts.add(newAccount);
    }
    public String getUUID() {
        return this.uuid;
    }
    public boolean validatePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
          
            System.err.println("ERROR ! , caught NoSuchAlgorithmException");;
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getFirstName(){
        return this.firstName;
    }
    public void printAccountSummary() {
        System.out.printf("\n\n%s's accounts summary\n",this.firstName);
        for(int i =0; i <this.accounts.size();i++){
            System.out.printf("  %d) %s\n",i+1,this.accounts.get(i).getSummaryLine());

        }
        System.out.println();
    }
    public int numAccounts() {
        return this.accounts.size();
    }

    //print transaction history for a particular account
    //acctIdx the index of the account to use
    public void printTransHistory(int accIdx) {
        this.accounts.get(accIdx).printTransHistory();

    }
    public double getAcctBalance(int accIdx) {
        return this.accounts.get(accIdx).getBalance();

    }



    // get the UUID of a particular account
    //@param accIdx the index of the account to use
    //@return the uuid of the account

    public String geAcctUUID(int accIdx) {
        return this.accounts.get( accIdx).getUUID();
    }
    public void addAcctTransaction(int accIdx, double amount, String memo) {
        this.accounts.get(accIdx).addTransaction(amount,memo);
    }
}