
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kayttoliittyma extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form Kayttoliittyma *
     *
     */
    static int perheenjasentenMaara;
    static int bruttotulojenSuuruus = 0;
    static int palvelunMaaraKuukaudessa;
    static int vahennysTuloista;
    static int maksuluokka;
    static double kotihoitomaksu;
    static double turvapuhelimenMaksu = 54;
    static double[][] taulukko = {{2.5, 5, 15, 25, 35}, {2.5, 5, 10, 15, 22}, {2.5, 5, 10, 14, 18}, {2.5, 5, 9, 12, 15}, {2.5, 5, 8, 11, 13}, {2.5, 5, 7, 9, 11}, {2.5, 5, 7, 9, 10}, {2.5, 5, 7, 8, 9}};
    static double maksuprosentti;
    static boolean onkoTurvapuhelinta = false;
    static boolean tulotIlmoitettu = true;

    public Kayttoliittyma() throws FileNotFoundException, Exception {
        initComponents();
        bruttotulot.setText("");
        kotihoitoMaksu.setText("");
        turvapuhelin.setText("");
        laskeNappi.addActionListener(this);
        turvapuhelinKylla.addActionListener(this);
        uudelleenNappi.addActionListener(this);
        tulos.setVisible(false);
    }

    public void laske() {
        perheenjasentenMaara = Integer.parseInt(perheenjasenet.getText());
        if (bruttotulot.getText().equals("")) {
            tulotIlmoitettu = false;
        } else {
            bruttotulojenSuuruus = Integer.parseInt(bruttotulot.getText());
        }

        palvelunMaaraKuukaudessa = Integer.parseInt(palvelunMaara.getText());
        vahennysTuloista = vahennysBruttotuloista(perheenjasentenMaara);//
        maksuluokka = maksuluokka(palvelunMaaraKuukaudessa);//
        maksuprosentti = maksuprosentti(perheenjasentenMaara, maksuluokka);//
        turvapuhelimenMaksu = turvapuhelimenMaksu(perheenjasentenMaara, bruttotulojenSuuruus);
    }

    public void tulos() {

        if (!tulotIlmoitettu) {

            switch (maksuluokka) {
                case 1:
                    kotihoitomaksu = 112.2;
                    break;
                case 2:
                    kotihoitomaksu = 336.6;
                    break;
                case 3:
                    kotihoitomaksu = 617.09;
                    break;
                case 4:
                    kotihoitomaksu = 1178.09;
                    break;
                case 5:
                    kotihoitomaksu = 2300.08;
                    break;
            }
        } else if (bruttotulojenSuuruus < vahennysTuloista) {
            kotihoitomaksu = 0;
        } else {
            kotihoitomaksu = ((bruttotulojenSuuruus - vahennysTuloista) * (maksuprosentti / 100));
        }
        if (tulotIlmoitettu) {

            kotihoitoMaksu.setText("Tulojenne mukaan arvioitu kotihoitomaksu on " + kotihoitomaksu + " euroa kuukaudessa.");
        } else {
            kotihoitoMaksu.setText("Kotihoitomaksu on tapauksessanne maksuluokkakohtaisen enimmaismaaran mukainen eli " + kotihoitomaksu + " euroa kuukaudessa.");
        }
        if (onkoTurvapuhelinta) {
            if (!tulotIlmoitettu) {
                turvapuhelin.setText("Palmian turvapuhelimen maksu on " + turvapuhelimenMaksu + " euroa kuukaudessa.");
            }

            if (tulotIlmoitettu && bruttotulojenSuuruus < 1099) {
                turvapuhelin.setText("Ei maksua Palmian turvapuhelimesta.");
            } else {
                turvapuhelin.setText("Palmian turvapuhelimen maksu on " + turvapuhelimenMaksu + " euroa kuukaudessa.");
            }
        } else {
            turvapuhelin.setText("Ei turvapuhelinta.");
        }
    }

    public static int vahennysBruttotuloista(int perheenjasenet) {

        switch (perheenjasenet) {
            case 1:
                vahennysTuloista = 563;
                break;
            case 2:
                vahennysTuloista = 1039;
                break;
            case 3:
                vahennysTuloista = 1628;
                break;
            case 4:
                vahennysTuloista = 2014;
                break;
            case 5:
                vahennysTuloista = 2438;
                break;
            case 6:
                vahennysTuloista = 2799;
                break;
            case 7:
                vahennysTuloista = 3141;
                break;
            case 8:
                vahennysTuloista = 3483;
                break;
        }
        return vahennysTuloista;
    }

    public static int maksuluokka(int palvelunMaaraKuukaudessa) {

        if (palvelunMaaraKuukaudessa < 6) {
            maksuluokka = 1;
        }
        if (palvelunMaaraKuukaudessa > 5 && palvelunMaaraKuukaudessa < 11) {
            maksuluokka = 2;
        }
        if (palvelunMaaraKuukaudessa > 10 && palvelunMaaraKuukaudessa < 21) {
            maksuluokka = 3;
        }
        if (palvelunMaaraKuukaudessa > 20 && palvelunMaaraKuukaudessa < 41) {
            maksuluokka = 4;
        }
        if (palvelunMaaraKuukaudessa > 40) {
            maksuluokka = 5;
        }
        return maksuluokka;
    }

    public static double maksuprosentti(int perheenjasenet, int maksuluokka) {
        maksuprosentti = taulukko[perheenjasenet - 1][maksuluokka - 1];
        return maksuprosentti;
    }

    public static double turvapuhelimenMaksu(int perheenjasenet, int bruttotulot) {

        if (!tulotIlmoitettu) {
            turvapuhelimenMaksu = 54;
        }
        if (tulotIlmoitettu && bruttotulot < 1099) {
            turvapuhelimenMaksu = 0;
        }
        if (tulotIlmoitettu && bruttotulot > 1098 && bruttotulot < 1648 && perheenjasenet == 1) {
            turvapuhelimenMaksu = 34.1;
        }
        if (tulotIlmoitettu && bruttotulot > 1098 && bruttotulot < 2029 && perheenjasenet >= 2) {
            turvapuhelimenMaksu = 34.1;
        } else {
            turvapuhelimenMaksu = 54;
        }
        return turvapuhelimenMaksu;
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(laskeNappi)) {
            laske();
            tulos();
            tulos.setVisible(true);
        }
        if (e.getSource().equals(turvapuhelinKylla)) {
            onkoTurvapuhelinta = true;
        }
        if (e.getSource().equals(uudelleenNappi)) {
            perheenjasentenMaara = 0;
            bruttotulojenSuuruus = 0;
            palvelunMaaraKuukaudessa = 0;
            tulos.setVisible(false);
            onkoTurvapuhelinta = false;
            tulotIlmoitettu = true;
            perheenjasenet.setText("");
            bruttotulot.setText("");
            palvelunMaara.setText("");
            turvapuhelinKylla.setSelected(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lomake = new javax.swing.JPanel();
        laskuriOtsikko = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        perheenjasenet = new javax.swing.JTextField();
        bruttotulot = new javax.swing.JTextField();
        palvelunMaara = new javax.swing.JTextField();
        turvapuhelinKylla = new javax.swing.JRadioButton();
        laskeNappi = new javax.swing.JButton();
        tulos = new javax.swing.JPanel();
        arvioOtsikko = new javax.swing.JLabel();
        kotihoitoMaksu = new javax.swing.JLabel();
        turvapuhelin = new javax.swing.JLabel();
        uudelleenNappi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        laskuriOtsikko.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        laskuriOtsikko.setText("Kotihoidon asiakasmaksulaskuri");

        jLabel1.setText("Perheenjasenten maara:");

        jLabel2.setText("Bruttotulot kuukaudessa:");

        jLabel3.setText("Kotona tarvittavan palvelun maara tunteina/kk:");

        jLabel4.setText("Haluatteko kayttoonne Palmian turvapuhelimen?");

        perheenjasenet.setColumns(2);
        perheenjasenet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                perheenjasenetActionPerformed(evt);
            }
        });

        bruttotulot.setColumns(7);

        palvelunMaara.setColumns(2);
        palvelunMaara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                palvelunMaaraActionPerformed(evt);
            }
        });

        turvapuhelinKylla.setText("Kylla");
        turvapuhelinKylla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turvapuhelinKyllaActionPerformed(evt);
            }
        });

        laskeNappi.setText("Laske");
        laskeNappi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laskeNappiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout lomakeLayout = new javax.swing.GroupLayout(lomake);
        lomake.setLayout(lomakeLayout);
        lomakeLayout.setHorizontalGroup(
            lomakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lomakeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lomakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(laskuriOtsikko)
                    .addGroup(lomakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(laskeNappi)
                        .addGroup(lomakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(lomakeLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(perheenjasenet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(lomakeLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bruttotulot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(lomakeLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(91, 91, 91)
                                .addComponent(palvelunMaara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(lomakeLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(turvapuhelinKylla)))))
                .addContainerGap(204, Short.MAX_VALUE))
        );
        lomakeLayout.setVerticalGroup(
            lomakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lomakeLayout.createSequentialGroup()
                .addComponent(laskuriOtsikko)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lomakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lomakeLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(lomakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(bruttotulot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(lomakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(palvelunMaara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(perheenjasenet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lomakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(turvapuhelinKylla))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(laskeNappi))
        );

        arvioOtsikko.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        arvioOtsikko.setText("Arvio kotihoidon maksusta");

        uudelleenNappi.setText("Uudelleen");

        javax.swing.GroupLayout tulosLayout = new javax.swing.GroupLayout(tulos);
        tulos.setLayout(tulosLayout);
        tulosLayout.setHorizontalGroup(
            tulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tulosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(arvioOtsikko)
                    .addComponent(kotihoitoMaksu)
                    .addComponent(turvapuhelin)
                    .addComponent(uudelleenNappi))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tulosLayout.setVerticalGroup(
            tulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tulosLayout.createSequentialGroup()
                .addComponent(arvioOtsikko)
                .addGap(20, 20, 20)
                .addComponent(kotihoitoMaksu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(turvapuhelin)
                .addGap(18, 18, 18)
                .addComponent(uudelleenNappi)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lomake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 98, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lomake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tulos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>                        

    private void laskeNappiActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void perheenjasenetActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void palvelunMaaraActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void turvapuhelinKyllaActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws FileNotFoundException, Exception {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Kayttoliittyma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Kayttoliittyma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Kayttoliittyma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Kayttoliittyma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Kayttoliittyma().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(Kayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel arvioOtsikko;
    private javax.swing.JTextField bruttotulot;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel kotihoitoMaksu;
    private javax.swing.JButton laskeNappi;
    private javax.swing.JLabel laskuriOtsikko;
    private javax.swing.JPanel lomake;
    private javax.swing.JTextField palvelunMaara;
    private javax.swing.JTextField perheenjasenet;
    private javax.swing.JPanel tulos;
    private javax.swing.JLabel turvapuhelin;
    private javax.swing.JRadioButton turvapuhelinKylla;
    private javax.swing.JButton uudelleenNappi;
    // End of variables declaration                   

}
