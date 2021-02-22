package Windows;

import Classes.*;
import java.util.concurrent.Semaphore;

public class Menu extends javax.swing.JFrame {

    //<--/--...Declarate of Time Variables...--/-->//
    private int dayDuration;
    public static volatile int daysForDispatch;
    public static volatile int currentDispatch;

    //<--/--...Initialice of Piece Counter...--/-->//
    public static volatile int Buttons = 0;
    public static volatile int TouchScreens = 0;
    public static volatile int NormalScreens = 0;
    public static volatile int Joysticks = 0;
    public static volatile int SDCards = 0;

    //<--/--...Initialice of Console Counter...--/-->//
    public static volatile int Consoles = 0;
    public static volatile int TotalConsoles = 0;

    //<--/--...Initialice of Producers Array...--/-->//
    private ButtonProducer arrayButtons[];
    private ScreenProducer arrayScreens[];
    private JoystickProducer arrayJoysticks[];
    private SDProducer arraySDCards[];
    private Assembler arrayAssemblers[];
    
    //<--/--...Initialice of Management Variables...--/-->//
    private Boss boss;
    private Manager manager;

    public Menu() {
        initComponents();
        this.setSize(1260, 560);
        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        Functions functions = new Functions();
        String[] txtData = functions.separateData(functions.readTXT());

        //<--/--...Declarate of Time Variables...--/-->//
        this.setDayDuration(Integer.parseInt(txtData[0]));
        this.daysForDispatch = Integer.parseInt(txtData[1]);
        this.currentDispatch = this.daysForDispatch;
        this.DayDuration.setText(Integer.toString(this.dayDuration));
        this.DaysForDispatch.setText(Integer.toString(this.daysForDispatch));
        this.CurrentDispatch.setText(Integer.toString(this.currentDispatch));
        
        //<--/--...Declarate of Management Variables...--/-->//
        this.BossStatus.setText("Mimido");
        this.ManagerStatus.setText("Cambea");
        this.TotalConsole.setText(Integer.toString(this.TotalConsoles));
        
        //<--/--...Initialize of Active Producers...--/-->//
        this.ActiveButtonProducer.setText(txtData[6]);
        this.ActiveNormalScreenProducer.setText(txtData[7]);
        this.ActiveTouchScreenProducer.setText(txtData[7]);
        this.ActiveJoystickProducer.setText(txtData[8]);
        this.ActiveSDCardProducer.setText(txtData[9]);

        //<--/--...Initialize of Maximun Producers...--/-->//
        this.MaxButtonProducer.setText(txtData[10]);
        this.MaxNormalScreenProducer.setText(txtData[11]);
        this.MaxTouchScreenProducer.setText(txtData[11]);
        this.MaxJoystickProducer.setText(txtData[12]);
        this.MaxSDCardProducer.setText(txtData[13]);

        //<--/--...Initialize of Storage...--/-->//
        this.ButtonStorage.setText(Integer.toString(this.Buttons));
        this.NormalScreenStorage.setText(Integer.toString(this.NormalScreens));
        this.TouchScreenStorage.setText(Integer.toString(this.TouchScreens));
        this.JoystickStorage.setText(Integer.toString(this.Joysticks));
        this.SDCardStorage.setText(Integer.toString(this.SDCards));

        //<--/--...Initialize of Maximun Storage...--/-->//
        this.MaxButtonStorage.setText(txtData[2]);
        this.MaxNormalScreenStorage.setText(Integer.toString((Integer.parseInt(txtData[3]) / 2)));
        this.MaxTouchScreenStorage.setText(Integer.toString((Integer.parseInt(txtData[3]) / 2)));
        this.MaxJoystickStorage.setText(txtData[4]);
        this.MaxSDCardStorage.setText(txtData[5]);

        //<--/--...Initialice of Assembler...--/-->//
        this.ActiveConsoleAssembler.setText(txtData[14]);
        this.MaxConsoleAssembler.setText(txtData[15]);
        this.ConsoleStorage.setText(Integer.toString(this.Consoles));

        //<--/--...Semaphores for ButtonProducer...--/-->//
        Semaphore mutexButtons = new Semaphore(1);
        Semaphore semButtons = new Semaphore(Integer.parseInt(this.MaxButtonStorage.getText()));
        Semaphore semAssemblerButtons = new Semaphore(0);

        //<--/--...Semaphores for NormalScreenProducer...--/-->//
        Semaphore mutexNormalScreens = new Semaphore(1);
        Semaphore semNormalScreens = new Semaphore(Integer.parseInt(this.MaxNormalScreenStorage.getText()));
        Semaphore semAssemblerNormalScreens = new Semaphore(0);

        //<--/--...Semaphores for TouchScreenProducer...--/-->//
        Semaphore mutexTouchScreens = new Semaphore(1);
        Semaphore semTouchScreens = new Semaphore(Integer.parseInt(this.MaxTouchScreenStorage.getText()));
        Semaphore semAssemblerTouchScreens = new Semaphore(0);

        //<--/--...Semaphores for JoystickProducer...--/-->//
        Semaphore mutexJoysticks = new Semaphore(1);
        Semaphore semJoysticks = new Semaphore(Integer.parseInt(this.MaxJoystickStorage.getText()));
        Semaphore semAssemblerJoysticks = new Semaphore(0);

        //<--/--...Semaphores for SDCardProducer...--/-->//
        Semaphore mutexSDCards = new Semaphore(1);
        Semaphore semSDCards = new Semaphore(Integer.parseInt(this.MaxSDCardStorage.getText()));
        Semaphore semAssemblerSDCards = new Semaphore(0);

        //<--/--...Semaphore for Assembler...--/-->//
        Semaphore mutexAssembler = new Semaphore(1);

        //<--/--...Semaphore for Management...--/-->//
        Semaphore mutexManagement = new Semaphore(1);
        
        //<--/--...Initialization of ButtonProducerArray...--/-->//
        this.setArrayButtons(new ButtonProducer[Integer.parseInt(this.MaxButtonProducer.getText())]);
        for (int i = 0, j = 0; i < this.arrayButtons.length; i++) {
            this.arrayButtons[i] = new ButtonProducer(this.dayDuration, mutexButtons, semButtons, semAssemblerButtons);
            if (j < Integer.parseInt(this.ActiveButtonProducer.getText())) {
                this.arrayButtons[i].start();
                j++;
            }
        }

        //<--/--...Initialization of ScreenProducerArray...--/-->//
        this.setArrayScreens(new ScreenProducer[Integer.parseInt(this.MaxNormalScreenProducer.getText())]);
        for (int i = 0, j = 0; i < this.arrayScreens.length; i++) {
            this.arrayScreens[i] = new ScreenProducer(this.dayDuration, mutexNormalScreens, semNormalScreens, semAssemblerNormalScreens, mutexTouchScreens, semTouchScreens, semAssemblerTouchScreens);
            if (j < Integer.parseInt(this.ActiveNormalScreenProducer.getText())) {
                this.arrayScreens[i].start();
                j++;
            }
        }

        //<--/--...Initialization of ScreenProducerArray...--/-->//
        this.setArrayJoysticks(new JoystickProducer[Integer.parseInt(this.MaxJoystickProducer.getText())]);
        for (int i = 0, j = 0; i < this.arrayJoysticks.length; i++) {
            this.arrayJoysticks[i] = new JoystickProducer(this.dayDuration, mutexJoysticks, semJoysticks, semAssemblerJoysticks);
            if (j < Integer.parseInt(this.ActiveJoystickProducer.getText())) {
                this.arrayJoysticks[i].start();
                j++;
            }
        }

        //<--/--...Initialization of ScreenProducerArray...--/-->//
        this.setArraySDCards(new SDProducer[Integer.parseInt(this.MaxSDCardProducer.getText())]);
        for (int i = 0, j = 0; i < this.arraySDCards.length; i++) {
            this.arraySDCards[i] = new SDProducer(this.dayDuration, mutexSDCards, semSDCards, semAssemblerSDCards);
            if (j < Integer.parseInt(this.ActiveSDCardProducer.getText())) {
                this.arraySDCards[i].start();
                j++;
            }
        }

        //<--/--...Initialization of AssemblerArray...--/-->//
        this.setArrayAssemblers(new Assembler[Integer.parseInt(this.MaxConsoleAssembler.getText())]);
        for (int i = 0, j = 0; i < this.arrayAssemblers.length; i++) {
            this.arrayAssemblers[i] = new Assembler(dayDuration, mutexAssembler,
                    mutexButtons, semButtons, semAssemblerButtons,
                    mutexNormalScreens, semNormalScreens, semAssemblerNormalScreens,
                    mutexTouchScreens, semTouchScreens, semAssemblerTouchScreens,
                    mutexJoysticks, semJoysticks, semAssemblerJoysticks,
                    mutexSDCards, semSDCards, semAssemblerSDCards);
            if (j < Integer.parseInt(this.ActiveConsoleAssembler.getText())) {
                this.arrayAssemblers[i].start();
                j++;
            }
        }
        
        //<--/--...Asing of Boss...--/-->//
        this.boss = new Boss(dayDuration, mutexManagement);
        this.boss.start();
        
        //<--/--...Asing of Manager...--/-->//
        this.manager = new Manager(dayDuration, daysForDispatch, mutexManagement);
        this.manager.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Background = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ActiveSDCardProducer = new javax.swing.JTextField();
        MaxJoystickStorage = new javax.swing.JTextField();
        MaxSDCardProducer = new javax.swing.JTextField();
        SDCardStorage = new javax.swing.JTextField();
        MaxSDCardStorage = new javax.swing.JTextField();
        Blank = new javax.swing.JTextField();
        SDCard = new javax.swing.JTextField();
        JoystickStorage = new javax.swing.JTextField();
        MaxJoystickProducer = new javax.swing.JTextField();
        ActiveJoystickProducer = new javax.swing.JTextField();
        Joystick = new javax.swing.JTextField();
        MaxTouchScreenStorage = new javax.swing.JTextField();
        TouchScreenStorage = new javax.swing.JTextField();
        MaxTouchScreenProducer = new javax.swing.JTextField();
        ActiveTouchScreenProducer = new javax.swing.JTextField();
        TouchScreen = new javax.swing.JTextField();
        MaxNormalScreenStorage = new javax.swing.JTextField();
        MaxNormalScreenProducer = new javax.swing.JTextField();
        NormalScreenStorage = new javax.swing.JTextField();
        ActiveNormalScreenProducer = new javax.swing.JTextField();
        NormalScreen = new javax.swing.JTextField();
        MaxButtonStorage = new javax.swing.JTextField();
        ButtonStorage = new javax.swing.JTextField();
        MaxButtonProducer = new javax.swing.JTextField();
        ActiveButtonProducer = new javax.swing.JTextField();
        Button = new javax.swing.JTextField();
        MaxProducerStorage = new javax.swing.JTextField();
        OccupiedProducerStorage = new javax.swing.JTextField();
        ProducerStorage = new javax.swing.JTextField();
        MaxProducer = new javax.swing.JTextField();
        ActiveProducer = new javax.swing.JTextField();
        Producer = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        OutputConsole = new javax.swing.JTextArea();
        MaxSDCardProducer1 = new javax.swing.JTextField();
        MaxJoystickProducer1 = new javax.swing.JTextField();
        MaxTouchScreenProducer1 = new javax.swing.JTextField();
        MaxNormalScreenProducer1 = new javax.swing.JTextField();
        MaxButtonProducer1 = new javax.swing.JTextField();
        Blank1 = new javax.swing.JTextField();
        MaxConsoleStorage = new javax.swing.JTextField();
        ConsoleStorage = new javax.swing.JTextField();
        MaxConsoleAssembler = new javax.swing.JTextField();
        ActiveConsoleAssembler = new javax.swing.JTextField();
        Console = new javax.swing.JTextField();
        MaxAssemblerStorage = new javax.swing.JTextField();
        OccupiedAssemblerStorage = new javax.swing.JTextField();
        AssemblerStorage = new javax.swing.JTextField();
        MaxAssembler = new javax.swing.JTextField();
        ActiveAssembler = new javax.swing.JTextField();
        Assembler = new javax.swing.JTextField();
        MaxButtonProducer3 = new javax.swing.JTextField();
        Duration = new javax.swing.JTextField();
        TotalConsole = new javax.swing.JTextField();
        CurrentDispatch = new javax.swing.JTextField();
        ManagerStatus = new javax.swing.JTextField();
        DaysForDispatch = new javax.swing.JTextField();
        DayDuration = new javax.swing.JTextField();
        Total = new javax.swing.JTextField();
        Remaining = new javax.swing.JTextField();
        Manager = new javax.swing.JTextField();
        Boss = new javax.swing.JTextField();
        BossStatus = new javax.swing.JTextField();
        Interval = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Background.setBackground(new java.awt.Color(51, 51, 51));
        Background.setMinimumSize(new java.awt.Dimension(100, 100));
        Background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("High Tower Text", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("-");
        Background.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 30, 30));

        jLabel2.setFont(new java.awt.Font("High Tower Text", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("-");
        Background.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, 30, 30));

        ActiveSDCardProducer.setEditable(false);
        ActiveSDCardProducer.setBackground(new java.awt.Color(51, 51, 51));
        ActiveSDCardProducer.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        ActiveSDCardProducer.setForeground(new java.awt.Color(204, 204, 204));
        ActiveSDCardProducer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ActiveSDCardProducer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ActiveSDCardProducer.setFocusable(false);
        ActiveSDCardProducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActiveSDCardProducerActionPerformed(evt);
            }
        });
        Background.add(ActiveSDCardProducer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, 90, 30));

        MaxJoystickStorage.setEditable(false);
        MaxJoystickStorage.setBackground(new java.awt.Color(51, 51, 51));
        MaxJoystickStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxJoystickStorage.setForeground(new java.awt.Color(204, 204, 204));
        MaxJoystickStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxJoystickStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxJoystickStorage.setFocusable(false);
        MaxJoystickStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxJoystickStorageActionPerformed(evt);
            }
        });
        Background.add(MaxJoystickStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 240, 90, 30));

        MaxSDCardProducer.setEditable(false);
        MaxSDCardProducer.setBackground(new java.awt.Color(51, 51, 51));
        MaxSDCardProducer.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxSDCardProducer.setForeground(new java.awt.Color(204, 204, 204));
        MaxSDCardProducer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxSDCardProducer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxSDCardProducer.setFocusable(false);
        MaxSDCardProducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxSDCardProducerActionPerformed(evt);
            }
        });
        Background.add(MaxSDCardProducer, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, 90, 30));

        SDCardStorage.setEditable(false);
        SDCardStorage.setBackground(new java.awt.Color(51, 51, 51));
        SDCardStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        SDCardStorage.setForeground(new java.awt.Color(204, 204, 204));
        SDCardStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SDCardStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        SDCardStorage.setFocusable(false);
        SDCardStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SDCardStorageActionPerformed(evt);
            }
        });
        Background.add(SDCardStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 270, 90, 30));

        MaxSDCardStorage.setEditable(false);
        MaxSDCardStorage.setBackground(new java.awt.Color(51, 51, 51));
        MaxSDCardStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxSDCardStorage.setForeground(new java.awt.Color(204, 204, 204));
        MaxSDCardStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxSDCardStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxSDCardStorage.setFocusable(false);
        MaxSDCardStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxSDCardStorageActionPerformed(evt);
            }
        });
        Background.add(MaxSDCardStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 270, 90, 30));

        Blank.setEditable(false);
        Blank.setBackground(new java.awt.Color(51, 51, 51));
        Blank.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        Blank.setForeground(new java.awt.Color(204, 204, 204));
        Blank.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Blank.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Blank.setFocusable(false);
        Blank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlankActionPerformed(evt);
            }
        });
        Background.add(Blank, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 90, 60));

        SDCard.setEditable(false);
        SDCard.setBackground(new java.awt.Color(51, 51, 51));
        SDCard.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        SDCard.setForeground(new java.awt.Color(204, 204, 204));
        SDCard.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SDCard.setText("SD Card");
        SDCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        SDCard.setFocusable(false);
        SDCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SDCardActionPerformed(evt);
            }
        });
        Background.add(SDCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 90, 30));

        JoystickStorage.setEditable(false);
        JoystickStorage.setBackground(new java.awt.Color(51, 51, 51));
        JoystickStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        JoystickStorage.setForeground(new java.awt.Color(204, 204, 204));
        JoystickStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JoystickStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        JoystickStorage.setFocusable(false);
        JoystickStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JoystickStorageActionPerformed(evt);
            }
        });
        Background.add(JoystickStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 240, 90, 30));

        MaxJoystickProducer.setEditable(false);
        MaxJoystickProducer.setBackground(new java.awt.Color(51, 51, 51));
        MaxJoystickProducer.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxJoystickProducer.setForeground(new java.awt.Color(204, 204, 204));
        MaxJoystickProducer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxJoystickProducer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxJoystickProducer.setFocusable(false);
        MaxJoystickProducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxJoystickProducerActionPerformed(evt);
            }
        });
        Background.add(MaxJoystickProducer, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 90, 30));

        ActiveJoystickProducer.setEditable(false);
        ActiveJoystickProducer.setBackground(new java.awt.Color(51, 51, 51));
        ActiveJoystickProducer.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        ActiveJoystickProducer.setForeground(new java.awt.Color(204, 204, 204));
        ActiveJoystickProducer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ActiveJoystickProducer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ActiveJoystickProducer.setFocusable(false);
        ActiveJoystickProducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActiveJoystickProducerActionPerformed(evt);
            }
        });
        Background.add(ActiveJoystickProducer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 90, 30));

        Joystick.setEditable(false);
        Joystick.setBackground(new java.awt.Color(51, 51, 51));
        Joystick.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        Joystick.setForeground(new java.awt.Color(204, 204, 204));
        Joystick.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Joystick.setText("Joystick");
        Joystick.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Joystick.setFocusable(false);
        Joystick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JoystickActionPerformed(evt);
            }
        });
        Background.add(Joystick, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 90, 30));

        MaxTouchScreenStorage.setEditable(false);
        MaxTouchScreenStorage.setBackground(new java.awt.Color(51, 51, 51));
        MaxTouchScreenStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxTouchScreenStorage.setForeground(new java.awt.Color(204, 204, 204));
        MaxTouchScreenStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxTouchScreenStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxTouchScreenStorage.setFocusable(false);
        MaxTouchScreenStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxTouchScreenStorageActionPerformed(evt);
            }
        });
        Background.add(MaxTouchScreenStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 210, 90, 30));

        TouchScreenStorage.setEditable(false);
        TouchScreenStorage.setBackground(new java.awt.Color(51, 51, 51));
        TouchScreenStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        TouchScreenStorage.setForeground(new java.awt.Color(204, 204, 204));
        TouchScreenStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TouchScreenStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        TouchScreenStorage.setFocusable(false);
        TouchScreenStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TouchScreenStorageActionPerformed(evt);
            }
        });
        Background.add(TouchScreenStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, 90, 30));

        MaxTouchScreenProducer.setEditable(false);
        MaxTouchScreenProducer.setBackground(new java.awt.Color(51, 51, 51));
        MaxTouchScreenProducer.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxTouchScreenProducer.setForeground(new java.awt.Color(204, 204, 204));
        MaxTouchScreenProducer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxTouchScreenProducer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxTouchScreenProducer.setFocusable(false);
        MaxTouchScreenProducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxTouchScreenProducerActionPerformed(evt);
            }
        });
        Background.add(MaxTouchScreenProducer, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 90, 30));

        ActiveTouchScreenProducer.setEditable(false);
        ActiveTouchScreenProducer.setBackground(new java.awt.Color(51, 51, 51));
        ActiveTouchScreenProducer.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        ActiveTouchScreenProducer.setForeground(new java.awt.Color(204, 204, 204));
        ActiveTouchScreenProducer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ActiveTouchScreenProducer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ActiveTouchScreenProducer.setFocusable(false);
        ActiveTouchScreenProducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActiveTouchScreenProducerActionPerformed(evt);
            }
        });
        Background.add(ActiveTouchScreenProducer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 90, 30));

        TouchScreen.setEditable(false);
        TouchScreen.setBackground(new java.awt.Color(51, 51, 51));
        TouchScreen.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        TouchScreen.setForeground(new java.awt.Color(204, 204, 204));
        TouchScreen.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TouchScreen.setText("Táctil");
        TouchScreen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        TouchScreen.setFocusable(false);
        TouchScreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TouchScreenActionPerformed(evt);
            }
        });
        Background.add(TouchScreen, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 90, 30));

        MaxNormalScreenStorage.setEditable(false);
        MaxNormalScreenStorage.setBackground(new java.awt.Color(51, 51, 51));
        MaxNormalScreenStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxNormalScreenStorage.setForeground(new java.awt.Color(204, 204, 204));
        MaxNormalScreenStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxNormalScreenStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxNormalScreenStorage.setFocusable(false);
        MaxNormalScreenStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxNormalScreenStorageActionPerformed(evt);
            }
        });
        Background.add(MaxNormalScreenStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 180, 90, 30));

        MaxNormalScreenProducer.setEditable(false);
        MaxNormalScreenProducer.setBackground(new java.awt.Color(51, 51, 51));
        MaxNormalScreenProducer.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxNormalScreenProducer.setForeground(new java.awt.Color(204, 204, 204));
        MaxNormalScreenProducer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxNormalScreenProducer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxNormalScreenProducer.setFocusable(false);
        MaxNormalScreenProducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxNormalScreenProducerActionPerformed(evt);
            }
        });
        Background.add(MaxNormalScreenProducer, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 180, 90, 30));

        NormalScreenStorage.setEditable(false);
        NormalScreenStorage.setBackground(new java.awt.Color(51, 51, 51));
        NormalScreenStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        NormalScreenStorage.setForeground(new java.awt.Color(204, 204, 204));
        NormalScreenStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NormalScreenStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        NormalScreenStorage.setFocusable(false);
        NormalScreenStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NormalScreenStorageActionPerformed(evt);
            }
        });
        Background.add(NormalScreenStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, 90, 30));

        ActiveNormalScreenProducer.setEditable(false);
        ActiveNormalScreenProducer.setBackground(new java.awt.Color(51, 51, 51));
        ActiveNormalScreenProducer.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        ActiveNormalScreenProducer.setForeground(new java.awt.Color(204, 204, 204));
        ActiveNormalScreenProducer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ActiveNormalScreenProducer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ActiveNormalScreenProducer.setFocusable(false);
        ActiveNormalScreenProducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActiveNormalScreenProducerActionPerformed(evt);
            }
        });
        Background.add(ActiveNormalScreenProducer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 90, 30));

        NormalScreen.setEditable(false);
        NormalScreen.setBackground(new java.awt.Color(51, 51, 51));
        NormalScreen.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        NormalScreen.setForeground(new java.awt.Color(204, 204, 204));
        NormalScreen.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NormalScreen.setText("Pantalla");
        NormalScreen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        NormalScreen.setFocusable(false);
        NormalScreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NormalScreenActionPerformed(evt);
            }
        });
        Background.add(NormalScreen, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 90, 30));

        MaxButtonStorage.setEditable(false);
        MaxButtonStorage.setBackground(new java.awt.Color(51, 51, 51));
        MaxButtonStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxButtonStorage.setForeground(new java.awt.Color(204, 204, 204));
        MaxButtonStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxButtonStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxButtonStorage.setFocusable(false);
        MaxButtonStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxButtonStorageActionPerformed(evt);
            }
        });
        Background.add(MaxButtonStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 150, 90, 30));

        ButtonStorage.setEditable(false);
        ButtonStorage.setBackground(new java.awt.Color(51, 51, 51));
        ButtonStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        ButtonStorage.setForeground(new java.awt.Color(204, 204, 204));
        ButtonStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ButtonStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ButtonStorage.setFocusable(false);
        ButtonStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonStorageActionPerformed(evt);
            }
        });
        ButtonStorage.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                ButtonStoragePropertyChange(evt);
            }
        });
        Background.add(ButtonStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 150, 90, 30));

        MaxButtonProducer.setEditable(false);
        MaxButtonProducer.setBackground(new java.awt.Color(51, 51, 51));
        MaxButtonProducer.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxButtonProducer.setForeground(new java.awt.Color(204, 204, 204));
        MaxButtonProducer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxButtonProducer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxButtonProducer.setFocusable(false);
        MaxButtonProducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxButtonProducerActionPerformed(evt);
            }
        });
        Background.add(MaxButtonProducer, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 90, 30));

        ActiveButtonProducer.setEditable(false);
        ActiveButtonProducer.setBackground(new java.awt.Color(51, 51, 51));
        ActiveButtonProducer.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        ActiveButtonProducer.setForeground(new java.awt.Color(204, 204, 204));
        ActiveButtonProducer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ActiveButtonProducer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ActiveButtonProducer.setFocusable(false);
        ActiveButtonProducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActiveButtonProducerActionPerformed(evt);
            }
        });
        Background.add(ActiveButtonProducer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 90, 30));

        Button.setEditable(false);
        Button.setBackground(new java.awt.Color(51, 51, 51));
        Button.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        Button.setForeground(new java.awt.Color(204, 204, 204));
        Button.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Button.setText("Botones");
        Button.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Button.setFocusable(false);
        Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonActionPerformed(evt);
            }
        });
        Background.add(Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 90, 30));

        MaxProducerStorage.setEditable(false);
        MaxProducerStorage.setBackground(new java.awt.Color(51, 51, 51));
        MaxProducerStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxProducerStorage.setForeground(new java.awt.Color(204, 204, 204));
        MaxProducerStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxProducerStorage.setText("Máximo");
        MaxProducerStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxProducerStorage.setFocusable(false);
        MaxProducerStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxProducerStorageActionPerformed(evt);
            }
        });
        Background.add(MaxProducerStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, 90, 30));

        OccupiedProducerStorage.setEditable(false);
        OccupiedProducerStorage.setBackground(new java.awt.Color(51, 51, 51));
        OccupiedProducerStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        OccupiedProducerStorage.setForeground(new java.awt.Color(204, 204, 204));
        OccupiedProducerStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        OccupiedProducerStorage.setText("Ocupado");
        OccupiedProducerStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        OccupiedProducerStorage.setFocusable(false);
        OccupiedProducerStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OccupiedProducerStorageActionPerformed(evt);
            }
        });
        Background.add(OccupiedProducerStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 90, 30));

        ProducerStorage.setEditable(false);
        ProducerStorage.setBackground(new java.awt.Color(51, 51, 51));
        ProducerStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        ProducerStorage.setForeground(new java.awt.Color(204, 204, 204));
        ProducerStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ProducerStorage.setText("Almacen");
        ProducerStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ProducerStorage.setFocusable(false);
        ProducerStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProducerStorageActionPerformed(evt);
            }
        });
        Background.add(ProducerStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, 180, 30));

        MaxProducer.setEditable(false);
        MaxProducer.setBackground(new java.awt.Color(51, 51, 51));
        MaxProducer.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxProducer.setForeground(new java.awt.Color(204, 204, 204));
        MaxProducer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxProducer.setText("Máximo");
        MaxProducer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxProducer.setFocusable(false);
        MaxProducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxProducerActionPerformed(evt);
            }
        });
        Background.add(MaxProducer, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 90, 30));

        ActiveProducer.setEditable(false);
        ActiveProducer.setBackground(new java.awt.Color(51, 51, 51));
        ActiveProducer.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        ActiveProducer.setForeground(new java.awt.Color(204, 204, 204));
        ActiveProducer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ActiveProducer.setText("Activos");
        ActiveProducer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ActiveProducer.setFocusable(false);
        ActiveProducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActiveProducerActionPerformed(evt);
            }
        });
        Background.add(ActiveProducer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 180, 30));

        Producer.setEditable(false);
        Producer.setBackground(new java.awt.Color(51, 51, 51));
        Producer.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        Producer.setForeground(new java.awt.Color(204, 204, 204));
        Producer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Producer.setText("Productores");
        Producer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Producer.setFocusable(false);
        Producer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProducerActionPerformed(evt);
            }
        });
        Background.add(Producer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 270, 30));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        OutputConsole.setEditable(false);
        OutputConsole.setBackground(new java.awt.Color(0, 0, 0));
        OutputConsole.setColumns(20);
        OutputConsole.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        OutputConsole.setForeground(new java.awt.Color(0, 204, 0));
        OutputConsole.setRows(5);
        OutputConsole.setBorder(null);
        jScrollPane1.setViewportView(OutputConsole);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 430));

        Background.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 470, 430));

        MaxSDCardProducer1.setEditable(false);
        MaxSDCardProducer1.setBackground(new java.awt.Color(51, 51, 51));
        MaxSDCardProducer1.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxSDCardProducer1.setForeground(new java.awt.Color(204, 204, 204));
        MaxSDCardProducer1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxSDCardProducer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxSDCardProducer1.setFocusable(false);
        MaxSDCardProducer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxSDCardProducer1ActionPerformed(evt);
            }
        });
        Background.add(MaxSDCardProducer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 90, 30));

        MaxJoystickProducer1.setEditable(false);
        MaxJoystickProducer1.setBackground(new java.awt.Color(51, 51, 51));
        MaxJoystickProducer1.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxJoystickProducer1.setForeground(new java.awt.Color(204, 204, 204));
        MaxJoystickProducer1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxJoystickProducer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxJoystickProducer1.setFocusable(false);
        MaxJoystickProducer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxJoystickProducer1ActionPerformed(evt);
            }
        });
        Background.add(MaxJoystickProducer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 90, 30));

        MaxTouchScreenProducer1.setEditable(false);
        MaxTouchScreenProducer1.setBackground(new java.awt.Color(51, 51, 51));
        MaxTouchScreenProducer1.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxTouchScreenProducer1.setForeground(new java.awt.Color(204, 204, 204));
        MaxTouchScreenProducer1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxTouchScreenProducer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxTouchScreenProducer1.setFocusable(false);
        MaxTouchScreenProducer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxTouchScreenProducer1ActionPerformed(evt);
            }
        });
        Background.add(MaxTouchScreenProducer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 90, 30));

        MaxNormalScreenProducer1.setEditable(false);
        MaxNormalScreenProducer1.setBackground(new java.awt.Color(51, 51, 51));
        MaxNormalScreenProducer1.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxNormalScreenProducer1.setForeground(new java.awt.Color(204, 204, 204));
        MaxNormalScreenProducer1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxNormalScreenProducer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxNormalScreenProducer1.setFocusable(false);
        MaxNormalScreenProducer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxNormalScreenProducer1ActionPerformed(evt);
            }
        });
        Background.add(MaxNormalScreenProducer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 90, 30));

        MaxButtonProducer1.setEditable(false);
        MaxButtonProducer1.setBackground(new java.awt.Color(51, 51, 51));
        MaxButtonProducer1.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxButtonProducer1.setForeground(new java.awt.Color(204, 204, 204));
        MaxButtonProducer1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxButtonProducer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxButtonProducer1.setFocusable(false);
        MaxButtonProducer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxButtonProducer1ActionPerformed(evt);
            }
        });
        Background.add(MaxButtonProducer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 90, 30));

        Blank1.setEditable(false);
        Blank1.setBackground(new java.awt.Color(51, 51, 51));
        Blank1.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        Blank1.setForeground(new java.awt.Color(204, 204, 204));
        Blank1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Blank1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Blank1.setFocusable(false);
        Blank1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Blank1ActionPerformed(evt);
            }
        });
        Background.add(Blank1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 90, 60));

        MaxConsoleStorage.setEditable(false);
        MaxConsoleStorage.setBackground(new java.awt.Color(51, 51, 51));
        MaxConsoleStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxConsoleStorage.setForeground(new java.awt.Color(204, 204, 204));
        MaxConsoleStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxConsoleStorage.setText("Ilimitado");
        MaxConsoleStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxConsoleStorage.setFocusable(false);
        MaxConsoleStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxConsoleStorageActionPerformed(evt);
            }
        });
        Background.add(MaxConsoleStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 380, 90, 30));

        ConsoleStorage.setEditable(false);
        ConsoleStorage.setBackground(new java.awt.Color(51, 51, 51));
        ConsoleStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        ConsoleStorage.setForeground(new java.awt.Color(204, 204, 204));
        ConsoleStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ConsoleStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ConsoleStorage.setFocusable(false);
        ConsoleStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsoleStorageActionPerformed(evt);
            }
        });
        ConsoleStorage.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                ConsoleStoragePropertyChange(evt);
            }
        });
        Background.add(ConsoleStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 380, 90, 30));

        MaxConsoleAssembler.setEditable(false);
        MaxConsoleAssembler.setBackground(new java.awt.Color(51, 51, 51));
        MaxConsoleAssembler.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxConsoleAssembler.setForeground(new java.awt.Color(204, 204, 204));
        MaxConsoleAssembler.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxConsoleAssembler.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxConsoleAssembler.setFocusable(false);
        MaxConsoleAssembler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxConsoleAssemblerActionPerformed(evt);
            }
        });
        Background.add(MaxConsoleAssembler, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 380, 90, 30));

        ActiveConsoleAssembler.setEditable(false);
        ActiveConsoleAssembler.setBackground(new java.awt.Color(51, 51, 51));
        ActiveConsoleAssembler.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        ActiveConsoleAssembler.setForeground(new java.awt.Color(204, 204, 204));
        ActiveConsoleAssembler.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ActiveConsoleAssembler.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ActiveConsoleAssembler.setFocusable(false);
        ActiveConsoleAssembler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActiveConsoleAssemblerActionPerformed(evt);
            }
        });
        Background.add(ActiveConsoleAssembler, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 380, 90, 30));

        Console.setEditable(false);
        Console.setBackground(new java.awt.Color(51, 51, 51));
        Console.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        Console.setForeground(new java.awt.Color(204, 204, 204));
        Console.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Console.setText("Consolas");
        Console.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Console.setFocusable(false);
        Console.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsoleActionPerformed(evt);
            }
        });
        Background.add(Console, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 90, 30));

        MaxAssemblerStorage.setEditable(false);
        MaxAssemblerStorage.setBackground(new java.awt.Color(51, 51, 51));
        MaxAssemblerStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxAssemblerStorage.setForeground(new java.awt.Color(204, 204, 204));
        MaxAssemblerStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxAssemblerStorage.setText("Máximo");
        MaxAssemblerStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxAssemblerStorage.setFocusable(false);
        MaxAssemblerStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxAssemblerStorageActionPerformed(evt);
            }
        });
        Background.add(MaxAssemblerStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 350, 90, 30));

        OccupiedAssemblerStorage.setEditable(false);
        OccupiedAssemblerStorage.setBackground(new java.awt.Color(51, 51, 51));
        OccupiedAssemblerStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        OccupiedAssemblerStorage.setForeground(new java.awt.Color(204, 204, 204));
        OccupiedAssemblerStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        OccupiedAssemblerStorage.setText("Ocupado");
        OccupiedAssemblerStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        OccupiedAssemblerStorage.setFocusable(false);
        OccupiedAssemblerStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OccupiedAssemblerStorageActionPerformed(evt);
            }
        });
        Background.add(OccupiedAssemblerStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 350, 90, 30));

        AssemblerStorage.setEditable(false);
        AssemblerStorage.setBackground(new java.awt.Color(51, 51, 51));
        AssemblerStorage.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        AssemblerStorage.setForeground(new java.awt.Color(204, 204, 204));
        AssemblerStorage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AssemblerStorage.setText("Almacen");
        AssemblerStorage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        AssemblerStorage.setFocusable(false);
        AssemblerStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AssemblerStorageActionPerformed(evt);
            }
        });
        Background.add(AssemblerStorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 320, 180, 30));

        MaxAssembler.setEditable(false);
        MaxAssembler.setBackground(new java.awt.Color(51, 51, 51));
        MaxAssembler.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxAssembler.setForeground(new java.awt.Color(204, 204, 204));
        MaxAssembler.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxAssembler.setText("Máximo");
        MaxAssembler.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxAssembler.setFocusable(false);
        MaxAssembler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxAssemblerActionPerformed(evt);
            }
        });
        Background.add(MaxAssembler, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 90, 30));

        ActiveAssembler.setEditable(false);
        ActiveAssembler.setBackground(new java.awt.Color(51, 51, 51));
        ActiveAssembler.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        ActiveAssembler.setForeground(new java.awt.Color(204, 204, 204));
        ActiveAssembler.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ActiveAssembler.setText("Activos");
        ActiveAssembler.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ActiveAssembler.setFocusable(false);
        ActiveAssembler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActiveAssemblerActionPerformed(evt);
            }
        });
        Background.add(ActiveAssembler, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 350, 180, 30));

        Assembler.setEditable(false);
        Assembler.setBackground(new java.awt.Color(51, 51, 51));
        Assembler.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        Assembler.setForeground(new java.awt.Color(204, 204, 204));
        Assembler.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Assembler.setText("Ensambladores");
        Assembler.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Assembler.setFocusable(false);
        Assembler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AssemblerActionPerformed(evt);
            }
        });
        Background.add(Assembler, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 270, 30));

        MaxButtonProducer3.setEditable(false);
        MaxButtonProducer3.setBackground(new java.awt.Color(51, 51, 51));
        MaxButtonProducer3.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        MaxButtonProducer3.setForeground(new java.awt.Color(204, 204, 204));
        MaxButtonProducer3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MaxButtonProducer3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MaxButtonProducer3.setFocusable(false);
        MaxButtonProducer3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxButtonProducer3ActionPerformed(evt);
            }
        });
        Background.add(MaxButtonProducer3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 380, 90, 30));

        Duration.setEditable(false);
        Duration.setBackground(new java.awt.Color(51, 51, 51));
        Duration.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        Duration.setForeground(new java.awt.Color(204, 204, 204));
        Duration.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Duration.setText("Duración");
        Duration.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Duration.setFocusable(false);
        Duration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DurationActionPerformed(evt);
            }
        });
        Background.add(Duration, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 90, 30));

        TotalConsole.setEditable(false);
        TotalConsole.setBackground(new java.awt.Color(51, 51, 51));
        TotalConsole.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        TotalConsole.setForeground(new java.awt.Color(204, 204, 204));
        TotalConsole.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TotalConsole.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        TotalConsole.setFocusable(false);
        TotalConsole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TotalConsoleActionPerformed(evt);
            }
        });
        Background.add(TotalConsole, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, 90, 30));

        CurrentDispatch.setEditable(false);
        CurrentDispatch.setBackground(new java.awt.Color(51, 51, 51));
        CurrentDispatch.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        CurrentDispatch.setForeground(new java.awt.Color(204, 204, 204));
        CurrentDispatch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        CurrentDispatch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        CurrentDispatch.setFocusable(false);
        CurrentDispatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CurrentDispatchActionPerformed(evt);
            }
        });
        CurrentDispatch.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                CurrentDispatchPropertyChange(evt);
            }
        });
        Background.add(CurrentDispatch, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 90, 30));

        ManagerStatus.setEditable(false);
        ManagerStatus.setBackground(new java.awt.Color(51, 51, 51));
        ManagerStatus.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        ManagerStatus.setForeground(new java.awt.Color(204, 204, 204));
        ManagerStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ManagerStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ManagerStatus.setFocusable(false);
        ManagerStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManagerStatusActionPerformed(evt);
            }
        });
        Background.add(ManagerStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 90, 30));

        DaysForDispatch.setEditable(false);
        DaysForDispatch.setBackground(new java.awt.Color(51, 51, 51));
        DaysForDispatch.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        DaysForDispatch.setForeground(new java.awt.Color(204, 204, 204));
        DaysForDispatch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DaysForDispatch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        DaysForDispatch.setFocusable(false);
        DaysForDispatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DaysForDispatchActionPerformed(evt);
            }
        });
        Background.add(DaysForDispatch, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 90, 30));

        DayDuration.setEditable(false);
        DayDuration.setBackground(new java.awt.Color(51, 51, 51));
        DayDuration.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        DayDuration.setForeground(new java.awt.Color(204, 204, 204));
        DayDuration.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DayDuration.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        DayDuration.setFocusable(false);
        DayDuration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DayDurationActionPerformed(evt);
            }
        });
        Background.add(DayDuration, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 90, 30));

        Total.setEditable(false);
        Total.setBackground(new java.awt.Color(51, 51, 51));
        Total.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        Total.setForeground(new java.awt.Color(204, 204, 204));
        Total.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Total.setText("Total");
        Total.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Total.setFocusable(false);
        Total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TotalActionPerformed(evt);
            }
        });
        Background.add(Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 90, 30));

        Remaining.setEditable(false);
        Remaining.setBackground(new java.awt.Color(51, 51, 51));
        Remaining.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        Remaining.setForeground(new java.awt.Color(204, 204, 204));
        Remaining.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Remaining.setText("Restantes");
        Remaining.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Remaining.setFocusable(false);
        Remaining.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemainingActionPerformed(evt);
            }
        });
        Background.add(Remaining, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 90, 30));

        Manager.setEditable(false);
        Manager.setBackground(new java.awt.Color(51, 51, 51));
        Manager.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        Manager.setForeground(new java.awt.Color(204, 204, 204));
        Manager.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Manager.setText("Gerente");
        Manager.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Manager.setFocusable(false);
        Manager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManagerActionPerformed(evt);
            }
        });
        Background.add(Manager, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 90, 30));

        Boss.setEditable(false);
        Boss.setBackground(new java.awt.Color(51, 51, 51));
        Boss.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        Boss.setForeground(new java.awt.Color(204, 204, 204));
        Boss.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Boss.setText("Jefe");
        Boss.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Boss.setFocusable(false);
        Boss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BossActionPerformed(evt);
            }
        });
        Background.add(Boss, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 90, 30));

        BossStatus.setEditable(false);
        BossStatus.setBackground(new java.awt.Color(51, 51, 51));
        BossStatus.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        BossStatus.setForeground(new java.awt.Color(204, 204, 204));
        BossStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        BossStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        BossStatus.setFocusable(false);
        BossStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BossStatusActionPerformed(evt);
            }
        });
        Background.add(BossStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 90, 30));

        Interval.setEditable(false);
        Interval.setBackground(new java.awt.Color(51, 51, 51));
        Interval.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
        Interval.setForeground(new java.awt.Color(204, 204, 204));
        Interval.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Interval.setText("Intervalo");
        Interval.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Interval.setFocusable(false);
        Interval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IntervalActionPerformed(evt);
            }
        });
        Background.add(Interval, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 90, 30));

        getContentPane().add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 430));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OccupiedProducerStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OccupiedProducerStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OccupiedProducerStorageActionPerformed

    private void SDCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SDCardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SDCardActionPerformed

    private void MaxNormalScreenProducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxNormalScreenProducerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxNormalScreenProducerActionPerformed

    private void ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonActionPerformed

    private void NormalScreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NormalScreenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NormalScreenActionPerformed

    private void TouchScreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TouchScreenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TouchScreenActionPerformed

    private void JoystickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JoystickActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JoystickActionPerformed

    private void MaxProducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxProducerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxProducerActionPerformed

    private void ActiveProducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActiveProducerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ActiveProducerActionPerformed

    private void ActiveButtonProducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActiveButtonProducerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ActiveButtonProducerActionPerformed

    private void ActiveNormalScreenProducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActiveNormalScreenProducerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ActiveNormalScreenProducerActionPerformed

    private void MaxButtonProducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxButtonProducerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxButtonProducerActionPerformed

    private void ActiveTouchScreenProducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActiveTouchScreenProducerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ActiveTouchScreenProducerActionPerformed

    private void MaxTouchScreenProducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxTouchScreenProducerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxTouchScreenProducerActionPerformed

    private void MaxJoystickProducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxJoystickProducerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxJoystickProducerActionPerformed

    private void ActiveJoystickProducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActiveJoystickProducerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ActiveJoystickProducerActionPerformed

    private void MaxSDCardProducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxSDCardProducerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxSDCardProducerActionPerformed

    private void ActiveSDCardProducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActiveSDCardProducerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ActiveSDCardProducerActionPerformed

    private void TouchScreenStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TouchScreenStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TouchScreenStorageActionPerformed

    private void JoystickStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JoystickStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JoystickStorageActionPerformed

    private void NormalScreenStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NormalScreenStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NormalScreenStorageActionPerformed

    private void SDCardStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SDCardStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SDCardStorageActionPerformed

    private void ButtonStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonStorageActionPerformed

    private void MaxButtonStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxButtonStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxButtonStorageActionPerformed

    private void MaxSDCardStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxSDCardStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxSDCardStorageActionPerformed

    private void MaxJoystickStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxJoystickStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxJoystickStorageActionPerformed

    private void MaxTouchScreenStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxTouchScreenStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxTouchScreenStorageActionPerformed

    private void MaxNormalScreenStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxNormalScreenStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxNormalScreenStorageActionPerformed

    private void MaxProducerStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxProducerStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxProducerStorageActionPerformed

    private void ProducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProducerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProducerActionPerformed

    private void ProducerStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProducerStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProducerStorageActionPerformed

    private void BlankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BlankActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BlankActionPerformed

    private void ButtonStoragePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_ButtonStoragePropertyChange

    }//GEN-LAST:event_ButtonStoragePropertyChange

    private void MaxSDCardProducer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxSDCardProducer1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxSDCardProducer1ActionPerformed

    private void MaxJoystickProducer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxJoystickProducer1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxJoystickProducer1ActionPerformed

    private void MaxTouchScreenProducer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxTouchScreenProducer1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxTouchScreenProducer1ActionPerformed

    private void MaxNormalScreenProducer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxNormalScreenProducer1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxNormalScreenProducer1ActionPerformed

    private void MaxButtonProducer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxButtonProducer1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxButtonProducer1ActionPerformed

    private void Blank1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Blank1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Blank1ActionPerformed

    private void ConsoleStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsoleStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ConsoleStorageActionPerformed

    private void ConsoleStoragePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_ConsoleStoragePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_ConsoleStoragePropertyChange

    private void MaxConsoleAssemblerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxConsoleAssemblerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxConsoleAssemblerActionPerformed

    private void ActiveConsoleAssemblerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActiveConsoleAssemblerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ActiveConsoleAssemblerActionPerformed

    private void ConsoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsoleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ConsoleActionPerformed

    private void OccupiedAssemblerStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OccupiedAssemblerStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OccupiedAssemblerStorageActionPerformed

    private void AssemblerStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AssemblerStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AssemblerStorageActionPerformed

    private void MaxAssemblerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxAssemblerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxAssemblerActionPerformed

    private void ActiveAssemblerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActiveAssemblerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ActiveAssemblerActionPerformed

    private void AssemblerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AssemblerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AssemblerActionPerformed

    private void MaxButtonProducer3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxButtonProducer3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxButtonProducer3ActionPerformed

    private void MaxConsoleStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxConsoleStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxConsoleStorageActionPerformed

    private void MaxAssemblerStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxAssemblerStorageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxAssemblerStorageActionPerformed

    private void DurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DurationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DurationActionPerformed

    private void TotalConsoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TotalConsoleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalConsoleActionPerformed

    private void CurrentDispatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CurrentDispatchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CurrentDispatchActionPerformed

    private void CurrentDispatchPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_CurrentDispatchPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_CurrentDispatchPropertyChange

    private void ManagerStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManagerStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ManagerStatusActionPerformed

    private void DaysForDispatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DaysForDispatchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DaysForDispatchActionPerformed

    private void DayDurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DayDurationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DayDurationActionPerformed

    private void TotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalActionPerformed

    private void RemainingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemainingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RemainingActionPerformed

    private void ManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManagerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ManagerActionPerformed

    private void BossActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BossActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BossActionPerformed

    private void BossStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BossStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BossStatusActionPerformed

    private void IntervalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IntervalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntervalActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    public int getDayDuration() {
        return dayDuration;
    }

    public void setDayDuration(int dayDuration) {
        this.dayDuration = dayDuration;
    }

    public int getDaysForDispatch() {
        return currentDispatch;
    }

    public void setDaysForDispatch(int daysForDispatch) {
        this.currentDispatch = daysForDispatch;
    }

    public ButtonProducer[] getArrayButtons() {
        return arrayButtons;
    }

    public void setArrayButtons(ButtonProducer[] arrayButtons) {
        this.arrayButtons = arrayButtons;
    }

    public ScreenProducer[] getArrayScreens() {
        return arrayScreens;
    }

    public void setArrayScreens(ScreenProducer[] arrayScreens) {
        this.arrayScreens = arrayScreens;
    }

    public JoystickProducer[] getArrayJoysticks() {
        return arrayJoysticks;
    }

    public void setArrayJoysticks(JoystickProducer[] arrayJoysticks) {
        this.arrayJoysticks = arrayJoysticks;
    }

    public SDProducer[] getArraySDCards() {
        return arraySDCards;
    }

    public void setArraySDCards(SDProducer[] arraySDCards) {
        this.arraySDCards = arraySDCards;
    }

    public Assembler[] getArrayAssemblers() {
        return arrayAssemblers;
    }

    public void setArrayAssemblers(Assembler[] arrayAssemblers) {
        this.arrayAssemblers = arrayAssemblers;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ActiveAssembler;
    private javax.swing.JTextField ActiveButtonProducer;
    private javax.swing.JTextField ActiveConsoleAssembler;
    private javax.swing.JTextField ActiveJoystickProducer;
    private javax.swing.JTextField ActiveNormalScreenProducer;
    private javax.swing.JTextField ActiveProducer;
    private javax.swing.JTextField ActiveSDCardProducer;
    private javax.swing.JTextField ActiveTouchScreenProducer;
    private javax.swing.JTextField Assembler;
    private javax.swing.JTextField AssemblerStorage;
    private javax.swing.JPanel Background;
    private javax.swing.JTextField Blank;
    private javax.swing.JTextField Blank1;
    private javax.swing.JTextField Boss;
    public static volatile javax.swing.JTextField BossStatus;
    private javax.swing.JTextField Button;
    public static volatile javax.swing.JTextField ButtonStorage;
    private javax.swing.JTextField Console;
    public static volatile javax.swing.JTextField ConsoleStorage;
    public static volatile javax.swing.JTextField CurrentDispatch;
    private javax.swing.JTextField DayDuration;
    private javax.swing.JTextField DaysForDispatch;
    private javax.swing.JTextField Duration;
    private javax.swing.JTextField Interval;
    private javax.swing.JTextField Joystick;
    public static volatile javax.swing.JTextField JoystickStorage;
    private javax.swing.JTextField Manager;
    public static volatile javax.swing.JTextField ManagerStatus;
    private javax.swing.JTextField MaxAssembler;
    private javax.swing.JTextField MaxAssemblerStorage;
    private javax.swing.JTextField MaxButtonProducer;
    private javax.swing.JTextField MaxButtonProducer1;
    private javax.swing.JTextField MaxButtonProducer3;
    private javax.swing.JTextField MaxButtonStorage;
    private javax.swing.JTextField MaxConsoleAssembler;
    private javax.swing.JTextField MaxConsoleStorage;
    private javax.swing.JTextField MaxJoystickProducer;
    private javax.swing.JTextField MaxJoystickProducer1;
    private javax.swing.JTextField MaxJoystickStorage;
    private javax.swing.JTextField MaxNormalScreenProducer;
    private javax.swing.JTextField MaxNormalScreenProducer1;
    private javax.swing.JTextField MaxNormalScreenStorage;
    private javax.swing.JTextField MaxProducer;
    private javax.swing.JTextField MaxProducerStorage;
    private javax.swing.JTextField MaxSDCardProducer;
    private javax.swing.JTextField MaxSDCardProducer1;
    private javax.swing.JTextField MaxSDCardStorage;
    private javax.swing.JTextField MaxTouchScreenProducer;
    private javax.swing.JTextField MaxTouchScreenProducer1;
    private javax.swing.JTextField MaxTouchScreenStorage;
    private javax.swing.JTextField NormalScreen;
    public static volatile javax.swing.JTextField NormalScreenStorage;
    private javax.swing.JTextField OccupiedAssemblerStorage;
    private javax.swing.JTextField OccupiedProducerStorage;
    public static volatile javax.swing.JTextArea OutputConsole;
    private javax.swing.JTextField Producer;
    private javax.swing.JTextField ProducerStorage;
    private javax.swing.JTextField Remaining;
    private javax.swing.JTextField SDCard;
    public static volatile javax.swing.JTextField SDCardStorage;
    private javax.swing.JTextField Total;
    public static volatile javax.swing.JTextField TotalConsole;
    private javax.swing.JTextField TouchScreen;
    public static volatile javax.swing.JTextField TouchScreenStorage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
