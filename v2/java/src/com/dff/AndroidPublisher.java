package com.dff;


import com.google.play.developerapi.samples.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

/**
 * Class that deals with the google play api in order to parse and perform multiple actions.
 *
 * @author Anthony Nahas
 * @version 1.0.0
 * @since 18.05.2017
 */
public class AndroidPublisher {

    public static void main(String[] args) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        //FileReader fileReader = new FileReader(new File("v2/java/src/resources/android-play-publisher2.json").getAbsolutePath());
        FileReader fileReader = new FileReader("C:\\git\\avLight.App\\android-play-publisher.json");

        Object object = parser.parse(fileReader);
        JSONObject jsonObject = (JSONObject) object;
        ApplicationConfig.APPLICATION_NAME = (String) jsonObject.get(ApplicationConfig.properties.APPLICATION_NAME.name());
        ApplicationConfig.PACKAGE_NAME = (String) jsonObject.get(ApplicationConfig.properties.PACKAGE_NAME.name());
        ApplicationConfig.TRACK = (String) jsonObject.get(ApplicationConfig.properties.TRACK.name());
        ApplicationConfig.SERVICE_ACCOUNT_EMAIL = (String) jsonObject.get(ApplicationConfig.properties.SERVICE_ACCOUNT_EMAIL.name());
        ApplicationConfig.APK_FILE_PATH = (String) jsonObject.get(ApplicationConfig.properties.APK_FILE_PATH.name());
        AndroidPublisherHelper.SRC_RESOURCES_KEY_P12 = (String) jsonObject.get(AndroidPublisherHelper.properties.SRC_RESOURCES_KEY_P12.name());

        print(jsonObject);

        ListApks.excute();
        BasicUploadApk.execute();
        ListApks.excute();
    }

    private static void print(Object o) {
        System.out.print(o);
    }
}
