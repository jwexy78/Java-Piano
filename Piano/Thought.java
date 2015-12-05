import java.util.ArrayList;
public class Thought
{
    public ArrayList<AudioPlayer> audioPlayers;
    private int itr;
    public int bpm;
    public Thought()
    {
        audioPlayers = new ArrayList<AudioPlayer>();
    }
    public void add(Fragment frag)
    {
        audioPlayers.add(new AudioPlayer(frag.copy()));
    }
    public void add(Thought thought)
    {
        for(AudioPlayer ap : thought.audioPlayers)
        {
            audioPlayers.add(ap);
        }
    }
    public void append(Thought thought)
    {
        String bls = blankLengthString();
        for(AudioPlayer p : thought.audioPlayers)
        {
            audioPlayers.add(new AudioPlayer(new Fragment(bls + p.fragment.getFullString())));
        }
    }
    public int length()
    {
        int longest = 0;
        for(AudioPlayer p : audioPlayers)
        {
            if(longest < p.fragment.length())
                longest = p.fragment.length();
        }
        return longest;
    }
    public String blankLengthString()
    {
        String output = "";
        String bit = (Fragment.REST < 10 ? "0" +Fragment.REST : "" +Fragment.REST);
        for(int i = 0; i < length(); i++)
            output += bit;
        return output;
    }
    public void tick()
    {
        for(AudioPlayer p : audioPlayers)
        {
            if(p.running)
                p.turnOffs();
        }
        for(AudioPlayer p : audioPlayers)
        {
            if(p.running)
                p.turnOns();
        }
    }
    public void setColor(int color)
    {
        for(AudioPlayer p : audioPlayers)
        {
            p.fragment.color = color;
        }
    }
    public boolean hasMore()
    {
        for(AudioPlayer p : audioPlayers)
        {
            if(p.running)
                return true;
        }
        return false;
    }
    public int getBPM()
    {
        return bpm;
    }
}
