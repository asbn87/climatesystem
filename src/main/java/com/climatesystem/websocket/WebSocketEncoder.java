package com.climatesystem.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.climatesystem.model.SensorMessage;
import com.google.gson.Gson;

public class WebSocketEncoder implements Encoder.Text<SensorMessage> {

    @Override
    public String encode(SensorMessage message) throws EncodeException
    {
        Gson gson = new Gson();
        String json = gson.toJson(message);
        return json;
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
