package com.climatesystem.websocket;

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
	private String mac;
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("id") String id) {
	    setSession(session);
	    
	    switch(id) {
	    
	    	case "dashboard":
	    		dashboardEndpoints.add(this);
	    		break;
	    		
	    	default:
	    		deviceEndpoints.add(this);
	    		break;
	    }
	    	
	}
	
	@OnClose
	public void onClose(Session session) {
		
		if(deviceEndpoints.contains(this)) {
			System.out.println(
					deviceEndpoints.get(deviceEndpoints.indexOf(this)).getMac()
					+ " disconnected from websocket.");	
			deviceEndpoints.remove(this);
		}
		else if(dashboardEndpoints.contains(this)) {
			System.out.println(
					dashboardEndpoints.get(dashboardEndpoints.indexOf(this)).getMac()
					+ " disconnected from websocket.");	
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
		
		// Forward incoming message to all connected peers
		for(Session sess : session.getOpenSessions()) {
			sess.getAsyncRemote().sendObject(message);
		}
	}
}
