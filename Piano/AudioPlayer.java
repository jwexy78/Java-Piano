
/**
 * Write a description of class AudioPlayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AudioPlayer
{
    public Fragment fragment;
    public boolean running;
    int previousNote;
    public AudioPlayer(Fragment frag)
    {
        previousNote = -1;
        fragment = frag;
        running = true;
    }
    public void turnOffs()
    {
        if(!fragment.hasNextNote())
        {
            if(previousNote != -1)
                Screen.endNote(previousNote);
            running = false;
            return;
        }
        int toPlay = fragment.peek();
        
        if(previousNote != -1)
        {
            if(toPlay != Fragment.HOLD)
            {
                if(fragment.notes[fragment.itr-1] != Fragment.REST)
                    Screen.endNote(previousNote);
            }
        }
    }
    public void turnOns()
    {
        int toPlay = fragment.nextNote();
        
        if(previousNote != -1)
        {
            if(toPlay != Fragment.HOLD)
            {
                if(toPlay != Fragment.REST)
                {
                    //If toPlay is an actual note
                    Screen.playNote(toPlay, fragment.partialVolume, fragment.color);
                    previousNote = toPlay;
                }
            }
        }
        else if(toPlay != Fragment.HOLD && toPlay != Fragment.REST)
        {
            //If toPlay is an actual note
            Screen.playNote(toPlay, fragment.partialVolume, fragment.color);
            previousNote = toPlay;
        }
    }
}
