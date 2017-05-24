/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.play.developerapi.samples;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.repackaged.com.google.common.base.Preconditions;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Apks.Upload;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Commit;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Insert;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Tracks.Update;
import com.google.api.services.androidpublisher.model.Apk;
import com.google.api.services.androidpublisher.model.AppEdit;
import com.google.api.services.androidpublisher.model.Track;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Uploads an apk to the target track.
 */
public class BasicUploadApk {

    private static final Log log = LogFactory.getLog(BasicUploadApk.class);

    /**
     * Track for uploading the apk, can be 'alpha', beta', 'production' or
     * 'rollout'.
     */

    public enum Track_ {
        none,
        alpha,
        beta,
        rollout,
        production
    }

    public static BasicUploadApk newInstance() {
        return new BasicUploadApk();
    }

    private void checkArguments() {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(ApplicationConfig.PACKAGE_NAME),
            "ApplicationConfig.PACKAGE_NAME cannot be null or empty!");
    }

    /**
     * Create the API Service
     *
     * @return - the api service
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private AndroidPublisher createAPIService() throws IOException, GeneralSecurityException {
        return AndroidPublisherHelper.init(
            ApplicationConfig.APPLICATION_NAME, ApplicationConfig.SERVICE_ACCOUNT_EMAIL);
    }

    /**
     * Create a new edit to make changes to the listing.
     *
     * @param edits - the new edit
     * @return
     * @throws IOException
     */
    private Insert createNewEdit(Edits edits) throws IOException {
        return edits
            .insert(ApplicationConfig.PACKAGE_NAME,
                null /** no content */);
    }

    private AbstractInputStreamContent createNewFileContent() {
        return new FileContent(AndroidPublisherHelper.MIME_TYPE_APK, new File(ApplicationConfig.APK_FILE_PATH));
    }

    /**
     * Upload new apk to developer console
     *
     * @param edits  the used edits
     * @param editId the used edit id
     * @return
     * @throws IOException
     */
    private Upload uploadNewAPK(Edits edits, String editId) throws IOException {
        return
            edits
                .apks()
                .upload(ApplicationConfig.PACKAGE_NAME, editId, createNewFileContent());
    }

    private void updateTrack(Edits edits, String editId, String track, int versionCode) throws IOException {
        List<Integer> apkVersionCodes = new ArrayList<>();
        if (versionCode != -1) {
            apkVersionCodes.add(versionCode);
        }
        Update updateTrackRequest = edits
            .tracks()
            .update(ApplicationConfig.PACKAGE_NAME,
                editId,
                track,
                new Track().setVersionCodes(apkVersionCodes));

        Track updatedTrack = updateTrackRequest.execute();

        if (updatedTrack.getTrack() != null) {
            log.info(String.format("Track %s has been updated.", updatedTrack.getTrack()));
        }
    }

    /**
     * Assign APK to target Track
     *
     * @param apk
     * @param edits
     * @param editId
     * @throws IOException
     */
    private void assignAPKtoTargetTrack(Apk apk, Edits edits, String editId) throws IOException {
        updateTrack(edits, editId, ApplicationConfig.TRACK.equals(Track_.alpha.name())
            ? Track_.beta.name() : Track_.alpha.name(), -1);

        updateTrack(edits, editId, ApplicationConfig.TRACK, apk.getVersionCode());
    }

    /**
     * Commit changes for edit
     *
     * @param edits
     * @param editId
     * @throws IOException
     */
    private void commit(Edits edits, String editId) throws IOException {
        Commit commitRequest = edits.commit(ApplicationConfig.PACKAGE_NAME, editId);
        AppEdit appEdit = commitRequest.execute();
        log.info(String.format("App edit with id %s has been comitted", appEdit.getId()));
    }

    /**
     * Perform a new workflow by starting with arguments'check, creating new edit, upload then commit!
     *
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public void execute() throws GeneralSecurityException, IOException {

        checkArguments();

        final Edits edits = createAPIService().edits();
        AppEdit edit = createNewEdit(edits).execute();
        final String editId = edit.getId();
        log.info(String.format("Created edit with id: %s", editId));

        Apk apk = uploadNewAPK(edits, editId).execute();
        log.info(String.format("Version code %d has been uploaded", apk.getVersionCode()));

        assignAPKtoTargetTrack(apk, edits, editId);

        commit(edits, editId);
    }
}