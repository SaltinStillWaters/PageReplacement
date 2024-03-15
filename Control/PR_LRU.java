package Control;

import java.util.Arrays;
import java.util.Deque;

public class PR_LRU
{
    /*
     * Driver function
     */
    public static void pageReplacement(Deque<String> jobQueue, String[][] frames, int[][] framesHistory)
    {
        //set up first job
        frames[0][0] = jobQueue.removeFirst();
        framesHistory[0][0] = 0;

        PR(jobQueue, frames, framesHistory);
    }

    /*
     * Main algorithm for page replacement
     */
    private static void PR(Deque<String> jobQueue, String[][] frames, int[][] framesHistory)
    {
        //when there is still space in memory
        //iter represent next set of frames to be chosen from
        int iter = 1;
        while (jobQueue.size() > 0)
        {
            copyPrevFrame(frames, iter);

            //check if there is still free memory
            int lastFrame = frames.length - 1;
            if (frames[lastFrame][iter].equals("-1"))
            {
                break;
            }

            for (int x = 0; x < frames.length; ++x)
            {
                String frameVal = frames[x][iter];
                if (frameVal.equals("-1"))
                {
                    frames[x][iter] = jobQueue.removeFirst();
                    framesHistory[x][iter] = 0;
                    break;
                }
                else if (frameVal.equals(jobQueue.peek()))
                {
                    frames[x][iter] = jobQueue.removeFirst();
                    framesHistory[x][iter] = 1;
                    break;
                }
            }

            ++iter;
        }

        while (jobQueue.size() > 0)
        {
            copyPrevFrame(frames, iter);

            int pageHitFrame = findPageHit(frames, iter, jobQueue.peek());
            if (pageHitFrame != -1)
            {
                frames[pageHitFrame][iter] = jobQueue.removeFirst();
                framesHistory[pageHitFrame][iter] = 1;
            }
            else
            {
                int leastRecentFrame = getLeastRecentFrame(framesHistory, iter);
                frames[leastRecentFrame][iter] = jobQueue.removeFirst();
                framesHistory[leastRecentFrame][iter] = 0;
            }
            
            ++iter;
        }
    }

    private static int findPageHit(String[][] frames, int iter, String job)
    {
        int pageHit = -1;
        for (int x = 0; x < frames.length; ++x)
        {
            if (frames[x][iter].equals(job))
            {
                pageHit = x;
                break;
            }
        }

        return pageHit;
    }

    private static int getLeastRecentFrame(int[][] framesHistory, int iter)
    {
        int[] totals = new int[framesHistory.length];
        Arrays.fill(totals, -1);

        for (int x = 0; x < framesHistory.length; ++x)
        {
            for (int y = 0; y < iter; ++y)
            {
                if (framesHistory[x][y] >= 0)
                {
                    totals[x] = y;
                }
            }
        }

        int least = 0;
        for (int x = 1; x < totals.length; ++x)
        {
            if (totals[x] < totals[least])
            {
                least = x;
            }
        }

        return least;
    }

    private static void copyPrevFrame(String[][] frames, int iter)
    {
        for (int x = 0; x < frames.length; ++x)
        {
            frames[x][iter] = frames[x][iter - 1];
        }
    }
}