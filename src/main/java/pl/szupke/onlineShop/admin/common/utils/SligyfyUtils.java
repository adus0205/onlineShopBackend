package pl.szupke.onlineShop.admin.common.utils;

import com.github.slugify.Slugify;
import org.apache.commons.io.FilenameUtils;

public class SligyfyUtils {
    public static String slugifyFileName(String filename) {
        String name = FilenameUtils.getBaseName(filename);
        Slugify slg = new Slugify();
        String changeName = slg
                .withCustomReplacement("_","-")
                .slugify(name);
        return changeName + "." + FilenameUtils.getExtension(filename);
    }

    public static String slugifySlug(String slug) {
        Slugify slugify = new Slugify();
        return slugify.withCustomReplacement("_","-")
                .slugify(slug);
    }
}
