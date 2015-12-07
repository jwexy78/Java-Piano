import java.util.ArrayList;
public class Thought
{
    public ArrayList<AudioPlayer> audioPlayers;
    public int itr;
    public int bpm;
    public double currentBeatPercentage;
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
            Fragment f = new Fragment(bls + p.fragment.getFullString());
            f.color = p.fragment.color;
            f.partialVolume = p.fragment.partialVolume;
            audioPlayers.add(new AudioPlayer(f));
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
        itr++;
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
    public int[][] shortChannels()
    {
        int[][] output = new int[audioPlayers.size()][10];
        for(int i = 0; i < audioPlayers.size(); i++)
        {
            for(int j = 0; j < Math.min(audioPlayers.get(i).fragment.length() - itr, 10); j++)
            {
                if(itr != 0)
                    output[i][j] = audioPlayers.get(i).fragment.notes[itr+j-1];
            }
            if(output[i][0] == Fragment.HOLD)
            {
                for(int k = itr-1; k > -1; k--)
                {
                    if(audioPlayers.get(i).fragment.notes[k] != Fragment.HOLD)
                    {
                        output[i][0] = audioPlayers.get(i).fragment.notes[k];
                        break;
                    }
                }
            }
        }
        return output;
    }
    public String toString()
    {
        String output = "{\n";
        for(AudioPlayer p : audioPlayers)
        {
            output += p.fragment.toString() +"\n";
        }
        output += "}";
        return output;
    }
    public String stringFromBeat(int beat)
    {
        String output = "{\n";
        for(AudioPlayer p : audioPlayers)
        {
            if(p.fragment.toString().length() > beat * 2)
                output += p.fragment.toString().substring(beat * 2);
            output += "\n";
        }
        output += "}";
        return output;
    }
}
