
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
    Note currentNote;
    public AudioPlayer(Fragment frag)
    {
        fragment = frag;
        running = true;
    }
    
    public void tick()
    {
        //If the current note is still playing
        if(currentNote != null && currentNote.hasMore())
        {
            currentNote.tick();
            return;
        }
        //If we had a previous note
        if(currentNote != null)
        {
            Screen.removeNote(currentNote);
        }
        if(fragment.hasMore())
        {
            currentNote = fragment.getNextNote();
            Screen.addNote(currentNote);
        }
        else
        {
            running = false;
        }
    }
//     
//     public void turnOffs()
//     {
//         if(!fragment.hasNextNote())
//         {
//             if(previousNote != -1)
//                 Screen.endNote(previousNote);
//             running = false;
//             return;
//         }
//         int toPlay = fragment.peek();
//         
//         if(previousNote != -1)
//         {
//             if(toPlay != Fragment.HOLD)
//             {
//                 if(fragment.notes[fragment.itr-1] != Fragment.REST)
//                     Screen.endNote(previousNote);
//             }
//         }
//     }
//     public void turnOns()
//     {
//         int toPlay = fragment.nextNote();
//         
//         if(previousNote != -1)
//         {
//             if(toPlay != Fragment.HOLD)
//             {
//                 if(toPlay != Fragment.REST)
//                 {
//                     //If toPlay is an actual note
//                     Screen.playNote(toPlay, fragment.volumes[fragment.itr - 1] / 100.0, fragment.color);
//                     previousNote = toPlay;
//                 }
//             }
//         }
//         else if(toPlay != Fragment.HOLD && toPlay != Fragment.REST)
//         {
//             //If toPlay is an actual note
//             Screen.playNote(toPlay, fragment.volumes[fragment.itr - 1] / 100.0, fragment.color);
//             previousNote = toPlay;
//         }
//     }
}
