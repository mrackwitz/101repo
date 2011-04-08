package de.uni_koblenz.oneoonecompanies;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import de.uni_koblenz.jgralab.GraphIOException;
import de.uni_koblenz.jgralab.JGraLab;
import de.uni_koblenz.jgralab.greql2.evaluator.GreqlEvaluator;
import de.uni_koblenz.jgralab.greql2.jvalue.JValue;
import de.uni_koblenz.jgralab.gretl.Context;
import de.uni_koblenz.jgralab.gretl.MatchReplace;
import de.uni_koblenz.jgralab.utilities.tg2dot.Tg2Dot;
import de.uni_koblenz.jgralab.utilities.tg2dot.dot.GraphVizOutputFormat;
import de.uni_koblenz.oneoonecompanies.schema.CompaniesGraph;
import de.uni_koblenz.oneoonecompanies.schema.OneOOneSchema;
import de.uni_koblenz.oneoonecompanies.schema.Person;

public class CompanyServices {
	static {
		JGraLab.setLogLevel(Level.OFF);
	}

	private CompaniesGraph graph;
	private static CompanyServices instance;
	private GreqlEvaluator eval;

	private CompanyServices() {
		File gf = new File("companies.tg");
		if (gf.exists()) {
			try {
				graph = OneOOneSchema.instance().loadCompaniesGraph(
						gf.getPath());
			} catch (GraphIOException e) {
				e.printStackTrace();
			}
		}
		if (graph == null) {
			resetGraph();
		}
		eval = new GreqlEvaluator((String) null, graph, null);
	}

	public static CompanyServices instance() {
		if (instance == null) {
			instance = new CompanyServices();
		}
		return instance;
	}

	public long getSumOfSalaries() {
		return Math.round(greqlEval(
				"sum(from p: V{Person} reportSet p.salary end)").toDouble());
	}

	public void cutSalaries(float factor) {
		for (Person p : graph.getPersonVertices()) {
			p.set_salary(Math.round(p.get_salary() / factor));
		}
	}

	public void cutSalariesWithGReTL() {
		Context c = new Context(graph);
		new MatchReplace(c, "('$[0]' | salary = 'round($[0].salary / 2)')",
				"V{Person}").execute();
	}

	public void resetGraph() {
		graph = CompanyCreator.createCompanyGraph();
		try {
			OneOOneSchema.instance().saveCompaniesGraph(
					new File("companies.tg").getPath(), graph);
		} catch (GraphIOException e) {
			e.printStackTrace();
		}
	}

	public void visualizeCompanies() {
		try {
			final File png = new File("companies.png");
			png.deleteOnExit();
			Tg2Dot.convertGraph(graph, png.getCanonicalPath(),
					GraphVizOutputFormat.PNG);
			final Image i = ImageIO.read(png);
			JPanel panel = new JPanel() {
				private static final long serialVersionUID = -891941166683362373L;

				@Override
				public void paintComponent(Graphics g) {
					g.drawImage(i, 0, 0, null);
				}
			};
			panel.setPreferredSize(new Dimension(i.getWidth(panel), i
					.getHeight(panel)));
			panel.setVisible(true);
			JFrame f = new JFrame("Companies");
			f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			JScrollPane sp = new JScrollPane(panel);
			f.getContentPane().add(sp);
			f.setSize(400, 400);
			f.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JValue greqlEval(String query) {
		eval.setQuery(query);
		eval.startEvaluation();
		return eval.getEvaluationResult();
	}

	public static void main(String[] args) {
		System.out.println("Before cut: "
				+ CompanyServices.instance().getSumOfSalaries());
		CompanyServices.instance().cutSalaries(2);
		System.out.println("After first cut: "
				+ CompanyServices.instance().getSumOfSalaries());
		CompanyServices.instance().cutSalariesWithGReTL();
		System.out.println("After 2nd cut: "
				+ CompanyServices.instance().getSumOfSalaries());
		CompanyServices.instance().visualizeCompanies();
	}
}