package org.terracotta;

import org.terracotta.message.pipe.Pipe;
import org.terracotta.message.pipe.BlockingQueueBasedPipe;
import org.terracotta.message.topology.Topology;
import org.terracotta.message.topology.DefaultTopology;
import org.terracotta.message.topology.TopologyManager;
import org.terracotta.message.routing.Router;
import org.terracotta.message.routing.RoundRobinRouter;
import org.terracotta.message.routing.Route;
import org.terracotta.util.jmx.ClusterEvents;
import org.terracotta.util.jmx.SimpleListener;


import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.MalformedObjectNameException;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * Created by vkatson
 * Date: 05.02.2009
 * Time: 13:01:24
 */
public class Consumer {
    public static void main(String[] args) throws InterruptedException, MalformedObjectNameException {
        System.out.println("-------------------------start-------------------------");
        Pipe.Factory pipeFactory = new BlockingQueueBasedPipe.Factory(10);
        Topology.Factory topologyFactory = new DefaultTopology.Factory(pipeFactory);

        Topology topology = TopologyManager.getInstance().<String, String>getOrCreateTopology("myTopologyName", topologyFactory);
/*
        Router router = new RoundRobinRouter();

        Route<String, String> route = topology.getRouteFor(router, null);
        Pipe<String> pipe = topology.getPipeFor(route.getRoutingID());
*/

        SimpleListener listener = new MyListener();
        ClusterEvents.registerListener(listener);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        System.out.println("My node id is: " + listener.getMyNodeId());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");

        Pipe<String> pipe = topology.getOrCreatePipeFor("myRoutingId");

        for (; ;) {
            String message = pipe.take();
            System.out.println(message);
        }
    }
}
