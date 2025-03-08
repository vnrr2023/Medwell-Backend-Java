package com.example.medwell.medwellbackend.scheduler;


import com.example.medwell.medwellbackend.utility.MailUtility;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;

@Component
public class AppointmentEmailScheduler {

    private final TaskScheduler taskScheduler;
    private final MailUtility mailUtility;


    public AppointmentEmailScheduler( MailUtility mailUtility) {
        ThreadPoolTaskScheduler taskScheduler=new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        this.taskScheduler = taskScheduler;
        this.mailUtility = mailUtility;
    }

    private String formatDate(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");
        String formattedDateTime = dateTime.format(formatter);
        DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH);
        String dayOfWeek = dateTime.format(dayOfWeekFormatter);
        return dayOfWeek+" "+formattedDateTime;
    }

    @Async
    public void createRemindersAndSendConfirmationMail(String email,String doctorName,String patientName,String address,LocalDateTime appointmentTime,double lat,double lon) throws MessagingException {

        mailUtility.sendAppointmentConfirmationMail(email,
                String.format("✅ You're All Set! Your Appointment with Dr. %s is Confirmed",doctorName),doctorName,patientName,address, formatDate(appointmentTime),lat,lon);

        scheduleReminders("1_day",appointmentTime,24 * 60 * 60 * 1000,email,doctorName,patientName,address,lat,lon);
        scheduleReminders("12_hours",appointmentTime,12 * 60 * 60 * 1000,email,doctorName,patientName,address,lat,lon);
        scheduleReminders("2_hours",appointmentTime,2 * 60 * 60 * 1000,email,doctorName,patientName,address,lat,lon);

    }

    private void scheduleReminders(String reminderType, LocalDateTime appointmentDateTime, long delayinMillis,String email,String doctorName,String patientName, String address,double lat, double lon) {

            Duration delay=Duration.ofMillis(delayinMillis);
            LocalDateTime reminderTime=appointmentDateTime.minus(delay);
            ZonedDateTime zonedReminderTime = reminderTime.atZone(ZoneId.systemDefault());
            Date reminderDate = Date.from(zonedReminderTime.toInstant());

        taskScheduler.schedule(()->{
           String subject=switch (reminderType){
               case "1_day" -> "⏳ Reminder: Your appointment is tomorrow!";
               case "12_hours" -> "📅 Reminder: Your appointment is in 12 hours!";
               case "2_hours" -> "🚀 Reminder: Your appointment is in 2 hours!";
               default -> "";
           };


            try {
                mailUtility.sendReminderMail(email,subject,doctorName,patientName,address,formatDate(appointmentDateTime),lat,lon);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        },reminderDate);
        System.out.println("✅ Reminder scheduled for " + reminderType + " at " + reminderTime);
    }

}
