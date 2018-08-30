package clientui;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import client.LightingClient;
import java.awt.Dimension;

/* @author:David Donovan x14111021
 *
 * @Author skeleton code by Dominic Carr	
 */
public class LightingUI extends ClientUI {

    private static final long serialVersionUID = -5318589393275157185L;
    private JButton brighten;
    private JButton dim;
    private JButton turnOff;
    private JButton turnOn;

// Lighting colour change
    private JButton blue;
    private JButton green;
    private JButton orange;
    private JButton purple;

    private final LightingClient parent;

    public LightingUI(LightingClient lightingClient) {
        super(lightingClient);
        parent = lightingClient;
        init();
    }

    @Override
    public void init() {
        super.init();
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);

        brighten = new JButton("Brighten");
        brighten.setEnabled(false);
        brighten.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{brighten});

        dim = new JButton("Dim");
        dim.setEnabled(false);
        dim.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{dim});

        turnOff = new JButton("Turn off Lighting");
        turnOff.setEnabled(false);
        turnOff.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{turnOff});

        turnOn = new JButton("Turn on Lighting");
        turnOn.setEnabled(true);
        turnOn.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{turnOn});

//colours 
        blue = new JButton("Switch to Blue");
        blue.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{blue});

        green = new JButton("Switch to Green");
        green.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{green});

        orange = new JButton("Switch to Orange");
        orange.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{orange});

        purple = new JButton("Switch to Purple");
        purple.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{purple});

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == brighten) {
            parent.brightenLighting();
        } else if (e.getSource() == dim) {
            parent.dimLighting();
        } else if (e.getSource() == turnOff) {
            parent.turnOff();
            brighten.setEnabled(false);
            dim.setEnabled(false);
            turnOff.setEnabled(false);
            turnOn.setEnabled(true);
        } else if (e.getSource() == turnOn) {
            parent.turnOn();
            brighten.setEnabled(true);
            dim.setEnabled(true);
            turnOff.setEnabled(true);
            turnOn.setEnabled(false);

        } else if (e.getSource() == blue) {
            parent.blueLight();
            // green.setEnabled(false);
            //  orange.setEnabled(false);
            //  purple.setEnabled(false);
            //  pink.setEnabled(false);

        } else if (e.getSource() == green) {
            parent.greenLight();
            //   blue.setEnabled(false);
            //   orange.setEnabled(false);
            //   purple.setEnabled(false);
            //   pink.setEnabled(false);

        } else if (e.getSource() == orange) {
            parent.orangeLight();
            //    green.setEnabled(false);
            //   blue.setEnabled(false);
            //  purple.setEnabled(false);
            //  pink.setEnabled(false);

        } else if (e.getSource() == purple) {
            parent.purpleLight();
            //  green.setEnabled(false);
            //   orange.setEnabled(false);
            //   blue.setEnabled(false);
            //   pink.setEnabled(false);

        }
    }
}
