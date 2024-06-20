package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.components.JwtTokenUtil;
import com.project.MovieWebsite.components.LocalizationUtil;
import com.project.MovieWebsite.constants.MessageKeys;
import com.project.MovieWebsite.dtos.UpdateUserDTO;
import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.exceptions.MailErrorExeption;
import com.project.MovieWebsite.models.Role;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserVIPRepository userVIPRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final LocalizationUtil localizationUtil;

    @Override
    public User createUser(UserDTO userDTO){

        Role existingRole= roleRepository.findByName(userDTO.getRoleName());

        UserVIP existingUserVip= userVIPRepository.findByName(userDTO.getVipName());

        User newUser = User.builder().
                fullName(userDTO.getFullName()).
                phoneNumber(userDTO.getPhoneNumber()).
                password(userDTO.getPassword()).
                imgAvatar(userDTO.getImgAvatar()).
                dob(userDTO.getDob()).
                facebookAccountId(userDTO.getFacebookAccountId()).
                googleAccountId(userDTO.getGoogleAccountId()).
                userVip(existingUserVip).
                role(existingRole).
                email(userDTO.getEmail()).
                isActive(userDTO.getIsActive()).
                build();
        if(userDTO.getGoogleAccountId().equals("0")  && userDTO.getFacebookAccountId().equals("0") ){
            String password= userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);

    }

    @Override
    public void checkAccount(UserDTO userDTO) throws DataNotFoundException, MailErrorExeption {

        if(userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())){
            throw new DataNotFoundException("Phone number is exists!");
        }

        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new MailErrorExeption("Email is exists!");
        }

    }


    @Override
    public User getUserById(int userId) throws DataNotFoundException{
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User updateUser(int userId, UpdateUserDTO userUpdateDTO) throws DataNotFoundException{

        User existingUser= userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

//        String newPhoneNumber= userUpdateDTO.getPhoneNumber();
//        if(!existingUser.getPhoneNumber().equals(newPhoneNumber) &&
//        userRepository.existsByPhoneNumber(newPhoneNumber)){
//            throw new DataNotFoundException("phone number already exists");
//        }

        if (!existingUser.getFullName().equals(userUpdateDTO.getFullName()) && !userUpdateDTO.getFullName().isEmpty()) {
            existingUser.setFullName(userUpdateDTO.getFullName());
        }

        if (!userUpdateDTO.getFacebookAccountId().equals("0")) {
            existingUser.setFacebookAccountId(userUpdateDTO.getFacebookAccountId());
        }
        if (!userUpdateDTO.getGoogleAccountId().equals("0")) {
            existingUser.setGoogleAccountId(userUpdateDTO.getGoogleAccountId());
        }

        if(!userUpdateDTO.getVipName().isEmpty()){
            UserVIP userVIP= userVIPRepository.findByName(userUpdateDTO.getVipName());
            existingUser.setUserVip(userVIP);
        }
        return existingUser;
    }

    @Override
    public void ban_account(int userId) throws DataNotFoundException {
        User existingUser= userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        existingUser.setIsActive(0);
        userRepository.save(existingUser);
    }

    @Override
    public void unban_account(int userId) throws DataNotFoundException {
        User existingUser= userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        existingUser.setIsActive(1);
        userRepository.save(existingUser);
    }


    public User updatePassword(String phoneNumber, String newPassword) {
        Optional<User> optionalUser= userRepository.findByPhoneNumber(phoneNumber);
        User existingUser= optionalUser.get();
        existingUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(existingUser);
        return existingUser;
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

        if (optionalUser.get().getIsActive()==0){
            throw new DataNotFoundException("Account is locked");
        }

        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(
                phoneNumber, password,
                existingUser.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if(jwtTokenUtil.isTokenExpired(token)){
            throw new Exception("Token is expired");
        }
        String phoneNumber= jwtTokenUtil.extractPhoneNumber(token);
        Optional<User> user= userRepository.findByPhoneNumber(phoneNumber);

        if(user.isPresent()){
            return user.get();
        }else{
            throw new Exception("User not found");
        }
    }

    @Override
    public boolean checkCurrentPassword(int userId, String passwordCheck) throws Exception{
        User existingUser= userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        if(!passwordEncoder.matches(passwordCheck, existingUser.getPassword())){
            throw new BadCredentialsException("Wrong current password");
        }
        return true;
    }
}
