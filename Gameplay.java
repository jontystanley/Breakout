package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener,ActionListener
{
	private boolean play=false;
	private int score=0;
    private int totalBricks=40;
	private Timer timer;
	private int delay=8;

	private int playerX=310;
	private int ballposX=120;
	private int ballposY=350;
	private int ballXdir=-2;
	private int ballYdir=-4;


	private int max=0;
	
	private MapGenerator map;

	public Gameplay()
{
	map=new MapGenerator(5,8);
	addKeyListener(this);
	setFocusable(true);
	setFocusTraversalKeysEnabled(true);
	timer = new Timer(delay,this);
	timer.start();
}

       public void paint(Graphics g)
{	//background
	g.setColor(Color.black);
	g.fillRect(1,1,692,592);
	
	//map
	
	map.draw((Graphics2D)g);
	
	//borders
	
	g.setColor(Color.blue);
	g.fillRect(0,0,3,592);
	g.fillRect(0,0,692,3);
	g.fillRect(691,0,10,592);
	
	
	//scores
	
	g.setColor(Color.white);
	g.setFont(new Font("serif",Font.BOLD,25));
	g.drawString(""+score, 590, 30);
	
	
	//board
	
	g.setColor(Color.green);
	g.fillRect(playerX,550,100,8);
	
	//ball
	g.setColor(Color.yellow);
	g.fillOval(ballposX,ballposY,20,20);
	
	
	if(ballposY>570||score==400) {
		play=false;
		ballXdir=0;
		ballYdir=0;
		g.setColor(Color.RED);
		g.setFont(new Font("serif",Font.BOLD,30));
		g.drawString("Game Over!! , Score ="+score, 190, 300);
		
		if(score>max)
			max=score;
		g.setColor(Color.GRAY);
		g.drawString("HIGHEST SCORE: "+max,205,400);
		
		g.setColor(Color.BLUE);
		g.setFont(new Font("serif",Font.BOLD,20));
		g.drawString("Press Enter to Restart", 235, 350);
		
	}
	g.dispose();
}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
				ballYdir=-ballYdir;
			}
			
			   for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX=j* map.brickWidth + 80;
						int brickY=i* map.brickHeight+ 50;
						int brickWidth=map.brickWidth;
						int brickHeight=map.brickHeight;
						
						Rectangle rect=new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect=rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score+=10;
							if(ballposX + 19 <=brickRect.x|| ballposX + 1 >=brickRect.x+brickRect.width) {
								ballXdir=-ballXdir;
								
							}else {
								ballYdir=-ballYdir;
							}
						}
						
					}
				}
				
			}
			
			ballposX+=ballXdir;
			ballposY+=ballYdir;
			//bounce left surface
			if(ballposX<0) {
				ballXdir=-ballXdir;
			}
			//bounce top surface
			if(ballposY<0) {
				ballYdir=-ballYdir;
			}
			//bounce right surface
			if(ballposX>670) {
				ballXdir=-ballXdir;
			}
		}
		
		repaint();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(playerX>=600) {
				playerX=600;
			}else {
				moveRight();
			}
		}
		
	
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(playerX<10) {
				playerX=10;
			}else {
				moveLeft();
			}
		}
		if(e.getKeyCode()== KeyEvent.VK_ENTER) {
			if(!play) {
				play=true;
				ballposX=120;
				ballposY=350;
				ballXdir=-2;
				ballYdir=-4;
				playerX=310;
				score=0;
				totalBricks=21;
				map=new MapGenerator(5,8);
				
				repaint();
				
			}
		}
		
	}
     public void moveRight() {
    	 play=true;
    	 playerX+=20;
	   }
     
     public void moveLeft() {
    	 play=true;
    	 playerX-=20;
    	 
     }
     
     
}
     
     

	