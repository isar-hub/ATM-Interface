import java.util.*;
public class Bank {

    private String name;

    private ArrayList<User> users;

    private ArrayList<Account> accounts;

    /**
     * @param name
     */
    public Bank (String name){
        this.name = name;
        this.users = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    //generate a new universally unique ID for a user
    public String getNewUserUUID() {
        
        //inits
        String uuid;
        Random ran = new Random();
        int len = 6;
        boolean nonUnique = false;
        

        //continue while until we get a unique ID
        do{
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid+=ran.nextInt(6);
                
            }


            //check whether it is unique or not
            for (User u : this.users) {
                if(uuid.compareTo(u.getUUID())==0){
                    nonUnique = true;
                    break;
                }
                
            }
        }while(nonUnique);
        return uuid;
    }

    public String getNewAccountUUID() {

        String uuid;
        Random ran = new Random();
        int len = 10;
        boolean nonUnique = false;
        

        //continue while until we get a unique ID
        do{
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid+=ran.nextInt(10);
                
            }


            //check whether it is unique or not
            for (Account a :this.accounts) {
                if(uuid.compareTo(a.getUUID())==0){
                    nonUnique = true;
                    break;
                }
                
            }
        }while(nonUnique);
        return uuid;
        

        
    }

    public void addAccount(Account newAccount) {
        this.accounts.add(newAccount); 
    }

    public User addUser(String firstName, String lastName, String pin){

        //create a new user object and add it to our list
        User newUser = new User(firstName,lastName,pin,this);
        this.users.add(newUser);

        //create a savings account for the user and add to user and bank account lists
        Account newAccount = new Account("savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;

    }

    public User userLogin(String UserID, String pin){

        //search through list of users
        for(User u :this.users){

            //check user ID is correct
            if(u.getUUID().compareTo(UserID)==0 && u.validatePin(pin )){
                return u;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    
}
