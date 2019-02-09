package com.app.chatapp.app; // {{ groupId}}.app
// import the rest service you created!
import com.app.chatapp.rest.RestService;
import com.app.chatapp.rest.RestService;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

public class EntryPoint extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    public EntryPoint() {
        // Register our rest service
        singletons.add(new RestService());
    }
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}