package igra;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.text.StyledEditorKit.BoldAction;

public class Igra extends Frame {
	private Basta basta;
	private boolean radi = false;
	private Label povrce;
	private Button button;
	private Checkbox lako, srednje, tesko;
	private static Igra igra;

	private Igra() {
		super("Igra");
		igra = this;
		setSize(450, 450);

		basta = new Basta(4, 4);
		add(basta, BorderLayout.CENTER);

		Panel panel = new Panel(new GridLayout(3, 1));

		Panel tezinaPanel = new Panel(new GridLayout(4, 1));
		Label tezina = new Label("Tezina: ");
		tezina.setAlignment(Label.CENTER);
		tezina.setFont(new Font(null, Font.BOLD, 15));

		CheckboxGroup group = new CheckboxGroup();
		lako = new Checkbox("Lako", false, group);
		srednje = new Checkbox("Srednje", true, group);
		tesko = new Checkbox("Tesko", false, group);

		tezinaPanel.add(tezina);
		tezinaPanel.add(lako);
		tezinaPanel.add(srednje);
		tezinaPanel.add(tesko);

		panel.add(tezinaPanel);

		Panel panelDugme = new Panel();
		button = new Button("Kreni");
		panelDugme.add(button);

		panel.add(panelDugme);

		povrce = new Label("Povrce: " + basta.getPovrce());
		povrce.setAlignment(Label.CENTER);
		povrce.setFont(new Font(null, Font.BOLD, 18));
		panel.add(povrce);

		add(panel, BorderLayout.LINE_END);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (radi) {
					basta.zaustavi();
					button.setLabel("Kreni");
					lako.setEnabled(true);
					srednje.setEnabled(true);
					tesko.setEnabled(true);
					radi = false;
				} else {
					button.setLabel("Stani");
					String tezina = group.getSelectedCheckbox().getLabel();
					if (tezina.equals("Lako")) {
						basta.setIntervalCekanja(1000);
						basta.setBrojKoraka(10);
					} else if (tezina.equals("Srednje")) {
						basta.setIntervalCekanja(750);
						basta.setBrojKoraka(8);
					} else {
						basta.setIntervalCekanja(500);
						basta.setBrojKoraka(6);
					}
					povrce.setText("Povrce: 100");
					radi = true;
					lako.setEnabled(false);
					srednje.setEnabled(false);
					tesko.setEnabled(false);
					basta.pokreni();
				}
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				basta.zaustavi();
				dispose();
			}
		});

		setVisible(true);
		setResizable(false);
	}

	public void krajIgre() {
		basta.zaustavi();
		button.setLabel("Kreni");
		lako.setEnabled(true);
		srednje.setEnabled(true);
		tesko.setEnabled(true);
		radi = false;
	}

	public static Igra getIgra() {
		if(igra == null)
			igra = new Igra();
		return igra;
	}

	public void postaviPovrce() {
		povrce.setText("Povrce: " + basta.getPovrce());
	}

	public static void main(String[] args) {
		new Igra();
	}
}
