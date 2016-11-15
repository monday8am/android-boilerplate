package com.monday8am.androidboilerplate;
import com.monday8am.androidboilerplate.data.local.RealmHelper;
import com.monday8am.androidboilerplate.data.model.Name;
import com.monday8am.androidboilerplate.data.model.Profile;
import com.monday8am.androidboilerplate.data.model.Ribot;
import com.monday8am.androidboilerplate.test.common.TestDataFactory;
import com.monday8am.androidboilerplate.util.DefaultConfig;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.log.RealmLog;
import rx.observers.TestSubscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Unit tests integration with a Realm instance using Robolectric
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({Realm.class, RealmLog.class})
public class RealmHelperTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();
    private Realm mMockRealm;

    @Before
    public void setup() {
        mockStatic(RealmLog.class);
        mockStatic(Realm.class);

        Realm mockRealm = PowerMockito.mock(Realm.class);
        when(Realm.getDefaultInstance()).thenReturn(mockRealm);
        this.mMockRealm = mockRealm;
    }

    @Test
    public void shouldBeAbleToGetDefaultInstance() {
        assertThat(Realm.getDefaultInstance(), is(mMockRealm));
    }

    @Test
    public void shouldBeAbleToMockRealmMethods() {
        when(mMockRealm.isAutoRefresh()).thenReturn(true);
        assertThat(mMockRealm.isAutoRefresh(), is(true));

        when(mMockRealm.isAutoRefresh()).thenReturn(false);
        assertThat(mMockRealm.isAutoRefresh(), is(false));
    }

    @Test
    public void shouldBeAbleToCreateARealmObject() {
        Name name = new Name();
        when(mMockRealm.createObject(Name.class)).thenReturn(name);

        Name outputName = mMockRealm.createObject(Name.class);
        assertThat(outputName, is(name));

        Profile profile = new Profile();
        profile.setName(name);
        when(mMockRealm.createObject(Profile.class)).thenReturn(profile);

        Profile outputProfile = mMockRealm.createObject(Profile.class);
        assertThat(outputProfile, is(profile));
        assertThat(outputProfile.getName(), is(name));

        Ribot ribot = new Ribot();
        ribot.setProfile(profile);
        when(mMockRealm.createObject(Ribot.class)).thenReturn(ribot);

        Ribot output = mMockRealm.createObject(Ribot.class);
        assertThat(output, is(ribot));
        assertThat(output.getProfile(), is(profile));
        assertThat(output.getProfile().getName(), is(name));
    }

    @Test
    public void setRibots() {
        doCallRealMethod()
                .when(mMockRealm).executeTransaction(Mockito.any(Realm.Transaction.class));

        Ribot ribot1 = TestDataFactory.makeRibot("r1");
        Ribot ribot2 = TestDataFactory.makeRibot("r2");
        List<Ribot> ribots = Arrays.asList(ribot1, ribot2);

        when(mMockRealm.copyToRealm(ribot1)).thenReturn(ribot1);
        when(mMockRealm.copyToRealm(ribot2)).thenReturn(ribot2);

        TestSubscriber<Ribot> result = new TestSubscriber<>();
        RealmHelper mRealmHelper = new RealmHelper();
        mRealmHelper.setRibots(ribots).subscribe(result);
        result.assertNoErrors();
        result.assertReceivedOnNext(ribots);

        // Verify that Realm#copyToRealm was called only once per Ribot
        verify(mMockRealm, times(1)).copyToRealm(ribot1);
        verify(mMockRealm, times(1)).copyToRealm(ribot2);

        // Verify that the begin transaction was called only once
        verify(mMockRealm, times(1)).executeTransaction(Mockito.any(Realm.Transaction.class));

        // Verify that the Realm was closed only once.
        verify(mMockRealm, times(1)).close();
    }


    @Test
    public void getRibots() {
        RealmResults<Ribot> ribots = mockRealmResults();
        when(mMockRealm.where(Ribot.class).findAll()).thenReturn(ribots);

        RealmHelper mRealmHelper = new RealmHelper();
        TestSubscriber<List<Ribot>> result = new TestSubscriber<>();
        mRealmHelper.getRibots().subscribe(result);
        result.assertNoErrors();
        result.assertValue(ribots);
    }

    @SuppressWarnings("unchecked")
    private <T extends RealmObject> RealmResults<T> mockRealmResults() {
        return mock(RealmResults.class);
    }

}