package com.kodcu;


import org.glassfish.jersey.media.sse.SseFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: usta
 * Date: 3/5/13
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
@ApplicationPath("/search")
public class SearchEndpoint extends Application {

    @Override
       public Set<Class<?>> getClasses() {

           Set<Class<?>> classes=new HashSet<>();
           classes.add(SearchResource.class);
           return classes;
       }

       @Override
       public Set<Object> getSingletons() {
           Set<Object> singletons=new HashSet<>();
        //  singletons.add(new JacksonFeature());
           singletons.add(new SseFeature());
           return singletons;
       }
}
