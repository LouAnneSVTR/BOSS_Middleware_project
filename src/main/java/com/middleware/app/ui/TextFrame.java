package com.middleware.app.ui;

import com.middleware.app.game.players.Player;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/*
public class TextFrame extends JFrame implements ComponentListener {

    private final Player player;
    public static GamePanel textArea;



    public TextFrame(Player player) {
        this.player = player;

        SwingUtilities.invokeLater(() -> {
            setTitle("KeyEvent Example");
            setSize(800, 800);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            Dimension totalSize = this.getSize();
            Dimension contentSize = this.getContentPane().getSize();

            textArea = new GamePanel(player, totalSize.width, totalSize.height);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            add(scrollPane);
            textArea.setBackground(Color.BLACK);

            textArea.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    try {
                        textArea.handleKeyPress(e); // Appel de la méthode handleKeyPress lorsqu'une touche est enfoncée
                    } catch (BadLocationException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            textArea.setFocusable(true);

            add(textArea);
            this.addComponentListener(this);

            this.getContentPane().setSize(this.getWidth(), this.getHeight());

            this.pack();

            textArea.setDimension(this.getWidth(), this.getHeight(), this.getContentPane().getWidth(), this.getContentPane().getHeight());

        });
        setVisible(true);


    }

    @Override
    public void componentResized(ComponentEvent e) {
        textArea.setDimension(this.getWidth(), this.getHeight(), this.getContentPane().getWidth(), this.getContentPane().getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    public static void colorText(String text, Color color) throws BadLocationException {
        textArea.addText(text, color);
    }

    public static void lineBreak() throws BadLocationException {
        textArea.addText("\n", Color.WHITE);
    }

    public static void updateLifeBoss(int newLifeBoss) {
        textArea.updateBossLife(newLifeBoss);
    }

    public static void updateLifePlayer(int newLifePlayer) {
        textArea.updatePlayerLife(newLifePlayer);
    }

    private void addColoredText() {
        StyledDocument doc = textArea.getStyledDocument();

        Style style = textArea.addStyle("RegularStyle", null);
        StyleConstants.setForeground(style, Color.BLACK);
        StyleConstants.setFontFamily(style, "Arial");
        StyleConstants.setFontSize(style, 12);

        Style boldStyle = textArea.addStyle("BoldStyle", style);
        StyleConstants.setBold(boldStyle, true);

        Style redStyle = textArea.addStyle("RedStyle", boldStyle);
        StyleConstants.setForeground(redStyle, Color.RED);

        Style greenStyle = textArea.addStyle("GreenStyle", boldStyle);
        StyleConstants.setForeground(greenStyle, Color.GREEN);

        try {
            doc.insertString(doc.getLength(), "Texte normal. ", style);
            doc.insertString(doc.getLength(), "Texte en gras. ", boldStyle);
            doc.insertString(doc.getLength(), "Texte en rouge. ", redStyle);
            doc.insertString(doc.getLength(), "Texte en vert. ", greenStyle);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
*/