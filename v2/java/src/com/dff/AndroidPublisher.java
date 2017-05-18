package com.dff;


import com.google.play.developerapi.samples.AndroidPublisherHelper;
import com.google.play.developerapi.samples.ApplicationConfig;
import com.google.play.developerapi.samples.ListApks;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by anahas on 18.05.2017.
 *
 * @author Anthony Nahas
 * @version 1.0
 * @since 18.05.2017
 */
public class AndroidPublisher {

    public static void main(String[] args) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        FileReader fileReader = new FileReader(new File("v2/java/src/resources/android-play-publisher2.json")
            .getAbsolutePath());

        Object object = parser.parse(fileReader);
        JSONObject jsonObject = (JSONObject) object;
        AndroidPublisherHelper.SRC_RESOURCES_KEY_P12 = (String) jsonObject.get(AndroidPublisherHelper.properties.SRC_RESOURCES_KEY_P12.name());
        ApplicationConfig.APPLICATION_NAME = (String) jsonObject.get(ApplicationConfig.properties.APPLICATION_NAME.name());
        ApplicationConfig.PACKAGE_NAME = (String) jsonObject.get(ApplicationConfig.properties.PACKAGE_NAME.name());
        ApplicationConfig.SERVICE_ACCOUNT_EMAIL = (String) jsonObject.get(ApplicationConfig.properties.SERVICE_ACCOUNT_EMAIL.name());
        ApplicationConfig.APK_FILE_PATH = (String) jsonObject.get(ApplicationConfig.properties.APK_FILE_PATH.name());

        print(jsonObject);

        ListApks.excute();
    }

    private static void print(Object o) {
        System.out.print(o);
    }
}
