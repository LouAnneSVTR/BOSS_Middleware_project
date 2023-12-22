package com.middleware.app.others;

import com.middleware.app.game.players.Player;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TextFrame extends JFrame {

    private final Player player;
    public static GamePanel textArea;



    public TextFrame(Player player) {
        this.player = player;

        setTitle("KeyEvent Example");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new GamePanel(player);
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

        setVisible(true);
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
