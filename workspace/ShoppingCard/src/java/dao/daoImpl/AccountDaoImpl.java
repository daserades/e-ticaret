package java.dao.daoImpl;

import java.dao.AccountDAO;
import java.entity.Account;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountDaoImpl implements AccountDAO{
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Account findAccount(String userName) {

		Session session=sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(Account.class);
		criteria.add(Restrictions.eq("userName", userName));
		return (Account)criteria.uniqueResult();
	}

}
