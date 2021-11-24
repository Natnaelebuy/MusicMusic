// Java program to play an Audio
// file using Clip Object
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SimpleAudioPlayer
{

	private static final boolean Playing = false;
	// to store current position
	Long currentFrame;
	Clip clip;
	
	// current status of clip
	String status;
	
	AudioInputStream audioInputStream;
	static String filePath;
	private static String song;

	// constructor to initialize streams and clip
	public SimpleAudioPlayer()
		throws UnsupportedAudioFileException,
		IOException, LineUnavailableException
	{
		// create AudioInputStream object
		audioInputStream =
				AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
		
		// create clip reference
		clip = AudioSystem.getClip();
		
		// open audioInputStream to the clip
		clip.open(audioInputStream);
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public static void main(String[] args)
	{
		try
		{
			filePath = "./song/DJ Khaled- Do You Mind.wav";
			SimpleAudioPlayer audioPlayer =
							new SimpleAudioPlayer();
			
			audioPlayer.play();
			Scanner sc = new Scanner(System.in);
			
			while (true)
			{
				System.out.println("1. pause");
				System.out.println("2. resume");
				System.out.println("3. restart");
				System.out.println("4. stop");
				System.out.println("5. Jump to specific time");
				System.out.println("6, search");
				System.out.println("7, Play");
				System.out.println("8, favorte");
				int c = sc.nextInt();
				audioPlayer.gotoChoice(c);
				if (c == 4)
				break;
			}
			sc.close();
		}
		
		catch (Exception ex)
		{
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		
		}
	}
	
	// Work as the user enters his choice
	
	private void gotoChoice(int c)
			throws IOException, LineUnavailableException, UnsupportedAudioFileException
	{
		switch (c)
		{
			case 1:
				pause();
				break;
			case 2:
				resumeAudio();
				break;
			case 3:
				restart();
				break;
			case 4:
				stop();
				break;
			case 5:
				System.out.println("Enter time (" + 0 +
				", " + clip.getMicrosecondLength() + ")");
				Scanner sc = new Scanner(System.in);
				long c1 = sc.nextLong();
				jump(c1);
				break;
			case 6:	
                System.out.println("-->Search by title<--");
                Search();
                break;
			case 7:
                System.out.println("-->Play<--");
                play(song);
                break;
			case 8:
			    System.out.println("-->favorte<--");
				Search();
				break;	
		}
	
	}
	
	private void play(String song2) {
		
	}

	// Method to play the audio
	public void play(){
		//start the clip
		clip.start();
		
		status = "play";
	}
	
	// Method to pause the audio
	public void pause(){
		if (status.equals("paused"))
		{
			System.out.println("audio is already paused");
			return;
		}
		this.currentFrame =
		this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}
	
	// Method to resume the audio
	public void resumeAudio() throws UnsupportedAudioFileException,
								IOException, LineUnavailableException
	{
		if (status.equals("play"))
		{
			System.out.println("Audio is already "+
			"being played");
			return;
		}
		clip.close();
		resetAudioStream();
		clip.setMicrosecondPosition(currentFrame);
		this.play();
	}
	
	// Method to restart the audio
	public void restart() throws IOException, LineUnavailableException,
											UnsupportedAudioFileException
	{
		clip.stop();
		clip.close();
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
	}
	
	// Method to stop the audio
	public void stop() throws UnsupportedAudioFileException,
	IOException, LineUnavailableException
	{
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}
	
	// Method to jump over a specific part
	public void jump(long c) throws UnsupportedAudioFileException, IOException,
														LineUnavailableException
	{
		if (c > 0 && c < clip.getMicrosecondLength())
		{
			clip.stop();
			clip.close();
			resetAudioStream();
			currentFrame = c;
			clip.setMicrosecondPosition(c);
			this.play();
		}
	}
	
	// Method to reset audio stream
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
											LineUnavailableException
	{
		audioInputStream = AudioSystem.getAudioInputStream(
		new File(filePath).getAbsoluteFile());
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public static void Search() {
        String[] Songlist;
        Songlist = new String[5];
          Songlist[0] = "./song/DJ Khaled- Do You Mind";
          Songlist[1] = "./song/DJ Khaled-Hold you down.wav";
          Songlist[2] = "./song/Ed Sheeran - Shape of You.wav";
          Songlist[3] = "./song/Migos - Bad and Boujee (feat. Lil Uzi Vert).wav";
          Songlist[4] = "./song/Travis Scott - Niagara Falls Ft 21 Savage.wav";

        for(int k = 0; k < Songlist.length; k++)
        {
            int a = k+1;
            System.out.println(a + Songlist[k]);
        }        
        System.out.println("Please choose a song");
        Scanner Selection = new Scanner(System.in);
        int Choice;
        Choice = Selection.nextInt() - 1;
        song = Songlist[Choice];
        
    }

}

