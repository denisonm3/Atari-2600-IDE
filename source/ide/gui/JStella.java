package ide.gui;

import ide.Recursos;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import jstella.core.JSException;
import jstella.runner.Intercessor;

/**
 *
 * @author Denison
 */
public class JStella extends javax.swing.JInternalFrame implements Intercessor.IfcIntercessorClient {

    private final HashMap<String, String> config;
    private final Intercessor intecessor;

    public JStella() {
        config = getDefaultConfig();
        intecessor = new Intercessor(this);
        intecessor.playROM(new ByteArrayInputStream(Recursos.ROM));
    }

    @Override
    public void displayCanvas(JPanel aCanvas) {
        this.getContentPane().add(aCanvas, java.awt.BorderLayout.CENTER);
        aCanvas.revalidate();
        aCanvas.requestFocusInWindow();
    }

    @Override
    public boolean respondToException(JSException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    @Override
    public void informUserOfPause(boolean aIsPaused) {

    }

    @Override
    public void updateSwitches() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, String> getConfiguration() {
        return config;
    }

    public Intercessor getIntercessor() {
        return intecessor;
    }

    private HashMap<String, String> getDefaultConfig() {
        HashMap<String, String> defaultConfig = new HashMap<>();
        defaultConfig.put("jstella.control.joystick.a.up", "jstella.control.joystick.a.up(" + KeyEvent.VK_UP + ")");
        defaultConfig.put("jstella.control.joystick.a.down", "jstella.control.joystick.a.down(" + KeyEvent.VK_DOWN + ")");
        defaultConfig.put("jstella.control.joystick.a.left", "jstella.control.joystick.a.left(" + KeyEvent.VK_LEFT + ")");
        defaultConfig.put("jstella.control.joystick.a.right", "jstella.control.joystick.a.right(" + KeyEvent.VK_RIGHT + ")");
        defaultConfig.put("jstella.control.joystick.a.button", "jstella.control.joystick.a.button(" + KeyEvent.VK_SPACE + ")");
        defaultConfig.put("jstella.control.paddle.a.cw", "jstella.control.paddle.a.cw(" + KeyEvent.VK_COMMA + ")");
        defaultConfig.put("jstella.control.paddle.a.ccw", "jstella.control.paddle.a.ccw(" + KeyEvent.VK_PERIOD + ")");
        defaultConfig.put("jstella.control.paddle.a.button", "jstella.control.paddle.a.button(" + KeyEvent.VK_Z + ")");
        defaultConfig.put("jstella.control.boostergrip.a.booster", "jstella.control.boostergrip.a.booster(" + KeyEvent.VK_G + ")");
        defaultConfig.put("jstella.control.boostergrip.a.trigger", "jstella.control.boostergrip.a.trigger(" + KeyEvent.VK_F + ")");
        defaultConfig.put("jstella.defaultscreen", "jstella.defaultscreen.snow");
        return defaultConfig;
    }

    public void closeRom() {
        intecessor.playROM(new ByteArrayInputStream(Recursos.ROM));
    }
}
