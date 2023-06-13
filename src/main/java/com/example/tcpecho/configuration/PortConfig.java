package com.example.tcpecho.configuration;

import com.example.tcpecho.server.EchoServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class PortConfig {
    private static final Logger logger = LogManager.getLogger(PortConfig.class);

    public static int findPort() {
        try (InputStream inputStream = EchoServer.class.getClassLoader().
                getResourceAsStream("application.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(inputStream);
            Map<String, Object> serverConfig = (Map<String, Object>) yamlData.get("server");
            int serverPort = Integer.parseInt(serverConfig.get("port").toString());
            logger.info("Server port: " + serverPort);
            return serverPort;
        } catch (Exception e) {
            logger.error("An error occurred", e);
        }
        return 0;
    }
}
