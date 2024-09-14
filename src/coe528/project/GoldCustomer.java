package coe528.project;

/*
 * NAME: Sukhmanjot Aulakh
 * STUDENT#: 501161279
 * COURSE: COE528 (Prof. Boujemaa Guermazi)
 */

public class GoldCustomer extends Customer{
    
    private final int FEE =GOLD_FEE;
    private String username;
    private long balance;
    
    public GoldCustomer(String username,long balance)
    {
        super(username,balance);
        this.username = username;
        this.balance = balance;
    }
    
    @Override
    public boolean makePurchase(long amount)
    {
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
        return "Gold";
    }
}
