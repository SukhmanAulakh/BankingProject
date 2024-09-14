package coe528.project;

/*
 * NAME: Sukhmanjot Aulakh
 * STUDENT#: 501161279
 * COURSE: COE528 (Prof. Boujemaa Guermazi)
 */

interface CustomerInterface {
    
    public final int SILVER_FEE = 20;
    public final int GOLD_FEE = 10;
    public final int PLATINUM_FEE = 0;
    
    public abstract long getBalance();
    public abstract String getUsername();
}
