import javax.swing.SwingUtilities;

import View.MainFrame;


public class Main 
{
    public static void main(String[] args) 
    {   
        SwingUtilities.invokeLater(() -> 
        {
            new MainFrame();
        });

        /* 
         * 
         String jobString = "5 4 3 2 3 5 2";
         Model model = Control.initModel(jobString, 3);
         
         model.printFrames();
         model.printFramesHistor
         */
    }    
}