package coe528.project;

/*
 * NAME: Sukhmanjot Aulakh
 * STUDENT#: 501161279
 * COURSE: COE528 (Prof. Boujemaa Guermazi)
 */

public class Customer extends User implements CustomerInterface{
    
    private String username;
    private long balance;
    
    public Customer(String username,long balance)
    {
        super(username);
        this.username=username;
        this.balance=balance;
    }
    
    public boolean deposit(long amount)
    {
        if(amount<=0)
        {
            return false;
        }
        else
        {
            balance+=amount;
            return true;
        }
    }
    
    public boolean withdraw(long amount)
    {
        if(amount<=balance&&amount>0)
        {
            balance-=amount;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean makePurchase(long amount)
    {
        if(balance>=amount)
        {
            withdraw(amount);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public long getBalance()
    {
        return balance;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public int getFEE()
    {
        return 0;
    }
    
    @Override
    public String toString()
    {
        return "Customer";
    }    
}
