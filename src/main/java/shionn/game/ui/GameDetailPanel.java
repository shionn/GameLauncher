package shionn.game.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.event.ListDataListener;

import shionn.game.games.Engine;
import shionn.game.games.Game;
import shionn.game.games.Proton;

public class GameDetailPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = -5616354546807861821L;
	private Game game;
	private Engine engine;

	public GameDetailPanel(Engine engine, Game game) {
		this.engine = engine;
		this.game = game;
		addMouseListener(this);
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buildImageTitle();
		buildTitle();
		buildSeparator();
		buildRunButton();
		buildProtonSelect();
	}

	private void buildImageTitle() {
		ImageIcon icon = new ImageIcon(game.getInstalersImgs().get(0));
		JLabel label = new JLabel(icon);
		add(label, buildHorizontalConstraint(0, 0));
	}

	private void buildTitle() {
		JLabel label = new JLabel(game.getName(), SwingConstants.CENTER);
		label.setFont(label.getFont().deriveFont(Font.BOLD, 24));
		label.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
//		label.setBorder(BorderFactory.createLineBorder(Color.red));
		label.setHorizontalAlignment(SwingConstants.CENTER);

		add(label, buildHorizontalConstraint(0, 1));
	}

	private void buildSeparator() {
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		GridBagConstraints gbc = buildHorizontalConstraint(0, 2);
		gbc.insets = new Insets(0, 0, 5, 0);
		add(separator, gbc);
	}

	private void buildRunButton() {
		JButton button = new JButton("Lancer ");
		button.setFont(getFont().deriveFont(Font.BOLD, 24));
		button.setBackground(Color.GREEN);
		button.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));
		button.setVisible(game.isInstalled());
		add(button, buildLeftConstraint(0, 3));
	}

	private void buildProtonSelect() {
		JLabel jLabel = new JLabel("Version Proton");
		add(jLabel, buildLeftConstraint(0, 4));

		JComboBox<Proton> box = new JComboBox<Proton>(new ComboBoxModel<Proton>() {

			@Override
			public int getSize() {
				return engine.getProtons().size();
			}

			@Override
			public Proton getElementAt(int index) {
				return engine.getProtons().get(index);
			}

			@Override
			public void addListDataListener(ListDataListener l) {
			}

			@Override
			public void removeListDataListener(ListDataListener l) {
			}

			@Override
			public void setSelectedItem(Object anItem) {
				game.setProton(((Proton) anItem).getName());
			}

			@Override
			public Object getSelectedItem() {
				return engine.proton(game.getProton());
			}
		});
		box.setRenderer(new ListCellRenderer<Proton>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends Proton> list, Proton value, int index,
					boolean isSelected, boolean cellHasFocus) {
				return new JLabel(value == null ? "--" : value.getName());
			}
		});

		add(box, buildLeftConstraint(1, 4));

	}

	private GridBagConstraints buildHorizontalConstraint(int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		return gbc;
	}

	private GridBagConstraints buildLeftConstraint(int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		return gbc;
	}

	private GridBagConstraints buildBothConstraint(int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		return gbc;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		e.consume();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		e.consume();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		e.consume();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		e.consume();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		e.consume();
	}

}
