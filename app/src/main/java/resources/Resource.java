package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import gui.Platform;



public class Resource {

    public static final String PREFIX = System.getProperty("java.io.tmpdir")+(Platform.isLinux()?"/":"")+"coulomb"; 
    public static final int resources_version = 4;
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

    public static void extractResourcesDirectory(String resources_directory , String directory) throws IOException {
        for (var resource : Resources.resources) {
            if (resource.startsWith(resources_directory)) {
                System.out.println("Trying to extract resource: "+resource);
                extract(resource, directory+"/"+resource);
                // try {extract(resource, directory+"/"+resource);} 
                // catch (Exception e) {
                //     System.out.println("Couldn't extract resource: " + resource);
                //     e.printStackTrace();
                // }
            } else {
                System.out.println("Not Extracting Resource: "+resource);
            }
        }
    }

    public static void extractResourcesDirectory(String resources_directory) throws IOException {
        extractResourcesDirectory(resources_directory, PREFIX);
    }

    public static void extractWindowsLibraries() throws IOException {
        extractResourcesDirectory("/lib/win");
    }

    public static void extractRasterIcons() throws IOException {
        extractResourcesDirectory("/icons/raster");
    }

    public static void setJnaLibraryPathForWindows() {
        System.setProperty("jna.library.path", PREFIX + "/lib/win");
    }

    public static void extractWindowsResources() {
        try {
            var status_file = new File(PREFIX + "/all-done");
            int version = -1;
            boolean status_file_exists = status_file.exists();
            
            if(status_file_exists) {
                Scanner status_scanner = new Scanner(new FileInputStream(status_file));
                try{version = status_scanner.nextInt();}catch(Exception e){}
                status_scanner.close();
            }

            if(!status_file_exists || version != resources_version) {
                extractWindowsLibraries();
                //extractRasterIcons(); 
            } 

            status_file.createNewFile();

            var status_printer = new PrintWriter(status_file);
            status_printer.print(resources_version); 
            
            status_printer.close();
            
        } catch (Exception e) {
            System.out.println("Fatal Error: Couldn't extract all resources.");
            e.printStackTrace();
            System.exit(1);
        }

    }
}
