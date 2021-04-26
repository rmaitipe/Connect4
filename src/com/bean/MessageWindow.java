package com.bean;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class MessageWindow extends JWindow{
    private String message; 
    public MessageWindow(JFrame parent, String message) { 
       super(parent);               
       this.message = message; 
       setSize(300, 300);       
       setLocationRelativeTo(parent);         
    }
    public void paint(Graphics g) { 
       super.paint(g);
       g.drawRect(0,0,getSize().width - 1,getSize().height - 1); 
       g.drawString(message,50,150); 
    } 
 }