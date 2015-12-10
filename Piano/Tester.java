
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tester
{
    public static void main(String[] args)
    {
        AudioPlayer p = new AudioPlayer(new Fragment("61110062"));
        for(int i = 0; i < 50; i++)
        {
            p.tick();
            System.out.println(p.currentNote);
        }
    }
}
