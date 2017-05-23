package com.dff.helpers;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by anahas on 18.05.2017.
 *
 * @author Anthony Nahas
 * @version 1.0
 * @since 18.05.2017
 */
public class FileHelper {

    public FileHelper() {
    }

    public String getFileExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (i > p) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }

    /**
     * Get the parent directory path of the current workplace
     *
     * @return - the path of the parent directory
     */
    public String getParentDirectoryPath() {
        return new File("").getAbsolutePath();
    }

    /**
     * Get the node modules directory if it is available
     *
     * @return - the target directory file
     * @throws FileNotFoundException - if it was not found
     */
    public File getNodeModulesDir() throws FileNotFoundException {
        File file = new File(getParentDirectoryPath());
        String fileName = file.getName();

        while (!fileName.equals("node_modules")) {
            file = new File(file.getAbsolutePath()).getParentFile();
            if (file == null) {
                throw new FileNotFoundException("404 - node_modules directory not found");
            }
            fileName = file.getName();
        }
        return file;
    }
}
