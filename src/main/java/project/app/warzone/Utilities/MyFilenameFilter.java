package project.app.warzone.Utilities;

import java.io.File;
import java.io.FilenameFilter;

public class MyFilenameFilter implements FilenameFilter {

    String d_initials;
    
    // constructor to initialize object
    public MyFilenameFilter(String p_initials)
    {
        this.d_initials = p_initials;
    }
    
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
