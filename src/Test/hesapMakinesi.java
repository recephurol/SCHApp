package Test;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;

class hesapMakinesi extends JFrame {

    private JPanel contentPane;

    static JButton[] btn = new JButton[16];

    int s1,s2,yap;
    int islem;

    int i,j;
    int sayac = 0;
    private JTextField sonuc;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    hesapMakinesi frame = new hesapMakinesi();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public hesapMakinesi() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 314, 359);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        sonuc = new JTextField();
        sonuc.setHorizontalAlignment(SwingConstants.RIGHT);
        sonuc.setFont(new Font("Cambria", Font.BOLD, 16));
        sonuc.setBounds(12, 13, 265, 45);
        contentPane.add(sonuc);
        sonuc.setColumns(10);

        String[] isaretler = {"9","8","7", "/",
                "6","5","4", "*",
                "3","2","1", "-",
                "0","C","=", "+" };

        for(i=0; i<4; i++) {
            for(j=0; j<4; j++) {
                btn[sayac] = new JButton();
                btn[sayac].setText(isaretler[sayac]);
                btn[sayac].setBounds((20 + j*65), (80+ i*55), 55, 45);
                contentPane.add(btn[sayac]);
                sayac++;
            }
        }

        ActionListener a1 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(e.getSource() == btn[0]){
                    sonuc.setText(sonuc.getText()+9);
                }
                if(e.getSource() == btn[1]){
                    sonuc.setText(sonuc.getText()+8);
                }
                if(e.getSource() == btn[2]){
                    sonuc.setText(sonuc.getText()+7);
                }
                if(e.getSource() == btn[3]){  // /
                    s1 = Integer.parseInt(sonuc.getText());
                    islem = 4;
                    sonuc.setText("");
                }
                if(e.getSource() == btn[4]){  //6
                    sonuc.setText(sonuc.getText()+6);
                }
                if(e.getSource() == btn[5]){ //5
                    sonuc.setText(sonuc.getText()+5);
                }
                if(e.getSource() == btn[6]){ //4
                    sonuc.setText(sonuc.getText()+4);
                }
                if(e.getSource() == btn[7]){ // *
                    s1 = Integer.parseInt(sonuc.getText());
                    islem = 3;
                    sonuc.setText("");;
                }
                if(e.getSource() == btn[8]){ //3
                    sonuc.setText(sonuc.getText()+3);
                }
                if(e.getSource() == btn[9]){ //2
                    sonuc.setText(sonuc.getText()+2);
                }
                if(e.getSource() == btn[10]){ //1
                    sonuc.setText(sonuc.getText()+1);
                }
                if(e.getSource() == btn[11]){ // -
                    s1 = Integer.parseInt(sonuc.getText());
                    islem = 2;
                    sonuc.setText("");;
                }
                if(e.getSource() == btn[12]){ //0
                    sonuc.setText(sonuc.getText()+0);
                }
                if(e.getSource() == btn[13]){ //C SİL
                    sonuc.setText("");
                }
                if(e.getSource() == btn[14]){ // =
                    s2 = Integer.parseInt(sonuc.getText());

                    if(islem==1) {
                        yap = s1 + s2;
                        sonuc.setText(Integer.toString(yap));
                    }
                    else if(islem==2) {
                        yap = s1 - s2;
                        sonuc.setText(Integer.toString(yap));
                    }
                    else if(islem==3) {
                        yap = s1 * s2;
                        sonuc.setText(Integer.toString(yap));
                    }
                    else if(islem==4) {
                        yap = s1 / s2;
                        sonuc.setText(Integer.toString(yap));
                    }
                }
                if(e.getSource() == btn[15]){ // +
                    s1 = Integer.parseInt(sonuc.getText());
                    islem = 1;
                    sonuc.setText("");
                }
            }
        };
        btn[0].addActionListener(a1);
        btn[1].addActionListener(a1);
        btn[2].addActionListener(a1);
        btn[3].addActionListener(a1);
        btn[4].addActionListener(a1);
        btn[5].addActionListener(a1);
        btn[6].addActionListener(a1);
        btn[7].addActionListener(a1);
        btn[8].addActionListener(a1);
        btn[9].addActionListener(a1);
        btn[10].addActionListener(a1);
        btn[11].addActionListener(a1);
        btn[12].addActionListener(a1);
        btn[13].addActionListener(a1);
        btn[14].addActionListener(a1);
        btn[15].addActionListener(a1);
    }
}