/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VehicleRental.gui;

/**
 *
 * @author FLS
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashFrame extends JFrame {
    private JProgressBar progressBar;
    private Timer timer;
    private int counter = 0;

    public SplashFrame() {
        setTitle("Vehicle Rental System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        initComponents();
        startProgress();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(70, 130, 180));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Title Label
        JLabel titleLabel = new JLabel("VEHICLE RENTAL SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));

        // Progress Bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(34, 139, 34));
        progressBar.setBackground(Color.WHITE);

        mainPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(progressBar, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void startProgress() {
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                progressBar.setValue(counter);
                
                if (counter >= 100) {
                    timer.stop();
                    dispose();
                    new LogInFrame().setVisible(true);
                }
            }
        });
        timer.start();
    }
}
