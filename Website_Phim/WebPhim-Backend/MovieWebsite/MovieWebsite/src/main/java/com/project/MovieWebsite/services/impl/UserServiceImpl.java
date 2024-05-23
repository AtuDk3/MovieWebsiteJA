package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.components.JwtTokenUtil;
import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Role;
import com.project.MovieWebsite.models.Token;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.models.UserVIP;
import com.project.MovieWebsite.repositories.RoleRepository;
import com.project.MovieWebsite.repositories.TokenRepository;
import com.project.MovieWebsite.repositories.UserRepository;
import com.project.MovieWebsite.repositories.UserVIPRepository;
import com.project.MovieWebsite.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserVIPRepository userVIPRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException{
        String phoneNumber = userDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataNotFoundException("Phone number is exists!");
       }
        Role existingRole= roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find role with id: "+userDTO.getRoleId()));
        UserVIP existingUserVip= userVIPRepository.findById(userDTO.getVipId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user vip with id: "+userDTO.getVipId()));

        User newUser = User.builder().fullName(userDTO.getFullName()).
                phoneNumber(userDTO.getPhoneNumber()).
                password(userDTO.getPassword()).
                imgAvatar(userDTO.getImgAvatar()).
                dob(userDTO.getDob()).
                facebookAccountId(userDTO.getFacebookAccountId()).
                googleAccountId(userDTO.getGoogleAccountId()).
                userVip(existingUserVip).
                role(existingRole).
                email(userDTO.getEmail()).
                //isActive(userDTO).
                build();
        if(userDTO.getGoogleAccountId().equals("0")  && userDTO.getFacebookAccountId().equals("0") ){
            String password= userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);

    }

//    public boolean verifyEmail(String token) {
//        Token verificationToken = tokenRepository.findByToken(token);
//        if (verificationToken != null) {
//            User user = verificationToken.getUser();
//            user.setIsActive(1);
//            userRepository.save(user);
//            tokenRepository.delete(verificationToken); // Optional: delete token after verification
//            return true;
//        }
//        return false;
//    }

//    public User registerUser(UserDTO userDTO) {
////        User user = new User();
////        user.setEmail(userDto.getEmail());
////        // Set other properties and save user
////        userRepository.save(user);
//
//        String token = UUID.randomUUID().toString();
//        Token verificationToken = new Token(user);
//        verificationTokenRepository.save(verificationToken);
//
//        return user;
//    }

    @Override
    public User getUserById(int userId) throws DataNotFoundException{
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(int userId, UserDTO userDTO) throws DataNotFoundException{
        Role existingRole= roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie type with id: "+userDTO.getRoleId()));
        UserVIP existingUserVip= userVIPRepository.findById(userDTO.getVipId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie type with id: "+userDTO.getVipId()));

        User existsUser = getUserById(userId);
        existsUser.setFullName(userDTO.getFullName());
        existsUser.setPhoneNumber(userDTO.getPhoneNumber());
        existsUser.setPassword(userDTO.getPassword());
        existsUser.setDob(userDTO.getDob());
        existsUser.setFacebookAccountId(userDTO.getFacebookAccountId());
        existsUser.setGoogleAccountId(userDTO.getGoogleAccountId());
        existsUser.setUserVip(existingUserVip);
        existsUser.setRole(existingRole);
        //existsUser.setIsActive(userDTO.getIsActive());
        return existsUser;
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    public void updatePassword(String phoneNumber, String newPassword) {
        Optional<User> optionalUser= userRepository.findByPhoneNumber(phoneNumber);
        User existingUser= optionalUser.get();
        existingUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(existingUser);
    }

    @Override
    public String login(String phoneNumber, String password)  throws  Exception{
        Optional<User> optionalUser= userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("Invalid phone number / password");
        }
        User existingUser= optionalUser.get();

        if(existingUser.getGoogleAccountId().equals("0")  && existingUser.getFacebookAccountId().equals("0") ){
            if(!passwordEncoder.matches(password, existingUser.getPassword())){
                throw new BadCredentialsException("Wrong phone number or password");
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(
                phoneNumber, password,
                existingUser.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
