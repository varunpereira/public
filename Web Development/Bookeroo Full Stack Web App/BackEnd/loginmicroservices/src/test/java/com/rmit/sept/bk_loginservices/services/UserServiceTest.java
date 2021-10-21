package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(Alphanumeric.class)
class UserServiceTest {
	
	User user;

    @Autowired
    UserService userService;

	@BeforeAll
    public void init(){
		// User saved
       user = new User();
	   user.setFullname("Bob Roberts");
       user.setAddress("3 Hill Road");
       user.setUsername("bobroberts@gmail.com");
       user.setPhonenumber("0311112222");
       user.setAbn("11223344550");
       user.setRegisterstatus("Pending");
       user.setAccounttype("Customer");
       user.setPassword("123456");
       user.setConfirmpassword("123456");
       userService.saveUser(user);
       System.out.println("User saved");
    }

    @Test
    void AupdateUser() {
        // Update user
        user.setFullname("Tim Roberts");
        user.setAddress("10 Hill Road");
        user.setUsername("bobroberts@gmail.com");
        user.setPhonenumber("8888666600");
        user.setRegisterstatus("Pending");
        user.setAccounttype("Customer");
        user.setPassword("123456");
        userService.updateUser(user, "bobroberts@gmail.com");
		assertEquals("Tim Roberts", user.getFullname());
        System.out.println("User updated");
    }

	@Test
    void BupdateToken() {
        // Update Token
        user.setToken("123456789");
        userService.updateToken("123456789", "bobroberts@gmail.com");
		assertEquals("123456789", user.getToken());
        System.out.println("Token updated");
    }

    @Test
    void CcompareToken() {
        // Finds token
        user = userService.compareToken(user.getUsername(), user.getToken());
		assertEquals("bobroberts@gmail.com", user.getUsername());
		assertEquals("123456789", user.getToken());
		System.out.println("Token found");
    }

    @Test
    void DuserStatus() {
        // Check status
		assertEquals(false, userService.userStatus("bobroberts@gmail.com"));
		System.out.println("Status checked");
    }
	

    @Test
    void EuserExists() {
        // Check if exists
		assertEquals(true, userService.userExists("bobroberts@gmail.com"));
        System.out.println("User exists");
    }
    
    @Test
    void FfindUser() {
    	// Finds a single user
    	String username = "bobroberts@gmail.com";
    	User user = userService.findUser(username);
    	assertEquals(username, user.getUsername());
    	System.out.println("User found");
    }

    @Test
    void GfindAllUsers() {
        // Finds all users
        List<User> users = userService.findAllUsers();
        assertEquals(1, users.size());
        System.out.println("Users found");
    }

    @AfterAll
    void deleteUser(){
        // Deletes user
        userService.deleteUser("bobroberts@gmail.com");
		assertEquals(false, userService.userExists("bobroberts@gmail.com"));
        System.out.println("User deleted");
    }
}