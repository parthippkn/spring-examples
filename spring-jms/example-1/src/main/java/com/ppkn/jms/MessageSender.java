package com.ppkn.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Parthipan on 6/23/2017.
 */
@Component
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(int count) {

        for (int i = 0; i < count; i++) {
            jmsTemplate.convertAndSend("Hello :" + i);
        }

    }
}
