package com.isansc.mvvmpoc.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.isansc.mvvmpoc.R;
import com.isansc.mvvmpoc.databinding.ActivityMainBinding;
import com.isansc.mvvmpoc.model.Player;
import com.isansc.mvvmpoc.viewModel.GameViewModel;

/**
 * A POC Implementing MVVM to a Tic Tac Toe game, following the instructions on
 * https://medium.com/@husayn.hakeem/android-by-example-mvvm-data-binding-introduction-part-1-6a7a5f388bf7
 */
public class MainActivity extends AppCompatActivity {

    private static final String NO_WINNER = "No one";
    private GameViewModel gameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        promptForPlayers();
    }

    public void promptForPlayers() {
        onPlayersSet("Player 1", "Player 2");
    }

    public void onPlayersSet(String player1, String player2) {
        initDataBinding(player1, player2);
    }

    private void initDataBinding(String player1, String player2) {
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.init(player1, player2);
        activityMainBinding.setGameViewModel(gameViewModel);
        setUpOnGameEndListener();
    }

    private void setUpOnGameEndListener() {
        gameViewModel.getWinner().observe(this, this::onGameWinnerChanged);
    }

    @VisibleForTesting
    public void onGameWinnerChanged(Player winner) {
        String winnerName = winner == null || TextUtils.isEmpty(winner.name) ? NO_WINNER : winner.name;
        showEndGameDialog(winnerName);
    }

    private void showEndGameDialog(String winner){
        new AlertDialog.Builder(this)
                .setTitle("GAME ENDED")
                .setMessage("Winner: " + winner)
                .setCancelable(false)
                .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        promptForPlayers();
                    }
                })
                .create().show();
    }
}
