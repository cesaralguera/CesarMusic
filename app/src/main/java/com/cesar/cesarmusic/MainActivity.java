package com.cesar.cesarmusic;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnPlay, btnAnterior, btnStop, btnSiguiente;
    private TextView txtNombreMusic;
    private RecyclerView recyclerView;
    private Boolean isPlay = false;
    private adapterMusic adMusic;
    private List<String> songList;
    private MediaPlayer mediaPlayer;
    private AssetManager assetManager;
    private int currentSongIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btnPlay);
        btnAnterior = findViewById(R.id.btnAnterior);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnStop = findViewById(R.id.btnStop);
        txtNombreMusic = findViewById(R.id.txtNombreMusic);
        txtNombreMusic.setMarqueeRepeatLimit(-1);
        txtNombreMusic.setSelected(true);


        assetManager = getAssets();
        String[] songFiles = new String[0];
        try {
            songFiles = assetManager.list("canciones");
        } catch (IOException e) {
            e.printStackTrace();
        }

        songList = new ArrayList<>();
        for (String songFile : songFiles) {
            songList.add(songFile);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adMusic = new adapterMusic(this, songList);
        recyclerView.setAdapter(adMusic);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isPlay) {
                        btnPlay.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_play_arrow_black_24dp, 0);
                        isPlay = false;
                        txtNombreMusic.setVisibility(View.INVISIBLE);
                        mediaPlayer.pause();

                    } else {
                        btnPlay.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_pause_black_24dp, 0);
                        isPlay = true;
                        txtNombreMusic.setVisibility(View.VISIBLE);
                        playMusic(currentSongIndex);
                        //mediaPlayer.start();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtNombreMusic.setVisibility(View.INVISIBLE);
                try {
                    if (isPlay) {
                        btnPlay.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_play_arrow_black_24dp, 0);
                        isPlay = false;
                        currentSongIndex = 0;
                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        adMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildPosition(view);
                currentSongIndex = position;
                String selectedSong = songList.get(position);
                txtNombreMusic.setText(selectedSong);

            }
        });

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlay) {
                    if (currentSongIndex < songList.size() - 1) {
                        currentSongIndex--;
                        playMusic(currentSongIndex);
                    } else {
                        currentSongIndex = 0;
                        playMusic(currentSongIndex);
                    }
                }
            }
        });


        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlay) {
                    if (currentSongIndex < songList.size() + 1) {
                        currentSongIndex++;
                        playMusic(currentSongIndex);
                    } else {
                        currentSongIndex = 0;
                        playMusic(currentSongIndex);
                    }
                }
            }
        });


    }

    private void playMusic(int index) {
        try {
            txtNombreMusic.setVisibility(View.VISIBLE);
            AssetFileDescriptor descriptor = assetManager.openFd("canciones/" + songList.get(index));
            txtNombreMusic.setText(songList.get(index));

            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            } else {
                mediaPlayer.reset();
            }

            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}