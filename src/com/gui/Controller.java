package com.gui;

import com.core.common.BaseModel;
import com.core.common.StartKeyListener;
import com.core.common.StopKeyListener;
import com.core.util.TimeAndRandomUtil;
import com.model.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * 控件控制类
 *
 * @author Celphis
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
public class Controller {
    public Integer modelType = 0;
    public boolean runFlag = false;
    public boolean initFlag = false;
    private BaseModel model;

    public Controller() {
        StartKeyListener.setController(this);
        StartKeyListener.register();
        StartKeyListener.addListener();
    }

    @FXML
    public RadioButton huanGouLiang;

    @FXML
    public RadioButton zuDuiTanSuo;

    @FXML
    public TextField tanSuoTimes;

    @FXML
    public RadioButton daJieJie;

    @FXML
    public RadioButton buHuanGouLiang;
    @FXML
    public Pane root;

    @FXML
    public Button btnStart;

    @FXML
    public Button btnStop;
    @FXML
    private TextArea logArea;

    @FXML
    public RadioButton danRenTanSuo;

    @FXML
    public RadioButton buDaJieJie;

    @FXML
    public TabPane tabPane;
    @FXML
    public ImageView gifImg;
    @FXML
    public Label logLable;
    @FXML
    private Tab jieJiePane;
    @FXML
    private Tab baiGuiPane;
    @FXML
    private Tab tanSuoPane;
    @FXML
    private Tab yuHunPane;
    @FXML
    private Tab yuLinPane;
    public ToggleGroup tanSuoModel = new ToggleGroup();
    public ToggleGroup tanSuoJieJie = new ToggleGroup();
    public ToggleGroup tanSuoGouLiang = new ToggleGroup();

    @FXML
    public void stop(Event event) {
        runFlag = false;
        if (model != null) {
            model.interrupt();
            model = null;
        }
        tabPane.setDisable(false);
        btnStop.setDisable(true);
        StopKeyListener.unregister();
        StartKeyListener.register();
        StartKeyListener.addListener();
        btnStart.setDisable(false);
        log("科技暂停...");
    }

    @FXML
    public void start(Event event) {
        try {
            runFlag = true;
            StartKeyListener.unregister();
            StopKeyListener.register();
            StopKeyListener.addListener();
            log("科技启动...");
            tabPane.setDisable(true);
            btnStart.setDisable(true);
            btnStop.setDisable(false);
            switch (tabPane.getSelectionModel().getSelectedItem().getText()) {
                case "探索":
                    if (model == null) {
                        model = new TanSuo();
                        model.start();
                    }
                    break;
                case "御魂":
                    if (model == null) {
                        model = new YuHun();
                        model.start();
                    }
                    break;
                case "御灵":
                    if (model == null) {
                        model = new YuLin();
                        model.start();
                    }
                    break;
                case "结界":
                    if (model == null) {
                        model = new JieJie();
                        model.start();
                    }
                    break;
                case "百鬼":
                    if (model == null) {
                        model = new BaiGui();
                        model.start();
                    }
                    break;
                default:
                    log("发生未知异常");
                    break;
            }
        } catch (Exception e) {
            log(e.toString());
        }
    }

    @FXML
    void tanSuoModel(Event event) {
        if (initFlag && modelType != 0) {
            modelType = 0;
            log("选择探索模式");
        }
    }

    @FXML
    void yuHunModel(Event event) {
        if (initFlag && modelType != 1) {
            modelType = 1;
            log("选择御魂模式");
        }
    }

    @FXML
    void yuLinModel(Event event) {
        if (initFlag && modelType != 2) {
            modelType = 2;
            log("选择御灵模式");
        }
    }

    @FXML
    void jieJieModel(Event event) {
        if (initFlag && modelType != 3) {
            modelType = 3;
            log("选择结界模式");
        }
    }

    @FXML
    void baiGuiModel(Event event) {
        if (initFlag && modelType != 4) {
            modelType = 4;
            log("选择百鬼模式");
        }
    }

    public TextArea getLogArea() {
        return logArea;
    }

    public void log(String log) {
        logArea.appendText(TimeAndRandomUtil.getTime() + "  " + log + "\n");
    }
}
