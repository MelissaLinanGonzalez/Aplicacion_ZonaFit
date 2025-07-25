package mlg.zona_fit;

import com.formdev.flatlaf.FlatDarculaLaf;
import mlg.zona_fit.gui.ZonaFitForma;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class ZonaFitSwing {
    public static void main(String[] args) {
        // Configuramos el modo oscuro
        FlatDarculaLaf.setup();
        // INstanciar la fábrica de Spring
        ConfigurableApplicationContext contextoSpring = new SpringApplicationBuilder(ZonaFitSwing.class).headless(false).web(WebApplicationType.NONE).run(args);

        // Crear un objeto de Swing a partir de la fábrica de Spring
        SwingUtilities.invokeLater(() -> {
            ZonaFitForma zonaFitForma = contextoSpring.getBean(ZonaFitForma.class);
            zonaFitForma.setVisible(true);
        });
    }
}
