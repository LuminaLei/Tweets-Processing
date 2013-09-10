import models.Parameter;
import controller.FileProcessor;

public class Processing {

    /**
     * @param args
     */

    public static void main(String[] args) {
        System.out.println("The system is working");
        Parameter para=new Parameter(args);
        FileProcessor filePro = new FileProcessor(para);
    }

}
