package com.example.hw9attempt4;

public class eventObject {
    public String imageURL;
    public String eventName;
    public String venue;
    public String category;
    public String date;
    public String time;

    public String id;

    public eventObject(String imageURL, String eventName, String venue,
                       String category, String date, String time, String id)
    {
        this.imageURL = imageURL;
        this.eventName = eventName;
        this.venue = venue;
        this.category = category;
        this.date = date;
        this.time = time;
        this.id = id;
    }

    public String get(String query)
    {
        switch (query) {
            case "imageURL":
                return imageURL;
            case "eventName":
                return eventName;
            case "venue":
                return venue;
            case "category":
                return category;
            case "date":
                return date;
            case "time":
                return time;
            default:
                return "Invalid field requested";
        }
    }
}
