package coe528.project;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/*
 * NAME: Sukhmanjot Aulakh
 * STUDENT#: 501161279
 * COURSE: COE528 (Prof. Boujemaa Guermazi)
 */

public class Manager extends User{
    
    public Manager()
    {
        super("admin");
    }
    
    public boolean addCustomer(String username, String password)
    {
        try
        {
            File customerFile = new File("./src/coe528/project/customers/"+username+".txt");
            if(customerFile.createNewFile())
            {
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(customerFile));
                fileWriter.write(password);
                fileWriter.newLine();
                fileWriter.write("100");
                fileWriter.close();
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public boolean removeCustomer(String username,String password)
    {
        try
        {
            //Get Exsisting Password
            BufferedReader customerFileReader = new BufferedReader(new FileReader("./src/coe528/project/customers/"+username+".txt"));
            String filePassword = customerFileReader.readLine();
            customerFileReader.close();
            
            if(password.equals(filePassword))
            {
                File customerFile = new File("./src/coe528/project/customers/"+username+".txt");
                if(customerFile.delete())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
               return false; 
            }
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    @Override
    public String toString()
    {
        return "MANAGER";
    }
    
}
