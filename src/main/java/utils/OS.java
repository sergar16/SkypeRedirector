package utils;

/**
 * Created by Serhii on 11/1/2015.
 */
public  final  class OS {

        private static String OS = null;
        public static String getOsName()
        {
            if(OS == null) { OS = System.getProperty("os.name"); }
            return OS;
        }
        public static boolean isWindows()
        {
            return getOsName().startsWith("Windows");
        }

       // public static boolean isUnix(){}
}
