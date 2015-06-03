package transitions;

import arcs.Arc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import util.ProbabilityFunctions;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thiago
 */
public class TimedTransition extends Transition {

    private double time;
    private double global_time = 0;
    private int type_probability_functions;
    private ProbabilityFunctions p;
    private ArrayList<Double> listTime = new ArrayList<>();

    public TimedTransition() {
        setIcon(new ImageIcon("src/images/timed.png"));
    }

    public TimedTransition(long id, String name) {
        super(id, name);
        setIcon(new ImageIcon("src/images/timed.png"));

    }

    public double getGlobal_time() {
        return global_time;
    }

    public void setGlobal_time(double global_time) {
        this.global_time = global_time;
    }

    public ProbabilityFunctions getP() {

        return p;
    }

    public void setP(ProbabilityFunctions p) {
        this.p = p;
    }

    @Override
    public void activate() {
        if (!getTimer().isRunning()) {
            getTimer().start();
        }
        time = getTimeT();
        System.out.println("Time 1:" + getTimeT());
        this.listTime.add(time);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        this.global_time++;
        time = time - 1.0;
        //System.out.println("Time 2:" + time);
        if (time <= 0.0) {

            for (int i = 0; i < getList_arc().size(); i++) {
                Arc arc = getList_arc().get(i);
                arc.checkArc();
                System.out.println("" + arc.getTransition().getState());
            }
            for (int i = 0; i < getList_arc().size(); i++) {
                Arc arc = getList_arc().get(i);
                arc.run();
                getParent().repaint();
                activate();
                //
                // }
                //System.out.println("Aqui" + arc.getClass().getName());

            }

            //activate();
        }

    }

    public int getType_probability_functions() {
        return type_probability_functions;
    }

    public void setType_probability_functions(int type_probability_functions) {
        this.type_probability_functions = type_probability_functions;
    }

    public double getTimeT() {
        switch (type_probability_functions) {
            case 0:
                JOptionPane.showMessageDialog(null, "Selet a probility function");
                break;
            case 1:
                System.out.println("Expo");
                this.time = this.p.getExponencial();
                break;
            case 2:
                System.out.println("Norma");

                this.time = this.p.getNormal();
                break;
            case 3:
                System.out.println("Uni");

                this.time = this.p.getUniform();
                break;

        }
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
