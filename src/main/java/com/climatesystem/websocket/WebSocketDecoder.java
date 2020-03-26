package com.climatesystem.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.climatesystem.model.SensorMessage;
import com.google.gson.Gson;

public class WebSocketDecoder implements Decoder.Text<SensorMessage> {

    @Override
    public SensorMessage decode(String s) throws DecodeException
    {
        Gson gson = new Gson();
        SensorMessage message = gson.fromJson(s, SensorMessage.class);
        
        return message;
    }
    
    @Override
    public boolean willDecode(String s)
    {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig)
    {
        // Custom initialization logic
    }

    @Override
    public void destroy()
    {
        // Close resources
    }
}
