package com.example.mongotest;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MongoTestApplicationTests {
	Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private MongodExecutable mongodExecutable;

	@Before
	public void setup() throws IOException {
		ImmutableMongodConfig config = MongodConfig
				.builder()
				.version(Version.Main.V5_0)
				.net(new Net("localhost", 27017,false))
				.build();

		MongodStarter starter = MongodStarter.getDefaultInstance();
		mongodExecutable = starter.prepare(config);
		mongodExecutable.start();
	}

	@After
	public void clean() {
		mongodExecutable.stop();
	}

	@Test
	public void contextLoads() {
		try (MongoClient mongoClient = MongoClients.create()) {
			mongoClient.listDatabaseNames().forEach(x -> log.info(x));
		}
	}

}
