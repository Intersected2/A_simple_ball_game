public class Scoreboard {
    private int p1;
    private int p2;
    public Scoreboard(){

    }
    public void reset(){
        p1 = 0;
        p2 = 0;
    }
    public void addp1(){
        p1++;
    }
    public void addp2(){
        p2++;
    }
    public int getp1(){
        return p1;
    }
    public int getp2(){
        return p2;
    }
}
