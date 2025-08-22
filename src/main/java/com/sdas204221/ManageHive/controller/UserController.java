package com.sdas204221.ManageHive.controller;

import com.sdas204221.ManageHive.model.Backup;
import com.sdas204221.ManageHive.model.User;
import com.sdas204221.ManageHive.service.JwtService;
import com.sdas204221.ManageHive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @PostMapping("login")
    public ResponseEntity<String> userLogin(@RequestBody User user){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        ));
        if (authentication.isAuthenticated()){
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))){
                return new ResponseEntity<>(
                        jwtService.generateToken(user.getUsername()),
                        HttpStatus.OK
                );
            }else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER"))){
            userService.delete(userDetails.getUsername());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PatchMapping
    public ResponseEntity<?> updateUser(@RequestBody User user,@AuthenticationPrincipal UserDetails userDetails){
        user.setUsername(userDetails.getUsername());
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<User> getUser(@AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(userService.findById(userDetails.getUsername()),HttpStatus.OK);
    }
    @GetMapping("backup")
    public ResponseEntity<Backup> userBackup(@AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(userService.userBackup(userDetails.getUsername()),HttpStatus.OK);
    }
}
