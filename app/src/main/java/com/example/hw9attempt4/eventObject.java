package com.example.hw9attempt4;

public class eventObject {
    public String imageURL;
    public String eventName;
    public String venue;
    public String category;
    public String date;
    public String time;

    public eventObject(String imageURL, String eventName, String venue,
                       String category, String date, String time)
    {
        this.imageURL = imageURL;
        this.eventName = eventName;
        this.venue = venue;
        this.category = category;
        this.date = date;
        this.time = time;
    }
}
