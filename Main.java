import Control.Control;
import Model.Model;



public class Main 
{
    public static void main(String[] args) 
    {   
       String jobString = "5 4 3 2 3 5 2";
       Model model = Control.initModel(jobString, 3);

       model.printFrames();
       model.printFramesHistory();
    }
     
}