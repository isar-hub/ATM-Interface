import java.util.*;

public class Account {

    //the name of the account
    private String name;

    //the current balance of the account
    private double balance;

    //the account ID number
    private String uuid;

    //the user object that owns this account
    private User holder;

    //the list of transactions for this account
    private ArrayList<Transactions> transactions;
    
    
    public Account (String name,User holder, Bank theBank){

        //set the account name and holder
        this.name = name;
        this.holder = holder;
        
        //get new account UUID
        this.uuid = theBank.getNewAccountUUID();

        //init transactions
        this.transactions = new ArrayList<Transactions>();

        //add to holder and bank lists
          
    }


    public String getUUID() {
        return this.uuid;
    }

//get summary line for the account
//return the string summart
    public Object getSummaryLine() {
        
        //get the account balance
        double balance = this.getBalance();

        //format the summary line, depending on the whether the balance is 
        //negative
        if(balance>=0){
            return String.format("%s ; ₹%.02f : %s", this.uuid, balance,this.name);

        }
        else{
            return String.format("%s ; ₹(%.02f) : %s", this.uuid, balance,this.name);
        }
    }


    double getBalance() {
        double balance =0;
        for(Transactions t :this.transactions){
            balance +=t.getAmount();
        }
        return balance;
    }

    //print the transaction history of the account
    public void printTransHistory() {

        System.out.printf("\nTransaction history for account %s\n",this.uuid);
        for(int t = this.transactions.size()-1; t>= 0; t--){
            System.out.println(this.transactions.get(t).getSummaryLine());

        }
        System.out.println();

    }

    //Add a new transaction to a particular account
    //accidx : the index of the account
    //amount : the amount of the transaction
    //memo : the memo of the transactions
    public void addTransaction(double amount, String memo) {
        
        //create new transaction object and add it to our list
        Transactions newTrans = new Transactions(amount, memo,this);
        this.transactions.add(newTrans);

    }
    
}


