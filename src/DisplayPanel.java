import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Notes to self: add a button on the end screen that sends you back to the menu

public class DisplayPanel extends JPanel implements MouseListener, KeyListener, ActionListener, MouseMotionListener {
    private Point mpos;
    private int mousex;
    private int mousey;
    private Timer mainT;
    private int mode;
    private int modequeue;
    private Hitbox mousehit;
    private Hitbox players2;
    private Hitbox player1;
    private Hitbox player2;
    private Hitbox ball;
    private int p1x;
    private int p1y;
    private int p2x;
    private int p2y;
    private boolean inital = true;
    private Timer mode_0;
    private int startside;
    private int velocityx; //ball
    private int startingvx;
    private int velocityy;
    private int v1;  //sliders
    private int v2;
    private Timer time;
    private int ballx;
    private int bally;
    private boolean scored;
    private boolean start;
    private Scoreboard scoreboard;
    private Timer cooldown;
    private Timer blink;
    private boolean blinking;
    private int blinks;
    private int hits;
    private Timer slower; //for testing I think
    private Timer slower2; //just for velocityy cuz why not
    private int speedinc;
    private boolean gameend;
    private Timer delay;
    private int delay_ram;
    private int pointsneeded;
    private boolean hitdisplay;
    private Hitbox menub;

    public DisplayPanel(){
        addMouseListener(this);
        addKeyListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        requestFocusInWindow();
        pointsneeded = 1;
        mode = 3;
        mainT = new Timer(10, this);
        time = new Timer(10, this);
        cooldown = new Timer(2500, this);
        blink = new Timer(400, this);
        slower = new Timer(300, this);
        slower2 = new Timer(15, this);
        delay = new Timer(1000, this);
        int ram = (int) (Math.random() * 500) + 500;
        mode_0 = new Timer(ram, this);
        this.mousehit = new Hitbox(mousex, mousey, 1, 1);  //hitbox for mouse
    }
    @Override
    public void paint(Graphics g){
        if (inital){ // only runs once (for hitboxes create the objects here)
            ballx = getWidth() / 2 - 15;
            bally = getHeight() / 2 - 15;
            startingvx = 5;
            velocityx = startingvx;
            p1x = 90;
            p1y = getHeight() / 2 - 50;
            p2x = getWidth() - 100;
            p2y = getHeight() / 2 - 50;
            delay_ram = 5;
            this.players2 = new Hitbox(getWidth() / 4, getHeight() / 2, 300, 150); // button for 1 player
            this.player1 = new Hitbox(p1x, p1y, 10, 100);
            this.player2 = new Hitbox(p2x, p2y, 10, 100);
            this.ball = new Hitbox(ballx, bally, 30, 30);
            this.menub = new Hitbox(getWidth() / 2 - 150, getHeight() / 2 + 100 - 35, 300, 70);
            this.scoreboard = new Scoreboard();
            if (getStartside()){
                startside = 1;
            }else{
                startside = -1;
            }
            velocityx *= startside;
            mainT.start();
            time.start();
            slower.start();
            slower2.start();
            inital = false;
        }
        if (mode == 0){
            mode0(g);
        }
        if (mode == 1){
            mode1(g);
        }
        if (mode == 2){
            mode2(g);
        }
        if (mode == 3){
            mode3(g);
        }
        if (hitdisplay){
            displayhit(g);
        }
    }
    public void displayhit(Graphics g){ //displays hitboxes
        g.setColor(Color.RED);
        if (players2.getvisibility()){
            g.drawRect(players2.getx(), players2.gety(), players2.getWidth(), players2.getHeight());
        }
        if (player1.getvisibility()){
            g.drawRect(player1.getx(), player1.gety(), player1.getWidth(), player1.getHeight());
        }
        if (player2.getvisibility()){
            g.drawRect(player2.getx(), player2.gety(), player2.getWidth(), player2.getHeight());
        }
        if (ball.getvisibility()){
            g.drawRect(ball.getx(), ball.gety(), ball.getWidth(), ball.getHeight());
        }
        if (menub.getvisibility()){
            g.drawRect(getWidth() / 2 - 150, getHeight() / 2 + 100 - 35, 300, 70);
        }
    }
    public void mode0(Graphics g){ //fake ahh loading screen
        int ram;
        super.paintComponent(g);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        ram = g.getFontMetrics().stringWidth("Loading...");
        g.drawString("Loading...", getWidth() / 2 - ram / 2, getHeight() / 3);
    }
    public void mode1(Graphics g){ //menu
        int ram;
        super.paintComponent(g);
        g.setColor(new Color(70, 154, 223, 255));
        g.fillRect(getWidth() / 4, getHeight() / 2, 300, 150);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        ram = g.getFontMetrics().stringWidth("2 players");
        g.drawString("2 players", getWidth() / 4 + 150 - ram / 2, getHeight() / 2 + 80);

    }
    public void mode2(Graphics g){ //displays what happens when game starts
        int ram;
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        ram = g.getFontMetrics().stringWidth(mousex + " " + mousey);
        g.drawString(String.valueOf(mousex) + " " + String.valueOf(mousey), getWidth() / 2 - ram / 2, getHeight() / 150 + 7);
        ram = g.getFontMetrics().stringWidth(ballx + " " + bally);
//        g.drawString(String.valueOf(ballx) + " " + String.valueOf(bally), getWidth() / 2 - ram / 2, getHeight() / 150 + 7);
        ram = g.getFontMetrics().stringWidth(scoreboard.getp1() + " " + scoreboard.getp2());
        g.drawString(scoreboard.getp1() + " " + scoreboard.getp2(), getWidth() / 2 - ram / 2, getHeight() / 150 + 20);
        g.drawLine(getWidth() / 2, getHeight(), getWidth() / 2, 0);
        g.setColor(new Color(70, 154, 223, 255));
        g.fillRect(p1x - 20, p1y, 30, 100);
        g.setColor(new Color(237, 80, 80, 255));
        g.fillRect(p2x, p2y, 30, 100);
        g.setColor(Color.black);
        if (!blinking){
            g.fillOval(ballx, bally, 30, 30);
        }
        if (delay_ram > -1 && delay.isRunning()){
            g.fillRect(getWidth() / 2 - 50, getHeight() / 2 - 50, 100, 100);
            g.setColor(Color.WHITE);
            g.fillRect(getWidth() / 2 - 40, getHeight() / 2 - 40, 80, 80);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            ram = g.getFontMetrics().stringWidth(String.valueOf(delay_ram));
            g.drawString(String.valueOf(delay_ram), getWidth() / 2 - ram / 2, getHeight() / 2 + 8);
        }
    }
    public void mode3(Graphics g){
        int ram;
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        ram = g.getFontMetrics().stringWidth(String.valueOf("Score: " + scoreboard.getp1() + " - " + scoreboard.getp2()));
        g.drawString("Score: " + scoreboard.getp1() + " - " + scoreboard.getp2(), getWidth() / 2 - ram / 2, getHeight() / 2 - 108);
        if (scoreboard.getp1() > scoreboard.getp2()){
            ram = g.getFontMetrics().stringWidth(String.valueOf("Player 1 wins!"));
            g.drawString("Player 1 wins!", getWidth() / 2 - ram / 2, getHeight() / 2 - 8);
        }else if (scoreboard.getp1() < scoreboard.getp2()){
            ram = g.getFontMetrics().stringWidth(String.valueOf("Player 2 wins!"));
            g.drawString("Player 2 wins!", getWidth() / 2 - ram / 2, getHeight() / 2 - 8);
        }
        g.setColor(Color.white);
        g.fillRect(getWidth() / 2 - 150, getHeight() / 2 + 100 - 35, 300, 70);
        g.setColor(Color.black);
        g2.setStroke(new BasicStroke(2));
        border(getWidth() / 2 - 150, getHeight() / 2 + 100 - 35, 300, 70, 10, g2);
        g.setColor(Color.black);
        ram = g.getFontMetrics().stringWidth("Return to menu");
        g.drawString("Return to menu", getWidth() / 2 - ram / 2, getHeight() / 2 + 145 - 35);
        g2.setStroke(new BasicStroke(1));
    }
    public void pscored(){ // when a player scores
        if (scored){
            if (ballx < getWidth() / 2){
                scoreboard.addp2();
            }
            if (ballx > getWidth() / 2){
                scoreboard.addp1();
            }
            ballx = (getWidth() / 2) - 15;
            bally = (getHeight() / 2) - 15;
            velocityx = startingvx;
            if (getStartside()){
                startside = 1;
            }else{
                startside = -1;
            }
            velocityx *= startside;
            velocityy = 0;
            cooldown.start();
            blink.start();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) { //timers its a mess ik
        if (e.getSource() == mainT){
            mousehit.changehitpos(mousex, mousey);
            ballLogic();
            repaint();
            displayedornot();
            ballcollision();
            checkmode3();
        }
        if (e.getSource() == mode_0){
            mode = modequeue;
            delay.start();
            displayedornot();
            mode_0.stop();
        }
        if (e.getSource() == time){
            if(!gameend){
                p1y += v1;
            }
            player1.changehitpos(p1x, p1y);
            if(p1y < 0){
                v1 = 0;
                p1y = 0;
            }
            if (p1y > getHeight() - 100){
                v1 = 0;
                p1y = getHeight() - 100;
            }
            if (!gameend){
                p2y += v2;
            }
            player2.changehitpos(p2x, p2y);
            if(p2y < 0){
                v2 = 0;
                p2y = 0;
            }
            if (p2y > getHeight() - 100){
                v2 = 0;
                p2y = getHeight() - 100;
            }
            if (mode == 2 && start && !scored && !gameend){
                ballx += velocityx;
                ball.changehitpos(ballx, bally);
            }
        }
        if (e.getSource() == cooldown){
            scored = false;
            cooldown.stop();
        }
        if (e.getSource() == blink){
            if (!blinking){
                blinking = true;
                blinks++;
            }else {
                blinking = false;
                blinks++;
            }
            if(blinks >= 6){
                blinks = 0;
                blink.stop();
            }
        }
        if (e.getSource() == slower){ // for testing
            System.out.println(mode);
            System.out.println(mousehit.getvisibility() && mousehit.getHitbox().intersects(menub.getHitbox()));
        }
        if (e.getSource() == slower2){
            if (mode == 2 && start && !scored && !gameend){
                bally += velocityy;
            }
        }
        if (e.getSource() == delay){
            if (delay_ram < 1){
                start = true;
                delay.stop();
            }else{
                delay_ram--;
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) { //movement for the paddles
        int ram = e.getKeyCode();
        if (ram == KeyEvent.VK_A){
            v1 = -5;
        }
        if (ram == KeyEvent.VK_D){
            v1 = 5;
        }
        if (ram == KeyEvent.VK_LEFT){
            v2 = -5;
        }
        if (ram == KeyEvent.VK_RIGHT){
            v2 = 5;
        }
        player1.changehitpos(p1x, p1y);
        player2.changehitpos(p2x, p2y);
        if (ram == KeyEvent.VK_J){
            if (hitdisplay){
                hitdisplay = false;
            }else{
                hitdisplay = true;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) { //a bug turned feature
//        int ram = e.getKeyCode();
//        if (ram == KeyEvent.VK_A){
//            v1 = 0;
//        }
//        if (ram == KeyEvent.VK_D){
//            v1 = 0;
//        }
//        if (ram == KeyEvent.VK_LEFT){
//            v2 = 0;
//        }
//        if (ram == KeyEvent.VK_RIGHT){
//            v2 = 0;
//        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (mousehit.getvisibility() && mousehit.getHitbox().intersects(players2.getHitbox())){
            mode = 0;
            delay_ram = 5;
            modequeue = 2;
            displayedornot();
            mode_0.start();
        }
        if (mousehit.getvisibility() && mousehit.getHitbox().intersects(menub.getHitbox())){
            mode = 1;
            gameend = false;
            start = false;
            hits = 0;
            displayedornot();
            reset();
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseDragged(MouseEvent e) {

    }
    @Override
    public void mouseMoved(MouseEvent e) {
        mpos = e.getPoint();
        mousex = (int) mpos.getX();
        mousey = (int) mpos.getY();
    }
    public void displayedornot(){
        if (mode == 0){
            ball.setvisibilityfalse();
            mousehit.setvisibilityfalse();
            players2.setvisibilityfalse();
            player1.setvisibilityfalse();
            player2.setvisibilityfalse();
            menub.setvisibilityfalse();
        }else{
            if (mode == 1){
                ball.setvisibilityfalse();
                mousehit.setvisibilitytrue();
                players2.setvisibilitytrue();
                player1.setvisibilityfalse();
                player2.setvisibilityfalse();
                menub.setvisibilityfalse();
            }
        }
        if (mode == 2){
            ball.setvisibilitytrue();
            mousehit.setvisibilityfalse();
            players2.setvisibilityfalse();
            player1.setvisibilitytrue();
            player2.setvisibilitytrue();
            menub.setvisibilityfalse();
        }
        if (mode == 3){
            ball.setvisibilityfalse();
            mousehit.setvisibilitytrue();
            player1.setvisibilityfalse();
            player2.setvisibilityfalse();
            players2.setvisibilityfalse();
            menub.setvisibilitytrue();
        }
    }
    public boolean getStartside(){
        int ram = (int) (Math.random() * 2);
        if (ram == 0){
            return false;
        }
        if (ram == 1){
            return true;
        }
        return false;
    }
    public void ballLogic(){ //just checks if the ball scores or nah
        if (ballx > getWidth() - 30 || ballx < 0){
            scored = true;
            pscored();
        }
    }
    public void ballcollision(){ //checks for collisions for the ball
        int ram = 0;
        int ram1 = 0;
        int ram2 = 0;
        if (ball.getHitbox().intersects(player1.getHitbox())){
            player1.setvisibilityfalse();
            player2.setvisibilitytrue();
            ballx = 100;
            ram = (int) (Math.random() * 2);
            ram2 = ((ball.gety() + (ball.getHeight() / 2)) - (player1.gety() + (player1.getHeight() / 2))) / 30;
//            ram1 = (int) (Math.random() * 2);
            ram1 += ram2;
            if (ram == 0){
                ram1 *= -1;
            }
            velocityy = ram1;
            velocityx *= -1;
            hits++;
            if (((int) Math.sqrt(hits) * 2) > speedinc){
                speedinc = (int) Math.sqrt(hits) * 2;
                if (velocityx > 0){
                    velocityx++;
                }else{
                    velocityx--;
                }
            }
        }
        if (ball.getHitbox().intersects(player2.getHitbox())){
            player1.setvisibilitytrue();
            player2.setvisibilityfalse();
            ballx = getWidth() - 130;
            ram = (int) (Math.random() * 2);
            ram2 = ((ball.gety() + (ball.getHeight() / 2)) - (player2.gety() + (player2.getHeight() / 2))) / 10;
//            ram1 = (int) (Math.random() * 2);
            ram1 += ram2;
            if (ram == 0){
                ram1 *= -1;
            }
            velocityy = ram1;
            velocityx *= -1;
            hits++;
            if (((int) Math.sqrt(hits) * 2) > speedinc){
                speedinc = (int) Math.sqrt(hits) * 2;
                if (velocityx > 0){
                    velocityx++;
                }else{
                    velocityx--;
                }
            }
        }
        if (ball.gety() < 0){
            bally = 1;
            velocityy *= -1;
        }
        if (ball.gety() > getHeight() - ball.getHeight()){
            bally = getHeight() - ball.getHeight() - 1;
            velocityy *= -1;
        }
    }
    public void checkmode3(){
        if (scoreboard.getp1() > pointsneeded && (scoreboard.getp1() - scoreboard.getp2()) > 1){
            gameend = true;
            mode = 3;
        }
        if (scoreboard.getp2() > pointsneeded && (scoreboard.getp2() - scoreboard.getp1()) > 1){
            gameend = true;
            mode = 3;
        }
    }
    public void border(int x, int y, int w, int h, int depth, Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        int ram = depth;
        g.setColor(Color.black);
        g2.setStroke(new BasicStroke(2));
        for (int i = 0; i < depth; i++){
            g.drawRect(x - i / 2, y - i / 2, w + i, h + i);
        }
    }
    public void reset(){
        ballx = getWidth() / 2 - 15;
        bally = getHeight() / 2 - 15;
        startingvx = 5;
        velocityx = startingvx;
        p1x = 90;
        p1y = getHeight() / 2 - 50;
        p2x = getWidth() - 100;
        p2y = getHeight() / 2 - 50;
        delay_ram = 5; // for testing
        speedinc = 0;
        blinks = 0;
        v1 = 0;
        v2 = 0;
        scoreboard.reset();
    }
}
