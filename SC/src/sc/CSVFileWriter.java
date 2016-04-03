/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sc;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Lothar
 */
public class CSVFileWriter {
   
   public static void CSVFileWriter(String sFileName, ArrayList<Double> suc_r, ArrayList<Double> red_r, ArrayList<Double> time){
       String msg;
       Double suc_m = 0.0, red_m = 0.0, time_m = 0.0;
       int size = suc_r.size();
       
	try {
	    FileWriter writer = new FileWriter(sFileName);
		 
            writer.append("Nombre");
	    writer.append(',');
	    writer.append("Tasa_acierto");
	    writer.append(',');
	    writer.append("Tasa_error");
            writer.append(',');
            writer.append("Time");
            writer.append('\n');
            
            for(int i = 0; i < size; i++){
                suc_m += suc_r.get(i);
                red_m += red_r.get(i);
                time_m += time.get(i);
                
                msg = "Partición " + ((i/2) + 1) + "-" + ((i%2) + 1);
                writer.append(msg);
                writer.append(',');
                writer.append(suc_r.get(i).toString());
                writer.append(',');
                writer.append(red_r.get(i).toString());
                writer.append(',');
                writer.append(time.get(i).toString());
                writer.append('\n');
            }

            //suc_m /= size * 1.0;
            //red_m /= size * 1.0;
            //time_m /= size * 1.0;
            
            writer.append("Media");
            writer.append(',');
            writer.append(suc_m.toString());
            writer.append(',');
            writer.append(red_m.toString());
            writer.append(',');
            writer.append(time_m.toString());
            writer.append('\n');
			
	    //generate whatever data you want
			
	    writer.flush();
	    writer.close();
	} catch(IOException e) {
	     e.printStackTrace();
	} 
    }
}
