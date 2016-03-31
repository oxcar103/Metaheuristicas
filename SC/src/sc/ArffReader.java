 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;


/**
 *
 * @author oxcar103
 */
public class ArffReader{
   private BufferedReader reader = null;
    
    public Instances getData(String pathname){
        Instances readData = null;
        
        try {
            reader = new BufferedReader(new FileReader(pathname));
            
            readData = new Instances(reader);
            
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArffReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArffReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return readData;
    }
}