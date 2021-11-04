package com.khacngoc.myproject;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.reward.Reward;
import com.huawei.hms.ads.reward.RewardAd;
import com.huawei.hms.ads.reward.RewardAdLoadListener;
import com.huawei.hms.ads.reward.RewardAdStatusListener;

public class RewardActivity extends AppCompatActivity {
    private Button watchAdButton;

    private RewardAd rewardAd;

    private TextView scoreView;

    private int score = 1;

    private int defaultScore = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_ads);

        // Initialize the HUAWEI Ads SDK.
        HwAds.init(this);

        // Load a rewarded ad.
        loadRewardAd();

        // Load the button for watching a rewarded ad.
        loadWatchVideoButton();

        // Load a score view.
        loadScoreView();
    }

    private void createRewardAd() {
        rewardAd = new RewardAd(RewardActivity.this, "testx9dtjwj8hp");
    }

    private void loadRewardAd() {
        if (rewardAd == null) {
            createRewardAd();
        }
        RewardAdLoadListener rewardAdLoadListener = new RewardAdLoadListener() {
            @Override
            public void onRewardAdFailedToLoad(int errorCode) {
                Toast.makeText(RewardActivity.this, "onRewardAdFailedToLoad errorCode is :" + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedLoaded() {
                Toast.makeText(RewardActivity.this, "onRewardedLoaded", Toast.LENGTH_SHORT).show();
            }
        };

        rewardAd.loadAd(new AdParam.Builder().build(), rewardAdLoadListener);
    }

    /**
     * Load the button for watching a rewarded ad.
     */
    private void loadWatchVideoButton() {
        watchAdButton = findViewById(R.id.show_video_button);
        watchAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewardAdShow();
            }
        });
    }

    private void loadScoreView() {
        scoreView = findViewById(R.id.coin_count_text);
        scoreView.setText("Score:" + score);
    }

    /**
     * Display a rewarded ad.
     */
    private void rewardAdShow() {
        if (rewardAd.isLoaded()) {
            rewardAd.show(RewardActivity.this, new RewardAdStatusListener() {
                @Override
                public void onRewardAdClosed() {
                    loadRewardAd();
                }

                @Override
                public void onRewardAdFailedToShow(int errorCode) {
                    Toast.makeText(RewardActivity.this, "onRewardAdFailedToShow errorCode is :" + errorCode, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onRewardAdOpened() {
                    Toast.makeText(RewardActivity.this, "onRewardAdOpened", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onRewarded(Reward reward) {
                    // You are advised to grant a reward immediately and at the same time, check whether the reward takes effect on the server.
                    // If no reward information is configured, grant a reward based on the actual scenario.
                    int addScore = reward.getAmount() == 0 ? defaultScore : reward.getAmount();
                    Toast.makeText(RewardActivity.this, "Watch video show finished, add " + addScore + " scores", Toast.LENGTH_SHORT).show();
                    addScore(addScore);
                    loadRewardAd();
                }
            });
        }
    }

    private void addScore(int addScore) {
        score += addScore;
        scoreView.setText("Score:" + score);
    }
}
