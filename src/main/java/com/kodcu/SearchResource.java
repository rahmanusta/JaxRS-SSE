package com.kodcu;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: usta
 * Date: 3/5/13
 * Time: 4:24 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/")
public class SearchResource {

    private static final SseBroadcaster BROADCASTER = new SseBroadcaster();
    private static final ScheduledExecutorService sch = Executors.newSingleThreadScheduledExecutor();

    static {

        sch.scheduleWithFixedDelay(new SearchTwitTask(BROADCASTER),0,30,TimeUnit.SECONDS);

    }

    @GET
    @Produces("text/event-stream")
    @Path("/hang")
    public EventOutput getMessages() {
        EventOutput eventOutput = new EventOutput();
        BROADCASTER.add(eventOutput);
        return eventOutput;
    }

}
