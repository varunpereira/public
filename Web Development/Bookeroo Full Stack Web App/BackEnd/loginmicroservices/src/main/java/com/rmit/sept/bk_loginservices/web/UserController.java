package com.rmit.sept.bk_loginservices.web;


import com.rmit.sept.bk_loginservices.model.User;
import com.rmit.sept.bk_loginservices.payload.*;
import com.rmit.sept.bk_loginservices.security.*;
import com.rmit.sept.bk_loginservices.services.*;
import com.rmit.sept.bk_loginservices.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.rmit.sept.bk_loginservices.security.SecurityConstant.TOKEN_PREFIX;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    // registers user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        // Validate passwords match
        userValidator.validate(user, result);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        User newUser = userService.saveUser(user);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    // updates user
    @PatchMapping("/updateUser/{username}")
    public ResponseEntity<?> updateUserDetails(@PathVariable(value = "username") String username,
                                               @Valid @RequestBody User user, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        User updateUser = userService.updateUser(user, username);

        return new ResponseEntity<User>(updateUser, HttpStatus.CREATED);
    }

    // deletes user
    @DeleteMapping("/deleteUser/{username}")
    public ResponseEntity<String> deleteUserByUserName (@PathVariable(value = "username") String username) {
        userService.deleteUser(username);
        return new ResponseEntity<String>("User : " + username  + " has been deleted", HttpStatus.OK);
    }

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    // logs in user
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
    }

    // updates token
    @PatchMapping("/updateToken/{token}/{username}")
    public ResponseEntity<?> updateUserToken(@PathVariable(value = "username") String username,
                                             @PathVariable(value = "token") String token){
        User updateToken = userService.updateToken(token, username);
        return ResponseEntity.ok(updateToken);
    }

    // verifies token
    @PostMapping("/verify/{username}/{token}")
    public ResponseEntity<?> tokenVerify(@PathVariable(value = "username") String username,
                                              @PathVariable(value = "token") String token){
        User verify = userService.compareToken(username, token);
        return new ResponseEntity<User>(verify, HttpStatus.OK);
    }

    // verifies if user is pending or registered
    @PostMapping("/checkStatus/{username}")
    public ResponseEntity<?> checkUserStatus(@PathVariable(value = "username") String username){
        if(!userService.userStatus(username)){
            return new ResponseEntity<String>("Pending", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>( "Registered", HttpStatus.OK);
        }
    }

    // returns all users
    @PostMapping("/allUsers")
    public List<User> viewAllUsers(){
        return userService.findAllUsers();
    }

    // verifies user exists
    @PostMapping("/userExists/{username}")
    public ResponseEntity<?> checkUserExists(@PathVariable(value = "username") String username){
        if(userService.userExists(username)){
            return new ResponseEntity<String>("Exists", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Does not exist", HttpStatus.OK);
        }
    }
    
    // returns a user
    @PostMapping("/findUser/{username}")
    public User viewUser(@PathVariable(value = "username") String username){
        return userService.findUser(username);
    }
}
