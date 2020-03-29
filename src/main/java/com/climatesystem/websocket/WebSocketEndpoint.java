package com.climatesystem.websocket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.climatesystem.model.SensorMessage;

@Component
@ServerEndpoint(value = "/ws/{id}", decoders = WebSocketDecoder.class, encoders = WebSocketEncoder.class)
public class WebSocketEndpoint {

	public static List<WebSocketEndpoint> deviceEndpoints = new CopyOnWriteArrayList<>(); 
	public static List<WebSocketEndpoint> dashboardEndpoints = new CopyOnWriteArrayList<>();
	
	private Session session;
	private String id;
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("id") String id) {
	    
		setSession(session);
	    setId(id);
		
	    switch(id) {
	    
	    	case "dashboard":
	    		this.session.setMaxIdleTimeout(-1); // Dashboard sessions should have no timeout.
	    		System.out.println("New dashboard session: " + this.session.getId());
	    		dashboardEndpoints.add(this);
	    		break;
	    		
	    	default:
	    		this.session.setMaxIdleTimeout(60000); // If a message hasn't been received in 1min timeout session.
	    		System.out.println("Device connected: " + this.id);
	    		deviceEndpoints.add(this);
	    		break;
	    }
	}
	
	@OnClose
	public void onClose(Session session) throws IOException {
		
		if(deviceEndpoints.contains(this)) {
			System.out.println("Mac: " + this.getId() + ", disconnected from websocket.");	
			deviceEndpoints.remove(this);
		}
		else if(dashboardEndpoints.contains(this)) {
			System.out.println("Session id: " + session.getId() + ", closed session.");
			dashboardEndpoints.remove(this);
		}
	}
	
	@OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
	
	@OnMessage
	public void onMessage(Session session, SensorMessage message) {
		
		message.setDate(new SimpleDateFormat("HH:mm:ss:SSS").format(new Date()));
		
		// Forward incoming message to all sessions connected to Dashboard
		for(WebSocketEndpoint dashboardEndpoint : dashboardEndpoints) {
			dashboardEndpoint.getSession().getAsyncRemote().sendObject(message);
		}
	}
}
