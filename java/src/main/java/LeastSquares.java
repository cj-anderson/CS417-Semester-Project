import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LeastSquares {
    private double[][] readings;
    private int step;
    private String filename;

    
    public LeastSquares(double[][] read, int s, String f){
        this.readings = read;
        this.step = s;
        this.filename = f;
    }

    //Generate filename to be found, or, if file doesn't exist, to be made.
    private String genFileName(int core){
        return filename.replace(".txt", "-core-" + String.format("%02d",core) + ".txt");
    }

    public void linearInterpolationLoop (){
        //Loop through each core.
        for (int i = 0; i < readings.length; i++){
            String coreFileName = genFileName(i);
            
            FileWriter out = null;

            try{
                /** Check and see if the file was made in the piecewise interpolation step.
                 *  If the file exists, open it and append to it.
                 *  If not, create it and write to it.
                 */
                File outFile = new File(coreFileName);

                if(outFile.exists()){
                    out = new FileWriter(coreFileName, true);
                }
                else{
                    out = new FileWriter(new File(coreFileName));
                }

                double[] ls = LeastSquaresApproximation(step, readings[i]);

                out.append(String.format("%6d <= x <= %6d ; y = %10.4f + %10.4f x ; least-squares \n", 
                0, (readings[i].length - 1) * step, ls[1], ls[0]));
                
                out.close();
            }catch(IOException ex){
                //Handle IO Exceptions.
                System.err.println("Error creating output file for core " + i
                + ex.getMessage());
            }
        }
    }

    //Perform Least-Squares Approximation for a core using the augmented matrix form, shown below. 
    //Then, store the result in the core's output file.  
    //[a1  a2 | a3]
    //[b1  b2 | b3]
    private double[] LeastSquaresApproximation(int step, double[] arr){
        double a1 = (double) arr.length;
        double a2 = genA2(step, arr);
        double a3 = genA3(step, arr);

        double b1 = a2;
        double b2 = genB2(step, arr);
        double b3 = genB3(step, arr);

        // find determinants for:
        // the initial matrix,
        // the slope, and the intercept.
        double det = (a1 * b2) - (a2 * b1);
        double det_slope = (a1 * b3) - (a3 * b1);
        double det_int = (a3 * b2) - (a2 * b3);
        

        if (det == 0){
            throw new ArithmeticException("Matrix is singular. Check for errors in data.");
        }

        //Use the determinants to find slope and intercept.
        double m = (double) det_slope/det;
        double b = (double) det_int/det;

        return new double[]{m,b};
    }

    //generate the following parts of the 
    private double genA2(int step, double[] arr){
        double  sum = 0;
        for (int i = 0; i < arr.length; i++){
            sum += i * step;
        }
        return sum;
    }

    private double genA3(int step, double[] arr){
        double sum = 0;
        for (int i = 0; i < arr.length; i++){
            sum += arr[i] ;
        }
        return sum;
    }

    private double genB2(int step, double[] arr){
        double sum = 0;
        for (int i = 0; i < arr.length; i++){
            sum += (i * step) * (i * step);
        }
        return sum;
    }

    private double genB3(int step, double[] arr){
        double sum = 0;
        for (int i = 0; i < arr.length; i++){
            sum += arr[i] * (i * step);
        }
        return sum;
    }
}
