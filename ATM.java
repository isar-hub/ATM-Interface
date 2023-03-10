import java.util.*;
public class ATM {
    public static void main(String[] args) {
        
        //init
        Scanner sc = new Scanner(System.in);

        //init bank
        Bank theBank = new Bank("Bank of Isar");
       


        //add a user which also creates a savings account
        User aUser = theBank.addUser("isar", "ahmad", "1234");

        //add a checking account for our user
        Account newAccount = new Account("Checking", aUser, theBank);

        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true){

            //stay in the login prompt until successfull login
            curUser = ATM.mainMenuPrompt(theBank,sc);

            //stay in main menu until user quits
            ATM.printUserMenu(curUser,sc);
        }

    }

    

    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        
        String userID;
        String pin;
        User authUser;

        //prompt user for user ID/pin combo until a correct one is reached
        do{
            
            System.out.printf("\n\nWelcome to %s\n\n",theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.printf("Enter pin: ");
            pin = sc.nextLine();

            //try to get the user object corresponding to the ID and pin combo
            authUser = theBank.userLogin(userID, pin);
            if(authUser== null){
                System.out.println("Incorrect user ID/pin Combination  "+ "Please try again.");

            }

        }
        while(authUser ==null);//continue looping until successful login
    
    return authUser;
    }

    private static void printUserMenu(User theUser, Scanner sc) {
        
       
        //print a summary of the user's accounts
        theUser.printAccountSummary();

        //init
        int choice;

        //user menu
        do {

             // It returns the first name of the user.
            System.out.printf("Welcome %s, what would like to do ?\n",theUser.getFirstName());
           
            System.out.println(" 1) Show transaction history");
            System.out.println(" 2) Withdraw");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.println();
            System.out.print("Enter Choice: ");
            choice = sc.nextInt();

            if(choice < 1 || choice > 5){
                System.out.println("Invalid Choice !");

            }
        } while (choice<1 || choice > 5);
    

         //process the choice
        switch (choice) {
            case 1:
                ATM.showTransHistory(theUser,sc);
                break;
        
            case 2: 
                ATM.withdrawlFunds(theUser, sc);
                break;

            case 3:
                ATM.depositFunds(theUser,sc);
                break;
            case 4:
                ATM.transferFunds(theUser,sc);
                break;
            case 5:
            sc.nextLine();
                break;
        }

        //redisplay this menu unless the user wants to quit
        if(choice!=5){
            ATM.printUserMenu(theUser, sc);
          
        }
        
    }


/**
 * process a funds deposit to the account 
 * @param theUser the logged in user
 * @param sc the scanner object user for user input
 */
    private static void depositFunds(User theUser, Scanner sc) {
    

        
                        //inits
                int toAccr;
                double amount;
                double acctBal;
                String memo;

                //get the account the transfer from
                do {
                    System.out.printf("Enter the number (1 to %d) of the account"+ " in which you want to deposit: ",theUser.numAccounts());
                    toAccr =sc.nextInt()-1;
                    if(toAccr < 0 || toAccr>=theUser.numAccounts()){
                        System.out.println("Invalid account!, Please try again");

                    }
                } while (toAccr<0 ||toAccr >= theUser.numAccounts());
                acctBal = theUser.getAcctBalance(toAccr);


                    //get the amount to transfer
                do {
                System.out.printf("Enter the amount to be deposited: ₹",acctBal);
                amount = sc.nextDouble();


                if(amount<0){
                    System.out.println("Ammount must be greater than zero.");
                    
                }
              

                } while (amount<0 );

                //gobble up rest of previous input
                sc.nextLine();


                //get a memo
                System.out.print("Enter a memo: ");
                memo = sc.nextLine();

                //do the withdrawl
                theUser.addAcctTransaction(toAccr, amount, memo);

    }


    /**
     * process transfering funds from one account to another
     * 
     * @param theUser the logged-in user onject
     * @param sct the scanner object used for the input
     */
    private static void transferFunds(User theUser, Scanner sc) {
    
    
        //inits
        int fromAccr;
        int toAcct;
        double amount;
        double acctBal;

        //get the account the transfer from
        do {
            System.out.printf("Enter the number (1 to %d) of the account\n"+ "to transfer from: ",theUser.numAccounts());
            fromAccr =sc.nextInt()-1;
            if(fromAccr < 0 || fromAccr>=theUser.numAccounts()){
                System.out.println("Invalid account!, Please try again");

            }
         } while (fromAccr<0 ||fromAccr >= theUser.numAccounts());
         acctBal = theUser.getAcctBalance(fromAccr);
    


        //get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"+ "to transfer to: ",theUser.numAccounts());
            toAcct =sc.nextInt()-1;
            if(toAcct < 0 || toAcct>=theUser.numAccounts()){
                System.out.println("Invalid account!, Please try again");

            }
        } while (toAcct<0 ||toAcct >= theUser.numAccounts());




        //get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max ₹%.02f): ₹",acctBal);
            amount = sc.nextDouble();
            if(amount<0){
                System.out.println("Ammount must be greater than zero.");
                
            }
            else if(amount>acctBal){
                System.out.printf("Amount must not be graeater than \n"+"balance of ₹%.02f.\n",acctBal);
            }

        } while (amount<0 || amount > acctBal);



        //finally do the transfer
        theUser.addAcctTransaction(fromAccr, -1*amount,String.format("Transfer to account %s",theUser.geAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount,String.format("Transfer to account %s",theUser.geAcctUUID(fromAccr)));

    }


        /**
         * process a fund withdrawl from an account
         * @param theUser the logged-in user object
         * @param sc the scannerobject user for user input
         */
        private static void withdrawlFunds(User theUser, Scanner sc) {
        
                 //inits
            int fromAccr;
            double amount;
            double acctBal;
            String memo;

            //get the account the transfer from
            do {
                System.out.printf("Enter the number (1 to %d) of the account"+ " from where you want to withdraw: ",theUser.numAccounts());
                fromAccr =sc.nextInt()-1;
                if(fromAccr < 0 || fromAccr>=theUser.numAccounts()){
                    System.out.println("Invalid account!, Please try again");

                }
            } while (fromAccr<0 ||fromAccr >= theUser.numAccounts());
            acctBal = theUser.getAcctBalance(fromAccr);
        

              //get the amount to transfer
        do {
            System.out.printf("Enter the amount to withdrawl (max ₹%.02f): ₹",acctBal);
            amount = sc.nextDouble();
            if(amount<0){
                System.out.println("Ammount must be greater than zero.");
                
            }
            else if(amount>acctBal){
                System.out.printf("Amount must not be graeater than \n"+"balance of ₹%.02f.\n",acctBal);
            }

        } while (amount<0 || amount > acctBal);

        //gobble up rest of previous input
        sc.nextLine();
        

        //get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        //do the withdrawl
        theUser.addAcctTransaction(fromAccr, -1*amount, memo);

    }


       


    private static void showTransHistory(User theUser, Scanner sc) {
    
        int theAcct;

        //get account whose transaction to look at
        do{
            System.out.printf("Enter the number (1 to %d) of the account"+ " whose transactions you want to see: ", theUser.numAccounts());
            theAcct= sc.nextInt()-1;
            if (theAcct<0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account, please try again");
                
            }
        }while(theAcct<0 || theAcct>= theUser.numAccounts());

        //print the transaction history
        theUser.printTransHistory(theAcct);


    }
}
  