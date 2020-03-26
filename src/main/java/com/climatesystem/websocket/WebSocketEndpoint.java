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
@ServerEndpoint(value = "/ws/{cmd}", decoders = WebSocketDecoder.class, encoders = WebSocketEncoder.class)
public class WebSocketEndpoint {

	private Session session;
	public static List<WebSocketEndpoint> listeners = new CopyOnWriteArrayList<>();
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	@OnOpen
	public void onOpen(Session session, @PathParam("cmd") String cmd) {
	    setSession(session);
	    listeners.add(this);
	}
	
	@OnClose
	public void onClose(Session session) {
	    listeners.remove(this);
	    setSession(null);
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
