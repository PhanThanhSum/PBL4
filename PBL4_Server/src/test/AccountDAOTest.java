package test;

import model.BO.Account;
import model.DAO.AccountDAO;

public class AccountDAOTest {
	public static void main(String[] args) {
		AccountDAO accountDAO = new AccountDAO();
		Account ac = accountDAO.selectById(new Account("Sum", "khongbiet"));
		System.out.println(ac.toString());
	}
}
