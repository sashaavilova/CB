package depositmanager;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Deposit implements Deposit_Manager {

    private String NameClient, FamClient;
    private double sum, proc, FinishProc;
    private int termDays;
    private Date startDate;
    private boolean withPercentCapitalization;
         
    public String getNameClient(){
        return NameClient;
    }    
    public String getFamClient(){
        return FamClient;
    }     
    public double getsum(){
        return sum;
    }     
    public double getproc(){
        return proc;
    }
    public double getFinishProc(){
        return FinishProc;
    }     
    public Date getstartDate(){
        return startDate;
    }     
    public int gettermDays(){
        return termDays;
    }     
    public boolean getwithPercentCapitalization(){
        return withPercentCapitalization;
    }
    public void print(){
        SimpleDateFormat sdf = new SimpleDateFormat ("dd.MM.yyyy");
        System.out.print(this.getNameClient());
        System.out.print(",");
        System.out.print(this.getFamClient());
        System.out.print(",");
        System.out.print(String.valueOf(this.getsum()));
        System.out.print(",");
        System.out.print(String.valueOf(this.getproc()));
        System.out.print(",");
        System.out.print(String.valueOf(this.getFinishProc()));
        System.out.print(",");
        System.out.print(String.valueOf(this.gettermDays()));
        System.out.print(",");
        System.out.print(sdf.format(this.getstartDate()));
        System.out.print(";\n"); 
    }  
    public void csv(List<Deposit> deposit) throws IOException{
        SimpleDateFormat sdf = new SimpleDateFormat ("dd.MM.yyyy");
        FileWriter sw = new FileWriter("test.csv");
        
        for(Deposit p : deposit){
        sw.append(p.getNameClient()).append(",").append(p.getFamClient()).append(",").append(String.valueOf(p.getsum())).append(",").append(String.valueOf(p.getproc()));
        sw.append(",").append(String.valueOf(p.getFinishProc())).append(",").append(String.valueOf(p.gettermDays())).append(",").append(sdf.format(p.getstartDate()));
        sw.append(";\n");               
    }    
    sw.flush();
    sw.close();
    }
    
    @Override
    public Deposit addDeposit(Client client, double ammount, double percent, double pretermPercent,int termDays, Date startDate, boolean withPercentCapitalization) {
       
       this.NameClient = client.name;
       this.FamClient = client.surname;
       this.sum = ammount;
       this.proc = percent;
       this.FinishProc = pretermPercent;
       this.termDays = termDays;
       this.startDate = startDate;
       this.withPercentCapitalization = withPercentCapitalization;
       
       return this;
    }
   
    @Override
    public List<Deposit> getClientDeposits(Client client, List<Deposit> deposit) {    
        List<Deposit> result = deposit.stream().filter(a -> Objects.equals(a.NameClient, client.name)).collect(Collectors.toList());        
        return(result); 
    }
    
    @Override
    public List<Deposit> getAllDeposits(List<Deposit> deposit) {        
        return(deposit);        
    }
    
    @Override
    public double getEarnings( Date currentDate) {
        double sum_ = 0;
     
        long difference = currentDate.getTime() - this.startDate.getTime() ;
        int year =  (int)(difference / (24 * 60 * 60 * 1000)/365); 
        
        if(!this.withPercentCapitalization){
            sum_=this.sum;
            for(int i=1; i<= year; i++)            
                sum_ += (sum_*this.proc)/100;
        }
        else         
            sum_ = year * ((this.sum * this.proc)/100) + this.sum;
        return sum_;
    }

    @Override
    public double removeDeposit(List<Deposit> deposit, Date closeDate) {
        double sum_deposit = 0;
        
        long difference = closeDate.getTime() - this.startDate.getTime() ;
        int year =  (int)(difference / (24 * 60 * 60 * 1000)/365); 
        if(year == this.termDays || year <= this.termDays){
            if(!this.withPercentCapitalization){
                sum_deposit=this.sum;
                for(int i=1; i<= year; i++)            
                    sum_deposit += (sum_deposit*this.FinishProc)/100;
            }
            else         
                sum_deposit = year * ((this.sum * this.FinishProc)/100) + this.sum;

            deposit.remove(this);
         }
        return sum_deposit;
    }
}