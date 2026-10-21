package com.smallmanseries.farlandstraveler.client.sound;

import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;

public class FLTMusics {
    public static Music createMusic(Holder<SoundEvent> soundEvent) {
        return new Music(soundEvent, 12000, 24000, true);
    }
}
