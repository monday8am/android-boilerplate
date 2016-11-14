package com.monday8am.androidboilerplate.ui.main;

import java.util.List;

import com.monday8am.androidboilerplate.data.model.Ribot;
import com.monday8am.androidboilerplate.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showRibots(List<Ribot> ribots);

    void showRibotsEmpty();

    void showError();

}
