package com.demo.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.server.service.MarvelService;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path="/api")
public class MarvelController {
    
    @Autowired
    private MarvelService service;
    
    @GetMapping(path="/search", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCharacters(@RequestParam String name) {

        System.out.println("Name: " + name);
      
        // Marvel marvel = service.getCharacter(nameStartsWith, limit, offset);
        JsonArray results = service.getCharacter(name);

        if(results != null) {
            return ResponseEntity.ok().body(results.toString());
        } else {
            return ResponseEntity.notFound().build();
        }
       
    }

    @GetMapping(path="/search/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCharacterById(@PathVariable int id) {

        JsonObject results = service.getCharacterById(id);

        if(results != null) {
            return ResponseEntity.ok().body(results.toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path="/search/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<String> postComment(@PathVariable int id, @RequestBody String comment) {
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Received comment data: " + comment); 

        service.postComments(id, comment);

        return ResponseEntity.ok().build();
    }
}
