package coe528.project;

/*
 * NAME: Sukhmanjot Aulakh
 * STUDENT#: 501161279
 * COURSE: COE528 (Prof. Boujemaa Guermazi)
 */
//This class will satisfy requirement 2 of project

// Overview: SilverCustomer is mutable, and inherits the properties of the customer
//           class. It is used to specialize behavior regarding to the silver customer.


// The abstraction function is:
/*
 A Class used to distinguish the specific behavior of a Silver Customer.
 The purchasing will be approriate to the fee associated with the silver customer.
*/

// The rep invariant is:
/*
 - amount for purchase must be greater than or equal to(50 + the cost of the FEE)
*/

public class SilverCustomer extends Customer{
    
    private final int FEE = SILVER_FEE;
    private String username;
    private long balance;
    
    //REQUIRES: username is non null and the balance is non null
    //MODIFIES: this
    //EFFECTS: sets username and balance of instance based on arguments
    public SilverCustomer(String username,long balance)
    {
        super(username,balance);
        this.username = username;
        this.balance = balance;
    }
    
    //REQUIRES: amount for purchase must be greater than or equal to(50 + the cost of the FEE)
    //MODIFIES: balance
    //EFFECTS: withdraws the amount of the purchase from the customers balance
    @Override
    public boolean makePurchase(long amount)
    {
        //Implement repOK()
        if(repOK(amount))
        {
            if(balance>=amount)
            {
                withdraw(amount);
                return true;
            }
        }
        return false;
    }
    
    //REQUIRES: FEE is not null
    //EFFECTS: returns FEE
    @Override
    public int getFEE()
    {
        return FEE;
    }
    
    //Implementation of Rep Invariant:
    //EFFECTS: checks if amount is appropriate for purchase
    public boolean repOK(long amount)
    {
        return (amount>=50+FEE);
    }
    
    //Implementation of the abstraction function 
    //EFFECTS: Returns a string that contains the behaviour level.
    @Override
    public String toString()
    {
        return "Silver";
    }
}
