package urun.fotografGosterme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class fotoGoster extends JPanel {
    private BufferedImage image;

    public fotoGoster() {
        try {
            image = ImageIO.read(new File("![](../../bos.png)"));
        } catch (IOException ex) {
            // handle exception...
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }


}
