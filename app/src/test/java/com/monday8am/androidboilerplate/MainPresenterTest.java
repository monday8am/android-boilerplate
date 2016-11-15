package com.monday8am.androidboilerplate;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import com.monday8am.androidboilerplate.data.DataManager;
import com.monday8am.androidboilerplate.data.model.Ribot;
import com.monday8am.androidboilerplate.test.common.TestDataFactory;
import com.monday8am.androidboilerplate.ui.main.MainMvpView;
import com.monday8am.androidboilerplate.ui.main.MainPresenter;
import com.monday8am.androidboilerplate.util.RxSchedulersOverrideRule;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock MainMvpView mMockMainMvpView;
    @Mock DataManager mMockDataManager;
    private MainPresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mMainPresenter = new MainPresenter(mMockDataManager);
        mMainPresenter.attachView(mMockMainMvpView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void loadRibotsReturnsRibots() {

        List<Ribot> ribotList = TestDataFactory.makeListRibots(10);
        RealmResults<Ribot> ribots = mockRealmResults ();
        when(mMockDataManager.getRibots())
                .thenReturn(Observable.just(ribots));

        // we can't mock a RealmResult. The real ribotList is passed
        // in place if an iterator is needed.
        when(ribots.iterator()).thenReturn(ribotList.iterator());
        when(ribots.size()).thenReturn(ribotList.size());

        mMainPresenter.loadRibots();
        verify(mMockMainMvpView).showRibots(ribots);
        verify(mMockMainMvpView, never()).showRibotsEmpty();
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadRibotsReturnsEmptyList() {
        RealmResults<Ribot> ribots = mockRealmResults ();
        when(mMockDataManager.getRibots())
                .thenReturn(Observable.just(ribots));

        mMainPresenter.loadRibots();
        verify(mMockMainMvpView).showRibotsEmpty();
        verify(mMockMainMvpView, never()).showRibots(anyListOf(Ribot.class));
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadRibotsFails() {
        when(mMockDataManager.getRibots())
                .thenReturn(Observable.<List<Ribot>>error(new RuntimeException()));

        mMainPresenter.loadRibots();
        verify(mMockMainMvpView).showError();
        verify(mMockMainMvpView, never()).showRibotsEmpty();
        verify(mMockMainMvpView, never()).showRibots(anyListOf(Ribot.class));
    }

    @SuppressWarnings("unchecked")
    private <T extends RealmObject> RealmResults<T> mockRealmResults() {
        return mock(RealmResults.class);
    }

}