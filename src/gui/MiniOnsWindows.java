package gui;

import util.DialogPUniform;
import util.DialogPNormal;
import util.DialogPExponential;
import places.Place;
import transitions.TimedTransition;
import transitions.ImmediateTransition;
import transitions.Transition;
import arcs.InputArc;
import arcs.Arc;
import arcs.InhibitorArc;
import arcs.OutputArc;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import util.ProbabilityFunctions;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thiago
 */
public class MiniOnsWindows extends JFrame implements ActionListener {

    
    private JLabel fondo = new JLabel(new ImageIcon("src/images/fondo.png"));
    //Opciones Places
    int place_id = 0;
    ArrayList<Place> arrayPlaces = new ArrayList<>();
    //Options Transcitions 
    int transcition_id;
    ArrayList<Transition> arrayTransition = new ArrayList<>();
    ArrayList<Arc> arrayArcs = new ArrayList<>();
    int opinput = 0,
            opoutput = 0,
            opinhibitor = 0;
    Place miPlace = null;
    Transition miTransition = null;
    JLabel lbl_global_timer = new JLabel();
    boolean bandera = false;
    private JButton btnPlace = new JButton(new ImageIcon("src/images/btnPlace.png"));
    private JButton btnInput = new JButton(new ImageIcon("src/images/btnInput.png"));
    private JButton btnOutput = new JButton(new ImageIcon("src/images/btnOutput.png"));
    private JButton btnInhibitor = new JButton(new ImageIcon("src/images/btnInhibitor.png"));
    private JButton btnImmediate = new JButton(new ImageIcon("src/images/btnImmediate.png"));
    private JButton btnTimed = new JButton(new ImageIcon("src/images/btnTimed.png"));
    //
    boolean vandera_place = false;
    boolean vandera_input = false;
    boolean vandera_output = false;
    boolean vandera_inhibitor = false;
    boolean vandera_immediate = false;
    boolean vandera_timed = false;
    //
    PanelPlace pplace = new PanelPlace();
    PanelInput pinput = new PanelInput();
    PanelOutput poutput = new PanelOutput();
    PanelInhibitor pinhibitor = new PanelInhibitor();
    PanelTimed ptimed = new PanelTimed();
    PanelImmediate pimmediate = new PanelImmediate();
    //JintenalFrame Probalities
    JButton btnplay = new JButton(new ImageIcon("src/images/play.png"));
    //
    ProbabilityFunctions probability;
    int bandera_play = 0;
//    int id=0;
//    int id=0;
    JFrame frame = this;

    public MiniOnsWindows() {

        init();
        events();
        panels();
        //mapa1();
    }

    private void init() {
        //
        setSize(1274, 669);
        //
        setLocationRelativeTo(null);
        //
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//        pn = new PanelPNormal(this);
//        pu = new PanelPUniform(this);
       

        fondo.setSize(getWidth() - 10, getHeight() - 10);
        fondo.setLayout(null);
        fondo.setLocation(0, 0);
        add(fondo);



//        fondo.add(pn);
//        fondo.add(pu);



        btnPlace.setSize(92, 92);
        btnPlace.setContentAreaFilled(false);
        btnPlace.setBorder(null);
        btnPlace.setLocation(947, 59);
        btnPlace.setCursor(new Cursor(12));
        fondo.add(btnPlace);

        btnInput.setSize(92, 92);
        btnInput.setContentAreaFilled(false);
        btnInput.setBorder(null);
        btnInput.setLocation(1051, 59);
        btnInput.setCursor(new Cursor(12));
        fondo.add(btnInput);

        btnOutput.setSize(92, 92);
        btnOutput.setContentAreaFilled(false);
        btnOutput.setBorder(null);
        btnOutput.setLocation(1157, 59);
        btnOutput.setCursor(new Cursor(12));
        fondo.add(btnOutput);


        btnInhibitor.setSize(92, 92);
        btnInhibitor.setContentAreaFilled(false);
        btnInhibitor.setBorder(null);
        btnInhibitor.setLocation(947, 179);
        btnInhibitor.setCursor(new Cursor(12));
        fondo.add(btnInhibitor);

        btnImmediate.setSize(92, 92);
        btnImmediate.setContentAreaFilled(false);
        btnImmediate.setBorder(null);
        btnImmediate.setLocation(1051, 179);
        btnImmediate.setCursor(new Cursor(12));
        fondo.add(btnImmediate);


        btnTimed.setSize(92, 92);
        btnTimed.setContentAreaFilled(false);
        btnTimed.setBorder(null);
        btnTimed.setLocation(1157, 179);
        btnTimed.setCursor(new Cursor(12));
        fondo.add(btnTimed);

        lbl_global_timer.setSize(200, 30);
        lbl_global_timer.setLocation(300, 20);
        fondo.add(lbl_global_timer);

        btnplay.setSize(51, 51);
        btnplay.setLocation(790, 564);
        btnplay.setContentAreaFilled(false);
        btnplay.setBorder(null);
        btnplay.setCursor(new Cursor(12));
        fondo.add(btnplay);

    }

    private void panels() {

        fondo.add(pplace);
        fondo.add(pinput);
        fondo.add(pinhibitor);
        fondo.add(poutput);
        //
        fondo.add(ptimed);
        fondo.add(pimmediate);
        ptimed.getTxt_idImmediate().setEditable(false);
        ptimed.getTxtNobreImmediate().setEditable(false);


    }

    private ProbabilityFunctions selectTypeProbability() {

        return probability;
    }

    private void controlProperty(Component panel) {
        pplace.setVisible(false);
        pinput.setVisible(false);
        poutput.setVisible(false);
        pinhibitor.setVisible(false);
        pimmediate.setVisible(false);
        ptimed.setVisible(false);
        panel.setVisible(true);
    }

    private void mapa1() {

        final Place p1 = new Place();
        Place p2 = new Place();
//
        final TimedTransition tt1 = new TimedTransition();
        tt1.setName("Tt1");
        ImmediateTransition tt2 = new ImmediateTransition(0.5);
        tt2.setName("It2");

        TimedTransition tt3 = new TimedTransition();
        tt3.setName("Tt3");

//
//
//        //Asid

//
        Arc ar1 = new OutputArc(1, 1, p1, tt1);
        ar1.setName("");
        Arc ar2 = new InputArc(1, 1, p1, tt2);
        Arc ar3 = new InhibitorArc(1, 1, p2, tt2);
        ar2.setName("");
        Arc ar4 = new OutputArc(1, 1, p2, tt2);
//
        Arc ar5 = new InputArc(1, 1, p2, tt3);
//
        ar1.setSize(fondo.getWidth(), fondo.getHeight());
        ar2.setSize(fondo.getWidth(), fondo.getHeight());
        ar3.setSize(fondo.getWidth(), fondo.getHeight());
        ar4.setSize(fondo.getWidth(), fondo.getHeight());
        ar5.setSize(fondo.getWidth(), fondo.getHeight());
//
        tt1.addArc(ar1);
        tt2.addArc(ar3);
        tt2.addArc(ar2);
        tt2.addArc(ar4);
        tt3.addArc(ar5);

        arrayTransition.add(tt1);
        arrayTransition.add(tt2);
        arrayTransition.add(tt3);


        //lbl_global_timer.setText(tt1.getGlobal_time() + "T");


        fondo.add(p1);
        fondo.add(p2);

        fondo.add(tt1);
        fondo.add(tt2);
        fondo.add(tt3);


        fondo.add(ar1);
        fondo.add(ar2);
        fondo.add(ar3);
        fondo.add(ar4);
        fondo.add(ar5);
    }

    private void mapa2() {

        final Place p1 = new Place();
        Place p2 = new Place();
//
        final TimedTransition tt1 = new TimedTransition();
        tt1.setName("Tt1");
        TimedTransition tt2 = new TimedTransition();
        tt2.setName("It2");

//        TimedTransition tt3 = new TimedTransition();
//        tt3.setName("Tt3");

//
//
//        //Asid

//
        Arc ar1 = new OutputArc(1, 1, p1, tt1);
        //ar1.setName("");
        Arc ar2 = new InputArc(1, 1, p1, tt2);
//        Arc ar3 = new InhibitorArc(1, 1, p2, tt2);
        //ar2.setName("");
        Arc ar4 = new OutputArc(1, 1, p2, tt2);
//
//        Arc ar5 = new InputArc(1, 1, p2, tt3);
//
        ar1.setSize(fondo.getWidth(), fondo.getHeight());
        ar2.setSize(fondo.getWidth(), fondo.getHeight());
//        ar3.setSize(fondo.getWidth(), fondo.getHeight());
        ar4.setSize(fondo.getWidth(), fondo.getHeight());
//        ar5.setSize(fondo.getWidth(), fondo.getHeight());
//
        tt1.addArc(ar1);
//        tt2.addArc(ar3);
        tt2.addArc(ar2);
        tt2.addArc(ar4);
//        tt3.addArc(ar5);

        arrayTransition.add(tt1);
        arrayTransition.add(tt2);
//        arrayTransition.add(tt3);


        //lbl_global_timer.setText(tt1.getGlobal_time() + "T");


        fondo.add(p1);
        fondo.add(p2);

        fondo.add(tt1);
        fondo.add(tt2);
//        fondo.add(tt3);


        fondo.add(ar1);
        fondo.add(ar2);
//        fondo.add(ar3);
        fondo.add(ar4);
//        fondo.add(ar5);


    }

    private void events() {

        final JComboBox combo = ptimed.getSelectProbality();

        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int op = combo.getSelectedIndex();
                combo.setSelectedIndex(op);
                probability = new ProbabilityFunctions(frame);


                switch (op) {
                    case 0:
                        JOptionPane.showMessageDialog(null, "Select a pribability function");
                        break;

                    case 1:
                        probability.setExponential();
                        break;
                    case 2:
                        probability.setNormal();
                        break;
                    case 3:
                        probability.setUniform();
                        break;

                }

            }
        });
        btnPlace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlProperty(pplace);
                btnPlace.setBorder(BorderFactory.createLineBorder(Color.blue));
                btnInput.setBorder(null);
                btnOutput.setBorder(null);
                btnInhibitor.setBorder(null);
                btnTimed.setBorder(null);
                btnImmediate.setBorder(null);

                //
                vandera_place = true;
                vandera_input = false;
                vandera_output = false;
                vandera_inhibitor = false;
                vandera_timed = false;
                vandera_immediate = false;
            }
        });

        btnInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlProperty(pinput);
                btnInput.setBorder(BorderFactory.createLineBorder(Color.blue));
                btnPlace.setBorder(null);
                btnOutput.setBorder(null);
                btnInhibitor.setBorder(null);
                btnTimed.setBorder(null);
                btnImmediate.setBorder(null);

                //
                vandera_place = false;
                vandera_input = true;
                vandera_output = false;
                vandera_inhibitor = false;
                vandera_timed = false;
                vandera_immediate = false;
            }
        });

        btnOutput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlProperty(poutput);

                btnOutput.setBorder(BorderFactory.createLineBorder(Color.blue));
                btnInput.setBorder(null);
                btnPlace.setBorder(null);
                btnInhibitor.setBorder(null);
                btnTimed.setBorder(null);
                btnImmediate.setBorder(null);
                //
                vandera_place = false;
                vandera_input = false;
                vandera_output = true;
                vandera_inhibitor = false;
                vandera_timed = false;
                vandera_immediate = false;
            }
        });

        btnInhibitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlProperty(pinhibitor);
                btnInhibitor.setBorder(BorderFactory.createLineBorder(Color.blue));
                btnPlace.setBorder(null);
                btnInput.setBorder(null);
                btnOutput.setBorder(null);
                btnTimed.setBorder(null);
                btnImmediate.setBorder(null);
                //
                vandera_place = false;
                vandera_input = false;
                vandera_output = false;
                vandera_inhibitor = true;
                vandera_timed = false;
                vandera_immediate = false;
            }
        });

        btnImmediate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlProperty(pimmediate);
                btnImmediate.setBorder(BorderFactory.createLineBorder(Color.blue));
                btnPlace.setBorder(null);
                btnInput.setBorder(null);
                btnOutput.setBorder(null);
                btnTimed.setBorder(null);
                btnInhibitor.setBorder(null);
                //
                vandera_place = false;
                vandera_input = false;
                vandera_output = false;
                vandera_inhibitor = false;
                vandera_timed = false;
                vandera_immediate = true;
            }
        });

        btnTimed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlProperty(ptimed);
                btnTimed.setBorder(BorderFactory.createLineBorder(Color.blue));
                btnPlace.setBorder(null);
                btnInput.setBorder(null);
                btnOutput.setBorder(null);
                btnImmediate.setBorder(null);
                btnInhibitor.setBorder(null);
                //
                vandera_place = false;
                vandera_input = false;
                vandera_output = false;
                vandera_inhibitor = false;
                vandera_timed = true;
                vandera_immediate = false;


            }
        });

        fondo.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (vandera_place) {
                    if (!pplace.getTxtNobrePlace().getText().equals("")) {
                        final Place place = new Place();
                        place.setLocation(e.getX() - place.getWidth() / 2, e.getY() - place.getHeight() / 2);
                        fondo.add(place);
                        arrayPlaces.add(place);
                        fondo.repaint();
                        pplace.getTxt_idPlace().setText("" + arrayPlaces.size());
                        place.setName(pplace.getTxtNobrePlace().getText());
                        place.setId(Long.parseLong(pplace.getTxt_idPlace().getText()));

                        place.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (vandera_input || vandera_output || vandera_inhibitor) {
                                    miPlace = place;
                                    place.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                                }
                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "Set Place name");
                    }
                }
                if (vandera_timed) {
                    if (ptimed.getSelectProbality().getSelectedIndex() != 0) {

                        final TimedTransition t = new TimedTransition();
                        t.setLocation(e.getX() - t.getWidth() / 2, e.getY() - t.getHeight() / 2);
                        fondo.add(t);
                        fondo.repaint();
                        arrayTransition.add(t);

                        ptimed.getTxtNobreImmediate().setText("Timed t: " + arrayTransition.size());

                        ptimed.getTxt_idImmediate().setText("" + (arrayTransition.size()));

                        t.setId(Long.parseLong(ptimed.getTxt_idImmediate().getText()));
                        t.setName(ptimed.getTxtNobreImmediate().getText());
                        t.setP(selectTypeProbability());

                        t.setType_probability_functions(ptimed.getSelectProbality().getSelectedIndex());
                        t.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //if (vandera_input || vandera_output || vandera_inhibitor) {
                                miTransition = t;

                                t.setBorder(BorderFactory.createLineBorder(Color.red));

                                Arc ar = createArc();
                                if (ar != null) {
                                    t.addArc(ar);
                                }
                                //}

                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "Select a probability function");
                    }
                }
                if (vandera_immediate) {
                    if (!pimmediate.getTxtProbability().getText().equals("")) {
                        final ImmediateTransition t = new ImmediateTransition(1);
                        t.setLocation(e.getX() - t.getWidth() / 2, e.getY() - t.getHeight() / 2);
                        fondo.add(t);
                        fondo.repaint();
                        arrayTransition.add(t);
                        pimmediate.getTxtNobreImmediate().setText("Immediate T: " + arrayTransition.size());
                        pimmediate.getTxt_idImmediate().setText("" + arrayTransition.size());

                        t.setName(pimmediate.getTxtNobreImmediate().getText());
                        t.setId(Long.parseLong(pimmediate.getTxt_idImmediate().getText()));
                        t.setProbability(Double.parseDouble(pimmediate.getTxtProbability().getText()));
                        t.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                miTransition = t;
                                t.setBorder(BorderFactory.createLineBorder(Color.red));
                                Arc ar = createArc();
                                if (ar != null) {
                                    t.addArc(ar);
                                }
                            }
                        });
                    } else {

                        JOptionPane.showMessageDialog(null, "Set probability");
                    }
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
        });

        btnplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bandera_play != 0) {
                    btnplay.setIcon(new ImageIcon("src/images/play.png"));
                    bandera_play = 0;
                } else {
                    btnplay.setIcon(new ImageIcon("src/images/pause.png"));
                    for (int i = 0; i < arrayTransition.size(); i++) {
                        arrayTransition.get(i).activate();
                    }
                    bandera_play++;
                }
            }
        });
    }

    private Arc createArc() {
        Arc arc = null;
        if (miPlace != null && miTransition != null) {

            if (vandera_input) {
                if (!pinput.getTxtMultiplicity().getText().equals("")) {
                    pinput.getTxtIdInput().setText("" + arrayArcs.size());
                    arc = new InputArc(Long.parseLong(pinput.getTxtIdInput().getText()), Integer.parseInt(pinput.getTxtMultiplicity().getText()), miPlace, miTransition);
                }
            }

            if (vandera_output) {
                if (!poutput.getTxtMultiplicity().getText().equals("")) {
                    poutput.getTxtIdOutput().setText("" + arrayArcs.size());
                    arc = new OutputArc(Long.parseLong(poutput.getTxtIdOutput().getText()), Integer.parseInt(poutput.getTxtMultiplicity().getText()), miPlace, miTransition);
                }
            }

            if (vandera_inhibitor) {
                if (!pinhibitor.getTxtMultiplicity().getText().equals("")) {
                    pinhibitor.getTxtIdOutput().setText("" + arrayArcs.size());
                    arc = new InhibitorArc(Long.parseLong(pinhibitor.getTxtIdOutput().getText()), Integer.parseInt(pinhibitor.getTxtMultiplicity().getText()), miPlace, miTransition);
                }
            }
            try {
                arc.setSize(fondo.getWidth(), fondo.getHeight());

                arrayArcs.add(arc);

                for (int i = 0; i < arrayPlaces.size(); i++) {
                    Place place = arrayPlaces.get(i);
                    place.setBorder(null);

                }
                for (int i = 0; i < arrayTransition.size(); i++) {
                    Transition tra = arrayTransition.get(i);
                    tra.setBorder(null);

                }
                miPlace = null;
                miTransition = null;
                fondo.add(arc);
                fondo.repaint();
                vandera_output = false;
                vandera_input = false;
                vandera_inhibitor = false;
                return arc;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: Set miltiplicity" + e.getMessage());
            }
        }

        return null;
    }

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;


                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MiniOnsWindows.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MiniOnsWindows().setVisible(true);

            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
