import java.util.ArrayList;
import java.util.HashMap;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import java.awt.event.*;

public class Piano extends JComponent
{
    public static final int TOP_HEIGHT = 300;
    public static final int KEY_WIDTH = 25;
    public static final int KEY_HEIGHT = 200;
    public static final int BLACK_KEY_WIDTH = 15;
    public static final int BLACK_KEY_HEIGHT = 100;
    public static final int BLOCK_HEIGHT = 5;
    public static final int ROUNDING = 20;
    public static final int MAX_VOLUME = 127;
    public static final String keyboard = "asdfghjkl;\"";
    public static final String fullKeyboard = "awsedftgyhujkolp;\"]E";
    public static Thought thoughtToDraw;
    private static int[] graphicsForNote;
    public static int octave;
    public static int KEYS_TO_DRAW = 33;
    private static final Color[] colors = new Color[] {Color.YELLOW,Color.GREEN,Color.BLUE,Color.ORANGE, Color.MAGENTA};
    public Piano()
    {
        this.setPreferredSize(new Dimension(KEY_WIDTH * KEYS_TO_DRAW,KEY_HEIGHT + TOP_HEIGHT));
        graphicsForNote = new int[150];
    }
    /**
     * Returns the center X location of a given note on the piano
     */
    private static int xFromNote(int note)
    {
        note = note - 12 * octave;
        if(graphicsForNote[note] != 0)
            return graphicsForNote[note];
            
        if(note < 57)
            return -100;
            
        double scaler = .5;
        for(int i = 57; i <= note; i++)
        {
            graphicsForNote[i] = (int)(scaler * KEY_WIDTH);
            if(i == note)
                return (int)(scaler * KEY_WIDTH);
            if( (i - 57) % 12 == 2 || (i - 57) % 12 == 7)
            {
                scaler += 1;
            }
            else
            {
                scaler += .5;
            }
        }
        return (int)(scaler * KEY_WIDTH);
    }
    public void paintComponent(Graphics gf)
    {
        Graphics2D g = (Graphics2D) gf;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,KEY_WIDTH * KEYS_TO_DRAW, TOP_HEIGHT);
        
        if(thoughtToDraw != null)
        {
            for(AudioPlayer p : thoughtToDraw.audioPlayers)
            {
                Note[] arr = p.fragment.getNextNotes();
                int counter = 0;
                for(Note n : arr)
                {
                    if(n != null)
                    {
                        counter += n.remainingLength();
                        if(!n.isRest())
                        {
                            g.setColor(colors[n.color - 1]);
                            if(noteIsBlack(n.note))
                            {
                                g.fillRoundRect(xFromNote(n.note) - BLACK_KEY_WIDTH/2,(int)(TOP_HEIGHT - (counter) * BLOCK_HEIGHT),BLACK_KEY_WIDTH,n.remainingLength() * BLOCK_HEIGHT,ROUNDING,ROUNDING);
                            }
                            else
                            {
                                g.fillRoundRect(xFromNote(n.note) - KEY_WIDTH/2,(int)(TOP_HEIGHT - (counter) * BLOCK_HEIGHT),KEY_WIDTH,n.remainingLength() * BLOCK_HEIGHT,ROUNDING,ROUNDING);
                            }
                        }
                    }
                }
            }
            
        }
        
        g.setColor(Color.WHITE);
        g.fillRect(0,TOP_HEIGHT,KEYS_TO_DRAW * KEY_WIDTH, KEY_HEIGHT);
        g.setColor(Color.BLACK);
        for(int i = 0; i < KEYS_TO_DRAW; i++)
        {
            g.drawRect(i * KEY_WIDTH,TOP_HEIGHT,KEY_WIDTH,KEY_HEIGHT);
        }
        
        
        for(int i = 1; i < 100; i++)
        {
            if(Screen.notes[i] != 0)
            {
                g.setColor(colors[Screen.notes[i]-1]);
                if(!noteIsBlack(i))
                {
                    g.fillRect(xFromNote(i) - KEY_WIDTH / 2+1,TOP_HEIGHT,KEY_WIDTH-1,KEY_HEIGHT);
                }
            }
        }
        
        g.setColor(Color.BLACK);
        
        for(int j = -7; j < KEYS_TO_DRAW; j += 7)
        {
            
            for(int i = j - 1; i < j + 2; i++)
            {
                g.fillRect(KEY_WIDTH * i - BLACK_KEY_WIDTH / 2,TOP_HEIGHT, BLACK_KEY_WIDTH,BLACK_KEY_HEIGHT);
            }
            for(int i = j + 3; i < j + 5; i++)
            {
                g.fillRect(KEY_WIDTH * i - BLACK_KEY_WIDTH / 2,TOP_HEIGHT, BLACK_KEY_WIDTH,BLACK_KEY_HEIGHT);
            }
        }
        
        
        for(int i = 1; i < 100; i++)
        {
            if(Screen.notes[i] != 0)
            {
                g.setColor(colors[Screen.notes[i]-1]);
                if(noteIsBlack(i))
                {
                    g.fillRect(xFromNote(i) - BLACK_KEY_WIDTH / 2+1,TOP_HEIGHT,BLACK_KEY_WIDTH,BLACK_KEY_HEIGHT);
                }
            }
        }
        
        if(Screen.scaleMode)
        {
            for(int i = 0; i < 11; i++)
            {
                int xLoc = xFromNote(Piano.noteInCurrentScale(i));
                if(Piano.noteInCurrentScaleIsBlack(i))
                {
                    g.setColor(Color.WHITE);
                    g.drawString(keyboard.substring(i,i+1),xLoc,TOP_HEIGHT+BLACK_KEY_HEIGHT - 10);
                }
                else
                {
                    g.setColor(Color.BLACK);
                    g.drawString(keyboard.substring(i,i+1),xLoc,TOP_HEIGHT+KEY_HEIGHT - 10);
                }
            }
        }
        else
        {
            for(int i = 0; i < fullKeyboard.length(); i++)
            {
                int xLoc = xFromNote(60 + i + 12 * octave);
                if(Piano.noteIsBlack(i+60))
                {
                    if(Screen.notes[i+60] != 0)
                        g.setColor(Color.BLACK);
                    else
                        g.setColor(Color.WHITE);
                    g.drawString(fullKeyboard.substring(i,i+1),xLoc,TOP_HEIGHT+BLACK_KEY_HEIGHT - 10);
                }
                else
                {
                    g.setColor(Color.BLACK);
                    g.drawString(fullKeyboard.substring(i,i+1),xLoc,TOP_HEIGHT+KEY_HEIGHT - 10);
                }
            }
        }
        
        
    }
    public static boolean noteIsBlack(int note)
    {
        note = (note - 9) % 12;
        if(note == 1 || note == 4 || note == 6 || note == 9 || note == 11)
        {
            return true;
        }
        return false;
    }
    public static boolean noteInCurrentScaleIsBlack(int i)
    {
        int note = noteInCurrentScale(i);
        return noteIsBlack(note);
    }
    /**
     * Returns the Synthesizer note for a value in the current scale
     */
    public static int noteInCurrentScale(int i)
    {
        if(Screen.shiftIsDown)
        {
            if(i == 0)
                return Screen.scaleNote;
            else if(i == 1)
                return Screen.scaleNote + 2;
            else if(i == 2)
                return Screen.scaleNote + 3;
            else if(i == 3)
                return Screen.scaleNote + 5;
            else if(i == 4)
                return Screen.scaleNote + 7;
            else if(i == 5)
                return Screen.scaleNote + 9;
            else if(i == 6)
                return Screen.scaleNote + 10;
            else if(i == 7)
                return Screen.scaleNote + 12;
            else if(i == 8)
                return Screen.scaleNote + 14;
            else if(i == 9)
                return Screen.scaleNote + 15;
            else if(i == 10)
                return Screen.scaleNote + 17;
        }
        else
        {
            if(i == 0)
                return Screen.scaleNote;
            else if(i == 1)
                return Screen.scaleNote + 2;
            else if(i == 2)
                return Screen.scaleNote + 4;
            else if(i == 3)
                return Screen.scaleNote + 5;
            else if(i == 4)
                return Screen.scaleNote + 7;
            else if(i == 5)
                return Screen.scaleNote + 9;
            else if(i == 6)
                return Screen.scaleNote + 11;
            else if(i == 7)
                return Screen.scaleNote + 12;
            else if(i == 8)
                return Screen.scaleNote + 14;
            else if(i == 9)
                return Screen.scaleNote + 16;
            else if(i == 10)
                return Screen.scaleNote + 17;
        }
        return -1;
    }
    public static int mapKeyToChromaticNote(int kc)
    {
        int output = -1;
        if(kc == 65)
            output =  60;
        else if(kc == 87)
            output =  61;
        else if(kc == 83)
            output =  62;
        else if(kc == 69)
            output =  63;
        else if(kc == 68)
            output =  64;
        else if(kc == 70)
            output =  65;
        else if(kc == 84)
            output =  66;
        else if(kc == 71)
            output =  67;
         else if(kc == 89)
            output =  68;
        else if(kc == 72)
            output =  69;
        else if(kc == 85)
            output =  70;
        else if(kc == 74)
            output =  71;
        else if(kc == 75)
            output =  72;
         else if(kc == 79)
            output =  73;
        else if(kc == 76)
            output =  74;
        else if(kc == 80)
            output =  75;
         else if(kc == 59)
            output =  76;
        else if(kc == 222)
            output =  77;
        else if(kc == 93)
            output =  78;
        else if(kc == 10)
            output = 79;
        if(output == -1)
            return -1;
        else
            return output + 12 * octave;
    }
    /**
     * Turns a KeyEvent integer into a Synthesizer note value
     */
    public static int mapKeyToNote(int kc)
    {
        if(kc == 65)
            return noteInCurrentScale(0);
        else if(kc == 83)
            return noteInCurrentScale(1);
        else if(kc == 68)
            return noteInCurrentScale(2);
        else if(kc == 70)
            return noteInCurrentScale(3);
        else if(kc == 71)
            return noteInCurrentScale(4);
        else if(kc == 72)
            return noteInCurrentScale(5);
        else if(kc == 74)
            return noteInCurrentScale(6);
        else if(kc == 75)
            return noteInCurrentScale(7);
        else if(kc == 76)
            return noteInCurrentScale(8);
        else if(kc == 59)
            return noteInCurrentScale(9);
        else if(kc == 222)
            return noteInCurrentScale(10);
        return -1;
    }
}
