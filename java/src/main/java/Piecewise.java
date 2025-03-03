import java.io.*;

public class Piecewise {
    double[][] readings;
    int step;
    String filename;

    private String genFileName(int core){
        return filename.replace(".txt", "-core-" + String.format("%02d",core) + ".txt");
    }

    private double[] PiecewiseInterpolation(int x1, int x2, double y1, double y2){
        double numerator = y2 - y1;
        double denominator = x2 - x1;

        double m = numerator / denominator;
        double b = y1 - (m * x1);

        return new double[]{m, b};
    }

    public void linearInterpolation (){
        for (int i = 0; i < readings.length; i++){
            String coreFileName = genFileName(i);
            
            try{
                FileWriter out =  new FileWriter(new File(coreFileName));
                for(int j = 0; j < readings[i].length-1; j++){
                    double[] data = PiecewiseInterpolation(
                        j * step, (j+1) * step, readings[i][j], readings[i][j+1]); 
    
                    out.append(String.format("%6d <= x <= %6d ; y = %10.4f + %10.4f x ; interpolation\n",
                    (j * step),  (j+1) * step, data[1], data[0]));
                }
                out.close();
            }catch(IOException ex){
                System.err.println("Error creating output file for core " + i
                + ex.getMessage());
            }

            
        }
    }
}
