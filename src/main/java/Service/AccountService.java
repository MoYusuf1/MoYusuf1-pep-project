package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public Account createAccount(Account account) {
        if(account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            return null;
        }
        if(account.getPassword().length() < 4) {
            return null;
        }
        if(accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return null;
        }
        return accountDAO.createAccount(account);
    }

    public Account login(Account account) {
        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if(existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            return existingAccount;
        }
        return null;
    }
}