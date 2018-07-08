package cardgame.graphic;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class ChooseFrame extends JFrame {
    
    private class ChooseMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (choosen)
                index = Integer.getInteger(((JRadioButton) group.getSelection()).getName());
            relase();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
        
    }
    
    List<JRadioButton> buttons;
    ButtonGroup group;
    JButton choose;
    JLabel label;
    boolean choosen;
    Integer index;
    boolean pressed;
    
    public ChooseFrame(List<String> l) {
        if (!l.isEmpty())
            initComponents(l);
        else 
            initComponents();
    }
    
    private void initComponents(List<String> l) {
        this.setLayout(new GridLayout(l.size() + 2, 0));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        pressed = false;
        index = 0;
        choosen = false;
        label = new JLabel("Choose one target:");
        group = new ButtonGroup(); 
        buttons = new ArrayList<JRadioButton>();
        for (String s : l) {
            JRadioButton aux = new JRadioButton(s);
            aux.setName(index.toString());
            buttons.add(aux);
            group.add(aux);
            index++;
        }
        index = 0;
        choose = new JButton("Choose");
        choose.addMouseListener(new ChooseMouseListener());
        
        buttons.get(0).setSelected(true);
        
        this.add(label);
        for (JRadioButton j : buttons) 
            this.add(j);
        this.add(choose);
        this.pack();
        this.setResizable(false);
        this.setLocation(480, 200);
        this.setVisible(true);
    }
    
    private void initComponents() {
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        pressed = false;
        index = -1;
        choosen = false;
        label = new JLabel("No target to choose");
        choose = new JButton("Ok");
        choose.addMouseListener(new ChooseMouseListener());
        
        this.add(Box.createVerticalStrut(5));
        this.add(label);
        this.add(Box.createVerticalStrut(5));
        this.add(choose);
        this.pack();
        this.setResizable(false);
        this.setLocation(480, 200);
        this.setVisible(true);
    }
    
    synchronized private void relase() {
        pressed = true;
        notifyAll();
    }
    
    /**
     * Called to choose the target of an effect.
     * @return the index of the target or -1 if there is no target to choose.
     * @throws InterruptedException 
     */
    synchronized public int selected() throws InterruptedException {
        while (!pressed)
            wait();
        this.setVisible(false);
        return index;
    }
    
}
