package igra;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class Rupa extends Canvas implements Runnable {
	private Basta basta;
	private Zivotinja zivotinja = null;
	private int ukBrKoraka, trenBrKoraka = 1;
	private Thread nit = null;
	private boolean udarena = false;
	
	public Rupa(Basta basta) {
		super();
		this.basta = basta;
		setBackground(new Color(102, 51, 0));
		setSize(70, 70);
	}
	
	public Basta getBasta() {
		return basta;
	}

	public Zivotinja getZivotinja() {
		return zivotinja;
	}

	public void setZivotinja(Zivotinja zivotinja) {
		this.zivotinja = zivotinja;
	}

	public int getUkBrKoraka() {
		return ukBrKoraka;
	}
	
	public void setUkBrKoraka(int ukBrKoraka) {
		this.ukBrKoraka = ukBrKoraka;
	}

	public int getTrenBrKoraka() {
		return trenBrKoraka;
	}
	
	public void setUdarena(boolean udarena) {
		this.udarena = udarena;
	}
	
	public synchronized void zgaziRupu() {
		if(zivotinja != null) {
			zivotinja.efekatUdareneZiv();
		}
	}
	
	@Override
	public synchronized void paint(Graphics g) {
		//setSize(basta.getWidth() / 5, basta.getHeight() / 5);
		if(zivotinja != null) zivotinja.crtaj(g);
	}
	
	public synchronized void stvori() {
		nit = new Thread(this);
		trenBrKoraka = 1;
		udarena = false;
	}
	
	public synchronized void pokreni() {
		if(nit != null) nit.start();
	}
	
	public synchronized void zaustavi() {
		if(nit != null) nit.interrupt();
	}
	
	public boolean pokrenuta() {
		return nit.isAlive();
	}

	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				if(trenBrKoraka == ukBrKoraka) {
					Thread.sleep(2000);
					zaustavi();
				}
				Thread.sleep(100);
				repaint();
				trenBrKoraka++;
			}
			
		} catch (InterruptedException e) {
			if(!udarena) zivotinja.efekatPobegleZiv();
			zivotinja = null;
			repaint();
			trenBrKoraka = 1;
			basta.obavesti(this);
		}
	}
}
