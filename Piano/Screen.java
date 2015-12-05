import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;

import javax.swing.JFrame;
import java.awt.event.*;

public class Screen
{
    public static MidiChannel piano;
    public static int[] notes;
    public static boolean shiftIsDown;
    public static int scaleNote;
    public static boolean scaleMode;
    public static int metronome = 120;
    public static final JFrame frame = new JFrame();
    public static void main( String[] args ) 
    {
        scaleMode = false;
        try 
        {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            piano = channels[0];
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            return;
        }
        
        notes = new int[100];
        scaleNote = 60;
        
        frame.getContentPane().add(new Piano());
        frame.pack();
        
        frame.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                System.out.println(e.getKeyCode());
                if(scaleMode)
                {
                    if(e.getKeyCode() == 16)
                        shiftIsDown = true;
                    if(e.getKeyCode() == 90)
                        scaleNote = 57;
                    if(e.getKeyCode() == 88)
                        scaleNote = 59;
                    if(e.getKeyCode() == 67)
                        scaleNote = 60;
                    if(e.getKeyCode() == 86)
                        scaleNote = 62;
                    if(e.getKeyCode() == 66)
                        scaleNote = 64;
                    if(e.getKeyCode() == 78)
                        scaleNote = 65;
                    if(e.getKeyCode() == 77)
                        scaleNote = 67;
                    int note = Piano.mapKeyToNote(e.getKeyCode());
                    if(note != -1)
                        playNote(note, 80);
                }
                else
                {
                    if(e.getKeyCode() == 39)
                        Piano.octave ++;
                    if(e.getKeyCode() == 37)
                        Piano.octave--;
                    int note = Piano.mapKeyToChromaticNote(e.getKeyCode());
                    if(note != -1 && (notes[note] == 0))
                        playNote(note, 80);
                }
                frame.repaint();
            }
            public void keyReleased(KeyEvent e)
            {
                
                if(scaleMode)
                {
                    if(e.getKeyCode() == 16)
                        shiftIsDown = false;
                    int note = Piano.mapKeyToNote(e.getKeyCode());
                    if(note != -1)
                        endNote(note);
                }
                else
                {
                    int note = Piano.mapKeyToChromaticNote(e.getKeyCode());
                    if(note != -1)
                        endNote(note);
                }
                frame.repaint();
            }
        });
        frame.show();
        
        //On-Off error testing
        Thought test = new Thought();
        test.bpm = 60;
        Fragment up = new Fragment("626160595859606162616059585960");
        Fragment down = new Fragment("60606060606060606060606060606060");
        test.add(down);
        test.add(up);
        //playThought(test);
        
        
        
        
        
        
        
        //Inside Out Song / Fragment + Thought method testing
        Fragment bgr1 = new Fragment("5059505950595059505950595059505950595059505950595059505950595059");
        Fragment bgr2 = new Fragment("4857485748574857485748574857485748574857485748574857485748574857");
        Fragment mel1 = new Fragment("7111117111116967697111671111696766711167111169676671116772716967");
        Fragment mel2 = new Fragment("6511116911116765676911651111676564691165111167656469116572716965");
        Fragment rest = new Fragment("00000000000000000000000000000000");
        Fragment intr = new Fragment("62666771111100000000000000000000");
        
         Fragment background = bgr1.append(bgr1.append(bgr2).loop(2));
         background.partialVolume = .6;
         Fragment melody = rest.loop(2).append(mel1.append(mel2).loop(2));
         melody.partialVolume = .8;
         Fragment interludes = rest.loop(6).append(intr.loop(2).append(intr.shift(-2).loop(2)));
         interludes.partialVolume = .8;
        
         background.color = 5;
         melody.color = 1;
         interludes.color = 3;
         
        Thought iso = new Thought();
        iso.add(background);
        iso.add(melody);
        iso.add(interludes);
        iso.bpm = 300;
        //playThought(iso);
        
        Piano.octave = -2;
        
        Fragment bass1 = new Fragment("00581111621111651111621111631111671111701111671111").shift(-24);
        Fragment bass2 = new Fragment("581111621111651111671111681111671111651111621111").shift(-24);
        Fragment bass3 = bass2.shift(5);
        Fragment bass4 = new Fragment("601111621111631111641111651111671111681111691111701111681111671111661111651111631111621111601111581111").shift(-24);
        
        Fragment tm1 = new Fragment("74000070000067700000000073000070000067700000");
        Fragment tm2 = new Fragment("000074000070000067700072700067700072701170000000");
        Fragment tm3 = new Fragment("000073000070000067700000000073000070000067700000");
        Fragment tm4 = new Fragment("000072000067000072710067630060700067681169000000");
        metronome = 600;
        
        Fragment chordRoots1 = new Fragment("00581111111158000000000000631111111163000000000000581111111158000000000000581111111158000000000000").shift(-12);
        Fragment chordRoots2 = new Fragment("631111111163000000000000631111111163000000000000581111111158000000000000551111111155000000000000").shift(-12);
        Fragment chordRoots3 = new Fragment("601111111160000000000000").shift(-12);
        Fragment chordRoots4 = new Fragment("6511111111650000000000005811111111580000000000005811111111580000005811111111111111111111").shift(-12);
        
        Fragment allBass = bass1.append(bass2).append(bass3).append(bass2).append(bass4);
        Fragment allMel = tm1.append(tm2).append(tm3).append(tm2).append(tm4).append(tm2);
        allBass.color = 3;
        allMel.color = 1;
        
        Thought chords = new Thought();
        chords.append(chordRoots1.dominantSeventh());
        chords.append(chordRoots2.dominantSeventh());
        chords.append(chordRoots3.minorSeventh());
        chords.append(chordRoots4.dominantSeventh());
        chords.setColor(2);
        
        Thought t = new Thought();
        t.add(allBass);
        t.add(allMel);
        t.add(chords);
        //t.add(chordRoots1.dominantSeventh().append(chordRoots2.dominantSeventh()));
        t.bpm = 600;
        playThought(t);
        
        //for(int i = 0; i < chords1.length; i++)
        //{
        //    playFragment(chords1[i].append(chords2[i]).append(chords3[i]).append(chords4[i]));
        //}
        
        //playFragment();
        //Fragment m = tm1.append(tm2).append(tm3).append(tm2).append(tm4).append(tm2);
        //playFragment(m);
        
        
        //Thought melody = new Thought();
        //melody.append(mel1);
        //melody.append(mel2);
        //melody.append(mel1);
        //melody.append(mel2);
        //melody.bpm = 220;
        
        //Thought background = new Thought();
        //background.append(bgr1);
        //background.append(bgr2);
        //background.append(bgr1);
        //background.append(bgr2);
        //background.bpm = 220;
        
        //Thought interludes = new Thought();
        //interludes.append(ntn1.loop(2));
        //interludes.append(intr.loop(2));
        //interludes.append(intr.shift(-2).loop(2));
        //interludes.bpm = 220;
        
        //playThought(background);
        //playThought(melody);
        //playThought(interludes);
    }
    private static void playThought(Thought t)
    {
        final Thought thought = t;
        Thread t1 = new Thread()
        {
            public void run()
            {
                while(thought.hasMore())
                {
                    thought.tick();
                    System.out.println();
                    pause((int)(60.0 / thought.getBPM() * 1000.0));
                }
            }
        };
        t1.start();
    }
//     private static void playFragment(Fragment f)
//     {
//         final Fragment frag = f;
//         Thread t1 = new Thread()
//         {
//             public void run()
//             {
//                 if(!frag.hasNextNote())
//                     return;
//                 int prevNote = 01;
//                 while(frag.hasNextNote())
//                 {
//                     int toPlay = frag.nextNote();
//                     if(toPlay != Fragment.REST && toPlay != Fragment.HOLD)
//                     {
//                         Screen.playNote(toPlay);
//                         prevNote = toPlay;
//                         try{sleep((int)(60.0 / metronome * 1000.0));}catch(Exception e){}
//                         if(!(frag.hasNextNote() && frag.peek() == Fragment.HOLD))
//                             Screen.endNote(toPlay);
//                     }
//                     else
//                     {
//                         if(toPlay == Fragment.REST)
//                         {
//                             Screen.endNote(prevNote);
//                             try{sleep((int)(60.0 / metronome * 1000.0));}catch(Exception e){}
//                         }
//                         else
//                         {
//                             try{sleep((int)(60.0 / metronome * 1000.0));}catch(Exception e){}
//                             if(!frag.hasNextNote())
//                                 Screen.endNote(prevNote);
//                             else if(frag.peek() != Fragment.HOLD)
//                             {
//                                 Screen.endNote(prevNote);
//                             }
//                         }
//                     }
//                 }
//             }
//         };
//         t1.start();
//     }
//     private static void playThought(Thought f)
//     {
//         final Thought frag = f;
//         Thread t1 = new Thread()
//         {
//             public void run()
//             {
//                 if(!frag.hasNextNote())
//                     return;
//                 int prevNote = 01;
//                 while(frag.hasNextNote())
//                 {
//                     int toPlay = frag.nextNote();
//                     if(toPlay != Fragment.REST && toPlay != Fragment.HOLD)
//                     {
//                         Screen.playNote(toPlay);
//                         prevNote = toPlay;
//                         try{sleep((int)(60.0 / frag.getBPM() * 1000.0));}catch(Exception e){}
//                         if(!(frag.hasNextNote() && frag.peek() == Fragment.HOLD))
//                             Screen.endNote(toPlay);
//                     }
//                     else
//                     {
//                         if(toPlay == Fragment.REST)
//                         {
//                             Screen.endNote(prevNote);
//                             try{sleep((int)(60.0 / frag.getBPM() * 1000.0));}catch(Exception e){}
//                         }
//                         else
//                         {
//                             try{sleep((int)(60.0 / frag.getBPM() * 1000.0));}catch(Exception e){}
//                             if(!frag.hasNextNote())
//                                 Screen.endNote(prevNote);
//                             else if(frag.peek() != Fragment.HOLD)
//                             {
//                                 Screen.endNote(prevNote);
//                             }
//                         }
//                     }
//                 }
//             }
//         };
//         t1.start();
//     }
    private static void pause(long time)
    {
        try{Thread.sleep(time);}catch(Exception e){}
    }
    
    /**
     * Turns on the channel for a given Synthesizer Note
     */
    public static int playNote(int note, double volume)
    {
        //if(notes[note])
        //    return note;
        System.out.println("Turning On: " +note);
        notes[note] = 1;
        piano.noteOn(note, (int)(volume * Piano.MAX_VOLUME));
        frame.repaint();
        return note;
    }
    /**
     * Turns on the channel for a given Synthesizer Note with a specific draw color
     */
    public static int playNote(int note, double volume, int color)
    {
        //if(notes[note])
        //    return note;
        System.out.println("Turning On: " +note);
        notes[note] = color;
        piano.noteOn(note, (int)(volume * Piano.MAX_VOLUME));
        frame.repaint();
        return note;
    }
    /**
     * Turns off the channel for a given Synthesizer Note
     */
    public static void endNote(int note)
    {
        if(notes[note] == 0)
            return;
        System.out.println("Turning Off: " +note);
        notes[note] = 0;
        piano.noteOff(note);
        frame.repaint();
    }
    
}
  