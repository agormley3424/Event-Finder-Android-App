package com.example.hw9attempt4;

public class ArtistObject {
    public String name;

    public String followers;

    public String spotifyLink;

    public String popularity;

    public String profileImage;

    public String album1;

    public String album2;

    public String album3;

    public ArtistObject(String name, String followers, String spotifyLink, String popularity,
                        String profileImage, String album1, String album2, String album3)
    {
        this.name = name;
        this.followers = followers;
        this.spotifyLink = spotifyLink;
        this.popularity = popularity;
        this.album1 = album1;
        this.album2 = album2;
        this.album3 = album3;
        this.profileImage = profileImage;
    }

    public ArtistObject(){}

}
