package com.demo.server.repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentsRepo {
    @Autowired
    private MongoTemplate template;

    public void postComment(Document comment) {
        
        template.insert(comment, "comments");
    }
}
