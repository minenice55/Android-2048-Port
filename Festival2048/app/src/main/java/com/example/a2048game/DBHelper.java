package com.example.a2048game;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DBHelper {
    FirebaseDatabase database;
    DatabaseReference userRef;
    Map<String, Player> players;
    long lastId;

    static DBHelper instance;

    private DBHelper() {
        this.database = FirebaseDatabase.getInstance("https://festival2048-default-rtdb.firebaseio.com/");
        this.players = new HashMap<>();
        this.lastId = 0;
    }

    public static Map<String, Player> getPlayers() {
        if (getInstance().players == null)
            DBHelper.getUsersReference();
        return getInstance().players;
    }

    public static DBHelper getInstance() {
        if (instance == null)
        {
            instance = new DBHelper();
        }
        return instance;
    }

    public static FirebaseDatabase getDatabase() {
        return getInstance().database;
    }

    public static DatabaseReference getUsersReference() {
        if (getInstance().userRef == null)
        {
            getInstance().userRef = getDatabase().getReference("users");
            getInstance().userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        getInstance().lastId = (snapshot).getChildrenCount();
                        getInstance().players = new HashMap<>();

                        for (DataSnapshot child: snapshot.getChildren()) {
                            getInstance().players.put(child.getKey(), child.getValue(Player.class));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return getInstance().userRef;
    }

    public Player findMatchingPlayer(String username)
    {
        getUsersReference();
        Log.println(Log.DEBUG, "Looking players ", "matching for " + username);
        return players.get(username);
    }

    public Player findMatchingPlayer(String username, String password)
    {
        getUsersReference();
        Log.println(Log.DEBUG, "Looking players ", "matching for " + username + " w/ pass");
        if (players.containsKey(username))
        {
            Player match = players.get(username);
            if (match.getUsername().equals(username) && match.getPasswordHash().equals(password))
                return match;
        }
        return null;
    }

    public static boolean addPlayer(String userId, String username, String password, String email) {
        DatabaseReference r = getUsersReference();

        if (getInstance().findMatchingPlayer(username) == null) {
            Player user = new Player(username, password, email);
            user.setAvatarCode(Player.testCodes[(int) getInstance().lastId % Player.testCodes.length]);
            r.child(username).setValue(user);
            return true;
        }
        return false;
    }
}
