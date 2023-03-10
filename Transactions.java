import java.util.*;
public class Transactions {
     
    //the amount of this transactions
    private double amount;

    // the time and date of this transaction
    private Date timestamp;

    //a memo for this transaction
    private String memo;

    //the account in which the transaction was performed
    private Account inAccount;

    public Transactions(double amount , Account inAccount){
        this.amount=amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo ="";

    }
    public Transactions(double amount, String memo, Account inAccount){
        
        //call the two org constructor first
        this(amount,inAccount);
        this.memo = memo;

    }

    //get the amount of the transactions
    // return the amount
    public double getAmount() {
        return this.amount;
    }
    public String getSummaryLine() {
        if(this.amount>= 0){
            return String.format("%s : ₹%.02f : %s\n", this.timestamp.toString() ,this.amount,this.memo);

        }
        else{
            return String.format("%s : ₹(%.02f) : %s\n", this.timestamp.toString() ,-this.amount,this.memo);

        }
    }


}


