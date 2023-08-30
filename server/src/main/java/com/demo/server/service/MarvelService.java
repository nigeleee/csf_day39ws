package com.demo.server.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.demo.server.model.Comment;
import com.demo.server.repository.CommentsRepo;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class MarvelService {

    @Autowired
    private CommentsRepo repo;

    @Value("${public_key}")
    String publicKey;

    @Value("${hash}")
    String hash;

    private static final String apiUri = "https://gateway.marvel.com:443/v1/public/characters";

    RestTemplate template = new RestTemplate();

    public JsonArray getCharacter(String name) {
        String uri = UriComponentsBuilder.fromUriString(apiUri)
                .queryParam("nameStartsWith", name)
                // .queryParam("limit", limit)
                // .queryParam("offset", offset)
                .queryParam("ts", 1)
                .queryParam("apikey", publicKey)
                .queryParam("hash", hash)
                .toUriString();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Built URI: " + uri);

        try {

            ResponseEntity<String> result = template.getForEntity(uri, String.class);

            String payload = result.getBody();
            JsonReader reader = Json.createReader(new StringReader(payload));

            JsonObject obj = reader.readObject();
            JsonObject dataObj = obj.getJsonObject("data");
            JsonArray resultsArray = dataObj.getJsonArray("results");

            JsonArrayBuilder results = Json.createArrayBuilder();
            
            // iterate through the array
            for (JsonValue value : resultsArray) {
                // declare an object
                JsonObject valueObj = value.asJsonObject();

                // get id
                Integer id = valueObj.getJsonNumber("id").intValue();
                // get name
                String characterName = valueObj.getString("name");
              
                // getting the image url from thumbnail and extension
                JsonObject thumbnailObj = valueObj.getJsonObject("thumbnail");
                String path = thumbnailObj.getString("path");
                String extension = thumbnailObj.getString("extension");
                String imageUrl = path + "." + extension;

                JsonObjectBuilder objBuilder = Json.createObjectBuilder();
                JsonObject details = objBuilder
                        .add("id", id)
                        .add("name", characterName)
                        .add("imageUrl", imageUrl)
                        .build();

                results.add(details);

            }

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Character details" + results);

            return results.build();
            // return new Marvel(results);

        } catch (Exception e) {
            throw new InternalError("No records found");
        }

    }

    public JsonObject getCharacterById(int id) {

         String uri = UriComponentsBuilder.fromUriString(apiUri)
                    .path("/{id}")
                    .queryParam("ts", 1)
                    .queryParam("apikey", publicKey)
                    .queryParam("hash", hash)
                    .buildAndExpand(id)
                    .toUriString();

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Built URI: " + uri);

        try {

            ResponseEntity<String> result = template.getForEntity(uri, String.class);

            String payload = result.getBody();
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonObject obj = reader.readObject();
            JsonObject dataObject = obj.getJsonObject("data");

            JsonArray resultArray = dataObject.getJsonArray("results");
            JsonObject detailObj = resultArray.getJsonObject(0);

            //id
            int cid = detailObj.getJsonNumber("id").intValue();
            // get name
            String name = detailObj.getString("name");
            //get description
            String description = detailObj.getString("description");

            //thumbnail
            JsonObject thumbnailObj = detailObj.getJsonObject("thumbnail");
            String path = thumbnailObj.getString("path");
            String extension = thumbnailObj.getString("extension");
            String imageUrl = path + "." + extension;

            JsonObjectBuilder builder = Json.createObjectBuilder();
            JsonObject details = builder    
                .add("id", cid)
                .add("name", name)
                .add("description", description)
                .add("imageUrl", imageUrl)
                .build();

            return details;

        } catch (Exception e) {
            throw new InternalError("Not found");
        }

    }

    public void postComments(int id, String comment) {
        
        String cid = UUID.randomUUID().toString().substring(0, 8);

        System.out.println(">>>>>>>>>>>>>>>>>> Received parameters: id=" + id + ", comment=" + comment);
         System.out.println(">>>>>>>>>>>>>>>>>> Generated CID: " + cid);
        
        Document commentDoc = new Document();
        commentDoc.append("cid", cid);
        commentDoc.append("text", comment);

        // JsonObject obj = Json.createObjectBuilder()
        //     .add("cid", cid)
        //     .add("text", comment)
        //     .build();
        
        // System.out.println(">>>>>>>>>>>>>>>>>>>>>> Comment document: " + commentDoc.toJson());


        repo.postComment(commentDoc);
        // repo.postComment(Document.parse(obj.toString()));
        
    }
}
