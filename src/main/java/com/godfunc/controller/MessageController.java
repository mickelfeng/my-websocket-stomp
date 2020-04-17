package com.godfunc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/hello")
    public void hello(String message) {
        log.info("接受到消息 {}", message);

    }

    @GetMapping("sendUserMsg")
    @ResponseBody
    public String sendUserMsg(String user, String msg) {
        simpMessagingTemplate.convertAndSendToUser(user, "/message", msg);
        return "success";
    }

    @GetMapping("sendCommonMsg")
    @ResponseBody
    public String sendCommonMsg(String msg) {
        simpMessagingTemplate.convertAndSend("/common", msg);
        return "success";
    }
}
