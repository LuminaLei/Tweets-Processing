package utils;

public class Lock {
    private  boolean islock=false;

    public  boolean getLock(){
       if(!islock){
           islock=true;
           return true;
       }else
           return false;
       
    }
    
    public  boolean releaseLock(){
        if(islock){
            islock=false;
            return true;
        }
        else return false;
        
    }
}
