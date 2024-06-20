import com.application.cab_application.DAO.AccountDao;
import com.application.cab_application.Models.Account;
import com.application.cab_application.enums.AccountType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAccountDao {
    Account account = new Account("bharath.s@bk.com", "SomePassword", "1234567890", AccountType.DRIVER);
    static int accountID ;

    @Test
    public void checkAccountCreation(){
        accountID = AccountDao.createAccount(account);
        assertTrue(accountID !=0);
    }

    @Test
    public void checkCreatedAccount(){
        Account account1 = AccountDao.getByID(accountID);
        System.out.println(accountID);
        assertEquals(account1.getEmail() , account.getEmail());
    }
}
