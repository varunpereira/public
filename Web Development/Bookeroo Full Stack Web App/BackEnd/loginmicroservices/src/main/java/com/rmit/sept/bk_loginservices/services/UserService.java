package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.UserRepository;
import com.rmit.sept.bk_loginservices.exceptions.*;
import com.rmit.sept.bk_loginservices.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // inserts user record in database
    public User saveUser (User newUser){

//        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
//        //Username has to be unique (exception)
//        // Make sure that password and confirmPassword match
//        // We don't persist or show the confirmPassword
//        return userRepository.save(newUser);

        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            newUser.setFullname(newUser.getFullname());
            newUser.setAddress(newUser.getAddress());
            newUser.setPhonenumber(newUser.getPhonenumber());
            newUser.setAccounttype(newUser.getAccounttype());
            newUser.setAbn((newUser.getAbn()));
            newUser.setToken(newUser.getToken());
            newUser.setRegisterstatus(newUser.getRegisterstatus());
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            newUser.setConfirmpassword("");
            return userRepository.save(newUser);

        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists");
        }
    }

    // updates user record in database
    public User updateUser(User userDetails, String username) {
        try {
            User updateUser = userRepository.findByUsername(username);
            updateUser.setPassword(userDetails.getPassword());
            updateUser.setUsername(userDetails.getUsername());
            updateUser.setFullname(userDetails.getFullname());
            updateUser.setAddress(userDetails.getAddress());
            updateUser.setPhonenumber(userDetails.getPhonenumber());
            updateUser.setAccounttype(userDetails.getAccounttype());
            updateUser.setAbn(userDetails.getAbn());
            updateUser.setRegisterstatus(userDetails.getRegisterstatus());
            return userRepository.save(updateUser);
        } catch (Exception e){
            throw new UsernameAlreadyExistsException("Cannot update user: '"+userDetails.getUsername());
        }
    }

    // deletes user record in database
    public void deleteUser(String username) {
        try {
            User user = userRepository.findByUsername(username);
            userRepository.delete(user);
        } catch (Exception e){
            throw new UsernameAlreadyExistsException("Cannot delete " + username);
        }
    }

    // updates user token in database
    public User updateToken(String token, String username) {
        try {
            User updateUserToken = userRepository.findByUsername(username);
            updateUserToken.setToken(token);
            return userRepository.save(updateUserToken);
        } catch (Exception e){
            throw new UsernameAlreadyExistsException("Cannot update user's token");
        }
    }

    // compares user token with username and account type in database
    public User compareToken(String username, String token){
        try {
            return userRepository.findByUsernameAndToken(username, token);
        } catch (Exception e){
            throw new UsernameAlreadyExistsException("Cannot find: " + username + " , " + token + " , "  + ". Access is denied");
        }
    }

    // checks if the user is pending or registered
    public Boolean userStatus(String username){
        try {
            User checkUser = userRepository.findByUsername(username);
            if(Objects.equals(checkUser.getRegisterstatus(), "Pending")){
                return false;
            } else {
                return true;
            }
        } catch (Exception e){
            throw new UsernameAlreadyExistsException(username + " " + "does not exist");
        }
    }

    // find if a username exists
    public boolean userExists(String username){
        return userRepository.existsByUsername(username);
    }

    // finds all users in the database
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
    
    // finds a single user
    public User findUser(String username) {
    	try {
    		return userRepository.findByUsername(username);
    	} catch (Exception e){
    		throw new UsernameAlreadyExistsException(username + " cannot be found");
    	}
    	
    }
}
