package Utilities;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    /**
     * @param cls:      use getClass()
     * @param pathname: name of path
     * @throws NullPointerException: throw when cls is null
     */
    public static ImageIcon loadImageResource(Class<?> cls, String pathname) {
        if (cls == null) throw new NullPointerException();

        ImageIcon image = null;
        try {
            InputStream stream = cls.getResourceAsStream(pathname);
            if (stream == null) throw new NullPointerException();

            BufferedImage resource = ImageIO.read(stream);
            image = new ImageIcon(resource);
        } catch (NullPointerException | IOException ex) {
            System.out.println("Cannot load file\n" + ex.getMessage());
        }

        return image;
    }

    public static @NotNull ImageIcon resizeImage(@NotNull ImageIcon imageIcon, int width, int height) {
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
