package com.spring.rest.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.rest.dao.UserDAO;
import com.spring.rest.model.User;

@RestController
public class HomeController {
	
	@Autowired
	private UserDAO userDAO;

	@GetMapping("/")
	public List<User> home() {
		 List<User> listUsers = userDAO.list();
		 return listUsers;
	}
	
	
	@PostMapping(value = "/save")
	    public ResponseEntity<User> saveUser(@RequestBody User user) {
		 userDAO.saveOrUpdate(user);
	        return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@PutMapping(value = "/edit")
    public ResponseEntity<User> editUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userDAO.get(userId);
        return new ResponseEntity<User>(user,HttpStatus.OK);      
    }
     
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Integer> deleteUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("id"));
        userDAO.delete(userId);
        return new ResponseEntity<Integer>(userId,HttpStatus.OK);     
    }
}
