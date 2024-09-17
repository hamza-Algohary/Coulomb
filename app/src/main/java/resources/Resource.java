package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import gui.Platform;

public class Resource {

    public static final String PREFIX = System.getProperty("java.io.tmpdir")+(Platform.isLinux()?"/":"")+"coulomb"; 

    /* Create a file with all its parent directories.  */
    public static FileOutputStream createDeepFile(String path) throws FileNotFoundException {
        new File(new File(path).getParent()).mkdirs();
        return new FileOutputStream(path);
    }

    public static void extract(String resource_path, String output_file_path) throws IOException {
        // System.out.println("Looking for resource: "+resource_path);
        InputStream input = Resource.class.getResourceAsStream(resource_path);
        if (input == null) {
            System.out.println("Couldn't find resource: " + resource_path);
        }
        FileOutputStream output = createDeepFile(output_file_path);
        byte[] buf = new byte[256];
        int read = 0;
        while ((read = input.read(buf)) > 0) {
            output.write(buf, 0, read);
        }
        output.close();
    }

    // public static void extract(String resource_path, String output_file_path) throws IOException {
    //     Resource.class.getResourceAsStream(resource_path).transferTo(createDeepFile(output_file_path));
    // }

    public static void extractResourcesDirectory(String resources_directory , String directory){
        for (var resource : Resources.resources) {
            if (resource.startsWith(resources_directory)) {
                System.out.println("Trying to extract resource: "+resource);
                try {extract(resource, directory+"/"+resource);} 
                catch (Exception e) {
                    System.out.println("Couldn't extract resource: " + resource);
                    e.printStackTrace();
                }
            } else {
                System.out.println("Not Extracting Resource: "+resource);
            }
        }
    }

    public static void extractResourcesDirectory(String resources_directory) {
        extractResourcesDirectory(resources_directory, PREFIX);
    }

    public static void extractWindowsLibraries() {
        extractResourcesDirectory("/lib/win");
        System.setProperty("jna.library.path", PREFIX + "/lib/win");
    }

    public static void extractRasterIcons() {
        extractResourcesDirectory("/icons/raster");
    }

    public static void extractThemes() {
        extractResourcesDirectory("/themes");
    }

    public static void extractWindowsResources() {
        extractWindowsLibraries();
        extractRasterIcons();
    }
}
