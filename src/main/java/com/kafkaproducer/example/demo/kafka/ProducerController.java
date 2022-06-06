package com.kafkaproducer.example.demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kafka")
public class ProducerController {
    @Autowired
    KafkaTemplate<String,User> kafkaTemplate;
    KafkaTemplate<String,String> kt;
    private static final String TOPIC="KafkaTest2";


   public ProducerController(KafkaTemplate<String, String> kt) {
        this.kt = kt;
    }

    @GetMapping("/publish")
    public String home(){
        kt.send(TOPIC,"Hello World!");
        return "Welcome to the Home page!";
    }

    @GetMapping("/publish/m{name}")
    public String namedisp(@PathVariable String name){
        kt.send(TOPIC,name);
        return "Published "+name;
    }

    @GetMapping("/publish/{name}")
    public String post(@PathVariable String name){
        kafkaTemplate.send(TOPIC,new User(name,"IT",5000L));
        return "Published the message successfully";
    }

    @PostMapping("/publish")
    public String postJ(@RequestBody User user){
       kafkaTemplate.send(TOPIC,user);
       String n= user.getName();
       return "Published "+n+" successfully!";
    }
}
