package com.spring.rest.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.rest.model.User;

/*this annotation tells springframework that is specific annotated class is used for DATA ACCESSING, also make it eligible to use @Autowired
annotation same as @Component annotation on any class makes it eligible from auto wiring.
it is also making this class to support DATAACCESSEXCEPTION TRANSACTION.
*/
@Repository
public class UserDAOImpl implements UserDAO {

	//autowiring session factory object
	@Autowired
	private SessionFactory sessionFactory;
	
	//no arg-constructor is of no use.
	public UserDAOImpl() {}
	
	//initializing it from java based bean creating (through java configuration in ApplicationContext.class)
	public UserDAOImpl(SessionFactory sessionFactory) {
		System.out.println("using autowired sessionFactory object is "+this.sessionFactory.hashCode());
		System.out.println("using java bean bind sessionFactory object is "+sessionFactory);
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public List<User> list() {
		List<User>userList = (List<User>)sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return userList;
	}

	@Override
	@Transactional
	public User get(int id) {
		 String hql = "from User where id=" + id;
	        Query query = sessionFactory.getCurrentSession().createQuery(hql);
	         
	        @SuppressWarnings("unchecked")
	        List<User> listUser = (List<User>) query.list();
	         
	        if (listUser != null && !listUser.isEmpty()) {
	            return listUser.get(0);
	        }
	         
	        return null;
	}

	@Override
	@Transactional
	public void saveOrUpdate(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);	
	}

	@Override
    @Transactional
	public void delete(int id) {
		User userToDelete = new User();
        userToDelete.setId(id);
        sessionFactory.getCurrentSession().delete(userToDelete);
	}

}
