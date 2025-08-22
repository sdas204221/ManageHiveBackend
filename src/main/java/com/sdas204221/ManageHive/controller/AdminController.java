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

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @PostMapping("login")
    public ResponseEntity<String> adminLogin(@RequestBody User user){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        ));
        if (authentication.isAuthenticated()){
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
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
    @PostMapping("user")
    public ResponseEntity<?> addUser(@RequestBody User user, @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
            user.setRole("USER");
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @DeleteMapping("user")
    public ResponseEntity<?> deleteUser(@RequestBody User user, @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
            userService.delete(user.getUsername());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PatchMapping("user/lock")
    public ResponseEntity<?> lockUser(@RequestBody User user,@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
            userService.lockUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PatchMapping("user/unlock")
    public ResponseEntity<?> unlockUser(@RequestBody User user,@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
            userService.unlockUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("users")
    public ResponseEntity<List<User>> getAllUsers(@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
            return new ResponseEntity<List<User>>(userService.getAllUsers(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PatchMapping("/user/password")
    public ResponseEntity<?> passwordChange(@RequestBody User user,@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
            User original=userService.findById(user.getUsername());
            if (original==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            userService.update(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("backup")
    public ResponseEntity<Backup> fullBackup(@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
            return new ResponseEntity<>(userService.fullBackup(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
