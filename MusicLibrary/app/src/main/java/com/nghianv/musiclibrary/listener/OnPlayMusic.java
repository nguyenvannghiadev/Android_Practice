package com.nghianv.musiclibrary.listener;

import com.nghianv.musiclibrary.model.Song;

import java.util.List;

public interface OnPlayMusic {
    void playSong(List<Song> list, int position);
}
