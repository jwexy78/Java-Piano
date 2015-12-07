
/**
 * Write a description of class Fragment here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fragment
{
    public int[] notes;
    private String string;
    public int itr;
    public static final int REST = 00;
    public static final int HOLD = 11;
    
    public double partialVolume;
    public int color;
    public Fragment(String str)
    {
        char[] arr = str.toCharArray();
        notes = new int[arr.length / 2];
        for(int i = 0; i < arr.length; i+= 2)
        {
            notes[i/2] = (arr[i]-'0')*10 + (arr[i+1]-'0');
        }
        itr = 0;
        partialVolume = 1;
        color = 1;
    }
    public Fragment append(Fragment m)
    {
        String newString = getFullString() + m.getFullString();
        Fragment out = new Fragment(newString);
        out.color = color;
        out.partialVolume = partialVolume;
        return out;
    }
    public Fragment loop(int n)
    {
        String out = getFullString();
        String output = "";
        for(int i = 0; i < n; i++)
            output += out;
        Fragment outF = new Fragment(output);
        outF.color = color;
        outF.partialVolume = partialVolume;
        return outF;
    }
    public Fragment shift(int scale)
    {
        Fragment m = copy();
        for(int i = 0; i < m.notes.length; i++)
        {
            if(m.notes[i] != Fragment.REST && m.notes[i] != Fragment.HOLD)
                m.notes[i] = m.notes[i] + scale;
        }
        return m;
    }
    public String getFullString()
    {
        String output = "";
        for(int i : notes)
        {
            if(i < 10)
                output += "0";
            output += i;
        }
        return output;
    }
    public String blankLengthString()
    {
        String output = "";
        String bit = (Fragment.REST < 10 ? "0" +Fragment.REST : "" +Fragment.REST);
        for(int i = 0; i < length(); i++)
            output += bit;
        return output;
    }
    public Fragment expand(int factor)
    {
        String output = "";
        for(int i = 0; i < notes.length; i++)
        {
            for(int j = 0; j < factor; j++)
            {
                if(j != 0)
                {
                    if(notes[i] != REST)
                    {
                        output += formatNote(HOLD);
                    }
                }
                else
                    output += formatNote(notes[i]);
            }
        }
        Fragment outF = new Fragment(output);
        outF.color = color;
        outF.partialVolume = partialVolume;
        return outF;
    }
    private String formatNote(int note)
    {
        return (note < 10 ? "0"+note:""+note);
    }
    public Thought dominantSeventh()
    {
        Thought out = new Thought();
        out.add(copy());
        out.add(copy().shift(4));
        out.add(copy().shift(7));
        out.add(copy().shift(10));
        return out;
    }
    public Thought minorSeventh()
    {
        Thought out = new Thought();
        out.add(copy());
        out.add(copy().shift(3));
        out.add(copy().shift(7));
        out.add(copy().shift(10));
        return out;
    }
    public Thought powerChord()
    {
        Thought out = new Thought();
        out.add(copy());
        out.add(copy().shift(7));
        return out;
    }
    public Thought superPowerChord()
    {
        Thought out = new Thought();
        out.add(copy());
        out.add(copy().shift(7));
        out.add(copy().shift(12));
        return out;
    }
    public int length()
    {
        return notes.length;
    }
    public Fragment copy()
    {
        return loop(1);
    }
    public int nextNote()
    {
        return notes[itr++];
    }
    public int peek()
    {
        return notes[itr];
    }
    public boolean hasNextNote()
    {
        return itr < notes.length;
    }
    public void restart()
    {
        itr = 0;
    }
    public String toString()
    {
        String output = "";
        for(int i : notes)
        {
            if(i == REST)
                output += "  ";
            else if(i == HOLD)
                output += "--";
            else
            {
                if(i < 10)
                    output += "0";
                output += i;
            }
        }
        return output;
    }
}
