package Controller;

import Model.Model;

public class Control 
{
    public static Model initModel(String jobString, int totalFrames)
    {
        return new Model(jobString, totalFrames);
    }
}
