package com.berkson.wish.wishlist.infra.config;

import com.mongodb.ConnectionString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * Created By : Berkson Ximenes
 * Date : 19/01/2026
 **/

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.berkson.wish.wishlist.infra.persistence")
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Value("${spring.data.mongodb.uri:mongodb://root:secret@localhost:27017/wishlistdb?authSource=admin}")
    private String mongoUri;
    @Override
    protected String getDatabaseName() {
        ConnectionString connectionString = new ConnectionString(mongoUri);
        return null;
    }
}
