package depositmanager;

import java.io.*;
import java.text.*;
import java.util.*;

public class DepositManager {

    public static void main(String[] args) throws IOException, ParseException {
    
    List<Deposit> ListDeposit = new ArrayList<>();  
    SimpleDateFormat sdf = new SimpleDateFormat ("dd.MM.yyyy");
    FileWriter sw = new FileWriter("test.csv");
        
    Client client_one = new Client("Nastya","Avilova","333567865");    
    ListDeposit.add(new Deposit().addDeposit(client_one, 12000, 6, 1, 10, new Date(), true));
    ListDeposit.add(new Deposit().addDeposit(client_one, 12000, 6, 1, 10, sdf.parse("09.11.2014"), false));
        
    Client client_two = new Client("Roman","Butvinsky","333567865");    
    ListDeposit.add(new Deposit().addDeposit(client_two, 100000, 3, 12, 1, new Date(), true));
    ListDeposit.add(new Deposit().addDeposit(client_two, 120000, 3, 12, 1, sdf.parse("09.01.2011"), false));
   
    new Deposit().csv(ListDeposit);
        
   List<Deposit> deposit_client = new Deposit().getClientDeposits(client_one, ListDeposit);
   for(Deposit p : deposit_client)
        p.print();
     
     
  ListDeposit = new Deposit().getAllDeposits(ListDeposit);
    for(Deposit p : ListDeposit)
        p.print();
  
    double sum_deposit = 0;
    sum_deposit = ListDeposit.get(1).getEarnings (new Date());
    System.out.print(sum_deposit);
    
    double sum_deposit_finish = 0;
    sum_deposit_finish = ListDeposit.get(1).removeDeposit(ListDeposit, new Date());
    //new Deposit().csv(ListDeposit);
    System.out.print(sum_deposit_finish);
     for(Deposit p : ListDeposit)
        p.print();
    } 
}
