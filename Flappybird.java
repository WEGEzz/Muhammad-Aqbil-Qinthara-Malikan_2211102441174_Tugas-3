import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Flappybird extends Actor
{
    private double g = 1; 
    private int y = 300;
    private boolean haspressed = false;
    private boolean isalive = true;
    private boolean isacross = false;
    private boolean hasaddscore = false; //Nilai awal artinya belum ditambah terlalu banyak
    public Flappybird(){
        GreenfootImage image = getImage();
        image.scale(50, 40);
    }
    public void act() 
    {
        //Jika menekan spasi, koordinat y akan berkurang dan terbang ke atas
        if(spacePressed()){
            g=-2;//Semakin rendah angkanya, semakin tinggi terbangannya
        }
        g +=0.1; //Nilai g meningkat 0,1 setiap saat
        y += g; //Dengan cara ini, nilai y tidak berubah dengan kecepatan konstan, tetapi menjadi semakin besar
        setLocation(getX(), (int)(y));
        //Jika menabrak pipa maka game over
        if(isTouchpipe()){
            isalive = false;
        }
        
        //setelah jatuh atau menabrak pipa maka flappybird hilang
        if(!isalive){
            getWorld().addObject(new Gameover(), 300, 200);
            getWorld().removeObject(this);
        }
       
        if(!hasaddscore && isacross && isalive){
            Score.add(1);
        }
        hasaddscore = isacross;
    }
    //Mengembalikan apabila spasi ditekan
    public boolean spacePressed(){
        boolean pressed = false;
        if(Greenfoot.isKeyDown("space")){
            if(!haspressed){//Jika belum melepaskan spasi, jangan kembali true
                pressed = true;
            }
            haspressed = true; //spasi telah ditekan
        }else{
            haspressed = false;
        }
        return pressed;
    }
    
    public boolean isTouchpipe(){
        isacross = false;
        for(Pipe pipe : getWorld().getObjects(Pipe.class)){
            if(Math.abs(pipe.getX() - getX()) < 60 ){
                if(Math.abs(pipe.getY() + 30 - getY()) > 37){
                    isalive = false;
                }
                isacross = true;
            }
        }
        return !isalive;
    }
}
