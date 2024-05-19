package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.DataMailDTO;
import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.services.ClientService;
import com.project.MovieWebsite.services.MailService;
import com.project.MovieWebsite.utils.Const;
import com.project.MovieWebsite.utils.DataUtils;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.HashMap;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private MailService mailService;

    @Override
    public String create(UserDTO userDTO) {
        try {
            DataMailDTO dataMail = new DataMailDTO();

            dataMail.setTo(userDTO.getEmail());
            dataMail.setSubject(Const.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

            Map<String, Object> props = new HashMap<>();
            props.put("name", userDTO.getFullName());
            String password= DataUtils.generateTempPwd(6);
            props.put("password", password);
            dataMail.setProps(props);
            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
            return password;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return null;
    }
}

