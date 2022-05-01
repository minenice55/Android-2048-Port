package com.example.a2048game;

public class Player {
    String username;
    String passwordHash;
    String email;
    String avatarCode;
    int highScore;

    public static String[] testCodes = {
            "080240030803032c040e0308050409020e040a070001000804000a01001e4004000214031304080f06030b05030a", // Matt
            "0800390522030504010d0353060400020a0004090007010804000a53005456040002140413010f0d04000a020109", // Elem
            "04007f030b01070f020c03080a080c02120000030500000f01070f04003a7f08000814031308100b080110080711", // i want die
            "01007f030d0304120c0b0301050802000f0206050002000804000a01001e00040002140313040702030101040204", // Beef Boss
            "0800400308040402020C0308060406020A0400000004000804000A0800444004000214031304170D04000A040109", // Guest A
            "080040030C040402020C0306060406020A0000000005000804000A0600374004000214031304170D04000A040109", // Guest B
            "0800400308040402020C0301060406020A0100000000000804000A0100214004000214031304170D04000A040109", // Guest C
            "0800400308030404020C0308060400020A0200000002010804000A0800184004000214031304170D04000A040109", // Guest D
            "080040030D030404020C0307060400020A0000000006010804000A07000E4004000214031304170D04000A040109", // Guest E
            "0800400308030404020C0301060400020A0000000007010804000A01000C4004000214031304170D04000A040109"  // Guest F
    };

    public Player() {
    }

    public Player(String username, String passwordHash, String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.avatarCode = MiiStudioApi.TESTCODE;
        this.highScore = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarCode() {
        return avatarCode;
    }

    public void setAvatarCode(String avatarCode) {
        this.avatarCode = avatarCode;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
