package gui;

import java.util.Locale;

import ch.bailu.gtk.gdk.GdkConstants;
import ch.bailu.gtk.gtk.Gtk;
import ch.bailu.gtk.type.Str;
import resources.Resource;

public class Platform {

    private static String osName() {
        return System.getProperty("os.name", "unknown").toLowerCase(Locale.ROOT);
    }

    public static boolean isWindows() {
        return osName().contains("win");
    }

    public static boolean isMac() {
        return osName().contains("mac");
    }

    public static boolean isLinux() {
        return osName().contains("linux");
    }

    public static void init() {
        //Resource.extractThemes();
        if(isWindows()) {
            Resource.setJnaLibraryPathForWindows();
            Resource.extractWindowsResources();
        } else if (isMac()) {
            System.out.println("Mac Detected");
        }
    }

    public static void launch_url(String url) {
        //if (isLinux()) {
            Gtk.showUri(Main.app.getWindow(), new Str(url), GdkConstants.CURRENT_TIME);
        // } else if (isWindows()) {
        //     try{
        //         String [] command = {"start",url};
        //         new ProcessBuilder(command).start();
        //     }catch (Exception e) {
        //         e.printStackTrace();
        //         System.out.println("Unable to launch URI");
        //     }
        // }

    }

    /* 
    public static void extractResource(String resource_path, String filesystem_path) throws IOException {
        // System.out.println("Looking for resource: "+resource_path);
        InputStream input = Main.class.getResourceAsStream(resource_path);
        if (input == null) {
            System.out.println("Couldn't find resource: " + resource_path);
        }
        FileOutputStream output = new FileOutputStream(filesystem_path);
        byte[] buf = new byte[256];
        int read = 0;
        while ((read = input.read(buf)) > 0) {
            output.write(buf, 0, read);
        }
        output.close();
    }
    */
    //private final static String APP_NAME = "coulomb";
    //public static final String TEMP_DIR = System.getProperty("java.io.tmpdir") + APP_NAME;


    // private final static String RANDOM_NUMBER = String.valueOf((int)
    // (Math.random()*1000));
    // private final static String TEMP_DIR = System.getProperty("java.io.tmpdir") +
    // APP_NAME + RANDOM_NUMBER;
    /*
    private static final String[] windows_libraries = {
            "libgif-7.dll",
            "libgstcheck-1.0-0.dll",
            "libgstcontroller-1.0-0.dll",
            "libgstreamer-1.0-0.dll",
            "libgstnet-1.0-0.dll",
            "libgstbase-1.0-0.dll",
            "libidn2-0.dll",
            "libmpdec++-4.dll",
            "libmpdec-4.dll",
            "libatomic-1.dll",
            "libstdc++-6.dll",
            "libgomp-1.dll",
            "libgcc_s_seh-1.dll",
            "libquadmath-0.dll",
            "libappstream-5.dll",
            "libiconv-2.dll",
            "libcharset-1.dll",
            "libepoxy-0.dll",
            "libssh2-1.dll",
            "libjson-glib-1.0-0.dll",
            "libfribidi-0.dll",
            "edit.dll",
            "libtre-5.dll",
            "libxmlb-2.dll",
            "libtiffxx-6.dll",
            "libtiff-6.dll",
            "libtermcap-0.dll",
            "libnghttp2-14.dll",
            "libcurl-4.dll",
            "libdatrie-1.dll",
            "libpython3.11.dll",
            "libpython3.dll",
            "libintl-8.dll",
            "libasprintf-0.dll",
            "libssl-3-x64.dll",
            "libcrypto-3-x64.dll",
            "libharfbuzz-gobject-0.dll",
            "libharfbuzz-0.dll",
            "libharfbuzz-subset-0.dll",
            "libbz2-1.dll",
            "libtasn1-6.dll",
            "libzstd.dll",
            "libp11-kit-0.dll",
            "libgraphene-1.0-0.dll",
            "libsystre-0.dll",
            "librsvg-2-2.dll",
            "liblzo2-2.dll",
            "libwebpmux-3.dll",
            "libsharpyuv-0.dll",
            "libwebpdemux-2.dll",
            "libwebp-7.dll",
            "libwebpdecoder-3.dll",
            "libpsl-5.dll",
            "libsqlite3-0.dll",
            "liblzma-5.dll",
            "libpixman-1-0.dll",
            "libfreetype-6.dll",
            "libunistring-5.dll",
            "libpcre2-posix-3.dll",
            "libpcre2-32-0.dll",
            "libpcre2-8-0.dll",
            "libpcre2-16-0.dll",
            "libcares-2.dll",
            "libexpat-1.dll",
            "libhistory8.dll",
            "libreadline8.dll",
            "libfontconfig-1.dll",
            "libgraphite2.dll",
            "tcl86.dll",
            "libgdk_pixbuf-2.0-0.dll",
            "libgio-2.0-0.dll",
            "libgobject-2.0-0.dll",
            "libgmodule-2.0-0.dll",
            "libgirepository-2.0-0.dll",
            "libgthread-2.0-0.dll",
            "libglib-2.0-0.dll",
            "libnghttp3-9.dll",
            "libdeflate.dll",
            "libpanelw6.dll",
            "libncurses++w6.dll",
            "libmenuw6.dll",
            "libformw6.dll",
            "libncursesw6.dll",
            "libcairo-gobject-2.dll",
            "libcairo-2.dll",
            "libcairo-script-interpreter-2.dll",
            "libpng16-16.dll",
            "tk86.dll",
            "libadwaita-1-0.dll",
            "libbrotlienc.dll",
            "libbrotlicommon.dll",
            "libbrotlidec.dll",
            "libjbig-0.dll",
            "libLerc.dll",
            "libjpeg-8.dll",
            "libturbojpeg.dll",
            "libxml2-2.dll",
            "libyaml-0-2.dll",
            "zlib1.dll",
            "libgtk-4-1.dll",
            "libwinpthread-1.dll",
            "libffi-8.dll",
            "libpangoft2-1.0-0.dll",
            "libpango-1.0-0.dll",
            "libpangowin32-1.0-0.dll",
            "libpangocairo-1.0-0.dll",
            "libthai-0.dll",
    };*/

    // public static void extract_windows_libraries() {
    //     boolean all_libs_extracted = true;
    //     new File(TEMP_DIR + "/win").mkdirs();
    //     for (var lib : resources.Resource.resources.list("/win")) {
    //         try {
    //             extractResource(/* "/win/"+ */lib, TEMP_DIR + "/" + lib);
    //             // System.out.println("Extracted "+lib+"to "+TEMP_DIR);
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //             all_libs_extracted = false;
    //             // System.out.println("Error");
    //             break;
    //         }
    //     }
    //     if (all_libs_extracted) {
    //         System.setProperty("jna.library.path", TEMP_DIR + "/win");
    //     }
    // }

    // public static void init() {
    //     System.out.println("Libraries:");
    //     try {
    //         for (var lib : resources.Resources.getResourceListing(Platform.class, "win/")) {
    //             System.out.println(lib);
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     if (isWindows()) {
    //         // System.out.println("Detected Operating System: Windows");
    //         extract_windows_libraries();
    //     }
    // }
}