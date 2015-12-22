
/**
 * Write a description of class Fragment here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
public class Fragment
{
    public ArrayList<Note> notes;
    //public int[] notes;
    //public int[] volumes;
    private String string;
    public int itr;
    public static final int REST = 00;
    public static final int HOLD = 11;
    public int color;
    public Fragment(String str)
    {
        char[] arr = str.toCharArray();
        notes = new ArrayList<Note>(arr.length / 2);
        Note oldNote = null;
        for(int i = 0; i < arr.length; i+= 2)
        {
            Note n = new Note();
            int value = (arr[i]-'0')*10 + (arr[i+1]-'0');
            if(value == Note.REST)
            {
                if(oldNote != null && oldNote.isRest())
                {
                    oldNote.length += Note.BASE;
                }
                else
                {
                    n.note = Note.REST;
                    oldNote = n;
                    notes.add(n);
                }
            }
            else if(value == Note.HOLD)
            {
                if(oldNote != null)
                    oldNote.length += Note.BASE;
            }
            else
            {
                n.note = value;
                oldNote = n;
                notes.add(n);
            }
        }
        itr = 0;
        color = 1;
        applyColor(color);
    }
    public Fragment append(Fragment m)
    {
        for(int i = 0 ; i < m.notes.size(); i++)//Note n : m.notes)
        {
            notes.add(m.notes.get(i).copy());
        }
        return this.applyColor(color);
    }
    public Fragment copy()
    {
        return new Fragment("").append(this);
    }
    public Fragment loop(int n)
    {
        int len = notes.size();
        for(int k = 1; k < n; k++)
        {
            for(int i = 0; i < len; i++)
            {
                notes.add(notes.get(i).copy());
            }
        }
        return this;
    }
    public Fragment shift(int scale)
    {
        for(Note n : notes)
        {
            if(!n.isRest())
                n.note += scale;
        }
        return this;
    }
    public String getFullString()
    {
        String output = "";
        for(Note i : notes)
        {
            output += i.toString();
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
        for(Note n : notes)
        {
            n.length *= factor;
        }
        return this;
    }
    public Fragment applyColor(int c)
    {
        color = c;
        for(Note n : notes)
        {
            n.color = color;
        }
        return this;
    }
    public Note[] getNextNotes()
    {
        Note[] out = new Note[10];
        for(int z = itr; z < notes.size() && z < itr + 9; z++)
        {
            out[z-itr+1] = notes.get(z);
        }
        if(itr > 0)
            out[0] = notes.get(itr - 1);
        return out;
    }
    public Fragment setSubdivision(int amt)
    {
        for(Note n : notes)
        {
            n.length = amt * n.length / Note.BASE;
        }
        return this;
    }
    
//     public Thought dominantSeventh()
//     {
//         Thought out = new Thought();
//         out.add(copy());
//         out.add(copy().shift(4));
//         out.add(copy().shift(7));
//         out.add(copy().shift(10));
//         return out;
//     }
//     public Thought minorSeventh()
//     {
//         Thought out = new Thought();
//         out.add(copy());
//         out.add(copy().shift(3));
//         out.add(copy().shift(7));
//         out.add(copy().shift(10));
//         return out;
//     }
//     public Thought powerChord()
//     {
//         Thought out = new Thought();
//         out.add(copy());
//         out.add(copy().shift(7));
//         return out;
//     }
//     public Thought superPowerChord()
//     {
//         Thought out = new Thought();
//         out.add(copy());
//         out.add(copy().shift(7));
//         out.add(copy().shift(12));
//         return out;
//     }
    public Fragment setVolume(int v)
    {
        for(Note n : notes)
            n.volume = v;
        return this;
    }
    public int length()
    {
        int output = 0;
        for(Note n : notes)
        {
            output += n.length;
        }
        return output / Note.BASE;
    }
    public Note getNextNote()
    {
        return notes.get(itr++);
    }
    public boolean hasMore()
    {
        return itr < notes.size();
    }
    //public Fragment copy()
    //{
    //    return loop(1);
    //}
    //public int nextNote()
    //{
    //    return notes[itr++];
    //}
    //public int peek()
    //{
    //    return notes[itr];
    //}
    //public boolean hasMore()
    //{
    //    return itr < notes.size();
    //}
    public void restart()
    {
        itr = 0;
    }
//     public String fullInfoString()
//     {
//         String out = "[";
//         for(int n : notes)
//             out += formatNote(n) + " ";
//         out += "\n ";
//         for(int n : volumes)
//             out += formatNote(n) + " ";
//         out += "]";
//         return out;
//     }
//     public String toString()
//     {
//         String output = "";
//         for(int i : notes)
//         {
//             if(i == REST)
//                 output += "  ";
//             else if(i == HOLD)
//                 output += "--";
//             else
//             {
//                 if(i < 10)
//                     output += "0";
//                 output += i;
//             }
//         }
//         return output;
//     }
    public String toString()
    {
        String out = "";
        for(Note n : notes)
        {
            out += n + " " ;
        }
        return out;
    }
}
