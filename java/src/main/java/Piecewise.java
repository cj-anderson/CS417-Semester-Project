import java.io.*;

public class Piecewise {
    private double[][] readings;
    private int step;
    private String filename;

    public Piecewise(double[][] read, int s, String f){
        this.readings = read;
        this.step = s;
        this.filename = f;
    }

    //Generate filename.
    private String genFileName(int core){
        return filename.replace(".txt", "-core-" + String.format("%02d",core) + ".txt");
    }

     /**
    * A function used to perform piecewise linear interpolation base on
    * two points, given by x1, x2, y1, and y2.
    *
    * @param x1,x2,y1,y2 used to perform linear interpolation
    */
    private double[] PiecewiseInterpolation(int x1, int x2, double y1, double y2){
        double numerator = y2 - y1;
        double denominator = x2 - x1;

        double m = numerator / denominator;
        double b = y1 - (m * x1);

        return new double[]{m, b};
    }

     /**
     * The main function used to use the functions of the Piecewise class.
     *
     * All data members are stored in a class object of this file.
     * @param readings An array of readings passed in from the main class.
     * @param step the length, in seconds, of time between readings.
     * @param filename the name of the input file, used to generate the output file names.
     */
    public void linearInterpolationLoop (){
        //Loop through each core.
        for (int i = 0; i < readings.length; i++){
            String coreFileName = genFileName(i);
            
            try{
                //Create a new output file.
                FileWriter out =  new FileWriter(new File(coreFileName));

                linearInterpolation(i, out);
                
                out.close();
            }catch(IOException ex){
                //Handle IO Exceptions.
                System.err.println("Error creating output file for core " + i
                + ex.getMessage());
            }
        }
    }

    public void linearInterpolation (int i, FileWriter out) throws IOException{
         //Perform the actual Piecewise Linear Interpolation.
         for(int j = 0; j < readings[i].length-1; j++){
            double[] data = PiecewiseInterpolation(
                j * step, (j+1) * step, readings[i][j], readings[i][j+1]); 

            out.append(String.format("%6d <= x <= %6d ; y = %10.4f + %10.4f x ; interpolation\n",
            (j * step),  (j+1) * step, data[1], data[0]));
        }
    }
}
