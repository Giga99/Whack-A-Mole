package igra;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Basta extends Panel implements Runnable {
	private int povrce, brojKoraka, brVrsta, brKolona;
	private Rupa[][] rupe;
	private boolean[][] rupeRade;
	private int intervalCekanja;
	private Thread nit;

	public Basta(int n, int m) {
		brVrsta = n;
		brKolona = m;
		setBackground(Color.GREEN);
		setLayout(new GridLayout(brVrsta, 1));
		rupeRade = new boolean[brVrsta][brKolona];
		rupe = new Rupa[brVrsta][brKolona];
		for (int i = 0; i < brVrsta; i++) {
			Panel panel = new Panel();
			for (int j = 0; j < brKolona; j++) {
				int a = i;
				int b = j;
				rupe[i][j] = new Rupa(this);
				rupeRade[i][j] = false;
				rupe[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						rupe[a][b].zgaziRupu();
					}
				});
				panel.add(rupe[i][j]);
			}
			add(panel);
		}
		povrce = 100;
	}

	public int getBrojKoraka() {
		return brojKoraka;
	}

	public void setBrojKoraka(int brojKoraka) {
		this.brojKoraka = brojKoraka;
		for (int i = 0; i < brVrsta; i++) {
			for (int j = 0; j < brKolona; j++) {
				rupe[i][j].setUkBrKoraka(brojKoraka);
			}
		}
	}

	public synchronized int getPovrce() {
		return povrce;
	}

	public synchronized void smanjiPovrce() {
		if(povrce > 0) povrce--;
		Igra.getIgra().postaviPovrce();
		if(povrce == 0) Igra.getIgra().krajIgre();
	}

	public synchronized void pokreni() {
		nit = new Thread(this);
		povrce = 100;
		nit.start();
	}

	public synchronized void zaustavi() {
		for (int i = 0; i < brVrsta; i++) {
			for (int j = 0; j < brKolona; j++) {
				rupe[i][j].zaustavi();
				rupeRade[i][j] = false;
			}
		}
		if(nit != null) nit.interrupt();
	}

	public synchronized void setIntervalCekanja(int intervalCekanja) {
		this.intervalCekanja = intervalCekanja;
	}

	public synchronized void obavesti(Rupa rupa) {
		for (int i = 0; i < brVrsta; i++) {
			for (int j = 0; j < brKolona; j++) {
				if(rupe[i][j] == rupa) rupeRade[i][j] = false;
			}
		}
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				int randN = (int) (Math.random() * brVrsta);
				int randM = (int) (Math.random() * brKolona);
				
				while(rupeRade[randN][randM]) {
					randN = (int) (Math.random() * brVrsta);
					randM = (int) (Math.random() * brKolona);
				}
				
				int randZiv = (int)(Math.random()*100);

				if(randZiv <=50) {
					Krtica krtica = new Krtica(rupe[randN][randM]);
					rupe[randN][randM].setZivotinja(krtica);
				} else {
					Jez jez = new Jez(rupe[randN][randM]);
					rupe[randN][randM].setZivotinja(jez);
				}
				rupe[randN][randM].stvori();
				rupe[randN][randM].pokreni();
				rupeRade[randN][randM] = true;

				Thread.sleep(intervalCekanja);
				intervalCekanja = intervalCekanja - intervalCekanja / 100;
			}

		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
}
