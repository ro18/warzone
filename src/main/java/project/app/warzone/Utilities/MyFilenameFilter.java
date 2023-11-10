package project.app.warzone.Utilities;

import java.io.File;
import java.io.FilenameFilter;

/**
 * This class is used to filter filenames 
 */
public class MyFilenameFilter implements FilenameFilter {

    String d_initials;
    
    /**
     * constructor to initialize object
     * 
     * @param p_initials            storing filename
     */
    public MyFilenameFilter(String p_initials)
    {
        System.out.println("current"+System.getProperty("user.dir"));
        this.d_initials = p_initials;
    }
    
    
    /** 
     * @param p_dir
     * @param p_name
     * @return boolean
     */
    // overriding the accept method of FilenameFilter
    // interface
    public boolean accept(File p_dir, String p_name)
    {
        String[] fileName = p_name.split("\\."); 

        // for(String a : fileName){
        //     System.out.println(a);
        // }
        return (fileName[0].equals(d_initials) && fileName[1].equals("map"));
    }
    
}
