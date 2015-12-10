
/**
 * Write a description of class Note here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Note
{
    public static final int REST = 00;
    public static final int HOLD = 11;
    public static final int BASE = 12;
    
    public static final int SLOW_TRIPLET = BASE * 4 / 3;
    public static final int QUARTER = BASE;
    public static final int EIGHT = BASE/2;
    public static final int TRIPLET = BASE/3;
    public static final int SIXTEENTH = BASE/4;
    public static final int FIFTH = BASE/5;
    public static final int SIXTUPLET = BASE/6;
    
    public static final int MAX_VOLUME = 99;
    public int note;
    public int length;
    public int itr;
    public int volume; //0 -> 99
    public int color;
    public Note()
    {
        length = BASE;
        volume = 99;
        itr = 0;
        color = 1;
    }
    public Note copy()
    {
        Note out = new Note();
        out.length = length;
        out.volume = volume;
        out.note = note;
        return out;
    }
    public int remainingLength()
    {
        return length - itr;
    }
    public boolean hasMore()
    {
        if(itr + 1 < length)
        {
            return true;            
        }
        else
        {
            itr = length;
            return false;
        }
    }
    public void tick()
    {
        itr++;
    }
    public boolean isRest()
    {
        return note == REST;
    }
    public String formatNote()
    {
        if(note < 10)
            return "0" +note;
        else if(note < 100)
            return "" +note;
        else
            return ("" +note).substring(0,2);
    }
    public String toString()
    {
        String out =  "[" +formatNote() + ":" +(length-itr) + "," +color + "." +volume +"]";
        for(int i = out.length(); i < length; i++)
            out += " ";
        return out;
    }
}
