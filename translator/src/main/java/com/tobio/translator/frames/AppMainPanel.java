package com.tobio.translator.frames;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.tobio.translator.lookandfeels.LookAndFeelUtils;
import com.tobio.translator.utils.ImageUtils;

public class AppMainPanel extends JFrame {

    private static final long     serialVersionUID = 1L;

    protected static AppMainPanel instance;
    protected static String       lookAndFeelClass = "";

    public JScrollPane            scrollPanel;
    public JPanel                 mainPanel;

    public JPanel                 translationsPanel;
    public JPanel                 translationSuppliersPanel;
    public JPanel                 lookAndFeelPanel;


    public static AppMainPanel getInstance() {
        if (AppMainPanel.instance == null) {
            AppMainPanel.instance = new AppMainPanel(null);
        }
        return AppMainPanel.instance;
    }


    public static AppMainPanel getInstance(String lookAndFeelClass) {
        if (AppMainPanel.instance == null) {
            AppMainPanel.instance = new AppMainPanel(lookAndFeelClass);
        }
        return AppMainPanel.instance;
    }


    protected AppMainPanel(String lookAndFeelClass) {
        super("Traductor by Tobío");

        LookAndFeelUtils.getInstance().init();

        this.init(lookAndFeelClass);

        this.loadLookAndFeel(lookAndFeelClass);

    }


    protected void loadLookAndFeel(String lookAndFeelClass) {

        try {
            LookAndFeelUtils.setDefaultLookAndFeel(UIManager.getLookAndFeel());

            if (lookAndFeelClass != null) {
                UIManager.setLookAndFeel(lookAndFeelClass);
            } else {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            }

            SwingUtilities.updateComponentTreeUI(this);

        } catch (Exception ex) {

        }
    }


    protected void init(String lookAndFeelClass) {

        if (lookAndFeelClass != null) {
            AppMainPanel.lookAndFeelClass = lookAndFeelClass;
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addComponents();

        this.setSize(1000, 1000);
        this.pack();

        this.addIconImage();
    }


    protected void addIconImage() {

        URL url = Thread.currentThread().getContextClassLoader().getResource(ImageUtils.APP_ICON);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        this.setIconImage(img);
    }


    protected void addComponents() {

        JMenuBar menubar = this.createMenuBar();

        this.translationsPanel = this.createTranslationsPanel();
        this.translationSuppliersPanel = this.createTranslationSuppliersPanel();
        this.lookAndFeelPanel = this.createLookAndFeelPanel();

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayout());

        this.scrollPanel = new JScrollPane();
        this.scrollPanel = new JScrollPane(this.translationsPanel);

        // this.changeState(States.MENU_TRANSLATIONS_STATE);
        this.mainPanel.removeAll();
        this.mainPanel.add(this.scrollPanel);
        this.mainPanel.revalidate();
        this.mainPanel.repaint();

        this.setJMenuBar(menubar);

        this.add(this.mainPanel);

    }


    public void changeState(int state) {

        switch (state) {
            case States.MENU_TRANSLATIONS_STATE:
                this.mainPanel.removeAll();
                this.mainPanel.add(this.scrollPanel);
                break;
            case States.MENU_PROVEEDORES_STATE:
                this.mainPanel.removeAll();
                this.mainPanel.add(this.translationSuppliersPanel);
                break;
            case States.MENU_LOOK_AND_FEEL_STATE:
                this.mainPanel.removeAll();
                this.mainPanel.add(this.lookAndFeelPanel);
                break;
            default:
                break;
        }

        this.mainPanel.revalidate();
        this.mainPanel.repaint();
        SwingUtilities.updateComponentTreeUI(AppMainPanel.getInstance(null));
    }


    public JPanel createTranslationSuppliersPanel() {
        return TranslationSuppliersPanel.getInstance(new GridBagLayout(), new HashMap<>());
    }


    protected JPanel createTranslationsPanel() {
        return TranslationsPanel.newInstance(new GridBagLayout(), new HashMap<>());
    }


    protected JPanel createLookAndFeelPanel() {
        return LookAndFeelPanel.newInstance(new GridBagLayout(), new HashMap<>());
    }


    protected JMenuBar createMenuBar() {
        return AppMenuBar.newInstance(new HashMap<>());
    }

}
