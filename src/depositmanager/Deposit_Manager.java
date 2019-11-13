package depositmanager;
import  java.util.*;
import java.util.List;

public interface Deposit_Manager {           
    Deposit addDeposit(Client client, double ammount, double percent, double pretermPercent, int termDays, Date startDate, boolean withPercentCapitalization);
    
    List<Deposit> getClientDeposits(Client client, List<Deposit> deposit);
    
    List<Deposit> getAllDeposits(List<Deposit> deposit);    
    
    double getEarnings(Date currentDate);
    double removeDeposit(List<Deposit> deposit, Date closeDate);
}
