package clientui;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import client.AudioClient;
import java.awt.Dimension;

/* @author:David Donovan x14111021
 *
 * @Author skeleton code by Dominic Carr	
 */
public class AudioUI extends ClientUI {

    private static final long serialVersionUID = -5318589393275157185L;
    private JButton on;
    private JButton off;
    private JButton playMusic;
    private JButton checkWeather;
    private JButton increaseVolume;
    private JButton decreaseVolume;
    private JButton connectToTV;
    private JButton disconnectFromTV;
    private final AudioClient parent;

    public AudioUI(AudioClient audioClient) {
        super(audioClient);
        parent = audioClient;
        init();
    }

    //create client buttons
    @Override
    public void init() {
        super.init();
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);

        //on button        
        on = new JButton("On");
        on.setEnabled(true);
        on.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{on});

        //increase volume button
        increaseVolume = new JButton("Volume +");
        increaseVolume.setEnabled(false);
        increaseVolume.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{increaseVolume});

        //transfer audio button
        playMusic = new JButton("Play Music");
        playMusic.setEnabled(false);
        playMusic.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{playMusic});

        //connect to TV button
        connectToTV = new JButton("Connect to Television");
        connectToTV.setEnabled(false);
        connectToTV.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{connectToTV});

        //off button
        off = new JButton("Off");
        off.setEnabled(false);
        off.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{off});

        //decrease volume button
        decreaseVolume = new JButton("Volume -");
        decreaseVolume.setEnabled(false);
        decreaseVolume.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{decreaseVolume});

        //check weather button
        checkWeather = new JButton("Check Weather");
        checkWeather.setEnabled(false);
        checkWeather.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{checkWeather});

        //disconnect from TV button
        disconnectFromTV = new JButton("Disconnect from TV");
        disconnectFromTV.setEnabled(false);
        disconnectFromTV.setPreferredSize(new Dimension(140, 25));
        add(new JButton[]{disconnectFromTV});
    }

    //show/hide buttons
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == on) {
            parent.powerOn();
            connectToTV.setEnabled(true);
            disconnectFromTV.setEnabled(false);
            playMusic.setEnabled(true);
            checkWeather.setEnabled(true);
            increaseVolume.setEnabled(true);
            decreaseVolume.setEnabled(true);
            off.setEnabled(true);
            on.setEnabled(false);// all buttons disabled except On button

        } else if (e.getSource() == off) {
            parent.powerOff();
            connectToTV.setEnabled(false);
            disconnectFromTV.setEnabled(false);
            playMusic.setEnabled(false);
            checkWeather.setEnabled(false);
            increaseVolume.setEnabled(false);
            decreaseVolume.setEnabled(false);
            on.setEnabled(true);
            off.setEnabled(false);

        } else if (e.getSource() == playMusic) {
            parent.playMusic();

        } else if (e.getSource() == checkWeather) {
            parent.checkWeather();

        } else if (e.getSource() == increaseVolume) {
            parent.increaseVolume();

        } else if (e.getSource() == decreaseVolume) {
            parent.decreaseVolume();

        } else if (e.getSource() == connectToTV) {
            parent.connectToTV();
            connectToTV.setEnabled(false);
            disconnectFromTV.setEnabled(true);

        } else if (e.getSource() == disconnectFromTV) {
            parent.disconnectFromTV();
            connectToTV.setEnabled(true);
            disconnectFromTV.setEnabled(false);
        }

    }
}
