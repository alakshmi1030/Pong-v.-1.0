/**
 * @(#)hockey.java
 *
 *
 * @author
 * @version 1.00 2011/8/5
 */
import java.applet.*;
import java.awt.*;
import java.util.*;

public class hockey extends Applet implements Runnable{

	private Image dbImage;
	private Graphics dbg;

	int x_pos = 250;		//denotes position of ball
	int y_pos = 250;		// "       "
    int xpos1 = 25;			//position of first block
    int ypos1 = 175;
    int xpos2 = 425;		//second block
    int ypos2 = 175;
    int radius = 20;

    int score1 = 0;			//scores of the two players
    int score2 = 0;

    int width = 25;			//width = x
    int length = 75;		//length = y

    Random r = new Random();

	int x_speed = r.nextInt(3) + 3;
	int y_speed = r.nextInt(3) + 3;
	int appletsize_x = 500;
	int appletsize_y = 500;

	String message1, message2,gameover;

	public void init()
	{
		setBackground(Color.black);
	}

	public void start()
	{
		Thread th = new Thread(this);
		th.start();
	}

	public void run()
	{
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		while(true)
		{
			//checks for paddle/board collisions
			if(xpos1 < 0)
			{
				xpos1 = 0;
			}
			else if(ypos1 < 0)
			{
				ypos1 = 0;
			}
			else if(ypos1 + length > 500)
			{
				ypos1 = 500 - length;
			}
			else if(xpos2 + width > 500)
			{
				xpos2 = 500 - width;
			}
			else if(ypos2 < 0)
			{
				ypos2 = 0;
			}
			else if(ypos2 + length > 500)
			{
				ypos2 = 500 - length;
			}
			else{}

			//checks for ball/board collisions and increments score
			if(x_pos > appletsize_x - radius)
			{
				x_pos = 250;
				y_pos = 250;
				score1++;
			}
			else if(x_pos < radius)
			{
				x_pos = 250;
				y_pos = 250;
				score2++;
			}
			if(score1 == 10)
			{
				gameover = "Player 1 wins! \n GAME OVER";
				x_speed = 0;
				y_speed = 0;

			}
			else if(score2 == 10)
			{
				gameover = "Player 2 wins! \n GAME OVER";
				x_speed = 0;
				y_speed = 0;
			}
			//checks for ball/paddle collisions
			else if(((x_pos - radius) <  xpos1 + width) && ((y_pos >= ypos1) && (y_pos <= ypos1 + length)))
			{
				x_speed = +2;
			}
			else if(((x_pos + radius) > xpos2) && ((y_pos >= ypos2) && (y_pos <= ypos2 + length)))
			{
				x_speed = -2;
			}

			x_pos += x_speed;

			if(y_pos > appletsize_y - radius)
			{
				y_speed = -1;
			}

			else if(y_pos < radius)
			{
				y_speed = +1;
			}

			y_pos += y_speed;


			repaint();
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException ex)
			{
			}
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}

	}

	public boolean keyDown(Event e, int key)
	{
		if(key == 119)
		{
			ypos1-=7;
		}
		/*
		else if(key == 97)
		{
			xpos1-=3;
		}
		*/
		else if(key == 115)
		{
			ypos1+=7;
		}
		/*
		else if(key == 100)
		{
			xpos1+=3;
		}

		else if(key == Event.LEFT)
		{
			xpos2-=3;
		}
		else if(key == Event.RIGHT)
		{
			xpos2+=3;
		}
		*/

		else if(key == Event.UP)
		{
			ypos2-=7;
		}
		else if(key == Event.DOWN)
		{
			ypos2+=7;
		}

		/*
		if(y_pos < 250)
		{
			ypos2 += -5*Math.random();
		}
		else if (y_pos > 250)
		{
			ypos2 += 5*Math.random();
		}
		*/
		else
		{
		}
		return(true);
	}

	public void paint(Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect(xpos1,ypos1,width,length);

		g.setColor(Color.blue);
		g.fillRect(xpos2,ypos2,width,length);

		g.setColor(Color.green);
      	g.fillOval(x_pos - radius,y_pos-radius,2*radius,2*radius);

      	Font text = new Font("Arial", Font.BOLD,16);
      	setFont(text);
      	g.setColor(Color.white);
      	g.drawString("Pong v. 1.0", 200,25);

      	message1 = "Player 1 Score: " +score1;
      	message2 = "Player 2 Score: " +score2;

      	g.drawString(message1,10,30);
      	g.drawString(message2,325,30);

		if((score1 == 10) || (score2 == 10))
		{
			g.drawString(gameover,175,250);
		}
	}

	 public void update(Graphics g)
     {
      	if(dbImage == null)
      	{
      		dbImage = createImage(this.getSize().width, this.getSize().height);
      		dbg = dbImage.getGraphics();
      	}

      	//clear screen
      	dbg.setColor(getBackground());
      	dbg.fillRect(0,0,this.getSize().width,this.getSize().height);

     	//draw elements
     	dbg.setColor(getForeground());
     	paint(dbg);

     	//draw Image on screen
     	g.drawImage(dbImage,0,0,this);
     }


}
