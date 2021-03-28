package com.gif.main;

import com.gif.EmailSender;
import com.gif.HtmlChangeDetector;
import com.gif.TimeScheduler;

import java.util.Arrays;

public class NotifierMain {

    public static void main(String[] args) {
        checkArguments(args);
        var minScheduleTime = Integer.parseInt(args[0]);
        var maxScheduleTime = Integer.parseInt(args[1]);
        var gmailUsername = args[2];
        var gmailPassword = args[3];
        var toAddress = args[4];
        var url = args[5];
        var cssQuery = args[6];
        var scheduler = TimeScheduler.of(minScheduleTime, maxScheduleTime);
        var emailSender = EmailSender.of(gmailUsername, gmailPassword, toAddress);
        var htmlChangeDetector = HtmlChangeDetector.of(url, cssQuery, scheduler, emailSender);
        htmlChangeDetector.listenForChanges();
    }

    private static void checkArguments(String[] args) {
        var argsAsList = Arrays.asList(args);
        if (argsAsList.size() != 7) {
            usage();
            System.exit(-1);
        }
    }

    private static void usage() {
        System.out.println("Usage: java -jar sauron.jar \"min_schedule_time_in_seconds\" " +
                "\"max_schedule_time_in_seconds\" " + "\"your_gmail_username\" " + "\"your_gmail_password\" " +
                "\"toAddress\" " + "\"url\" " + "\"cssQuery\"");
        System.out.println("Example: java -jar sauron.jar \"4\" \"10\" \"sauron@gmail.com\" \"sauronpass\" \"lotr@gmail.com\" " +
                "\"www.google.com\" \"#__next\"");
    }
}
