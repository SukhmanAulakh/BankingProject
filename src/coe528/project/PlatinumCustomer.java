package coe528.project;

/*
 * NAME: Sukhmanjot Aulakh
 * STUDENT#: 501161279
 * COURSE: COE528 (Prof. Boujemaa Guermazi)
 */

public class PlatinumCustomer extends Customer{
    
    private final int FEE =PLATINUM_FEE;
    private String username;
    private long balance;
    
    public PlatinumCustomer(String username,long balance)
    {
        super(username,balance);
        this.username = username;
        this.balance = balance;
    }
    
    @Override
    public boolean makePurchase(long amount)
    {
        //Since platinum fee is 0 so shouldnt change much
        //Variable remains incase fee is ever going to be increased
        if(balance>=amount&& amount>=(50+FEE))
        {
            withdraw(amount);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public int getFEE()
    {
        return FEE;
    }
    
    @Override
    public String toString()
    {
        return "Platinum";
    }
}
