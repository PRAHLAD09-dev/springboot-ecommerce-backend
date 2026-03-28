package com.prahlad.ecommerce.service.otp;

import com.prahlad.ecommerce.exception.EmailException;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService 
{

    @Value("${sendgrid.api.key}")
    private String apiKey;

    @Value("${sendgrid.from.email}")
    private String fromEmail;
    
    

    public void sendOtp(String toEmail, String otp) 
    {

        Email from = new Email(fromEmail , "Prahlad Bhakat");
        Email to = new Email(toEmail);
        

        Content content = new Content(
                "text/plain",
                " Hi,\n " +

                " Your verification code for Ecommerce App: "  + otp + "\nThis code will expire in 5 minutes."
                +
                 "\nIf you did not request this, please ignore.\n"
                +
                 "\nThanks,"
                +
                 "\nTeam Ecommerce"
        );

        Mail mail = new Mail(from, "OTP Verification", to, content);

        try 
        {
        	SendGrid sg = new SendGrid(apiKey);

        	Request request = new Request();
        	request.setMethod(Method.POST);
        	request.setEndpoint("mail/send");
        	request.setBody(mail.build());

        	Response response = sg.api(request);      

            if (response.getStatusCode() >= 400) 
            {
                throw new EmailException("Failed to send email");
            }

        } 
        
        catch (Exception e) 
        {
            throw new EmailException("Email sending failed");
        }
    }
    
    public void sendSimpleMail(String toEmail, String subject, String body) 
    {
        
        Email from = new Email(fromEmail, "Ecommerce App");
        Email to = new Email(toEmail);

        Content content = new Content("text/plain", body);

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);

        Request request = new Request();
        try 
        {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            if (response.getStatusCode() >= 400) 
            {
                throw new EmailException("Email sending failed");
            }

        } 
        catch (Exception e) 
        {
            throw new EmailException("Error sending email: " + e.getMessage());
        }
    }
}