import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;

import javax.swing.JFrame;
import java.awt.event.*;

import java.util.ArrayList;
public class Screen
{
    public static MidiChannel piano;
    public static int[] notes;
    public static boolean shiftIsDown;
    public static int scaleNote;
    public static boolean scaleMode;
    public static int metronome = 120;
    public static final JFrame frame = new JFrame();
    private static ArrayList<Note> currentNotes;
    public static void main( String[] args ) 
    {
        currentNotes = new ArrayList<Note>();
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
        
        
        pause(2000);
        
        //playThought(letItGo());
        
        Thought test = new Thought();
        Fragment frag = new Fragment("48554855");
        test.add(frag);
        Fragment frag2 = new Fragment("60656772").loop(4).setSubdivision(Note.SIXTEENTH);
        test.add(frag2);
        test.bpm = 60;
        playThought(letItGo());
        
        //test.setVolume(90);
        //System.out.println(test.fullInfoString());
        //System.out.println(test.expand(2).fullInfoString());
        
        
        //System.out.println(snowman());
        //playThought(letItGo());
        
        //Thought bwu = blueWarmup();
        //Piano.thoughtToDraw = bwu;
        //playThought(bwu);
        
    }
//     private static Thought randomSolo()
//     {
//         String[] notes = new String[] {"60","62","63","67","72"};
//         String[] rythms = new String[] {"n-nn-nn-n000","00n--n--0000","nnnnnnnnnnnn","n-n-n-n-n-n-",};
//         Fragment f = new Fragment("");
//         for(int i = 0; i < 200; i++)
//         {
//             String rythm = rythms[(int)(Math.random() * rythms.length)];
//             String out = "";
//             for(char c : rythm.toCharArray())
//             {
//                 if(c == '0')
//                     out += "00";
//                 else if(c == '-')
//                     out += "11";
//                 else if(c == 'n')
//                     out += notes[(int)(Math.random() * notes.length)];
//                     
//                 //for(int j = 0; Math.random() < .5; j++)
//                 //{
//                 //    out += "00";
//                 //}
//             }
//             f.append(new Fragment(out));
//         }
//         f.setVolume(80);
//         
//         Fragment m = new Fragment("");
//         m.append(new Fragment("481111111148000000000000").loop(2));
//         m.append(new Fragment("531111111153000000000000").loop(2));
//         m.append(new Fragment("481111111148000000000000").loop(2));
//         m.append(new Fragment("531111111153000000000000").loop(2));
//         
//         m.append(new Fragment("551111111155000000000000"));
//         m.append(new Fragment("531111111153000000000000"));
//         m.append(new Fragment("481111111148000000000000").loop(2));
//         
//         
//         Fragment n = new Fragment("");
//         n.append(new Fragment("551111111157000000000000").loop(2));
//         n.append(new Fragment("601111111162000000000000").loop(2));
//         n.append(new Fragment("551111111157000000000000").loop(2));
//         n.append(new Fragment("601111111162000000000000").loop(2));
//         n.append(new Fragment("621111111164000000000000"));
//         n.append(new Fragment("601111111162000000000000"));
//         n.append(new Fragment("551111111157000000000000").loop(2));
//         
//         
//         m = m.loop(10);
//         n = n.loop(10);
//         
//         m.color = 2;
//         n.color = 2;
//         
//         Thought out = new Thought();
//         out.add(f);
//         out.add(m);
//         out.add(n);
//         out.bpm = 400;
//         return out;
//     }
//     private static Thought snowman()
//     {
//         Fragment bells = new Fragment("");
//         Fragment new Fragment("");
//         bells.append(new Fragment("7479847679847784").loop(4).append(new Fragment("88111111111111110000000000000000")));
//         bass1.append(new Fragment("6511116011117211").loop(4).append(new Fragment("72111111111111110000000000000000")));
//         bass1.color = 5;
//         Thought whole = new Thought();
//         whole.add(bells);
//         whole.add(bass1);
//         whole.bpm = 400;
//         return whole;
//     }

    private static Thought littleMermaid()
    {
        Fragment bass1 =  new Fragment("");
        Fragment bass2 = new Fragment("");
        Fragment bass3 = new Fragment("");
        bass1.color = bass2.color = bass3.color = 3;
        Fragment melody1 = new Fragment("");
        Fragment melody2 = new Fragment("");
        melody1.color = melody2.color = 1;
        Fragment bells1 = new Fragment("");
        Fragment bells2 = new Fragment("");
        bells1.color = bells2.color = 2;
        
        bass1.append(new Fragment("5860").expand(8).loop(4));
        bass2.append(new Fragment("6567").expand(8).loop(4));
        bass3.append(new Fragment("7072").expand(8).loop(4));
        
        melody1.append(new Fragment("0000").expand(8).loop(2));
        melody2.append(new Fragment("0000").expand(8).loop(2));
        
        bells1.append(new Fragment("70727477").loop(2).append(new Fragment("72747679").loop(2)).loop(2).shift(12));
        bells1.append(new Fragment("70727477").loop(2).append(new Fragment("72747679").loop(2)).loop(5).shift(12).setVolume(60));
        
        
        melody1.append(new Fragment("70727474111111007274767611111100"));
        melody2.append(new Fragment("74767777111111007677797911111100"));
        
        melody1.append(new Fragment("70727474117211747274117676111100"));
        melody2.append(new Fragment("74767777117611777677117979111100"));
        
        melody1.append(new Fragment("72117476117476741111117474767700"));
        melody2.append(new Fragment("76117779117779771111117777798100"));
        
        melody1.append(new Fragment("77117676111111117411111111111111"));
        melody2.append(new Fragment("81117979111111117711111111111111"));
        
        bass1.append(new Fragment("57586260").expand(8));
        bass2.append(new Fragment("60626564").expand(8));
        bass3.append(new Fragment("64656900").expand(8));
        
        
        melody1.append(new Fragment("70727474111111007274767611111100"));
        melody2.append(new Fragment("74767777111111007677797911111100"));
        
        melody1.append(new Fragment("70727474117211747274117676111100"));
        melody2.append(new Fragment("74767777117611777677117979111100"));
        
        melody1.append(new Fragment("72117476117476741111117474767700"));
        melody2.append(new Fragment("76117779117779771111117777798100"));
        
        melody1.append(new Fragment("77117676111111117411111111741177"));
        melody2.append(new Fragment("81117979111111117711111111111111"));
        
        bass1.append(new Fragment("5860").expand(8).loop(2));
        bass2.append(new Fragment("6567").expand(8).loop(2));
        bass3.append(new Fragment("7072").expand(8).loop(2));
        bass1.append(new Fragment("57586260").expand(8));
        bass2.append(new Fragment("60626564").expand(8));
        bass3.append(new Fragment("64656900").expand(8));
        
        bells1.append(new Fragment("70727477").loop(2).append(new Fragment("72747679").loop(2)).loop(3).shift(12).setVolume(60));
        
        
        melody1.append(new Fragment("81818181797776111172111111741176").setSubdivision(Note.SLOW_TRIPLET));
        //melody2.append(new Fragment("81117979111111117711111111111111"));
        
        //melody1.append(new Fragment("70727474117211747274767611111100"));
        //melody2.append(new Fragment("74767777117611777677797911111100"));
        
        Thought whole = new Thought();
        whole.add(bass1);
        whole.add(bass2);
        whole.add(bass3);
        whole.add(melody1);
        whole.add(melody2);
        whole.add(bells1);
        whole.add(bells2);
        whole.bpm = 300;
        
        System.out.println(whole);
        return whole;
    }
    private static Thought letItGo()
    {
        Piano.octave = -1;
        
        Fragment bass1 = new Fragment("");
        Fragment bass2 = new Fragment("");
        Fragment bass3 = new Fragment("");
        
        Fragment melody1 = new Fragment("");
        Fragment melody2 = new Fragment("");
        
        Fragment bells1 = new Fragment("");
        Fragment bells2 = new Fragment("");
        Fragment bells3 = new Fragment("");
        
        Fragment fun = new Fragment("");
        fun.applyColor(4);
        
        bass1.applyColor(2);
        bass2.applyColor(2);
        bass3.applyColor(2);
        
        melody1.applyColor(5);
        melody2.applyColor(5);
        
        //bass1.color = bass2.color = bass3.color = 2;
        bells1.color = bells2.color = 1;
        //melody1.color = melody2.color = 5;
        
        
        
        bass1.append(new Fragment("55515348").expand(8).loop(5)).append(new Fragment("5551").expand(8));
        bass2.append(new Fragment("62586055").expand(8).loop(5)).append(new Fragment("6258").expand(8));
        bass3.append(new Fragment(bass1.blankLengthString()));
        
        
        
        
        
        bass1.append(new Fragment("48111111111111111111111111115311"));
        bass2.append(new Fragment("55111111111111111111111111111160"));
        bass3.append(new Fragment("00000000000000000000000000000000"));
        
        bass1.append(new Fragment("65111111111111111165111165116511")).append(new Fragment("65111111111111111165111165116511").shift(-2)).append(new Fragment("65111111111111111165111165116511"));
        bass2.append(new Fragment("60111160111160111160111160116011")).append(new Fragment("60111160111160111160111160116011").shift(-2)).append(new Fragment("60111160111160111160111160116011"));
        bass3.append(new Fragment("53115311115311115311115311531153")).append(new Fragment("53115311115311115311115311531153").shift(-2)).append(new Fragment("53115311115311115311115311531153"));
        
         

        bells1.append(new Fragment("6970626911701111697062701169116067696067116911606511111163111111"));
        bells1.append(new Fragment("6970626911701111697062701169116067696067116911606511111164111111"));
        bells1.shift(12);
        //         
        bells3.append(new Fragment(bells1.blankLengthString()));
        bells3.append(bells1);
        bells3.append(new Fragment("6970626911701111706962701169116067696067116911606511111163111111").shift(12));
        bells3.append(new Fragment("6970626911701111706962701169116000000000").shift(12));
        
        bells1.append(new Fragment("0000").loop(58));
        bells1.append(new Fragment("651111117211111177111111"));
        bells1.append(new Fragment("0000").loop(39));
        
        bells3.append(new Fragment("0000").loop(39));
        bells3.append(new Fragment("000000000000000000000000"));
        
        bells2.append(new Fragment(bells1.blankLengthString()));
        
        
        melody1.append(new Fragment("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000060"));
        melody1.append(new Fragment("6211621162116262625858581111585860621111115857551111111111111160"));
        melody1.append(new Fragment("6262626211116565116211111111585860621111116260621111111111111111"));
        melody2.append(new Fragment(melody1.blankLengthString()));
        melody1.append(new Fragment("0000626211651167116511621165116511651163116211636211111111111111"));
        melody2.append(new Fragment("0000555511551160116011601160115811581158115811115511111111111111"));
        
        melody1 = melody1.append(new Fragment("0000626260581160111160626011581160111111111111111111111111111111"));
        melody2 = melody2.append(new Fragment("0000555555551153111153535311531155111111111111111111111111111111"));
        
        
        bass1.append(new Fragment("6311").expand(2).loop(5));
        bass2.append(new Fragment("5811").expand(2).loop(5));
        bass3.append(new Fragment("00511151115111511151").expand(2));
        
        bass1.append(new Fragment("6311").expand(1).loop(2));
        bass2.append(new Fragment("5811").expand(1).loop(2));
        bass3.append(new Fragment("00511151").expand(1));
        
        bass1.append(new Fragment("63").expand(1).loop(5));
        bass2 = bass2.append(new Fragment("58").expand(1).loop(5));
        bass3 = bass3.append(new Fragment("51").expand(1).loop(5));
        bass1.append(new Fragment("00").expand(1).loop(3));
        bass2 = bass2.append(new Fragment("00").expand(1).loop(3));
        bass3 = bass3.append(new Fragment("00").expand(1).loop(3));
        
        melody1 = melody1.append(new Fragment("0000606011651165116011601167116711116765676711656769117011691169"));
        melody2 = melody2.append(new Fragment("0000000011601160116011601160116011110000000000000060116011601160"));
        
        melody1 = melody1.append(new Fragment("0000606011651165116011601167116711111111111111111111111111116567116911701111111170707070706769"));
        melody2 = melody2.append(new Fragment("0000000011601160116011601160116011111111111111111111111111115858115811631111111163636363630000"));
        
        fun.append(new Fragment(bass1.blankLengthString()));
        fun.append(new Fragment("72716971696765676564626462605960").setSubdivision(Note.EIGHT).shift(-2));
        fun.append(new Fragment("60626462646567656769716971727472").setSubdivision(Note.EIGHT).shift(-7));
        fun.append(new Fragment("72716971696765676564626462605960").setSubdivision(Note.EIGHT).shift(-5));
        fun.append(new Fragment("60626462646567656769716971727472").setSubdivision(Note.EIGHT).shift(-9));
        fun.setVolume(100);
        
        bass1.append(new Fragment("58535551").expand(8).loop(3));
        bass2.append(new Fragment("62575855").expand(8).loop(3));
        bass3.append(new Fragment("65606258").expand(8).loop(3));
        
        melody1.append(new Fragment("7011111111116565721111111111117069676767116769117011727011116769701111111111"));
        melody1.append(new Fragment("657472111111111170727411741175117472707270111111111111"));
        melody2.append(new Fragment("00").loop(65));
        //melody2 = melody2.append(new Fragment("000000001160116011601160116011601111111111111111111111111111585811581163111111116363636363000000"));
        
        bells1 = bells1.append(new Fragment("00").loop(2));
        bells2 = bells2.append(new Fragment("00").loop(2));
        bells3 = bells3.append(new Fragment("00").loop(2));
        
        bells3 = bells3.append(new Fragment("8277").loop(4));
        bells2 = bells2.append(new Fragment("8600").loop(4));
        bells3 = bells3.append(new Fragment("8177").loop(4));
        bells2 = bells2.append(new Fragment("8400").loop(4));
        bells3 = bells3.append(new Fragment("8279").loop(4));
        bells2 = bells2.append(new Fragment("8600").loop(4));
        bells3 = bells3.append(new Fragment("8279").loop(4));
        bells2 = bells2.append(new Fragment("8700").loop(4));
        
        bells3 = bells3.append(new Fragment("8277").loop(4));
        bells2 = bells2.append(new Fragment("8600").loop(4));
        bells3 = bells3.append(new Fragment("8177").loop(4));
        bells2 = bells2.append(new Fragment("8400").loop(4));
        bells3 = bells3.append(new Fragment("8279").loop(4));
        bells2 = bells2.append(new Fragment("8600").loop(4));
        bells3 = bells3.append(new Fragment("8279").loop(4));
        bells2 = bells2.append(new Fragment("8700").loop(4));
        
        bass1.append(new Fragment("50480000").expand(8));
        bass2.append(new Fragment("53430000").expand(8));
        bass3.append(new Fragment("57550000").expand(8));
        
        
        melody1 = melody1.append(new Fragment("7711117411117211111111117011701177111174111170111111111111117070691111651111651111111111111111747500757475747575747011111111"));
        melody2 = melody2.append(new Fragment("65111165111165111111111165116511651111651111651111111111"));
        
        bells3.setVolume(60);
        bells2.setVolume(60);
        
        Thought whole = new Thought();
        whole.add(bass1);
        whole.add(bass2);
        whole.add(bass3);
        whole.add(bells1);
        whole.add(bells2);
        whole.add(bells3);
        whole.add(melody1);
        whole.add(melody2);
        whole.add(fun);
        whole.bpm = 240;
        System.out.println(whole);
        return whole;
    }
    private static Thought insideOut()
    {
        //Inside Out Song / Fragment + Thought method testing
        Fragment bgr1 = new Fragment("50595059505950595059505950595059");
        Fragment bgr2 = new Fragment("4857485748574857485748574857485748574857485748574857485748574857");
        Fragment mel1 = new Fragment("7111117111116967697111671111696766711167111169676671116772716967");
        Fragment mel2 = new Fragment("6511116911116765676911651111676564691165111167656469116572716965");
        Fragment rest = new Fragment("00000000000000000000000000000000");
        Fragment intr = new Fragment("62666771111111111100000000000000");
        
        Fragment background = bgr1.append(bgr1.loop(2).append(bgr2).loop(2));
        background.setVolume(60);
        Fragment melody = rest.append(mel1.append(mel2).loop(2));
        melody.setVolume(80);
        Fragment interludes = rest.loop(5).append(intr.loop(2).append(intr.shift(-2).loop(2)));
        interludes.setVolume(80);
        
        background.color = 5;
        melody.color = 1;
        interludes.color = 3;
        
        Piano.octave = -1;
         
        Thought iso = new Thought();
        iso.add(background);
        iso.add(melody);
        iso.add(interludes);
        iso.bpm = 300;
        return iso;
    }
//     private static Thought blueWarmup()
//     {
//         Piano.octave = -2;
//         
//         Fragment new Fragment("00581111621111651111621111631111671111701111671111").shift(-24);
//         Fragment bass2 = new Fragment("581111621111651111671111681111671111651111621111").shift(-24);
//         Fragment bass3 = bass2.shift(5);
//         Fragment bass4 = new Fragment("601111621111631111641111651111671111681111691111701111681111671111661111651111631111621111601111581111").shift(-24);
//         
//         Fragment tm1 = new Fragment("74000070000067700000000073000070000067700000");
//         Fragment tm2 = new Fragment("000074000070000067700072700067700072701170000000");
//         Fragment tm3 = new Fragment("000073000070000067700000000073000070000067700000");
//         Fragment tm4 = new Fragment("000072000067000072710067630060700067681169000000");
//         metronome = 600;
//         
//         Fragment chordRoots1 = new Fragment("00581111111158000000000000631111111163000000000000581111111158000000000000581111111158000000000000").shift(-12);
//         Fragment chordRoots2 = new Fragment("631111111163000000000000631111111163000000000000581111111158000000000000551111111155000000000000").shift(-12);
//         Fragment chordRoots3 = new Fragment("601111111160000000000000").shift(-12);
//         Fragment chordRoots4 = new Fragment("6511111111650000000000005811111111580000000000005811111111580000005811111111111111111111").shift(-12);
//         
//         Fragment allBass = bass1.append(bass2).append(bass3).append(bass2).append(bass4);
//         Fragment allMel = tm1.append(tm2).append(tm3).append(tm2).append(tm4).append(tm2);
//         allBass.color = 3;
//         allMel.color = 1;
//         
//         Thought chords = new Thought();
//         chords.append(chordRoots1.dominantSeventh());
//         chords.append(chordRoots2.dominantSeventh());
//         chords.append(chordRoots3.minorSeventh());
//         chords.append(chordRoots4.dominantSeventh());
//         chords.setColor(2);
//         
//         Thought t = new Thought();
//         t.add(allBass);
//         t.add(allMel);
//         t.add(chords);
//         t.bpm = 600;
//         return t;
//     }
    private static void playThought(Thought t)
    {
        Piano.thoughtToDraw = t;
        final Thought thought = t;
        Thread t1 = new Thread()
        {
            public void run()
            {
                int counter = 0;
                while(thought.hasMore())
                {
                    thought.tick();
                    
                    frame.repaint();
                    //System.out.println(thought);
                    //System.out.println("\f" + thought.stringFromBeat(thought.itr));
                    int toRest = (int)(60.0 / thought.getBPM() * 1000.0) / Note.BASE;
                    pause(toRest);
//                     while(toRest > 0)
//                     {
//                         if(toRest <= 25)
//                         {
//                             thought.currentBeatPercentage = 1;//toRest * 1.0 / (int)(60.0 / thought.getBPM() * 1000.0);
//                             frame.repaint();
//                             pause(toRest);
//                             toRest = 0;
//                         }
//                         else
//                         {
//                             thought.currentBeatPercentage = 1 - (toRest * 1.0 / (int)(60.0 / thought.getBPM() * 1000.0));
//                             frame.repaint();
//                             pause(25);
//                             toRest -= 25;
//                         }
//                     }
                }
            }
        };
        t1.start();
    }
    private static void pause(long time)
    {
        try{Thread.sleep(time);}catch(Exception e){}
    }
    
    public static void addNote(Note n)
    {
        currentNotes.add(n);
        playNote(n.note, n.volume * 1.0 /  Note.MAX_VOLUME, n.color);
    }
    public static void removeNote(Note n)
    {
        currentNotes.remove(n);
        Note sameNote = null;
        for(Note no : currentNotes)
        {
            if(no.note == n.note)
            {
                sameNote = no;
                break;
            }
        }
        if(sameNote == null)
        {
            endNote(n.note);
        }
        else
        {
            notes[n.note] = sameNote.color;
        }
    }
    
    /**
     * Turns on the channel for a given Synthesizer Note
     */
    private static int playNote(int note, double volume)
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
    private static int playNote(int note, double volume, int color)
    {
        //if(notes[note])
        //    return note;
        //System.out.println("Turning On: " +note);
        notes[note] = color;
        piano.noteOn(note, (int)(volume * Piano.MAX_VOLUME));
        frame.repaint();
        return note;
    }
    /**
     * Turns off the channel for a given Synthesizer Note
     */
    private static void endNote(int note)
    {
        if(notes[note] == 0)
            return;
        //System.out.println("Turning Off: " +note);
        notes[note] = 0;
        piano.noteOff(note);
        frame.repaint();
    }
    
}
  