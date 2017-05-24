package com.dff;


import com.dff.helpers.FileHelper;
import com.google.play.developerapi.samples.AndroidPublisherHelper;
import com.google.play.developerapi.samples.ApplicationConfig;
import com.google.play.developerapi.samples.BasicUploadApk;
import com.google.play.developerapi.samples.ListApks;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Class that deals with the google play api in order to parse and perform multiple actions.
 *
 * @author Anthony Nahas
 * @version 1.1.1
 * @since 18.05.2017
 */
public class AndroidPublisher {

    public static void main(String[] args) throws IOException, ParseException, GeneralSecurityException {

        JSONParser parser = new JSONParser();
        FileReader fileReader = new FileReader("android-play-publisher.json");
//        FileReader fileReader = new FileReader(new File("v2/java/src/resources/android-play-publisher2.json").getAbsolutePath());
        //FileReader fileReader = new FileReader("C:\\git\\avLight.App\\android-play-publisher.json");

        Object object = parser.parse(fileReader);
        JSONObject jsonObject = (JSONObject) object;

        ApplicationConfig.TRACK = (String) jsonObject.get(ApplicationConfig.properties.TRACK.name());
        if (ApplicationConfig.TRACK.equals(BasicUploadApk.Track_.none.name()) || ApplicationConfig.TRACK.isEmpty()) {
            return;
        }

        ApplicationConfig.APPLICATION_NAME = (String) jsonObject.get(ApplicationConfig.properties.APPLICATION_NAME.name());
        ApplicationConfig.PACKAGE_NAME = (String) jsonObject.get(ApplicationConfig.properties.PACKAGE_NAME.name());
        ApplicationConfig.SERVICE_ACCOUNT_EMAIL = (String) jsonObject.get(ApplicationConfig.properties.SERVICE_ACCOUNT_EMAIL.name());
        ApplicationConfig.APK_FILE_PATH = (String) jsonObject.get(ApplicationConfig.properties.APK_FILE_PATH.name());
        AndroidPublisherHelper.SRC_RESOURCES_KEY_P12 = (String) jsonObject.get(AndroidPublisherHelper.properties.SRC_RESOURCES_KEY_P12.name());

        print(jsonObject.toJSONString());

        FileHelper fileHelper = new FileHelper();
        if (!fileHelper.getFileExtension(ApplicationConfig.APK_FILE_PATH).equals("apk")) {
            System.out.println("apk extension has been not found --> expect apk file name as argument");
            String apkFileName;
            try {
                apkFileName = args[0];
                System.out.println("args[0] has been found (apkFileName) --> " + apkFileName);
                ApplicationConfig.APK_FILE_PATH += apkFileName;
                System.out.println("APK file path --> " + ApplicationConfig.APK_FILE_PATH);
            } catch (Exception e) {
                System.err.println(e
                    + System.getProperty("line.separator")
                    + "there was no apk path file found as well "
                    + "in the android-play-publisher.json as in the arguments");
            }
        }

        ListApks.execute();
        BasicUploadApk.newInstance().execute();
        ListApks.execute();
    }

    private static void print(Object o) {
        System.out.println(o);
    }
}
