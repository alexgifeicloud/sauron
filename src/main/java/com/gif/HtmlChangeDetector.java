package com.gif;

import org.jsoup.Jsoup;

public final class HtmlChangeDetector {

    private final String url;
    private final String cssQuery;
    private final TimeScheduler timeScheduler;
    private final EmailSender emailSender;

    private HtmlChangeDetector(String url, String cssQuery, TimeScheduler timeScheduler, EmailSender emailSender) {
        this.url = url;
        this.cssQuery = cssQuery;
        this.timeScheduler = timeScheduler;
        this.emailSender = emailSender;
    }

    public static HtmlChangeDetector of(String url, String cssQuery, TimeScheduler timeScheduler, EmailSender emailSender) {
        return new HtmlChangeDetector(url, cssQuery, timeScheduler, emailSender);
    }

    public void listenForChanges() {
        var before = "";
        var after = "";
        var userAgentsPool = new UserAgents();
        while (true) {
            try {
                before = after;
                // var randomAgent = userAgentsPool.chooseRandomAgent();
                var connection = Jsoup.connect(url);
                var document = connection.get();
                var elements = document.select(cssQuery);
                after = elements.toString();
                if (!before.equals(after)) {
                    logChange(before, after);
                    var text = "Before: " + before + "\n\n" + "After: " + after;
                    emailSender.sendEmail(document.title(), text);
                }
                Thread.sleep(timeScheduler.nextRunInterval() * 1000L);

            } catch (Exception e) {
                Logger.log("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    private void logChange(String before, String after) {
        Logger.log("Change detected.");
        Logger.log("Before: " + before);
        Logger.log("After: " + after);
    }
}
