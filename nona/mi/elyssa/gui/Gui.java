package nona.mi.elyssa.gui;

import nona.mi.elyssa.robot.Bot;

import javax.swing.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class Gui{

    private JFrame jframe;
    private JPanel mainPanel;

    private JPanel pathPanel;
    private JLabel pathLabel;
    private JTextField pathField;

    private JPanel timePanel;
    private JLabel timeLabel;
    private JTextField timeField;

    private JPanel keyPanel;
    private JRadioButton breedRadio;
    private JRadioButton sweetRadio;
    private ButtonGroup buttonGroup;

    private JPanel tipPanel;
    private JLabel tipLabel;

    private JPanel buttonPanel;
    private JButton check;

    private String title;
    private String breedingTipText;
    private String sweetTipText;


    public Gui(){

        title = "Elyssa 2.1 ";

        breedingTipText = "Please set L as DOWN key.";
        sweetTipText = "Please set Z as ACTION key.";

        jframe = new JFrame(title);
        jframe.setSize(550, 250);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.PINK);

        pathPanel = new JPanel();
        pathPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pathPanel.setBackground(Color.PINK);

        pathLabel = new JLabel("Rom path: ");
        pathLabel.setForeground(Color.BLACK);
        pathField = new JTextField(36);

        pathPanel.add(pathLabel);
        pathPanel.add(pathField);
        mainPanel.add(pathPanel);

        timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        timePanel.setBackground(Color.PINK);

        timeLabel = new JLabel("Time (millis): ");
        timeLabel.setForeground(Color.BLACK);
        timeField = new JTextField(36);

        timePanel.add(timeLabel);
        timePanel.add(timeField);
        mainPanel.add(timePanel);

        keyPanel = new JPanel();
        keyPanel.setBackground(Color.PINK);

        breedRadio = new JRadioButton("Breeding", true);
        breedRadio.setBackground(Color.PINK);
        breedRadio.setForeground(Color.BLACK);
        breedRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipLabel.setText(breedingTipText);
            }
        });

        sweetRadio = new JRadioButton("Sweet Scent / Legend / IV", false);
        sweetRadio.setBackground(Color.PINK);
        sweetRadio.setForeground(Color.BLACK);
        sweetRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipLabel.setText(sweetTipText);
            }
        });

        buttonGroup = new ButtonGroup();
        buttonGroup.add(breedRadio);
        buttonGroup.add(sweetRadio);

        keyPanel.add(breedRadio);
        keyPanel.add(sweetRadio);
        mainPanel.add(keyPanel);

        tipPanel = new JPanel();
        tipPanel.setBackground(Color.PINK);

        tipLabel = new JLabel(breedingTipText);
        tipLabel.setForeground(Color.BLACK);

        tipPanel.add(tipLabel);
        mainPanel.add(tipPanel);

        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.PINK);

        check = new JButton("START");
        check.setBackground(Color.YELLOW);
        check.setForeground(Color.BLACK);
        check.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                jframe.setTitle(title + "- RUNNING...");
                manageInputs();
                jframe.setTitle(title + "- DONE!");
            }
        });
        check.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    jframe.setTitle(title + "- RUNNING...");
                    manageInputs();
                    jframe.setTitle(title + "- DONE!");
                }
            }
            @Override
            public void keyTyped(KeyEvent e){

            }
            @Override
            public void keyReleased(KeyEvent e){

            }
        });

        buttonPanel.add(check);
        mainPanel.add(buttonPanel);

        jframe.add(mainPanel);
        jframe.setVisible(true);
    }

    private void manageInputs(){

        String pathText = pathField.getText();
        String timeText = timeField.getText();

        String key = "";
        if (breedRadio.isSelected()) {
            key = Bot.L_KEY;
        } else {
            key = Bot.Z_KEY;
        }


        File file = new File(pathText);

        if (file.exists()) {

            String extension = "";
            String fileName = file.getName();
            extension = fileName.substring(fileName.indexOf(".")+1);

            if (extension.equalsIgnoreCase("gba")) {

                try{
                    Integer targetTime = Integer.parseInt(timeText);

                    if (targetTime > 0) {
                        new Bot(file, targetTime, key);
                    }

                } catch(Exception ex){
                    say("Invalid time.");
                }

            } else {
                say("The file does not seem to be .gba.");
            }

        } else {
            say("The file does not exists.");
        }
    }

    private void say(String message){
        JOptionPane.showMessageDialog(null, message);
    }

}