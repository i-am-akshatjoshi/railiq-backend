package com.railiq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendBookingConfirmation(String toEmail, String trainNo, String pnr, String journeyDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("RailIQ Booking Confirmed — PNR " + pnr);
        message.setText(
            "Your booking is confirmed!\n\n" +
            "Train No: " + trainNo + "\n" +
            "PNR: " + pnr + "\n" +
            "Journey Date: " + journeyDate + "\n\n" +
            "Thank you for using RailIQ."
        );
        mailSender.send(message);
    }

    @Async
    public void sendWaitlistUpdate(String toEmail, String pnr, int newWlPosition, double confirmationProbability) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("RailIQ Waitlist Update — PNR " + pnr);
        message.setText(
            "Your waitlist status has changed.\n\n" +
            "PNR: " + pnr + "\n" +
            "New WL Position: " + newWlPosition + "\n" +
            "Confirmation Probability: " + String.format("%.1f", confirmationProbability * 100) + "%\n\n" +
            "Thank you for using RailIQ."
        );
        mailSender.send(message);
    }
}