package Model;
/*
 * Represents the datatype used by the Control and View to communicate with each other
 */

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import Controller.PR_LRU;

public class Model 
{
    private Deque<String> jobQueue;
    private int totalFrames;
    private String[][] frames;
    private int[][] framesHistory;

    public Model(String jobString, int totalFrames) 
    {
        this.jobQueue = initJobQueue(jobString);
        this.totalFrames = totalFrames;
        this.frames = new String[totalFrames][jobQueue.size()];
        this.framesHistory = new int[totalFrames][jobQueue.size()];

        initFrames();
        pageReplacement();
    }

    private void pageReplacement()
    {
        PR_LRU.pageReplacement(jobQueue, frames, framesHistory);
    }

    private void initFrames()
    {
        for (String[] arr : this.frames)
        {
            Arrays.fill(arr, "");
        }

        /*  Legend:
         *  -1  - no change
         *  0   - page fault
         *  1   - page hit
         */
        for (int[] arr : this.framesHistory)
        {
            Arrays.fill(arr, -1);
        }
    }

    private Deque<String> initJobQueue(String jobString)
    {
        String[] parts = jobString.split(" ");

        Deque<String> jobQueue = new ArrayDeque<>();
        for (String job : parts)
        {
            jobQueue.add(job);
        }

        return jobQueue;
    }

    public String[][] getFrames()
    {
        return frames;
    }

    public int[][] getFramesHistory()
    {
        return framesHistory;
    }

    public int getTotalFrames()
    {
        return totalFrames;
    }

    public void printFrames()
    {
        System.out.println("Frames:");
        for (String[] arr : frames) 
        {
            for (String str : arr)
            {
                System.out.print(str + "\t");
            }
            System.out.println();
        }
    }

    public void printFramesHistory()
    {
        System.out.println("Frames History:");
        for (int[] arr : framesHistory) 
        {
            for (int num : arr)
            {
                System.out.print(num + "\t");
            }
            System.out.println();
        }
    }
}
